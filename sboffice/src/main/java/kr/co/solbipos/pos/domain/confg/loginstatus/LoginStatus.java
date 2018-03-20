package kr.co.solbipos.pos.domain.confg.loginstatus;

import kr.co.solbipos.application.domain.cmm.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author 정용길
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginStatus extends Page {

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
    private String sysStatFg;

    /** 매장 상태 이름 */
    private String sysStatFgNm;

    /** 로그인 순서 */
    private String loginSeq;
}
