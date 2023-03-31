package no.ntnu.calculator.controller;

import lombok.RequiredArgsConstructor;
import no.ntnu.calculator.model.User;
import no.ntnu.calculator.security.AuthenticationService;
import no.ntnu.calculator.service.CalculateService;
import no.ntnu.calculator.service.LogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calculate")
@RequiredArgsConstructor
public class CalculateController {
    private final CalculateService calculateService;
    private final LogService logService;
    private final AuthenticationService authenticationService;

    @PostMapping
    private ResponseEntity<Object> calculate(@RequestBody CalculateDTO calculateDTO) {
        User user = authenticationService.getLoggedInUser();
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        double result;
        try {
            result = calculateService
                    .calculate(calculateDTO.getFirstNumber(), calculateDTO.getSecondNumber(), calculateDTO.getOperator().charAt(0));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        String logString = String
                .format("%f %s %f = %f", calculateDTO.getFirstNumber(), calculateDTO.getOperator(), calculateDTO.getSecondNumber(), result);
        logService.addLogEntry(logString, user.getUsername());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/log")
    private ResponseEntity<Object> getLog() {
        User user = authenticationService.getLoggedInUser();
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(LogDTO.from(logService.getLogEntries(user.getUsername())));
    }
}
