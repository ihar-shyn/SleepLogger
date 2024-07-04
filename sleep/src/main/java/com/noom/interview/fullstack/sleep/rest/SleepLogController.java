package com.noom.interview.fullstack.sleep.rest;

import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.service.SleepLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sleep")
public class SleepLogController {

    private final SleepLogService sleepLogService;

    @Autowired
    public SleepLogController(SleepLogService sleepLogService) {
        this.sleepLogService = sleepLogService;
    }

    @PostMapping("/log")
    public SleepLog createSleepLog(@RequestBody SleepLog sleepLog) {
        return sleepLogService.createSleepLog(sleepLog);
    }

    @GetMapping("/last-night")
    public ResponseEntity<SleepLog> getLastNightSleep(@RequestParam Long userId) {
        return ResponseEntity.of(sleepLogService.getLastNightSleep(userId));
    }

    @GetMapping("/last-30-days")
    public Map<String, String> getLast30DaysSleepLogs(@RequestParam Long userId) {
        return sleepLogService.getLast30DaysSleepLogs(userId);
    }
}
