<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<wj-popup control="batchFuncLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:970px;">
    <div class="wj-dialog wj-dialog-columns title">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="func.batch" />
            <a href="" class="wj-hide btn_close" onclick="closePop()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
                <table class="tblType01">
                    <colgroup>
                        <col class="w15" />
                        <col class="w35" />
                        <col class="w15" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <th>적용대상매장</th>
                        <td id="storeInfo"></td>
                        <input type="hidden" id="hdStoreCd" />
                        <th><s:message code="func.funFg"/></th>
                        <td>
                            <%--<div class="sb-select w100">--%>
                                <%--<wj-combo-box
                                        id="bfFnkeyFg"
                                        ng-model="fnkeyFg"
                                        control="bfFnkeyFg"
                                        items-source="_getComboData('bfFnkeyFg')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)">
                                </wj-combo-box>--%>
                            <%--</div>--%>
                            <select class="sb-select" id="bfFnkeyFg"></select>
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="func.fnkeyNo"/></th>
                        <td><input type="text" id="bfFnkeyNo" ng-model="fnkeyNo" /></td>
                        <th><s:message code="func.fnkeyNm"/></th>
                        <td><input type="text" id="bfFnkeyNm" ng-model="fnkeyNm" /></td>
                    </tr>
                    </tbody>
                </table>
                <%-- 조회 --%>
                <div class="mt10 tr">
                        <button class="btn_skyblue" id="btnSearch" onClick="regFnkey()"><s:message code="cmm.search" /></button>
                </div>

                <%--- 적용매장 그리드 --%>
                <div class="oh mt20">
                    <div class="w50 fl" ng-controller="regFnkeyCtrl">
                        <div class="wj-TblWrap mr10" style="height:430px; overflow-y:hidden;">
                            <div class="oh">
                                <span class="fl bk lh20 s14"><s:message code="func.regFnkey"/></span>
                                <span class="fr">
                                    <a href="#" class="btn_grayS2" ng-click="delete()"><s:message code="cmm.del" /></a>
                                  </span>
                            </div>
                            <div id="regFnkeyGrid" class="mt10" style="height: 380px; overflow-y: hidden;">
                                <wj-flex-grid
                                        autoGenerateColumns="false"
                                        control="flex"
                                        initialized="initGrid(s,e)"
                                        sticky-headers="true"
                                        selection-mode="Row"
                                        items-source="data"
                                        item-formatter="_itemFormatter"
                                        allow-merging="Cells">

                                    <!-- define columns -->
                                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="func.funFgCd"/>" binding="fnkeyFg" width="50" is-read-only="true" align="center" allow-merging="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="func.funFg"/>" binding="fnkeyFgNm" width="90" is-read-only="true" align="center" allow-merging="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="func.fnkeyNo"/>" binding="fnkeyNo" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="func.fnkeyNm"/>" binding="fnkeyNm" width="*" is-read-only="true" ></wj-flex-grid-column>
                                </wj-flex-grid>
                            </div>
                        </div>
                    </div>

                    <%--- 미적용매장 그리드 --%>
                    <div class="w50 fr" ng-controller="noRegFnkeyCtrl">

                        <div class="wj-TblWrap ml10" style="height:430px; overflow-y: hidden;" >
                            <div class="oh">
                                <span class="fl bk lh20 s14"><s:message code="func.noRegFnkey"/></span>
                                <span class="fr"><a href="#" class="btn_grayS2" ng-click="regist()" ><s:message code="func.regist"/></a></span>
                            </div>

                            <div id="noRegFnKeyGrid" class="mt10" style="height: 380px; overflow-y: hidden;">
                                <wj-flex-grid
                                        autoGenerateColumns="false"
                                        control="flex"
                                        initialized="initGrid(s,e)"
                                        sticky-headers="true"
                                        selection-mode="Row"
                                        items-source="data"
                                        item-formatter="_itemFormatter"
                                        allow-merging="Cells">

                                    <!-- define columns -->
                                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="func.funFgCd"/>" binding="fnkeyFg" width="50" is-read-only="true" align="center" allow-merging="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="func.funFg"/>" binding="fnkeyFgNm" width="90" is-read-only="true" align="center" allow-merging="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="func.fnkeyNo"/>" binding="fnkeyNo" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="func.fnkeyNm"/>" binding="fnkeyNm" width="*" is-read-only="true" ></wj-flex-grid-column>
                                </wj-flex-grid>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

    </div>
</wj-popup>
<script>

    // 기능구분 콤보박스 셋팅
    var funcFgData = ${ccu.getCommCode("026")};
    var ele = document.getElementById('bfFnkeyFg');

    // 콤보박스 데이터 셋팅('전체'는 코드값이 없으므로 따로 처리)
    ele.innerHTML = ele.innerHTML +'<option value="' + funcFgData[0]['value'] + '">' + funcFgData[0]['name'] + '</option>';
    for (var i = 1; i < funcFgData.length; i++) {
        ele.innerHTML = ele.innerHTML +'<option value="' + funcFgData[i]['value'] + '">[' + funcFgData[i]['value'] + '] ' + funcFgData[i]['name'] + '</option>';
    }

</script>
<script type="text/javascript" src="/resource/solbipos/js/pos/confg/func/batchFunc.js?ver=20200709.26" charset="utf-8"></script>