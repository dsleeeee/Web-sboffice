/****************************************************************
 *
 * 파일명 : incln.js
 * 설  명 : 회원 구매성향 분석 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.10.12    Daniel      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  회원 구매성향 분석 그리드 생성
 */
app.controller('inclnCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('inclnCtrl', $scope, $http, true));
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

    // <-- 그리드 헤더2줄 -->
    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem = {};
    dataItem.group = messages["incln.group"];
    dataItem.allGroup = messages["incln.allGroup"];
    dataItem.gendrF = messages["incln.gendrFg"];
    dataItem.gendrG = messages["incln.gendrFg"];
    dataItem.ageGroupOne = messages["incln.ageGroup"];
    dataItem.ageGroupTwo = messages["incln.ageGroup"];
    dataItem.ageGroupThree = messages["incln.ageGroup"];
    dataItem.ageGroupFour = messages["incln.ageGroup"];
    dataItem.ageGroupFive = messages["incln.ageGroup"];
    dataItem.ageGroupSix = messages["incln.ageGroup"];

    s.columnHeaders.rows[0].dataItem = dataItem;

    s.itemFormatter = function (panel, r, c, cell) {
      if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
        //align in center horizontally and vertically
        panel.rows[r].allowMerging = true;
        panel.columns[c].allowMerging = true;
        wijmo.setCss(cell, {
          display: 'table',
          tableLayout: 'fixed'
        })
        cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
        wijmo.setCss(cell.children[0], {
          display: 'table-cell',
          verticalAlign: 'middle',
          textAlign: 'center'
        });
      }
      // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
      else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
        // GroupRow 인 경우에는 표시하지 않는다.
        if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
          cell.textContent = '';
        } else {
          if (!isEmpty(panel._rows[r]._data.rnum)) {
            cell.textContent = (panel._rows[r]._data.rnum).toString();
          } else {
            cell.textContent = (r + 1).toString();
          }
        }
      }
      // readOnly 배경색 표시
      else if (panel.cellType === wijmo.grid.CellType.Cell) {
        var col = panel.columns[c];
        if (col.isReadOnly) {
          wijmo.addClass(cell, 'wj-custom-readonly');
        }
      }
    }
    // <-- //그리드 헤더2줄 -->
  };a

  // <-- 검색 호출 -->
  $scope.$on("membrPossesnCtrl", function (event, data) {
    $scope.searchMembrPossesn();
    event.preventDefault();
  });

  // 회원매출점유 그리드 조회
  $scope.searchMembrPossesn = function () {

    var params = {};
    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
    params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간

    $scope._inquiryMain("/membr/anals/incln/incln/getInclnList.sb", params, function () {
    }, false);
  };
  // <-- //검색 호출 -->
}]);