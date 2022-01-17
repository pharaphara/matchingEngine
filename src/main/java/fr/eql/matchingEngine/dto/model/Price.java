package fr.eql.matchingEngine.dto.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import fr.eql.matchingEngine.dto.constant.TradingPair;

@Entity
public class Price {
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int id;
	
	private double price;
	
	@Enumerated(EnumType.STRING)
	private TradingPair pair;
	
	private LocalDateTime timestamp;

	public Price() {
		super();
	}
	
	public Price(Ordre ordre) {
		price = ordre.getAveragePrice();
		pair = ordre.getCurrencyPair();
		timestamp=ordre.getFilledDate();
	}

	public Price(int id, double price, TradingPair pair, LocalDateTime timestamp) {
		super();
		this.id = id;
		this.price = price;
		this.pair = pair;
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public TradingPair getPair() {
		return pair;
	}

	public void setPair(TradingPair pair) {
		this.pair = pair;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Price [id=" + id + ", price=" + price + ", pair=" + pair + ", timestamp=" + timestamp + "]";
	}
	
}
	