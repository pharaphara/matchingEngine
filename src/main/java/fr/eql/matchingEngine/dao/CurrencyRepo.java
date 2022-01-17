package fr.eql.matchingEngine.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eql.matchingEngine.dto.entity.Currency;

public interface CurrencyRepo extends JpaRepository<Currency, String>{

}
