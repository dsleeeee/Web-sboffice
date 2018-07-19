package kr.co.solbipos.base.store.tablelayout.service;

import java.util.List;
import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.base.store.tableattr.enums.TblGrpFg;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Class Name : TableGroupVO.java
 * @Description : 기초관리 > 매장관리 > 테이블관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  조병준      최초생성
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TableGroupVO extends CmmVO {

    private static final long serialVersionUID = 1L;

    /** 매장코드 */
    private String storeCd;

    /** 테이블그룹코드 */
    private String tblGrpCd;

    /** 테이블그룹구분-일반,배달 */
    private TblGrpFg tblGrpFg;

    /** 테이블그룹명 */
    private String tblGrpNm;

    /** 배경이미지명 */
    private String bgImgNm;

    /** 배경색 */
    private String bgColor;

    /** 표기순서 */
    private Long dispSeq;

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
    
    /** 테이블들 */
    private List<TableVO> tables;

}
