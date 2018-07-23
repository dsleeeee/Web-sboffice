package kr.co.solbipos.store.manage.storemanage.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 가맹점관리 > 매장관리 > 매장정보관리 > 환경설정
 * 
 * @author 김지은
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class KitchenPrintVO extends CmmVO {

    /** 매장코드 */
    private String storeCd;
    
    /** 프린터번호 */
    private String prterNo;
    
    /** 프린터명 */
    private String prterNm;
    
    /** 포스번호 */
    private String posNo;
    
    /** 프린터종류구분 */
    private String prterKindFg;
    
    /** 프린터포트구분 */
    private String prterPortFg;
    
    /** 프린터속도구분 */
    private String prterSpeedFg;
    
    /** 프린터넷아이피 */
    private String prterNetIp;
    
    /** 프린터넷포트 */
    private String prterNetPort;
    
    /** 프린터출력수량 */
    private String prterOutputQty;
    
    /** 사용여부  (Y:사용, N:미사용) */
    private UseYn useYn;
    
    /** 등록일시 */
    private String regDt;

    /** 등록아이디 */
    private String regId;

    /** 수정일시 */
    private String modDt;

    /** 수정아이디 */
    private String modId;

}
