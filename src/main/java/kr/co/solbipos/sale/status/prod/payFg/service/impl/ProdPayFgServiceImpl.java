package kr.co.solbipos.sale.status.prod.payFg.service.impl;

import kr.co.common.data.domain.CustomComboVO;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.prod.payFg.service.ProdPayFgService;
import kr.co.solbipos.sale.status.prod.payFg.service.ProdPayFgVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProdPayFgService")
public class ProdPayFgServiceImpl implements ProdPayFgService {
    private final ProdPayFgMapper prodPayFgMapper;
    private final MessageService messageService;

    @Autowired
    public ProdPayFgServiceImpl(ProdPayFgMapper prodPayFgMapper, MessageService messageService) {
    	this.prodPayFgMapper = prodPayFgMapper;
        this.messageService = messageService;
    }

    /** 상품별 매출 - 결제수단 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPayColList(ProdPayFgVO prodPayFgVO, SessionInfoVO sessionInfoVO) {

        prodPayFgVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodPayFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (prodPayFgVO.getStoreCd() == null || "".equals(prodPayFgVO.getStoreCd())) {
            prodPayFgVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return prodPayFgMapper.getPayColList(prodPayFgVO);
    }

    /** 결제수단별탭 - 조회 */
    @Override
    public List<DefaultMap<String>> getProdPayFgList(ProdPayFgVO prodPayFgVO, SessionInfoVO sessionInfoVO) {

        prodPayFgVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    	prodPayFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodPayFgVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(prodPayFgVO.getStoreCd()).equals("")) {
            prodPayFgVO.setArrStoreCd(prodPayFgVO.getStoreCd().split(","));
        }

        // 결제수단 array 값 세팅
        prodPayFgVO.setArrPayCol(prodPayFgVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = prodPayFgVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        prodPayFgVO.setPivotPayCol(pivotPayCol);

        return prodPayFgMapper.getProdPayFgList(prodPayFgVO);
    }

    /** 결제수단별탭 - 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getProdPayFgExcelList(ProdPayFgVO prodPayFgVO, SessionInfoVO sessionInfoVO) {

        prodPayFgVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodPayFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodPayFgVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(!StringUtil.getOrBlank(prodPayFgVO.getStoreCd()).equals("")) {
            prodPayFgVO.setArrStoreCd(prodPayFgVO.getStoreCd().split(","));
        }

        // 결제수단 array 값 세팅
        prodPayFgVO.setArrPayCol(prodPayFgVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = prodPayFgVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        prodPayFgVO.setPivotPayCol(pivotPayCol);

        return prodPayFgMapper.getProdPayFgExcelList(prodPayFgVO);
    }
    
}