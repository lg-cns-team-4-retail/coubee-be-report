package com.coubee.coubee_be_report.service;

import com.coubee.coubee_be_report.domain.dto.DailyStatisticDto;
import com.coubee.coubee_be_report.domain.dto.MonthlyStatisticDto;
import com.coubee.coubee_be_report.domain.dto.WeeklyStatisticDto;
import com.coubee.coubee_be_report.repository.StatisticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticService {
    private final StatisticRepository statisticRepository;

    @Transactional(readOnly = true)
    public DailyStatisticDto dailyStatistic(Long storeId){
//       TODAY
        LocalDateTime startDay = LocalDate.now().atStartOfDay();
        LocalDateTime endDay = LocalDateTime.now();

//       YESTERDAY
        LocalDateTime yesterdayStart = startDay.minusDays(1);
        LocalDateTime yesterdayEnd = endDay.minusDays(1);

//        Amount
        Long dailyAmount =  statisticRepository.dailyStatisticSql(storeId, startDay, endDay);
        Long yesterdayAmount = statisticRepository.dailyStatisticSql(storeId, yesterdayStart, yesterdayEnd);

//        ChangeRate
        double rawChangeRate = ((double)(dailyAmount - yesterdayAmount) / yesterdayAmount) * 100;
        String stringChangeRate = Double.toString(rawChangeRate);
        BigDecimal decimalChangeRate = new BigDecimal(stringChangeRate);
        decimalChangeRate = decimalChangeRate.setScale(2, RoundingMode.HALF_UP);
        double changeRate = decimalChangeRate.doubleValue();

        return DailyStatisticDto.builder()
                .storeId(storeId)
                .totalSales(dailyAmount)
                .changeRate(changeRate)
                .build();
    }

    @Transactional(readOnly = true)
    public WeeklyStatisticDto weeklyStatistic(Long storeId){
//       This Week
        LocalDateTime thisWeekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
        LocalDateTime thisWeekEnd = LocalDateTime.now();

//       Last Week
        LocalDateTime lastWeekStart = thisWeekStart.minusWeeks(1);
        LocalDateTime lastWeekEnd = thisWeekEnd.minusWeeks(1);

//        Amount
        Long thisWeekAmount = statisticRepository.weeklyStatisticSql(storeId, thisWeekStart, thisWeekEnd);
        Long lastWeekAmount = statisticRepository.weeklyStatisticSql(storeId, lastWeekStart, lastWeekEnd);

//        ChangeRate
        double rawChangeRate = ((double)(thisWeekAmount - lastWeekAmount) / lastWeekAmount) * 100;
        String stringChangeRate = Double.toString(rawChangeRate);
        BigDecimal decimalChangeRate = new BigDecimal(stringChangeRate);
        decimalChangeRate = decimalChangeRate.setScale(2, RoundingMode.HALF_UP);
        double changeRate = decimalChangeRate.doubleValue();

        return WeeklyStatisticDto.builder()
                .storeId(storeId)
                .totalSales(thisWeekAmount)
                .changeRate(changeRate)
                .build();
    }

    @Transactional(readOnly = true)
    public MonthlyStatisticDto monthlyStatistic(Long storeId){
//        ThisMonth
        LocalDateTime thisMonthStart = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime thisMonthEnd = LocalDateTime.now();

//        LastMonth
        LocalDateTime lastMonthStart = thisMonthStart.minusMonths(1);
        LocalDateTime lasMonthEnd = thisMonthEnd.minusMonths(1);

//        Amount
        Long thisMonthAmount = statisticRepository.monthlyStatisticSql(storeId, thisMonthStart, thisMonthEnd);
        Long lastMonthAmount = statisticRepository.monthlyStatisticSql(storeId, lastMonthStart, lasMonthEnd);

//        ChangeRate
        double rawChangeRate = ((double)(thisMonthAmount - lastMonthAmount) / lastMonthAmount) * 100;
        String stringChangeRate = Double.toString(rawChangeRate);
        BigDecimal decimalChangeRate = new BigDecimal(stringChangeRate);
        decimalChangeRate = decimalChangeRate.setScale(2, RoundingMode.HALF_UP);
        double changeRate = decimalChangeRate.doubleValue();

        return MonthlyStatisticDto.builder()
                .storeId(storeId)
                .totalSales(thisMonthAmount)
                .changeRate(changeRate)
                .build();
    }
}
