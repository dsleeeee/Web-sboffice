/****************************************************************
 *
 * 파일명 : envConfgBatchChangeEnvSettingCtrl.js
 * 설  명 : 환경설정관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.13     노현수      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var allEnvstGrp  = new Array();
var cmmEnvstGrp  = new Array();
var foodEnvstGrp = new Array();
var posEnvstGrp  = new Array();

// dataMap 에서 name 만을 dataMap으로 사용. (name과 value 동시 사용시 오류) // todo 추후 수정 필요
for(var i in envstGrpCdNm) {
  // allEnvstGrp.push(envstGrpCdNm[i].name);
  if(envstGrpCdNm[i].nmcodeItem1 === '00'){
    cmmEnvstGrp.push(envstGrpCdNm[i].nmcodeNm);
  } else if(envstGrpCdNm[i].nmcodeItem1 === '01'){
    foodEnvstGrp.push(envstGrpCdNm[i].nmcodeNm);
  } else if(envstGrpCdNm[i].nmcodeItem1 === '03') {
    posEnvstGrp.push(envstGrpCdNm[i].nmcodeNm);
  }
}


var useYn = [
//	{"name":"매장형태","value":"1"}
  {"name":"사용","value":"Y"}
  ,{"name":"미사용","value":"N"}
];

/**
 * 대표명칭 그리드 생성
 */
app.controller('envConfgBatchChangeEnvSettingCtrl', ['$scope', '$http', function ($scope, $http) {

  $scope._setComboData("envstFg", envstFgNm);
  $scope._setComboData("envstGrpCd", envstGrpCdNm);
  $scope._setComboData("rUseYn", useYn);             // 사용여부

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('envConfgBatchChangeEnvSettingCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("envConfgBatchChangeEnvSettingCtrl");
    // 그리드 DataMap 설정
    $scope.envstFgNmDataMap = new wijmo.grid.DataMap(envstFgNm, 'value', 'name');
    // $scope.envstGrpCdNmDataMap = new wijmo.grid.DataMap(envstGrpCdNm, 'value', 'name');
    $scope.envstGrpCdNmDataMap = allEnvstGrp;
    $scope.targtFgDataMap = new wijmo.grid.DataMap(targtFg, 'value', 'name');
    $scope.dirctInYnDataMap = new wijmo.grid.DataMap([{id: "Y", name: "직접"}, {
      id: "N",
      name: "선택"
    }], 'id', 'name');
    $scope.useYnDataMap = new wijmo.grid.DataMap([{id: "Y", name: "사용"}, {
      id: "N",
      name: "사용안함"
    }], 'id', 'name');
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "envstCd") {
          var item = s.rows[e.row].dataItem;
          if (item.status !== "I") {
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          } else {
            wijmo.removeClass(e.cell, 'wj-custom-readonly');
          }
        }
      }
    });
    // 대표명칭 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === "envstCd") {
        var dataItem = s.rows[e.row].dataItem;
        if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
          e.cancel = true;
        }
      }
    });
    // 대표명칭 그리드 선택 이벤트
    s.hostElement.addEventListener('mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var selectedRow = s.rows[ht.row].dataItem;
        var col = ht.panel.columns[ht.col];
        if( col.binding === "envstCd" && selectedRow.status !== "I") {
          $scope._broadcast('detailCtrl', selectedRow.envstCd);
        }
      }
    });
  };
  // 대표명칭 그리드 조회
  $scope.$on("envConfgBatchChangeEnvSettingCtrl", function(event, data) {

    // 파라미터
    var params = {};

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sys/cd/envConfg/envConfg/envst/list.sb", params, function() {

      $scope.flex.collectionView.commitEdit();

    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 일괄변경
  $scope.batchChangeEnvSetting = function() {
    // 시스템패스워드 비우기
    $scope.systemPw = "";

    var batchEnvSettingVal= "";

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
      return false;
    }

    batchEnvSettingVal = $scope.envSettingVal;

    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].useYn = $scope.envSettingVal;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    if(params.length <= 0) {
      s_alert.pop(messages["cmm.not.select"]);
      return;
    }

    $scope.flex.collectionView.refresh();
  };

  // 저장
  $scope.saveEnvSetting = function() {
    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
      return false;
    }

    if($scope.systemPw === "" || $scope.systemPw === undefined) {
      $scope._popMsg(messages["envConfgBatchChange.store.systemPwBlank"]); // 시스템패스워드를 입력해주세요.
      return false;

    } else {
      var date = new Date();
      var year = new String(date.getFullYear());
      var month = new String(date.getMonth()+1);
      month = month.length <= 1 ? "0"+month : month;
      var day = new String(date.getDate());
      day = day.length <= 1 ? "0"+day : day;
      var hh = new String(date.getHours());
      hh = hh < 10 ? "0"+hh : hh;
      var pw = userId + year + month + day + hh + "99";

      if($scope.systemPw !== pw) {
        $scope._popMsg(messages["envConfgBatchChange.store.systemPwError"]); // 시스템패스워드를 틀렸습니다. 다시확인해주세요.
        return false;
      }
    }

    // 저장 하시겠습니까?
    var msg = messages["cmm.choo.save"];

    $scope._popConfirm(msg, function() {

      // 파라미터 설정
      var params = new Array();
      for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
        var item = $scope.flex.collectionView.items[i];
        if ($scope.flex.collectionView.items[i].gChk) {
          params.push($scope.flex.collectionView.items[i]);
        }
      }

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save("/store/manage/envConfgBatchChange/envConfgBatchChangeEnvSetting/getEnvConfgBatchChangeEnvSettingSave.sb", params, function(){ $scope.allSearch(); });
    });
  };

  // 재조회
  $scope.allSearch = function () {
    $scope.searchEnvConfgBatchChangeEnvSetting();
  };
}]);

/**
 * 세부명칭 그리드 생성
 */
app.controller('detailCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('detailCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("detailCtrl");
    // 그리드 DataMap 설정
    $scope.useYnDataMap = new wijmo.grid.DataMap([{id: "Y", name: "사용"}, {
      id: "N",
      name: "사용안함"
    }], 'id', 'name');
    $scope.defltYnDataMap = new wijmo.grid.DataMap([{id: "Y", name: "기본"}, {
      id: "N",
      name: "기본아님"
    }], 'id', 'name');
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "envstValCd") {
          var item = s.rows[e.row].dataItem;
          if (item.status !== "I") {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          } else {
            wijmo.removeClass(e.cell, 'wj-custom-readonly');
          }
        }
      }
    });
    // 세부명칭 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === "envstValCd") {
        var dataItem = s.rows[e.row].dataItem;
        if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
          e.cancel = true;
        }
      }
    });
  };
  // 세부명칭 그리드 조회
  $scope.$on("detailCtrl", function(event, data) {
    // 파라미터
    var params = {};
    params.envstCd = data;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/sys/cd/envConfg/envConfg/envstDtl/list.sb", params, function() {
    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
}]);
