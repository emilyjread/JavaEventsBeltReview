package com.emilyread.events.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emilyread.events.models.Event;
import com.emilyread.events.models.Message;

@Repository
public interface MessagesRepo extends CrudRepository<Message,Long>{
	List<Message> findAllByEvent(Event e);
}
