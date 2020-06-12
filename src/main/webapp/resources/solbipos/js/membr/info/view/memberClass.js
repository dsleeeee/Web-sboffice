/****************************************************************
 *
 * 파일명 : memberClass.js
 * 설  명 : 회원정보관리 > 회원등급설정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.06.09     이재영      1.0
 *
 * **************************************************************/
var app = agrid.getApp();
app.controller('memberClassCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberClassCtrl', $scope, $http, false));



    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("memberClass", memberClassList);

    $scope._getComboDataQuery('067', 'useYn', '');
    $scope._getComboDataQuery('067', 'membrDcYn', '');
    $scope._getComboDataQuery('067', 'defaultYn', '');
    $scope._getComboDataQuery('054', 'membrPointYn', '');
    $scope._getComboDataQuery('032', 'membrAnvsrYn', '');

    // 선택 회원
    $scope.selectedMember;
    $scope.setSelectedMember = function (member) {
        $scope.selectedMember = member;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.$apply(function () {
            $scope.data = new wijmo.collections.CollectionView(result);
        });
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "membrClassType") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });
        // 페이징???
        // if ($scope._getPagingInfo('curr') > 0) {
        //     params['curr'] = $scope._getPagingInfo('curr');
        // } else {
        //     params['curr'] = 1;
        // }

        // 등급목록
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 회원코드 클릭시 해당 기능 목록 조회
                if (col.binding === "membrClassType") {
                    var selectedData = s.rows[ht.row].dataItem
                    $scope.setSelectedMember(selectedData);
                    var scope = agrid.getScope('memberClassDetailCtrl');
                    scope._broadcast('memberClassDetailCtrl', $scope.getSelectedMember());

                    event.preventDefault();
                }
            }
        });
    };
    $scope.$on("memberClassCtrl", function (event, data) {
        $scope.setSelectedMember(data);
        $scope.detailData = data
        event.preventDefault();
    });
    // 신규등록
    $scope.newAdd = function () {
        $scope.detailData = {}
    };
    // 저장
    $scope.classSave = function () {
        var params = $scope.detailData;
        $scope._postJSONSave.withOutPopUp("/membr/info/grade/grade/classRegist.sb", params, function (response) {
            console.log(response);
            if (response.data.status == 'OK') {
                $scope._popMsg(messages["cmm.saveSucc"]);
            } else {
                $scope._popMsg(messages["cmm.saveFail"]);
                return false;
            }
        });
    };
    // 등급 삭제
    $scope.classDel = function(){
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                params.push($scope.flex.collectionView.items[i]);
            }
        }
        console.log(params);
        // 회원 사용여부 '미사용'으로 변경 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/membr/info/grade/grade/remove.sb", params, function(){ $scope.getMember() });

    };
    $scope.getMember = function() {
        $scope.data = new wijmo.collections.CollectionView(result);
    }
}]);

