package kr.co.solbipos.adi.alimtalk.alimtalkTemplate.service;

/**
 * @Class Name : ApiTemplateImageReceiveVO.java
 * @Description : NHN API 응답(템플릿 이미지 등록)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.30  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.06.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ApiTemplateImageReceiveVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** header */
    private ApiTemplateImageReceiveHeaderVO header;

    /** templateImage */
    private ApiTemplateImageReceiveTemplateImageVO templateImage;

    public ApiTemplateImageReceiveHeaderVO getHeader() { return header; }

    public void setHeader(ApiTemplateImageReceiveHeaderVO header) { this.header = header; }

    public ApiTemplateImageReceiveTemplateImageVO getTemplateImage() { return templateImage; }

    public void setTemplateImage(ApiTemplateImageReceiveTemplateImageVO templateImage) { this.templateImage = templateImage; }


    /** header */
    public class ApiTemplateImageReceiveHeaderVO {

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

    /** templateImage */
    public class ApiTemplateImageReceiveTemplateImageVO {

        /** 이미지명 */
        private String templateImageName;

        /** 이미지 URL */
        private String templateImageUrl;

        public String getTemplateImageName() { return templateImageName; }

        public void setTemplateImageName(String templateImageName) { this.templateImageName = templateImageName; }

        public String getTemplateImageUrl() { return templateImageUrl; }

        public void setTemplateImageUrl(String templateImageUrl) { this.templateImageUrl = templateImageUrl; }
    }
}