package kr.co.solbipos.sale.status.cupReturnStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.cupReturnStatus.service.CupReturnStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name : CupReturnStatusMapper.java
 * @Description : 맘스터치 > 매출분석2 > 컵보증금회수현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.17  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.07.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface CupReturnStatusMapper {

    /** 컵보증금회수현황 - 조회 */
    List<DefaultMap<Object>> getCupReturnStatusList(CupReturnStatusVO cupReturnStatusVO);
}
