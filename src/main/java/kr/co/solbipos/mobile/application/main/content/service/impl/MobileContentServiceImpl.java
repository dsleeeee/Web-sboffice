package kr.co.solbipos.mobile.application.main.content.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.DateUtil;
import kr.co.solbipos.application.common.enums.MainSrchFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.application.main.content.service.MobileContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Class Name : MobileContentServiceImpl.java
 * @Description : (모바일) 어플리케이션 > 메인 > 내용
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.27  김설아      최초생성
 *
 * @author 솔비포스 WEB개발팀 김설아
 * @since 2021.05.27
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mobileContentService")
public class MobileContentServiceImpl implements MobileContentService {

    private final MobileContentMapper mobileContentMapper;

    /** Constructor Injection */
    @Autowired
    public MobileContentServiceImpl(MobileContentMapper mobileContentMapper) {
        this.mobileContentMapper = mobileContentMapper;
    }

    /** 총 매장수(전체, 오픈, 폐점, 중지, 데모) */
    @Override
    public List<DefaultMap<String>> getMobileStoreCntList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.MASTER) {
            result = mobileContentMapper.getMobileStoreCntList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            result = mobileContentMapper.getMobileStoreCntAgencyList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = mobileContentMapper.getMobileStoreCntHqList(sessionInfoVO);
        }

        return result;
    }

    /** 총 포스수(전체, 오픈, 폐점, 중지, 데모) */
    @Override
    public List<DefaultMap<String>> getMobilePosCntList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.MASTER) {
            result = mobileContentMapper.getMobilePosCntList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            result = mobileContentMapper.getMobilePosCntAgencyList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = mobileContentMapper.getMobilePosCntHqList(sessionInfoVO);
        }

        return result;
    }

    /** 주간매출(매장수/포스수) */
    @Override
    public List<DefaultMap<String>> getMobileWeekSaleList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.MASTER) {
            result = mobileContentMapper.getMobileWeekSaleList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            result = mobileContentMapper.getMobileWeekSaleAgencyList(sessionInfoVO);
        }

        return result;
    }

    /** 공지사항 */
    @Override
    public List<DefaultMap<String>> getMobileNoticeList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.MASTER) {
            result = mobileContentMapper.getMobileNoticeList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            result = mobileContentMapper.getMobileNoticeAgencyList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = mobileContentMapper.getMobileNoticeHqList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            result = mobileContentMapper.getMobileNoticeStoreList(sessionInfoVO);
        }

        return result;
    }

    /** 주간 POS 설치현황(신규설치/재설치) */
    @Override
    public List<DefaultMap<String>> getMobileWeekPosInstList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.MASTER) {
            result = mobileContentMapper.getMobileWeekPosInstList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            result = mobileContentMapper.getMobileWeekPosInstAgencyList(sessionInfoVO);
        }

        return result;
    }

    /** 주간 POS 설치 상위 대리점 */
    @Override
    public List<DefaultMap<String>> getMobileWeekPosInstTopList(SessionInfoVO sessionInfoVO) {
        return mobileContentMapper.getMobileWeekPosInstTopList(sessionInfoVO);
    }

    /** 주간 매출 상위 가맹점 */
    @Override
    public List<DefaultMap<String>> getMobileWeekSaleAgencyTopList(SessionInfoVO sessionInfoVO) {
        return mobileContentMapper.getMobileWeekSaleAgencyTopList(sessionInfoVO);
    }

    /** 매출현황 */
    @Override
    public List<DefaultMap<String>> getMobileSaleWeekList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = mobileContentMapper.getMobileSaleWeekList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            result = mobileContentMapper.getMobileSaleWeekStoreList(sessionInfoVO);
        }

        return result;
    }

    /** 매출 상위 상품 */
    @Override
    public List<DefaultMap<String>> getMobileSaleProdWeekList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = mobileContentMapper.getMobileSaleProdWeekList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            result = mobileContentMapper.getMobileSaleProdWeekStoreList(sessionInfoVO);
        }

        return result;
    }

    /** 매출 상위 가맹점 */
    @Override
    public List<DefaultMap<String>> getMobileSaleStoreWeekList(SessionInfoVO sessionInfoVO) {
        return mobileContentMapper.getMobileSaleStoreWeekList(sessionInfoVO);
    }

    /** 오늘의 매출건수 */
    @Override
    public List<DefaultMap<String>> getMobileDaySaleCntList(SessionInfoVO sessionInfoVO) {
        return mobileContentMapper.getMobileDaySaleCntList(sessionInfoVO);
    }

    /** 오늘의 매출금액 */
    @Override
    public List<DefaultMap<String>> getMobileDaySaleAmtList(SessionInfoVO sessionInfoVO) {
        return mobileContentMapper.getMobileDaySaleAmtList(sessionInfoVO);
    }

}