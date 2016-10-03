package kg.freesms.util;

import java.util.ArrayList;
import java.util.List;

import kg.freesms.entity.Phone;

import com.javacodegeeks.kannel.api.SMSManager;
import com.javacodegeeks.kannel.api.SMSPushRequestException;

public class KannelSmsSender {

    private String SEND_SMS_USERNAME = "kannel"; // sendsms-user username = kannel from kannel.conf
    private String SEND_SMS_PASSWORD = "kannel"; // sendsms-user password = kannel from kannel.conf
    private String HOST = "localhost";
    private int PORT = 13013;
    
    /**
     * constructors
     */
    public KannelSmsSender() {
    }
    
    public KannelSmsSender(String username, String password, String host, int port) {
        this.SEND_SMS_USERNAME = username;
        this.SEND_SMS_PASSWORD = password;
        this.HOST = host;
        this.PORT = port;
    }
    
    public String sendSMS(List<Phone> phones, String message) {
        
        String response = "";
        
        SMSManager smsManager = SMSManager.getInstance();
        
        // We can change the prefetch size of the background worker thread
        smsManager.setMessagesPrefetchSize(30);
        
        // We can change the send message rate
        smsManager.setMessagesSendRate(65);
        
        System.out.println("====phones.size()====" + phones.size());
        
        List<String> recipientsGroupA = new ArrayList<>();
        
        for (Phone phone : phones) {
            System.out.println("====phone====" + phone);
            recipientsGroupA.add(phone.getPhoneNumber());
        }
        
        try {
            
            // Send SMS to multiple recipients
            response = smsManager.sendSMS(HOST, String.valueOf(PORT), SEND_SMS_USERNAME, SEND_SMS_PASSWORD, "1234", recipientsGroupA, message);
            
        } catch (SMSPushRequestException smsPushRequestException) {
            smsPushRequestException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        
        return response;
    }
    
}
