#!/bin/bash

good_ids=("1" "2" "3" "4" "4" "5" "6" "7" "8" "9" "10")

while true
do
	good_id=${good_ids[$RANDOM % 9]}

	echo "AttendenceMetrics Load..."
	curl 'http://localhost:8083/'
	
  curl --location --request GET 'http://localhost:8083/good/'"$good_id"'/add' \
--header 'Accept-Encoding: gzip, deflate, br' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \

  curl --location --request POST 'http://localhost:8083/buy' \
--header 'Accept-Encoding: gzip, deflate, br' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--header 'X-CSRF-Token: 57c5424e-b58c-48d2-bc15-88b881d1d794' \
--header 'Cookie: Idea-ade462ff=de76a9ae-fb69-4ac8-b2a0-91ee2e9cc4d8; adminer_key=2775f1992141069b8ab4f5879d7b0538; adminer_version=4.8.0; adminer_sid=e19d1a182c38d05d02d331ef4d1f2960; grafana_session=21a2bc37943e22bfd94f64d6eacbd88a; JSESSIONID=6134AA90A523E7601FE56DC3112DD5BA' \
--form 'textField="123"' \
--form 'intField="123"'
	
done