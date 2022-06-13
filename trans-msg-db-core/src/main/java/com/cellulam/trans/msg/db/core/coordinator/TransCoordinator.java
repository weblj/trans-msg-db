package com.cellulam.trans.msg.db.core.coordinator;

import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.exceptions.TransMessageSendException;
import com.cellulam.trans.msg.db.core.factories.MessageSenderFactory;
import com.cellulam.trans.msg.db.core.message.MessageSender;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * transaction coordinator
 *
 * @author eric.li
 * @date 2022-06-11 21:32
 */
@Slf4j
public class TransCoordinator {

    private final MessageSender consumerMessageSender;

    private final ExecutorService messageSendThreadPool;

    private TransCoordinator() {
        this.consumerMessageSender = MessageSenderFactory.getConsumerSender(TransContext.getConfiguration().getMessageProviderType());
        this.messageSendThreadPool = Executors.newFixedThreadPool(TransContext.getConfiguration().getMessageSendThreadPoolSize());
    }

    public final static TransCoordinator instance = new TransCoordinator();

    /**
     * async commit
     *
     * @param transMessage
     */
    public void asyncCommit(String transType, String transMessage) {
        messageSendThreadPool.execute(() -> this.commit(transType, transMessage));
    }

    /**
     * sync commit
     *
     * @param transMessage
     */
    public void commit(String transType, String transMessage) {
        try {
            this.sendMessage(transType, transMessage);
        } catch (Exception e) {
            log.error(String.format("Failed to commit,  message: %s", transMessage), e);
        }
    }

    private void sendMessage(String transType, String message) {
        try {
            this.consumerMessageSender.send(transType, message);
        } catch (Exception e) {
            throw new TransMessageSendException("Failed to send message: " + message, e);
        }
    }
}
