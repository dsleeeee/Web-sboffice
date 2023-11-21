<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" ng-controller="captionMsgGrpCtrl" id="captionMsgGrpView" style="display: none;">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="captionMsg.captionMsgGrp"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('captionMsgGrpCtrl', 1)">
                <s:message code="cmm.search"/>
            </button>
        </div>
    </div>

    <table class="searchTbl">
        <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 기능키/메시지 화면구분코드 --%>
            <th><%--<s:message code="captionMsg.captionMsg"/>&nbsp;--%><s:message code="captionMsg.captionImgCd"/></th>
            <td>
                <input type="text" id="captionImgCd" class="sb-input w100" ng-model="captionImgCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <!-- 기능키/메시지 화면구분명 -->
            <th><%--<s:message code="captionMsg.captionMsg"/>&nbsp;--%><s:message code="captionMsg.captionImgNm"/></th>
            <td>
                <input type="text" id="captionImgNm" class="sb-input w100" ng-model="captionImgNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="listScaleBox"
                ng-model="listScaleVer"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)">
        </wj-combo-box>
        <%-- 삭제 --%>
        <button class="btn_skyblue ml5 fr" id="btnCaptionMsgGrpdel" ng-click="captionMsgGrpDel()"><s:message code="cmm.del"/></button>
        <%-- 신규등록 --%>
        <button class="btn_skyblue ml5 fr" id="btnCaptionMsgGrpReg" ng-click="captionMsgGrpReg()">
            <s:message code="cmm.new.add"/>
        </button>
    </div>

    <%-- 화면구분등록 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    control="flex"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    initialized="initGrid(s,e)"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" visible="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="captionMsg.captionImgCd"/>" binding="captionImgCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="captionMsg.captionImgNm"/>" binding="captionImgNm" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="captionMsg.fileOrgNm"/>" binding="fileOrgNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="captionMsg.fileSize"/>" binding="fileSize" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="captionMsg.preview"/>" binding="preview" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="captionMsg.download"/>" binding="downLoad" align="center" width="100" is-read-only="true"></wj-flex-grid-column>

                <%-- 저장시 필요 --%>
                <wj-flex-grid-column header="" binding="fileNm" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="" binding="fileExt" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

                <%-- 삭제시 필요(확인용) --%>
                <wj-flex-grid-column header="" binding="msgCnt" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="captionMsgGrpCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/multilingual/captionMsg/captionMsgGrp.js?ver=20231120.01" charset="utf-8"></script>

<%-- 신규등록 팝업 레이어 --%>
<c:import url="/WEB-INF/view/base/multilingual/captionMsg/captionMsgGrpReg.jsp">
</c:import>

<%-- 상세팝업 레이어 --%>
<c:import url="/WEB-INF/view/base/multilingual/captionMsg/captionMsgGrpDtl.jsp">
</c:import>

<%-- 미리보기 --%>
<c:import url="/WEB-INF/view/base/multilingual/captionMsg/captionMsgGrpImg.jsp">
</c:import>
