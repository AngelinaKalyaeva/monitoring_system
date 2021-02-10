#!/bin/bash

code_response=("200" "200" "200" "200" "200" "200" "200" "200" "200" "500")
url=("/hello" "/bye")

while true
do
	rand_code=${code_response[$RANDOM % 10]}
	rand_url=${url[$RANDOM %2]}
	echo "${rand_code}"
	echo "${rand_url}"
	echo "PaymentSystemMetric gone..."
	curl 'http://192.168.99.101:8080/graphql' -H 'Accept-Encoding: gzip, deflate, br' -H 'Content-Type: application/json' -H 'Accept: application/json' -H 'Connection: keep-alive' -H 'DNT: 1' -H 'Origin: file://' --data-binary '{"query":"mutation { writePaymentSystemMetric(serviceId: \"10\", code: \"'"${rand_code}"'\", type: \"visa\") { service { id efficiency { payment { code } } } } }"}' --compressed
	echo "SlaErrorMetric gone..."
	curl 'http://192.168.99.101:8080/graphql' -H 'Accept-Encoding: gzip, deflate, br' -H 'Content-Type: application/json' -H 'Accept: application/json' -H 'Connection: keep-alive' -H 'DNT: 1' -H 'Origin: file://' --data-binary '{"query":"mutation { writeSlaError(serviceId: \"10\", url: \"'"${rand_url}"'\", code: \"'"${rand_code}"'\", ) { service { id efficiency { sla { url error { code } } } } } }"}' --compressed
done