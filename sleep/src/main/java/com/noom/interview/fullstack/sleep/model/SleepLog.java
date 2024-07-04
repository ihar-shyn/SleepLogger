package com.noom.interview.fullstack.sleep.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class SleepLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private LocalDate sleepDate;
    private LocalTime timeInBedStart;
    private LocalTime timeInBedEnd;
    private Long totalTimeInBed;

    @Enumerated(EnumType.STRING)
    private Feeling userFeeling;

    public enum Feeling {
        BAD, OK, GOOD
    }

    public SleepLog(Long id, Long userId, LocalDate sleepDate, LocalTime timeInBedStart, LocalTime timeInBedEnd, Long totalTimeInBed, Feeling userFeeling) {
        this.id = id;
        this.userId = userId;
        this.sleepDate = sleepDate;
        this.timeInBedStart = timeInBedStart;
        this.timeInBedEnd = timeInBedEnd;
        this.totalTimeInBed = totalTimeInBed;
        this.userFeeling = userFeeling;
    }

    public SleepLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getSleepDate() {
        return sleepDate;
    }

    public void setSleepDate(LocalDate sleepDate) {
        this.sleepDate = sleepDate;
    }

    public LocalTime getTimeInBedStart() {
        return timeInBedStart;
    }

    public void setTimeInBedStart(LocalTime timeInBedStart) {
        this.timeInBedStart = timeInBedStart;
    }

    public LocalTime getTimeInBedEnd() {
        return timeInBedEnd;
    }

    public void setTimeInBedEnd(LocalTime timeInBedEnd) {
        this.timeInBedEnd = timeInBedEnd;
    }

    public Long getTotalTimeInBed() {
        return totalTimeInBed;
    }

    public void setTotalTimeInBed(Long totalTimeInBedMinutes) {
        this.totalTimeInBed = totalTimeInBedMinutes;
    }

    public Feeling getUserFeeling() {
        return userFeeling;
    }

    public void setUserFeeling(Feeling userFeeling) {
        this.userFeeling = userFeeling;
    }
}
