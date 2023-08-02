package com.thardal.bankapp.log.dao;

import com.thardal.bankapp.log.entity.LogDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogDetailsDao extends JpaRepository<LogDetails,Long> {
}
