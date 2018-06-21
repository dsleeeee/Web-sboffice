package kr.co.solbipos.service.grid;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.grid.impl.GridSupportServiceImpl;
import kr.co.common.service.message.MessageService;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GridSupportServiceTests {

    @Mock
    MessageService      messageService;
    
    @InjectMocks
    GridSupportServiceImpl gridSupportService;

    DefaultMap<Object> map = null;
    List<String> columnsFilter = null;
    
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        
        map = new DefaultMap<>(); // 초기화
    }
    
    /**
      * getGridColumns
      * 일반적인 경우
      */
    @Test
    @Ignore
    public void test100() {
        map.put("shopCd", "test");
        
        List<HashMap<String, String>> result = gridSupportService.getGridColumns(map, null);
        assertNotNull(result);
    }
    
    /**
      * getGridColumns
      * 필터 적용
      */
    @Test
    @Ignore
    public void test101() {
        map.put("shopCd", "test");
        map.put("saleDate", "test");
        map.put("dcmSaleAmt", "test");
        
        columnsFilter = Arrays.asList("dcmSaleAmt", "saleDate");
        
        List<HashMap<String, String>> result = gridSupportService.getGridColumns(map, columnsFilter);
        
        // 필터 적용한 만큼 나왔는지 확인
        assertEquals(result.size(), columnsFilter.size());
        
        // 필터 제대로 적용 됬는지 확인
        result.stream().forEach(x->{
            HashMap<String, String> obj = (HashMap<String, String>) x;
            assertTrue(columnsFilter.indexOf(obj.get("binding")) > -1);
        });
    }
    
    /**
      * getGridColumns
      * null 체크
      */
    @Test
    @Ignore
    public void test102() {
        List<HashMap<String, String>> result = gridSupportService.getGridColumns(null, null);
        assertNull(result);
    }
    
    /**
      * makeHeader 
      * messageService.get(keyName) return null 확인
      */
    @Test
    @Ignore
    public void test200() {
        String keyName = "shopCd";
        HashMap<String, String> result = gridSupportService.makeHeader(keyName);
        assertTrue(keyName.equals(result.get("header")));
    }
    
    /**
      * makeHeader
      * messageService.get(keyName) return "" 확인
      */
    @Test
    @Ignore
    public void test201() {
        String keyName = "shopCd";
        given(messageService.get(keyName)).willReturn("");
        
        HashMap<String, String> result = gridSupportService.makeHeader(keyName);
        assertTrue(keyName.equals(result.get("header")));
    }
}















