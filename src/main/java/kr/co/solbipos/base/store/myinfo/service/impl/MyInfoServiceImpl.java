package kr.co.solbipos.base.store.myinfo.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.exception.BizException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.jsp.ColumnLayout;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.myinfo.enums.NmcodeGrpCd;
import kr.co.solbipos.base.store.myinfo.enums.WijmoGridDataType;
import kr.co.solbipos.base.store.myinfo.service.MyInfoService;
import kr.co.solbipos.base.store.myinfo.service.MyInfoVO;
import kr.co.solbipos.base.store.myinfo.service.WijmoGridColumnVO;
import kr.co.solbipos.base.store.myinfo.service.WijmoGridVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqNmcodeVO;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.List;

import static java.util.Arrays.asList;
import static kr.co.common.data.enums.Status.FAIL;
import static kr.co.common.data.enums.Status.OK;
import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.solbipos.base.store.myinfo.enums.NmcodeGrpCd.*;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @Class Name : MyInfoServiceImpl.java
 * @Description : 기초관리 > 매장관리 > 내정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.07.27  이호원      최초생성
 *
 * @author NHN한국사이버결제 이호원
 * @since 2018.07.27
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service
@Transactional
public class MyInfoServiceImpl implements MyInfoService{

    private final MyInfoMapper myInfoMapper;
    private final SessionService sessionService;
    private final MessageService messageService;
    private final ColumnLayout layout;

    /** Constructor Injection */
    @Autowired
    public MyInfoServiceImpl(MyInfoMapper myInfoMapper, SessionService sessionService, MessageService messageService, ColumnLayout layout) {
        this.myInfoMapper = myInfoMapper;
        this.sessionService = sessionService;
        this.messageService = messageService;
        this.layout = layout;
    }

    private String getHqOfficeCd( SessionInfoVO info ){
        return info.getOrgnCd();
    }

    private SessionInfoVO getSessionInfo(){
        return sessionService.getSessionInfo();
    }

    @Override
    public WijmoGridVO< HqNmcodeVO > getGridInfo( NmcodeGrpCd nmcodeGrpCd ){
        String id = "";
        String name = getSessionInfo().getCurrentMenu().getResrceCd();

        Long index = -1L;
        List< WijmoGridColumnVO > columns = null;
        WijmoGridColumnVO checkColumn =
                new WijmoGridColumnVO( "gChk", messageService.get("cmm.chk"), WijmoGridDataType.Boolean, 40);

        if( nmcodeGrpCd == STORE_TYPE ){
            id = "storeType";
            columns = asList( checkColumn,
                              getGridColumn( id, "nmcodeCd", WijmoGridDataType.Number, 70),
                              getGridColumn( id, "nmcodeNm", WijmoGridDataType.String, "*"),
                              getGridColumn( id, "nmcodeItem1", WijmoGridDataType.String, "*") );
            index = 1L;
        }
        else if( nmcodeGrpCd == GRP ){
            id = "grp";
            columns = asList( checkColumn,
                              getGridColumn( id, "nmcodeCd", WijmoGridDataType.Number, "*"),
                              getGridColumn( id, "nmcodeNm", WijmoGridDataType.String, "*") );
            index = 2L;
        }
        else if( nmcodeGrpCd == HOUR ){
            id = "hour";
            columns = asList( checkColumn,
                              getGridColumn( id, "nmcodeNm", WijmoGridDataType.String, "*"),
                              getGridColumn( id, "nmcodeItem1", WijmoGridDataType.String, "*"),
                              getGridColumn( id, "nmcodeItem2", WijmoGridDataType.String, "*") );
            index = 3L;
        }
        else if( nmcodeGrpCd == GUEST ){
            id = "guest";
            columns = asList( checkColumn,
                              getGridColumn( id, "nmcodeNm", WijmoGridDataType.String, 150) );
            index = 4L;
        }

        return new WijmoGridVO<>( id, name, columns, index, layout.getColumnLayout(index), nmcodeList(nmcodeGrpCd) );
    }

    private WijmoGridColumnVO getGridColumn( String id, String column, WijmoGridDataType dataType, Object width ){
        String messageCode = "myInfo." + id + "." + column;

        return new WijmoGridColumnVO( column, messageService.get(messageCode), dataType, width );
    }

    @Override
    public List< HqNmcodeVO > nmcodeList( NmcodeGrpCd nmcodeGrpCd ){
        HqNmcodeVO hqNmcode = new HqNmcodeVO();

        hqNmcode.setHqOfficeCd( getHqOfficeCd(getSessionInfo()) );
        hqNmcode.setNmcodeGrpCd( nmcodeGrpCd.getCode() );

        List< HqNmcodeVO > hqNmcodes = myInfoMapper.findAllHqNmcodeByHqOfficeCdAndNmcodeGrpCd( hqNmcode );

        if( !hqNmcodes.isEmpty() ){
            return hqNmcodes;
        }
        //없는 경우 공통코드에서 본사코드로 insert 해야 함
        myInfoMapper.saveHqNmcodeFromCmNmcode( hqNmcode );

        return myInfoMapper.findAllHqNmcodeByHqOfficeCdAndNmcodeGrpCd( hqNmcode );
    }

