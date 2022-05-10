package kr.co.solbipos.sale.dlvr.dlvrFg.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.dlvr.dlvrFg.service.DlvrFgService;
import kr.co.solbipos.sale.dlvr.dlvrFg.service.DlvrFgVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DlvrFgServiceImpl.java
 * @Description : 매출관리 > 배달현황 > 내점/배달/포장 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.21  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.05.21
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("dlvrFgService")
@Transactional
public class DlvrFgServiceImpl implements DlvrFgService {
    private final DlvrFgMapper dlvrFgMapper;

    public DlvrFgServiceImpl(DlvrFgMapper dlvrFgMapper) {
        this.dlvrFgMapper = dlvrFgMapper;
    }

    /** 배달구분 */
    @Override
    public String getDlvrFg(DlvrFgVO dlvrFgVO, SessionInfoVO sessionInfoVO) {
        dlvrFgVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dlvrFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dlvrFgVO.setStoreCd(sessionInfoVO.getStoreCd());

        return String.valueOf(dlvrFgMapper.getDlvrFg(dlvrFgVO));
    }

    /** 배달구분 콤보박스 데이터 */
    @Override
    public List<DefaultMap<String>> getDlvrFgData(DlvrFgVO dlvrFgVO, SessionInfoVO sessionInfoVO) {
        dlvrFgVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dlvrFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dlvrFgVO.setStoreCd(sessionInfoVO.getStoreCd());

        List<DefaultMap<String>> resultList = dlvrFgMapper.getDlvrFgData(dlvrFgVO);

        DefaultMap<String> dateMap1 = new DefaultMap<String>();
        dateMap1.put("value", "0");
        dateMap1.put("name", "일반");

        DefaultMap<String> dateMap2 = new DefaultMap<String>();
        dateMap2.put("value", "1");
        dateMap2.put("name", "CID");

        resultList.add(0, dateMap1);
        resultList.add(1, dateMap2);

        return resultList;
    }


    /** 상품별탭 - 유형별 */
    @Override
    public List<DefaultMap<Object>> getOrderFg(DlvrFgVO dlvrFgVO, SessionInfoVO sessionInfoVO) {

        dlvrFgVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dlvrFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dlvrFgVO.setEmpNo(sessionInfoVO.getEmpNo());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if(!StringUtil.getOrBlank(dlvrFgVO.getStoreCd()).equals("")) {
                dlvrFgVO.setArrStoreCd(dlvrFgVO.getStoreCd().split(","));
            }
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dlvrFgVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 결제수단 array 값 세팅
        dlvrFgVO.setArrPayCol(dlvrFgVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = dlvrFgVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        dlvrFgVO.setPivotPayCol(pivotPayCol);

        return dlvrFgMapper.getOrderFg(dlvrFgVO);
    }

    /** 상품별탭 - 상품별 */
    @Override
    public List<DefaultMap<Object>> getProd(DlvrFgVO dlvrFgVO, SessionInfoVO sessionInfoVO) {

        dlvrFgVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dlvrFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dlvrFgVO.setEmpNo(sessionInfoVO.getEmpNo());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if(!StringUtil.getOrBlank(dlvrFgVO.getStoreCd()).equals("")) {
                dlvrFgVO.setArrStoreCd(dlvrFgVO.getStoreCd().split(","));
            }
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dlvrFgVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 결제수단 array 값 세팅
        dlvrFgVO.setArrPayCol(dlvrFgVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = dlvrFgVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        dlvrFgVO.setPivotPayCol(pivotPayCol);

        return dlvrFgMapper.getProd(dlvrFgVO);
    }

    /** 상품별탭 - 상품별(상세) */
    @Override
    public List<DefaultMap<Object>> getProdDtl(DlvrFgVO dlvrFgVO, SessionInfoVO sessionInfoVO) {

        dlvrFgVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dlvrFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dlvrFgVO.setEmpNo(sessionInfoVO.getEmpNo());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if(!StringUtil.getOrBlank(dlvrFgVO.getStoreCd()).equals("")) {
                dlvrFgVO.setArrStoreCd(dlvrFgVO.getStoreCd().split(","));
            }
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dlvrFgVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 결제수단 array 값 세팅
        dlvrFgVO.setArrPayCol(dlvrFgVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = dlvrFgVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        dlvrFgVO.setPivotPayCol(pivotPayCol);

        return dlvrFgMapper.getProdDtl(dlvrFgVO);
    }

    /** 상품-영수별매출상세 */
    @Override
    public List<DefaultMap<Object>> getSaleDtl(DlvrFgVO dlvrFgVO, SessionInfoVO sessionInfoVO) {

        dlvrFgVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dlvrFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dlvrFgVO.setEmpNo(sessionInfoVO.getEmpNo());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if(!StringUtil.getOrBlank(dlvrFgVO.getStoreCd()).equals("")) {
                dlvrFgVO.setArrStoreCd(dlvrFgVO.getStoreCd().split(","));
            }
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dlvrFgVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 결제수단 array 값 세팅
        dlvrFgVO.setArrPayCol(dlvrFgVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = dlvrFgVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        dlvrFgVO.setPivotPayCol(pivotPayCol);

        return dlvrFgMapper.getSaleDtl(dlvrFgVO);
    }
}