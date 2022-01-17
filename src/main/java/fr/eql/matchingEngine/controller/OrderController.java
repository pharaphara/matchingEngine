package fr.eql.matchingEngine.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.eql.matchingEngine.dao.OrderRepository;
import fr.eql.matchingEngine.dao.PaymentRepo;
import fr.eql.matchingEngine.dto.constant.OrderStatus;
import fr.eql.matchingEngine.dto.constant.TradingPair;
import fr.eql.matchingEngine.dto.model.OrderDTO;
import fr.eql.matchingEngine.dto.model.Ordre;
import fr.eql.matchingEngine.endpointservices.servicesinterface.WalletServices;
import fr.eql.matchingEngine.services.servicesinterface.OrderServices;

@RestController
@RequestMapping("/bookOrder")
public class OrderController {

	@Autowired
	OrderServices orderServices;
	
	@Autowired
	WalletServices walletServices;

	@Autowired
	OrderRepository orderRepository;

	@GetMapping("/hello")
	public String hello(){
		return "Hello there !";

	}

	@PostMapping("/order")
	public ResponseEntity<?> newOrder(@RequestBody Ordre newOrder){
		return orderServices.newOrder(newOrder);
	}

	@GetMapping("/myOrders")
	public ResponseEntity<?> getOrdersByUser(@RequestParam String user){
		return new ResponseEntity<Object>(orderRepository.findByUser(user), HttpStatus.OK) ;
	}

	@GetMapping("/status")
	public ResponseEntity<?> getByStatus(@RequestParam OrderStatus status){
		return new ResponseEntity<Object>(orderRepository.findByStatus(status), HttpStatus.OK) ;
	}

	@GetMapping("/getLastPrice")
	public ResponseEntity<?> getLastPrice(@RequestParam TradingPair pair){
		return new ResponseEntity<Object>(null, HttpStatus.OK) ;
	}
}
