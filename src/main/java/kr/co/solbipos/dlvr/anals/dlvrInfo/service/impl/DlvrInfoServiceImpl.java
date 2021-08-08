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

/**
 * @Class Name : DlvrInfoServiceImpl.java
 * @Description : 배달관리 > 배달분석 > 배달내역
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
@Service("dlvrInfoService")
@Transactional
public class DlvrInfoServiceImpl implements DlvrInfoService {
  private final DlvrInfoMapper dlvrInfoMapper;

  @Autowired
  public DlvrInfoServiceImpl(DlvrInfoMapper dlvrInfoMapper) {
    this.dlvrInfoMapper = dlvrInfoMapper;
  }

  /** 배달내역조회 */
  @Override
  public List<DefaultMap<Object>> getDlvrInfoList(@RequestBody DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO) {
    dlvrInfoVO.setStoreCd(sessionInfoVO.getStoreCd());
    return dlvrInfoMapper.getDlvrInfoList(dlvrInfoVO);
  }

  /** 영수증 상세 조회 */
  @Override
  public DefaultMap<String> getBillInfo(DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO) {
    return dlvrInfoMapper.getBillInfo(dlvrInfoVO);
  }

  /** 영수증 상세 조회 */
  @Override
  public List<DefaultMap<Object>> getBillInfoList(DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO) {
    return dlvrInfoMapper.getBillInfoList(dlvrInfoVO);
  }


}
