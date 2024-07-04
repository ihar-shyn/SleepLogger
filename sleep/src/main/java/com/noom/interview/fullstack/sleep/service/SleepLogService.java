package com.noom.interview.fullstack.sleep.service;

import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.repository.SleepLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SleepLogService {

    SleepLogRepository sleepLogRepository;

    @Autowired
    public SleepLogService(SleepLogRepository sleepLogRepository) {
        this.sleepLogRepository = sleepLogRepository;
    }

    public SleepLog createSleepLog(SleepLog sleepLog) {
        return sleepLogRepository.save(sleepLog);
    }

    public Optional<SleepLog> getLastNightSleep(Long userId) {
        LocalDate today = LocalDate.now();
        return sleepLogRepository.findFirstByUserIdAndSleepDateOrderBySleepDateDesc(userId, today);
    }

    public Map<String, String> getLast30DaysSleepLogs(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(30);
        List<SleepLog> rawData = sleepLogRepository.findAllByUserIdAndSleepDateBetween(userId, startDate, today);

        return calculateAverageData(rawData);
    }

    private Map<String, String> calculateAverageData(List<SleepLog> rawData) {
        Map<String, String> result = new HashMap<>();

        LocalDate rangeStart = rawData.stream().map(SleepLog::getSleepDate).min(LocalDate::compareTo).orElse(null);
        LocalDate rangeFinish = rawData.stream().map(SleepLog::getSleepDate).min(LocalDate::compareTo).orElse(null);
        long totalSleepTime = rawData.stream().mapToLong(SleepLog::getTotalTimeInBed).sum();
        Map<String, Long> moodData = rawData.stream().filter(entry -> entry.getUserFeeling() != null).map(entry -> entry.getUserFeeling().name())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        List<LocalTime> asleepTimeData = rawData.stream().map(SleepLog::getTimeInBedStart).collect(Collectors.toList());
        List<LocalTime> wakeupTimeData = rawData.stream().map(SleepLog::getTimeInBedEnd).collect(Collectors.toList());

        result.put("range_start", rangeStart != null ? rangeStart.toString() : "no_data");
        result.put("range_finish", rangeFinish != null ? rangeFinish.toString() : "no_data");
        result.put("total_sleep_time", String.valueOf(totalSleepTime));
        result.put("mood_data", moodData.toString());
        result.put("average_start", asleepTimeData.isEmpty() ? "no_data" : asleepTimeData.get(asleepTimeData.size()/2).toString());
        result.put("average_finish", wakeupTimeData.isEmpty() ? "no_data" : wakeupTimeData.get(wakeupTimeData.size()/2).toString());

        return result;
    }
}
