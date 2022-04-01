package kr.co.solbipos.adi.alimtalk.alimtalkSendType.service;

/**
 * @Class Name : ApiTokenReceiveVO.java
 * @Description : NHN API 응답(발신프로필 토큰 인증)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.25  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.03.25
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ApiTokenReceiveVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** header */
    private ApiTokenReceiveHeaderVO header;

    /** sender */
    private ApiTokenReceiveSenderVO sender;

    public ApiTokenReceiveHeaderVO getHeader() { return header; }

    public void setHeader(ApiTokenReceiveHeaderVO header) { this.header = header; }

    public ApiTokenReceiveSenderVO getSender() { return sender; }

    public void setSender(ApiTokenReceiveSenderVO sender) { this.sender = sender; }


    /** header */
    public class ApiTokenReceiveHeaderVO {

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

    /** sender */
    public class ApiTokenReceiveSenderVO {

        /** 카카오계정ID */
        private String plusFriendId;

        /** NHN매장계정 아이디 */
        private String senderKey;

        /** 사업자 카테고리 코드 */
        private String categoryCode;

        /** 플러스 친구 상태코드 */
        private String status;

        public String getPlusFriendId() { return plusFriendId; }

        public void setPlusFriendId(String plusFriendId) { this.plusFriendId = plusFriendId; }

        public String getSenderKey() { return senderKey; }

        public void setSenderKey(String senderKey) { this.senderKey = senderKey; }

        public String getCategoryCode() { return categoryCode; }

        public void setCategoryCode(String categoryCode) { this.categoryCode = categoryCode; }

        public String getStatus() { return status; }

        public void setStatus(String status) { this.status = status; }
    }
}