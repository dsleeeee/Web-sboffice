// 시 VALUE
var Hh = [24];
for(i =0 ; i < 24; i++){
  var timeVal = i.toString();
  if(i>=0 && i<=9){
    timeVal = "0" + timeVal;
  }
  Hh[i] = {"name":timeVal,"value":timeVal}
}

// 분, 초 VALUE
var MmSs = [60];
for(i =0 ; i < 60; i++){
  var timeVal = i.toString();
  if(i>=0 && i<=9){
    timeVal = "0" + timeVal;
  }
  MmSs[i] = {"name":timeVal,"value":timeVal}
}
/** 요일별 그리드 controller */
app.controller('daysCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('daysCtrl', $scope, $http, true));

  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("daysCtrl");

    // 그리드 DataMap 설정
    $scope.sysStatFgMap    = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    $scope.timeHourMap    = new wijmo.grid.DataMap(Hh, 'value', 'name');
    $scope.timeMsMap    = new wijmo.grid.DataMap(MmSs, 'value', 'name');
    $scope.orderCloseYnMap = new wijmo.grid.DataMap([
      {id: "Y", name: messages["outstockReqDate.orderCloseYnY"]},
      {id: "N", name: messages["outstockReqDate.orderCloseYnN"]},
    ], 'id', 'name');


    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        // if (col.binding === "storeCd") {
        //   wijmo.addClass(e.cell, 'wijLink');
        //   wijmo.addClass(e.cell, 'wj-custom-readonly');
        // }
      }

    });

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    s.columnHeaders.rows[0].dataItem = {
      storeCd       : messages["outstockReqDate.storeCd"],
      storeNm       : messages["outstockReqDate.storeNm"],
      ownerNm       : messages["cmm.owner.nm"],
      sysStatFg     : messages["outstockReqDate.sysStatFg"],
      orderCloseYn  : messages["outstockReqDate.orderCloseYn"],
      sun           : messages["outstockReqDate.outstockReqDate"],
      mon           : messages["outstockReqDate.outstockReqDate"],
      tue           : messages["outstockReqDate.outstockReqDate"],
      wed           : messages["outstockReqDate.outstockReqDate"],
      thu           : messages["outstockReqDate.outstockReqDate"],
      fri           : messages["outstockReqDate.outstockReqDate"],
      sat           : messages["outstockReqDate.outstockReqDate"],
      // startHourSun  : messages["outstockReqDate.sun"],
      // startMsSun    : messages["outstockReqDate.sun"],
      // endHourSun    : messages["outstockReqDate.sun"],
      // endMsSun      : messages["outstockReqDate.sun"],
      // startHourMon  : messages["outstockReqDate.mon"],
      // startMsMon    : messages["outstockReqDate.mon"],
      // endHourMon    : messages["outstockReqDate.mon"],
      // endMsMon      : messages["outstockReqDate.mon"],
      // startHourTue  : messages["outstockReqDate.tue"],
      // startMsTue    : messages["outstockReqDate.tue"],
      // endHourTue    : messages["outstockReqDate.tue"],
      // endMsTue      : messages["outstockReqDate.tue"],
      // startHourWed  : messages["outstockReqDate.wed"],
      // startMsWed    : messages["outstockReqDate.wed"],
      // endHourWed    : messages["outstockReqDate.wed"],
      // endMsWed      : messages["outstockReqDate.wed"],
      // startHourThu  : messages["outstockReqDate.thu"],
      // startMsThu    : messages["outstockReqDate.thu"],
      // endHourThu    : messages["outstockReqDate.thu"],
      // endMsThu      : messages["outstockReqDate.thu"],
      // startHourFri  : messages["outstockReqDate.fri"],
      // startMsFri    : messages["outstockReqDate.fri"],
      // endHourFri    : messages["outstockReqDate.fri"],
      // endMsFri      : messages["outstockReqDate.fri"],
      // startHourSat  : messages["outstockReqDate.sat"],
      // startMsSat    : messages["outstockReqDate.sat"],
      // endHourSat    : messages["outstockReqDate.sat"],
      // endMsSat      : messages["outstockReqDate.sat"],
      daysRemark    : messages["outstockReqDate.remark"]
    };

  };


  // 체크박스가 있는 헤더머지 때문에 itemFormatter 를 재정의함.
  $scope.itemFormatter = function (panel, r, c, cell) {
    if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
      //align in center horizontally and vertically
      panel.rows[r].allowMerging    = true;
      panel.columns[c].allowMerging = true;

      wijmo.setCss(cell, {
        display    : 'table',
        tableLayout: 'fixed'
      });
      cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
      wijmo.setCss(cell.children[0], {
        display      : 'table-cell',
        verticalAlign: 'middle',
        textAlign    : 'center'
      });



      if ((panel.grid.columnHeaders.rows.length - 1) === r) {
        // 헤더의 전체선택 클릭 로직
        var flex   = panel.grid;
        var column = flex.columns[c];
        // check that this is a boolean column
        if (column.binding === 'gChk' || column.format === 'checkBox' || column.format === 'checkBoxText') {
          // prevent sorting on click
          column.allowSorting = false;
          // count true values to initialize checkbox
          var cnt             = 0;
          for (var i = 0; i < flex.rows.length; i++) {
            if (flex.getCellData(i, c) === true) {
              cnt++;
            }
          }
          // create and initialize checkbox
          if (column.format === 'checkBoxText') {
            cell.innerHTML = '<input id=\"' + column.binding + '\" type=\"checkbox\" class=\"wj-cell-check\" />'
              + '<label for=\"' + column.binding + '\" class=\"wj-header-label\">' + cell.innerHTML + '</label>';
          } else {
            cell.innerHTML = '<input type=\"checkbox\" class=\"wj-cell-check\" />';
          }
          var cb           = cell.firstChild;
          cb.checked       = cnt > 0;
          cb.indeterminate = cnt > 0 && cnt < flex.rows.length;
          // apply checkbox value to cells
          cb.addEventListener('click', function (e) {
            flex.beginUpdate();
            for (var i = 0; i < flex.rows.length; i++) {
              var cell = flex.cells.getCellElement(i, c);

              // 활성화 및 readOnly 아닌 경우에만 체크되도록
              // if (!cell.children[0].disabled) {
              if (!cell.children[0].disabled) {
                flex.setCellData(i, c, cb.checked);
              }
            }
            flex.endUpdate();
          });
        }
      }
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
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("daysCtrl", function (event, data) {
    $scope.searchDaysList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.searchDaysList = function () {
    // 파라미터
    var params     = {};
    params.storeCd = $("#daysSelectStoreCd").val();
    params.listScale = $scope.conListScale.text; //-페이지 스케일 갯수
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/order/outstockReqDate/days/list.sb", params);
  };

  // 요청일 저장
  $scope.saveDays = function () {
    var params = [];
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";

      // 시작시간
      $scope.flex.collectionView.itemsEdited[i].orderStartTimeSun = $scope.flex.collectionView.itemsEdited[i].startHourSun + $scope.flex.collectionView.itemsEdited[i].startMsSun;
      $scope.flex.collectionView.itemsEdited[i].orderStartTimeMon = $scope.flex.collectionView.itemsEdited[i].startHourMon + $scope.flex.collectionView.itemsEdited[i].startMsMon;
      $scope.flex.collectionView.itemsEdited[i].orderStartTimeTue = $scope.flex.collectionView.itemsEdited[i].startHourTue + $scope.flex.collectionView.itemsEdited[i].startMsTue;
      $scope.flex.collectionView.itemsEdited[i].orderStartTimeWed = $scope.flex.collectionView.itemsEdited[i].startHourWed + $scope.flex.collectionView.itemsEdited[i].startMsWed;
      $scope.flex.collectionView.itemsEdited[i].orderStartTimeThu = $scope.flex.collectionView.itemsEdited[i].startHourThu + $scope.flex.collectionView.itemsEdited[i].startMsThu;
      $scope.flex.collectionView.itemsEdited[i].orderStartTimeFri = $scope.flex.collectionView.itemsEdited[i].startHourFri + $scope.flex.collectionView.itemsEdited[i].startMsFri;
      $scope.flex.collectionView.itemsEdited[i].orderStartTimeSat = $scope.flex.collectionView.itemsEdited[i].startHourSat + $scope.flex.collectionView.itemsEdited[i].startMsSat;

      // 마감시간
      $scope.flex.collectionView.itemsEdited[i].orderEndTimeSun   = $scope.flex.collectionView.itemsEdited[i].endHourSun + $scope.flex.collectionView.itemsEdited[i].endMsSun;
      $scope.flex.collectionView.itemsEdited[i].orderEndTimeMon   = $scope.flex.collectionView.itemsEdited[i].endHourMon + $scope.flex.collectionView.itemsEdited[i].endMsMon;
      $scope.flex.collectionView.itemsEdited[i].orderEndTimeTue   = $scope.flex.collectionView.itemsEdited[i].endHourTue + $scope.flex.collectionView.itemsEdited[i].endMsTue;
      $scope.flex.collectionView.itemsEdited[i].orderEndTimeWed   = $scope.flex.collectionView.itemsEdited[i].endHourWed + $scope.flex.collectionView.itemsEdited[i].endMsWed;
      $scope.flex.collectionView.itemsEdited[i].orderEndTimeThu   = $scope.flex.collectionView.itemsEdited[i].endHourThu + $scope.flex.collectionView.itemsEdited[i].endMsThu;
      $scope.flex.collectionView.itemsEdited[i].orderEndTimeFri   = $scope.flex.collectionView.itemsEdited[i].endHourFri + $scope.flex.collectionView.itemsEdited[i].endMsFri;
      $scope.flex.collectionView.itemsEdited[i].orderEndTimeSat   = $scope.flex.collectionView.itemsEdited[i].endHourSat + $scope.flex.collectionView.itemsEdited[i].endMsSat;

      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    $scope._save("/iostock/order/outstockReqDate/days/save.sb", params, function () {
      $scope.searchDaysList()
    });
  };

}]);
