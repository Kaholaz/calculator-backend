package no.ntnu.calculator.controller;

import lombok.Getter;
import lombok.Setter;
import no.ntnu.calculator.model.LogEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Getter
@Setter
public class LogDTO {
    Iterable<String> entries;

    public static  LogDTO from(Iterable<LogEntry> entries) {
        LogDTO dto = new LogDTO();
        Iterable<String> stringEntries = StreamSupport.stream(entries.spliterator(), false)
                .map(LogEntry::getLog)
                .collect(Collectors.toList());
        dto.setEntries(stringEntries);
        return dto;
    }
}
