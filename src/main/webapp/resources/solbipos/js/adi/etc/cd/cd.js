/****************************************************************
 *
 * 파일명 : cd.js
 * 설  명 : 명칭관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.09.13     노현수      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();
// 조회조건 DropBoxDataMap
var useYnFg = [
  {"name":"전체","value":""},
  {"name":"사용","value":"Y"},
  {"name":"사용안함","value":"N"}
];
// 사용여부 DropBoxDataMap
var useYnFgDataMap = new wijmo.grid.DataMap([
  {id: "Y", name: "사용"},
  {id: "N", name: "사용안함"}
  ], 'id', 'name');

// 코드 = '146' 이면 코드항목2 YN
var useYnCiDataMap = new wijmo.grid.DataMap([
  {id: "Y", name: "Y"},
  {id: "N", name: "N"}
], 'id', 'name');

/**
 * 대표명칭 그리드 생성
 */
app.controller('representCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('representCtrl', $scope, $http, true));
  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("srchUseYnFg", useYnFg);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("representCtrl");
    // 그리드 내 콤보박스 설정
    $scope.useYnFgDataMap = useYnFgDataMap;
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "nmcodeCd") {
          var item = s.rows[e.row].dataItem;
          if (item.status !== "I") {
            wijmo.addClass(e.cell, 'wijLink');
            //wijmo.addClass(e.cell, 'wj-custom-readonly');
          } else {
            //wijmo.removeClass(e.cell, 'wj-custom-readonly');
          }
        }
      }
    });
    // 대표명칭 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (sender, elements) {
      var col = sender.columns[elements.col];
      if (col.binding === "nmcodeCd") {
        var dataItem = s.rows[elements.row].dataItem;
        if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
          elements.cancel = true;
        }
      }
    });
    // 대표명칭 그리드 선택 이벤트
    s.hostElement.addEventListener('mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var selectedRow = s.rows[ht.row].dataItem
        var col = ht.panel.columns[ht.col];
        if( col.binding === "nmcodeCd" && selectedRow.status !== "I") {
          //$scope._broadcast('detailCtrl', selectedRow.nmcodeCd);
          $scope._broadcast('detailCtrl', selectedRow);
        }
      }
    });
  };
  // 대표명칭 그리드 조회
  $scope.$on("representCtrl", function(event, data) {
    // 파라미터
    var params = {};
    params.nmcodeGrpCd = "000";
    params.useYn = $scope.useYnFg;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/adi/etc/cd/cd/list.sb", params, function() {
      // 대표명칭 그리드 버튼 show
      //$("#btnAddRepresent").show();
      //$("#btnDelRepresent").show();
      //$("#btnSaveRepresent").show();
    });

    // [본사권한 매장사용 공통코드 매장수정 허용] 버튼 표시 제어
    // 본사 로그인 + 코드 검색란에 현재 년월일시(yyyymmddHH)를 입력하고 조회한 경우에만 표시
    var d = new Date();
    var nowYmdH = d.getFullYear().toString()
        + ("0" + (d.getMonth() + 1)).slice(-2)
        + ("0" + d.getDate()).slice(-2)
        + ("0" + d.getHours()).slice(-2);
    if (gvOrgnFg === "H" && $("#srchNmcodeCd").val() === nowYmdH) {
      $("#btnStoreAllow").show();
    } else {
      $("#btnStoreAllow").hide();
    }

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 대표명칭 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.nmcodeGrpCd = "000";
    params.useYn = "Y";

    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };
  // 대표명칭 그리드 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      var item = $scope.flex.collectionView.itemsAdded[i];
      if(item.nmcodeCd === undefined || item.nmcodeCd.length === 0){
        $scope._popMsg(messages["cd.represent.require.nmcodeCd"]); // 대표명칭의 코드를 입력해주세요
        return false;
      }

      if(item.nmcodeNm === undefined || item.nmcodeNm.length === 0){
        $scope._popMsg(messages["cd.represent.require.nmcodeNm"]); // 대표명칭의 코드명을 입력해주세요
        return false;
      }

      $scope.flex.collectionView.itemsAdded[i].status = "I";
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
      $scope.flex.collectionView.itemsRemoved[i].status = "D";
      params.push($scope.flex.collectionView.itemsRemoved[i]);
    }
    // 컬럼 길이(Byte) 체크 : 오라클 한글 3Byte 기준
    for (var b = 0; b < params.length; b++) {
      var chkItem = params[b];
      if (nvl(chkItem.nmcodeCd + '', '').getByteLengthForOracle() > 4) {
        $scope._popMsg(messages["cd.nmcodeCd"] + messages["cmm.overLength"] + " 4 " + ", 현재 : " + nvl(chkItem.nmcodeCd + '', '').getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
        return false;
      }
      if (nvl(chkItem.nmcodeNm + '', '').getByteLengthForOracle() > 50) {
        $scope._popMsg(messages["cd.nmcodeNm"] + messages["cmm.overLength"] + " 50 " + ", 현재 : " + nvl(chkItem.nmcodeNm + '', '').getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
        return false;
      }
      if (nvl(chkItem.nmcodeItem1 + '', '').getByteLengthForOracle() > 200) {
        $scope._popMsg(messages["cd.nmcodeItem1"] + messages["cmm.overLength"] + " 200 " + ", 현재 : " + nvl(chkItem.nmcodeItem1 + '', '').getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
        return false;
      }
      if (nvl(chkItem.nmcodeItem2 + '', '').getByteLengthForOracle() > 200) {
        $scope._popMsg(messages["cd.nmcodeItem2"] + messages["cmm.overLength"] + " 200 " + ", 현재 : " + nvl(chkItem.nmcodeItem2 + '', '').getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
        return false;
      }
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/adi/etc/cd/cd/save.sb", params);
  }
  // 대표명칭 그리드 행 삭제
  $scope.deleteRow = function() {
    for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
      var item = $scope.flex.collectionView.items[i];
      if(item.gChk){
        if(item.cnt > 0){
          $scope._popMsg("세부명칭이 등록된 대표명칭은 삭제할 수 없습니다. ");
          return false;
        }
        $scope.flex.collectionView.removeAt(i);
      }
    }
  }

  // 엑셀다운로드
  $scope.excelDownload = function(){

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles   : true,
        includeColumns      : function (column) {
          return column.visible;
        }
      }, '대표명칭_' + getCurDateTime() + '.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);

  }
}]);

