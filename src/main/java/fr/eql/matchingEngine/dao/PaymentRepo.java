package fr.eql.matchingEngine.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eql.matchingEngine.dto.model.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {
	
	Payment findFirstByOrderByTimestampAsc();

}
