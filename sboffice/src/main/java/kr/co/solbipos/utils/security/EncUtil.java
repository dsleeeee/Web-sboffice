package kr.co.solbipos.utils.security;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import kr.co.solbipos.utils.spring.StringUtil;

public class EncUtil {
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

    public static byte[] convertHash(String _msg, String _type, Charset _charSet) throws Exception {
        MessageDigest md = null;
        byte[] ditarr = null;
        try {
            md = MessageDigest.getInstance(_type);
            if (_charSet.equals("")) {
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
}
