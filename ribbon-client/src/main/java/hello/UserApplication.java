package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
//name：配置文件中myService为前缀,RibbonConfiguration为客户端额外配置，不能进行全局扫描，只适用于此客户端
@RibbonClient(name="myService",configuration=RibbonConfiguration.class)
public class UserApplication {
	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	@Autowired
	RestTemplate restTemplate;
	@RequestMapping("/hi")
	public String hi(@RequestParam(value = "name", defaultValue = "Artaban") String name) {
		//http://myService/greeting  ：SpringCloud的一个特性将url地址换成应用名称，此工程因为没有使用注册中心但是myService前缀设置了地址。所以此处设置myService
		String greeting = this.restTemplate.getForObject("http://myService/greeting", String.class);
		return String.format("%s, %s!", greeting, name);
	}
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
}
