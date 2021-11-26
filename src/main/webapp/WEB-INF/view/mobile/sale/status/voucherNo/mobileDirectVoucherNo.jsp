<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<style type="text/css">
    .cusWrap {width: 100%; min-height: 568px; height: 100%; display: table;}
    /*.cusWrap {width: 760px; min-height: 590px; height: 100%; display: table;}*/
    .content-middle {width: 100%; height: 100%; display: table-cell; vertical-align: middle;}
    /*.cusTitle {display:block; width:100%; height:100%; line-height:45px; color:#337dde;  padding:0 15px; font-size:0.875em; position:relative;}*/
</style>


<body ng-app="rootApp" ng-controller="rootCtrl">
<div class="cusWrap">
    <div class="content-middle" ng-controller="mobileVoucherNoCtrl">
    <div class="searchBar">
        <%-- 최종교환권번호 --%>
        <a href="#" class="fl"><s:message code="mobile.voucherNo"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('mobileVoucherNoCtrl')">
            <s:message code="mobile.cmm.search"/>
        </button>
    </div>
    <div class="oh sb-select dkbr">
        <div class="wj-TblWrapBr" style="height:100%;">
            <div id="divRank">
                <table class='tblType01'>
                    <colgroup>
                        <col width='100%' />
                    </colgroup>
                    <tbody>
                        <tr>
                            <th style='text-align: center; font-size: 2.0em;'>
                                <s:message code="mobile.voucherNo"/>
                            </th>
                        </tr>
                        <tr>
                            <td style='text-align: center; padding:30px; font-size: 7.0em;'>
                                <label id="lblVoucherNo"></label>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
    </div>
</div>
    </div>
</div>
</body>

<script type="text/javascript">
    var userId = "${userId}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/sale/status/voucherNo/mobileVoucherNo.js?ver=20211117.01" charset="utf-8"></script>