package kr.co.solbipos.adi.alimtalk.alimtalkSendType.service;

/**
 * @Class Name : ApiSendCancelReceiveVO.java
 * @Description : NHN API 응답(메세지발송 취소)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.20  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.04.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ApiSendCancelReceiveVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** header */
    private ApiSendCancelReceiveHeaderVO header;

    public ApiSendCancelReceiveHeaderVO getHeader() { return header; }

    public void setHeader(ApiSendCancelReceiveHeaderVO header) { this.header = header; }


    /** header */
    public class ApiSendCancelReceiveHeaderVO {

        /** 결과코드 */
        private Integer resultCode;

        /** 결과메세지 */
        private String resultMessage;

        /** 성공여부 */
        private String isSuccessful;

        public Integer getResultCode() { return resultCode; }

        public void setResultCode(Integer resultCode) { this.resultCode = resultCode; }

        public String getResultMessage() { return resultMessage; }

        public void setResultMessage(String resultMessage) { this.resultMessage = resultMessage; }

        public String getIsSuccessful() { return isSuccessful; }

        public void setIsSuccessful(String isSuccessful) { this.isSuccessful = isSuccessful; }
    }
}