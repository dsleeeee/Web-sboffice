package kr.co.solbipos.adi.sms.resveCountStatus.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : ResveCountStatusVO.java
 * @Description : 부가서비스 > SMS분석 > 보나비문자전송현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.05.02  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.05.02
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class ResveCountStatusVO extends PageVO {

    private static final long serialVersionUID = -8859631406606126474L;

    /** 조회월 */
    private String startMonth;
    private String endMonth;

    /** sms 금액 */
    private String smsAmt;
    /** lms 금액 */
    private String lmsAmt;

    /** 옵션 */
    private String option;

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public String getSmsAmt() {
        return smsAmt;
    }

    public void setSmsAmt(String smsAmt) {
        this.smsAmt = smsAmt;
    }

    public String getLmsAmt() {
        return lmsAmt;
    }

    public void setLmsAmt(String lmsAmt) {
        this.lmsAmt = lmsAmt;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
