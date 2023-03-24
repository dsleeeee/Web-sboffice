package kr.co.solbipos.sale.status.payTemporary.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.payTemporary.service.PayTemporaryService;
import kr.co.solbipos.sale.status.payTemporary.service.PayTemporaryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("payTemporaryService")
public class PayTemporaryServiceImpl implements PayTemporaryService {
    private final PayTemporaryMapper payTemporaryMapper;

    @Autowired
    public PayTemporaryServiceImpl(PayTemporaryMapper payTemporaryMapper) {
        this.payTemporaryMapper = payTemporaryMapper;
    }

    /** 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPayTemporaryList(PayTemporaryVO payTemporaryVO, SessionInfoVO sessionInfoVO) {
        payTemporaryVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        payTemporaryVO.setEmpNo(sessionInfoVO.getEmpNo());
        payTemporaryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        String[] storeCds = payTemporaryVO.getStoreCds().split(",");
        payTemporaryVO.setStoreCdList(storeCds);

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (payTemporaryVO.getStoreHqBrandCd() == "" || payTemporaryVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = payTemporaryVO.getUserBrands().split(",");
                payTemporaryVO.setUserBrandList(userBrandList);
            }
        }

        return payTemporaryMapper.getPayTemporaryList(payTemporaryVO);
    }

    @Override
    public List<DefaultMap<String>> getPayTemporaryExcelList(PayTemporaryVO payTemporaryVO, SessionInfoVO sessionInfoVO) {
        payTemporaryVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        payTemporaryVO.setEmpNo(sessionInfoVO.getEmpNo());
        payTemporaryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        String[] storeCds = payTemporaryVO.getStoreCds().split(",");
        payTemporaryVO.setStoreCdList(storeCds);

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (payTemporaryVO.getStoreHqBrandCd() == "" || payTemporaryVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = payTemporaryVO.getUserBrands().split(",");
                payTemporaryVO.setUserBrandList(userBrandList);
            }
        }

        return payTemporaryMapper.getPayTemporaryExcelList(payTemporaryVO);
    }

    @Override
    public List<DefaultMap<String>> getPayTemporaryDtlList(PayTemporaryVO payTemporaryVO, SessionInfoVO sessionInfoVO) {
        payTemporaryVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        payTemporaryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg().equals(OrgnFg.STORE)){
            payTemporaryVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return payTemporaryMapper.getPayTemporaryDtlList(payTemporaryVO);
    }

}