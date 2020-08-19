<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<wj-popup id="wjVendrExactRegistLayer" control="wjVendrExactRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;">
  <div id="vendrExactRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="vendrExactRegistCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="vendrExact.reg.title"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body">
      <form id="vendrExactForm" ng-submit="submitForm()" novalidate>
        <table class="tblType01">
          <colgroup>
            <col class="w15"/>
            <col class="w25"/>
            <col class="w10"/>
            <col class="w50"/>
          </colgroup>
          <tbody>
          <tr>
            <th><s:message code="vendrExact.reg.excclcDate"/></th>
            <td>
              <div class="sb-select">
                <span class="txtIn"><input id="excclcDate" class="w120px" ng-disabled="excclcDateFg" ng-model="vendrExact.excclcDate"></span>
              </div>
            </td>
            <th><s:message code="vendrExact.reg.vendr"/></th>
            <td>
              <%-- 거래처선택 모듈 싱글 선택 사용시 include
                   param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                closeFunc - 팝업 닫기시 호출할 함수
              --%>
              <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrS.jsp" flush="true">
                <jsp:param name="targetId" value="vendrExactRegistSelectVendr"/>
              </jsp:include>
              <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
            </td>
          </tr>
          <tr>
            <th><s:message code="vendrExact.reg.excclcTot"/></th>
            <td>
              <input type="text" id="excclcTot" name="excclcTot" ng-model="vendrExact.excclcTot" class="sb-input w150px" maxlength="8"
                     data-check="1,10,number" ng-required="true" ng-blur="excclcTotOnBlur($event)" ng-focus="excclcTotOnFocus($event)"/>
            </td>
          </tr>
          <tr>
            <th><s:message code="vendrExact.reg.remark"/></th>
            <td colspan="3">
              <div>
                <textarea id="remark" class="w100 tArea1" style="height:100px;" ng-model="vendrExact.remark" data-check="1,10"></textarea>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
        <div class="mt10 pdb20 oh bb fr">
          <!-- 저장 -->
          <button type="submit" id="btnSave" class="btn_blue">
            <s:message code="cmm.save"/></button>
          <!-- 삭제 -->
          <button type="button" id="btnDelete" class="btn_blue" ng-if="btnDeleteIfFg" ng-click="delete()">
            <s:message code="cmm.delete"/></button>
        </div>
      </form>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/vendr/vendrExact/vendrExactRegist.js?ver=20181224.01" charset="utf-8"></script>
