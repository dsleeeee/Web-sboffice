package kr.co.solbipos.sale.moms.posRcvPayMoms.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : PosRcvPayMomsVO.java
 * @Description : 맘스터치 > 정산 > POS내역수신(결제)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.18  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.09.18
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class PosRcvPayMomsVO extends PageVO {

    private static final long serialVersionUID = 1L;

    private String hqOfficeCd; //본사사업장코드
    private String storeCd; //매장코드(매장선택 싱글, 필수)
    private String saleDate; //조회일자(필수)

    // TODO: 인터페이스정의서(SAP B1_인터페이스정의서_RPA_v4.0) 기준 상세 컬럼 추가
    // 순번, 결제구분, 결제수단, 결제수단번호, 승인일자, 승인시간, 승인번호, 승인구분,
    // 총매출금액, 실매출금액, 할인금액, 공급가, 부가세, 쿠폰금액, 원거래승인번호, 원거래승인일자,
    // 카드결제 시 추가: 할부개월, 매입사, 발급사, 밴사

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }
}
