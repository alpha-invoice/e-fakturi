package interns.invoices.controllers;

import interns.invoices.models.User;
import interns.invoices.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * Defines a RestController class where we
 * specify the endpoint URLs for the User service.
 * It uses an Autowired annotation for our repository
 * which tells spring boot to inject an instance of our
 * {@link UserRepository}.
 */
@RestController
public class UserRestController {
    @Autowired
    private UserRepository userRepository;

    /**
     * Service URL for our
     * {@link UserRepository#findOne(Serializable)} method
     * @param id a parameter which is passed from the URL Path
     * @return a json representation of the found User or null
     */
    @RequestMapping("/user/{id}")
    User getUserById(@Param("id") Long id) {
        return this.userRepository.findOne(id);
    }
}
