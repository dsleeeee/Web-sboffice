/**
 * 키오스크 테마설정
 * get application
 */
var app = agrid.getApp();


app.controller('kioskThemeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 공통 컨트롤러 상속 (_postJSONSave, _popConfirm, _popMsg 등 사용 위해 필수)
    angular.extend(this, new RootController('kioskThemeCtrl', $scope, $http, false));

    // 컬러테마 콤보 목록
    $scope.themeComboList = kioskThemeComboList;
    // 선택된 컬러테마 (서버 저장값 kioskThemeVal, 없으면 기본 LYNK1 블루)
    $scope.colorTheme = (typeof kioskThemeVal !== "undefined" && kioskThemeVal) ? String(kioskThemeVal) : "LYNK1";

    $scope.init = function () {
        // 프리뷰 데이터 렌더링
        kioskThemePreview.render();
        // 기본 테마 적용
        kioskThemePreview.applyPreset($scope.colorTheme);
    };

    // 컬러테마 변경 시 프리뷰에 즉시 반영
    $scope.changeTheme = function (s) {
        if (s && s.selectedValue) {
            kioskThemePreview.applyPreset(s.selectedValue);
        }
    };

    // 저장 (컬러테마 envst 1357, 매장 단위)
    $scope.saveTheme = function () {
        // 저장하시겠습니까?
        $scope._popConfirm(messages["cmm.choo.save"], function () {
            var params = {};
            params.envstVal = $scope.colorTheme;

            $scope._postJSONSave.withPopUp("/base/prod/kioskKeyMap/kioskKeyMap/saveKioskTheme.sb", params, function (response) {
                // 저장 성공 (성공 메시지는 withPopUp에서 처리)
            });
        });
    };
}]);

/**
 * 프리뷰 렌더링 및 컬러 적용 (원본 previewtool 로직 이식)
 */
