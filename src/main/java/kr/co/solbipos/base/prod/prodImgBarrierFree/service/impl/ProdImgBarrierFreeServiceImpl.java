package kr.co.solbipos.base.prod.prodImgBarrierFree.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prodImgBarrierFree.service.ProdImgBarrierFreeService;
import kr.co.solbipos.base.prod.prodImgBarrierFree.service.ProdImgBarrierFreeVO;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ProdImgBarrierFreeServiceImpl.java
 * @Description : 기초관리 > 상품관리2 > 베리어프리-이미지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.05.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.05.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodImgBarrierFreeService")
@Transactional
public class ProdImgBarrierFreeServiceImpl implements ProdImgBarrierFreeService {
    private final ProdImgBarrierFreeMapper prodImgBarrierFreeMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdImgBarrierFreeServiceImpl(ProdImgBarrierFreeMapper prodImgBarrierFreeMapper, MessageService messageService) {
        this.prodImgBarrierFreeMapper = prodImgBarrierFreeMapper;
        this.messageService = messageService;
    }

    /** 베리어프리-이미지관리 - 조회 */
    @Override
    public List<DefaultMap<Object>> getProdImgBarrierFreeList(ProdImgBarrierFreeVO prodImgBarrierFreeVO, SessionInfoVO sessionInfoVO) {

        prodImgBarrierFreeVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodImgBarrierFreeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodImgBarrierFreeVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        prodImgBarrierFreeVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (prodImgBarrierFreeVO.getProdHqBrandCd() == "" || prodImgBarrierFreeVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (prodImgBarrierFreeVO.getUserProdBrands() != null && !"".equals(prodImgBarrierFreeVO.getUserProdBrands())) {
                    String[] userBrandList = prodImgBarrierFreeVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        prodImgBarrierFreeVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        return prodImgBarrierFreeMapper.getProdImgBarrierFreeList(prodImgBarrierFreeVO);
    }

    /** 베리어프리-이미지관리 - 이미지 조회 */
    @Override
    public List<DefaultMap<Object>> getProdImgBarrierFreeImageList(ProdImgBarrierFreeVO prodImgBarrierFreeVO, SessionInfoVO sessionInfoVO) {

        prodImgBarrierFreeVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodImgBarrierFreeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodImgBarrierFreeVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return prodImgBarrierFreeMapper.getProdImgBarrierFreeImageList(prodImgBarrierFreeVO);
    }

    /** 베리어프리-이미지관리 - 이미지 저장 */
    @Override
    public String getProdImgBarrierFreeImageSave(MultipartHttpServletRequest multi, ProdImgBarrierFreeVO prodImgBarrierFreeVO, SessionInfoVO sessionInfoVO) {

        String isSuccess = "";

        try{

            // 저장 경로 설정
            String path_folder = "";
            String dt = currentDateTimeString();

            // 접속권한에 따른 등록자정보 및 경로 셋팅
            // 첨부파일 업로드 시, sessionInfoVO에 있는 orgnFg 값을 제대로 읽어오지 못하는 현상때문에 해당 방식 사용.
            if(String.valueOf(prodImgBarrierFreeVO.getOrgnFg()).equals("H")) {
                prodImgBarrierFreeVO.setModId(prodImgBarrierFreeVO.getUserId());
                prodImgBarrierFreeVO.setRegId(prodImgBarrierFreeVO.getUserId());
                path_folder = prodImgBarrierFreeVO.getHqOfficeCd();

            } else if(String.valueOf(prodImgBarrierFreeVO.getOrgnFg()).equals("S")) {
                prodImgBarrierFreeVO.setModId(prodImgBarrierFreeVO.getUserId());
                prodImgBarrierFreeVO.setRegId(prodImgBarrierFreeVO.getUserId());
                path_folder = prodImgBarrierFreeVO.getStoreCd();
            }

            prodImgBarrierFreeVO.setImgChgDt(dt);
            prodImgBarrierFreeVO.setModDt(dt);
            prodImgBarrierFreeVO.setRegDt(dt);

            // 서버 저장 경로 (imgFg -> 001: 기본이미지, 002: KIOSK이미지, 003: DID이미지)
            // 서버용 - 개발/운영 서버에 반영할 진짜 경로!!!!!!
            String pre_path = BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + path_folder + "/";
            String path = BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + path_folder + "/" + prodImgBarrierFreeVO.getImgFg() + "/";

            // 로컬 테스트용 - 로컬에서 파일 업로드가 잘되는지 확인하기 위해 임의로 설정한 경로
//            String pre_path = "D:\\prod_img\\" + path_folder + "/";
//            String path = "D:\\prod_img\\" + path_folder + "/" + prodImgBarrierFreeVO.getImgFg() + "/";

            // DB 저장 경로
            String path_table = multi.getRequestURL().toString().replace(multi.getRequestURI(),"") + "/ProdImg/" + path_folder + "/" + prodImgBarrierFreeVO.getImgFg();

            // 서버 저장 위치에 해당 폴더가 존재하는지 확인 후 없으면 폴더 생성
            // 부모-자식 폴더 동시에 생성 불가하기 때문에 pre_path 폴더 생성 후 path 폴더 생성
            File pre_dir = new File(pre_path);
            if(!pre_dir.isDirectory()){
                pre_dir.mkdir();
            }
            File dir = new File(path);
            if(!dir.isDirectory()){
                dir.mkdir();
            }

            // 업로드를 위한 새 파일명
            String newFileName = "";
            // 원본 파일명
            String orgFileName = "";
            // 파일 확장자
            String fileExt = "";

            List<MultipartFile> fileList = multi.getFiles(prodImgBarrierFreeVO.getImgFgType());

            for(MultipartFile mFile : fileList)
            {
                newFileName = String.valueOf(System.currentTimeMillis()); // 파일명 (물리적으로 저장되는 파일명)
                orgFileName = mFile.getOriginalFilename();
                fileExt = FilenameUtils.getExtension(orgFileName);

                if(!fileExt.equals("png") && !fileExt.equals("PNG") && !fileExt.equals("jpg") && !fileExt.equals("JPG") )
                {
                    isSuccess = "3";
                    break;
                }

                if(orgFileName.lastIndexOf('.') > 0) { // 파일명 최소 한글자 이상은 되어야함.

                    prodImgBarrierFreeVO.setImgUrl(path_table);
                    prodImgBarrierFreeVO.setImgFileNm(newFileName + "." + fileExt);

                    // 서버 파일 업로드
                    try {
                        File destFile = new File(path + newFileName + "." + fileExt);
                        mFile.transferTo(destFile);
                        Runtime.getRuntime().exec("chmod -R 640 " + destFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // 이미지 정보 DB 저장
                    // 1. 기존 이미지 있는지 확인
                    String imgFileNm = prodImgBarrierFreeMapper.getProdImgBarrierFreeImageChk(prodImgBarrierFreeVO);

                    // 2. 기존이미지가 없는경우 INSERT, 있는경우 UPDATE
                    if(imgFileNm == null) {
                        if(prodImgBarrierFreeMapper.getProdImgBarrierFreeImageSaveInsert(prodImgBarrierFreeVO) > 0) {
                            isSuccess = "0";
                        } else {
                            isSuccess = "1";
                        }
                    } else {
                        if(prodImgBarrierFreeMapper.getProdImgBarrierFreeImageSaveUpdate(prodImgBarrierFreeVO) > 0) {
                            // 서버 파일 삭제
                            File delFile = new File(path + imgFileNm);
                            if(delFile.exists()) {
                                delFile.delete();
                            }
                            isSuccess = "0";
                        } else {
                            isSuccess = "1";
                        }
                    }
                }
            }

        }catch(Exception e){

            isSuccess = "1";
        }
        return isSuccess;
    }

    /** 베리어프리-이미지관리 - 이미지 삭제 */
    @Override
    public boolean getProdImgBarrierFreeImageDelete(ProdImgBarrierFreeVO prodImgBarrierFreeVO, SessionInfoVO sessionInfoVO){

        boolean isSuccess = true;

        try{

            // 저장 경로 설정
            String path_folder = "";

            // 접속권한에 따른 경로 셋팅
            if(String.valueOf(prodImgBarrierFreeVO.getOrgnFg()).equals("H")) {
                path_folder = prodImgBarrierFreeVO.getHqOfficeCd();

            } else if(String.valueOf(prodImgBarrierFreeVO.getOrgnFg()).equals("S")) {
                path_folder = prodImgBarrierFreeVO.getStoreCd();
            }

            // 서버 저장 경로 설정 (imgFg -> 001: 기본이미지, 002: KIOSK이미지, 003: DID이미지)
            // 서버용 - 개발/운영 서버에 반영할 진짜 경로!!!!!!
            String path = BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + path_folder + "/" + prodImgBarrierFreeVO.getImgFg() + "/";

            // 로컬 테스트용 - 로컬에서 파일 업로드가 잘되는지 확인하기 위해 임의로 설정한 경로
//            String path = "D:\\prod_img\\" + path_folder + "/" + prodImgBarrierFreeVO.getImgFg() + "/";

            // 삭제할 상품이미지 파일명 가져오기
            String imgFileNm = prodImgBarrierFreeMapper.getProdImgBarrierFreeImageChk(prodImgBarrierFreeVO);

            if(imgFileNm.length() > 0){

                // 상품 이미지 저장 delete
                if(prodImgBarrierFreeMapper.getProdImgBarrierFreeImageDelete(prodImgBarrierFreeVO) > 0) {
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

    /** 베리어프리-이미지관리 매장적용 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getProdImgBarrierFreeStoreRegistList(ProdImgBarrierFreeVO prodImgBarrierFreeVO, SessionInfoVO sessionInfoVO) {

        prodImgBarrierFreeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return prodImgBarrierFreeMapper.getProdImgBarrierFreeStoreRegistList(prodImgBarrierFreeVO);
    }

    /** 베리어프리-이미지관리 매장적용 팝업 - 저장 */
    @Override
    public int getProdImgBarrierFreeStoreRegistSave(ProdImgBarrierFreeVO[] prodImgBarrierFreeVOs, SessionInfoVO sessionInfoVO){

        int result = 0;
        String dt = currentDateTimeString();

        // 매장 키오스크 포스 환경설정값 일괄 저장
        for (ProdImgBarrierFreeVO prodImgBarrierFreeVO : prodImgBarrierFreeVOs) {

            prodImgBarrierFreeVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodImgBarrierFreeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            prodImgBarrierFreeVO.setImgChgDt(dt);
            prodImgBarrierFreeVO.setRegDt(dt);
            prodImgBarrierFreeVO.setRegId(sessionInfoVO.getUserId());
            prodImgBarrierFreeVO.setModDt(dt);
            prodImgBarrierFreeVO.setModId(sessionInfoVO.getUserId());

            // 이미지타입
            if(!StringUtil.getOrBlank(prodImgBarrierFreeVO.getImgFg()).equals("")) {
                prodImgBarrierFreeVO.setArrImgFg(prodImgBarrierFreeVO.getImgFg().split(","));
            }

            // 기존 매장이미지 삭제
            prodImgBarrierFreeMapper.getProdImgBarrierFreeStoreRegistDelete(prodImgBarrierFreeVO);

            for(int i = 0; i < prodImgBarrierFreeVO.getArrImgFg().length; i++){
                System.out.println("삭제 명령어 보기 : " + "/usr/bin/rm -rf " + BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + prodImgBarrierFreeVO.getStoreCd() + "/" + prodImgBarrierFreeVO.getArrImgFg()[i] + "/");
                // 삭제명령어
                try {
                    Runtime.getRuntime().exec("/usr/bin/rm -rf " + BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + prodImgBarrierFreeVO.getStoreCd() + "/" + prodImgBarrierFreeVO.getArrImgFg()[i] + "/");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 서버 파일복사
                prodImgBarrierFreeVO.setOrgImgFg(prodImgBarrierFreeVO.getArrImgFg()[i]);

                // 등록된 이미지 리스트 가져옴
                List<DefaultMap<String>> orgFileNm = prodImgBarrierFreeMapper.getProdImgBarrierFreeStoreRegistFileNm(prodImgBarrierFreeVO);

                String pre_path = BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + prodImgBarrierFreeVO.getStoreCd() + "/";
                String path = BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + prodImgBarrierFreeVO.getStoreCd() + "/" + prodImgBarrierFreeVO.getArrImgFg()[i] + "/";

                // 서버 저장 위치에 해당 폴더가 존재하는지 확인 후 없으면 폴더 생성
                // 부모-자식 폴더 동시에 생성 불가하기 때문에 pre_path 폴더 생성 후 path 폴더 생성
                File pre_dir = new File(pre_path);
                if(!pre_dir.isDirectory()){
                    pre_dir.mkdir();
                }
                File dir = new File(path);
                if(!dir.isDirectory()){
                    dir.mkdir();
                }

                for(int j = 0; j < orgFileNm.size(); j++){
                    // 서버 파일 업로드
                    System.out.println("복사 명령어 : " + "/usr/bin/cp -f " + BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + prodImgBarrierFreeVO.getHqOfficeCd() + "/" + prodImgBarrierFreeVO.getArrImgFg()[i] + "/" + orgFileNm.get(j).get("orgImgFileNm") + " " + BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + prodImgBarrierFreeVO.getStoreCd() + "/" + prodImgBarrierFreeVO.getArrImgFg()[i] + "/");
                    try {
                        Runtime.getRuntime().exec("/usr/bin/cp -f " + BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + prodImgBarrierFreeVO.getHqOfficeCd() + "/" + prodImgBarrierFreeVO.getArrImgFg()[i] + "/" + orgFileNm.get(j).get("orgImgFileNm") + " " + BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + prodImgBarrierFreeVO.getStoreCd() + "/" + prodImgBarrierFreeVO.getArrImgFg()[i] + "/");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // DB 저장
                if(orgFileNm.size() > 0) {
                    // 본사상품이미지 매장적용
                    result = prodImgBarrierFreeMapper.getProdImgBarrierFreeStoreRegistSave(prodImgBarrierFreeVO);
                }
            }

            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return result;
    }

    /** 베리어프리-이미지관리 이미지복사 팝업 - 저장 */
    @Override
    public int getProdImgBarrierFreeCopySave(ProdImgBarrierFreeVO prodImgBarrierFreeVO, SessionInfoVO sessionInfoVO) {

        int result = 0;

        String dt = currentDateTimeString();

        prodImgBarrierFreeVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodImgBarrierFreeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodImgBarrierFreeVO.setStoreCd(sessionInfoVO.getStoreCd());
        prodImgBarrierFreeVO.setImgChgDt(dt);
        prodImgBarrierFreeVO.setRegDt(dt);
        prodImgBarrierFreeVO.setRegId(sessionInfoVO.getUserId());
        prodImgBarrierFreeVO.setModDt(dt);
        prodImgBarrierFreeVO.setModId(sessionInfoVO.getUserId());

        // 저장 경로 설정
        String path_folder = "";

        // 접속권한에 따른 등록자정보 및 경로 셋팅
        // 첨부파일 업로드 시, sessionInfoVO에 있는 orgnFg 값을 제대로 읽어오지 못하는 현상때문에 해당 방식 사용.
        if(String.valueOf(prodImgBarrierFreeVO.getOrgnFg()).equals("H")) {
            path_folder = prodImgBarrierFreeVO.getHqOfficeCd();

        } else if(String.valueOf(prodImgBarrierFreeVO.getOrgnFg()).equals("S")) {
            path_folder = prodImgBarrierFreeVO.getStoreCd();
        }

        // 서버용 - 개발/운영 서버에 반영할 진짜 경로!!!!!!
        String pre_path = BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + path_folder + "/";
        String path = BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + path_folder + "/" + prodImgBarrierFreeVO.getImgFg() + "/";
        String orgPath = BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + path_folder + "/" + prodImgBarrierFreeVO.getOrgImgFg() + "/";

        // 로컬 테스트용 - 로컬에서 파일 업로드가 잘되는지 확인하기 위해 임의로 설정한 경로
//        String pre_path = "D:\\prod_img\\" + path_folder + "/";
//        String path = "D:\\prod_img\\" + path_folder + "/" + prodImgBarrierFreeVO.getImgFg() + "/";
//        String orgPath = "D:\\prod_img\\" + path_folder + "/" + prodImgBarrierFreeVO.getOrgImgFg() + "/";

        // 서버 저장 위치에 해당 폴더가 존재하는지 확인 후 없으면 폴더 생성
        // 부모-자식 폴더 동시에 생성 불가하기 때문에 pre_path 폴더 생성 후 path 폴더 생성
        File pre_dir = new File(pre_path);
        if(!pre_dir.isDirectory()){
            pre_dir.mkdir();
        }
        File dir = new File(path);
        if(!dir.isDirectory()){
            dir.mkdir();
        }
        File org_dir = new File(orgPath);
        if(!org_dir.isDirectory()){
            org_dir.mkdir();
        }

        if(prodImgBarrierFreeVO.getGubun().equals("A")){   // 전체복사
            // 등록된 이미지 리스트 가져옴
            List<DefaultMap<String>> orgFileNm = prodImgBarrierFreeMapper.getProdImgBarrierFreeStoreRegistFileNm(prodImgBarrierFreeVO);

            for(int i = 0; i < orgFileNm.size(); i++){
                System.out.println("복사 할 파일 : " + orgFileNm.get(i).get("orgImgFileNm"));
                // 서버 파일 업로드
                System.out.println("전체복사 명령어 : " + "/usr/bin/cp -f " + orgPath + orgFileNm.get(i).get("orgImgFileNm") + " " + path);
                try {
                    Runtime.getRuntime().exec("/usr/bin/cp -f " + orgPath + orgFileNm.get(i).get("orgImgFileNm") + " " + path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            result = prodImgBarrierFreeMapper.getProdImgBarrierFreeCopySaveAll(prodImgBarrierFreeVO);

        } else if(prodImgBarrierFreeVO.getGubun().equals("I")) {   // 단일복사
            String orgFileNm = prodImgBarrierFreeMapper.getProdImgBarrierFreeCopyFileNm(prodImgBarrierFreeVO);

            if(orgFileNm != null){

                System.out.println("단일복사 명령어 : " + "/usr/bin/cp -f " + orgPath + orgFileNm + " " + path);

                // 서버 파일 업로드
                try {
                    Runtime.getRuntime().exec("/usr/bin/cp -f " + orgPath + orgFileNm + " " + path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            result = prodImgBarrierFreeMapper.getProdImgBarrierFreeCopySave(prodImgBarrierFreeVO);
        }

        return result;
    }

    /** 베리어프리-이미지관리 이미지전체삭제 팝업 - 전체삭제 */
    @Override
    public int getProdImgBarrierFreeDeleteAll(ProdImgBarrierFreeVO prodImgBarrierFreeVO, SessionInfoVO sessionInfoVO) {

        prodImgBarrierFreeVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodImgBarrierFreeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodImgBarrierFreeVO.setStoreCd(sessionInfoVO.getStoreCd());

        // 저장 경로 설정
        String path_folder = "";

        // 접속권한에 따른 등록자정보 및 경로 셋팅
        // 첨부파일 업로드 시, sessionInfoVO에 있는 orgnFg 값을 제대로 읽어오지 못하는 현상때문에 해당 방식 사용.
        if(String.valueOf(prodImgBarrierFreeVO.getOrgnFg()).equals("H")) {
            path_folder = prodImgBarrierFreeVO.getHqOfficeCd();

        } else if(String.valueOf(prodImgBarrierFreeVO.getOrgnFg()).equals("S")) {
            path_folder = prodImgBarrierFreeVO.getStoreCd();
        }

        // 서버 저장 경로 (imgFg -> 001: 기본이미지, 002: KIOSK이미지, 003: DID이미지)
        // 서버용 - 개발/운영 서버에 반영할 진짜 경로!!!!!!
        String path = BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + path_folder + "/" + prodImgBarrierFreeVO.getImgFg() + "/";

        // 로컬 테스트용 - 로컬에서 파일 업로드가 잘되는지 확인하기 위해 임의로 설정한 경로
//        String path = "D:\\prod_img\\" + path_folder + "/" + prodImgBarrierFreeVO.getImgFg() + "/";

        System.out.println("전체삭제 명령어 : " + "/usr/bin/rm -rf " + path);

        try {
            Runtime.getRuntime().exec("/usr/bin/rm -rf " + path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return prodImgBarrierFreeMapper.getProdImgBarrierFreeDeleteAll(prodImgBarrierFreeVO);
    }
}