package kr.co.common.utils.spring;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Random;
import java.util.UUID;
import javax.swing.text.MaskFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.MD5Utils;

/**
 * @author 정용길
 *
 */
public class StringUtil extends StringUtils {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);
    private final static char UNDER_SCORE = '_';

    public static String generateUUID() {
        return MD5Utils.md5toHex(System.nanoTime() + "-" + UUID.randomUUID().toString());
    }

    /**
     * CamelCase 문자열로 변환
     * 
     * <pre>
     * ex1) SOME_TEXT &gt; someText
     * ex2) some_text &gt; someText
     * </pre>
     * 
     * @param str String
     * @return A camelcase string type name.
     */
    public static String toCamelCaseName(String str) {
        if (isEmpty(str) || (str.indexOf(UNDER_SCORE) < 0 && Character.isLowerCase(str.charAt(0))))
            return str;

        StringBuffer sb = new StringBuffer();
        boolean nextUpper = false;
        char currentChar = '\0';

        for (int i = 0, limit = str.length(); i < limit; ++i) {
            currentChar = str.charAt(i);

            if (currentChar == UNDER_SCORE)
                nextUpper = true;
            else {
                if (nextUpper) {
                    nextUpper = false;
                    sb.append(Character.toUpperCase(currentChar));
                } else
                    sb.append(Character.toLowerCase(currentChar));
            }
        }

        return sb.toString();
    }

    /**
     * 정해진 길이만큼 0으로 채움
     * 
     * @param str String
     * @param len int
     * @return String
     */
    public static String lpad(String str, int len) {
        return lpad(str, len, "0");
    }

    /**
     * 정해진 길이만큼 입력받은 문자료 채움
     * 
     * @param str String
     * @param len int
     * @param pad String
     * @return String
     */
    public static String lpad(String str, int len, String pad) {
        if (isEmpty(str) || isEmpty(pad) || str.length() > len)
            return str;

        if ("0".equals(pad))
            return String.format("%0" + len + "d", Integer.parseInt(str));

        int max = len - str.length();

        StringBuffer sb = new StringBuffer(str);

        for (int i = 0; i < max; i++)
            sb.insert(0, pad);

        return sb.toString();
    }

    /**
     * byteSize 이상 문자는 ...로 줄임
     * 
     * @param str String
     * @param byteSize int
     * @return String
     */
    public static String getStrCut(String str, int byteSize) {
        return getStrCut(str, byteSize, "...");
    }

    /**
     * byteSize 이상 문자는 입력받은 문자로 줄임
     * 
     * @param str String
     * @param byteSize int
     * @param ellipsis String
     * @return String
     */
    public static String getStrCut(String str, int byteSize, String ellipsis) {
        if (isEmpty(str) || getByteLength(str) <= byteSize)
            return str;

        int rSize = 0;

        for (int len = 1, limit = str.length(); len < limit; len++, rSize++) {
            if (str.charAt(rSize) > 0x007F)
                len++;

            if (len > byteSize)
                break;
        }

        return str.substring(0, rSize) + ellipsis;
    }

    /**
     * 금액 형태로 바꾼다. ex)23232323.3 -&gt; 23,232,323.3
     * 
     * @param num String 문자열 숫자
     * @return 금액 형태의 숫자값 String
     */
    public static String Digit2Comma(String num) {
        if (isEmpty(num))
            return "0";

        if (num.indexOf(".") == -1)
            return String.format("%,d", Long.parseLong(num));

        int pointSize = num.length() - (num.indexOf(".") + 1);

        return String.format("%,." + pointSize + "f", Double.parseDouble(num));
    }

    /**
     * 문자열을 입력한 포맷에 따라 변환 후 반환
     * 
     * @param string String
     * @param mask String
     * @return String
     */
    public static String formatString(String string, String mask) {
        try {
            MaskFormatter mf = new MaskFormatter(mask);

            mf.setValueContainsLiteralCharacters(false);

            return mf.valueToString(string);
        } catch (ParseException e) {
            if (LOGGER.isErrorEnabled())
                LOGGER.error(e.getMessage());

            return "";
        }
    }

    /**
     * 문자열의 길이를 바이트 수로 반환(UTF-8)
     * 
     * @param str String
     * @return byteSize int
     */
    public static int getByteLength(String str) {
        return getByteLength(str, "UTF-8");
    }

    /**
     * 문자열의 길이를 입력받은 인코딩 타입의 바이트 수로 반환
     * 
     * @param str String
     * @param charset String
     * @return byteSize int
     */
    public static int getByteLength(String str, String charset) {
        try {
            return isEmpty(str) ? 0 : str.getBytes(charset).length;
        } catch (UnsupportedEncodingException e) {
            return str.getBytes().length;
        }
    }

    /**
     * 문자열이 비어있을경우 first 아닐경우 second
     * 
     * @param str String
     * @param first String
     * @param second String
     * @return String
     */
    public static String getFirstIfEmptyOrSecond(String str, String first, String second) {
        return isEmpty(str) ? first : second;
    }

    /**
     * Object 가 Null 인 경우 value, 아닐 경우 String 으로 변환
     * 
     * @param object Object
     * @param value String
     * @return String
     */
    public static String getOrDefault(Object object, String value) {
        return object != null ? object.toString().trim() : value;
    }

    /**
     * Object 가 Null 인 경우 "", 아닐 경우 String 으로 변환
     * 
     * @param object Object
     * @return String
     */
    public static String getOrBlank(Object object) {
        return getOrDefault(object, "");
    }

    /**
     * 문자열 체크
     * 
     * @param str String...
     * @return boolean
     */
    public static boolean isEmpties(String... str) {
        for (String s : str)
            if (isEmpty(s))
                return true;

        return false;
    }

    /**
     * 문자열 체크(한개라도 있는지 체크)
     * 
     * @param str String...
     * @return boolean
     */
    public static boolean isNotEmpties(String... str) {
        for (String s : str)
            if (!isEmpty(s))
                return false;

        return true;
    }

    /**
     * 문자열 중복 체크(중복된 문자열이 한개라도 있는지 체크)
     * 
     * @param str String...
     * @return boolean
     */
    public static boolean isDuplicate(String[] str) {
        for (int i = 0; i < str.length; i++) {
            for (int j = 0; j < str.length; j++) {
                if (i == j)
                    continue;

                if (str[i].equals(str[j])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @기능 : checkObjectDiff ()
     * @param Object
     * @param Object
     * @param String[]
     * @return String
     */
    public static String checkObjectDiff(DefaultMap<Object> o1, DefaultMap<Object> o2,
            String compare[]) {
        String output = "";

        if (o1 == null)
            return "ERROR";
        if (o2 == null)
            return "ERROR";
        if (compare == null)
            return "ERROR";

        for (int j = 0; j < compare.length; j++) {
            if (!o2.getStr(compare[j]).equals(o1.getStr(compare[j]))) {
                output = output + compare[j] + " : [" + o2.getStr(compare[j]) + "] → ["
                        + o1.getStr(compare[j]) + "]" + String.valueOf((char) 31);
            }
        }

        return output;
    }

    /**
     * path 마지막의 / 또는 \를 삭제함
     * 
     * @param path
     * @return String
     */
    public static String removeTrailingSlash(String path) {
        return path.replaceAll("[\\/\\\\]+$", "");
    }

    public static String convertDateFormat(String ymd) {
        String convertDate = "";

        if (ymd != null) {
            if (ymd.length() == 10) {
                convertDate = ymd.replaceAll("/", "");
                convertDate = convertDate.substring(4, 8) + convertDate.substring(2, 4)
                        + convertDate.substring(0, 2);
            } else if (ymd.length() == 7) {
                convertDate = ymd.replaceAll("/", "");
                convertDate = convertDate.substring(2, 6) + convertDate.substring(0, 2);
            }
        }

        return convertDate;
    }

    /**
     * object 를 json 형태로 변경 key=value > "key"="value"
     * 
     * @param obj
     * @return
     */
    public static String convertToJson(Object obj) {
        String jsonStr = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonStr = mapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    /**
     * string 타입의 절반을 * 로 표시
     * @param s
     * @return
     */
    public static String strMaskingHalf(String s) {
        String t = s.substring((s.length() / 2) + 1, s.length());
        String m = "";
        for (int i = 0; i < t.length(); i++) {
            m += "*";
        }
        return s.replaceAll(t, m);
    }
    
    /**
      * size 에 맞는 랜덤 숫자를 리턴
      * @param size
      * @return
      */
    public static String getRandomNumber(int size) {
        Random rnd =new Random();
        StringBuffer buf =new StringBuffer();
        for(int i=0;i<size;i++){
            buf.append((rnd.nextInt(9))); 
        }
        return buf.toString();
    }
    
    /**
      * 
      * @param param1
      * @param param2
      * @return
      */
    public static boolean equalsNull(String param1, String param2) {
        if(isEmpty(param1) && isEmpty(param2)) {
            return true;
        }
        else if(!isEmpty(param1) && param1.equals(param2)) {
            return true;
        }
        else if(!isEmpty(param2) && param2.equals(param1)) {
            return true;
        }
        return false;
    }
}


