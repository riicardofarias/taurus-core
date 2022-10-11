FROM azul/zulu-openjdk-alpine:13
MAINTAINER Ricardo Farias <riicardofarias@gmail.com>

# Add the service itself
ADD target/taurus-core-1.0.jar /usr/share/api/app.jar

# Startup service
ENTRYPOINT ["java", "-jar", "/usr/share/api/app.jar", "-Djava.net.preferIPv4Stack=true"]