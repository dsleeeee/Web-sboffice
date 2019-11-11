<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="instlRequestDtlLayer" control="instlRequestDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="instlRequestDtlCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <span id="spanInstlRequestDtlTitle"></span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body sc2" style="height: 300px;">
            <div class="w100 mt10">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 200px;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter"
                            id="wjApprGridList">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="instl.seqNo"/>" binding="seqNo" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.agencyNm"/>" binding="agencyNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.instReqId"/>" binding="instReqId" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.instInsId"/>" binding="instInsId" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.instReqDt"/>" binding="instReqDt" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.instInsDt"/>" binding="instInsDt" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.instFg"/>" binding="instFg" width="80" data-map="instFgDataMap" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.instReason"/>" binding="instReason" data-map="reasonDatMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.remark"/>" binding="remark" align="left" width="150" is-read-only="true"></wj-flex-grid-column>

                    </wj-flex-grid>

                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>
        <div class="btnSet2">
          <%-- 닫기 --%>
          <span><a href="#" class="btn_gray" ng-click="close()"><s:message code="cmm.close" /></a></span>
        </div>
    </div>
</wj-popup>

<script type="text/javascript">

</script>
<script type="text/javascript" src="/resource/solbipos/js/pos/license/instlManage/instlRequestDtl.js?ver=20191016" charset="utf-8"></script>