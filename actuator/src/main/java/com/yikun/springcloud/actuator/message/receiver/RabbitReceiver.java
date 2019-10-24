package com.yikun.springcloud.actuator.message.receiver;

import com.yikun.springcloud.actuator.entity.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 请自行安装rabbitmq , 以下是Mac环境下的安装启动过程
 * 如果你还没有安装过brew,那么请使用一下指令安装下这个mac平台里十分好用的包管理器
 * /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
 * 更新brew资源  brew update
 * 执行安装  brew install rabbitmq
 * RabbitMQ安装后的路径为：/usr/local/Cellar/rabbitmq/3.8.0 (版本根据安装版本确定)
 * 切换到MQ目录,注意你的安装版本可能不是3.8.0
 * cd /usr/local/Cellar/rabbitmq/3.8.0/
 * 启用rabbitmq management插件
 * sudo sbin/rabbitmq-plugins enable rabbitmq_management
 * 进入user_home下 编辑 vi .bash_profile 配置环境变量
 * //加入以下两行
 * export RABBIT_HOME=/usr/local/Cellar/rabbitmq/3.8.0
 * export PATH=$PATH:$RABBIT_HOME/sbin
 * 立即生效
 * source /etc/profile
 * 后台启动
 * rabbitmq-server -detached
 * 查看状态
 * rabbitmqctl status
 * 访问可视化监控插件的界面
 * 浏览器内输入 http://localhost:15672,默认的用户名密码都是guest,登录后可以在Admin那一列菜单内添加自己的用户
 * rabbitmqctl stop 关闭
 */
@Component
public class RabbitReceiver {
	
	// 定义监听字符串队列名称
	@RabbitListener(queues = { "${rabbitmq.queue.msg}" })
	public void receiveMsg(String msg) {
 		System.out.println("收到消息: 【" + msg + "】");
	}
	
    // 定义监听用户队列名称
	@RabbitListener(queues = { "${rabbitmq.queue.user}" })
	public void receiveUser(User user) {
		System.out.println("收到用户信息【" + user + "】");
	}
}
