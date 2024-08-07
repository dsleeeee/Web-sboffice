package kr.co.solbipos.iostock.loan.virtualAccountInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.loan.virtualAccountInfo.service.VirtualAccountInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : VirtualAccountInfoMappper.java
 * @Description : 수불관리 > 주문관리 > 가상계좌-기초정보등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.08.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface VirtualAccountInfoMapper {

    /** 가상계좌-기초정보등록 - 조회 */
    List<DefaultMap<Object>> getVirtualAccountInfoList(VirtualAccountInfoVO virtualAccountInfoVO);

    /** 가상계좌-기초정보등록 - 저장 merge */
    int getVirtualAccountInfoSaveMerge(VirtualAccountInfoVO virtualAccountInfoVO);
}