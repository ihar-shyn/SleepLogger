package com.noom.interview.fullstack.sleep.service;

import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.repository.SleepLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

public class SleepLogServiceTest {

    @Mock
    private SleepLogRepository repository;

    @InjectMocks
    private SleepLogService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetLastNightSleep() {
        Long userId = 1L;
        LocalDate sleepDate = LocalDate.now();
        SleepLog sleepLog = new SleepLog();
        sleepLog.setUserId(userId);
        sleepLog.setSleepDate(sleepDate);

        given(repository.findFirstByUserIdAndSleepDateOrderBySleepDateDesc(anyLong(), any(LocalDate.class)))
                .willReturn(Optional.of(sleepLog));

        Optional<SleepLog> result = service.getLastNightSleep(userId);
        assertThat(result).isNotNull();
        assertThat(result.get().getUserId()).isEqualTo(userId);
        assertThat(result.get().getSleepDate()).isEqualTo(sleepDate);
    }

    @Test
    public void testGetLast30DaysSleepLogs() {
        Long userId = 1L;
        LocalDate today = LocalDate.now();
        SleepLog sleepLog1 = new SleepLog();
        sleepLog1.setUserId(userId);
        sleepLog1.setSleepDate(today);
        sleepLog1.setTimeInBedEnd(LocalTime.of(8,10));
        sleepLog1.setTimeInBedStart(LocalTime.of(20, 10));
        sleepLog1.setTotalTimeInBed(720L);
        sleepLog1.setUserFeeling(SleepLog.Feeling.GOOD);

        SleepLog sleepLog2 = new SleepLog();
        sleepLog2.setUserId(userId);
        sleepLog2.setSleepDate(today.minusDays(1));
        sleepLog2.setTimeInBedEnd(LocalTime.of(9,10));
        sleepLog2.setTimeInBedStart(LocalTime.of(22, 10));
        sleepLog2.setTotalTimeInBed(660L);

        given(repository.findAllByUserIdAndSleepDateBetween(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .willReturn(List.of(sleepLog1, sleepLog2));

        Map<String, String> result = service.getLast30DaysSleepLogs(userId);
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(6);
        assertThat(result.get("range_start")).isEqualTo(today.minusDays(1).toString());
        assertThat(result.get("range_finish")).isEqualTo(today.minusDays(1).toString());
        assertThat(result.get("total_sleep_time")).isEqualTo("1380");

    }
}
