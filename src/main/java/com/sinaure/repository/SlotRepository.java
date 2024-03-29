package com.sinaure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sinaure.config.model.Slot;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {

}