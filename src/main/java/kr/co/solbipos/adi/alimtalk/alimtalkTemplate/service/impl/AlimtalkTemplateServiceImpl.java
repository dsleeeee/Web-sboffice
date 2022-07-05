package kr.co.solbipos.adi.alimtalk.alimtalkTemplate.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.alimtalk.alimtalkTemplate.service.AlimtalkTemplateService;
import kr.co.solbipos.adi.alimtalk.alimtalkTemplate.service.AlimtalkTemplateVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;
import kr.co.common.system.BaseEnv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : AlimtalkTemplateServiceImpl.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡 템플릿관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.08  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.06.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("alimtalkTemplateService")
@Transactional
public class AlimtalkTemplateServiceImpl implements AlimtalkTemplateService {
    private final AlimtalkTemplateMapper alimtalkTemplateMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public AlimtalkTemplateServiceImpl(AlimtalkTemplateMapper alimtalkTemplateMapper) {
        this.alimtalkTemplateMapper = alimtalkTemplateMapper;
    }

    /** 알림톡 템플릿관리 - 조회 */
    @Override
    public List<DefaultMap<Object>> getAlimtalkTemplateList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO) {

        alimtalkTemplateVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return alimtalkTemplateMapper.getAlimtalkTemplateList(alimtalkTemplateVO);
    }

    /** 알림톡 전송유형 콤보박스 조회 */
    @Override
    public List<DefaultMap<Object>> getSendTypeCdComboList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO) {

        return alimtalkTemplateMapper.getSendTypeCdComboList(alimtalkTemplateVO);
    }

    /** 알림톡 전송유형상세 콤보박스 조회 */
    @Override
    public List<DefaultMap<Object>> getSendTypeDtlCdComboList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO) {

        return alimtalkTemplateMapper.getSendTypeDtlCdComboList(alimtalkTemplateVO);
    }

    /** 알림톡 계정 콤보박스 조회 */
    @Override
    public List<DefaultMap<Object>> getGroupKeyComboList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<Object>> result = new ArrayList<DefaultMap<Object>>();

        // 그룹
        if(alimtalkTemplateVO.getTemplateGrpFg().equals("1")) {
            result = alimtalkTemplateMapper.getGroupKeyComboList(alimtalkTemplateVO);
        }

        return result;
    }

    /** 알림톡 템플릿 카테고리(대분류) 콤보박스 조회 */
    @Override
    public List<DefaultMap<Object>> getTemplateClsCdLComboList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO) {

        return alimtalkTemplateMapper.getTemplateClsCdLComboList(alimtalkTemplateVO);
    }

    /** 알림톡 템플릿 카테고리(중분류) 콤보박스 조회 */
    @Override
    public List<DefaultMap<Object>> getTemplateClsCdMComboList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO) {

        return alimtalkTemplateMapper.getTemplateClsCdMComboList(alimtalkTemplateVO);
    }

    /** 알림톡 템플릿등록 팝업 - #{변수} 조회 */
    @Override
    public List<DefaultMap<Object>> getAlimtalkTemplateParamsList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO) {

        return alimtalkTemplateMapper.getAlimtalkTemplateParamsList(alimtalkTemplateVO);
    }

    /** 전체 #{변수} 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getAlimtalkTemplateParamsColList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO) {

        return alimtalkTemplateMapper.getAlimtalkTemplateParamsColList(alimtalkTemplateVO);
    }

    /** 알림톡 템플릿등록 팝업 - 저장 */
    @Override
//    public int getAlimtalkTemplateRegisterSave(AlimtalkTemplateVO[] alimtalkTemplateVOs, SessionInfoVO sessionInfoVO) {
    public String getAlimtalkTemplateRegisterSave(AlimtalkTemplateVO[] alimtalkTemplateVOs, SessionInfoVO sessionInfoVO) {

//        int procCnt = 0;
        String procCnt = "";
        String currentDt = currentDateTimeString();

        for (AlimtalkTemplateVO alimtalkTemplateVO : alimtalkTemplateVOs) {
            alimtalkTemplateVO.setOrgnCd(sessionInfoVO.getOrgnCd());

            alimtalkTemplateVO.setRegDt(currentDt);
            alimtalkTemplateVO.setRegId(sessionInfoVO.getUserId());
            alimtalkTemplateVO.setModDt(currentDt);
            alimtalkTemplateVO.setModId(sessionInfoVO.getUserId());

            alimtalkTemplateVO.setSendTypeCd(alimtalkTemplateVOs[0].getSendTypeCd());
            alimtalkTemplateVO.setSendTypeDtlCd(alimtalkTemplateVOs[0].getSendTypeDtlCd());
            alimtalkTemplateVO.setCommonFg(alimtalkTemplateVOs[0].getCommonFg());

            // 템플릿코드(자동 채번)
            String templateNum = alimtalkTemplateMapper.getAlimtalkTemplateTemplateNum(alimtalkTemplateVO);
            alimtalkTemplateVO.setTemplateCd(alimtalkTemplateVO.getSendTypeCd() + alimtalkTemplateVO.getSendTypeDtlCd() + alimtalkTemplateVO.getCommonFg() + templateNum);
            alimtalkTemplateVO.setTemplateNm(alimtalkTemplateVO.getSendTypeDtlNm() + alimtalkTemplateVO.getCommonFg() + templateNum);

            System.out.println("WEB_ALIMTALK >>> 알림톡 템플릿등록 >>> 저장 >>> 템플릿코드 : " + alimtalkTemplateVO.getTemplateCd());
            System.out.println("WEB_ALIMTALK >>> 알림톡 템플릿등록 >>> 저장 >>> 템플릿명 : " + alimtalkTemplateVO.getTemplateNm());

            alimtalkTemplateMapper.getAlimtalkTemplateRegisterSaveInsert(alimtalkTemplateVO);

//            procCnt = alimtalkTemplateVO.getTemplateCd();
        }

        return procCnt;
    }

    /** 알림톡 템플릿등록 팝업 - 저장 */
    @Override
