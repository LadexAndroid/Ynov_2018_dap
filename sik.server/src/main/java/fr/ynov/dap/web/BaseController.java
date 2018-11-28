package fr.ynov.dap.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.ynov.dap.contract.AppUserRepository;
import fr.ynov.dap.dto.out.ExceptionOutDto;
import fr.ynov.dap.exception.UserNotFoundException;
import fr.ynov.dap.model.AppUser;

/**
 * BaseController.
 * @author Kévin Sibué
 *
 */
public abstract class BaseController {

    /**
     * Instance of AppUserRepository.
     * Auto resolved by Autowire.
     */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * Logger instance.
     */
    private Logger logger = LogManager.getLogger(getClassName());

    /**
     * Return current instance of Logger.
     * @return Logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Get current instance of app user repository.
     * @return App user repository
     */
    public AppUserRepository getAppUserRepository() {
        return appUserRepository;
    }

    /**
     * Return current class name.
     * @return Class name
     */
    protected abstract String getClassName();

    /**
     * Method to handle every exception occurred on Spring application.
     * @param request Current http request
     * @param ex Current exception which fire the handler
     * @return Response to client.
     */
    @ExceptionHandler(Exception.class)
    public @ResponseBody final ResponseEntity<ExceptionOutDto> handledException(final HttpServletRequest request,
            final Exception ex) {

        ExceptionOutDto response = new ExceptionOutDto(ex.getLocalizedMessage());

        getLogger().error(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    /**
     * Find a user from database.
     * @param userId User id
     * @return User
     * @throws UserNotFoundException Thrown if no user found
     */
    protected AppUser getUserById(final String userId) throws UserNotFoundException {

        AppUser user = appUserRepository.findByUserKey(userId);

        if (user == null) {

            getLogger().error("Try to get undefined user with id : " + userId);

            throw new UserNotFoundException();

        }

        return user;

    }

}
