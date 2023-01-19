<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSystemImg00001Layer" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;height:530px;" fade-in="false" fade-out="false">
    <div class="wj-dialog wj-dialog-columns">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <label id="lblTitle"></label>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body h400">
            <img style="width: 100%; height: 100%;" src="http://neo.solbipos.com/ProdImg/00001/SYSTEM_IMG_00001.JPG" alt="" />
        </div>

        <div class="wj-dialog-footer">
            <button class="btn wj-hide btn_gray"><s:message code="cmm.close"/></button>
        </div>
    </div>
</wj-popup>

