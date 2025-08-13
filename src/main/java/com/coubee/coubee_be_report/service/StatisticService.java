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
import java.time.*;
import java.time.temporal.TemporalAdjusters;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticService {
    private final StatisticRepository statisticRepository;

    @Transactional(readOnly = true)
    public DailyStatisticDto dailyStatistic(Long storeId){
        ZoneId seoulZone = ZoneId.of("Asia/Seoul");
//       TODAY
        ZonedDateTime startDay = ZonedDateTime.now(seoulZone).toLocalDate().atStartOfDay(seoulZone);
        ZonedDateTime endDay = ZonedDateTime.now(seoulZone);


//       YESTERDAY
        ZonedDateTime yesterdayStart = startDay.minusDays(1);
        ZonedDateTime yesterdayEnd = endDay.minusDays(1);

//        Amount
        Long dailyAmount =  statisticRepository.dailyStatisticSql(storeId, startDay, endDay);
        Long yesterdayAmount = statisticRepository.dailyStatisticSql(storeId, yesterdayStart, yesterdayEnd);

        log.info("오늘 매출(dailyAmount): {}", dailyAmount);
        log.info("어제 매출(yesterdayAmount): {}", yesterdayAmount);

//        ChangeRate
        double changeRate;
        if (yesterdayAmount == 0){
            log.info("일로 드감", yesterdayAmount);
//            전날 매출이 0 이고 당일 매출이 0 일 때
            if(dailyAmount == 0) changeRate = 0.0;
//            전날 매출이 0 이고 당일 매출이 0 초과일 때
            else changeRate = 100.0;
        }
        else{
//            전날 매출이 0초과이고 당일 매출이 0일 때
            if (dailyAmount == 0){
                changeRate = -100.0;
            }
//            전날 매출이 0초과이고 당일 매출이 0초과일 때
            else{
        double rawChangeRate = ((double)(dailyAmount - yesterdayAmount) / yesterdayAmount) * 100;
        String stringChangeRate = Double.toString(rawChangeRate);
        BigDecimal decimalChangeRate = new BigDecimal(stringChangeRate);
        decimalChangeRate = decimalChangeRate.setScale(2, RoundingMode.HALF_UP);
        changeRate = decimalChangeRate.doubleValue();
            }
        }

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
        double changeRate;
        if (lastWeekAmount == 0){
//            전주 매출이 0 이고 이번주 매출이 0 일 때
            if(thisWeekAmount == 0) changeRate = 0.0;
//            전주 매출이 0 이고 이번주 매출이 0 초과일 때
            else changeRate = 100.0;
        }
        else{
//            전주 매출이 0초과이고 이번주 매출이 0일 때
            if (thisWeekAmount == 0){
                changeRate = -100.0;
            }
//            전주 매출이 0초과이고 이번주 매출이 0초과일 때
            else{
                double rawChangeRate = ((double)(thisWeekAmount - lastWeekAmount) / lastWeekAmount) * 100;
                String stringChangeRate = Double.toString(rawChangeRate);
                BigDecimal decimalChangeRate = new BigDecimal(stringChangeRate);
                decimalChangeRate = decimalChangeRate.setScale(2, RoundingMode.HALF_UP);
                changeRate = decimalChangeRate.doubleValue();
            }
        }

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
        double changeRate;
        if (lastMonthAmount == 0){
//            전달 매출이 0 이고 이번달 매출이 0 일 때
            if(thisMonthAmount == 0) changeRate = 0.0;
//            전달 매출이 0 이고 이번달 매출이 0 초과일 때
            else changeRate = 100.0;
        }
        else{
//            전달 매출이 0초과이고 이번달 매출이 0일 때
            if (thisMonthAmount == 0){
                changeRate = -100.0;
            }
//            전달 매출이 0초과이고 이번달 매출이 0초과일 때
            else{
                double rawChangeRate = ((double)(thisMonthAmount - lastMonthAmount) / lastMonthAmount) * 100;
                String stringChangeRate = Double.toString(rawChangeRate);
                BigDecimal decimalChangeRate = new BigDecimal(stringChangeRate);
                decimalChangeRate = decimalChangeRate.setScale(2, RoundingMode.HALF_UP);
                changeRate = decimalChangeRate.doubleValue();
            }
        }

        return MonthlyStatisticDto.builder()
                .storeId(storeId)
                .totalSales(thisMonthAmount)
                .changeRate(changeRate)
                .build();
    }
}
