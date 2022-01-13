package fr.eql.matchingEngine.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.eql.matchingEngine.dto.model.GeobusApiModel;
import fr.eql.matchingEngine.dto.model.Order;
import fr.eql.matchingEngine.dto.model.OrderDTO;
import fr.eql.matchingEngine.services.servicesinterface.OrderServices;

@RestController
@RequestMapping("/bookOrder")
public class OrderController {
	
	@Autowired
	OrderServices orderServices;

	@GetMapping("/hello")
	public String hello(){
		return "Hello there !";
		
	}
	
	@PostMapping("bookOrder/order")
	public ResponseEntity<?> newOrder(@RequestBody OrderDTO orderdto){
		
		Order order = new Order();
		
		BeanUtils.copyProperties(orderdto, order);
		
		
		return orderServices.newOrder(order);
	}
}
