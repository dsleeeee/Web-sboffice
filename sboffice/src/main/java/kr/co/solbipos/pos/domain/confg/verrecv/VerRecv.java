package kr.co.solbipos.pos.domain.confg.verrecv;

import org.hibernate.validator.constraints.NotBlank;
import kr.co.solbipos.application.domain.cmm.PageVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 포스관리 > POS 설정관리 > POS 버전 수신현황
 * 
 * @author 김지은
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class VerRecv extends PageVO {
    
    /** 버전일련번호 */
    private String verSerNo;
    
    /** 버전명 */
    private String verSerNm;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;
    
    /** 매장코드 */
    private String storeCd;
    
    /** 매장명 */
    private String storeNm;
    
    /** 최종버전 */
    private String lastVer;
    
    /** 사용여부 */
    private String useYn;
    
    /** 등록일시 */
    private String regDt;
    
    /** 등록아이디 */
    private String regId;
    
    /** 수정일시 */
    private String modDt;
    
    /** 수정아이디 */
    private String modId;
}
