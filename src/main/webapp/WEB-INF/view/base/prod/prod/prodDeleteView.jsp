<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="prodDeleteLayer" control="prodDeleteLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:450px; height:200px;">

    <div ng-controller="prodDeleteCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.title.delProd"/>
            <a href="#" class="wj-hide btn_close" ng-click="closeProdDelete()"></a>
        </div>

        <div style="padding:10px 30px 0 30px;">
            <p class="s13 bk lh20">
                <s:message code="prod.delete.msg"/>
            </p>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div class="btnSet tc mt20">
                <button class="btn_blue" id="btnSelectProdDelete" ng-click="selectProdDelete()"><s:message code="prod.selectDelete"/></button>
                <button class="btn_blue" id="btnAllProdDelete" ng-click="allProdDelete()"><s:message code="prod.allDelete"/></button>
            </div>
        </div>
    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/prodDeleteView.js?ver=20220322.01" charset="utf-8"></script>