package kr.co.solbipos.sale.status.prod.rank.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.prod.rank.service.ProdRankService;
import kr.co.solbipos.sale.status.prod.rank.service.ProdRankVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProdRankService")
public class ProdRankServiceImpl implements ProdRankService {
    private final ProdRankMapper prodRankMapper;
    private final MessageService messageService;

    @Autowired
    public ProdRankServiceImpl(ProdRankMapper prodRankMapper, MessageService messageService) {
    	this.prodRankMapper = prodRankMapper;
        this.messageService = messageService;
    }

    /** 상품매출순위탭 - 조회 */
    @Override
    public List<DefaultMap<String>> getProdRankList(ProdRankVO prodRankVO, SessionInfoVO sessionInfoVO) {

        prodRankVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    	prodRankVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(prodRankVO.getStoreCd()).equals("")) {
        	prodRankVO.setArrStoreCd(prodRankVO.getStoreCd().split(","));
        }

        return prodRankMapper.getProdRankList(prodRankVO);
    }

    /** 상품매출순위탭 - 차트 조회 */
    @Override
    public List<DefaultMap<String>> getProdRankChartList(ProdRankVO prodRankVO, SessionInfoVO sessionInfoVO) {

        prodRankVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    	prodRankVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodRankVO.setEmpNo(sessionInfoVO.getEmpNo());

    	if (!StringUtil.getOrBlank(prodRankVO.getStoreCd()).equals("")) {
    		prodRankVO.setArrStoreCd(prodRankVO.getStoreCd().split(","));
		}

        return prodRankMapper.getProdRankChartList(prodRankVO);
    }

    /** 상품매출순위탭 - 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getProdRankExcelList(ProdRankVO prodRankVO, SessionInfoVO sessionInfoVO) {

        prodRankVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
    	prodRankVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodRankVO.setEmpNo(sessionInfoVO.getEmpNo());

    	if (!StringUtil.getOrBlank(prodRankVO.getStoreCd()).equals("")) {
    		prodRankVO.setArrStoreCd(prodRankVO.getStoreCd().split(","));
		}

        return prodRankMapper.getProdRankExcelList(prodRankVO);
    }

}