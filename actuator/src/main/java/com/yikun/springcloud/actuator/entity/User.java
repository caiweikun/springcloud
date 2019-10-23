package com.yikun.springcloud.actuator.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity()
// 定义映射的表
@Table(name = "t_user")
// get set function
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false,  unique = true)
	private String userName;

	@Column()
	private String password;

	public User() {
	}


}
