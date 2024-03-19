FROM eclipse-temurin:17-jre-alpine
WORKDIR /
ADD ./target/UserProfileService.jar UserProfileService.jar
ENTRYPOINT ["java", "-Dprocess.name=UserProfileService", "-jar", "UserProfileService.jar"]
