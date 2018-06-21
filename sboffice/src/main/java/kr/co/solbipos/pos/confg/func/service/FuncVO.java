package kr.co.solbipos.pos.confg.func.service;

import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Class Name : FuncVO.java
 * @Description : 포스관리 > POS 설정관리 > POS 기능정의
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.06.01  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FuncVO extends CmmVO{

    private static final long serialVersionUID = 1L;

    /** 기능키번호 */
    private String fnkeyNo;

    /** 기능키명 */
    private String fnkeyNm;

    /** 기능키구분 */
    private String fnkeyFg;

    /** 매장종류구분 */
    private String storeFg;

    /** 포스구분 */
    private String posFg;

    /** 일반 이미지파일명 */
    private String imgFileNm0;

    /** 외식 이미지파일명 */
    private String imgFileNm1;

    /** 일반 사용여부 */
    private String fnkeyUseYn0;

    /** 외식 사용여부 */
    private String fnkeyUseYn1;

    /** 표기순번 */
    private String dispSeq;

    /** 위치조정여부 */
    private String posiAdjYn;

    /** 열위치 */
    private String colPosi;

    /** 줄위치 */
    private String rowPosi;

    /** 폭 */
    private String width;

    /** 높이 */
    private String height;

    /** 사용여부 */
    private String useYn;
}
