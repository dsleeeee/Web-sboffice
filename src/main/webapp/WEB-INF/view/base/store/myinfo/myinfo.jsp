<%@ page pageEncoding="utf-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="orgnNm" value="${sessionScope.sessionInfo.orgnNm}" />

<%-- 우편번호 찾기 팝업 --%>
<%-- 선택한 주소를 부모창에 바인딩 하기 위해, 각 화면마다 구분자를 지정하여 element id명을 파악한다. --%>
<%-- jsp:param 방식은 API 호출 시, 파라미터 사용을 불허하기 때문에 호출이 거부됨. --%>
<input type="hidden" id="pageNm" value="myInfo" />
<%@ include file="/WEB-INF/view/application/layer/searchAddr.jsp" %>

<div class="subCon">
  <%--<form id="myInfoForm" method="post" action="/base/store/myInfo/myInfo/save.sb">--%>
    <h2 class="h2_tit oh lh30">
      <s:message code="myInfo.title1" />
      <div class="txtIn fr">
        <button class="btn_skyblue" onclick="envSetting()"><s:message code="myInfo.myInfo.envSetting" /></button>
        <button class="btn_skyblue" onclick="saveMyInfo()"><s:message code="cmm.save" /></button>
      </div>
    </h2>
    <table class="searchTbl mt10">
      <colgroup>
        <col class="w15" />
        <col class="w35" />
        <col class="w15" />
        <col class="w35" />
      </colgroup>
      <tbody>
        <tr class="brt">
          <%--본사코드 --%>
          <th><s:message code="myInfo.myInfo.hqOfficeCd" /></th>
          <td>
            <input id="hqOfficeCd" class="sb-input w100" type="text" value="${myInfo.hqOfficeCd}" disabled="disabled">
          </td>
          <%-- 본사명 --%>
          <th><s:message code="myInfo.myInfo.hqOfficeNm" /></th>
          <td>
            <input id="hqOfficeNm" class="sb-input w100" type="text" value="${myInfo.hqOfficeNm}" disabled="disabled">
          </td>
        </tr>
        <tr class="brt">
          <%-- 대표자명 --%>
          <th><s:message code="myInfo.myInfo.ownerNm" /></th>
          <td>
            <input id="ownerNm" name="ownerNm" class="sb-input w100" type="text" value="${myInfo.ownerNm}" maxlength="25">
          </td>
          <%--사업자번호 --%>
          <th><s:message code="hqManage.bizNo" /></th>
          <td>
            <input id="bizNo" type="text" class="sb-input w100" value="${myInfo.bizNo}" disabled="disabled">
          </td>
        </tr>
        <tr class="brt">
          <%-- 상호명 --%>
          <th><s:message code="myInfo.myInfo.bizStoreNm" /></th>
          <td>
            <input id="bizStoreNm" name="bizStoreNm" class="sb-input w100" type="text" value="${myInfo.bizStoreNm}" maxlength="25">
          </td>
          <%-- 전화번호 --%>
          <th><s:message code="myInfo.myInfo.telNo" /></th>
          <td>
            <input id="telNo" name="telNo" class="sb-input w100" type="text" value="${myInfo.telNo}" maxlength="15">
          </td>
        </tr>
        <tr class="brt">
          <%-- 날씨표시지역 --%>
          <th><s:message code="myInfo.myInfo.areaCd" /></th>
          <td>
              <select class="sb-select w30" id="areaCd" name="areaCd" type="text" value="${myInfo.areaCd}">
              <c:forEach items="${areaCds}" var="l">
                <option value="${l.nmcodeCd}">${l.nmcodeNm}</option>
              </c:forEach>
              </select>
          </td>
          <%-- 팩스번호 --%>
          <th><s:message code="myInfo.myInfo.faxNo" /></th>
          <td>
            <input id="faxNo" name="faxNo" class="sb-input w100" type="text" value="${myInfo.faxNo}" maxlength="15">
          </td>
        </tr>
        <tr class="brt">
          <%-- 홈페이지 --%>
          <th><s:message code="myInfo.myInfo.hmpgAddr" /></th>
          <td>
            <input id="hmpgAddr" name="hmpgAddr" class="sb-input w100" type="text" value="${myInfo.hmpgAddr}" maxlength="70">
          </td>
          <%-- 이메일 --%>
          <th><s:message code="myInfo.myInfo.emailAddr" /></th>
          <td>
            <input id="emailAddr" name="emailAddr" class="sb-input w100" type="text" value="${myInfo.emailAddr}" maxlength="50">
          </td>
        </tr>
        <tr class="brt">
          <%-- 주소 --%>
          <th><s:message code="myInfo.myInfo.addr" /></th>
          <td colspan="3">
            <input type="text" id="postNo" name="postNo" class="sb-input w80px" maxlength="5" placeholder="우편번호" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');" value="${myInfo.postNo}" readonly/>
            <a href="#" class="btn_grayS ml5" onclick="searchAddr()">
              <s:message code="myInfo.myInfo.addrSearch" />
            </a><br/>
            <%-- 주소상세 --%>
            <input type="text" class="sb-input w100" id="addr" name="addr" placeholder="주소1" style="margin:4px 0px;" value="${myInfo.addr}" readonly/>
            <input type="text" class="sb-input w100" id="addrDtl" name="addrDtl" placeholder="주소2" value="${myInfo.addrDtl}"/>
          </td>
        </tr>
        <tr>
          <%--상단 로고 이미지--%>
          <th><s:message code="myInfo.myInfo.image.title1" /></th>
          <td>
            <p class="shopimg fl">
              <span><s:message code="myInfo.myInfo.image.suggest" /></span>
              <f:form id="regForm" name="regForm" method="post" enctype="multipart/form-data">
                <input type="file" id="fileTitle" name="fileTitle" class="form-control" accept="image/x-png, .jpg, .gif" onchange="imagePreview(this, 'title')"/>
                <input type="hidden" id="hdTitleFileNm" />
              </f:form>
            </p>
            <div id="imgTitle" style="width:125px;height:25px;clear:both;border:1px solid #e8e8e8;overflow: hidden;"></div>
          </td>
        </tr>
        <tr class="brt" style="display: none;">
          <%--상단 로고 이미지--%>
          <th><s:message code="myInfo.myInfo.image.title1" /></th>
          <td>
            <p class="shopimg fl">
              <span><s:message code="myInfo.myInfo.image.suggest" /></span><br />
              <s:message code="myInfo.myInfo.image.width" /><br />
              <s:message code="myInfo.myInfo.image.height" /><br />
              <s:message code="myInfo.myInfo.image.types" /><br />
              <s:message code="myInfo.myInfo.image.size" /><br />
              <a href="#" class="btn_grayS mt5"><s:message code="myInfo.myInfo.image.button" /></a>
            </p>
            <div class="preImg fr">
              <span class="yes"><img src="/resource/solbipos/css/img/logo_main.png" alt="" /></span><!--등록한 이미지가 있는 경우-->
            </div>
          </td>
          <%--도장이미지--%>
          <th><s:message code="myInfo.myInfo.image.title2" /></th>
          <td>
            <p class="shopimg fl">
              <span><s:message code="myInfo.myInfo.image.suggest" /></span><br />
              <s:message code="myInfo.myInfo.image.width" /><br />
              <s:message code="myInfo.myInfo.image.height" /><br />
              <s:message code="myInfo.myInfo.image.types" /><br />
              <s:message code="myInfo.myInfo.image.size" /><br />
              <a href="#" class="btn_grayS mt5"><s:message code="myInfo.myInfo.image.button" /></a>
            </p>
            <div class="preImg fr">
              <span class="no">IMAGE</span><!--등록한 이미지가 없는 경우-->
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  <%--</form>--%>


