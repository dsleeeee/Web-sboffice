package kr.co.common.utils;

import kr.co.solbipos.application.session.user.enums.PwChgResult;
import kr.co.solbipos.base.store.emp.enums.EmpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
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
        // 2021.10.15 1. 현재 사용자 아이디 로직이 8~12 생성임으로 기존 7자리 이하의 소속코드(관리자/본사/매장)에 따른 기본 아이디는 생성될 수 없는 상태
        // 2021.10.15 2. 매장코드 직접입력 상태, 8자리 이상 매장코드가 생성되는 경우 기존 생성된 아이디와 중복될 수 있기 때문에 아이디 직접입력 및 [중복체크]가 들어갔음
        // 2021.10.15 3. 1번과 같은 이유로 ds s h 로 시작하는 아이디 생성 못하게 하는 부분은 제거해야 2번에서 8자리 이상의 정상적인 아이디가 생성됨


        if( len > 12 || len < 8 ) {
            return EmpResult.USER_ID_LENGHTH_REGEXP;
        } else if( flag == true ) {
            return EmpResult.USER_ID_CANNOT_USE_HANGEUL;
        } else if( flag2 == false ) {
            return EmpResult.USER_ID_MUST_CONTAIN_ENG_CAHR;
        } else if( flag3 == false ) {
            return EmpResult.USER_ID_ONLY_ENG_NUM_CHAR;
        } /*else if(value.startsWith("ds")) { // 데모 본사, 매장 사원
            if(Pattern.matches("[0-9]*", value.substring(2, len))){
                return EmpResult.USER_ID_DUPLICATE;
            }
        } else if(value.startsWith("s")) { // 운영 매장 사원
            if(Pattern.matches("[0-9]*", value.substring(1, len))){
                return EmpResult.USER_ID_DUPLICATE;
            }
        } else if(value.startsWith("h")) { // 운영 본사 사원
            if(Pattern.matches("[0-9]*", value.substring(1, len))){
                return EmpResult.USER_ID_DUPLICATE;
            }
        }*/

        return EmpResult.SUCCESS;
    }

    /**
     *
     * 아이디 유효성 체크(숫자만 입력도 가능, 현재는 BBQ 전용)
     *
     * @param value
     * @return String
     * @comment
     * @author 노현수
     * @since 2018. 06. 08.
     */
    public static EmpResult checkUserId2(String value) {

        int len = value.length();

        boolean flag = Pattern.matches(".*[ㄱ-ㅎ|ㅏ-ㅣ|가-힝]+.*$", value);
        boolean flag3 = Pattern.matches("[a-zA-Z0-9]*", value);

        // 2021.11.17 BBQ는 매장코드가 숫자로만 되어있어서, 매장코드를 웹 사용자 아이디로 그대로 사용하기 위해 숫자로만 이뤄진 웹 사용자 아이디도 생성 가능하도록 변경.

        if( len > 12 || len < 8 ) {
            return EmpResult.USER_ID_LENGHTH_REGEXP;
        } else if( flag == true ) {
            return EmpResult.USER_ID_CANNOT_USE_HANGEUL;
        } else if( flag3 == false ) {
            return EmpResult.USER_ID_ONLY_ENG_NUM_CHAR;
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

    /**
     * 스트링 Decoder
     * URLDecode 시 특문에 대한 예외처리
     *
     * @param str String
     * @return String
     * @comment
     * @author 노현수
     * @since 2018. 12. 20.
     */
    public static String decoder(String str) {

        String result = "";
        try {
            StringBuffer tempBuffer = new StringBuffer();
            int incrementor = 0;
            int dataLength = str.length();
            while (incrementor < dataLength) {
                char charecterAt = str.charAt(incrementor);
                if (charecterAt == '%') {
                    tempBuffer.append("<percentage>");
                } else if (charecterAt == '+') {
                    tempBuffer.append("<plus>");
                } else {
                    tempBuffer.append(charecterAt);
                }
                incrementor++;
            }
            result = tempBuffer.toString();
            result = URLDecoder.decode(result, "UTF-8");
            result = result.replaceAll("<percentage>", "%");
            result = result.replaceAll("<plus>", "+");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /** 변환 */
    public static String convertInputValue(String inputStr)
    {
        if(inputStr.indexOf("&"  ) !=-1){ inputStr = inputStr.replaceAll("&"     , "&amp;"   ); }
        if(inputStr.indexOf("<"  ) !=-1){ inputStr = inputStr.replaceAll("<"     , "&lt;"    ); }
        if(inputStr.indexOf(">"  ) !=-1){ inputStr = inputStr.replaceAll(">"     , "&gt;"    ); }
        if(inputStr.indexOf("\"" ) !=-1){ inputStr = inputStr.replaceAll("\""    , "&quot;"  ); }
        if(inputStr.indexOf("'"  ) !=-1){ inputStr = inputStr.replaceAll("'"     , "&apos;"  ); }
        if(inputStr.indexOf(" "  ) !=-1){ inputStr = inputStr.replaceAll(" "     , "&nbsp;"  ); }
        if(inputStr.indexOf("\n" ) !=-1){ inputStr = inputStr.replaceAll("\n"    , "<br />"  ); }

        return inputStr;
    }

    /** 변환 */
    public static String removeInputValue(String inputStr)
    {
        if(inputStr.indexOf("<script>")       !=-1){ inputStr = inputStr.replaceAll("<script>"        , ""); }
        if(inputStr.indexOf("</script>")      !=-1){ inputStr = inputStr.replaceAll("</script>"       , ""); }
        if(inputStr.indexOf("<javascript>")   !=-1){ inputStr = inputStr.replaceAll("<javascript>"    , ""); }
        if(inputStr.indexOf("</javascript>")  !=-1){ inputStr = inputStr.replaceAll("</javascript>"   , ""); }
        if(inputStr.indexOf("<vbscript>")     !=-1){ inputStr = inputStr.replaceAll("<vbscript>"      , ""); }
        if(inputStr.indexOf("</vbscript>")    !=-1){ inputStr = inputStr.replaceAll("</vbscript>"     , ""); }

        return inputStr;
    }

    /** 변환 */
    public static String removeInputValueHtml(String inputStr)
    {
        if(inputStr.toLowerCase().indexOf("script"                    )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("script"                  , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onabort"                   )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onabort"                 , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onactivate"                )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onactivate"              , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onafterprint"              )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onafterprint"            , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onafterupdate"             )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onafterupdate"           , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onbeforeactivate"          )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onbeforeactivate"        , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onbeforecopy"              )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onbeforecopy"            , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onbeforecut"               )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onbeforecut"             , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onbeforedeactivate"        )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onbeforedeactivate"      , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onbeforeeditfocus"         )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onbeforeeditfocus"       , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onbeforepaste"             )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onbeforepaste"           , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onbeforeprint"             )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onbeforeprint"           , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onbeforeunload"            )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onbeforeunload"          , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onbeforeupdate"            )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onbeforeupdate"          , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onblur"                    )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onblur"                  , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onbounce"                  )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onbounce"                , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("oncellchange"              )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("oncellchange"            , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onchange"                  )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onchange"                , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onclick"                   )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onclick"                 , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("oncontextmenu"             )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("oncontextmenu"           , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("oncontrolselect"           )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("oncontrolselect"         , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("oncopy"                    )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("oncopy"                  , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("oncut"                     )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("oncut"                   , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("ondataavailable"           )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("ondataavailable"         , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("ondatasetchanged"          )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("ondatasetchanged"        , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("ondatasetcomplete"         )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("ondatasetcomplete"       , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("ondblclick"                )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("ondblclick"              , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("ondeactivate"              )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("ondeactivate"            , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("ondrag"                    )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("ondrag"                  , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("ondragend"                 )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("ondragend"               , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("ondragenter"               )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("ondragenter"             , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("ondragleave"               )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("ondragleave"             , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("ondragover"                )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("ondragover"              , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("ondragstart"               )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("ondragstart"             , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("ondrop"                    )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("ondrop"                  , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onerror"                   )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onerror"                 , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onerrorupdate"             )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onerrorupdate"           , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onfilterchange"            )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onfilterchange"          , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onfinish"                  )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onfinish"                , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onfocus"                   )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onfocus"                 , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onfocusin"                 )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onfocusin"               , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onfocusout"                )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onfocusout"              , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onhelp"                    )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onhelp"                  , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onkeydown"                 )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onkeydown"               , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onkeypress"                )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onkeypress"              , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onkeyup"                   )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onkeyup"                 , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onlayoutcomplete"          )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onlayoutcomplete"        , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onload"                    )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onload"                  , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onlosecapture"             )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onlosecapture"           , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onmousedown"               )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onmousedown"             , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onmouseenter"              )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onmouseenter"            , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onmouseleave"              )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onmouseleave"            , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onmousemove"               )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onmousemove"             , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onmouseout"                )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onmouseout"              , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onmouseover"               )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onmouseover"             , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onmouseup"                 )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onmouseup"               , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onmousewheel"              )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onmousewheel"            , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onmove"                    )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onmove"                  , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onmoveend"                 )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onmoveend"               , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onmovestart"               )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onmovestart"             , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onpaste"                   )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onpaste"                 , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onpropertychange"          )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onpropertychange"        , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onreadystatechange"        )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onreadystatechange"      , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onreset"                   )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onreset"                 , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onresize"                  )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onresize"                , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onresizeend"               )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onresizeend"             , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onresizestart"             )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onresizestart"           , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onrowenter"                )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onrowenter"              , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onrowexit"                 )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onrowexit"               , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onrowsdelete"              )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onrowsdelete"            , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onrowsinserted"            )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onrowsinserted"          , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onscroll"                  )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onscroll"                , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onselect"                  )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onselect"                , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onselectionchange"         )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onselectionchange"       , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onselectstart"             )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onselectstart"           , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onstart"                   )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onstart"                 , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onstop"                    )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onstop"                  , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onsubmit"                  )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onsubmit"                , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onunload"                  )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onunload"                , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onbegin"                   )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onbegin"                 , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onend"                     )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onend"                   , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onrepeat"                  )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onrepeat"                , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("oncancel"                  )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("oncancel"                , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("oncanplay"                 )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("oncanplay"               , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("oncanplaythrough"          )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("oncanplaythrough"        , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onclose"                   )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onclose"                 , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("oncuechange"               )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("oncuechange"             , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("ondurationchange"          )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("ondurationchange"        , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onemptied"                 )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onemptied"               , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onended"                   )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onended"                 , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("oninput"                   )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("oninput"                 , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("oninvalid"                 )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("oninvalid"               , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onloadeddata"              )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onloadeddata"            , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onloadedmetadata"          )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onloadedmetadata"        , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onloadstart"               )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onloadstart"             , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onpause"                   )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onpause"                 , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onplay"                    )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onplay"                  , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onplaying"                 )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onplaying"               , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onprogress"                )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onprogress"              , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onratechange"              )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onratechange"            , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onseeked"                  )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onseeked"                , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onseeking"                 )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onseeking"               , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onshow"                    )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onshow"                  , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onstalled"                 )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onstalled"               , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onsuspend"                 )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onsuspend"               , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("ontimeupdate"              )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("ontimeupdate"            , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("ontoggle"                  )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("ontoggle"                , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onvolumechange"            )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onvolumechange"          , "사용할 수 없는 문자열"); }
        if(inputStr.toLowerCase().indexOf("onwaiting"                 )   !=-1){ inputStr = inputStr.toLowerCase().replaceAll("onwaiting"               , "사용할 수 없는 문자열"); }

        return inputStr;
    }
}
