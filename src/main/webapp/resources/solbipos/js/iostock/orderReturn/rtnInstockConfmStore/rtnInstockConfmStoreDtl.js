/****************************************************************
 *
 * 파일명 : rtnInstockConfmStore.js
 * 설  명 : 반품본사입고현황 상세화면 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.07.09     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 반품본사입고현황 상세화면 그리드 controller */
app.controller('rtnInstockConfmStoreDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('rtnInstockConfmStoreDtlCtrl', $scope, $http, true));

    $scope._setComboData("stmtAcctFg", [
        {"name": messages["rtnInstockConfmStore.dtl.stmtAcctAll"], "value": ""},
        {"name": messages["rtnInstockConfmStore.dtl.stmtAcctSplr"], "value": "1"},
        {"name": messages["rtnInstockConfmStore.dtl.stmtAcctSplrRcpnt"], "value": "2"}
    ]);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        var comboParams = {};
        comboParams.nmcodeGrpCd = "093";

        var url = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';

        // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
        $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

        // 그리드 포맷 핸들러
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "inEtcQty") { // 입수에 따라 출고수량 컬럼 readonly 컨트롤
                    if (item.poUnitQty === 1) {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);

                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                    }
                }
            }
        });

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        //Header column merge (출고수량, 입고수량)
        s.allowMerging = 'ColumnHeaders';
        s.columnHeaders.rows[0].allowMerging = true;

        //Header - START
        //헤더 생성
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        for (var i = 0; i < s.columnHeaders.rows.length; i++) {
            s.columnHeaders.setCellData(i, "slipNo", messages["rtnInstockConfmStore.dtl.slipNo"]);
            s.columnHeaders.setCellData(i, "slipFg", messages["rtnInstockConfmStore.dtl.slipFg"]);
            s.columnHeaders.setCellData(i, "seq", messages["rtnInstockConfmStore.dtl.seq"]);
            s.columnHeaders.setCellData(i, "storeCd", messages["rtnInstockConfmStore.dtl.storeCd"]);
            s.columnHeaders.setCellData(i, "prodCd", messages["rtnInstockConfmStore.dtl.prodCd"]);
            s.columnHeaders.setCellData(i, "prodNm", messages["rtnInstockConfmStore.dtl.prodNm"]);
            s.columnHeaders.setCellData(i, "barcdCd", messages["rtnInstockConfmStore.dtl.barcdCd"]);
            s.columnHeaders.setCellData(i, "poUnitFg", messages["rtnInstockConfmStore.dtl.poUnitFg"]);
            s.columnHeaders.setCellData(i, "poUnitQty", messages["rtnInstockConfmStore.dtl.poUnitQty"]);
            s.columnHeaders.setCellData(i, "outSplyUprc", messages["rtnInstockConfmStore.dtl.outSplyUprc"]);

            s.columnHeaders.setCellData(i, "outUnitQty", messages["rtnInstockConfmStore.dtl.outUnitQty"]);
            s.columnHeaders.setCellData(i, "outEtcQty", messages["rtnInstockConfmStore.dtl.outUnitQty"]);
            s.columnHeaders.setCellData(i, "inUnitQty", messages["rtnInstockConfmStore.dtl.inUnitQty"]);
            s.columnHeaders.setCellData(i, "inEtcQty", messages["rtnInstockConfmStore.dtl.inUnitQty"]);

            s.columnHeaders.setCellData(i, "inTotQty", messages["rtnInstockConfmStore.dtl.inTotQty"]);
            s.columnHeaders.setCellData(i, "inAmt", messages["rtnInstockConfmStore.dtl.inAmt"]);
            s.columnHeaders.setCellData(i, "inVat", messages["rtnInstockConfmStore.dtl.inVat"]);
            s.columnHeaders.setCellData(i, "inTot", messages["rtnInstockConfmStore.dtl.inTot"]);
            s.columnHeaders.setCellData(i, "remark", messages["rtnInstockConfmStore.dtl.remark"]);
            s.columnHeaders.setCellData(i, "vatFg01", messages["rtnInstockConfmStore.dtl.vatFg"]);
        }
        //Header - END

        for (var i = 0; i < 19; i++) {
            s.columnHeaders.columns[i].allowMerging = true;
        }
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("rtnInstockConfmStoreDtlCtrl", function (event, data) {
        $scope.startDate = data.startDate;
        $scope.endDate = data.endDate;
        $scope.slipFg = data.slipFg;
        $scope.slipNo = data.slipNo;
        $scope.vendrCd = data.vendrCd;
        $scope.wjRtnInstockConfmStoreDtlLayer.show(true);

        //전표상세 조회
        $scope.getSlipNoInfo();
        
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 전표상세 조회
    $scope.getSlipNoInfo = function () {
        var params = {};
        params.slipNo = $scope.slipNo;

        //가상로그인 session 설정
        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method: 'POST', //방식
            url: '/iostock/orderReturn/rtnInstockConfmStore/rtnInstockConfmStore/getSlipNoInfo.sb', /* 통신할 URL */
            params: params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data)) {

                    $scope.outDate = response.data.data.outDate;
                    $scope.inDate = response.data.data.inDate;
                    $scope.slipFg = response.data.data.slipFg;
                    $scope.slipKind = response.data.data.slipKind;
                    $scope.slipKindNm = response.data.data.slipKindNm;
                    $scope.procFg = response.data.data.procFg;
                    $scope.storeCd = response.data.data.storeCd;
                    $scope.storeNm = response.data.data.storeNm;
                    $scope.hdRemark = response.data.data.remark;
                    $scope.dlvrCd = nvl(response.data.data.dlvrCd, '');
                    $scope.dlvrNm = response.data.data.dlvrNm;
                    
                    // 컬럼 조회만 가능
                    $scope.flex.isReadOnly = true;

                    // 출고확정
                    if ($scope.procFg === "20") {
                        $("#lblTitle").text(messages["rtnInstockConfmStore.dtl.slipNo"] + ' : ' + $scope.slipNo + ', ' + messages["rtnInstockConfmStore.dtl.store"] + ' : ' + $scope.storeNm + ', ' + messages["rtnInstockConfmStore.dtl.outDate"] + ' : ' + getFormatDate($scope.outDate));
                    }
                    // 수주확정 또는 입고확정
                    else if ($scope.procFg === "10" || $scope.procFg === "30") {
                        // 수주확정
                        if ($scope.procFg === "10") {
                            $("#lblTitle").text(messages["rtnInstockConfmStore.dtl.slipNo"] + ' : ' + $scope.slipNo + ', ' + messages["rtnInstockConfmStore.dtl.store"] + ' : ' + $scope.storeNm + ', ' + messages["rtnInstockConfmStore.dtl.reqDate"] + ' : ' + getFormatDate($scope.outDate));
                        }
                        // 입고확정
                        else if ($scope.procFg === "30") {
                            $("#lblTitle").text(messages["rtnInstockConfmStore.dtl.slipNo"] + ' : ' + $scope.slipNo + ', ' + messages["rtnInstockConfmStore.dtl.store"] + ' : ' + $scope.storeNm + ', ' + messages["rtnInstockConfmStore.dtl.outDate"] + ' : ' + getFormatDate($scope.outDate) + ', ' + messages["rtnInstockConfm.dtl.inDate"] + ' : ' + getFormatDate($scope.inDate));
                        }
                    }

                    // 반품본사입고현황 상세화면 리스트 조회
                    $scope.searchRtnInstockConfmStoreDtlList();
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

    // 반품본사입고현황 상세화면 리스트 조회
    $scope.searchRtnInstockConfmStoreDtlList = function () {
        // 파라미터
        var params    = {};
        params.slipNo = $scope.slipNo;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/iostock/orderReturn/rtnInstockConfmStore/rtnInstockConfmStore/dtlList.sb", params, function () {
        });
    };

    // 거래명세표
    $scope.reportTrans = function () {
        var params = {};
        params.startDate = $scope.startDate;
        params.endDate = $scope.endDate;
        params.slipFg = $scope.slipFg;
        params.strSlipNo = $scope.slipNo;
        params.stmtAcctFg = $scope.stmtAcctFg;
        $scope._broadcast('transReportCtrl', params);
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
        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method: 'POST', //방식
            url: comboUrl, /* 통신할 URL */
            params: params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data.list)) {
                    var list = response.data.data.list;
                    var comboArray = [];
                    var comboData = {};

                    if (comboFg.indexOf("combo") >= 0 && nvl(comboId, '') !== '') {
                        comboArray = [];
                        if (option === "A") {
                            comboData.name = messages["cmm.all"];
                            comboData.value = "";
                            comboArray.push(comboData);
                        } else if (option === "S") {
                            comboData.name = messages["cmm.select"];
                            comboData.value = "";
                            comboArray.push(comboData);
                        }

                        for (var i = 0; i < list.length; i++) {
                            comboData = {};
                            comboData.name = list[i].nmcodeNm;
                            comboData.value = list[i].nmcodeCd;
                            comboArray.push(comboData);
                        }
                        $scope._setComboData(comboId, comboArray);
                    }

                    if (comboFg.indexOf("map") >= 0 && nvl(gridMapId, '') !== '') {
                        comboArray = [];
                        for (var i = 0; i < list.length; i++) {
                            comboData = {};
                            comboData.id = list[i].nmcodeCd;
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