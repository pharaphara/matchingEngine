package fr.eql.matchingEngine.services.servicesimpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import fr.eql.matchingEngine.dao.OrderRepository;
import fr.eql.matchingEngine.dto.constant.OrderStatus;
import fr.eql.matchingEngine.dto.constant.OrderType;
import fr.eql.matchingEngine.dto.constant.TradingPair;
import fr.eql.matchingEngine.dto.model.Ordre;
import fr.eql.matchingEngine.endpointservices.servicesinterface.WalletServices;
import fr.eql.matchingEngine.services.servicesinterface.MatchingService;

@Service
public class MatchinServiceImpl implements MatchingService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	WalletServices walletServices;

	private Map<TradingPair, LinkedList<Ordre>> bidBooks;
	private Map<TradingPair, LinkedList<Ordre>> askBooks;

	@PostConstruct
	public void init() {

		List<Ordre> allOpenBid = orderRepository.findByOrderTypeAndStatusInOrderByLimitPrice(OrderType.BID, Arrays.asList(OrderStatus.NEW,OrderStatus.PARTIALLY_FILLED));
		System.out.println("open bid size = "+allOpenBid.size());
		List<Ordre> allOpenAsk = orderRepository.findByOrderTypeAndStatusInOrderByLimitPrice(OrderType.ASK, Arrays.asList(OrderStatus.NEW,OrderStatus.PARTIALLY_FILLED));
		System.out.println("open ask size = "+allOpenAsk.size());

		//initilizing each book
		bidBooks = new HashMap<TradingPair, LinkedList<Ordre>>();
		askBooks = new HashMap<TradingPair, LinkedList<Ordre>>();

		//Create an entry for each trading pair in each book order
		Arrays.stream(TradingPair.values()).forEach(pair->{
			bidBooks.put(pair, new LinkedList<Ordre>());
			askBooks.put(pair, new LinkedList<Ordre>());

		});


		//add all orders in the right linkedlist
		allOpenBid.forEach(bidOrder->bidBooks.get(bidOrder.getCurrencyPair()).add(bidOrder));
		allOpenAsk.forEach(askOrder->askBooks.get(askOrder.getCurrencyPair()).add(askOrder));


		bookToConsole();

	}

	private void bookToConsole() {
		bidBooks.forEach((pair,book)->{
			if(book.size()>0) {
				System.out.println("Pair  :"+pair);
				book.forEach(ordre->System.out.println("BUY"+" "+ordre.getLimitPrice()+" -"+(ordre.getAmount()-ordre.getFilledamount())));
			}

		});
		System.out.println("------");
		askBooks.forEach((pair,book)->{
			if(book.size()>0) {

				book.forEach(ordre->System.out.println("SEL"+" "+ordre.getLimitPrice()+" -"+(ordre.getAmount()-ordre.getFilledamount())));
			}

		});
	}
	@Override
	public void updateBook(Ordre newOrder) {

		init();
		bookToConsole();
		matchOrders();

	}

	private void matchOrders() {
		bidBooks.forEach((pair,book)->{

			if(book.size()>0&&askBooks.get(pair).size()>0) {
				Ordre lowestBID =book.getLast();
				Ordre lowestASK = askBooks.get(pair).getFirst();


				if(lowestBID.getLimitPrice()>=lowestASK.getLimitPrice()) {
					fullfillOrders(lowestBID,lowestASK);
					matchOrders();//recursivelly match all possible orders
				}
			}
		});
	}

	private void fullfillOrders(Ordre BID, Ordre ASK) {
		//oldest order set the trade price
		double tradePrice= (BID.getCreationDate().isBefore(ASK.getCreationDate()))? BID.getLimitPrice():ASK.getLimitPrice() ;
		if (BID.getAmount()-BID.getFilledamount()>=ASK.getAmount()-ASK.getFilledamount()) {
			
			
			
			BID.setAveragePrice(BID.getAveragePrice()+tradePrice/BID.getAmount()*ASK.leftamount());
			ASK.setAveragePrice(ASK.getAveragePrice()+tradePrice/ASK.getAmount()/ASK.leftamount());
			BID.setFilledamount(BID.getFilledamount()+ASK.getAmount()-ASK.getFilledamount());
			ASK.setFilledamount(ASK.getAmount());
			ASK.setStatus(OrderStatus.FILLED);
			walletServices.sendPayment(ASK);
			if (BID.getAmount()==BID.getFilledamount()) {
				BID.setStatus(OrderStatus.FILLED);
				walletServices.sendPayment(BID);
			}else {
				BID.setStatus(OrderStatus.PARTIALLY_FILLED);

			}

		}else {
			
			ASK.setAveragePrice(ASK.getAveragePrice()+tradePrice/ASK.getAmount()*BID.leftamount());
			BID.setAveragePrice(BID.getAveragePrice()+tradePrice/BID.getAmount()/BID.leftamount());
			ASK.setFilledamount(ASK.getFilledamount()+BID.getAmount()-BID.getFilledamount());
			BID.setFilledamount(BID.getAmount());
			BID.setStatus(OrderStatus.FILLED);
			walletServices.sendPayment(BID);
			if (ASK.getAmount()==ASK.getFilledamount()) {
				ASK.setStatus(OrderStatus.FILLED);
				walletServices.sendPayment(ASK);
			}else {
				ASK.setStatus(OrderStatus.PARTIALLY_FILLED);

			}
		}
		orderRepository.saveAll(Arrays.asList(BID,ASK));
		
		init();



	}



}
