package org.d3h.application.models;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
 
@Embeddable
public class ExamStudentId implements Serializable { 

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Exam exam;
	
	@ManyToOne(cascade = CascadeType.ALL)
    private Student student;
 
    
    public ExamStudentId() {
		super();
	}

	public ExamStudentId(Exam exam, Student student) {
		super();
		this.exam = exam;
		this.student = student;
	}

	public Exam getExam() {
        return exam;
    }
 
    public void setExam(Exam exam) {
        this.exam = exam;
    }
 
    public Student getStudent() {
        return student;
    }
 
    public void setStudent(Student student) {
        this.student = student;
    }
}