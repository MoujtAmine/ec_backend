package org.d3h.application.services;

import java.util.List;

import org.d3h.application.models.Professor;
import org.d3h.application.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository professorRepo;
	
	//GET
	public List<Professor> getAll(){
		return professorRepo.findAll();
	}
	
	public Professor getById(long id) {
		if(professorRepo.existsById(id))
			return professorRepo.getOne(id);
		return null;
	}
	
	//SAVE
	public Professor save(Professor professor) {
		return professorRepo.save(professor);
	}
	
	//UPDATE
	public Professor update(Professor professor, long id) {
		
		if(!professorRepo.existsById(id))
			return null;
		
		professor.setId(id);
		return professorRepo.save(professor);
		
	}
	
	//DELETE
	public boolean deleteById(long id) {
		
		if(professorRepo.existsById(id)) {
			professorRepo.deleteById(id);
			return true;
		}
			
		return false;
		

	}
	
}
