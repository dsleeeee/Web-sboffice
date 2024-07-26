package kr.co.solbipos.iostock.loan.virtualAccount.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.loan.virtualAccount.service.VirtualAccountVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
// API VO
import kr.co.solbipos.iostock.loan.virtualAccount.service.ApiVirtualAccountRegisterVO;
import kr.co.solbipos.iostock.loan.virtualAccount.service.ApiVirtualAccountRegisterReceiveVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : VirtualAccountMapper.java
 * @Description : 수불관리 > 주문관리 > 가상계좌내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.24  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.07.24
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface VirtualAccountMapper {

    /** 가상계좌 키값 리스트 조회 */
    List<DefaultMap<String>> getVirtualAccountKeyColList(VirtualAccountVO virtualAccountVO);

    /** 가상계좌 등록순번 조회 */
    String getVirtualAccountReqSeq(VirtualAccountVO virtualAccountVO);

    /** 가상계좌 입금 생성 팝업 - 가상계좌 발급 저장 Insert */
    int getVirtualAccountRegisterSaveInsert(VirtualAccountVO virtualAccountVO);

    /** 가상계좌 입금 생성 팝업 - 가상계좌 발급 저장 update */
    int getVirtualAccountRegisterSaveUpdate(VirtualAccountVO virtualAccountVO);
}