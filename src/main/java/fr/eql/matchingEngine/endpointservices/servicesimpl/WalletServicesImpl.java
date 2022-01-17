package fr.eql.matchingEngine.endpointservices.servicesimpl;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import fr.eql.matchingEngine.dao.PaymentRepo;
import fr.eql.matchingEngine.dto.constant.OrderType;
import fr.eql.matchingEngine.dto.entity.Ordre;
import fr.eql.matchingEngine.dto.entity.Payment;
import fr.eql.matchingEngine.dto.model.PaymentDto;
import fr.eql.matchingEngine.dto.model.PriceDto;
import fr.eql.matchingEngine.endpointservices.servicesinterface.WalletServices;
import fr.eql.matchingEngine.services.servicesinterface.PriceServices;

@Service
public class WalletServicesImpl implements WalletServices {

	Logger log = LoggerFactory.getLogger(WalletServicesImpl.class);

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	private PaymentRepo paymentRepo;
	
	@Autowired
	private PriceServices priceServices;	
	
	@Value("${services.walletApi}")
	private String url ;

	
	
	
	

	
	@Override
	public void sendPayment(Ordre ordre) {
		
		priceServices.setLastPrice(new PriceDto(ordre));
		
		//BTC_EUR : BTC=BASE ,  EUR=COUNTER

		String[] tickerTab = ordre.getCurrencyPair().name().split("_");



		Payment basePayement = new Payment(tickerTab[0], ordre.getUser(), ordre.getAmount(),LocalDateTime.now(),ordre);
		Payment counterPayement = new Payment(tickerTab[1], ordre.getUser(), ordre.getAmount()*ordre.getAveragePrice(),LocalDateTime.now(),ordre);

		if (ordre.getOrderType().equals(OrderType.ASK)) {
			basePayement.setMontant(basePayement.getMontant()*-1);
		}else {
			counterPayement.setMontant(counterPayement.getMontant()*-1);
		}

		



		if (postPayload(basePayement).getStatusCode().is2xxSuccessful()) {
			basePayement.setSent(true);
		}else {
			basePayement.setSent(false);
		}
		if (postPayload(counterPayement).getStatusCode().is2xxSuccessful()) {
			counterPayement.setSent(true);
		}else {
			counterPayement.setSent(false);
		}
		
		paymentRepo.save(basePayement);
		paymentRepo.save(counterPayement);


	}


	private ResponseEntity<String> postPayload(Payment payment) {

		PaymentDto payementDto = new PaymentDto();

		BeanUtils.copyProperties(payment, payementDto);

		ResponseEntity<String>  response = webClientBuilder.build()
				.post()
				.uri(url)
				.bodyValue(payementDto)
				.headers(header -> header.setContentType(MediaType.APPLICATION_JSON))
				.retrieve()
				.toEntity(String.class)
				.retry(3)
				.onErrorContinue((err, i) -> {log.info("onErrorContinue={}", i);})
				.block();
		
		log.info(response.getStatusCode().name());
		return response;
	}
}
