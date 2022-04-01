<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjAlimtalkAddresseeeDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:430px;height:350px;" fade-in="false" fade-out="false">
    <div ng-controller="alimtalkAddresseeDtlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="alimtalkAddresseeDtl.info"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:250px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns.="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="alimtalkAddresseeDtl.phoneNumber"/>" binding="phoneNumber" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="alimtalkAddresseeDtl.addr"/>" binding="addr" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="alimtalkAddresseeDtl.saleDate"/>" binding="saleDate" width="80" is-read-only="true" align="center" format="date"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/alimtalk/alimtalkSendHist/alimtalkAddresseeDtl.js?ver=20220330.01" charset="utf-8"></script>