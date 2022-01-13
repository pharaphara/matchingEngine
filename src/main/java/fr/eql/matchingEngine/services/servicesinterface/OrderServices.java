package fr.eql.matchingEngine.services.servicesinterface;

import org.springframework.http.ResponseEntity;

import fr.eql.matchingEngine.dto.model.Order;
import fr.eql.matchingEngine.dto.model.OrderDTO;

public interface OrderServices {
	ResponseEntity<?> newOrder(Order order);

}
