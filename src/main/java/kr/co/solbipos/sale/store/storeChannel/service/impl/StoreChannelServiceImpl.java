package kr.co.solbipos.sale.store.storeChannel.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelService;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : StoreChannelServiceImpl.java
 * @Description : 맘스터치 > 점포매출 > 채널별 매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.14  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeChannelService")
@Transactional
public class StoreChannelServiceImpl implements StoreChannelService {

    private final StoreChannelMapper storeChannelMapper;

    public StoreChannelServiceImpl(StoreChannelMapper storeChannelMapper) {
        this.storeChannelMapper = storeChannelMapper;
    }

    /** 주문채널별현황 - 결제수단 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPayColList(StoreChannelVO storeChannelVO, SessionInfoVO sessionInfoVO){
        return storeChannelMapper.getPayColList(storeChannelVO);
    }

    /** 주문채널별현황 - 할인수단 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDcColList(StoreChannelVO storeChannelVO, SessionInfoVO sessionInfoVO){
        return storeChannelMapper.getDcColList(storeChannelVO);
    }

    /** 주문채널별현황 - 객수 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getGuestColList(StoreChannelVO storeChannelVO, SessionInfoVO sessionInfoVO){
        storeChannelVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            storeChannelVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return storeChannelMapper.getGuestColList(storeChannelVO);
    }

    /** 주문채널별현황 - 주문채널 구분자 조회 */
    @Override
    public List<DefaultMap<String>> getDlvrInFgColList(StoreChannelVO storeChannelVO, SessionInfoVO sessionInfoVO) {
        storeChannelVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            storeChannelVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return storeChannelMapper.getDlvrInFgColList(storeChannelVO);
    }

    /** 주문채널별현황 - 기간별 탭 조회 */
    @Override
    public List<DefaultMap<String>> getStoreChannelPeriodList(StoreChannelVO storeChannelVO, SessionInfoVO sessionInfoVO) {
        storeChannelVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            storeChannelVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 매장코드
        if(!StringUtil.getOrBlank(storeChannelVO.getSrchStoreCd()).equals("")) {
            storeChannelVO.setArrStoreCd(storeChannelVO.getSrchStoreCd().split(","));
        }

        // 결제수단 array 값 세팅
        storeChannelVO.setArrPayCol(storeChannelVO.getPayCol().split(","));
        // 할인구분 array 값 세팅
        storeChannelVO.setArrDcCol(storeChannelVO.getDcCol().split(","));
        // 객수컬럼 array 값 세팅
        storeChannelVO.setArrGuestCol(storeChannelVO.getGuestCol().split(","));

        return storeChannelMapper.getStoreChannelPeriodList(storeChannelVO);
    }

    /** 주문채널별현황 - 일별 탭 조회 */
    @Override
    public List<DefaultMap<String>> getStoreChannelDayList(StoreChannelVO storeChannelVO, SessionInfoVO sessionInfoVO) {

        storeChannelVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            storeChannelVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 매장코드
        if(!StringUtil.getOrBlank(storeChannelVO.getSrchStoreCd()).equals("")) {
            storeChannelVO.setArrStoreCd(storeChannelVO.getSrchStoreCd().split(","));
        }

        // 주문채널 구분자 array 값 세팅
        storeChannelVO.setArrDlvrInFgCol(storeChannelVO.getDlvrInFgCol().split(","));

        return storeChannelMapper.getStoreChannelDayList(storeChannelVO);
    }

    /** 주문채널별현황 - 일별 상세 조회 */
    @Override
    public List<DefaultMap<String>> getStoreChannelDtlList(StoreChannelVO storeChannelVO, SessionInfoVO sessionInfoVO) {

        storeChannelVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        System.out.println("받은거 : " + storeChannelVO.getSrchStoreCd() + " / " + storeChannelVO.getSrchStoreCd());
        System.out.println("orgnFg : " + sessionInfoVO.getOrgnFg());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            storeChannelVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        System.out.println("매장 : " + sessionInfoVO.getStoreCd());
        System.out.println("매장arr : " + storeChannelVO.getSrchStoreCd());

        // 매장코드
        if(!StringUtil.getOrBlank(storeChannelVO.getSrchStoreCd()).equals("")) {
            storeChannelVO.setArrStoreCd(storeChannelVO.getSrchStoreCd().split(","));
        }

        return storeChannelMapper.getStoreChannelDtlList(storeChannelVO);
    }

    /** 주문채널별현황 - 월별 탭 조회 */
    @Override
    public List<DefaultMap<String>> getStoreChannelMonthList(StoreChannelVO storeChannelVO, SessionInfoVO sessionInfoVO) {

        storeChannelVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            storeChannelVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 매장코드
        if(!StringUtil.getOrBlank(storeChannelVO.getSrchStoreCd()).equals("")) {
            storeChannelVO.setArrStoreCd(storeChannelVO.getSrchStoreCd().split(","));
        }

        // 주문채널 구분자 array 값 세팅
        storeChannelVO.setArrDlvrInFgCol(storeChannelVO.getDlvrInFgCol().split(","));

        return storeChannelMapper.getStoreChannelMonthList(storeChannelVO);
    }
}
