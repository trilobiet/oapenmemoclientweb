# In /etc/systemd/system create a file named oapen-memo-client-website.service with the following content:

[Unit]
Description=OAPEN MEMO Client Website
After=syslog.target network.target

[Service]
SuccessExitStatus=143
User=oapen
Group=oapen

Type=simple

ExecStart=java -Xmx512m -jar /home/oapen/oapenmemo/clientweb.jar

[Install] 
WantedBy=multi-user.target
