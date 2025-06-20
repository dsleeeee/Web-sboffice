package kr.co.solbipos.adi.sms.remainAmtChk.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.sms.remainAmtChk.service.RemainAmtChkVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : RemainAmtChkMapper.java
 * @Description : 부가서비스 > SMS분석 > 잔여금액확인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.06.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface RemainAmtChkMapper {

    /** 조회 */
    List<DefaultMap<String>> getRemainAmtChkList(RemainAmtChkVO remainAmtChkVO);

    /** 충전/사용내역 팝업 - 조회 */
    List<DefaultMap<String>> getRemainAmtHistList(RemainAmtChkVO remainAmtChkVO);
}
