package kr.co.solbipos.store.hq.brand.service;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * @Class Name : HqBrandService.java
 * @Description : 가맹점관리 > 본사정보 > 브랜드정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface HqBrandService {

    /**  브랜드 목록 조회 */
    List<DefaultMap<String>> getBrandlist(HqBrandVO hqBrand, SessionInfoVO sessionInfoVO);

    /** 브랜드 저장 */
    int save(HqBrandVO[] hqBrandVOs, SessionInfoVO sessionInfoVO);

    /** 환경설정 조회 */
    List<DefaultMap<String>> getConfigList(HqBrandVO hqBrand);

    /** 환경설정 저장 */
    int saveConfig(HqEnvstVO[] hqEnvsts, SessionInfoVO sessionInfoVO);

}
