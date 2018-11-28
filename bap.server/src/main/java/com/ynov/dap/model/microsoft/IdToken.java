package com.ynov.dap.model.microsoft;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class IdToken.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdToken {

    private final static Integer INT_UNIX_TIME = 1000;

    /** The expiration time. */
    @JsonProperty("exp")
    private long expirationTime;

    /** The not before. */
    @JsonProperty("nbf")
    private long notBefore;

    /** The tenant id. */
    @JsonProperty("tid")
    private String tenantId;

    /** The nonce. */
    private String nonce;

    /** The name. */
    @JsonProperty("preferred_username")
    private String name;

    /** The email. */
    private String email;

    /** The preferred username. */
    private String preferredUsername;

    /** The object id. */
    @JsonProperty("oid")
    private String objectId;

    /**
     * Parses the encoded token.
     *
     * @param encodedToken the encoded token
     * @param nonce        the nonce
     * @return the id token
     * @throws JsonParseException   the json parse exception
     * @throws JsonMappingException the json mapping exception
     * @throws IOException          Signals that an I/O exception has occurred.
     */
    public static IdToken parseEncodedToken(final String encodedToken, final String nonce)
            throws JsonParseException, JsonMappingException, IOException {
        String[] tokenParts = encodedToken.split("\\.");

        String idToken = tokenParts[1];

        byte[] decodedBytes = Base64.getUrlDecoder().decode(idToken);

        ObjectMapper mapper = new ObjectMapper();
        IdToken newToken = null;
        newToken = mapper.readValue(decodedBytes, IdToken.class);
        if (!newToken.isValid(nonce)) {
            return null;
        }
        return newToken;
    }

    /**
     * Gets the expiration time.
     *
     * @return the expiration time
     */
    public long getExpirationTime() {
        return expirationTime;
    }

    /**
     * Sets the expiration time.
     *
     * @param expirationTime the new expiration time
     */
    public void setExpirationTime(final long expirationTime) {
        this.expirationTime = expirationTime;
    }

    /**
     * Gets the not before.
     *
     * @return the not before
     */
    public long getNotBefore() {
        return notBefore;
    }

    /**
     * Sets the not before.
     *
     * @param notBefore the new not before
     */
    public void setNotBefore(final long notBefore) {
        this.notBefore = notBefore;
    }

    /**
     * Gets the tenant id.
     *
     * @return the tenant id
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Sets the tenant id.
     *
     * @param tenantId the new tenant id
     */
    public void setTenantId(final String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Gets the nonce.
     *
     * @return the nonce
     */
    public String getNonce() {
        return nonce;
    }

    /**
     * Sets the nonce.
     *
     * @param nonce the new nonce
     */
    public void setNonce(final String nonce) {
        this.nonce = nonce;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets the preferred username.
     *
     * @return the preferred username
     */
    public String getPreferredUsername() {
        return preferredUsername;
    }

    /**
     * Sets the preferred username.
     *
     * @param preferredUsername the new preferred username
     */
    public void setPreferredUsername(final String preferredUsername) {
        this.preferredUsername = preferredUsername;
    }

    /**
     * Gets the object id.
     *
     * @return the object id
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * Sets the object id.
     *
     * @param objectId the new object id
     */
    public void setObjectId(final String objectId) {
        this.objectId = objectId;
    }

    /**
     * Gets the unix epoch as date.
     *
     * @param epoch the epoch
     * @return the unix epoch as date
     */
    private Date getUnixEpochAsDate(final long epoch) {
        // Epoch timestamps are in seconds,
        // but Jackson converts integers as milliseconds.
        // Rather than create a custom deserializer, this helper will do
        // the conversion.
        return new Date(epoch * INT_UNIX_TIME);
    }

    /**
     * Checks if is valid.
     *
     * @param nonce the nonce
     * @return true, if is valid
     */
    private boolean isValid(final String nonce) {
        // This method does some basic validation
        // For more information on validation of ID tokens, see
        // https://azure.microsoft.com/en-us/documentation/articles/active-directory-v2-tokens/#validating-tokens
        Date now = new Date();

        // Check expiration and not before times
        if (now.after(this.getUnixEpochAsDate(this.expirationTime))
                || now.before(this.getUnixEpochAsDate(this.notBefore))) {
            // Token is not within it's valid "time"
            return false;
        }

        // Check nonce
        if (!nonce.equals(this.getNonce())) {
            // Nonce mismatch
            return false;
        }

        return true;
    }
}