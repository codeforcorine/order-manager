import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.utility.TestcontainersConfiguration;

@SpringBootTest(classes = com.technical.evaluation.orders.OrderManagementApiApplication.class)
@Import(TestcontainersConfiguration.class)
class OrderManagementApiApplicationTests {

    @Test
    void contextLoads() {
    }

}
