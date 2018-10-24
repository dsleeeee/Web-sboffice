package kr.co.common.service.code.impl;

import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreEnvVO;
import kr.co.solbipos.sys.cd.envconfg.service.EnvstVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : CmmEnvMapper.java
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
@Mapper
@Repository
public interface CmmEnvMapper {

    /** 환경변수 코드 조회 */
    <E> List<E> selectEnvCodeList(String envstCd);

    /** 본사 환경변수 값 조회 */
    String getHqEnvst(HqEnvstVO hqEnvstVO);

    /** 매장 환경변수 값 조회 */
    String getStoreEnvst(StoreEnvVO storeEnvVO);

    /** 환경변수명 조회 */
    String getEnvNm(EnvstVO envstVO);
}
