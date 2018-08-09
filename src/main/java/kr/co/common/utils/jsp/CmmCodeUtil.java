package kr.co.common.utils.jsp;

import static kr.co.common.utils.spring.StringUtil.convertToJson;
import static org.springframework.util.StringUtils.isEmpty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import kr.co.common.data.domain.CommonCodeVO;
import kr.co.common.data.domain.EnvCodeVO;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.code.CmmCodeService;

/**
 * jsp 에서 직접 사용 공통 코드 comboBox 에 직접 사용하는 data 형식으로 돌려줌
 *
 * @author 정용길
 */
@Component("cmmCodeUtil")
public class CmmCodeUtil {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
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

        CommonCodeVO commonCodeVO = getCommCodeData(nmcodeGrpCd);
        if(commonCodeVO == null) {
            return assmblEmptyCombo();
        }
        // 결과 형태를 만들어서 json 으로 리턴
        return assmblObj(commonCodeVO.getCodeList(), "nmcodeNm", "nmcodeCd", UseYn.ALL);
    }

    /**
     * 공통 코드 조회  "ALL" 제외 (JSON, 명칭/코드)
     *
     * @param nmcodeGrpCd
     * @return
     */
    public String getCommCodeExcpAll(String nmcodeGrpCd) {

        CommonCodeVO commonCodeVO = getCommCodeData(nmcodeGrpCd);
        if(commonCodeVO == null) {
            return assmblEmptyCombo();
        }
        // 결과 형태를 만들어서 json 으로 리턴
        return assmblObj(commonCodeVO.getCodeList(), "nmcodeNm", "nmcodeCd", UseYn.N);
    }

    /**
     * 공통 코드 조회  선택 포함 (JSON, 명칭/코드)
     *
     * @param nmcodeGrpCd
     * @return
     */
    public String getCommCodeSelect(String nmcodeGrpCd) {

        CommonCodeVO commonCodeVO = getCommCodeData(nmcodeGrpCd);
        if(commonCodeVO == null) {
            return assmblEmptyCombo();
        }
        // 결과 형태를 만들어서 json 으로 리턴
        return assmblObj(commonCodeVO.getCodeList(), "nmcodeNm", "nmcodeCd", UseYn.ALL);
    }


    /**
     * 공통 코드 조회(JSON, 선택안함)
     *
     * @param nmcodeGrpCd
     * @return
     */
    public String getCommCodeNoSelect(String nmcodeGrpCd) {

        CommonCodeVO commonCodeVO = getCommCodeData(nmcodeGrpCd);
        if(commonCodeVO == null) {
            return assmblEmptyCombo();
        }
        // 결과 형태를 만들어서 json 으로 리턴
        return assmblObj(commonCodeVO.getCodeList(), "nmcodeNm", "nmcodeCd", UseYn.Y);
    }

    /**
     * 공통 코드 조회(JSON, 항목전체)
     *
     * @param nmcodeGrpCd
     * @return
     */
    public String getCommCodeAll(String nmcodeGrpCd) {

        CommonCodeVO commonCodeVO = getCommCodeData(nmcodeGrpCd);
        if(commonCodeVO == null) {
            return assmblEmptyCombo();
        }
        // 결과 형태를 만들어서 json 으로 리턴
        return convertToJson(commonCodeVO.getCodeList());
    }

    /**
     * 공통 코드 조회, Redis 처리
     *
     * @param nmcodeGrpCd
     * @return
     */
    public CommonCodeVO getCommCodeData(String nmcodeGrpCd) {

        CommonCodeVO commonCodeVO = new CommonCodeVO();
        commonCodeVO.setComCdFg(nmcodeGrpCd);

        List<DefaultMap<String>> codeList = null;

        try {
            // 요청한 코드가 레디스에 있는지 확인
            if (cmmCodeService.hasCode(nmcodeGrpCd)) {
                // 레디스에서 조회
                commonCodeVO = cmmCodeService.getCodeList(nmcodeGrpCd);
            }
            // 레디스에 없으면 디비에서 조회 후 레디스에 세팅
            else {
                codeList = cmmCodeService.selectCmmCodeList(nmcodeGrpCd);

                if (isEmpty(codeList)) { // 조회 결과 없으면 데이터 없음 리턴
                    return null;
                }

                commonCodeVO.setCodeList(codeList);
                // 레디스에 공통코드 세팅
                cmmCodeService.setCodeList(commonCodeVO);
            }
        } catch (Exception e) {
            LOGGER.warn("redis error.... 공통코드 디비에서 조회 : {}", nmcodeGrpCd);

            if (isEmpty(codeList)) { // 디비에서 조회가 안되었으면 조회
                codeList = cmmCodeService.selectCmmCodeList(nmcodeGrpCd);
            }

            if (isEmpty(codeList)) { // 조회 결과 없으면 데이터 없음 리턴
                return null;
            }

            commonCodeVO.setCodeList(codeList);
        }
        return commonCodeVO;
    }

    /**
     * 공통코드 콤보박스의 내용을 json data 를 생성
     *
     * @param source 조회된 코드 리스트
     * @param name 콤보박스에 보여줄 변수명
     * @param value 콤보박스에 value로 사용될 변수명
     * @return
     */
    public <E> String assmblObj(List<DefaultMap<E>> source, String name, String value, UseYn option) {
        if (ObjectUtils.isEmpty(source)) {
            LOGGER.warn("assmble source empty...");
            return "";
        }
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        if(option == UseYn.ALL) {
            HashMap<String, String> m = new HashMap<>();
            m.put(COMBO_NAME, "전체");
            m.put(COMBO_VALUE, "");
            list.add(m);
        }
        else if(option == UseYn.Y) {
            HashMap<String, String> m = new HashMap<>();
            m.put(COMBO_NAME, "선택안함");
            m.put(COMBO_VALUE, "N");
            list.add(m);
        }

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

    /**
     * 환경변수 공통코드 조회 (TB_CM_ENVST_DTL)
     *
     * @param envstCd
     * @return
     */
    public String getEnvCode(String envstCd) {

        EnvCodeVO envCodeVO = getEnvCodeData(envstCd);
        if(envCodeVO == null) {
            return assmblEmptyCombo();
        }
        // 결과 형태를 만들어서 json 으로 리턴
        return assmblObj(envCodeVO.getCodeList(), "envstValNm", "envstValCd", UseYn.ALL);
    }

    /**
     * 환경변수 공통코드 조회  "ALL" 제외 (TB_CM_ENVST_DTL)
     *
     * @param envstCd
     * @return
     */
    public String getEnvCodeExcpAll(String envstCd) {

        EnvCodeVO envCodeVO = getEnvCodeData(envstCd);
        if(envCodeVO == null) {
            return assmblEmptyCombo();
        }
        // 결과 형태를 만들어서 json 으로 리턴
        return assmblObj(envCodeVO.getCodeList(), "envstValNm", "envstValCd", UseYn.N);
    }

    /**
     * 환경변수 코드 조회
     *
     * @param envstCd
     * @return
     */
    private EnvCodeVO getEnvCodeData(String envstCd) {

        EnvCodeVO envCodeVO = new EnvCodeVO();

        envCodeVO.setEnvstCd(envstCd);

        List<DefaultMap<String>> codeList = null;

        codeList = cmmCodeService.selectEnvCodeList(envstCd);

        if (isEmpty(codeList)) { // 조회 결과 없으면 데이터 없음 리턴
            return null;
        }

        envCodeVO.setCodeList(codeList);

        return envCodeVO;
    }

    /**
     * 대리점코드 조회
     *
     * @param
     * @return
     */
    public String getAgencyList() {

        List<DefaultMap<String>> agencyList = cmmCodeService.getAgencyList();

        // 결과 형태를 만들어서 json 으로 리턴
        return assmblObj(agencyList, "agencyNm", "agencyCd", UseYn.ALL);
    }

    /**
     * 벤사 코드 조회
     * @return
     */
    public String getVanList() {
        List<DefaultMap<String>> agencyList = cmmCodeService.getVanList();

        // 결과 형태를 만들어서 json 으로 리턴
        return assmblObj(agencyList, "vanNm", "vanCd", UseYn.ALL);
    }


    /**
     * 본사 코드 조회
     * @return
     */
    public String getHqOfficeList() {
        List<DefaultMap<String>> hqOfficeList = cmmCodeService.getHqOfficeList();

        // 결과 형태를 만들어서 json 으로 리턴
        return assmblObj(hqOfficeList, "vanNm", "vanCd", UseYn.ALL);
    }
}