var kioskThemePreview = (function () {

    var imgBase = (typeof kioskThemeImgBase !== "undefined") ? kioskThemeImgBase : "img";
    var goodsImgs = [1, 2, 3, 4, 5].map(function (n) { return imgBase + "/sample_goods" + n + ".png"; });

    var goodsData = [
        {name:'아이스 아메리카노', price:'24,900', badge:'BEST', img:0},
        {name:'바닐라라떼', price:'6,900', badge:'NEW', img:1},
        {name:'가래떡 떡볶이', price:'24,900', badge:null, img:2},
        {name:'가래떡 떡볶이', price:'24,900', badge:null, img:3},
        {name:'가래떡 떡볶이', price:'15,900', badge:null, singset:true, img:4},
        {name:'바닐라라떼', price:'6,900', badge:null, img:1},
        {name:'아이스 아메리카노', price:'24,900', badge:null, img:0},
        {name:'신제품 까르보나라', price:'24,900', badge:'BEST', img:2, dim:false},
        {name:'신제품 까르보나라', price:'14,500', oldPrice:'24,500', badge:null, img:3},
        {name:'아이스 아메리카노', price:'24,900', badge:null, img:0},
        {name:'가래떡 떡볶이', price:'24,900', badge:null, img:4},
        {name:'가래떡 떡볶이', price:'24,900', badge:null, placeholder:true, img:2},
        {name:'가래떡 떡볶이', price:'15,900', badge:null, singset:true, img:1},
        {name:'바닐라라떼', price:'6,900', badge:null, img:3},
        {name:'아이스 아메리카노', price:'24,900', badge:null, img:0},
        {name:'신제품 까르보나라', price:'24,900', badge:null, img:4}
    ];

    var optionData = [
        {name:'아메리카노', price:'24,900', img:0},
        {name:'바닐라라떼', price:'6,900', img:1},
        {name:'가래떡 떡볶이', price:'24,900', img:2},
        {name:'가래떡 떡볶이', price:'24,900', img:3},
        {name:'신제품 까르보나라', price:'24,900', img:4},
        {name:'아이스 아메리카노', price:'24,900', img:0},
        {name:'바닐라라떼', price:'6,900', img:1},
        {name:'가래떡 떡볶이', price:'24,900', img:2},
        {name:'신제품 까르보나라', price:'24,900', img:3},
        {name:'아이스 아메리카노', price:'24,900', img:4},
        {name:'바닐라라떼', price:'6,900', img:0},
        {name:'가래떡 떡볶이', price:'24,900', img:1},
        {name:'신제품 까르보나라', price:'24,900', img:4},
        {name:'아이스 아메리카노', price:'24,900', img:0},
        {name:'바닐라라떼', price:'6,900', img:1},
        {name:'가래떡 떡볶이', price:'24,900', img:2}
    ];

    var orderData = [
        {name:'아주 맛있는 가래떡 떡볶이', kcal:'1,076 kcal', qty:1, price:'20,600', option:'케이준 양념감자(중) + 1,000원, 치즈스틱 2조각 + 0원 케이준 양념감자(중) + 1,000원, 치즈스틱 2조각 + 0원', img:2},
        {name:'속이 시원한 아이스 아메리카노', kcal:'182 kcal', qty:1, price:'20,600', option:'케이준 양념감자(중) + 1,000원, 치즈스틱 2조각 + 0원 케이준 양념감자(중) + 1,000원, 치즈스틱 2조각 + 0원', img:0},
        {name:'달콤 듬뿍 바닐라라떼', kcal:'320 kcal', qty:1, price:'6,900', option:'케이준 양념감자(중) + 1,000원, 치즈스틱 2조각 + 0원 케이준 양념감자(중) + 1,000원, 치즈스틱 2조각 + 0원', img:1},
        {name:'아주 맛있는 가래떡 떡볶이', kcal:'320 kcal', qty:1, price:'6,900', option:'케이준 양념감자(중) + 1,000원, 치즈스틱 2조각 + 0원 케이준 양념감자(중) + 1,000원, 치즈스틱 2조각 + 0원', img:3}
    ];

    function renderGoods(containerId, dataList, priceLabel) {
        var grid = document.getElementById(containerId);
        if (!grid) { return; }
        grid.innerHTML = "";
        dataList.forEach(function (g) {
            var item = document.createElement('div');
            item.style.cssText = 'position:relative; display:flex; flex-direction:column; align-items:center; gap:10px;' + (g.dim ? 'opacity:0.3;' : '');
            var badgeHtml = '';
            if (g.badge === 'BEST') badgeHtml = '<span class="tk-primary tk-btnText" style="position:absolute; top:-10px; left:3px; width:80px; padding:5px 0; border-radius:100px; text-align:center; font-size:20px; font-weight:700;">BEST</span>';
            if (g.badge === 'NEW') badgeHtml = '<span class="tk-priceBg" style="position:absolute; top:-10px; left:3px; color:#fff; width:80px; padding:5px 0; border-radius:100px; text-align:center; font-size:20px; font-weight:700;">NEW</span>';
            var imgHtml = g.placeholder
                ? '<div style="width:100%;aspect-ratio:1/1;border:1px solid #e1e1e1;border-radius:30px;background:#fafafa;display:flex;align-items:center;justify-content:center;color:#ccc;font-size:22px;text-align:center;">이미지<br>준비중</div>'
                : '<div style="width:100%;aspect-ratio:1/1;border:1px solid #e1e1e1;border-radius:30px;overflow:hidden;"><img src="' + goodsImgs[g.img] + '" style="width:100%;height:100%;object-fit:cover;"></div>';
            var priceHtml = g.singset
                ? '<div style="display:flex;gap:5px;align-items:center;"><span class="tk-singset tk-singsetText" style="width:34px;height:34px;border-radius:100px;font-size:26px;display:flex;align-items:center;justify-content:center;">세</span><span class="tk-priceText" style="font-size:26px;font-weight:700;">' + (priceLabel || '') + g.price + '</span></div>'
                : g.oldPrice
                ? '<div style="display:flex;gap:5px;align-items:baseline;"><span style="font-size:20px;color:#999;text-decoration:line-through;">' + g.oldPrice + '</span><span class="tk-priceText" style="font-size:26px;font-weight:700;">' + (priceLabel || '') + g.price + '</span></div>'
                : '<p class="tk-priceText" style="font-size:26px; font-weight:700; margin:0;">' + (priceLabel || '') + g.price + '</p>';
            item.innerHTML = badgeHtml + imgHtml + '<p style="font-size:26px; font-weight:700; color:#212121; margin:0; text-align:center; white-space:nowrap; overflow:hidden; text-overflow:ellipsis; width:100%;">' + g.name + '</p>' + priceHtml;
            grid.appendChild(item);
        });
    }

    function renderOrder() {
        var orderList = document.getElementById('orderList');
        if (!orderList) { return; }
        orderList.innerHTML = "";
        orderData.forEach(function (o) {
            var row = document.createElement('div');
            row.style.cssText = 'display:flex; gap:30px; align-items:flex-start;';
            row.innerHTML =
                '<div style="display:flex; flex-direction:column; align-items:center; gap:8px; flex-shrink:0;">' +
                '  <div style="width:200px; height:200px; border:1px solid #e1e1e1; border-radius:30px; overflow:hidden;"><img src="' + goodsImgs[o.img] + '" style="width:100%;height:100%;object-fit:cover;"></div>' +
                '  <p style="font-size:22px; color:#212121; margin:0;">' + o.kcal + '</p>' +
                '</div>' +
                '<div style="flex:1;">' +
                '  <div style="display:flex; align-items:center; justify-content:space-between;">' +
                '    <p style="font-size:26px; font-weight:700; color:#212121; margin:0;">' + o.name + '</p>' +
                '    <img src="' + imgBase + '/delete.svg" style="width:48px;height:48px;cursor:pointer;">' +
                '  </div>' +
                '  <div style="display:flex; align-items:center; gap:16px; margin-top:10px;">' +
                '    <div class="numBtn" style="width:48px;height:48px;border:2px solid #e1e1e1;"><img src="' + imgBase + '/minus2.png" style="width:20px;height:4px;"></div>' +
                '    <span style="font-size:28px; font-weight:700; color:#212121;">' + o.qty + '</span>' +
                '    <div class="numBtn tk-primary" style="width:48px;height:48px;"><img src="' + imgBase + '/plus2.png" style="width:20px;height:20px;"></div>' +
                '    <span class="tk-priceText" style="font-size:28px; font-weight:700;">' + o.price + '</span>' +
                '  </div>' +
                (o.option ? '  <p style="font-size:26px; color:#212121; margin:10px 0 0; line-height:1.4;">' + o.option + '</p>' : '') +
                '</div>';
            orderList.appendChild(row);
        });
    }

    /* ---------- 컬러 유틸 ---------- */
    function hexToRgb(h) { h = h.replace('#', ''); return [parseInt(h.substr(0, 2), 16), parseInt(h.substr(2, 2), 16), parseInt(h.substr(4, 2), 16)]; }
    function luminance(hex) { var rgb = hexToRgb(hex).map(function (v) { v /= 255; return v <= 0.03928 ? v / 12.92 : Math.pow((v + 0.055) / 1.055, 2.4); }); return 0.2126 * rgb[0] + 0.7152 * rgb[1] + 0.0722 * rgb[2]; }
    function textOn(hex) { return luminance(hex) < 0.55 ? '#ffffff' : '#212121'; }
    function mix(hex, target, amt) {
        var rgb = hexToRgb(hex);
        return '#' + [rgb[0] + (target[0] - rgb[0]) * amt, rgb[1] + (target[1] - rgb[1]) * amt, rgb[2] + (target[2] - rgb[2]) * amt]
            .map(function (x) { return Math.max(0, Math.min(255, Math.round(x))).toString(16).padStart(2, '0'); }).join('');
    }

    // 프리셋: 링크1~4 실제 토큰 조합
    var presets = {
        "LYNK1": { primary:'#006bff', singset:'#006bff', price:'#ff5414' }, // LYNK1 · 블루
        "LYNK2": { primary:'#da292c', singset:'#212121', price:'#da292c' }, // LYNK2 · 레드
        "LYNK3": { primary:'#56c44a', singset:'#212121', price:'#fa2828' }, // LYNK3 · 그린
        "LYNK4": { primary:'#262626', singset:'#262626', price:'#fa2828' }  // LYNK4 · 딥그레이
    };

    // 프리뷰 영역으로 한정하기 위한 셀렉터 prefix
    var ROOT = '#kioskThemePreview ';
    function $all(sel) { return document.querySelectorAll(ROOT + sel); }

    function renderColors(colors) {
        var primary = colors.primary;
        var singset = colors.singset;
        var price = colors.price;
        var icnText = luminance(primary) < 0.85 ? primary : '#212121';
        var btnText = textOn(primary);
        var singsetText = textOn(singset);
        var primaryBg7 = mix(primary, [255, 255, 255], 0.93);

        $all('.tk-primary').forEach(function (el) { el.style.background = primary; });
        $all('.tk-btnText').forEach(function (el) { el.style.color = btnText; });
        $all('.tk-icnText').forEach(function (el) { el.style.color = icnText; });
        $all('.tk-icnText.maskIcon').forEach(function (el) { el.style.backgroundColor = icnText; });
        $all('.tk-icntextborder').forEach(function (el) { el.style.borderColor = icnText; });
        $all('.tk-priceText').forEach(function (el) { el.style.color = price; });
        $all('.tk-priceBg').forEach(function (el) { el.style.background = price; });
        $all('.tk-singset').forEach(function (el) { el.style.background = singset; });
        $all('.tk-singsetText').forEach(function (el) { el.style.color = singsetText; });
        $all('.tk-primaryBg7').forEach(function (el) { el.style.background = primaryBg7; });
    }

    function applyPreset(n) {
        var p = presets[n] || presets["LYNK1"];
        renderColors(p);
    }

    /* ---------- 슬라이드 네비게이션 ---------- */
    var names = ['인트로', '메인', '옵션선택', '주문확인', '신용카드결제', '결제완료'];
    var current = 0;
    var slides, dotsWrap, navInitialized = false;

    function goTo(i) {
        if (!slides || !slides.length) { return; }
        slides[current].classList.remove('active');
        current = (i + slides.length) % slides.length;
        slides[current].classList.add('active');
        Array.prototype.forEach.call(dotsWrap.children, function (d, idx) { d.classList.toggle('active', idx === current); });
        var stepLabel = document.getElementById('stepLabel');
        if (stepLabel) { stepLabel.textContent = (current + 1) + ' / ' + slides.length + ' · ' + names[current]; }
    }

    function initNav() {
        if (navInitialized) { return; }
        slides = document.querySelectorAll('#kioskThemePreview .slide');
        dotsWrap = document.getElementById('dots');
        if (!slides.length || !dotsWrap) { return; }
        dotsWrap.innerHTML = "";
        slides.forEach(function (s, i) {
            var d = document.createElement('div');
            d.className = 'dot' + (i === 0 ? ' active' : '');
            d.addEventListener('click', function () { goTo(i); });
            dotsWrap.appendChild(d);
        });
        var prevBtn = document.getElementById('prevBtn');
        var nextBtn = document.getElementById('nextBtn');
        if (prevBtn) { prevBtn.addEventListener('click', function () { goTo(current - 1); }); }
        if (nextBtn) { nextBtn.addEventListener('click', function () { goTo(current + 1); }); }
        var viewport = document.querySelector('#kioskThemePreview #viewport');
        if (viewport) {
            viewport.addEventListener('click', function (e) {
                var rect = e.currentTarget.getBoundingClientRect();
                var clickX = e.clientX - rect.left;
                if (clickX > rect.width / 2) { goTo(current + 1); } else { goTo(current - 1); }
            });
        }
        navInitialized = true;
        goTo(0);
    }

    return {
        render: function () {
            renderGoods('goodsGrid', goodsData, null);
            renderGoods('optionGrid', optionData, '+');
            renderOrder();
            initNav();
        },
        applyPreset: applyPreset
    };
})();
