package kr.co.solbipos.adi.sms.smsSendHist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.sms.smsSendHist.service.SmsSendHistVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SmsSendHistMapper.java
 * @Description : 부가서비스 > SMS관리 > SMS전송이력
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.05  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.08.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SmsSendHistMapper {

    /** SMS전송이력 - 조회 */
    List<DefaultMap<Object>> getSmsSendHistList(SmsSendHistVO smsSendHistVO);

    /** 수신자정보 팝업 - 조회 */
    List<DefaultMap<Object>> getAddresseeDtlList(SmsSendHistVO smsSendHistVO);
}