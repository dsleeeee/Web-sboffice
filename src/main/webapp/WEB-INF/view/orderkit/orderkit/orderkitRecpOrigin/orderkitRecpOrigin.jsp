<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="subCon" ng-controller="orderkitRecpOriginCtrl">
    <%--searchTbl--%>
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="orderkit.orderkitRecpOrigin"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue mr3" id="nxBtnSearch3" ng-click="_broadcast('orderkitRecpOriginCtrl',1)">
                <s:message code="cmm.search"/>
            </button>
        </div>
    </div>

    <div class="mt10 oh sb-select dkbr">
        <div class="updownSet oh">
            <%-- 오더킷 바로가기 --%>
            <button class="btn_skyblue" id="orderkitGoto" ng-click="orderkitGoto()"><s:message code="orderkit.orderkitGoto"/></button>
        </div>
    </div>

    <%-- left --%>
    <div class="wj-TblWrap mt10 mb20 w50 fl">
        <div class="wj-TblWrapBr mr10 pd10" style="height:530px;">
            <div class="tc b4" style="background-color:#ebf5ff">
                <table class="tblType01">
                    <colgroup>
                        <col class="w100"/>
                    </colgroup>
                    <tbody>
                    <th class="tc">
                        <label>원산지관리</label>
                    </th>
                    <tr>
                        <td class="lh30">
                            <textarea id="recpOriginInfoDetail" class="w100" cols="42" style="height:450px;resize:none;"></textarea>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <%--left--%>

    <%--right--%>
    <div class="wj-TblWrap mt10 mb10 w50 fr">
        <div class="wj-TblWrapBr pd10" style="height:530px;">
            ▶ 원산지 정보는 오더킷 사이트에서 설정해 주세요.
        </div>
    </div>
</div>

<script type="text/javascript">

    $(document).ready(function () {

        // 원산지 정보 조회
        var scope = agrid.getScope("orderkitRecpOriginCtrl");
        scope.searchRecpOrigin();

    });

</script>


<script type="text/javascript" src="/resource/solbipos/js/orderkit/orderkit/orderkitRecpOrigin/orderkitRecpOrigin.js?ver=20260114.01" charset="utf-8"></script>