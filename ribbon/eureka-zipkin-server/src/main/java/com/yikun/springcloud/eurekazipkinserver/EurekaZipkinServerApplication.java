package com.yikun.springcloud.eurekazipkinserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Spring Cloud Sleuth
 * 微服务架构是一个分布式架构，一个请求可能需要调用很多个服务，而内部服务的调用复杂度决定了问题难以定位。所以必须实现分布式链路追踪
 * ，去跟进一个请求到底有哪些服务参与，参与的顺序又是怎样的，从而达到每个请求的步骤清晰可见，出了问题能快速定位的目的。
 *
 * 官网 https://zipkin.io/
 *
 * 先介绍下有关的专业术语
 * Zipkin主要包括四个模块 
 * - Collector     接收或收集各应用传输的数据 
 * - Storage       存储接受或收集过来的数据，当前支持Memory，MySQL，Cassandra，ElasticSearch等，默认存储在内存中。 
 * - API（Query）   负责查询Storage中存储的数据，提供简单的JSON API获取数据，主要提供给web UI使用 
 * - Web           提供简单的web界面
 *
 * Span：基本工作单元，例如，在一个新建的span中发送一个RPC等同于发送一个回应请求给RPC，span通过一个64位ID唯一标识，trace以另一个64位ID表示，span还有其他数据信息，比如摘要、时间戳事件、关键值注释(tags)、span的ID、以及进度ID(通常是IP地址)
 * Trace：一系列spans组成的一个树状结构，例如，如果你正在跑一个分布式服务工程，你可能需要创建一个trace。
 * Annotation：用来及时记录一个事件的存在，一些核心annotations用来定义一个请求的开始和结束
 * cs  - Client Sent -客户端发起一个请求，这个annotation描述了这个span的开始
 * sr  - Server Received -服务端获得请求并准备开始处理它，如果将其sr减去cs时间戳便可得到网络延迟
 * ss  - Server Sent -注解表明请求处理的完成(当请求返回客户端)，如果ss减去sr时间戳便可得到服务端需要的处理请求时间
 * cr  - Client Received -表明span的结束，客户端成功接收到服务端的回复，如果cr减去cs时间戳便可得到客户端从服务端获取回复的所有所需时间
 * cs:Client Send，客户端发起请求；
 * sr:Server Receive，服务器接受请求，开始处理；
 * ss:Server Send，服务器完成处理，给客户端应答；
 * cr:Client Receive，客户端接受应答从服务器；
 *
 *
 * 本演示实例 Zipkin 链路追踪组件
 * 在Spring Boot 2.0之前，我们需要 新建 eureka-zipkin-server 工程 作为链路追踪服务中心，负责存储链路数据。
 * Spring Boot 2.0之后，使用EnableZipkinServer创建自定义的zipkin服务器已经被废弃，将无法启动 !!!
 * 我们介绍 官网 给我们介绍的启动方式
 *
 * 查看官网 https://zipkin.io/pages/quickstart.html 提供了三种启动方式
 * Regardless of how you start Zipkin, browse to http://your_host:9411 to find traces!
 * 方法一 Docker  我们需要安装 docker
 * The Docker Zipkin project is able to build docker images, provide scripts and a docker-compose.yml for launching pre-built images. The quickest start is to run the latest image directly:
 * docker run -d -p 9411:9411 openzipkin/zipkin
 * 方法二 Java
 * If you have Java 8 or higher installed, the quickest way to get started is to fetch the latest release as a self-contained executable jar:
 * curl -sSL https://zipkin.io/quickstart.sh | bash -s
 * java -jar zipkin.jar
 *
 * 另外 还需要改造 eureka-zuul-client路由网关服务  eureka-client,eureka-ribbon-client,eureka-feign-client 工程，使他们能够上传链路数据
 * 需要添加 spring-cloud-starter-zipkin 的依赖 ，并配置 spring.zipkin.base-uri   spring.sleuth.sampler.probability
 * spring-cloud-starter-zipkin中已包含了spring-cloud-starter-sleuth与spring-cloud-sleuth-zipkin的依赖，所以使用sleuth-zipkin的SpringCloud项目只需添加spring-cloud-starter-zipkin依赖即可
 *
 * **************************************
 *
 * 使用 RabbitMQ 传输链路数据
 * zipkin 默认是通过http 上传，可以用 RabbitMQ 来传输链路数据
 * https://github.com/openzipkin/zipkin/tree/master/zipkin-collector/rabbitmq
 *
 * 添加依赖 spring-cloud-stream-binder-rabbit 包含了 spring-boot-starter-amqp
 * The RabbitMQ collector will be enabled when the addresses or uri for the RabbitMQ server(s) is set.
 * Example usage:
 * 启动 RABBIT_ADDRESSES=localhost java -jar zipkin.jar
 *
 * Configuration
 * The following configuration can be set for the RabbitMQ Collector.
 *
 * Property	Environment Variable	Description
 * zipkin.collector.rabbitmq.concurrency	RABBIT_CONCURRENCY	Number of concurrent consumers. Defaults to 1
 * zipkin.collector.rabbitmq.connection-timeout	RABBIT_CONNECTION_TIMEOUT	Milliseconds to wait establishing a connection. Defaults to 60000 (1 minute)
 * zipkin.collector.rabbitmq.queue	RABBIT_QUEUE	Queue from which to collect span messages. Defaults to zipkin
 * zipkin.collector.rabbitmq.uri	RABBIT_URI	RabbitMQ URI spec-compliant URI, ex. amqp://user:pass@host:10000/vhost
 *
 * If the URI is set, the following properties will be ignored.
 * zipkin.collector.rabbitmq.addresses	RABBIT_ADDRESSES	Comma-separated list of RabbitMQ addresses, ex. localhost:5672,localhost:5673
 * zipkin.collector.rabbitmq.password	RABBIT_PASSWORD	Password to use when connecting to RabbitMQ. Defaults to guest
 * zipkin.collector.rabbitmq.username	RABBIT_USER	Username to use when connecting to RabbitMQ. Defaults to guest
 * zipkin.collector.rabbitmq.virtual-host	RABBIT_VIRTUAL_HOST	RabbitMQ virtual host to use. Defaults to /
 * zipkin.collector.rabbitmq.use-ssl	RABBIT_USE_SSL	Set to true to use SSL when connecting to RabbitMQ
 *
 * 我们访问 RabbitMQ 的 Web 管理界面 http://localhost:15672
 * 可以看到，RabbitMQ 的 Queues 里已经创建了 一个 zipkin 的队列，说明 ZipServer 集成 RabbitMQ 成功了。下面开始搭建 Zipkin Client 端
 *
 * 对于使用 rabbitmq  改造 eureka-zuul-client路由网关服务  eureka-client,eureka-ribbon-client,eureka-feign-client 工程，使他们通过rabbitmq 够上传链路数据
 * 需要添加 spring-cloud-stream-binder-rabbit 的依赖 ，同时配置 spring.rabbitmq 的主机，端口，用户名和密码，spring.zipkin.sender.type=rabbit
 * 查看消息队列控制面板，可以看见 队列zipkin 是否有被消费
 *
 * 启动命令
 * java -jar zipkin.jar --zipkin.collector.rabbitmq.addresses=127.0.0.1:5672 --zipkin.collector.rabbitmq.username=guest --zipkin.collector.rabbitmq.password=guest
 * 或者 java -jar zipkin.jar --zipkin.collector.rabbitmq.addresses=127.0.0.1:5672  （其他默认）
 *
 * ************************************
 *
 * 在 mysql 数据库中存储链路数据
 * zipkin 的链路数据默认 是存储在内存中的 ，在实际生产中，我们需要做持久化
 * 官网上的说明 https://github.com/openzipkin/zipkin/tree/master/zipkin-storage/mysql-v1
 *
 * Zipkin's MySQL component is tested against MySQL 5.7 and applies when STORAGE_TYPE is set to mysql:
 *
 * `MYSQL_DB`: The database to use. Defaults to "zipkin".
 * `MYSQL_USER` and `MYSQL_PASS`: MySQL authentication, which defaults to empty string.
 * `MYSQL_HOST`: Defaults to localhost
 * `MYSQL_TCP_PORT`: Defaults to 3306
 * `MYSQL_MAX_CONNECTIONS`: Maximum concurrent connections, defaults to 10
 * `MYSQL_USE_SSL`: Requires `javax.net.ssl.trustStore` and `javax.net.ssl.trustStorePassword`, defaults to false.
 * Alternatively you can use MYSQL_JDBC_URL and specify the complete JDBC url yourself. Note that the URL constructed by using the separate settings above will also include the following parameters: ?autoReconnect=true&useSSL=false&useUnicode=yes&characterEncoding=UTF-8. If you specify the JDBC url yourself, add these parameters as well.
 *  启动实例  $ STORAGE_TYPE=mysql MYSQL_USER=root java -jar zipkin.jar
 *
 * 新建数据库 MYSQL_DB Defaults to "zipkin".
 * 导入数据库脚本 https://github.com/openzipkin/zipkin/blob/master/zipkin-storage/mysql-v1/src/main/resources/mysql.sql
 *
 * java -jar zipkin.jar --STORAGE_TYPE=mysql --MYSQL_DB=zipkin --MYSQL_USER=root --MYSQL_PASS=123456 --MYSQL_HOST=localhost --MYSQL_TCP_PORT=3306
 * 带mq的启动方式 java -jar zipkin.jar --STORAGE_TYPE=mysql --MYSQL_DB=zipkin --MYSQL_USER=root --MYSQL_PASS=123456 --MYSQL_HOST=localhost --MYSQL_TCP_PORT=3306 --zipkin.collector.rabbitmq.addresses=127.0.0.1:5672
 *
 * 或者 java -jar zipkin.jar --STORAGE_TYPE=mysql --MYSQL_USER=root --MYSQL_PASS=123456 --MYSQL_JDBC_URL=jdbc:mysql://localhost:3306/zipkin?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull
 * docker镜像启动实例 参考 docker run -d -p 9411:9411 -e STORAGE_TYPE=mysql -e MYSQL_HOST=localhost -e MYSQL_TCP_PORT=1320 -e MYSQL_DB=db_zipkin -e MYSQL_USER=root -e MYSQL_PASS=1HJRTUy0eVn8O2lo -e zipkin.collector.rabbitmq.addresses=localhost:5672 -e zipkin.collector.rabbitmq.username=root -e zipkin.collector.rabbitmq.password=1qaz2wsx  openzipkin/zipkin
 *
 * *************************************
 * 使用 ElasticSearch 存储链路数据
 * https://github.com/openzipkin/zipkin/tree/master/zipkin-server
 *
 * Zipkin's Elasticsearch storage component supports versions 5-7.x and applies when STORAGE_TYPE is set to elasticsearch
 * The following apply when STORAGE_TYPE is set to elasticsearch:
 * * `ES_HOSTS`: A comma separated list of elasticsearch base urls to connect to ex. http://host:9200.
 *               Defaults to "http://localhost:9200".
 * * `ES_PIPELINE`: Indicates the ingest pipeline used before spans are indexed. No default.
 * * `ES_TIMEOUT`: Controls the connect, read and write socket timeouts (in milliseconds) for
 *                 Elasticsearch Api. Defaults to 10000 (10 seconds)
 * * `ES_INDEX`: The index prefix to use when generating daily index names. Defaults to zipkin.
 * * `ES_DATE_SEPARATOR`: The date separator to use when generating daily index names. Defaults to '-'.
 * * `ES_INDEX_SHARDS`: The number of shards to split the index into. Each shard and its replicas
 *                      are assigned to a machine in the cluster. Increasing the number of shards
 *                      and machines in the cluster will improve read and write performance. Number
 *                      of shards cannot be changed for existing indices, but new daily indices
 *                      will pick up changes to the setting. Defaults to 5.
 * * `ES_INDEX_REPLICAS`: The number of replica copies of each shard in the index. Each shard and
 *                        its replicas are assigned to a machine in the cluster. Increasing the
 *                        number of replicas and machines in the cluster will improve read
 *                        performance, but not write performance. Number of replicas can be changed
 *                        for existing indices. Defaults to 1. It is highly discouraged to set this
 *                        to 0 as it would mean a machine failure results in data loss.
 * * `ES_USERNAME` and `ES_PASSWORD`: Elasticsearch basic authentication, which defaults to empty string.
 *                                    Use when X-Pack security (formerly Shield) is in place.
 * * `ES_HTTP_LOGGING`: When set, controls the volume of HTTP logging of the Elasticsearch Api.
 *                      Options are BASIC, HEADERS, BODY
 * Example usage:
 * To connect normally:
 * $ STORAGE_TYPE=elasticsearch ES_HOSTS=http://myhost:9200 java -jar zipkin.jar
 * To log Elasticsearch api requests:
 * $ STORAGE_TYPE=elasticsearch ES_HTTP_LOGGING=BASIC java -jar zipkin.jar
 *
 * 关闭 zipkin
 * STORAGE_TYPE=elasticsearch ES_HOSTS=http://localhost:9200 java -jar zipkin.jar
 * 带mq的启动方式 java -jar zipkin.jar --STORAGE_TYPE=elasticsearch --ES_HOSTS=http://localhost:9200 --zipkin.collector.rabbitmq.addresses=127.0.0.1:5672
 *
 * 我们可以查看elasticsearch 中是否有索引： GET _cat/indices
 * **************************************
 *
 * 使用 Kibana 展示链路数据（Kibana 下载和安装参考文档）
 * ElasticSearch 可以和 Kibana 结合，将链路数据展示在Kibana ，安装完成 Kibana 后启动，Kibana默认会向本地端口为 9200的 ElasticSearch 读取数据
 * Kibana 默认的端口为 5601，访问Kibana 的主页 http://localhost:5601
 * 单击 "Managerment" 按钮，然后单击"Create index pattern" ，添加一个index。我们将 ElasticSearch中写入链路数据的index配置为 "zipkin*",
 * 那么界面填写为 "zipkin-*"，就能找到对应的zipkin索引，单击"Create"。创建完成index后，单击"Discover"，就可以在界面上展示链路数据了。
 *
 */
@SpringBootApplication
public class EurekaZipkinServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaZipkinServerApplication.class, args);
    }

}
