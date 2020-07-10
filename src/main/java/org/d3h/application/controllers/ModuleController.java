package org.d3h.application.controllers;

import java.net.URI;
import java.util.List;
import org.d3h.application.models.Module;
import org.d3h.application.payload.ApiResponse;
import org.d3h.application.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class ModuleController {

	@Autowired
	private ModuleService moduleService;
	
	//---GET---
	@GetMapping(value="/modules")
	public List<Module> getAllModules(){
		
		return(moduleService.getAll());		
	}
	
	@GetMapping(value="/modules/{id}")
	public Module getModuleById(@PathVariable long id){
		
		return moduleService.getById(id);		
	}
	
	
	//POST
	@PostMapping("/modules")
	public ResponseEntity<?> createModule(@RequestBody Module module) {
		
		Module savedModule = moduleService.save(module);
		if(savedModule == null)
			return new ResponseEntity<>(new ApiResponse(false,"This module is already existed!"),HttpStatus.BAD_REQUEST);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedModule.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true,"This Module is sucessfully created"));
	}
	
	//PUT
	@PutMapping("/modules/{id}")
	public ResponseEntity<?> updateModule(@RequestBody Module module,@PathVariable long id) {
		
		Module updatedModule = moduleService.update(module, id);
		if(updatedModule == null)
			return new ResponseEntity<>(new ApiResponse(false, "cet ID ne correspond à aucun module !"), HttpStatus.BAD_REQUEST);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		.buildAndExpand(updatedModule.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true,"This Module is sucessfully updated"));
	}
	
	//DELETE
	@DeleteMapping("/modules/{id}")
	public ResponseEntity<?> deleteModule(@PathVariable long id) {
		boolean isDeleted = moduleService.deleteById(id);
		if(!isDeleted)
			return new ResponseEntity<>(new ApiResponse(false, "cet ID ne correspond à aucun module !"), HttpStatus.BAD_REQUEST);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		.buildAndExpand(deleteModule(id)).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true,"Module is sucessfully deleted"));
	}
}
