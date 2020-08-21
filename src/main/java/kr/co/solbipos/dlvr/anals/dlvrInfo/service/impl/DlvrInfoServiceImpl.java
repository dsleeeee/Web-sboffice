package kr.co.solbipos.dlvr.anals.dlvrInfo.service.impl;


import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.dlvr.anals.dlvrInfo.service.DlvrInfoService;
import kr.co.solbipos.dlvr.anals.dlvrInfo.service.DlvrInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("dlvrInfoService")
@Transactional
public class DlvrInfoServiceImpl implements DlvrInfoService {
  private final DlvrInfoMapper dlvrInfoMapper;

  @Autowired
  public DlvrInfoServiceImpl(DlvrInfoMapper dlvrInfoMapper) {
    this.dlvrInfoMapper = dlvrInfoMapper;
  }

  @Override
  public List<DefaultMap<Object>> getDlvrInfoList(DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO) {
    return dlvrInfoMapper.getDlvrInfoList(dlvrInfoVO);
  }
}