/**
 * 세부명칭 그리드 생성
 */
app.controller('detailCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('detailCtrl', $scope, $http, false));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("detailCtrl");
    // 그리드 내 콤보박스 설정
    $scope.useYnFgDataMap = useYnFgDataMap;
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "nmcodeCd") {
          var item = s.rows[e.row].dataItem;
          if (item.status !== "I") {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          } else {
            wijmo.removeClass(e.cell, 'wj-custom-readonly');
          }
        }
        var itemChk = s.rows[e.row].dataItem;
        if(itemChk.nmcodeGrpCd === "146"){
          $scope.useYnCiDataMap = useYnCiDataMap;
        }else{
          $scope.useYnCiDataMap = "";
        }
        if (itemChk.nmcodeGrpCd == "093" && (itemChk.nmcodeCd == "1" || itemChk.nmcodeCd == "2"))
        {
            if (col.binding === "gChk" || col.binding === "nmcodeNm" || col.binding === "nmcodeItem1" || col.binding === "nmcodeItem2")
            {
                wijmo.addClass(e.cell, 'wj-custom-readonly');
            }
        }
      }
    });
    // 세부명칭 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (sender, elements) {
      var col = sender.columns[elements.col];
      if (col.binding === "nmcodeCd") {
        var dataItem = s.rows[elements.row].dataItem;
        if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
          elements.cancel = true;
        }
      }
      var dataItemChk = s.rows[elements.row].dataItem;
      if (dataItemChk.nmcodeGrpCd == "093" && (dataItemChk.nmcodeCd == "1" || dataItemChk.nmcodeCd == "2"))
      {
          if (col.binding === "gChk" || col.binding === "nmcodeNm" || col.binding === "nmcodeItem1" || col.binding === "nmcodeItem2") {
              elements.cancel = true;
          }
      }
    });

    // 세부명칭 그리드 붙여넣기 방지
    s.pastingCell.addHandler(function (sender, elements) {
      var col = sender.columns[elements.col];
      if (col.binding === "nmcodeCd") {
        var dataItem = s.rows[elements.row].dataItem;
        if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
          elements.cancel = true;
        }
      }
      var dataItemChk = s.rows[elements.row].dataItem;
      if (dataItemChk.nmcodeGrpCd == "093" && (dataItemChk.nmcodeCd == "1" || dataItemChk.nmcodeCd == "2"))
      {
        if (col.binding === "gChk" || col.binding === "nmcodeNm" || col.binding === "nmcodeItem1" || col.binding === "nmcodeItem2") {
          elements.cancel = true;
        }
      }
    });
  };
  // 세부명칭 그리드 초기화
  $scope.$on("init", function() {
    $scope._gridDataInit();
  });
  // 세부명칭 그리드 버튼 표시 제어
  // 기본 표시조건 외에, 가맹점의 공통(C) 코드는 본사 허용설정(TB_HQ_NMCODE 251)의 I/U/D에 따라 버튼을 개별 표시한다.
  $scope.setDetailBtns = function(params) {
    $scope.allowChkYn = "N";  // 허용설정 검증 대상 여부
    $scope.allowInsYn = "Y";  // 추가(I) 허용여부 (기본 허용)
    $scope.allowUpdYn = "Y";  // 수정(U) 허용여부 (기본 허용)
    $scope.allowDelYn = "Y";  // 삭제(D) 허용여부 (기본 허용)
    if( (gvOrgnFg == "H" && (params.nmcodeItem1 == "C" || params.nmcodeItem1 == "H"    )   )
    ||  (gvOrgnFg == "S" && (params.nmcodeItem1 == "S" )                                   )
    ||  (gvOrgnFg == "S" && (params.nmcodeItem1 == "C" && gvHqOfficeCd == "00000"      )   )
      )
    {
      $("#btnAddDetail").show();
      $("#btnDelDetail").show();
      $("#btnSaveDetail").show();
    }
    else if (gvOrgnFg == "S" && params.nmcodeItem1 == "C")
    {
      // 가맹점의 공통(C) 코드 : 본사 허용설정 조회 후 허용된 버튼만 표시
      $("#btnAddDetail").hide();
      $("#btnDelDetail").hide();
      $("#btnSaveDetail").hide();
      $.postJSON("/adi/etc/cd/cd/getCdStoreAllowItem.sb", {nmcodeNm: params.nmcodeGrpCd}, function (response) {
        // 응답 도착 시점에 다른 그룹으로 이동했으면 무시 (응답 역전 방지)
        if (params.nmcodeGrpCd !== $("#s_nmcodeCd").val()) {
          return false;
        }
        var allow = response.data || {};
        $scope.allowChkYn = "Y";
        $scope.allowInsYn = nvl(allow.iYn, "N");
        $scope.allowUpdYn = nvl(allow.uYn, "N");
        $scope.allowDelYn = nvl(allow.dYn, "N");
        if ($scope.allowInsYn === "Y") { $("#btnAddDetail").show(); }
        if ($scope.allowDelYn === "Y") { $("#btnDelDetail").show(); }
        // 저장버튼은 추가(I) 또는 수정(U) 허용 시 표시 (추가분 저장도 저장버튼으로 수행되므로)
        if ($scope.allowInsYn === "Y" || $scope.allowUpdYn === "Y") { $("#btnSaveDetail").show(); }
      });
    }
    else
    {
      $("#btnAddDetail").hide();
      $("#btnDelDetail").hide();
      $("#btnSaveDetail").hide();
    }
  };

  // 세부명칭 그리드 조회
  $scope.$on("detailCtrl", function(event, data) {
    // 파라미터
    var params = {};
    params.nmcodeGrpCd = data.nmcodeCd;
    params.nmcodeItem1 = data.nmcodeItem1;
    // params.nmcodeItem2 = data.nmcodeItem2;
    // 조회URL, 파라미터, 콜백함수 형태로 조회함수 호출
    $scope._inquirySub("/adi/etc/cd/cd/list.sb", params, function() {
      // 세부명칭 그리드 버튼 show
      $scope.setDetailBtns(params);
    });

    $("#s_nmcodeCd").val(data.nmcodeCd);
    $("#s_nmcodeItem1").val(data.nmcodeItem1);
    $("#s_nmcodeItem2").val(data.nmcodeItem2);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

    $scope.searchDetailCtrl = function(){
        var params = {};
        params.nmcodeGrpCd = $("#s_nmcodeCd").val();
        params.nmcodeItem1 = $("#s_nmcodeItem1").val();
        params.nmcodeItem2 = $("#s_nmcodeItem2").val();
        // 조회URL, 파라미터, 콜백함수 형태로 조회함수 호출
        $scope._inquirySub("/adi/etc/cd/cd/list.sb", params, function() {
          // 세부명칭 그리드 버튼 show
          $scope.setDetailBtns(params);
        });
    };

  // 세부명칭 그리드 행 추가
  $scope.addRow = function() {
    var gridRepresent = agrid.getScope('representCtrl');
    var selectedRow = gridRepresent.flex.selectedRows[0]._data;

    var params = {};
    params.nmcodeGrpCd = selectedRow.nmcodeCd;
    params.useYn = "Y";
    if(selectedRow.nmcodeCd === "146"){
      params.nmcodeItem2 = "Y";
    }
    params.gChk = false;

    $scope._addRow(params, 1);
  };
  // 세부명칭 그리드 저장
  $scope.save = function() {

    // 가맹점 공통(C) 코드 : 본사 허용설정(I/U)에 따른 저장 검증
    if ($scope.allowChkYn === "Y") {
      if ($scope.flex.collectionView.itemsAdded.length > 0 && $scope.allowInsYn !== "Y") {
        $scope._popMsg(messages["cd.storeAllow.noAddAuth"]); // 추가 권한이 없습니다.
        return false;
      }
      if ($scope.flex.collectionView.itemsEdited.length > 0 && $scope.allowUpdYn !== "Y") {
        $scope._popMsg(messages["cd.storeAllow.noModAuth"]); // 수정 권한이 없습니다.
        return false;
      }
    }

    $scope._popConfirm(messages["cmm.choo.save"], function() {
      // 파라미터 설정
      var params = new Array();

      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];
        if (item.nmcodeNm === undefined || item.nmcodeNm.length === 0) {
          $scope._popMsg(messages["cd.detail.require.nmcodeNm"]); // 세부명칭의 코드명을 입력해주세요
          return false;
        }
        if (item.nmcodeGrpCd == "146") {
          if (item.nmcodeItem2 === undefined || item.nmcodeItem2 === '') {
            $scope._popMsg(messages["cd.detail.require.nmcodeItem2"]); // 세부명칭의 코드항목2를 선택해주세요
            return false;
          }
        }
        if (item.useYn === undefined || item.useYn.length === 0) {
          $scope._popMsg(messages["cd.detail.require.useYn"]); // 세부명칭의 사용여부를 선택해주세요
          return false;
        }

        $scope.flex.collectionView.itemsEdited[i].status = "U";
        params.push($scope.flex.collectionView.itemsEdited[i]);
      }

      for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
        var item = $scope.flex.collectionView.itemsAdded[i];
        if (item.nmcodeCd === undefined || item.nmcodeCd.length === 0) {
          $scope._popMsg(messages["cd.detail.require.nmcodeCd"]); // 세부명칭의 코드를 입력해주세요
          return false;
        }

        if (item.nmcodeNm === undefined || item.nmcodeNm.length === 0) {
          $scope._popMsg(messages["cd.detail.require.nmcodeNm"]); // 세부명칭의 코드명을 입력해주세요
          return false;
        }
        if (item.nmcodeGrpCd === "146") {
          if (item.nmcodeItem2 === undefined || item.nmcodeItem2.length === 0) {
            $scope._popMsg(messages["cd.detail.require.nmcodeItem2"]); // 세부명칭의 코드항목2를 선택해주세요
            return false;
          }
        }
        if (item.useYn === undefined || item.useYn.length === 0) {
          $scope._popMsg(messages["cd.detail.require.useYn"]); // 세부명칭의 사용여부를 선택해주세요
          return false;
        }


        if (item.nmcodeGrpCd === "160") {
          if (item.nmcodeCd.length > $("#s_nmcodeItem2").val()) {
            $scope._popMsg(messages["cd.detail.require.nmcodeCdLengthChk"] + ' (' + $("#s_nmcodeItem2").val() + ')'); // 세부명칭의 코드자릿수를 확인하여 주십시오.
            return false;
          }
        } else {
          if (item.nmcodeCd.length != $("#s_nmcodeItem2").val()) {
            $scope._popMsg(messages["cd.detail.require.nmcodeCdLengthChk"] + ' (' + $("#s_nmcodeItem2").val() + ')'); // 세부명칭의 코드자릿수를 확인하여 주십시오.
            return false;
          }
        }

        var check_nmcodeCd_cnt = 0;
        for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
          if ($scope.flex.collectionView.items[j].nmcodeCd == item.nmcodeCd) {
            check_nmcodeCd_cnt++;
          }
        }
        if (check_nmcodeCd_cnt > 1) {
          $scope._popMsg(messages["cd.detail.require.nmcodeCdChk"] + ' (' + item.nmcodeCd + ')'); // 코드중복 확인
          return false;
        }

        $scope.flex.collectionView.itemsAdded[i].status = "I";
        params.push($scope.flex.collectionView.itemsAdded[i]);


      }
      // 컬럼 길이(Byte) 체크 : 오라클 한글 3Byte 기준
      for (var b = 0; b < params.length; b++) {
        var chkItem = params[b];
        if (nvl(chkItem.nmcodeCd + '', '').getByteLengthForOracle() > 4) {
          $scope._popMsg(messages["cd.nmcodeCd"] + messages["cmm.overLength"] + " 4 " + ", 현재 : " + nvl(chkItem.nmcodeCd + '', '').getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
          return false;
        }
        if (nvl(chkItem.nmcodeNm + '', '').getByteLengthForOracle() > 50) {
          $scope._popMsg(messages["cd.nmcodeNm"] + messages["cmm.overLength"] + " 50 " + ", 현재 : " + nvl(chkItem.nmcodeNm + '', '').getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
          return false;
        }
        if (nvl(chkItem.nmcodeItem1 + '', '').getByteLengthForOracle() > 200) {
          $scope._popMsg(messages["cd.nmcodeItem1"] + messages["cmm.overLength"] + " 200 " + ", 현재 : " + nvl(chkItem.nmcodeItem1 + '', '').getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
          return false;
        }
        if (nvl(chkItem.nmcodeItem2 + '', '').getByteLengthForOracle() > 200) {
          $scope._popMsg(messages["cd.nmcodeItem2"] + messages["cmm.overLength"] + " 200 " + ", 현재 : " + nvl(chkItem.nmcodeItem2 + '', '').getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
          return false;
        }
      }

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save("/adi/etc/cd/cd/save.sb", params, function () {
        $scope.allSearch()
      });

    });

    // 재조회
    $scope.allSearch = function () {
      $scope.searchDetailCtrl();
    };

  }
  // 세부명칭 그리드 행 삭제
  $scope.deleteRow = function() {
        // 가맹점 공통(C) 코드 : 본사 허용설정(D)에 따른 삭제 검증
        if ($scope.allowChkYn === "Y" && $scope.allowDelYn !== "Y") {
          $scope._popMsg(messages["cd.storeAllow.noDelAuth"]); // 삭제 권한이 없습니다.
          return false;
        }

        $scope._popConfirm(messages["cd.detail.require.delConfirm"], function() {
          var params = new Array();
          for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk) {
              if (item.nmcodeGrpCd == "093" && (item.nmcodeCd == "1" || item.nmcodeCd == "2")){
                $scope._popMsg(messages["cd.detail.require.chk.093.1"]); // 093 발주단위 기본값 0 낱개 1 박스 는 삭제할 수 없습니다.
                return false;
              }else {
                item.status = 'D';
                params.push(item);
              }
            }
          }
          // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
          $scope._save("/adi/etc/cd/cd/save.sb", params, function () {
            $scope.allSearch()
          });

        });
  }

}]);

