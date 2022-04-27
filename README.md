# 商品秒杀项目

## 概述

本项目是一套电商系统，基于SpringBoot+MyBatisplus+redis+rabbitmq实现，主要解决秒杀应用场景下的高并发问题。秒杀其实主要解决两个问题，一个是并发读，一个是并发写。并发读的核心优化 理念是尽量减少用户到服务端来“读”数据，或者让他们读更少的数据；并发写的处理原则也一样，它要求我们在数据库层面独立出来一个库，做特殊的处理。

所以从技术角度上看“稳、准、快”，就对应了我们架构上的高可用、一致性和高性能的要求 

* **高性能。** 秒杀涉及大量的并发读和并发写，因此支持高并发访问这点非常关键。对应的方案比如 动静分离方案、热点的发现与隔离、请求的削峰与分层过滤、服务端的极致优化 。

* **一致性。** 秒杀中商品减库存的实现方式同样关键。可想而知，有限数量的商品在同一时刻被很多 倍的请求同时来减库存，减库存又分为“拍下减库存”“付款减库存”以及预扣等几种，在大并发更新 的过程中都要保证数据的准确性，其难度可想而知 。

* **高可用。** 现实中总难免出现一些我们考虑不到的情况，所以要保证系统的高可用和正确性，我们 还要设计一个 PlanB 来兜底，以便在最坏情况发生时仍然能够从容应对。

## 项目使用框架介绍

### SpringBoot

> SpringBoot可以让你快速构建基于Spring的Web应用程序，内置多种Web容器(如Tomcat)，通过启动入口程序的main函数即可运行。

### Mybatis-Plus

> [MyBatis-Plus (opens new window)](https://github.com/baomidou/mybatis-plus)（简称 MP）是一个 [MyBatis (opens new window)](https://www.mybatis.org/mybatis-3/)的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。MyBatis 是一款优秀的持久层框架，它支持自定义 SQL、存储过程以及高级映射。MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

## 项目搭建

**使用IDEA初始化SpringBoot项目**

### 项目目录架构

### Mybayis-Plus逆向工程

### 整合redist

### 整合rabbitmq

## API接口

## 框架学习

### Mybatisplus

### redis

### rabbitmq