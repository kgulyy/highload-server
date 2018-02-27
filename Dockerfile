FROM ubuntu:16.04

MAINTAINER Konstantin Gulyy

# Обвновление списка пакетов
RUN apt-get -y update

# Установка JDK
RUN apt-get install -y openjdk-8-jdk-headless
RUN apt-get install -y maven

# Копируем исходный код в Docker-контейнер
ENV APP /opt
ADD ./ $APP

# Собираем и устанавливаем пакет
WORKDIR $APP
RUN mvn clean install

# Объявлем порт сервера
EXPOSE 80

# Запускаем сервер
CMD java -jar $APP/target/highload-server-1.0-jar-with-dependencies.jar
