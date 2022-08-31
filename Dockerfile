FROM amazoncorretto:8
WORKDIR /
ADD ./target/UserProfileService.jar UserProfileService.jar
ENTRYPOINT ["java", "-Dprocess.name=UserProfileService", "-jar", "UserProfileService.jar"]
