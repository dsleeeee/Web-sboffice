package kr.co.solbipos.adi.board.library.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.system.BaseEnv;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.board.library.service.LibraryService;
import kr.co.solbipos.adi.board.library.service.LibraryVO;
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
 * @Class Name : LibraryServiceImpl.java
 * @Description : 부가서비스 > 자료실 > 자료실
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.03.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.03.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("libraryService")
@Transactional
public class LibraryServiceImpl implements LibraryService {
    private final LibraryMapper libraryMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public LibraryServiceImpl(LibraryMapper libraryMapper) {
        this.libraryMapper = libraryMapper;
    }

    /** 자료실 조회 */
    @Override
    public List<DefaultMap<Object>> getLibraryList(LibraryVO libraryVO, SessionInfoVO sessionInfoVO) {

        return libraryMapper.getLibraryList(libraryVO);
    }

    /** 자료실 상세 팝업 - 첨부파일 조회 */
    @Override
    public List<LibraryVO> getLibraryDetailAtchList(LibraryVO libraryVO, SessionInfoVO sessionInfoVO) {

        return libraryMapper.getLibraryDetailAtchList(libraryVO);
    }

    /** 자료실 신규등록,수정 팝업 - 자료실 조회 */
    @Override
    public List<DefaultMap<Object>> getLibraryInfoList(LibraryVO libraryVO, SessionInfoVO sessionInfoVO) {

        return libraryMapper.getLibraryInfoList(libraryVO);
    }

    /** 자료실 신규등록,수정 팝업 - 첨부파일 저장 */
    @Override
    public boolean getLibraryInfoAtchSave(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo) {

//        System.out.println("test1111");
        boolean isSuccess = false;

        try{

            // 업로드 파일 읽기
            LibraryVO libraryInfo = new LibraryVO();

            // 현재 일자
            String currentDt = currentDateTimeString();

            libraryInfo.setModDt(currentDt);
            libraryInfo.setModId(sessionInfo.getUserId());
            libraryInfo.setRegDt(currentDt);
            libraryInfo.setRegId(sessionInfo.getUserId());
            libraryInfo.setUserId(sessionInfo.getUserId());
            libraryInfo.setUserNm(sessionInfo.getUserNm());

            libraryInfo.setBoardCd((String)multi.getParameter("boardCd"));
            libraryInfo.setBoardSeqNo((String)multi.getParameter("boardSeqNo"));
            libraryInfo.setTitle((String)multi.getParameter("title"));

            // 신규
            if(String.valueOf(GridDataFg.INSERT).equals(multi.getParameter("status"))) {

                // 게시판 게시일련번호 조회(자동채번)
                String boardSeqNo = libraryMapper.getBoardBoardSeqNo(libraryInfo);
                libraryInfo.setBoardSeqNo(boardSeqNo);

                // 첨부파일 자료명 저장 insert
                libraryMapper.getLibraryInfoSaveInsert(libraryInfo);
            }
            // 수정
            else if(String.valueOf(GridDataFg.UPDATE).equals(multi.getParameter("status"))) {

                isSuccess = true;
                // 첨부파일 자료명 저장 update
                libraryMapper.getLibraryInfoSaveUpate(libraryInfo);
            }

            // 저장 경로 설정 (개발시 로컬)
//            String path = "D:\\Workspace\\javaWeb\\testBoardAtch\\";

            // 파일서버 대응 경로 지정 (운영)
            String path = BaseEnv.FILE_UPLOAD_DIR + "board/";

            // 업로드 되는 파일명
            String newFileName = "";
            // 원본 파일명
            String orgFileName="";

            // 경로에 폴도가 있는지 체크
            File dir = new File(path);
            if(!dir.isDirectory()){
                dir.mkdir();
            }

            List<MultipartFile> fileList = multi.getFiles("file");
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

                if(mFile.getOriginalFilename().lastIndexOf('.') > 1) {
                    // 파일경로
                    libraryInfo.setFilePath(path);
                    // 파일명 (물리적으로 저장되는 파일명)
                    libraryInfo.setFileNm(newFileName);
                    // 원본 파일명
                    libraryInfo.setOrginlFileNm(orgFileName);
                    // 파일확장자
                    libraryInfo.setFileExt(fileExt);

                    // 파일 저장하는 부분
                    try {
                        mFile.transferTo(new File(path+newFileName));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // 첨부파일 저장시 IDX (자동채번)
                    String idx = libraryMapper.getBoardAtchIdx(libraryInfo);
                    libraryInfo.setIdx(idx);

                    // 첨부파일 저장 isert
                    if(libraryMapper.getLibraryInfoAtchSaveIsert(libraryInfo) > 0) {
                        isSuccess = true;
                    } else {
                        isSuccess = false;
                    }
                }
            }

        }catch(Exception e){

            isSuccess = false;
        }
        return isSuccess;
    }

    /** 자료실 신규등록,수정 팝업 - 저장 */
    @Override
    public int getLibraryInfoSave(LibraryVO libraryVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        libraryVO.setModDt(currentDt);
        libraryVO.setModId(sessionInfoVO.getUserId());

        if (libraryVO.getStatus() == GridDataFg.DELETE) {

            procCnt = libraryMapper.getLibraryInfoSaveDelete(libraryVO);

//            libraryMapper.getLibraryInfoAtchSaveDelete(libraryVO);
        }

        return procCnt;
    }

    /** 자료실 첨부파일 삭제 */
    @Override
    public int getLibraryInfoAtchDel(LibraryVO libraryVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        libraryVO.setModDt(currentDt);
        libraryVO.setModId(sessionInfoVO.getUserId());

        procCnt = libraryMapper.getLibraryInfoAtchDel(libraryVO);

        return procCnt;
    }
}