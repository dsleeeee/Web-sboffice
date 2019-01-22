/** 물량오류 상세 그리드 controller */
app.controller('storeMoveDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeMoveDtlCtrl', $scope, $http, true));

  $scope.moveDate            = wcombo.genDate("#dtlMoveDate");
  $scope.moveDate.isReadOnly = true;
  $scope.sessionStoreCd      = gvStoreCd;

  $scope._setComboData("srchDtlDlvrFg", [
    {"name": messages["storeMove.dlvrFg0"], "value": "0"},
    {"name": messages["storeMove.dlvrFg1"], "value": "1"},
    {"name": messages["storeMove.dlvrFg2"], "value": "2"}
  ]);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    var comboParams         = {};
    comboParams.nmcodeGrpCd = "097";
    var url                 = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
    $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // 그리드 포맷 핸들러
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col  = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "outEtcQty") { // 입수에 따라 주문수량 컬럼 readonly 컨트롤
          if (item.poUnitQty === 1) {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
            wijmo.setAttribute(e.cell, 'aria-readonly', true);

            // Attribute 의 변경사항을 적용.
            var html         = e.cell.outerHTML;
            e.cell.outerHTML = html;
          }
        }
      }
    });

    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        // 주문수량 수정시 금액,VAT,합계 계산하여 보여준다.
        if (col.binding === "outUnitQty" || col.binding === "outEtcQty" || col.binding === "splyUprc") {
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


  $scope.calcAmt = function (item) {
    /** 수량이 없는 경우 계산하지 않음.
        null 또는 undefined 가 나올수 있으므로 확실하게 확인하기 위해 nvl 처리로 null 로 바꿔서 비교 */
    if (nvl(item.outUnitQty, null) === null && (item.poUnitQty !== 1 && nvl(item.outEtcQty, null) === null)) return false;

    var outSplyUprc  = parseInt(nvl(item.splyUprc, 0));
    var poUnitQty    = parseInt(item.poUnitQty);
    var vat01        = parseInt(item.vatFg01);
    var outEnvst0011 = parseInt(item.outEnvst0011);

    var unitQty    = parseInt(nvl(item.outUnitQty, 0)) * parseInt(item.poUnitQty);
    var etcQty     = parseInt(nvl(item.outEtcQty, 0));
    var totQty     = parseInt(unitQty + etcQty);
    var tempOutAmt = Math.round(totQty * outSplyUprc / poUnitQty);
    var outAmt     = tempOutAmt - Math.round(tempOutAmt * vat01 * outEnvst0011 / 11);
    var outVat     = Math.round(tempOutAmt * vat01 / (10 + outEnvst0011));
    var outTot     = parseInt(outAmt + outVat);

    item.outTotQty = totQty; // 총수량
    item.amt       = outAmt; // 이출금액
    item.vat       = outVat; // 이출VAT
    item.tot       = outTot; // 이출합계
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("storeMoveDtlCtrl", function (event, data) {
    $scope.slipNo = data.slipNo;
    $scope.wjStoreMoveDtlLayer.show(true);

    $scope.fnBtnLayerDisplay(false);
    $scope.getSlipNoInfo();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 전표상세 조회
  $scope.getSlipNoInfo = function () {
    var params    = {};
    params.slipNo = $scope.slipNo;

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/move/storeMove/storeMoveDtl/getSlipNoInfo.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {

          $scope.moveDate.value = new Date(getFormatDate(response.data.data.moveDate, "-"));
          $scope.procFg         = response.data.data.procFg;
          $scope.dlvrFg         = response.data.data.dlvrFg;
          $scope.dtlHdRemark    = response.data.data.remark;
          $scope.outStoreCd     = response.data.data.outStoreCd;
          $scope.outStoreNm     = response.data.data.outStoreNm;
          $scope.inStoreCd      = response.data.data.inStoreCd;
          $scope.inStoreNm      = response.data.data.inStoreNm;

          $("#storeMoveDtlOutSelectStoreCd").val($scope.outStoreCd);
          $("#storeMoveDtlOutSelectStoreNm").val('[' + $scope.outStoreCd + '] ' + $scope.outStoreNm);
          $("#storeMoveDtlInSelectStoreCd").val($scope.inStoreCd);
          $("#storeMoveDtlInSelectStoreNm").val('[' + $scope.inStoreCd + '] ' + $scope.inStoreNm);

          var regDt      = response.data.data.regDt;
          var outConfmDt = response.data.data.outConfmDt;
          var inConfmDt  = response.data.data.inConfmDt;
          var hqConfmDt  = response.data.data.hqConfmDt;
          var outSlipNo  = response.data.data.outSlipNo;
          var inSlipNo   = response.data.data.inSlipNo;

          $("#regDt").html(regDt !== null ? getFormatDateTime(regDt) : '');
          $("#outConfmDt").html(outConfmDt !== null ? getFormatDateTime(outConfmDt) : '');
          $("#inConfmDt").html(inConfmDt !== null ? getFormatDateTime(inConfmDt) : '');
          $("#hqConfmDt").html(hqConfmDt !== null ? getFormatDateTime(hqConfmDt) : '');
          $("#thOutSlipNo").html($scope.sessionStoreCd == $scope.outStoreCd ? messages ["storeMove.dtl.outSlipNo"] : '');
          $("#outSlipNo").html($scope.sessionStoreCd == $scope.outStoreCd ? outSlipNo : '');
          $("#thInSlipNo").html($scope.sessionStoreCd == $scope.inStoreCd ? messages ["storeMove.dtl.inSlipNo"] : '');
          $("#inSlipNo").html($scope.sessionStoreCd == $scope.inStoreCd ? inSlipNo : '');

          if ($scope.procFg === '0') {
            if ($scope.sessionStoreCd == $scope.outStoreCd) {
              $scope.fnBtnLayerDisplay(true);
              $timeout(function () {
                $("#btnDtlConfirm").html(messages["storeMove.dtl.outConfirmBtn"]);
              }, 100);
              $scope.flex.isReadOnly = false;
            }
          } else {
            $scope.flex.isReadOnly = true;

            if ($scope.procFg === '1' && $scope.sessionStoreCd == $scope.inStoreCd) {
              $scope.btnDtlConfirm = true;
              $timeout(function () {
                $("#btnDtlConfirm").html(messages["storeMove.dtl.inConfirmBtn"]);
              }, 100);
            }
          }

          $scope.searchStoreMoveDtlList();
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


  // 매장이동관리 상세내역 리스트 조회
  $scope.searchStoreMoveDtlList = function () {
    // 파라미터
    var params            = {};
    params.slipNo         = $scope.slipNo;
    params.sessionStoreCd = $scope.sessionStoreCd;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/move/storeMove/storeMoveDtl/list.sb", params, function () {
    });
  };


  // 저장
  $scope.save = function (confirmFg) {
    var params = [];

    // 확정이면서 그리드의 수정된 내역은 없는 경우 저장로직 태우기 위해 값 하나를 강제로 수정으로 변경한다.
    if (confirmFg === "Y" && $scope.flex.collectionView.itemsEdited.length <= 0) {
      var item = $scope.flex.collectionView.items[0];
      if (item === null) return false;

      $scope.flex.collectionView.editItem(item);
      item.status = "U";
      $scope.flex.collectionView.commitEdit();
    }

    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];

      if (item.outEtcQty !== null && (parseInt(item.outEtcQty) >= parseInt(item.poUnitQty))) {
        $scope._popMsg(messages["storeMove.dtl.not.etcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
        return false;
      }
      if (item.outTot !== null && (parseInt(item.outTot) > 9999999999)) {
        $scope._popMsg(messages["storeMove.dtl.not.overOutTot"]); // 이출금액이 너무 큽니다.
        return false;
      }

      item.status    = "U";
      item.slipNo    = $scope.slipNo;
      item.dlvrFg    = $scope.dlvrFg;
      item.remark    = $scope.dtlHdRemark;
      item.procFg    = $scope.procFg;
      item.storageCd = "001";
      item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
      item.confirmFg = confirmFg;

      params.push(item);
    }

    $scope._save("/iostock/move/storeMove/storeMoveDtl/save.sb", params, function () {
      $scope.saveStoreMoveDtlCallback()
    });
  };


  $scope.confirm = function () {
    var msg = messages["storeMove.dtl.confirmMsg"]; // 현전표를 확정하시겠습니까?
    s_alert.popConf(msg, function () {
      $scope.save('Y');
    });
  };


  $scope.saveStoreMoveDtlCallback = function () {
    $scope.wjStoreMoveDtlLayer.hide(true);

    var storeMoveScope = agrid.getScope('storeMoveCtrl');
    storeMoveScope.searchStoreMoveList();
  };


  $scope.addProd = function () {
    var params        = {};
    params.slipNo     = $scope.slipNo;
    params.outStoreCd = $scope.outStoreCd;
    params.inStoreCd  = $scope.inStoreCd;

    $scope._broadcast('storeMoveAddProdCtrl', params);
  };


  $scope.delete = function () {
    var msg = messages["storeMove.dtl.delMsg"]; // 현전표를 삭제하시겠습니까?
    s_alert.popConf(msg, function () {
      var params    = {};
      params.slipNo = $scope.slipNo;

      /** 로딩바 show */
      $scope.$broadcast('loadingPopupActive', messages["cmm.saving"]);

      // ajax 통신 설정
      $http({
        method : 'POST', //방식
        url    : '/iostock/move/storeMove/storeMoveDtl/delete.sb', /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope._httpStatusCheck(response, true)) {
          $scope._popMsg(messages["cmm.saveSucc"]);
          $scope.saveStoreMoveDtlCallback();
        }
      }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        $scope._popMsg(messages["cmm.saveFail"]);
        /** 로딩바 hide */
        $scope.$broadcast('loadingPopupInactive');
        return false;
      }).then(function () {
        // "complete" code here
        /** 로딩바 hide */
        $scope.$broadcast('loadingPopupInactive');
      });
    });
  };


  // 버튼 display
  $scope.fnBtnLayerDisplay = function (isVisible) {
    $scope.btnDtlAddProd = isVisible;
    $scope.btnDtlSave    = isVisible;
    $scope.btnDtlConfirm = isVisible;
    $scope.btnDtlDel     = isVisible;
  };


  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.storeMoveDtlOutSelectStoreShow = function () {
    $scope._broadcast('storeMoveDtlOutSelectStoreCtrl');
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.storeMoveDtlInSelectStoreShow = function () {
    $scope._broadcast('storeMoveDtlInSelectStoreCtrl');
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
