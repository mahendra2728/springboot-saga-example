Topics used in this example
--------------
orders-event
payment-event


Sample Request for placing order

URL : http://localhost:8081/v1/api/createOrder

{
    "userId" : 101,
    "productId" : 123,
    "productName" : "mobiles",
    "productAmount" : 100
}


1)Create topic
bin/kafka-topics.sh --create --topic TOPIC_NAME --replication-factor 1  --partitions 2  --zookeeper localhost:2181

2)Get list of topics
bin/kafka-topics.sh --bootstrap-server=localhost:9092 --list

3)Read messages from topic
 bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic TOPIC_NAME --from-beginning