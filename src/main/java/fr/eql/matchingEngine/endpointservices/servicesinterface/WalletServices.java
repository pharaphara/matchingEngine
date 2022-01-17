package fr.eql.matchingEngine.endpointservices.servicesinterface;

import java.util.List;

import fr.eql.matchingEngine.dto.constant.TradingPair;
import fr.eql.matchingEngine.dto.entity.Ordre;

public interface WalletServices {

	void sendPayment(Ordre ordre);

}
