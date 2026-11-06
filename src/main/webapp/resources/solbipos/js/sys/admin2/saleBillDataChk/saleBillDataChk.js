/****************************************************************
 *
 * 파일명 : saleBillDataChk.js
 * 설  명 : 매출자료검증 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.11.03     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  매출자료검증 그리드 생성
 */
app.controller('saleBillDataChkCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleBillDataChkCtrl', $scope, $http, false));

    // 영업일자
    var srchSaleDate = wcombo.genDateVal("#srchSaleDate", gvStartDate);

    // 검증결과 (검증한 순서대로 위에 쌓음)
    var chkList = [];

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.data = new wijmo.collections.CollectionView(chkList);

        // ReadOnly 효과설정, 오류 결과는 빨간색 표시
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                wijmo.addClass(e.cell, 'wj-custom-readonly');
                if ((col.binding === "resultNm" || col.binding === "resultCode") && item.resultCode !== "0000") {
                    wijmo.addClass(e.cell, 'red');
                }
            }
        });
    };

    // 검증
    $scope.saleBillDataChk = function () {

        // 매장코드, 포스번호, 영수번호 필수
        if (isEmpty($scope.storeCd)) {
            $scope._popMsg(messages["saleBillDataChk.storeCd"] + messages["cmm.require.text"]);
            return false;
        }
        if (isEmpty($scope.posNo)) {
            $scope._popMsg(messages["saleBillDataChk.posNo"] + messages["cmm.require.text"]);
            return false;
        }
        if (isEmpty($scope.billNo)) {
            $scope._popMsg(messages["saleBillDataChk.billNo"] + messages["cmm.require.text"]);
            return false;
        }

        var params = {};
        params.storeCd  = $scope.storeCd;
        params.saleDate = wijmo.Globalize.format(srchSaleDate.value, 'yyyyMMdd');
        params.posNo    = $scope.posNo;
        params.billNo   = $scope.billNo;

        $scope._postJSONQuery.withPopUp("/sys/admin2/saleBillDataChk/saleBillDataChk/getSaleBillDataChk.sb", params, function (response) {
            var result = response.data.data;

            var item = {};
            item.chkDt      = wijmo.Globalize.format(new Date(), 'yyyy-MM-dd HH:mm:ss');
            item.storeCd    = params.storeCd;
            item.saleDate   = wijmo.Globalize.format(srchSaleDate.value, 'yyyy-MM-dd');
            item.posNo      = params.posNo;
            item.billNo     = params.billNo;
            item.resultCode = result.resultCode;
            item.resultNm   = result.resultCode === "0000" ? messages["saleBillDataChk.resultOk"] : messages["saleBillDataChk.resultErr"];
            item.resultMsg  = result.resultMsg;

            chkList.unshift(item);
            $scope.data.refresh();
        });
    };

}]);
