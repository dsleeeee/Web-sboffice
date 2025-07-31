package kr.co.solbipos.sale.mrpizza.menuSaleMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.mrpizza.daySaleMrpizza.service.DaySaleMrpizzaVO;
import kr.co.solbipos.sale.mrpizza.menuSaleMrpizza.service.MenuSaleMrpizzaVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MenuSaleMrpizzaMapper.java
 * @Description : 미스터피자 > 마케팅조회 > 메뉴별판매
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.25  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.07.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface MenuSaleMrpizzaMapper {

    /** 메뉴별판매 엑셀다운로드 리스트 조회 */
    List<DefaultMap<Object>> getMenuSaleMrpizzaList(MenuSaleMrpizzaVO menuSaleMrpizzaVO);

    /** 메뉴별판매 엑셀다운로드 리스트 조회 */
    List<DefaultMap<Object>> getMenuSaleMrpizzaExcelList(MenuSaleMrpizzaVO menuSaleMrpizzaVO);
}
