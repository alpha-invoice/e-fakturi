package interns.invoices.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

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

    /**
     * The method that provides for the implementation of the logic behind the
     * upload file. The maximum file size that can be uploaded is 10Mb as per
     * program specification and it is specified in the appliaction.properties
     * in the Multipart section of the spring configurations.
     *
     * This method processes POST requests, where a Multipart file is expected
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
    @RequestMapping(path = "/api/upload", method = RequestMethod.POST)
    public ResponseEntity<?> uploadUserTemplateFile(@Valid @RequestParam("file") MultipartFile file) {
        /*
         * NB!
         *
         * TODO: Get the currently logged in user from the Session service. At
         * this time there is no authentication service.
         *
         * This is the default setting for now existing user with ID 9L. The
         * value of user should be set from the session service.
         */
        try {
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            validateTemplate(convFile);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private static boolean validateTemplate(File file) throws InvalidFormatException, IOException {

        FileInputStream fis = new FileInputStream(file);
        XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));

        XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
        List<String> tags = getTagValues(extractor.getText());

        Collections.sort(placeholders);
        Collections.sort(tags);
        return placeholders.equals(tags);
    }

    private static List<String> getTagValues(final String str) {
        final List<String> tagValues = new ArrayList<String>();
        Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            tagValues.add(matcher.group());
        }
        return tagValues;
    }
}
