FROM tumcyk/micro-java:latest

MAINTAINER Tomasz Kuśmierczyk <tomasz.kusmierczyk@mobica.com>

#this is Docker build cache buster see: CheRuisiBesares  at https://github.com/docker/docker/issues/1996
ADD http://www.random.org/strings/?num=10&len=8&digits=on&upperalpha=on&loweralpha=on&unique=on&format=plain&rnd=new uuid
#RUN git clone -b provision https://github.com/tomqsm/letsweb-micro.git /srv/letsweb-micro

# WORKDIR /srv/letsweb-micro

#RUN cd /srv/letsweb-micro && mvn clean install

EXPOSE 8070

ENTRYPOINT rm -rf /srv/letsweb-micro && git clone -b provision https://github.com/tomqsm/letsweb-micro.git /srv/letsweb-micro && cd /srv/letsweb-micro && mvn clean package && java -Djava.security.egd=file:/dev/./urandom -jar target/letsweb-micro-1.0-SNAPSHOT.jar

#ENTRYPOINT /bin/bash
