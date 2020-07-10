package org.d3h.application.controllers;

import java.net.URI;
import java.util.List;
import org.d3h.application.models.Professor;
import org.d3h.application.payload.ApiResponse;
import org.d3h.application.services.ProfessorService;
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
public class ProfessorController {

	@Autowired
	private ProfessorService professorService;
	
	//---GET---
	@GetMapping(value="/professors")
	public List<Professor> getAllProfessors(){
		
		return(professorService.getAll());		
	}
	
	@GetMapping(value="/professors/{id}")
	public Professor getProfessorById(@PathVariable long id){
		
		return professorService.getById(id);		
	}
	
	
	//POST
	@PostMapping("/professors")
	public ResponseEntity<?> createProfessor(@RequestBody Professor professor) {
		
		Professor savedProfessor = professorService.save(professor);
		if(savedProfessor == null)
			return new ResponseEntity<>(new ApiResponse(false,"This professor is already existed!"),HttpStatus.BAD_REQUEST);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedProfessor.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true,"This Professor is sucessfully created"));
	}
	
	//PUT
	@PutMapping("/professors/{id}")
	public ResponseEntity<?> updateProfessor(@RequestBody Professor professor,@PathVariable long id) {
		
		Professor updatedProfessor = professorService.update(professor, id);
		if(updatedProfessor == null)
			return new ResponseEntity<>(new ApiResponse(false, "cet ID ne correspond à aucun professeur !"), HttpStatus.BAD_REQUEST);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		.buildAndExpand(updatedProfessor.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true,"This Professor is sucessfully updated"));
	}
	
	//DELETE
	@DeleteMapping("/professors/{id}")
	public ResponseEntity<?> deleteProfessor(@PathVariable long id) {
		boolean isDeleted = professorService.deleteById(id);
		if(!isDeleted)
			return new ResponseEntity<>(new ApiResponse(false, "cet ID ne correspond à aucun étudiant !"), HttpStatus.BAD_REQUEST);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		.buildAndExpand(deleteProfessor(id)).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true,"Professor is sucessfully deleted"));
	}
}
