package com.montrackjpa.JPASpringBootExercises.currencies.repository;


import com.montrackjpa.JPASpringBootExercises.currencies.entitity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency,Long> {
}
