package kr.co.solbipos.base.prod.qrOrderKeyMap.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.qrOrderKeyMap.service.QrOrderKeyMapService;
import kr.co.solbipos.base.prod.qrOrderKeyMap.service.QrOrderKeyMapVO;
import kr.co.solbipos.store.storeMoms.lsmStore.service.LsmStoreVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
/**
 * @Class Name  : QrOrderKeyMapServiceImpl.java
 * @Description : 기초관리 > 상품관리2 > QR주문키맵관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.11.28  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.11.28
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("QrOrderKeyMapService")
@Transactional
public class QrOrderKeyMapServiceImpl implements QrOrderKeyMapService {

    private final QrOrderKeyMapMapper qrOrderKeyMapMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    public QrOrderKeyMapServiceImpl(QrOrderKeyMapMapper qrOrderKeyMapMapper, MessageService messageService, CmmEnvUtil cmmEnvUtil) {
        this.qrOrderKeyMapMapper = qrOrderKeyMapMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** QR오더 카테고리 (분류) 조회 */
    @Override
    public List<DefaultMap<Object>> getQrOrderCategory(QrOrderKeyMapVO qrOrderKeyMapVO, SessionInfoVO sessionInfoVO) {

        qrOrderKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            qrOrderKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return qrOrderKeyMapMapper.getQrOrderCategory(qrOrderKeyMapVO);
    }

    /** QR오더 - QR오더 카테고리(분류) 저장 */
    @Override
    public int saveQrOrderCategory(QrOrderKeyMapVO[] qrOrderKeyMapVOS, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( QrOrderKeyMapVO qrOrderKeyMapVO : qrOrderKeyMapVOS) {

            qrOrderKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            qrOrderKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                qrOrderKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            qrOrderKeyMapVO.setClsFg("Q"); // Q : KCP-QR
            qrOrderKeyMapVO.setRegDt(currentDt);
            qrOrderKeyMapVO.setRegId(sessionInfoVO.getUserId());
            qrOrderKeyMapVO.setModDt(currentDt);
            qrOrderKeyMapVO.setModId(sessionInfoVO.getUserId());

            // 페이지 수 계산
            int indexNo = Integer.parseInt(qrOrderKeyMapVO.getIndexNo());
            qrOrderKeyMapVO.setTuPage(Integer.toString((int)(Math.floor((indexNo - 1) / 4) + 1)));

            if ( qrOrderKeyMapVO.getStatus() == GridDataFg.INSERT ) { // 생성

                // 분류코드 생성
                qrOrderKeyMapVO.setTuClsCd(qrOrderKeyMapMapper.getQrOrderCategoryCode(qrOrderKeyMapVO));

                result += qrOrderKeyMapMapper.insertQrOrderCategory(qrOrderKeyMapVO);

            } else if ( qrOrderKeyMapVO.getStatus() == GridDataFg.UPDATE ) { // 수정

                result += qrOrderKeyMapMapper.updateQrOrderCategory(qrOrderKeyMapVO);

            } else if ( qrOrderKeyMapVO.getStatus() == GridDataFg.DELETE ) { // 삭제
                result += qrOrderKeyMapMapper.deleteQrOrderCategory(qrOrderKeyMapVO);

                // 해당 카테고리(분류)에 해당하는 상품도 삭제
                qrOrderKeyMapMapper.deleteAllQrOrderKeyMap(qrOrderKeyMapVO);
            }
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            // 키오스크 카테고리 TX 데이터 변경처리 PKG 호출(맘스터치)
            QrOrderKeyMapVO qrOrderKeyMapVO = new QrOrderKeyMapVO();
            qrOrderKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            qrOrderKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            qrOrderKeyMapVO.setTuClsType("");
            qrOrderKeyMapVO.setRegId(sessionInfoVO.getUserId());
//            qrOrderKeyMapMapper.updateKioskClsMomsLsm(qrOrderKeyMapVO);
        }

        if ( result >= 0) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** QR오더 키맵 조회 */
    @Override
    public List<DefaultMap<Object>> getQrOrderKeyMap(QrOrderKeyMapVO qrOrderKeyMapVO, SessionInfoVO sessionInfoVO) {
        qrOrderKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            qrOrderKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return qrOrderKeyMapMapper.getQrOrderKeyMap(qrOrderKeyMapVO);
    }

    /** QR오더 - QR오더 키맵 수정 */
    @Override
    public int updateQrOrderKeyMap(QrOrderKeyMapVO[] qrOrderKeyMapVOS, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();
        String pageFg = "";

        for ( QrOrderKeyMapVO qrOrderKeyMapVO : qrOrderKeyMapVOS) {

            qrOrderKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            qrOrderKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                qrOrderKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            qrOrderKeyMapVO.setClsFg("Q"); // Q : KCP-QR
            qrOrderKeyMapVO.setModDt(currentDt);
            qrOrderKeyMapVO.setModId(sessionInfoVO.getUserId());

            // 페이지 수 계산
            int indexNo = Integer.parseInt(qrOrderKeyMapVO.getIndexNo());
            qrOrderKeyMapVO.setTuPage(Integer.toString((int)(Math.floor((indexNo - 1) / 16) + 1)));

            if ( qrOrderKeyMapVO.getStatus() == GridDataFg.UPDATE ) { // 수정
                // 내점
                if(qrOrderKeyMapVO.isSaleTypeYnSin()){
                    qrOrderKeyMapVO.setSaleTypeYnSinVal("Y");
                }else{
                    qrOrderKeyMapVO.setSaleTypeYnSinVal("N");
                }
                // 배달
                if(qrOrderKeyMapVO.isSaleTypeYnDlv()){
                    qrOrderKeyMapVO.setSaleTypeYnDlvVal("Y");
                }else{
                    qrOrderKeyMapVO.setSaleTypeYnDlvVal("N");
                }
                // 포장
                if(qrOrderKeyMapVO.isSaleTypeYnPkg()){
                    qrOrderKeyMapVO.setSaleTypeYnPkgVal("Y");
                }else{
                    qrOrderKeyMapVO.setSaleTypeYnPkgVal("N");
                }

                // 내점,배달,포장 변경 시 저장
                result = qrOrderKeyMapMapper.updateProductSaleType(qrOrderKeyMapVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                result = qrOrderKeyMapMapper.updateQrOrderKeyMap(qrOrderKeyMapVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            } else if ( qrOrderKeyMapVO.getStatus() == GridDataFg.DELETE ) { // 삭제
                result += qrOrderKeyMapMapper.deleteQrOrderKeyMap(qrOrderKeyMapVO);
            }
        }

//        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { 트리거 처리
//            // 키오스크 카테고리 TX 데이터 변경처리 PKG 호출(맘스터치)
//            QrOrderKeyMapVO qrOrderKeyMapVO = new QrOrderKeyMapVO();
//            qrOrderKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
//            qrOrderKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
//            qrOrderKeyMapVO.setTuClsType("");
//            qrOrderKeyMapVO.setRegId(sessionInfoVO.getUserId());
//            qrOrderKeyMapMapper.updateKioskClsMomsLsm(qrOrderKeyMapVO);
//        }

        if ( result >= 0) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** QR오더 상품 조회 */
    @Override
    public List<DefaultMap<String>> getQrOrderProdList(QrOrderKeyMapVO qrOrderKeyMapVO, SessionInfoVO sessionInfoVO) {
        qrOrderKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            qrOrderKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return qrOrderKeyMapMapper.getQrOrderProdList(qrOrderKeyMapVO);
    }

    /** QR오더 - QR오더 키맵 수정 */
    @Override
    public int saveQrOrderKeyMap(QrOrderKeyMapVO[] qrOrderKeyMapVOS, SessionInfoVO sessionInfoVO) {
        String dt = currentDateTimeString();

        int procCnt = 0;
        String pageFg = "";

        for(QrOrderKeyMapVO qrOrderKeyMapVO : qrOrderKeyMapVOS) {

            qrOrderKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            qrOrderKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                qrOrderKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            qrOrderKeyMapVO.setClsFg("Q"); // Q : KCP-QR
            qrOrderKeyMapVO.setRegDt(dt);
            qrOrderKeyMapVO.setRegId(sessionInfoVO.getUserId());
            qrOrderKeyMapVO.setModDt(dt);
            qrOrderKeyMapVO.setModId(sessionInfoVO.getUserId());
            
            // 내점
            if(qrOrderKeyMapVO.isSaleTypeYnSin()){
                qrOrderKeyMapVO.setSaleTypeYnSinVal("Y");
            }else{
                qrOrderKeyMapVO.setSaleTypeYnSinVal("N");
            }
            // 배달
            if(qrOrderKeyMapVO.isSaleTypeYnDlv()){
                qrOrderKeyMapVO.setSaleTypeYnDlvVal("Y");
            }else{
                qrOrderKeyMapVO.setSaleTypeYnDlvVal("N");
            }
            // 포장
            if(qrOrderKeyMapVO.isSaleTypeYnPkg()){
                qrOrderKeyMapVO.setSaleTypeYnPkgVal("Y");
            }else{
                qrOrderKeyMapVO.setSaleTypeYnPkgVal("N");
            }

            // 내점,배달,포장 변경 시 저장
            procCnt = qrOrderKeyMapMapper.updateProductSaleType(qrOrderKeyMapVO);
            if(procCnt <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // QR오더 키 관련 코드 조회
            DefaultMap<String> keyValue = qrOrderKeyMapMapper.getQrOrderKeyMapCode(qrOrderKeyMapVO);

            qrOrderKeyMapVO.setTuKeyCd(keyValue.get("tuKeyCd"));
            qrOrderKeyMapVO.setIndexNo(String.valueOf(keyValue.get("indexNo")));

            // 페이지 수 계산
            int indexNo = Integer.parseInt(String.valueOf(keyValue.get("indexNo")));
            qrOrderKeyMapVO.setTuPage(Integer.toString((int)(Math.floor((indexNo - 1) / 16) + 1)));

            // 키맵등록
            procCnt = qrOrderKeyMapMapper.saveQrOrderKeyMap(qrOrderKeyMapVO);
            if(procCnt <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }


        return procCnt;
    }

    /** 개발/운영 URL 조회 */
    @Override
    public List<DefaultMap<Object>> getApiEnvNm(QrOrderKeyMapVO qrOrderKeyMapVO, SessionInfoVO sessionInfoVO) {
        qrOrderKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());

        return qrOrderKeyMapMapper.getApiEnvNm(qrOrderKeyMapVO);
    }

    /** API 호출 결과 저장 */
    @Override
    public int saveApiLog(QrOrderKeyMapVO qrOrderKeyMapVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        qrOrderKeyMapVO.setRegDt(currentDt);
        qrOrderKeyMapVO.setRegId(sessionInfoVO.getUserId());
        qrOrderKeyMapVO.setModDt(currentDt);
        qrOrderKeyMapVO.setModId(sessionInfoVO.getUserId());

        qrOrderKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        qrOrderKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());

        return qrOrderKeyMapMapper.saveApiLog(qrOrderKeyMapVO);
    }
}
