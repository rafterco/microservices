package com.raftercode.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepostory extends JpaRepository<Customer, Integer> {
}
