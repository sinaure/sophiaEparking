package com.sinaure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sinaure.config.model.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
	@Query("SELECT l FROM Log l WHERE l.plaque = ?1")
	List<Log> findByPlaque(String plaque);
}