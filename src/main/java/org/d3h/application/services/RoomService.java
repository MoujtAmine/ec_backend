package org.d3h.application.services;

import java.util.List;

import org.d3h.application.models.Room;
import org.d3h.application.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepo;
	
	//GET
	public List<Room> getAll(){
		return roomRepo.findAll();
	}
	
	public Room getById(long id) {
		if(roomRepo.existsById(id))
			return roomRepo.getOne(id);
		return null;
	}
	
	//SAVE
	public Room save(Room room) {
		return roomRepo.save(room);
	}
	
	//UPDATE
	public Room update(Room room, long id) {
		
		if(!roomRepo.existsById(id))
			return null;
		
		room.setId(id);
		return roomRepo.save(room);
		
	}
	
	//DELETE
	public boolean deleteById(long id) {
		
		if(roomRepo.existsById(id)) {
			roomRepo.deleteById(id);
			return true;
		}
			
		return false;
		

	}
	
}
