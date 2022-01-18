package fr.eql.matchingEngine.dto.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import fr.eql.matchingEngine.dto.constant.TradingPair;
import fr.eql.matchingEngine.dto.model.PriceDto;
import fr.eql.matchingEngine.services.servicesinterface.PriceServices;

@Entity
public class Currency {
	
	
	@Id
	private String ticker;
	private String name;
	
	private double supply;
	private double price;
	
	public Currency() {
		super();
	}

	public Currency(String ticker, String name, double supply, double price) {
		super();
		this.ticker = ticker;
		this.name = name;
		this.supply = supply;
		this.price = price;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSupply() {
		return supply;
	}

	public void setSupply(double supply) {
		this.supply = supply;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Currency [ticker=" + ticker + ", name=" + name + ", supply=" + supply + ", price=" + price + "]";
	}
	
}

	