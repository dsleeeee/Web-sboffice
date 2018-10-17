package kr.co.common.template.view;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.JavaScriptResult;
import kr.co.common.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Javascript 용 View 처리
 * 
 * @author 이호원
 */
@Component(value = "script")
public class JavaScriptView extends AbstractView {

    /** 메세지 서비스 */
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public JavaScriptView(MessageService messageService) {
        this.messageService = messageService;
        setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        if (model.get("result") == null) {
            return;
        }
        
        response.setContentType(getContentType());

        JavaScriptResult result = (JavaScriptResult) model.get("result");
        PrintWriter prtr = response.getWriter();
        String format = "%s", script = result.getScript();
        Status status = result.getStatus();

        if (status != null) {
            format = "alert('%s');history.back(-1);";
            script = messageService.get(status.getReason());
        }

        prtr
        .append("<html><head></head><body><script type=\"text/javascript\">")
        .append(String.format(format, script))
        .append("</script></body></html>").println();
        
        prtr.close();
    }
}
