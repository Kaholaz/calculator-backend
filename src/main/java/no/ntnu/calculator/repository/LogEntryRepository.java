package no.ntnu.calculator.repository;

import no.ntnu.calculator.model.LogEntry;
import no.ntnu.calculator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
    List<LogEntry> findAllByUser(User user);
}
