/****************************************************************
 *
 * 파일명 : verRecv.js
 * 설  명 : 포스버전수신현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.01.07     김지은      1.0           Angular방식으로 변경
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 조회조건 DropBoxDataMap
var recvYn = [
  {"name":"전체","value":""},
  {"name":"수신완료","value":"2"},
  {"name":"수신오류","value":"3"},
  {"name":"미수신","value":"1"}
];

function changeTab(val){

  if(val === 'S'){
    location.href = '/pos/confg/verRecv/storeRecv/list.sb';
  } else if(val === 'V') {
    location.href = '/pos/confg/verRecv/verStore/list.sb';
  }
}

function searchPosVerList(){
  var scope = agrid.getScope('verRecvCtrl');
  scope.getVersionList();
}

/**********************************************************************
 *  버전목록 그리드
 **********************************************************************/
app.controller('verRecvCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('verRecvCtrl', $scope, $http, true));

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("verRecvYnCombo", recvYn);

  // 콤보박스 데이터
  $scope._setComboData("progFgCombo", progFg);

  // 선택 버전
  $scope.selectVersion;
  $scope.setSelectVersion = function(ver){
    $scope.selectVersion = ver;
  };
  $scope.getSelectVersion = function(){
    return $scope.selectVersion;
  };

  $scope.progFgDataMap = new wijmo.grid.DataMap(progFg, 'value', 'name');

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "verSerNo" || col.binding === "verSerNoStore") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 버전 선택
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        if ( col.binding === "verSerNo" || col.binding === "verSerNoStore") {
          var selectedData = s.rows[ht.row].dataItem;
          selectedData.gubun = col.binding;
          $scope.setSelectVersion(selectedData);
          // $scope.versionInfoDetailLayer.show(true);

          var storeScope = agrid.getScope('verRecvStoreCtrl');
          storeScope._broadcast('verRecvStoreCtrl', $scope.getSelectVersion());

          event.preventDefault();
        }
      }
    });
  };

  // 조회 버튼 클릭
  $scope.$on("verRecvCtrl", function(event, data) {
    $scope.getVersionList();
    event.preventDefault();
  });

  // 버전 목록 조회
  $scope.getVersionList = function(){
    var params = {};
    params.listScale = 50;
    params.curr = $scope._getPagingInfo('curr');
    params.verSerNo = $("#verSerNo").val();
    params.verSerNm = $("#verSerNm").val();
    params.hqOfficeCd = hqOfficeCd;
    params.progFg = $scope.progFgCombo.selectedValue;

    $scope._inquiryMain("/pos/confg/verRecv/verRecv/list.sb", params, function() {
      $scope.$apply(function() {
        var storeScope = agrid.getScope('verRecvStoreCtrl');
        storeScope._gridDataInit();
      });
    });
  };


}]);


/**********************************************************************
 *  버전수신매장 그리드
 **********************************************************************/
app.controller('verRecvStoreCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('verRecvStoreCtrl', $scope, $http, true));

  // 조회조건 콤보박스 데이터 Set
  // $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // 선택 버전
  $scope.selectVersion;
  $scope.setSelectVersion = function(ver){
    $scope.selectVersion = ver;
  };
  $scope.getSelectVersion = function(){
    return $scope.selectVersion;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.verRecvFgDatMap = new wijmo.grid.DataMap(verRecvFg, 'value', 'name');
  };

  // 조회 버튼 클릭
  $scope.$on("verRecvStoreCtrl", function(event, data) {

    if( !isEmptyObject(data) ) {
      $scope.setSelectVersion(data);
    }

    $scope.getVersionStoreList();
    event.preventDefault();
  });

  // 버전수신정보 목록 조회
  $scope.getVersionStoreList = function(){
    var params = {};
    var scope = agrid.getScope('verRecvCtrl');

    params.listScale = 30;
    params.curr = $scope._getPagingInfo('curr');
    params.verSerNo = $scope.getSelectVersion().verSerNo;
    params.verRecvYn = scope.verRecvYn;
    params.hqOfficeCd = hqOfficeCd;
    params.progFg =  $scope.getSelectVersion().progFg;

    var url;
    if ($scope.getSelectVersion().gubun === 'verSerNo') {
      url = "/pos/confg/verRecv/verRecv/storeList.sb";
    } else {
      url = "/pos/confg/verRecv/verRecv/storeList2.sb";
    }
    // console.log('dtl params', params);
      $scope._inquiryMain(url, params, function() {
        // 선택한 테이블에 따른 리스트 항목 visible
        var grid = wijmo.Control.getControl("#wjVerRecvStoreGridList");
        var columns = grid.columns;
        if ($scope.getSelectVersion().gubun === 'verSerNo') {
          columns[3].visible = true;
        } else {
          columns[3].visible = false;
        }
      });
  };

  // <-- 엑셀다운로드 호출 -->
  $scope.excelDownload = function(){
    var params       = {};
    var scope = agrid.getScope('verRecvCtrl');

    params.curr = $scope._getPagingInfo('curr');
    params.verSerNo = $scope.getSelectVersion().verSerNo;
    params.verRecvYn = scope.verRecvYn;
    params.gubun =$scope.getSelectVersion().gubun;

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope._broadcast('verRecvStoreExcelCtrl', params);
  };
  // <-- //엑셀다운로드 호출 -->

}]);

/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('verRecvStoreExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('verRecvStoreExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // <-- 검색 호출 -->
  $scope.$on("verRecvStoreExcelCtrl", function(event, data) {
    $scope.getVersionStoreExcelList(data);
    event.preventDefault();
  });

  $scope.getVersionStoreExcelList = function(data){
    var params = {};
    params.curr = data.curr;
    params.verSerNo = data.verSerNo;
    params.verRecvYn = data.verRecvYn;
    params.hqOfficeCd = hqOfficeCd;

    var url;
    if(data.gubun === 'verSerNo'){
      url = "/pos/confg/verRecv/verRecv/storeExcelList.sb";
    } else {
      url = "/pos/confg/verRecv/verRecv/storeExcelList2.sb";
    }

    $scope._inquiryMain(url, params, function() {

      if ($scope.excelFlex.rows.length <= 0) {
        $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
        return false;
      }

      // 선택한 테이블에 따른 리스트 항목 visible
      var grid = wijmo.Control.getControl("#wjVerRecvStoreExcelGridList");
      var columns = grid.columns;
      if (data.gubun === 'verSerNo') {
        columns[3].visible = true;
      } else {
        columns[3].visible = false;
      }

      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
      $timeout(function()	{
        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.excelFlex,
            {
              includeColumnHeaders: 	true,
              includeCellStyles	: 	false,
              includeColumns      :	function (column) {
                return column.visible;
              }
            },
            '버전별매장수신정보_'+getToday()+'.xlsx',
            function () {
              $timeout(function () {
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
              }, 10);
            }
        );
      }, 10);

    });
  };
  // <-- //검색 호출 -->

}]);
