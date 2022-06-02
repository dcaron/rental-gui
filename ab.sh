curl -X POST --location "http://bike-rental-first-default.cnr.axon.devops.cloud-native.biz/generateRentals?bikeType=city&loops=3&concurrency=10" \
    -H "Accept: text/plain" -w "\n\n%{time_connect} + %{time_starttransfer} = %{time_total}\n"

curl -X POST --location "http://bike-rental-second-default.cnr.axon.devops.cloud-native.biz/generateRentals?bikeType=city&loops=3&concurrency=10" \
    -H "Accept: text/plain" -w "\n\n%{time_connect} + %{time_starttransfer} = %{time_total}\n"