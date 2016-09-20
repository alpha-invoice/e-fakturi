package interns.invoices.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import interns.invoices.models.InvoiceTemplate;
import interns.invoices.models.UserInfo;
import interns.invoices.repositories.InvoiceTemplateRepository;
import interns.invoices.repositories.UserRepository;

/**
 * This class handles the API end-point, where a specific user uploads a
 * specific template, that the specific user wants to use when issuing an new
 * invoice.
 *
 * The UserRepository is used, where the Autowired annotation provides for the
 * management of the instance of the implementation if the UserRepository
 * interface, which extends the JpaRepository. The exposed end-point is /upload.
 *
 */

@RestController
@CrossOrigin
public class FileUploadController {

    private static final String INVALID_PLACEHOLDERS = "Placeholders are either not present or insuficient.";
    private static final Pattern PATTERN = Pattern.compile("\\$\\{(.*?)\\}");
    private static List<String> placeholders = new ArrayList<String>();
    static {
        placeholders.add("${recipient.name}");
        placeholders.add("${recipient.address}");
        placeholders.add("${recipient.eik}");
        placeholders.add("${recipient.in}");
        placeholders.add("${recipient.mol}");
        placeholders.add("${recipient.mol}");
        placeholders.add("${date}");
        placeholders.add("${sender.name}");
        placeholders.add("${sender.address}");
        placeholders.add("${sender.eik}");
        placeholders.add("${sender.in}");
        placeholders.add("${sender.mol}");
        placeholders.add("${sender.mol}");
        placeholders.add("${number}");
        placeholders.add("${item}");
        placeholders.add("${quantity}");
        placeholders.add("${price}");
        placeholders.add("${total}");
        placeholders.add("${total}");
        placeholders.add("${withVAT}");
        placeholders.add("${tax}");
        placeholders.add("${currency}");
    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    InvoiceTemplateRepository templateRepository;

    /**
     * The method that provides for the implementation of the logic behind the
     * upload file. The maximum file size that can be uploaded is 10Mb as per
     * program specification and it is specified in the appliaction.properties
     * in the Multipart section of the spring configurations.
     *
     * This method processes PATCH requests, where a Multipart file is expected
     * as a request parameter. The method saves the file as a byte array to the
     * database. Per system requirements, there is no need to save the file to
     * system and then store the path to that file in the database.
     *
     * In the case of an Exception when reading the file, the operation is
     * aborted and response code 400 Bad Request is returned to the user.
     *
     * @param file
     *            Multipart file, which is the template that the user will be
     *            using for issuing invoices. Maximum size should be 10Mb.
     * @return HTTP status code 200 OK if the file was written to the database
     *         correctly or HTTP status code 400 Bad Request otherwise.
     */
    @RequestMapping(path = "/api/templates", method = RequestMethod.PATCH)
    public ResponseEntity<?> uploadUserTemplateFile(@Valid @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {

        String fileName = file.getOriginalFilename();
        File convFile = new File(fileName);

        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            convFile.createNewFile();
            fos.write(file.getBytes());

            if (!validateTemplate(convFile)) {
                throw new InvalidFormatException(INVALID_PLACEHOLDERS);
            }

            UserInfo cachedUser = (UserInfo) request.getSession().getAttribute("user");
            InvoiceTemplate template = new InvoiceTemplate();
            template.setName(fileName);
            template.setUserInvoiceTemplate(file.getBytes());
            template.setUserInfo(cachedUser);
            cachedUser.getTemplates().add(template);

            this.templateRepository.save(template);
            this.userRepository.save(cachedUser);

        } catch (InvalidFormatException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(INVALID_PLACEHOLDERS);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Checks whether a docx template is valid - contains all necessary
     * placeholders.
     *
     * @param file
     *            The docx file to be validated.
     * @return Boolean representing whether the template is valid.
     * @throws InvalidFormatException
     *             Occurs when a docx file cannot be opened, possibly due to
     *             file corruption.
     * @throws IOException
     *             Occurs when a file cannot be loaded, i.e. not found.
     */
    private static boolean validateTemplate(File file) throws InvalidFormatException, IOException {

        XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(file));

        XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
        List<String> tags = getPlaceholders(extractor.getText());

        Collections.sort(placeholders);
        Collections.sort(tags);
        extractor.close();
        return placeholders.equals(tags);
    }

    /**
     * Collects all strings which match the docx4j placeholder pattern.
     *
     * @param text
     *            The text to be scanned.
     * @return List of all string which are valid placeholders.
     */
    private static List<String> getPlaceholders(final String text) {
        final List<String> tagValues = new ArrayList<String>();
        Matcher matcher = PATTERN.matcher(text);
        while (matcher.find()) {
            tagValues.add(matcher.group());
        }
        return tagValues;
    }
}
