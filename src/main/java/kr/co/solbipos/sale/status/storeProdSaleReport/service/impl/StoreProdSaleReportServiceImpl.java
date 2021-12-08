package kr.co.solbipos.sale.status.storeProdSaleReport.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.system.BaseEnv;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.storeProdSaleReport.service.StoreProdSaleReportService;
import kr.co.solbipos.sale.status.storeProdSaleReport.service.StoreProdSaleReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.io.File;
import java.util.List;

import static kr.co.common.utils.DateUtil.*;

/**
 * @Class Name : StoreProdSaleReportServiceImpl.java
 * @Description : 매출관리 > 매출현황2 > 기간별 매장-상품 매출 다운로드(고봉민)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.12.01  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.12.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeProdSaleReportService")
@Transactional
public class StoreProdSaleReportServiceImpl implements StoreProdSaleReportService {
    private final StoreProdSaleReportMapper storeProdSaleReportMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreProdSaleReportServiceImpl(StoreProdSaleReportMapper storeProdSaleReportMapper) {
        this.storeProdSaleReportMapper = storeProdSaleReportMapper;
    }

    /** 기간별 매장-상품 매출 다운로드 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreProdSaleReportList(StoreProdSaleReportVO storeProdSaleReportVO, SessionInfoVO sessionInfoVO) {

        storeProdSaleReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeProdSaleReportMapper.getStoreProdSaleReportList(storeProdSaleReportVO);
    }

    /** 기간별 매장-상품 매출 다운로드 탭 - 자료생성 저장 */
    @Override
    public int getStoreProdSaleReportSave(StoreProdSaleReportVO storeProdSaleReportVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();
        String currentDate = currentDateString();
        String currentTime = currentTimeString();

        storeProdSaleReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeProdSaleReportVO.setReqDate(currentDate);
        storeProdSaleReportVO.setReqTime(currentTime);

        storeProdSaleReportVO.setRegDt(currentDt);
        storeProdSaleReportVO.setRegId(sessionInfoVO.getUserId());
        storeProdSaleReportVO.setModDt(currentDt);
        storeProdSaleReportVO.setModId(sessionInfoVO.getUserId());

        procCnt = storeProdSaleReportMapper.getStoreProdSaleReportSaveInsert(storeProdSaleReportVO);

        return procCnt;
    }

    /** 기간별 매장-상품 매출 다운로드 탭 - 삭제 */
    @Override
    public int getStoreProdSaleReportDel(StoreProdSaleReportVO[] storeProdSaleReportVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for(StoreProdSaleReportVO storeProdSaleReportVO : storeProdSaleReportVOs) {

            storeProdSaleReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            procCnt = storeProdSaleReportMapper.getStoreProdSaleReportDel(storeProdSaleReportVO);

            String pathFull = BaseEnv.FILE_UPLOAD_DIR + "/MediaBase/SaleReport/" + storeProdSaleReportVO.getFileName();

            // 파일 삭제
            File delFile = new File(pathFull);
            if(delFile.exists()) {
                delFile.delete();
            }
        }

        return procCnt;
    }

    /** 기간별 매장-상품 매출 다운로드 탭 - 자료생성 요청건 존재여부 확인 */
    @Override
    public DefaultMap<String> getStoreProdSaleReportChk(StoreProdSaleReportVO storeProdSaleReportVO, SessionInfoVO sessionInfoVO) {

        storeProdSaleReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeProdSaleReportMapper.getStoreProdSaleReportChk(storeProdSaleReportVO);
    }

    /** 지사-지역관리 탭 - 지사 조회 */
    @Override
    public List<DefaultMap<Object>> getBranchAreaList(StoreProdSaleReportVO storeProdSaleReportVO, SessionInfoVO sessionInfoVO) {

        storeProdSaleReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeProdSaleReportMapper.getBranchAreaList(storeProdSaleReportVO);
    }

    /** 지사-지역관리 탭 - 지사 저장 */
    @Override
    public int getBranchAreaSave(StoreProdSaleReportVO[] storeProdSaleReportVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(StoreProdSaleReportVO storeProdSaleReportVO : storeProdSaleReportVOs) {

            storeProdSaleReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeProdSaleReportVO.setModDt(currentDt);
            storeProdSaleReportVO.setModId(sessionInfoVO.getUserId());

            if (storeProdSaleReportVO.getStatus() == GridDataFg.INSERT) {
                storeProdSaleReportVO.setRegDt(currentDt);
                storeProdSaleReportVO.setRegId(sessionInfoVO.getUserId());

                // 지사코드(자동 채번)
                String branchCd = storeProdSaleReportMapper.getBranchAreaBranchCd(storeProdSaleReportVO);
                storeProdSaleReportVO.setBranchCd(branchCd);

                procCnt = storeProdSaleReportMapper.getBranchAreaSaveInsert(storeProdSaleReportVO);

            } else if(storeProdSaleReportVO.getStatus() == GridDataFg.UPDATE) {
                procCnt = storeProdSaleReportMapper.getBranchAreaSaveUpdate(storeProdSaleReportVO);

            } else if (storeProdSaleReportVO.getStatus() == GridDataFg.DELETE) {
                procCnt = storeProdSaleReportMapper.getBranchAreaSaveDelete(storeProdSaleReportVO);

                // 지사 delete 시, 지역 delete
                procCnt = storeProdSaleReportMapper.getBranchAreaDetailSaveDeleteAll(storeProdSaleReportVO);
            }
        }

        return procCnt;
    }

    /** 지사-지역관리 탭 - 지역 조회 */
    @Override
    public List<DefaultMap<Object>> getBranchAreaDetailList(StoreProdSaleReportVO storeProdSaleReportVO, SessionInfoVO sessionInfoVO) {

        storeProdSaleReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeProdSaleReportMapper.getBranchAreaDetailList(storeProdSaleReportVO);
    }

    /** 지사-지역관리 탭 - 지역 저장 */
    @Override
    public int getBranchAreaDetailSave(StoreProdSaleReportVO[] storeProdSaleReportVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(StoreProdSaleReportVO storeProdSaleReportVO : storeProdSaleReportVOs) {

            storeProdSaleReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeProdSaleReportVO.setModDt(currentDt);
            storeProdSaleReportVO.setModId(sessionInfoVO.getUserId());

            if (storeProdSaleReportVO.getStatus() == GridDataFg.INSERT) {
                storeProdSaleReportVO.setRegDt(currentDt);
                storeProdSaleReportVO.setRegId(sessionInfoVO.getUserId());

                // 지역코드(자동 채번)
                String area = storeProdSaleReportMapper.getBranchAreaAreaCd(storeProdSaleReportVO);
                storeProdSaleReportVO.setAreaCd(area);

                procCnt = storeProdSaleReportMapper.getBranchAreaDetailSaveInsert(storeProdSaleReportVO);

            } else if(storeProdSaleReportVO.getStatus() == GridDataFg.UPDATE) {
                procCnt = storeProdSaleReportMapper.getBranchAreaDetailSaveUpdate(storeProdSaleReportVO);

            } else if (storeProdSaleReportVO.getStatus() == GridDataFg.DELETE) {
                procCnt = storeProdSaleReportMapper.getBranchAreaDetailSaveDelete(storeProdSaleReportVO);
            }
        }

        return procCnt;
    }

    /** 지역-매장관리 탭 - 지역 조회 */
    @Override
    public List<DefaultMap<Object>> getAreaStoreMappingList(StoreProdSaleReportVO storeProdSaleReportVO, SessionInfoVO sessionInfoVO) {

        storeProdSaleReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeProdSaleReportMapper.getAreaStoreMappingList(storeProdSaleReportVO);
    }

    /** 지역-매장관리 탭 - 지역-매장 조회 */
    @Override
    public List<DefaultMap<Object>> getAreaStoreMappingDetailList(StoreProdSaleReportVO storeProdSaleReportVO, SessionInfoVO sessionInfoVO) {

        storeProdSaleReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeProdSaleReportMapper.getAreaStoreMappingDetailList(storeProdSaleReportVO);
    }

    /** 지역-매장관리 탭 - 매장 조회 */
    @Override
    public List<DefaultMap<Object>> getAreaStoreMappingStoreList(StoreProdSaleReportVO storeProdSaleReportVO, SessionInfoVO sessionInfoVO) {

        storeProdSaleReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeProdSaleReportMapper.getAreaStoreMappingStoreList(storeProdSaleReportVO);
    }

    /** 지사-지역관리 탭 - 지역-매장 저장 delete */
    @Override
    public int getAreaStoreMappingDetailSave(StoreProdSaleReportVO[] storeProdSaleReportVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(StoreProdSaleReportVO storeProdSaleReportVO : storeProdSaleReportVOs) {

            storeProdSaleReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeProdSaleReportVO.setModDt(currentDt);
            storeProdSaleReportVO.setModId(sessionInfoVO.getUserId());

           if (storeProdSaleReportVO.getStatus() == GridDataFg.DELETE) {
                procCnt = storeProdSaleReportMapper.getAreaStoreMappingDetailSaveDelete(storeProdSaleReportVO);
            }
        }

        return procCnt;
    }

    /** 지사-지역관리 탭 - 지역-매장 저장 */
    @Override
    public int getAreaStoreMappingStoreSave(StoreProdSaleReportVO[] storeProdSaleReportVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(StoreProdSaleReportVO storeProdSaleReportVO : storeProdSaleReportVOs) {

            storeProdSaleReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeProdSaleReportVO.setModDt(currentDt);
            storeProdSaleReportVO.setModId(sessionInfoVO.getUserId());
            storeProdSaleReportVO.setRegDt(currentDt);
            storeProdSaleReportVO.setRegId(sessionInfoVO.getUserId());

            procCnt = storeProdSaleReportMapper.getAreaStoreMappingStoreSaveInsert(storeProdSaleReportVO);
        }

        return procCnt;
    }
}