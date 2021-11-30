package kr.co.solbipos.base.prod.prodImg.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.prodImg.service.ProdImgService;
import kr.co.solbipos.base.prod.prodImg.service.ProdImgVO;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("prodImgService")
public class ProdImgServiceImpl implements ProdImgService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ProdImgMapper prodImgMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public ProdImgServiceImpl(ProdImgMapper prodImgMapper, MessageService messageService, CmmEnvUtil cmmEnvUtil) {
        this.prodImgMapper = prodImgMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**  상품이미지관리 - 상품목록조회 */
    @Override
    public List<DefaultMap<String>> getProdList(@RequestBody ProdImgVO prodImgVO, SessionInfoVO sessionInfoVO) {

        String orgnFg = sessionInfoVO.getOrgnFg().getCode();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();

        // 소속구분 설정
        prodImgVO.setOrgnFg(orgnFg);
        prodImgVO.setHqOfficeCd(hqOfficeCd);
        prodImgVO.setStoreCd(storeCd);
        prodImgVO.setUserId(sessionInfoVO.getUserId());

        return prodImgMapper.getProdList(prodImgVO);
    }

    /**  상품이미지관리 - 상품이미지조회 */
    @Override
    public List<DefaultMap<String>> getProdImg(@RequestBody ProdImgVO prodImgVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        prodImgVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodImgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodImgVO.setStoreCd(sessionInfoVO.getStoreCd());

        return prodImgMapper.getProdImg(prodImgVO);
    }

    /**  상품이미지관리 - 상품이미지저장 */
    @Override
    public String saveProdImg(MultipartHttpServletRequest multi, ProdImgVO prodImgVO, SessionInfoVO sessionInfoVO) {

        String isSuccess = "";

        try{

            // 저장 경로 설정
            String path_folder = "";
            String dt = currentDateTimeString();

            // 접속권한에 따른 등록자정보 및 경로 셋팅
            // 첨부파일 업로드 시, sessionInfoVO에 있는 orgnFg 값을 제대로 읽어오지 못하는 현상때문에 해당 방식 사용.
            if(String.valueOf(prodImgVO.getOrgnFg()).equals("H")) {
                prodImgVO.setModId(prodImgVO.getUserId());
                prodImgVO.setRegId(prodImgVO.getUserId());
                path_folder = prodImgVO.getHqOfficeCd();

            } else if(String.valueOf(prodImgVO.getOrgnFg()).equals("S")) {
                prodImgVO.setModId(prodImgVO.getUserId());
                prodImgVO.setRegId(prodImgVO.getUserId());
                path_folder = prodImgVO.getStoreCd();
            }

            prodImgVO.setImgChgDt(dt);
            prodImgVO.setModDt(dt);
            prodImgVO.setRegDt(dt);

            // 서버 저장 경로 (imgFg -> 001: 기본이미지, 002: KIOSK이미지, 003: DID이미지)
            // 서버용 - 개발/운영 서버에 반영할 진짜 경로!!!!!!
            String pre_path = BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + path_folder + "/";
            String path = BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + path_folder + "/" + prodImgVO.getImgFg() + "/";

            // 로컬 테스트용 - 로컬에서 파일 업로드가 잘되는지 확인하기 위해 임의로 설정한 경로
            //String pre_path = "D:\\prod_img\\" + path_folder + "/";
            //String path = "D:\\prod_img\\" + path_folder + "/" + prodImgVO.getImgFg() + "/";

            // DB 저장 경로
            String path_table = multi.getRequestURL().toString().replace(multi.getRequestURI(),"") + "/ProdImg/" + path_folder + "/" + prodImgVO.getImgFg();

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

            List<MultipartFile> fileList = multi.getFiles(prodImgVO.getImgFgType());

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

                    prodImgVO.setImgUrl(path_table);
                    prodImgVO.setImgFileNm(newFileName + "." + fileExt);

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
                    String imgFileNm = prodImgMapper.getProdImgChk(prodImgVO);

                    // 2. 기존이미지가 없는경우 INSERT, 있는경우 UPDATE
                    if(imgFileNm == null) {
                        if(prodImgMapper.saveProdImg(prodImgVO) > 0) {
                            isSuccess = "0";
                        } else {
                            isSuccess = "1";
                        }
                    } else {
                        if(prodImgMapper.updateProgImg(prodImgVO) > 0) {
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

    /**  상품이미지관리 - 상품이미지삭제 */
    @Override
    public boolean delProdImg(ProdImgVO prodImgVO, SessionInfoVO sessionInfoVO){

        boolean isSuccess = true;

        try{

            // 저장 경로 설정
            String path_folder = "";

            // 접속권한에 따른 경로 셋팅
            if(String.valueOf(prodImgVO.getOrgnFg()).equals("H")) {
                path_folder = prodImgVO.getHqOfficeCd();

            } else if(String.valueOf(prodImgVO.getOrgnFg()).equals("S")) {
                path_folder = prodImgVO.getStoreCd();
            }

            // 서버 저장 경로 설정 (imgFg -> 001: 기본이미지, 002: KIOSK이미지, 003: DID이미지)
            String path = BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + path_folder + "/" + prodImgVO.getImgFg() + "/";

            // 삭제할 상품이미지 파일명 가져오기
            String imgFileNm = prodImgMapper.getProdImgChk(prodImgVO);

            if(imgFileNm.length() > 0){

                // 상품 이미지 저장 delete
                if(prodImgMapper.delProdImg(prodImgVO) > 0) {
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

    /** 상품이미지관리 - 이미지매장적용 매장리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(@RequestBody ProdImgVO prodImgVO, SessionInfoVO sessionInfoVO) {

        prodImgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return prodImgMapper.getStoreList(prodImgVO);
    }

    /** 상품이미지관리 - 본사상품이미지 매장적용 */
    @Override
    public int prodImgToStore(ProdImgVO[] prodImgVOs, SessionInfoVO sessionInfoVO){

        int result = 0;
        String dt = currentDateTimeString();

        // 매장 키오스크 포스 환경설정값 일괄 저장
        for (ProdImgVO prodImgVO : prodImgVOs) {

            prodImgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            prodImgVO.setImgChgDt(dt);
            prodImgVO.setRegDt(dt);
            prodImgVO.setRegId(sessionInfoVO.getUserId());
            prodImgVO.setModDt(dt);
            prodImgVO.setModId(sessionInfoVO.getUserId());

            // 이미지타입
            if(!StringUtil.getOrBlank(prodImgVO.getImgFg()).equals("")) {
                prodImgVO.setArrImgFg(prodImgVO.getImgFg().split(","));
            }

            // 기존 매장이미지 삭제
            prodImgMapper.delStoreProdImg(prodImgVO);

            // 본사상품이미지 매장적용
            result = prodImgMapper.prodImgToStore(prodImgVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        }

        return result;
    }

}
