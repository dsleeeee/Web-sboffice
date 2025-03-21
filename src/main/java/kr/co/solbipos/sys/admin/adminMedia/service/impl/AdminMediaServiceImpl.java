package kr.co.solbipos.sys.admin.adminMedia.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.system.BaseEnv;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.media.service.MediaApplcStoreVO;
import kr.co.solbipos.base.store.memberTerms.service.MemberTermsVO;
import kr.co.solbipos.base.store.memberTerms.service.impl.MemberTermsMapper;
import kr.co.solbipos.pos.confg.verrecv.enums.VerRecvFg;
import kr.co.solbipos.sys.admin.adminMedia.service.AdminMediaService;
import kr.co.solbipos.sys.admin.adminMedia.service.AdminMediaVO;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("AdminMediaServiceImpl")
@Transactional
public class AdminMediaServiceImpl implements AdminMediaService {

    private final AdminMediaMapper adminMediaMapper;
    private final MessageService messageService;
    private final MemberTermsMapper memberTermsMapper; // 이용약관관리
    private final PopupMapper popupMapper;

    public AdminMediaServiceImpl(AdminMediaMapper adminMediaMapper, MessageService messageService, MemberTermsMapper memberTermsMapper, PopupMapper popupMapper) {
        this.adminMediaMapper = adminMediaMapper;
        this.messageService = messageService;
        this.memberTermsMapper = memberTermsMapper;
        this.popupMapper = popupMapper;
    }

    /** 파일타입 콤보박스 조회 */
    @Override
    public List<DefaultMap<String>> getFileTypeComboList(AdminMediaVO adminMediaVO, SessionInfoVO sessionInfoVO) {
        return adminMediaMapper.getFileTypeComboList(adminMediaVO);
    }

    /** 듀얼모니터영상관리 탭 - 조회 */
    @Override
    public List<DefaultMap<String>> getMediaList(SessionInfoVO sessionInfoVO, AdminMediaVO adminMediaVO) {
        return adminMediaMapper.getMediaList(adminMediaVO);
    }

