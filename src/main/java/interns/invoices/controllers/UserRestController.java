package interns.invoices.controllers;

import interns.invoices.models.UserInfo;
import interns.invoices.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Defines a RestController class where we
 * specify the endpoint URLs for the UserInfo service.
 * It uses an Autowired annotation for our repository
 * which tells spring boot to inject an instance of our
 * {@link UserRepository}.
 */
@RestController
public class UserRestController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/me")
    public UserInfo myInfo(HttpServletRequest request) {
        return (UserInfo)request.getSession().getAttribute("user");
    }
}
