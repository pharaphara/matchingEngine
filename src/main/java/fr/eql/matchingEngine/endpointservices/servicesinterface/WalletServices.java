package fr.eql.matchingEngine.endpointservices.servicesinterface;

import java.util.List;

import fr.eql.matchingEngine.dto.model.Ordre;

public interface WalletServices {

	void updateWallets(List<Ordre> orderList);

}
