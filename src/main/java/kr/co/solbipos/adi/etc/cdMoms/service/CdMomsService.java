package kr.co.solbipos.adi.etc.cdMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : CdMomsService.java
 * @Description : 맘스터치 > 기타관리 > 명칭관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.27  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.10.27
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface CdMomsService {

    /** 대표명칭 코드목록 조회 */
    List<DefaultMap<String>> getNmcodeGrpCdMomsList(CdMomsVO cdMomsVO);

    /** 세부명칭 코드목록 조회 */
    List<DefaultMap<String>> getNmcodeCdMomsList(CdMomsVO cdMomsVO);

    /** 코드목록 저장 */
    int getNmcodeCdMomsSave(CdMomsVO[] cdMomsVOs, SessionInfoVO sessionInfoVO);
}