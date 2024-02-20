FROM alpine:3.19.1 as mvn

ENV MAVEN_HOME=/usr/share/maven
RUN apk add --no-cache maven
ENV PATH=$MAVEN_HOME/bin:$PATH

WORKDIR /root

COPY pom.xml .
RUN mvn dependency:go-offline

COPY . ./
RUN mvn clean package && \
    mv $(find /root/target/ -type f -name '*.jar' -a ! -name '*-javadoc.jar' -a ! -name '*-sources.jar' -a ! -name '*-tests.jar') /root/target/app.jar


FROM openjdk:21-jdk-slim
WORKDIR /var/app

COPY --from=mvn /root/target/app.jar /var/app/app.jar

# Run as non-root
RUN adduser --system --group appuser
RUN chown -R appuser:appuser /var/app
USER appuser

CMD ["java", "-jar", "app.jar"]