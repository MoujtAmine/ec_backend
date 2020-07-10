package org.d3h.application.services;

import java.util.List;
import org.d3h.application.models.Student;
import org.d3h.application.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepo;
	
	//GET
	public List<Student> getAll(){
		return studentRepo.findAll();
	}
	
	public Student getById(long id) {
		if(studentRepo.existsById(id))
			return studentRepo.getOne(id);
		return null;
	}
	
	//SAVE
	public Student save(Student student) {
		return studentRepo.save(student);
	}
	
	//UPDATE
	public Student update(Student student, long id) {
		
		if(!studentRepo.existsById(id))
			return null;
		
		student.setId(id);
		return studentRepo.save(student);
		
	}
	
	//DELETE
	public boolean deleteById(long id) {
		
		if(studentRepo.existsById(id)) {
			studentRepo.deleteById(id);
			return true;
		}
			
		return false;
		
	}
	
}
