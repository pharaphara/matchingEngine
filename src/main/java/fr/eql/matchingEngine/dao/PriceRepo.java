package fr.eql.matchingEngine.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.eql.matchingEngine.dto.model.Price;

public interface PriceRepo extends JpaRepository<Price, Integer> {
//	
//	@Query("SELECT p FROM RuleVo r where r.name = :name") 
//    List<Long> findIdByName(@Param("name") String name);


}
