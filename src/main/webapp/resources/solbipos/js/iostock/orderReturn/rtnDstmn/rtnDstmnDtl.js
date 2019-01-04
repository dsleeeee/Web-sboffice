/** 거래명세표 상세 그리드 controller */
app.controller('rtnDstmnDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('rtnDstmnDtlCtrl', $scope, $http, true));

  $scope.dtlOutDate = wcombo.genDate("#dtlOutDate");

  $scope._setComboData("stmtAcctFg", [
    {"name": messages["rtnDstmn.dtl.stmtAcctAll"], "value": ""},
    {"name": messages["rtnDstmn.dtl.stmtAcctSplr"], "value": "1"},
    {"name": messages["rtnDstmn.dtl.stmtAcctSplrRcpnt"], "value": "2"}
  ]);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 주문단위
    var comboParams         = {};
    comboParams.nmcodeGrpCd = "097";
    var url = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
    $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // 배송기사
    comboParams = {}; // 여러번 조회시 초기화를 해줘야함...
    var url     = '/iostock/order/outstockConfm/outstockConfm/getDlvrCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo", "srchDtlDlvrCd", null, url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // 그리드 포맷 핸들러
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col  = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "outEtcQty") { // 입수에 따라 출고수량 컬럼 readonly 컨트롤
          if (item.poUnitQty === 1) {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
            wijmo.setAttribute(e.cell, 'aria-readonly', true);

            // Attribute 의 변경사항을 적용.
            e.cell.outerHTML = e.cell.outerHTML;
          }
        }
      }
    });

    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "outSplyUprc" || col.binding === "outUnitQty" || col.binding === "outEtcQty") { // 출고수량 수정시
          var item = s.rows[e.row].dataItem;
          $scope.calcAmt(item);
        }
      }

      s.collectionView.commitEdit();
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 금액 계산
  $scope.calcAmt = function (item) {
    var outSplyUprc = parseInt(item.outSplyUprc);
    var poUnitQty   = parseInt(item.poUnitQty);
    var vat01       = parseInt(item.vatFg01);
    var envst0011   = parseInt(item.envst0011);

    var unitQty = parseInt(nvl(item.outUnitQty, 0)) * parseInt(item.poUnitQty);
    var etcQty  = parseInt(nvl(item.outEtcQty, 0));
    var totQty  = parseInt(unitQty + etcQty);
    var tempAmt = Math.round(totQty * outSplyUprc / poUnitQty);
    var outAmt  = tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);
    var outVat  = Math.round(tempAmt * vat01 / (10 + envst0011));
    var outTot  = parseInt(outAmt + outVat);

    item.outTotQty = totQty; // 총출고수량
    item.outAmt    = outAmt; // 금액
    item.outVat    = outVat; // VAT
    item.outTot    = outTot; // 합계
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("rtnDstmnDtlCtrl", function (event, data) {
    $scope.slipNo = data.slipNo;
    $scope.wjRtnDstmnDtlLayer.show(true);

    $scope.getSlipNoInfo();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.getSlipNoInfo = function () {
    var params    = {};
    params.slipNo = $scope.slipNo;

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/orderReturn/rtnDstmn/rtnDstmnDtl/getSlipNoInfo.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {

          $scope.dtlOutDate.value = new Date(getFormatDate(response.data.data.outDate, "-"));
          $scope.outDate          = response.data.data.outDate;
          $scope.inDate           = response.data.data.inDate;
          $scope.slipFg           = response.data.data.slipFg;
          $scope.slipKind         = response.data.data.slipKind;
          $scope.slipKindNm       = response.data.data.slipKindNm;
          $scope.procFg           = response.data.data.procFg;
          $scope.storeCd          = response.data.data.storeCd;
          $scope.storeNm          = response.data.data.storeNm;
          $scope.hdRemark         = response.data.data.remark;
          $scope.hqRemark         = response.data.data.hqRemark;
          $scope.dlvrCd           = response.data.data.dlvrCd;
          $scope.dlvrNm           = response.data.data.dlvrNm;

          // 수주확정
          if ($scope.procFg === "10") {
            $("#spanDtlTitle").html(messages["rtnDstmn.dtl.slipNo"]+' : ' + $scope.slipNo + ', '+messages["rtnDstmn.dtl.store"]+' : ' + $scope.storeNm + ', '+messages["rtnDstmn.dtl.reqDate"]+' : ' + getFormatDate($scope.outDate));
            $("#outstockBtnLayer").show();
            $scope.spanOutstockConfirmFg = true;
            $scope.btnDtlSave = true;
            $scope.btnOutstockAfterDtlSave = false;

            $scope.flex.isReadOnly = false;
          }
          // 출고확정 또는 입고확정
          else if ($scope.procFg === "20" || $scope.procFg === "30") {
            $("#outstockBtnLayer").hide();
            $scope.spanOutstockConfirmFg = false;
            $scope.btnDtlSave = false;
            $scope.btnOutstockAfterDtlSave = true;
            $scope.flex.isReadOnly = true;

            // 출고확정
            if ($scope.procFg === "20") {
              $("#spanDtlTitle").html(messages["rtnDstmn.dtl.slipNo"]+' : ' + $scope.slipNo + ', '+messages["rtnDstmn.dtl.store"]+' : ' + $scope.storeNm + ', '+messages["rtnDstmn.dtl.outDate"]+' : ' + getFormatDate($scope.outDate));
            }
            // 입고확정
            else if ($scope.procFg === "30") {
              $("#spanDtlTitle").html(messages["rtnDstmn.dtl.slipNo"]+' : ' + $scope.slipNo + ', '+messages["rtnDstmn.dtl.store"]+' : ' + $scope.storeNm + ', '+messages["rtnDstmn.dtl.outDate"]+' : ' + getFormatDate($scope.outDate) + ', '+messages["rtnDstmn.dtl.inDate"]+' : ' + getFormatDate($scope.inDate));
            }
          }

          $scope.searchRtnDstmnDtlList();
        }
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      $scope._popMsg(messages["cmm.saveFail"]);
      return false;
    }).then(function () {
      // "complete" code here
    });
  };

  // 거래명세표 상세내역 리스트 조회
  $scope.searchRtnDstmnDtlList = function () {
    // 파라미터
    var params    = {};
    params.slipNo = $scope.slipNo;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/orderReturn/rtnDstmn/rtnDstmnDtl/list.sb", params, function () {
    });
  };

  // 저장
  $scope.save = function () {
    var params = [];

    // 확정처리가 체크 되어있으면서 그리드의 수정된 내역은 없는 경우 저장로직 태우기 위해 값 하나를 강제로 수정으로 변경한다.
    if ($("#outstockConfirmFg").is(":checked") && $scope.flex.collectionView.itemsEdited.length <= 0) {
      var item = $scope.flex.collectionView.items[0];
      if (item === null) return false;

      $scope.flex.collectionView.editItem(item);
      item.status = "U";
      $scope.flex.collectionView.commitEdit();
    }

    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];

      if (item.outUnitQty === null && item.outEtcQty === null) {
        $scope._popMsg(messages["rtnDstmn.dtl.require.outQty"]); // 출고수량을 입력해주세요.
        return false;
      }
      if (item.outEtcQty !== null && (parseInt(item.outEtcQty) >= parseInt(item.poUnitQty))) {
        $scope._popMsg(messages["rtnDstmn.dtl.not.outEtcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
        return false;
      }
      if (item.outTot !== null && (parseInt(item.outTot) > 9999999999)) {
        $scope._popMsg(messages["rtnDstmn.dtl.not.overOutTot"]); // 출고금액이 너무 큽니다.
        return false;
      }

      item.status    = "U";
      item.outDate   = wijmo.Globalize.format($scope.dtlOutDate.value, 'yyyyMMdd');
      item.hdRemark  = $scope.hdRemark;
      item.hqRemark  = $scope.hqRemark;
      item.dlvrCd    = $scope.dlvrCd;
      item.confirmFg = ($("#outstockConfirmFg").is(":checked") ? $("#outstockConfirmFg").val() : "");

      params.push(item);
    }

    $scope._save("/iostock/orderReturn/rtnDstmn/rtnDstmnDtl/save.sb", params, function () {
      $scope.saveRtnDstmnDtlCallback()
    });
  };

  // 저장 후 콜백 함수
  $scope.saveRtnDstmnDtlCallback = function () {
    var rtnDstmnScope = agrid.getScope('rtnDstmnCtrl');
    rtnDstmnScope.searchRtnDstmnList();

    $scope.wjRtnDstmnDtlLayer.hide(true);
  };

  // 출고확정 이후 저장. 비고, 본사비고, 배송기사를 저장한다.
  $scope.saveOutstockAfter = function () {
    // 파라미터
    var params      = {};
    params.slipNo   = $scope.slipNo;
    params.hdRemark = $scope.hdRemark;
    params.hqRemark = $scope.hqRemark;
    params.dlvrCd   = $scope.dlvrCd;

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/orderReturn/rtnDstmn/rtnDstmnDtl/saveOutstockAfter.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        $scope._popMsg(messages["cmm.saveSucc"]);
        $scope.flex.collectionView.clearChanges();
        $scope.saveRtnDstmnDtlCallback();
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      $scope._popMsg(messages["cmm.saveFail"]);
      return false;
    }).then(function () {
      // "complete" code here
    });
  };

  $scope.fnConfirm = function () {
    if ($("#outstockConfirmFg").prop("checked")) {
      $("#divDtlOutDate").show();
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
