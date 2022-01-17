package fr.eql.matchingEngine.services.servicesinterface;

import fr.eql.matchingEngine.dto.constant.TradingPair;
import fr.eql.matchingEngine.dto.model.Price;

public interface PriceServices {
	
	
	Price getLastPrice(TradingPair pair);

}
