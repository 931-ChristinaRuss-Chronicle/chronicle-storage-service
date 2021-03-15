package com.revature.chronicle.services;

import com.revature.chronicle.daos.NoteRepo;
import com.revature.chronicle.models.Note;
import com.revature.chronicle.models.Tag;
import com.revature.chronicle.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service to handle business logic surrounding data access layer for notes
 */
@Service
public class NoteService {
    private static final Logger logger = LoggerFactory.getLogger(NoteService.class);
    @Autowired
    private NoteRepo noteRepo;

    public NoteService(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    /**
     * Finds all Videos that have the all of provided tags.
     * @param tags the tags provided by the user
     * @return a list of videos that have all tags
     */
    public List<Note> findAllNotesByTags(List<Tag> tags, User user){
        System.out.println("Entered service method");
        List<Note> desiredNotes = new ArrayList<>();
        int offset = 0;
        final int LIMIT = 50;
        do{
            //Query database for first 50 most recent results
            //Since date is a timestamp it should account for hours, mins, secs as well ensuring the order of the list
            List<Note> notes = noteRepo.findNotesWithOffsetAndLimit(offset,LIMIT);
            System.out.println(notes.size());

            //Check if notes is empty as no more records exist
            if(!notes.isEmpty()){
                //Iterate through 50 results
                for(Note note:notes){
                    //Check to see if result has all passed in tags,if so add to desiredVideos
                    if(note.getTags().containsAll(tags)){
                    	if(user.getRole() != null && user.getRole().equals("ROLE_ADMIN")) {
                    		logger.info("Adding note");
                    		desiredNotes.add(note);
                    	} else {
                    		if(!note.isPrivate()) {
                    			logger.info("Adding note");
                        		desiredNotes.add(note);
                    		} else {
	                    		for(String u : note.getWhitelist()) {
	                    			if(u.equals(user.getUid())) {
		                    			logger.info("Adding note");
		                        		desiredNotes.add(note);
		                        		break;
		                    		}
	                    		}
	                    		logger.warn("Not on note whitelist");
                    		}
                    	}
                    }
                    else{
                        logger.warn("Note not found");
                    }
                }
            }
            else{
                break;
            }
            offset+= notes.size();
        }
        while(desiredNotes.size() < 50 && !desiredNotes.isEmpty());

        //Find way to sort by return if it doesn't keep by recent order
        return desiredNotes;
    }

    public boolean save(Note note) {
        try {
            noteRepo.save(note);
            return true;
        }
        catch(Exception e) {
            logger.warn(e.getMessage());
            return false;
        }
    }
    
    public boolean update(Note note, User user) {
        try {
        	if(user.getRole() != null && user.getRole().equals("ROLE_ADMIN") || user.getUid().equals(note.getUser())) {
	            noteRepo.save(note);
	            return true;
        	} else {
        		return false;
        	}
        }
        catch(Exception e) {
            logger.warn(e.getMessage());
            return false;
        }
    }

    public List<Note> findAll(User user) {
        try {
        	List<Note> desiredNotes = new ArrayList<>();
            int offset = 0;
            final int LIMIT = 50;
            do{
                //Query database for first 50 most recent results
                //Since date is a timestamp it should account for hours, mins, secs as well ensuring the order of the list
                List<Note> notes = noteRepo.findNotesWithOffsetAndLimit(offset, LIMIT);

                //Check if notes is empty as no more records exist
                if(!notes.isEmpty()){
                    //Iterate through 50 results
                    for(Note note:notes){
                        //Check to see if result has all passed in tags,if so add to desiredVideos
                    	if(user.getRole() != null && user.getRole().equals("ROLE_ADMIN")) {
                    		logger.info("Adding note");
                    		desiredNotes.add(note);
                    	} else {
                    		if(!note.isPrivate()) {
                    			logger.info("Adding note");
                        		desiredNotes.add(note);
                    		} else {
                    			for(String u : note.getWhitelist()) {
	                    			if(u.equals(user.getUid())) {
		                    			logger.info("Adding note");
		                        		desiredNotes.add(note);
		                        		break;
		                    		}
	                    		}
	                    		logger.warn("Not on note whitelist");
                    		}
                    	}
                    }
                }
                else{
                    break;
                }
                offset+= notes.size();
            }
            while(desiredNotes.size() < 50 && !desiredNotes.isEmpty());

            //Find way to sort by return if it doesn't keep by recent order
            return desiredNotes;
        }
        catch (Exception e) {
            logger.warn(e.getMessage());
            return new ArrayList<Note>();
        }
    }

    public Note findById(int id) {
        // Changing method to return an Optional<Note> for consistency with the video service.
        //
        // Returning an Optional object instead of a Note object has been deemed the better option, since it reduces the
        // chance of null pointer exceptions and forces the programmer to actually check if the Note exists before
        // acting upon it.
        try{
            return noteRepo.findById(id).get();
        }
        catch (Exception e){
            logger.warn(e.getMessage());
            return null;
        }
    }

    public boolean deleteNote(Note note, User user) {
        try {
        	if(user.getRole() != null && user.getRole().equals("ROLE_ADMIN") || user.getUid().equals(note.getUser())) {
	            noteRepo.save(note);
	            return true;
        	} else {
        		return false;
        	}
        }
        catch (Exception e) {
            logger.warn(e.getMessage());
            return false;
        }
    }
}