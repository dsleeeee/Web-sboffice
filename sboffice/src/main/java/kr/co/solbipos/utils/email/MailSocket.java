package kr.co.solbipos.utils.email;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import kr.co.solbipos.system.Prop;
import kr.co.solbipos.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 메일발송 소켓 Class
 * 
 * @author bjcho
 *
 */

@Slf4j
@Component
public class MailSocket {
    @Autowired
    Prop prop;

    private String host;
    private int port;

    private String comeData = "";
    private String sendData = "";
    private Socket mailSocket = null;

    private InputStream is = null;
    private OutputStream ous = null;

    private BufferedReader br = null;
    private BufferedWriter bw = null;

    private boolean result = false;

    private long startTime = 0;
    private long endTime = 0;

    /**
     * 메일소켓 초기화
     * 
     * @param SendData String
     * @return MailSocket
     */
    public MailSocket init(String SendData) {
        host = prop.emailHost;
        port = prop.emailPort;

        comeData = "";
        sendData = "";

        mailSocket = null;

        is = null;
        ous = null;

        br = null;
        bw = null;

        result = false;

        startTime = 0;
        endTime = 0;

        sendData = SendData;

        return this;
    }

    /**
     * 응답 메시지 Getter
     * 
     * @return String
     */
    public String getComeData() {
        return comeData;
    }

    /**
     * 발송 메시지 Getter
     * 
     * @return String
     */
    public String getSendData() {
        return sendData;
    }

    /**
     * 메일소켓 메시지 전송
     * 
     * @return boolean
     * @throws IOException IOException
     * @throws Exception Exception
     */
    public boolean send() throws IOException, Exception {
        return send(false);
    }

    /**
     * 메일소켓 메시지 전송
     * 
     * @param baRecv boolean
     * @return boolean
     * @throws IOException IOException
     * @throws Exception Exception
     */
    public boolean send(boolean baRecv) throws IOException, Exception {
        // 전문결과 메세지 담기
        StringBuffer logMsg = new StringBuffer();

        try {
            try {
                mailSocket = new Socket(host, port);
                mailSocket.setSoTimeout(10 * 3000); // 30초
            } catch (java.net.ConnectException ce) {
                StringBuffer msg1 =
                        new StringBuffer("ConnectException occurred at MailSocket.send.try.ce\n")
                                .append("[MailSocket - CreateSocket Error] - IP : " + host
                                        + " PORT : " + port + "\n")
                                .append("SEND_DATA : [" + sendData + "]\n");

                log.error(msg1.toString(), ce);

                msg1 = null;
                ce.printStackTrace();
                throw ce;
            } catch (IOException ioe) {
                StringBuffer msg1 =
                        new StringBuffer("IOException occurred at MailSocket.send.try.ioe\n")
                                .append("[MailSocket - CreateSocket Error] - IP : " + host
                                        + " PORT : " + port + "\n")
                                .append("SEND_DATA : [" + sendData + "]\n");
                log.error(msg1.toString(), ioe);

                msg1 = null;
                ioe.printStackTrace();
                throw ioe;
            }

            startTime = System.currentTimeMillis();
            logMsg.append("\n");
            logMsg.append("Mail Server IP : " + host + " PORT : " + port + "\n");
            logMsg.append("SEND_DATE : ")
                    .append(DateUtil.date2string(new Date(), "yyyy-MM-dd HH:mm:ss")).append("\n");

            is = mailSocket.getInputStream();
            ous = mailSocket.getOutputStream();

            br = new BufferedReader(new InputStreamReader(is, "8859_1"));
            bw = new BufferedWriter(new OutputStreamWriter(ous, "KSC5601"));

            bw.write(sendData);
            logMsg.append("SEND_DATA : [").append(sendData).append("]\n\n");

            log.debug(String.format("Mail SendData:%s", sendData));

            bw.flush();

            if (baRecv) {
                try {
                    if ((comeData = br.readLine()) != null) {
                        comeData = new String(new String(comeData.getBytes("8859_1"), "EUC_KR"));// CharConversion.E2K_EUC(comeData);
                        result = true;

                        logMsg.append("RECV_DATE : ")
                                .append(DateUtil.date2string(new Date(), "yyyy-MM-dd HH:mm:ss"))
                                .append("\n");
                        logMsg.append("RECV_DATA : [").append(comeData).append("]\n");

                        endTime = System.currentTimeMillis();
                        logMsg.append("걸린 시간(ms)").append(Long.toString((endTime - startTime), 10))
                                .append("\n\n");
                    }
                } catch (IOException ioe2) {
                    StringBuffer msg1 =
                            new StringBuffer("IOException occurred at MailSocket.send.try.ioe2 ");
                    msg1.append("\n");
                    msg1.append("[MailSocket - ReadLine Error] - IP : " + host + " PORT : " + port
                            + "\n");
                    msg1.append("SEND_DATA : [" + sendData + "]\n");

                    log.error(msg1.toString(), ioe2);

                    msg1 = null;
                    ioe2.printStackTrace();
                    throw ioe2;
                }
            }

        } catch (Exception ex) {
            StringBuffer msg1 = new StringBuffer("Exception occurred at MailSocket.send.try.ex ");
            msg1.append("\n");
            msg1.append("[MailSocket - Exception Error] - IP : " + host + " PORT : " + port + "\n");
            msg1.append("SEND_DATA : [" + sendData + "]\n");

            log.error(msg1.toString(), ex);

            msg1 = null;
            ex.printStackTrace();
            throw ex;
        } finally {
            if (br != null)
                br.close();
            if (bw != null)
                bw.close();
            if (mailSocket != null)
                mailSocket.close();

            log.debug(logMsg.toString());

            logMsg = null;
        }

        return result;
    }
}
