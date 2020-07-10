package org.d3h.application.controllers;

import java.net.URI;
import java.util.List;
import org.d3h.application.models.Room;
import org.d3h.application.payload.ApiResponse;
import org.d3h.application.services.RoomService;
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
public class RoomController {

	@Autowired
	private RoomService roomService;
	
	//---GET---
	@GetMapping(value="/rooms")
	public List<Room> getAllRooms(){
		
		return(roomService.getAll());		
	}
	
	@GetMapping(value="/rooms/{id}")
	public Room getRoomById(@PathVariable long id){
		
		return roomService.getById(id);		
	}
	
	
	//POST
	@PostMapping("/rooms")
	public ResponseEntity<?> createRoom(@RequestBody Room room) {
		
		Room savedRoom = roomService.save(room);
		if(savedRoom == null)
			return new ResponseEntity<>(new ApiResponse(false,"This room is already existed!"),HttpStatus.BAD_REQUEST);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedRoom.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true,"This Room is sucessfully created"));
	}
	
	//PUT
	@PutMapping("/rooms/{id}")
	public ResponseEntity<?> updateRoom(@RequestBody Room room,@PathVariable long id) {
		
		Room updatedRoom = roomService.update(room, id);
		if(updatedRoom == null)
			return new ResponseEntity<>(new ApiResponse(false, "cet ID ne correspond à aucune salle !"), HttpStatus.BAD_REQUEST);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		.buildAndExpand(updatedRoom.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true,"This Room is sucessfully updated"));
	}
	
	//DELETE
	@DeleteMapping("/rooms/{id}")
	public ResponseEntity<?> deleteRoom(@PathVariable long id) {
		boolean isDeleted = roomService.deleteById(id);
		if(!isDeleted)
			return new ResponseEntity<>(new ApiResponse(false, "cet ID ne correspond à aucune salle !"), HttpStatus.BAD_REQUEST);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		.buildAndExpand(deleteRoom(id)).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true,"Room is sucessfully deleted"));
	}
}
