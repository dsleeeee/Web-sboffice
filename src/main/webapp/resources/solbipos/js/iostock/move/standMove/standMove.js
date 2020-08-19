/**
 * get application
 */
var app = agrid.getApp();

/** 매장이동관리 그리드 controller */
app.controller('standMoveCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('standMoveCtrl', $scope, $http, true));

  var srchStartDate = wcombo.genDateVal("#srchStartDate", getToday());
  var srchEndDate   = wcombo.genDateVal("#srchEndDate", getToday());
  $scope.storeCd = gvStoreCd;
  
  //이동구분
  $scope.ioFgMap = new wijmo.grid.DataMap([
    {id: "-1", name: messages["standMove.standMove"]},
    {id: "1", name: messages["standMove.storageMove"]}
  ], 'id', 'name');
  //진행구분
  $scope.procFgMap = new wijmo.grid.DataMap([
    {id: "0", name: messages["standMove.procFg0"]},
    {id: "1", name: messages["standMove.procFg1"]}
  ], 'id', 'name');

  //이동구분
  $scope._setComboData("srchIoFg", [
    {"name": messages["cmm.all"], "value": ""},
    {"name": messages["standMove.standMove"],  "value": "-1"},
    {"name": messages["standMove.storageMove"], "value": "1"}
  ]);
  //진행구분
  $scope._setComboData("srchProcFg", [
    {"name": messages["cmm.all"], "value": ""},
    {"name": messages["standMove.procFg0"], "value": "N"},
    {"name": messages["standMove.procFg1"], "value": "Y"}
  ]);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("standMoveCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "slipNo") { // 전표번호
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        }
        else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
        }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "slipNo") { // 전표번호 클릭
          var params    = {};
          params.slipFg 	= selectedRow.slipFg;
          params.slipNo 	= selectedRow.slipNo;
          params.moveDate 	= selectedRow.moveDate;
          params.confmYn	= selectedRow.confmYn;
          $scope._broadcast('standMoveDtlCtrl', params);
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("standMoveCtrl", function (event, data) {
    $scope.searchStandMoveList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 매장이동관리 리스트 조회
  $scope.searchStandMoveList = function () {
    // 파라미터
    var params        = {};
    params.startDate  = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
    params.endDate    = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');
    params.storeCd    = $scope.storeCd;
    params.procFg	  = $scope.procFg;  //진행
    params.ioFg	  	  = $scope.ioFg;    //이동구분
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params.sid 	 = document.getElementsByName('sessionId')[0].value;
    }
    
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/move/standMove/standMove/list.sb", params);
  };

  // 신규등록
  $scope.newRegist = function () {
    var params    = {};
    $scope._broadcast("standMoveRegistCtrl", params);
  };

}]);
