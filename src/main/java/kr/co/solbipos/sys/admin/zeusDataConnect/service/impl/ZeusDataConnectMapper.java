package kr.co.solbipos.sys.admin.zeusDataConnect.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.admin.zeusDataConnect.service.ZeusDataConnectVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ZeusDataConnectMapper.java
 * @Description : 시스템관리 > 관리자기능 > 제우스데이터연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.03.19  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.03.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ZeusDataConnectMapper {

    /** 제우스데이터연동 - 제우스 PKG 호출 01 */
    String getZeusPkg01(ZeusDataConnectVO zeusDataConnectVO);

    /** 제우스데이터연동 - 제우스 PKG 호출 02 */
    String getZeusPkg02(ZeusDataConnectVO zeusDataConnectVO);

    /** 제우스데이터연동 - 제우스->링크 데이터연동 */
    String getZeusPkg01Call(ZeusDataConnectVO zeusDataConnectVO);

    /** 제우스데이터연동 - 연동신청처리 */
    String getZeusPkg02Call(ZeusDataConnectVO zeusDataConnectVO);

    /** 제우스데이터연동 - 조회 */
    List<DefaultMap<Object>> getZeusDataConnectList(ZeusDataConnectVO zeusDataConnectVO);
}