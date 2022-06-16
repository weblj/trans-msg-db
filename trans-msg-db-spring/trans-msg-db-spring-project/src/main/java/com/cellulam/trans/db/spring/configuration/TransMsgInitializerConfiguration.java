package com.cellulam.trans.db.spring.configuration;

import com.trans.db.facade.TransMessageProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 * @author eric.li
 * @date 2022-06-16 12:43
 */
@Configuration
@AutoConfigureAfter(TransMsgConfiguration.class)
public class TransMsgInitializerConfiguration implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof TransMessageProcessor) {
            ConsumerProcessorHolder.holder.addMessageProcessors((TransMessageProcessor<?>) bean);
        }
        return bean;
    }
}
