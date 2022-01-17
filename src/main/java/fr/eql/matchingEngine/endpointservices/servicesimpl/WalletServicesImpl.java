package fr.eql.matchingEngine.endpointservices.servicesimpl;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import fr.eql.matchingEngine.dao.PaymentRepo;
import fr.eql.matchingEngine.dao.PriceRepo;
import fr.eql.matchingEngine.dto.constant.OrderType;
import fr.eql.matchingEngine.dto.constant.TradingPair;
import fr.eql.matchingEngine.dto.model.Ordre;
import fr.eql.matchingEngine.dto.model.Payment;
import fr.eql.matchingEngine.dto.model.PaymentDto;
import fr.eql.matchingEngine.dto.model.Price;
import fr.eql.matchingEngine.endpointservices.servicesinterface.WalletServices;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WalletServicesImpl implements WalletServices {

	Logger log = LoggerFactory.getLogger(WalletServicesImpl.class);

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	private PaymentRepo paymentRepo;
	
	@Autowired
	PriceRepo priceRepo;
	
	@Value("${services.walletApi}")
	private String url ;

	
	
	
	

	
	@Override
	public void sendPayment(Ordre ordre) {
		
		Price lastPrice = new Price(ordre);
		priceRepo.save(lastPrice);
		
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
