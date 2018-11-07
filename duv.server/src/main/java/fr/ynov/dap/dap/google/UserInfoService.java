package fr.ynov.dap.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.oauth2.Oauth2;

/**
 *
 * @author David_tepoche
 *
 */
@Service
public class UserInfoService extends BaseService {

    @Override
    protected final String getClassName() {
        return UserInfoService.class.getName();
    }

    /**
     * get the email of the user.
     *
     * @param userId token identifier
     * @return email
     * @throws IOException              when retrieve the token fail.
     * @throws GeneralSecurityException error while you try to authenticated
     */
    public String getEmail(final String userId) throws IOException, GeneralSecurityException {
        return getService(userId).userinfo().get().execute().getEmail().toString();
    }

    /**
     *
     * @param userId user key
     * @return the Oauth2 service
     * @throws GeneralSecurityException throws by getCredential from BaseService
     * @throws IOException              throws by getCredential from BaseService
     */
    private Oauth2 getService(final String userId) throws GeneralSecurityException, IOException {
        return new Oauth2.Builder(GoogleNetHttpTransport.newTrustedTransport(), JACKSON_FACTORY, getCredential(userId))
                .setApplicationName(getConfig().getApplicationName()).build();
    }

}
