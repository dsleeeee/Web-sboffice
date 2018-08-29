<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

  <!-- dialog used to pick and re-order grid columns -->
    <wj-popup control="pickColumn" show-trigger="Click" hide-trigger="Click" style="display: none;">
      <div class="wj-dialog wj-dialog-columns">
        <div class="wj-dialog-header wj-dialog-header-font">
          컬럼을 선택하세요.
          <a href="javascript:;" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body">
          <div class="text-info mb10">
            표시하려는 열을 오른쪽의 목록으로 드래그하십시오.<br/>
            원하지 않는 항목을 왼쪽의 목록으로 드래그하십시오.<br/>
            오른쪽의 목록에서 드래그하여 열의 순서를 변경하십시오.<br/>
            그리드에 변경 사항을 적용하려면 "적용"버튼을 클릭하십시오.
          </div>
          <div id="<c:out value="${param.pickerTarget}" />"></div>
        </div>
        <div class="wj-dialog-footer">
          <button class="btn wj-hide-apply btn_blue">적용</button>
          <button class="btn wj-hide btn_gray">취소</button>
        </div>
      </div>
    </wj-popup>
    <!--//dialog used to pick and re-order grid columns -->

