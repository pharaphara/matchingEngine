package fr.eql.matchingEngine.services.servicesimpl;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eql.matchingEngine.dao.OrderRepository;
import fr.eql.matchingEngine.dto.constant.Interval;
import fr.eql.matchingEngine.dto.constant.OrderStatus;
import fr.eql.matchingEngine.dto.constant.TradingPair;
import fr.eql.matchingEngine.dto.entity.Ordre;
import fr.eql.matchingEngine.dto.model.PriceDto;
import fr.eql.matchingEngine.dto.model.PricesRequest;
import fr.eql.matchingEngine.services.servicesinterface.PriceServices;

@Service
public class PriceServicesImpl  implements PriceServices{
	
	@Autowired
	OrderRepository orderRepository;
	
	private Map<TradingPair, PriceDto> lastPrices;
	
	//when api start look for last prices in database
	@PostConstruct
	void onInit() {
		lastPrices= new HashMap<TradingPair, PriceDto>();
		Arrays.stream(TradingPair.values()).forEach(pair->{
			PriceDto lastPrice = new PriceDto(111d, pair, LocalDateTime.now());
			Ordre ordre = orderRepository.findFirstByCurrencyPairAndStatusOrderByIdAsc(pair, OrderStatus.FILLED);
			if (ordre!=null) {
				BeanUtils.copyProperties(ordre, lastPrice);
				
				lastPrices.put(pair, lastPrice);
			}
			

		});
	}

	@Override
	public PriceDto getLastPrice(TradingPair pair) {
		return lastPrices.get(pair);
	}

	@Override
	public void setLastPrice(PriceDto price) {
		lastPrices.put(price.getCurrencyPair(), price);
		
	}

	@Override
	public List<PriceDto> getPricesBetween(TradingPair pair, LocalDateTime start, LocalDateTime end) {
		List<Ordre> ordres = orderRepository.findByCurrencyPairAndFilledDateBetweenOrderByFilledDateAsc(pair, start, end);
		List<PriceDto> prices = new ArrayList<>();
		ordres.forEach(o->prices.add(new PriceDto(o)));

		return prices;
	}

	@Override
	public List<PriceDto> getPricesBetween(PricesRequest request) {
		return getPricesBetween(request.getPair(), request.getStart(), request.getEnd());
	}

	@Override
	public List<Double[]> getLastCandles(TradingPair pair, Interval interval, int nb) {
		List<PriceDto> prices=getPricesBetween(pair, LocalDateTime.now().minusHours(1), LocalDateTime.now());
		
		return null;
	}

	
	

}
