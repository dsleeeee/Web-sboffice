<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 환경설정 레이어 --%>

<div id="clsDim" class="fullDimmed" style="display:none;"></div>
<div id="clsLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w30">
      <p id="clsTitle" class="tit"></p>
      <a href="javascript:;" class="btn_close"></a>
      <div class="con">
        <div class="tabType1">
          <ul>
            <%-- 환경설정 탭 --%>
            <li><a href="javascript:;" id="envSettingTab"><s:message code="hqBrand.envSetting" /></a></li>
            <%-- 분류관리 탭 --%>
            <li><a href="javascript:;" id="classSettingTab" class="on"><s:message code="hqBrand.classSetting" /></a></li>
          </ul>
        </div>
        
        <div>
          <div class="oh mt10">
            <div class="wj-TblWrap mr10" style="height:200px;">
              <div class="oh mb10">
                <%-- 저장 --%>
                <span class="fr ml5"><a id="btnSave" href="javascript:;" class="btn_grayS2"><s:message code="cmm.save" /></a></span>
                <%-- 삭제 --%>
                <span class="fr ml5"><a id="btnDel" href="javascript:;" class="btn_grayS2"><s:message code="cmm.delete" /></a></span>
                <%-- 추가 --%>
                <span class="fr"><a id="btnAdd" href="javascript:;" class="btn_grayS2"><s:message code="cmm.add" /></a></span>
              </div>
              <%-- 위즈모 --%>
              <div id="clsTree" style="height:450px;"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<script>

<%-- 메뉴 트리 생성 --%>
var tree = new wijmo.nav.TreeView('#clsTree', {
  displayMemberPath: 'prodClassNm',
  childItemsPath: 'items',
  expandOnClick : true,
  isReadOnly: false,
  showCheckboxes: false
});

var view = new wijmo.collections.CollectionView(tree.itemsSource);

tree.nodeEditEnded.addHandler(function(s, e) {
  
  var level = tree.selectedNode.level;
  var item = tree.selectedNode.dataItem;
  var editItem = {status : "U", hqBrandCd : selectedBrand.hqBrandCd, prodClassNm : item.prodClassNm, prodClassCd : item.prodClassCd, pprodClassCd : item.pprodClassCd, level : level}

  var isData = false;
  
  if(view.itemsAdded.length > 0) {
    for(var i = 0; i < view.itemsAdded.length; i++) {
      if(view.itemsAdded[i].level == level) {
        view.itemsAdded[i].prodClassNm = item.prodClassNm;
        isData = true;
      } 
    }
  }
  if(view.itemsEdited.length > 0){
    for(var i = 0; i < view.itemsEdited.length; i++) {
      if(view.itemsEdited[i].level == level) {
        view.itemsEdited[i].prodClassNm = item.prodClassNm;
        isData = true;
      }
    }
  }
  
  if(!isData) {
    view.itemsEdited.push(editItem);
  }
  
});

<%-- 추가 버튼 클릭 --%>
$("#clsLayer #btnAdd").click(function(){
  if(tree.selectedNode == undefined){
    s_alert.pop("<s:message code='hqBrand.require.select.node' />");
    return;
  }
  
  var addStr        = "<s:message code='hqBrand.require.input.clsNm'/>";
  var pprodClassCd  = tree.selectedNode.itemsSource[0].prodClassCd;
  var level         = tree.selectedNode.level + 1;
  var newItem       = {status : "I",  hqBrandCd : selectedBrand.hqBrandCd, prodClassNm : addStr , pprodClassCd : pprodClassCd , level : level};
  
  <%-- child node 없으면 추가 (한 depth당 하나의 분류 등록 가능하도록) --%>
  var node = tree.selectedNode;
  if(node.nodes == null){ 
    tree.selectedNode = node.addChildNode(0, newItem);
    view.itemsAdded.push(newItem);
    tree.loadTree();
  }else {
    s_alert.pop("<s:message code='hqBrand.not.add.node' />");
    return;
  }
});

