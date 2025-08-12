package com.coubee.coubee_be_report.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WeeklyStatisticDto {
    private Long storeId;

    private Long totalSales;

    private double changeRate;
}
