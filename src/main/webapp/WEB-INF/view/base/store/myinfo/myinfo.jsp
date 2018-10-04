<%@ page pageEncoding="utf-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<div class="subCon">
  <form id="myInfoForm" method="post" action="/base/store/myInfo/myInfo/save.sb">
    <h2 class="h2_tit oh lh30">
      <s:message code="myInfo.title1" />
      <div class="txtIn fr">
        <button class="btn_skyblue"><s:message code="cmm.save" /></button>
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
            <div class="sb-select">
              <input id="hqOfficeCd" type="text" value="${myInfo.hqOfficeCd}" disabled="disabled">
            </div>
          </td>
          <%-- 본사명 --%>
          <th><s:message code="myInfo.myInfo.hqOfficeNm" /></th>
          <td>
            <div class="sb-select">
              <input id="hqOfficeNm" type="text" value="${myInfo.hqOfficeNm}" disabled="disabled">
            </div>
          </td>
        </tr>
        <tr class="brt">
          <%-- 대표자명 --%>
          <th><s:message code="myInfo.myInfo.ownerNm" /></th>
          <td>
            <div class="sb-select">
              <input id="ownerNm" name="ownerNm" type="text" value="${myInfo.ownerNm}" maxlength="25">
            </div>
          </td>
          <%--사업자번호 --%>
          <th><s:message code="hqManage.bizNo" /></th>
          <td>
            <div class="sb-select">
              <input id="bizNo" type="text" value="${myInfo.bizNo}" disabled="disabled">
            </div>
          </td>
        </tr>
        <tr class="brt">
          <%-- 상호명 --%>
          <th><s:message code="myInfo.myInfo.bizStoreNm" /></th>
          <td>
            <div class="sb-select">
              <input id="bizStoreNm" name="bizStoreNm" type="text" value="${myInfo.bizStoreNm}" maxlength="25">
            </div>
          </td>
          <%-- 전화번호 --%>
          <th><s:message code="myInfo.myInfo.telNo" /></th>
          <td>
            <div class="sb-select">
              <input id="telNo" name="telNo" type="text" value="${myInfo.telNo}" maxlength="15">
            </div>
          </td>
        </tr>
        <tr class="brt">
          <%-- 날씨표시지역 --%>
          <th><s:message code="myInfo.myInfo.areaCd" /></th>
          <td>
            <div class="sb-select">
              <select id="areaCd" name="areaCd" type="text" value="${myInfo.areaCd}">
              <c:forEach items="${areaCds}" var="l">
                <option value="${l.nmcodeCd}">${l.nmcodeNm}</option>
              </c:forEach>
              </select>
            </div>
          </td>
          <%-- 팩스번호 --%>
          <th><s:message code="myInfo.myInfo.faxNo" /></th>
          <td>
            <div class="sb-select">
              <input id="faxNo" name="faxNo" type="text" value="${myInfo.faxNo}" maxlength="15">
            </div>
          </td>
        </tr>
        <tr class="brt">
          <%-- 홈페이지 --%>
          <th><s:message code="myInfo.myInfo.hmpgAddr" /></th>
          <td>
            <div class="sb-select">
              <input id="hmpgAddr" name="hmpgAddr" type="text" value="${myInfo.hmpgAddr}" maxlength="70">
            </div>
          </td>
          <%-- 이메일 --%>
          <th><s:message code="myInfo.myInfo.emailAddr" /></th>
          <td>
            <div class="sb-select">
              <input id="emailAddr" name="emailAddr" type="text" value="${myInfo.emailAddr}" maxlength="50">
            </div>
          </td>
        </tr>
        <tr class="brt">
          <%-- 주소 --%>
          <th><s:message code="myInfo.myInfo.addr" /></th>
          <td colspan="3">
            <input type="text" class="sb-input w30 mb5" />
            <a href="#" class="btn_grayS ml5"><s:message code="myInfo.myInfo.addrSearch" /></a>
            <br>
            <%-- 주소상세 --%>
            <div class="sb-select">
              <input id="addr" name="addr" type="text" value="${myInfo.addr}" maxlength="100">
            </div>
          </td>
        </tr>
        <tr class="brt">
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
  </form>
  <%-- 매장형태 --%>
  <h2 class="h2_tit mt40"><s:message code="myInfo.title2" /></h2>
  <div class="updownSet oh">
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
<script>
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

var win = window,
    doc = document,
    myInfoFormComponent = generateWijmoComponent([
      "hqOfficeCd", "hqOfficeNm", "ownerNm", "bizNo", "bizStoreNm",
      "telNo", "faxNo", "areaCd", "emailAddr", "hmpgAddr", "addr"
    ]),
    gridIds = [ "storeType", "grp", "hour", "guest" ];

$( "#myInfoForm" ).submit(function( e ){
  e.preventDefault();

  $.postJSONSave( this.action,
    getComponentParameters( myInfoFormComponent ),
    function( result ){ win.s_alert.pop( messages["cmm.modify"] ); },
    function( result ){ win.s_alert.pop( messages["cmm.modify.fail"] ); }
  );
});

gridIds.forEach(function( gridId ){
  var selector = "#" + gridId,
      gridSelector = selector + "Grid",
      addBtnSelector = selector + "AddRowBtn",
      delBtnSelector = selector + "DelRowBtn",
      saveBtnSelector = selector + "SaveBtn",
      listURL = "/base/store/myInfo/" + gridId + "/list.sb",
      saveURL = "/base/store/myInfo/" + gridId + "/save.sb";

  $.getJSON( listURL ).then(function( gridInfo ){
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
