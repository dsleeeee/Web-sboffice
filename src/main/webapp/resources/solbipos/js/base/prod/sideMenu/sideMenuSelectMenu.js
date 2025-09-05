/****************************************************************
 *
 * 파일명 : sideMenuSelectMenu.js
 * 설  명 : 사이드메뉴>선택메뉴 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.14     노현수      1.0
 *
 * **************************************************************/

// 고정상품구분
var fixProdFgDataMap = new wijmo.grid.DataMap([
  {id: "0", name: "선택"},
  {id: "1", name: "고정"}
], 'id', 'name');

// 필수선택여부
var requireYnDataMap = new wijmo.grid.DataMap([
    {id: "N", name: "선택안함"},
    {id: "Y", name: "필수선택"}
], 'id', 'name');

// 선택그룹세트구분
var sdselTypeFgDataMap = new wijmo.grid.DataMap([
  {id: "C", name: "세트"},
  {id: "S", name: "싱글세트"}
], 'id', 'name');

// 선택그룹 검색타입
var vSrchTypeSelGroup = [
    {"name":"그룹명","value":"grpNm"},
    {"name":"그룹코드","value":"grpCd"}
];

// 선택상품 검색타입
var vSrchTypeSelProd = [
    {"name":"상품명","value":"prodNm"},
    {"name":"상품코드","value":"prodCd"}
];

// 적용매장구분
var regStoreFgData = [
  {"name":"미사용","value":"0"},
  {"name":"제외매장","value":"1"},
  {"name":"허용매장","value":"2"}
];

// 출력여부
var printYnData = [
  {"name":"사용","value":"Y"},
  {"name":"미사용","value":"N"}
];

// 분류구분
var popUpClassYnData = [
    {"name": "미사용", "value": "N"},
    {"name": "피자", "value": "Y"},
    {"name": "엣지", "value": "E"},
    {"name": "토핑", "value": "T"}
];

/**
 * 사이드메뉴 선택그룹 그리드 생성
 */
