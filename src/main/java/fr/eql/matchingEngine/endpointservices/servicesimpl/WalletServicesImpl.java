package fr.eql.matchingEngine.endpointservices.servicesimpl;

import java.net.URI;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import fr.eql.matchingEngine.dao.PaymentRepo;
import fr.eql.matchingEngine.dto.constant.OrderType;
import fr.eql.matchingEngine.dto.model.Ordre;
import fr.eql.matchingEngine.dto.model.Payment;
import fr.eql.matchingEngine.dto.model.PaymentDto;
import fr.eql.matchingEngine.endpointservices.servicesinterface.WalletServices;

@Service
public class WalletServicesImpl implements WalletServices {

	

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	private PaymentRepo paymentRepo;


	@Override
	public void sendPayment(Ordre ordre) {
		//BTC_EUR : BTC=BASE ,  EUR=COUNTER

		String[] tickerTab = ordre.getCurrencyPair().name().split("_");



		Payment basePayement = new Payment(tickerTab[0], ordre.getUser(), ordre.getAmount(),ordre);
		Payment counterPayement = new Payment(tickerTab[1], ordre.getUser(), ordre.getAmount()*ordre.getAveragePrice(),ordre);

		if (ordre.getOrderType().equals(OrderType.BID)) {
			basePayement.setMontant(basePayement.getMontant()*-1);
		}else {
			counterPayement.setMontant(counterPayement.getMontant()*-1);
		}

		paymentRepo.save(basePayement);
		paymentRepo.save(counterPayement);



		postPayload(basePayement);
		postPayload(basePayement);


	}


	private void postPayload(Payment payment) {

		PaymentDto payementDto = new PaymentDto();

		BeanUtils.copyProperties(payment, payementDto);

		ResponseEntity<String>  response = webClientBuilder.build()
				.post()
				.uri("http://headers.jsontest.com/")
				.bodyValue(payementDto)
				.headers(header -> header.setContentType(MediaType.APPLICATION_JSON))
				.retrieve()
				.toEntity(String.class)
				.retry(3)
				.block();
		
		System.out.println(response.getBody());
	}
}
