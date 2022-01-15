package fr.eql.matchingEngine.dto.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Payment {
	

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int id;
	
	private String currencyTicker;
	private String userEmail;
	private double montant;
	
	@ManyToOne ()
	@JoinColumn(name = "ordre_id")
	private Ordre ordre;
	

	public Payment() {
		super();
	}


	public Payment(String currencyTicker, String userEmail, double montant, Ordre ordre) {
		super();
		this.currencyTicker = currencyTicker;
		this.userEmail = userEmail;
		this.montant = montant;
		this.ordre = ordre;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCurrencyTicker() {
		return currencyTicker;
	}


	public void setCurrencyTicker(String currencyTicker) {
		this.currencyTicker = currencyTicker;
	}


	public String getUserEmail() {
		return userEmail;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	public double getMontant() {
		return montant;
	}


	public void setMontant(double montant) {
		this.montant = montant;
	}


	public Ordre getOrdre() {
		return ordre;
	}


	public void setOrdre(Ordre ordre) {
		this.ordre = ordre;
	}
	
}