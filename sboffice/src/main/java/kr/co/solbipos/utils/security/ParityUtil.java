package kr.co.solbipos.utils.security;

public class ParityUtil {
    public ParityUtil() {
        // TODO Auto-generated constructor stub
    }

    private static String gPlistNumber = "7536082419";
    private static String gPlistStr = "WD6O8EJ0A391YVUGS7PLMX425KBTQCHFINRZ";

    /**
     * <b>이름 : </b>createParityNumber<br>
     * <b>설명 : </b>
     * 
     * <pre>
     *     페이티를 숫자로 생성한다.
     * </pre>
     * 
     * <b>작성자 : </b>2016. 1. 14. - 양행식<br>
     * <b>반환타입 : </b> String<br>
     * <b>변경이력 : </b> <br>
     * 
     * @param _oriMsg
     * @return
     */
    public static String createParityNumber(String _oriMsg) {
        return createParity(_oriMsg, gPlistNumber);
    }

    /**
     * <b>이름 : </b>createParityNumber2Leng<br>
     * <b>설명 : </b>
     * 
     * <pre>
     * 
     * </pre>
     * 
     * <b>작성자 : </b>2016. 2. 25. - 양행식<br>
     * <b>반환타입 : </b> String<br>
     * <b>변경이력 : </b> <br>
     * 
     * @param _oriMsg
     * @return
     */
    public static String createParityNumber2Leng(String _oriMsg) {
        String lTmp = createParityNumber(_oriMsg);
        return lTmp + createParityNumber(lTmp);
    }

    /**
     * <b>이름 : </b>createParityStr<br>
     * <b>설명 : </b>
     * 
     * <pre>
     *     페리티를 영문 숫자로 생성한다.
     * </pre>
     * 
     * <b>작성자 : </b>2016. 1. 14. - 양행식<br>
     * <b>반환타입 : </b> String<br>
     * <b>변경이력 : </b> <br>
     * 
     * @param _oriMsg
     * @return
     */
    public static String createParityStr(String _oriMsg) {
        return createParity(_oriMsg, gPlistStr);
    }

    /**
     * <b>이름 : </b>createParityStr2Leng<br>
     * <b>설명 : </b>
     * 
     * <pre>
     * 
     * </pre>
     * 
     * <b>작성자 : </b>2016. 2. 25. - 양행식<br>
     * <b>반환타입 : </b> String<br>
     * <b>변경이력 : </b> <br>
     * 
     * @param _oriMsg
     * @return
     */
    public static String createParityStr2Leng(String _oriMsg) {
        String lTmp = createParityStr(_oriMsg);
        return lTmp + createParityStr(lTmp);
    }

    /**
     * <b>이름 : </b>createParity<br>
     * <b>설명 : </b>
     * 
     * <pre>
     *     페러티 비트 생성
     * </pre>
     * 
     * <b>작성자 : </b>2016. 1. 14. - 양행식<br>
     * <b>반환타입 : </b> String<br>
     * <b>변경이력 : </b> <br>
     * 
     * @param _oriMsg
     * @return
     */
    public static String createParity(String _oriMsg, String _pList) {
        char[] lOriChar = _oriMsg.toCharArray();
        int chi = 0;
        int chisum = 0;
        for (int i = 0; i < lOriChar.length; i++) {
            chi = lOriChar[i];
            if (i % 2 == 0) {
                chi = chi * (5 + i);
            } else {
                chi = chi * (10 + i);
            }
            chisum += chi;
        }
        char[] pList = _pList.toCharArray();
        return String.valueOf(pList[chisum % pList.length]);
    }

    /**
     * <b>이름 : </b>checkParityString<br>
     * <b>설명 : </b>
     * 
     * <pre>
     *     영문 숫자로 된 페르티를 체크한다.
     * </pre>
     * 
     * <b>작성자 : </b>2016. 1. 14. - 양행식<br>
     * <b>반환타입 : </b> boolean<br>
     * <b>변경이력 : </b> <br>
     * 
     * @param _msg
     * @return
     */
    public static boolean checkParityString(String _msg) {
        return checkParity(_msg, gPlistStr);
    }

    /**
     * <b>이름 : </b>checkParityNumber<br>
     * <b>설명 : </b>
     * 
     * <pre>
     *     숫자로 된 페르티를 체크한다.
     * </pre>
     * 
     * <b>작성자 : </b>2016. 1. 14. - 양행식<br>
     * <b>반환타입 : </b> boolean<br>
     * <b>변경이력 : </b> <br>
     * 
     * @param _msg
     * @return
     */
    public static boolean checkParityNumber(String _msg) {
        return checkParity(_msg, gPlistNumber);
    }

    /**
     * <b>이름 : </b>checkParity<br>
     * <b>설명 : </b>
     * 
     * <pre>
     *     페러티비트 체크
     * </pre>
     * 
     * <b>작성자 : </b>2016. 1. 14. - 양행식<br>
     * <b>반환타입 : </b> boolean<br>
     * <b>변경이력 : </b> <br>
     * 
     * @param _msg
     * @return
     */
    public static boolean checkParity(String _msg, String _pList) {
        char[] lOriChar = _msg.toCharArray();
        int chi = 0;
        int chisum = 0;
        for (int i = 0; i < lOriChar.length - 1; i++) {
            chi = lOriChar[i];
            if (i % 2 == 0) {
                chi = chi * (5 + i);
            } else {
                chi = chi * (10 + i);
            }
            chisum += chi;
        }

        char[] pList = _pList.toCharArray();
        if (lOriChar[lOriChar.length - 1] == pList[chisum % pList.length]) {
            return true;
        }
        return false;
    }
}
