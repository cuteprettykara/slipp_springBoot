package net.slipp.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Question extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_question_writer"))
	@JsonProperty
	private User writer;
	
	@OneToMany(mappedBy="question")
	@OrderBy("id DESC")
	private List<Answer> answers;
	
	@JsonProperty
	private String title;
	
	@Lob
	@JsonProperty
	private String contents;
	
	@JsonProperty
	private Integer countOfAnswer = 0;

	public Question() {
	}
	
	public Question(User writer, String title, String contents) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}

	public void update(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}
	
	public boolean isSameWriter(User sessionUser) {
		
		return this.writer.equals(sessionUser);
	}
	
	public void addAnswerCnt() {
		++this.countOfAnswer;
	}
	
	public void deleteAnswerCnt() {
		--this.countOfAnswer;
	}
	
	@Override
	public String toString() {
		return "Question [id=" + getId() + ", title=" + title + ", contents=" + contents + "]";
	}

}
