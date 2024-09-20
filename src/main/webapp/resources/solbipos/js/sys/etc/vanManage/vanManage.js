/****************************************************************
 *
 * 파일명 : vanManage.js
 * 설  명 : 밴사정보관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.09.12     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 대표명칭 그리드 생성
 */
app.controller('vanManageCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vanManageCtrl', $scope, $http, false));
    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 내 콤보박스 설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "nmcodeCd") {
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 대표명칭 그리드 에디팅 방지
        // s.beginningEdit.addHandler(function (sender, elements) {
        //     var col = sender.columns[elements.col];
        //     if (col.binding === "nmcodeCd") {
        //         var dataItem = s.rows[elements.row].dataItem;
        //         if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
        //             elements.cancel = true;
        //         }
        //     }
        // });

        // 대표명칭 그리드 선택 이벤트
        s.hostElement.addEventListener('mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var selectedRow = s.rows[ht.row].dataItem;
                var col = ht.panel.columns[ht.col];
                if( col.binding === "nmcodeCd") {
                    $scope._broadcast('vanManageDtlCtrl', selectedRow.nmcodeCd);
                }
            }
        });
    };

    // 대표명칭 그리드 조회
    $scope.$on('vanManageCtrl', function(event, data) {
        var params = {};
        params.nmcodeGrpCd = "078";
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sys/etc/vanManage/vanManage/list.sb", params, function() {
        });
        var scope = agrid.getScope("vanManageDtlCtrl");
        scope._gridDataInit();
        scope.vanFg = '';
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


}]);
/**
 * 세부명칭 그리드 생성
 */
app.controller('vanManageDtlCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vanManageDtlCtrl', $scope, $http, false));
    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "vanFg") {
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 대표명칭 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (sender, elements) {
            var col = sender.columns[elements.col];
            if (col.binding === "vanFg") {
                elements.cancel = true;
            }
        });
    };

    // 세부명칭 그리드 조회
    $scope.$on("vanManageDtlCtrl", function(event, data) {
        var params = {};
        params.vanFg = data;
        $scope.vanFg = params.vanFg;
        // 조회URL, 파라미터, 콜백함수 형태로 조회함수 호출
        $scope._inquirySub("/sys/etc/vanManage/vanManage/getVanManageList.sb", params, function() {
        });
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 세부명칭 그리드 행 추가
    $scope.addRow = function() {

        if($scope.vanFg.length < 1){
            $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
            return false;
        }

        var params = {};
        params.vanFg = $scope.vanFg;
        params.vanCd = '자동채번';

        $scope._addRow(params);
    };

    // 세부명칭 그리드 저장
    $scope.save = function() {

        // 숫자만 입력
        var numChkexp = /[^0-9]/g;

        // 파라미터 설정
        var params = [];

        // 수정
        for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
            $scope.flex.collectionView.itemsEdited[u].status = "U";

            // 밴사명
            if($scope.flex.collectionView.itemsEdited[u].vanNm == null || $scope.flex.collectionView.itemsEdited[u].vanNm == ""){
                $scope._popMsg("밴사명을 입력해주세요"); // 밴사명을 입력해주세요.
                return false;
            }

            // 메인포트
            if($scope.flex.collectionView.itemsEdited[u].mainPort !== "" && $scope.flex.collectionView.itemsEdited[u].mainPort !== null && $scope.flex.collectionView.itemsEdited[u].mainPort !== undefined) {
                if (numChkexp.test($scope.flex.collectionView.itemsEdited[u].mainPort)) {
                    $scope._popMsg(messages["vanManage.mainPort"] + messages["cmm.require.number"]); // 메인Port는 숫자만 입력해주세요.
                    return false;
                }
            }

            // 서브포트
            if($scope.flex.collectionView.itemsEdited[u].subPort !== "" && $scope.flex.collectionView.itemsEdited[u].subPort !== null && $scope.flex.collectionView.itemsEdited[u].subPort !== undefined) {
                if (numChkexp.test($scope.flex.collectionView.itemsEdited[u].subPort)) {
                    $scope._popMsg(messages["vanManage.subPort"] + messages["cmm.require.number"]); // 서브Port는 숫자만 입력해주세요.
                    return false;
                }
            }

            // 팩스번호
            if($scope.flex.collectionView.itemsEdited[u].faxNo !== "" && $scope.flex.collectionView.itemsEdited[u].faxNo !== null) {
                if (numChkexp.test($scope.flex.collectionView.itemsEdited[u].faxNo)) {
                    $scope._popMsg(messages["vanManage.faxNo"] + messages["cmm.require.number"]); // 팩스번호는 숫자만 입력해주세요.
                    return false;
                }
            }
            params.push($scope.flex.collectionView.itemsEdited[u]);
        }
        
        // 신규 등록
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            $scope.flex.collectionView.itemsAdded[i].status = "I";

            // 밴사명
            if($scope.flex.collectionView.itemsAdded[i].vanNm == null || $scope.flex.collectionView.itemsAdded[i].vanNm == ""){
                $scope._popMsg("밴사명을 입력해주세요"); // 밴사명을 입력해주세요.
                return false;
            }

            // 메인포트
            if($scope.flex.collectionView.itemsAdded[i].mainPort !== "" && $scope.flex.collectionView.itemsAdded[i].mainPort !== null && $scope.flex.collectionView.itemsAdded[i].mainPort !== undefined) {
                if (numChkexp.test($scope.flex.collectionView.itemsAdded[i].mainPort)) {
                    $scope._popMsg(messages["vanManage.mainPort"] + messages["cmm.require.number"]); // 메인Port는 숫자만 입력해주세요.
                    return false;
                }
            }

            // 서브포트
            if($scope.flex.collectionView.itemsAdded[i].subPort !== "" && $scope.flex.collectionView.itemsAdded[i].subPort !== null && $scope.flex.collectionView.itemsAdded[i].subPort !== undefined) {
                if (numChkexp.test($scope.flex.collectionView.itemsAdded[i].subPort)) {
                    $scope._popMsg(messages["vanManage.subPort"] + messages["cmm.require.number"]); // 서브Port는 숫자만 입력해주세요.
                    return false;
                }
            }

            // 팩스번호
            if($scope.flex.collectionView.itemsAdded[i].faxNo !== "" && $scope.flex.collectionView.itemsAdded[i].faxNo !== null && $scope.flex.collectionView.itemsAdded[i].faxNo !== undefined) {
                if (numChkexp.test($scope.flex.collectionView.itemsAdded[i].faxNo)) {
                    $scope._popMsg(messages["vanManage.faxNo"] + messages["cmm.require.number"]); // 팩스번호는 숫자만 입력해주세요.
                    return false;
                }
            }
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sys/etc/vanManage/vanManage/getVanSave.sb", params, function() {
            $scope._broadcast('vanManageDtlCtrl', $scope.vanFg);
        });
    };

    // 등록 상품 삭제
    $scope.delete = function(){

        var params = [];

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].vanFg = $scope.vanFg
                params.push($scope.flex.collectionView.items[i]);
            }
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sys/etc/vanManage/vanManage/getVanDelete.sb", params, function(){
            $scope._broadcast('vanManageDtlCtrl', $scope.vanFg);
        });
    };

}]);
