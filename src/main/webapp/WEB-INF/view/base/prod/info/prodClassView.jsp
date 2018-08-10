<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="baseUrl" value="/base/prod/info/" />


<div class="subCon">

  <%-- 상품 분류 관리 --%>
  <h2 class="h2_tit oh lh30">
    <s:message code="info.setting.productClass"/>
  </h2>

  <div class="wj-TblWrap mt20">
    <%-- 왼쪽 --%>
    <div class="w50 fl">
      <div class="wj-TblWrapBr ml10 pd20" style="height:500px;">
        <div class="sb-select dkbr mb10 oh">
          <div id="theComboBox3" class="w130 fl"></div>
          <div class="fl">
            <%-- 전체펼치기 --%>
            <button class="btn_skyblue" id="btnExpand"><s:message code="cmm.all.expand" /></button>
            <%-- 전체접기 --%>
            <button class="btn_skyblue" id="btnFold"><s:message code="cmm.all.fold" /></button>
          </div>
          <div class="fr">
            <%-- 추가버튼 --%>
            <button class="btn_skyblue" id="btnAdd"><s:message code="cmm.add" /></button>
            <%-- 삭제버튼 --%>
            <button class="btn_skyblue" id="btnDel"><s:message code="cmm.delete" /></button>
            <%-- 저장버튼 --%>
            <button class="btn_skyblue" id="btnSave"><s:message code="cmm.save" /></button>
          </div>
        </div>
        <%--위즈모 트리--%>
        <div id="clsTree" class="mt20" style="height:380px;"></div>
        <%--//위즈모 트리--%>
      </div>
    </div>

    <%--- 오른쪽 --%>
    <div class="w50 fl">
      <div class="wj-TblWrap ml10 pd10" style="height:500px;">
        <p class="tl s14 mt30 lh15">▶ [상품분류]는 삭제할 수 없습니다.</p>
        <p class="tl s14 mt30 lh15">▶ 초기 분류 등록시 [상품분류]를 선택 후, [추가]버튼을 클릭하여 분류를 등록해 주세요.</p>
        <p class="tl s14 mt30 lh15">▶ 분류 선택 후 [추가]버튼 클릭시, 하위 분류를 추가할 수 있습니다.</p>
        <p class="tl s14 mt30 lh15">▶ 분류 삭제시, 해당 분류의 하위 분류가 등록되어 있으면 분류 삭제가 불가능합니다.</p>
        <p class="tl s14 mt30 lh15">▶ 분류 삭제시, 해당 분류에 등록된 상품이 있으면 분류 삭제가 불가능합니다.</p>
        <p class="tl s14 mt30 lh15">▶ 분류 추가/삭제 후, [저장]버튼을 클릭해야 변경이 적용됩니다.</p>
      </div>
    </div>
  </div>
</div>

