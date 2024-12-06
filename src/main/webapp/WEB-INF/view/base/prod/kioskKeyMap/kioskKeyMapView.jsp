<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<style>
#thPre, #thNext { background-color: #d5d5d5; width: 28px; }
#kioskKeyMapViewLayer table thead tr th div { height: 10px; width: 102px; text-align: center; }
#kioskKeyMapViewLayer table tbody tr td div { height: 140px; width: 160px; text-align: center; }
#kioskKeyMapViewLayer table thead tr th, table tbody tr td { border:1px solid #ddd; }
</style>

<%-- 팝업 부분 설정 - width 는 강제 해주어야함.. 해결방법? 확인 필요 : 20180829 노현수 --%>
<wj-popup id="kioskKeyMapViewLayer" control="kioskKeyMapViewLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:630px;">
    <div class="wj-dialog wj-dialog-columns" ng-controller="kioskKeyMapViewCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <h3 class="fl" style="line-height:50px;"><s:message code="kioskKeyMap.kioskKeyMapView"/></h3>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body">

            <div class="sb-select" style="width:150px;">
                <wj-combo-box
                        id="tuClsTypeView"
                        ng-model="tuClsTypeView"
                        items-source="_getComboData('tuClsTypeView')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        control="tuClsTypeViewCombo"
                        selected-index-changed="changeTuClsType(s)">
                </wj-combo-box>
            </div>

            <table class="searchTbl mt10" style="border:1px solid #ddd; background-color: #d5d5d5">
                <colgroup>
                    <col class="w5"/>
                    <col class="w22"/>
                    <col class="w22"/>
                    <col class="w22"/>
                    <col class="w22"/>
                    <col class="w5"/>
                </colgroup>
                <thead>
                <tr>
                    <th id="thPre" rowspan="2" ng-click="thPre()">◀</th>
                    <th id="th0"><div id="tuCls0" ng-click="thClick('0')"></div></th>
                    <th id="th1"><div id="tuCls1" ng-click="thClick('1')"></div></th>
                    <th id="th2"><div id="tuCls2" ng-click="thClick('2')"></div></th>
                    <th id="th3"><div id="tuCls3" ng-click="thClick('3')"></div></th>
                    <th id="thNext" rowspan="2" ng-click="thNext()">▶</th>
                </tr>
                <tr>
                    <th id="th4"><div id="tuCls4" ng-click="thClick('4')"></div></th>
                    <th id="th5"><div id="tuCls5" ng-click="thClick('5')"></div></th>
                    <th id="th6"><div id="tuCls6" ng-click="thClick('6')"></div></th>
                    <th id="th7"><div id="tuCls7" ng-click="thClick('7')"></div></th>
                </tr>
                </thead>
            </table>
            <table class="searchTbl" style="border:1px solid #ddd;">
                <colgroup>
                    <col class="w5"/>
                    <col class="w30"/>
                    <col class="w30"/>
                    <col class="w30"/>
                    <col class="w5"/>
                </colgroup>
                <tbody>
                <tr>
                    <td id="tdPre" rowspan="4" ng-click="tdPre()">◀</td>
                    <td><div id="tuKey0" ng-click="tuKeyClick('0')"></div></td>
                    <td><div id="tuKey1" ng-click="tuKeyClick('1')"></div></td>
                    <td><div id="tuKey2" ng-click="tuKeyClick('2')"></div></td>
                    <td id="tdNext" rowspan="4" ng-click="tdNext()">▶</td>
                </tr>
                <tr>
                    <td><div id="tuKey3" ng-click="tuKeyClick('3')"></div></td>
                    <td><div id="tuKey4" ng-click="tuKeyClick('4')"></div></td>
                    <td><div id="tuKey5" ng-click="tuKeyClick('5')"></div></td>
                </tr>
                <tr>
                    <td><div id="tuKey6" ng-click="tuKeyClick('6')"></div></td>
                    <td><div id="tuKey7" ng-click="tuKeyClick('7')"></div></td>
                    <td><div id="tuKey8" ng-click="tuKeyClick('8')"></div></td>
                </tr>
                <tr>
                    <td><div id="tuKey9"  ng-click="tuKeyClick('9')"></div></td>
                    <td><div id="tuKey10" ng-click="tuKeyClick('10')"></div></td>
                    <td><div id="tuKey11" ng-click="tuKeyClick('11')"></div></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskKeyMap/kioskKeyMapView.js?ver=20241127.01" charset="utf-8"></script>
