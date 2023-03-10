package kr.co.solbipos.sys.cd.envstHqMsMng.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : EnvstHqMsMngVO.java
 * @Description : 시스템관리 > 코드관리 > 환경설정사용설정
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.06  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.03.06
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class EnvstHqMsMngVO extends CmmVO {

    private static final long serialVersionUID = 8257152089649317467L;
    /** 본사/매장 코드 */
    private String orgnCd;
    /** 환경설정코드 */
    private String envstCd;
    /** 등록구분 */
    private String orgnFg;

    public String getOrgnCd() {
        return orgnCd;
    }

    public void setOrgnCd(String orgnCd) {
        this.orgnCd = orgnCd;
    }

    public String getEnvstCd() {
        return envstCd;
    }

    public void setEnvstCd(String envstCd) {
        this.envstCd = envstCd;
    }

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }
}
