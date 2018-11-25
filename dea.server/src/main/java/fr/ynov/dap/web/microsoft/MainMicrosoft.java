
package fr.ynov.dap.web.microsoft;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;


/**
 * Controlleur de microsoft
 * 
 * @author antod
 *
 */
@Controller
public class MainMicrosoft
{

  /**
   * Page de microsoft
   * 
   * @param model
   * @return
   */
  @RequestMapping("/index")
  public String index(Model model, HttpServletRequest request)
  {
//    UUID state = UUID.randomUUID();
//    UUID nonce = UUID.randomUUID();

    // Save the state and nonce in the session so we can
    // verify after the auth process redirects back
//    HttpSession session = request.getSession();
//    session.setAttribute("expected_state", state);
//    session.setAttribute("expected_nonce", nonce);

//    String loginUrl = AuthHelper.getLoginUrl(state, nonce, userKey);
//    model.addAttribute("loginUrl", loginUrl);

    // Name of a definition in WEB-INF/defs/pages.xml
    return "index";
  }
}
