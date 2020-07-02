package kr.co.solbipos.membr.anals.incln.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.incln.service.InclnService;
import kr.co.solbipos.membr.anals.incln.service.InclnVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("InclnService")
@Transactional
public class InclnServiceImpl implements InclnService{
  private final InclnMapper mapper;

  @Autowired
  public InclnServiceImpl(InclnMapper mapper) {
    this.mapper = mapper;
  }

  public List<DefaultMap<Object>> getInclnList(InclnVO inclnVo,
                                               SessionInfoVO sessionInfoVO) {
    return mapper.getInclnList(inclnVo);

  }
}
