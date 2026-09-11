package kr.co.solbipos.sale.benson.timeProdChannelBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.benson.timeProdChannelBenson.service.TimeProdChannelBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : TimeProdChannelBensonMapper.java
 * @Description : (벤슨) 상품매출분석 > 상품별시간대매출(채널별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface TimeProdChannelBensonMapper {

    /** 상품별시간대매출(채널별) 조회 */
    List<DefaultMap<String>> getTimeProdChannelBensonList(TimeProdChannelBensonVO timeProdChannelBensonVO);

    /** 상품별시간대매출(채널별) 엑셀다운로드 */
    List<DefaultMap<String>> getTimeProdChannelBensonExcelList(TimeProdChannelBensonVO timeProdChannelBensonVO);
}
