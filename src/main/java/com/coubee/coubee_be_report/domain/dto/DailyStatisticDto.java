package com.coubee.coubee_be_report.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class DailyStatisticDto {
    private Long storeId;

    private Long totalSales;

    private Long yesterdaySales;

    private double changeRate;
}