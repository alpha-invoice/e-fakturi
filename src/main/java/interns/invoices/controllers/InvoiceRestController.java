package interns.invoices.controllers;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import interns.invoices.exceptions.InvalidInvoiceException;
import interns.invoices.models.Company;
import interns.invoices.models.Invoice;
import interns.invoices.models.UserInfo;
import interns.invoices.repositories.CompanyRepository;
import interns.invoices.repositories.InvoiceRepository;
import interns.invoices.repositories.UserRepository;
import interns.invoices.services.CreatePDFService;

/**
 * Defines a RestController class where we specify the endpoint URLs for the
 * Company service. It uses an Autowired annotation for our repository which
 * tells spring boot to inject an instance of our {@link CompanyRepository} and
 * {@link InvoiceRepository}.
 */
@RestController
@CrossOrigin
public class InvoiceRestController {
    private static final String CONTENT_DISPOSITION_TYPE_INLINE_STRING = "attachment; filname=";
    private static final String DEFAULT_TEMPLATE_PATH = "defaultTemplate.docx";
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * GET Service URL for finding all created invoices.
     *
     * @return a json representation of all invoices found in the repository or
     *         an empty collection
     */
    // TODO: Should return all invoices created by a user
    @RequestMapping("/api/invoices")
    Collection<Invoice> getAllInvoices(HttpServletRequest request) {
        UserInfo cachedUser = (UserInfo) request.getSession().getAttribute("user");
        Collection<Invoice> invoices = new HashSet<>();
        cachedUser.getMyCompanies().stream().forEach(company -> invoices.addAll(company.getIssuedInvoices()));
        return invoices;
    }

    /**
     * Creates and invoice with the selected template in pdf format and returns
     * it to the user for download.
     *
     * @param invoice
     *            The {@link Invoice} object containing the data which is to be
     *            printed on the pdf.
     * @return Response entity containing the status code and, if creation is
     *         successful, the invoice pdf file.
     * @throws InvalidInvoiceException
     *             Occurs when the entered data is in an invalid format.
     */
    @CrossOrigin
    @RequestMapping(value = "/api/invoices/create", method = RequestMethod.POST)
    public ResponseEntity<InputStreamResource> createInvoice(@RequestBody Invoice invoice, HttpServletRequest request)
            throws InvalidInvoiceException {
        ResponseEntity<InputStreamResource> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserInfo cachedUser = (UserInfo) request.getSession().getAttribute("user");

        String templateName = invoice.getTemplateName();
        try {
            InputStream inputstream = null;
            if (TemplateRestController.DEFAUL_TEMPLATE.equals(templateName)) {
                inputstream = new FileInputStream(DEFAULT_TEMPLATE_PATH);
            } else {
                inputstream = new ByteArrayInputStream(
                        userRepository.findOne(cachedUser.getId()).getTemplate(templateName).getUserInvoiceTemplate());
            }

            response = ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            CONTENT_DISPOSITION_TYPE_INLINE_STRING +
                                    invoice.getSender().getName() + invoice.getInvoiceNumber() + "\"")
                    .body(new InputStreamResource(
                            new ByteArrayInputStream(
                                    CreatePDFService.createInvoicePDF(invoice, inputstream).toByteArray())));
        } catch (javax.validation.ConstraintViolationException | IOException cve) {
            throw new InvalidInvoiceException(cve);
        } catch (IllegalStateException e) {
            // when tring to update user existing company throws this exception
            System.out.println("Error while trying to update tha values of excisting enity with same values ");
        } catch (Exception e) {
            // In case docx4j cannot load OpenXML schemas.
            e.printStackTrace();
        }

            // get cached user
            // set invoice owner
            invoice.getSender().setOwner(cachedUser);
            //here we update the two companies in our database who match
            //the companies from the invoice
            Company sender = updateCompany(invoice.getSender());
            cachedUser.addCompany(sender);
            updateCompany(invoice.getRecipient());
            this.invoiceRepository.save(invoice);
        // response = parseInvoiceToResponseObject(invoice);

        return response;
    }

    // private ResponseEntity<InputStreamResource>
    // parseInvoiceToResponseObject(Invoice invoice)
    // throws Docx4JException, JAXBException, IOException {
    // return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
    // .header(HttpHeaders.CONTENT_DISPOSITION,
    // CONTENT_DISPOSITION_TYPE_INLINE_STRING + invoice.getSender().getName()
    // + invoice.getInvoiceNumber() + "\"")
    // .body(new InputStreamResource(
    // new
    // ByteArrayInputStream(CreatePDFService.createInvoicePDF(invoice).toByteArray())));
    // }


    /**
     * Updates a company record by verifying whether it exists. If a company
     * with an EIK number already exists, the record is updated. Otherwise a new
     * company record is stored in the database.
     *
     * @param company
     *            the company we want to store in the database.
     */
    private Company updateCompany(Company company) {
        Optional<Long> companyId = checkCompanyInDB(company.getEik());
        companyId.ifPresent(company::setId);
        return this.companyRepository.save(company);
    }

    /**
     * Checks whether a company record already exists in the database by
     * providing an EIK number.
     *
     * @param eik
     *            the company EIK by which we perform the search.
     * @return Optional company id.
     */
    private Optional<Long> checkCompanyInDB(String eik) {
        Company found = this.companyRepository.findCompanyByEik(eik);
        if (found != null) {
            return Optional.of(found.getId());
        }
        return Optional.empty();
    }
}
