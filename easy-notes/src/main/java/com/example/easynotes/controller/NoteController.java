package com.example.easynotes.controller;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;

@RestController
@RequestMapping("/api")
public class NoteController {

	@Autowired
	NoteRepository noteRepository;
	
	//Get All notes
	@GetMapping("/notes")
	public List<Note> getAllNotes()
	{
		return noteRepository.findAll();
	}
	
	
	//create a Note	
	@PostMapping("/notes")
	public Note createNote(@Valid @RequestBody Note note)
	{
		return noteRepository.save(note);
		
	}
	
	//Get a single Note
	@GetMapping("/notes/{id}")
	public Resource<Note> getNoteById(@PathVariable(value="id") Long noteId)
	{	
		Note note = noteRepository.findById(noteId).orElseThrow(()-> new ResourceNotFoundException("Note", "Id", noteId));
		//"all-users" , SERVER_PATH+"/users"
		//retrieveAllUsers
		Resource<Note> resource = new Resource<Note>(note);
		ControllerLinkBuilder linkTo=linkTo(methodOn(this.getClass()).getAllNotes());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
	
	//Update a Note
	
	@PutMapping("/notes/{id}")
	public Note updateNote(@PathVariable(value="id") Long noteId, @Valid @RequestBody Note noteDetails)
	{
		Note note = noteRepository.findById(noteId).orElseThrow(()-> new ResourceNotFoundException("note", "id", noteId));
		 note.setTitle(noteDetails.getTitle());
		 note.setContent(noteDetails.getContent());
		 Note updatedNote = noteRepository.save(note);
		 return updatedNote;
		
	}
	
	//Delete a Note
	@DeleteMapping("/notes/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value="id") Long noteId)
	{
		Note note = noteRepository.findById(noteId).orElseThrow(()-> new ResourceNotFoundException("Note", "id ",noteId));
		noteRepository.delete(note);		
		return ResponseEntity.ok().build();
	}
	
	
	
}
