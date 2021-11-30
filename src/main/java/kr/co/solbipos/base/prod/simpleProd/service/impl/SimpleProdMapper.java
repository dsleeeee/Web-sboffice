package kr.co.solbipos.base.prod.simpleProd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.simpleProd.service.SimpleProdVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SimpleProdMapper.java
 * @Description : 기초관리 > 상품관리 > 간편상품등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.08.26  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.08.26
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface SimpleProdMapper {

    /** 거래처 콤보 조회 */
    List<DefaultMap<String>> vendrComboList(SimpleProdVO simpleProdVO);

    /** 검증결과 전체 삭제 */
    int getSimpleProdCheckDeleteAll(SimpleProdVO simpleProdVO);

    /** 검증결과 삭제 */
    int getSimpleProdCheckDelete(SimpleProdVO simpleProdVO);

    /** 상품명 중복체크 */
    int getProdNmCnt(SimpleProdVO simpleProdVO);

    /** 바코드 중복체크 */
    int getBarCdCnt(SimpleProdVO simpleProdVO);

    /** 검증결과 저장 */
    int getSimpleProdCheckSave(SimpleProdVO simpleProdVO);

    /** 검증결과 조회 */
    List<DefaultMap<Object>> getSimpleProdList(SimpleProdVO simpleProdVO);

    /** 거래처 저장 */
    int getVendorProdSaveInsert(SimpleProdVO simpleProdVO);

    /** 브랜드 등록여부 체크 */
    int getHqBrandCdChk(SimpleProdVO simpleProdVO);
}