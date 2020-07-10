package org.d3h.application.controllers;

import java.net.URI;
import java.util.List;
import org.d3h.application.models.Formation;
import org.d3h.application.payload.ApiResponse;
import org.d3h.application.services.FormationService;
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
public class FormationController {

	@Autowired
	private FormationService formationService;
	
	//---GET---
	@GetMapping(value="/formations")
	public List<Formation> getAllFormations(){
		
		return(formationService.getAll());		
	}
	
	@GetMapping(value="/formations/{id}")
	public Formation getFormationById(@PathVariable long id){
		
		return formationService.getById(id);		
	}
	
	
	//POST
	@PostMapping("/formations")
	public ResponseEntity<?> createFormation(@RequestBody Formation formation) {
		
		Formation savedFormation = formationService.save(formation);
		if(savedFormation == null)
			return new ResponseEntity<>(new ApiResponse(false,"This formation is already existed!"),HttpStatus.BAD_REQUEST);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedFormation.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true,"This Formation is sucessfully created"));
	}
	
	//PUT
	@PutMapping("/formations/{id}")
	public ResponseEntity<?> updateFormation(@RequestBody Formation formation,@PathVariable long id) {
		
		Formation updatedFormation = formationService.update(formation, id);
		if(updatedFormation == null)
			return new ResponseEntity<>(new ApiResponse(false, "cet ID ne correspond à aucune formation !"), HttpStatus.BAD_REQUEST);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		.buildAndExpand(updatedFormation.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true,"This Formation is sucessfully updated"));
	}
	
	//DELETE
	@DeleteMapping("/formations/{id}")
	public ResponseEntity<?> deleteFormation(@PathVariable long id) {
		boolean isDeleted = formationService.deleteById(id);
		if(!isDeleted)
			return new ResponseEntity<>(new ApiResponse(false, "cet ID ne correspond à aucune formation !"), HttpStatus.BAD_REQUEST);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		.buildAndExpand(deleteFormation(id)).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true,"Formation is sucessfully deleted"));
	}
}
