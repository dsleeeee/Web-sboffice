// 시 VALUE
var Hh = [24];
for (i = 0; i < 24; i++) {
  var timeVal = i.toString();
  if (i >= 0 && i <= 9) { timeVal = "0" + timeVal; }
  Hh[i] = {"name": timeVal, "value": timeVal};
}
// 분 VALUE
var MmSs = [60];
for (i = 0; i < 60; i++) {
  var timeVal = i.toString();
  if (i >= 0 && i <= 9) { timeVal = "0" + timeVal; }
  MmSs[i] = {"name": timeVal, "value": timeVal};
}

/** 영업시간 복사 - 오케스트레이션 */
app.controller('bizHourCopyCtrl', ['$scope', '$http', function ($scope, $http) {

  // 조회한 원본 매장코드 (조회 전이거나 조회 후 원본 매장을 바꾸면 복사 차단)
  $scope.searchedSrcStoreCd = "";

  // 원본 매장 조회 → 요일별/특정일 읽기전용 그리드 로드
  $scope.search = function () {
    if ($("#copySrcStoreCd").val() === "") {
      $scope._popMsg(messages["bizHour.require.selectStore"]);
      return false;
    }
    $scope.searchedSrcStoreCd = $("#copySrcStoreCd").val();
    $scope._broadcast('bizHourCopyDaysCtrl', {proc: "search"});
    $scope._broadcast('bizHourCopySpecificCtrl', {proc: "search"});
  };

  // 복사 실행 (원본 → 타겟, copyType : D 요일별 / S 특정일 / A 전체)
  $scope.bizHourCopy = function (copyType) {
    var srcStoreCd = $("#copySrcStoreCd").val();
    var tgtStoreCd = $("#copyTgtStoreCd").val();

    if (srcStoreCd === "") {
      $scope._popMsg(messages["bizHour.require.selectStore"]);
      return false;
    }
    // 기준매장 조회 후 복사 (조회 전 → 조회 안내 / 조회 후 기준매장 변경 → 재조회 안내)
    if ($scope.searchedSrcStoreCd !== srcStoreCd) {
      $scope._popMsg($scope.searchedSrcStoreCd === ""
        ? messages["bizHour.require.searchFirst"]
        : messages["bizHour.changed.reSearch"]);
      return false;
    }
    if (tgtStoreCd === "") {
      $scope._popMsg(messages["bizHour.require.selectCopyStore"]);
      return false;
    }
    // 복사대상에 원본 매장이 포함되면 차단
    if (("," + tgtStoreCd + ",").indexOf("," + srcStoreCd + ",") !== -1) {
      $scope._popMsg(messages["bizHour.duplicate.targetStore"]);
      return false;
    }

    // 복사 유형별 확인 메시지 (D:요일별 / S:특정일 / A:전체)
    var confirmMsg = copyType === 'D' ? messages["bizHour.confirm.copyDays"]
                   : copyType === 'S' ? messages["bizHour.confirm.copySpecific"]
                   :                    messages["bizHour.confirm.copyMsg"];

    s_alert.popConf(confirmMsg, function () {
      var params = {};
      params.storeCd     = srcStoreCd;
      params.copyStoreCd = tgtStoreCd;
      params.copyType    = copyType;
      // 로딩(#_saving)·sid·세션만료 처리는 postJSONArray가 담당, FAIL 시 서버 업무오류 메시지 표시
      $.postJSONArray(baseUrl + "bizHourCopy/copy.sb", params,
        function (result) {
          s_alert.pop(messages["cmm.saveSucc"]);
        },
        function (result) {
          s_alert.pop(result.message);
        });
    });
  };

}]);