app.controller('sideMenuSelectGroupCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuSelectGroupCtrl', $scope, $http, false));

  // 상품 본사통제구분 (H : 본사, S: 매장)
  // $scope.prodEnvstVal = prodEnvstVal;

  // 본사에서 들어왔을때는 매장코드가 없다. (가상로그인 후, 세로고침 몇번 하면 gvOrgnFg가 바뀌는 것 예방)
  // $scope.userStoreCd = gvStoreCd;
  // $scope.btnShowFg = false;

  // if(($scope.prodEnvstVal === 'HQ' && isEmptyObject($scope.userStoreCd))
  //     || ($scope.prodEnvstVal === 'STORE' &&  !isEmptyObject($scope.userStoreCd))) {
  //   $scope.btnShowFg = true;
  // }


  // 그리드 Refresh
  $scope.$on('selectMenuRefresh', function (event, data) {
    $scope.flex.refresh();
  });

  // 선택
  $scope.selectedSelGroup;
  $scope.setSelectedSelGroup = function(data) {
    $scope.selectedSelGroup = data;
  };
  $scope.getSelectedSelGroup = function(){
    return $scope.selectedSelGroup;
  };

  // 콤보박스 데이터 Set
  $scope._setComboData("srchTypeSelGroup", vSrchTypeSelGroup);
  $scope._setComboData("srchTypeSelProd", vSrchTypeSelProd);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 내 콤보박스 설정
    $scope.brandDataMap = new wijmo.grid.DataMap(brandList, 'value', 'name'); // 브랜드
    $scope.fixProdFgDataMap = fixProdFgDataMap;
    $scope.sdselTypeFgDataMap = sdselTypeFgDataMap;
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYnData, 'value', 'name'); //하프앤하프

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === 'sdselGrpCd') {
          var item = s.rows[e.row].dataItem;
          if (item.status !== 'I') {
            wijmo.addClass(e.cell, 'wijLink');
          }
        }
      }
    });

    // 선택그룹 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === 'sdselGrpCd') {
        var dataItem = s.rows[e.row].dataItem;
        if (nvl(dataItem.status, '') === '' && dataItem.status !== 'I') {
          e.cancel = true;
        }
      }
    });

    // 선택그룹 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if ( col.binding === 'sdselGrpCd') {
          if (selectedRow.sdselGrpCd !== '' && selectedRow.sdselGrpCd !== undefined && selectedRow.sdselGrpCd !== '자동채번') {
            $("#sideSelectGroupTitle").html(" [" + selectedRow.sdselGrpCd + "]" + selectedRow.sdselGrpNm);
            $("#hdHalfAndHalfYn").val(selectedRow.halfAndHalfYn);
            $("#sideClassTitle").html("");

            // 선택한 선택그룹의 브랜드코드 갖고있기(선택상품 추가 팝업에서 사용)
            if(brandUseFg === "1"){
              if(orgnFg === "HQ"){
                $("#hdSdselGrpBrandCd").val(selectedRow.hqBrandCd);
              }
            }else{
              $("#hdSdselGrpBrandCd").val("");
            }

            if (hqOfficeCd != '00000' && orgnFg == 'STORE' && selectedRow.sdselGrpCd <= 799999) {
              $("#btnUpSelClass").hide();
              $("#btnDownSelClass").hide();
              $("#btnAddSelClass").hide();
              $("#btnDelSelClass").hide();
              $("#btnSaveSelClass").hide();
              $("#btnSdselClassCopy").hide();
              $("#btnSdselClassRegStore").hide();

              $("#btnSdselProdCopy").hide();
              $("#btnUpSelProd").hide();
              $("#btnDownSelProd").hide();
              $("#btnAddSelProd").hide();
              $("#btnDelSelProd").hide();
              $("#btnSaveSelProd").hide();
              $("#btnSdselProdRegStore").hide();
            } else {
              $("#btnUpSelClass").show();
              $("#btnDownSelClass").show();
              $("#btnAddSelClass").show();
              $("#btnDelSelClass").show();
              $("#btnSaveSelClass").show();
              $("#btnSdselClassCopy").show();
              $("#btnSdselClassRegStore").show();

              $("#btnSdselProdCopy").hide();
              $("#btnUpSelProd").hide();
              $("#btnDownSelProd").hide();
              $("#btnAddSelProd").hide();
              $("#btnDelSelProd").hide();
              $("#btnSaveSelProd").hide();
              $("#btnSdselProdRegStore").hide();
            }

            $scope.setSelectedSelGroup(selectedRow);
            $scope._broadcast('sideMenuSelectClassCtrl', selectedRow);
            var prodGrid = agrid.getScope('sideMenuSelectProdCtrl');
            prodGrid._gridDataInit();
          }
        }
      }
    });
  };

  // <-- 검색 호출 -->
  $scope.$on('sideMenuSelectGroupCtrl', function(event, data) {
    // 초기 버튼 셋팅
    // 선택분류 버튼
    $("#btnUpSelClass").hide();
    $("#btnDownSelClass").hide();
    $("#btnAddSelClass").hide();
    $("#btnDelSelClass").hide();
    $("#btnSaveSelClass").hide();
    $("#btnSdselClassCopy").hide();
    $("#btnSdselClassRegStore").hide();

    // 선택상품버튼
    $("#btnSdselProdCopy").hide();
    $("#btnUpSelProd").hide();
    $("#btnDownSelProd").hide();
    $("#btnAddSelProd").hide();
    $("#btnDelSelProd").hide();
    $("#btnSaveSelProd").hide();
    $("#btnSdselProdRegStore").hide();

    $("#sideSelectGroupTitle").html("");
    $("#hdHalfAndHalfYn").val("");
    var attrScope = agrid.getScope('sideMenuSelectClassCtrl');
    attrScope._gridDataInit();   // 선택분류 그리드 초기화

    $("#sideClassTitle").html("");
    var prodScope = agrid.getScope('sideMenuSelectProdCtrl');
    prodScope._gridDataInit();   // 선택상품 그리드 초기화

    // 선택그룹 그리드 조회
    $scope.srchSelGroup();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 선택그룹 그리드 조회
  $scope.srchSelGroup = function(){

    // 파라미터
    var params = {};
    params.sdselTypeFg = 'C';
    params.sdselGrpCd = "";
    params.sdselGrpNm = "";
    params.sdselProdCd = "";
    params.sdselProdNm = "";

    // 그룹명, 그룹코드 검색조건
    if($("#txtSelGroup").val() !== ""){
      if($scope.srchTypeSelGroupCombo.selectedValue === "grpNm"){
        params.sdselGrpCd = "";
        params.sdselGrpNm = $("#txtSelGroup").val();
      }
      else if($scope.srchTypeSelGroupCombo.selectedValue === "grpCd"){
        params.sdselGrpCd = $("#txtSelGroup").val();
        params.sdselGrpNm = "";
      }
    }

    // 상품명, 상품코드 검색조건
    if($("#txtSelProd").val() !== ""){
      if($scope.srchTypeSelProdCombo.selectedValue === "prodNm"){
        params.sdselProdCd = "";
        params.sdselProdNm = $("#txtSelProd").val();
      }
      else if($scope.srchTypeSelProdCombo.selectedValue === "prodCd"){
        params.sdselProdCd = $("#txtSelProd").val();
        params.sdselProdNm = "";
      }
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/sideMenu/menuGrp/list.sb', params,function() {
      // <-- 그리드 visible -->
      // 선택한 테이블에 따른 리스트 항목 visible
      var grid = wijmo.Control.getControl("#wjGridSelGroupList");
      var columns = grid.columns;

      // 컬럼 총갯수
      var columnsCnt = 6;
      if(hqOfficeCd == 'A0001' && orgnFg == 'HQ') {
        // 컬럼 총갯수
        columnsCnt = 7;
      }

      // 합계가 0이면 해당 컬럼 숨기기
      for (var j = 0; j < columnsCnt; j++) {
        // [1014 포스프로그램구분]
        if(posVerEnvstVal === "1") {
          if(columns[j].binding == "fixProdFg") {
            columns[j].visible = false;
          }
        } else if(posVerEnvstVal === "2") {
          if(columns[j].binding == "fixProdFg") {
            columns[j].visible = true;
          }
        }
      }
      // <-- //그리드 visible -->
    }, false);
  };
  // <-- //검색 호출 -->

  // 선택그룹 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.status = 'I';
    params.gChk = true;
    params.sdselGrpCd = '자동채번';

    if(brandUseFg === "1") {
      if (orgnFg === "HQ") {
        params.hqBrandCd = null;
      }
    }

    params.fixProdFg = 0;
    params.sdselTypeFg = "C";
    params.halfAndHalfYn = "N";

    // 추가기능 수행 : 파라미터
    $scope._addRow(params, 2);
  };

  // 선택그룹 그리드 행 삭제
  $scope.deleteRow = function() {
    $scope._popConfirm(messages["cmm.choo.delete"], function() {
      // 파라미터 설정
      var params = [];
      for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
        var item = $scope.flex.collectionView.items[i];
        if (item.gChk) {
          if(item.sdselGrpCd === "자동채번" || item.sdselGrpCd  === null || item.sdselGrpCd  === undefined || item.sdselGrpCd  === ""){
            $scope.flex.collectionView.removeAt(i);
          }else {
            if ((orgnFg == "HQ") || (orgnFg == "STORE" && hqOfficeCd == '00000') || (orgnFg == "STORE" && hqOfficeCd != "00000" && item.sdselGrpCd > 799999)) {
              if (item.cnt === 0 || item.cnt === null || item.cnt === undefined || item.cnt === "") {
                $scope.flex.collectionView.removeAt(i);
              } else {
                $scope._popMsg(messages["sideMenu.selectMenu.sdselClass.notNull"]);
                return false;
              }
            } else {
              $scope._popMsg(messages["sideMenu.selectMenu.edited"]);
              $scope._broadcast('sideMenuAttrClassCtrl');
              return false;
            }
          }
        }
      }
      for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
        $scope.flex.collectionView.itemsRemoved[d].status = 'D';
        params.push($scope.flex.collectionView.itemsRemoved[d]);
      }
      // 삭제기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save('/base/prod/sideMenu/menuGrp/save.sb', params, function() {
        $("#sideSelectGroupTitle").html("");
        $("#hdHalfAndHalfYn").val("");
        var attrScope = agrid.getScope('sideMenuSelectClassCtrl');
        attrScope._gridDataInit();   // 그리드 초기화

        $("#sideClassTitle").html("");
        var prodScope = agrid.getScope('sideMenuSelectProdCtrl');
        prodScope._gridDataInit();   // 그리드 초기화
        // 저장 후 그리드 재조회
        $scope._broadcast("sideMenuSelectGroupCtrl");
      });
    });
  };

  // 저장
  $scope.save = function() {
    $scope._popConfirm(messages["cmm.choo.save"], function() {
      $scope.flex.collectionView.commitEdit();

      // 파라미터 설정
      var params = [];
      var orgChk = 0;

      // // dispSeq 재설정   < 선택그룹은 dispSeq가 없다
      // var editItems = [];
      // for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
      //   if( isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
      //     editItems.push($scope.flex.collectionView.items[s]);
      //   }
      // }
      //
      // for (var s = 0; s < editItems.length; s++) {
      //   editItems[s].dispSeq = (s + 1);
      //   $scope.flex.collectionView.editItem(editItems[s]);
      //   $scope.flex.collectionView.commitEdit();
      // }

      for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
        if ((orgnFg == "HQ") || (orgnFg == "STORE" && hqOfficeCd =="00000") || (orgnFg == "STORE" && hqOfficeCd !="00000" && $scope.flex.collectionView.itemsEdited[u].sdselGrpCd > 799999)) {
          if($scope.flex.collectionView.itemsEdited[u].sdselGrpNm == ""){
            $scope._popMsg(messages["sideMenu.selectMenu.sdselGrpNm"] + messages["sideMenu.selectMenu.inputEnv"]);
            return false;
          }
          if($scope.maxChk($scope.flex.collectionView.itemsEdited[u].sdselGrpNm)) {
            $scope.flex.collectionView.itemsEdited[u].status = 'U';
            params.push($scope.flex.collectionView.itemsEdited[u]);
          } else {
            $scope._popMsg(messages["cmm.max50Chk"]);
            return false;
          }

        } else if(orgChk == 0) {
          orgChk = 1;
        }
      }
      for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
        if($scope.flex.collectionView.itemsAdded[i].sdselGrpNm == ""){
          $scope._popMsg(messages["sideMenu.selectMenu.sdselGrpCd"] + messages["sideMenu.selectMenu.inputEnv"]);
          return false;
        }
        if($scope.maxChk($scope.flex.collectionView.itemsAdded[i].sdselGrpNm)) {

          // addRow가 제대로 안된 데이터 다시 파악하여 필수값 채워주기
          if($scope.flex.collectionView.itemsAdded[i].gChk === undefined){
            $scope.flex.collectionView.itemsAdded[i].gChk = true;
            $scope.flex.collectionView.itemsAdded[i].sdselGrpCd = '자동채번';
          }

          $scope.flex.collectionView.itemsAdded[i].status = 'I';
          $scope.flex.collectionView.itemsAdded[i].sdselTypeFg = 'C';
          params.push($scope.flex.collectionView.itemsAdded[i]);
        } else {
          $scope._popMsg(messages["cmm.max50Chk"]);
          return false;
        }
      }

      if (orgChk) {
        $scope._popMsg(messages["sideMenu.selectMenu.edited"]);
        orgChk = 0;
        return false;
      }

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save('/base/prod/sideMenu/menuGrp/save.sb', params, function() {
        $("#sideSelectGroupTitle").html("");
        $("#hdHalfAndHalfYn").val("");
        var attrScope = agrid.getScope('sideMenuSelectClassCtrl');
        attrScope._gridDataInit();   // 그리드 초기화

        $("#sideClassTitle").html("");
        var prodScope = agrid.getScope('sideMenuSelectProdCtrl');
        prodScope._gridDataInit();   // 그리드 초기화
        // 저장 후 그리드 재조회
        $scope._broadcast("sideMenuSelectGroupCtrl");
      });
    });
  };

  $scope.maxChk = function (val){
    var str = val;
    var strLength = 0;
    var strTitle = "";
    var strPiece = "";
    for (i = 0; i < str.length; i++){
      var code = str.charCodeAt(i);
      var ch = str.substr(i,1).toUpperCase();
      //체크 하는 문자를 저장
      strPiece = str.substr(i,1)
      code = parseInt(code);
      if ((ch < "0" || ch > "9") && (ch < "A" || ch > "Z") && ((code > 255) || (code < 0))){
        strLength = strLength + 3; //UTF-8 3byte 로 계산
      }else{
        strLength = strLength + 1;
      }
      if(strLength > 50){ //제한 길이 확인
        return false;
      }else{
        strTitle = strTitle+strPiece; //제한길이 보다 작으면 자른 문자를 붙여준다.
      }
    }
    return true;
  };

}]);


