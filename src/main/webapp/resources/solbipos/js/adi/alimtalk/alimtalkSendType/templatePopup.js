/****************************************************************
 *
 * 파일명 : templatePopup.js
 * 설  명 : 템플릿 선택 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.15     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 템플릿 목록 선택
function templateChoice(templateGrpFg, templateCd, templateNm, templateContent) {
    var scope = agrid.getScope('templatePopupCtrl');
    var params = {};
    params.templateGrpFg = templateGrpFg;
    params.templateCd = templateCd;
    params.templateNm = templateNm;
    params.templateContent = templateContent;
    scope.templateChoice(params);
}

/**
 *  템플릿 조회 그리드 생성
 */
app.controller('templatePopupCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('templatePopupCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("templatePopupCtrl", function(event, data) {
        $scope.searchTemplatePopup(data);
        event.preventDefault();
    });

    $scope.searchTemplatePopup = function(params) {
        $("#divTemplateCommentList").html("");

        $scope._postJSONQuery.withOutPopUp("/adi/alimtalk/alimtalkSendType/templatePopup/getAlimtalkSendTypeDetailTemplateList.sb", params, function(response) {
            var list = response.data.data.list;
            var innerHtml = "";

            if(list.length > 0) {
                for(var i=0; i < list.length; i++) {
                    innerHtml += "<div style=\"float:left; text-align:center; width:205px; height:270px; padding-top:10px; padding-right:10px;\">";
                    innerHtml += "<table>";
                    innerHtml += "<colgroup>";
                    innerHtml += "<col class=\"w100\" />";
                    innerHtml += "</colgroup>";
                    innerHtml += "<tbody>";
                    innerHtml += "<table>";
                    innerHtml += "<tr><td><input type=\"text\" class=\"sb-input-alk w100\" value=\""+ list[i].commonFgNm +"\" disabled/></td></tr>";
                    innerHtml += "<tr style=\"height: 10px\"></tr>";
                    innerHtml += "<tr><td><input type=\"text\" class=\"sb-input-msg w100\" value=\""+ list[i].templateNm +"\" readonly/></td></tr>";
                    innerHtml += "<tr style=\"height: 10px\"></tr>";
                    // innerHtml += "<tr><td><textarea style=\"width:100%; height:160px; overflow-x:hidden; background-color: #EAF7FF\" onclick=\"templateChoice(\'"+ list[i].templateGrpFg + "\', \'"+ list[i].templateCd + "\', \'"+ list[i].templateNm + "\', \'"+ list[i].templateContent.replaceAll("\n", "\\n") + "\')\" readonly>" + list[i].templateContent + "</textarea></td></tr>";
                    innerHtml += "<tr><td><textarea style=\"width:100%; height:160px; overflow-x:hidden; background-color: #EAF7FF\" readonly>" + list[i].templateContent + "</textarea></td></tr>";
                    innerHtml += "<tr style=\"height: 5px\"></tr>";
                    innerHtml += "<tr><td><button class=\"btn_skyblue\" onclick=\"templateChoice(\'"+ list[i].templateGrpFg + "\', \'"+ list[i].templateCd + "\', \'"+ list[i].templateNm + "\', \'"+ list[i].templateContent.replaceAll("\n", "\\n") + "\')\">" + '선택' + "</button></td></tr>";
                    innerHtml += "</table>";
                    innerHtml += "</div>";
                }
                $("#divTemplateCommentList").html(innerHtml);
            } else {
                innerHtml += "<div style=\"float:left; text-align:center; width:225px; height:190px; padding-top:10px; padding-right:10px;\">";
                innerHtml += "<table>";
                innerHtml += "<colgroup>";
                innerHtml += "<col class=\"w100\" />";
                innerHtml += "</colgroup>";
                innerHtml += "<tbody>";
                innerHtml += "<table>";
                innerHtml += "<tr style=\"height: 20px\"></tr>";
                innerHtml += "<tr><td>"+'- 등록된 템플릿이 없습니다.'+"</td></tr>";
                innerHtml += "</table>";
                innerHtml += "</div>";
                $("#divTemplateCommentList").html(innerHtml);
            }
        }, false);
    };
    // <-- //검색 호출 -->

    // 템플릿 목록 선택
    $scope.templateChoice = function (data) {
        var scope = agrid.getScope('alimtalkSendTypeTemplateCtrl');
        scope.templateChoiceChange(data);

        $scope.wjTemplatePopupLayer.hide();
        event.preventDefault();
    };
}]);