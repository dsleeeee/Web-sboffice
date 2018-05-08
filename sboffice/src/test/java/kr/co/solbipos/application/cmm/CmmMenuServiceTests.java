package kr.co.solbipos.application.cmm;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.runners.MockitoJUnitRunner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CmmMenuServiceTests {
/*
    @Mock
    SessionService sessionService;

    @Mock
    CmmMenuMapper cmmMenuMapper;

    @InjectMocks
    CmmMenuServiceImpl cmmMenuService;


    resrceInfoBaseVO rb;

    List<resrceInfoBaseVO> histMenu;
    List<resrceInfoBaseVO> fixMenu;

    SessionInfoVO sessionInfoVO;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        histMenu = new ArrayList<>();
        resrceInfoBaseVO histRb1 = new resrceInfoBaseVO();
        histRb1.setResrceCd("00003");
        histRb1.setPResrce("00000");
        histRb1.setActivation(true);
        histMenu.add(histRb1);

        resrceInfoBaseVO histRb2 = new resrceInfoBaseVO();
        histRb2.setResrceCd("00004");
        histRb2.setPResrce("00000");
        histMenu.add(histRb2);


        fixMenu = new ArrayList<>();
        resrceInfoBaseVO fixRb1 = new resrceInfoBaseVO();
        fixRb1.setResrceCd("00005");
        fixRb1.setPResrce("00000");
        fixMenu.add(fixRb1);

        resrceInfoBaseVO fixRb2 = new resrceInfoBaseVO();
        fixRb2.setResrceCd("00006");
        fixRb2.setPResrce("00000");
        fixMenu.add(fixRb2);


        sessionInfoVO = new sessionInfoVO();
        sessionInfoVO.setFixMenu(fixMenu);
        sessionInfoVO.setHistMenu(histMenu);
    }

    @Test
    public void checkActivationTest() {

        sessionInfoVO result = cmmMenuService.checkActivation("00005", sessionInfoVO);

        String resultCd = "";
        String type = "";
        int resultCnt = 0;

        List<resrceInfoBaseVO> hist = result.getHistMenu();

        for (int i = 0; i < hist.size(); i++) {
            resrceInfoBaseVO r = hist.get(i);
            if(r.isActivation()) {
                resultCnt++;
                resultCd = r.getResrceCd();
                type = "hist";
            }
        }

        List<resrceInfoBaseVO> fix = result.getFixMenu();

        for (int i = 0; i < fix.size(); i++) {
            resrceInfoBaseVO r = fix.get(i);
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
*/
}









