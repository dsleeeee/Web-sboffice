/****************************************************************
 *
 * 파일명 : menuRankDisplay.js
 * 설  명 : 메뉴 순위 표시 관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.08.06     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

function registStore(nmcodeCd, nmcodeNm){
    var scope = agrid.getScope('menuRankDisplayCtrl');
    scope.registStoreCnt(nmcodeCd, nmcodeNm);
}

app.controller('menuRankDisplayCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('menuRankDisplayCtrl', $scope, $http, true));

    $scope.$on("menuRankDisplayCtrl", function(event, data) {
        // 메뉴 순위 표시 사용여부 조회
        $scope.searchUseRank();
        event.preventDefault();
    });

    // 메뉴 순위 표시 사용여부 조회
    $scope.searchUseRank = function () {

        var params = {};

        $scope._postJSONQuery.withOutPopUp("/base/prod/menuRank/display/getRankInfo.sb", params, function (response) {

            var list = response.data.data.list;
            var innerHtml = "";

            if (list.length > 0) {

                innerHtml += "<table class='tblType01 mt20'>";
                innerHtml += "<colgroup><col width='8%' /><col width='52%' /><col width='20%' />";
                innerHtml += "<col width='20%' />";
                innerHtml += "</colgroup><tbody>";
                innerHtml += "<tr>";
                innerHtml += "<th style='text-align: center;'>" + messages["menuRank.num"] + "</th>";
                innerHtml += "<th style='text-align: center;'>" + messages["menuRank.displayContent"] + "</th>";

                // 본사
                if(orgnFg != null && orgnFg == 'HQ') {
                    innerHtml += "<th style='text-align: center;'>" + messages["menuRank.storeCnt"] + "</th>";
                }

                // 매장
                if(orgnFg != null && orgnFg == 'STORE') {
                    innerHtml += "<th style='text-align: center;'>" + messages["menuRank.displayYn"] + "</th>";
                }

                innerHtml += "</tr>"

                for (var i = 0; i < list.length; i++) {

                    innerHtml += "<tr>";
                    innerHtml += "<td style='height:35px;' align='center'>" + (i+1) + "</td>";
                    innerHtml += "<td align='center'>" + list[i].nmcodeNm + "</td>";

                    // 본사 - 사용 매장수
                    if(orgnFg != null && orgnFg == 'HQ') {
                        innerHtml += "<td align='center'>";
                        innerHtml += "<a href='#' onclick='registStore(\"" + list[i].nmcodeCd + "\",\"" + list[i].nmcodeNm + "\")'>" + list[i].storeCnt + "</a>";
                        innerHtml += "</td>";
                    }

                    // 매장 - 메뉴순위표시 사용여부 체크박스
                    if(orgnFg != null && orgnFg == 'STORE') {

                        innerHtml += "<td align='center'>";
                        innerHtml += "<input type='checkBox' id='rank_" + list[i].nmcodeCd + "' ";

                        if (list[i].useYn === "Y") { innerHtml += "checked"; }

                        innerHtml += " >";
                        innerHtml += "<input type='hidden' name='nmcodeCd' value='"+ list[i].nmcodeCd +"'>";
                        innerHtml += "<input type='hidden' name='nmcodeNm' value='"+ list[i].nmcodeNm +"'>";
                    }

                    innerHtml += "</td>";
                    innerHtml += "</tr>";
                }

                innerHtml += "</tbody>";
                innerHtml += "</table>";
            }

            $("#divRank").html(innerHtml);
        });
    };

    // 메뉴 순위 표시 사용여부 저장(매장권한에서만 사용)
    $scope.rankUseSave = function () {

        var objNmcodeCd = document.getElementsByName("nmcodeCd");
        var objNmcodeNm = document.getElementsByName("nmcodeNm");
        var paramArr = [];

        for(var i=0; i<objNmcodeCd.length; i++){

            var param = {};
            param.nmcodeCd = objNmcodeCd[i].value;
            param.nmcodeNm = objNmcodeNm[i].value;

            if($("#rank_" + objNmcodeCd[i].value).prop("checked")){
                param.useYn = "Y";
            }else{
                param.useYn = "N";
            }

            paramArr.push(param);
        }

        $.postJSONArray("/base/prod/menuRank/display/saveRankUse.sb", paramArr, function(result) {
            s_alert.pop(messages["cmm.saveSucc"]);

            // 재조회
            $scope.searchUseRank();

        },
        function(result) {
                s_alert.pop(result.message);
        });

    }

    // 사용매장 수 클릭 시
    $scope.registStoreCnt = function(nmcodeCd, nmcodeNm) {

        $scope.regMenuRankStoreLayer.show(true);

        var params = {};
        params.nmcodeCd = nmcodeCd;
        params.nmcodeNm = nmcodeNm;

        $scope._broadcast('regMenuRankStoreCtrl', params);
    }

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {
        $scope.searchUseRank();
    });

}]);