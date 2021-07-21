<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<wj-popup id="kitchenprintLinkLayer" control="kitchenprintLinkLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:650px;">

    <div ng-controller="kitchenprintLinkCtrl">

        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.title.kitchenprintLink"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <div class="wj-dialog-body">
            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            item-formatter="_itemFormatter"
                            is-read-only="false">
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                       <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="prod.storeCd"/>" binding="storeCd" width="80"is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prod.storeNm"/>" binding="storeNm" width="*" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prod.prterNo"/>" binding="prterNo" width="80"is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prod.prterNm"/>" binding="prterNm" width="100" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
               </div>
           </div>
                <%-- 저장 버튼 --%>
                <div class="tc mt20">
                    <button id="funcSave" class="btn_blue" ng-click="save()">
                        <s:message code="cmm.save" />
                    </button>
                </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/kitchenprintList.js?ver=20200213.15" charset="utf-8"></script>