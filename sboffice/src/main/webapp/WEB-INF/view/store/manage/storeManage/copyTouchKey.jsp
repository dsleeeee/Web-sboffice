<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--

터치키 관련 개발 되고나서 진행해야 함.

1. 사용자가 시스템관리자 - 특정 매장의 터치키를 특정본사 또는 특정매장의 터치로 복사함.
2.         매장         - 자기 터치키를 자기 본사 또는 자기 프랜차이즈의 타 매장에서 복사함.

기존로직

1. 소스매장 포스프로그램 및 터치 줄수 조회
2. 타겟매장 포스프로그램 조회
3. 타겟매장 터치 줄수 조회
4. 로그인 사용자가 본사 - 자기 터치키를 자기 프랜차이즈 매장 전체에 복사
  4.1. 소스매장 터치 줄수, 터치분류 줄수 검색
  4.2. 본사에 등록되어 있는 매장 조회
  4.3. 저장

 --%>
<div id="touchKeyCopyArea" style="display:none;">

  <ul class="txtSty2 mt10 pdb20">
    <li>복사할 소스매장을 선택해 주십시오.<br />
      <p>(매장을 선택하지 않으면 본사의 터치키가 선택됩니다.)</p>
    </li>
  </ul>
  <%-- 
  <select id="copyTouchKeyHq" class="wj-TblWrapBr"></select>
  <select id="copyTouchKeyBrand"></select>
  <select id="copyTouchKeyStore"></select>
  --%>
  <%-- 본사 --%>
  <div class="sb-select w40">
    <div id="copyTouchKeyHq"></div>
  </div>
  <%-- 브랜드 --%>
  <div class="sb-select w40">
    <div id="copyTouchKeyBrand"></div>
  </div>
  <%-- 매장 --%>
  <div class="sb-select w40">
    <div id="copyTouchKeyStore"></div>
  </div>
  
  <div id="viewBtnArea"class="mt10 tc">
    <%-- 저장 --%>
    <button class="btn_skyblue" id="btnDel"><s:message code="cmm.delete" /></button>
  </div>
  
  <ul class="txtSty2 mt10 pdb20">
    <li class="mt10">타겟매장의 상품마스터가 있는 자료만 복사됩니다. </li>
    <li class="mt10">매장을 선택하지 않은 경우 본사의 터치가 복사됩니다. </li>
    <li class="mt10">같은 프렌차이즈의 매장을 소스매장으로 선택하는 것을 권장합니다. </li>
    <li class="mt10">프랜차이즈가 다르거나 단독매장일 경우 상품마스터가 동일한 경우에만 복사하십시오. </li>
  </ul>
</div>

<script>
<%-- 콤보박스 초기화 --%>
var copyTouchKeyHq    = wcombo.genCommonBox("#copyTouchKeyHq", null);
var copyTouchKeyBrand = wcombo.genCommonBox("#copyTouchKeyBrand", null);
var copyTouchKeyStore = wcombo.genCommonBox("#copyTouchKeyStore", null);

<%-- 주방프린터-상품등록 영역 보여줌 --%>
function showCopyTouchKeyLayout(){
  $("#touchKeyCopyArea").show();
  getHqList();
}

<%-- 본사 목록 조회 --%>
function getHqList(){
  var param = {};
  $.postJSON("/store/manage/storeManage/storeManage/getHqList.sb", param, function(result) {
    if(result.status === "FAIL") {
      s_alert.pop(result.message);
      return;
    }
    copyTouchKeyHq.itemsSource = result.data.list;
  })
  .fail(function(){
    s_alert.pop("Ajax Fail");
  });
}

<%-- 본사 선택시 브랜드 목록 조회  --%>
copyTouchKeyHq.selectedIndexChanged.addHandler(function(s, e){
  var param = {};
  param.hqOfficeCd = copyTouchKeyHq.selectedValue;
  
  $.postJSON("/store/manage/storeManage/storeManage/getHqBrandList.sb", param, function(result) {
    
    if(result.status === "FAIL") {
      s_alert.pop(result.message);
      return;
    }
    copyTouchKeyBrand.itemsSource = result.data.list;
  })
  .fail(function(){
      s_alert.pop("Ajax Fail");
  });
});

<%-- 브랜드 선택시 매장 목록 조회  --%>
copyTouchKeyBrand.selectedIndexChanged.addHandler(function(s, e){
  var param = {};
  param.hqOfficeCd  = copyTouchKeyHq.selectedValue;
  param.hqBrandCd   = copyTouchKeyBrand.selectedValue;
  
  $.postJSON("/store/manage/storeManage/storeManage/getTouchKeyStoreList.sb", param, function(result) {
    
    if(result.status === "FAIL") {
      s_alert.pop(result.message);
      return;
    }
    copyTouchKeyStore.itemsSource = result.data.list;
  })
  .fail(function(){
      s_alert.pop("Ajax Fail");
  });
});

<%-- 주방프린터-상품등록 레이아웃 보이지 않기--%>
function hideCopyTouchKeyLayout() {
  $("#touchKeyCopyArea").hide();
}

</script>