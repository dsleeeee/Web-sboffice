package kr.co.solbipos.pos.domain.confg.func;

import kr.co.solbipos.application.domain.cmm.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 포스관리 > POS 설정관리 > POS 기능정의
 *
 * @author 김지은
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
