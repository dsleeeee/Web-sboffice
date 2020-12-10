package kr.co.solbipos.membr.anals.incln.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.incln.service.InclnService;
import kr.co.solbipos.membr.anals.incln.service.InclnVO;
import kr.co.solbipos.membr.info.regist.service.impl.RegistMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("InclnService")
@Transactional
public class InclnServiceImpl implements InclnService {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  private final InclnMapper inclnMapper;

  @Autowired
  public InclnServiceImpl(InclnMapper inclnMapper) {
    this.inclnMapper = inclnMapper;
  }

  /** 회원 구매성향 분석 조회 */
  @Override
  public List<DefaultMap<Object>> getInclnList(InclnVO inclnVo, SessionInfoVO sessionInfoVO) {

    LOGGER.debug("sessionInfoVO.getOrgnGrpCd(): {}", sessionInfoVO.getOrgnGrpCd());
    LOGGER.debug("sessionInfoVO.getOrgnFg(): {}", sessionInfoVO.getOrgnFg());
    LOGGER.debug("sessionInfoVO.getHqOfficeCd(): {}", sessionInfoVO.getHqOfficeCd());
    LOGGER.debug("sessionInfoVO.getStoreCd(): {}", sessionInfoVO.getStoreCd());

    inclnVo.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    inclnVo.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());  //회원소속코드
    inclnVo.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
      inclnVo.setStoreCd(sessionInfoVO.getStoreCd());
    }

    return inclnMapper.getInclnList(inclnVo);
  }
}