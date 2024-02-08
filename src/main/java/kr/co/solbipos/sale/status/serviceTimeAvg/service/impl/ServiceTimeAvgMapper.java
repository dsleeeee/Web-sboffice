package kr.co.solbipos.sale.status.serviceTimeAvg.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.serviceTimeAvg.service.ServiceTimeAvgVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ServiceTimeAvgMapper.java
 * @Description : 맘스터치 > 매출분석2 > 서비스타임(평균시간)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.02.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.02.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ServiceTimeAvgMapper {

    /** 서비스타임(평균시간) - 조회 */
    List<DefaultMap<Object>> getServiceTimeAvgList(ServiceTimeAvgVO serviceTimeAvgV);
}