    @Override
    public int getMediaDelete(AdminMediaVO[] adminMediaVOS, SessionInfoVO sessionInfoVO) {
        int procCnt = 0;

        for(AdminMediaVO adminMediaVO : adminMediaVOS) {

            System.out.println("듀얼모니터영상관리 탭 >>> 삭제 >>> verSerNo : " + adminMediaVO.getVerSerNo());

            // 동영상 삭제
            procCnt = adminMediaMapper.getMediaDelete(adminMediaVO);
            if(procCnt <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 적용매장 삭제
            procCnt = adminMediaMapper.getMediaHqStoreDelete(adminMediaVO);

            System.out.println("듀얼모니터영상관리 탭 >>> 삭제 >>> fileNmExt : " + adminMediaVO.getFileNmExt());

//            String path = "D:\\Workspace\\javaWeb\\testMediaImg\\";
            String path = BaseEnv.FILE_UPLOAD_DIR + "Media/";
            String pathFull = path + adminMediaVO.getFileNmExt();
            File delFile = new File(pathFull);
            if(!adminMediaVO.getFileNmExt().equals("0000000169017628290977")) {      //기준테이블바탕화면 실제파일은 삭제하지 않음
                if(delFile.exists()) {
                    delFile.delete();
                }
            }
        }

        return procCnt;
    }

    /** 파일등록 팝업 - 첨부파일 확장자 체크 */
    @Override
    public String getFileType(SessionInfoVO sessionInfoVO, AdminMediaVO adminMediaVO) {
        return adminMediaMapper.getFileType(adminMediaVO);
    }

    /** 파일 등록 */
    @Override
    public String getRegistMedia(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo) {
        String isSuccess;

        try{
            AdminMediaVO adminMediaVO = uploadFile(multi);
            if (!adminMediaVO.getResult().equals("T")){
                isSuccess = "2";
                return isSuccess;
            }

            System.out.println("파일타입 : " + (String)multi.getParameter("fileType"));
            // 이용약관관리시 이전내역은 전부 사용여부 N처리
            if( ("008").equals((String)multi.getParameter("fileType")) ) {
                String currentDt = currentDateTimeString();

                MemberTermsVO memberTermsVO = new MemberTermsVO();
                memberTermsVO.setModDt(currentDt);
                memberTermsVO.setModId((String)multi.getParameter("userId"));

                memberTermsVO.setOrgnFg((String) multi.getParameter("orgnFg"));
                memberTermsVO.setHqOfficeCd((String) multi.getParameter("hqOfficeCd"));
                memberTermsVO.setStoreCd((String)multi.getParameter("storeCd"));

                System.out.println("이용약관관리 신규등록시 이전내역은 전부 사용여부 N처리");

                memberTermsMapper.getMemberTermsRegistPreUpdateSave(memberTermsVO);
            }

            String insertDt = currentDateTimeString();
            adminMediaVO.setHqOfficeCd((String) multi.getParameter("hqOfficeCd"));
            adminMediaVO.setStoreCd((String)multi.getParameter("storeCd"));

            adminMediaVO.setVerSerNo(adminMediaMapper.getFileCd(adminMediaVO));

            adminMediaVO.setVerSerNm((String)multi.getParameter("verSerNm"));

            adminMediaVO.setStartDate((String)multi.getParameter("startDate"));
            adminMediaVO.setEndDate((String)multi.getParameter("endDate"));
            adminMediaVO.setFileType((String)multi.getParameter("fileType"));

            if(String.valueOf(UseYn.Y).equals(multi.getParameter("useYn"))){
                adminMediaVO.setUseYn(UseYn.Y);
            } else {
                adminMediaVO.setUseYn(UseYn.N);
            }

            // 파일 확장자명 조회
            String fileType = adminMediaMapper.getFileTypeNm(adminMediaVO);

            if(adminMediaVO.getFileOrgNm().length() > 0){
                String orgFileNm[] = adminMediaVO.getFileOrgNm().split("\\\\");
                adminMediaVO.setFileOrgNm(orgFileNm[orgFileNm.length-1]);
            } else {
                adminMediaVO.setFileOrgNm(fileType + "_" + adminMediaVO.getVerSerNo());
            }

            adminMediaVO.setRegDt(insertDt);
            adminMediaVO.setRegId((String)multi.getParameter("userId"));
            adminMediaVO.setModDt(insertDt);
            adminMediaVO.setModId((String)multi.getParameter("userId"));

            adminMediaVO.setDispTime((String)multi.getParameter("dispTime"));

            // 동영상출력순서 자동채번
            String dispSeq = adminMediaMapper.getDispSeq(adminMediaVO);
            adminMediaVO.setDispSeq(dispSeq);
            System.out.println("파일타입 : " +fileType + " / 동영상출력순서 : " + dispSeq);

            adminMediaVO.setLangFg((String)multi.getParameter("langFg"));
            adminMediaVO.setAdverUrl((String)multi.getParameter("adverUrl"));

            // 파일 등록
            if(adminMediaMapper.verRegist(adminMediaVO) > 0) {
                isSuccess = "0";
            } else {
                isSuccess = "1";
            }

        }catch(Exception e){
            isSuccess = "1";
        }
        return isSuccess;
    }

    /** 파일 수정 */
    @Override
    public String getModifyMedia(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo) {
        String isSuccess;

        try{
            AdminMediaVO adminMediaVO = uploadFile(multi);
            if (!adminMediaVO.getResult().equals("T")){
                isSuccess = "2";
                return isSuccess;
            }

            String insertDt = currentDateTimeString();

            adminMediaVO.setHqOfficeCd((String)multi.getParameter("hqOfficeCd"));
            adminMediaVO.setStoreCd((String)multi.getParameter("storeCd"));

            adminMediaVO.setVerSerNo((String)multi.getParameter("verSerNo"));
            adminMediaVO.setVerSerNm((String)multi.getParameter("verSerNm"));
            adminMediaVO.setStartDate((String)multi.getParameter("startDate"));
            adminMediaVO.setEndDate((String)multi.getParameter("endDate"));
            adminMediaVO.setFileType((String)multi.getParameter("fileType"));
            adminMediaVO.setDelYn("N");

            if(String.valueOf(UseYn.Y).equals(multi.getParameter("useYn"))){
                adminMediaVO.setUseYn(UseYn.Y);
            } else {
                adminMediaVO.setUseYn(UseYn.N);
            }

            // 파일 확장자명 조회
            String fileType = adminMediaMapper.getFileTypeNm(adminMediaVO);
            // 파일이 수정 된 경우
            if(adminMediaVO.getFileOrgNm() != null) {
                // 2. 기존에 등록된 파일 삭제
                // 파일서버 대응 경로 지정 (운영)
                String path = BaseEnv.FILE_UPLOAD_DIR + "Media/";
                DefaultMap<String> fileInfo = adminMediaMapper.dtlInfo2(adminMediaVO);
                if(!fileInfo.get("fileNm").equals("0000000169017628290977")){   //기준테이블바탕화면 실제파일은 삭제하지 않음
                    if (fileInfo.size() > 0) {
                        // 서버 파일 삭제
                        File delFile = new File(path + fileInfo.get("fileNm"));
                        if (delFile.exists()) {
                            delFile.delete();
                        }
                    }
                }

                if(adminMediaVO.getFileOrgNm().length() > 0){
                    String orgFileNm[] = adminMediaVO.getFileOrgNm().split("\\\\");
                    adminMediaVO.setFileOrgNm(orgFileNm[orgFileNm.length-1]);
                } else {
                    adminMediaVO.setFileOrgNm(fileType + "_" + adminMediaVO.getVerSerNo());
                }

            }

            adminMediaVO.setModDt(insertDt);
            adminMediaVO.setModId((String)multi.getParameter("userId"));

            adminMediaVO.setDispTime((String)multi.getParameter("dispTime"));
            adminMediaVO.setLangFg((String)multi.getParameter("langFg"));
            adminMediaVO.setAdverUrl((String)multi.getParameter("adverUrl"));

            // 파일 수정
            adminMediaMapper.verModify(adminMediaVO);

            isSuccess = "0";

        }catch(Exception e){

            isSuccess = "1";
        }
        return isSuccess;
    }

    /** 듀얼모니터영상관리 탭 - 파일정보 상세 조회 */
    @Override
    public DefaultMap<String> dtlInfo(SessionInfoVO sessionInfoVO, AdminMediaVO adminMediaVO) {
        return adminMediaMapper.dtlInfo(adminMediaVO);
    }

    /** 버전 체크 */
    @Override
    public String chkFileType(SessionInfoVO sessionInfoVO, AdminMediaVO adminMediaVO) {
        return adminMediaMapper.chkFileType(adminMediaVO);
    }

    /** 날짜 체크 */
    @Override
    public String chkDate(SessionInfoVO sessionInfoVO, AdminMediaVO adminMediaVO) {
        return adminMediaMapper.chkDate(adminMediaVO);
    }

    /** 듀얼모니터영상관리 탭 - 파일 중복 가능 갯수 확인 */
    @Override
    public String chkDupCnt(SessionInfoVO sessionInfoVO, AdminMediaVO adminMediaVO) {
        return adminMediaMapper.chkDupCnt(adminMediaVO);
    }

    /** 듀얼모니터영상관리 탭 - 파일 갯수 확인 */
    @Override
    public int chkFileTypeCnt(SessionInfoVO sessionInfoVO, AdminMediaVO adminMediaVO) {
        return adminMediaMapper.chkFileTypeCnt(adminMediaVO);
    }

    /** 파일 업로드 (등록, 수정 )  */
    private AdminMediaVO uploadFile(MultipartHttpServletRequest multi) {

        String isSuccess = "";

        AdminMediaVO adminMediaVO = new AdminMediaVO();

        // 저장 경로 설정 (개발시 로컬)
//        String root = multi.getSession().getServletContext().getRealPath("/");
//        String path = root+"resources/upload/";
//        String path = "D:\\Workspace\\javaWeb\\testMediaImg\\";

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
            newFileName = (String)multi.getParameter("orgnCd")+ String.valueOf(System.currentTimeMillis() + rndNm);

            MultipartFile mFile = multi.getFile(uploadFile);
            String orgFileName = mFile.getOriginalFilename();
            String fileExt = FilenameUtils.getExtension(orgFileName);

            adminMediaVO.setFileType((String)multi.getParameter("fileType"));

            // 수정 시 파일은 수정하지 않고 다른 정보만 수정 할 경우
            if (!orgFileName.equals("")) {

                // 첨부파일 확장자 체크
                String type = adminMediaMapper.getFileType(adminMediaVO);     // 확장자|확장자|확장자 를
                String[] typeList = type.split(",");         // |기준으로 잘라서 배열에 넣음

                for(int i = 0; i < typeList.length; i++){          // 잘린 확장자 확인
                    if(fileExt.equals(typeList[i].replace(".",""))){
                        adminMediaVO.setResult("T");
                    }
                }

                if (mFile.getOriginalFilename().lastIndexOf('.') > 0) { // 파일명 최소 한글자 이상은 되어야함.

                    orgFileName = mFile.getOriginalFilename().substring(0, mFile.getOriginalFilename().lastIndexOf('.'));
                    // 파일경로
                    adminMediaVO.setFileDir(path);
                    // 파일명 (물리적으로 저장되는 파일명)
                    adminMediaVO.setFileNm(newFileName);
                    // 파일확장자
                    adminMediaVO.setFileExt(fileExt);
                    // 파일사이즈
                    Long fileSize = mFile.getSize();
                    adminMediaVO.setFileSize(fileSize.intValue());
                    // 파일 MIME_TYPE
                    adminMediaVO.setFileMimeType(mFile.getContentType());
                    // 원본 파일명
                    adminMediaVO.setFileOrgNm(orgFileName);
                }

                try {
                    mFile.transferTo(new File(path + newFileName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                adminMediaVO.setResult("T");
            }
        }

        return adminMediaVO;
    }

    /** 재생순서관리 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMediaPlaySeqList(AdminMediaVO adminMediaVO, SessionInfoVO sessionInfoVO) {
        return adminMediaMapper.getMediaPlaySeqList(adminMediaVO);
    }

    /** 재생순서관리 탭 - 저장 */
    @Override
    public int getMediaPlaySeqSaveUpdate(AdminMediaVO[] adminMediaVOs, SessionInfoVO sessionInfoVO) {
        int procCnt = 0;

        String currentDt = currentDateTimeString();

        for(AdminMediaVO adminMediaVO : adminMediaVOs) {

            adminMediaVO.setModDt(currentDt);
            adminMediaVO.setModId(sessionInfoVO.getUserId());

            procCnt = adminMediaMapper.getMediaPlaySeqSaveUpdate(adminMediaVO);
        }

        return procCnt;
    }

    /** 매장등록 - 조회 */
    @Override
    public List<DefaultMap<String>> srchStoreList(AdminMediaVO adminMediaVO, SessionInfoVO sessionInfoVO) {
        System.out.println(adminMediaVO.getHqOfficeCd() + "본사코드 서비스");
        return adminMediaMapper.srchStoreList(adminMediaVO);
    }

    /** 매장 적용 - 파일 등록 */
    @Override
    public int getRegistStore(MediaApplcStoreVO[] applcStores, SessionInfoVO sessionInfo) {
        int procCnt = 0;

        String dt = currentDateTimeString();

        for(MediaApplcStoreVO applcStore : applcStores) {

            applcStore.setRegDt(dt);
            applcStore.setModDt(dt);
            applcStore.setRegId(sessionInfo.getUserId());
            applcStore.setModId(sessionInfo.getUserId());
            applcStore.setVerRecvFg(VerRecvFg.REG);
            applcStore.setVerRecvDt(dt);;

            int result = adminMediaMapper.getRegistStore(applcStore);
            procCnt++;
        }

        return procCnt;
    }

    /** 매장 적용 - 파일 삭제 */
    @Override
    public int getRemoveStore(MediaApplcStoreVO[] applcStores, SessionInfoVO sessionInfo) {
        int procCnt = 0;

        for(MediaApplcStoreVO applcStore : applcStores) {
            procCnt = adminMediaMapper.getRemoveStore(applcStore);
            if(procCnt <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return procCnt;
    }
}