<%-- 삭제 버튼 클릭 --%>
$("#clsLayer #btnDel").click(function(){
  if(tree.selectedNode == undefined){
    s_alert.pop("<s:message code='hqBrand.require.select.node' />");
    return;
  }
  
  if(tree.selectedNode.nodes != null) {
    s_alert.pop("<s:message code='hqBrand.require.delete.child' />");
    return;
  } else {
    
    var delItem = tree.selectedNode.dataItem;
    delItem.status = "D";
    
    view.itemsRemoved.push(delItem);
    
    var parent  = tree.selectedNode.parentNode;
    var arr     = parent ? parent.dataItem[tree.childItemsPath] : tree.itemsSource;
    var index   = arr.indexOf(tree.selectedItem);
    
    arr.splice(index, 1);
    tree.loadTree();
  }
});

<%-- 저장 버튼 클릭 --%>
$("#clsLayer #btnSave").click(function(){
  
  var msg = "<s:message code='hqBrand.require.clsNm'/>";
  var paramArr = new Array();
  
  for(var i = 0; i < view.itemsAdded.length; i++) {
    if(view.itemsAdded[i].prodClassNm == "" || view.itemsAdded[i].prodClassNm == msg) {
      s_alert.pop("<s:message code='hqBrand.require.clsNm'/>");
      return;
    }
    if(view.itemsAdded[i].prodClassNm.length > 15 ) {
      s_alert.pop("<s:message code='hqBrand.require.clsNm'/><s:message code='cmm.regexp' arguments='15'/>");
      return;
    }
    
    paramArr.push(view.itemsAdded[i]);
  }
  for(var i = 0; i < view.itemsEdited.length; i++) {
    if(view.itemsEdited[i].prodClassNm == "" || view.itemsEdited[i].prodClassNm == msg) {
      s_alert.pop("<s:message code='hqBrand.prodClassNm'/>");
      return;
    }
    if(view.itemsAdded[i].prodClassNm.length > 15 ) {
      s_alert.pop("<s:message code='hqBrand.prodClassNm'/><s:message code='cmm.regexp' arguments='15'/>");
      return;
    }
    paramArr.push(view.itemsEdited[i]);
  }
  for(var i = 0; i < view.itemsRemoved.length; i++) {
    paramArr.push(view.itemsRemoved[i]);
  }
  
  if(paramArr.length <= 0) {
    s_alert.pop("<s:message code='cmm.not.modify'/>");
    return;
  }
  
  paramArr.sort(function (a, b){
    return a.level < b.level ? -1 : a.level > b.level ? 1 : 0;  
  });
  
  $.postJSONArray("/store/hq/hqbrand/productclass/save.sb", paramArr, function(result) {
    s_alert.pop("<s:message code='msg.save.succ' />");
    view.clearChanges();
    getClsTreeData();
  },
  function(result) {
    s_alert.pop(result.data.msg);
  });
});

<%-- 분류관리 팝업 오픈 --%>
function openClsLayer(){
  
  $("#clsDim").show();
  $("#clsLayer").show();
  
  var clsTitle = "[" + selectedBrand.hqBrandCd + "] "+ selectedBrand.hqBrandNm;
  
  $("#clsTitle").text(clsTitle);
  
  getClsTreeData();
}

<%-- 분류 데이터 조회 --%>
function getClsTreeData(){
  
  var param = {};
  param.hqOfficeCd = selectedBrand.hqOfficeCd;
  param.hqBrandCd  = selectedBrand.hqBrandCd;
   
  $.postJSON("/store/hq/hqbrand/productclass/list.sb", param, function(result) {

    if(result.data.list == null || result.data.list.length == 0){
      var addStr        = "<s:message code='hqBrand.require.input.clsNm'/>";
      var pprodClassCd  = "00000";
      var level         = 0;
      var newItem       = { hqBrandCd : selectedBrand.hqBrandCd, prodClassNm : addStr , prodClassCd : "00001" , status : "I", pprodClassCd : pprodClassCd, level : level};
      
      tree.itemsSource = [newItem];
      tree.loadTree(true);
    }else{
      tree.itemsSource = result.data.list;
    }
    
    view = new wijmo.collections.CollectionView(tree.itemsSource);
    view.trackChanges = true;
  },
  function(result) {
    s_alert.pop(result.data.msg);
  });
}

<%-- 환경설정 탭--%>
$("#clsLayer #envSettingTab").click(function(){
  $("#clsDim").hide();
  $("#clsLayer").hide();
  
  openEnvLayer();
});

<%-- 레이어팝업 닫기 --%>
$("#clsLayer .btn_close").click(function(){
  $("#clsDim").hide();
  $("#clsLayer").hide();
});

</script>

