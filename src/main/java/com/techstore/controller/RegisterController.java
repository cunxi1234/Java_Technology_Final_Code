package com.techstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.techstore.model.NguoiDung;
import com.techstore.service.UserService;
import com.techstore.service.SecurityService;
import com.techstore.validator.UserValidator;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
	
	
	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("newUser", new NguoiDung());
		return "client/register";
	}
	
	@PostMapping("/register")
	public String registerProcess(@ModelAttribute("newUser") @Valid NguoiDung nguoiDung, BindingResult bindingResult, Model model) {
	    
		userValidator.validate(nguoiDung, bindingResult);
		
        if (bindingResult.hasErrors()) {
            return "client/register";
        }
        
        userService.saveUserForMember(nguoiDung);

        securityService.autologin(nguoiDung.getEmail(), nguoiDung.getConfirmPassword());

        return "redirect:/";
	}
}
