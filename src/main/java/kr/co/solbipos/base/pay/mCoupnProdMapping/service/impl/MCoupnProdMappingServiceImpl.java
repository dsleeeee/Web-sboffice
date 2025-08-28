package kr.co.solbipos.base.pay.mCoupnProdMapping.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.pay.mCoupnProdMapping.service.MCoupnProdMappingService;
import kr.co.solbipos.base.pay.mCoupnProdMapping.service.MCoupnProdMappingVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MCoupnProdMappingServiceImpl.java
 * @Description : 기초관리 > 결제수단 > 모바일쿠폰상품매핑
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.19  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.08.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mCoupnProdMappingService")
@Transactional
public class MCoupnProdMappingServiceImpl implements MCoupnProdMappingService {
    private final MCoupnProdMappingMapper mCoupnProdMappingMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MCoupnProdMappingServiceImpl(MCoupnProdMappingMapper mCoupnProdMappingMapper){
        this.mCoupnProdMappingMapper = mCoupnProdMappingMapper;
    }

    /** 모바일쿠폰상품매핑 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMCoupnProdMappingList(MCoupnProdMappingVO mCoupnProdMappingVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<Object>> result = new ArrayList<DefaultMap<Object>>();

        mCoupnProdMappingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mCoupnProdMappingVO.setUserId(sessionInfoVO.getUserId());

        // 조회구분 (A:가로, B:세로)
        if( "A".equals(mCoupnProdMappingVO.getSearchGubun()) ) {
            // 모바일쿠폰사-상품코드 최대수
            int mcoupnProdCnt = mCoupnProdMappingVO.getMCoupnProdCnt();

            // 동적 컬럼 생성을 위한 쿼리 변수;
            String sQuery1 = "";
            String sQuery2 = "";

            for(int i=1; i < mcoupnProdCnt+1; i++) {
                sQuery1 +=", REGEXP_SUBSTR(AA.MCOUPN_PROD_CD_LIST, '[^,]+', 1, "+ i + ") AS MCOUPN_PROD_CD_" + i + "\n";

                sQuery2 +=", thpmpc.MCOUPN_PROD_CD_"+ i + "\n";
            }
            mCoupnProdMappingVO.setsQuery1(sQuery1);
            mCoupnProdMappingVO.setsQuery2(sQuery2);

            result = mCoupnProdMappingMapper.getMCoupnProdMappingList(mCoupnProdMappingVO);
        } else {
            result = mCoupnProdMappingMapper.getMCoupnProdMappingList2(mCoupnProdMappingVO);
        }

        return result;
    }

    /** 모바일쿠폰상품매핑 - 모바일쿠폰사-상품코드 최대수 */
    @Override
    public DefaultMap<Object> getMCoupnProdMappingCnt(MCoupnProdMappingVO mCoupnProdMappingVO, SessionInfoVO sessionInfoVO) {

        mCoupnProdMappingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mCoupnProdMappingVO.setUserId(sessionInfoVO.getUserId());

        return mCoupnProdMappingMapper.getMCoupnProdMappingCnt(mCoupnProdMappingVO);
    }

    /** 모바일쿠폰상품매핑 - 엑셀업로드 양식 조회 */
    @Override
    public List<DefaultMap<Object>> getMCoupnProdMappingExcelUploadSampleList(MCoupnProdMappingVO mCoupnProdMappingVO, SessionInfoVO sessionInfoVO) {

        return mCoupnProdMappingMapper.getMCoupnProdMappingExcelUploadSampleList(mCoupnProdMappingVO);
    }

    /** 모바일쿠폰상품매핑 엑셀업로드 팝업 - 검증결과 전체 삭제 */
    @Override
    public int getMCoupnProdMappingExcelUploadCheckDeleteAll(MCoupnProdMappingVO mCoupnProdMappingVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        mCoupnProdMappingVO.setSessionId(sessionInfoVO.getSessionId());
        mCoupnProdMappingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        procCnt += mCoupnProdMappingMapper.getMCoupnProdMappingExcelUploadCheckDeleteAll(mCoupnProdMappingVO);

        return procCnt;
    }

    /** 모바일쿠폰상품매핑 엑셀업로드 팝업 - 업로드시 임시테이블 저장 */
    @Override
    public int getMCoupnProdMappingExcelUploadCheckSave(MCoupnProdMappingVO[] mCoupnProdMappingVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for (MCoupnProdMappingVO mCoupnProdMappingVO : mCoupnProdMappingVOs) {
            mCoupnProdMappingVO.setRegDt(currentDt);
            mCoupnProdMappingVO.setRegId(sessionInfoVO.getUserId());
            mCoupnProdMappingVO.setModDt(currentDt);
            mCoupnProdMappingVO.setModId(sessionInfoVO.getUserId());

            mCoupnProdMappingVO.setSessionId(sessionInfoVO.getSessionId());
            mCoupnProdMappingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            procCnt += mCoupnProdMappingMapper.getMCoupnProdMappingExcelUploadCheckSave(mCoupnProdMappingVO);
        }

        return procCnt;
    }

