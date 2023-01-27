package kr.co.solbipos.sale.prod.saleDtlChannel.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.system.BaseEnv;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.saleDtlChannel.service.SaleDtlChannelService;
import kr.co.solbipos.sale.prod.saleDtlChannel.service.SaleDtlChannelVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

import static kr.co.common.utils.DateUtil.*;

/**
 * @Class Name : SaleDtlChannelServiceImpl.java
 * @Description : 맘스터치 > 상품매출분석 > 매출상세현황(채널별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.28   권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.12.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("saleDtlChannelService")
@Transactional
public class SaleDtlChannelServiceImpl implements SaleDtlChannelService {

    private final SaleDtlChannelMapper saleDtlChannelMapper;

    public SaleDtlChannelServiceImpl(SaleDtlChannelMapper saleDtlChannelMapper) {
        this.saleDtlChannelMapper = saleDtlChannelMapper;
    }

    /** 매출상세현황(채널별) 조회 */
    @Override
    public List<DefaultMap<String>> getSaleDtlChannelList(SaleDtlChannelVO saleDtlChannelVO, SessionInfoVO sessionInfoVO){
        saleDtlChannelVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        saleDtlChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            saleDtlChannelVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = saleDtlChannelVO.getStoreCds().split(",");
        saleDtlChannelVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (saleDtlChannelVO.getProdCds() != null && !"".equals(saleDtlChannelVO.getProdCds())) {
            String[] prodCdList = saleDtlChannelVO.getProdCds().split(",");
            saleDtlChannelVO.setProdCdList(prodCdList);
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (saleDtlChannelVO.getStoreHqBrandCd() == "" || saleDtlChannelVO.getStoreHqBrandCd() == null || saleDtlChannelVO.getProdHqBrandCd() == "" || saleDtlChannelVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = saleDtlChannelVO.getUserBrands().split(",");
                saleDtlChannelVO.setUserBrandList(userBrandList);
            }
        }

        // 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";

        String[] arrDlvrInFgCol = saleDtlChannelVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                saleDtlChannelVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        String[] orderFg = {"STIN","DLVR","PACK"}; // 내점, 배달, 포장
        String[] list = saleDtlChannelVO.getArrDlvrInFgCol(); // 주문채널


        // 내점, 배달, 포장의 수량과 실매출액
        for(int j = 1; j <= orderFg.length; j++) {
            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY1" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY2" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY3" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT1" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT2" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT3" + "\n";

            sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN SALE_QTY END) AS " + orderFg[j-1] + "_SALE_QTY1" + "\n";
            sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN SALE_QTY END) AS " + orderFg[j-1] + "_SALE_QTY2" + "\n";
            sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' THEN SALE_QTY END) AS " + orderFg[j-1] + "_SALE_QTY3" + "\n";
            sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_REAL_SALE_AMT1" + "\n";
            sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_REAL_SALE_AMT2" + "\n";
            sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_REAL_SALE_AMT3" + "\n";
        }

        // 내점, 배달, 포장의 주문채널별 수량과 실매출액
        for(int j = 1; j <= orderFg.length; j++) {
            for (int i = 0; i < list.length; i++) {
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY1" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY2" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY3" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT1" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT2" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT3" + "\n";

                sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN SALE_QTY END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY1" + "\n";
                sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN SALE_QTY END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY2" + "\n";
                sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' THEN SALE_QTY END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY3" + "\n";
                sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT1" + "\n";
                sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT2" + "\n";
                sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT3" + "\n";
            }
        }

        saleDtlChannelVO.setsQuery1(sQuery1);
        saleDtlChannelVO.setsQuery2(sQuery2);

        return saleDtlChannelMapper.getSaleDtlChannelList(saleDtlChannelVO);
    }

    /** 매출상세현황(채널별) 조회(엑셀용) */
