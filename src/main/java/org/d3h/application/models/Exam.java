package org.d3h.application.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Exam implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private LocalDateTime date;
	
	@Enumerated(EnumType.STRING)
	private Session session;
	
	@ManyToOne
	private Room room;
	
	@ManyToOne
	private Module module;
	
	
	@OneToMany(mappedBy = "primaryKey.exam",cascade = CascadeType.ALL)
	private List<ExamProfessor> professors = new ArrayList<>();
	
    @OneToMany(mappedBy = "primaryKey.exam",cascade = CascadeType.ALL)
    private List<ExamStudent> students = new ArrayList<>();
	
	public Exam() {
		super();
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	

	public List<ExamStudent> getStudents() {
		return students;
	}


	public void setStudents(List<ExamStudent> students) {
		this.students = students;
	}


	public List<ExamProfessor> getProfessors() {
		return professors;
	}


	public void setProfessors(List<ExamProfessor> professors) {
		this.professors = professors;
	}

	
	public void addExamStudent(ExamStudent examStudent) {
		this.students.add(examStudent);
	}
	
	public void addExamProfessor(ExamProfessor examProfessor) {
		this.professors.add(examProfessor);
	}
	
	public List<Student> getParticipants() {
    	
    	List<Student> participants = new ArrayList<>();
    	for(ExamStudent es : this.getStudents()) 
    		participants.add(es.getStudent());
    	
    	return participants;
    }
	
	public List<Professor> getSurveillants() {
    	
    	List<Professor> surveillants = new ArrayList<>();
    	for(ExamProfessor ep : this.getProfessors()) 
    		surveillants.add(ep.getProfessor());
    	
    	return surveillants;
    }
	
	public boolean isStidentParticipate(Student s) {
		
		for(Student std : this.getParticipants()) {
			if(std.getId() == s.getId())
				return true;
		}

		return false;
	}
	
	public boolean isProfParticipate(Professor p) {
		
		for(Professor prof : this.getSurveillants()) {
			if(prof.getId() == p.getId())
				return true;
		}

		return false;
	}
	
	public ExamStudent getExamStudentById(long id) {
		for(ExamStudent es : this.getStudents()) {
			if(es.getPrimaryKey().getStudent().getId() == id) {
				return es;
			}
		}
		return null;
	}
	
	public ExamProfessor getExamProfessorById(long id) {
		for(ExamProfessor ep : this.getProfessors()) {
			if(ep.getPrimaryKey().getProfessor().getId() == id) {
				return ep;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "Exam [id=" + id + ", date=" + date + ", session=" + session + ", room=" + room + ", module=" + module + "]";
	}

}
