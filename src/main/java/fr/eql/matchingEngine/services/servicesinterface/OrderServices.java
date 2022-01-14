package fr.eql.matchingEngine.services.servicesinterface;

import org.springframework.http.ResponseEntity;

import fr.eql.matchingEngine.dto.model.OrderDTO;
import fr.eql.matchingEngine.dto.model.Ordre;

public interface OrderServices {
	ResponseEntity<?> newOrder(Ordre newOrder);

}
