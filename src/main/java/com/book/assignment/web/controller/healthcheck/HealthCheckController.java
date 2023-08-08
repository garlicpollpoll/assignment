package com.book.assignment.web.controller.healthcheck;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HealthCheckController {

    @GetMapping("/health_check")
    public String healthCheck() {
        log.info("health check");
        return "ok";
    }
}
