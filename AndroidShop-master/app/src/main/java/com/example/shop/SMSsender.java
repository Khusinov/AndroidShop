package com.example.shop;

import android.telephony.SmsManager;
import android.widget.Toast;

public class SMSsender {
    public Boolean smsSend(String phoneNumber,String message){

        try {
            SmsManager smgr = SmsManager.getDefault();
            smgr.sendTextMessage(phoneNumber,null,message,null,null);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
