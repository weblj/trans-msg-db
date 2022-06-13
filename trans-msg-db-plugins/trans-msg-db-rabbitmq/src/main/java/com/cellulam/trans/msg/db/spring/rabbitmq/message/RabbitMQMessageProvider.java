package com.cellulam.trans.msg.db.spring.rabbitmq.message;

import com.cellulam.trans.msg.db.core.message.MessageProcessor;
import com.cellulam.trans.msg.db.core.message.MessageSender;
import com.cellulam.trans.msg.db.core.spi.MessageProviderSPI;
import lombok.extern.slf4j.Slf4j;

/**
 * @author eric.li
 * @date 2022-06-11 19:30
 */
@Slf4j
public class RabbitMQMessageProvider implements MessageProviderSPI {
    private MessageProcessor messageConsumerProcessor;
    private MessageProcessor messageProducerProcessor;

    private RabbitMQSender messageConsumerSender;
    private RabbitMQSender messageProducerSender;

    public RabbitMQMessageProvider() {
        this.messageConsumerSender = new RabbitMQSender();
        this.messageProducerSender = new RabbitMQSender();
    }

    @Override
    public void registerMessageConsumerProcessor(MessageProcessor processor) {
        this.messageConsumerProcessor = processor;
    }

    @Override
    public void registerMessageProducerProcessor(MessageProcessor processor) {
        this.messageProducerProcessor = processor;
    }

    @Override
    public MessageSender getProducerMsgSender() {
        return this.messageProducerSender;
    }

    @Override
    public MessageSender getConsumerMsgSender() {
        return this.messageConsumerSender;
    }

    @Override
    public void start() {
        if(this.messageConsumerProcessor != null) {
            //TODO listen MQ success and invoke messageProcessor
            this.messageConsumerProcessor.process("test process message");
        }

        if(this.messageProducerProcessor != null) {
            //TODO listen ACK message and invoke ack
            this.messageProducerProcessor.process("test ack message");
        }
    }

    @Override
    public String getType() {
        return "RabbitMQ";
    }
}
