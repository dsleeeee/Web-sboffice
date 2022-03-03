<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup control="promotionExampleLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
    <div class="wj-dialog wj-dialog-columns" ng-controller="promotionExampleCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="promotion.promotion"/>&nbsp;<s:message code="promotion.example"/>
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <div class="wj-dialog-body">

            <div class="divBar mt10" id="ex1" onclick="divFldUnfld('ex1')">
                <a href="#" class="open">
                    (사례1) 2022년 3월1일부터 3월31일까지 모든 고객을 대상으로 구매 금액의 15%를 할인해 주는 행사
                </a>
            </div>
            <div class="w100" id="ex1Div">
                <img src="/resource/solbipos/css/img/promotion/example/ex1.JPG" style="width:100%" alt="ex1" />
            </div>

            <div class="divBar mt10" id="ex2" onclick="divFldUnfld('ex2')">
                <a href="#" class="open">
                    (사례2) 매주 수요일 오전 10:30 부터 11:30 사이에 구매시 구매 금액의 20% 할인
                </a>
            </div>
            <div class="w100" id="ex2Div">
                <img src="/resource/solbipos/css/img/promotion/example/ex2.JPG" style="width:100%" alt="ex2" />
            </div>

            <div class="divBar mt10" id="ex3" onclick="divFldUnfld('ex3')">
                <a href="#" class="open">
                    (사례3) 10만원 이상 구매시 구매 금액의 10% 할인, 20만원 이상 구매시 구매금액의 20% 할인, 30만원 이상 구매시 구매금액의 30%할인
                </a>
            </div>
            <div class="w100" id="ex3Div">
                <img src="/resource/solbipos/css/img/promotion/example/ex3.JPG" style="width:100%" alt="ex3" />
            </div>

            <div class="divBar mt10" id="ex4" onclick="divFldUnfld('ex4')">
                <a href="#" class="open">
                    (사례4) 골드회원 10% 할인, 플래티넘 회원 20% 할인, VIP 회원 30%할인
                </a>
            </div>
            <div class="w100" id="ex4Div">
                <img src="/resource/solbipos/css/img/promotion/example/ex4.JPG" style="width:100%" alt="ex4" />
            </div>

            <div class="divBar mt10" id="ex5" onclick="divFldUnfld('ex5')">
                <a href="#" class="open">
                    (사례5) 0100500003(올리브치킨) 1개 주문시 해당 메뉴 3000원 할인
                </a>
            </div>
            <div class="w100" id="ex5Div">
                <img src="/resource/solbipos/css/img/promotion/example/ex5.JPG" style="width:100%" alt="ex5" />
            </div>

            <div class="divBar mt10" id="ex6" onclick="divFldUnfld('ex6')">
                <a href="#" class="open">
                    (사례6) 0200500001(불고기피자)와 0200700002(고구마피자) 각각 1개씩 2개 주문시 해당 메뉴 10% 할인
                </a>
            </div>
            <div class="w100" id="ex6Div">
                <img src="/resource/solbipos/css/img/promotion/example/ex6.JPG" style="width:100%" alt="ex6" />
            </div>

            <div class="divBar mt10" id="ex7" onclick="divFldUnfld('ex7')">
                <a href="#" class="open">
                    (사례7) 0200500001(불고기피자)와 0200700002(고구마피자) 중 아무거나 1개 주문시 해당 메뉴 2000원 할인
                </a>
            </div>
            <div class="w100" id="ex7Div">
                <img src="/resource/solbipos/css/img/promotion/example/ex7.JPG" style="width:100%" alt="ex7" />
            </div>

            <div class="divBar mt10" id="ex8" onclick="divFldUnfld('ex8')">
                <a href="#" class="open">
                    (사례8) 0200900011(쉬림프 피자) 주문시 0202100011(베이컨 파스타) 50% 할인
                </a>
            </div>
            <div class="w100" id="ex8Div">
                <img src="/resource/solbipos/css/img/promotion/example/ex8.JPG" style="width:100%" alt="ex8" />
            </div>

            <div class="divBar mt10" id="ex9" onclick="divFldUnfld('ex9')">
                <a href="#" class="open">
                    (사례9) 총 구매금액 3만원 이상 주문시 0202100011(베이컨 파스타) 50% 할인
                </a>
            </div>
            <div class="w100" id="ex9Div">
                <img src="/resource/solbipos/css/img/promotion/example/ex9.JPG" style="width:100%" alt="ex9" />
            </div>

            <div class="divBar mt10" id="ex10" onclick="divFldUnfld('ex10')">
                <a href="#" class="open">
                    (사례10) 0200900011(쉬림프 피자) 1판 주문시 0302100001(콜라) 1개 증정
                </a>
            </div>
            <div class="w100" id="ex10Div">
                <img src="/resource/solbipos/css/img/promotion/example/ex10.JPG" style="width:100%" alt="ex10" />
            </div>

            <div class="divBar mt10" id="ex11" onclick="divFldUnfld('ex11')">
                <a href="#" class="open">
                    (사례11) 0200300001~5(나뚜르 종류 아이스크림)중 아무거나 2개 구매시 0200300001(나뚜르 그린티) 1개 더 증정 (2+1)
                </a>
            </div>
            <div class="w100" id="ex11Div">
                <img src="/resource/solbipos/css/img/promotion/example/ex11.JPG" style="width:100%" alt="ex11" />
            </div>

            <div class="divBar mt10" id="ex12" onclick="divFldUnfld('ex12')">
                <a href="#" class="open">
                    (사례12) 0200300001(나뚜르 그린티) 1개 구매시 0200400001~5(티백 종류) 아무거나 1개 선택 증정
                </a>
            </div>
            <div class="w100" id="ex12Div">
                <img src="/resource/solbipos/css/img/promotion/example/ex12.JPG" style="width:100%" alt="ex12" />
            </div>

            <div class="divBar mt10" id="ex13" onclick="divFldUnfld('ex13')">
                <a href="#" class="open">
                    (사례13) 0201900011(실론티 후라이팬)+0201900012(실론티 냄비) 구매시 특별가 59,700원으로 판매
                </a>
            </div>
            <div class="w100" id="ex13Div">
                <img src="/resource/solbipos/css/img/promotion/example/ex13.JPG" style="width:100%" alt="ex13" />
            </div>

        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/promotion/promotion/promotionExample.js?ver=20220303.01"
        charset="utf-8"></script>