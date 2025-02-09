FROM maven:3.8.5-openjdk-17

RUN rm -rf /etc/localtime
RUN ln -s /usr/share/zoneinfo/Asia/Kuala_Lumpur /etc/localtime

RUN mkdir -p /opt/uitm/webapps/MYATTEND
RUN mkdir -p /opt/uitm/logs/MYATTEND
RUN mkdir -p /home/uitm

COPY . /opt/uitm/webapps/MYATTEND
RUN groupadd -g 10001 uitm && useradd -u 10001 -r -g uitm uitm

RUN chown -R uitm:uitm /opt/uitm/webapps
RUN chown -R uitm:uitm /opt/uitm/logs
RUN chown -R uitm:uitm /home/uitm

USER uitm
WORKDIR /opt/uitm/webapps/MYATTEND
RUN chmod -R 777 /home/uitm/

ENTRYPOINT ["mvn","spring-boot:run"]

