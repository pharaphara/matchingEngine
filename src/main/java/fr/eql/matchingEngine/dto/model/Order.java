package fr.eql.matchingEngine.dto.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import fr.eql.matchingEngine.dto.model.Constants.OrderStatus;
import fr.eql.matchingEngine.dto.model.Constants.TradingPair;

@Entity
public class Order {
	
	@Id
	@GeneratedValue
	private int id;
	

	/**
     * user email
	 */
	private String user;
	
	/**
	 * BASE/COUNTER, ex : BTC/USD, ETH/EUR
	 */
	private Constants.TradingPair currencyPair;
	
	 /**
      * BID=Buying order (the trader is providing the counter currency)
	  * ASK=Selling order (the trader is providing the base currency)
	  */
	private String orderType ; 
	
	/**
     * amount in base currency
	 */
	private float amount;
	
	/**
     * amount in base currency
	 */
	private float filledamount;
	
	/**
     * desired price in counter currency for 1 unit of base currency
     */
	private float limitPrice;
	
	/**
     * order status
	 */
	private Constants.OrderStatus status;

	/**
     * timestamp of creation date
	 */
	private LocalDateTime creationDate;

	public Order() {
		super();
	}

	public Order(int id, String user, TradingPair currencyPair, String orderType, float amount, float filledamount,
			float limitPrice, OrderStatus status, LocalDateTime creationDate) {
		super();
		this.id = id;
		this.user = user;
		this.currencyPair = currencyPair;
		this.orderType = orderType;
		this.amount = amount;
		this.filledamount = filledamount;
		this.limitPrice = limitPrice;
		this.status = status;
		this.creationDate = creationDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Constants.TradingPair getCurrencyPair() {
		return currencyPair;
	}

	public void setCurrencyPair(Constants.TradingPair currencyPair) {
		this.currencyPair = currencyPair;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getFilledamount() {
		return filledamount;
	}

	public void setFilledamount(float filledamount) {
		this.filledamount = filledamount;
	}

	public float getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(float limitPrice) {
		this.limitPrice = limitPrice;
	}

	public Constants.OrderStatus getStatus() {
		return status;
	}

	public void setStatus(Constants.OrderStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", user=" + user + ", currencyPair=" + currencyPair + ", orderType=" + orderType
				+ ", amount=" + amount + ", filledamount=" + filledamount + ", limitPrice=" + limitPrice + ", status="
				+ status + ", creationDate=" + creationDate + "]";
	}
	
	
	

}
