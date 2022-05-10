package kr.co.solbipos.sale.anals.nonSaleCharge.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.anals.nonSaleCharge.service.NonSaleChargeService;
import kr.co.solbipos.sale.anals.nonSaleCharge.service.NonSaleChargeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service("nonSaleChargeService")
public class NonSaleChargeServiceImpl implements NonSaleChargeService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final NonSaleChargeMapper nonSaleChargeMapper;

    /** Constructor Injection */
    @Autowired
    public NonSaleChargeServiceImpl(NonSaleChargeMapper nonSaleChargeMapper) {
        this.nonSaleChargeMapper = nonSaleChargeMapper;
    }

    /** 비매출 충전내역 조회 */
    @Override
    public List<DefaultMap<String>> getNonSaleChargeList(@RequestBody NonSaleChargeVO nonSaleChargeVO, SessionInfoVO sessionInfoVO) {

        nonSaleChargeVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        nonSaleChargeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        nonSaleChargeVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if(!StringUtil.getOrBlank(nonSaleChargeVO.getStoreCd()).equals("")) {
                nonSaleChargeVO.setArrStoreCd(nonSaleChargeVO.getStoreCd().split(","));
            }
        }

        return nonSaleChargeMapper.getNonSaleChargeList(nonSaleChargeVO);
    }

    /** 비매출 충전내역 조회 Excel 다운로드 */
    @Override
    public List<DefaultMap<String>> getNonSaleChargeExcelList(@RequestBody NonSaleChargeVO nonSaleChargeVO, SessionInfoVO sessionInfoVO) {

        nonSaleChargeVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        nonSaleChargeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        nonSaleChargeVO.setEmpNo(sessionInfoVO.getEmpNo());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if(!StringUtil.getOrBlank(nonSaleChargeVO.getStoreCd()).equals("")) {
                nonSaleChargeVO.setArrStoreCd(nonSaleChargeVO.getStoreCd().split(","));
            }
        }

        return nonSaleChargeMapper.getNonSaleChargeExcelList(nonSaleChargeVO);
    }
}
