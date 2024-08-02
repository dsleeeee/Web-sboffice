package kr.co.solbipos.mobile.stock.curr.hqCurr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.stock.curr.hqCurr.service.MobileHqCurrVO;
import kr.co.solbipos.stock.curr.hqCurr.service.HqCurrVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name : MobileHqCurrMapper.java
 * @Description : (모바일)재고현황 > 현재고현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.19  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.19
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobileHqCurrMapper {

    /** 현재고현황 - 본사 현재고현황 리스트 조회 */
    List<DefaultMap<String>> getHqCurrList(MobileHqCurrVO mobileHqCurrVO);
    /** 현재고현황 - 매장 현재고현황 리스트 조회 */
    List<DefaultMap<String>> getHqStoreCurrList(MobileHqCurrVO mobileHqCurrVO);

    /** 현재고현황 - 본사 현재고현황 상세리스트 조회 */
    List<DefaultMap<String>> getHqCurrDtlList(MobileHqCurrVO mobileHqCurrVO);

    /** 현재고현황 - 매장 현재고현황 상세리스트 조회 */
    List<DefaultMap<String>> getStoreCurrDtlList(MobileHqCurrVO mobileHqCurrVO);

    /** 현재고현황 - 본사 현재고현황 엑셀 전체 리스트 조회 */
    List<DefaultMap<String>> getHqCurrExcelList(MobileHqCurrVO mobileHqCurrVO);

    /** 현재고현황 - 매장 현재고현황 엑셀 전체 리스트 조회 */
    List<DefaultMap<String>> getHqStoreCurrExcelList(MobileHqCurrVO mobileHqCurrVO);
}
