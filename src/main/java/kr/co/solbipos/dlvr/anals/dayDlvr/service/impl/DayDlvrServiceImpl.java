package kr.co.solbipos.dlvr.anals.dayDlvr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.dlvr.anals.dayDlvr.service.DayDlvrService;
import kr.co.solbipos.dlvr.anals.dayDlvr.service.DayDlvrVO;
import kr.co.solbipos.membr.info.dlvr.service.impl.DlvrMapper;
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
  public List<DefaultMap<Object>> getDayDlvrList(DayDlvrVO dayDlvrVO, SessionInfoVO sessionInfoVO) {
    return dayDlvrMapper.getDayDlvrList(dayDlvrVO);
  }
}
