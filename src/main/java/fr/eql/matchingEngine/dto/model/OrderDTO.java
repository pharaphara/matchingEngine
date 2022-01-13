package fr.eql.matchingEngine.dto.model;


public class OrderDTO {
	
	/**
     * user email
	 */
	private String user;
	
	/**
	 * BASE/COUNTER, ex : BTC/USD, ETH/EUR
	 */
	private String currencyPair;
	
	 /**
      * BID=Buying order (the trader is providing the counter currency)
	  * ASK=Selling order (the trader is providing the base currency)
	  */
	private String orderType; 
	
	/**
     * amount in base currency
	 */
	private float amount;
	
	/**
     * desired price in counter currency for 1 unit of base currency
	 */
	private float limitPrice;

	public OrderDTO(String user, String currencyPair, String orderType, float amount, float limitPrice) {
		super();
		this.user = user;
		this.currencyPair = currencyPair;
		this.orderType = orderType;
		this.amount = amount;
		this.limitPrice = limitPrice;
	}

	public OrderDTO() {
		super();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getCurrencyPair() {
		return currencyPair;
	}

	public void setCurrencyPair(String currencyPair) {
		this.currencyPair = currencyPair;
	}

	public String getOrderType() {
		return this.orderType;
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

	public float getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(float limitPrice) {
		this.limitPrice = limitPrice;
	}

	@Override
	public String toString() {
		return "orderDTO [user=" + user + ", currencyPair=" + currencyPair + ", OrderType=" + orderType + ", amount="
				+ amount + ", limitPrice=" + limitPrice + "]";
	}
	
	
	
	

}
