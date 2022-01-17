package fr.eql.matchingEngine.services.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eql.matchingEngine.dao.PriceRepo;
import fr.eql.matchingEngine.dto.constant.TradingPair;
import fr.eql.matchingEngine.dto.model.Price;
import fr.eql.matchingEngine.services.servicesinterface.PriceServices;

@Service
public class PriceServicesImpl  implements PriceServices{
	
	@Autowired
	PriceRepo priceRepo;

	@Override
	public Price getLastPrice(TradingPair pair) {
		// TODO Auto-generated method stub
		return null;
	}

}
