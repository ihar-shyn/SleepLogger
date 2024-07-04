package com.noom.interview.fullstack.sleep.repository;

import com.noom.interview.fullstack.sleep.model.SleepLog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SleepLogRepositoryTest {

    @Autowired
    private SleepLogRepository repository;
    private final LocalDate today = LocalDate.now();

    @BeforeEach
    public void prefillRepository() {
        SleepLog sleepLog1 = new SleepLog();
        sleepLog1.setUserId(1L);
        sleepLog1.setSleepDate(today);
        repository.save(sleepLog1);

        SleepLog sleepLog2 = new SleepLog();
        sleepLog2.setUserId(1L);
        sleepLog2.setSleepDate(today.minusDays(1));
        repository.save(sleepLog2);


        SleepLog sleepLog3 = new SleepLog();
        sleepLog3.setUserId(2L);
        sleepLog3.setSleepDate(today);
        repository.save(sleepLog3);
    }

    @Test
    public void testFindFirstByUserIdAndSleepDateOrderBySleepDateDesc() {
        Optional<SleepLog> result = repository.findFirstByUserIdAndSleepDateOrderBySleepDateDesc(1L, today);
        assertThat(result).isPresent();
        assertThat(result.get().getUserId()).isEqualTo(1L);
        assertThat(result.get().getSleepDate()).isEqualTo(today);
    }

    @Test
    public void testFindAllByUserIdAndSleepDateBetween() {
        List<SleepLog> result = repository.findAllByUserIdAndSleepDateBetween(1L, today.minusDays(10), today);
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(1)).isNotNull();
        assertThat(result.get(0).getUserId()).isEqualTo(1L);
        assertThat(result.get(0).getUserId()).isEqualTo(1L);
    }

    @AfterEach
    public void cleanAll() {
        repository.deleteAll();
    }
}
