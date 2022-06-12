package com.cellulam.trans.msg.db.core.recovery;

import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.enums.TransStatus;
import com.cellulam.trans.msg.db.core.factories.DynamicConfigFactory;
import com.cellulam.trans.msg.db.core.repository.TransRepository;
import com.cellulam.trans.msg.db.core.repository.model.Transaction;
import com.cellulam.trans.msg.db.core.spi.DynamicConfigSPI;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @author eric.li
 * @date 2022-06-12 22:20
 */
public class BranchTransRegister {
    private final DynamicConfigSPI dynamicConfigSPI;

    private BranchTransRegister() {
        this.dynamicConfigSPI = DynamicConfigFactory.getInstance(TransContext.getConfiguration().getDynamicConfigType());
    }

    public final static BranchTransRegister instance = new BranchTransRegister();

    public boolean registerBranchTrans(Transaction trans) {
        List<String> consumers = dynamicConfigSPI.getConsumers(trans.getTransType(), trans.getProducer());
        if (CollectionUtils.isEmpty(consumers)) {
            TransRepository.instance.finishTrans(trans, TransStatus.IGNORED);
            return false;
        }
        consumers.parallelStream()
                .forEach(x -> TransRepository.instance.registerBranchTrans(x, trans));
        return true;
    }
}
