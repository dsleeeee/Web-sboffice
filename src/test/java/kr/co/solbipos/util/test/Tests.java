package kr.co.solbipos.util.test;

import static org.junit.Assert.assertTrue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests {
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    
    @Before
    public void init() {
    }

    @Test
    @Ignore
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

    
    @Test
    public void test200() {
        List<String> list = new ArrayList<String> ();
        list.add("S000001");
        list.add("S000002");
        list.add("S000003");
        
        String[] arr = {"S000001", "S000001", "S000002", "S000002", "S000003"};
        
        boolean check = true;
        
        int i=0;
        for(i=0; i<arr.length; i++) {
            if(list.indexOf(arr[i]) == -1) {
                check = false;
                break;
            }
        }
        
        LOGGER.error("result 1, {} : {}", i, check);
    }
    
    @Test
    public void test201() {
        String target = "S000001";
        
        List<String> list = new ArrayList<String> ();
        list.add("S000001");
        list.add("S000002");
        list.add("S000003");
        
        boolean check = false;
        
        if(list.indexOf(target) > -1) {
            check = true;
        }

        LOGGER.error("result 2 : {}", check);
    }
}



