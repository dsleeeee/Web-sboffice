package kr.co.solbipos.adi.etc.alimtalk.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.etc.alimtalk.service.AlimtalkVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : AlimtalkService.java
 * @Description : 맘스터치 > 기타관리 > 매출트레킹수신자목록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.06.15  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.06.15
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface AlimtalkService {

    /** 조회 */
    List<DefaultMap<String>> getAlimtalkList(AlimtalkVO alimtalkVO, SessionInfoVO sessionInfoVO);

    /** 저장 */
    int getAlimtalkSave(AlimtalkVO[] alimtalkVOs, SessionInfoVO sessionInfoVO);
}