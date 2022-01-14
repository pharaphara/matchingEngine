package fr.eql.matchingEngine.services.servicesimpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.eql.matchingEngine.dao.OrderRepository;
import fr.eql.matchingEngine.dto.constant.OrderStatus;
import fr.eql.matchingEngine.dto.model.OrderDTO;
import fr.eql.matchingEngine.dto.model.Ordre;
import fr.eql.matchingEngine.services.servicesinterface.MatchingService;
import fr.eql.matchingEngine.services.servicesinterface.OrderServices;

@Service
public class OrderServicesImpl implements OrderServices{
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	MatchingService matchingService;
	
	@Override
	public ResponseEntity<?> newOrder(Ordre newOrder) {
		
		newOrder.setCreationDate(LocalDateTime.now());
		newOrder.setStatus(OrderStatus.NEW);
		
		orderRepository.save(newOrder);
		matchingService.updateBook(newOrder);
		
	
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	

}
