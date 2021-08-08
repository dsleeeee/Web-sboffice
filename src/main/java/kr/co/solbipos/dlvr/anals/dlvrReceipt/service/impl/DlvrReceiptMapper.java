package kr.co.solbipos.dlvr.anals.dlvrReceipt.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.dlvr.anals.dlvrReceipt.service.DlvrReceiptVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DlvrReceiptMapper.java
 * @Description : 배달관리 > 배달분석 > 배달지별 내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.09  Joshua      최초생성
 *
 * @author
 * @since 2020.07.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface DlvrReceiptMapper {
    /** 배달지별 - 조회*/
    List<DefaultMap<String>> getDlvrReceiptList(DlvrReceiptVO dlvrReceiptVO);

    /** 배달지별 - 상세조회*/
    List<DefaultMap<String>> getDlvrReceiptDetailList(DlvrReceiptVO dlvrReceiptVO);
}
