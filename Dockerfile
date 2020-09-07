FROM ubuntu:latest

RUN apt-get update -y

RUN apt-get install git openjdk-14-jdk-headless wget gradle -y

RUN git clone https://github.com/Myeongjoon/cicdtest.git

WORKDIR cicdtest

run ./gradlew build

WORKDIR build/libs

RUN ls -al