/**
 * 사이드메뉴 선택분류 그리드 생성
 */
app.controller('sideMenuSelectClassCtrl', ['$scope', '$http', 'sdselGrpCd', function ($scope, $http, sdselGrpCd) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuSelectClassCtrl', $scope, $http, false));

  // 상품 본사통제구분 (H : 본사, S: 매장)
  // $scope.prodEnvstVal = prodEnvstVal;

  // 본사에서 들어왔을때는 매장코드가 없다. (가상로그인 후, 세로고침 몇번 하면 gvOrgnFg가 바뀌는 것 예방)
  // $scope.userStoreCd = gvStoreCd;
  // $scope.btnShowFg = false;

  // if(($scope.prodEnvstVal === 'HQ' && isEmptyObject($scope.userStoreCd))
  //     || ($scope.prodEnvstVal === 'STORE' &&  !isEmptyObject($scope.userStoreCd))) {
  //   $scope.btnShowFg = true;
  // }

  // 그리드 Refresh
  $scope.$on('selectMenuRefresh', function (event, data) {
    $scope.flex.refresh();
  });

  // sdselGrpCd Data Setter
  $scope.setSdselGrpCd = function (value) {
    sdselGrpCd.set(value);
  };
  // sdselGrpCd Data Getter
  $scope.getSdselGrpCd = function () {
    return sdselGrpCd.get();
  };

  // 선택
  $scope.selectedSelClassFixProdFg;
  $scope.setSelectedSelClassFixProdFg = function(fixProdFg) {
    $scope.selectedSelClassFixProdFg = fixProdFg;
  };
  $scope.getSelectedSelClassFixProdFg = function(){
    return $scope.selectedSelClassFixProdFg;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 내 콤보박스 설정
    $scope.requireYnDataMap = requireYnDataMap;
    $scope.regStoreFgDataMap = new wijmo.grid.DataMap(regStoreFgData, 'value', 'name'); // 적용매장구분
    $scope.oldRegStoreFgDataMap = new wijmo.grid.DataMap(regStoreFgData, 'value', 'name'); // 적용매장구분
    $scope.topYnDataMap = new wijmo.grid.DataMap(printYnData, 'value', 'name'); // 상단표기여부
    $scope.expandYnDataMap = new wijmo.grid.DataMap(printYnData, 'value', 'name'); // 펼치기여부
    $scope.mappingYnDataMap = new wijmo.grid.DataMap(printYnData, 'value', 'name'); // ERP상품맵핑여부

    if(hqOfficeCd == 'A0001') { // 보나비(A0001) 분류구분 별도 셋팅
      $scope.popUpClassYnDataMap = new wijmo.grid.DataMap(bonaviePopUpClassYnData, 'value', 'name'); // 분류구분
    } else {
      $scope.popUpClassYnDataMap = new wijmo.grid.DataMap(popUpClassYnData, 'value', 'name'); // 분류구분
    }

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === 'sdselClassCd') {
          var item = s.rows[e.row].dataItem;
          if (item.status !== 'I') {
            wijmo.addClass(e.cell, 'wijLink');
          }
        }
      }
    });
    // 선택분류 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === 'sdselClassCd') {
        var dataItem = s.rows[e.row].dataItem;
        if (nvl(dataItem.status, '') === '' && dataItem.status !== 'I') {
          e.cancel = true;
        }
      }
    });
    // 선택분류 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if(col.binding === 'sdselClassCd') {
          if(selectedRow.sdselClassCd !== '' && selectedRow.sdselClassCd !== undefined && selectedRow.sdselClassCd !== '자동채번') {
            $("#sideClassTitle").html(" [" + selectedRow.sdselClassCd + "]" + selectedRow.sdselClassNm);

            if (hqOfficeCd != '00000' && orgnFg == 'STORE' && selectedRow.sdselClassCd <= 799999) {
              $("#btnSdselProdCopy").hide();
              $("#btnUpSelProd").hide();
              $("#btnDownSelProd").hide();
              $("#btnAddSelProd").hide();
              $("#btnDelSelProd").hide();
              $("#btnSaveSelProd").hide();
              $("#btnSdselProdRegStore").hide();
            } else {
              $("#btnSdselProdCopy").show();
              $("#btnUpSelProd").show();
              $("#btnDownSelProd").show();
              $("#btnAddSelProd").show();
              $("#btnDelSelProd").show();
              $("#btnSaveSelProd").show();
              $("#btnSdselProdRegStore").show();
            }

            var params = {};
            params.sdselClassCd = selectedRow.sdselClassCd;
            params.sdselQty = selectedRow.sdselQty;
            params.selGroupFixProdFg = $scope.getSelectedSelClassFixProdFg();
            params.sdselGrpCd = $scope.getSdselGrpCd();
            $scope._broadcast('sideMenuSelectProdCtrl', params);
          }
        }
      }
    });
  };

  // 선택분류 그리드 조회
  $scope.$on('sideMenuSelectClassCtrl', function(event, data) {
    // scope 영역에 변수 Set
    $scope.setSdselGrpCd(data.sdselGrpCd);

    // 변수 set - 고정여부
    $scope.setSelectedSelClassFixProdFg(data.fixProdFg);

    // 파라미터
    var params = {};
    params.sdselGrpCd = data.sdselGrpCd;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/sideMenu/menuClass/list.sb', params,function() {
        // <-- 그리드 visible -->
        // 선택한 테이블에 따른 리스트 항목 visible
        var grid = wijmo.Control.getControl("#wjGridSelClassList");
        var columns = grid.columns;

        // 컬럼 총갯수
        var columnsCnt = 7;

        // 합계가 0이면 해당 컬럼 숨기기
        for (var j = 0; j < columnsCnt; j++) {
          // [1014 포스프로그램구분]
          if(posVerEnvstVal === "1") {
            if(columns[j].binding == "requireYn") {
              columns[j].visible = false;
            }
          } else if(posVerEnvstVal === "2") {
            // [1261 필수선택사용여부]
            if(requireYnEnvstVal === "1") {
              // 선택그룹 그리드에 고정여부
              if($scope.getSelectedSelClassFixProdFg() === "1") {
                if(columns[j].binding == "requireYn" || columns[j].binding == "sdselQty") {
                  columns[j].visible = false;
                }
              } else {
                if(columns[j].binding == "requireYn" || columns[j].binding == "sdselQty") {
                  columns[j].visible = true;
                }
              }
            } else if(requireYnEnvstVal === "0") {
              if(columns[j].binding == "requireYn") {
                columns[j].visible = false;
              }
            }
          }

          // 아티제 전용 처리
          if(columns[j].binding == "requireYn" && orgnFg == 'HQ' && (hqOfficeCd == 'DS001' || hqOfficeCd == 'A0001')) {
            columns[j].visible = true;
          }
        }
        // <-- //그리드 visible -->
    }, false);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 선택분류 그리드 행 추가
  $scope.addRow = function() {
    if($("#sideSelectGroupTitle").html() == ""){
      $scope._popMsg(messages["sideMenu.selectMenu.sdselGrp.null"]);
      return false;
    }
    // 파라미터 설정
    var params = {};
    params.sdselGrpCd = $scope.getSdselGrpCd();
    params.sdselQty = '0';
    params.status = 'I';
    params.gChk = true;
    params.sdselClassCd = '자동채번';
    params.requireYn = "N";
    params.regStoreFg = "0";
    params.topYn = "N";
    params.expandYn = "N";
    params.mappingYn = "N";
    params.popUpClassYn = "N";

    // 추가기능 수행 : 파라미터
    $scope._addRow(params, 2);
  };

  // 선택분류 그리드 행 삭제
  $scope.deleteRow = function() {
    $scope._popConfirm(messages["cmm.choo.delete"], function() {
      // 파라미터 설정
      var params = [];
      for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
        var item = $scope.flex.collectionView.items[i];
        if (item.gChk) {
          if(item.sdselClassCd === "자동채번" || item.sdselClassCd  === null || item.sdselClassCd  === undefined || item.sdselClassCd  === ""){
            $scope.flex.collectionView.removeAt(i);
          }else {
            if (item.cnt === 0 || item.cnt === null || item.cnt === undefined || item.cnt === "") {
              $scope.flex.collectionView.removeAt(i);
            } else {
              $scope._popMsg(messages["sideMenu.selectMenu.sdselClass.notNull"]);
              return false;
            }
          }
        }
      }
      for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
        $scope.flex.collectionView.itemsRemoved[d].status = 'D';
        params.push($scope.flex.collectionView.itemsRemoved[d]);
      }
      $scope._save('/base/prod/sideMenu/menuClass/save.sb', params, function() {

        // 선택그룹 리스트 재조회
        //$scope._broadcast("sideMenuSelectGroupCtrl");

        // 선택분류 리스트 재조회
        var grpGrid = agrid.getScope('sideMenuSelectGroupCtrl');
        var selectedSelGroup = grpGrid.getSelectedSelGroup();
        $scope._broadcast('sideMenuSelectClassCtrl', selectedSelGroup);

        // 선택상품 리스트 재조회
        $("#sideClassTitle").html("");
        var prodScope = agrid.getScope('sideMenuSelectProdCtrl');
        prodScope._gridDataInit();   // 그리드 초기화

      });
    });
  };

  // 선택분류 그리드 저장
  $scope.saveClass = function() {
    var msg = messages["cmm.choo.save"];

    if(orgnFg == 'HQ' && hqOfficeCd == 'DS021') {
      msg = messages["sideMenu.selectMenu.sdselClassRegStoreAlert"] + " " +  messages["cmm.choo.save"]; // 적용매장구분 변경된 경우 등록된 적용매장은 모두 삭제됩니다. 저장하시겠습니까?
    }

    $scope._popConfirm(msg, function() {
      $scope.flex.collectionView.commitEdit();

      // 파라미터 설정
      var params = [];

      // dispSeq 재설정
      // var editItems = [];
      // for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
      //   if($scope.flex.collectionView.items[s].dispSeq !== (s+1)){
      //     if( isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
      //       editItems.push($scope.flex.collectionView.items[s]);
      //     }
      //   }
      // }
      //
      // for (var s = 0; s < editItems.length; s++) {
      //   editItems[s].dispSeq = (s + 1);
      //   $scope.flex.collectionView.editItem(editItems[s]);
      //   editItems[s].status = "U";
      //   $scope.flex.collectionView.commitEdit();
      // }
      for(var s = 0, num = 1; s < $scope.flex.rows.length; s++, num++) {
        if ($scope.flex.collectionView.items[s].dispSeq !== num) {
          $scope.flex.collectionView.items[s].dispSeq = num;
          $scope.flex.collectionView.editItem($scope.flex.collectionView.items[s]);
          $scope.flex.collectionView.items[s].status = "U";
          $scope.flex.collectionView.commitEdit();
        }
      }

      for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
        if($scope.flex.collectionView.itemsEdited[u].sdselClassNm == ""){
          $scope._popMsg(messages["sideMenu.selectMenu.sdselClassNm"] + messages["sideMenu.selectMenu.inputEnv"]);
          return false;
        }

        // 분류명 최대길이 체크
        if (nvl($scope.flex.collectionView.itemsEdited[u].sdselClassNm, '') !== '' &&
            nvl($scope.flex.collectionView.itemsEdited[u].sdselClassNm + '', '').getByteLengthForOracle() > 50) {
          var msg = messages["sideMenu.selectMenu.sdselClassNm"] + messages["cmm.overLength"] + " 50 " +
              ", 현재 : " + $scope.flex.collectionView.itemsEdited[u].sdselClassNm.getByteLengthForOracle() + messages["cmm.bateLengthInfo"];
          $scope._popMsg(msg);
          return false;
        }

        if(nvl($scope.flex.collectionView.itemsEdited[u].fixProdCnt, 0) > nvl($scope.flex.collectionView.itemsEdited[u].sdselQty, 0)){
          $scope._popMsg( "'" + $scope.flex.collectionView.itemsEdited[u].sdselClassNm + "[" + $scope.flex.collectionView.itemsEdited[u].sdselClassCd + "]'"
              + messages["sideMenu.selectMenu.sdselQtyChk.msg"]
              + "<br/> (선택분류수량 : " + $scope.flex.collectionView.itemsEdited[u].sdselQty + " / 고정상품수량합계 : "  + $scope.flex.collectionView.itemsEdited[u].fixProdCnt+ ")");
          return false;
        }

        // 필수선택여부가 'Y'이면 수량은 1 이상
        if($scope.flex.collectionView.itemsEdited[u].requireYn === "Y") {
          if(parseInt(nvl($scope.flex.collectionView.itemsEdited[u].sdselQty, 0)) >= 1) {
          } else {
            $scope._popMsg(messages["sideMenu.selectMenu.requireYnQtyChk.msg"]); // 필수선택시 수량은 1 이상 입력해주세요.
            return false;
          }
        }

        if($scope.maxChk($scope.flex.collectionView.itemsEdited[u].sdselClassNm)){
          $scope.flex.collectionView.itemsEdited[u].status = 'U';
          params.push($scope.flex.collectionView.itemsEdited[u]);
        } else {
          $scope._popMsg(messages["cmm.max50Chk"]);
          return false;
        }
      }

      for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
        if($scope.flex.collectionView.itemsAdded[i].sdselClassNm == ""){
          $scope._popMsg(messages["sideMenu.selectMenu.sdselClassNm"] + messages["sideMenu.selectMenu.inputEnv"]);
          return false;
        }

        // 분류명 최대길이 체크
        if (nvl($scope.flex.collectionView.itemsAdded[i].sdselClassNm, '') !== '' &&
            nvl($scope.flex.collectionView.itemsAdded[i].sdselClassNm + '', '').getByteLengthForOracle() > 50) {
          var msg = messages["sideMenu.selectMenu.sdselClassNm"] + messages["cmm.overLength"] + " 50 " +
              ", 현재 : " + $scope.flex.collectionView.itemsAdded[i].sdselClassNm.getByteLengthForOracle() + messages["cmm.bateLengthInfo"];
          $scope._popMsg(msg);
          return false;
        }

        // 필수선택여부가 'Y'이면 수량은 1 이상
        if($scope.flex.collectionView.itemsAdded[i].requireYn === "Y") {
          if(parseInt(nvl($scope.flex.collectionView.itemsAdded[i].sdselQty, 0)) >= 1) {
          } else {
            $scope._popMsg(messages["sideMenu.selectMenu.requireYnQtyChk.msg"]); // 필수선택시 수량은 1 이상 입력해주세요.
            return false;
          }
        }

        if($scope.maxChk($scope.flex.collectionView.itemsAdded[i].sdselClassNm)) {

          // addRow가 제대로 안된 데이터 다시 파악하여 필수값 채워주기
          if($scope.flex.collectionView.itemsAdded[i].gChk === undefined){
            $scope.flex.collectionView.itemsAdded[i].sdselGrpCd =  $scope.getSdselGrpCd();
            $scope.flex.collectionView.itemsAdded[i].gChk = true;
            $scope.flex.collectionView.itemsAdded[i].sdselClassCd = '자동채번';
            if($scope.flex.collectionView.itemsAdded[i].sdselQty === null ||
                $scope.flex.collectionView.itemsAdded[i].sdselQty === undefined ||
                $scope.flex.collectionView.itemsAdded[i].sdselQty === ""){
              $scope.flex.collectionView.itemsAdded[i].sdselQty = 0;
            }
          }

          $scope.flex.collectionView.itemsAdded[i].status = "I";
          params.push($scope.flex.collectionView.itemsAdded[i]);
        } else {
          $scope._popMsg(messages["cmm.max50Chk"]);
          return false;
        }
      }

        // 보나비(A0001) 본사 및 하위매장만 체크
        if (hqOfficeCd == "A0001") {
            // 선택그룹 진행단계를 사용하는 경우 체크
            if ($("#hdHalfAndHalfYn").val() == "Y") {
                for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                    if ($scope.flex.collectionView.items[i].popUpClassYn == "N") {
                        $scope._popMsg(messages["sideMenu.selectMenu.popUpClassYnChk.use.msg"]); // 선택그룹의 진행단계가 '사용'이면, 선택분류의 진행단계 '미사용'은 선택할 수 없습니다.
                        return false;
                    }
                }
            }
        }

      // 미스터피자 본사 및 하위매장만 체크
      if (hqOfficeCd == "H0614" || hqOfficeCd == 'H0616' || hqOfficeCd == 'DS008') {

          // 선택분류 분류구분 별 갯수
          var pizzaCnt = 0;
          var edgeCnt = 0;
          var toppingCnt = 0;

          // 선택분류 분류구분 [피자]의 row num
          var pizzaRnum = 0;

          for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
              if ($scope.flex.collectionView.items[i].popUpClassYn == "Y") {
                  pizzaCnt++;
                  pizzaRnum = i;
              }
              if ($scope.flex.collectionView.items[i].popUpClassYn == "E") { edgeCnt++; }
              if ($scope.flex.collectionView.items[i].popUpClassYn == "T") { toppingCnt++; }
          }

          if (pizzaCnt > 1) {
              $scope._popMsg(messages["sideMenu.selectMenu.popUpClassYnChk.Y.msg"]); // 분류구분 [피자]는 최대 1개만 선택이 가능합니다.
              return false;
          }

          if (edgeCnt > 1) {
              $scope._popMsg(messages["sideMenu.selectMenu.popUpClassYnChk.E.msg"]); // 분류구분 [엣지]는 최대 1개만 선택이 가능합니다.
              return false;
          }

          if (toppingCnt > 1) {
              $scope._popMsg(messages["sideMenu.selectMenu.popUpClassYnChk.T.msg"]); // 분류구분 [토핑]은 최대 1개만 선택이 가능합니다.
              return false;
          }

          // 선택그룹 하프앤하프를 사용하는 경우 체크
          if($("#hdHalfAndHalfYn").val() == "Y") {
              // 분류구분 [피자]가 있을때, 수량은 반드시 2로 입력
              if (pizzaCnt > 0) {
                  if(parseInt(nvl($scope.flex.collectionView.items[pizzaRnum].sdselQty, 0)) !== 2){
                      $scope._popMsg(messages["sideMenu.selectMenu.popUpClassYnChk.sdselQty.msg"]); // 선택그룹 하프앤하프 사용시, 분류구분 [피자]의 수량은 2로 입력하여 주십시오.
                      return false;
                  }
              }
          }
      }

      // console.log('2 params',params);

      if(orgnFg == 'HQ') {
        // 적용매장 전체 삭제
        $scope._postJSONSave.withOutPopUp("/base/prod/sideMenu/menuClass/getSdselClassRegStoreDeleteAll.sb", params, function(){
          // 저장
          $scope.save(params);
        });
      } else {
        // 저장
        $scope.save(params);
      }

    });
  };

  // 저장
  $scope.save = function(params) {
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save('/base/prod/sideMenu/menuClass/save.sb', params, function() {

      // 선택그룹 리스트 재조회
      //$scope._broadcast("sideMenuSelectGroupCtrl");

      // 선택분류 리스트 재조회
      var grpGrid = agrid.getScope('sideMenuSelectGroupCtrl');
      var selectedSelGroup = grpGrid.getSelectedSelGroup();
      $scope._broadcast('sideMenuSelectClassCtrl', selectedSelGroup);

      // 선택상품 리스트 재조회
      $("#sideClassTitle").html("");
      var prodScope = agrid.getScope('sideMenuSelectProdCtrl');
      prodScope._gridDataInit();   // 그리드 초기화

    });
  };

  $scope.maxChk = function (val){
    var str = val;
    var strLength = 0;
    var strTitle = "";
    var strPiece = "";
    for (i = 0; i < str.length; i++){
      var code = str.charCodeAt(i);
      var ch = str.substr(i,1).toUpperCase();
      //체크 하는 문자를 저장
      strPiece = str.substr(i,1)
      code = parseInt(code);
      if ((ch < "0" || ch > "9") && (ch < "A" || ch > "Z") && ((code > 255) || (code < 0))){
        strLength = strLength + 3; //UTF-8 3byte 로 계산
      }else{
        strLength = strLength + 1;
      }
      if(strLength > 50){ //제한 길이 확인
        return false;
      }else{
        strTitle = strTitle+strPiece; //제한길이 보다 작으면 자른 문자를 붙여준다.
      }
    }
    return true;
  };

  // 위로 옮기기 버튼
  $scope.rowMoveUp = function() {
    var movedRows = 0;
    for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
      var item = $scope.flex.collectionView.items[i];
      if (i > 0 && item.gChk) {
        if (!$scope.flex.collectionView.items[i - 1].gChk) {
          movedRows = i - 1;
          var tmpItem = $scope.flex.collectionView.items[movedRows];
          $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
          $scope.flex.collectionView.items[i] = tmpItem;
          $scope.flex.collectionView.commitEdit();
          $scope.flex.collectionView.refresh();
        }
      }
    }
    $scope.flex.select(movedRows, 1);
  };

  // 아래로 옮기기 버튼
  $scope.rowMoveDown = function() {
    var movedRows = 0;
    for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
      var item = $scope.flex.collectionView.items[i];
      if ((i < $scope.flex.itemsSource.itemCount - 1) && item.gChk) {
        if (!$scope.flex.collectionView.items[i + 1].gChk) {
          movedRows = i + 1;
          var tmpItem = $scope.flex.collectionView.items[movedRows];
          $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
          $scope.flex.collectionView.items[i] = tmpItem;
          $scope.flex.collectionView.commitEdit();
          $scope.flex.collectionView.refresh();
        }
      }
    }
    $scope.flex.select(movedRows, 1);
  };

  // 선택 매장
  $scope.selectedSdselClass;
  $scope.setSelectedSdselClass = function(store) {
    $scope.selectedSdselClass = store;
  };
  $scope.getSelectedSdselClass = function(){
    return $scope.selectedSdselClass;
  };

  // 선택분류복사
  $scope.sdselClassCopy = function() {
    var params = {};
    params.sdselGrpCd = $scope.getSdselGrpCd();
    params.sdselGrpCdNm = $("#sideSelectGroupTitle").html();
    params.halfAndHalfYn = $("#hdHalfAndHalfYn").val();
    $scope.setSelectedSdselClass(params);

    $scope.wjSdselClassCopyLayer.show(true);
    event.preventDefault();
  };

  // 선택분류 적용매장등록
  $scope.sdselClassRegStore = function() {
    var params = {};
    params.sdselGrpCd = $scope.getSdselGrpCd();
    params.sdselGrpCdNm = $("#sideSelectGroupTitle").html();
    $scope.setSelectedSdselClass(params);

    $scope.wjSdselClassRegStoreLayer.show(true);
    event.preventDefault();
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {

    // 선택분류복사 팝업 핸들러 추가
    $scope.wjSdselClassCopyLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('sdselClassCopyCtrl', $scope.getSelectedSdselClass());
      }, 50)
    });

    // 선택분류 적용매장등록 팝업 핸들러 추가
    $scope.wjSdselClassRegStoreLayer.shown.addHandler(function (s) {
      setTimeout(function() {
          $scope._broadcast('sdselClassRegStoreCtrl', $scope.getSelectedSdselClass());
      }, 50)
    });
  });

}]).factory('sdselGrpCd', function () {
  // 사이드메뉴 선택분류 그리드 의 변수 값 영역
  var sdselGrpCd = {};
  sdselGrpCd.set = function (value) {
    sdselGrpCd.value = value;
  };
  sdselGrpCd.get = function () {
    return sdselGrpCd.value;
  };
  return sdselGrpCd;
});


