import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.stream.StreamReceiver
import printscript.redis.consumer.RedisStreamConsumer
import java.time.Duration

// Clase concreta de prueba
class TestRedisStreamConsumer : RedisStreamConsumer<String>("test-stream", "test-group", DummyRedisTemplate()) {
    public override fun onMessage(record: ObjectRecord<String, String>) {
        println("Processing record: ${record.value}")
    }

    override fun options(): StreamReceiver.StreamReceiverOptions<String, ObjectRecord<String, String>> {
        return StreamReceiver.StreamReceiverOptions.builder()
            .pollTimeout(Duration.ofMillis(100))
            .targetType(String::class.java)
            .build()
    }
}

// Dummy RedisTemplate para propósitos básicos
class DummyRedisTemplate : ReactiveRedisTemplate<String, String>(
    mock(ReactiveRedisConnectionFactory::class.java),
    RedisSerializationContext.string(),
)

class RedisStreamConsumerTest {
    @Test
    fun `test consumer instantiation`() {
        val consumer = TestRedisStreamConsumer()
        println("Consumer instantiated successfully: $consumer")
    }

    @Test
    fun `test onMessage execution`() {
        val consumer = TestRedisStreamConsumer()
        val dummyRecord = ObjectRecord.create("test-stream", "test-message")
        consumer.onMessage(dummyRecord)
        println("onMessage executed successfully")
    }
}
