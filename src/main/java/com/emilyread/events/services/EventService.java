package com.emilyread.events.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.emilyread.events.models.Event;
import com.emilyread.events.models.Message;
import com.emilyread.events.models.User;
import com.emilyread.events.repos.EventsRepo;
import com.emilyread.events.repos.UserRepo;

@Service
public class EventService {
	
	private final EventsRepo eventsRepo;
	private final UserRepo userRepo;
	
	public EventService(EventsRepo eventsRepo, UserRepo userRepo) {
		this.eventsRepo = eventsRepo;
		this.userRepo = userRepo;
	}

	//READ ALL
    public List<Event> allEvents() {
        return eventsRepo.findAll();
    }
    
    
    //READ BY STATE- LOCAL
    public List<Event> eventsInState(String state) {
        return eventsRepo.findAllByState(state);
    }
    
    //READ BY STATE - NOT LOCAL
    public List<Event> eventsOutOfState(String state) {
        return eventsRepo.findByStateNotContains(state);
    }

    //READ BY ID
    public Event findEvent(Long id) {
        Optional<Event> optionalEvent = eventsRepo.findById(id);
        if(optionalEvent.isPresent()) {
            return optionalEvent.get();
        } else {
            return null;
        }
    }
    
    //UPDATE
    public Event updateEvent(Event e) {
    	return eventsRepo.save(e);
    }
    
   
    //CREATE
    public Event createEvent(Event e) {
        return eventsRepo.save(e);
    }
    
    //DELETE    
    public void deleteEvent(Long id) {
        eventsRepo.deleteById(id);
    }
    
    //JOIN EVENT
    public User findUser(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            return null;
        }
    }
 
    public Event addMessage(Event e, Message m) {
    	e.getMessages().add(m);
    	return eventsRepo.save(e);
    }
    
}
