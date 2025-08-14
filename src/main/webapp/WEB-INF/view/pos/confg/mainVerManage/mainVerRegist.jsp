<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjMainVerRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;height:550px;" fade-in="false" fade-out="false">
    <div ng-controller="mainVerRegistCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="mainVerRegist.info"/>
            <label id="lblHqOfficeCd"></label>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w20"/>
                    <col class="w30"/>
                    <col class="w20"/>
                    <col class="w30"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 구분 --%>
                    <th><s:message code="mainVerRegist.verGubun" /></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchVerGubunCombo"
                                    ng-model="verGubun"
                                    items-source="_getComboData('verGubunCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchVerGubunCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <%-- 버전일련번호 --%>
                    <th><s:message code="mainVerRegist.verSerNo" /></th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchVerSerNo" ng-model="srchVerSerNo" onkeyup="fnNxBtnSearch('2');"/>
                    </td>
                    <%-- 버전적용명 --%>
                    <th><s:message code="mainVerRegist.verSerNm" /></th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchVerSerNm" ng-model="srchVerSerNm" onkeyup="fnNxBtnSearch('2');"/>
                    </td>
                </tr>
                <tr>
                    <%-- 프로그램상세구분 --%>
                    <th><s:message code="mainVerRegist.progDetailFg" /></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchProgDetailFgCombo"
                                    ng-model="progDetailFg"
                                    items-source="_getComboData('progDetailFgCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchProgDetailFgCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 파일설명 --%>
                    <th><s:message code="mainVerRegist.fileDesc" /></th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchFileDescm" ng-model="srchFileDescm" onkeyup="fnNxBtnSearch('2');"/>
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 tr">
                <div class="oh sb-select dkbr">
                    <%--조회--%>
                    <button class="btn_skyblue fr" id="nxBtnSearch2" ng-click="_broadcast('mainVerRegistCtrl', 1)"><s:message code="cmm.search" /></button>
                </div>
            </div>

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:260px; overflow-y: hidden; overflow-x: hidden;">
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
                        <wj-flex-grid-column header="<s:message code="mainVerRegist.verGubun"/>" binding="verGubun" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mainVerRegist.verSerNo"/>" binding="verSerNo" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mainVerRegist.verSerNm"/>" binding="verSerNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mainVerRegist.fileDesc"/>" binding="fileDesc" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mainVerRegist.progFg"/>" binding="progFg" data-map="progFgDataMap" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mainVerRegist.progDetailFg"/>" binding="progDetailFg" data-map="progDetailFgDataMap" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mainVerRegist.systemTypeFg"/>" binding="systemTypeFg" data-map="systemTypeFgDataMap" width="75" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mainVerRegist.pgmYn"/>" binding="pgmYn" data-map="pgmYnDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mainVerRegist.dbYn"/>" binding="dbYn" data-map="dbYnDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mainVerRegist.imgYn"/>" binding="imgYn" data-map="imgYnDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mainVerRegist.fileSize"/>" binding="fileSize" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mainVerRegist.regCnt"/>" binding="regCnt" width="75" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mainVerRegist.recvCnt"/>" binding="recvCnt" width="75" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mainVerRegist.useYn"/>" binding="useYn" data-map="useYnDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mainVerRegist.orgnCds"/>" binding="orgnCds" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>

            <%-- 등록 버튼 --%>
            <div class="tc mt20">
                <button id="funcRegist" class="btn_blue">
                    <s:message code="cmm.regist" />
                </button>
            </div>

        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript">
    <%-- 프로그램구분 --%>
    var progFgData = ${ccu.getCommCode("059")};
    <%-- 프로그램상세구분 --%>
    var progDetailFgData = ${ccu.getCommCode("405")};
    // 사용여부
    var useYnData = ${ccu.getCommCode("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/pos/confg/mainVerManage/mainVerRegist.js?ver=20250807.01" charset="utf-8"></script>