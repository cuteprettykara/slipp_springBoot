package net.slipp.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Question {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty
	private Long id;
	
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
	
	private LocalDateTime createDate;
	
	@JsonProperty
	private Integer countOfAnswer = 0;

	public Question() {
	}
	
	public Question(User writer, String title, String contents) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createDate = LocalDateTime.now();
	}
	
	public String getFormattedCreateDate() {
		if (createDate == null) return "";
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
		return this.createDate.format(dateTimeFormatter); 
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", title=" + title + ", contents=" + contents + ", createDate=" + createDate
				+ "]";
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

/*	public void update(Question updateQuestion) {
		this.title = updateQuestion.title;
		this.contents = updateQuestion.contents;
	}*/

		
	
}
