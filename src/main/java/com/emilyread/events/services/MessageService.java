package com.emilyread.events.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.emilyread.events.models.Event;
import com.emilyread.events.models.Message;
import com.emilyread.events.repos.MessagesRepo;

@Service
public class MessageService {
	
private final MessagesRepo messagesRepo;
	
	public MessageService(MessagesRepo messagesRepo) {
		this.messagesRepo = messagesRepo;
	}
	
	//READ BY EVENT
    public List<Message> allMessagesByEvent(Event e) {
        return messagesRepo.findAllByEvent(e);
    }
    
    //CREATE MESSAGE
    
    public Message createMessage(Message m) {
        return messagesRepo.save(m);
    }
    
    
}
