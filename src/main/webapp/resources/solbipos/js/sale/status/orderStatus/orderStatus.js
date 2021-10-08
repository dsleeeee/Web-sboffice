/****************************************************************
 *
 * 파일명 : orderStatus.js
 * 설  명 : 주문현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.10.01     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
// 배달여부
var dlvrYnData = [
    {"name": "배달", "value": "Y"},
    {"name": "배달외", "value": "N"}
];
// 회원여부
var membrYnData = [
    {"name": "회원", "value": "Y"},
    {"name": "비회원", "value": "N"}
];
// 예약여부
var resveYnData = [
    {"name": "예약", "value": "Y"},
    {"name": "즉시", "value": "N"}
];
// 환급여부
var refundYnData = [
    {"name": "환급", "value": "Y"},
    {"name": "미환급", "value": "N"}
];
// 전송여부
var sendYnData = [
    {"name": "전송", "value": "Y"},
    {"name": "미전송", "value": "N"}
];
// 주문구분
var orderFgData = [
    {"name": "주문", "value": "1"},
    {"name": "취소", "value": "2"},
    {"name": "결제", "value": "3"}
];
// 배달주문구분
var dlvrOrderFgData = [
    {"name": "일반", "value": "1"},
    {"name": "배달", "value": "2"},
    {"name": "포장", "value": "3"}
];
var app = agrid.getApp();

/**
 * 주문현황 그리드 생성
 */
app.controller('orderStatusCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('orderStatusCtrl', $scope, $http, false));

  //페이지 스케일 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // 등록일자 셋팅
  $scope.startDate = wcombo.genDateVal("#startDate", gvStartDate);
  $scope.endDate   = wcombo.genDateVal("#endDate", gvEndDate);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
      // 그리드 DataMap 설정
      $scope.dlvrYnDataMap = new wijmo.grid.DataMap(dlvrYnData, 'value', 'name');           // 배달여부
      $scope.membrYnDataMap = new wijmo.grid.DataMap(membrYnData, 'value', 'name');       // 회원여부
      $scope.resveYnDataMap = new wijmo.grid.DataMap(resveYnData, 'value', 'name');         // 예약여부
      $scope.refundYnDataMap = new wijmo.grid.DataMap(refundYnData, 'value', 'name');       // 환급여부
      $scope.sendYnDataMap = new wijmo.grid.DataMap(sendYnData, 'value', 'name');           // 전송여부
      $scope.orderFgDataMap = new wijmo.grid.DataMap(orderFgData, 'value', 'name');         // 주문구분
      $scope.dlvrOrderFgDataMap = new wijmo.grid.DataMap(dlvrOrderFgData, 'value', 'name'); // 배달주문구분

      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
          if (e.panel == s.cells) {
              var col = s.columns[e.col];

              if (col.format === "date") {
                  e.cell.innerHTML = getFormatDate(e.cell.innerText);
              } else if (col.format === "dateTime") {
                  e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
              } else if (col.format === "time") {
                  e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
              }

              var col = s.columns[e.col];
              if (col.binding === "orderNo") {
                  wijmo.addClass(e.cell, 'wijLink');
              }
          }
      });

      // 그리드 선택 이벤트
      s.hostElement.addEventListener('mousedown', function(e) {
        var ht = s.hitTest(e);
        if( ht.cellType === wijmo.grid.CellType.Cell) {
            var selectedRow = s.rows[ht.row].dataItem;
            var col = ht.panel.columns[ht.col];
            if( col.binding === "orderNo") {
                $("#orderStatusDtlLayer").show();
                var params = {};
                params.saleDate = selectedRow.saleDate.replaceAll("-","");
                params.orderNo = selectedRow.orderNo;
                $scope._broadcast('orderStatusDtlCtrl', params);
            }
        }
    });
  };

  // <-- 검색 호출 -->
  // 그리드 조회
  $scope.$on("orderStatusCtrl", function(event, data) {
    $scope.searchOrderStatusList();
    event.preventDefault();
  });

  // 그리드 조회
  $scope.searchOrderStatusList = function(){
    var params = {};
    params.startDate = wijmo.Globalize.format($scope.startDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format($scope.endDate.value, 'yyyyMMdd');
    params.listScale = $scope.listScale;;

    $scope._inquiryMain('/sale/orderStatus/orderStatus/orderStatus/getOrderStatusList.sb', params);
  };
  // <-- //검색 호출 -->

}]);
