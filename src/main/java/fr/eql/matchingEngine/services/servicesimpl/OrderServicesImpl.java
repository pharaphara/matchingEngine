package fr.eql.matchingEngine.services.servicesimpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.eql.matchingEngine.dao.OrderRepository;
import fr.eql.matchingEngine.dto.model.Order;
import fr.eql.matchingEngine.dto.model.OrderDTO;
import fr.eql.matchingEngine.services.servicesinterface.OrderServices;

@Service
public class OrderServicesImpl implements OrderServices{
	
	@Autowired
	OrderRepository orderRepository;

	@Override
	public ResponseEntity<?> newOrder(Order order) {
		if(order.getCreationDate()==null) {
			order.setCreationDate(LocalDateTime.now());
		}
		orderRepository.save(order);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	

}
