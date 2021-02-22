CREATE DATABASE IF NOT EXISTS analytics_metrics;
CREATE TABLE IF NOT EXISTS analytics_metrics.attendance (
	`serviceId` String,
	`url` String, 
	`count` Int32,
	`timestamp` DateTime('Europe/Moscow')
) ENGINE=Log;

CREATE TABLE IF NOT EXISTS analytics_metrics.dynamic_sale (
	`serviceId` String,
	`cost` Int32,
	`saleCount` Int32, 
	`returnCount` Int32,
	`timestamp` DateTime('Europe/Moscow')
) ENGINE=Log;

CREATE TABLE IF NOT EXISTS analytics_metrics.product_dynamic (
	`serviceId` String,
	`productId` String,
	`salesCount` Int32, 
	`timestamp` DateTime('Europe/Moscow')
) ENGINE=Log;

INSERT INTO analytics_metrics.attendance VALUES ('10', '/hello', 1, 1613423448);
INSERT INTO analytics_metrics.dynamic_sale VALUES ('10', 100, 1, 0, 1613423448);
INSERT INTO analytics_metrics.dynamic_sale VALUES ('10', 500, 0, 1, 1613423448);
INSERT INTO analytics_metrics.product_dynamic VALUES ('10', '1000', 10, 1613423448);