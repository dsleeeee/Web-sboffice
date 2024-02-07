/****************************************************************
 *
 * 파일명 : cornerAdd.js
 * 설  명 : 매장정보관리 > 매장터미널관리 > 코너추가 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.03.02     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('cornerAddCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('cornerAddCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "cornrCd") {
                    var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                    wijmo.setAttribute(e.cell, 'aria-readonly', true);
                }
            }
        });

        // 코너코드 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (sender, elements) {
            var col = sender.columns[elements.col];
            if (col.binding === "cornrCd") {
                elements.cancel = true;
            }
        });

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 변경시 체크박스 체크
                if (col.binding === "cornrNm" || col.binding === "ownerNm" || col.binding === "bizNo" ||
                    col.binding === "telNo" || col.binding === "vanCd" || col.binding === "vanTermnlNo" ||
                    col.binding === "vanSerNo") {
                    $scope.checked(item);
                }
            }
            s.collectionView.commitEdit();
        });
    };

    $scope.$on("cornerAddCtrl", function(event, data) {

        // 매장코드, 매장명
       $("#cnr_storeCd").text(data.storeCd);
       $("#cnr_storeNm").text(data.storeNm);
        
        // 코너 리스트 조회
        $scope.searchCornerList();

    });

    // 코너 리스트 조회
    $scope.searchCornerList = function () {
        // 파라미터
        var params = {};
        params.storeCd = $("#cnr_storeCd").text();

        $scope._inquirySub(baseUrl + "corner/getCornerDtlList.sb", params, function() {});
    };

    // 코너 추가
    $scope.cornerAdd = function () {
        var params = {};
        params.status = "I";
        params.cornrCd = "자동채번";
        params.cornrNm = "";
        params.ownerNm = "";
        params.bizNo = "";
        params.telNo = "";
        params.vanCd = "";
        params.vanTermnlNo = "";
        params.vanSerNo = "";
        params.termnlCnt = 0;
        params.gChk = true;

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };

    // 코너 저장
    $scope.cornerSave = function () {

        $scope._popConfirm(messages["cmm.choo.save"], function() {

            $scope.flex.collectionView.commitEdit();

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                $scope.flex.collectionView.itemsEdited[i].storeCd = $("#cnr_storeCd").text();
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }
            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                $scope.flex.collectionView.itemsAdded[i].status = "I";
                $scope.flex.collectionView.itemsAdded[i].storeCd = $("#cnr_storeCd").text();
                params.push($scope.flex.collectionView.itemsAdded[i]);
            }
            for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
                $scope.flex.collectionView.itemsRemoved[i].status = "D";
                $scope.flex.collectionView.itemsRemoved[i].storeCd = $("#cnr_storeCd").text();
                params.push($scope.flex.collectionView.itemsRemoved[i]);
            }

            if(params.length <= 0) {
                // 선택된 데이터가 없습니다.
                $scope._popMsg(messages["cmm.not.select"]);
                return false;
            }

            // 필수값 체크
            for (var i = 0; i < params.length; i++) {

                if(params[i].status !== "D") {

                    // 코너명을(를) 입력해주세요.
                    if (params[i].cornrNm == "") {
                        $scope._popMsg(messages["terminalManage.cornrNm"] + messages["terminalManage.require.input"]);
                        return false;
                    }

                    // 사업자번호을(를) 입력해주세요.
                    if (params[i].bizNo == "") {
                        $scope._popMsg(messages["terminalManage.bizNo"] + messages["terminalManage.require.input"]);
                        return false;
                    }

                    // 숫자만 입력
                    var numChkexp = /[^-\.0-9]/g;
                    if (numChkexp.test(nvl(params[i].bizNo, 0)) || String(params[i].bizNo).split('.').length - 1 > 1) {
                        // 사업자번호는 숫자만 입력해주세요.
                        $scope._popMsg(messages["terminalManage.bizNo"] + messages["cmm.require.number"]);
                        return false;
                    }

                    if (numChkexp.test(nvl(params[i].telNo, 0)) || String(params[i].telNo).split('.').length - 1 > 1) {
                        // 전화번호는 숫자만 입력해주세요.
                        $scope._popMsg(messages["terminalManage.telNo"] + messages["cmm.require.number"]);
                        return false;
                    }
                }
            }

            params = JSON.stringify(params);
            $scope._save(baseUrl + "corner/insertCorner.sb", params, function () {

                // 코너 리스트 재조회
                $scope.searchCornerList();

                // 코너 SelectBox 재조회
                var terminalScope = agrid.getScope('terminalCtrl');
                terminalScope.setCorner();
            });

        });
    };

    // 코너 삭제
    $scope.cornerDel = function () {

        // 삭제 전 코너에 등록된 터미널정보가 있는지 체크
        for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk) {
                if(item.termnlCnt > 0){
                    $scope._popMsg( "'[" + item.cornrCd + "]" + item.cornrNm + "' " + messages["terminalManage.cornr.delete.chk.msg"]);
                    return false;
                }
            }
        }

        for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk) {
                $scope.flex.collectionView.removeAt(i);
            }
        }

        // 코너 정보 저장
        $scope.cornerSave();
    };

    // 닫기
    $scope.close = function () {
        $scope.cornerAddLayer.hide();
    };

    // 코너정보 수정시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    };

}]);