package fr.eql.matchingEngine.endpointservices.servicesinterface;

import fr.eql.matchingEngine.dto.model.Ordre;

public interface PaymentServices {	

	void sendPayment(Ordre ordre);

}
