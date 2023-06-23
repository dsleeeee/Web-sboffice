package kr.co.solbipos.adi.etc.alimtalk.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.etc.alimtalk.service.AlimtalkVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : AlimtalkMapper.java
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
@Mapper
@Repository
public interface AlimtalkMapper {

    List<DefaultMap<String>> getAlimtalkFgList(SessionInfoVO sessionInfoVO);

    /** 조회 */
    List<DefaultMap<String>> getAlimtalkList(AlimtalkVO alimtalkVO);

    /** 저장 */
    int getAlimtalkSave(AlimtalkVO alimtalkVO);

    /** 삭제 */
    int getAlimtalkDelete(AlimtalkVO alimtalkVO);
}