package vip.ifmm.navicatweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@MapperScan(basePackages = {
        "vip.ifmm.navicatweb.dao"
})
//@PropertySource(value = "classpath:properties/*.properties", encoding = "utf-8")
public class NavicatWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(NavicatWebApplication.class, args);
    }

}
