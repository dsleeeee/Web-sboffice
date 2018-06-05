package kr.co.solbipos.adi.dclz.dclzmanage.service;

import kr.co.solbipos.adi.dclz.dclzmanage.enums.DclzInFg;
import kr.co.solbipos.application.common.service.PageVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author 정용길
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DclzManageVO extends PageVO {

    /** 매장코드 */
    private String storeCd;

    /** 매장코드 array */
    private String arrStoreCd[];

    /** 사원번호 */
    private String empNo;

    /** 출근일자 */
    private String empInDate;

    /** 포스번호 */
    private String posNo;

    /** 영업일자 */
    private String saleDate;

    /** 출근일시 */
    private String empInDt;

    /** 퇴근일시 */
    private String empOutDt;

    /** 근무시간 분단위 */
    private Long workTime;

    /**
     * 입력구분 > 입력구분 공통코드:087
     */
    private DclzInFg inFg;
    
    /** 입력 구분 이름 */
    private String inFgNm;

    /** 비고 */
    private String remark;

    /** 등록일시 */
    private String regDt;

    /** 등록아이디 */
    private String regId;

    /** 수정일시 */
    private String modDt;

    /** 수정아이디 */
    private String modId;
}
