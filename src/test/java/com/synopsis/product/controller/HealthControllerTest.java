package com.synopsis.product.controller;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HealthControllerTest {

    private final HealthController controller = new HealthController();

    @Test
    void healthReturnsUpStatus() {
        Map<String, String> result = controller.health();

        assertThat(result).containsEntry("status", "UP");
    }
}