//    public boolean getAlimtalkTemplateImageFileSave(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo) {
    public String getAlimtalkTemplateImageFileSave(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo) {

        boolean isSuccess = true;
        String procCnt = "";

        try{

            // 업로드 파일 읽기
            AlimtalkTemplateVO alimtalkTemplateVO = new AlimtalkTemplateVO();

            // 현재 일자
            String currentDt = currentDateTimeString();

            alimtalkTemplateVO.setModDt(currentDt);
            alimtalkTemplateVO.setModId(sessionInfo.getUserId());

            alimtalkTemplateVO.setOrgnCd((String)multi.getParameter("orgnCd"));

            // 저장경로 폴더
            String path_folder = alimtalkTemplateVO.getOrgnCd();

            // 저장 경로 설정 (개발시 로컬) (파일 저장용)
//            String pre_path = "D:\\Workspace\\javaWeb\\alimtalkTemplateImg\\";
//            String path = "D:\\Workspace\\javaWeb\\alimtalkTemplateImg\\" + path_folder + "\\";

            // 파일서버 대응 경로 지정 (운영) (파일 저장용)
            String pre_path = BaseEnv.FILE_UPLOAD_DIR + "alimtalkTemplateImg/";
            String path = BaseEnv.FILE_UPLOAD_DIR + "alimtalkTemplateImg/" + path_folder + "/";
            // FileRoot/alimtalkTemplateImg/A0001/1597125734220.jpg
            // C:\Users\김설아\.IntelliJIdea2018.3\system\tomcat\Unnamed_sboffice\work\Catalina\localhost\ROOT\FileRoot\alimtalkTemplateImg\A0001\1597125734220.jpg

            // 저장 경로 설정 (디비 저장용)
            String path_table = multi.getRequestURL().toString().replace(multi.getRequestURI(),"") + "/alimtalkTemplateImg/" + path_folder;
            // http://192.168.0.85:10001/alimtalkTemplateImg/A0001

            // 업로드 되는 파일명
            String newFileName = "";
            // 원본 파일명
            String orgFileName = "";

            // 경로에 폴도가 있는지 체크
            File pre_dir = new File(pre_path);
            if(!pre_dir.isDirectory()){
                pre_dir.mkdir();
            }
            File dir = new File(path);
            if(!dir.isDirectory()){
                dir.mkdir();
            }

            List<MultipartFile> fileList = multi.getFiles("file");
            // 선택한 파일이 있으면
            for(MultipartFile mFile : fileList)
            {
                newFileName = String.valueOf(System.currentTimeMillis()); // 파일명 (물리적으로 저장되는 파일명)
                orgFileName = mFile.getOriginalFilename(); // 원본 파일명
                String fileExt = FilenameUtils.getExtension(orgFileName); // 파일확장자

                // orgFileName
                if ( orgFileName.contains(".") ) {
                    orgFileName = orgFileName.substring(0, orgFileName.lastIndexOf("."));
                }
                // IE에선 C:\Users\김설아\Desktop\123\new2.txt
                // 크롬에선 new2.txt
                if ( orgFileName.contains("\\") ) {
                    orgFileName = orgFileName.substring(orgFileName.lastIndexOf("\\"));
                    orgFileName = orgFileName.substring(1);
                }

                if(mFile.getOriginalFilename().lastIndexOf('.') > 0) { // 파일명 최소 한글자 이상은 되어야함.
                    // 파일경로
                    alimtalkTemplateVO.setFilePath(path_table);
                    // 파일명 (물리적으로 저장되는 파일명)
                    alimtalkTemplateVO.setFileNm(newFileName);
                    // 원본 파일명
//                    alimtalkTemplateVO.setOrginlFileNm(orgFileName);
                    // 파일확장자
                    alimtalkTemplateVO.setFileExt(fileExt);

                    // 파일 저장하는 부분
                    try {
                        mFile.transferTo(new File(path+newFileName+"."+fileExt));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    procCnt = newFileName+"."+fileExt;
                }
            }

        }catch(Exception e){

            isSuccess = false;
        }
//        return isSuccess;
        return procCnt;
    }

    /** 알림톡 템플릿상세 팝업 - 조회 */
    @Override
    public DefaultMap<String> getAlimtalkTemplateDtlList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO) {

        DefaultMap<String> resultMap = new DefaultMap<String>();

        resultMap = alimtalkTemplateMapper.getAlimtalkTemplateDtlList(alimtalkTemplateVO);

        return resultMap;
    }

    /** 알림톡 템플릿상세 팝업 - 버튼 조회 */
    @Override
    public List<DefaultMap<Object>> getAlimtalkTemplateDtlButtonsList(AlimtalkTemplateVO alimtalkTemplateVO, SessionInfoVO sessionInfoVO) {

        return alimtalkTemplateMapper.getAlimtalkTemplateDtlButtonsList(alimtalkTemplateVO);
    }
}