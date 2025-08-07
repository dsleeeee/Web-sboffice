package kr.co.solbipos.sale.mrpizza.dcDetailMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.mrpizza.dcDetailMrpizza.service.DcDetailMrpizzaVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DcDetailMrpizzaMapper.java
 * @Description : 미스터피자 > 마케팅조회 > 할인세부내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.30  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.07.30
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface DcDetailMrpizzaMapper {

    /** 할인세부내역 - 전체점포 탭 리스트 조회 */
    List<DefaultMap<Object>> getDcDetailMrpizzaAllStoreList(DcDetailMrpizzaVO dcDetailMrpizzaVO);

    /** 할인세부내역 - 선택점포 탭 리스트 조회 */
    List<DefaultMap<Object>> getDcDetailMrpizzaSelectStoreList(DcDetailMrpizzaVO dcDetailMrpizzaVO);

    /** 할인세부내역 - 할인구분 탭 리스트 조회 */
    List<DefaultMap<Object>> getDcDetailMrpizzaDcTypeList(DcDetailMrpizzaVO dcDetailMrpizzaVO);
}
