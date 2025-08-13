package com.coubee.coubee_be_report.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public interface StatisticRepository {
    Long dailyStatisticSql(Long storeId, ZonedDateTime startDay, ZonedDateTime endDay);
    Long weeklyStatisticSql(Long storeId, LocalDateTime startWeek, LocalDateTime endWeek);
    Long monthlyStatisticSql(Long storeId, LocalDateTime startMonth, LocalDateTime endMonth);
}