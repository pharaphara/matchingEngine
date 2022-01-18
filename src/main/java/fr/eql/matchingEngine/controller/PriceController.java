package fr.eql.matchingEngine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.eql.matchingEngine.dao.CurrencyRepo;
import fr.eql.matchingEngine.dto.constant.TradingPair;
import fr.eql.matchingEngine.dto.entity.Currency;
import fr.eql.matchingEngine.dto.model.PriceDto;
import fr.eql.matchingEngine.dto.model.PricesRequest;
import fr.eql.matchingEngine.services.servicesinterface.PriceServices;

@RestController
@RequestMapping("/price")
public class PriceController {

	@Autowired
	PriceServices priceServices;
	
	@Autowired
	CurrencyRepo currencyRepo;
	
	@GetMapping("/getLastPrice")
	public ResponseEntity<PriceDto> getLastPrice(@RequestParam TradingPair pair){
		return new ResponseEntity<PriceDto>(priceServices.getLastPrice(pair), HttpStatus.OK) ;
	}

	@GetMapping("/getPrices") 
	public ResponseEntity<List<PriceDto>> getPrices(@RequestBody PricesRequest request){
		return new ResponseEntity<List<PriceDto>>(priceServices.getPricesBetween(request), HttpStatus.OK);
	}
	
	@GetMapping("/getCurrency")
	public ResponseEntity<Currency> getCurrency(@RequestParam String ticker){
		Currency currency = currencyRepo.findById(ticker).get();
		currency.setPrice(priceServices.getLastPrice(TradingPair.valueOf(ticker+"_EUR")).getPrice());
		
		return new ResponseEntity<Currency> (currencyRepo.findById(ticker).get(),HttpStatus.OK);
	}
	
	@GetMapping("/getCurrencies")
	public ResponseEntity<List<Currency>> getCurrencies(){
		List<Currency> currencies = currencyRepo.findAll();
		currencies.forEach(currency -> currency.setPrice(priceServices.getLastPrice(TradingPair.valueOf(currency.getTicker()+"_EUR")).getPrice()));
		
		return new ResponseEntity<List<Currency>> (currencies,HttpStatus.OK);
	}
}
