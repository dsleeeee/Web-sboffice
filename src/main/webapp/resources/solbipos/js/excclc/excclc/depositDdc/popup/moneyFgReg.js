/****************************************************************
 *
 * 파일명 : moneyFgReg.js
 * 설  명 : 계정등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.04.20     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('moneyFgRegCtrl', ['$scope', '$http', '$compile', '$timeout', function ($scope, $http, $compile, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('moneyFgRegCtrl', $scope, $http, true));

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

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });
    };

    $scope.$on('moneyFgRegCtrl', function (event, data) {

        // 그룹코드 보여주기
        $("#lblNmcodeGrpCd").text($("#hdNmcodeGrpCd").val());

        // 계정코드 조회
        $scope.getMoneyFgList();
        event.preventDefault();
    });

    // 계정코드 조회
    $scope.getMoneyFgList = function () {

        var params = {};
        params.nmcodeGrpCd = $("#hdNmcodeGrpCd").val();

        $scope._inquirySub("/iostock/vendr/vendrOrder/vendrOrderDtl/getVendrOrderTypeCdList.sb", params, function() {

            // 계정코드 [00]입금은 기본필수 코드 이므로, edit 안되게 막기
            var grid = wijmo.Control.getControl("#wjGridMoneyFgReg");
            var rows = grid.rows;

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];
                if(item.nmcodeCd === "00"){
                    item.gChk = false;
                    rows[i].isReadOnly = true;
                }
            }

        }, false);
    };

    // 계정코드 추가
    $scope.addMoneyFg = function () {

        var params = {};

        params.gChk = false;
        params.nmcodeCd = '';
        params.nmcodeNm = '';
        params.useYn = 'Y';

        // 행추가
        $scope._addRow(params, 1);
    };

    // 계정코드 삭제
    $scope.delMoneyFg= function () {
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){
                $scope.flex.collectionView.removeAt(i);
            }
        }

        // 삭제하면서 바로 저장
        $scope.saveMoneyFg();
    };

    // 계정코드 저장
    $scope.saveMoneyFg = function () {

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
                $scope._popMsg(messages['depositDdc.code'] + messages['cmm.require.text']); // 코드을(를) 입력해주세요.
                return;
            }

            if( $scope.flex.collectionView.items[m].nmcodeCd.length < 2) {
                $scope._popMsg(messages['depositDdc.code2.require']); // 코드는 2자리로 입력하세요.
                return;
            }

            if (/[0-9]/g.test($scope.flex.collectionView.items[m].nmcodeCd) === false) {
                $scope._popMsg(messages['depositDdc.num.require']); // 코드는 숫자로 입력하세요.
                return;
            }

            if( $scope.flex.collectionView.items[m].nmcodeNm === null || $scope.flex.collectionView.items[m].nmcodeNm === '' || isEmptyObject($scope.flex.collectionView.items[m].nmcodeNm)) {
                $scope._popMsg(messages['depositDdc.name'] + messages['cmm.require.text']); // 명칭을(를) 입력해주세요.
                return;
            }
        }

        // 중복체크
        for (var j=0; j<$scope.flex.collectionView.itemCount; j++) {
            for (var k=0; k<$scope.flex.collectionView.itemCount; k++) {
                if(j !== k){
                    if($scope.flex.collectionView.items[j].nmcodeCd === $scope.flex.collectionView.items[k].nmcodeCd){
                        $scope._popMsg(messages['depositDdc.code'] + '[' + $scope.flex.collectionView.items[j].nmcodeCd + ']이(가) ' + messages['depositDdc.duplicate']); // 코드[1]가 중복됩니다. 다시 입력하세요.
                        return;
                    }
                }
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/iostock/vendr/vendrOrder/vendrOrderDtl/saveVendrOrderTypeCdList.sb', params, function() {

            // 계정코드 재조회
            $scope.getMoneyFgList();

            // 계정 콤보박스 재셋팅
            var scope = agrid.getScope("depositDdcRegCtrl");
            scope.setCombo();
        });
    }


}]);