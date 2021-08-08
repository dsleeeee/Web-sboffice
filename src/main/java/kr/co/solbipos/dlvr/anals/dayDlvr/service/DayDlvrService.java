package kr.co.solbipos.dlvr.anals.dayDlvr.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
/**
 * @Class Name : DlvrInfoService.java
 * @Description : 배달관리 > 배달분석 > 일자별 배달현황
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
public interface DayDlvrService {

  /** 배달매출조회 */
  List<DefaultMap<Object>> getDayDlvrSaleList(DayDlvrVO dayDlvrVO, SessionInfoVO sessionInfoVO);

  /** 배달외매출조회 */
  List<DefaultMap<Object>> getDayNonDlvrSaleList(DayDlvrVO dayDlvrVO, SessionInfoVO sessionInfoVO);

  /** 배달매출상세조회 */
  List<DefaultMap<Object>> getDaySaleDtlList(DayDlvrVO dayDlvrVO, SessionInfoVO sessionInfoVO);
}
