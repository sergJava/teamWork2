spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
telegram.bot.token=

spring.datasource.url=jdbc:postgresql://localhost:5432/telegramDb
spring.datasource.username=worker
spring.datasource.password=telegram

# Activating the default profile (local)
spring.profiles.active=local

spring.liquibase.change-log=classpath:liquibase/changelog-master.yml

# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto= update

spring.jpa.show-sql=true

# Token stub (will be redefined by the local profile)
telegram.bot.token=placeholder-token