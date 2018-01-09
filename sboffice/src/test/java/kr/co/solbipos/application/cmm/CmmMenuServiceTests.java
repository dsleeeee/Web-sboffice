package kr.co.solbipos.application.cmm;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.resource.ResrceInfoBase;
import kr.co.solbipos.application.persistance.cmm.CmmMenuMapper;
import kr.co.solbipos.service.cmm.CmmMenuServiceImpl;
import kr.co.solbipos.service.session.SessionService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CmmMenuServiceTests {

    @Mock
    SessionService sessionService;

    @Mock
    CmmMenuMapper cmmMenuMapper;

    @InjectMocks
    CmmMenuServiceImpl cmmMenuService;

    
    ResrceInfoBase rb;
    
    List<ResrceInfoBase> histMenu;
    List<ResrceInfoBase> fixMenu;
    
    SessionInfo sessionInfo;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        histMenu = new ArrayList<>();
        ResrceInfoBase histRb1 = new ResrceInfoBase();
        histRb1.setResrceCd("00003");
        histRb1.setPResrce("00000");
        histRb1.setActivation(true);
        histMenu.add(histRb1);
        
        ResrceInfoBase histRb2 = new ResrceInfoBase();
        histRb2.setResrceCd("00004");
        histRb2.setPResrce("00000");
        histMenu.add(histRb2);
        
        
        fixMenu = new ArrayList<>();
        ResrceInfoBase fixRb1 = new ResrceInfoBase();
        fixRb1.setResrceCd("00005");
        fixRb1.setPResrce("00000");
        fixMenu.add(fixRb1);
        
        ResrceInfoBase fixRb2 = new ResrceInfoBase();
        fixRb2.setResrceCd("00006");
        fixRb2.setPResrce("00000");
        fixMenu.add(fixRb2);
        
        
        sessionInfo = new SessionInfo();
        sessionInfo.setFixMenu(fixMenu);
        sessionInfo.setHistMenu(histMenu);
    }

    @Test
    public void checkActivationTest() {
        
        SessionInfo result = cmmMenuService.checkActivation("00005", sessionInfo);
        
        String resultCd = "";
        String type = "";
        int resultCnt = 0;
        
        List<ResrceInfoBase> hist = result.getHistMenu();
        
        for (int i = 0; i < hist.size(); i++) {
            ResrceInfoBase r = hist.get(i);
            if(r.isActivation()) {
                resultCnt++;
                resultCd = r.getResrceCd();
                type = "hist";
            }
        }
        
        List<ResrceInfoBase> fix = result.getFixMenu();
        
        for (int i = 0; i < fix.size(); i++) {
            ResrceInfoBase r = fix.get(i);
            if(r.isActivation()) {
                resultCnt++;
                resultCd = r.getResrceCd();
                type = "fix";
            }
        }
        
        assertTrue(resultCnt == 1);
        assertTrue(resultCd.equals("00005"));
        
        log.info("cd : {}, type : {}", resultCd, type);
    }

}









