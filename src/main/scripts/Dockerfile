FROM java:8
WORKDIR /
ADD UserProfileService.jar UserProfileService.jar
ENTRYPOINT ["java", "-Dprocess.name=UserProfileService", "-jar", "UserProfileService.jar"]
