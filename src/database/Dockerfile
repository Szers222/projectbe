FROM mysql:8.0

ENV MYSQL_DATABASE mobileproject
ENV MYSQL_ROOT_PASSWORD='root123'

COPY newW_v3.sql /docker-entrypoint-initdb.d/

EXPOSE 3306