<div style="display: none;">
  <%--기초코드관리--%>
  <h2 class="h2_tit mt40"><s:message code="myInfo.title2" /></h2>
  <div class="updownSet oh">
    <%-- 매장형태 --%>
    <span class="fl bk lh30"><s:message code="myInfo.storeType.title" /></span>
    <div class="txtIn">
      <button id="storeTypeAddRowBtn" name="addRowBtn" class="btn_skyblue"><s:message code="cmm.add" /></button>
      <button id="storeTypeDelRowBtn" name="delRowBtn" class="btn_skyblue"><s:message code="cmm.delete" /></button>
      <button id="storeTypeSaveBtn" name="saveBtn" class="btn_skyblue"><s:message code="cmm.save" /></button>
    </div>
  </div>
  <div id="storeTypeGrid" class="mt10"></div>
  <%-- 매장그룹 --%>
  <div class="updownSet mt40 oh">
    <span class="fl bk lh30"><s:message code="myInfo.grp.title" /></span>
    <div class="txtIn">
      <button id="grpAddRowBtn" name="addRowBtn" class="btn_skyblue"><s:message code="cmm.add" /></button>
      <button id="grpDelRowBtn" name="delRowBtn" class="btn_skyblue"><s:message code="cmm.delete" /></button>
      <button id="grpSaveBtn" name="saveBtn" class="btn_skyblue"><s:message code="cmm.save" /></button>
    </div>
  </div>
  <div id="grpGrid" class="mt10"></div>
  <%-- 시간대분류 --%>
  <div class="updownSet mt40 oh">
    <span class="fl bk lh30"><s:message code="myInfo.hour.title" /></span>
    <div class="txtIn">
      <button id="hourAddRowBtn" name="addRowBtn" class="btn_skyblue"><s:message code="cmm.add" /></button>
      <button id="hourDelRowBtn" name="delRowBtn" class="btn_skyblue"><s:message code="cmm.delete" /></button>
      <button id="hourSaveBtn" name="saveBtn" class="btn_skyblue"><s:message code="cmm.save" /></button>
    </div>
  </div>
  <div id="hourGrid" class="mt10"></div>
  <%-- 고객수 구분 --%>
  <div class="updownSet mt40 oh">
    <span class="fl bk lh30"><s:message code="myInfo.guest.title" /></span>
    <div class="txtIn">
      <button id="guestAddRowBtn" name="addRowBtn" class="btn_skyblue"><s:message code="cmm.add" /></button>
      <button id="guestDelRowBtn" name="delRowBtn" class="btn_skyblue"><s:message code="cmm.delete" /></button>
      <button id="guestSaveBtn" name="saveBtn" class="btn_skyblue"><s:message code="cmm.save" /></button>
    </div>
  </div>
  <div id="guestGrid" class="mt10"></div>
