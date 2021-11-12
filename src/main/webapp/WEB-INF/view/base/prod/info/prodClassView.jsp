<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
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
      <div class="wj-TblWrapBr ml10 pd20" style="height:540px;">
        <div class="sb-select dkbr mb10 oh">
          <div class="fl">
            <%-- 전체펼치기 --%>
            <button class="btn_skyblue" id="btnExpand"><s:message code="cmm.all.expand" /></button>
            <%-- 전체접기 --%>
            <button class="btn_skyblue" id="btnFold"><s:message code="cmm.all.fold" /></button>
          </div>
          <div class="fr" id ="divProd" style="display: none">
            <%-- 추가버튼 --%>
            <button class="btn_skyblue" id="btnAdd"><s:message code="cmm.add" /></button>
            <%-- 삭제버튼 --%>
            <button class="btn_skyblue" id="btnDel"><s:message code="cmm.delete" /></button>
            <%-- 저장버튼 --%>
            <button class="btn_skyblue" id="btnSave"><s:message code="cmm.save" /></button>
          </div>
        </div>
        <%--위즈모 트리--%>
        <div class="wj-TblWrapBr pd20" style="height:470px;">
          <div class="theTreeAll_cls" id="clsTree" class="mt20" style="height:440px;"></div>
        </div>
        <%--//위즈모 트리--%>
      </div>
    </div>

    <%--- 오른쪽 --%>
    <div class="w50 fl">
      <div class="wj-TblWrap ml10 pd10" style="height:500px;">
        <%--<p class="tl s14 mt30 lh15">▶ [상품분류]는 삭제할 수 없습니다.</p>--%>
        <%--<p class="tl s14 mt30 lh15">▶ 초기 분류 등록시 [상품분류]를 선택 후, [추가]버튼을 클릭하여 분류를 등록해 주세요.</p>--%>
        <p class="tl s14 mt30 lh15">▶ 분류추가 : 상위분류 선택 후 [추가]버튼 클릭시, 하위 분류를 추가할 수 있습니다.</p>
        <%--<p class="tl s14 mt30 lh15">▶ 분류 삭제시, 해당 분류의 하위 분류가 등록되어 있으면 분류 삭제가 불가능합니다.</p>--%>
        <%--<p class="tl s14 mt30 lh15">▶ 분류 삭제시, 해당 분류에 등록된 상품이 있으면 분류 삭제가 불가능합니다.</p>--%>
        <p class="tl s14 mt30 lh15">▶ 분류 추가/삭제 후, [저장]버튼을 클릭해야 변경이 적용됩니다.</p>
      </div>
    </div>
  </div>
</div>

<script>
  // String 형식
  var prodAuthEnvstVal = "${prodAuthEnvstVal}";
  var orgnFg = "${orgnFg}";
  var hqOfficeCd = "${hqOfficeCd}";

  $(document).ready(function() {
    // 상품생성설정
    $("#divProd").css("display", "none");
    // 단독매장
    if(hqOfficeCd == "00000") {
      $("#divProd").css("display", "block");
      // 프랜 본사,매장
    } else {
      if((prodAuthEnvstVal== "ALL") || (orgnFg === 'HQ' && prodAuthEnvstVal== "HQ") || (orgnFg === 'STORE' && prodAuthEnvstVal== "STORE")) {
        $("#divProd").css("display", "block");
      }
    }
  });
</script>

