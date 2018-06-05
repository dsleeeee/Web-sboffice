package kr.co.solbipos.store.persistence.hq.hqbrand;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.domain.hq.hqbrand.HqBrandVO;
import kr.co.solbipos.store.domain.hq.hqbrand.HqClsVO;
import kr.co.solbipos.store.domain.hq.hqbrand.HqEnvstVO;

/**
 * 가맹점관리 > 본사정보 > 브랜드정보관리
 * 
 * @author 김지은
 */
public interface HqBrandMapper {

    /**
     * 브랜드 목록 조회
     * @param hqBrand
     * @return
     */
    List<DefaultMap<String>> list(HqBrandVO hqBrand);


    /**
     * 브랜드 코드 조회
     * @param hqBrandVO
     * @return
     */
    String getHqBrandCd(HqBrandVO hqBrandVO);
    
    /**
     * 브랜드 추가
     * @param hqBrandVO
     * @return
     */
    int insertBrand(HqBrandVO hqBrandVO);

    /**
     * 브랜드 수정
     * @param hqBrandVO
     * @return
     */
    int updateBrand(HqBrandVO hqBrandVO);

    /**
     * 브랜드 삭제
     * @param hqBrandVO
     * @return
     */
    int deleteBrand(HqBrandVO hqBrandVO);

    /**
     * 환경설정 조회
     * @param hqBrand
     * @return
     */
    List<DefaultMap<String>> getConfigList(HqBrandVO hqBrand);

    /**
     * 환경설정 등록
     * @param hqBrand
     * @return
     */
    int insertConfig(HqEnvstVO hqEnvst);

    /**
     * 환경설정 수정
     * @param hqBrand
     * @return
     */
    int updateConfig(HqEnvstVO hqEnvst);

    /**
     * 환경설정 수정 - 매장
     * @param hqEnvst
     * @return
     */
    int updateConfigStore(HqEnvstVO hqEnvst);

    /**
     * 분류목록 조회
     * @param hqBrand
     * @return
     */
    List<DefaultMap<String>> getClsList(HqBrandVO hqBrand);
    
    /**
     * 분류 코드 조회
     * @param hqClsVO
     * @return
     */
    String getClsCd(HqClsVO hqClsVO);
    
    /**
     * 상위분류 코드 조회
     * @param hqClsVO
     * @return
     */
    String getPProdClsCd(HqClsVO hqClsVO);
    
    /**
     * 분류 등록
     * @param hqBrandVO
     * @return
     */
    int insertCls(HqClsVO hqClsVO);

    /**
     * 분류 수정
     * @param hqBrandVO
     * @return
     */
    int updateCls(HqClsVO hqClsVO);

    /**
     * 해당 분류로 등록된 상품 조회
     * @param hqClsVO
     * @return
     */
    int chkProdCnt(HqClsVO hqClsVO);
    
    /**
     * 분류 삭제
     * @param hqClsVO
     * @return
     */
    int deleteCls(HqClsVO hqClsVO);



    
}
