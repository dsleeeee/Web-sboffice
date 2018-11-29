package kr.co.common.utils;

import kr.co.solbipos.application.session.user.enums.PwChgResult;
import kr.co.solbipos.base.store.emp.enums.EmpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * @Class Name : CmmUtil.java
 * @Description : 공통 util 함수 Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  노현수      최초생성
 *
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by KRPartners All right reserved.
 */
public class CmmUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CmmUtil.class);
//    private static String PASSWORD_REGEX =
//        "^(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*\\d+)(?=.*[^\\w\\sㄱ-ㅎㅏ-ㅣ가-힣]).{8,}$";
    private static String PASSWORD_REGEX = "^[A-Za-z0-9]{6,20}$";


    /**
      *
      * @param list
      * @param target
      * @return
      */
    public static boolean listIndexOf(List<String> list, String target) {
        return list.indexOf(target) > -1;
    }

    /**
      *
      * @param list
      * @param target
      * @return
      */
    public static boolean listIndexOf(List<String> list, String[] target) {
        for(int i=0; i<target.length; i++) {
            if(list.indexOf(target[i]) == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * <pre>
     * 패스워드 정책 체크
     * </pre>
     *
     * @param password
     * @return
     */
    public static boolean passwordPolicyCheck(String password) {
        if (isEmpty(password)) {
            LOGGER.warn("password-policy check password null. password:{}", password);
            return false;
        }

        // 비밀번호 8자리 이상, 비밀번호는 영문 대문자, 영문 소문자, 숫자, 특수기호 모두가 포함되어야 합니다.
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);

        Matcher m = pattern.matcher(password);

        if (m.find()) {
            return true;
        }

        LOGGER.info("password policy check false");
        return false;
    }

    /**
     *
     * 아이디 유효성 체크
     *
     * @param value
     * @return String
     * @comment
     * @author 노현수
     * @since 2018. 06. 08.
     */
    public static EmpResult checkUserId(String value) {

        int len = value.length();

        boolean flag = Pattern.matches(".*[ㄱ-ㅎ|ㅏ-ㅣ|가-힝]+.*$", value);
        boolean flag2 = Pattern.matches(".*[a-zA-Z]+.*", value);
        boolean flag3 = Pattern.matches("[a-zA-Z0-9]*", value);

        // ds 와 s로 시작하는 id는 본사 관리자 또는 매장 관리자의 id가 될 수 있어서
        // 해당 아이디로는 등록이 불가능하도록 함.

        if( len > 12 || len < 6 ) {
            return EmpResult.USER_ID_LENGHTH_REGEXP;
        } else if( flag == true ) {
            return EmpResult.USER_ID_CANNOT_USE_HANGEUL;
        } else if( flag2 == false ) {
            return EmpResult.USER_ID_MUST_CONTAIN_ENG_CAHR;
        } else if( flag3 == false ) {
            return EmpResult.USER_ID_ONLY_ENG_NUM_CHAR;
        } else if(value.startsWith("ds")) {
            if(Pattern.matches("[0-9]*", value.substring(2, len))){
                return EmpResult.USER_ID_DUPLICATE;
            }
        } else if(value.startsWith("s")) {
            if(Pattern.matches("[0-9]*", value.substring(1, len))){
                return EmpResult.USER_ID_DUPLICATE;
            } 
        }

        return EmpResult.SUCCESS;
    }

    /**
     *
     * 비밀번호 유효성 체크
     *
     * @param value
     * @return String
     * @throws Exception
     * @comment
     * @author 노현수
     * @since 2018. 06. 08.
     */
    public static PwChgResult checkPasswd(String value) throws Exception {

        String rs = "";
        int len = value.length();

        boolean flag = Pattern.matches("^[a-zA-Z0-9~!@#]*$", value);
        boolean flag1 = Pattern.matches(".*[0-9]+.*", value);
        boolean flag2 = Pattern.matches(".*[a-zA-Z]+.*", value);

        if(len < 6 || len > 20) {
            return PwChgResult.PASSWORD_NOT_MATCH_LENGTH;
        } else if (flag == false) {
            return PwChgResult.PASSWORD_NOT_MATCH_CHAR;
        } else if (flag1 == false) {
            return PwChgResult.PASSWORD_NOT_CONTAIN_NUMBER;
        } else if(flag2 == false) {
            return PwChgResult.PASSWORD_NOT_CONTAIN_ENG_CHAR;
        } else if(checkContinuous3Character(value)) {
            return PwChgResult.PASSWORD_CONTINUED_CHAR;
        } else if (checkDuplicateCharacter(value, 3)){
            return PwChgResult.PASSWORD_SAME_CHAR;
        } else {
            return PwChgResult.CHECK_OK;
        }
    }

    /**
     *
     * 연속된 문자열 3개 체크
     * ex)
     *  123word : true,
     *  wordcba : true
     * @param value
     * @return boolean
     * @comment
     * @author 노현수
     * @since 2018. 06. 08.
     */
    public static boolean checkContinuous3Character(String value) {

        byte[] b = value.getBytes();
        int p = value.length();
        // 연속된 3개의 문자 사용불가 (오름차순)
        for (int i = 0; i <= p-3; i++) {
            int b1 = b[i] + 1;
            int b2 = b[i + 1];
            int b3 = b[i + 1] + 1;
            int b4 = b[i + 2];

            if ((b1 == b2) && (b3 == b4)) {
                return true;
            } else {
                continue;
            }
        }
        // 연속된 3개의 문자 사용불가 (내림차순)
        for (int i = 0; i <= p-3; i++) {
            int b1 = b[i + 1] + 1;
            int b2 = b[i + 2] + 2;

            if ((b[i] == b1) && (b[i] == b2)) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    /**
     *
     * 연속된 같은 문자열 체크
     * ex)
     *  11word : true,
     *  wordcba : true
     *
     * @param value
     * @param limitCount
     * @return boolean
     * @throws Exception
     * @comment
     * @author 노현수
     * @since 2018. 06. 08.
     */
    public static boolean checkDuplicateCharacter(String value, int limitCount) {
        int p = value.length();
        byte[] b = value.getBytes();

        for (int i = 0; i <= p-limitCount; i++) {
            int b1 = b[i + 1];
            int b2 = b[i + 2];

            if ((b[i] == b1) && (b[i] == b2)) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }


    /** null 일 경우 대체값 반환 */
    public static <D extends Object> D nvl(D value, D replacement) {
        return value == null ? replacement : value;
    }

    /** object 자체 null 대체값 반환 */
    public static String checkNull(Object obj) {
        return obj == null ? "" : (String.valueOf(obj));
    }

}
