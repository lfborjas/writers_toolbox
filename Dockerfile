FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/writers-toolbox.jar /writers-toolbox/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/writers-toolbox/app.jar"]
