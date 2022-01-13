package fr.eql.matchingEngine.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.eql.matchingEngine.dto.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

}
