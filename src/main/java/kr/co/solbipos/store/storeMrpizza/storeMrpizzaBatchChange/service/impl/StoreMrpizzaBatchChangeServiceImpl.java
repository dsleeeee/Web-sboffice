package kr.co.solbipos.store.storeMrpizza.storeMrpizzaBatchChange.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.storeMrpizza.storeMrpizzaBatchChange.service.StoreMrpizzaBatchChangeService;
import kr.co.solbipos.store.storeMrpizza.storeMrpizzaBatchChange.service.StoreMrpizzaBatchChangeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : StoreMrpizzaBatchChangeServiceImpl.java
 * @Description : 미스터피자 > 매장관리 > 매장정보일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.18  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.08.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeMrpizzaBatchChangeService")
@Transactional
public class StoreMrpizzaBatchChangeServiceImpl implements StoreMrpizzaBatchChangeService {
    
    private final StoreMrpizzaBatchChangeMapper storeMrpizzaBatchChangeMapper;

    @Autowired
    public StoreMrpizzaBatchChangeServiceImpl(StoreMrpizzaBatchChangeMapper storeMrpizzaBatchChangeMapper) {
        this.storeMrpizzaBatchChangeMapper = storeMrpizzaBatchChangeMapper;
    }

    /** 매장목록 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO, SessionInfoVO sessionInfoVO) {
        storeMrpizzaBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeMrpizzaBatchChangeMapper.getStoreList(storeMrpizzaBatchChangeVO);
    }

    /** 매장정보 저장 */
    @Override
    public int getStoreBatchChangeSave(StoreMrpizzaBatchChangeVO[] storeMrpizzaBatchChangeVOs, SessionInfoVO sessionInfoVO) {
        int storeCnt = 0;
        String currentDt = currentDateTimeString();

        for(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO : storeMrpizzaBatchChangeVOs) {

            storeMrpizzaBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            storeMrpizzaBatchChangeVO.setRegDt(currentDt);
            storeMrpizzaBatchChangeVO.setRegId(sessionInfoVO.getUserId());
            storeMrpizzaBatchChangeVO.setModDt(currentDt);
            storeMrpizzaBatchChangeVO.setModId(sessionInfoVO.getUserId());


            if(storeMrpizzaBatchChangeVO.getStatus() == GridDataFg.UPDATE) {

                if(storeMrpizzaBatchChangeVO.getStoreFg() != null && storeMrpizzaBatchChangeVO.getStoreFg().equals("1")) {

                    // TB_MS_STORE 저장
                    storeCnt += storeMrpizzaBatchChangeMapper.getStoreBatchChangeSave(storeMrpizzaBatchChangeVO);
                }
                if(storeMrpizzaBatchChangeVO.getStoreInfoFg() != null && storeMrpizzaBatchChangeVO.getStoreInfoFg().equals("1")) {
                    // TB_MS_STORE_INFO 저장
                    storeCnt += storeMrpizzaBatchChangeMapper.getStoreInfoBatchChangeSave(storeMrpizzaBatchChangeVO);
                }
            }
        }

        return storeCnt;
    }

    /** 업로드시 임시테이블 삭제 */
    @Override
    public int getStoreExcelUploadCheckDeleteAll(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        storeMrpizzaBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeMrpizzaBatchChangeVO.setSessionId(sessionInfoVO.getSessionId());

        result += storeMrpizzaBatchChangeMapper.getStoreExcelUploadCheckDeleteAll(storeMrpizzaBatchChangeVO);

        return result;
    }

    /** 검증결과 삭제 */
    @Override
    public int getStoreExcelUploadCheckDelete(StoreMrpizzaBatchChangeVO[] storeMrpizzaBatchChangeVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;

        for(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO : storeMrpizzaBatchChangeVOs) {

            storeMrpizzaBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeMrpizzaBatchChangeVO.setSessionId(sessionInfoVO.getSessionId());

            result += storeMrpizzaBatchChangeMapper.getStoreExcelUploadCheckDelete(storeMrpizzaBatchChangeVO);
        }

        return result;
    }

