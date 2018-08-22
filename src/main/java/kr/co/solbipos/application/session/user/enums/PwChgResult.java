package kr.co.solbipos.application.session.user.enums;

import org.apache.ibatis.type.MappedTypes;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.common.data.enums.CodeEnum;
import kr.co.common.data.handler.CodeEnumTypeHandler;

/**
 * 패스워드 변경 시도 결과
 *
 * @author bjcho
 */
public enum PwChgResult implements CodeEnum  {

    /** 비밀번호 일치 하지 않음 */
    PASSWORD_NOT_MATCH("PASSWORD_NOT_MATCH"),
    /** 변경 패스워드가 기존 패스워드와 동일함. */
    PASSWORD_NEW_OLD_MATH("PASSWORD_NEW_OLD_MATH"),
    /** 패스워드 정책이 맞지 않음 */
    PASSWORD_REGEXP("PASSWORD_REGEXP"),
    /** 비밀번호는 최소 6자 이상 20자 이하만 가능 합니다. */
    PASSWORD_NOT_MATCH_LENGTH("PASSWORD_NOT_MATCH_LENGTH"),
    /** 비밀번호는 숫자와 영문, 특수문자(!,@,$,~)만 사용 가능합니다 */
    PASSWORD_NOT_MATCH_CHAR("PASSWORD_NOT_MATCH_CHAR"),
    /** 비밀번호는 반드시 숫자가 포함되어야 합니다. */
    PASSWORD_NOT_CONTAIN_NUMBER("PASSWORD_NOT_CONTAIN_NUMBER"),
    /** 비밀번호는 영문자가 반드시 포함되어야 합니다. */
    PASSWORD_NOT_CONTAIN_ENG_CHAR("PASSWORD_NOT_CONTAIN_ENG_CHAR"),
    /** 숫자 또는 알파벳 순서대로 3자이상 사용하는 비밀번호는 사용할 수 없습니다. */
    PASSWORD_CONTINUED_CHAR("PASSWORD_CAN_NOT_BE_USED_CONTINUED_CHAR"),
    /** 동일한 문자 또는 숫자를 3자 이상 사용할 수 없습니다. */
    PASSWORD_SAME_CHAR("PASSWORD_CAN_NOT_BE_USED_SAME_CHAR_CONTINUE_THREE_MORE"),
    /** 변경 패스워드가 2개가 서로 맞지 않음 */
    NEW_PASSWORD_NOT_MATCH("NEW_PASSWORD_NOT_MATCH"),
    /** 비정상적인 접근 */
    UUID_NOT_MATCH("UUID_NOT_MATCH"),
    /** UUID 유효지간 지남 */
    UUID_LIMIT_ERROR("UUID_LIMIT_ERROR"),
    /** 유져 정보가 없음 */
    EMPTY_USER("EMPTY_USER"),
    /** id 정보가 맞지 않음 */
    ID_NOT_MATCH("ID_NOT_MATCH"),
    /** 사용하지 않는 계정 */
    NOT_USE("NOT_USE"),
    /** 체크 오류 */
    CHECK_NOK("CHECK_NOK"),
    /** 체크 완료 */
    CHECK_OK("CHECK_OK");

    private String code;

    PwChgResult(String code) {
        this.code = code;
    }

    @MappedTypes(PwChgResult.class)
    public static class TypeHandler extends CodeEnumTypeHandler<PwChgResult> {
        public TypeHandler() {
        super(PwChgResult.class);
        }
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }
}
