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

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository; 
	
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
		try {
			Question question = questionRepository.findById(id).get();
			hasPermission(session, question);
			model.addAttribute("question", question);
			return "/qna/updateForm";
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
	}
	
	private void hasPermission(HttpSession session, Question question) {
		if (!HttpSessionutils.isLoginUser(session)) throw new IllegalStateException("로그인이 필요합니다.");
		
		User sessionUser = HttpSessionutils.getUserFromSession(session);
		
		if (!question.isSameWriter(sessionUser)) throw new IllegalStateException("자신이 쓴 글만  수정, 삭제가 가능합니다.");	
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents, HttpSession session, Model model) {
		try {
			Question question = questionRepository.findById(id).get();
			hasPermission(session, question);
			question.update(title, contents);
			questionRepository.save(question);
			return String.format("redirect:/questions/%d", id);
			
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
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
	public String delete(@PathVariable Long id, String title, String contents, HttpSession session, Model model) {
		try {
			Question question = questionRepository.findById(id).get();
			hasPermission(session, question);
			
			questionRepository.deleteById(id);
			return "redirect:/";
			
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
	}
}
