package kr.co.common.template.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;
import kr.co.common.system.Prop;
import kr.co.common.utils.spring.ObjectUtil;
import kr.co.common.utils.spring.StringUtil;

/**
 * Download 용 View 처리
 * 
 * @author 이호원
 */
@Component(value = "download")
public class DownloadView extends AbstractView {
    /** 기록자 */
    private static final Logger logger = LoggerFactory.getLogger(DownloadView.class);
    /** 파일명 */
    String fileName;
    /** 프로퍼티 */
    @Autowired
    Prop prop;

    public DownloadView() {
        setContentType("application/download;charset=UTF-8");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) {
        File file = new File(StringUtil.removeTrailingSlash(prop.excelTempDir) + "/"
                + model.get("targetFile").toString());
        Object changeFileName = model.get("changeFile");
        String fileName =
                ObjectUtil.isEmpty(changeFileName) ? file.getName() : changeFileName.toString();
        String userAgent = request.getHeader("User-Agent");

        try {
            if (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1)
                fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", " ");
            else
                fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            if (logger.isErrorEnabled())
                logger.error("MSIE : Unable to encode the fileName");
        }

        response.setContentType(getContentType());
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        FileInputStream fis = null;
        OutputStream out = null;

        try {
            out = response.getOutputStream();
            fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, out);

            out.flush();
        } catch (IOException e) {
            if (logger.isErrorEnabled())
                logger.error("File download fail");
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (Exception e) {
                }
            if (out != null)
                try {
                    out.close();
                } catch (IOException e) {
                }
        }
    }
}
