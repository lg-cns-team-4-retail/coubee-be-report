package com.coubee.coubee_be_report.api.open;


import com.coubee.coubee_be_report.common.dto.ApiResponseDto;
import com.coubee.coubee_be_report.common.web.context.GatewayRequestHeaderUtils;
import com.coubee.coubee_be_report.domain.dto.DailyStatisticDto;
import com.coubee.coubee_be_report.domain.dto.MonthlyStatisticDto;
import com.coubee.coubee_be_report.domain.dto.WeeklyStatisticDto;
import com.coubee.coubee_be_report.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/reports/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping(value = "/sales/daily")
    public ApiResponseDto<DailyStatisticDto> viewDailyStatistic(@RequestParam Long storeId){
        String userId = GatewayRequestHeaderUtils.getUserIdOrThrowException();
        DailyStatisticDto dailyStatistic = statisticService.dailyStatistic(storeId);
        return ApiResponseDto.createOk(dailyStatistic);
    }
    @GetMapping(value = "/sales/weekly")
    public ApiResponseDto<WeeklyStatisticDto> viewWeeklyStatistic(@RequestParam Long storeId){
        String userId = GatewayRequestHeaderUtils.getUserIdOrThrowException();
        WeeklyStatisticDto weeklyStatistic = statisticService.weeklyStatistic(storeId);
        return ApiResponseDto.createOk(weeklyStatistic);
    }
    @GetMapping(value = "/sales/monthly")
    public ApiResponseDto<MonthlyStatisticDto> viewMonthlyStatistic(@RequestParam Long storeId){
        String userId = GatewayRequestHeaderUtils.getUserIdOrThrowException();
        MonthlyStatisticDto monthlyStatistic = statisticService.monthlyStatistic(storeId);
        return ApiResponseDto.createOk(monthlyStatistic);
    }

}
