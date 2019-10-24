package com.yikun.springcloud.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Register： 服务注册
 * 当Eureka Client 向Eureka Server 注册时， Eureka Client 提供自身的元数据，比如IP 地址、端口、运行状况H1 标的Uri 、主页地址等信息。
 * Renew： 服务续约
 * Eureka Client 在默认的情况下会每隔30 秒发送一次心跳来进行服务续约。通过服务续约来告知Eureka Server该Eureka Client 仍然可用，没有出现故障。
 * 正常情况下，如果Eureka Server在90 秒内没有收到Eureka Client 的心跳， Eureka Server 会将Eureka Client 实例从注册列表中删除。
 * 注意：官方建议不要更改服务续约的间隔时间。
 * Fetch Registries： 获取服务注册列表信息
 * Eureka Client 从Eureka Server 获取服务注册表信息，井将其缓存在本地。Eureka Client 会使用服务注册列表信息查找其他服务的信息，从而进行远程调用。该注册列表信息定时（每
 * 30 秒） 更新一次，每次返回注册列表信息可能与Eureka Client 的缓存信息不同， Eureka Client会自己处理这些信息。如呆由于某种原因导致注册列表信息不能及时匹配， Eureka Client 会重
 * 新获取整个注册表信息。Eureka Server 缓存了所有的服务注册列表信息，并将整个注册列表以及每个应用程序的信息进行了压缩，压缩内容和没有压缩的内容完全相同。Eureka Client 和
 * Eureka Server 可以使用JSON 和XML 数据格式进行通信。在默认的情况下， Eureka Client 使用JSON 格式的方式来获取服务注册列表的信息。
 * Cancel: 服务下线
 * Eureka Client 在程序关闭时可以向Eureka Server 发送下线请求。发送请求后，该客户端的实例信息将从Eureka Server 的服务注册列表中删除。该下线请求不会自动完成，需要在程序
 * 关闭时调用以下代码：DiscoveryManager.getinstance().shutdownComponent();
 * Eviction: 服务剔除
 * 在默认情况下，当Eureka Client 连续90 秒没有向Eureka Server 发送服务续约（即心跳〉    时， Eureka Server 会将该服务实例从服务注册列表删除，即服务剔除。
 *
 * 构建高可用的 Eureka Server 集群
 * 新建 server01 和 server02 profiles 通过指定不同的服务端口号，开启两个服务注册中心，并让他们互相注册
 * eureka-client 可以向其中一个服务治理中心注册，因为服务治理中心处于同一个集群，所以注册信息会同步
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}
