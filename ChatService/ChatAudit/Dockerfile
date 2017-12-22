FROM chat_base_image:master

RUN mkdir -p /srv
COPY target/ChatAudit-1.0-SNAPSHOT-allinone.jar /srv
EXPOSE 8080
ENTRYPOINT ["java","-jar","/srv/ChatAudit-1.0-SNAPSHOT-allinone.jar"]