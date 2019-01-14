package kr.co.solbipos.pos.install.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.pos.install.service.InstallVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : InstallManageMapper.java
 * @Description : 포스관리 > 설치관리 > 설치관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.01.02  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2019.01.02
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface InstallManageMapper {

    /** 설치요청 목록 조회 */
    List<DefaultMap<String>> getInstallRequestList(InstallVO installVO);

    /** 포스 목록 조회 */
    List<DefaultMap<String>> getPosList(InstallVO installVO);

    /** 설치요청 등록 */
    int saveInstallRequest(InstallVO installVO);

    /** 포스 H/W인증키 초기화 */
    int initPosHwKey(InstallVO installVO);

}
