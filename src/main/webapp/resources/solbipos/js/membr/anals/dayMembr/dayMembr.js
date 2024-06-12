/****************************************************************
 *
 * 파일명 : dayMembr.js
 * 설  명 : 일자별회원 구매내역 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.08.13     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  일자별회원 구매내역 그리드 생성
 */
app.controller('dayMembrCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dayMembrCtrl', $scope, $http, false));

  // 검색조건에 조회기간
  var startDate = wcombo.genDateVal("#startDate", gvStartDate);
  var endDate = wcombo.genDateVal("#endDate", gvEndDate);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.saleFgDataMap = new wijmo.grid.DataMap(saleFgData, 'value', 'name'); //판매구분

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
        if (col.binding === "realSaleAmt" || col.binding === "membrNm") {
          wijmo.addClass(e.cell, 'wijLink');
        }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        }  else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
        }
      }
    });

    // 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];

        // 매출액 클릭시 상세정보 조회
        if (col.binding === "realSaleAmt") {
          var params = s.rows[ht.row].dataItem;
          params.gubun = "dayMembr";
          $scope.setSelectedStore(params);
          $scope.dayMembrPurchsViewLayer.show(true);
          event.preventDefault();
        }

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
  $scope.$on("dayMembrCtrl", function (event, data) {
    $scope.searchDayMembr();
    event.preventDefault();
  });

  // 일자별회원 구매내역 그리드 조회
  $scope.searchDayMembr = function () {
    if (!$scope.valueCheck()) {
      return false;
    }

    var params = {};
    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
    params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
    params.payCol    = payCol;

    $scope._inquiryMain("/membr/anals/dayMembr/dayMembr/getDayMembrList.sb", params, function () {}, false);
  };

  // 체크
  $scope.valueCheck = function () {
    // 최대 3달까지 선택가능합니다.
    var msg = messages['dayMembr.dateError'];
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

    // 매출액 상세정보 팝업 핸들러 추가
    $scope.dayMembrPurchsViewLayer.shown.addHandler(function (s) {
      setTimeout(function () {
        $scope._broadcast('dayMembrPurchsCtrl', $scope.getSelectedStore());
      }, 50)
    });

    // 회원명 상세정보 팝업 핸들러 추가
    $scope.dayMembrDetailViewLayer.shown.addHandler(function (s) {
      setTimeout(function () {
        $scope._broadcast('dayMembrDetailCtrl', $scope.getSelectedStore());
      }, 50)
    });
  });

  // 엑셀 다운로드
  $scope.excelDownload = function () {

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles: true,
        includeColumns: function (column) {
          return column.visible;
        }
      }, '일자별회원구매내역_' + getToday() + '.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);