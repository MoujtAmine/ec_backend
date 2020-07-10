package org.d3h.application.models;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
 
@Embeddable
public class ExamProfessorId implements Serializable { 

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Exam exam;
	
	@ManyToOne(cascade = CascadeType.ALL)
    private Professor professor;
 
    
    public ExamProfessorId() {
		super();
	}

	public ExamProfessorId(Exam exam, Professor professor) {
		super();
		this.exam = exam;
		this.professor = professor;
	}

	public Exam getExam() {
        return exam;
    }
 
    public void setExam(Exam exam) {
        this.exam = exam;
    }
 
    public Professor getProfessor() {
        return professor;
    }
 
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}