package fr.eql.matchingEngine.endpointservices.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import fr.eql.matchingEngine.dto.model.Ordre;
import fr.eql.matchingEngine.endpointservices.servicesinterface.WalletServices;

@Service
public class WalletServicesImpl implements WalletServices {

	@Autowired
	private WebClient.Builder webClientBuilder;
	
	
	@Override
	public void updateWallets(List<Ordre> orderList) {
		orderList.forEach(order->{
			
//			ResponseEntity<String>  response = webClientBuilder.build()
//					.post()
//					.uri(url+"exchangeinstance/")
//					.cookie("NSC_mc_wtsw_qse-hftuw3", "ffffffff0949aa2f45525d5f4f58455e445a4a4216cb")//needed to send all request to the same node
//					.bodyValue(filters)
//					.accept(MediaType.APPLICATION_JSON)
//					.headers(header -> header.setBearerAuth(tokenService.getToken()))
//					.retrieve()
//					.toEntity(String.class)
//					.retry(3)
//					.block();
//
//			return response;
			
		});

	}

}
