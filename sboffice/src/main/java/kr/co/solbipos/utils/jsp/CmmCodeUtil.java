package kr.co.solbipos.utils.jsp;

import static kr.co.solbipos.utils.spring.StringUtil.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import kr.co.solbipos.application.domain.sample.CommonCode;
import kr.co.solbipos.service.code.CmmCodeService;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.system.Prop;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 정용길 jsp 에서 직접 사용 공통 코드 comboBox 에 직접 사용하는 data 형식으로 돌려줌
 */
@Slf4j
@Component("cmmCodeUtil")
public class CmmCodeUtil {

    @Autowired
    Prop prop;

    @Autowired
    CmmCodeService cmmCodeService;

    public static final String COMBO_NAME = "name";
    public static final String COMBO_VALUE = "value";

    /**
     * 조회 건수 리스트
     * 
     * @return
     */
    public String getListScale() {
        String listScale[] = {"15", "20", "50", "100", "200", "300"};

        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < listScale.length; i++) {
            HashMap<String, String> m = new HashMap<>();
            m.put(COMBO_NAME, listScale[i]);
            m.put(COMBO_VALUE, listScale[i]);
            list.add(m);
        }

        return convertToJson(list);
    }

    /**
     * 공통 코드 조회(JSON, 명칭/코드)
     * 
     * @param nmcodeGrpCd
     * @return
     */
    public String getCommCode(String nmcodeGrpCd) {
        
        CommonCode commonCode = getCommCodeData(nmcodeGrpCd);
        if(commonCode == null) {
            return assmblEmptyCombo();
        }
        // 결과 형태를 만들어서 json 으로 리턴
        return assmblObj(commonCode.getCodeList(), "nmcodeNm", "nmcodeCd");
    }
    /**
     * 공통 코드 조회(JSON, 항목전체)
     * 
     * @param nmcodeGrpCd
     * @return
     */
    public String getCommCodeAll(String nmcodeGrpCd) {
        
        CommonCode commonCode = getCommCodeData(nmcodeGrpCd);
        if(commonCode == null) {
            return assmblEmptyCombo();
        }
        // 결과 형태를 만들어서 json 으로 리턴
        return convertToJson(commonCode.getCodeList());
    }
    /**
     * 공통 코드 조회, Redis 처리
     * 
     * @param nmcodeGrpCd
     * @return
     */
    private CommonCode getCommCodeData(String nmcodeGrpCd) {

        CommonCode commonCode = new CommonCode();
        commonCode.setComCdFg(nmcodeGrpCd);

        List<DefaultMap<String>> codeList = null;

        try {
            // 요청한 코드가 레디스에 있는지 확인
            if (cmmCodeService.hasCode(nmcodeGrpCd)) {
                // 레디스에서 조회
                commonCode = cmmCodeService.getCodeList(nmcodeGrpCd);
            }
            // 레디스에 없으면 디비에서 조회 후 레디스에 세팅
            else {
                codeList = cmmCodeService.selectCmmCodeList(nmcodeGrpCd);

                if (isEmpty(codeList)) { // 조회 결과 없으면 데이터 없음 리턴
                    return null;
                }

                commonCode.setCodeList(codeList);
                // 레디스에 공통코드 세팅
                cmmCodeService.setCodeList(commonCode);
            }
        } catch (Exception e) {
            log.warn("redis error.... 공통코드 디비에서 조회 : {}", nmcodeGrpCd);

            if (isEmpty(codeList)) { // 디비에서 조회가 안되었으면 조회
                codeList = cmmCodeService.selectCmmCodeList(nmcodeGrpCd);
            }

            if (isEmpty(codeList)) { // 조회 결과 없으면 데이터 없음 리턴
                return null;
            }

            commonCode.setCodeList(codeList);
        }
        return commonCode;
    }

    /**
     * 공통코드 콤보박스의 내용을 json data 를 생성
     * 
     * @param source 조회된 코드 리스트
     * @param name 콤보박스에 보여줄 변수명
     * @param value 콤보박스에 value로 사용될 변수명
     * @return
     */
    public <E> String assmblObj(List<DefaultMap<E>> source, String name, String value) {
        if (ObjectUtils.isEmpty(source)) {
            log.warn("assmble source empty...");
            return "";
        }
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> m = new HashMap<>();
        m.put(COMBO_NAME, "전체");
        m.put(COMBO_VALUE, "ALL");
        list.add(m);
        
        source.stream().forEach(x -> {
            list.add(assmbl(x, name, value)); // 콤보박스의 내용을 리스트에 추가
        });

        return convertToJson(list);
    }

    /**
     * 공통코드 콤보박스의 객체를 생성
     * 
     * @param obj
     * @param name
     * @param value
     * @return
     */
    public <E> HashMap<String, String> assmbl(DefaultMap<E> obj, String name, String value) {
        HashMap<String, String> m = new HashMap<>();
        m.put(COMBO_NAME, obj.getStr(name)); // 콤보박스 display
        m.put(COMBO_VALUE, obj.getStr(value)); // 콤보박스 value
        return m;
    }

    /**
     * 공통 코드 결과가 없을 경우 리턴
     * 
     * @return
     */
    public String assmblEmptyCombo() {
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> m = new HashMap<>();
        m.put(COMBO_NAME, "no data..");
        m.put(COMBO_VALUE, "9999");
        list.add(m);
        return convertToJson(list);
    }

    /*
     * public <E> String assmblObj(List<DefaultMap<E>> source, String name[], String value[]) { if(
     * ObjectUtils.isEmpty(source) ) { log.warn("assmble source empty..."); return ""; }
     * 
     * List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
     * 
     * source.stream().forEach( x -> { list.add( assmbl(x, name, value) ); });
     * 
     * return convertToJson(list); }
     * 
     * public <E> String addStr(DefaultMap<E> obj, String arr[]) { return addStr(obj, arr, ""); }
     * 
     * public <E> String addStr(DefaultMap<E> obj, String arr[], String separator) { if(
     * ObjectUtils.isEmpty(obj) || ObjectUtils.isEmpty(arr) ) { return ""; }
     * 
     * String returnStr = ""; for( int i = 0; i < arr.length; i++ ) { returnStr +=
     * obj.getStr(arr[i]); if( i == arr.length - 1 ) { } else { if( !isEmpty(separator) ) {
     * returnStr += separator; } } } return returnStr; }
     */
}
