
package fr.ynov.dap.data;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


/**
 * Classe AppUser
 * 
 * @author antod
 *
 */
@Entity
public class AppUser
{
  /**
   * Attribut Id
   */
  @Id
  @GeneratedValue
  private Integer id;
  /**
   * Attribut name
   */
  private String name;

  /**
   * Liste des comptes google
   */
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
  private List<GoogleAccount> googleAccounts = new ArrayList<GoogleAccount>();

  /**
   * Ajoute un compte à l'utilisateur
   * 
   * @param account
   */
  public void addGoogleAccount(GoogleAccount account)
  {
    account.setOwner(this);

    this.getAccounts().add(account);
  }

  /**
   * Récupère le nom
   * 
   * @return
   */
  public String getName()
  {
    return name;
  }

  /**
   * Change le nom
   * 
   * @param name
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Récupère les comptes google
   * 
   * @return
   */
  public List<GoogleAccount> getAccounts()
  {
    return googleAccounts;
  }
}
