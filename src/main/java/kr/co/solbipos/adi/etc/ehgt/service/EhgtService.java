package kr.co.solbipos.adi.etc.ehgt.service;

import java.util.List;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * @Class Name : EhgtService.java
 * @Description : 부가서비스 > 환율 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.09  조병준      최초생성
 *
 * @author NHN한국사이버결제 조병준
 * @since 2018. 08.09
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface EhgtService {

    /**
     * 환율 관리 리스트 조회
     *
     * @param ehgtVO
     * @param sessionInfoVO
     * @return
     */
    List<DefaultMap<Object>> getEhgtListBySaleDt(EhgtVO ehgtVO, SessionInfoVO sessionInfoVO);

    /**
     * 환율 관리 해당일의 환율 조회
     *
     * @param ehgtVO
     * @param sessionInfoVO
     * @return
     */
    List<DefaultMap<String>> getEhgtDetailBySaleDt(EhgtVO ehgtVO, SessionInfoVO sessionInfoVO);

    /**
     * 환율 저장
     *
     * @param ehgtVOs
     * @param sessionInfoVO
     * @return
     */
    int saveEhgts(List<EhgtVO> ehgtVOs, SessionInfoVO sessionInfoVO);

    /**
     * 프랜차이즈, 단독매장 공통 코드 조회
     * 프랜차이즈 본사/매장은 HQ_NMCODE, 단독매장은 MS_STORE_NMCODE 조회
     *
     * @param useYn
     * @param sessionInfoVO
     * @return
     */
    List<DefaultMap<String>> getCdListByGrpCd(UseYn useYn, SessionInfoVO sessionInfoVO);

    /**
     * 본사/가맹점 통화 코드 저장
     *
     * @param crncyCdVOs
     * @param sessionInfo
     * @return
     */
    int updateCrncyCd(List<CrncyCdVO> crncyCdVOs, SessionInfoVO sessionInfoVO);

}
