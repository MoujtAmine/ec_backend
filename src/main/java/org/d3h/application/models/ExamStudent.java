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
@Table(name = "exams_students")
@AssociationOverrides({
    @AssociationOverride(name = "primaryKey.exam",
        joinColumns = @JoinColumn(name = "exam_id")),
    @AssociationOverride(name = "primaryKey.student",
        joinColumns = @JoinColumn(name = "student_id")) })
public class ExamStudent implements Serializable {

	private static final long serialVersionUID = 1L;

    // composite-id key
	@EmbeddedId
	private ExamStudentId primaryKey;
     
    // additional fields
    private boolean present;

	public ExamStudent() {
		super();
	}
	
	public ExamStudent(ExamStudentId primaryKey, boolean present) {
		super();
		this.primaryKey = primaryKey;
		this.present = present;
	}
	
    public ExamStudent(Exam f, Student d, boolean p) {
		// create primary key
		this.primaryKey = new ExamStudentId();
		
		// initialize attributes
		this.primaryKey.setExam(f);
		this.primaryKey.setStudent(d);
		this.present = p;
		
		// update relationships to assure referential integrity
		this.primaryKey.getExam().addExamStudent(this);
		//f.getStudents().add(this);
		//d.getExams().add(this);
	}

	public ExamStudentId getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(ExamStudentId primaryKey) {
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
    public Student getStudent() {
        return getPrimaryKey().getStudent();
    }
 
    public void setStudent(Student student) {
        getPrimaryKey().setStudent(student);
    }

	public boolean isPresent() {
		return present;
	}

	public void setPresent(boolean present) {
		this.present = present;
	}
	
}