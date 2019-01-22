/** 물량오류 상세 그리드 controller */
app.controller('volmErrDtlCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('volmErrDtlCtrl', $scope, $http, true));

  $scope.outDate = wcombo.genDate("#dtlOutDate");

  $scope.errFgMap = new wijmo.grid.DataMap([
    {id: "0", name: messages["volmErr.reg"]},
    {id: "1", name: messages["volmErr.confirm"]},
  ], 'id', 'name');

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("volmErrDtlCtrl", function (event, data) {
    $scope.slipNo   = data.slipNo;
    $scope.slipFg   = data.slipFg;
    $scope.procFg   = data.procFg;
    $scope.storeCd  = data.storeCd;
    $scope.storeNm  = data.storeNm;
    $scope.hdRemark = data.hdRemark;

    $scope.wjVolmErrDtlLayer.show(true);
    $("#spanDtlTitle").html(messages["volmErr.dtl.slipNo"] + ' : ' + $scope.slipNo + ', ' + messages["volmErr.dtl.store"] + ' : ' + '[' + $scope.storeCd + '] ' + $scope.storeNm);

    if ($scope.procFg === "0") {
      $("#volmErrBtnLayer").show();
      $scope.volmErrConfirmFg = true;
      $scope.btnDtlSave = true;
    }
    else {
      $("#volmErrBtnLayer").hide();
      $scope.volmErrConfirmFg = false;
      $scope.btnDtlSave = false;
    }

    $("#volmErrConfirmFg").prop("checked", false);
    $("#divDtlOutDate").hide(); //페이지 호출시 출고일자는 일단 무조건 hide 처리.

    // 물량오류 처리구분 콤보박스 조회 및 생성. slipFg 가 있어야 하므로 상세페이지를 호출할때 조회하도록 함.
    var comboParams         = {};
    comboParams.nmcodeGrpCd = "089";
    comboParams.nmcodeItem1 = $scope.slipFg;
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
    $scope._queryCombo("map", null, "errFgMap", "/iostock/cmm/iostockCmm/getCombo.sb", comboParams);

    $scope.searchVolmErrDtlList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 물량오류 상세내역 리스트 조회
  $scope.searchVolmErrDtlList = function () {
    // 파라미터
    var params    = {};
    params.slipNo = $scope.slipNo;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/volmErr/volmErr/volmErrDtl/list.sb", params, function () {
    });
  };


  // 저장
  $scope.save = function () {
    if (!$("#volmErrConfirmFg").is(":checked")) {
      $scope._popMsg(messages["volmErr.dtl.require.confirmCheck"]); // 확정을 체크해 주세요.
      return false;
    }

    var params           = [];
    var newSlipNoFg      = "N";
    var hqNewAdjustFg    = "N";
    var storeNewAdjustFg = "N";

    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];

      if (item.errFg === null || item.errFg === "") {
        $scope._popMsg(messages["volmErr.dtl.require.selectErrFg"]); // 처리구분을 선택해 주세요.
        return false;
      }

      if (newSlipNoFg === "N" && (item.errFg === "O2" || item.errFg === "O4" || item.errFg === "R2")) {
        newSlipNoFg = "Y";
      }
      if (hqNewAdjustFg === "N" && (item.errFg === "O4" || item.errFg === "O5" || item.errFg === "R4")) {
        hqNewAdjustFg = "Y";
      }
      if (storeNewAdjustFg === "N" && item.errFg === "R2") {
        storeNewAdjustFg = "Y";
      }

      item.status           = "U";
      item.slipNo           = $scope.slipNo;
      item.slipFg           = $scope.slipFg;
      item.storeCd          = $scope.storeCd;
      item.hdRemark         = $scope.hdRemark;
      item.outDate          = wijmo.Globalize.format($scope.outDate.value, 'yyyyMMdd');
      item.confirmFg        = ($("#volmErrConfirmFg").is(":checked") ? $("#volmErrConfirmFg").val() : "");
      item.procFg           = $scope.procFg;
      item.newSlipNoFg      = newSlipNoFg;
      item.hqNewAdjustFg    = hqNewAdjustFg;
      item.storeNewAdjustFg = storeNewAdjustFg;

      params.push(item);
    }

    $scope._save("/iostock/volmErr/volmErr/volmErrDtl/save.sb", params, function () {
      $scope.saveVolmErrDtlCallback()
    });
  };

  $scope.saveVolmErrDtlCallback = function () {
    $scope.wjVolmErrDtlLayer.hide(true);

    var volmErrScope = agrid.getScope('volmErrCtrl');
    volmErrScope.searchVolmErrList();

  };

  // 확정체크시 값 체크
  $scope.fnConfirmChk = function () {
    if ($("#volmErrConfirmFg").is(":checked")) {
      var showDate = "N";
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];
        if (item.errFg === null || item.errFg === "") {
          $scope._popMsg(messages["volmErr.dtl.require.selectErrFg"]); // 처리구분을 선택해 주세요.
          $("#volmErrConfirmFg").prop("checked", false);
          return false;
        }
        if (showDate === "N" && (item.errFg === "O2" || item.errFg === "O4" || item.errFg === "R2")) {
          showDate = "Y";
        }
      }

      if (showDate === "Y") {
        $("#divDtlOutDate").show();
      }
    }
    else {
      $("#divDtlOutDate").hide();
    }
  };

  // DB 데이터를 조회해와서 그리드에서 사용할 Combo를 생성한다.
  // comboFg : map - 그리드에 사용할 Combo, combo - ComboBox 생성. 두가지 다 사용할경우 combo,map 으로 하면 둘 다 생성.
  // comboId : combo 생성할 ID
  // gridMapId : grid 에서 사용할 Map ID
  // url : 데이터 조회할 url 정보. 명칭관리 조회시에는 url 필요없음.
  // params : 데이터 조회할 url에 보낼 파라미터
  // option : A - combo 최상위에 전체라는 텍스트를 붙여준다. S - combo 최상위에 선택이라는 텍스트를 붙여준다. A 또는 S 가 아닌 경우는 데이터값만으로 생성
  // callback : queryCombo 후 callback 할 함수
  $scope._queryCombo = function (comboFg, comboId, gridMapId, url, params, option, callback) {
    var comboUrl = "/iostock/cmm/iostockCmm/getCombo.sb";
    if (url) {
      comboUrl = url;
    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : comboUrl, /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data.list)) {
          var list       = response.data.data.list;
          var comboArray = [];
          var comboData  = {};

          if (comboFg.indexOf("combo") >= 0 && nvl(comboId, '') !== '') {
            comboArray = [];
            if (option === "A") {
              comboData.name  = messages["cmm.all"];
              comboData.value = "";
              comboArray.push(comboData);
            } else if (option === "S") {
              comboData.name  = messages["cmm.select"];
              comboData.value = "";
              comboArray.push(comboData);
            }

            for (var i = 0; i < list.length; i++) {
              comboData       = {};
              comboData.name  = list[i].nmcodeNm;
              comboData.value = list[i].nmcodeCd;
              comboArray.push(comboData);
            }
            $scope._setComboData(comboId, comboArray);
          }

          if (comboFg.indexOf("map") >= 0 && nvl(gridMapId, '') !== '') {
            comboArray = [];
            for (var i = 0; i < list.length; i++) {
              comboData      = {};
              comboData.id   = list[i].nmcodeCd;
              comboData.name = list[i].nmcodeNm;
              comboArray.push(comboData);
            }
            $scope[gridMapId] = new wijmo.grid.DataMap(comboArray, 'id', 'name');
          }
        }
      }
    }, function errorCallback(response) {
      $scope._popMsg(messages["cmm.error"]);
      return false;
    }).then(function () {
      if (typeof callback === 'function') {
        $timeout(function () {
          callback();
        }, 10);
      }
    });
  };

}]);
