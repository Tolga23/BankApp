package com.thardal.bankapp.log.service.entityservice;

import com.thardal.bankapp.global.service.BaseEntityService;
import com.thardal.bankapp.log.dao.LogDetailsDao;
import com.thardal.bankapp.log.entity.LogDetails;
import org.springframework.stereotype.Service;

@Service
public class LogDetailsEntityService extends BaseEntityService<LogDetails, LogDetailsDao> {
    public LogDetailsEntityService(LogDetailsDao dao) {
        super(dao);
    }
}
