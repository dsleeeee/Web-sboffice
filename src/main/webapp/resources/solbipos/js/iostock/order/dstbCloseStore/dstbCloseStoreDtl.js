/** 분배마감 상세 그리드 controller */
app.controller('dstbCloseStoreDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dstbCloseStoreDtlCtrl', $scope, $http, true));

  $scope.procFgMap = new wijmo.grid.DataMap([
    {id: "00", name: messages["rtnDstbCloseStore.dtl.procFgReg"]},
    {id: "10", name: messages["rtnDstbCloseStore.dtl.procFgMd"]},
    {id: "20", name: messages["rtnDstbCloseStore.dtl.procFgDstbClose"]},
    {id: "30", name: messages["rtnDstbCloseStore.dtl.procFgSlip"]}
  ], 'id', 'name');

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    var comboParams         = {};
    comboParams.nmcodeGrpCd = "093";
    var url = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
    $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // 그리드 포맷 핸들러
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col  = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "mgrEtcQty") { // 입수에 따라 분배수량 컬럼 readonly 컨트롤
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
        if (col.binding === "mgrSplyUprc" || col.binding === "mgrUnitQty" || col.binding === "mgrEtcQty") { // 분배수량 수정시
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


    //Header column merge
    s.allowMerging                          = 'ColumnHeaders';
    s.columnHeaders.rows[0].allowMerging    = true;
  };

  // 금액 계산
  $scope.calcAmt = function (item) {
    var mgrSplyUprc = parseInt(item.mgrSplyUprc);
    var poUnitQty   = parseInt(item.poUnitQty);
    var vat01       = parseInt(item.vatFg01);
    var envst0011   = parseInt(item.envst0011);

    var unitQty = parseInt(nvl(item.mgrUnitQty, 0)) * parseInt(item.poUnitQty);
    var etcQty  = parseInt(nvl(item.mgrEtcQty, 0));
    var totQty  = parseInt(unitQty + etcQty);
    var tempAmt = Math.round(totQty * mgrSplyUprc / poUnitQty);
    var mgrAmt  = tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);
    var mgrVat  = Math.round(tempAmt * vat01 / (10 + envst0011));
    var mgrTot  = parseInt(mgrAmt + mgrVat);

    item.mgrTotQty = totQty; // 총분배수량
    item.mgrAmt    = mgrAmt; // 금액
    item.mgrVat    = mgrVat; // VAT
    item.mgrTot    = mgrTot; // 합계
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dstbCloseStoreDtlCtrl", function (event, data) {
    $scope.reqDate = data.reqDate;
    $scope.storeCd = data.storeCd;
    $scope.storeNm = data.storeNm;
    $scope.slipFg  = data.slipFg;
    $scope.procFg  = data.procFg;

    $scope.wjDstbCloseStoreDtlLayer.show(true);
    $("#spanDtlTitle").html('['+messages["dstbCloseStore.dtl.order"]+'] ' + '[' + $scope.storeCd + '] ' + $scope.storeNm);

    if (parseInt($scope.procFg) < 20) {
      $scope.btnDtlSave = true;
    }
    else {
      $scope.btnDtlSave = false;
    }
    $scope.searchDstbCloseStoreDtlList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 분배마감 상세내역 리스트 조회
  $scope.searchDstbCloseStoreDtlList = function () {
    // 파라미터
    var params     = {};
    params.reqDate = $scope.reqDate;
    params.storeCd = $scope.storeCd;
    params.slipFg  = $scope.slipFg;
    params.procFg  = $scope.procFg;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/order/dstbCloseStore/dstbCloseStoreDtl/list.sb", params, function () {
    });
  };

  // 저장
  $scope.save = function () {
    var params = [];

    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];

      if (item.mgrUnitQty === null && item.mgrEtcQty === null) {
        $scope._popMsg(messages["dstbCloseStore.dtl.require.mgrQty"]); // 분배수량을 입력해주세요.
        return false;
      }
      if (item.mgrEtcQty !== null && (parseInt(item.mgrEtcQty) >= parseInt(item.poUnitQty))) {
        $scope._popMsg(messages["dstbCloseStore.dtl.not.mgrEtcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
        return false;
      }
      if (item.mgrTot !== null && (parseInt(item.mgrTot) > 9999999999)) {
        $scope._popMsg(messages["dstbCloseStore.dtl.not.overMgrTot"]); // 분배금액이 너무 큽니다.
        return false;
      }

      item.status  = "U";
      item.reqDate = $scope.reqDate;
      item.slipFg  = $scope.slipFg;
      params.push(item);
    }

    $scope._save("/iostock/order/dstbCloseStore/dstbCloseStoreDtl/save.sb", params, function () {
      $scope.saveDstbCloseStoreDtlCallback()
    });
  };

  // 저장 후 콜백 함수
  $scope.saveDstbCloseStoreDtlCallback = function () {
    var dstbCloseStoreScope = agrid.getScope('dstbCloseStoreCtrl');
    dstbCloseStoreScope.searchDstbCloseStoreList();

    $scope.wjDstbCloseStoreDtlLayer.hide(true);
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


	//[엑셀 다운로드] - START	------------------------------------------------------------------------------------------------------------------------------
	$scope.excelDownload = function(){
		if ($scope.flex.rows.length <= 0) {
			$scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
			return false;
		}

		$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
		$timeout(function()	{
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(
                $scope.flex,
                {
                    includeColumnHeaders: 	true,
                    includeCellStyles   : 	true,
                    includeColumns      :   function (column) {
                                                //return column.visible;
                                                return column.binding != 'confirmYn'; //확정
                                            }
                },
              //'분배마감(매장별)_상세_' + getToday() + '.xlsx',
                '분배마감(매장별)_상세_' + getCurDate('-') + '.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
	};
    //[엑셀 다운로드] - END	------------------------------------------------------------------------------------------------------------------------------


}]);
