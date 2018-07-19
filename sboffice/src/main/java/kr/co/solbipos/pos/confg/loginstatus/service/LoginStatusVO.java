package kr.co.solbipos.pos.confg.loginstatus.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.pos.confg.loginstatus.enums.SysStatFg;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Class Name : LoginStatusVO.java
 * @Description : 포스관리 > POS 설정관리 > POS 로그인현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginStatusVO extends PageVO {

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;

    /** POS 번호 */
    private String posNo;

    /** 영업일자 */
    private String loginDate;

    /** 디바이스 번호 */
    private String hwAuthKey;

    /** 로그인 IP */
    private String loginIp;

    /** 로그인 일시 */
    private String loginDt;

    /** POS 현재 버전 */
    private String posVerNo;

    /** 매장 상태 */
    private SysStatFg sysStatFg;

    /** 매장 상태 이름 */
    private String sysStatFgNm;

    /** 로그인 순서 */
    private String loginSeq;
}
