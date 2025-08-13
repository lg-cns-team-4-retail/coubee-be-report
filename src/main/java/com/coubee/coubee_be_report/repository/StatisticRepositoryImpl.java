package com.coubee.coubee_be_report.repository;

import com.coubee.coubee_be_report.constant.OrderStatusConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class StatisticRepositoryImpl implements StatisticRepository{
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long dailyStatisticSql(Long storeId, LocalDateTime startDay, LocalDateTime endDay) {
        String sql = "SELECT COALESCE(SUM(o.total_amount),0) AS daily_amount " +
                "FROM orders o WHERE o.store_id = ? AND o.updated_at >= ? AND o.updated_at < ?  AND o.status = ?";
        java.sql.Timestamp startTimestamp = java.sql.Timestamp.valueOf(startDay);
        java.sql.Timestamp endTimestamp = java.sql.Timestamp.valueOf(endDay);
        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                storeId,
                startTimestamp,
                endTimestamp,
                OrderStatusConstant.Received
        );
    }

    @Override
    public Long weeklyStatisticSql(Long storeId, LocalDateTime startWeek, LocalDateTime endWeek) {
        String sql = "SELECT COALESCE(SUM(o.total_amount),0) AS daily_amount " +
                "FROM orders o WHERE o.store_id = ? AND o.updated_at >= ? AND o.updated_at < ?  AND o.status = ?";
        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                storeId,
                startWeek,
                endWeek,
                OrderStatusConstant.Received
        );
    }


    @Override
    public Long monthlyStatisticSql(Long storeId,LocalDateTime startMonth, LocalDateTime endMonth) {
        String sql = "SELECT COALESCE(SUM(o.total_amount),0) AS daily_amount " +
                "FROM orders o WHERE o.store_id = ? AND o.updated_at >= ? AND o.updated_at < ?  AND o.status = ?";
        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                storeId,
                startMonth,
                endMonth,
                OrderStatusConstant.Received
        );
    }
}
