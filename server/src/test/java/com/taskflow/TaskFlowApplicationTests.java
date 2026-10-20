package com.taskflow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class TaskFlowApplicationTests {

    @Test
    @DisplayName("Verify Spring Boot application context loads with SQL database")
    void contextLoads() {
        // Confirms that all beans, JPA entities, and SQL DataSource initialize successfully
    }
}
