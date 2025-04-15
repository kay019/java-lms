package nextstep.application.controller;

import nextstep.application.service.SessionRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students/{studentId}/sessions")
public class SessionController {
    private final SessionRegistrationService sessionRegistrationService;

    public SessionController(SessionRegistrationService sessionRegistrationService) {
        this.sessionRegistrationService = sessionRegistrationService;
    }

    @PostMapping("/{sessionId}/register")
    public ResponseEntity<String> register(
            @PathVariable Long studentId,
            @PathVariable Long sessionId
    ) {
        return sessionRegistrationService.register(studentId, sessionId);
    }
}
