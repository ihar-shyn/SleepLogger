package com.noom.interview.fullstack.sleep.repository;

import com.noom.interview.fullstack.sleep.model.SleepLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SleepLogRepository extends JpaRepository<SleepLog, Long> {
    Optional<SleepLog> findFirstByUserIdAndSleepDateOrderBySleepDateDesc(Long userId, LocalDate sleepDate);

    List<SleepLog> findAllByUserIdAndSleepDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}
