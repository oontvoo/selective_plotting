$mysql --user=root mysql

CREATE USER 'lionv'@'localhost'  IDENTIFIED BY 'lionv';
GRANT ALL PRIVILEGES ON *.* TO 'lionv'@'lionv' WITH GRANT OPTION;

$mysql --user=lionv -p
<enter password>

CREATE DATABASE wcl_data;
USE wcl_data;