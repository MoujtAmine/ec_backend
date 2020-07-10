package org.d3h.application.controllers;

import java.net.URI;
import java.util.List;
import org.d3h.application.models.Student;
import org.d3h.application.payload.ApiResponse;
import org.d3h.application.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"*"})
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	//---GET---
	@GetMapping(value="/students")
	public List<Student> getAllStudents(){
		
		return(studentService.getAll());		
	}
	
	@GetMapping(value="/students/{id}")
	public Student getStudentById(@PathVariable long id){
		
		return studentService.getById(id);		
	}
	
	
	//POST
	@PostMapping("/students")
	public ResponseEntity<?> createStudent(@RequestBody Student student) {
		
		Student savedStudent = studentService.save(student);
		if(savedStudent == null)
			return new ResponseEntity<>(new ApiResponse(false,"This student is already existed!"),HttpStatus.BAD_REQUEST);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedStudent.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true,"This Student is sucessfully created"));
	}
	
	//PUT
	@PutMapping("/students/{id}")
	public ResponseEntity<?> updateStudent(@RequestBody Student student,@PathVariable long id) {
		
		Student updatedStudent = studentService.update(student, id);
		if(updatedStudent == null)
			return new ResponseEntity<>(new ApiResponse(false, "cet ID ne correspond à aucun étudiant !"), HttpStatus.BAD_REQUEST);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		.buildAndExpand(updatedStudent.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true,"This Student is sucessfully updated"));
	}
	
	//DELETE
	@DeleteMapping("/students/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable long id) {
		boolean isDeleted = studentService.deleteById(id);
		if(!isDeleted)
			return new ResponseEntity<>(new ApiResponse(false, "cet ID ne correspond à aucun étudiant !"), HttpStatus.BAD_REQUEST);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		.buildAndExpand(deleteStudent(id)).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true,"Student is sucessfully deleted"));
	}
	
}
