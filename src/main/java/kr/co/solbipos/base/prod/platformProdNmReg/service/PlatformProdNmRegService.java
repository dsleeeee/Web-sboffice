package kr.co.solbipos.base.prod.platformProdNmReg.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface PlatformProdNmRegService {

    /** 플랫폼 상품명 등록 리스트 조회 */
    List<DefaultMap<String>> getPlatformProdNmRegList(PlatformProdNmRegVO platformProdNmRegVO, SessionInfoVO sessionInfoVO);

    /** 플랫폼 상품명 등록 저장 */
    int savePlatformProdNm(PlatformProdNmRegVO[] platformProdNmRegVOs, SessionInfoVO sessionInfoVO);

}
