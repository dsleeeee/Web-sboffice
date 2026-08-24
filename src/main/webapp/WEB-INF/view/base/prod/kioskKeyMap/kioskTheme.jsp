<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<%-- 프리뷰 이미지 리소스 경로 --%>
<c:set var="imgBase" value="/resource/solbipos/css/img/kioskTheme"/>

<style>
  /* 프리뷰 전용 스타일 (전체 페이지 오염 방지를 위해 #kioskThemePreview 로 스코프) */
  #kioskThemePreview { display:flex; flex-direction:column; align-items:flex-start; }
  #kioskThemePreview #viewport { width:346px; height:614px; overflow:hidden; border-radius:16px; border:1px solid #ddd; position:relative; cursor:pointer; box-shadow:0 4px 16px rgba(0,0,0,0.08); }
  #kioskThemePreview .slide { width:1080px; height:1920px; transform:scale(0.32); transform-origin:top left; position:relative; background:#fff; display:none; flex-direction:column; }
  #kioskThemePreview .slide.active { display:flex; }
  #kioskThemePreview #nav { display:flex; align-items:center; justify-content:center; gap:16px; margin-top:14px; width:346px; }
  #kioskThemePreview #dots { display:flex; gap:6px; }
  #kioskThemePreview .dot { width:7px; height:7px; border-radius:50%; background:#ccc; cursor:pointer; }
  #kioskThemePreview .dot.active { background:#333; }
  #kioskThemePreview #nav button { border:none; background:#eee; width:36px; height:36px; border-radius:50%; cursor:pointer; font-size:16px; }
  #kioskThemePreview #stepLabel { font-size:12px; color:#888; margin-top:6px; width:346px; text-align:center; }
  #kioskThemePreview img { display:block; }
  #kioskThemePreview .maskIcon { -webkit-mask-size:contain; mask-size:contain; -webkit-mask-repeat:no-repeat; mask-repeat:no-repeat; -webkit-mask-position:center; mask-position:center; }
  #kioskThemePreview .numBtn { border-radius:50%; display:flex; align-items:center; justify-content:center; flex-shrink:0; cursor:pointer; }
  #kioskThemePreview .customScroll { scrollbar-width: thin; scrollbar-color: transparent transparent; }
  #kioskThemePreview .customScroll::-webkit-scrollbar { width: 6px; height: 6px; }
  #kioskThemePreview .customScroll::-webkit-scrollbar-thumb { background: transparent; border-radius: 10px; }
  #kioskThemePreview .customScroll::-webkit-scrollbar-track { background: transparent; }
  #kioskThemePreview .customScroll:hover::-webkit-scrollbar-thumb { background: rgba(0,0,0,0.25); }
  #kioskThemePreview .customScroll:hover { scrollbar-color: rgba(0,0,0,0.25) transparent; }
</style>

