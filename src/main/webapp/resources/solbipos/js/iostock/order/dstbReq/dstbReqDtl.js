/** 분배요청 상세 그리드 controller */
app.controller('dstbReqDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dstbReqDtlCtrl', $scope, $http, true));

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
        if (col.binding === "mdEtcQty") { // 입수에 따라 분배수량 컬럼 readonly 컨트롤
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
        if (col.binding === "mdSplyUprc" || col.binding === "mdUnitQty" || col.binding === "mdEtcQty") { // 분배수량 수정시
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
    var mdSplyUprc = parseInt(item.mdSplyUprc);
    var poUnitQty  = parseInt(item.poUnitQty);
    var vat01      = parseInt(item.vatFg01);
    var envst0011  = parseInt(item.envst0011);

    var unitQty = parseInt(nvl(item.mdUnitQty, 0)) * parseInt(item.poUnitQty);
    var etcQty  = parseInt(nvl(item.mdEtcQty, 0));
    var totQty  = parseInt(unitQty + etcQty);
    var tempAmt = Math.round(totQty * mdSplyUprc / poUnitQty);
    var mdAmt   = tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);
    var mdVat   = Math.round(tempAmt * vat01 / (10 + envst0011));
    var mdTot   = parseInt(mdAmt + mdVat);

    item.mdTotQty = totQty;// 총분배수량
    item.mdAmt    = mdAmt; // 금액
    item.mdVat    = mdVat; // VAT
    item.mdTot    = mdTot; // 합계
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dstbReqDtlCtrl", function (event, data) {
    $scope.reqDate = data.reqDate;
    $scope.storeCd = data.storeCd;
    $scope.storeNm = data.storeNm;
    $scope.slipFg  = data.slipFg;
    $scope.procFg  = data.procFg;
    $scope.vendrCd = data.vendrCd;

    $("#spanDtlTitle").html('['+messages["dstbReq.dtl.order"]+'] ' + '[' + $scope.storeCd + '] ' + $scope.storeNm);
    $scope.orderProcFgCheck();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 주문진행구분 체크 및 HD 비고 조회
  $scope.orderProcFgCheck = function () {
    var params     = {};
    params.reqDate = $scope.reqDate;
    params.slipFg  = $scope.slipFg;
    params.storeCd = $scope.storeCd;

    //가상로그인 session 설정
	      if(document.getElementsByName('sessionId')[0]){
	    	  params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/order/storeOrder/storeOrderRegist/orderProcFgCheck.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          $scope.procFg   = response.data.data.procFg;
          $scope.hdRemark = response.data.data.remark;
        }
        $scope.searchStoreLoan();
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

  // 매장여신 조회
  $scope.searchStoreLoan = function () {
    var params     = {};
    params.reqDate = $scope.reqDate;
    params.slipFg  = $scope.slipFg;
    params.storeCd = $scope.storeCd;

    //가상로그인 session 설정
	      if(document.getElementsByName('sessionId')[0]){
	    	  params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/order/storeOrder/storeOrderRegist/storeLoan.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          if($scope.availableOrderAmt !== null) {
            $scope.prevOrderTot      = response.data.data.prevOrderTot;
            $scope.limitLoanAmt      = response.data.data.limitLoanAmt;
            $scope.currLoanAmt       = response.data.data.currLoanAmt;
            $scope.maxOrderAmt       = response.data.data.maxOrderAmt;
            $scope.noOutstockAmtFg   = response.data.data.noOutstockAmtFg;
            $scope.availableOrderAmt = response.data.data.availableOrderAmt;

            //미출고금액 고려여부 사용인 경우
            if ($scope.noOutstockAmtFg === "Y") {
              if (parseInt($scope.availableOrderAmt) <= (parseInt($scope.currLoanAmt) - parseInt($scope.prevOrderTot))) {
                // 해당 조건에는 조회해 온 주문가능액 그대로 사용
              }
              else if (parseInt($scope.availableOrderAmt) >= (parseInt($scope.currLoanAmt) - parseInt($scope.prevOrderTot)) && parseInt($scope.maxOrderAmt) != 0) {
                $scope.availableOrderAmt = parseInt($scope.currLoanAmt) - parseInt($scope.prevOrderTot);
              }
              else {
                $scope.availableOrderAmt = parseInt($scope.availableOrderAmt) - parseInt($scope.prevOrderTot);
              }
            }

            $("#dtlAvailableOrderAmt").html("주문가능액 : " + addComma($scope.availableOrderAmt));
          }
          else {
            $("#dtlAvailableOrderAmt").html('');
          }
        }
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      $scope._popMsg(messages["cmm.saveFail"]);
      return false;
    }).then(function () {
      // "complete" code here
      $scope.wjDstbReqDtlLayer.show(true);
      $("#dstbConfirmFg").prop("checked", false);

      // 주문내역이 분배완료가 아닌 경우 분배 저장버튼 show
      if ($scope.procFg !== "20") {
        $scope.dstbBtnLayer = true;
      }
      else {
        $scope.dstbBtnLayer = false;
      }

      $scope.searchDstbReqDtlList();
    });
  };

  // 분배요청 상세내역 리스트 조회
  $scope.searchDstbReqDtlList = function () {
    // 파라미터
    var params     = {};
    params.reqDate = $scope.reqDate;
    params.storeCd = $scope.storeCd;
    params.slipFg  = $scope.slipFg;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/order/dstbReq/dstbReqDtl/list.sb", params, function () {
    });
  };

  // 공급가 및 수량적용
  $scope.setQtyApply = function () {
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      var item = $scope.flex.collectionView.items[i];
      $scope.flex.collectionView.editItem(item);

      item.mdSplyUprc = item.splyUprc;
      item.mdUnitQty  = item.orderUnitQty;
      item.mdEtcQty   = item.orderEtcQty;
      $scope.calcAmt(item);

      $scope.flex.collectionView.commitEdit();
    }
  };

  // 저장 전 값 체크
  $scope.saveValueCheck = function () {
    var params = [];
    var mdTot  = 0;

    // 분배완료여부가 체크 되어있으면서 그리드의 수정된 내역은 없는 경우 저장로직 태우기 위해 값 하나를 강제로 수정으로 변경한다.
    if ($("#dstbConfirmFg").is(":checked") && $scope.flex.collectionView.itemsEdited.length <= 0) {
      var item = $scope.flex.collectionView.items[0];
      if (item === null) return false;

      $scope.flex.collectionView.editItem(item);
      item.status = "U";
      $scope.flex.collectionView.commitEdit();
    }

    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];

      if (item.mdUnitQty === null && item.mdEtcQty === null) {
        $scope._popMsg(messages["dstbReq.dtl.require.mdQty"]); // 분배수량을 입력해주세요.
        return false;
      }
      if (item.mdEtcQty !== null && (parseInt(item.mdEtcQty) >= parseInt(item.poUnitQty))) {
        $scope._popMsg(messages["dstbReq.dtl.not.mdEtcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
        return false;
      }
      if (item.mdTot !== null && (parseInt(item.mdTot) > 9999999999)) {
        $scope._popMsg(messages["dstbReq.dtl.not.overMdTot"]); // 분배금액이 너무 큽니다.
        return false;
      }

      item.status        = "U";
      item.reqDate       = $scope.reqDate;
      item.slipFg        = $scope.slipFg;
      item.empNo         = "0000";
      item.storageCd     = "999";	//전체재고용 창고코드 ('001' -> '000' -> '999')
      item.hqBrandCd     = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
      item.dstbConfirmFg = ($("#dstbConfirmFg").is(":checked") ? $("#dstbConfirmFg").val() : "");
      item.hdRemark      = $scope.hdRemark;
      item.vendrCd       = $scope.vendrCd;
      mdTot += parseInt(item.mdTot);
      params.push(item);
    }

    if ($scope.availableOrderAmt !== null && parseInt($scope.availableOrderAmt) < parseInt(mdTot)) {
      var msg = messages["dstbReq.dtl.mdTotOver"]; //분배금액이 주문가능액을 초과하였습니다. 계속 진행 하시겠습니까?
      var id  = s_alert.randomString(5);
      var pop = $("#_layerConf").clone(true).attr("id", id).appendTo(document.body);
      pop.find("p").text(msg);

      // 확인 클릭
      pop.find("a.btn_blue.conf").bind("click", function () {
        $("#_alertTent").hide();
        pop.remove();
        $scope.saveDstbReqDtl(params);
      });

      // 취소 클릭
      pop.find("a.btn_gray.conf").bind("click", function () {
        $("#_alertTent").hide();
        pop.remove();
        // 그리드 초기화
        var cv          = new wijmo.collections.CollectionView([]);
        cv.trackChanges = true;
        $scope.data     = cv;
        return false;
      });

      $("#_alertTent").show();
      pop.show();
    }
    else {
      $scope.saveDstbReqDtl(params);
    }
  };

  // 분배 저장
  $scope.saveDstbReqDtl = function (params) {
    // 분배완료여부를 체크하고 저장하는 경우
    if($("#dstbConfirmFg").is(":checked")) {
      // 분배완료를 체크하셨습니다. 분배자료를 생성하므로 주문내역의 자료를 수정하실 수 없습니다. 계속 하시겠습니까?
      var msg = messages["dstbReq.dtl.confirmText"];
      s_alert.popConf(msg, function () {
        $scope._save("/iostock/order/dstbReq/dstbReqDtl/save.sb", params, function () {
          $scope.saveDstbReqDtlCallback()
        });
      });
    }
    else {
      $scope._save("/iostock/order/dstbReq/dstbReqDtl/save.sb", params, function () {
        $scope.saveDstbReqDtlCallback()
      });
    }
  };

  // 저장 후 콜백 함수
  $scope.saveDstbReqDtlCallback = function () {
    var dstbReqScope = agrid.getScope('dstbReqCtrl');
    dstbReqScope.searchDstbReqList();

    $scope.wjDstbReqDtlLayer.hide(true);
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

    //가상로그인 session 설정
	      if(document.getElementsByName('sessionId')[0]){
	    	  params['sid'] = document.getElementsByName('sessionId')[0].value;
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
                                                return column.visible;
                                            }
                },
              //'분배등록(요청분)_상세_' + getToday() + '.xlsx',
                '분배등록(요청분)_상세_' + getCurDate('-') + '.xlsx',
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
