package kr.co.solbipos.base.output.postemplate.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.output.postemplate.service.PosTemplateVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PosServiceMapper.java
 * @Description : 기초관리 > 출력물관리 > 포스출력물관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.04  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface PosTemplateMapper {
    
    /** 출력물종류 목록 조회 */
    List<DefaultMap<String>> getPrintTypeList(PosTemplateVO posTemplateVO);
    
    /** 출력물코드 목록 조회 */
    List<DefaultMap<String>> getPrintCodeList(PosTemplateVO posTemplateVO);

    /** 출력물템플릿 목록 조회 */
    List<DefaultMap<String>> getPosTemplateList(PosTemplateVO posTemplateVO);

    /** 출력물템플릿 목록 생성 */
    int insertPosTemplateList(PosTemplateVO posTemplateVO);

    /** 출력물템플릿 목록 수정 */
    int updatePosTemplateList(PosTemplateVO posTemplateVO);

    /** 출력물템플릿 목록 삭제 */
    int deletePosTemplateList(PosTemplateVO posTemplateVO);

    /** 출력물템플릿 수정 */
    int savePosTemplate(PosTemplateVO posTemplateVO);

    /** 실제출력물 템플릿 수정 */
    int updatePosTemplatePrint(PosTemplateVO posTemplateVO);

    /** 매장 출력물템플릿 수정 : 프로시저 호출  */
    String updatePosTemplateForStoreFromHq(PosTemplateVO posTemplateVO);

}
