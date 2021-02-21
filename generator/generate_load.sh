#!/bin/bash

code_response=("200" "200" "200" "200" "200" "200" "200" "200" "200" "500")
url_time_response=("100" "121" "545" "19" "1000" "103" "110" "503" "111" "99")
attendency_count_response=("25" "30" "40" "15" "10" "25" "31" "29" "17" "19")
sale_count_response=("2" "3" "4" "1" "10" "5" "1" "9" "17" "19")
sale_cost_response=("1000" "1500" "100" "10" "2000" "15000" "170" "935" "120" "1200")
url=("/hello" "/bye")

while true
do
	rand_code=${code_response[$RANDOM % 10]}
	rand_url=${url[$RANDOM % 2]}
	rand_time=${url_time_response[$RANDOM % 10]}
	rand_attendency=${attendency_count_response[$RANDOM % 10]}
	rand_sale_count=${sale_count_response[$RANDOM % 10]}
	rand_sale_cost=${sale_cost_response[$RANDOM % 10]}
	echo "${rand_code}"
	echo "${rand_url}"
	echo "${rand_attendency}"
	echo "AttendenceMetrics Load..."
	curl 'http://192.168.99.102:8080/graphql' -H 'Accept-Encoding: gzip, deflate, br' -H 'Content-Type: application/json' -H 'Accept: application/json' -H 'Connection: keep-alive' -H 'DNT: 1' -H 'Origin: file://' --data-binary '{"query":"mutation { writeAttendancy(serviceId: \"10\", url: \"'"${rand_url}"'\", count:'"${rand_attendency}"') { service { datetime analytics { attendance { url count } } } } }"}' --compressed
	echo "SaleCountDynamic..."
	curl 'http://192.168.99.102:8080/graphql' -H 'Accept-Encoding: gzip, deflate, br' -H 'Content-Type: application/json' -H 'Accept: application/json' -H 'Connection: keep-alive' -H 'DNT: 1' -H 'Origin: file://' --data-binary '{"query":"mutation { writeSaleDynamic(serviceId: \"10\", saleCount: '"${rand_sale_count}"', returnCount: 0,  cost: '"${rand_sale_cost}"') {\n    service {\n      datetime\n      analytics {\n        dynamic {\n          sale {\n            saleCount\n          }\n        }\n      }\n    }\n  }\n}"}' --compressed
	echo "PaymentSystemMetric gone..."
	curl 'http://192.168.99.102:8080/graphql' -H 'Accept-Encoding: gzip, deflate, br' -H 'Content-Type: application/json' -H 'Accept: application/json' -H 'Connection: keep-alive' -H 'DNT: 1' -H 'Origin: file://' --data-binary '{"query":"mutation { writePaymentSystemMetric(serviceId: \"10\", code: \"'"${rand_code}"'\", type: \"visa\") { service { id efficiency { payment { code } } } } }"}' --compressed
	echo "SlaErrorMetric gone..."
	curl 'http://192.168.99.102:8080/graphql' -H 'Accept-Encoding: gzip, deflate, br' -H 'Content-Type: application/json' -H 'Accept: application/json' -H 'Connection: keep-alive' -H 'DNT: 1' -H 'Origin: file://' --data-binary '{"query":"mutation { writeSlaError(serviceId: \"10\", url: \"'"${rand_url}"'\", code: \"'"${rand_code}"'\", ) { service { id efficiency { sla { url error { code } } } } } }"}' --compressed
	rand_url=${url[$RANDOM % 2]}
	rand_time=${url_time_response[$RANDOM % 10]}
	echo "SlaTimingMetric gone..."
	curl 'http://192.168.99.102:8080/graphql' -H 'Accept-Encoding: gzip, deflate, br' -H 'Content-Type: application/json' -H 'Accept: application/json' -H 'Connection: keep-alive' -H 'DNT: 1' -H 'Origin: file://' --data-binary '{"query":"mutation { writeSlaTiming(serviceId: \"10\", url: \"'"${rand_url}"'\", time: '"${rand_time}"', slaTime: 500) { service { id efficiency { sla { url timing { time slaTime } } } } } }"}' --compressed
	rand_url=${url[$RANDOM % 2]}
	rand_time=${url_time_response[$RANDOM % 10]}
	echo "SlaTimingMetric gone..."
	curl 'http://192.168.99.102:8080/graphql' -H 'Accept-Encoding: gzip, deflate, br' -H 'Content-Type: application/json' -H 'Accept: application/json' -H 'Connection: keep-alive' -H 'DNT: 1' -H 'Origin: file://' --data-binary '{"query":"mutation { writeSlaTiming(serviceId: \"10\", url: \"'"${rand_url}"'\", time: '"${rand_time}"', slaTime: 500) { service { id efficiency { sla { url timing { time slaTime } } } } } }"}' --compressed
done