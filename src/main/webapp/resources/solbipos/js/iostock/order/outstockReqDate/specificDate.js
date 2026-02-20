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
/** 특정일 그리드 controller */
app.controller('specificCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('specificCtrl', $scope, $http, true));

  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("specificCtrl");

    // 그리드 DataMap 설정
    $scope.sysStatFgMap     = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    $scope.timeHourMap    = new wijmo.grid.DataMap(Hh, 'value', 'name');
    $scope.timeMsMap    = new wijmo.grid.DataMap(MmSs, 'value', 'name');
    $scope.outstockReqYnMap = new wijmo.grid.DataMap([
      {id: "Y", name: messages["outstockReqDate.outstockReqYnY"]},
      {id: "N", name: messages["outstockReqDate.outstockReqYnN"]},
    ], 'id', 'name');

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        // if (col.binding === "storeCd") {
        //   wijmo.addClass(e.cell, 'wijLink');
        //   wijmo.addClass(e.cell, 'wj-custom-readonly');
        // }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        }
        else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
        }
      }
    });

    // 헤더머지
    s.allowMerging  = 2;
    s.itemFormatter = function (panel, r, c, cell) {
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
        if (col.isReadOnly || panel.grid.isReadOnly) {
          wijmo.addClass(cell, 'wj-custom-readonly');
        }
      }
    }

  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("specificCtrl", function (event, data) {
    $scope.searchspecificDateList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 특정일 그리드 조회
  $scope.searchspecificDateList = function () {
    // 파라미터
    var params     = {};
    if(orgnFg === "STORE"){
      params.storeCd  = orgnCd;
    }else {
      params.storeCd = $("#speSelectStoreCd").val();
    }
    params.listScale = $scope.conListScale.text; //-페이지 스케일 갯수
    // params.listScale = 15;
    // params.listScale = listScaleBoxSpecific.selectedValue;
    // params.curr = 1;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/order/outstockReqDate/specificDate/list.sb", params);
  };

  // 특정일 신규등록
  $scope.newSpecificDate = function () {
    $scope._broadcast('speDateRegistCtrl');
  };

  // 특정일 저장
  $scope.saveSpecificDate = function () {

    $scope._popConfirm(messages["cmm.choo.save"], function() {
      var params = [];
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        $scope.flex.collectionView.itemsEdited[i].status = "U";

        if ($scope.flex.collectionView.itemsEdited[i].outstockReqYn === 'Y') {
          $scope.flex.collectionView.itemsEdited[i].orderStartTime = $scope.flex.collectionView.itemsEdited[i].startHour + $scope.flex.collectionView.itemsEdited[i].startMs;
          $scope.flex.collectionView.itemsEdited[i].orderEndTime = $scope.flex.collectionView.itemsEdited[i].endHour + $scope.flex.collectionView.itemsEdited[i].endMs;
        } else {
          $scope.flex.collectionView.itemsEdited[i].orderStartTime = null;
          $scope.flex.collectionView.itemsEdited[i].orderEndTime = null;
        }
        params.push($scope.flex.collectionView.itemsEdited[i]);
      }
      $.postJSONArray("/iostock/order/outstockReqDate/specificDate/save.sb", params, function (result) {
            s_alert.pop(messages["cmm.saveSucc"]);
            $scope.searchspecificDateList();
          },
          function (result) {
            s_alert.pop(result.data.msg);
          });
    });

  };

  // 특정일 삭제
  $scope.deleteSpecificDate = function () {
    // 삭제 하시겠습니까?
    var msg = messages["cmm.choo.delete"];
    s_alert.popConf(msg, function () {
      var params = [];
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        $scope.flex.collectionView.itemsEdited[i].status = "U";
        params.push($scope.flex.collectionView.itemsEdited[i]);
      }
      $.postJSONArray("/iostock/order/outstockReqDate/specificDate/delete.sb", params, function(result) {
            s_alert.pop( messages["cmm.delSucc"] );
            $scope.searchspecificDateList();
          },
          function(result) {
            s_alert.pop(result.data.msg);
          });
    });
  };

}]);
