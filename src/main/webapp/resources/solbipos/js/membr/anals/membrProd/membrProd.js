/****************************************************************
 *
 * 파일명 : membrProd.js
 * 설  명 : 회원 상품 구매내역 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.09.11     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  회원 상품 구매내역 그리드 생성
 */
app.controller('membrProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('membrProdCtrl', $scope, $http, true));
  /*
  // 접속사용자의 권한(H : 본사, S: 매장)
  $scope.orgnFg = gvOrgnFg;

  // 매장권한으로 로그인 한 경우, 본인매장만 내역 조회가능.
  if($scope.orgnFg === 'S') {
     $scope.storeCds = gvStoreCd;
  }
 */
  // 검색조건에 조회기간
  var startDate = wcombo.genDateVal("#startDate", gvStartDate);
  var endDate = wcombo.genDateVal("#endDate", gvEndDate);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 합계
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    //그리드 링크설정
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "membrNm") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];

        // 회원명 클릭시 상세정보 조회
        if (col.binding === "membrNm") {
          $scope.setSelectedStore(s.rows[ht.row].dataItem);
          $scope.dayMembrDetailViewLayer.show(true);
          event.preventDefault();
        }
      }
    });
  };

  // <-- 검색 호출 -->
  $scope.$on("membrProdCtrl", function (event, data) {
    $scope.searchMembrProd();
    event.preventDefault();
  });

  // 회원 상품 구매내역 그리드 조회
  $scope.searchMembrProd = function () {
    if (!$scope.valueCheck()) {
      return false;
    }

    var params = {};
    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
    params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간

    $scope._inquiryMain("/membr/anals/membrProd/membrProd/getMembrProdList.sb", params, function () {}, false);
  };

  // 체크
  $scope.valueCheck = function () {
    // 최대 3달까지 선택가능합니다.
    var msg = messages['membrProd.dateError'];
    var date1 = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
    var date2 = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));

    var diffDay = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24);
    if (diffDay > 93) {
      $scope._popMsg(msg);
      return false;
    }

    return true;
  };
  // <-- //검색 호출 -->

  // 선택 매장
  $scope.selectedStore;
  $scope.setSelectedStore = function (store) {
    $scope.selectedStore = store;
  };
  $scope.getSelectedStore = function () {
    return $scope.selectedStore;
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {

    // 회원명 상세정보 팝업 핸들러 추가
    $scope.dayMembrDetailViewLayer.shown.addHandler(function (s) {
      setTimeout(function () {
        $scope._broadcast('dayMembrDetailCtrl', $scope.getSelectedStore());
      }, 50)
    });
  });

  // 엑셀 다운로드
  $scope.excelDownload = function () {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();

    if (dd < 10) {
      dd= '0' + dd;
    }

    if (mm < 10) {
      mm = '0' + mm;
    }

    today = String(yyyy) + String(mm) + dd;

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {

      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles: true,
        formatItem: saveFormatItem,
        includeColumns: function (column) {
          return column.visible;
        }
      }, '회원관리_회원분석_회원상품구매내역_' + today + '.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

  function saveFormatItem(args) {
    let p = args.panel, row = args.row, col = args.col, xlsxCell = args.xlsxCell, cell, color;
    if (p.cellType === wijmo.grid.CellType.Cell) {
      if (p.columns[col].binding === 'prodCd') {
        if (xlsxCell.value) {
          xlsxCell.style.format = "@"
        }
      }
    }
  };



}]);