<script>

  // 분류명 수정 여부 확인을 위한 Map 변수
  var orgTree = new Map();
  var modTree = new Map();

  $(document).ready(function() {

    <%--- 본사/매장 구분에 따라 middel url 다름. --%>
    var middleUrl = "";

    <c:if test="${orgnFg == 'HQ'}">
      middleUrl = "hq/";
    </c:if>
    <c:if test="${orgnFg == 'STORE'}">
      middleUrl = "store/";
    </c:if>

    //console.log(middleUrl);

    <%-- 메뉴 트리 생성 --%>
    var tree = new wijmo.nav.TreeView('#clsTree', {
      displayMemberPath: 'prodClassNm',
      childItemsPath: 'items',
      expandOnClick : true,
      isReadOnly: false,
      showCheckboxes: false,
      nodeEditStarting: function (s, e) {
        if (e.node.level == 0 ) {
          e.cancel = true;
        }
      }
    });

    var view = new wijmo.collections.CollectionView(tree.itemsSource);

    getClsTreeData();

    <%-- 트리 데이터 수정시 : 제대로 event를 인식하지 못하여 사용안함(2021.03.03) --%>
    <%-- tree.nodeEditEnded.addHandler(function(s, e) {

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
    }); --%>


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
                      prodClassNm : "상품분류정보관리" ,
                      prodClassCd : "00000" ,
                      pProdClassCd : null,
                      level : 0 };

          tree.itemsSource = [root];
          tree.loadTree(true);

        }else{
          tree.itemsSource = result.data.list;
        }

        view = new wijmo.collections.CollectionView(tree.itemsSource);
        view.trackChanges = true;

        // 초기 분류 값 가지고 있기(후에 수정한 분류명을 찾아내기 위해)
        getOrgTree(tree.itemsSource);

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

      var theItem;
      var theNode;

      if(tree.selectedNode == undefined){
        // 선택한 노드가 없을 때, root 분류 아래 Level에 새 분류 추가하도록
        theItem = findItem(tree.itemsSource, '00000');
        theNode = tree.getNode(theItem);
        theNode.select();
      }

      <%-- 상위분류 저장 후 하위분류를 입력해주세요. --%>
      if(tree.selectedNode.dataItem.prodClassCd === undefined){
        s_alert.pop("<s:message code='info.require.save.pClsNm' />");
        return;
      }

      <%-- 3단계 이상 상품불류를 생성할수 없습니다. --%>
      if(tree.selectedNode.level > 2){
        s_alert.pop("<s:message code='info.prodClassAddChk.msg' />");
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

      if (node) {
        var index = node.nodes ? node.nodes.length : 0;
        tree.selectedNode = node.addChildNode(index, newItem);
      }
      else {
        var index = tree.nodes ? tree.nodes.length : 0;
        tree.selectedNode = tree.addChildNode(index, newItem);
      }

      view.itemsAdded.push(newItem);
      tree.loadTree();

      // 다시 부모노드로 focus 하여, 부모노드를 다시 클릭해야하는 번거로움 없앰.
      theItem = findItem(tree.itemsSource, pProdClassCd);
      theNode = tree.getNode(theItem);
      theNode.select();

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


      } else {

        var delItem = tree.selectedNode.dataItem;

        // 해당 분류로 등록된 상품여부 확인
        var params = {};
        params.prodClassCd = delItem.prodClassCd;

        // 매장일때 코드값이 80001 이하면 본사이므로 삭제불가
        if("${orgnFg}" == "STORE") {
          if("${hqOfficeCd}" != "00000") {
            if (params.prodClassCd < 80001) {
              s_alert.pop("<s:message code='info.prodClassCdHqDel' />"); // 본사에서 등록한 상품분류는 삭제 불가능합니다.
              return;
            }
          }
        }

        $.postJSON("/base/prod/info/class/chkProdCnt.sb", params, function(result) {

            // 해당 분류에 속한 상품이 없을 때
            if(result.data === 0) {

                // 이번에 추가한(저장된 적 없는) 분류를 삭제할 때
                if (delItem.status === 'I') {
                    // 삭제하려는 분류와, 이번에 추가하려고 했던 분류값을 비교하여 값을 찾아내 삭제
                    for (var i = 0; i < view.itemsAdded.length; i++) {
                        if ((delItem.prodClassNm === view.itemsAdded[i].prodClassNm) && (delItem.pProdClassCd === view.itemsAdded[i].pProdClassCd) && (delItem.level === view.itemsAdded[i].level)) {
                            view.itemsAdded.splice(i, 1);
                        }
                    }
                } else { // 이미 저장되어 있는 분류를 삭제할 때
                    delItem.status = "D";
                    view.itemsRemoved.push(delItem);
                }

                // 트리뷰 적용
                var parent = tree.selectedNode.parentNode;
                var arr = parent ? parent.dataItem[tree.childItemsPath] : tree.itemsSource;
                var index = arr.indexOf(tree.selectedItem);

                arr.splice(index, 1);
                tree.loadTree();
            }
        },
        function(result) {
          s_alert.pop(result.message);
        });
      }
    });

    <%-- 저장 버튼 클릭 --%>
    $("#btnSave").click(function(){

      var msg = "<s:message code='info.require.input.clsNm'/>";
      var paramArr = [];

      // 수정한 분류정보 가져오기
      getEditItem();

      for(var i = 0; i < view.itemsAdded.length; i++) {
        if(view.itemsAdded[i].prodClassNm == "" || view.itemsAdded[i].prodClassNm == msg) {
          s_alert.pop("<s:message code='info.require.clsNm'/>");
          return;
        }
        if(view.itemsAdded[i].prodClassNm.length > 15 ) {
          s_alert.pop("<s:message code='info.prodClassNm'/><s:message code='cmm.regexp' arguments='15'/>");
          return;
        }

        // 분류명 앞뒤 공백 및 엔터값 제거
        view.itemsAdded[i].prodClassNm = view.itemsAdded[i].prodClassNm.trim().removeEnter();

        paramArr.push(view.itemsAdded[i]);
      }

      for(var i = 0; i < view.itemsEdited.length; i++) {
        if(view.itemsEdited[i].prodClassNm == "" || view.itemsEdited[i].prodClassNm == msg) {
          s_alert.pop("<s:message code='info.require.clsNm'/>");
          return;
        }
        // 매장일때 코드값이 80001 이하면 본사이므로 수정불가
        if("${orgnFg}" == "STORE") {
          if("${hqOfficeCd}" != "00000") {
            if (view.itemsEdited[i].prodClassCd < 80001) {
              s_alert.pop("<s:message code='info.prodClassCdHqSave' />"); // 본사에서 등록한 상품분류는 수정 불가능합니다.
              return;
            }
          }
        }
        if(view.itemsEdited[i].prodClassNm.length > 15 ) {
          s_alert.pop("<s:message code='info.prodClassNm'/><s:message code='cmm.regexp' arguments='15'/>");
          return;
        }

        // 분류명 앞뒤 공백 및 엔터값 제거
        view.itemsEdited[i].prodClassNm = view.itemsEdited[i].prodClassNm.trim().removeEnter();

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

    // 수정한 분류정보 가져오기
    function getEditItem() {

      // 초기화
      view.itemsEdited.length = 0;
      modTree.clear();

      // 수정한 분류 map에 담아 비교
      getModTree(tree.itemsSource);

      var editItem = {};

      modTree.forEach(function(value,key,map){
        if(value !== orgTree.get(key)){
          editItem = {
            status : "U",
            hqOfficeCd : "${storeCd}",
            storeCd : "${orgnCd}",
            prodClassNm : value.split('││')[1],
            prodClassCd : key,
            pProdClassCd : value.split('││')[0],
            level : 0
          };

          view.itemsEdited.push(editItem);
        }
      });
    };

  });

  // 상위노드 item 찾기
  function findItem(items, text) {
    for (var i = 0; i < items.length; i++) {
      var item = items[i];
      if (item.prodClassCd == text) {
        return item;
      }
      if (item.items) {
        item = findItem(item.items, text);
        if (item) {
          return item;
        }
      }
    }
    return null; //  not found
  }

  // 초기 상품 분류 값을 Map 변수에 Setting
  function getOrgTree(items){
    for (var i = 0; i < items.length; i++) {
      var item = items[i];
      if (item.prodClassCd) {
        orgTree.set(item.prodClassCd, item.pProdClassCd + "││" + item.prodClassNm);
      }
      if (item.items) {
        item = getOrgTree(item.items);
        if (item) {
          orgTree.set(item.prodClassCd, item.pProdClassCd + "││" + item.prodClassNm);
        }
      }
    }
  }

  // 수정한 상품 분류 값을 Map 변수에 Setting
  function getModTree(items){
    for (var i = 0; i < items.length; i++) {
      var item = items[i];
      if (item.prodClassCd) {
        modTree.set(item.prodClassCd, item.pProdClassCd + "││" + item.prodClassNm);
      }
      if (item.items) {
        item = getModTree(item.items);
        if (item) {
          modTree.set(item.prodClassCd, item.pProdClassCd + "││" + item.prodClassNm);
        }
      }
    }
  }

</script>
