package cn.junoyi.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动服务端
 *
 * @author Fan
 */
@SpringBootApplication(scanBasePackages= {"cn.junoyi"})
public class JunoYiServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(JunoYiServerApplication.class);
    }
}