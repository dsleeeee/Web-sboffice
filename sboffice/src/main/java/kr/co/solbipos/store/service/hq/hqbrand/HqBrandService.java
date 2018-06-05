package kr.co.solbipos.store.service.hq.hqbrand;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.domain.login.SessionInfoVO;
import kr.co.solbipos.store.domain.hq.hqbrand.HqBrandVO;
import kr.co.solbipos.store.domain.hq.hqbrand.HqEnvstVO;
import kr.co.solbipos.store.domain.hq.hqbrand.HqClsVO;

/**
 * 가맹점관리 > 본사정보 > 브랜드정보관리
 * 
 * @author 김지은
 */
public interface HqBrandService {

    /**
     * 브랜드 목록 조회
     * @param brand
     * @return
     */
    List<DefaultMap<String>> list(HqBrandVO hqBrand);

    /**
     * 브랜드 저장
     * @param hqBrandVOs
     * @param sessionInfoVO
     * @return
     */
    int save(HqBrandVO[] hqBrandVOs, SessionInfoVO sessionInfoVO);

    /**
     * 환경설정 조회
     * @param hqBrand
     * @return
     */
    List<DefaultMap<String>> getConfigList(HqBrandVO hqBrand);

    /**
     * 환경설정 저장
     * @param hqBrands
     * @param sessionInfoVO
     * @return
     */
    int saveConfig(HqEnvstVO[] hqEnvsts, SessionInfoVO sessionInfoVO);

    /**
     * 분류 목록 조회
     * @param hqBrand
     * @return
     */
    List<HqClsVO> getClsList(HqBrandVO hqBrand);

    /**
     * 분류 등록
     * @param hqBrandVOs
     * @param sessionInfoVO
     * @return
     */
    int clsSave(HqClsVO[] HqClsVOs, SessionInfoVO sessionInfoVO);

}
