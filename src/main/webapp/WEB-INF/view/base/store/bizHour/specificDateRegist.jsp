<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<wj-popup id="wjSpeRegistLayer" control="wjSpeRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:950px;">
  <div id="speRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="speRegistCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="bizHour.specificDate"/> &nbsp;<s:message code="cmm.new.add"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body">
      <div class="mt10 oh sb-select dkbr">
        <p class="tl s13 mb10 ml5 fl">* <s:message code="bizHour.specificDate.regMsg"/> </p>
      </div>
      <form id="speForm" ng-submit="submitForm()">
        <table class="tblType01">
          <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
          </colgroup>
          <tbody>
          <tr>
            <%-- 특정일 --%>
            <th><s:message code="bizHour.specificDate"/><em class="imp">*</em></th>
            <td>
              <div class="sb-select">
                <span class="txtIn"><input id="bizDate" class="w200px" ng-model="speData.bizDate"></span>
              </div>
            </td>
            <c:if test="${orgnFg == 'HQ'}">
              <%-- 매장선택 --%>
              <th><s:message code="cmm.store.select"/><em class="imp">*</em></th>
              <td>
                <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                  <jsp:param name="targetTypeFg" value="S"/>
                  <jsp:param name="targetId" value="speRegistStore"/>
                </jsp:include>
              </td>
            </c:if>
          </tr>
          <tr>
            <%-- 영업구분 --%>
            <th><s:message code="bizHour.bizFg"/><em class="imp">*</em></th>
            <td>
              <div class="sb-select w50 fl">
                <wj-combo-box
                        id="speBizFg"
                        ng-model="speData.bizFg"
                        items-source="_getComboData('speBizFgCombo')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        control="speBizFgCtrl">
                </wj-combo-box>
              </div>
            </td>
              <%-- 영업시간 --%>
            <th><s:message code="bizHour.bizTime"/><em class="imp">*</em></th>
            <td>
              <div class="sb-select w20 fl">
                <wj-combo-box id="speStartHour" ng-model="speData.startHour" items-source="_getComboData('speStartHourCombo')" display-member-path="name" selected-value-path="value" is-editable="false" control="speStartHourCtrl"></wj-combo-box>
              </div>
              <div class="fl pd5 s14"><label> : </label></div>
              <div class="sb-select w20 fl">
                <wj-combo-box id="speStartMs" ng-model="speData.startMs" items-source="_getComboData('speStartMsCombo')" display-member-path="name" selected-value-path="value" is-editable="false" control="speStartMsCtrl"></wj-combo-box>
              </div>
              <div class="fl pd5 s14"><label> ~ </label></div>
              <div class="sb-select w20 fl">
                <wj-combo-box id="speEndHour" ng-model="speData.endHour" items-source="_getComboData('speEndHourCombo')" display-member-path="name" selected-value-path="value" is-editable="false" control="speEndHourCtrl"></wj-combo-box>
              </div>
              <div class="fl pd5 s14"><label> : </label></div>
              <div class="sb-select w20 fl">
                <wj-combo-box id="speEndMs" ng-model="speData.endMs" items-source="_getComboData('speEndMsCombo')" display-member-path="name" selected-value-path="value" is-editable="false" control="speEndMsCtrl"></wj-combo-box>
              </div>
            </td>
          </tr>
          <tr>
            <th><s:message code="bizHour.remark"/></th>
            <td colspan="3">
              <div>
                <textarea id="speRemark" class="w100 tArea1" style="height:100px;" ng-model="speData.remark" maxlength="200"></textarea>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
        <div class="mt10 pdb20 oh bb">
          <button type="submit" id="btnSpeRegistSave" class="btn_blue fr"><s:message code="cmm.save"/></button>
        </div>
      </form>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/store/bizHour/specificDateRegist.js?ver=20261001.03" charset="utf-8"></script>
