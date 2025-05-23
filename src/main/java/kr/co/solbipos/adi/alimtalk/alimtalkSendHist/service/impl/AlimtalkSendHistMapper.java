package kr.co.solbipos.adi.alimtalk.alimtalkSendHist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.alimtalk.alimtalkSendHist.service.AlimtalkSendHistVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : AlimtalkSendHistMapper.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡 전송이력
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.30  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.03.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface AlimtalkSendHistMapper {

    /** 알림톡 전송이력 - 조회 */
    List<DefaultMap<Object>> getAlimtalkSendHistList(AlimtalkSendHistVO alimtalkSendHistVO);

    /** 알림톡 전송이력 - 엑셀 조회 */
    List<DefaultMap<Object>> getAlimtalkSendHistExcelList(AlimtalkSendHistVO alimtalkSendHistVO);

    /** 알림톡 수신자정보 팝업 - 조회 */
    List<DefaultMap<Object>> getAlimtalkAddresseeDtlList(AlimtalkSendHistVO alimtalkSendHistVO);
}