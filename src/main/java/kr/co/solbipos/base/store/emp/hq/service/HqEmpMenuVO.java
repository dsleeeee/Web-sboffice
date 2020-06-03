package kr.co.solbipos.base.store.emp.hq.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.sys.auth.authgroup.enums.IncldExcldFg;

/**
 * @Class Name : HqMenuEmpVO.java
 * @Description : 기초관리 > 사원관리 > 사원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.06.02  이다솜      최초생성
 *
 * @author 솔비포스 백엔드 PT 이다솜
 * @since 2020. 06.02
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public class HqEmpMenuVO extends PageVO {

    private static final long serialVersionUID = -8629520166107013789L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 복사할 사원코드 */
    private String copyEmpNo;
    /** 사원코드 */
    private String empNo;
    /** 메뉴 리소스 코드 */
    private String resrceCd;
    /** 포함 제외 여부 */
    private IncldExcldFg incldExcldFg;
    /** 사용여부 */
    private String useYn;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getCopyEmpNo() {
        return copyEmpNo;
    }

    public void setCopyEmpNo(String copyEmpNo) {
        this.copyEmpNo = copyEmpNo;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getResrceCd() {
        return resrceCd;
    }

    public void setResrceCd(String resrceCd) {
        this.resrceCd = resrceCd;
    }

    public IncldExcldFg getIncldExcldFg() {
        return incldExcldFg;
    }

    public void setIncldExcldFg(IncldExcldFg incldExcldFg) {
        this.incldExcldFg = incldExcldFg;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
}
