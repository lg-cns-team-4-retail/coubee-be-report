package com.coubee.coubee_be_report;

import com.coubee.coubee_be_report.domain.dto.DailyStatisticDto;
import com.coubee.coubee_be_report.domain.dto.MonthlyStatisticDto;
import com.coubee.coubee_be_report.domain.dto.WeeklyStatisticDto;
import com.coubee.coubee_be_report.repository.StatisticRepository;
import com.coubee.coubee_be_report.service.StatisticService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Statistic 서비스로")
public class StatisticTest {
    @InjectMocks
    private StatisticService statisticService;

    @Mock
    private StatisticRepository statisticRepository;

    @Test
    @DisplayName("일별 매출과 증감율을 알 수 있다")
    void getDailyStatistic(){
//        given
        Long storeId = 1L;
        Long yesterdayAmount = 300000L;
        Long todayAmount = 200000L;
//        when
        when(statisticRepository.dailyStatisticSql(eq(storeId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(todayAmount)
                .thenReturn(yesterdayAmount);
        DailyStatisticDto response = statisticService.dailyStatistic(storeId);

//        then
        System.out.println(response.getTotalSales());
        System.out.println(response.getChangeRate());
    }

    @Test
    @DisplayName("주별 매출과 증감율을 알 수 있다")
    void getWeeklyStatistic(){
//        given
        Long storeId = 1L;
        Long thisWeekAmount = 1235948L;
        Long lastWeekAmount = 1500000L;
//        when
        when(statisticRepository.weeklyStatisticSql(eq(storeId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(thisWeekAmount)
                .thenReturn(lastWeekAmount);
        WeeklyStatisticDto response = statisticService.weeklyStatistic(storeId);
//        then
        System.out.println(response.getTotalSales());
        System.out.println(response.getChangeRate());
    }

    @Test
    @DisplayName("월별 매출과 증감율을 알 수 있다")
    void getMonthlyStatistic(){
//        given
        Long storeId = 1L;
        Long thisMonthAmount = 36788152L;
        Long lastMonthAmount = 31548756L;
//        when
        when(statisticRepository.monthlyStatisticSql(eq(storeId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(thisMonthAmount)
                .thenReturn(lastMonthAmount);
        MonthlyStatisticDto response = statisticService.monthlyStatistic(storeId);
//        then
        System.out.println(response.getTotalSales());
        System.out.println(response.getChangeRate());
    }
}
