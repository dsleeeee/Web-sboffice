package kr.co.solbipos.store.storeMoms.dataRcvStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.storeMoms.dataRcvStatus.service.DataRcvStatusService;
import kr.co.solbipos.store.storeMoms.dataRcvStatus.service.DataRcvStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : DataRcvStatusServiceImpl.java
 * @Description : 맘스터치 > 매장관리 > 자료수신현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.03  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.03
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("dataRcvStatusService")
public class DataRcvStatusServiceImpl implements DataRcvStatusService {

    private final DataRcvStatusMapper dataRcvStatusMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public DataRcvStatusServiceImpl(DataRcvStatusMapper dataRcvStatusMapper, PopupMapper popupMapper) {
        this.dataRcvStatusMapper = dataRcvStatusMapper;
        this.popupMapper = popupMapper;
    }

    /** 자료수신현황 헤더 조회 */
    @Override
    public List<DefaultMap<String>> getDataRcvStatusHdrList(DataRcvStatusVO dataRcvStatusVO, SessionInfoVO sessionInfoVO) {

        dataRcvStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장브랜드 '전체' 일때 사용자 브랜드 배열 세팅
        if (dataRcvStatusVO.getStoreHqBrandCd() == null || dataRcvStatusVO.getStoreHqBrandCd().equals("")) {
            if (dataRcvStatusVO.getUserBrands() != null && !dataRcvStatusVO.getUserBrands().equals("")) {
                dataRcvStatusVO.setUserBrandList(dataRcvStatusVO.getUserBrands().split(","));
            }
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dataRcvStatusVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dataRcvStatusVO.getStoreCds(), 3900));
            dataRcvStatusVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dataRcvStatusMapper.getDataRcvStatusHdrList(dataRcvStatusVO);
    }

    /** 자료수신현황 상세 조회 */
    @Override
    public List<DefaultMap<String>> getDataRcvStatusDtlList(DataRcvStatusVO dataRcvStatusVO, SessionInfoVO sessionInfoVO) {

        dataRcvStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return dataRcvStatusMapper.getDataRcvStatusDtlList(dataRcvStatusVO);
    }
}
