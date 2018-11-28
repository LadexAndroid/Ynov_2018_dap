package fr.ynov.dap.web;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;

/**
 * Controller to manage user.
 * @author thibault
 *
 */
@RestController
public class AppUserController extends HandlerErrorController {

    /**
     * Repository of AppUser.
     */
    @Autowired
    private AppUserRepository repositoryUser;

    /**
     * Register a app user.
     * @param userKey  the userKey to create
     * @return the view to Display (on Error)
     * @throws HttpResponseException if bad request
     */
    @RequestMapping("/user/add/{userKey}")
    public AppUser registerUser(@PathVariable("userKey") final String userKey) throws HttpResponseException {
        if (repositoryUser.existsByUserKey(userKey)) {
            throw new HttpResponseException(HttpStatus.SC_BAD_REQUEST, "User '" + userKey + "' already exist.");
        }
        AppUser user = new AppUser();
        user.setUserKey(userKey);
        return repositoryUser.save(user);
    }
}
