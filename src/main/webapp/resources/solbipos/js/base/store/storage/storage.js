/**
 * get application
 */
var app = agrid.getApp();

var useYnAllComboData = [
	  {"name": "전체", "value": ""},
	  {"name": "사용", "value": "Y"},
	  {"name": "미사용", "value": "N"}
	];

/** 창고관리 그리드 controller */
app.controller('storageListCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storageListCtrl', $scope, $http, true));

  // 콤보박스 데이터 Set
  $scope._setComboData('listScaleBox', gvListScaleBoxData);
  
  // 사용여부를 쓰는 콤보박스의 데이터 (조회용)
  $scope._setComboData('useYnAllComboData', useYnAllComboData);
  
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
	  
	// picker 사용시 호출 : 미사용시 호출안함
	$scope._makePickColumns("storageListCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "storageCd") { // 창고코드
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "storageCd") { // 창고코드
          var params       = {};
          params.storageCd = selectedRow.storageCd;
          params.storageNm = selectedRow.storageNm;
          params.useYn     = selectedRow.useYn;

          $scope._broadcast('storageModLayerCtrl', params);
        }
      }
    });
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("storageListCtrl", function (event, data) {
    $scope.searchStorageList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

//다른 컨트롤러의 broadcast 받기
  $scope.$on("storageListCtrlSrch", function (event, data) {
    $scope.searchStorageList(false);
    
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  
  //창고 정보 수정 후 리프레시
  
  // 창고 리스트 조회
  $scope.searchStorageList = function (isPageChk) {
    
    // 파라미터
    var params       = {};
    params.useYn     = $scope.useYn;
    params.storageCd = $scope.storageCd;
    params.storageNm = $scope.storageNm;
    params.listScale = $scope.listScale; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;
    
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/base/store/storage/storage/list.sb", params);
    
  };

  // 신규창고등록 팝업
  $scope.regStoragePopup = function () {
	  $scope._broadcast('storageRegLayerCtrl');
  };

  
  // 엑셀 다운로드
  $scope.excelDownloadStorage = function () {
    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles   : true,
        includeColumns      : function (column) {
          return column.visible;
        }
      }, '창고관리_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };
  
}]);
