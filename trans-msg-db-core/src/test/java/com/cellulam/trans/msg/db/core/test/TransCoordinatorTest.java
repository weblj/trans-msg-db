package com.cellulam.trans.msg.db.core.test;

import com.cellulam.msg.db.test.concurrent.SyncExecutorService;
import com.cellulam.trans.msg.db.core.coordinator.TransCoordinator;
import com.cellulam.trans.msg.db.core.test.configuration.BaseInitiatedTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * @author eric.li
 * @date 2022-06-12 02:12
 */
public class TransCoordinatorTest extends BaseInitiatedTest {

    @BeforeClass
    public static void before() throws Exception {
        Field field = TransCoordinator.class.getDeclaredField("messageSendThreadPool");
        field.setAccessible(true);
        field.set(TransCoordinator.instance, new SyncExecutorService());
    }
    @Test
    public void testCommit() {
        TransCoordinator.instance.asyncCommit(UUID.randomUUID().toString(), "Test body");
    }
}
