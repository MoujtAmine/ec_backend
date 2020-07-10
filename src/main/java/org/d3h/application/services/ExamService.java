package org.d3h.application.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.d3h.application.models.Exam;
import org.d3h.application.models.ExamProfessor;
import org.d3h.application.models.ExamStudent;
import org.d3h.application.models.Module;
import org.d3h.application.models.Professor;
import org.d3h.application.models.Room;
import org.d3h.application.models.Session;
import org.d3h.application.models.Student;
import org.d3h.application.repositories.ExamRepository;
import org.d3h.application.repositories.ModuleRepository;
import org.d3h.application.repositories.RoomRepository;
import org.d3h.application.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamService {

	@Autowired
	private ExamRepository examRepo;
	
	@Autowired
	private StudentRepository studentRepo;

	/*
	@Autowired
	private ProfessorRepository professorRepo;
	 */
	@Autowired
	private RoomRepository roomRepo;
	
	@Autowired
	private ModuleRepository moduleRepo;
	
	//GET
	public List<Exam> getAll(){
		return examRepo.findAll();
	}
	
	public Exam getById(long id) {
		if(examRepo.existsById(id))
			return examRepo.getOne(id);
		return null;
	}
	
	//SAVE
	public Exam save(Exam exam) {
		return examRepo.save(exam);
	}
	
	public Exam create(Module m, Room r, LocalDateTime d, List<Professor> profs, Session session) {
		
		Exam exam = new Exam();

		//Initialiser la date :
		exam.setDate(d);
		
		//Initialiser la session :
		exam.setSession(session);
		
		//Initialiser la salle :
		if(roomRepo.findById(r.getId()).isPresent())
			exam.setRoom(r); 
		
		//Initialiser le module :
		if(moduleRepo.findById(m.getId()).isPresent())
			exam.setModule(m); 
		
        //Initialiser liste des surveillants :
        List<ExamProfessor> professorsList = new ArrayList<>();        
        for( Professor p : profs ) {
        	if(p != null) {
            	ExamProfessor ep = new ExamProfessor(exam,p,false);
            	professorsList.add(ep);
        	}
        }

        //Initialiser liste des participants :
        // Et ce d'une manière automatique
        // c-à-d : les participants sont les étudiants qui sont déja inscrits dans la formation qui contient le module M 
        List<ExamStudent> studentsList = new ArrayList<>();
        for( Student s : m.getFormation().getStudents()) {
        	if(s != null) {
        		ExamStudent es = new ExamStudent(exam, s, false); 
            	studentsList.add(es);
        	}
        } 
        
        examRepo.save(exam);
		return exam;
		
	}

	//UPDATE
	public Exam update(Exam exam, long id) {
		
		if(!examRepo.existsById(id))
			return null;
		
		exam.setId(id);
		return examRepo.save(exam);
		
	}
	
	public boolean setStudentPresence(long examId, long studentId, boolean isPresent) {
		
		Exam exam = examRepo.getOne(examId);
		
		
		if(this.isStudentParticipate(studentId, examId)) {
			for(ExamStudent es : exam.getStudents()) {
				if(es.getPrimaryKey().getStudent().getId() == studentId) {
					es.setPresent(isPresent);
					break;
				}
			}
			examRepo.save(exam);
			return true;
		}
		else {
			// Cet étudiant n'est pas un participant
			System.out.println("Etudiant ID-"+studentId+"n'existe pas dans la liste d'examen ID-"+examId );
			return false;
		}
		


	}
	
	public boolean setProfessorPresence(long examId, long profId, boolean isPresent) {
		
		Exam exam = examRepo.getOne(examId);
		ExamProfessor ep = exam.getExamProfessorById(profId);

		// prof n'existe pas dans la liste de surveillance
		if(ep == null) 
			return false;
		
		ep.setPresent(isPresent);
		examRepo.save(exam);
		return true;
	}
	
	public boolean isStudentParticipate(long studentId, long examId) {
		Exam exam = examRepo.getOne(examId);
		for(Student s : exam.getParticipants()) {
			if(s.getId() == studentId)
				return true;
		}
		
		return false;
	}
	
	//DELETE
	public boolean deleteById(long id) {
		
		if(examRepo.existsById(id)) {
			examRepo.deleteById(id);
			return true;
		}
		return false;
	}

	public List<Student> getListParticiants(long id) {

		if(examRepo.existsById(id)) 
			return examRepo.getOne(id).getParticipants();
		return null;

	}

	public List<Professor> getListSurveillants(long id) {

		if(examRepo.existsById(id)) 
			return examRepo.getOne(id).getSurveillants();
		return null;

	}
}
