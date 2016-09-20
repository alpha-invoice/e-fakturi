package interns.invoices.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import javax.xml.bind.JAXBException;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
import services.CreatePDFService;

/**
 * Defines a RestController class where we specify the endpoint URLs for the
 * Company service. It uses an Autowired annotation for our repository which
 * tells spring boot to inject an instance of our {@link CompanyRepository} and
 * {@link InvoiceRepository}.
 */
@RestController
@CrossOrigin
public class InvoiceRestController {
    private static final String CONTENT_DISPOSITION_TYPE_INLINE_STRING = "inline;filname=";
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
        UserInfo cachedUser = (UserInfo)request.getSession().getAttribute("user");
        UserInfo user = this.userRepository.findOne(cachedUser.getId());
        Collection<Invoice> invoices = new HashSet<>();
        user.getMyCompanies().stream().forEach(company->invoices.addAll(company.getIssuedInvoices()));
        return invoices;
    }

    /**
     * PATCH Service URL which stores a new invoice to the database. If the
     * request json body contains a sender company and a recipient company which
     * have an eik which is already contained in our database, the companies are
     * then updated by the new values passed from the json. If the EIK number is
     * not found in the companies database, it stores a new company record in
     * the database.
     *
     * @param invoice
     *            the invoice to be created which is passed by a json body
     *            request parameter.
     * @throws InvalidInvoiceException
     *             thrown when the JPA throws a
     *             {@link javax.validation.ConstraintViolationException} when
     *             validating the input json request body.
     */
    @RequestMapping(value = "/api/invoices", method = RequestMethod.PATCH)
    public ResponseEntity<InputStreamResource> saveInvoice(@RequestBody Invoice invoice, HttpServletRequest request) throws InvalidInvoiceException {
        UserInfo cachedUser = (UserInfo) request.getSession().getAttribute("user");
        
        ResponseEntity<InputStreamResource> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);        
        try {
            invoice.getSender().setOwner(cachedUser);
            Company sender = updateCompany(invoice.getSender());
            updateCompany(invoice.getRecipient());
            this.invoiceRepository.save(invoice);
            cachedUser.addCompany(sender);
            this.userRepository.save(cachedUser);
            
            
            response = ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            CONTENT_DISPOSITION_TYPE_INLINE_STRING +
                                    invoice.getSender().getName() + invoice.getInvoiceNumber() + "\"")
                    .body(new InputStreamResource(
                            new ByteArrayInputStream(CreatePDFService.createInvoicePDF(invoice).toByteArray())));
            
        } catch (javax.validation.ConstraintViolationException | IOException cve) {
            throw new InvalidInvoiceException(cve);
        } catch (Docx4JException | JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return response;
    }

    /**
     * Creates and invoice in pdf format and returns it to the user for preview.
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
    public ResponseEntity<InputStreamResource> createInvoice(@RequestBody Invoice invoice)
            throws InvalidInvoiceException {
        ResponseEntity<InputStreamResource> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            response = ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            CONTENT_DISPOSITION_TYPE_INLINE_STRING +
                                    invoice.getSender().getName() + invoice.getInvoiceNumber() + "\"")
                    .body(new InputStreamResource(
                            new ByteArrayInputStream(CreatePDFService.createInvoicePDF(invoice).toByteArray())));
            
            this.invoiceRepository.save(invoice);
        } catch (javax.validation.ConstraintViolationException | IOException cve) {
            throw new InvalidInvoiceException(cve);
        } catch (Exception e) {
            // In case docx4j cannot load OpenXML schemas.
            e.printStackTrace();
        }

        return response;
    }

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
