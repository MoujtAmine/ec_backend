package org.d3h.application.services;

import java.util.List;

import org.d3h.application.models.Formation;
import org.d3h.application.repositories.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormationService {

	@Autowired
	private FormationRepository formationRepo;
	
	//GET
	public List<Formation> getAll(){
		return formationRepo.findAll();
	}
	
	public Formation getById(long id) {
		if(formationRepo.existsById(id))
			return formationRepo.getOne(id);
		return null;
	}
	
	//SAVE
	public Formation save(Formation formation) {
		return formationRepo.save(formation);
	}
	
	//UPDATE
	public Formation update(Formation formation, long id) {
		
		if(!formationRepo.existsById(id))
			return null;
		
		formation.setId(id);
		return formationRepo.save(formation);
		
	}
	
	//DELETE
	public boolean deleteById(long id) {
		
		if(formationRepo.existsById(id)) {
			formationRepo.deleteById(id);
			return true;
		}
			
		return false;
		

	}
	
}
