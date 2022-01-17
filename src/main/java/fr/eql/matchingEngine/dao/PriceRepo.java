package fr.eql.matchingEngine.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.eql.matchingEngine.dto.constant.TradingPair;
import fr.eql.matchingEngine.dto.model.Price;

public interface PriceRepo extends JpaRepository<Price, Integer> {
	
//	@Query("SELECT p FROM Price p where p.pair = :name") 
//    List<Price> findIdByName(@Param("pair") TradingPair pair);


}
