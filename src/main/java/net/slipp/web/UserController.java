package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/createForm")
	public String createForm(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/form";
	}

	@PostMapping
	public String create(User user) {
		userRepository.save(user);
		
		return "redirect:users";
	}
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable long id, Model model) {
		User user = userRepository.findById(id).get();
		
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	
	@PutMapping("/{id}/update")
	public String update(@PathVariable long id, User newUser) {
		User dbUser = userRepository.findById(id).get();
		System.out.println("dbUser : " + dbUser);
		System.out.println("newUser : " + newUser);
		
		dbUser.update(newUser);
		userRepository.save(dbUser);
		
		return "redirect:/users";
	}
	
	@GetMapping("/loginForm")
	public String loginForm(Model model) {
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		User dbUser = userRepository.findByUserId(userId);
		
		if (dbUser == null) {
			System.out.println("Login Failure!");
			return "redirect:/users/loginForm";
		}
		
		if (!dbUser.matchPassword(password)) {
			System.out.println("Login Failure!");
			return "redirect:/users/loginForm";
		}
		
		System.out.println("Login Success!");
		session.setAttribute("user", dbUser);
		
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/";
	}
	
	@GetMapping("/profile")
	public String profile() {
		return "/user/profile";
	}
	
}
