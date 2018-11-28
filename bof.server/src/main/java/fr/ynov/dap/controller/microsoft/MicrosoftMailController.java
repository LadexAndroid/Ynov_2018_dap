package fr.ynov.dap.controller.microsoft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.microsoft.Message;
import fr.ynov.dap.model.microsoft.OutlookAccountModel;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.repository.microsoft.OutlookAccountRepository;
import fr.ynov.dap.service.microsoft.MicrosoftMailService;

@Controller
@RequestMapping("/microsoft")
public class MicrosoftMailController {

	@Autowired
	private OutlookAccountRepository outlookAccountRepository;

	@Autowired
	private MicrosoftMailService microsoftMailService;

	@RequestMapping("/mail")
	public String mail(Model model, HttpServletRequest request) {

		HashMap<String, Message[]> usersMessages = new HashMap<>();
		for (OutlookAccountModel account : outlookAccountRepository.findAll()) {
			usersMessages.put(account.getAccountName(), microsoftMailService.getMails(account));
		}
		model.addAttribute("usersMessages", usersMessages);
		return "MicrosoftMail";
	}

}