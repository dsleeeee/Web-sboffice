package kr.co.solbipos.system;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import kr.co.solbipos.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.utils.jsp.ColumnLayout;
import kr.co.solbipos.utils.jsp.ColumnList;

/**
 * {@code JSP} 에서 사용 {@code bean} 등록
 * 
 * @author 정용길
 */
@Component
public class BeanLoader implements ServletContextAware {

    @Autowired
    CmmCodeUtil cmmCodeUtil;

    @Autowired
    ColumnList columnList;
    
    @Autowired
    ColumnLayout columnLayout;

    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @PostConstruct
    public void init() {
        // ccu : jsp 에서 호출하는 변수가 됨
        servletContext.setAttribute("ccu", cmmCodeUtil); // 공통코드 호출 관련 
        servletContext.setAttribute("cl", columnList); // 그리드 헤더 리스트
        servletContext.setAttribute("clo", columnLayout); // 저장된 컬럼 layout
    }
}
