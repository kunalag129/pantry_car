FROM java:7
VOLUME /tmp
ADD pantry_car-1.2.0.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]