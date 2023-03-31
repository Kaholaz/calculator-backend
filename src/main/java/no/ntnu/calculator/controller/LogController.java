package no.ntnu.calculator.controller;

import lombok.RequiredArgsConstructor;
import no.ntnu.calculator.model.User;
import no.ntnu.calculator.security.AuthenticationService;
import no.ntnu.calculator.service.LogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
public class LogController {
    private final LogService logService;
    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<Object> getLog() {
        User user  = authenticationService.getLoggedInUser();
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(logService.getLogEntries(user.getUsername()));
    }
}
