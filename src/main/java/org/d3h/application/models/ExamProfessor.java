package org.d3h.application.models;


import java.io.Serializable;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "exams_professors")
@AssociationOverrides({
    @AssociationOverride(name = "primaryKey.exam",
        joinColumns = @JoinColumn(name = "exam_id")),
    @AssociationOverride(name = "primaryKey.professor",
        joinColumns = @JoinColumn(name = "professor_id")) })
public class ExamProfessor implements Serializable {

	private static final long serialVersionUID = 1L;

    // composite-id key
	@EmbeddedId
	private ExamProfessorId primaryKey;
     
    // additional fields
    private boolean present;

	public ExamProfessor() {
		super();
	}
	
	public ExamProfessor(ExamProfessorId primaryKey, boolean present) {
		super();
		this.primaryKey = primaryKey;
		this.present = present;
	}
	
    public ExamProfessor(Exam e, Professor prof, boolean p) {
		// create primary key
		this.primaryKey = new ExamProfessorId();
		
		// initialize attributes
		this.primaryKey.setExam(e);
		this.primaryKey.setProfessor(prof);
		this.present = p;
		
		// update relationships to assure referential integrity
		this.primaryKey.getExam().addExamProfessor(this);
		//f.getProfessors().add(this);
		//d.getExams().add(this);
	}

	public ExamProfessorId getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(ExamProfessorId primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	@Transient
    public Exam getExam() {
        return getPrimaryKey().getExam();
    }
 
    public void setExam(Exam exam) {
        getPrimaryKey().setExam(exam);
    }
 
    @Transient
    public Professor getProfessor() {
        return getPrimaryKey().getProfessor();
    }
 
    public void setProfessor(Professor professor) {
        getPrimaryKey().setProfessor(professor);
    }

	public boolean isPresent() {
		return present;
	}

	public void setPresent(boolean present) {
		this.present = present;
	}
	       
}