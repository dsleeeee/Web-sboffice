package kr.co.solbipos.mobile.stock.curr.hqCurr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.stock.curr.hqCurr.service.MobileHqCurrService;
import kr.co.solbipos.mobile.stock.curr.hqCurr.service.MobileHqCurrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name : MobileHqCurrServiceImpl.java
 * @Description : (모바일)재고현황 > 현재고현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.19  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.19
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mobileHqCurrService")
@Transactional
public class MobileHqCurrServiceImpl implements MobileHqCurrService {

    private final MobileHqCurrMapper mobileHqCurrMapper;

    @Autowired
    public MobileHqCurrServiceImpl(MobileHqCurrMapper mobileHqCurrMapper) {
        this.mobileHqCurrMapper = mobileHqCurrMapper;
    }

    /** 현재고현황 - 현재고현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqCurrList(MobileHqCurrVO mobileHqCurrVO, SessionInfoVO sessionInfoVO) {
        mobileHqCurrVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(mobileHqCurrVO.getVendrCd()).equals("")) {
            mobileHqCurrVO.setArrVendrCd(mobileHqCurrVO.getVendrCd().split(","));
        }
        List<DefaultMap<String>> list;
        if(mobileHqCurrVO.getOrgnFg() == "H" && mobileHqCurrVO.getOrgnFg() != null) { // 본사권한
            list = mobileHqCurrMapper.getHqCurrList(mobileHqCurrVO);
        }else { // 매장권한
            mobileHqCurrVO.setStoreCd(sessionInfoVO.getStoreCd());
            list = mobileHqCurrMapper.getHqStoreCurrList(mobileHqCurrVO);
        }
        return list;
    }

    /** 현재고현황 - 현재고현황 상세리스트 조회 */
    @Override
    public List<DefaultMap<String>> gethqCurrDtlList(MobileHqCurrVO mobileHqCurrVO, SessionInfoVO sessionInfoVO) {
        //mobileHqCurrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
//    	if( !StringUtil.getOrBlank(mobileHqCurrVO.getHqOfficeCd()).equals("")) {
//			return hqCurrMapper.getHqCurrDtlList(mobileHqCurrVO);
//		} else {
//			//mobileHqCurrVO.setStoreCd(sessionInfoVO.getStoreCd());
//			return hqCurrMapper.getStoreCurrDtlList(mobileHqCurrVO);
//		}
        mobileHqCurrVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        if(mobileHqCurrVO.getOrgnFg() == "H" && mobileHqCurrVO.getOrgnFg() != null) { // 본사권한
            if( !StringUtil.getOrBlank(mobileHqCurrVO.getHqOfficeCd()).equals("")) {
                return mobileHqCurrMapper.getHqCurrDtlList(mobileHqCurrVO);
            } else {
                return mobileHqCurrMapper.getStoreCurrDtlList(mobileHqCurrVO);
            }
        } else {
            return mobileHqCurrMapper.getStoreCurrDtlList(mobileHqCurrVO);
        }


    }

    /** 현재고현황 - 현재고현황 엑셀 전체 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqCurrExcelList(MobileHqCurrVO mobileHqCurrVO, SessionInfoVO sessionInfoVO) {
        mobileHqCurrVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(mobileHqCurrVO.getVendrCd()).equals("")) {
            mobileHqCurrVO.setArrVendrCd(mobileHqCurrVO.getVendrCd().split(","));
        }
        List<DefaultMap<String>> list;
        if(mobileHqCurrVO.getOrgnFg() == "H" && mobileHqCurrVO.getOrgnFg() != null) { // 본사권한
            list = mobileHqCurrMapper.getHqCurrExcelList(mobileHqCurrVO);
        }else { // 매장권한
            mobileHqCurrVO.setStoreCd(sessionInfoVO.getStoreCd());
            list = mobileHqCurrMapper.getHqStoreCurrExcelList(mobileHqCurrVO);
        }
        return list;
    }
}
