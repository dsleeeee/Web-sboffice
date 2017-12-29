package kr.co.solbipos.utils.security;


import java.io.UnsupportedEncodingException;
import java.util.Base64;
import kr.co.solbipos.utils.spring.StringUtil;

// import th.co.treepay.lib.kmc.crypto.KMCCrypto;
// import th.co.treepay.lib.kms.lib.base.ErrInfo;

/**
 * 암복호화 관련 유틸
 * 
 * @author SL LEE
 *
 */
public class CryptoUtil {

    public static final String strTokenizeKeySetId = "KID-nNqz2WkWaY4R8s95";


    public static String getMaskedData(String str) {
        // ErrInfo errInfo = new ErrInfo();
        try {
            if (!StringUtil.isEmpty(str)) {

                // str = KMCCrypto.getMaskedData( str , errInfo );
                // if(errInfo.getErrNo()!=0){
                // return "";
                // }
            }
        } catch (Exception e) {
            return "";
        }
        return str;
    }

    public static String getMaskedDataDCB(String str) {

        byte[] decoded = Base64.getDecoder().decode(str);
        try {
            return (new String(decoded, "UTF-8")).trim();
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return "";
        }
    }

    public static String getEncodeDataDCB(String str) {
        try {
            return Base64.getEncoder().encodeToString(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return "";
        }
    }


    /*
     * public static String getTokenizeData(KMCCrypto.DataType eDataType, String str ) { ErrInfo
     * errInfo = new ErrInfo(); try { if(!StringUtil.isEmpty( str )){
     * 
     * str = KMCCrypto.tokenizeData(eDataType, strTokenizeKeySetId , 0,str,errInfo);
     * 
     * if(errInfo.getErrNo()!=0){ return ""; } } } catch (Exception e) { return ""; } return str; }
     */


    public static String getTokenizeData(String str) {
        // return getTokenizeData(KMCCrypto.DataType.CREDIT_CARD_NO,str);
        return "";
    }

}
