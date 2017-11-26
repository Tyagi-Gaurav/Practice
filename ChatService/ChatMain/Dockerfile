FROM chat_base_image:master

RUN mkdir -p /srv
COPY target/ChatMain-1.0-SNAPSHOT-allinone.jar /srv
EXPOSE 8080
ENTRYPOINT ["java","-jar","/srv/ChatMain-1.0-SNAPSHOT-allinone.jar"]