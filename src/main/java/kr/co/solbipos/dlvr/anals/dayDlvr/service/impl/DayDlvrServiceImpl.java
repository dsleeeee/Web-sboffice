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

@Service("dayDlvrService")
@Transactional
public class DayDlvrServiceImpl implements DayDlvrService {
  private final DayDlvrMapper dayDlvrMapper;

  @Autowired
  public DayDlvrServiceImpl(DayDlvrMapper dayDlvrMapper) {
    this.dayDlvrMapper = dayDlvrMapper;
  }


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

  @Override
  public List<DefaultMap<Object>> getDayNonDlvrSaleList(DayDlvrVO dayDlvrVO, SessionInfoVO sessionInfoVO) {
    return dayDlvrMapper.getDayNonDlvrSaleList(dayDlvrVO);
  }

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
