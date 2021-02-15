CREATE DATABASE IF NOT EXISTS analytics_metrics;
CREATE TABLE IF NOT EXISTS analytics_metrics.attendance (
	`serviceId` String,
	`url` String, 
	`count` Int32,
	`timestamp` DateTime('Europe/Moscow')
) ENGINE=Log;

INSERT INTO analytics_metrics.attendance VALUES ('10', '/hello', 1, 1613423448);