package fr.ynov.dap.model;

import fr.ynov.dap.model.enumeration.CredentialTypeEnum;

/**
 * Represent user's credential data.
 * @author Kévin Sibué
 *
 */
public class Credential {

    /**
     * User's Id.
     */
    private String userId;

    /**
     * User's token.
     */
    private String token;

    /**
     * User's refresh token.
     */
    private String refreshToken;

    /**
     * Token's expiration time.
     */
    private long expirationTime;

    /**
     * Type a current credential. e.g. Microsoft, Google, ...
     */
    private CredentialTypeEnum type;

    /**
     * Store tenant id for API which use it.
     */
    private String tenantId = "";

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param val the userId to set
     */
    public void setUserId(final String val) {
        this.userId = val;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param val the token to set
     */
    public void setToken(final String val) {
        this.token = val;
    }

    /**
     * @return the refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param val the refreshToken to set
     */
    public void setRefreshToken(final String val) {
        this.refreshToken = val;
    }

    /**
     * @return the expirationTime
     */
    public long getExpirationTime() {
        return expirationTime;
    }

    /**
     * @param val the expirationTime to set
     */
    public void setExpirationTime(final long val) {
        this.expirationTime = val;
    }

    /**
     * @return the type
     */
    public CredentialTypeEnum getType() {
        return type;
    }

    /**
     * @param val the type to set
     */
    public void setType(final CredentialTypeEnum val) {
        this.type = val;
    }

    /**
     * @return the tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param val the tenantId to set
     */
    public void setTenantId(final String val) {
        this.tenantId = val;
    }

}
