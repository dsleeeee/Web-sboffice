package kr.co.common.utils.security;

import kr.co.common.utils.spring.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncUtil.class);
    public static final String SHA2_ALGORITHM_NAME = "SHA-256";

    public static String encrypt(String str) {
        if (StringUtil.isEmpty(str))
            return str;

        byte[] b;

        try {
            b = MessageDigest.getInstance(SHA2_ALGORITHM_NAME).digest(str.getBytes());
        } catch (NoSuchAlgorithmException e) {
            return "";
        }

        StringBuffer sbff = new StringBuffer();

        for (int i = 0, limit = b.length; i < limit; ++i)
            sbff.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));

        return sbff.toString();
    }

    static String eXtaHEX[] =
        {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    public static String byteToHex(byte[] baHex) {
        StringBuffer stringbuffer = new StringBuffer();
        for (int i = 0; i < baHex.length; i++) {
            stringbuffer.append(eXtaHEX[baHex[i] >> 4 & 0xf]);
            stringbuffer.append(eXtaHEX[baHex[i] & 0xf]);
        }

        return stringbuffer.toString();
    }

    public static String convertSha256ToHex(String _msg) {
        return byteToHex(convertSha256(_msg));
    }

    public static byte[] convertSha256(String _msg) {
        return convertSha256(_msg, Charset.defaultCharset());
    }

    public static byte[] convertSha256(String _msg, Charset _charSet) {
        try {
            return convertHash(_msg, SHA2_ALGORITHM_NAME, _charSet);
        } catch (Exception e) {
        }
        return null;
    }

    @SuppressWarnings("unlikely-arg-type")
    public static byte[] convertHash(String _msg, String _type, Charset _charSet) throws Exception {
        MessageDigest md = null;
        byte[] ditarr = null;
        try {
            md = MessageDigest.getInstance(_type);
            if ("".equals(_charSet)) {
                md.update(_msg.getBytes());
            } else {
                md.update(_msg.getBytes(_charSet));
            }
            ditarr = md.digest();
            return ditarr;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     *
     * 데이터를 SHA-256 암호화 한다.
     *
     * @param value
     * @return String
     * @throws Exception
     * @comment
     * @author 노현수
     * @since 2018. 06. 08.
     */
    public static String setEncSHA256(String value) {

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value.getBytes());

            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            StringBuffer hexString = new StringBuffer();
            String hex = null;
            for (int i = 0; i < byteData.length; i++) {
                hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("[EncUtil::setEncSHA256::NoSuchAlgorithmException] : " + e.getMessage());
            throw new RuntimeException();
        }
    }
}
