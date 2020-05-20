package kr.co.solbipos.store.manage.storemanage.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.sys.auth.authgroup.enums.IncldExcldFg;

/**
 * @Class Name : StoreMenuVO.java
 * @Description : 가맹점관리 > 매장관리 > 매장정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.05.15  이다솜      최초생성
 *
 * @author 솔비포스 백엔드 PT 이다솜
 * @since 2020. 05. 15
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public class StoreMenuVO extends PageVO {

    private static final long serialVersionUID = -1809798208870386448L;

    /** 매장코드 */
    private String storeCd;
    /** 복사할 매장코드 */
    private String copyStoreCd;
    /** 메뉴 리소스 코드 */
    private String resrceCd;
    /** 포함 제외 여부 */
    private IncldExcldFg incldExcldFg;
    /** 사용여부 */
    private String useYn;

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getCopyStoreCd() {
        return copyStoreCd;
    }

    public void setCopyStoreCd(String copyStoreCd) {
        this.copyStoreCd = copyStoreCd;
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
