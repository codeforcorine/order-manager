import com.technical.evaluation.orders.OrderManagementApiApplication;
import org.springframework.boot.SpringApplication;
import org.testcontainers.utility.TestcontainersConfiguration;

public class TestOrderManagementApiApplication {

    public static void main(String[] args) {
        SpringApplication.from(OrderManagementApiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
