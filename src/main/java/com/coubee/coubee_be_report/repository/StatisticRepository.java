package com.coubee.coubee_be_report.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface StatisticRepository {
    Integer dailyStatisticSql(Long storeId, LocalDateTime startDay, LocalDateTime endDay);
    Long weeklyStatisticSql(Long storeId, LocalDateTime startWeek, LocalDateTime endWeek);
    Long monthlyStatisticSql(Long storeId, LocalDateTime startMonth, LocalDateTime endMonth);
}