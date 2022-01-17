package fr.eql.matchingEngine.services.servicesinterface;

import java.time.LocalDateTime;
import java.util.List;

import fr.eql.matchingEngine.dto.constant.Interval;
import fr.eql.matchingEngine.dto.constant.TradingPair;
import fr.eql.matchingEngine.dto.model.PriceDto;
import fr.eql.matchingEngine.dto.model.PricesRequest;

public interface PriceServices {
	
	void setLastPrice(PriceDto price);
	PriceDto getLastPrice(TradingPair pair);
	List<PriceDto> getPricesBetween(TradingPair pair,LocalDateTime start, LocalDateTime end);
	List<PriceDto> getPricesBetween(PricesRequest request);
	List<Double[]>getLastCandles(TradingPair pair,Interval interval,int nb);
	

}
