package fr.ynov.dap.data;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.ynov.dap.microsoft.auth.IdToken;
import fr.ynov.dap.microsoft.auth.TokenResponse;

/**
 * @author adrij
 *
 */
@Entity
public class MicrosoftAccount {

    /**
     * Length of the string to save on the database.
     * It's used to specify the length of long string, like the authCode.
     */
    private static final int STRING_LENGTH = 8192;

    /**
     * Microsoft Account Id.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Owner of this MicrosoftAccount.
     */
    @ManyToOne
    private AppUser appUser;

    /**
     * Get the owner.
     * @return the owner of this MicrosoftAccount.
     */
    public AppUser getOwner() {
        return this.appUser;
    }

    /**
     * Set the owner of this MicrosoftAccount.
     * @param user AppUser (owner) of this MicrosoftAccount.
     */
    public void setOwner(final AppUser user) {
        this.appUser = user;
    }

    /**
     * Name of the account.
     */
    @Column
    private String accountName;

    /**
     * Getter of AccountName.
     * @return account name.
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Setter of AccountName.
     * @param name AccountName
     */
    public void setAccountName(final String name) {
        this.accountName = name;
    }

    /**
     * TokenResponse from the Microsoft API.
     */
    @Column(length = STRING_LENGTH)
    private String tokenResponse;

    /**
     * Getter of the TokenResponse.
     * @return the Microsoft token response.
     * @throws JsonParseException exception if the parse fails.
     * @throws JsonMappingException exception if the mapping fails.
     * @throws IOException exception if the stored value is incorrect.
     */
    public TokenResponse getTokenResponse() throws JsonParseException, JsonMappingException, IOException {
        return new ObjectMapper().readValue(tokenResponse, TokenResponse.class);
    }

    /**
     * Setter of TokenResponse.
     * @param token tokenResponse to store.
     * @throws JsonProcessingException exception if the serialization fails.
     */
    public void setTokenResponse(final TokenResponse token) throws JsonProcessingException {
        this.tokenResponse = (String) new ObjectMapper().writeValueAsString(token);
    }

    /**
     * IdToken from the Microsoft API.
     */
    @Column(length = STRING_LENGTH)
    private String idToken;

    /**
     * IdToken getter.
     * @return the token id.
     * @throws JsonParseException exception if the parse fails.
     * @throws JsonMappingException exception if the mapping fails.
     * @throws IOException exception if the stored value is incorrect.
     */
    public IdToken getIdToken() throws JsonParseException, JsonMappingException, IOException {
        return new ObjectMapper().readValue(idToken, IdToken.class);
    }

    /**
     * IdToken setter.
     * @param idTok IdToken.
     * @throws JsonProcessingException exception if the serialization fails.
     */
    public void setIdToken(final IdToken idTok) throws JsonProcessingException {
        String tokenTest = (String) new ObjectMapper().writeValueAsString(idTok);
        this.idToken = (String) new ObjectMapper().writeValueAsString(idTok);
    }

    /**
     * Default Constructor.
     */
    public MicrosoftAccount() {
    }
}
