package interns.invoices.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import interns.invoices.models.UserInfo;
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
@RestController()
public class FileUploadController {

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
        String id = "";
        UserInfo user = userRepository.findOne(id);

        try {
            user.setUserInvoiceTemplate(file.getBytes());
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
