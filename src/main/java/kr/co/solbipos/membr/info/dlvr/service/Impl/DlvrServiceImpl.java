package kr.co.solbipos.membr.info.dlvr.service.Impl;


import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.mony.drawhist.service.impl.DrawHistServiceImpl;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.info.dlvr.service.DlvrService;
import kr.co.solbipos.membr.info.dlvr.service.DlvrVO;
import kr.co.solbipos.membr.info.point.service.MemberPointVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("DlvrService")
@Transactional
public class DlvrServiceImpl implements DlvrService {
  @Override
  public List<DefaultMap<Object>> getDlvrList(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO) {
    return null;
  }
//  private final DlvrMapper dlvrMapper;
//
//  @Autowired
//  public DlvrServiceImpl(DlvrMapper dlvrMapper) {
//    this.dlvrMapper = dlvrMapper;
//  }
//
//  @Override
//  public List<DefaultMap<Object>> getDlvrList(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO) {
//    return dlvrMapper.getDlvrList(dlvrVO);
//  }
}
