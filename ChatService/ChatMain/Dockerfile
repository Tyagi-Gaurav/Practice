FROM chat_base_image:master

RUN mkdir -p /srv
COPY target/ChatMain-1.0-SNAPSHOT-allinone.jar /srv
COPY entrypoint.sh /srv
RUN chmod +x /srv/entrypoint.sh
EXPOSE 8080

ENTRYPOINT ["/srv/entrypoint.sh"]