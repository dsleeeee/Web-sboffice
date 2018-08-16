<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="layerName" value="_crncy" />
<c:set var="baseUrl" value="/adi/etc/ehgt/crncy" />

<div id="${layerName}Mask" class="fullDimmed" style="display: none;"></div>
<div id="${layerName}Layer" class="layer" style="display: none;">
  <div class="layer_inner">
    <%--layerContent--%>
    <div class="title w500">
      <p class="tit"><s:message code="ehgt.crncyFgRegist" /></p>
      <a href="javascript:;" class="btn_close ${layerName}Close"></a>
      <div class="con" style="height:300px;">
        <div class="sc" style="height:270px;">
          <div id="_codeGrid" class="mt10"></div>
        </div>
      </div>
      <div class="btnSet">
        <span><a href="javascript:;" class="btn_blue" id="${layerName}Save"><s:message code="cmm.edit" /></a></span>
        <span><a href="javascript:;" class="btn_gray ${layerName}Close"><s:message code="cmm.close" /></a></span>
      </div>
    </div>
    <%--//layerContent--%>
  </div>
</div>

<script>
var _codeGrid;
$(document).ready(function() {

  <%-- 레이어 닫기 --%>
  $(".${layerName}Close").click(function(e) {
    $("#${layerName}Mask, #${layerName}Layer").hide();
    $("#btnSearch").click();
  });
  
  <%-- 정보 저장 --%>
  $("#${layerName}Save").click(function(e){
    _codeGrid.save();
  });

});

<%-- 레이어 호출 --%>
function _showCrncyRegistLayer() {
  
  if(isEmpty(_codeGrid)) {
    _codeGrid = new codeGrid('#_codeGrid');
  }
  //wijmo.grid.FlexGrid.refreshAll();
  _codeGrid.getData();

  $("#${layerName}Mask, #${layerName}Layer").show();
}
</script>

<script>
//init Grid
codeGrid = function(div) {
  
  <%-- 사용 여부 --%>
  var useYn = ${ccu.getCommCodeExcpAll("904")};
  var useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');

  var rdata = 
    [
      {binding: "nmcodeNm", header: "<s:message code='ehgt.name' />", width: "*", isReadOnly: true},
      {binding: "nmcodeItem1", header: "<s:message code='ehgt.crncyUnit' />", width: "*", isReadOnly: true},
      {binding: "nmcodeItem2", header: "<s:message code='ehgt.country' />", width: "*", isReadOnly: true},
      {binding: "useYn", header: "<s:message code='cmm.useYn' />", dataMap: useYnDataMap, width: "*"}
    ];

  this.grid = wgrid.genGrid(div, rdata, false);
  
  var grid = this.grid;
  
  <%-- 그리드 전체 편집 가능하게, 편집 불가항목은 binding에서 처리 --%>
  grid.isReadOnly = false;

  <%-- Row Header 없애기 --%>
  grid.rowHeaders.columns.splice(0, 1);

};

//함수에서 공통으로 사용할 그리드 변수
codeGrid.prototype.grid = null;

codeGrid.prototype.getData = function() {
  var grid = this.grid;
  var param = {};
  wgrid.getGridData("${baseUrl}/list.sb", param, grid);
};

codeGrid.prototype.save = function() {
  
  var grid = this.grid;
  var view = grid.collectionView;

  //console.log(view.itemsAdded, view.itemsRemoved);
  var paramArr = new Array();
  for(var i = 0; i < view.itemsEdited.length; i++) {
    view.itemsEdited[i].status = 'U';
    paramArr.push(view.itemsEdited[i]);
  }
  //console.log(paramArr);
  if(paramArr.length <= 0) {
    s_alert.pop("<s:message code='cmm.not.modify'/>");
    return;
  }
  $.postJSONArray("${baseUrl}/save.sb", paramArr, function(result) {
    s_alert.pop("<s:message code='cmm.saveSucc' />");
    view.clearChanges();
  },
  function(result) {
    s_alert.pop(result.data.msg);
  });
};

</script>