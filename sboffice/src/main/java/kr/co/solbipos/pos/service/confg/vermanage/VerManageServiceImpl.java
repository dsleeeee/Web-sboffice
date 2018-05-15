package kr.co.solbipos.pos.service.confg.vermanage;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.system.Prop;
import kr.co.solbipos.application.domain.login.SessionInfoVO;
import kr.co.solbipos.pos.domain.confg.vermanage.ApplcStoreVO;
import kr.co.solbipos.pos.domain.confg.vermanage.VerInfoVO;
import kr.co.solbipos.pos.enums.VerRecvFg;
import kr.co.solbipos.pos.persistence.confg.vermanage.VerManageMapper;

@Service
public class VerManageServiceImpl implements VerManageService{

    @Autowired
    Prop prop;
    
    @Autowired
    VerManageMapper mapper;

    @Autowired
    MessageService messageService;

    @Override
    public List<DefaultMap<String>> list(VerInfoVO verInfo) {
        return mapper.getList(verInfo);
    }

    @Override
    public DefaultMap<String> dtlInfo(VerInfoVO verInfo) {
        return mapper.dtlInfo(verInfo);
    }
    
    @Override
    public List<DefaultMap<String>> storeList(VerInfoVO verInfo) {
        return mapper.storeList(verInfo);
    }
    
    @Override
    public int verDelete(VerInfoVO verInfo) {
        //TODO 버전 적용 매장도 함께 삭제해야 할지?
        return mapper.verDelete(verInfo);
    }

    @Override
    public int chkVerSerNo(VerInfoVO verInfo) {
        return mapper.chkVerSerNo(verInfo);
    }
    

    @Override
    public List<DefaultMap<String>> srchStoreList(ApplcStoreVO applcStore) {
        return mapper.srchStoreList(applcStore);
    }

    @Override
    public int registStore(ApplcStoreVO[] applcStores, SessionInfoVO sessionInfo) {
        
        int procCnt = 0;
        
        String insertDt = currentDateTimeString();
        
        for(ApplcStoreVO applcStore : applcStores) {
            applcStore.setRegDt(insertDt);
            applcStore.setModDt(insertDt);
            applcStore.setRegId(sessionInfo.getUserId());
            applcStore.setModId(sessionInfo.getUserId());
            applcStore.setVerRecvFg(VerRecvFg.REG);
            applcStore.setVerRecvDt(insertDt);;

            procCnt += mapper.registStore(applcStore);
        }
        
        if(procCnt == applcStores.length) {
            return procCnt;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    
    @Override
    public boolean regist(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo) {

        //TODO 업로드랑 저장 됐는데 어디서 오류나는지 찾기
        boolean isSuccess = false;
        
        try{
            
            VerInfoVO verInfo = uploadFile(multi);
            
            String insertDt = currentDateTimeString();
            
            verInfo.setVerSerNo((String)multi.getParameter("verSerNo"));
            verInfo.setVerSerNm((String)multi.getParameter("verSerNm"));
            verInfo.setFileSize((String)multi.getParameter("fileSize"));
            verInfo.setFileDesc((String)multi.getParameter("fileDesc"));
            verInfo.setProgFg((String)multi.getParameter("progFg"));
            verInfo.setPgmYn((String)multi.getParameter("pgmYn"));
            verInfo.setImgYn((String)multi.getParameter("imgYn"));
            verInfo.setDbYn((String)multi.getParameter("dbYn"));
          
            if(String.valueOf(UseYn.Y) == multi.getParameter("useYn")){
                verInfo.setUseYn(UseYn.Y);
            } else {
                verInfo.setUseYn(UseYn.N);
            }

            verInfo.setRegDt(insertDt);
            verInfo.setRegId(sessionInfo.getUserId());
            verInfo.setModDt(insertDt);
            verInfo.setModId(sessionInfo.getUserId());
            
            mapper.verRegist(verInfo);
            
            isSuccess = true;
            
        }catch(Exception e){
            
            isSuccess = false;
        }
        return isSuccess;
    }
    

    @Override
    public boolean modify(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo) {

        //TODO 업로드랑 저장 됐는데 어디서 오류나는지 찾기
        boolean isSuccess = false;
        
        try{
            VerInfoVO verInfo = uploadFile(multi);
            
            String insertDt = currentDateTimeString();
            
            verInfo.setVerSerNo((String)multi.getParameter("verSerNo"));
            verInfo.setVerSerNm((String)multi.getParameter("verSerNm"));
            verInfo.setFileSize((String)multi.getParameter("fileSize"));
            verInfo.setFileDesc((String)multi.getParameter("fileDesc"));
            verInfo.setProgFg((String)multi.getParameter("progFg"));
            verInfo.setPgmYn((String)multi.getParameter("pgmYn"));
            verInfo.setImgYn((String)multi.getParameter("imgYn"));
            verInfo.setDbYn((String)multi.getParameter("dbYn"));
          
            if(String.valueOf(UseYn.Y) == multi.getParameter("useYn")){
                verInfo.setUseYn(UseYn.Y);
            } else {
                verInfo.setUseYn(UseYn.N);
            }

            verInfo.setRegDt(insertDt);
            verInfo.setRegId(sessionInfo.getUserId());
            verInfo.setModDt(insertDt);
            verInfo.setModId(sessionInfo.getUserId());

            mapper.verModify(verInfo);
            
            isSuccess = true;
            
        }catch(Exception e){
            
            isSuccess = false;
        }
        return isSuccess;

    }
    
    
    /**
     * 파일 업로드 
     * @param multi
     * @return
     */
    private VerInfoVO uploadFile(MultipartHttpServletRequest multi) {
        VerInfoVO verInfo = new VerInfoVO();

        // 저장 경로 설정
        String root = multi.getSession().getServletContext().getRealPath("/");
//        String path = root+"resources/upload/";
        String path = root+prop.fileUploadDir;
        

        String newFileName = ""; // 업로드 되는 파일명
         
        File dir = new File(path);
        if(!dir.isDirectory()){
            dir.mkdir();
        }
         
        Iterator<String> files = multi.getFileNames();
        while(files.hasNext()){
            String uploadFile = files.next();
                         
            MultipartFile mFile = multi.getFile(uploadFile);
            String fileName = mFile.getOriginalFilename();
            newFileName = System.currentTimeMillis()+"."
                    +fileName.substring(fileName.lastIndexOf(".")+1);
            
            verInfo.setFileNm(newFileName);
            verInfo.setFileDir(path);
            
            try {
                mFile.transferTo(new File(path+newFileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return verInfo;
    }
}