    /** 업로드시 임시테이블 저장 */
    @Override
    public int getStoreExcelUploadCheckSave(StoreMrpizzaBatchChangeVO[] storeMrpizzaBatchChangeVOs, SessionInfoVO sessionInfoVO) {
        int storeCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();

        for(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO : storeMrpizzaBatchChangeVOs) {

            storeMrpizzaBatchChangeVO.setRegDt(currentDt);
            storeMrpizzaBatchChangeVO.setRegId(sessionInfoVO.getUserId());
            storeMrpizzaBatchChangeVO.setModDt(currentDt);
            storeMrpizzaBatchChangeVO.setModId(sessionInfoVO.getUserId());

            storeMrpizzaBatchChangeVO.setSessionId(sessionInfoVO.getSessionId());
            storeMrpizzaBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeMrpizzaBatchChangeVO.setSeq(i);

            String result = "";

            // 매장코드
            if(storeMrpizzaBatchChangeMapper.getStoreCdChk(storeMrpizzaBatchChangeVO) > 0){
                // 팀별
                if(storeMrpizzaBatchChangeVO.getMomsTeam() != null && !"".equals(storeMrpizzaBatchChangeVO.getMomsTeam())){
                    storeMrpizzaBatchChangeVO.setNmcodeGrpCd("151");
                    String hqNmcode = storeMrpizzaBatchChangeMapper.getHqNmcodeChk(storeMrpizzaBatchChangeVO);
                    // 잘못된 정보 입력시
                    if(hqNmcode == null || hqNmcode == "") {
                        result += "팀별, ";
                    }
                }
                // 지역구분
                if(storeMrpizzaBatchChangeVO.getMomsAreaFg() != null && !"".equals(storeMrpizzaBatchChangeVO.getMomsAreaFg())){
                    storeMrpizzaBatchChangeVO.setNmcodeGrpCd("153");
                    String hqNmcode = storeMrpizzaBatchChangeMapper.getHqNmcodeChk(storeMrpizzaBatchChangeVO);
                    // 잘못된 정보 입력시
                    if(hqNmcode == null || hqNmcode == "") {
                        result += "지역구분, ";
                    }
                }
                // 상권
                if(storeMrpizzaBatchChangeVO.getMomsCommercial() != null && !"".equals(storeMrpizzaBatchChangeVO.getMomsCommercial())){
                    storeMrpizzaBatchChangeVO.setNmcodeGrpCd("154");
                    String hqNmcode = storeMrpizzaBatchChangeMapper.getHqNmcodeChk(storeMrpizzaBatchChangeVO);
                    // 잘못된 정보 입력시
                    if(hqNmcode == null || hqNmcode == "") {
                        result += "상권, ";
                    }
                }
                // 점포유형
                if(storeMrpizzaBatchChangeVO.getMomsShopType() != null && !"".equals(storeMrpizzaBatchChangeVO.getMomsShopType())){
                    storeMrpizzaBatchChangeVO.setNmcodeGrpCd("155");
                    String hqNmcode = storeMrpizzaBatchChangeMapper.getHqNmcodeChk(storeMrpizzaBatchChangeVO);
                    // 잘못된 정보 입력시
                    if(hqNmcode == null || hqNmcode == "") {
                        result += "점포유형, ";
                    }
                }
            } else {
                result += "존재하지 않는 매장입니다";
            }

            if(result == null || result == "") {
                result = "검증성공";
            } else {
                result = result + "잘못된 정보가 입력되었습니다.";
            }
            storeMrpizzaBatchChangeVO.setResult(result);

            // 검증결과 저장
            storeCnt += storeMrpizzaBatchChangeMapper.getStoreExcelUploadCheckSave(storeMrpizzaBatchChangeVO);
            i++;
        }

        return storeCnt;
    }

    /** 검증결과 조회 */
    @Override
    public List<DefaultMap<String>> getStoreExcelUploadCheckList(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO, SessionInfoVO sessionInfoVO) {
        storeMrpizzaBatchChangeVO.setSessionId(sessionInfoVO.getSessionId());
        storeMrpizzaBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeMrpizzaBatchChangeMapper.getStoreExcelUploadCheckList(storeMrpizzaBatchChangeVO);
    }

    /** 엑셀 저장 */
    @Override
    public int getSimpleSave(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO, SessionInfoVO sessionInfoVO) {
        int storeCnt = 0;
        String currentDt = currentDateTimeString();

        storeMrpizzaBatchChangeVO.setRegDt(currentDt);
        storeMrpizzaBatchChangeVO.setRegId(sessionInfoVO.getUserId());
        storeMrpizzaBatchChangeVO.setModDt(currentDt);
        storeMrpizzaBatchChangeVO.setModId(sessionInfoVO.getUserId());

        storeMrpizzaBatchChangeVO.setSessionId(sessionInfoVO.getSessionId());
        storeMrpizzaBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());


        if(storeMrpizzaBatchChangeVO.getStoreFg() != null && storeMrpizzaBatchChangeVO.getStoreFg() != "") {

            String[] storeCdList = storeMrpizzaBatchChangeVO.getStoreFg().split(",");
            storeMrpizzaBatchChangeVO.setStoreCdList(storeCdList);

            // TB_MS_STORE 저장
//            storeCnt += storeMrpizzaBatchChangeMapper.getStoreBatchChangeUploadSave(storeMrpizzaBatchChangeVO);
        }
        if(storeMrpizzaBatchChangeVO.getStoreInfoFg() != null && storeMrpizzaBatchChangeVO.getStoreInfoFg() != "") {

            String[] storeInfoCdList = storeMrpizzaBatchChangeVO.getStoreInfoFg().split(",");
            storeMrpizzaBatchChangeVO.setStoreCdList(storeInfoCdList);

            // TB_MS_STORE_INFO 저장
            storeCnt += storeMrpizzaBatchChangeMapper.getSimpleStoreInfoSave(storeMrpizzaBatchChangeVO);
        }

        return storeCnt;
    }

    /** 검증결과 조회 */
    @Override
    public List<DefaultMap<String>> getTmpStoreList(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO, SessionInfoVO sessionInfoVO) {
        storeMrpizzaBatchChangeVO.setSessionId(sessionInfoVO.getSessionId());
        storeMrpizzaBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeMrpizzaBatchChangeMapper.getTmpStoreList(storeMrpizzaBatchChangeVO);
    }

    /** 변경된 값만 임시테이블 저장 */
    @Override
    public int getDiffValSave(StoreMrpizzaBatchChangeVO[] storeMrpizzaBatchChangeVOs, SessionInfoVO sessionInfoVO) {
        int storeCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();

        for(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO : storeMrpizzaBatchChangeVOs) {

            storeMrpizzaBatchChangeVO.setRegDt(currentDt);
            storeMrpizzaBatchChangeVO.setRegId(sessionInfoVO.getUserId());
            storeMrpizzaBatchChangeVO.setModDt(currentDt);
            storeMrpizzaBatchChangeVO.setModId(sessionInfoVO.getUserId());

            storeMrpizzaBatchChangeVO.setSessionId(sessionInfoVO.getSessionId());
            storeMrpizzaBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeMrpizzaBatchChangeVO.setSeq(i);

            // 검증결과 저장
            storeCnt += storeMrpizzaBatchChangeMapper.getStoreExcelUploadCheckSave(storeMrpizzaBatchChangeVO);
            i++;

        }

        return storeCnt;
    }
}
