package kr.co.solbipos.dlvr.anals.dayDlvr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.dlvr.anals.dayDlvr.service.DayDlvrService;
import kr.co.solbipos.dlvr.anals.dayDlvr.service.DayDlvrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name : DlvrInfoServiceImpl.java
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
@Service("dayDlvrService")
@Transactional
public class DayDlvrServiceImpl implements DayDlvrService {
  private final DayDlvrMapper dayDlvrMapper;

  @Autowired
  public DayDlvrServiceImpl(DayDlvrMapper dayDlvrMapper) {
    this.dayDlvrMapper = dayDlvrMapper;
  }

  /** 배달매출조회 */
  @Override
  public List<DefaultMap<Object>> getDayDlvrSaleList(DayDlvrVO dayDlvrVO, SessionInfoVO sessionInfoVO) {
    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
      dayDlvrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    }
    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
      dayDlvrVO.setStoreCd(sessionInfoVO.getStoreCd());
    }
    return dayDlvrMapper.getDayDlvrSaleList(dayDlvrVO);
  }

  /** 배달외매출조회 */
  @Override
  public List<DefaultMap<Object>> getDayNonDlvrSaleList(DayDlvrVO dayDlvrVO, SessionInfoVO sessionInfoVO) {
    return dayDlvrMapper.getDayNonDlvrSaleList(dayDlvrVO);
  }

  /** 배달매출상세조회 */
  @Override
  public List<DefaultMap<Object>> getDaySaleDtlList(DayDlvrVO dayDlvrVO, SessionInfoVO sessionInfoVO) {
    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
      dayDlvrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    }
    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
      dayDlvrVO.setStoreCd(sessionInfoVO.getStoreCd());
    }
    return dayDlvrMapper.getDaySaleDtlList(dayDlvrVO);
  }
}
