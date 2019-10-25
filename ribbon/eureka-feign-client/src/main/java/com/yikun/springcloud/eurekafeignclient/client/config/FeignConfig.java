package com.yikun.springcloud.eurekafeignclient.client.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class FeignConfig {

    /**
     * 指定一个配置类，将会覆盖默认的Retry的Bean，更改了该FeignClient的请求失败重试的策略，
     * 重试间隔为100毫秒，最大重试时间为1秒，重试次数为5次
     *
     * @return
     */
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(100, SECONDS.toMillis(1), 5);
    }
}
