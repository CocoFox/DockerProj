FROM openjdk:8
COPY ./ /usr/src/comparateur-chaussures-master/
WORKDIR /usr/src/comparateur-chaussures-master/
RUN dpkg -i mysql-connector-java_8.0.16-1debian9_all.deb && \
    apt-get install -f
RUN javac */*.java
CMD ["java", "app.App"]
