package kr.co.solbipos.mobile.stock.status.currUnity.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.stock.status.currUnity.service.MobileCurrUnityService;
import kr.co.solbipos.mobile.stock.status.currUnity.service.MobileCurrUnityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name : MobileCurrUnityServiceImpl.java
 * @Description : (모바일)재고현황 > 본사매장통합현재고
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
@Service("mobileCurrUnityService")
@Transactional
public class MobileCurrUnityServiceImpl implements MobileCurrUnityService {

    private final MobileCurrUnityMapper mobileCurrUnityMapper;

    @Autowired
    public MobileCurrUnityServiceImpl(MobileCurrUnityMapper mobileCurrUnityMapper) {
        this.mobileCurrUnityMapper = mobileCurrUnityMapper;
    }

    /** 본사매장통합현재고 - 본사매장통합현재고 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCurrUnityList(MobileCurrUnityVO mobileCurrUnityVO, SessionInfoVO sessionInfoVO) {

        mobileCurrUnityVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileCurrUnityVO.setEmpNo(sessionInfoVO.getEmpNo());
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(mobileCurrUnityVO.getVendrCd()).equals("")) {
            mobileCurrUnityVO.setArrVendrCd(mobileCurrUnityVO.getVendrCd().split(","));
        }

        return mobileCurrUnityMapper.getCurrUnityList(mobileCurrUnityVO);
    }

    /** 본사매장통합현재고 - 본사매장통합현재고 본사 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCurrUnityHqDtlList(MobileCurrUnityVO mobileCurrUnityVO, SessionInfoVO sessionInfoVO) {

        mobileCurrUnityVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(mobileCurrUnityVO.getVendrCd()).equals("")) {
            mobileCurrUnityVO.setArrVendrCd(mobileCurrUnityVO.getVendrCd().split(","));
        }

        return mobileCurrUnityMapper.getCurrUnityHqDtlList(mobileCurrUnityVO);
    }

    /** 본사매장통합현재고 - 본사매장통합현재고 매장 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCurrUnityStoreDtlList(MobileCurrUnityVO mobileCurrUnityVO, SessionInfoVO sessionInfoVO) {

        mobileCurrUnityVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileCurrUnityVO.setEmpNo(sessionInfoVO.getEmpNo());
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(mobileCurrUnityVO.getVendrCd()).equals("")) {
            mobileCurrUnityVO.setArrVendrCd(mobileCurrUnityVO.getVendrCd().split(","));
        }

        return mobileCurrUnityMapper.getCurrUnityStoreDtlList(mobileCurrUnityVO);
    }

    /** 본사매장통합현재고 - 본사매장통합현재고 전체 엑셀 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCurrUnityExcelList(MobileCurrUnityVO mobileCurrUnityVO, SessionInfoVO sessionInfoVO) {
        mobileCurrUnityVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(mobileCurrUnityVO.getVendrCd()).equals("")) {
            mobileCurrUnityVO.setArrVendrCd(mobileCurrUnityVO.getVendrCd().split(","));
        }

        return mobileCurrUnityMapper.getCurrUnityExcelList(mobileCurrUnityVO);
    }

    /** 본사매장통합현재고 - 본사매장통합현재고 본사 상세 전체 엑셀 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCurrUnityHqDtlExcelList(MobileCurrUnityVO mobileCurrUnityVO, SessionInfoVO sessionInfoVO) {
        mobileCurrUnityVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(mobileCurrUnityVO.getVendrCd()).equals("")) {
            mobileCurrUnityVO.setArrVendrCd(mobileCurrUnityVO.getVendrCd().split(","));
        }

        return mobileCurrUnityMapper.getCurrUnityHqDtlExcelList(mobileCurrUnityVO);
    }

    /** 본사매장통합현재고 - 본사매장통합현재고 매장 상세 전체 엑셀 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCurrUnityStoreDtlExcelList(MobileCurrUnityVO mobileCurrUnityVO,
                                                                  SessionInfoVO sessionInfoVO) {
        mobileCurrUnityVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(mobileCurrUnityVO.getVendrCd()).equals("")) {
            mobileCurrUnityVO.setArrVendrCd(mobileCurrUnityVO.getVendrCd().split(","));
        }

        return mobileCurrUnityMapper.getCurrUnityStoreDtlExcelList(mobileCurrUnityVO);
    }
}
