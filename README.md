# monitoring_system  

docker build -t prometheus-server .  
docker run -p 9090:9090 prometheus-server    
  
docker run -d -p 3000:3000 --name=grafana -e "GF_INSTALL_PLUGINS=grafana-clock-panel,grafana-simple-json-datasource,vertamedia-clickhouse-datasource" grafana/grafana  

docker run -d --name clickhouse-server --ulimit nofile=262144:262144 -p 8123:8123 yandex/clickhouse-server  
docker run -it --rm --link clickhouse-server:clickhouse-server yandex/clickhouse-client --host clickhouse-server


пример запроса к GraphQL:
ошибки платежной системы:
mutation {
  writePaymentSystemMetric(serviceId: "10", code: "200", type: "visa") {
  	service {
  		id
      efficiency {
        payment {
          code
        }
      }
    }
  }
}
процент ошибок обращения к url:
mutation {
  writeSlaError(serviceId: "10", url: "/bye", code: "500", ) {
  	service {
      id
      efficiency {
        sla {
          url
          error {
            code
          }
        }
      }
    }  
  }
}