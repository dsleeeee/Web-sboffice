package kr.co.solbipos.adi.etc.cdMrpizza.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name  : CdMrpizzaService.java
 * @Description : 미스터피자 > 기타관리 > 명칭관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.14  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.08.14
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface CdMrpizzaService {

    /** 대표명칭 코드목록 조회 */
    List<DefaultMap<String>> getNmcodeGrpCdMrpizzaList(CdMrpizzaVO cdMrpizzaVO);

    /** 세부명칭 코드목록 조회 */
    List<DefaultMap<String>> getNmcodeCdMrpizzaList(CdMrpizzaVO cdMrpizzaVO);

    /** 시스템 명칭관리 - 저장 */
    int getNmcodeCdMrpizzaSave(CdMrpizzaVO[] cdMrpizzaVOS, SessionInfoVO sessionInfoVO);
}
