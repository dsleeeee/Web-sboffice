package kr.co.solbipos.base.prod.prodOption.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.prodOption.service.ProdOptionVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProdOptionMapper.java
 * @Description : 기초관리 > 상품관리 > 옵션관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.19  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.12.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface ProdOptionMapper {

    /* 옵션그룹조회 */
    List<DefaultMap<String>> getProdOptionGroup(ProdOptionVO prodOptionVO);

    /* 옵션그룹 코드 채번 */
    String getProdOptionGroupCode(ProdOptionVO prodOptionVO);

    /* 옵션그룹저장 */
    int saveProdOptionGroup(ProdOptionVO prodOptionVO);

    /* 옵션그룹삭제 */
    int deleteProdOptionGroup(ProdOptionVO prodOptionVO);

    /* 옵션속성조회 */
    List<DefaultMap<String>> getProdOptionVal(ProdOptionVO prodOptionVO);

    /* 옵션속성 코드 채번 */
    String getProdOptionValCode(ProdOptionVO prodOptionVO);

    /* 옵션속성저장 */
    int saveProdOptionVal(ProdOptionVO prodOptionVO);

    /* 옵션속성삭제 */
    int deleteProdOptionVal(ProdOptionVO prodOptionVO);
}