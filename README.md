# monitoring_system  

docker build -t prometheus-server .  
docker run -p 9090:9090 prometheus-server    
  
docker run -d -p 3000:3000 grafana/grafana  

docker run -d —name clickhouse-server —ulimit nofile=262144:262144 -p 8123:8123 yandex/clickhouse-server  
docker run -it —rm —link clickhouse-server:clickhouse-server yandex/clickhouse-client —host clickhouse-server