app.controller('memberClassDetailCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberClassDetailCtrl', $scope, $http, true));

    // 선택 기능 구분
    $scope.selectedMember;
    $scope.setSelectedMember = function (data) {
        $scope.selectedMember = data;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // bindColumnGroup 생성
        // bindColumnGroups(s, dataHeader);

        // ReadOnly 효과설정
        // s.formatItem.addHandler(function (s, e) {
        //     if (e.panel === s.cells) {
        //         var col = s.columns[e.col];
        //         if (col.binding === "fnkeyNo") {
        //             wijmo.addClass(e.cell, 'wijLink');
        //         }
        //     }
        // });
    };
    $scope.$on("memberClassDetailCtrl", function (event, data) {
        $scope.setSelectedMember(data);
        $scope.getClassList();
        event.preventDefault();
    });
    // 기능 목록 조회
    $scope.getClassList = function () {

        var params = {};
        params.membrClassCd = $scope.getSelectedMember().membrClassCd;
        params.membrOrgnCd = $scope.getSelectedMember().membrOrgnCd;

        $scope.$broadcast('loadingPopupActive');
        // ajax 통신 설정
        $http({
            method: 'POST', //방식
            url: '/membr/info/grade/view/getMemberClassDetail.sb', /* 통신할 URL */
            params: params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            var list = response.data.data.list.mcp;
            var detail = response.data.data.list.mcd;
            if (list.length === undefined || list.length === 0) {
                $scope.data = new wijmo.collections.CollectionView([]);
            } else {
                var data = new wijmo.collections.CollectionView(list);
                data.trackChanges = true;
                $scope.data = data;
            }
            var classScope = agrid.getScope('memberClassCtrl');
            classScope._broadcast('memberClassCtrl', $scope.getSelectedMember(detail));
            $("button").show();

        }, function errorCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.error']);
            }
            return false;
        }).then(function () {
            // 'complete' code here
            if (typeof callback === 'function') {
                setTimeout(function () {
                    callback();
                }, 10);
            }
        });
    };

    // // 위로 옮기기
    // $scope.up = function(){
    //     var movedRows = 0;
    //     for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
    //         var item = $scope.flex.collectionView.items[i];
    //         if (i > 0 && item.gChk) {
    //             if (!$scope.flex.collectionView.items[i - 1].gChk) {
    //                 movedRows = i - 1;
    //                 var tmpItem = $scope.flex.collectionView.items[movedRows];
    //                 $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
    //                 $scope.flex.collectionView.items[i] = tmpItem;
    //                 $scope.flex.collectionView.commitEdit();
    //                 $scope.flex.collectionView.refresh();
    //             }
    //         }
    //     }
    //     $scope.flex.select(movedRows, 1);
    // };
    //
    // // 아래로 옮기기
    // $scope.down = function(){
    //     var movedRows = 0;
    //     for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
    //         var item = $scope.flex.collectionView.items[i];
    //         if ((i < $scope.flex.itemsSource.itemCount - 1) && item.gChk) {
    //             if (!$scope.flex.collectionView.items[i + 1].gChk) {
    //                 movedRows = i + 1;
    //                 var tmpItem = $scope.flex.collectionView.items[movedRows];
    //                 $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
    //                 $scope.flex.collectionView.items[i] = tmpItem;
    //                 $scope.flex.collectionView.commitEdit();
    //                 $scope.flex.collectionView.refresh();
    //             }
    //         }
    //     }
    //     $scope.flex.select(movedRows, 1);
    // };
    //
    // // 행 추가
    // $scope.add = function(){
    //     // 파라미터 설정
    //     var params = {};
    //     params.fnkeyNo = '자동채번';
    //     params.posiAdjYn = false;
    //     params.fnkeyUseYn0 = false;
    //     params.fnkeyUseYn1 = false;
    //     params.useYn = true;
    //     // 추가기능 수행 : 파라미터
    //     $scope._addRow(params, 2);
    // };
    //
    // // 행 삭제
    // $scope.del = function(){
    //     for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
    //         var item = $scope.flex.collectionView.items[i];
    //         if(item.gChk){
    //             $scope.flex.collectionView.removeAt(i);
    //         }
    //     }
    // };
    //
    // // 저장
    // $scope.save = function(){
    //     $scope.flex.collectionView.commitEdit();
    //
    //     // 파라미터 설정
    //     var params = [];
    //
    //     for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
    //         $scope.flex.collectionView.itemsRemoved[d].status = 'D';
    //         $scope.flex.collectionView.itemsRemoved[d].fnkeyFg = $scope.getSelectedFuncFg().nmcodeCd;
    //         params.push($scope.flex.collectionView.itemsRemoved[d]);
    //     }
    //
    //     // dispSeq 재설정
    //     var editItems = [];
    //     for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
    //         if( isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
    //             editItems.push($scope.flex.collectionView.items[s]);
    //         }
    //     }
    //
    //     for (var s = 0; s < editItems.length; s++) {
    //         editItems[s].dispSeq = (s + 1);
    //         $scope.flex.collectionView.editItem(editItems[s]);
    //         $scope.flex.collectionView.commitEdit();
    //     }
    //
    //     for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
    //         $scope.flex.collectionView.itemsEdited[u].status = 'U';
    //         $scope.flex.collectionView.itemsEdited[d].fnkeyFg = $scope.getSelectedFuncFg().nmcodeCd;
    //         params.push($scope.flex.collectionView.itemsEdited[u]);
    //     }
    //     for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
    //         $scope.flex.collectionView.itemsAdded[i].status = 'I';
    //         $scope.flex.collectionView.itemsAdded[d].fnkeyFg = $scope.getSelectedFuncFg().nmcodeCd;
    //         params.push($scope.flex.collectionView.itemsAdded[i]);
    //     }
    //
    //     // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    //     $scope._save('/pos/confg/func/func/save.sb', params, function() {
    //         // 저장 후 재조회
    //         $scope._broadcast('funcCtrl', $scope.getSelectedFuncFg());
    //     });
    // };

}]);