package kr.co.solbipos.util.test;

import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import kr.co.solbipos.utils.DateUtil;
import kr.co.solbipos.utils.jsp.CmmCodeUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests {

    @Before
    public void init() {
    }

    @Test
    public void test100() throws ParseException {
        
        String date = "20180123";
        String time = "103523";
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date d = dateFormat.parse(date+time);
        Date dd = DateUtils.addMinutes(d, 3);
        
        Date c = new Date();
        Date cc = DateUtils.addMinutes(c, -5);
        
        if(c.getTime() > cc.getTime()) {
            
        }
        
        assertTrue(true);
    }

}


