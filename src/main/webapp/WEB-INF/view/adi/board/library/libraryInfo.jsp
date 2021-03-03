<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjLibraryInfoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;height:400px;" fade-in="false" fade-out="false">
    <div ng-controller="libraryInfoCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <span id="libraryInfoTitle" class="ml20"></span>
            <label id="lblStatus" style="display: none"></label>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body sc2" style="height: 370px;">
            <table class="tblType01">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                    <tr>
                        <%-- 자료명 --%>
                        <th>
                            <s:message code="libraryInfo.title"/>
                        </th>
                        <td colspan="3">
                            <input type="text" class="sb-input w100" id="srchTitle" ng-model="title" />
                        </td>
                    </tr>
                    <tr>
                        <%-- 자료등록 --%>
                        <th>
                            <s:message code="libraryInfo.file"/>
                        </th>
                        <td colspan="3">
                            <f:form id="libraryForm" name="libraryForm" method="post" enctype="multipart/form-data" >
                                <%--다중업로드--%>
                                <input multiple="multiple" type="file" id="file" name="file"/>
                            </f:form>
                        </td>
                    </tr>
                </tbody>
            </table>

            <%-- 첨부파일 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:150px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="libraryInfo.orginlFileNm"/>" binding="orginlFileNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="libraryInfo.del"/>" binding="del" width="50" is-read-only="true" align="center"></wj-flex-grid-column>

                        <%--삭제시 필요--%>
                        <wj-flex-grid-column header="<s:message code="libraryInfo.idx"/>" binding="idx" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>

            <%-- 저장 버튼 --%>
            <div class="tc mt20">
                <button id="funcSave" class="btn_blue">
                    <s:message code="cmm.save" />
                </button>
            </div>

        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/board/library/libraryInfo.js?ver=20200311.23" charset="utf-8"></script>