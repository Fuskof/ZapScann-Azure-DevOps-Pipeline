package smart.hub.components;

import com.microsoft.azure.servicebus.*;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;
import net.serenitybdd.core.Serenity;
import org.apache.log4j.Logger;
import org.awaitility.core.ConditionTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.awaitility.Awaitility.with;
import static org.awaitility.Durations.ONE_SECOND;
import static smart.hub.helpers.Constants.LOG_SEPARATOR;

@Component
public class ServiceBus {

    @Autowired
    private ConnectionState connectionState;

    @Autowired
    private Environment env;

    private Logger logger = Logger.getLogger(ServiceBus.class.getName());

    public QueueClient getServiceBusQueueClient(String connectionString, String queueName) {
        if (connectionState.getServiceBusPool().isEmpty()
                || !connectionState.getServiceBusPool().containsKey(queueName)) {
            QueueClient queueClient = createServiceBusQueueClient(connectionString, queueName);
            connectionState.getServiceBusPool().put(queueName, queueClient);
        }
        return connectionState.getServiceBusPool().get(queueName);
    }

    public QueueClient getServiceBusQueueClient(String queueName) {
        String connectionString = env.getProperty("serviceBus.connectionString");
        return getServiceBusQueueClient(connectionString, queueName);
    }

    public QueueClient createServiceBusQueueClient(String connectionString, String queueName) {
        try {
            return new QueueClient(new ConnectionStringBuilder(connectionString, queueName), ReceiveMode.PEEKLOCK);
        } catch (InterruptedException | ServiceBusException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public QueueClient createServiceBusQueueClient(String queueName) {
        String connectionString = env.getProperty("serviceBus.connectionString");
        return createServiceBusQueueClient(connectionString, queueName);
    }

    public IMessageReceiver createServiceBusMessageReceiver(String connectionString, String queueName) {
        try {
            return ClientFactory.createMessageReceiverFromConnectionStringBuilder(
                    new ConnectionStringBuilder(connectionString, queueName), ReceiveMode.PEEKLOCK);
        } catch (InterruptedException | ServiceBusException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public IMessageReceiver createServiceBusMessageReceiver(String queueName) {
        String connectionString = env.getProperty("serviceBus.connectionString");
        return createServiceBusMessageReceiver(connectionString, queueName);
    }

    private Message createServiceBusMessage(String content, String contentType,
                                            String messageId, String label, Duration duration) {
        Message message = new Message(messageId, content, contentType);
        message.setLabel(label);
        message.setTimeToLive(duration);
        logger.info(String.format(LOG_SEPARATOR + "Service bus message %s prepared:\n%s"
                + LOG_SEPARATOR, messageId, content));
        return message;
    }

    public void sendServiceBusMessage(QueueClient queueClient, String content, String contentType,
                                      String messageId, String label, Duration duration) {
        Message message = createServiceBusMessage(content, contentType, messageId, label, duration);
        try {
            queueClient.send(message);
            Thread.sleep(1000);
        } catch (InterruptedException | ServiceBusException e) {
            e.printStackTrace();
        }
        Serenity.setSessionVariable("serviceBusSentMessage").to(message);
        Serenity.setSessionVariable("serviceBusSentMessageId").to(messageId);
        Serenity.setSessionVariable("serviceBusSentMessageContent").to(content);
    }

    public void sendServiceBusMessage(QueueClient queueClient, String messageId, String content, String contentType) {
        String label = "QA";
        Duration duration = Duration.ofMinutes(1);
        sendServiceBusMessage(queueClient, content, contentType, messageId, label, duration);
    }

    public void sendServiceBusMessage(QueueClient queueClient, String content, String contentType) {
        String messageId = UUID.randomUUID().toString();
        sendServiceBusMessage(queueClient, messageId, content, contentType);
    }

    public void sendServiceBusMessageAsXml(QueueClient queueClient, String messageId, String content) {
        sendServiceBusMessage(queueClient, messageId, content, "application/xml");
    }

    public void sendServiceBusMessageAsXml(QueueClient queueClient, String content) {
        sendServiceBusMessage(queueClient, content, "application/xml");
    }

    public void sendServiceBusMessageAsJson(QueueClient queueClient, String messageId, String content) {
        sendServiceBusMessage(queueClient, messageId, content, "application/json");
    }

    public void sendServiceBusMessageAsJson(QueueClient queueClient, String content) {
        sendServiceBusMessage(queueClient, content, "application/json");
    }

    public Message getServiceBusMessage(IMessageReceiver receiver, String messageId) {

        List<Message> messages = new ArrayList<>();
        try {
            with().pollInterval(ONE_SECOND)
                    .and().with().pollDelay(ONE_SECOND)
                    .await()//.atMost(5, TimeUnit.SECONDS)
                    //the default timeout is 10 seconds
                    .until(() -> {
                        String receivedMessageId = "";
                        Message latestMessage = (Message) receiver.peek();
                        if (!Objects.isNull(latestMessage)) {
                            messages.add(latestMessage);
                            if (!Objects.isNull(latestMessage.getMessageId())) {
                                receivedMessageId = latestMessage.getMessageId();
                            }
                        }
                        return receivedMessageId.equals(messageId);
                    });
            Message message = messages.get(messages.size() - 1);
            logger.info(String.format(LOG_SEPARATOR + "Message found:\n%s" + LOG_SEPARATOR, new String(message.getBody())));
            Serenity.setSessionVariable("serviceBusReceivedMessage").to(message);
            return message;
        } catch (ConditionTimeoutException e) {
            logger.info(String.format(LOG_SEPARATOR + "No message with messageId: %s found" + LOG_SEPARATOR, messageId));
            return new Message();
        } finally {
            receiver.closeAsync();
        }
    }

//    public static void main(String[] args) {
//        ServiceBus sb = new ServiceBus();
//        String connectionString = "Endpoint=sb://dssmarthub.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=8znRgstfTFTKMuiAUqmS+h+tx/I2dEXI9rVaIi6LHMo=";
//        String queueName = "rules-configuration-queue";
//        QueueClient queueClient = null;
//        try {
//            queueClient = new QueueClient(new ConnectionStringBuilder(connectionString, queueName), ReceiveMode.PEEKLOCK);
//            IMessageReceiver receiver = ClientFactory.createMessageReceiverFromConnectionStringBuilder(new ConnectionStringBuilder(connectionString, queueName), ReceiveMode.PEEKLOCK);
//            String sentMessageId = UUID.randomUUID().toString();
//            Message sentMessage = sb.createServiceBusMessage("QA_TEST_" + System.currentTimeMillis(), "application/xml", sentMessageId, "QA", Duration.ofMinutes(1));
//            sb.sendServiceBusMessage(queueClient, sentMessage);
//            Message receivedMessage = sb.getServiceBusMessage(receiver, sentMessageId);
//            String receivedMessageId = receivedMessage.getMessageId();
//            System.out.println(receivedMessageId.equals(sentMessageId) ? "Test passed" : "Test failed");
//        } catch (InterruptedException | ServiceBusException e) {
//            e.printStackTrace();
//        }
//    }
}
