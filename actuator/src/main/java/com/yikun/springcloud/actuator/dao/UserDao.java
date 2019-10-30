package com.yikun.springcloud.actuator.dao;


import com.yikun.springcloud.actuator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * use springcloud;
 * create table t_user(
 *     id bigint not null auto_increment primary key ,
 *     user_name varchar(255) not null ,
 *     password varchar(255) not null ,
 *     unique key(user_name)
 * );
 * insert into `springcloud`.`t_user` (`password`, `user_name`) VALUES ( '123456', 'test1');
 * select * from t_user;
 */
//@Repository
public interface UserDao extends JpaRepository<User, Long> {

	User findByUserName(String username);
}