    /** 모바일쿠폰상품매핑 엑셀업로드 결과 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMCoupnProdMappingExcelUploadResultList(MCoupnProdMappingVO mCoupnProdMappingVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<Object>> result = new ArrayList<DefaultMap<Object>>();

        mCoupnProdMappingVO.setSessionId(sessionInfoVO.getSessionId());
        mCoupnProdMappingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 조회구분 (1:중복, 2:삭제,추가,유지)
        if( "1".equals(mCoupnProdMappingVO.getSearchGubun()) ) {
            result = mCoupnProdMappingMapper.getMCoupnProdMappingExcelUploadResultList(mCoupnProdMappingVO);
        } else {
            result = mCoupnProdMappingMapper.getMCoupnProdMappingExcelUploadResultList2(mCoupnProdMappingVO);
        }

        return result;
    }

    /** 모바일쿠폰상품매핑 엑셀업로드 결과 팝업 - 저장 */
    @Override
    public int getMCoupnProdMappingExcelUploadResultSave(MCoupnProdMappingVO[] mCoupnProdMappingVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();

        // 삭제, 저장, 유지
        for(MCoupnProdMappingVO mCoupnProdMappingVO : mCoupnProdMappingVOs) {
            mCoupnProdMappingVO.setModDt(currentDt);
            mCoupnProdMappingVO.setModId(sessionInfoVO.getUserId());
            mCoupnProdMappingVO.setRegDt(currentDt);
            mCoupnProdMappingVO.setRegId(sessionInfoVO.getUserId());

            mCoupnProdMappingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            mCoupnProdMappingVO.setUserId(sessionInfoVO.getUserId());

            if(i == 1) {
                // 백업
                procCnt = mCoupnProdMappingMapper.getMCoupnProdMappingExcelUploadBackUpSaveInsert(mCoupnProdMappingVO);
            }

            // 삭제
            if("D".equals(mCoupnProdMappingVO.getResultGubun())) {
                procCnt = mCoupnProdMappingMapper.getMCoupnProdMappingExcelUploadResultSaveDelete(mCoupnProdMappingVO);
            }
            // 추가
            else if("I".equals(mCoupnProdMappingVO.getResultGubun())) {
                procCnt = mCoupnProdMappingMapper.getMCoupnProdMappingExcelUploadResultSaveInsert(mCoupnProdMappingVO);
            }
//            // 유지
//            else if("U".equals(mCoupnProdMappingVO.getResultGubun())) {
//                procCnt = mCoupnProdMappingMapper.getMCoupnProdMappingExcelUploadResultSaveUpdate(mCoupnProdMappingVO);
//            }

            i++;
        }

        return procCnt;
    }

    /** 모바일쿠폰상품매핑 이력조회 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMCoupnProdMappingHistList(MCoupnProdMappingVO mCoupnProdMappingVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<Object>> result = new ArrayList<DefaultMap<Object>>();

        mCoupnProdMappingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mCoupnProdMappingVO.setUserId(sessionInfoVO.getUserId());

        // 조회구분 (A:가로, B:세로)
        if( "A".equals(mCoupnProdMappingVO.getSearchGubun()) ) {
            // 모바일쿠폰사-상품코드 최대수
            int mcoupnProdCnt = mCoupnProdMappingVO.getMCoupnProdCnt();

            // 동적 컬럼 생성을 위한 쿼리 변수;
            String sQuery1 = "";
            String sQuery2 = "";

            for(int i=1; i < mcoupnProdCnt+1; i++) {
                sQuery1 +=", REGEXP_SUBSTR(AA.MCOUPN_PROD_CD_LIST, '[^,]+', 1, "+ i + ") AS MCOUPN_PROD_CD_" + i + "\n";

                sQuery2 +=", thpmpcul.MCOUPN_PROD_CD_"+ i + "\n";
            }
            mCoupnProdMappingVO.setsQuery1(sQuery1);
            mCoupnProdMappingVO.setsQuery2(sQuery2);

            result = mCoupnProdMappingMapper.getMCoupnProdMappingHistList(mCoupnProdMappingVO);
        } else {
            result = mCoupnProdMappingMapper.getMCoupnProdMappingHistList2(mCoupnProdMappingVO);
        }

        return result;
    }

    /** 모바일쿠폰상품매핑 이력조회 팝업 - 모바일쿠폰사-상품코드 최대수 */
    @Override
    public DefaultMap<Object> getMCoupnProdMappingHistCnt(MCoupnProdMappingVO mCoupnProdMappingVO, SessionInfoVO sessionInfoVO) {

        mCoupnProdMappingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mCoupnProdMappingVO.setUserId(sessionInfoVO.getUserId());

        return mCoupnProdMappingMapper.getMCoupnProdMappingHistCnt(mCoupnProdMappingVO);
    }
}