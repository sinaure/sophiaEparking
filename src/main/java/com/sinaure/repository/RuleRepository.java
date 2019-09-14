package com.sinaure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sinaure.config.model.Parking;
import com.sinaure.config.model.Rule;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {

}