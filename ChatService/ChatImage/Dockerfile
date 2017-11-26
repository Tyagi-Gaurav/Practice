FROM centos:centos7

RUN yum update -y && \
    yum install -y wget && \
    cd /opt && \
    wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u151-b12/e758a0de34e24606bca991d704f6dcbf/jdk-8u151-linux-x64.tar.gz" && \
    tar -zxf jdk-8u151-linux-x64.tar.gz && \
    alternatives --install /usr/bin/java java /opt/jdk1.8.0_151/bin/java 2
