package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.AnswerRepository;
import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository; 
	
	@Autowired
	private AnswerRepository asnwerRepository;
	
	@GetMapping("/form")
	public String form(HttpSession session) {
		if (!HttpSessionutils.isLoginUser(session)) return "redirect:/users/loginForm";
		
		return "/qna/form";
	}
	
	@PostMapping
	public String create(String title, String contents, HttpSession session) {
		if (!HttpSessionutils.isLoginUser(session)) return "redirect:/users/loginForm";
		
		User sessionUser = HttpSessionutils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser, title, contents);
		questionRepository.save(newQuestion);
		
		return "redirect:/";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		model.addAttribute("question", questionRepository.findById(id).get());
		
		return "/qna/show";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		if (!HttpSessionutils.isLoginUser(session)) return "redirect:/users/loginForm";
		
		User sessionUser = HttpSessionutils.getUserFromSession(session);
		Question question = questionRepository.findById(id).get();
		
		if (!question.isSameWriter(sessionUser)) return "redirect:/users/loginForm";
		
		model.addAttribute("question", question);
		return "/qna/updateForm";
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents, HttpSession session) {
		if (!HttpSessionutils.isLoginUser(session)) return "redirect:/users/loginForm";
		
		User sessionUser = HttpSessionutils.getUserFromSession(session);
		Question question = questionRepository.findById(id).get();
		
		if (!question.isSameWriter(sessionUser)) return "redirect:/users/loginForm";
		
		Question dbQuestion = questionRepository.findById(id).get();
		dbQuestion.update(title, contents);
		questionRepository.save(dbQuestion);
		
		return String.format("redirect:/questions/%d", id);
	}
	
/*	@PutMapping("/{id}")
	public String update(@PathVariable Long id, Question updateQuestion, HttpSession session) {
		Question dbQuestion = questionRepository.findById(id).get();
		
		System.out.println("*** updateQuestion : " + updateQuestion);
		System.out.println("*** dbQuestion : " + dbQuestion);
		
		dbQuestion.update(updateQuestion);
		questionRepository.save(dbQuestion);
		
		return String.format("redirect:/questions/%d", id);
	}*/
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, String title, String contents, HttpSession session) {
		if (!HttpSessionutils.isLoginUser(session)) return "redirect:/users/loginForm";
		
		User sessionUser = HttpSessionutils.getUserFromSession(session);
		Question question = questionRepository.findById(id).get();
		
		if (!question.isSameWriter(sessionUser)) return "redirect:/users/loginForm";
		
		questionRepository.deleteById(id);
		return "redirect:/";
	}
}
