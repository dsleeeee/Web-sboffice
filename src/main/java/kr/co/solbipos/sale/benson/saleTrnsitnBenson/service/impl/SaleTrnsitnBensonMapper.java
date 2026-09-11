package kr.co.solbipos.sale.benson.saleTrnsitnBenson.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.benson.saleTrnsitnBenson.service.SaleTrnsitnBensonDatesVO;
import kr.co.solbipos.sale.benson.saleTrnsitnBenson.service.SaleTrnsitnBensonVO;

/**
 * @Class Name : SaleTrnsitnBensonMapper.java
 * @Description : 벤슨 > 매출분석 > 매출추이분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface SaleTrnsitnBensonMapper {

    /**
     * 매출추이분석 목록조회 (벤슨 > 매출분석 > 매출추이분석)
     * @param   saleTrnsitnBensonVO
     * @return  java.util.List<DefaultMap<String>> - XML_String
     * @author  김유승
     * @since   2026.09.10
    */
    List<DefaultMap<String>> getSaletrnsitnBensonList(SaleTrnsitnBensonVO saleTrnsitnBensonVO);

    /**
     * 특정일 기준 이전일자들 조회 (벤슨 > 매출분석 > 매출추이분석)
     * @param   saleTrnsitnBensonVO
     * @return  SaleTrnsitnBensonDatesVO
     * @author  김유승
     * @since   2026.09.10
    */
    SaleTrnsitnBensonDatesVO getPreviouseDatesInfo(SaleTrnsitnBensonVO saleTrnsitnBensonVO);

    /**
     * 매출추이분석(엑셀) 목록조회 (벤슨 > 매출분석 > 매출추이분석)
     * @param   saleTrnsitnBensonVO
     * @return  java.util.List<DefaultMap<String>> - XML_String
     * @author  김유승
     * @since   2026.09.10
    */
    List<DefaultMap<String>> getSaletrnsitnBensonExcelList(SaleTrnsitnBensonVO saleTrnsitnBensonVO);

}
