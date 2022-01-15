package com.library.services.impl;

import com.library.services.OrangeSMSService;
import com.library.utils.SendSMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrangeSMSServiceImpl implements OrangeSMSService {

    @Autowired
    private SendSMS sendSMS;

    @Override
    public void sendSMS(String receiver, String message) {
        sendSMS.sendSMS(receiver, message);
    }
}
