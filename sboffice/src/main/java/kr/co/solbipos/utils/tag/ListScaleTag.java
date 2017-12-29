package kr.co.solbipos.utils.tag;

import java.io.IOException;
import java.util.regex.Pattern;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import kr.co.solbipos.utils.spring.StringUtil;

public class ListScaleTag extends TagSupport {
    private static final long serialVersionUID = 1L;

    /* 출력관련 StringBuffer */
    StringBuffer defaultSb, optionSb;

    private static final String DEFAULT_SCALES = "20,50,100,300,500";

    /* select 태그 template */
    String selectTpl = "<select id=\"%s\" class=\"m_0 select select_xs bg_white\">%s</select>";
    /* option 태그 template */
    String optTpl = "<option value=\"%s\" %s label=\"%s\" />";

    /* 태그 생성될 items */
    private String[] items;
    /* scales (ex.20,50,100,300,500) */
    private String scales;
    /* label 명 */
    private String label;
    /* id 속성 */
    private String id;
    /* optional 값 초기화 */
    private String selected;

    @Override
    public int doStartTag() throws JspException {
        defaultSb = new StringBuffer();
        optionSb = new StringBuffer();

        try {

            JspWriter out = pageContext.getOut();

            out.println(generateListScale());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return SKIP_BODY;
    }

    /**
     * select 태그를 생성하여 반환한다.
     * 
     * @return listScale select tag String
     * @throws JspException
     */
    private String generateListScale() throws JspException {
        scales = validScales();
        items = scales.split(",");

        for (String o : items) {
            o = o.trim();
            optionSb.append(
                    String.format(optTpl, o, !StringUtil.isEmpty(selected) && selected.equals(o)
                            ? "selected=\"selected\"" : "", o + label));
        }

        defaultSb.append(String.format(selectTpl, !StringUtil.isEmpty(id) ? id : "listScaleDisplay",
                optionSb.toString()));

        return defaultSb.toString();
    }

    /**
     * 입력받은 scales를 검증한다.
     * 
     * @return 검증 완료 된 String
     */
    public String validScales() {
        String regExr = "(,{0,1}\\d{1,})+$";

        if (StringUtil.isEmpty(scales) || !Pattern.matches(regExr, scales)) {
            return DEFAULT_SCALES;
        }

        return scales;
    }

    @Override
    public int doEndTag() throws JspException {
        defaultSb = null;
        optionSb = null;
        return super.doEndTag();
    }

    public String getSelectTpl() {
        return selectTpl;
    }

    public void setSelectTpl(String selectTpl) {
        this.selectTpl = selectTpl;
    }

    public String getOptTpl() {
        return optTpl;
    }

    public void setOptTpl(String optTpl) {
        this.optTpl = optTpl;
    }

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    public String getScales() {
        return scales;
    }

    public void setScales(String scales) {
        this.scales = scales;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

}
