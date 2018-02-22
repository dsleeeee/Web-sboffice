package kr.co.solbipos.enums;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.solbipos.system.CodeEnumTypeHandler;

/**
 * 로그인 결과
 * TB_WB_LOGIN_HIST 테이블에 stat_cd 에 저장 
 * 
 * @author bjcho
 */
public enum SystemCode implements CodeEnum  {
    
    OK("OK"),
    LOGIN_FAIL("LOGIN_FAIL"), /* 로그인 실패 */
    PARAM_EMPTY("PARAM_EMPTY"), /* 파라미터 입력 안했을 경우 */
    ABNORMAL_ACCESS("ABNORMAL_ACCESS"), /* 비정상적인 접근 */
//    NOT_EXISTS_ID("NOT_EXISTS_ID"), /* 존재하지 않는 ID */
//    LOGIN_BLOCK("LOGIN_BLOCK"), /* 유져 lock 상태 */
//    PASSWORD_EXPIRE("PASSWORD_EXPIRE"), /* 패스워드 만료 : 변경 필요 */
//    PASSWORD_CHANGE("PASSWORD_CHANGE"), /* 패스워드 변경 필요 */
//    PASSWORD_ERROR("PASSWORD_ERROR"), /* 패스워드 틀림 */
//    PASSWORD_REUSED("PASSWORD_REUSED"), /* 패스워드 변경시 최근(5) 패스워드 사용 */
//    PASSWORD_NOT_MATCHING("PASSWORD_NOT_MATCHING"), /* 패스워드 변경시 변경 패스워드 틀림 */
//    PASSWORD_NOT_POLICY("PASSWORD_NOT_POLICY"), /* 패스워드 정책 맞지 않음 */
//    PASSWORD_NOT_AUTH("PASSWORD_NOT_AUTH"), /* 패스워드 변경 권한 없음 */
//    PASSWORD_CHANGE_OK("PASSWORD_CHANGE_OK"), /* 패스워드 변경 성공 */
    DUPLICATE_DATA("DUPLICATE_DATA"), // 해당 데이터가 이미 존재합니다.
    NOT_SELECTED("NOT_SELECTED"), // 해당 데이터가 존재하지 않습니다.
    NOT_INSERTED("NOT_INSERTED"), // 해당 데이터를 등록할 수 없습니다.
    NOT_UPDATED("NOT_UPDATED"), // 해당 데이터를 수정할 수 없습니다.
    NOT_DELETED("NOT_DELETED"), // 해당 데이터를 삭제할 수 없습니다.
    NOT_EXISTS_ARTICLE("NOT_EXISTS_ARTICLE"), // 게시글이 존재하지 않습니다.
    DOWNLOAD_FAILURE("DOWNLOAD_FAILURE"), // 다운로드 실패
    PARAM_INVALID("PARAM_INVALID"), // 요청 파라미터 값이 잘못되었습니다.
    ADMIN_POLICY_CHANGE("ADMIN_POLICY_CHANGE"); // 접속자 정보가 변경되어 로그아웃 처리 됨

    private String code;
    private SystemCode[] value;
  
    SystemCode(String code, SystemCode... values) {
        this.code = code;
        this.value = values;
    }
  
    public SystemCode[] getValues() {
        return value;
    }
   
    @MappedTypes(SystemCode.class)
    public static class TypeHandler extends CodeEnumTypeHandler<SystemCode> {
        public TypeHandler() {
            super(SystemCode.class);
        }
    }
     
    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
