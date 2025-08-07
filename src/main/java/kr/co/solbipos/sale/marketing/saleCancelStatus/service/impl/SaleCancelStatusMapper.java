package kr.co.solbipos.sale.marketing.saleCancelStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.marketing.saleCancelStatus.service.SaleCancelStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name  : SaleCancelStatusMapper.java
 * @Description : 미스터피자 > 마케팅조회 > 취소현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.31  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.07.31
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface SaleCancelStatusMapper {

    /** 취소현황 - 전체점포 탭 조회 */
    List<DefaultMap<Object>> getSaleCancelStatusList(SaleCancelStatusVO saleCancelStatusVO);

    /** 취소현황 - 전체점포 탭 상세조회 */
    List<DefaultMap<Object>> getSaleCancelStatusDtlList(SaleCancelStatusVO saleCancelStatusVO);

    /** 취소현황 - 선택점포 탭 조회 */
    List<DefaultMap<Object>> getSaleCancelStatusStoreList(SaleCancelStatusVO saleCancelStatusVO);
}