</div>

</div>

<script>
    var orgnCd      = "${orgnCd}";

function generateWijmoComponent( elementIds ){
  var dropComponent = wijmo.input.ComboBox,
      textComponent = wijmo.input.InputMask,
      component = {};

  elementIds.forEach(function( id ){
    var selector = "#" + id,
        elem = doc.getElementById( id ),
        type = elem.nodeName,
        value = elem.getAttribute( "value" ),
        isDisabled = elem.hasAttribute( "disabled" );

    //console.log( elem, type, value );

    if( type === "SELECT" ){
      component[ id ] = new dropComponent( selector, {
        selectedValue: value,
        isAnimated: true,
        isEditable: false,
        isDisabled: isDisabled
      });
    }else{
      component[ id ] = new textComponent( selector, {
        value: value,
        isDisabled: isDisabled
      });
    }
  });

  return component;
}

function getComponentParameters( componentForm ){
  var params = {};

  for( var key in componentForm ){
    var component = componentForm[ key ];

    //console.log( key, component, component.isDisabled );

    if( !component.isDisabled ){
      params[ key ] = component.selectedValue || component.value;
    }
  }

  return params;
}

function arrayCopy( items ){
  var arr = [];

  items instanceof Array && items.forEach(function( item ){ arr.push( item ); });
  return arr;
}

