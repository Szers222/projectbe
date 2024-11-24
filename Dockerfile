# Sử dụng image OpenJDK
FROM openjdk:23-jdk-slim

# Biến ARG để chỉ định tên file JAR
ARG JAR_FILE=target/*.jar

# Đặt thư mục làm việc trong container
WORKDIR /app

# Sao chép file JAR của ứng dụng vào thư mục làm việc
COPY ${JAR_FILE} app.jar

# Mở cổng 8080
EXPOSE 8080

# Chạy ứng dụng JAR bằng lệnh default
ENTRYPOINT ["java", "-jar", "app.jar"]