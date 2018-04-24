<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div id="_authMask" class="fullDimmed" style="display: none;"></div>
<div id="_authLayer" class="layer" style="display: none;">
  <div class="layer_inner">
    <%--layerContent--%>
    <div class="title w500">
      <p class="tit"><s:message code="authGroup.authGive" />[<span id="_title"></span>]</p>
      <a href="javascript:;" class="btn_close _btnAuthClose"></a>
      <div class="con" style="height:300px;">
        <div class="sc" style="height:270px;">
          <div id="_authTree" class="mt10"></div>
        </div>
      </div>
      <div class="btnSet">
        <span><a href="javascript:;" class="btn_blue" id="_btnAuthSave"><s:message code="cmm.edit" /></a></span>
        <span><a href="javascript:;" class="btn_gray _btnAuthClose"><s:message code="cmm.close" /></a></span>
      </div>
    </div>
    <%--//layerContent--%>
  </div>
</div>

<script>
var _authTree;
$(document).ready(function() {

  $("._btnAuthClose").click(function(e) {
    $("#_authMask, #_authLayer").hide();
  });
  
  <%-- 리스스 정보 저장 --%>
  $("#_btnAuthSave").click(function(e){
    _authTree.save();
  });

});

<%-- 레이어 호출 --%>
function _showAuthExceptLayer(userId) {
  
  $("#_title").html(userId);
  if(isEmpty(_authTree)) {
    _authTree = new authTree('#_authTree');
  }
  _authTree.getData(userId);
}
</script>

<script>
//
authTree = function(div) {
  <%-- 메뉴 트리 생성 --%>
  this.tree = new wijmo.nav.TreeView(div, {
    displayMemberPath: 'resrceDisp',
    childItemsPath: 'items',
    expandOnClick : true,
    isReadOnly: true,
    showCheckboxes: true
  });
  var tree = this.tree;
  
  //var view = new wijmo.collections.CollectionView();
  //this.view = view;
  var view = this.view;
  
  <%-- 트리 체크박스 초기화 --%>
  tree.loadedItems.addHandler(function(s, e) {
    s.collapseToLevel(0);
    <%-- //TODO 느림.. --%>
    for (var nd = tree.getFirstNode(); nd; nd = nd.next()) {
      //console.log( new Date().getTime());
      if(!isEmpty(nd)){
        nd.isChecked = nd.dataItem.authFg;
      }
    }
    tree.view = new wijmo.collections.CollectionView(tree.checkedItems);
    $("#_authMask, #_authLayer").show();
  });
  
  <%-- 트리에 아이템 체크 상태가 바뀌었을 때 CollectionView에 반영 --%>
  tree.checkedItemsChanged.addHandler(function(s, e) {
    
    var view = tree.view;
    view.clearChanges();
    
    //TODO 변경될 때 마다 저장 => 저장 버튼 클릭 시 처리 검토
    for(var i = 0; i < tree.checkedItems.length; i++) {
      if(!view.contains(tree.checkedItems[i])) {
        view.itemsAdded.push(tree.checkedItems[i]);
      }
    }
    var viewNew = new wijmo.collections.CollectionView(tree.checkedItems);
    
    for(var i = 0; i < view.items.length; i++) {
      if(!viewNew.contains(view.items[i])) {
        view.itemsRemoved.push(view.items[i]);
      }
    }
    //console.log(view.itemsAdded, view.itemsRemoved);
  });
};

authTree.prototype.getData = function(userId) {
  
  //var view = this.view;
  var tree = this.tree;
  tree.currentUserId = userId;
  
  //console.log(tree.view);
  var param = {};
  if(!isEmpty(userId)) {
    param.userId = userId;
    tree.itemsSource = [];
    $.postJSON("/sys/auth/authgroup/authgroup/listResrceById.sb", param, function(result) {
      tree.itemsSource = result.data.list;
    },
    function(result) {
      s_alert.pop(result.data.msg);
    });
  }
};

authTree.prototype.save = function() {
  
  var tree = this.tree;
  var view = tree.view;

  if(isEmpty(tree.currentUserId)) {
    s_alert.pop("<s:message code='login.userId' /><s:message code='cmm.require.text' />");
    return;
  }
  
  //console.log(view.itemsAdded, view.itemsRemoved);
  var paramArr = new Array();
  for(var i = 0; i < view.itemsAdded.length; i++) {
    view.itemsAdded[i].status = 'I';
    view.itemsAdded[i].userId = tree.currentUserId;
    paramArr.push(view.itemsAdded[i]);
  }
  for(var i = 0; i < view.itemsRemoved.length; i++) {
    view.itemsRemoved[i].status = 'D';
    view.itemsRemoved[i].userId = tree.currentUserId;
    paramArr.push(view.itemsRemoved[i]);
  }
  //console.log(paramArr);
  if(paramArr.length <= 0) {
    s_alert.pop("<s:message code='cmm.not.modify'/>");
    return;
  }
  $.postJSONArray("/sys/auth/authgroup/authgroup/saveResrceById.sb", paramArr, function(result) {
    s_alert.pop("<s:message code='msg.save.succ' />");
    tree.view.clearChanges();
  },
  function(result) {
    s_alert.pop(result.data.msg);
  });
};

</script>