    @Override
    public Status addNmcode( HqNmcodeVO hqNmcode ){
        SessionInfoVO sessionInfo = getSessionInfo();
        String hqOfficeCd = getHqOfficeCd( sessionInfo ),
                regId = sessionInfo.getUserId(),
                regDt = currentDateTimeString();

        hqNmcode.setHqOfficeCd( hqOfficeCd );
        hqNmcode.setUseYn( UseYn.Y );
        hqNmcode.setRegId( regId );
        hqNmcode.setRegDt( regDt );
        hqNmcode.setModId( regId );
        hqNmcode.setModDt( regDt );

        HqNmcodeVO findHqNmcode = null;
        //코드를 직접 등록하는 경우에는 중복 체크해야 함
        if( isEmpty(hqNmcode.getNmcodeCd()) ||
            isEmpty(findHqNmcode = myInfoMapper.findHqNmcodeByHqOfficeCdAndNmcodeGrpCdAndNmcodeCd(hqNmcode)) ){
            return myInfoMapper.insertHqNmcode( hqNmcode ) == 1 ? OK : FAIL;
        }

        if( UseYn.N == findHqNmcode.getUseYn() ){
            //미사용중인 코드는 빈 값으로 처리해야 함 ( 초기화 )
            if( hqNmcode.getNmcodeNm() == null ){
                hqNmcode.setNmcodeNm( "" );
            }

            if( hqNmcode.getNmcodeItem1() == null ){
                hqNmcode.setNmcodeItem1( "" );
            }

            if( hqNmcode.getNmcodeItem2() == null ){
                hqNmcode.setNmcodeItem2( "" );
            }

            return myInfoMapper.updateHqNmcode( hqNmcode ) == 1 ? OK : FAIL;
        }

        throw new BizException( FAIL, messageService.get("cmm.saveFail") );
    }

    @Override
    public Status modifyNmcode( HqNmcodeVO hqNmcode ){
        SessionInfoVO sessionInfo = getSessionInfo();
        String hqOfficeCd = getHqOfficeCd( sessionInfo ),
                modId = sessionInfo.getUserId(),
                modDt = currentDateTimeString();

        hqNmcode.setHqOfficeCd( hqOfficeCd );
        hqNmcode.setModId( modId );
        hqNmcode.setModDt( modDt );

        return myInfoMapper.updateHqNmcode( hqNmcode ) == 1 ? OK : FAIL;
    }

    @Override
    public Status removeNmcode( HqNmcodeVO hqNmcode ){
        SessionInfoVO sessionInfo = getSessionInfo();
        String hqOfficeCd = getHqOfficeCd( sessionInfo ),
                modId = sessionInfo.getUserId(),
                modDt = currentDateTimeString();

        //hqNmcode.setNmcodeGrpCd( nmcodeGrpCd );
        //hqNmcode.setNmcodeCd( nmcodeCd );
        hqNmcode.setHqOfficeCd( hqOfficeCd );
        hqNmcode.setModId( modId );
        hqNmcode.setModDt( modDt );

        return myInfoMapper.deleteHqNmcode( hqNmcode ) == 1 ? OK : FAIL;
    }

    @Override
    public Status saveMultipleNmcode( List<HqNmcodeVO> addList, List<HqNmcodeVO> modList, List<HqNmcodeVO> delList,
                                       NmcodeGrpCd nmcodeGrpCd ){
        //하나라도 실패하면 rollback 되도록 처리해야함
        addList.forEach( hqNmcode -> {
            hqNmcode.setNmcodeGrpCd( nmcodeGrpCd.getCode() );
            addNmcode( hqNmcode );
        });
        modList.forEach( hqNmcode -> {
            hqNmcode.setNmcodeGrpCd( nmcodeGrpCd.getCode() );
            modifyNmcode( hqNmcode );
        });
        delList.forEach( hqNmcode -> {
            hqNmcode.setNmcodeGrpCd( nmcodeGrpCd.getCode() );
            removeNmcode( hqNmcode );
        });

        return OK;
    }

    @Override
    public MyInfoVO getMyInfo(){
        return myInfoMapper.findById( getHqOfficeCd(getSessionInfo()) );
    }

    @Override
    public Status modifyMyInfo( MyInfoVO myInfo ){
        SessionInfoVO sessionInfo = getSessionInfo();
        String hqOfficeCd = getHqOfficeCd( sessionInfo ),
                modId = sessionInfo.getUserId(),
                modDt = currentDateTimeString();

        //myInfo.setHqOfficeCd( hqOfficeCd );
        myInfo.setModId( modId );
        myInfo.setModDt( modDt );

        return myInfoMapper.updateHqOffice( myInfo ) == 1 ? OK : FAIL;
    }

    @Override
    public String saveTitleImg(MultipartHttpServletRequest multi) {

        String isSuccess = "";

        try{

            // 서버용 - 개발/운영 서버에 반영할 진짜 경로!!!!!!
            String path = BaseEnv.FILE_UPLOAD_DIR + "logo_img/";

            // 로컬 테스트용 - 로컬에서 파일 업로드가 잘되는지 확인하기 위해 임의로 설정한 경로
//            String path = "D:\\logo_img\\";

            // 경로에 폴더가 있는지 체크 없으면 생성
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

            List<MultipartFile> fileList = multi.getFiles("file");
            for(MultipartFile mFile : fileList)
            {
                newFileName = multi.getParameter("newFileName"); // 파일명 (물리적으로 저장되는 파일명)
                orgFileName = mFile.getOriginalFilename();
                fileExt = FilenameUtils.getExtension(orgFileName);

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

                if(!fileExt.equals("png") && !fileExt.equals("PNG") && !fileExt.equals("jpg") && !fileExt.equals("JPG"))
                {
                    isSuccess = "3";
                    break;
                }

                if(mFile.getOriginalFilename().lastIndexOf('.') > 0) { // 파일명 최소 한글자 이상은 되어야함.
                    // 파일 저장하는 부분
                    try {
                        File destFile = new File(path + newFileName);
                        mFile.transferTo(destFile);
                        isSuccess = "0";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }catch(Exception e){
            isSuccess = "1";
        }
        return isSuccess;
    }
}
