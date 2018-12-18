package kr.co.solbipos.application.pos.posPostpaid.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.pos.posPostpaid.service.PosPostpaidStoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PosPostpaidMapper.java
 * @Description : POS 화면에서 세금계산서 발행요청
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.03  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 12.03
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface PosPostpaidMapper {

    /** 대상 회원 목록 조회 */
    List<DefaultMap<String>> getMemberList(PosPostpaidStoreVO posPostpaidStoreVO);

    /** 세금계산서 발행 요청 등록 */
    int saveTaxBillRequet(PosPostpaidStoreVO posPostpaidStoreVO);

}
