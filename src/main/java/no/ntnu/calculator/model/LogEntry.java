package no.ntnu.calculator.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LogEntry {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String log;

    @ManyToOne
    User user;
}
