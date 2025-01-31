echo '{ "tenant" : "tenant1", "productSku" : "sku123", "usedAmount" : 15.5, "useUnity" : "GB" }' | docker exec -i kafka kafka-console-producer.sh --bootstrap-server kafka:9092 --topic pulse.received
