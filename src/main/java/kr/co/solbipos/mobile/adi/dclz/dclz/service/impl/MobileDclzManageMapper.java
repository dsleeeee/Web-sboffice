package kr.co.solbipos.mobile.adi.dclz.dclz.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.adi.dclz.dclz.service.MobileDclzManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileDclzManageMapper.java
 * @Description : 부가서비스 > 근태 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.09  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.07.09
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobileDclzManageMapper {

    /**
     * 근태관리 리스트 조회
     *
     * @param dclzManageVO
     * @return
     */
    List<DefaultMap<String>> getDclzManage(MobileDclzManageVO mobileDclzManageVO);

}
