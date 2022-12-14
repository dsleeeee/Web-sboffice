<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup id="dayCloseDtlLayer" control="dayCloseDtlLayer" show-trigger="Click" hide-trigger="Click" style="overflow-x:auto; overflow-y: auto;display:none;width:1000px;">
  <div class="wj-dialog wj-dialog-columns title" ng-controller="dayCloseDtlCtrl">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="saleRegistKwu.newRegist"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

  <div class="wj-dialog-body">
    <h3 class="h3_tbl brt" style="height: 35px;">
        <span id="dayCloseDtlTitle" class="fl"></span>
        <button class="btn_skyblue ml5 fr" id="closeCancel" ng-click="close('0')" ><s:message code="dayClose.closeCancel" /></button>
        <button class="btn_skyblue ml5 fr" id="close" ng-click="close('1')" ><s:message code="dayClose.close" /></button>
        <button class="btn_skyblue ml5 fr" id="btnSearch" ng-click="getDayCloseDtl()" ><s:message code="dayClose.closeDataRecv"/></button>
    </h3>
    <div class="tblBr">
      <table class="searchTbl">
      <colgroup>
        <col class="w8"/>
        <col class="w17"/>
        <col class="w8"/>
        <col class="w17"/>
        <col class="w8"/>
        <col class="w17"/>
        <col class="w8"/>
        <col class="w17"/>
      </colgroup>
      <tbody>
      <tr>
        <%-- 개점일자 --%>
        <th><s:message code="dayClose.openDate"/></th>
        <td>
          <div class="sb-select">
            <span class="txtIn"><input id="openDate" class="w110px" readonly></span>
          </div>
        </td>
        <%-- 마감일자 --%>
        <th><s:message code="dayClose.closeDate"/></th>
        <td>
          <div class="sb-select">
            <span class="txtIn"><input id="closeDate" class="w110px" readonly></span>
          </div>
        </td>
        <th><s:message code="dayClose.interestAmt"/></th>
        <td><input type="text" class="sb-input w100" id="interestAmt" ng-model="interestAmt" numberOnly/></td>
      </tr>
      </tbody>
    </table>
    </div>
    <h3 class="h3_tbl"><s:message code="dayClose.monthInAmtSum"/></h3>
    <div class="tblBr">
      <table class="searchTbl">
        <colgroup>
          <col class="w16"/>
          <col class="w34"/>
          <col class="w16"/>
          <col class="w34"/>
        </colgroup>
        <tbody>
          <tr>
            <th><s:message code="dayClose.inAmt"/></th>
            <td><input type="text" class="sb-input w100" id="inAmt" ng-model="inAmt" numberOnly/></td>
            <th><s:message code="dayClose.outAmt"/></th>
            <td><input type="text" class="sb-input w100" id="outAmt" ng-model="outAmt" numberOnly/></td>
          </tr>
        </tbody>
      </table>
    </div>
    <h3 class="h3_tbl"><s:message code="dayClose.inInfo"/></h3>
    <div class="tblBr">
      <table class="searchTbl">
        <colgroup>
          <col class="w8"/>
          <col class="w17"/>
          <col class="w8"/>
          <col class="w17"/>
          <col class="w8"/>
          <col class="w17"/>
          <col class="w8"/>
          <col class="w17"/>
        </colgroup>
        <tbody>
        <tr>
        <th><s:message code="dayClose.groupAmt"/></th>
        <td><input type="text" class="sb-input w100" id="groupAmt" ng-model="groupAmt" numberOnly/></td>
        <th><s:message code="dayClose.hockeyAmt"/></th>
        <td><input type="text" class="sb-input w100" id="hockeyAmt" ng-model="hockeyAmt" numberOnly/></td>
        <th><s:message code="dayClose.etcAmt"/></th>
        <td><input type="text" class="sb-input w100" id="etcAmt" ng-model="etcAmt" numberOnly/></td>
        <th><s:message code="dayClose.inDayAmt"/></th>
        <td><input type="text" class="sb-input w100" id="inDayAmt" ng-model="inDayAmt" numberOnly/></td>
      </tr>
      <tr>
        <th><s:message code="dayClose.inSum"/></th>
        <td><input type="text" class="sb-input w100" id="inSum" ng-model="inSum" numberOnly/></td>
        <th><s:message code="dayClose.inMonthSum"/></th>
        <td><input type="text" class="sb-input w100" id="inMonthSum" ng-model="inMonthSum" numberOnly/></td>
        <th><s:message code="dayClose.inBMonthSum"/></th>
        <td><input type="text" class="sb-input w100" id="inBMonthSum" ng-model="inBMonthSum" numberOnly/></td>
        <th><s:message code="dayClose.inTotalSum"/></th>
        <td><input type="text" class="sb-input w100" id="inTotalSum" ng-model="inTotalSum" numberOnly/></td>
      </tr>
        </tbody>
      </table>
    </div>
      <h3 class="h3_tbl"><s:message code="dayClose.outInfo"/></h3>
      <div class="tblBr">
      <table class="searchTbl">
        <colgroup>
          <col class="w8"/>
          <col class="w17"/>
          <col class="w8"/>
          <col class="w17"/>
          <col class="w8"/>
          <col class="w17"/>
          <col class="w8"/>
          <col class="w17"/>
        </colgroup>
        <tbody>
      <tr>
        <th><s:message code="dayClose.outSum"/></th>
        <td><input type="text" class="sb-input w100" id="outSum" ng-model="outSum" numberOnly/></td>
        <th><s:message code="dayClose.outMonthSum"/></th>
        <td><input type="text" class="sb-input w100" id="outMonthSum" ng-model="outMonthSum" numberOnly/></td>
        <th><s:message code="dayClose.outBMonthSum"/></th>
        <td><input type="text" class="sb-input w100" id="outBMonthSum" ng-model="outBMonthSum" numberOnly/></td>
        <th><s:message code="dayClose.outTotalSum"/></th>
        <td><input type="text" class="sb-input w100" id="outTotalSum" ng-model="outTotalSum" numberOnly/></td>
      </tr>
      <tr>
        <th><s:message code="dayClose.remark1"/></th>
        <td colspan="7"><input type="text" class="sb-input w100" id="remark1" ng-model="remark1"/></td>
      </tr>
      <tr>
        <th><s:message code="dayClose.remark2"/></th>
        <td colspan="7"><input type="text" class="sb-input w100" id="remark2" ng-model="remark2"/></td>
      </tr>
      <tr>
        <th><s:message code="dayClose.remark3"/></th>
        <td colspan="7"><input type="text" class="sb-input w100" id="remark3" ng-model="remark3"/></td>
      </tr>
      <tr>
        <th><s:message code="dayClose.remark4"/></th>
        <td colspan="7"><input type="text" class="sb-input w100" id="remark4" ng-model="remark4"/></td>
      </tr>
      <tr>
        <th><s:message code="dayClose.remark5"/></th>
        <td colspan="7"><input type="text" class="sb-input w100" id="remark5" ng-model="remark5"/></td>
      </tr>
      <tr >
        <th><s:message code="dayClose.remark6"/></th>
        <td colspan="7"><textarea class="sb-input w100" style="height:100px;resize: none;" id="remark6" ng-model="remark6"></textarea></td>
      </tr>
      </tbody>
    </table>
    </div>
  </div>
</div>

<script type="text/javascript">
  $(function(){
    $("input:text[numberOnly]").on("keyup", function() {
      $(this).val($(this).val().replace(/[^-|^0-9]/g,""));
      $(this).val($(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
    });
  });
</script>

<script type="text/javascript" src="/resource/solbipos/js/excclc/excclc/dayClose/dayCloseDtl.js?ver=20221213.01" charset="utf-8"></script>
