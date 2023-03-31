package no.ntnu.calculator.service;

import lombok.RequiredArgsConstructor;
import no.ntnu.calculator.model.LogEntry;
import no.ntnu.calculator.model.User;
import no.ntnu.calculator.repository.LogEntryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogEntryRepository logEntryRepository;
    private final UserService userService;
    public void addLogEntry(String entry, String username) {
        User user = userService.getUser(username);
        LogEntry logEntry = new LogEntry();
        logEntry.setLog(entry);
        logEntry.setUser(user);
        logEntryRepository.save(logEntry);
    }

    public Iterable<LogEntry> getLogEntries(String username) {
        User user = userService.getUser(username);
        return logEntryRepository.findAllByUser(user);
    }
}
