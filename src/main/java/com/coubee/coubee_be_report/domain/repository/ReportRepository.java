package com.coubee.coubee_be_report.domain.repository;

import com.coubee.coubee_be_report.domain.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