function imagePreview(imgVal, imgFg){
  var reader = new FileReader();
  reader.onload = function(e) {
    if(imgFg === 'title') $("#imgTitle").html("<img src='" +  e.target.result + "' class='imgPic'>");
  };
  reader.readAsDataURL(imgVal.files[0]);

  var fileSize = 0;
  var element = "";
  var errMsg = "";

  if(imgFg === "title"){
    fileSize = 1024;
    element = "fileTitle";
    errMsg = messages["myInfo.fileSizeChk.1024.msg"];
  }
  // 상품이미지 크기제한 체크
  if (!isNull($("#" + element)[0].files[0])) {
    var maxSize = fileSize * 1024;
    var fileSize = $("#" + element)[0].files[0].size;
    if (fileSize > maxSize) {
      s_alert.pop(errMsg);
      return;
    }
  }

  // 이미지 파일 여부 체크
  if (isNull($("#" + element)[0].files[0])) {
    errMsg = messages["myInfo.require.msg"];
    s_alert.pop(errMsg);
    return;
  }

  // 이미지명 형식 체크
  var imgFullNm = $("#" + element).val().substring($("#" + element).val().lastIndexOf('\\') + 1);
  if(1 > imgFullNm.lastIndexOf('.')){
    s_alert.pop(messages["myInfo.fileNmChk.msg"]);
    return;
  }

  // 이미지(.png) 확장자 체크
  var reg = /(.*?)\.(png|PNG|jpg|JPG|gif|GIF)$/;

  if(! $("#" + element).val().match(reg)) {
    s_alert.pop(messages["myInfo.fileExtensionChk.msg"]);
    return;
  }

  // 이미지를 등록하시겠습니까?
  var msg = messages["myInfo.fileReg.msg"];
  s_alert.popConf(msg, function () {

    // var formData = new FormData($("#regForm")[0]);

    var file = $("#" + element)[0].files[0];

      // ajax로 전달할 폼 객체
      var formData = new FormData();
      // 폼 객체에 파일추가, append("변수명", 값)
      formData.append("file", file);
      formData.append("newFileName", orgnCd);
      console.log(orgnCd);

    $.ajax({
      url: "/base/store/myInfo/titleImg/saveTitleImg.sb",
      type: "POST",
      data: formData,
      processData: false,
      contentType: false,
      cache: false,
      success: function(result) {
        if (result.status === "OK") {
          s_alert.pop("등록되었습니다.");
        }
        else if (result.status === "FAIL") {
          var msg = result.status + " : " + result.data.msg;
          s_alert.pop(msg);
          // $scope.$broadcast('loadingPopupInactive');
        }
        else if (result.status === "SERVER_ERROR") {
          s_alert.pop(result.message);
          // $scope.$broadcast('loadingPopupInactive');
        }
        else {
          var msg = result.status + " : " + result.message;
          s_alert.pop(msg);
          // $scope.$broadcast('loadingPopupInactive');
        }
      },
      error : function(result){
        s_alert.pop("error");
        // $scope.$broadcast('loadingPopupInactive');
      }
    },function(){
      s_alert.pop("Ajax Fail By HTTP Request 2");
      // $scope.$broadcast('loadingPopupInactive');
    });
  });
}

var win = window,
    doc = document,
    /*myInfoFormComponent = generateWijmoComponent([
      "hqOfficeCd", "hqOfficeNm", "ownerNm", "bizNo", "bizStoreNm",
      "telNo", "faxNo", "areaCd", "emailAddr", "hmpgAddr"
    ]),*/
    gridIds = [ "storeType", "grp", "hour", "guest" ];

/*$( "#myInfoForm" ).submit(function( e ){
  e.preventDefault();

  $.postJSONSave( this.action,
    getComponentParameters( myInfoFormComponent ),
    function( result ){ win.s_alert.pop( messages["cmm.modify"] ); },
    function( result ){ win.s_alert.pop( messages["cmm.modify.fail"] ); }
  );
});*/

