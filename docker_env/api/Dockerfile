FROM phusion/baseimage:0.11

# Use baseimage-docker's init system.
CMD ["/sbin/my_init"]

RUN apt-get update -yqq && \
    apt-get install -yqq software-properties-common git wget unzip

RUN add-apt-repository -y ppa:openjdk-r/ppa
RUN apt-get update

RUN apt-get -yqq install openjdk-11-jdk

RUN mkdir /etc/service/api_service
COPY api_service.sh /etc/service/api_service/run
RUN chmod +x /etc/service/api_service/run

RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

EXPOSE 9000
