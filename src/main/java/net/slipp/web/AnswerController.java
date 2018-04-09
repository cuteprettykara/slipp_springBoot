package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.Answer;
import net.slipp.domain.AnswerRepository;
import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

@Controller
@RequestMapping("/questions/{questionId}/answer")
public class AnswerController {
	
	@Autowired
	private AnswerRepository asnwerRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	
	@PostMapping("")
	public String create(@PathVariable Long questionId, String contents, HttpSession session) {
		if (!HttpSessionutils.isLoginUser(session)) return "redirect:/users/loginForm";
		
		User sessionUser = HttpSessionutils.getUserFromSession(session);
		Question question = questionRepository.findById(questionId).get();
		
		Answer answer = new Answer(sessionUser, question, contents);
		
		asnwerRepository.save(answer);
		
		return String.format("redirect:/questions/%d", questionId);
	}
}
