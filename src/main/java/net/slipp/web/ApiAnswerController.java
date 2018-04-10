package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.slipp.domain.Answer;
import net.slipp.domain.AnswerRepository;
import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

@RestController
@RequestMapping("/api/questions/{questionId}/answer")
public class ApiAnswerController {
	
	@Autowired
	private AnswerRepository asnwerRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	
	@PostMapping("")
	public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
		if (!HttpSessionutils.isLoginUser(session)) return null;
		
		User sessionUser = HttpSessionutils.getUserFromSession(session);
		Question question = questionRepository.findById(questionId).get();
		
		Answer answer = new Answer(sessionUser, question, contents);
		System.out.println("Answer : " + answer);
		return asnwerRepository.save(answer);
	}
}
