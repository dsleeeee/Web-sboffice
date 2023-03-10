/****************************************************************
 *
 * 파일명 : envstRemark.js
 * 설  명 : 환경설정기능설명 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.03.03     권지현      1.0
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

/**
 * 대표명칭 그리드 생성
 */
app.controller('representCtrl', ['$scope', '$http', function ($scope, $http) {

  $scope._setComboData("envstFg", envstFgNm);
  $scope._setComboData("envstGrpCd", envstGrpCdNm);

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('representCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("representCtrl");
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
          $scope._broadcast('envstRemarkCtrl', selectedRow);
        }
      }
    });
  };
  // 대표명칭 그리드 조회
  $scope.$on("representCtrl", function(event, data) {

    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sys/cd/envstRemark/envstRemark/envst/list.sb", params, function() {
      // 대표명칭 그리드 버튼 show
      $("#btnSaveRepresent").show();
      $("#btnSaveDetail").hide();
      $("#envstCd").val("");
      $("#envstRemark").val("");

      $scope.flex.collectionView.commitEdit();

    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.getList = function (){

  }

  // 설정그룹 변경시 환경그룹 dataMap 변경 ( //todo 추후 유통 추가)
  $scope.changeEnvstFg = function(s, e){
    if (e.panel === s.cells) {
      var col = s.columns[e.col];
      if (col.binding === "envstGrpCdNm") {
        var changeEnvstFg = s.rows[e.row].dataItem.envstFg;
        switch (changeEnvstFg) {
          case '00':
            col.dataMap = cmmEnvstGrp;
            break;
          case '01':
            col.dataMap = foodEnvstGrp;
            break;
          case '03':
            col.dataMap = posEnvstGrp;
            break;
        }
      }
    }
  };

}]);

/**
 * 설명 그리드 생성
 */
app.controller('envstRemarkCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('envstRemarkCtrl', $scope, $http, true));

  // 세부명칭 그리드 조회
  $scope.$on("envstRemarkCtrl", function(event, data) {
    $("#envstCd").val(data.envstCd);
    $("#envstRemark").val(data.envstRemark);
    $("#btnSaveDetail").show();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 세부명칭 그리드 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = {};
    params.envstCd = $("#envstCd").val();
    params.envstRemark = $scope.envstRemark;

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._postJSONSave.withPopUp("/sys/cd/envstRemark/envstRemark/envst/saveEnvstRemark.sb", params, function(response){
      var result = response.data.data;
      if(result < 1){
        $scope._popMsg(messages["cmm.registFail"]);
      }else{
        $scope._popMsg(messages["cmm.saveSucc"]);
        $scope._broadcast("representCtrl");
      }
    });
  };

}]);
