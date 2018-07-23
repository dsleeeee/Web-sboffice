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
public class StorePosEnvVO extends CmmVO {

    /** 매장코드 */
    private String storeCd;
    
    /** 매장명 */
    private String storeNm;
    
    /** 포스번호 */
    private String posNo;

    /** 포스명 */
    private String posNm;
    
    /** 환경설정코드 */
    private String envstCd;
    
    /** 환경설정명 */
    private String envstNm;
    
    /** 환경설정구분 */
    private String envstFg;
    
    /** 환경설정그룹코드 */
    private String envGrpCd;
    
    /** 환경설정값 */
    private String envstVal;
    
    /** 환경설정값코드 */
    private String envstValCd;
    
    /** 환경설정값명 */
    private String envstValNm;

    /** 기본여부 */
    private String defltYn;

    /** 직접입력여부 (Y:직접, N:선택) */
    private String dirctInYn;

    /** 포스구분 (W:웹, P:포스) */
    private String posFg;
    
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
    
    /** 포스 복사시, 타겟 포스 */
    private String targetPosNo;
    

}
