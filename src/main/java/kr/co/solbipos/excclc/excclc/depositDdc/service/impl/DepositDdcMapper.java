package kr.co.solbipos.excclc.excclc.depositDdc.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.excclc.excclc.depositDdc.service.DepositDdcVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DepositDdcMapper.java
 * @Description : 수불관리 > 수주관리 > 입금/공제관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.20  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2022.04.20
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface DepositDdcMapper {

    /** 매장별집계 탭 - 매장별집계 리스트 조회 */
    List<DefaultMap<String>> getStoreTotalList(DepositDdcVO depositDdcVO);

    /** 매장별집계 탭 - 상세내역 리스트 조회 */
    List<DefaultMap<String>> getStoreTotalDtlList(DepositDdcVO depositDdcVO);

    /** 매장별집계 탭 - 입금/기타공제 계정 콤보박스 데이터 조회 */
    List<DefaultMap<String>> getMoneyFgCombo(DepositDdcVO depositDdcVO);

    /** 매장별집계 탭 - 상세내역 리스트의 입금/기타공제 단일건 등록 */
    int insertDepositDdc(DepositDdcVO depositDdcVO);

    /** 매장별집계 탭 - 상세내역 리스트의 입금/기타공제 단일건 조회 */
    DefaultMap<String> getDepositDdc(DepositDdcVO depositDdcVO);

    /** 매장별집계 탭 - 상세내역 리스트의 입금/기타공제 단일건 수정 */
    int updateDepositDdc(DepositDdcVO depositDdcVO);

    /** 매장별집계 탭 - 상세내역 리스트의 입금/기타공제 단일건 삭제 */
    int deleteDepositDdc(DepositDdcVO depositDdcVO);
}
