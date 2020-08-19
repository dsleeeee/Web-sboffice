/****************************************************************
 *
 * 파일명 : libraryDetail.js
 * 설  명 : 자료실 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2010.03.11     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  팝업 그리드 생성
 */
app.controller('libraryDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('libraryDetailCtrl', $scope, $http, true));

    // <-- 검색 호출 -->
    $scope.$on("libraryDetailCtrl", function(event, data) {
        if(userId !== data.userId) {
            $("#delButton").hide();
            $("#modifyButton").hide();
        } else if (userId === data.userId) {
            $("#delButton").show();
            $("#modifyButton").show();
        }
        $scope.setSelectedLibraryDetail(data);
        $scope.searchLibraryDetail();
        event.preventDefault();
    });

    $scope.searchLibraryDetail = function() {
        var params = {};
        params.boardCd = $scope.selectedLibraryDetail.boardCd;
        params.boardSeqNo = $scope.selectedLibraryDetail.boardSeqNo;

        $scope._postJSONQuery.withOutPopUp( "/adi/board/library/library/getLibraryDetailAtchList.sb", params, function(response){
            if($.isEmptyObject(response.data) ) {
                $scope._popMsg(messages["cmm.empty.data"]);
                return false;
            }

            var list = response.data.data;
            var innerHtml = "";

            $scope.libraryDetail = list[0];
            $scope.libraryDetail.title = $scope.libraryDetail.title;

            for(var i=0; i< list.length; i++) {

                innerHtml += "<tr>";
                if(i === 0) {
                    innerHtml += "<th rowspan='"+list.length+"'>"+messages["libraryDetail.file"]+"</th>";
                }
                innerHtml += "<td colspan='3'><a href=\"/adi/board/library/library/getLibraryDetailAtchDownload.sb?fileNm=" + list[i].fileNm + "&orginlFileNm=" + list[i].orginlFileNm + "&fileExt=" + list[i].fileExt + "\">" + list[i].orginlFileNm + "." + list[i].fileExt + "</a></td>";
                innerHtml += "</tr>";
            }

            $("#fileContent").html(innerHtml);
        });
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedLibraryDetail;
    $scope.setSelectedLibraryDetail = function(store) {
        $scope.selectedLibraryDetail = store;
    };
    $scope.getSelectedLibraryDetail = function(){
        return $scope.selectedLibraryDetail;
    };

    // 삭제
    $scope.del = function(){
        // 해당 자료를 삭제하시겠습니까?
        $scope._popConfirm(messages["libraryDetail.delConfirm"], function() {

            var params = {};
            params.boardCd = $scope.selectedLibraryDetail.boardCd;
            params.boardSeqNo = $scope.selectedLibraryDetail.boardSeqNo;
            params.status = "D";

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            // $scope._save("/adi/board/library/library/getLibraryInfoSave.sb", params, function(){ });
            $scope._postJSONSave.withPopUp("/adi/board/library/library/getLibraryInfoSave.sb", params, function () { $scope.close(); });
        });
    };

    // 수정
    $scope.modify = function(){
        $scope.wjLibraryInfoLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 자료실 신규,수정 팝업 핸들러 추가
        $scope.wjLibraryInfoLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('LibraryInfoCtrl', $scope.getSelectedLibraryDetail());
            }, 50)
        });
    });

    // 팝업 닫기
    $scope.close = function(){
        $scope.setSelectedLibraryDetail(null);
        $scope.wjLibraryDetailLayer.hide();

        // 재조회
        $scope._broadcast('libraryListCtrl');
    };

}]);