/**
 * 본사권한 매장사용 공통코드 매장수정 허용 팝업
 */
app.controller('cdStoreAllowCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('cdStoreAllowCtrl', $scope, $http, false));

  // 선택된 공통코드그룹
  $scope.selGrpCd = "";

  // 대표명칭(공통) 그리드 초기화
  $scope.initGrpGrid = function (s, e) {
    // ReadOnly 배경 + 코드 셀 링크 효과
    s.formatItem.addHandler(function (s2, e2) {
      if (e2.panel === s2.cells) {
        var col = s2.columns[e2.col];
        wijmo.addClass(e2.cell, 'wj-custom-readonly');
        if (col.binding === "nmcodeCd") {
          wijmo.addClass(e2.cell, 'wijLink');
        }
      }
    });
    // 행 클릭 시 해당 그룹의 매장 허용설정 조회
    s.hostElement.addEventListener('mousedown', function (e2) {
      var ht = s.hitTest(e2);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var selectedRow = s.rows[ht.row].dataItem;
        if (selectedRow) {
          $scope.searchStoreAllowList(selectedRow.nmcodeCd);
        }
      }
    });
  };

  // 매장 그리드 초기화
  $scope.initStoreGrid = function (s, e) {
    // 공용 _save가 $scope.flex 기준으로 동작(저장 후 clearChanges/콜백)하므로 매장 그리드를 연결
    $scope.flex = s;
  };

  // 팝업 열기
  $scope.$on("cdStoreAllowOpen", function (event, data) {
    $scope.selGrpCd = "";
    if ($scope.storeFlex) {
      $scope.storeFlex.itemsSource = new wijmo.collections.CollectionView([]);
    }

    // 대표명칭(공통 C) 목록 조회 (전용 쿼리 - 서버에서 C 구분 필터)
    $.postJSON("/adi/etc/cd/cd/getCdStoreAllowGrpList.sb", {}, function (response) {
      var list = response.data.list;
      if (list === undefined || list === null) {
        list = [];
      }
      $scope.grpFlex.itemsSource = new wijmo.collections.CollectionView(list);
    });

    $scope.cdStoreAllowLayer.show(true);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 선택 그룹의 매장 허용설정 조회
  $scope.searchStoreAllowList = function (grpCd) {
    $scope.selGrpCd = grpCd;
    $.postJSON("/adi/etc/cd/cd/getCdStoreAllowList.sb", {nmcodeNm: grpCd}, function (response) {
      var list = response.data.list;
      if (list === undefined || list === null) {
        list = [];
      }
      var rows = [];
      for (var i = 0; i < list.length; i++) {
        rows.push({
          storeCd: list[i].storeCd,
          storeNm: list[i].storeNm,
          orgNmcodeCd: nvl(list[i].nmcodeCd, ""), // 기존 251 설정 코드(없으면 신규)
          orgInsYn: list[i].iYn,                  // 기존 허용여부(변경 비교용)
          orgDelYn: list[i].dYn,
          orgUpdYn: list[i].uYn,
          iChk: list[i].iYn === "Y",
          dChk: list[i].dYn === "Y",
          uChk: list[i].uYn === "Y"
        });
      }
      var cv = new wijmo.collections.CollectionView(rows);
      cv.trackChanges = true;
      $scope.storeFlex.itemsSource = cv;
    });
  };

  // 허용설정 저장 (변경분만 : 신규=I(코드 서버채번), 변경=U, 전부해제=D)
  $scope.saveStoreAllow = function () {
    if ($scope.selGrpCd === "") {
      $scope._popMsg(messages["cd.storeAllow.selectGrp"]); // 대표명칭을 선택해주세요.
      return false;
    }

    // 편집 중인 체크박스 값 커밋 (마지막 클릭 미반영 방지)
    $scope.storeFlex.finishEditing();

    var items = $scope.storeFlex.collectionView.items;
    var params = new Array();
    for (var i = 0; i < items.length; i++) {
      var item = items[i];
      var insYn = item.iChk ? "Y" : "N";
      var updYn = item.uChk ? "Y" : "N";
      var delYn = item.dChk ? "Y" : "N";
      if (insYn === item.orgInsYn && updYn === item.orgUpdYn && delYn === item.orgDelYn) {
        continue; // 변경 없음
      }
      var row = {};
      row.nmcodeNm = $scope.selGrpCd;   // 공통코드그룹코드
      row.nmcodeItem1 = item.storeCd;   // 매장코드
      row.insYn = insYn;                // 허용액션 Y/N (NMCODE_ITEM_2 조립은 서버에서 수행)
      row.updYn = updYn;
      row.delYn = delYn;
      if (item.orgNmcodeCd === "") {
        row.status = "I";               // 신규 (코드는 서버에서 MAX+1 채번)
      } else {
        row.nmcodeCd = item.orgNmcodeCd;
        // 전부 해제 시 설정 삭제
        row.status = (insYn === "N" && updYn === "N" && delYn === "N") ? "D" : "U";
      }
      params.push(row);
    }

    if (params.length === 0) {
      $scope._popMsg(messages["cd.storeAllow.noChange"]); // 변경된 내용이 없습니다.
      return false;
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/adi/etc/cd/cd/saveCdStoreAllow.sb", params, function () {
      $scope.searchStoreAllowList($scope.selGrpCd); // 재조회
    });
  };

  // 팝업 닫기
  $scope.close = function () {
    $scope.cdStoreAllowLayer.hide();
  };

}]);
