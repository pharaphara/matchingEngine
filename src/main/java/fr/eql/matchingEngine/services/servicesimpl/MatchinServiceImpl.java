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
				book.forEach(ordre->System.out.println("BUY"+" "+ordre.getLimitPrice()+" -"+ordre.getId()));
			}

		});
		System.out.println("------");
		askBooks.forEach((pair,book)->{
			if(book.size()>0) {

				book.forEach(ordre->System.out.println("SEL"+" "+ordre.getLimitPrice()+" -"+ordre.getId()));
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
		if (BID.getAmount()>=ASK.getAmount()) {
			BID.setFilledamount(BID.getFilledamount()+ASK.getAmount());
			ASK.setFilledamount(ASK.getAmount());
			ASK.setStatus(OrderStatus.FILLED);
			walletServices.updateWallets(ASK);
			if (BID.getAmount()==BID.getFilledamount()) {
				BID.setStatus(OrderStatus.FILLED);
				walletServices.updateWallets(BID);
			}else {
				BID.setStatus(OrderStatus.PARTIALLY_FILLED);
				
			}

		}else {
			BID.setFilledamount(BID.getAmount());
			ASK.setFilledamount(ASK.getFilledamount()+BID.getAmount());
			BID.setStatus(OrderStatus.FILLED);
			walletServices.updateWallets(BID);
			if (ASK.getAmount()==ASK.getFilledamount()) {
				ASK.setStatus(OrderStatus.FILLED);
				walletServices.updateWallets(ASK);
			}else {
				ASK.setStatus(OrderStatus.PARTIALLY_FILLED);
				
			}
		}

		orderRepository.saveAll(Arrays.asList(BID,ASK));
		
		
		init();



	}



}
