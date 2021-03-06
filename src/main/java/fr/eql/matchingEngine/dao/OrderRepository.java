package fr.eql.matchingEngine.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import fr.eql.matchingEngine.dto.constant.OrderStatus;
import fr.eql.matchingEngine.dto.constant.OrderType;
import fr.eql.matchingEngine.dto.constant.TradingPair;
import fr.eql.matchingEngine.dto.entity.Ordre;

@Repository
public interface OrderRepository extends JpaRepository<Ordre, Integer>{
	
	List<Ordre> findByStatus(OrderStatus status);
	List<Ordre> findByStatusNotLike(OrderStatus status);
 	List<Ordre> findByStatusIn(List<OrderStatus> list);
 	List<Ordre> findByOrderTypeAndStatusIn(OrderType type, List<OrderStatus> list);
 	List<Ordre> findByOrderTypeAndStatusInOrderByLimitPriceAscAmountDesc(OrderType type, List<OrderStatus> list);
 	List<Ordre> findByOrderTypeAndStatusInOrderByLimitPriceAscAmountAsc(OrderType type, List<OrderStatus> list);
	List<Ordre> findByUser(String user);
	Ordre findFirstByCurrencyPairAndStatusOrderByIdAsc(TradingPair pair,OrderStatus status);
	Ordre findFirstByUserOrderByIdAsc(String user);
	List<Ordre> findByCurrencyPairAndFilledDateBetweenOrderByFilledDateAsc(TradingPair pair, LocalDateTime start, LocalDateTime end);
	List<Ordre> findByCurrencyPair(TradingPair pair);
}
