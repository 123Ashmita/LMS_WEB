package com.api.controllers;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.api.entities.Patron;
import com.api.services.PatronService;

@Controller
@RequestMapping("/patron")
public class PatronController {
	
	@Autowired
	private PatronService patronService;
	
	@GetMapping("/add")
    public String showAddPatronForm(Model model) {
        model.addAttribute("patron", new Patron()); 
        return "add-patron";  
    }
	
	@PostMapping("/addPatron")
	public String addPatron(Patron patron,Model model) {
		if(patronService.existByPatronId(patron.getPid())) {
			model.addAttribute("message", "Patron with ID " + patron.getPid() + " already exists.");
			return "add-patron";
		}
		 patronService.addPatron(patron);	
		 return "redirect:/patron";
	}

	
	@GetMapping(" ")
	public String getPatronList(Model model) {
		List<Patron> patrons=patronService.getList();
		model.addAttribute("patrons", patrons); 
		return "patron-list";
	}
	@DeleteMapping("/deletePatron/{pid}")
	public ResponseEntity<String> deletePatron(@PathVariable int pid) {
	    try {
	        patronService.deletePatron(pid);
	        return ResponseEntity.ok("delete " + pid + " successfully");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	} 
	}
	 @PostMapping("/delete/{pid}")
	    public String deletePatron(@PathVariable int pid,RedirectAttributes redirectAttributes) {
	        try {
	            patronService.deletePatron(pid);
	            redirectAttributes.addFlashAttribute("message", "Patron with ID " + pid + " deleted successfully.");
	        } catch (Exception e) {
	        	redirectAttributes.addFlashAttribute("error", "patron has borrowed book || can't delete");
	        }
	        return "redirect:/patron";
	    }

}