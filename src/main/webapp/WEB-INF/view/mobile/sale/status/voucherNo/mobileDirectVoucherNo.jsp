<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
    button{
        width:150px;
        height:50px;
    }
    tr, th, td{
        border-bottom: hidden;
    }
</style>

<meta name="viewport" content="width=device-width, user-scalable=no initial-scale=1.0">
<body ng-app="rootApp" ng-controller="rootCtrl">
    <div class="content-middle" ng-controller="mobileVoucherNoCtrl">
        <div class="oh sb-select dkbr">
            <div class="wj-TblWrapBr" style="height:100%;">
                <div id="divRank">
                    <table class='tblType01'>
                        <colgroup>
                            <col width='100%' />
                        </colgroup>
                        <tbody>
                            <tr>
                                <th style='text-align: center; font-size: 2.0em; padding:20px;'>
                                    <button class="btn_blue" id="btnSearch" ng-click="_broadcast('mobileVoucherNoCtrl')">
                                        <s:message code="mobile.cmm.search"/>
                                    </button>
                                </th>
                            </tr>
                            <tr>
                                <th style='text-align: center; font-size: 3.0em; padding: 20px;'>
                                    <s:message code="mobile.voucherNo"/>
                                </th>
                            </tr>
                            <tr>
                                <td style='text-align: center; padding:30px; font-size: 11.5em;'>
                                    <label id="lblVoucherNo"></label>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>

<script type="text/javascript">
    var userId = "${userId}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/sale/status/voucherNo/mobileVoucherNo.js?ver=20211117.01" charset="utf-8"></script>