// 기초정보 저장
function saveMyInfo(){

  var url = "/base/store/myInfo/myInfo/save.sb";
  var msg = "";
  var params = {};

  params.hqOfficeCd = $("#hqOfficeCd").val();
  params.ownerNm = $("#ownerNm").val();
  params.bizStoreNm = $("#bizStoreNm").val();
  params.telNo = $("#telNo").val();
  params.faxNo = $("#faxNo").val();
  params.areaCd = $("#areaCd").val();
  params.hmpgAddr = $("#hmpgAddr").val();
  params.emailAddr = $("#emailAddr").val();
  params.postNo = $("#postNo").val();
  params.addr = $("#addr").val();
  params.addrDtl = $("#addrDtl").val();

  $.ajax({
    type: "POST",
    url: url,
    data:  JSON.stringify(params),
    success: function(result){
      if (result.status === "OK") {
        msg = "<s:message code='cmm.saveSucc' />";
      }else{
        msg = "<s:message code='cmm.saveFail' />";
      }
      s_alert.popOk(msg, function() {});
    },
    cache: false,
    dataType: "json",
    contentType : 'application/json'
  });

}

// 환경설정 팝업
function envSetting(){
  openEnvSettingLayer($("#hqOfficeCd").val(), $("#hqOfficeNm").val());
}

gridIds.forEach(function( gridId ){
  var selector = "#" + gridId,
      gridSelector = selector + "Grid",
      addBtnSelector = selector + "AddRowBtn",
      delBtnSelector = selector + "DelRowBtn",
      saveBtnSelector = selector + "SaveBtn",
      listURL = "/base/store/myInfo/" + gridId + "/list.sb",
      saveURL = "/base/store/myInfo/" + gridId + "/save.sb";

  $.postJSON( listURL ).then(function( gridInfo ){
    var grid = wgrid.genGrid( gridSelector, gridInfo.columns),
        view = new wijmo.collections.CollectionView( gridInfo.rows ),
        rowsCount = gridInfo.rows.length;

    grid.itemsSource = view;
    grid.isReadOnly = false;
    <%-- 이미 등록된 코드는 수정하지 못하도록 해야함 --%>
    grid.beginningEdit.addHandler(function( s, e ){
      s._cols[e.col].binding === "nmcodeCd" && ( e.row + 1 ) <= rowsCount && ( e.cancel = true );
    });
    <%-- 변경사항을 view.itemsXXX 배열에 기록 --%>
    view.trackChanges = true;
    <%-- 등록버튼 --%>
    $( addBtnSelector ).click(function( e ){
      var newRow = view.addNew();

      newRow.gChk = false;
      view.commitNew();
      //console.log( "added : ", newRow );
    });
    <%-- 삭제버튼 --%>
    $( delBtnSelector ).click(function( e ){
      <%-- 체크된 열만 삭제 --%>
      for( var li = 0, limit = view.itemCount; li < limit; ){
        var item = view.items[ li ];

        if( item.gChk ){
          view.remove( item );
          limit--;
          //console.log( "deleted : ", item );
        }else{
          li++;
        }
      }
    });
    <%-- 저장버튼 --%>
    $( saveBtnSelector ).click(function( e ){
      <%-- itemsXXX 는 순수 배열이 아니라서 배열로 넘기도록 처리 --%>
      $.postJSONSave( saveURL, {
          addItems: arrayCopy( view.itemsAdded ),
          modItems: arrayCopy( view.itemsEdited ),
          delItems: arrayCopy( view.itemsRemoved )
        },
        function( result ){ s_alert.pop( messages["cmm.saveSucc"] ); },
        function( result ){ s_alert.pop( result.message ); }
      );
    });
  });
});
</script>

<%-- 환경설정 --%>
<c:import url="/WEB-INF/view/store/hq/hqManage/config.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
  <c:param name="orgnFg" value="${orgnFg}"/>
  <c:param name="orgnCd" value="${orgnCd}"/>
  <c:param name="orgnNm" value="${orgnNm}"/>
</c:import>
