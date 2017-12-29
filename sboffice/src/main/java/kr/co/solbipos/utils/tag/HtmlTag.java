package kr.co.solbipos.utils.tag;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.poi.ss.formula.functions.T;
import org.apache.taglibs.standard.resources.Resources;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.utils.spring.StringUtil;

public class HtmlTag extends TagSupport {
    private static final long serialVersionUID = 1L;

    /* 출력관련 StringBuffer */
    StringBuffer defaultOptionSb, optionSb;

    private static final String CARRIAGE_RETURN = "\\r";

    private static final String WIN_NEWLINE = "\\n";

    private static final String LINUX_NEWLINE = CARRIAGE_RETURN + WIN_NEWLINE;

    /* select 태그 Template */
    String selectTpl = "<select%s%s%s>%s</select>";
    /* option 태그 Template */
    String optTpl = "<option value=\"%s\" %s label=\"%s\">%s</option>";
    /* checkbox 태그 Template */
    String checkboxTpl =
            "<input type=\"checkbox\"%s%s%s value=\"%s\" %s/><label for=\"%s\">%s</label>";
    /* radio 태그 Template */
    String radioTpl = "<input type=\"radio\"%s%s%s value=\"%s\" %s/><label for=\"%s\">%s</label>";
    /* id 와 name 지정 Template */
    String idAndNmTpl = " id=\"%s\" name=\"%s\"";
    /* class 지정 Template */
    String classTpl = " class=\"%s\"";
    /* style 지정 Template */
    String styleTpl = " style=\"%s\"";
    /* 태그 생성 itmes */
    private List<T> items;
    /* name 속성 */
    private String name;
    /* radio, select, checkbox */
    private Option type;
    /* label 명 */
    private String label;
    /* value 속성 */
    private String value;
    /* optional 값 초기화 */
    private String selected;
    /* 클래스명 */
    private String classNm;
    /* 스타일 */
    private String style;
    /* 첫번째 Value */
    private String firstValue;
    /* 첫번째 Label */
    private String firstLabel;
    /* 마지막 Value */
    private String lastValue;
    /* 마지막 Label */
    private String lastLabel;
    /* 다중선택 */
    private boolean multiple = false;
    /* 필수 */
    private boolean required = false;

    @Override
    public int doStartTag() throws JspException {
        defaultOptionSb = new StringBuffer();
        optionSb = new StringBuffer();

        try {
            JspWriter out = pageContext.getOut();

            if (items == null || items.size() == 0)
                throw new JspException(Resources.getMessage("TAG_NULL_ATTRIBUTE"));

            switch (type) {
                case SELECT:
                    out.println(generateSelect());
                    break;
                case RADIO:
                    out.println(generateRadioOrCheckbox(radioTpl));
                    break;
                case CHECKBOX:
                    out.println(generateRadioOrCheckbox(checkboxTpl));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return SKIP_BODY;
    }

    /**
     * select 태그를 생성하여 반환한다.
     * 
     * @return 생성된 select 태그 String
     * @throws JspException
     */
    private String generateSelect() throws JspException {
        if (!StringUtil.isEmpty(firstLabel)) {
            optionSb.append(String.format(optTpl, StringUtil.isEmpty(firstValue) ? "" : firstValue,
                    StringUtil.isEmpty(selected) ? "selected=\"selected\"" : "", firstLabel,
                    firstLabel));
        }

        for (Object o : items) {
            if (o instanceof DefaultMap) {
                DefaultMap<Object> m = (DefaultMap<Object>) o;
                optionSb.append(
                        String.format(optTpl, m.getStr(value),
                                !StringUtil.isEmpty(selected) && selected.equals(m.getStr(value))
                                        ? " selected=\"selected\"" : "",
                                m.getStr(label), m.getStr(label)));
                continue;
            }
            throw new JspException(Resources.getMessage("FOREACH_BAD_ITEMS"));
        }

        if (!StringUtil.isEmpty(lastLabel)) {
            optionSb.append(String.format(optTpl, StringUtil.isEmpty(lastValue) ? "" : lastValue,
                    StringUtil.isEmpty(selected) ? " selected=\"selected\"" : "", lastLabel,
                    lastLabel));
        }

        defaultOptionSb.append(String.format(selectTpl,
                String.format(idAndNmTpl, name, name) + (multiple ? " multiple" : "")
                        + (required ? " required=\"required\"" : ""),
                !StringUtil.isEmpty(classNm) ? String.format(classTpl, classNm) : "",
                !StringUtil.isEmpty(style) ? String.format(styleTpl, style) : "",
                optionSb.toString()));

        return defaultOptionSb.toString();
    }

    /**
     * 라디오 또는 체크박스를 생성하여 반환한다.
     * 
     * @param tpl String
     * @return 생성된 라디오 또는 체크박스 String
     * @throws JspException
     */
    private String generateRadioOrCheckbox(String tpl) throws JspException {
        for (int i = 0, itemSize = items.size(); i < itemSize; i++) {
            Object o = items.get(i);
            if (o instanceof DefaultMap) {
                DefaultMap<Object> m = (DefaultMap<Object>) o;
                optionSb.append(String.format(tpl,
                        String.format(idAndNmTpl, name + i, name)
                                + (required ? " required=\"required\"" : ""),
                        !StringUtil.isEmpty(classNm) ? String.format(classTpl, classNm) : "",
                        !StringUtil.isEmpty(style) ? String.format(styleTpl, style) : "",
                        m.getStr(value),
                        !StringUtil.isEmpty(selected) && selected.equals(m.getStr(value))
                                ? " checked=\"checked\"" : "",
                        name + i, m.getStr(label)));
                continue;
            }
            throw new JspException(Resources.getMessage("FOREACH_BAD_ITEMS"));
        }

        return defaultOptionSb.append(optionSb).toString();
    }

    public Option getType() {
        return type;
    }

    public void setType(Option type) {
        this.type = type;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassNm() {
        return classNm;
    }

    public void setClassNm(String classNm) {
        this.classNm = classNm;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFirstValue() {
        return firstValue;
    }

    public Object getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public void setFirstValue(String firstValue) {
        this.firstValue = firstValue;
    }

    public String getFirstLabel() {
        return firstLabel;
    }

    public void setFirstLabel(String firstLabel) {
        this.firstLabel = firstLabel;
    }

    public String getLastValue() {
        return lastValue;
    }

    public void setLastValue(String lastValue) {
        this.lastValue = lastValue;
    }

    public String getLastLabel() {
        return lastLabel;
    }

    public void setLastLabel(String lastLabel) {
        this.lastLabel = lastLabel;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public enum Option {
        SELECT, RADIO, CHECKBOX;
    }

    /**
     * NEWLINE 을 br태그로 변환한다.
     * 
     * @param str String
     * @return 변환 된 String
     */
    public static String nl2br(String str) {
        return StringUtil.isEmpty(str) ? str
                : Pattern.compile(LINUX_NEWLINE + "|" + WIN_NEWLINE).matcher(str)
                        .replaceAll("<br/>");
    }
}
