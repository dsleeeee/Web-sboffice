package kr.co.solbipos.adi.sms.badword.service.impl;

import kr.co.solbipos.adi.sms.badword.service.BadwordVO;
import kr.co.solbipos.adi.sms.badword.service.FilterResult;
import kr.co.solbipos.adi.sms.smsSend.service.SmsSendVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : BadwordFilterMapper.java
 * @Description : 부가서비스 > SMS관리 > SMS전송(탭) > 금칙어 필터링
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.06.23  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.06.23
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface BadwordFilterMapper {

    /** 활성 금칙어 전체 목록 조회 (캐시용) */
    List<BadwordVO> selectActiveBadwordList();

    /** 차단이력 저장 */
    int insertMessageBlock(SmsSendVO smsSendVO);
}
