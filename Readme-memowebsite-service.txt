In /etc/systemd/system create a file named oapen-memo-client-website.service with the following content:

[Unit]
Description=OAPEN MEMO Client Website
After=syslog.target

[Service]
User=oapen
ExecStart=/home/oapen/oapenmemo/clientweb.jar SuccessExitStatus=143

[Install] 
WantedBy=multi-user.target
