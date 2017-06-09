package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.WeightedResponseTimeRule;

/**
 * 客户端额外配置，设置之后会覆盖客户端默认配置
 * @author hushuang
 * @email hd1611756908@gmail.com
 * @Time  2017年6月9日 下午7:53:23
 * @Description：
 */
public class RibbonConfiguration {
	@Autowired
	IClientConfig ribbonClientConfig;
	@Bean
	public IPing ribbonPing(IClientConfig config) {
		return new PingUrl();//检测服务器状态状态
	}
	//负载均衡策略设置（权重，随机，轮询等等）
	@Bean
	public IRule ribbonRule(IClientConfig config) {
		return new WeightedResponseTimeRule();
	}

}
