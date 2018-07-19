package kr.co.solbipos.pos.confg.vermanage.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @Class Name : VerInfoVO.java
* @Description : 포스관리 > POS 설정관리 > POS 버전 관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  김지은      최초생성
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
public class VerInfoVO extends PageVO {

    /** 버전일련번호 */
    private String verSerNo;
    
    /** 버전일련번호 */
    private String verSerNm;

    /** 파일설명 (버전적용명) */
    private String fileNm;

    /** 파일사이즈 */
    private String fileSize;

    /** 파일 저장 디렉토리 */
    private String fileDir;
    
    /** 파일설명 */
    private String fileDesc;

    /** 포스 프로그램 구분 */
    private String progFg;

    /** PGM 포함 여부 */
    private String pgmYn;

    /** DB 포함 여부 */
    private String dbYn;

    /** IMG 포함 여부 */
    private String imgYn;

    /** 사용여부 */
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