<div id="kioskThemeView" name="kioskThemeView" class="subCon" style="display: none;padding: 10px 20px 40px;">

    <div ng-controller="kioskThemeCtrl" ng-init="init()">

        <%-- 조회조건 (우상단 저장 버튼) --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="kioskKeyMap.kioskTheme"/></a>
            <%-- 저장 --%>
            <button class="btn_blue fr mt5 mr10" id="btnSaveTheme" ng-click="saveTheme()"><s:message code="cmm.save"/></button>
        </div>

        <table class="searchTbl">
            <colgroup>
                <col class="w10" />
                <col class="w90" />
            </colgroup>
            <tbody>
            <tr>
                <%-- 컬러테마 --%>
                <th><s:message code="kioskKeyMap.colorTheme"/></th>
                <td>
                    <div style="display:flex; align-items:center;">
                        <div class="sb-select" style="width:200px;">
                            <wj-combo-box
                                    id="colorTheme"
                                    ng-model="colorTheme"
                                    items-source="themeComboList"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="colorThemeCombo"
                                    selected-index-changed="changeTheme(s)">
                            </wj-combo-box>
                        </div>
                        <%-- 컬러 예시 (선택 테마 대표색) --%>
                        <span id="colorThemeSwatch" style="width:16px; height:16px; margin-left:10px; border:1px solid #ccc; border-radius:3px; flex-shrink:0;"></span>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>

        <%-- 프리뷰 화면(왼쪽) + 안내문구(오른쪽) --%>
        <div class="mt20" style="display:flex; align-items:flex-start; gap:30px;">
        <div id="kioskThemePreview">
            <div id="viewport">

                <%-- ① 인트로 --%>
                <div class="slide active" data-i="0">
                    <div style="height:1200px; position:relative; overflow:hidden;">
                        <img src="${imgBase}/sample_intro.png" style="width:100%;height:100%;object-fit:cover;">
                    </div>
                    <div style="background:#f0f0f0; padding:20px 0; display:flex; gap:14px; align-items:center; justify-content:center;">
                        <span style="background:#212121; color:#fff; padding:15px 20px; border-radius:100px; font-size:28px;"><img src="${imgBase}/ic_flag_korea.png" style="width:34px; height:34px; display:inline-block; position:relative ; top:4px;margin-right:10px" /><span style="position:relative; top:-2px;">한국어</span></span>
                        <span style="color:#212121; font-size:28px; padding:15px 20px;"><img src="${imgBase}/ic_flag_english.png" style="width:34px; height:34px; display:inline-block; position:relative ; top:4px;margin-right:10px" /><span style="position:relative; top:-2px;">English</span></span>
                        <span style="color:#212121; font-size:28px; padding:15px 20px;"><img src="${imgBase}/ic_flag_china.png" style="width:34px; height:34px; display:inline-block; position:relative ; top:4px;margin-right:10px" /><span style="position:relative; top:-2px;">中國語</span></span>
                        <span style="color:#212121; font-size:28px; padding:15px 20px;"><img src="${imgBase}/ic_flag_japan.png" style="width:34px; height:34px; display:inline-block; position:relative ; top:4px;margin-right:10px" /><span style="position:relative; top:-2px;">日本語</span></span>
                    </div>
                    <div style="flex:1; display:flex; flex-direction:column; align-items:center; justify-content:center; gap:50px;">
                        <div style="text-align:center;">
                            <p style="font-size:46px; font-weight:700; color:#212121; margin:0;">카드전용 주문기기 입니다.</p>
                            <p style="font-size:28px; color:#212121; margin:10px 0 0;">현금 및 기타 결제는 카운터에서 진행해 주세요.</p>
                        </div>
                        <div style="display:flex; gap:50px; padding:0 115px; width:100%; box-sizing:border-box;">
                            <div class="tk-primary" style="flex:1; height:230px; border-radius:30px; display:flex; align-items:center; justify-content:center;"><span class="tk-btnText" style="font-size:58px; font-weight:700;">매장</span></div>
                            <div class="tk-primary" style="flex:1; height:230px; border-radius:30px; display:flex; align-items:center; justify-content:center;"><span class="tk-btnText" style="font-size:58px; font-weight:700;">포장</span></div>
                        </div>
                    </div>
                </div>

                <%-- ② 메인 --%>
                <div class="slide" data-i="1">
                    <div style="height:110px; background:#fff; display:flex; align-items:center; justify-content:space-between; padding-right:15px; position:relative;">
                        <div style="height:100%; display:flex; align-items:center; padding:0 25px;">
                            <img src="${imgBase}/ic_home.png" style="width:54px;height:54px;">
                        </div>
                        <div style="position:absolute; left:50%; top:50%; transform:translate(-50%,-50%);"><span class="tk-icnText" style="font-size:44px; font-weight:700;">링크</span></div>
                        <div style="display:flex; flex-direction:column; gap:10px; align-items:flex-end;">
                            <p style="font-size:18px; color:#212121; margin:0;">남은시간 : 178초</p>
                            <div style="display:flex; gap:10px;">
                                <span style="background:#212121; color:#fff; padding:2px 15px; border-radius:10px; font-size:26px;">매장</span>
                                <span style="background:#e1e1e1; color:#212121; padding:2px 15px; border-radius:10px; font-size:26px;">포장</span>
                            </div>
                        </div>
                    </div>
                    <div style="width:100%; overflow-x:auto;" class="tk-primary customScroll">
                        <div class="tk-primary" style="display:flex; align-items:center; padding:30px 40px; gap:0;">
                            <div style="background:#fff; height:80px; width:210px; display:flex; align-items:center; justify-content:center; border-radius:15px; flex-shrink:0;"><p class="tk-icnText" style="font-size:28px; font-weight:700; margin:0;">인기메뉴</p></div>
                            <div style="height:80px; width:210px; display:flex; align-items:center; justify-content:center; flex-shrink:0;"><p class="tk-btnText" style="font-size:28px; font-weight:700; margin:0;">떡볶이</p></div>
                            <div style="height:80px; width:210px; display:flex; align-items:center; justify-content:center; flex-shrink:0;"><p class="tk-btnText" style="font-size:28px; font-weight:700; margin:0;">떡닭피세트</p></div>
                            <div style="height:80px; width:210px; display:flex; align-items:center; justify-content:center; flex-shrink:0;"><p class="tk-btnText" style="font-size:28px; font-weight:700; margin:0;">커피</p></div>
                            <div style="height:80px; width:210px; display:flex; align-items:center; justify-content:center; flex-shrink:0;"><p class="tk-btnText" style="font-size:28px; font-weight:700; margin:0;">팥빙수</p></div>
                            <div style="height:80px; width:210px; display:flex; align-items:center; justify-content:center; flex-shrink:0;"><p class="tk-btnText" style="font-size:28px; font-weight:700; margin:0;">어린이음료</p></div>
                        </div>
                    </div>
                    <div style="flex:1; min-height:0; overflow-y:auto; background:#fff;" class="customScroll">
                        <div id="goodsGrid" style="display:grid; grid-template-columns:repeat(4,1fr); gap:30px 20px; padding:50px 40px 100px;"></div>
                    </div>
                    <div style="position:relative;">
                        <span class="tk-icnText tk-primaryBg7" style="position:absolute; top:-37px; right:40px; padding:15px 25px; border-radius:100px; font-size:26px; font-weight:700;">+ 영양정보</span>
                        <div style="background:#fff; padding:0 40px;"><div style="background:#f0f0f0; width:200px; margin-left:45px; padding:10px 5px; border-radius:15px 15px 0 0; text-align:center;"><p style="font-size:20px; font-weight:700; color:#212121; margin:0;">주문상품 0</p></div></div>
                        <div style="background:#f0f0f0; height:250px; display:flex; flex-direction:column; align-items:center; justify-content:center; gap:10px;"><i class="ti ti-shopping-cart" style="font-size:80px; color:#ccc;"></i><p style="font-size:46px; color:#ccc; margin-top:0;"><img src="${imgBase}/ic_basket.svg" style="width:100px; height:100px; margin:10px auto">메뉴를 선택해 주세요.</p></div>
                    </div>
                    <div style="height:135px; display:flex; align-items:stretch;">
                        <div style="flex:1; display:flex; flex-direction:column; align-items:flex-end; justify-content:center; gap:10px; padding-right:20px;"><p style="font-size:26px; font-weight:700; color:#212121; margin:0;">총 수량</p><p style="font-size:46px; font-weight:700; color:#212121; margin:0;">0</p></div>
                        <div style="flex:1; display:flex; flex-direction:column; align-items:flex-end; justify-content:center; gap:10px; padding-right:20px;"><p style="font-size:26px; font-weight:700; color:#212121; margin:0;">총 금액</p><p class="tk-priceText" style="font-size:46px; font-weight:700; margin:0;">0</p></div>
                        <div class="tk-primaryBg7" style="width:130px; display:flex; align-items:center; justify-content:center;">
                            <div class="tk-icnText maskIcon" style="width:64px;height:64px;-webkit-mask-image:url('${imgBase}/ic_delete.svg');mask-image:url('${imgBase}/ic_delete.svg');background-color:currentColor;"></div>
                        </div>
                        <div class="tk-primary" style="width:360px; display:flex; align-items:center; justify-content:center;"><p class="tk-btnText" style="font-size:46px; font-weight:700; margin:0;">결제하기</p></div>
                    </div>
                </div>

                <%-- ③ 옵션선택 --%>
                <div class="slide" data-i="2">
                    <div class="tk-primary" style="height:110px; display:flex; align-items:center; justify-content:space-between; padding-right:15px; position:relative; flex-shrink:0;">
                        <div style="height:100%; display:flex; align-items:center; padding:0 25px; gap:8px;"><i class="ti ti-arrow-left tk-btnText" style="font-size:44px;"></i><img src="${imgBase}/ic_back.svg" style="height:54px; width:54px;"><span class="tk-btnText" style="font-size:28px; font-weight:700;">이전</span></div>
                        <div style="position:absolute; left:50%; top:50%; transform:translate(-50%,-50%);"><span class="tk-btnText" style="font-size:44px; font-weight:700;">링크</span></div>
                        <div style="display:flex; flex-direction:column; gap:10px; align-items:flex-end;">
                            <p class="tk-btnText" style="font-size:18px; margin:0;">남은시간 : 178초</p>
                            <div style="display:flex; gap:10px;"><span class="tk-icnText" style="background:#fff; padding:2px 15px; border-radius:10px; font-size:26px;">매장</span><span class="tk-btnText" style="background:rgba(0,0,0,0.15); padding:2px 15px; border-radius:10px; font-size:26px;">포장</span></div>
                        </div>
                    </div>
                    <div style="background:#f0f0f0; padding:40px; display:flex; gap:30px; align-items:flex-start; flex-shrink:0;">
                        <div style="width:230px; height:230px; flex-shrink:0; border:1px solid #e1e1e1; border-radius:30px; overflow:hidden;"><img src="${imgBase}/sample_goods1.png" style="width:100%;height:100%;object-fit:cover;"></div>
                        <div style="flex:1;">
                            <p style="font-size:28px; font-weight:700; color:#212121; margin:0; line-height:1.3;">아이스 아메리카노 세트 (사이드 메뉴 선택 가능)</p>
                            <p style="font-size:28px; color:#212121; margin:10px 0 0; line-height:1.4;">시원하고 진한 에스프레소에 얼음을 더한 아이스 아메리카노와 함께 즐기는 사이드 메뉴 세트입니다.</p>
                            <div style="display:flex; align-items:center; gap:16px; margin-top:16px;">
                                <div class="numBtn" style="width:70px;height:70px;border:2px solid #e1e1e1;">
                                    <img src="${imgBase}/minus2.png" style="width:28px;height:6px;">
                                </div>
                                <span style="font-size:46px; font-weight:700; color:#212121;">1</span>
                                <div class="numBtn tk-primary" style="width:70px;height:70px;">
                                    <img src="${imgBase}/plus2.png" style="width:28px;height:28px;">
                                </div>
                                <span class="tk-priceText" style="font-size:46px; font-weight:700;">20,600</span>
                                <span style="font-size:22px; color:#999; margin-left:auto;">Total 1,308 kcal</span>
                            </div>
                        </div>
                    </div>
                    <div class="tk-primaryBg7" style="height:70px; display:flex; align-items:center; gap:14px; padding:0 40px; flex-shrink:0;">
                        <span class="tk-primary tk-btnText" style="font-size:22px; font-weight:700; padding:4px 16px; border-radius:100px;">필수</span>
                        <span style="font-size:28px; font-weight:700; color:#212121; flex:1;">사이드 메뉴</span>
                        <span style="font-size:28px; color:#212121;">(선택 가능 수량 : 8)</span>
                    </div>
                    <div id="optionGrid" style="flex:1; overflow-y:auto; padding:30px 40px; display:grid; grid-template-columns:repeat(4,1fr); gap:20px; align-content:start;" class="customScroll"></div>
                    <div style="flex-shrink:0;">
                        <div style="background:#fff; padding:0 40px;"><div style="background:#f0f0f0; width:200px; margin-left:45px; padding:10px 5px; border-radius:15px 15px 0 0; text-align:center;"><p style="font-size:20px; font-weight:700; color:#212121; margin:0;">주문상품 0</p></div></div>
                        <div style="background:#f0f0f0; height:250px; display:flex; flex-direction:column; align-items:center; justify-content:center; gap:10px;"><i class="ti ti-shopping-cart" style="font-size:80px; color:#ccc;"></i><p style="font-size:46px; color:#ccc; margin-top:0;"><img src="${imgBase}/ic_basket.svg" style="width:100px; height:100px; margin:10px auto">메뉴를 선택해 주세요.</p></div>
                    </div>
                    <div style="height:135px; display:flex; align-items:stretch; flex-shrink:0;">
                        <div style="flex:1; display:flex; flex-direction:column; align-items:flex-end; justify-content:center; gap:8px; padding-right:20px;"><p style="font-size:26px; font-weight:700; color:#212121; margin:0;">옵션 총 수량</p><p style="font-size:46px; font-weight:700; color:#212121; margin:0;">+0</p></div>
                        <div style="flex:1; display:flex; flex-direction:column; align-items:flex-end; justify-content:center; gap:8px; padding-right:20px;"><p style="font-size:26px; font-weight:700; color:#212121; margin:0;">옵션 총 금액</p><p class="tk-priceText" style="font-size:46px; font-weight:700; margin:0;">+0</p></div>
                        <div class="tk-primaryBg7" style="width:130px; display:flex; align-items:center; justify-content:center;">
                            <div class="tk-icnText maskIcon" style="width:56px;height:56px;-webkit-mask-image:url('${imgBase}/ic_back2.svg');mask-image:url('${imgBase}/ic_back2.svg');background-color:currentColor;"></div>
                        </div>
                        <div class="tk-primary" style="width:360px; display:flex; align-items:center; justify-content:center;"><p class="tk-btnText" style="font-size:46px; font-weight:700; margin:0;">추가하기</p></div>
                    </div>
                </div>

                <%-- ④ 주문확인 --%>
                <div class="slide" data-i="3">
                    <div class="tk-primary" style="height:110px; display:flex; align-items:center; justify-content:space-between; padding-right:15px; position:relative; flex-shrink:0;">
                        <div style="height:100%; display:flex; align-items:center; padding:0 25px; gap:8px;"><i class="ti ti-arrow-left tk-btnText" style="font-size:44px;"></i><img src="${imgBase}/ic_back.svg" style="height:54px; width:54px;"><span class="tk-btnText" style="font-size:28px; font-weight:700;">이전</span></div>
                        <div style="position:absolute; left:50%; top:50%; transform:translate(-50%,-50%);"><span class="tk-btnText" style="font-size:44px; font-weight:700;">링크</span></div>
                        <div style="display:flex; flex-direction:column; gap:10px; align-items:flex-end;">
                            <p class="tk-btnText" style="font-size:18px; margin:0;">남은시간 : 178초</p>
                            <div style="display:flex; gap:10px;"><span class="tk-icnText" style="background:#fff; padding:2px 15px; border-radius:10px; font-size:26px;">매장</span><span class="tk-btnText" style="background:rgba(0,0,0,0.15); padding:2px 15px; border-radius:10px; font-size:26px;">포장</span></div>
                        </div>
                    </div>
                    <div style="padding:50px 40px 50px; text-align:center; flex-shrink:0;">
                        <p style="font-size:46px; font-weight:700; color:#212121; margin:0;">주문하실 내용이 맞나요?</p>
                        <p style="font-size:28px; color:#212121; margin:0 0 0;">결제 후 취소나 변경이 어렵습니다.</p>
                    </div>
                    <div id="orderList" style="flex:1; overflow-y:auto; padding:0 100px 50px; display:flex; flex-direction:column; gap:36px;" class="customScroll"></div>
                    <div style="background:#f0f0f0; height:651px; box-sizing:border-box; padding:40px; display:flex; flex-direction:column; gap:40px; flex-shrink:0;">
                        <div style="display:flex; gap:12px;">
                            <div style="flex:1;"><p style="font-size:28px; color:#212121; margin:0;">주문금액</p><p style="font-size:46px; font-weight:700; color:#212121; margin:0;">41,200</p></div>
                            <div style="flex:1;"><p style="font-size:28px; color:#212121; margin:0;">할인금액</p><p style="font-size:46px; font-weight:700; color:#212121; margin:0;">-1,000</p></div>
                            <div style="flex:1;"><p style="font-size:28px; color:#212121; margin:0;">결제 한 금액</p><p style="font-size:46px; font-weight:700; color:#212121; margin:0;">0</p></div>
                            <div style="flex:1;"><p style="font-size:28px; color:#212121; margin:0;">총 결제 금액</p><p class="tk-priceText" style="font-size:46px; font-weight:700; margin:0;">40,200</p></div>
                        </div>
                        <div style="background:#e1e1e1; border-radius:30px; padding:30px 40px 25px; display:flex; flex-direction:column; gap:25px;">
                            <div style="display:flex; align-items:center; gap:10px;">
                                <span style="flex:1; font-size:28px; font-weight:700; color:#212121;">선택하신 사항이 맞습니까?</span>
                                <div style="width:400px; display:flex; align-items:center;">
                                    <div style="flex:1; display:flex; align-items:center; gap:10px;"><img src="${imgBase}/check_off.svg" style="width:40px;height:40px;"><span style="font-size:28px;color:#212121;">매장</span></div>
                                    <div style="flex:1; display:flex; align-items:center; gap:10px;"><div class="tk-primary maskIcon" style="width:40px;height:40px;-webkit-mask-image:url('${imgBase}/check_on.svg');mask-image:url('${imgBase}/check_on.svg');"><img src="${imgBase}/ic_check.svg" style="position:absolute; width:40px;height:40px;"></div><span style="font-size:28px;color:#212121;">포장</span></div>
                                </div>
                            </div>
                            <div style="height:1px; background:#ccc;"></div>
                            <div style="display:flex; align-items:center; gap:10px;">
                                <div style="flex:1;">
                                    <p style="font-size:28px; font-weight:700; color:#212121; margin:0;">일회용품 받지 않겠습니다.</p>
                                    <p style="font-size:22px; color:#212121; margin:4px 0 0; line-height:1.3;">'아니요' 선택 시, 메뉴별 필요 물품 제공(빨대, 포크 등)</p>
                                </div>
                                <div style="width:400px; display:flex; align-items:center;">
                                    <div style="flex:1; display:flex; align-items:center; gap:10px;"><img src="${imgBase}/check_off.svg" style="width:40px;height:40px;"><span style="font-size:28px;color:#212121;">예</span></div>
                                    <div style="flex:1; display:flex; align-items:center; gap:10px;"><div class="tk-primary maskIcon" style="width:40px;height:40px;-webkit-mask-image:url('${imgBase}/check_on.svg');mask-image:url('${imgBase}/check_on.svg');"><img src="${imgBase}/ic_check.svg" style="position:absolute; width:40px;height:40px;"></div><span style="font-size:28px;color:#212121;">아니요</span></div>
                                </div>
                            </div>
                        </div>
                        <div style="display:flex; gap:20px;">
                            <div style="flex:1; background:#fff; border-radius:30px; padding:10px; display:flex; flex-direction:column; align-items:center; justify-content:center; gap:5px; height:150px"><img src="${imgBase}/ic_pay_credit.png" style="width:76px;height:76px;"><span style="font-size:28px; font-weight:700; color:#212121;">신용카드</span></div>
                            <div style="flex:1; background:#fff; border-radius:30px; padding:10px; display:flex; flex-direction:column; align-items:center; justify-content:center; gap:5px; height:150px"><img src="${imgBase}/ic_pay_coupon.png" style="width:76px;height:76px;"><span style="font-size:28px; font-weight:700; color:#212121;">모바일 쿠폰</span></div>
                            <div style="flex:1; background:#fff; border-radius:30px; padding:10px; display:flex; flex-direction:column; align-items:center; justify-content:center; gap:5px; height:150px"><img src="${imgBase}/ic_pay_giftcard.png" style="width:76px;height:76px;"><span style="font-size:28px; font-weight:700; color:#212121;">상품권</span></div>
                            <div style="flex:1; background:#fff; border-radius:30px; padding:10px; display:flex; flex-direction:column; align-items:center; justify-content:center; gap:5px; height:150px"><img src="${imgBase}/ic_pay_simple.png" style="width:76px;height:76px;"><span style="font-size:28px; font-weight:700; color:#212121;">간편결제</span></div>
                        </div>
                    </div>
                </div>

                <%-- ⑤ 신용카드결제 --%>
                <div class="slide" data-i="4" style="background:#8a8a8a; align-items:center; justify-content:center; padding:0 100px; box-sizing:border-box;">
                    <div style="width:880px; background:#fff; border-radius:30px; overflow:hidden;">
                        <div style="background:#f0f0f0; height:140px; display:flex; align-items:center; justify-content:center;"><span style="font-size:46px; font-weight:700; color:#212121;">신용카드 결제</span></div>
                        <div style="padding:40px 70px; display:flex; flex-direction:column; align-items:center; gap:20px;">
                            <p style="font-size:34px; font-weight:700; color:#212121; text-align:center; margin:0;">신용카드를 투입구에 끝까지 넣으시고 결제가 완료될 때까지 빼지 마세요.</p>
                            <p style="font-size:24px; color:#212121; text-align:center; margin:0;">삼성페이의 경우 신용카드 투입구에 스마트폰의 뒷면을 대주세요.</p>
                            <div style="width:740px; border-radius:30px; overflow:hidden;">
                                <img src="${imgBase}/sample_pay.png" style="width:100%;">
                            </div>
                        </div>
                        <div style="display:flex; gap:30px; padding:0 70px 70px;">
                            <div class="tk-icntextborder tk-icnText" style="flex:1; height:130px; border-radius:30px; display:flex; align-items:center; justify-content:center; border-width:3px; border-style:solid;"><span style="font-size:46px; font-weight:700;">취소</span></div>
                            <div class="tk-primary" style="flex:1; height:130px; border-radius:30px; display:flex; align-items:center; justify-content:center;"><span class="tk-btnText" style="font-size:46px; font-weight:700;">결제하기</span></div>
                        </div>
                    </div>
                </div>

                <%-- ⑥ 결제완료 --%>
                <div class="slide" data-i="5" style="background:#8a8a8a; align-items:center; justify-content:center; padding:0 100px; box-sizing:border-box;">
                    <div style="width:880px; background:#fff; border-radius:30px; overflow:hidden;">
                        <div style="background:#f0f0f0; height:140px; display:flex; align-items:center; justify-content:center;"><span style="font-size:46px; font-weight:700; color:#212121;">결제 완료</span></div>
                        <div style="padding:50px 70px; display:flex; flex-direction:column; align-items:center; gap:24px;">
                            <p style="font-size:42px; font-weight:700; color:#212121; margin:0;">결제가 완료되었습니다.</p>
                            <p style="font-size:28px; color:#212121; margin:0;">영수증을 확인해 주세요.</p>
                            <div style="width:400px;">
                                <img src="${imgBase}/sample_receipt.png" style="width:100%;">
                            </div>
                            <div style="text-align:center;"><p style="font-size:32px; color:#212121; margin:0;">교환번호</p><p class="tk-priceText" style="font-size:50px; font-weight:700; margin:4px 0 0;">K0002</p></div>
                        </div>
                        <div style="display:flex; gap:30px; padding:0 70px 70px;">
                            <div class="tk-icntextborder tk-icnText" style="flex:1; height:130px; border-radius:30px; display:flex; align-items:center; justify-content:center; border-width:3px; border-style:solid;"><span style="font-size:38px; font-weight:700;">영수증 미출력</span></div>
                            <div class="tk-primary" style="flex:1; height:130px; border-radius:30px; display:flex; align-items:center; justify-content:center;"><span class="tk-btnText" style="font-size:38px; font-weight:700;">영수증 출력</span></div>
                        </div>
                    </div>
                </div>

            </div>

            <div id="nav">
                <button id="prevBtn">←</button>
                <div id="dots"></div>
                <button id="nextBtn">→</button>
            </div>
            <p id="stepLabel"></p>
        </div>

        <%-- 안내 문구 (프리뷰 오른쪽) --%>
        <div style="flex:1; font-size:12px; line-height:1.6; padding-top:8px;">
            <p style="margin:0; color:#212121;"><span style="color:#e0392b;">❗</span>컬러를 변경하면 미리보기 색상이 바뀌며, 하단 화살표 버튼으로 화면을 확인 할 수 있습니다.</p>
            <p style="margin:8px 0 0; color:#212121;">저장한 컬러는 기본 모드 키오스크에만 적용되며, 마스터수신 후 반영됩니다.</p>
        </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    // 프리뷰 이미지 리소스 경로
    var kioskThemeImgBase = "${imgBase}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskKeyMap/kioskTheme.js?ver=20260824.01" charset="utf-8"></script>
