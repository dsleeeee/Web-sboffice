package kr.co.solbipos.store.storeMoms.storeBatchChange.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prodBatchChange.service.ProdBatchChangeVO;
import kr.co.solbipos.base.prod.prodExcelUpload.service.ProdExcelUploadVO;
import kr.co.solbipos.store.storeMoms.storeBatchChange.service.StoreBatchChangeService;
import kr.co.solbipos.store.storeMoms.storeBatchChange.service.StoreBatchChangeVO;
import kr.co.solbipos.store.storeMoms.storeBatchChange.service.impl.StoreBatchChangeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : StoreBatchChangeServiceImpl.java
 * @Description : 맘스터치 > 매장관리 > 매장정보일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.01.17  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.01.17
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service
public class StoreBatchChangeServiceImpl implements StoreBatchChangeService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final StoreBatchChangeMapper storeBatchChangeMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public StoreBatchChangeServiceImpl(StoreBatchChangeMapper storeBatchChangeMapper, MessageService messageService) {
        this.storeBatchChangeMapper = storeBatchChangeMapper;
        this.messageService = messageService;
    }

    /** 매장목록 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(StoreBatchChangeVO storeBatchChangeVO, SessionInfoVO sessionInfoVO) {

        storeBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장브랜드 '전체' 일때
        if (storeBatchChangeVO.getStoreHqBrandCd() == "" || storeBatchChangeVO.getStoreHqBrandCd() == null) {
            // 사용자별 브랜드 array 값 세팅
            String[] userBrandList = storeBatchChangeVO.getUserBrands().split(",");
            storeBatchChangeVO.setUserBrandList(userBrandList);
        }

        return storeBatchChangeMapper.getStoreList(storeBatchChangeVO);
    }

    /** 매장정보 저장 */
    @Override
    public int getStoreBatchChangeSave(StoreBatchChangeVO[] storeBatchChangeVOs, SessionInfoVO sessionInfoVO) {

        int storeCnt = 0;
        String currentDt = currentDateTimeString();

        for(StoreBatchChangeVO storeBatchChangeVO : storeBatchChangeVOs) {

            storeBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            storeBatchChangeVO.setRegDt(currentDt);
            storeBatchChangeVO.setRegId(sessionInfoVO.getUserId());
            storeBatchChangeVO.setModDt(currentDt);
            storeBatchChangeVO.setModId(sessionInfoVO.getUserId());


            if(storeBatchChangeVO.getStatus() == GridDataFg.UPDATE) {
                // TB_MS_STORE 저장
                if(storeBatchChangeVO.getBranchCd() != null && !storeBatchChangeVO.getBranchCd().equals("")) {
                    storeCnt += storeBatchChangeMapper.getStoreBatchChangeSave(storeBatchChangeVO);
                }
                // TB_MS_STORE_INFO 저장
                if((storeBatchChangeVO.getMomsTeam() != null && !storeBatchChangeVO.getMomsTeam().equals(""))
                        || (storeBatchChangeVO.getMomsAcShop() != null && !storeBatchChangeVO.getMomsAcShop().equals(""))
                        || (storeBatchChangeVO.getMomsAreaFg() != null && !storeBatchChangeVO.getMomsAreaFg().equals(""))
                        || (storeBatchChangeVO.getMomsCommercial() != null && !storeBatchChangeVO.getMomsCommercial().equals(""))
                        || (storeBatchChangeVO.getMomsShopType() != null && !storeBatchChangeVO.getMomsShopType().equals(""))
                        || (storeBatchChangeVO.getMomsStoreManageType() != null && !storeBatchChangeVO.getMomsStoreManageType().equals(""))) {
                    storeCnt += storeBatchChangeMapper.getStoreInfoBatchChangeSave(storeBatchChangeVO);
                }
            }
        }

        return storeCnt;
    }

    /** 임시테이블 삭제 */
    @Override
    public int getStoreExcelUploadCheckDeleteAll(StoreBatchChangeVO storeBatchChangeVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        storeBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeBatchChangeVO.setSessionId(sessionInfoVO.getUserId());

        result += storeBatchChangeMapper.getStoreExcelUploadCheckDeleteAll(storeBatchChangeVO);

        return result;
    }

    /** 검증결과 삭제 */
    @Override
    public int getStoreExcelUploadCheckDelete(StoreBatchChangeVO[] storeBatchChangeVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;

        for(StoreBatchChangeVO storeBatchChangeVO : storeBatchChangeVOs) {
            storeBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeBatchChangeVO.setSessionId(sessionInfoVO.getUserId());

            result += storeBatchChangeMapper.getStoreExcelUploadCheckDelete(storeBatchChangeVO);
        }

        return result;
    }

    /** 임시테이블 검증 후 저장 */
    @Override
    public int getStoreExcelUploadCheckSave(StoreBatchChangeVO[] storeBatchChangeVOs, SessionInfoVO sessionInfoVO) {
        int storeCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();

        for(StoreBatchChangeVO storeBatchChangeVO : storeBatchChangeVOs) {

            storeBatchChangeVO.setRegDt(currentDt);
            storeBatchChangeVO.setRegId(sessionInfoVO.getUserId());
            storeBatchChangeVO.setModDt(currentDt);
            storeBatchChangeVO.setModId(sessionInfoVO.getUserId());

            storeBatchChangeVO.setSessionId(sessionInfoVO.getUserId());
            storeBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeBatchChangeVO.setSeq(i);

            String result = "";

            // 매장코드
            if(storeBatchChangeMapper.getStoreCdChk(storeBatchChangeVO) > 0){
                // 지사코드
                if(storeBatchChangeVO.getBranchCd() != null && !"".equals(storeBatchChangeVO.getBranchCd())){
                    String branchCd = storeBatchChangeMapper.getBranchCdChk(storeBatchChangeVO);
                    // 잘못된 정보 입력시
                    if(branchCd == null || branchCd == "") {
                        result += "지사, ";
                    }
                }
                // 팀별
                if(storeBatchChangeVO.getMomsTeam() != null && !"".equals(storeBatchChangeVO.getMomsTeam())){
                    storeBatchChangeVO.setNmcodeGrpCd("151");
                    String hqNmcode = storeBatchChangeMapper.getHqNmcodeChk(storeBatchChangeVO);
                    // 잘못된 정보 입력시
                    if(hqNmcode == null || hqNmcode == "") {
                        result += "팀별, ";
                    }
                }
                // AC점포별
                if(storeBatchChangeVO.getMomsAcShop() != null && !"".equals(storeBatchChangeVO.getMomsAcShop())){
                    storeBatchChangeVO.setNmcodeGrpCd("152");
                    String hqNmcode = storeBatchChangeMapper.getHqNmcodeChk(storeBatchChangeVO);
                    // 잘못된 정보 입력시
                    if(hqNmcode == null || hqNmcode == "") {
                        result += "AC점포별, ";
                    }
                }
                // 지역구분
                if(storeBatchChangeVO.getMomsAreaFg() != null && !"".equals(storeBatchChangeVO.getMomsAreaFg())){
                    storeBatchChangeVO.setNmcodeGrpCd("153");
                    String hqNmcode = storeBatchChangeMapper.getHqNmcodeChk(storeBatchChangeVO);
                    // 잘못된 정보 입력시
                    if(hqNmcode == null || hqNmcode == "") {
                        result += "지역구분, ";
                    }
                }
                // 상권
                if(storeBatchChangeVO.getMomsCommercial() != null && !"".equals(storeBatchChangeVO.getMomsCommercial())){
                    storeBatchChangeVO.setNmcodeGrpCd("154");
                    String hqNmcode = storeBatchChangeMapper.getHqNmcodeChk(storeBatchChangeVO);
                    // 잘못된 정보 입력시
                    if(hqNmcode == null || hqNmcode == "") {
                        result += "상권, ";
                    }
                }
                // 점포유형
                if(storeBatchChangeVO.getMomsShopType() != null && !"".equals(storeBatchChangeVO.getMomsShopType())){
                    storeBatchChangeVO.setNmcodeGrpCd("155");
                    String hqNmcode = storeBatchChangeMapper.getHqNmcodeChk(storeBatchChangeVO);
                    // 잘못된 정보 입력시
                    if(hqNmcode == null || hqNmcode == "") {
                        result += "점포유형, ";
                    }
                }
                // 매장관리타입
                if(storeBatchChangeVO.getMomsStoreManageType() != null && !"".equals(storeBatchChangeVO.getMomsStoreManageType())){
                    storeBatchChangeVO.setNmcodeGrpCd("156");
                    String hqNmcode = storeBatchChangeMapper.getHqNmcodeChk(storeBatchChangeVO);
                    // 잘못된 정보 입력시
                    if(hqNmcode == null || hqNmcode == "") {
                        result += "매장관리타입, ";
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
            storeBatchChangeVO.setResult(result);

            // 검증결과 저장
            storeCnt += storeBatchChangeMapper.getStoreExcelUploadCheckSave(storeBatchChangeVO);
            i++;
        }

        return storeCnt;
    }

    /** 검증결과 조회 */
    @Override
    public List<DefaultMap<String>> getStoreExcelUploadCheckList(StoreBatchChangeVO storeBatchChangeVO, SessionInfoVO sessionInfoVO) {

        storeBatchChangeVO.setSessionId(sessionInfoVO.getUserId());
        storeBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return storeBatchChangeMapper.getStoreExcelUploadCheckList(storeBatchChangeVO);
    }

    /** 매장정보 저장 */
    @Override
    public int getSimpleSave(StoreBatchChangeVO[] storeBatchChangeVOs, SessionInfoVO sessionInfoVO) {
        int storeCnt = 0;
        String currentDt = currentDateTimeString();

        for(StoreBatchChangeVO storeBatchChangeVO : storeBatchChangeVOs) {
            storeBatchChangeVO.setRegDt(currentDt);
            storeBatchChangeVO.setRegId(sessionInfoVO.getUserId());
            storeBatchChangeVO.setModDt(currentDt);
            storeBatchChangeVO.setModId(sessionInfoVO.getUserId());

            storeBatchChangeVO.setSessionId(sessionInfoVO.getUserId());
            storeBatchChangeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            // TB_MS_STORE 저장
            // branchCd
            storeCnt += storeBatchChangeMapper.getStoreBatchChangeUploadSave(storeBatchChangeVO);

            // TB_MS_STORE_INFO 저장
            // momsTeam, momsAcShop, momsAreaFg, momsCommercial, momsShopType, momsStoreManageType
            storeCnt += storeBatchChangeMapper.getSimpleStoreInfoSave(storeBatchChangeVO);

            // 전체 삭제
            storeBatchChangeMapper.getStoreExcelUploadCheckDeleteAll(storeBatchChangeVO);
        }

        return storeCnt;
    }

}
