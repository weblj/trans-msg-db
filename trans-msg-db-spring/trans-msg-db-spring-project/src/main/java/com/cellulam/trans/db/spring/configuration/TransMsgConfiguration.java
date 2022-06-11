package com.cellulam.trans.db.spring.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * trans configuration
 *
 * @author eric.li
 * @date 2022-06-10 16:12
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TransMsgConfiguration {

}
