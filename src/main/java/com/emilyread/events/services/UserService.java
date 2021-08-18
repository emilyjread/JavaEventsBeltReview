package com.emilyread.events.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.emilyread.events.models.Event;
import com.emilyread.events.models.User;
import com.emilyread.events.repos.EventsRepo;
import com.emilyread.events.repos.UserRepo;

@Service
public class UserService {

	
private final UserRepo userRepository;
private final EventsRepo eventsRepo;
    
    public UserService(UserRepo userRepository, EventsRepo eventsRepo) {
        this.userRepository = userRepository;
		this.eventsRepo = eventsRepo;
    }
    
    
    // register user and hash their password
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepository.save(user);
    }
    
    // find user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    // find user by id
    public User findUserById(Long id) {
    	Optional<User> u = userRepository.findById(id);
    	
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
    }
    
    // authenticate user
    public boolean authenticateUser(String email, String password) {
        // first find the user by email
        User user = userRepository.findByEmail(email);
        // if we can't find it by email, return false
        if(user == null) {
            return false;
        } else {
            // if the passwords match, return true, else, return false
            if(BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    public User findUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            return null;
        }
    }
    
    public Event findEvent(Long id) {
        Optional<Event> optionalEvent = eventsRepo.findById(id);
        if(optionalEvent.isPresent()) {
            return optionalEvent.get();
        } else {
            return null;
        }
    }
    
    public User joinEvent(Long event_id, Long user_id) {
	    Event e = findEvent(event_id);	
	    User u = findUser(user_id);
	    u.getAttendingEvents().add(e);
	    return userRepository.save(u);
    }
    public User cancelEvent(Long event_id, Long user_id) {
	    Event e = findEvent(event_id);	
	    User u = findUser(user_id);
	    u.getAttendingEvents().remove(e);
	    return userRepository.save(u);
    }
    
    
    

}
