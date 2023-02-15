package erp.viewcached;

import org.apache.kafka.clients.consumer.KafkaConsumer;

public class CacheInvalidationReceiver {
    private KafkaConsumer<String, Object> consumer;


    private KafkaConsumer<String, Object> createConsumer(String group) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, group);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        deserializationStrategy.configValueDeserializerClass(props);
        KafkaConsumer<String, Object> consumer = new KafkaConsumer<>(props);
        List<String> topics = new ArrayList<>();
        topics.add("arp_process_message");
        consumer.subscribe(topics);
        return consumer;
    }

    public synchronized List<Message> receive() throws Exception {
        if (lastReceiveMessageCount == 0) {
            LockSupport.parkNanos(1L);
        }
        List<Message> messageList = new ArrayList<>();
        ConsumerRecords<String, Object> records = consumer.poll(Duration
                .ofMillis(0));
        for (ConsumerRecord<String, Object> record : records) {
            deserializationStrategy.deserialize(record);
            messageList.add(deserializationStrategy.deserialize(record));
        }
        lastReceiveMessageCount = messageList.size();
        return messageList;
    }
}
