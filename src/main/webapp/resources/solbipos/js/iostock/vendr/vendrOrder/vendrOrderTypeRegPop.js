/****************************************************************
 *
 * 파일명 : vendrOrderTypeRegPop.js
 * 설  명 : 발주타입등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.12.10     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('vendrOrderTypeRegPopCtrl', ['$scope', '$http', '$compile', '$timeout', function ($scope, $http, $compile, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vendrOrderTypeRegPopCtrl', $scope, $http, true));

    $scope.initGrid = function (s, e) {
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');

        // 코드 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (sender, elements) {
            var col = sender.columns[elements.col];
            if (col.binding === "nmcodeCd") {
                var dataItem = s.rows[elements.row].dataItem;
                if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
                    elements.cancel = true;
                }
            }
        });
    };

    $scope.$on('vendrOrderTypeRegPopCtrl', function (event, data) {

        // 그룹코드 보여주기
        $("#lblNmcodeGrpCd").text($("#hdNmcodeGrpCd").val());

        // 발주타입코드 조회
        $scope.getVendrOrderTypeList();
        event.preventDefault();
    });

    // 발주타입코드 조회
    $scope.getVendrOrderTypeList = function () {

        var params = {};
        params.nmcodeGrpCd = $("#hdNmcodeGrpCd").val();

        $scope._inquirySub("/iostock/vendr/vendrOrder/vendrOrderDtl/getVendrOrderTypeCdList.sb", params, function() {}, false);
    };

    // 발주타입코드 추가
    $scope.addVendrType = function () {

        var params = {};

        params.gChk = false;
        params.nmcodeCd = '';
        params.nmcodeNm = '';
        params.useYn = 'Y';

        // 행추가
        $scope._addRow(params, 1);
    }

    // 발주타입코드 삭제
    $scope.delVendrType= function () {
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){
                $scope.flex.collectionView.removeAt(i);
            }
        }

        // 삭제하면서 바로 저장
        $scope.saveVendrType();
    }

    // 발주타입코드 저장
    $scope.saveVendrType = function () {

        $scope.flex.collectionView.commitEdit();

        // 파라미터 설정
        var params = [];

        for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
            $scope.flex.collectionView.itemsRemoved[d].nmcodeGrpCd = $("#hdNmcodeGrpCd").val();
            $scope.flex.collectionView.itemsRemoved[d].status = 'D';
            params.push($scope.flex.collectionView.itemsRemoved[d]);
        }

        for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
            $scope.flex.collectionView.itemsEdited[u].nmcodeGrpCd = $("#hdNmcodeGrpCd").val();
            $scope.flex.collectionView.itemsEdited[u].status = 'U';
            params.push($scope.flex.collectionView.itemsEdited[u]);
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            $scope.flex.collectionView.itemsAdded[i].nmcodeGrpCd = $("#hdNmcodeGrpCd").val();
            $scope.flex.collectionView.itemsAdded[i].status = 'I';
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }

        // 생성, 수정 Validation Check
        for (var m = 0; m < $scope.flex.collectionView.itemCount; m++) {

            if( $scope.flex.collectionView.items[m].nmcodeCd === null || $scope.flex.collectionView.items[m].nmcodeCd === '' || isEmptyObject($scope.flex.collectionView.items[m].nmcodeCd)) {
                $scope._popMsg(messages['vendrOrder.pop.code'] + messages['vendrOrder.pop.require']); // 코드을(를) 입력해주세요.
                return;
            }

            if( $scope.flex.collectionView.items[m].nmcodeNm === null || $scope.flex.collectionView.items[m].nmcodeNm === '' || isEmptyObject($scope.flex.collectionView.items[m].nmcodeNm)) {
                $scope._popMsg(messages['vendrOrder.pop.areaNm'] + messages['vendrOrder.pop.require']); // 명칭을(를) 입력해주세요.
                return;
            }
        }
        
        // 중복체크
        for (var j=0; j<$scope.flex.collectionView.itemCount; j++) {
            for (var k=0; k<$scope.flex.collectionView.itemCount; k++) {
                if(j !== k){
                    if($scope.flex.collectionView.items[j].nmcodeCd === $scope.flex.collectionView.items[k].nmcodeCd){
                        $scope._popMsg(messages['vendrOrder.pop.code'] + '[' + $scope.flex.collectionView.items[j].nmcodeCd + ']이(가) ' + messages['vendrOrder.pop.duplicate']); // 코드[1]가 중복됩니다. 다시 입력하세요.
                        return;
                    }
                }
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/iostock/vendr/vendrOrder/vendrOrderDtl/saveVendrOrderTypeCdList.sb', params, function() {

            // 코드 리스트 재조회
            $scope.getVendrOrderTypeList();

            // 발주타입 dynamic combo 재조회
            var vendrOrderCtrlScope = agrid.getScope("vendrOrderCtrl");
            var url                 = '/iostock/vendr/vendrOrder/vendrOrderDtl/getOrderTypeCombo.sb';
            var comboParams         = {};
            comboParams.nmcodeGrpCd = "024";
            // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
            vendrOrderCtrlScope._queryCombo("combo", "orderType", null, url, comboParams, "S"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.
        });
    }


}]);