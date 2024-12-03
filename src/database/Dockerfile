FROM mysql:8.0

ENV MYSQL_DATABASE mobileproject
ENV MYSQL_ROOT_PASSWORD root12345

COPY web.sql /docker-entrypoint-initdb.d/

EXPOSE 3306