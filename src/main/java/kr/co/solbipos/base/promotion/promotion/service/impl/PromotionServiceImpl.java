package kr.co.solbipos.base.promotion.promotion.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.system.BaseEnv;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.promotion.promotion.service.PromotionService;
import kr.co.solbipos.base.promotion.promotion.service.PromotionVO;
import kr.co.solbipos.base.store.media.service.MediaVO;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : PromotionServiceImpl.java
 * @Description : 기초관리 - 프로모션관리 - 프로모션관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.13  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021 .04. 13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("PromotionService")
public class PromotionServiceImpl implements PromotionService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PromotionMapper promotionMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public PromotionServiceImpl(PromotionMapper promotionMapper, MessageService messageService) {
        this.promotionMapper = promotionMapper;
        this.messageService = messageService;
    }

    /** 프로모션관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPromotionList(@RequestBody PromotionVO promotionVO, SessionInfoVO sessionInfoVO) {

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionMapper.getPromotionList(promotionVO);
    }

    /** 프로모션 등록/수정 */
    @Override
    public String savePromotion(PromotionVO promotionVO, SessionInfoVO sessionInfoVO){

        int result = 0;
        String currentDt = currentDateTimeString();

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        promotionVO.setRegDt(currentDt);
        promotionVO.setRegId(sessionInfoVO.getUserId());
        promotionVO.setModDt(currentDt);
        promotionVO.setModId(sessionInfoVO.getUserId());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
            promotionVO.setRegFg(sessionInfoVO.getOrgnFg().getCode()); // 프로모션 등록구분 H:본사 S:매장
        }

        // 신규등록 시, 프로모션 코드 생성
        String promotionCd = "";
        if(!"".equals(promotionVO.getPromotionCd()) && promotionVO.getPromotionCd() != null){
            promotionCd = promotionVO.getPromotionCd();

            // 수정모드 시, 적용상품 체크 여부에 따른 적용상품 리스트 데이터 삭제
            // 적용상품 체크 제거(모든 프로모션 종류에서 상품 필요 20211209)
            /*if("N".equals(promotionVO.getProdCdYn())){
                promotionMapper.deletePromotionProdAll(promotionVO);
            }*/

            // 수정모드 시, 혜택유형 선택에 따른 혜택상품 리스트 데이터 삭제
            if("1".equals(promotionVO.getTypeCd()) || "2".equals(promotionVO.getTypeCd()) || "5".equals(promotionVO.getTypeCd()) || "6".equals(promotionVO.getTypeCd())){
                result = promotionMapper.deletePromotionPresentAll(promotionVO);
            }

        }else{
            promotionCd = promotionMapper.getPromotionCode(promotionVO);
            promotionVO.setPromotionCd(promotionCd);
        }

        // 프로모션 코드가 있는 경우, 저장
        if(!"".equals(promotionCd) && promotionCd != null){

            // 프로모션 마스터 정보 저장
            result = promotionMapper.savePromotionH(promotionVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 프로모션 적용조건 정보 저장
            result = promotionMapper.savePromotionCondi(promotionVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 프로모션 적용혜택 정보 저장
            result = promotionMapper.savePromotionBene(promotionVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        }

        if(result > 0){
            return promotionCd;
        }else{
            return "";
        }
    }

    /** 프로모션 상세 조회 */
    @Override
    public DefaultMap<String> getPromotionDetail(PromotionVO promotionVO, SessionInfoVO sessionInfoVO){

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionMapper.getPromotionDetail(promotionVO);
    }

    /** 프로모션 적용상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPromotionProdList(@RequestBody PromotionVO promotionVO, SessionInfoVO sessionInfoVO) {

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionMapper.getPromotionProdList(promotionVO);
    }

    /** 프로모션 적용상품 선택팝업 상품리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdList(@RequestBody PromotionVO promotionVO, SessionInfoVO sessionInfoVO) {

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        promotionVO.setUserId(sessionInfoVO.getUserId());

        return promotionMapper.getProdList(promotionVO);
    }

    /** 프로모션 적용상품 선택팝업 분류리스트 조회 */
    @Override
    public List<DefaultMap<String>> getClassList(@RequestBody PromotionVO promotionVO, SessionInfoVO sessionInfoVO){

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionMapper.getClassList(promotionVO);
    }

    /** 프로모션 적용상품 선택팝업 상품추가/수정/삭제 */
    @Override
    public int savePromotionProd(PromotionVO[] promotionVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for ( PromotionVO promotionVO : promotionVOs ) {

            promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            promotionVO.setRegDt(currentDt);
            promotionVO.setRegId(sessionInfoVO.getUserId());
            promotionVO.setModDt(currentDt);
            promotionVO.setModId(sessionInfoVO.getUserId());

            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            if ( promotionVO.getStatus() == GridDataFg.INSERT ) {

                // 프로모션 적용상품 추가
                result = promotionMapper.insertPromotionProd(promotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }else if( promotionVO.getStatus() == GridDataFg.UPDATE ){

                // 프로모션 적용상품 조건수량 수정
                result = promotionMapper.updatePromotionProd(promotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }else if( promotionVO.getStatus() == GridDataFg.DELETE ){

                // 프로모션 적용상품 삭제
                result = promotionMapper.deletePromotionProd(promotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }
        }

        if ( procCnt == promotionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }

    /** 프로모션 적용매장 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPromotionStoreList(@RequestBody PromotionVO promotionVO, SessionInfoVO sessionInfoVO) {

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionMapper.getPromotionStoreList(promotionVO);
    }

    /** 프로모션 적용매장 선택팝업 매장리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(@RequestBody PromotionVO promotionVO, SessionInfoVO sessionInfoVO) {

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionMapper.getStoreList(promotionVO);
    }

    /** 프로모션 적용매장 선택팝업 전매장적용 */
    @Override
    public int insertPromotionStoreAll(PromotionVO promotionVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        promotionVO.setRegDt(currentDt);
        promotionVO.setRegId(sessionInfoVO.getUserId());
        promotionVO.setModDt(currentDt);
        promotionVO.setModId(sessionInfoVO.getUserId());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 프로모션 전매장적용
        result = promotionMapper.insertPromotionStoreAll(promotionVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }

    /** 프로모션 적용매장 선택팝업 매장추가/삭제 */
    @Override
    public int savePromotionStore(PromotionVO[] promotionVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for ( PromotionVO promotionVO : promotionVOs ) {

            promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            promotionVO.setRegDt(currentDt);
            promotionVO.setRegId(sessionInfoVO.getUserId());
            promotionVO.setModDt(currentDt);
            promotionVO.setModId(sessionInfoVO.getUserId());

            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            if ( promotionVO.getStatus() == GridDataFg.INSERT ) {

                // 프로모션 적용매장 추가
                result = promotionMapper.insertPromotionStore(promotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }else if( promotionVO.getStatus() == GridDataFg.DELETE ){

                // 프로모션 적용매장 삭제
                result = promotionMapper.deletePromotionStore(promotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }
        }

        if ( procCnt == promotionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }

    /** 프로모션 혜택상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPromotionPresentList(@RequestBody PromotionVO promotionVO, SessionInfoVO sessionInfoVO) {

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionMapper.getPromotionPresentList(promotionVO);
    }

    /** 프로모션 혜택상품 선택팝업 상품리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPresentProdList(@RequestBody PromotionVO promotionVO, SessionInfoVO sessionInfoVO) {

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionMapper.getPresentProdList(promotionVO);
    }

    /** 프로모션 혜택상품 선택팝업 상품추가/수정/삭제 */
    @Override
    public int savePromotionPresent(PromotionVO[] promotionVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for ( PromotionVO promotionVO : promotionVOs ) {

            promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            promotionVO.setRegDt(currentDt);
            promotionVO.setRegId(sessionInfoVO.getUserId());
            promotionVO.setModDt(currentDt);
            promotionVO.setModId(sessionInfoVO.getUserId());

            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            if ( promotionVO.getStatus() == GridDataFg.INSERT ) {

                // 프로모션 혜택상품 추가
                result = promotionMapper.insertPromotionPresent(promotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }else if( promotionVO.getStatus() == GridDataFg.UPDATE ){

                // 프로모션 혜택상품 조건수량 수정
                result = promotionMapper.updatePromotionPresent(promotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }else if( promotionVO.getStatus() == GridDataFg.DELETE ){

                // 프로모션 혜택상품 삭제
                result = promotionMapper.deletePromotionPresent(promotionVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                procCnt ++;

            }
        }

        if ( procCnt == promotionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }

    /**
     * 프로모션 종류 조회(콤보박스용)
     */
    @Override
    public List<DefaultMap<String>> getPromotionTypeList() {
        return promotionMapper.getPromotionTypeList();
    }

    /** 프로모션 종류 변경에 따른 필수값 저장 */
    @Override
    public String savePromotionDefaultSet(PromotionVO promotionVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        promotionVO.setModDt(currentDt);
        promotionVO.setModId(sessionInfoVO.getUserId());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            promotionVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        String promotionCd = promotionVO.getPromotionCd();

        // 프로모션 코드가 있는 경우, 저장
        if(!"".equals(promotionCd) && promotionCd != null){

            // 수정모드 시, 혜택유형 선택에 따른 혜택상품 리스트 데이터 삭제
            if("1".equals(promotionVO.getTypeCd()) || "2".equals(promotionVO.getTypeCd()) || "5".equals(promotionVO.getTypeCd()) || "6".equals(promotionVO.getTypeCd())){
                result = promotionMapper.deletePromotionPresentAll(promotionVO);
            }

            // 프로모션 종류 변경 (마스터)
            result = promotionMapper.updatePromotionDefaultSet(promotionVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 프로모션 종류 변경에 따른 적용조건 필수값 저장
            result = promotionMapper.updatePromotionCondiDefaultSet(promotionVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 프로모션 종류 변경에 따른 적용혜택 필수값 저장
            result = promotionMapper.updatePromotionBeneDefaultSet(promotionVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        }

        if(result > 0){
            return promotionCd;
        }else{
            return "";
        }
    }

    /** 프로모션 적용매장 전체삭제 */
    @Override
    public int deletePromotionStoreAll(PromotionVO promotionVO, SessionInfoVO sessionInfoVO){

        promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        int result = promotionMapper.deletePromotionStoreAll(promotionVO);
        if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }

    /** 프로모션 적용매장 매장 엑셀업로드 */
    @Override
    public int excelUploadPromotionStore(PromotionVO[] promotionVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        // 업로드 프로모션 적용매장 등록
        for (PromotionVO promotionVO : promotionVOs) {

            promotionVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            promotionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            promotionVO.setRegDt(currentDt);
            promotionVO.setRegId(sessionInfoVO.getUserId());
            promotionVO.setModDt(currentDt);
            promotionVO.setModId(sessionInfoVO.getUserId());

            // "'" 제거
            promotionVO.setStoreCd(promotionVO.getStoreCd() != null ? promotionVO.getStoreCd().replaceAll("'","") : "");

            // 프로모션 적용매장 추가
            if(!"".equals(promotionVO.getStoreCd())){
                result = promotionMapper.insertPromotionStore(promotionVO);
               if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            procCnt ++;
        }

        if (procCnt == promotionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }

    /**  프로모션 키오스크 배너 조회 */
    @Override
    public List<DefaultMap<String>> getPromotionBanner(MediaVO mediaVO, SessionInfoVO sessionInfoVO) {

        mediaVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mediaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            mediaVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return promotionMapper.getPromotionBanner(mediaVO);
    }

    /**  프로모션 키오스크 배너 등록 */
    @Override
    public String savePromotionBanner(MultipartHttpServletRequest multi, MediaVO mediaVO, SessionInfoVO sessionInfoVO) {

        String isSuccess;
        String dt = currentDateTimeString();

        // 기존파일이 있는경우, 먼저 삭제
        if(!"".equals(mediaVO.getVerSerNo())){
            LOGGER.info("기존파일삭제 : " + mediaVO.getVerSerNo());
            delPromotionBanner(mediaVO, sessionInfoVO);
        }

        try{
             mediaVO = uploadFile(multi, mediaVO);
            if (!mediaVO.getResult().equals("T")){ // 파일 확장자체크 결과 오류인경우
                isSuccess = "2";
                return isSuccess;
            }

            mediaVO.setVerSerNo(promotionMapper.getFileCd(mediaVO));

            String fileType = promotionMapper.getFileTypeNm(mediaVO);
            if(mediaVO.getFileOrgNm().length() > 0){
                String orgFileNm[] = mediaVO.getFileOrgNm().split("\\\\");
                mediaVO.setFileOrgNm(orgFileNm[orgFileNm.length-1]);
            } else {
                mediaVO.setFileOrgNm(fileType + "_" + mediaVO.getVerSerNo());
            }

            mediaVO.setVerSerNm(mediaVO.getFileOrgNm());
            mediaVO.setRegDt(dt);
            mediaVO.setModDt(dt);
            mediaVO.setDispTime("3");
            mediaVO.setDispSeq(promotionMapper.getDispSeq(mediaVO));

            // 파일 등록
            if(promotionMapper.verRegist(mediaVO) > 0) {
                isSuccess = "0";
            } else {
                isSuccess = "1";
            }

        }catch(Exception e){
            isSuccess = "1";
        }
        return isSuccess;

    }

    /** 파일 업로드 (등록, 수정) */
    private MediaVO uploadFile(MultipartHttpServletRequest multi, MediaVO mediaVO) {

        String isSuccess = "";

        // 저장 경로 설정 (개발시 로컬)
        //String path = "D:\\prod_img/";

        // 파일서버 대응 경로 지정 (운영)
        String path = BaseEnv.FILE_UPLOAD_DIR + "Media/";

        // 업로드 되는 파일명
        String newFileName = "";

        File dir = new File(path);
        if(!dir.isDirectory()){
            dir.mkdir();
        }

        Iterator<String> files = multi.getFileNames();

        while(files.hasNext()){

            String uploadFile = files.next();

            String rndNm = "";

            for(int i=0; i<8; i++) {
                int rndVal = (int)(Math.random() * 62);
                if(rndVal < 10) {
                    rndNm += rndVal;
                }
            }

            // 파일명 orgnCd + 기존 + 8자리 난수
            newFileName = mediaVO.getOrgnCd() + String.valueOf(System.currentTimeMillis() + rndNm);

            MultipartFile mFile = multi.getFile(uploadFile);
            String orgFileName = mFile.getOriginalFilename();
            String fileExt = FilenameUtils.getExtension(orgFileName);

            if (!"".equals(orgFileName)) {

                String type = promotionMapper.getFileType(mediaVO);
                String[] typeList = type.split(",");

                // 확장자 확인
                for(int i = 0; i < typeList.length; i++){
                    if(fileExt.equals(typeList[i].replace(".",""))){
                        mediaVO.setResult("T");
                    }
                }

                if (mFile.getOriginalFilename().lastIndexOf('.') > 0) { // 파일명 최소 한글자 이상은 되어야함.

                    orgFileName = mFile.getOriginalFilename().substring(0, mFile.getOriginalFilename().lastIndexOf('.'));
                    // 파일경로
                    mediaVO.setFileDir(path);
                    // 파일명 (물리적으로 저장되는 파일명)
                    mediaVO.setFileNm(newFileName);
                    // 파일확장자
                    mediaVO.setFileExt(fileExt);
                    // 파일사이즈
                    Long fileSize = mFile.getSize();
                    mediaVO.setFileSize(fileSize.intValue());
                    // 파일 MIME_TYPE
                    mediaVO.setFileMimeType(mFile.getContentType());
                    // 원본 파일명
                    mediaVO.setFileOrgNm(orgFileName);
                }

                try {
                    // 파일 서버에 업로드
                    mFile.transferTo(new File(path + newFileName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return mediaVO;
    }

    /**  프로모션 키오스크 배너 삭제 */
    @Override
    public boolean delPromotionBanner(MediaVO mediaVO, SessionInfoVO sessionInfoVO){

        boolean isSuccess = true;

        try{

            // 저장 경로 설정
            //String path = "D:\\prod_img/";
            String path = BaseEnv.FILE_UPLOAD_DIR + "Media/";

            // 기존 파일 정보가 있는지 확인
            String imgFileNm = promotionMapper.getFileChk(mediaVO);

            if(imgFileNm.length() > 0){

                // 파일 delete
                if(promotionMapper.delFile(mediaVO) > 0) {
                    // 서버 파일 삭제
                    File delFile = new File(path + imgFileNm);
                    if(delFile.exists()) {
                        delFile.delete();
                    }
                    isSuccess = true;
                } else {
                    isSuccess = false;
                }

            }else{
                isSuccess = false;
            }

        }catch(Exception e){

            isSuccess = false;
        }
        return isSuccess;
    }

    /** 프로모션 키오스크 배너 수정(프로모션관련 정보만 수정) */
    @Override
    public int modPromotionBanner(MediaVO mediaVO, SessionInfoVO sessionInfoVO){

        int result = 0;
        String dt = currentDateTimeString();

        mediaVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mediaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            mediaVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        mediaVO.setRegDt(dt);
        mediaVO.setRegId(sessionInfoVO.getUserId());
        mediaVO.setModDt(dt);
        mediaVO.setModId(sessionInfoVO.getUserId());

        // 파일 정보 테이블에 프로모션 정보만 UPDATE
        result = promotionMapper.modPromotionBanner(mediaVO);
        if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }
}
