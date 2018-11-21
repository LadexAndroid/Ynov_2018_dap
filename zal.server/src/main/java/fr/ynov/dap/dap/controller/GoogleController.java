package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.dap.service.GoogleService;

@Controller
public class GoogleController {
	
	@Autowired
	@Qualifier("google")
	GoogleService googleService;
	
	@RequestMapping("/datastore")
	public String getCredentialDataStore(final ModelMap model) throws IOException, GeneralSecurityException{
		
		DataStore<StoredCredential> output = googleService.getCredentialDataStore();
		Map<Integer, String> map= new HashMap<Integer, String>();
		
		int count = 0;
		for(StoredCredential sc : output.values()){
			map.put(count, sc.getAccessToken());
			count++;
		}
		
		model.addAttribute("credentials", map);
		
		return "datastore";
	}
}
