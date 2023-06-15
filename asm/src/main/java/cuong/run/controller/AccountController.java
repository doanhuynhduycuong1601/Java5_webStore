package cuong.run.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cuong.run.model.Customer;
import cuong.run.pojo.ChangePass;
import cuong.run.pojo.LoginPojo;
import cuong.run.pojo.UpdateInfor;
import cuong.run.service.AccountService;
import cuong.run.service.Cookie;
import cuong.run.service.Session;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
	final AccountService accountService;
	final Session session;
	final Cookie cookie;
	@GetMapping("/register")
	public String register(@ModelAttribute("accounts") LoginPojo login ,Model model) {
		model.addAttribute("page", "include/register.jsp");
		session.remove("codeRegister");
		return "index";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("accounts") LoginPojo login,
			BindingResult bindingResult ,Model model) throws MessagingException {
		model.addAttribute("page", "include/register.jsp");
		if(bindingResult.hasErrors()) {
			return "index";
		}
		
		if(!accountService.register(login, bindingResult, model)) {
			return "index";
		}
		
		return "redirect:/account/login";
	}
	
	@GetMapping("/login")
	public String login(@ModelAttribute("accounts") LoginPojo login ,Model model) {
		String email = cookie.getValue("account");
		if(!email.equals("")) {
			login.setEmail(email);
			model.addAttribute("remember", "checked=\"checked\"");
		}
		model.addAttribute("page", "include/login.jsp");
		return "index";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("accounts") LoginPojo login,
			BindingResult bindingResult ,Model model) throws MessagingException {
		model.addAttribute("page", "include/login.jsp");
		if(bindingResult.hasErrors()) {
			return "index";
		}
		
		if(!accountService.login(login, bindingResult, model)) {
			return "index";
		}
		
		
		return accountService.checkLoginUri(model);
	}
	
	@GetMapping("/logout")
	public String logout() {
		session.remove("account");
		return "redirect:/";
	}
	
	
	@GetMapping("/changepass")
	public String changepass(@ModelAttribute("accounts") ChangePass change ,Model model) {
		model.addAttribute("page", "include/changePass.jsp");
		return "index";
	}
	
	@PostMapping("/changepass")
	public String changepass(@Valid @ModelAttribute("accounts") ChangePass change,
			BindingResult bindingResult ,Model model) {
		model.addAttribute("page", "include/changePass.jsp");
		if(bindingResult.hasErrors()) {
			return "index";
		}
		
		if(!accountService.changepass(change, bindingResult, model)) {
			return "index";
		}
		return "redirect:/store";
	}
	
	@GetMapping("/update/infor")
	public String updateInfor(Model model) {
		model.addAttribute("accounts", accountService.toUpdateInfor((Customer)session.get("account")));
		model.addAttribute("page", "include/updateInformation.jsp");
		return "index";
	}
	
	@PostMapping("/update/infor")
	public String update(@Valid @ModelAttribute("accounts") UpdateInfor change,
			BindingResult bindingResult ,Model model) {
		model.addAttribute("page", "include/updateInformation.jsp");
		if(bindingResult.hasErrors()) {
			return "index";
		}
		accountService.update(change);
		
		return accountService.checkUpdateUri();
	}
	
	@ModelAttribute("gender")
	private Map<Boolean, String> gender() {
		return Map.of(true, "Trai", false, "Gái");
	}
	
}
