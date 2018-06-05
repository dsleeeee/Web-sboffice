package kr.co.solbipos.sys.service.auth.authgroup;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.transaction.annotation.Transactional;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.auth.authgroup.service.AuthorGrpResrceVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthGroupServceTest {

    List<DefaultMap<String>> list;
    List<DefaultMap<String>> authed;

    @Before
    public void init() {
        list = new ArrayList<DefaultMap<String>>();
        DefaultMap<String> map = new DefaultMap<String>();
        map.put("level", "1");
        map.put("pResrce", "000000");
        map.put("resrceCd", "000001");
        map.put("resrceNm", "포스관리");
        map.put("dispIdx", "0");
        list.add(map);

        map = new DefaultMap<String>();
        map.put("level", "2");
        map.put("pResrce", "000001");
        map.put("resrceCd", "000012");
        map.put("resrceNm", "POS 설정관리");
        map.put("dispIdx", "0");
        list.add(map);

        map = new DefaultMap<String>();
        map.put("level", "3");
        map.put("pResrce", "000012");
        map.put("resrceCd", "000069");
        map.put("resrceNm", "POS 기능 정의");
        map.put("dispIdx", "0");
        list.add(map);

        map = new DefaultMap<String>();
        map.put("level", "4");
        map.put("pResrce", "000069");
        map.put("resrceCd", "000335");
        map.put("resrceNm", "조회");
        map.put("dispIdx", "");
        list.add(map);

        map = new DefaultMap<String>();
        map.put("level", "4");
        map.put("pResrce", "000069");
        map.put("resrceCd", "000336");
        map.put("resrceNm", "저장");
        map.put("dispIdx", "");
        list.add(map);

        map = new DefaultMap<String>();
        map.put("level", "3");
        map.put("pResrce", "000012");
        map.put("resrceCd", "000070");
        map.put("resrceNm", "POS 버전 관리");
        map.put("dispIdx", "1");
        list.add(map);
        map = new DefaultMap<String>();
        map.put("level", "4");
        map.put("pResrce", "000070");
        map.put("resrceCd", "000341");
        map.put("resrceNm", "적용매장-조회");
        map.put("dispIdx", "1");
        list.add(map);

        map.put("level", "1");
        map.put("pResrce", "000000");
        map.put("resrceCd", "000003");
        map.put("resrceNm", "시스템관리");
        map.put("dispIdx", "2");
        list.add(map);

        //System.out.println( list.toString() );
        /*
        HashMap<String, DefaultMap<String>> data = new HashMap<String, DefaultMap<String>>();
        for(DefaultMap<String> rm : list) {
            data.put(rm.getStr("resrceCd"), rm);
        }
        //System.out.println( data.toString() );
        */
        authed = new ArrayList<DefaultMap<String>>();
        map = new DefaultMap<String>();
        map.put("resrceCd", "000335");
        map.put("resrcePath", "/000001/000012/000069/000335");
        authed.add(map);
        map = new DefaultMap<String>();
        map.put("resrceCd", "000336");
        map.put("resrcePath", "/000001/000012/000069/000336");
        authed.add(map);
    }

    /**
     * mxGraph XML 파싱 테스트
     *
     */
    @Test
    //@Ignore
    //@Rollback(false)
    public void test_100() {
        makeTreeData(list, authed);
    }

    private static List<AuthorGrpResrceVO> makeTreeData(List<DefaultMap<String>> list, List<DefaultMap<String>> authedList) {
        List<AuthorGrpResrceVO> input = new ArrayList<AuthorGrpResrceVO>();
        AuthorGrpResrceVO authorGrpResrceVO = new AuthorGrpResrceVO();
        for(DefaultMap<String> rm : list) {
            authorGrpResrceVO = new AuthorGrpResrceVO();
            authorGrpResrceVO.setResrceCd(rm.getStr("resrceCd"));
            authorGrpResrceVO.setPResrce(rm.getStr("pResrce"));
            authorGrpResrceVO.setResrceNm(rm.getStr("resrceNm"));
            authorGrpResrceVO.setItems(new ArrayList<AuthorGrpResrceVO>());
            input.add(authorGrpResrceVO);
        }

        String resrcePath = "";
        String[] arrayRes;
        DefaultMap<String> authedResrce = new DefaultMap<String>();
        for(DefaultMap<String> item : authedList) {
            resrcePath = item.getStr("resrcePath").substring(1);
            arrayRes = resrcePath.split("\\/");
            for(String res : arrayRes) {
                authedResrce.put(res, "");
            }
        }
        log.debug(authedResrce.toString());

        Map<String, AuthorGrpResrceVO> hm = new LinkedHashMap<String, AuthorGrpResrceVO>();
        AuthorGrpResrceVO child;
        AuthorGrpResrceVO mmdParent;

        for(AuthorGrpResrceVO item : input) {
            if( !hm.containsKey(item.getResrceCd()) && authedResrce.containsKey(item.getResrceCd()) ) {
                hm.put(item.getResrceCd(), item);
            }
            child = hm.get(item.getResrceCd());

            if(!item.getPResrce().equals("") && !item.getPResrce().equals("000000")) {
                if(hm.containsKey(item.getPResrce())) {
                    mmdParent = hm.get(item.getPResrce());
                    mmdParent.getItems().add(child);
                }
            }

        }
        //System.out.println( hm.toString() );

        List<AuthorGrpResrceVO> retrunData = new ArrayList<AuthorGrpResrceVO>();
        for (AuthorGrpResrceVO mmd : hm.values()) {
            if (mmd.getPResrce() == null || mmd.getPResrce().equals("")
                    || mmd.getPResrce().equals("000000")) {
                System.out.println( mmd.toString() );
                retrunData.add(mmd);
            }
            //returnMap.put(key, value)
        }
        //retrunData.add(hm);
        //System.out.println( retrunData.toString() );
        return retrunData;
    }

}
