/****************************************************************
 *
 * 파일명 : mediaPlaySeq.js
 * 설  명 : (관리자) 듀얼모니터영상관리 - 재생순서관리 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.03.07     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 파일타입
var fileTypeMediaPlaySeqComboData = [
    {"name":"POS 듀얼모니터(광고)","value":"001"},
    {"name":"로고","value":"002"},
    {"name":"키오스크(인트로)","value":"003"},
    {"name":"DID","value":"004"},
    {"name":"POS테이블 바탕화면","value":"005"},
    {"name":"로그인로고","value":"006"},
    {"name":"POS (인트로)","value":"007"},
    {"name":"영양정보(낮은화면)","value":"017"}
];

/**
 *  재생순서관리 그리드 생성
 */
app.controller('adminMediaPlaySeqCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('adminMediaPlaySeqCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("fileTypeMediaPlaySeq", fileTypeMediaPlaySeqComboData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.fileTypeDataMap = new wijmo.grid.DataMap(fileTypeMediaPlaySeqComboData, 'value', 'name'); // 파일타입
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name'); // 사용여부
        $scope.langFgDataMap = new wijmo.grid.DataMap(langFg, 'value', 'name'); // 언어구분

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 파일사이즈
                if (col.binding === "fileSize") {
                    if(e.cell.innerText !== null && e.cell.innerText !== undefined && e.cell.innerText !== ""){
                        e.cell.innerHTML = getfileSize(e.cell.innerText.replaceAll(',',''));
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("adminMediaPlaySeqCtrl", function(event, data) {
        $scope.searchMediaPlaySeq();
        event.preventDefault();
    });

    $scope.searchMediaPlaySeq = function(){
        var params = {};

        $scope._inquiryMain("/sys/admin/adminMedia/adminMediaPlaySeq/getMediaPlaySeqList.sb", params, function() { }, false);
    };
    // <-- //검색 호출 -->

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
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {
            var dispSeq = 1;

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                $scope.flex.collectionView.items[i].dispSeq = dispSeq;
                params.push($scope.flex.collectionView.items[i]);
                dispSeq++;
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/sys/admin/adminMedia/adminMediaPlaySeq/getMediaPlaySeqSaveUpdate.sb", params, function(){ $scope.allSearch() });
        });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchMediaPlaySeq();
    };
}]);