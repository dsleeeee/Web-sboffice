package kr.co.solbipos.adi.sms.resveCountStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.sms.resveCountStatus.service.ResveCountStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ResveCountStatusMapper.java
 * @Description : 부가서비스 > SMS분석 > 보나비문자전송현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.05.02  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.05.02
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface ResveCountStatusMapper {

    /** 보나비예약건수현황 - 조회 */
    List<DefaultMap<String>> getResveCountStatusList(ResveCountStatusVO resveCountStatusVO);
}