/** 영업시간 복사 - 원본 요일별 (읽기전용 피벗) */
app.controller('bizHourCopyDaysCtrl', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('bizHourCopyDaysCtrl', $scope, $http, false));

  $scope.initGrid = function (s, e) {
    $scope._gridDataInit();

    $scope.sysStatFgMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    $scope.timeHourMap  = new wijmo.grid.DataMap(Hh, 'value', 'name');
    $scope.timeMsMap    = new wijmo.grid.DataMap(MmSs, 'value', 'name');

    // 헤더머지 : 상단(요일) + 하단(시작/종료) 2줄 헤더
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    s.columnHeaders.rows[0].dataItem = {
      storeCd     : messages["bizHour.storeCd"],
      storeNm     : messages["bizHour.storeNm"],
      ownerNm     : messages["cmm.owner.nm"],
      sysStatFg   : messages["bizHour.sysStatFg"],
      startHourSun: messages["bizHour.day1"], startMsSun: messages["bizHour.day1"], endHourSun: messages["bizHour.day1"], endMsSun: messages["bizHour.day1"],
      startHourMon: messages["bizHour.day2"], startMsMon: messages["bizHour.day2"], endHourMon: messages["bizHour.day2"], endMsMon: messages["bizHour.day2"],
      startHourTue: messages["bizHour.day3"], startMsTue: messages["bizHour.day3"], endHourTue: messages["bizHour.day3"], endMsTue: messages["bizHour.day3"],
      startHourWed: messages["bizHour.day4"], startMsWed: messages["bizHour.day4"], endHourWed: messages["bizHour.day4"], endMsWed: messages["bizHour.day4"],
      startHourThu: messages["bizHour.day5"], startMsThu: messages["bizHour.day5"], endHourThu: messages["bizHour.day5"], endMsThu: messages["bizHour.day5"],
      startHourFri: messages["bizHour.day6"], startMsFri: messages["bizHour.day6"], endHourFri: messages["bizHour.day6"], endMsFri: messages["bizHour.day6"],
      startHourSat: messages["bizHour.day7"], startMsSat: messages["bizHour.day7"], endHourSat: messages["bizHour.day7"], endMsSat: messages["bizHour.day7"]
    };
  };

  $scope.itemFormatter = function (panel, r, c, cell) {
    if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
      panel.rows[r].allowMerging    = true;
      panel.columns[c].allowMerging = true;
      wijmo.setCss(cell, {display: 'table', tableLayout: 'fixed'});
      cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
      wijmo.setCss(cell.children[0], {display: 'table-cell', verticalAlign: 'middle', textAlign: 'center'});
    }
    else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
      if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
        cell.textContent = '';
      } else {
        cell.textContent = (r + 1).toString();
      }
    }
    else if (panel.cellType === wijmo.grid.CellType.Cell) {
      wijmo.addClass(cell, 'wj-custom-readonly');
    }
  };

  $scope.$on("bizHourCopyDaysCtrl", function (event, data) {
    if (data.proc === "search") { $scope.searchSrcDays(); }
    event.preventDefault();
  });

  // 원본 매장 요일별 조회 (피벗, 단일 매장)
  $scope.searchSrcDays = function () {
    var params = {};
    params.storeCd   = $("#copySrcStoreCd").val();
    params.listScale = 5000;
    params.curr      = 1;
    $scope._inquirySub(baseUrl + "days/pivotList.sb", params);
  };

}]);

/** 영업시간 복사 - 원본 특정일 (읽기전용) */
app.controller('bizHourCopySpecificCtrl', ['$scope', '$http', function ($scope, $http) {
  angular.extend(this, new RootController('bizHourCopySpecificCtrl', $scope, $http, false));

  $scope.initGrid = function (s, e) {
    $scope._gridDataInit();

    $scope.sysStatFgMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    $scope.bizFgMap     = new wijmo.grid.DataMap(bizFgData, 'value', 'name');
    $scope.timeHourMap  = new wijmo.grid.DataMap(Hh, 'value', 'name');
    $scope.timeMsMap    = new wijmo.grid.DataMap(MmSs, 'value', 'name');

    // 날짜 포맷 표시
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        }
      }
    });
  };

  $scope.itemFormatter = function (panel, r, c, cell) {
    if (panel.cellType === wijmo.grid.CellType.RowHeader) {
      if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
        cell.textContent = '';
      } else {
        cell.textContent = (r + 1).toString();
      }
    }
    else if (panel.cellType === wijmo.grid.CellType.Cell) {
      wijmo.addClass(cell, 'wj-custom-readonly');
    }
  };

  $scope.$on("bizHourCopySpecificCtrl", function (event, data) {
    if (data.proc === "search") { $scope.searchSrcSpecific(); }
    event.preventDefault();
  });

  // 원본 매장 특정일 조회 (단일 매장)
  $scope.searchSrcSpecific = function () {
    var params = {};
    params.storeCd   = $("#copySrcStoreCd").val();
    params.listScale = 5000;
    params.curr      = 1;
    $scope._inquirySub(baseUrl + "specificDate/list.sb", params);
  };

}]);
