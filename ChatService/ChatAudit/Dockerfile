FROM chat_base_image:master

RUN mkdir -p /srv

COPY target/ChatAudit-1.0-SNAPSHOT-allinone.jar /srv
COPY entrypoint.sh /srv
RUN chmod +x /srv/entrypoint.sh

EXPOSE 25520
ENTRYPOINT ["/srv/entrypoint.sh"]