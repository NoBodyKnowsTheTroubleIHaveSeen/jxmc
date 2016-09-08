package org.whh.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.GzhSendRecord;

public interface GzhSendRecordDao
		extends PagingAndSortingRepository<GzhSendRecord, Long>, JpaSpecificationExecutor<GzhSendRecord> {
	GzhSendRecord findByMsgIdAndFakeId(String msgId,String fakeId);
}
