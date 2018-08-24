package kr.co.common.exception;


import kr.co.common.data.enums.CodeType;

/**
 * 코드, 환경변수 관련 Exception
 *
 * @author 김지은
 *
 */
public class CodeException extends BizException {

    private static final long serialVersionUID = 1L;

    public CodeException(CodeType codeType, String codeCd, String responseURL) {
        super(codeType, codeCd, responseURL);
    }
}