/**
 * 사이드메뉴 선택상품 그리드 생성
 */
app.controller('sideMenuSelectProdCtrl', ['$scope', '$http', 'sdselClassCd', function ($scope, $http, sdselClassCd) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuSelectProdCtrl', $scope, $http, false));

  // 상품 본사통제구분 (H : 본사, S: 매장)
  // $scope.prodEnvstVal = prodEnvstVal;

  // 본사에서 들어왔을때는 매장코드가 없다. (가상로그인 후, 세로고침 몇번 하면 gvOrgnFg가 바뀌는 것 예방)
  // $scope.userStoreCd = gvStoreCd;
  // $scope.btnShowFg = false;

  // if(($scope.prodEnvstVal === 'HQ' && isEmptyObject($scope.userStoreCd))
  //     || ($scope.prodEnvstVal === 'STORE' &&  !isEmptyObject($scope.userStoreCd))) {
  //   $scope.btnShowFg = true;
  // }

  // 그리드 Refresh
  $scope.$on('selectMenuRefresh', function (event, data) {
    $scope.flex.refresh();
  });

  // sdselGrpCd Data Setter
  $scope.setSdselGrpCd = function (value) {
    $scope.sdselGrpCd = value;
  };
  // sdselGrpCd Data Getter
  $scope.getSdselGrpCd = function () {
    return $scope.sdselGrpCd;
  };

  // sdselGrpCd Data Setter
  $scope.setSdselClassCd = function (value) {
    sdselClassCd.set(value);
  };
  // sdselGrpCd Data Getter
  $scope.getSdselClassCd = function () {
    return sdselClassCd.get();
  };

  // 선택분류의 수량 set
  $scope.sdselQty = 0;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    // s.formatItem.addHandler(function (s, e) {
    //   if (e.panel === s.cells) {
    //     var col = s.columns[e.col];
    //     if (col.binding === 'prodCd') {
    //       var item = s.rows[e.row].dataItem;
    //       if (item.status !== 'I') {
    //         wijmo.addClass(e.cell, 'wijLink');
    //       }
    //     }
    //   }
    // });

    // 그리드 내 콤보박스 설정
    $scope.brandDataMap = new wijmo.grid.DataMap(brandList, 'value', 'name'); // 브랜드
    $scope.fixProdFgDataMap = fixProdFgDataMap;
    $scope.regStoreFgDataMap = new wijmo.grid.DataMap(regStoreFgData, 'value', 'name'); // 적용매장구분
    $scope.oldRegStoreFgDataMap = new wijmo.grid.DataMap(regStoreFgData, 'value', 'name'); // 적용매장구분
    $scope.printYnDataMap = new wijmo.grid.DataMap(printYnData, 'value', 'name'); // 출력여부

    // 선택상품 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === 'prodCd' || col.binding === 'prodNm') {
        e.cancel = true;
      }
    });
    // 그리드 선택 이벤트
    // s.addEventListener(s.hostElement, 'mousedown', function(e) {
    //   var ht = s.hitTest(e);
    //   if (ht.cellType === wijmo.grid.CellType.Cell) {
    //     var col = ht.panel.columns[ht.col];
    //     var selectedRow = s.rows[ht.row].dataItem;
    //     // 상품코드/상품명 클릭시
    //     if (col.binding === 'prodCd') {
    //       $scope.selectProdView(false);
    //     }
    //   }
    // });
};
  // 선택상품 그리드 조회
  $scope.$on('sideMenuSelectProdCtrl', function(event, data) {
    // scope 영역에 변수 Set
    $scope.setSdselClassCd(data.sdselClassCd); // 선택분류코드
    $scope.sdselQty = parseInt(data.sdselQty); // 선택분류의 수량
    $scope.selGroupFixProdFg = data.selGroupFixProdFg; // 선택그룹의 고정여부
    if(data.sdselGrpCd !== null && data.sdselGrpCd !== '' && data.sdselGrpCd !== undefined){
      $scope.setSdselGrpCd(data.sdselGrpCd);
    }

    // 파라미터
    var params = {};
    params.sdselClassCd = data.sdselClassCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/sideMenu/menuProd/list.sb', params,function() {
      // <-- 그리드 visible -->
      // 선택한 테이블에 따른 리스트 항목 visible
      var grid = wijmo.Control.getControl("#wjGridSelProdList");
      var columns = grid.columns;

      // 컬럼 총갯수
      var columnsCnt = 6;

      // 합계가 0이면 해당 컬럼 숨기기
      for (var j = 0; j < columnsCnt; j++) {
          // 선택그룹 그리드에 고정여부
          if($scope.selGroupFixProdFg === "1") {
            if(columns[j].binding == "fixProdFg") {
              columns[j].visible = false;
            }
          } else {
            if(columns[j].binding == "fixProdFg") {
              columns[j].visible = true;
            }
          }
      }
      // <-- //그리드 visible -->
    }, false);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 선택상품 그리드 행 추가
  $scope.addRow = function() {
    if($("#sideClassTitle").html() == ""){
      $scope._popMsg(messages["sideMenu.selectMenu.sdselClass.null"]);
      return false;
    }

    var params = {};
    params.sdselClassCd = $scope.getSdselClassCd();
    params.sdselGrpBrandCd = $("#hdSdselGrpBrandCd").val();
    params.sdselGrpCd = agrid.getScope('sideMenuSelectClassCtrl').getSdselGrpCd();

    $scope._broadcast('sideMenuProdCtrl', params);

    $scope.selectProdView(true);
  };

  // 선택상품 그리드 행 삭제
  $scope.deleteRow = function() {
    $scope._popConfirm(messages["cmm.choo.delete"], function() {
      // 파라미터 설정
      var params = [];
      for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
        var item = $scope.flex.collectionView.items[i];
        if (item.gChk) {
          $scope.flex.collectionView.removeAt(i);
        }
      }
      for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
        $scope.flex.collectionView.itemsRemoved[d].status = 'D';
        params.push($scope.flex.collectionView.itemsRemoved[d]);
      }

      // 삭제기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save('/base/prod/sideMenu/menuProd/save.sb', params, function() {

        // 선택상품 리스트 재조회
        var params = {};
        params.sdselClassCd = $scope.getSdselClassCd();
        params.sdselQty = $scope.sdselQty;
        $scope._broadcast('sideMenuSelectProdCtrl', params);

        // 선택분류 리스트 재조회
        var grpGrid = agrid.getScope('sideMenuSelectGroupCtrl');
        var selectedSelGroup = grpGrid.getSelectedSelGroup();
        $scope._broadcast('sideMenuSelectClassCtrl', selectedSelGroup);
        // $scope._broadcast("sideMenuSelectGroupCtrl");
      });
    });
  };

  // 선택상품 그리드 저장
  $scope.saveProd = function() {
    var msg = messages["cmm.choo.save"];

    if(orgnFg == 'HQ' && hqOfficeCd == 'DS021') {
      msg = messages["sideMenu.selectMenu.sdselClassRegStoreAlert"] + " " +  messages["cmm.choo.save"]; // 적용매장구분 변경된 경우 등록된 적용매장은 모두 삭제됩니다. 저장하시겠습니까?
    }

    $scope._popConfirm(msg, function() {
      $scope.flex.collectionView.commitEdit();

      // 파라미터 설정
      var params = [];

      // dispSeq 재설정1
      // var editItems = [];
      // for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
      //   if (isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
      //     editItems.push($scope.flex.collectionView.items[s]);
      //   }
      // }
      //
      // for (var s = 0; s < editItems.length; s++) {
      //   editItems[s].dispSeq = (s + 1);
      //   if($scope.flex.collectionView.items[s].dispSeq !== editItems[s].dispSeq) {
      //     $scope.flex.collectionView.editItem(editItems[s]);
      //     editItems[s].status = "U";
      //     $scope.flex.collectionView.commitEdit();
      //   }
      // }
      for(var s = 0, num = 1; s < $scope.flex.rows.length; s++, num++) {
        if ($scope.flex.collectionView.items[s].dispSeq !== num) {
          $scope.flex.collectionView.items[s].dispSeq = num;
          $scope.flex.collectionView.editItem($scope.flex.collectionView.items[s]);
          $scope.flex.collectionView.items[s].status = "U";
          $scope.flex.collectionView.commitEdit();
        }
      }

      for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
        $scope.flex.collectionView.itemsEdited[u].status = 'U';
        params.push($scope.flex.collectionView.itemsEdited[u]);
      }
      for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
        $scope.flex.collectionView.itemsAdded[i].status = 'I';
        params.push($scope.flex.collectionView.itemsAdded[i]);
      }

      for (var m = 0; m < params.length; m++) {
        if(params[m].status !== 'D') {
          //if(  params[m].addProdQty === null  || params[m].addProdQty === '' || params[m].addProdQty === 0 ) {
          if(  params[m].addProdQty === null  || params[m].addProdQty === '') {
            $scope._popMsg("상품 수량을 한 개 이상 입력해주세요.");
            return false;
          }
        }
      }

      // 구분이 '고정'인 상품의 수량합 체크(선택분류의 수량보다 크면 안됨)
      var chkFixProdCnt = 0;
      for (var m = 0; m < $scope.flex.collectionView.items.length; m++) {
        if($scope.flex.collectionView.items[m].status !== 'D') {
          if(parseInt(nvl($scope.flex.collectionView.items[m].fixProdFg,0)) === 1) {
            chkFixProdCnt += parseInt($scope.flex.collectionView.items[m].addProdQty);
          }
        }
      }

      if(chkFixProdCnt > parseInt($scope.sdselQty)){
        $scope._popMsg(messages["sideMenu.selectMenu.fixProdCntChk.msg"] + "<br/> (고정상품수량합계 : " + chkFixProdCnt + " / 선택분류수량 : "  + $scope.sdselQty + ")"); // 구분이 '고정'인 상품의 수량합이 선택분류의 수량보다 클 수 없습니다.
        return false;
      }

      for (var m = 0; m < params.length; m++) {
        // 단가 100000000 이상 입력 불가
        if (params[m].addProdUprc >= 100000000) {
          $scope._popMsg(messages["sideMenu.selectMenu.addProdUprcInChk"]); // 단가는 숫자만(정수8자리) 입력해주세요.
          return false;
        }
        // 단가 -100000000 이하 입력 불가
        if (params[m].addProdUprc <= -100000000) {
          $scope._popMsg(messages["sideMenu.selectMenu.addProdUprcInChk"]); // 단가는 숫자만(정수8자리) 입력해주세요.
          return false;
        }

        // 수량 99 이상 입력 불가
        if (params[m].addProdQty >= 100) {
          $scope._popMsg(messages["sideMenu.selectMenu.addProdQtyInChk"]); // 수량은 숫자만(정수2자리) 입력해주세요.
          return false;
        }
        // 수량 -99 이하 입력 불가
        if (params[m].addProdQty <= -100) {
          $scope._popMsg(messages["sideMenu.selectMenu.addProdQtyInChk"]); // 수량은 숫자만(정수2자리) 입력해주세요.
          return false;
        }
      }

        // 새로 등록하려는 상품이 선택그룹에 이미 등록되어 있는지 확인 로직 추가(아티제만 적용, 나머지 본사는 그대로)
        if (hqOfficeCd === "A0001") {

            // 새로 등록하는 선택상품 코드 셋팅
            var str = "";
            for (var m = 0; m < params.length; m++) {
                if (params[m].status === "I") {
                    str += params[m].prodCd + ",";
                }
            }

            if (str.length > 0) {
                var vParams = {};
                vParams.sdselGrpCd = agrid.getScope('sideMenuSelectClassCtrl').getSdselGrpCd();
                vParams.prodCd = str.substr(0, str.length - 1);
                $.postJSON("/base/prod/sideMenu/chkMenuProdUse.sb", vParams, function (result) {
                    if (result.status === "OK") {
                        if (result.data.list == null || result.data.list.length == 0) {
                            if (orgnFg == 'HQ') {
                                // 적용매장 전체 삭제
                                $scope._postJSONSave.withOutPopUp("/base/prod/sideMenu/menuProd/getSdselProdRegStoreDeleteAll.sb", params, function () {
                                    // 저장
                                    $scope.save(params);
                                });
                            } else {
                                // 저장
                                $scope.save(params);
                            }
                        } else {

                            // 선택그룹에 이미 등록되어있는 상품 alert msg 띄우기
                            var popMsg = "다른 선택분류에 이미 등록된 상품이 있습니다.</br></br>";
                            for (var k = 0; k < result.data.list.length; k++) {
                                popMsg += "[" + result.data.list[k].prodCd + "]" + result.data.list[k].prodNm + " : 선택분류[" + result.data.list[k].sdselClassCd + "]에 등록" + "</br>"
                            }
                            $scope._popMsg(popMsg);
                            return false;
                        }
                    }
                });
            } else {
                if (orgnFg == 'HQ') {
                    // 적용매장 전체 삭제
                    $scope._postJSONSave.withOutPopUp("/base/prod/sideMenu/menuProd/getSdselProdRegStoreDeleteAll.sb", params, function () {
                        // 저장
                        $scope.save(params);
                    });
                } else {
                    // 저장
                    $scope.save(params);
                }
            }
        } else {
            if (orgnFg == 'HQ') {
                // 적용매장 전체 삭제
                $scope._postJSONSave.withOutPopUp("/base/prod/sideMenu/menuProd/getSdselProdRegStoreDeleteAll.sb", params, function () {
                    // 저장
                    $scope.save(params);
                });
            } else {
                // 저장
                $scope.save(params);
            }
        }
    });
  };

  // 저장
  $scope.save = function(params) {
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save('/base/prod/sideMenu/menuProd/save.sb', params, function() {

      // 선택상품 리스트 재조회
      var params = {};
      params.sdselClassCd = $scope.getSdselClassCd();
      params.sdselQty = $scope.sdselQty;
      $scope._broadcast('sideMenuSelectProdCtrl', params);

      // 선택분류 리스트 재조회
      var grpGrid = agrid.getScope('sideMenuSelectGroupCtrl');
      var selectedSelGroup = grpGrid.getSelectedSelGroup();
      $scope._broadcast('sideMenuSelectClassCtrl', selectedSelGroup);
      // $scope._broadcast("sideMenuSelectGroupCtrl");
    });
  };

  // 위로 옮기기 버튼
  $scope.rowMoveUp = function() {
    var movedRows = 0;
    for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
      var item = $scope.flex.collectionView.items[i];
      if (i > 0 && item.gChk) {
        if (!$scope.flex.collectionView.items[i - 1].gChk) {
          movedRows = i - 1;
          var tmpItem = $scope.flex.collectionView.items[movedRows];
          $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
          $scope.flex.collectionView.items[i] = tmpItem;
          $scope.flex.collectionView.commitEdit();
          $scope.flex.collectionView.refresh();
        }
      }
    }
    $scope.flex.select(movedRows, 1);
  };
  // 아래로 옮기기 버튼
  $scope.rowMoveDown = function() {
    var movedRows = 0;
    for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
      var item = $scope.flex.collectionView.items[i];
      if ((i < $scope.flex.itemsSource.itemCount - 1) && item.gChk) {
        if (!$scope.flex.collectionView.items[i + 1].gChk) {
          movedRows = i + 1;
          var tmpItem = $scope.flex.collectionView.items[movedRows];
          $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
          $scope.flex.collectionView.items[i] = tmpItem;
          $scope.flex.collectionView.commitEdit();
          $scope.flex.collectionView.refresh();
        }
      }
    }
    $scope.flex.select(movedRows, 1);
  };
  // 상품선택 팝업
  $scope.selectProdView = function(type) {
    var popUp = $scope.sideMenuProdLayer;
    setTimeout(function() {
      popUp.show(true, function (s) {
        // 수정 버튼 눌렀을때만
        if (s.dialogResult === "wj-hide-apply") {
          var scope = agrid.getScope('sideMenuProdCtrl', $scope.getSdselClassCd());
          for (var i = 0; i < scope.flex.collectionView.items.length; i++) {
            if (scope.flex.collectionView.items[i].gChk) {

              if(brandUseFg === "1"){
                if(orgnFg === "HQ") {
                  var hqBrandCd = scope.flex.collectionView.items[i].hqBrandCd;
                }
              }
              var prodCd = scope.flex.collectionView.items[i].prodCd;
              var prodNm = scope.flex.collectionView.items[i].prodNm;
              if ( type ) {
                // 행추가
                var params = {};
                params.sdselClassCd = $scope.getSdselClassCd();
                params.status = 'I';
                if(brandUseFg === "1") {
                  if (orgnFg === "HQ") {
                    params.hqBrandCd = hqBrandCd;
                  }
                }
                params.prodCd = prodCd;
                params.prodNm = prodNm;
                params.addProdUprc = 0;
                params.addProdQty = 1; // 기본으로 하나씩 들어가도록 // todo 추후 수정
                params.gChk = true;
                params.fixProdFg = 0;
                params.regStoreFg = "0";
                params.printYn = 'Y';
                // 추가기능 수행 : 파라미터
                $scope._addRow(params);
              } else {
                var selectedRow = $scope.flex.selectedRows[0]._idx;
                $scope.flex.setCellData(selectedRow, 'prodCd', prodCd);
                $scope.flex.setCellData(selectedRow, 'prodNm', prodNm);
              }
            }
          }
        }
      });
    }, 50);
  };

  // 선택 매장
  $scope.selectedSdselProd;
  $scope.setSelectedSdselProd = function(store) {
    $scope.selectedSdselProd = store;
  };
  $scope.getSelectedSdselProd = function(){
    return $scope.selectedSdselProd;
  };

  // 선택상품복사
  $scope.sdselProdCopy = function() {
    var params = {};
    params.sdselGrpCd = $scope.getSdselGrpCd();
    params.sdselGrpCdNm = $("#sideSelectGroupTitle").html();
    params.sdselClassCd = $scope.getSdselClassCd();
    params.sdselClassCdNm = $("#sideClassTitle").html();
    params.sdselQty = $scope.sdselQty;
    params.selGroupFixProdFg = $scope.selGroupFixProdFg;
    $scope.setSelectedSdselProd(params);

    $scope.wjSdselProdCopyLayer.show(true);
    event.preventDefault();
  };

  // 선택상품 적용매장등록
  $scope.sdselProdRegStore = function() {
    var params = {};
    params.sdselClassCd = $scope.getSdselClassCd();
    params.sdselClassCdNm = $("#sideClassTitle").html();
    $scope.setSelectedSdselProd(params);

    $scope.wjSdselProdRegStoreLayer.show(true);
    event.preventDefault();
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {

    // 선택상품복사 팝업 핸들러 추가
    $scope.wjSdselProdCopyLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('sdselProdCopyCtrl', $scope.getSelectedSdselProd());
      }, 50)
    });

    // 선택상품 적용매장등록 팝업 핸들러 추가
    $scope.wjSdselProdRegStoreLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('sdselProdRegStoreCtrl', $scope.getSelectedSdselProd());
      }, 50)
    });
  });

}]).factory('sdselClassCd', function () {
  // 사이드메뉴 선택상품 그리드 의 변수 값 영역
  var sdselClassCd = {};
  sdselClassCd.set = function (value) {
    sdselClassCd.value = value;
  };
  sdselClassCd.get = function () {
    return sdselClassCd.value;
  };
  return sdselClassCd;
});
