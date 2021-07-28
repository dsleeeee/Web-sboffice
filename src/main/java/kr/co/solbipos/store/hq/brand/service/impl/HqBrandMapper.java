package kr.co.solbipos.store.hq.brand.service.impl;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.hq.brand.service.HqBrandVO;
import kr.co.solbipos.store.hq.brand.service.HqClsVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import org.springframework.stereotype.Repository;

/**
 * @Class Name : HqBrandMapper.java
 * @Description : 가맹점관리 > 본사정보 > 브랜드정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface HqBrandMapper {

    /** 본사 브랜드 목록 조회 */
    List<DefaultMap<String>> getHqBrandlist(HqBrandVO hqBrand);

    /** 매장 브랜드 목록 조회 */
    List<DefaultMap<String>> getMsBrandlist(HqBrandVO hqBrand);

    /** 브랜드 코드 조회 */
    String getHqBrandCd(HqBrandVO hqBrandVO);

    /** 본사 하위 매장 조회 */
    //List<DefaultMap<String>> getStoreList(HqBrandVO hqBrand);

    /** 본사 브랜드 추가 */
    int insertHqBrand(HqBrandVO hqBrandVO);
    /** 본사-매장 브랜드 추가 */
    //int insertHqMsBrand(HqBrandVO hqBrandVO);
    /** 매장 브랜드 추가 */
    int insertMsBrand(HqBrandVO hqBrandVO);

    /** 본사 브랜드 수정 */
    int updateHqBrand(HqBrandVO hqBrandVO);
    /** 본사-매장 브랜드 수정 */
    //int updateHqMsBrand(HqBrandVO hqBrandVO);
    /** 매장 브랜드 수정 */
    int updateMsBrand(HqBrandVO hqBrandVO);

    /** 브랜드 삭제 */
    int deleteBrand(HqBrandVO hqBrandVO);

    /** 본사 브랜드코드 중복체크 */
    String chkHqBrandCd(HqBrandVO hqBrandVO);

    /** 환경설정 조회 */
    List<DefaultMap<String>> getConfigList(HqBrandVO hqBrand);

    /** 환경설정 등록 */
    int insertConfig(HqEnvstVO hqEnvst);

    /** 환경설정 수정 */
    int updateConfig(HqEnvstVO hqEnvst);

    /** 환경설정 수정 - 매장 */
    int updateConfigStore(HqEnvstVO hqEnvst);
}
