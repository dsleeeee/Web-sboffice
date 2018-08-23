package kr.co.common.service.code;

import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import kr.co.solbipos.sys.cd.envconfg.service.EnvstVO;

import java.util.List;

/**
 * @Class Name : CmmEvnService.java
 * @Description : 환경변수 관련
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.22  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.22
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface CmmEnvService {

    /**
     * 환경변수 조회
     *
     * @param envstCd
     * @return
     */
    <E> List<E> selectEnvCodeList(String envstCd);

    /**
     * 본사 환경변수 값 조회
     *
     * @param hqEnvstVO
     * @return String (TB_HQ_ENVST)
     */
    String getHqEnvst(HqEnvstVO hqEnvstVO);

    /**
     * 환경변수명 값 조회
     *
     * @param envstVO
     * @return String
     */
    String getEnvNm(EnvstVO envstVO);
}
