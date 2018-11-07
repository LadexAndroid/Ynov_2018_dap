package fr.ynov.dap.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author adrij
 *
 */
@Entity
public class AppUser {

    /**
     * Id of the AppUser.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * name of the AppUser.
     */
    @Column(unique = true)
    private String userKey;

    /**
     * List of GoogleAccount owned by this AppUser.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appUser")
    private List<GoogleAccount> googleAccounts;

    /**
     * Get the list of GoogleAccount owned by this AppUser.
     * @return List of GoogleAccount.
     */
    public List<GoogleAccount> getGoogleAccounts() {
        return googleAccounts;
    }

    /**
     * Add a GoogleAccount to that UserApp.
     * @param account GoogleAccount to add.
     */
    public void addGoogleAccount(final GoogleAccount account){
        account.setOwner(this);
        this.getGoogleAccounts().add(account);

    }

    /**
     * Default constructor.
     */
    public AppUser() {
    }

    /**
     * UserKey getter.
     * @return userKey
     */
    public String getUserKey() {
        return userKey;
    }

    /**
     * UserKey setter.
     * @param key userKey.
     */
    public void setUserKey(final String key) {
        this.userKey = key;
    }

    /**
     * Get the a list of all AccountnName of googleAccount of this AppUser.
     * @return list of accountName
     */
    public List<String> getGoogleAccountNames(){
        List<String> accountNames = new ArrayList<String>();
        List<GoogleAccount> accounts = getGoogleAccounts();
        for (GoogleAccount account : accounts) {
            accountNames.add(account.getAccountName());
        }

        return accountNames;
    }

}
