package kr.co.solbipos.adi.etc.cdKwu.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : CdKwuService.java
 * @Description : 광운대 > 공통코드 > 공통코드
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.0  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.09.07
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface CdKwuService {

    /** 대표명칭 코드목록 조회 */
    List<DefaultMap<String>> getNmcodeGrpCdKwuList(CdKwuVO cdKwuVO);

    /** 세부명칭 코드목록 조회 */
    List<DefaultMap<String>> getNmcodeCdKwuList(CdKwuVO cdKwuVO);

    /** 코드목록 저장 */
    int getNmcodeCdKwuSave(CdKwuVO[] cdKwuVOs, SessionInfoVO sessionInfoVO);
}