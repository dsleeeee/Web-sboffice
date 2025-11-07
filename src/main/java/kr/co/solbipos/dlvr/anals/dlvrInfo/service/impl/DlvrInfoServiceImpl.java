package kr.co.solbipos.dlvr.anals.dlvrInfo.service.impl;


import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.dlvr.anals.dlvrInfo.service.DlvrInfoService;
import kr.co.solbipos.dlvr.anals.dlvrInfo.service.DlvrInfoVO;
import kr.co.solbipos.sale.cmmSalePopup.billInfo.service.BillInfoVO;
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
    dlvrInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    dlvrInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    dlvrInfoVO.setStoreCd(sessionInfoVO.getStoreCd());
    return dlvrInfoMapper.getDlvrInfoList(dlvrInfoVO);
  }

  /** 배달내역 엑셀다운로드 조회 */
  @Override
  public List<DefaultMap<Object>> getDlvrInfoExcelList(@RequestBody DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO) {
    dlvrInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    dlvrInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    dlvrInfoVO.setStoreCd(sessionInfoVO.getStoreCd());
    return dlvrInfoMapper.getDlvrInfoExcelList(dlvrInfoVO);
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

  @Override
  public DefaultMap<String> getBillPayInfo(DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO) {
    dlvrInfoVO = setPayCol(dlvrInfoVO, sessionInfoVO);
    return dlvrInfoMapper.getBillPayInfo(dlvrInfoVO);
  }

  @Override
  public DefaultMap<String> getBillGuestInfo(DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO) {
    return dlvrInfoMapper.getBillGuestInfo(dlvrInfoVO);
  }

  @Override
  public List<DefaultMap<String>> getBillProdList(DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO) {
    dlvrInfoVO.setArrDcCol(dlvrInfoVO.getDcCol().split(","));
    // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
    String pivotDcCol = "";
    String arrDcCol[] = dlvrInfoVO.getDcCol().split(",");
    for(int i=0; i < arrDcCol.length; i++) {
      pivotDcCol += (pivotDcCol.equals("") ? "" : ",") + "'"+arrDcCol[i]+"'"+" AS DC"+arrDcCol[i];
    }
    dlvrInfoVO.setPivotDcCol(pivotDcCol);

    return dlvrInfoMapper.getBillProdList(dlvrInfoVO);
  }

  /** 결제수단 컬럼 세팅  */
  public DlvrInfoVO setPayCol(DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO) {

    /** PAY_CD = 02 현금,현금영수증 분리 */

    // 결제수단 array 값 세팅
    String payCol = "";

    // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
    String pivotPayCol = "";
    String arrPayCol[] = dlvrInfoVO.getPayCol().split(",");
    for (int i = 0; i < arrPayCol.length; i++) {
      // 현금,현금영수증 제외
      if (!(("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i]))) {
        pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
        payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
      }
    }
    dlvrInfoVO.setPivotPayCol(pivotPayCol);
    dlvrInfoVO.setArrPayCol(payCol.split(","));

    return dlvrInfoVO;
  }


}
