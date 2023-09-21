package kr.co.solbipos.common.popup.selectStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.CmmUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.common.popup.selectStore.service.SelectStoreService;
import kr.co.solbipos.common.popup.selectStore.service.SelectStoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SelectStoreServiceImpl.java
 * @Description : (공통) 매장 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.09.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("selectStoreServiceImpl")
@Transactional
public class SelectStoreServiceImpl implements SelectStoreService {
    private final SelectStoreMapper selectStoreMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SelectStoreServiceImpl(SelectStoreMapper selectStoreMapper) { this.selectStoreMapper = selectStoreMapper; }

    /** 매장 공통 - 매장 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSelectStoreList(SelectStoreVO selectStoreVO, SessionInfoVO sessionInfoVO) {

        selectStoreVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        selectStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        selectStoreVO.setEmpNo(sessionInfoVO.getEmpNo());
        selectStoreVO.setUserId(sessionInfoVO.getUserId());
        // 매장브랜드가 '전체' 일때
        if (selectStoreVO.getStoreHqBrandCd() == "" || selectStoreVO.getStoreHqBrandCd() == null) {
            // 사용자별 브랜드 array 값 세팅
            String[] userBrandList = selectStoreVO.getUserBrands().split(",");
            selectStoreVO.setUserBrandList(userBrandList);
        }

        List<DefaultMap<String>> resultList = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
            resultList = selectStoreMapper.getSelectStoreList(selectStoreVO);
        }

        return resultList;
    }

    /** 매장 공통 - 회사 구분 조회 */
    @Override
    public DefaultMap<Object> getSelectStoreCompanyFg(SelectStoreVO selectStoreVO, SessionInfoVO sessionInfoVO) {

        selectStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return selectStoreMapper.getSelectStoreCompanyFg(selectStoreVO);
    }

    /** 사용자별 브랜드 콤보박스 조회 */
    @Override
    public List<DefaultMap<String>> getSelectBrandMomsList(SelectStoreVO selectStoreVO, SessionInfoVO sessionInfoVO) {

        selectStoreVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        selectStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            selectStoreVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        selectStoreVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 사용자별 브랜드 사용 조회
            String userBrands = selectStoreMapper.getUserBrandCdList(selectStoreVO);
            selectStoreVO.setUserBrands(userBrands);

            if (selectStoreVO.getUserBrands() != null && !"".equals(selectStoreVO.getUserBrands())) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = selectStoreVO.getUserBrands().split(",");
                selectStoreVO.setUserBrandList(userBrandList);
            }
        }

        return selectStoreMapper.getSelectBrandMomsList(selectStoreVO);
    }

    /** 사용자별 코드별 공통코드 콤보박스 조회 */
    @Override
    public List<DefaultMap<String>> getSelectHqNmcodeMomsList(SelectStoreVO selectStoreVO, SessionInfoVO sessionInfoVO) {

        selectStoreVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        selectStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            selectStoreVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        selectStoreVO.setUserId(sessionInfoVO.getUserId());

        List<DefaultMap<String>> resultList = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 사용자별 코드별 공통코드 조회
            String userHqNmcodeCd = selectStoreMapper.getUserHqNmcodeCdList(selectStoreVO);
            selectStoreVO.setUserHqNmcodeCd(userHqNmcodeCd);

            resultList = selectStoreMapper.getSelectHqNmcodeMomsList(selectStoreVO);
        }

        return resultList;
    }

    /** 사용자별 그룹 콤보박스 조회 */
    @Override
    public List<DefaultMap<String>> getSelectBranchMomsList(SelectStoreVO selectStoreVO, SessionInfoVO sessionInfoVO) {

        selectStoreVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        selectStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            selectStoreVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        selectStoreVO.setUserId(sessionInfoVO.getUserId());

        List<DefaultMap<String>> resultList = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
            // 사사용자별 그룹 조회
            String userHqNmcodeCd = selectStoreMapper.getUserBranchCdList(selectStoreVO);
            selectStoreVO.setUserHqNmcodeCd(userHqNmcodeCd);

            resultList = selectStoreMapper.getSelectBranchMomsList(selectStoreVO);
        }

        return resultList;
    }

    /** 업로드매장 공통 - 업로드매장 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSelectUploadStoreList(SelectStoreVO selectStoreVO, SessionInfoVO sessionInfoVO) {

        selectStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        selectStoreVO.setUserId(sessionInfoVO.getUserId());

        List<DefaultMap<String>> resultList = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ){

            resultList = selectStoreMapper.getSelectUploadStoreList(selectStoreVO);
        }

        return resultList;
    }

    /** 업로드매장 공통 - 검증결과 저장 */
    @Override
    public int getSelectUploadStoreExcelUploadSave(SelectStoreVO[] selectStoreVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(SelectStoreVO selectStoreVO : selectStoreVOs) {
            selectStoreVO.setRegDt(currentDt);
            selectStoreVO.setRegId(sessionInfoVO.getUserId());
            selectStoreVO.setModDt(currentDt);
            selectStoreVO.setModId(sessionInfoVO.getUserId());

            selectStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            selectStoreVO.setUserId(sessionInfoVO.getUserId());

            procCnt += selectStoreMapper.getSelectUploadStoreExcelUploadSave(selectStoreVO);
        }

        return procCnt;
    }

    /** 업로드매장 공통 - 검증결과 전체 삭제 */
    @Override
    public int getSelectUploadStoreExcelUploadDeleteAll(SelectStoreVO selectStoreVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        selectStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        selectStoreVO.setUserId(sessionInfoVO.getUserId());

        procCnt += selectStoreMapper.getSelectUploadStoreExcelUploadDeleteAll(selectStoreVO);

        return procCnt;
    }

    /** 업로드매장 공통 - 업로드매장 텍스트박스 조회 */
    @Override
    public DefaultMap<Object> getSelectUploadStoreText(SelectStoreVO selectStoreVO, SessionInfoVO sessionInfoVO) {

        selectStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        selectStoreVO.setUserId(sessionInfoVO.getUserId());

        return selectStoreMapper.getSelectUploadStoreText(selectStoreVO);
    }
}