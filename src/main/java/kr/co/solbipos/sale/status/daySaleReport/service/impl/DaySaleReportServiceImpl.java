package kr.co.solbipos.sale.status.daySaleReport.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.system.BaseEnv;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.daySaleReport.service.DaySaleReportService;
import kr.co.solbipos.sale.status.daySaleReport.service.DaySaleReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.io.File;
import java.util.List;

import static kr.co.common.utils.DateUtil.*;

/**
 * @Class Name : DaySaleReportServiceImpl.java
 * @Description : 매출관리 > 매출현황2 > 일별매출내역 다운로드(제너시스올떡 분식대장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.12.08  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.12.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("daySaleReportService")
@Transactional
public class DaySaleReportServiceImpl implements DaySaleReportService {
    private final DaySaleReportMapper daySaleReportMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public DaySaleReportServiceImpl(DaySaleReportMapper daySaleReportMapper) {
        this.daySaleReportMapper = daySaleReportMapper;
    }

    /** 일별매출내역 다운로드 - 조회 */
    @Override
    public List<DefaultMap<Object>> getDaySaleReportList(DaySaleReportVO daySaleReportVO, SessionInfoVO sessionInfoVO) {

        daySaleReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return daySaleReportMapper.getDaySaleReportList(daySaleReportVO);
    }

    /** 일별매출내역 다운로드 - 자료생성 저장 */
    @Override
    public int getDaySaleReportSave(DaySaleReportVO[] daySaleReportVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();
        String currentDate = currentDateString();
        String currentTime = currentTimeString();

        for(DaySaleReportVO daySaleReportVO : daySaleReportVOs) {
            daySaleReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            daySaleReportVO.setReqDate(currentDate);
            daySaleReportVO.setReqTime(currentTime);

            daySaleReportVO.setRegDt(currentDt);
            daySaleReportVO.setRegId(sessionInfoVO.getUserId());
            daySaleReportVO.setModDt(currentDt);
            daySaleReportVO.setModId(sessionInfoVO.getUserId());

            procCnt = daySaleReportMapper.getDaySaleReportSaveInsert(daySaleReportVO);
        }

        return procCnt;
    }

    /** 일별매출내역 다운로드 - 삭제 */
    @Override
    public int getDaySaleReportDel(DaySaleReportVO[] daySaleReportVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for(DaySaleReportVO daySaleReportVO : daySaleReportVOs) {

            daySaleReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            procCnt = daySaleReportMapper.getDaySaleReportDel(daySaleReportVO);

            String pathFull = BaseEnv.FILE_UPLOAD_DIR + "/MediaBase/SaleReport/" + daySaleReportVO.getFileName();

            // 파일 삭제
            File delFile = new File(pathFull);
            if(delFile.exists()) {
                delFile.delete();
            }
        }

        return procCnt;
    }

    /** 일별매출내역 다운로드 - 자료생성 요청건 존재여부 확인 */
    @Override
    public DefaultMap<String> getDaySaleReportChk(DaySaleReportVO daySaleReportVO, SessionInfoVO sessionInfoVO) {

        daySaleReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        String[] storeCds = daySaleReportVO.getStoreCds().split(",");
        daySaleReportVO.setStoreCdList(storeCds);

        return daySaleReportMapper.getDaySaleReportChk(daySaleReportVO);
    }

    /** 일별매출내역 조회 - 조회 */
    @Override
    public List<DefaultMap<Object>> getDaySaleReportListList(DaySaleReportVO daySaleReportVO, SessionInfoVO sessionInfoVO) {

        daySaleReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            daySaleReportVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // '20220401' AS A01, '20220402' AS A02...
        int dataCreateMonth = Integer.parseInt(daySaleReportVO.getDataCreateMonth());
        int dataCreateMonthLastDate = Integer.parseInt(daySaleReportVO.getDataCreateMonthLastDate());
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotDateCol = "";
        for(int i=1; i <= dataCreateMonthLastDate; i++) {
            if (i < 10) {
                pivotDateCol += (pivotDateCol.equals("") ? "" : ",") + "'"+dataCreateMonth+"0"+i+"'"+" AS A0"+i;
            } else {
                pivotDateCol += (pivotDateCol.equals("") ? "" : ",") + "'"+dataCreateMonth+i+"'"+" AS A"+i;
            }
        }
        daySaleReportVO.setPivotDateCol(pivotDateCol);

        // 매출 발생 일별 기준, 동적 컬럼 생성을 위한 쿼리 변수
        String sQuery1 = "";
        String sQuery2 = "";
        String sQuery3 = ", (0";
        for(int i=1; i <= dataCreateMonthLastDate; i++) {
            if (i < 10) {
                sQuery1 += ", NVL(A0" + i + "_TOT_SALE_QTY, 0) AS A0" + i + "_TOT_SALE_QTY" + "\n";
                sQuery1 += ", NVL(A0" + i + "_TOT_SALE_AMT, 0) AS A0" + i + "_TOT_SALE_AMT" + "\n";
                sQuery1 += ", NVL(A0" + i + "_TOT_DC_AMT, 0) AS A0" + i + "_TOT_DC_AMT" + "\n";
                sQuery1 += ", NVL(A0" + i + "_TOT_REAL_SALE_AMT, 0) AS A0" + i + "_TOT_REAL_SALE_AMT" + "\n";

                sQuery2 += ", null AS A0" + i + "_TOT_SALE_QTY" + "\n";
                sQuery2 += ", null AS A0" + i + "_TOT_SALE_AMT" + "\n";
                sQuery2 += ", null AS A0" + i + "_TOT_DC_AMT" + "\n";
                sQuery2 += ", null AS A0" + i + "_TOT_REAL_SALE_AMT" + "\n";

                sQuery3 += "+ NVL(A0" + i + "_TOT_SALE_QTY, 0)";
                sQuery3 += "+ NVL(A0" + i + "_TOT_SALE_AMT, 0)";
                sQuery3 += "+ NVL(A0" + i + "_TOT_DC_AMT, 0)";
                sQuery3 += "+ NVL(A0" + i + "_TOT_REAL_SALE_AMT, 0)";
            } else {
                sQuery1 += ", NVL(A" + i + "_TOT_SALE_QTY, 0) AS A" + i + "_TOT_SALE_QTY" + "\n";
                sQuery1 += ", NVL(A" + i + "_TOT_SALE_AMT, 0) AS A" + i + "_TOT_SALE_AMT" + "\n";
                sQuery1 += ", NVL(A" + i + "_TOT_DC_AMT, 0) AS A" + i + "_TOT_DC_AMT" + "\n";
                sQuery1 += ", NVL(A" + i + "_TOT_REAL_SALE_AMT, 0) AS A" + i + "_TOT_REAL_SALE_AMT" + "\n";

                sQuery2 += ", null AS A" + i + "_TOT_SALE_QTY" + "\n";
                sQuery2 += ", null AS A" + i + "_TOT_SALE_AMT" + "\n";
                sQuery2 += ", null AS A" + i + "_TOT_DC_AMT" + "\n";
                sQuery2 += ", null AS A" + i + "_TOT_REAL_SALE_AMT" + "\n";

                sQuery3 += "+ NVL(A" + i + "_TOT_SALE_QTY, 0)";
                sQuery3 += "+ NVL(A" + i + "_TOT_SALE_AMT, 0)";
                sQuery3 += "+ NVL(A" + i + "_TOT_DC_AMT, 0)";
                sQuery3 += "+ NVL(A" + i + "_TOT_REAL_SALE_AMT, 0)";

                if(i == dataCreateMonthLastDate) {
                    sQuery3 += ") AS ALL_TOT_AMT";
                }
            }
        }
        daySaleReportVO.setsQuery1(sQuery1);
        daySaleReportVO.setsQuery2(sQuery2);
        daySaleReportVO.setsQuery3(sQuery3);

        return daySaleReportMapper.getDaySaleReportListList(daySaleReportVO);
    }
}