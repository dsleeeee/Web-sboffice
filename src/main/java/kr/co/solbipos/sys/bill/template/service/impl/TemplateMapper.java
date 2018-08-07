package kr.co.solbipos.sys.bill.template.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.bill.template.service.TemplateVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : TemplateMapper.java
 * @Description : 시스템관리 > 포스출력물관리 > 출력물 샘플
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface TemplateMapper {
    
    /** 출력물종류 목록 조회 */
    List<DefaultMap<String>> getPrintTypeList(TemplateVO templateVO);
    
    /** 출력물코드 목록 조회 */
    List<DefaultMap<String>> getPrintCodeList(TemplateVO templateVO);

    /** 출력물템플릿 목록 조회 */
    List<DefaultMap<String>> getTemplateList(TemplateVO templateVO);

    /** 출력물템플릿 목록 생성 */
    int insertTemplateList(TemplateVO templateVO);

    /** 출력물템플릿 목록 수정 */
    int updateTemplateList(TemplateVO templateVO);

    /** 출력물템플릿 목록 삭제 */
    int deleteTemplateList(TemplateVO templateVO);
}
