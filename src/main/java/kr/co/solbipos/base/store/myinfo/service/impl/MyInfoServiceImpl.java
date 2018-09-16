package kr.co.solbipos.base.store.myinfo.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.exception.BizException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.ColumnLayout;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.myinfo.enums.NmcodeGrpCd;
import kr.co.solbipos.base.store.myinfo.enums.WijmoGridDataType;
import kr.co.solbipos.base.store.myinfo.service.MyInfoService;
import kr.co.solbipos.base.store.myinfo.service.MyInfoVO;
import kr.co.solbipos.base.store.myinfo.service.WijmoGridColumnVO;
import kr.co.solbipos.base.store.myinfo.service.WijmoGridVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqNmcodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private MyInfoMapper mapper;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ColumnLayout layout;

    private String getHqOfficeCd( SessionInfoVO info ){
        return info.getOrgnCd();
    }

    private SessionInfoVO getSessionInfo(){
        return sessionService.getSessionInfo();
    }

    @Override
    public WijmoGridVO< HqNmcodeVO > getGridInfo( NmcodeGrpCd nmcodeGrpCd ){
        String id = "",
                name = getSessionInfo().getCurrentMenu().getResrceCd();
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

        List< HqNmcodeVO > hqNmcodes = mapper.findAllHqNmcodeByHqOfficeCdAndNmcodeGrpCd( hqNmcode );

        if( !hqNmcodes.isEmpty() ){
            return hqNmcodes;
        }
        //없는 경우 공통코드에서 본사코드로 insert 해야 함
        mapper.saveHqNmcodeFromCmNmcode( hqNmcode );

        return mapper.findAllHqNmcodeByHqOfficeCdAndNmcodeGrpCd( hqNmcode );
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
            isEmpty(findHqNmcode = mapper.findHqNmcodeByHqOfficeCdAndNmcodeGrpCdAndNmcodeCd(hqNmcode)) ){
            return mapper.insertHqNmcode( hqNmcode ) == 1 ? OK : FAIL;
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

            return mapper.updateHqNmcode( hqNmcode ) == 1 ? OK : FAIL;
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

        return mapper.updateHqNmcode( hqNmcode ) == 1 ? OK : FAIL;
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

        return mapper.deleteHqNmcode( hqNmcode ) == 1 ? OK : FAIL;
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
        return mapper.findById( getHqOfficeCd(getSessionInfo()) );
    }

    @Override
    public Status modifyMyInfo( MyInfoVO myInfo ){
        SessionInfoVO sessionInfo = getSessionInfo();
        String hqOfficeCd = getHqOfficeCd( sessionInfo ),
                modId = sessionInfo.getUserId(),
                modDt = currentDateTimeString();

        myInfo.setHqOfficeCd( hqOfficeCd );
        myInfo.setModId( modId );
        myInfo.setModDt( modDt );

        return mapper.updateHqOffice( myInfo ) == 1 ? OK : FAIL;
    }
}
