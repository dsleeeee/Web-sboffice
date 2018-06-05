package kr.co.common.service.cmm;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentTimeMsString;
import static org.springframework.util.StringUtils.isEmpty;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.Prop;
import kr.co.solbipos.application.common.service.HqVO;
import kr.co.solbipos.application.common.service.MenuUseHistVO;
import kr.co.solbipos.application.common.service.MenuVO;
import kr.co.solbipos.application.common.service.ResrceInfoBaseVO;
import kr.co.solbipos.application.common.service.ResrceInfoVO;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.common.service.impl.CmmCodeMapper;
import kr.co.solbipos.application.common.service.impl.CmmMenuMapper;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CmmMenuServiceImpl implements CmmMenuService {

    @Autowired
    SessionService sessionService;

    @Autowired
    CmmMenuMapper cmmMenuMapper;

    @Autowired
    CmmCodeMapper cmmCodeMapper;

    @Autowired
    Prop prop;

    /**
     *
     * 레이어 팝업 매장 조회
     *
     */

    @Override
    public List<StoreVO> selectStore( StoreVO storeVO ) {
        return cmmMenuMapper.selectStore( storeVO );
    }

    /**
     * 레이어 팝업 본사 조회
     */
    @Override
    public List<HqVO> selectHq(HqVO hqVO) {
        return cmmMenuMapper.selectHq(hqVO);
    }
    

    /**
     *
     * 메뉴 디비 작업 관련
     *
     */


    @Override
    public int insertMenuUseHist( MenuUseHistVO menuUseHistVO ) {
        return cmmMenuMapper.insertMenuUseHist( menuUseHistVO );
    }

    @Override
    public List<ResrceInfoBaseVO> selectBkmkMenu( SessionInfoVO sessionInfoVO ) {
        return cmmMenuMapper.selectBkmkMenu( sessionInfoVO );
    }

    @Override
    public List<ResrceInfoBaseVO> selectFixingMenu( SessionInfoVO sessionInfoVO ) {
        return cmmMenuMapper.selectFixingMenu( sessionInfoVO );
    }


    /**
     *
     * 즐겨 찾기 메뉴 관리
     *
     */


    @Override
    public int insertMenuUseHist( ResrceInfoVO resrceInfoVO, SessionInfoVO sessionInfoVO ) {
        MenuUseHistVO menuUseHistVO = new MenuUseHistVO();
        menuUseHistVO.setUserId( sessionInfoVO.getUserId() );
        menuUseHistVO.setResrceCd( resrceInfoVO.getResrceCd() );
        menuUseHistVO.setUseDate( currentDateString() );
        menuUseHistVO.setUseDt( currentTimeMsString() );
        return insertMenuUseHist( menuUseHistVO );
    }

    @Override
    public void addHistMenu( ResrceInfoVO resrceInfoVO, SessionInfoVO sessionInfoVO ) {
        // 추가 할 리소스 생성 및 세팅
        ResrceInfoBaseVO resrceInfoBaseVO = new ResrceInfoBaseVO();
        resrceInfoBaseVO.setResrceCd( resrceInfoVO.getResrceCd() );
        resrceInfoBaseVO.setPResrce( resrceInfoVO.getPResrce() );
        resrceInfoBaseVO.setResrceNm( resrceInfoVO.getResrceNm() );
        resrceInfoBaseVO.setUrl( resrceInfoVO.getUrl() );
        addHistMenu( resrceInfoBaseVO, sessionInfoVO );
    }

    @Override
    public SessionInfoVO addHistMenu( ResrceInfoBaseVO resrceInfoBaseVO,
            SessionInfoVO sessionInfoVO ) {
        String resrceCd = resrceInfoBaseVO.getResrceCd(); // 추가 할려는 리소스
        List<ResrceInfoBaseVO> histMenu = sessionInfoVO.getHistMenu(); // 히스토리 메뉴
        List<ResrceInfoBaseVO> fixMenu = sessionInfoVO.getFixMenu(); // 고정 메뉴

        // 고정 메뉴에 선택한 메뉴가 있는지 체크 > 추가하지 않고 고정메뉴 활성화
        int fSize = fixMenu.size();
        if ( fSize > 0 ) {
            for ( int i = 0; i < fSize; i++ ) {
                ResrceInfoBaseVO rb = fixMenu.get( i );
                // 같은게 있으면 히스토리에 추가하지 않고 해당 고정 메뉴 활성화 하고 세션 저장
                if ( rb.getResrceCd().equals( resrceCd ) ) {
                    sessionInfoVO.setFixMenu( fixMenu );
                    return checkActivation( resrceCd, sessionInfoVO );
                }
            }
        }

        if ( isEmpty( histMenu ) ) {
            histMenu = new ArrayList<>();
        }
        // 기존 히스토리 메뉴 리스트에 있는지 체크 > 지우고 젤 뒤에 추가
        int hSize = histMenu.size();
        if ( hSize == 0 ) {
            histMenu.add( resrceInfoBaseVO );
            sessionInfoVO.setHistMenu( histMenu );
            return checkActivation( resrceCd, sessionInfoVO );
        } else {
            for ( int i = 0; i < hSize; i++ ) {
                ResrceInfoBaseVO rb = histMenu.get( i );
                // 히스토리 메뉴에 같은게 있으면 지우고 젤 뒤에 추가
                if ( rb.getResrceCd().equals( resrceCd ) ) {
                    histMenu.remove( i );
                    histMenu.add( resrceInfoBaseVO );
                    sessionInfoVO.setHistMenu( histMenu );
                    return checkActivation( resrceCd, sessionInfoVO );
                }
                // 마지막 까지 같은게 없으면
                if ( i == hSize - 1 ) {
                    // 히스토리 리소스 갯수가 세팅한 갯수를 넘어가면 지우고 추가함
                    if ( hSize >= prop.sessionHistMenuSize ) {
                        histMenu.remove( 0 ); // 첫번째꺼를 지운다.
                        histMenu.add( resrceInfoBaseVO );
                    }
                    // 세팅한 갯수를 넘어가지 않으면 바로 추가
                    else {
                        histMenu.add( resrceInfoBaseVO );
                    }
                    sessionInfoVO.setHistMenu( histMenu );
                    return checkActivation( resrceCd, sessionInfoVO );
                }
            }
        }
        return sessionInfoVO;
    }

    @Override
    public SessionInfoVO checkActivation( String resrceCd, SessionInfoVO sessionInfoVO ) {
        List<ResrceInfoBaseVO> histMenu = sessionInfoVO.getHistMenu(); // 히스토리 메뉴
        List<ResrceInfoBaseVO> fixMenu = sessionInfoVO.getFixMenu(); // 고정 메뉴

        if ( !isEmpty( fixMenu ) && fixMenu.size() > 0 ) {
            for ( ResrceInfoBaseVO resrceInfoBaseVO : fixMenu ) {
                if ( resrceInfoBaseVO.getResrceCd().equals( resrceCd ) ) {
                    resrceInfoBaseVO.setActivation( true );
                    sessionInfoVO.setCurrentMenu( resrceInfoBaseVO ); // 현재 메뉴 정보 등록
                } else {
                    resrceInfoBaseVO.setActivation( false );
                }
            }
        }

        if ( !isEmpty( histMenu ) && histMenu.size() > 0 ) {
            for ( ResrceInfoBaseVO resrceInfoBaseVO : histMenu ) {
                if ( resrceInfoBaseVO.getResrceCd().equals( resrceCd ) ) {
                    resrceInfoBaseVO.setActivation( true );
                    sessionInfoVO.setCurrentMenu( resrceInfoBaseVO ); // 현재 메뉴 정보 등록
                } else {
                    resrceInfoBaseVO.setActivation( false );
                }
            }
        }

        sessionInfoVO.setFixMenu( fixMenu );
        sessionInfoVO.setHistMenu( histMenu );

        // 레디스에 수정한 세션정보를 저장
        sessionService.setSessionInfo( sessionInfoVO );

        return sessionInfoVO;
    }

    @Override
    public void deleteHistMenu( String resrceCd, SessionInfoVO sessionInfoVO ) {
        List<ResrceInfoBaseVO> histMenu = sessionInfoVO.getHistMenu(); // 히스토리 메뉴
        if ( !isEmpty( histMenu ) ) {
            int size = histMenu.size();
            for ( int i = 0; i < size; i++ ) {
                ResrceInfoBaseVO r = histMenu.get( i );
                if ( r.getResrceCd().equals( resrceCd ) ) {
                    histMenu.remove( i );
                    break;
                }
            }
            sessionInfoVO.setHistMenu( histMenu );
            checkActivation( resrceCd, sessionInfoVO );
        }
    }

    @Override
    public void deleteFixMenu( String resrceCd, SessionInfoVO sessionInfoVO ) {
        List<ResrceInfoBaseVO> fixMenu = sessionInfoVO.getFixMenu();
        if ( !isEmpty( fixMenu ) ) {
            int size = fixMenu.size();
            for ( int i = 0; i < size; i++ ) {
                ResrceInfoBaseVO r = fixMenu.get( i );
                if ( r.getResrceCd().equals( resrceCd ) ) {
                    fixMenu.remove( i );
                    break;
                }
            }
            sessionInfoVO.setFixMenu( fixMenu );
            checkActivation( resrceCd, sessionInfoVO );
        }
    }

    @Override
    public List<MenuVO> makeMenu( SessionInfoVO sessionInfoVO, String menuType ) {

        DefaultMap<String> map = new DefaultMap<String>();

        map.put( "grpCd", sessionInfoVO.getGrpCd() );
        map.put( "userId", sessionInfoVO.getUserId() );

        // Tree 생성을 위한 모든 리소스(Menu 포함) 조회
        // 로그인 아이디의 그룹 기준 권한이 있는 것 체크
        List<DefaultMap<String>> list = cmmMenuMapper.selectAllResrce( map );

        // 로그인 아이디 기준 권한이 있는 기능의 탐색 경로 조회
        // Tree의 기준 데이터에서 권한이 없는 리소스를 빼기 위해 사용
        List<DefaultMap<String>> authedList = new ArrayList<DefaultMap<String>>();
        if ( "A".equals( menuType ) ) {
            authedList = cmmMenuMapper.selectAuthedResrce( map );
        } else if ( "F".equals( menuType ) ) {
            authedList = cmmMenuMapper.selectAuthedBkmk( map );
        }

        return makeTreeData( list, authedList );

    }


    @Override
    public List<String> selectStoreCdList( String hqOfficeCd ) {
        return cmmMenuMapper.selectStoreCdList( hqOfficeCd );
    }

    @Override
    public List<MenuVO> makeTreeData( List<DefaultMap<String>> list,
            List<DefaultMap<String>> authedList ) {

        List<MenuVO> menuVOs = new ArrayList<MenuVO>();
        MenuVO menu = new MenuVO();
        for ( DefaultMap<String> item : list ) {
            menu = new MenuVO();
            menu.setCd( item.getStr( "cd" ) );
            menu.setPcd( item.getStr( "pcd" ) );
            menu.setNm( item.getStr( "nm" ) );
            menu.setUrl( item.getStr( "url" ) );
            menu.setIcon( item.getStr( "icon" ) );
            menu.setItems( new ArrayList<MenuVO>() );
            menuVOs.add( menu );
        }
        // 권한이 있는 기능에서 상위 리소스 정보 생성
        // 상위 리소스 맵에 없는 리소스는 권한이 없는 것으로 판단.
        DefaultMap<String> authedResrce = new DefaultMap<String>();
        if ( authedList != null && authedList.size() > 0 ) {
            String resrcePath = "";
            String[] arrayRes;
            for ( DefaultMap<String> item : authedList ) {
                resrcePath = item.getStr( "resrcePath" ).substring( 1 );
                arrayRes = resrcePath.split( "\\/" );
                for ( String res : arrayRes ) {
                    authedResrce.put( res, "" );
                }
            }
            log.debug( authedResrce.toString() );
        }

        Map<String, MenuVO> hm = new LinkedHashMap<String, MenuVO>();
        MenuVO child;
        MenuVO mmdParent;

        if ( authedResrce.size() > 0 ) {
            for ( MenuVO menuVO : menuVOs ) {
                if ( !hm.containsKey( menuVO.getCd() ) ) {
                    if ( authedResrce.size() > 0 ) {
                        if ( authedResrce.containsKey( menuVO.getCd() ) ) {
                            hm.put( menuVO.getCd(), menuVO );
                        }
                    } else {
                        hm.put( menuVO.getCd(), menuVO );
                    }
                }
                child = hm.get( menuVO.getCd() );
                // child 가 null인 경우에는 list 에 담지 않는다 : 화면에서 표시할때 오류생김 - 20180509 노현수
                if ( child != null && !"".equals( menuVO.getPcd() ) && !"000000".equals( menuVO.getPcd() ) ) {
                    if ( hm.containsKey( menuVO.getPcd() ) ) {
                        mmdParent = hm.get( menuVO.getPcd() );
                        mmdParent.getItems().add( child );
                    }
                }
            }
            // log.debug( hm.toString() );
        }

        List<MenuVO> retrunData = new ArrayList<MenuVO>();
        for ( MenuVO mmd : hm.values() ) {
            if ( mmd.getPcd() == null || "".equals( mmd.getPcd() )
                    || "000000".equals( mmd.getPcd() ) ) {
                retrunData.add( mmd );
            }
        }
        log.debug( retrunData.toString() );
        return retrunData;
    }

}


