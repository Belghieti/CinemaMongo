package com.demo.backend.repository;

import com.demo.backend.model.Box;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxRepository extends JpaRepository<Box, Long> {
}
