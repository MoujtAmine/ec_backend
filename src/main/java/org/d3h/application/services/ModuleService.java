package org.d3h.application.services;

import java.util.List;

import org.d3h.application.models.Module;
import org.d3h.application.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleService {

	@Autowired
	private ModuleRepository moduleRepo;
	
	//GET
	public List<Module> getAll(){
		return moduleRepo.findAll();
	}
	
	public Module getById(long id) {
		if(moduleRepo.existsById(id))
			return moduleRepo.getOne(id);
		return null;
	}
	
	//SAVE
	public Module save(Module module) {
		return moduleRepo.save(module);
	}
	
	//UPDATE
	public Module update(Module module, long id) {
		
		if(!moduleRepo.existsById(id))
			return null;
		
		module.setId(id);
		return moduleRepo.save(module);
		
	}
	
	//DELETE
	public boolean deleteById(long id) {
		
		if(moduleRepo.existsById(id)) {
			moduleRepo.deleteById(id);
			return true;
		}
			
		return false;
		

	}
	
}
