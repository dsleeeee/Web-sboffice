<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 포스기능 인증허용대상 설정 레이어 --%>
<div id="posAuthDim" class="fullDimmed" style="display:none;"></div>
<div id="posAuthLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w800px">
      <p class="tit" id="popTitle"></p>
      <a href="#" class="btn_close"></a>
      <div class="con sc2" style="height:500px;">
        <div class="tr">
          <%-- 저장버튼 --%>
          <a id="btnSave" href="#" class="btn_grayS2"><s:message code="cmm.save" /></a>
        </div>
        <%-- 위즈모 그리드 --%>
        <div>
          <div id="authSettingGrid" class="mt10 mb20"></div>
        </div>
      </div>
    </div>
  </div>
</div>

<script>

  <%--  인증허용대상 (사원) 그리드 --%>
  var authSettingHeader =
      [
        {binding:"storeCd", header:"<s:message code='posFunc.storeCd' />", isReadOnly:true, visible:false, width:"*"},
        {binding:"empNo", header:"<s:message code='posFunc.empNo' />", isReadOnly:true, width:"*"},
        {binding:"empNm", header:"<s:message code='posFunc.empNm' />", isReadOnly:true, width:"*"},
        {binding:"useYn", header:"<s:message code='posFunc.useYn' />", dataType:wijmo.DataType.Boolean, width:"*"}
      ];
  <%-- 그리드 생성 --%>
  var authSettingGrid = wgrid.genGrid("#authSettingGrid", authSettingHeader);

  authSettingGrid.isReadOnly = false;

  <%--  인증허용대상 설정 레이어 팝업 오픈 --%>
  function openSetAuthLayer(){

    $("#posAuthDim").show();
    $("#posAuthLayer").show();

    $("#popTitle").text("["+selectedFnkey.fnkeyNo+"] " + selectedFnkey.fnkeyNm);

    getPosAuthData();
  }

  <%-- 그리드 데이터 조회 --%>
  function getPosAuthData(){

    var param = {};
    param.storeCd = selectedFnkey.storeCd;
    param.fnkeyNo = selectedFnkey.fnkeyNo;

    $.postJSON("/base/store/posfunc/auth/getAuthEmpList.sb", param, function(result) {
          var list = result.data.list;
          authSettingGrid.itemsSource = new wijmo.collections.CollectionView(list);
          authSettingGrid.collectionView.trackChanges = true;
        },
        function (result) {
          s_alert.pop(result.message);

        }
    );
  }

  <%-- 체크박스 초기화 --%>
  authSettingGrid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;

      if( col.binding == "useYn") {
        e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.useYn == 1 ? 'checked' : '') + '>';
      }
    }
  });


  <%-- 저장버튼 클릭 --%>
  $("#posAuthLayer #btnSave").click(function(){
    var paramArr = [];

    for(var i = 0; i < authSettingGrid.collectionView.itemCount; i ++) {
      authSettingGrid.collectionView.items[i].fnkeyNo = selectedFnkey.fnkeyNo;
      paramArr.push(authSettingGrid.collectionView.items[i]);
    }

    $.postJSONArray("/base/store/posfunc/auth/saveAuthEmp.sb", paramArr, function(result) {
          s_alert.pop("<s:message code='cmm.saveSucc' />");
          //getPosAuthData();
          $("#posAuthDim").hide();
          $("#posAuthLayer").hide();
        },
        function(result) {
          s_alert.pop(result.message);
        });
  });

  <%-- 레이어팝업 닫기 --%>
  $("#posAuthLayer .btn_close").click(function(){
    $("#posAuthDim").hide();
    $("#posAuthLayer").hide();
  });

</script>
