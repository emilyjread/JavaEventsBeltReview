package com.emilyread.events.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.emilyread.events.models.Event;
import com.emilyread.events.models.Message;
import com.emilyread.events.models.User;
import com.emilyread.events.services.EventService;
import com.emilyread.events.services.MessageService;
import com.emilyread.events.services.UserService;

@Controller
public class MainController {
	private final UserService userService;
	private final EventService eventService;
	private final MessageService messageService;
	
	public MainController(UserService userService, EventService eventService, MessageService messageService) {
	
		this.userService = userService;
		this.eventService = eventService;
		this.messageService = messageService;
	}
	
	//DASHBOARD- AFTER SUCCESSFUL LOGIN
	
	@RequestMapping("/dashboard")
	public String home(@ModelAttribute("user") User user, @ModelAttribute("event") Event event, Model model,  HttpSession session) {
		
		Long u_id = (Long) session.getAttribute("user_id");
		
		if (u_id==null) {
			return "redirect:/";
		}
		User thisUser = userService.findUserById(u_id);
		model.addAttribute("user", thisUser);
		
		//listing events
		
		List<Event> localEvents= eventService.eventsInState(thisUser.getState());
		model.addAttribute("localEvents", localEvents);
		
		List<Event> notLocalEvents= eventService.eventsOutOfState(thisUser.getState());
		model.addAttribute("notLocalEvents", notLocalEvents);
		
		return "dashboard.jsp";
		
	}
	
	//CREATE EVENT
	@RequestMapping(value= "/events", method=RequestMethod.POST)
	public String create(@Valid @ModelAttribute("event") Event event, BindingResult result, Model model, HttpSession session){
		if(result.hasErrors()) {
			Long u_id = (Long) session.getAttribute("user_id");
			
			if (u_id==null) {
				return "redirect:/";
			}
			User thisUser = userService.findUserById(u_id);
			model.addAttribute("user", thisUser);
			List<Event> localEvents= eventService.eventsInState(thisUser.getState());
			model.addAttribute("localEvents", localEvents);
			
			List<Event> notLocalEvents= eventService.eventsOutOfState(thisUser.getState());
			model.addAttribute("notLocalEvents", notLocalEvents);
			return "/dashboard.jsp";
		}
		else {
			eventService.createEvent(event);
			return "redirect:/dashboard";
		}
		
	}
	
	//SHOW EVENT
	@RequestMapping("/events/{id}")
	public String show(@ModelAttribute("message") Message message, @PathVariable("id") Long id, Model model, HttpSession session) {
		//event details
	    Event e = eventService.findEvent(id);
	    model.addAttribute("event", e);
	    
	    //get user
	    Long user_id= (Long) session.getAttribute("user_id");
	    model.addAttribute("user_id", user_id);
	    
	    //messages
	    List<Message> messages = e.getMessages();
	    model.addAttribute("messages", messages);
	    
	    //attendees
	    List<User> attendees= e.getAttendees();
	    int length = attendees.size();
	    model.addAttribute("attendees", attendees);
	    model.addAttribute("attendeesLength", length);
	    
	    return "/showEvent.jsp";
	}
	
	//EDIT EVENT
	@RequestMapping("/events/{id}/edit")
	public String editEvent(@PathVariable("id") Long id, Model model, HttpSession session) {
	    Event e = eventService.findEvent(id);
	    Long user_id= (Long) session.getAttribute("user_id");
	    User u=  userService.findUserById(user_id);
	   
	    if (e.getUser()== u) {
	    	model.addAttribute("event", e);
	    	return "/editEvent.jsp";	    	
	    }
	    return "redirect:/dashboard";
	}
	
	@RequestMapping(value="/events/{id}", method=RequestMethod.PUT)
	public String updateEvent(@Valid @ModelAttribute("event") Event event, BindingResult result, @PathVariable("id") Long id) {
		if (result.hasErrors()) {
			return "/editEvent.jsp";
        } else {
            eventService.updateEvent(event);
            return "redirect:/events/"+id;
        }
	}
	
	//DELETE EVENT
	@RequestMapping(value="/events/{id}", method=RequestMethod.DELETE)
	public String deleteEvent(@PathVariable("id") Long id) {
		eventService.deleteEvent(id);
		return "redirect:/dashboard";
	}
	
	//ADD MESSAGE
	@RequestMapping(value="/addmessage", method=RequestMethod.POST)
	public String addMessage(@Valid @ModelAttribute("message") Message message, BindingResult result, @RequestParam("event") Long event_id){
		if(result.hasErrors()) {
			return "/dashboard.jsp";
		}
		else {  
			messageService.createMessage(message);
			return "redirect:/events/"+ event_id;
		}
		
	}
	
	//JOIN EVENT
	@RequestMapping(value="/events/{id}/join")
	public String joinEvent(@ModelAttribute("event") Event event, @PathVariable("id") Long id, HttpSession session){
		Long user_id= (Long) session.getAttribute("user_id");
		userService.joinEvent(id, user_id);
		return "redirect:/events/"+id;
		
	}
	@RequestMapping(value="/events/{id}/cancel")
	public String cancelEvent(@ModelAttribute("event") Event event, @PathVariable("id") Long id, HttpSession session){
		Long user_id= (Long) session.getAttribute("user_id");
		userService.cancelEvent(id, user_id);
		return "redirect:/events/"+id;
		
	}

	

}
