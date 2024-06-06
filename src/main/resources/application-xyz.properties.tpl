
server.port=8080

spring.datasource.url=jdbc:mysql://1.3.2.4:3306/oapen_memo?reconnect=true&rewriteBatchedStatements=true
spring.datasource.username=********
spring.datasource.password=********

logging.file.name=${user.home}/oapenmemo/logs/oapen_memo-taskrunner.log
logging.level.root=INFO
logging.level.org.oapen.memoproject.taskrunner=INFO
logging.logback.rollingpolicy.max-history=40

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

application.filesroot=${user.home}/...

#Disable default error page
server.error.whitelabel.enabled=false 

#spring.devtools.restart.exclude=

