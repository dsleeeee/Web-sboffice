package kr.co.common.data;

import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.jsp.ColumnLayout;
import kr.co.common.utils.jsp.ColumnList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

/**
 * {@code JSP} 에서 사용 {@code bean} 등록
 *
 * @author 정용길
 */
@Component
public class BeanLoader implements ServletContextAware {

    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;
    private final ColumnList columnList;
    private final ColumnLayout columnLayout;

    private ServletContext servletContext;

    /** Constructor Injection */
    @Autowired
    public BeanLoader(CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil, ColumnList columnList, ColumnLayout columnLayout) {
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
        this.columnList = columnList;
        this.columnLayout = columnLayout;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @PostConstruct
    public void init() {
        // ccu : jsp 에서 호출하는 변수가 됨
        servletContext.setAttribute("ccu", cmmCodeUtil); // 공통코드 호출 관련
        servletContext.setAttribute("cnv", cmmEnvUtil); // 환경변수 호출 관련
        servletContext.setAttribute("cl", columnList); // 그리드 헤더 리스트
        servletContext.setAttribute("clo", columnLayout); // 저장된 컬럼 layout
    }
}