//    @Override
//    public List<DefaultMap<String>> getSaleDtlChannelExcelList(SaleDtlChannelVO saleDtlChannelVO, SessionInfoVO sessionInfoVO){
//        saleDtlChannelVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
//        saleDtlChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
//
//        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
//            saleDtlChannelVO.setStoreCds(sessionInfoVO.getStoreCd());
//        }
//
//        // 매장 array 값 세팅
//        String[] storeCds = saleDtlChannelVO.getStoreCds().split(",");
//        saleDtlChannelVO.setStoreCdList(storeCds);
//
//        // 상품 array 값 세팅
//        if (saleDtlChannelVO.getProdCds() != null && !"".equals(saleDtlChannelVO.getProdCds())) {
//            String[] prodCdList = saleDtlChannelVO.getProdCds().split(",");
//            saleDtlChannelVO.setProdCdList(prodCdList);
//        }
//
//        // 매장브랜드 '전체' 일때
//        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
//            if (saleDtlChannelVO.getStoreHqBrandCd() == "" || saleDtlChannelVO.getStoreHqBrandCd() == null || saleDtlChannelVO.getProdHqBrandCd() == "" || saleDtlChannelVO.getProdHqBrandCd() == null) {
//                // 사용자별 브랜드 array 값 세팅
//                String[] userBrandList = saleDtlChannelVO.getUserBrands().split(",");
//                saleDtlChannelVO.setUserBrandList(userBrandList);
//            }
//        }
//
//        // 동적 컬럼 생성을 위한 쿼리 변수;
//        String sQuery1 = "";
//        String sQuery2 = "";
//
//        String[] arrDlvrInFgCol = saleDtlChannelVO.getDlvrInFgCol().split(",");
//
//        if(arrDlvrInFgCol.length > 0){
//            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
//                saleDtlChannelVO.setArrDlvrInFgCol(arrDlvrInFgCol);
//            }
//        }
//
//        String[] orderFg = {"STIN","DLVR","PACK"}; // 내점, 배달, 포장
//        String[] list = saleDtlChannelVO.getArrDlvrInFgCol(); // 주문채널
//
//
//        // 내점, 배달, 포장의 수량과 실매출액
//        for(int j = 1; j <= orderFg.length; j++) {
//            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY1" + "\n";
//            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY2" + "\n";
//            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY3" + "\n";
//            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT1" + "\n";
//            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT2" + "\n";
//            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT3" + "\n";
//
//            sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN SALE_QTY END) AS " + orderFg[j-1] + "_SALE_QTY1" + "\n";
//            sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN SALE_QTY END) AS " + orderFg[j-1] + "_SALE_QTY2" + "\n";
//            sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' THEN SALE_QTY END) AS " + orderFg[j-1] + "_SALE_QTY3" + "\n";
//            sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_REAL_SALE_AMT1" + "\n";
//            sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_REAL_SALE_AMT2" + "\n";
//            sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_REAL_SALE_AMT3" + "\n";
//        }
//
//        // 내점, 배달, 포장의 주문채널별 수량과 실매출액
//        for(int j = 1; j <= orderFg.length; j++) {
//            for (int i = 0; i < list.length; i++) {
//                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY1" + "\n";
//                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY2" + "\n";
//                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY3" + "\n";
//                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT1" + "\n";
//                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT2" + "\n";
//                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT3" + "\n";
//
//                sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN SALE_QTY END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY1" + "\n";
//                sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN SALE_QTY END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY2" + "\n";
//                sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' THEN SALE_QTY END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY3" + "\n";
//                sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT1" + "\n";
//                sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT2" + "\n";
//                sQuery2 += ", (CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT3" + "\n";
//            }
//        }
//
//        saleDtlChannelVO.setsQuery1(sQuery1);
//        saleDtlChannelVO.setsQuery2(sQuery2);
//
//        return saleDtlChannelMapper.getSaleDtlChannelExcelList(saleDtlChannelVO);
//    }


    /** 매출상세현황(채널별) 매출 다운로드 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSaleDtlChannelExcelList(SaleDtlChannelVO saleDtlChannelVO, SessionInfoVO sessionInfoVO) {

        saleDtlChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return saleDtlChannelMapper.getSaleDtlChannelExcelList(saleDtlChannelVO);
    }

    /** 매출상세현황(채널별) 매출 다운로드 탭 - 자료생성 저장 */
    @Override
    public int getSaleDtlChannelSave(SaleDtlChannelVO saleDtlChannelVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();
        String currentDate = currentDateString();
        String currentTime = currentTimeString();

        saleDtlChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        saleDtlChannelVO.setReqDate(currentDate);
        saleDtlChannelVO.setReqTime(currentTime);

        saleDtlChannelVO.setRegDt(currentDt);
        saleDtlChannelVO.setRegId(sessionInfoVO.getUserId());
        saleDtlChannelVO.setModDt(currentDt);
        saleDtlChannelVO.setModId(sessionInfoVO.getUserId());

        // 매장 array 값 세팅
        String[] storeCds = saleDtlChannelVO.getStoreCds().split(",");
        saleDtlChannelVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (saleDtlChannelVO.getProdCds() != null && !"".equals(saleDtlChannelVO.getProdCds())) {
            String[] prodCdList = saleDtlChannelVO.getProdCds().split(",");
            saleDtlChannelVO.setProdCdList(prodCdList);
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (saleDtlChannelVO.getStoreHqBrandCd() == "" || saleDtlChannelVO.getStoreHqBrandCd() == null || saleDtlChannelVO.getProdHqBrandCd() == "" || saleDtlChannelVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = saleDtlChannelVO.getUserBrands().split(",");
                saleDtlChannelVO.setUserBrandList(userBrandList);
            }
        }

        // 매장브랜드 뒤에 , 제거용
        saleDtlChannelVO.setUserBrands(saleDtlChannelVO.getUserBrands().substring(0, saleDtlChannelVO.getUserBrands().length()-1));

        procCnt = saleDtlChannelMapper.getSaleDtlChannelSaveInsert(saleDtlChannelVO);

        return procCnt;
    }

    /** 매출상세현황(채널별) 매출 다운로드 탭 - 삭제 */
    @Override
    public int getSaleDtlChannelDel(SaleDtlChannelVO[] saleDtlChannelVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for(SaleDtlChannelVO saleDtlChannelVO : saleDtlChannelVOs) {

            saleDtlChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            procCnt = saleDtlChannelMapper.getSaleDtlChannelDel(saleDtlChannelVO);

            String pathFull = BaseEnv.FILE_UPLOAD_DIR + "/MediaBase/SaleReport/" + saleDtlChannelVO.getFileName();

            // 파일 삭제
            File delFile = new File(pathFull);
            if(delFile.exists()) {
                delFile.delete();
            }
        }

        return procCnt;
    }

    /** 매출상세현황(채널별) 매출 다운로드 탭 - 자료생성 요청건 존재여부 확인 */
    @Override
    public DefaultMap<String> getSaleDtlChannelChk(SaleDtlChannelVO saleDtlChannelVO, SessionInfoVO sessionInfoVO) {

        saleDtlChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return saleDtlChannelMapper.getSaleDtlChannelChk(saleDtlChannelVO);
    }
}
