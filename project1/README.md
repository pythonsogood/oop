# Endterm OOP project

Online shopping backend service written with [Spring Boot](https://spring.io/projects/spring-boot) in [Java 23](https://openjdk.org/projects/jdk/23) programming language

[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/license/mit/)

## [application.properties](/project1/src/main/resources/application.properties) structure
| Key | Description |
| --- | --- |
| user.authorization.jwt.secret | Secret key for JWT verification in user authorization |
| user.authorization.jwt.duration | Expiration time (in seconds) for JWT token in user authorization |
| store.authorization.admin_token | Admin key for store modificaion |
| user.authorization.admin_token | Admin key for user balance modification |
| spring.datasource.url | Connection url for PostreSQL database `jdbc:postgresql://host:port/database_name` |
| spring.datasource.username | Database username |
| spring.datasource.password | Database user password |
| spring.jpa.hibernate.ddl-auto | Must be set to `update` for correct work |