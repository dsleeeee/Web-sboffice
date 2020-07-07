package kr.co.solbipos.membr.info.dlvr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.info.dlvr.service.DlvrService;
import kr.co.solbipos.membr.info.dlvr.service.DlvrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("dlvrService")
@Transactional
public class DlvrServiceImpl implements DlvrService {
  private final DlvrMapper dlvrMapper;

  @Autowired
  public DlvrServiceImpl(DlvrMapper dlvrMapper) {
    this.dlvrMapper = dlvrMapper;
  }

  @Override
  public List<DefaultMap<Object>> getDlvrList(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO) {
    return dlvrMapper.getDlvrList(dlvrVO);
  }

  @Override
  public List<DefaultMap<Object>> getDlvrTelList(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO) {
    return dlvrMapper.getDlvrTelList(dlvrVO);
  }

  @Override
  public int deleteDlvr(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO) {
    return dlvrMapper.deleteDlvr(dlvrVO);
  }

  @Override
  public int deleteDlvrTel(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO) {
    return dlvrMapper.deleteDlvrTel(dlvrVO);
  }

}
