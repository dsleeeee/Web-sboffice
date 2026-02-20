/****************************************************************
 *
 * 파일명 : empTalk.js
 * 설  명 : 키오스크 직원대화 관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.02.12     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 키오스크 직원대화 관리 그리드 생성
 */
app.controller('empTalkCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('empTalkCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');

        // 대화코드 ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var dataItem = s.rows[e.row].dataItem;
                if(orgnFg === 'STORE') {
                    if(dataItem.regFg === "H") {
                        if (col.binding === "empTextInfo" || col.binding === "useYn") {
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);
                        }
                    }
                }
            }
        });

        // 대화코드 에디팅 방지
        s.beginningEdit.addHandler(function (s, e) {
            var col = s.columns[e.col];
            var dataItem = s.rows[e.row].dataItem;

            // 대화 코드는 입력 불가능
            if (col.binding === "empTextNo") {
                e.cancel = true;
            }
            if(orgnFg === 'STORE'){
                if(dataItem.regFg === "H"){
                    if(col.binding === "empTextInfo" || col.binding === "useYn") {
                        e.cancel = true;
                    }
                }
            }

        });

        // 키오스크 직원대화 조회
        $scope.getEmpTalkList();

    };

    $scope.$on("empTalkCtrl", function(event, data) {
        $scope.getEmpTalkList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 키오스크 직원대화 조회
    $scope.getEmpTalkList = function(){

        var params = {};

        // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
        $scope._inquiryMain("/base/store/empTalk/empTalk/getEmpTalkList.sb", params, function(){});

    };

    // 위로 옮기기 버튼
    $scope.rowMoveUp = function() {
        var movedRows = 0;
        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            var item = $scope.flex.collectionView.items[i];
            if (i > 0 && item.gChk) {
                if (!$scope.flex.collectionView.items[i - 1].gChk) {
                    movedRows = i - 1;
                    var tmpItem = $scope.flex.collectionView.items[movedRows];
                    $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
                    $scope.flex.collectionView.items[i] = tmpItem;
                    $scope.flex.collectionView.commitEdit();
                    $scope.flex.collectionView.refresh();
                }
            }
        }
        $scope.flex.select(movedRows, 1);
    };

    // 아래로 옮기기 버튼
    $scope.rowMoveDown = function() {
        var movedRows = 0;
        for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if ((i < $scope.flex.itemsSource.itemCount - 1) && item.gChk) {
                if (!$scope.flex.collectionView.items[i + 1].gChk) {
                    movedRows = i + 1;
                    var tmpItem = $scope.flex.collectionView.items[movedRows];
                    $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
                    $scope.flex.collectionView.items[i] = tmpItem;
                    $scope.flex.collectionView.commitEdit();
                    $scope.flex.collectionView.refresh();
                }
            }
        }
        $scope.flex.select(movedRows, 1);
    };

    // 대화관리 그리드 행 추가
    $scope.addRow = function() {
        var params = {};
        params.status = "I";
        params.gChk = true;
        params.empTextNo="자동채번";
        params.useYn = "Y";
        if(orgnFg === "STORE") {
            params.regFg = 'S'
            params.regFgNm = "매장";  
        }
        $scope._addRow(params);
    };

    // 대화관리 그리드 행 삭제
    $scope.delete = function(){

        var msg = messages["cmm.choo.delete"];
        if(orgnFg === 'STORE'){
            msg = messages["empTalk.msg.saveEmpTalk"];
        }

        $scope._popConfirm(msg, function() {
            // 파라미터 설정
            var params = [];
            for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                var item = $scope.flex.collectionView.items[i];
                if (item.gChk) {
                    $scope.flex.collectionView.removeAt(i);
                }
            }

            for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
                $scope.flex.collectionView.itemsRemoved[d].status = 'D';
                params.push($scope.flex.collectionView.itemsRemoved[d]);
            }
            // 삭제기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/store/empTalk/empTalk/saveEmpTalk.sb', params, function() {
                $scope.getEmpTalkList();
            });
        });
    };

    // 대화관리 저장
    $scope.save = function() {

        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var s = 0, num = 1; s < $scope.flex.rows.length; s++, num++) {
                if ($scope.flex.collectionView.items[s].dispSeq !== num) {
                    $scope.flex.collectionView.items[s].dispSeq = num;
                    $scope.flex.collectionView.editItem($scope.flex.collectionView.items[s]);
                    $scope.flex.collectionView.items[s].status = "U";
                    $scope.flex.collectionView.commitEdit();
                }
            }

            for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
                $scope.flex.collectionView.itemsEdited[u].status = 'U';
                if (nvl($scope.flex.collectionView.itemsEdited[u].empTextInfo, '').getByteLengthForOracle() > 60) {
                    var msg = messages["empTalk.empTextInfo"] + messages["cmm.overLength"] + " 60 " +
                        ", 현재 : " + $scope.flex.collectionView.itemsEdited[u].empTextInfo.getByteLengthForOracle() + messages["cmm.bateLengthInfo"];
                    $scope._popMsg(msg);
                    return false;
                }
                params.push($scope.flex.collectionView.itemsEdited[u]);
            }
            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                $scope.flex.collectionView.itemsAdded[i].status = 'I';
                if (nvl($scope.flex.collectionView.itemsAdded[i].empTextInfo, '').getByteLengthForOracle() > 60) {
                    var msg = messages["empTalk.empTextInfo"] + messages["cmm.overLength"] + " 60 " +
                        ", 현재 : " + $scope.flex.collectionView.itemsAdded[i].empTextInfo.getByteLengthForOracle() + messages["cmm.bateLengthInfo"];
                    $scope._popMsg(msg);
                    return false;
                }
                params.push($scope.flex.collectionView.itemsAdded[i]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/store/empTalk/empTalk/saveEmpTalk.sb", params, function () {
                $scope.getEmpTalkList();
            });
        });
    }

    $scope.empTalkRegStore = function (){

        var params = {}

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/base/store/empTalk/empTalk/empTalkRegStore.sb", params, function(){
            $scope.getEmpTalkList();
        });

    };

}]);