<script>

  $(document).ready(function() {

    <%--- 본사/매장 구분에 따라 middel url 다름. --%>
    var middleUrl = "";

    <c:if test="${orgnFg == 'HQ'}">
      middleUrl = "hq/";
    </c:if>
    <c:if test="${orgnFg == 'STORE'}">
      middleUrl = "store/";
    </c:if>

    console.log(middleUrl);

    <%-- 메뉴 트리 생성 --%>
    var tree = new wijmo.nav.TreeView('#clsTree', {
      displayMemberPath: 'prodClassNm',
      childItemsPath: 'items',
      expandOnClick : true,
      isReadOnly: false,
      showCheckboxes: false
    });

    var view = new wijmo.collections.CollectionView(tree.itemsSource);

    getClsTreeData();

    <%-- 트리 데이터 수정시 --%>
    tree.nodeEditEnded.addHandler(function(s, e) {

      var level = tree.selectedNode.level;
      var item = tree.selectedNode.dataItem;
      var editItem = {  status : "U",
                        hqOfficeCd : "${storeCd}",
                        storeCd : "${orgnCd}",
                        prodClassNm : item.prodClassNm,
                        prodClassCd : item.prodClassCd,
                        pProdClassCd : item.pProdClassCd,
                        level : level};

      var isData = false;

      if(view.itemsAdded.length > 0) {
        for(var i = 0; i < view.itemsAdded.length; i++) {
          if(view.itemsAdded[i].level == level) {
            isData = true;
          }
        }
      }
      if(view.itemsEdited.length > 0){
        for(var i = 0; i < view.itemsEdited.length; i++) {
          if(view.itemsEdited[i].level == level) {
            isData = true;
          }
        }
      }

      if(!isData) {
        view.itemsEdited.push(editItem);
      }
    });


    <%-- 분류 데이터 조회 --%>
    function getClsTreeData(){

      var param = {};

      $.postJSON("${baseUrl}"+ middleUrl + "getProdClass.sb", param, function(result) {

        <%--- 분류 데이터 없으면 초기 데이터 생성 --%>
        if(result.data.list == null || result.data.list.length == 0){

          var addStr        = "<s:message code='info.require.input.clsNm'/>";
          var pProdClassCd  = "00000";
          var level         = 0;
          var root = {status : "I",
                      hqOfficeCd : "${storeCd}",
                      storeCd : "${orgnCd}",
                      prodClassNm : "상품분류" ,
                      prodClassCd : "00000" ,
                      pProdClassCd : null,
                      level : 0 }

          tree.itemsSource = [root];
          tree.loadTree(true);

        }else{
          tree.itemsSource = result.data.list;
        }

        view = new wijmo.collections.CollectionView(tree.itemsSource);
        view.trackChanges = true;
      },
      function(result) {
        s_alert.pop(result.message);
      });
    }

    <%-- 펼침 버튼 클릭 --%>
    $("#btnExpand").click(function(){
      tree.collapseToLevel(100000);
    });

    <%-- 접기 버튼 클릭 --%>
    $("#btnFold").click(function(){
      tree.collapseToLevel(0);
    });

    <%-- 추가 버튼 클릭 --%>
    $("#btnAdd").click(function(){

      <%-- 선택된 노드가 없으면, 분류를 선택해주세요. --%>
      if(tree.selectedNode == undefined){
        s_alert.pop("<s:message code='info.require.select.node' />");
        return;
      }

      var addStr        = "<s:message code='info.require.input.clsNm'/>";
      var pProdClassCd  = tree.selectedNode.dataItem.prodClassCd;
      var level         = tree.selectedNode.level + 1;
      var newItem       = {};

      newItem = { status : "I",
                  hqOfficeCd : "${storeCd}",
                  storeCd : "${orgnCd}",
                  prodClassNm : addStr ,
                  pProdClassCd : pProdClassCd,
                  level : level};

      var node = tree.selectedNode;
      tree.selectedNode = node.addChildNode(0, newItem);
      view.itemsAdded.push(newItem);
      tree.loadTree();
    });

    <%-- 삭제 버튼 클릭 --%>
    $("#btnDel").click(function(){

      <%-- 분류 미선택시, 분류를 선택해주세요. --%>
      if(tree.selectedNode == undefined){
        s_alert.pop("<s:message code='info.require.select.node' />");
        return;
      }

      <%-- root는 삭제 불가능 --%>
      if(tree.selectedNode.level == 0 ){
        s_alert.pop("<s:message code='info.unable.delete.root' />");
        return;
      }

      <%-- 하위 분류 존재시, 하위분류부터 삭제 가능합니다. --%>
      if(tree.selectedNode.nodes != null) {
        s_alert.pop("<s:message code='info.require.delete.child' />");
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
    $("#btnSave").click(function(){

      var msg = "<s:message code='info.require.clsNm'/>";
      var paramArr = new Array();

      for(var i = 0; i < view.itemsAdded.length; i++) {
        if(view.itemsAdded[i].prodClassNm == "" || view.itemsAdded[i].prodClassNm == msg) {
          s_alert.pop("<s:message code='info.require.clsNm'/>");
          return;
        }
        if(view.itemsAdded[i].prodClassNm.length > 15 ) {
          s_alert.pop("<s:message code='info.require.clsNm'/><s:message code='cmm.regexp' arguments='15'/>");
          return;
        }
        paramArr.push(view.itemsAdded[i]);
      }

      for(var i = 0; i < view.itemsEdited.length; i++) {
        if(view.itemsEdited[i].prodClassNm == "" || view.itemsEdited[i].prodClassNm == msg) {
          s_alert.pop("<s:message code='info.require.clsNm'/>");
          return;
        }
        if(view.itemsEdited[i].prodClassNm.length > 15 ) {
          s_alert.pop("<s:message code='info.prodClassNm'/><s:message code='cmm.regexp' arguments='15'/>");
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

      $.postJSONArray("${baseUrl}" + "class/saveProdClass.sb", paramArr, function(result) {
        s_alert.pop("<s:message code='cmm.saveSucc' />");
        view.clearChanges();
        getClsTreeData();
      },
      function(result) {
        s_alert.pop(result.message);
      });
    });
  });
</script>
