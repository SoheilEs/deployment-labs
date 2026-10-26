package lab;

import java.net.InetAddress;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @GetMapping("/")
    public Map<String, String> home() throws Exception {

        String version =
                System.getenv().getOrDefault("APP_VERSION", "v1");

        return Map.of(
                "application", "java-deployment-lab",
                "version", version,
                "hostname",
                InetAddress.getLocalHost().getHostName()
        );
    }


    @GetMapping("/health")
    public Map<String, String> health() {

        return Map.of(
                "status", "healthy"
        );
    }


    @GetMapping("/slow")
    public Map<String, String> slow()
            throws InterruptedException {

        Thread.sleep(10000);

        return Map.of(
                "message", "slow request completed"
        );
    }


    @GetMapping("/memory")
    public String memory() {

        byte[] data = new byte[100 * 1024 * 1024];

        return "Allocated: " + data.length;
    }


    @GetMapping("/crash")
    public void crash() {

        System.exit(1);
    }
}