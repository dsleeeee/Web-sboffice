package kr.co.solbipos.dlvr.anals.dlvrInfo.service.impl;


import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.dlvr.anals.dlvrInfo.service.DlvrInfoService;
import kr.co.solbipos.dlvr.anals.dlvrInfo.service.DlvrInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

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
  public List<DefaultMap<Object>> getDlvrInfoList(@RequestBody DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO) {
    dlvrInfoVO.setStoreCd(sessionInfoVO.getStoreCd());
    return dlvrInfoMapper.getDlvrInfoList(dlvrInfoVO);
  }

  @Override
  public DefaultMap<String> getBillInfo(DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO) {
    return dlvrInfoMapper.getBillInfo(dlvrInfoVO);
  }

  @Override
  public List<DefaultMap<Object>> getBillInfoList(DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO) {
    return dlvrInfoMapper.getBillInfoList(dlvrInfoVO);
  }


}
