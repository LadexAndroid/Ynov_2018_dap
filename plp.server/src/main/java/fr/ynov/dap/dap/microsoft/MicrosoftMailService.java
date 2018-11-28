package fr.ynov.dap.dap.microsoft;

import fr.ynov.dap.dap.GMailService;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.microsoft.OutlookAccount;
import fr.ynov.dap.dap.data.microsoft.Token;
import fr.ynov.dap.dap.microsoft.models.*;
import fr.ynov.dap.dap.repositories.AppUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MicrosoftMailService {
    /**
     * Instantiate logger.
     */
    private static final Logger LOG = LogManager.getLogger(GMailService.class);

    /**
     * instantiate userRepository
     */
    @Autowired
    AppUserRepository userRepository;

//    public String mail(final String userKey, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    public List<Map> mail(final String userKey, RedirectAttributes redirectAttributes) {

        AppUser appUser = userRepository.findByName(userKey);
        List<Map> listResponse = new ArrayList<>();

        for (OutlookAccount outlookAccount : appUser.getOutlookAccount()) {
            if (outlookAccount.getToken() == null) {
                // No tokens in session, user needs to sign in
                redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
            }

            outlookAccount.setToken(AuthHelper.ensureTokens(outlookAccount.getToken(), outlookAccount.getTenantId()));

            OutlookService outlookService = OutlookServiceBuilder.getOutlookService(
                    outlookAccount.getToken().getAccessToken());

            // Retrieve messages from the inbox
            String folder = "inbox";
            // Sort by time received in descending order
            String sort = "receivedDateTime DESC";
            // Only return the properties we care about
            String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
            // Return at most 100 messages
            Integer maxResults = 100;

            try {
                PagedResult<Message> messages = outlookService.getMessages(
                        folder, sort, properties, maxResults)
                        .execute().body();

                Map<String, Object> account = new HashMap<>();
                account.put("name", outlookAccount.getName());
                account.put("messages", messages.getValue());
                listResponse.add(account);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
                LOG.error("Can't get messages", e);
            }
        }

        return listResponse;
    }
}
