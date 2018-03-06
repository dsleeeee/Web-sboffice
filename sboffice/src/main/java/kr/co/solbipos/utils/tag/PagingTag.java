package kr.co.solbipos.utils.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.taglibs.standard.resources.Resources;
import org.springframework.util.StringUtils;
import kr.co.solbipos.application.domain.cmm.Page;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PagingTag extends TagSupport {
    private static final long serialVersionUID = 1L;

    /* 출력관련 StringBuffer */
    StringBuffer defaultSb;

    /* 페이징에 사용되는 정보 */
    private Page pageInfo;

    /* text 또는 img */
    private String type;
    /* 이전 페이징 그룹으로 이동하는 아이콘 */
    private String prevImg;
    /* 다음 페이징 그룹으로 이동하는 아이콘 */
    private String nextImg;
    /* 이전 페이징 그룹으로 이동하는 텍스트 */
    private String prevText =
            "<i class=\"fa fa-chevron-left\"></i><span class=\"hidden\">prev</span>";
    /* 다음 페이징 그룹으로 이동하는 텍스트 */
    private String nextText =
            "<i class=\"fa fa-chevron-right\"></i><span class=\"hidden\">next</span>";;

    /* 이전 Label Template */
    private static String prevLabel =
            "<li><a href=\"javascript:;\" class=\"prev\" data-value=\"%s\">%s</a></li>";
    /* 다음 Label Template */
    private static String nextLabel =
            "<li><a href=\"javascript:;\" class=\"next\" data-value=\"%s\">%s</a></li>";
    /* 활성화 Label Template */
    private static String currLabel = "<li><a class=\"page_num on\">%s</a></li>";
    /* 페이지 Label Template */
    private static String otherLabel =
            "<li><a href=\"javascript:;\" class=\"page_num\" data-value=\"%s\">%s</a></li>";

    @Override
    public int doStartTag() throws JspException {
        defaultSb = new StringBuffer();

        try {
            JspWriter out = pageContext.getOut();

            if (pageInfo.getTotalPage() < 0)
                throw new JspException(Resources.getMessage("SET_NO_VALUE"));

            defaultSb.append("<ul class=\"clear\">");

            if (pageInfo.getPrev() > 1) {
                defaultSb.append(String.format(prevLabel, pageInfo.getPrev(), getPrevLabel(type)));
            }

            int first = pageInfo.getPrev() == 1 ? 1 : pageInfo.getPrev() + 1;
            int last = pageInfo.getNext() == pageInfo.getTotalPage() ? pageInfo.getTotalPage()
                    : pageInfo.getNext() - 1;

            for (int i = first; i <= last; i++) {
                if (i == pageInfo.getCurr()) {
                    defaultSb.append(String.format(currLabel, i));
                    continue;
                }
                defaultSb.append(String.format(otherLabel, i, i));
            }

            if (pageInfo.getNext() < pageInfo.getTotalPage()) {
                defaultSb.append(String.format(nextLabel, pageInfo.getNext(), getNextLabel(type)));
            }

            defaultSb.append("</ul>");
            // logger.debug(defaultSb.toString());
            out.print(defaultSb.toString());

        } catch (IOException e) {
            log.debug(e.getMessage());
        }

        return SKIP_BODY;
    }

    /**
     * type에 따라 prev label 을 반환한다.
     * 
     * @param t String
     * @return Object
     */
    private Object getPrevLabel(String t) {
        if ("text".equals(t) && !StringUtils.isEmpty(prevText))
            return prevText;

        if ("img".equals(t) && !StringUtils.isEmpty(prevImg))
            return prevImg;

        return "&lt;";
    }

    /**
     * type에 따라 next label 을 반환한다.
     * 
     * @param t String
     * @return Object
     */
    private Object getNextLabel(String t) {
        if ("text".equals(t) && !StringUtils.isEmpty(nextText))
            return nextText;

        if ("img".equals(t) && !StringUtils.isEmpty(nextImg))
            return nextImg;

        return "&gt;";
    }

    @Override
    public int doEndTag() throws JspException {
        defaultSb = null;
        return super.doEndTag();
    }

    public Page getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(Page pageInfo) {
        this.pageInfo = pageInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrevImg() {
        return prevImg;
    }

    public void setPrevImg(String prevImg) {
        this.prevImg = prevImg;
    }

    public String getNextImg() {
        return nextImg;
    }

    public void setNextImg(String nextImg) {
        this.nextImg = nextImg;
    }

    public String getPrevText() {
        return prevText;
    }

    public void setPrevText(String prevText) {
        this.prevText = prevText;
    }

    public String getNextText() {
        return nextText;
    }

    public void setNextText(String nextText) {
        this.nextText = nextText;
    }
}
