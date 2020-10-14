/****************************************************************
 *
 * 파일명 : posFuncUseFnKey.js
 * 설  명 : 포스기능키 사용등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.10.08    이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// angular 그리드 생성
app.controller('posFuncUseFnKeyCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('posFuncUseFnKeyCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    $scope.$on("posFuncUseFnKeyCtrl", function(event, data) {

        $scope.posFuncUseFnKeyLayer.show(true);

        // 선택한 fnkey 이름 셋팅 및 hidden 에 값 가지고 있기
        $("#fnKeyTitle").text(data.fnkeyNm);
        $("#hdStoreCd").val(data.storeCd);
        $("#hdPosNo").val(data.posNo);

        // 포스기능키목록 그리드 조회
        $scope.getPosFuncDetail(data);

        event.preventDefault();

    });

    // 포스기능키목록 그리드 조회
    $scope.getPosFuncDetail = function(data) {
        var params = {};

        params.storeCd = data.storeCd;
        params.posNo = data.posNo;
        params.fnkeyFg = data.fnkeyFg;

        $scope._inquiryMain("/base/store/posfunc/use/getPosConfDetail.sb", params, function() {}, false);
    }

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

    // 저장
    $scope.save = function() {

        $scope.flex.collectionView.commitEdit();

        // 파라미터 설정
        var params = [];

        // dispSeq 재설정
        var editItems = [];
        for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
            if( isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
                editItems.push($scope.flex.collectionView.items[s]);
            }
        }

        for (var s = 0; s < editItems.length; s++) {
            editItems[s].dispSeq = (s + 1);
            console.log(editItems);
            $scope.flex.collectionView.editItem(editItems[s]);
            editItems[s].status = "U";
            $scope.flex.collectionView.commitEdit();
        }


        for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
            $scope.flex.collectionView.itemsEdited[u].status = 'U';
            $scope.flex.collectionView.itemsEdited[u].storeCd = $("#hdStoreCd").val();
            $scope.flex.collectionView.itemsEdited[u].posNo = $("#hdPosNo").val();

            params.push($scope.flex.collectionView.itemsEdited[u]);
        }


        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/store/posfunc/use/savePosConf.sb', params, function() {

            $scope.posFuncUseFnKeyLayer.hide(true);

            // 저장 후 부모창 그리드 재조회
            showPosFuncList();
        });
    }

}]);