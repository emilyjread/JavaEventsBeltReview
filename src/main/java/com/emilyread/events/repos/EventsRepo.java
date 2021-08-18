package com.emilyread.events.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emilyread.events.models.Event;



@Repository
public interface EventsRepo extends CrudRepository<Event, Long>{
	List<Event> findAll();
	List<Event> findAllByState(String State);
	List<Event> findByStateNotContains(String State);
}
