/****************************************************************
 *
 * 파일명 : userWebChkCnt.js
 * 설  명 : 사용자웹사용이력 > [조회정보] 팝업 (ETC050 조회건수 탭 이관)
 *          ①   USER_ID 종합 (임계치 OR 조건 + 자동일시정지 판정)
 *          ①-1 자동일시정지 내역
 *          ②   USER_ID + IP별 상세
 *          ③   메뉴(URL)별 건수
 *          ④   일시정지 이력
 *          ⑤   웹사용자정보변경이력
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.09.22     김유승      1.0            최초생성
 * 2026.09.23     김유승      1.1            로그인 유형별 · 로그인IP수 · 현재메뉴 불일치 · ③ 메뉴별 건수
 * 2026.09.28     김유승      1.2            ETC050 동기화 — 임계치 초과 강조, ② 구분 태그 차단출처 반영,
 *                                          ② 0건 시 원천 데이터 진단, 안내 문구 동기화
 * 2026.09.28     김유승      1.3            조회기간 달력을 공통 모듈(wcombo.genDateVal)로 변경
 * 2026.10.14     김유승      1.4            ① 자동일시정지(SP_JOB_AUTO_PAUSE_IUD01) 판정 항목 · 판정항목 설명,
 *                                          ①-1 자동일시정지 내역 추가
 *
 * **************************************************************/
var app = agrid.getApp();

app.controller('userWebChkCntCtrl', ['$scope', '$http', '$timeout', '$sce', function ($scope, $http, $timeout, $sce) {

    angular.extend(this, new RootController('userWebChkCntCtrl', $scope, $http, $timeout, false));

    var TH_DEFAULT = { thChkToday: '500', thChk5m: '100', thBlkToday: '1', thBlk10m: '1', thLgnToday: '100', thLgn5m: '10' };

    // 조회기간 달력 (userWebHist 와 동일 모듈) — 기본값 오늘
    var chkDay1 = wcombo.genDateVal("#chkDay1", new Date());
    var chkDay2 = wcombo.genDateVal("#chkDay2", new Date());

    var C_ERR = '#c0392b', C_WARN = '#e67e22', C_CNT = '#1a5276', C_SUB = '#888';

    var RISK_MAP = {
        'A': { txt: '★★ 국외IP에서 조회중', color: C_ERR , bold: true  },
        'B': { txt: '★ 국외IP+조회중',      color: C_ERR , bold: true  },
        'C': { txt: '국외IP 최근접근',        color: C_WARN, bold: true  },
        'D': { txt: '국외IP+로그인실패',      color: C_WARN, bold: true  },
        'E': { txt: '국외IP 기간이력',        color: C_SUB , bold: false },
        'F': { txt: '최근 로그인실패',        color: C_SUB , bold: false }
    };

    /* ══════════ 자동 일시정지(SP_JOB_AUTO_PAUSE_IUD01) 판정 기준 ══════════
       ※ SP 상수(C_MODE_xx / C_TH_Bx / C_PAUSE_SCOPE / C_MAX_REAL_PER_RUN) 와 반드시 동일하게 유지
       mode : 'Y' 실제 일시정지 / 'T' 시험(이력만) / 'N' 미사용                              */
    var AP_CONF = {
        scope     : 'TARGET',   // 'TARGET' 감시 대상만 / 'ALL' 전체
        maxPerRun : 20,
        items : [
            { code:'G1', mode:'T', th:0  , field:'min5Real'   , col:'국외IP일치 · 5분 실건수',
                nm:'국외IP에서 조회 중',
                desc:'오늘 국외IP 차단 이력의 IP(REAL_IP·LOGIN_IP) 중 하나가 최근 조회 IP(조회건수 알림의 마지막 IP)와 같고, 최근 5분 실조회가 1건 이상' },
            { code:'G2', mode:'T', th:0  , field:'blkMin10Cnt', col:'국외IP 10분 · 5분 실건수',
                nm:'국외IP 접근 직후 조회',
                desc:'최근 10분 안에 국외IP 차단 이력이 1건 이상이고, 최근 5분 실조회가 1건 이상' },
            { code:'B1', mode:'T', th:200, field:'min5Real'   , col:'5분 실건수',
                nm:'5분 대량 조회',
                desc:'최근 5분 실조회(예외 URL 제외) 건수가 기준 이상 — 대량 조회·데이터 수집 의심' },
            { code:'B2', mode:'T', th:30 , field:'apMe5m'     , col:'5분 불일치',
                nm:'현재메뉴 불일치 다발',
                desc:'최근 5분 현재메뉴 불일치 실건수(예외 URL 제외)가 기준 이상 — 화면에 열려있지 않은 메뉴 URL 을 직접 호출' },
            { code:'B3', mode:'T', th:3  , field:'apIp5m'     , col:'5분 조회IP',
                nm:'다중 IP 조회',
                desc:'최근 5분 조회건수 알림의 IPS·IPD·IP 에 나온 서로 다른 IP 수가 기준 이상 — 계정 공유·탈취 의심' },
            { code:'B4', mode:'T', th:20 , field:'lgnMin5Tot' , col:'5분 로그인',
                nm:'로그인 반복',
                desc:'최근 5분 로그인 시도(성공+실패) 건수가 기준 이상 — 무차별 대입·자동 로그인 의심' },
            { code:'B5', mode:'T', th:60 , field:'apUrl5mMax' , col:'5분 같은URL',
                nm:'같은 URL 반복 조회',
                desc:'최근 5분 URL 1개의 실조회 건수(URLD 기준, NOURL·ETC 제외)가 기준 이상 — 스크래핑 의심' }
        ]
    };
    var AP_MODE = {
        'Y': { nm:'실제'  , bg:'#fadbd8', fg:'#c0392b' },
        'T': { nm:'시험'  , bg:'#d6eaf8', fg:'#2471a3' },
        'N': { nm:'미사용', bg:'#eeeeee', fg:'#999999' }
    };

    $scope.chkCntData        = new wijmo.collections.CollectionView([]);
    $scope.chkCntIpData      = new wijmo.collections.CollectionView([]);
    $scope.chkCntMenuData    = new wijmo.collections.CollectionView([]);
    $scope.autoPauseData     = new wijmo.collections.CollectionView([]);
    $scope.pauseHistData     = new wijmo.collections.CollectionView([]);
    $scope.userInfoLogData   = new wijmo.collections.CollectionView([]);
    $scope.chkCntSummary     = '';
    $scope.chkCntIpSummary   = '';
    $scope.chkCntMenuSummary = '';
    $scope.autoPauseSummary  = '';
    $scope.pauseHistSummary  = '';
    $scope.userInfoLogSummary = '';

    $scope.$on("userWebChkCntCtrl", function (event, data) {
        $scope.openChkCntPopup();
        event.preventDefault();
    });

    /* ── 유틸 ── */
    function fmt(d, f)   { return wijmo.Globalize.format(d, f); }
    function todayDate() { var t = new Date(); return new Date(t.getFullYear(), t.getMonth(), t.getDate()); }
    function toInt(v, def) {
        var n = parseInt(String(v === undefined || v === null ? '' : v).replace(/[^0-9]/g, ''), 10);
        return isNaN(n) ? def : n;
    }
    function esc(s) {
        return String(s === undefined || s === null ? '' : s)
            .replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
    }
    function validDates() {
        if (!chkDay1.value) chkDay1.value = todayDate();
        if (!chkDay2.value) chkDay2.value = todayDate();
        if (chkDay1.value.getTime() > chkDay2.value.getTime()) {   // from > to 이면 교환
            var t = chkDay1.value; chkDay1.value = chkDay2.value; chkDay2.value = t;
        }
    }
    function day1Ymd() { return fmt(chkDay1.value, 'yyyyMMdd'); }
    function day2Ymd() { return fmt(chkDay2.value, 'yyyyMMdd'); }
    function periodLabel() {
        var a = fmt(chkDay1.value, 'yyyy-MM-dd'), b = fmt(chkDay2.value, 'yyyy-MM-dd');
        return a === b ? a : a + ' ~ ' + b;
    }
    function includesToday() {
        return day2Ymd() >= fmt(todayDate(), 'yyyyMMdd');
    }
    function extractList(res) {
        var d    = res.data || {};
        var list = (d.data && d.data.list) || d.list || (angular.isArray(d.data) ? d.data : []) || [];
        return angular.isArray(list) ? list : [];
    }
    function extractOne(res) {
        var d = res.data || {};
        return (d.data && !angular.isArray(d.data)) ? d.data : d;
    }
    function markErr(cell, bold) { cell.style.color = C_ERR; if (bold) cell.style.fontWeight = 'bold'; }
    function markCnt(cell, bold) { cell.style.color = C_CNT; if (bold) cell.style.fontWeight = 'bold'; }
    function tag(txt, color, bold) {
        return '<span style="color:' + color + ';' + (bold ? 'font-weight:bold;' : '') + '">' + txt + '</span>';
    }

    /* ── 자동 일시정지 유틸 ── */
    function apItem(code) {
        for (var i = 0; i < AP_CONF.items.length; i++) if (AP_CONF.items[i].code === code) return AP_CONF.items[i];
        return null;
    }
    function apTh(code) { var it = apItem(code); return it ? it.th : 0; }
    function apSplit(s) {
        return (s && s !== '-') ? String(s).split(',').filter(function (c) { return c !== ''; }) : [];
    }
    function apParams() {
        var p = { apHitYn: ($scope.apHitYn === 'N' ? 'N' : 'Y') }, use = [];
        angular.forEach(AP_CONF.items, function (it) {
            if (it.mode !== 'N') use.push(it.code);
            if (it.th > 0) p['apTh' + it.code] = it.th;
        });
        p.apUseItems = use.join(',');
        return p;
    }
    /* 코드 칩 — forceMode 가 있으면 그 모드 색(이력용), 없으면 현재 설정 모드 색 */
    function apCodesHtml(codes, forceMode) {
        var list = apSplit(codes);
        if (list.length === 0) return '<span style="color:#bbb;">-</span>';
        return list.map(function (c) {
            var it = apItem(c), m = AP_MODE[forceMode || (it ? it.mode : 'N')] || AP_MODE.N;
            return '<span title="' + esc(it ? it.nm : c) + '" style="display:inline-block; margin-right:3px; padding:0 5px; border-radius:3px;'
                + ' font-size:11px; font-weight:bold; background:' + m.bg + '; color:' + m.fg + ';">' + esc(c) + '</span>';
        }).join('');
    }
    function apCodesTitle(item) {
        return apSplit(item.apHitCodes).map(function (c) {
            var it = apItem(c);
            if (!it) return c;
            return c + ' ' + it.nm + ' [' + AP_MODE[it.mode].nm + ']'
                + (it.th > 0 ? ' : ' + toInt(item[it.field], 0) + ' ≥ ' + it.th : '');
        }).join('\n');
    }
    /* SP STEP 3 와 같은 순서로 예상 처리 판정 (인원 초과 보류는 행 단위로 알 수 없어 요약에서 경고) */
    function apJudge(r) {
        var codes = apSplit(r.apHitCodes);
        if (codes.length === 0) return { txt:'', color:C_SUB };
        if (r.apExc)            return { txt:'제외 (' + r.apExc + ')', color:C_SUB, exc:true };
        var real = codes.filter(function (c) { var it = apItem(c); return it && it.mode === 'Y'; });
        var inScope = (AP_CONF.scope === 'ALL' || r.apTargetYn === 'Y');
        if (real.length > 0 && inScope) return { txt:'실제 정지 대상', color:C_ERR , bold:true, real:true };
        if (real.length > 0)            return { txt:'대상외 보류 (시험기록)', color:C_WARN, bold:true };
        return { txt:'시험 기록', color:'#2471a3' };
    }
    function buildApGuideHtml() {
        var th = 'style="border:1px solid #d6dde3; padding:3px 6px; background:#eef3f7; text-align:center;"';
        var td = 'style="border:1px solid #d6dde3; padding:3px 6px;"';
        var h = '<div style="font-size:11px; color:#444; line-height:1.7;">'
            + '<b>자동 일시정지 Job</b> (SP_JOB_AUTO_PAUSE_IUD01, 5분 주기) 판정 항목 — '
            + '실제 정지 범위: <b>' + (AP_CONF.scope === 'ALL' ? '전체 사용자' : '감시 대상(FN_CHK_BLOCK_TARGET_USER_YN = Y)만') + '</b>'
            + ' · 1회 실제 정지 <b>' + AP_CONF.maxPerRun + '명</b> 초과 시 데이터 오류로 보고 전원 보류'
            + '<table style="width:100%; border-collapse:collapse; margin:4px 0;">'
            + '<tr><th ' + th + '>항목</th><th ' + th + '>이름</th><th ' + th + '>모드</th><th ' + th + '>기준</th>'
            + '<th ' + th + '>판정 조건</th><th ' + th + '>① 확인 열</th></tr>';
        angular.forEach(AP_CONF.items, function (it) {
            var m = AP_MODE[it.mode];
            h += '<tr><td ' + td + ' align="center"><b>' + it.code + '</b></td>'
                + '<td ' + td + '>' + esc(it.nm) + '</td>'
                + '<td ' + td + ' align="center"><span style="padding:0 5px; border-radius:3px; background:' + m.bg + '; color:' + m.fg + '; font-weight:bold;">' + m.nm + '</span></td>'
                + '<td ' + td + ' align="center">' + (it.th > 0 ? it.th + ' 이상' : '&gt; 0') + '</td>'
                + '<td ' + td + '>' + esc(it.desc) + '</td>'
                + '<td ' + td + '>' + esc(it.col) + '</td></tr>';
        });
        h += '</table>'
            + '· <b>실제(Y)</b> 항목에 하나라도 해당 + 범위 안 → USER_STAT_FG 00 → 50 (이력 PROC_FG = P)<br>'
            + '· <b>시험(T)</b> 항목만 해당 / 범위 밖(대상외 보류) / 인원 초과(인원초과 보류) → 상태 변경 없이 이력만 기록 (PROC_FG = T, 같은 사용자 30분 내 중복 생략)<br>'
            + '· <b>제외</b> : 이미 일시정지(50) · 사용안함 · 관리자(TB_CM_EMPLOYEE.ADMIN_FG = A) · 화이트리스트 NMCODE 252 (USER = 아이디 / IP = 최근 조회 IP 앞자리)<br>'
            + '· <b>해제</b> : SP_JOB_CHK_LOGIN_IUD01 30분 자동 해제 또는 로그인 화면 본인 해제 — ①-1 내역의 해제일시·해제자로 확인<br>'
            + '· 화면 값은 <b>조회 시각 기준</b> 최근 5분/10분이라 Job 실행 시각과 차이가 있을 수 있습니다. G1·G2·B4 는 조회기간을 오늘로 둘 때 SP 와 같습니다.'
            + '</div>';
        return h;
    }

    $scope.apHitYn      = 'Y';
    $scope.apGuideShow  = false;
    $scope.apGuideHtml  = $sce.trustAsHtml(buildApGuideHtml());
    $scope.apConfLine   = $sce.trustAsHtml(
        AP_CONF.items.map(function (it) {
            var m = AP_MODE[it.mode];
            return '<span title="' + esc(it.nm) + '" style="margin-right:3px; padding:0 5px; border-radius:3px; font-size:11px; font-weight:bold; background:'
                + m.bg + '; color:' + m.fg + ';">' + it.code + ' ' + m.nm + '</span>';
        }).join(''));
    $scope.toggleApGuide = function (ev) {
        if (ev) ev.preventDefault();
        $scope.apGuideShow = !$scope.apGuideShow;
    };

    $scope.apProcFg     = '';
    $scope.apProcFgList = [ { v:'', n:'전체' }, { v:'P', n:'실제 정지(P)' }, { v:'T', n:'시험·보류(T)' } ];

    /* ── 그리드 초기화 : ① USER_ID 종합 ── */
    $scope.initChkCntGrid = function (s, e) {
        s.formatItem.addHandler(function (s, e) {
            if (e.panel !== s.cells) return;
            var col  = s.columns[e.col];
            var item = s.rows[e.row].dataItem;
            if (!item) return;
            var b = col.binding, v = toInt(item[b], 0);

            // 현재 조회조건의 임계치 (0 = 미사용)
            var thChkToday = toInt($scope.thChkToday, 0), thChk5m = toInt($scope.thChk5m, 0);
            var thLgnToday = toInt($scope.thLgnToday, 0), thLgn5m = toInt($scope.thLgn5m, 0);

            if (b === 'userId') {
                wijmo.addClass(e.cell, 'wijLink');
                e.cell.title = '이 사용자의 IP별 상세 / 메뉴별 건수 보기';
            }
            if (b === 'risk') {
                var m = RISK_MAP[item.risk];
                e.cell.innerHTML = m ? tag(m.txt, m.color, m.bold) : tag('-', C_SUB, false);
            }

            // 행 배경 — 위험도 A/B 붉은 · 국외IP 이력 노란
            if (item.risk === 'A' || item.risk === 'B')      e.cell.style.background = '#fdf2f1';
            else if (toInt(item.blkTodayCnt, 0) > 0)         e.cell.style.background = '#fff8e1';

            // 임계치 초과 강조 (ETC050 동일)
            if (b === 'todayReal')   { (thChkToday > 0 && v >= thChkToday) ? markErr(e.cell, true)  : markCnt(e.cell, true);  }
            if (b === 'min5Real')    { (thChk5m    > 0 && v >= thChk5m   ) ? markErr(e.cell, true)  : markCnt(e.cell, true);  }
            if (b === 'lgnTodayTot') { (thLgnToday > 0 && v >= thLgnToday) ? markErr(e.cell, true)  : markCnt(e.cell, true);  }
            if (b === 'lgnMin5Tot')  { (thLgn5m    > 0 && v >= thLgn5m   ) ? markErr(e.cell, false) : markCnt(e.cell, false); }
            if (b === 'lgnMin5Err'   && v > 0) markErr(e.cell, false);
            if (b === 'blkTodayCnt'  && v > 0) markErr(e.cell, false);
            if (b === 'blkMin10Cnt'  && v > 0) markErr(e.cell, false);
            if (b === 'lgnTodaySucc')          e.cell.style.color = '#1e8449';
            if (b === 'meReal'       && v > 0) markErr(e.cell, true);
            if (/^lgnTodayE[1-5]$/.test(b) && v > 0) markErr(e.cell, false);
            if (b === 'lgnIpCnt'     && v > 1) markCnt(e.cell, true);
            if (b === 'ipHit'        && item.ipHit === 'Y') markErr(e.cell, true);

            // 자동 일시정지 판정
            if (b === 'apHitCodes') {
                e.cell.innerHTML = apCodesHtml(item.apHitCodes);
                e.cell.title     = apCodesTitle(item);
            }
            if (b === 'apResult' && item.apResult) {
                var j = apJudge(item);
                e.cell.innerHTML = tag(esc(j.txt), j.color, j.bold);
            }
            if (b === 'apTargetYn' && item.apTargetYn === 'Y')  markErr(e.cell, true);
            if (b === 'apExc' && item.apExc)                     e.cell.style.color = C_SUB;
            if (b === 'userStatFg' && item.userStatFg === '50')  e.cell.innerHTML = tag('50 (일시정지)', C_ERR, true);
            if (b === 'apMe5m'     && apTh('B2') > 0 && v >= apTh('B2')) markErr(e.cell, true);
            if (b === 'apIp5m'     && apTh('B3') > 0 && v >= apTh('B3')) markErr(e.cell, true);
            if (b === 'apUrl5mMax' && apTh('B5') > 0 && v >= apTh('B5')) markErr(e.cell, true);
            if (b === 'apUrl5mTop') e.cell.title = item.apUrl5mTop || '';
        });

        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var row = s.rows[ht.row].dataItem;
                if (col.binding === 'userId' && row && row.userId) {
                    $scope.ipUserId = row.userId;
                    $scope.searchChkCntIp();
                    if (!$scope.$$phase) $scope.$apply();
                }
            }
        });
    };

    /* ── ② 구분 태그 (ETC050 blkKind 반영) ── */
    function gubunHtml(item) {
        var g   = item.gubun;
        var geo = toInt(item.geoCnt, 0), sec = toInt(item.secCnt, 0);
        var blkKind = (geo > 0 && sec > 0) ? '국외IP+로그인차단'
            : (sec > 0)            ? '로그인차단(SecViol)'
                :                        '국외판정';
        if (g === 'A') return tag('★★ ' + blkKind + ' IP에서 조회중', C_ERR , true);
        if (g === 'B') return tag('★ '  + blkKind + ' IP 조회이력'  , C_ERR , true);
        if (g === 'C') return tag(blkKind + ' IP (조회 없음)'         , C_WARN, true);
        if (g === 'N') return tag('IP미기록(YN호출)'                  , C_SUB , false);
        if (g === 'T') return tag('IP표기초과 합산'                   , C_SUB , false);
        if (g === 'L') {
            var lt = toInt(item.lgnTodayTot, 0), mt = toInt(item.meTot, 0);
            var lTxt = (lt > 0 && mt > 0) ? '로그인·현재메뉴불일치 집계'
                : (mt > 0)           ? '현재메뉴불일치 집계'
                    :                      '로그인 집계';
            return tag(lTxt + ' (IP 없음)', '#2471a3', false);
        }
        return tag('-', C_SUB, false);
    }

    /* ── 그리드 초기화 : ② IP별 상세 ── */
    $scope.initChkCntIpGrid = function (s, e) {
        var loginCols = ['lgnTodayTot','lgnTodaySucc','lgnTodayE1','lgnTodayE2','lgnTodayE3','lgnTodayE4','lgnTodayE5','lgnMin5Tot','lgnMin5Err','meReal','meExc','meTot'];
        var chkCols   = ['todayReal','todayExc','todayTot','min5Real','min5Tot','blkTodayCnt','blkMin10Cnt'];

        s.formatItem.addHandler(function (s, e) {
            if (e.panel !== s.cells) return;
            var col  = s.columns[e.col];
            var item = s.rows[e.row].dataItem;
            if (!item) return;
            var b = col.binding, v = toInt(item[b], 0);
            var isL = (item.gubun === 'L');
            var hot = (item.gubun === 'A' || item.gubun === 'B');

            if (b === 'gubun') e.cell.innerHTML = gubunHtml(item);

            if (b === 'ipv') {
                if (isL) {
                    e.cell.innerHTML = tag('(IP 없음)', C_SUB, false);
                } else {
                    var ipHtml = '<b>' + esc(item.ipv) + '</b>';
                    if (toInt(item.isApprox, 0) === 1) {
                        ipHtml += ' <span style="color:#888;" title="IPD 필드가 없는 구버전 알림 — 사용자 합계를 마지막 IP 에 귀속한 근사치">(근사)</span>';
                    }
                    e.cell.innerHTML = ipHtml;
                    e.cell.style.color = hot ? C_ERR : C_CNT;
                }
            }

            // 로그인/불일치 열은 (IP 없음) 행에서만, 조회/차단 열은 IP 행에서만 의미 있음 → 나머지는 '-'
            if (!isL && loginCols.indexOf(b) >= 0) { e.cell.innerHTML = '<span style="color:#bbb;">-</span>'; return; }
            if (isL  && chkCols.indexOf(b)   >= 0) { e.cell.innerHTML = '<span style="color:#bbb;">-</span>'; return; }

            // 강조 (ETC050 동일)
            if (isL) {
                if (b === 'lgnTodayTot')  { (v >= 50) ? markErr(e.cell, true) : markCnt(e.cell, true); }
                if (b === 'lgnTodaySucc')  e.cell.style.color = '#1e8449';
                if (/^lgnTodayE[1-5]$/.test(b) && v > 0) markErr(e.cell, false);
                if (b === 'lgnMin5Tot'   && v > 0) markErr(e.cell, true);
                if (b === 'lgnMin5Err'   && v > 0) markErr(e.cell, false);
                if (b === 'meReal'       && v > 0) markErr(e.cell, true);
            } else {
                if (b === 'todayReal')             markCnt(e.cell, true);
                if (b === 'min5Real'     && v > 0) markErr(e.cell, false);
                if (b === 'blkTodayCnt'  && v > 0) markErr(e.cell, false);
                if (b === 'blkMin10Cnt'  && v > 0) markErr(e.cell, false);
                if (b === 'secCnt'       && v > 0) markErr(e.cell, false);
            }

            if (hot)                                   e.cell.style.background = '#fdf2f1';
            else if (isL && toInt(item.meReal, 0) > 0) e.cell.style.background = '#fdf2f2';
        });
    };

    /* ── 그리드 초기화 : ③ 메뉴(URL)별 ── */
    $scope.initChkCntMenuGrid = function (s, e) {
        s.formatItem.addHandler(function (s, e) {
            if (e.panel !== s.cells) return;
            var col  = s.columns[e.col];
            var item = s.rows[e.row].dataItem;
            if (!item) return;
            var b = col.binding;
            var isExc = (item.isExc === 'Y');

            if (b === 'url')    e.cell.title = item.url    || '';
            if (b === 'ipList') e.cell.title = item.ipList || '';
            if (b === 'excRule') {
                var chip = 'padding:1px 6px; border-radius:3px; font-size:11px; font-weight:bold;';
                if (isExc) {
                    e.cell.innerHTML = '<span style="background:#e8e8e8; color:#444; ' + chip + '" title="' + esc(item.excRule) + '">예외URL</span>'
                        + ' <span style="color:#777; font-size:11px;">(' + esc(item.excRule) + ')</span>';
                } else if (item.excRule) {
                    e.cell.innerHTML = '<span style="background:#e8e8e8; color:#444; ' + chip + '">' + esc(item.excRule) + '</span>';
                } else {
                    e.cell.innerHTML = '<span style="background:#d5f5e3; color:#1e8449; ' + chip + '">실조회</span>';
                }
            }
            if (b === 'meCnt' && !isExc && toInt(item.meCnt, 0) > 0)       markErr(e.cell, true);
            if ((b === 'whTot' || b === 'chkCnt') && !isExc)               markCnt(e.cell, true);
            if (b === 'whReal' && !isExc && toInt(item.whReal, 0) > 0)     markCnt(e.cell, false);
            if (b === 'ipCnt'  && toInt(item.ipCnt, 0) > 1)                markCnt(e.cell, true);

            if (isExc)                            e.cell.style.background = '#f4f6f7';
            else if (toInt(item.meCnt, 0) > 0)    e.cell.style.background = '#fdf2f2';
        });
    };

    /* ── 팝업 오픈 ── */
    $scope.openChkCntPopup = function () {
        chkDay1.value    = todayDate();
        chkDay2.value    = todayDate();
        $scope.chkUserId = '';
        angular.forEach(TH_DEFAULT, function (v, k) { $scope[k] = v; });
        $scope.apHitYn     = 'Y';
        $scope.apProcFg    = '';
        $scope.apGuideShow = false;
        if (!$scope.ipUserId) $scope.ipUserId = '';
        $scope.chkCntData.sourceCollection      = [];
        $scope.chkCntIpData.sourceCollection    = [];
        $scope.chkCntMenuData.sourceCollection  = [];
        $scope.autoPauseData.sourceCollection   = [];
        $scope.pauseHistData.sourceCollection   = [];
        $scope.userInfoLogData.sourceCollection = [];
        $scope.chkCntSummary      = '';
        $scope.chkCntIpSummary    = '';
        $scope.chkCntMenuSummary  = '';
        $scope.autoPauseSummary   = '';
        $scope.pauseHistSummary   = '';
        $scope.userInfoLogSummary = '';
        if (!$scope.$$phase) $scope.$apply();
        $scope.searchChkCnt();
    };

    /* ── ① USER_ID 종합 조회 ── */
    $scope.searchChkCnt = function () {
        validDates();

        var params = {
            chkDay1    : day1Ymd(),
            chkDay2    : day2Ymd(),
            chkUserId  : ($scope.chkUserId || '').trim(),
            thChkToday : toInt($scope.thChkToday, 0),
            thChk5m    : toInt($scope.thChk5m   , 0),
            thBlkToday : toInt($scope.thBlkToday, 0),
            thBlk10m   : toInt($scope.thBlk10m  , 0),
            thLgnToday : toInt($scope.thLgnToday, 0),
            thLgn5m    : toInt($scope.thLgn5m   , 0)
        };
        angular.extend(params, apParams());

        $scope.$broadcast('loadingPopupActive');
        $http.post('/sys/stats/userWebHist/userWebHist/getUserWebChkCntList.sb', params)
            .then(function (res) {
                var list = extractList(res);

                var blkCnt = 0, riskCnt = 0, meUserCnt = 0;
                var apHit = 0, apReal = 0, apEtc = 0, apExc = 0;
                angular.forEach(list, function (r) {
                    if (toInt(r.blkTodayCnt, 0) > 0) blkCnt++;
                    if (toInt(r.meTot, 0) > 0)       meUserCnt++;
                    if (r.risk === 'A' || r.risk === 'B' || r.risk === 'C' || r.risk === 'D') riskCnt++;
                    if (r.apExc === null || r.apExc === undefined) r.apExc = '';
                    var j = apJudge(r);
                    r.apResult = j.txt;               // 엑셀에도 그대로 나가도록 데이터에 넣음
                    if (apSplit(r.apHitCodes).length > 0) {
                        apHit++;
                        if (j.exc) apExc++; else if (j.real) apReal++; else apEtc++;
                    }
                });
                $scope.chkCntData.sourceCollection = list;
                $scope.chkCntData.refresh();

                var html = '조회기간: <b>' + periodLabel() + '</b>'
                    + (includesToday() ? '' : ' <span style="color:#c0392b;font-size:11px;">(과거 기간 — ②④⑦ 최근5분/10분 조건과 자동정지 판정은 현재시각 기준이라 0 으로 나옵니다)</span>')
                    + (params.chkUserId !== ''
                        ? ' &nbsp;|&nbsp; USER_ID 검색: <b>' + esc(params.chkUserId) + '</b> <span style="color:#555;font-size:11px;">(포함 검색 · 임계치 미적용)</span>'
                        : '')
                    + ' &nbsp;|&nbsp; ' + (params.chkUserId !== '' ? '검색 결과' : '조회조건 충족') + ': <b>' + list.length + '명</b>'
                    + ' &nbsp;|&nbsp; 국외IP 이력: <b>' + blkCnt + '명</b>'
                    + ' &nbsp;|&nbsp; 현재메뉴 불일치: <b>' + meUserCnt + '명</b>'
                    + ' &nbsp;|&nbsp; <span style="color:#c0392b;font-weight:bold;">위험 ' + riskCnt + '명</span>'
                    + ' &nbsp;|&nbsp; 자동정지 판정: <b>' + apHit + '명</b>'
                    + ' (<span style="color:#c0392b;font-weight:bold;">실제 ' + apReal + '</span>'
                    + ' · 시험/보류 ' + apEtc + ' · 제외 ' + apExc + ')'
                    + (apReal > AP_CONF.maxPerRun
                        ? ' <span style="color:#c0392b;font-weight:bold;">⚠ 실제 대상 ' + apReal + '명 &gt; ' + AP_CONF.maxPerRun + '명 — Job 은 전원 보류합니다</span>'
                        : '')
                    + ' &nbsp;|&nbsp; <span style="color:#555;font-size:11px;">* 임계치는 OR 조건 · USER_ID 클릭 시 아래 IP별 상세·메뉴별 건수 조회'
                    + ' · 로그인IP수 = TB_WB_LOGIN_HIST_CHK.LOGIN_IP 기준 · 불일치는 스크립트 MENUERR_USER_ID_LIST 대상만 수집</span>';
                $scope.chkCntSummary = $sce.trustAsHtml(html);

                $scope.$broadcast('loadingPopupInactive');
                $scope.searchAutoPause(true);
            }, function () {
                $scope.$broadcast('loadingPopupInactive');
                $scope._popMsg('조회정보(조회건수) 조회 중 오류가 발생했습니다.');
            });
    };

    /* ══════════ ①-1 자동일시정지 내역 ══════════
       맨 위 조회기간 + ① USER_ID 검색(포함) + 구분 콤보
       silent = true 이면 로딩 팝업 생략 (① 조회 시 연쇄 호출용) */
    $scope.searchAutoPause = function (silent) {
        validDates();
        var params = {
            chkDay1   : day1Ymd(),
            chkDay2   : day2Ymd(),
            chkUserId : ($scope.chkUserId || '').trim(),
            apProcFg  : $scope.apProcFg || ''
        };

        if (!silent) $scope.$broadcast('loadingPopupActive');
        $http.post('/sys/stats/userWebHist/userWebHist/getAutoPauseHistList.sb', params)
            .then(function (res) {
                var list = extractList(res);
                angular.forEach(list, function (r) { r.statFg = (r.befStatFg || '-') + ' → ' + (r.aftStatFg || '-'); });
                $scope.autoPauseData.sourceCollection = list;
                $scope.autoPauseData.refresh();

                var real = 0, still = 0, rel = 0, test = 0, hold = 0, users = {};
                angular.forEach(list, function (r) {
                    users[String(r.userId).toUpperCase()] = 1;
                    if (r.apKind === '실제정지') { real++; if (r.apState === '정지중') still++; else rel++; }
                    else if (r.apKind === '시험기록') test++;
                    else if (r.apKind !== '실제(미반영)') hold++;
                });
                $scope.autoPauseSummary = $sce.trustAsHtml(
                    '조회기간: <b>' + periodLabel() + '</b>'
                    + (params.chkUserId !== '' ? ' &nbsp;|&nbsp; USER_ID 검색: <b>' + esc(params.chkUserId) + '</b>' : '')
                    + ' &nbsp;|&nbsp; 내역 <b>' + list.length + '건</b> (' + Object.keys(users).length + '명)'
                    + ' &nbsp;|&nbsp; <span style="color:#c0392b;font-weight:bold;">실제 정지 ' + real + '건</span>'
                    + ' (정지중 <b>' + still + '</b> · 해제 ' + rel + ')'
                    + ' &nbsp;|&nbsp; 보류 ' + hold + '건 &nbsp;|&nbsp; 시험기록 ' + test + '건'
                    + ' &nbsp;|&nbsp; <span style="color:#555;font-size:11px;">* PROC_ID = SP_JOB_AUTO_PAUSE · 해제일시 = 정지 이후 첫 \'50 → 50 아님\' 사용자정보 변경 로그 · USER_ID 클릭 시 ② 상세 조회</span>');

                if (!silent) $scope.$broadcast('loadingPopupInactive');
            }, function () {
                if (!silent) $scope.$broadcast('loadingPopupInactive');
                $scope._popMsg('자동일시정지 내역 조회 중 오류가 발생했습니다.');
            });
    };

    $scope.initAutoPauseGrid = function (s, e) {
        var KIND = {
            '실제정지'     : { c: C_ERR    , b: true  },
            '실제(미반영)' : { c: C_SUB    , b: false },
            '대상외 보류'  : { c: C_WARN   , b: true  },
            '인원초과 보류': { c: C_WARN   , b: true  },
            '시험기록'     : { c: '#2471a3', b: false }
        };
        s.formatItem.addHandler(function (s, e) {
            if (e.panel !== s.cells) return;
            var col = s.columns[e.col], item = s.rows[e.row].dataItem;
            if (!item) return;
            var b = col.binding;

            if (b === 'userId') { wijmo.addClass(e.cell, 'wijLink'); e.cell.title = '이 사용자의 IP별 상세 / 메뉴별 건수 보기'; }
            if (b === 'apKind') { var k = KIND[item.apKind]; if (k) e.cell.innerHTML = tag(esc(item.apKind), k.c, k.b); }
            if (b === 'apRealCodes') e.cell.innerHTML = apCodesHtml(item.apRealCodes, 'Y');
            if (b === 'apTestCodes') e.cell.innerHTML = apCodesHtml(item.apTestCodes, 'T');
            if (b === 'apState') {
                var st = item.apState || '-';
                if (st === '정지중')               e.cell.innerHTML = tag('정지중', C_ERR, true);
                else if (st.indexOf('해제') === 0) e.cell.innerHTML = tag(esc(st), '#1e8449', true);
            }
            if (b === 'targetYn' && item.targetYn === 'Y') markErr(e.cell, true);
            if (b === 'ipHit'    && item.ipHit    === 'Y') markErr(e.cell, true);
            if (b === 'remark' || b === 'summaryTxt' || b === 'url5mTop') e.cell.title = item[b] || '';

            if (item.apKind === '실제정지') e.cell.style.background = (item.apState === '정지중') ? '#fdf2f1' : '#f4fbf6';
        });

        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col], row = s.rows[ht.row].dataItem;
                if (col.binding === 'userId' && row && row.userId) {
                    $scope.ipUserId = row.userId;
                    $scope.searchChkCntIp();
                    if (!$scope.$$phase) $scope.$apply();
                }
            }
        });
    };

    /* ── ② 0건 시 원천 데이터 진단 (ETC050 동일) ── */
    function diagChkCntIp(params) {
        $http.post('/sys/stats/userWebHist/userWebHist/getUserWebChkCntIpDiag.sb', params)
            .then(function (res) {
                var d = extractOne(res) || {};
                var dgChkAll = toInt(d.dgChkAll, 0), dgChkIpd = toInt(d.dgChkIpd, 0), dgLgn = toInt(d.dgLgn, 0);
                var dgBlk    = toInt(d.dgBlk   , 0), dgBlkAll = toInt(d.dgBlkAll, 0), dgSec = toInt(d.dgSec, 0);

                var why = '<span style="color:#1a5276;font-weight:bold;">' + esc(params.ipUserId) + '</span> 의 조회기간(' + periodLabel() + ') 데이터가 없습니다.'
                    + ' &nbsp;|&nbsp; <span style="font-size:11px;color:#555;">원천 데이터 진단 → '
                    + '조회건수 알림 <b>' + dgChkAll + '건</b>'
                    + (dgChkAll > 0 ? ' (IPD 포함 <b>' + dgChkIpd + '건</b>)' : '')
                    + ' · 로그인 시도 알림 <b>' + dgLgn + '건</b>'
                    + ' · 국외IP 기간 <b>' + dgBlk + '건</b>'
                    + ' · 로그인차단(SecViol) <b>' + dgSec + '건</b>'
                    + (dgBlkAll > dgBlk ? ' (전체기간 ' + dgBlkAll + '건)' : '')
                    + '</span><br><span style="font-size:11px;color:#777;">';
                if (dgChkAll === 0 && dgBlk === 0) {
                    if (dgLgn > 0) {
                        why += '→ 로그인 시도 집계만 있고 조회건수·국외IP 이력이 없습니다. '
                            + '<span style="color:#c0392b;">로그인만 반복되고 조회가 0건이면 자동 로그인 클라이언트 또는 ID 형식 불일치를 의심하세요.</span>';
                    } else {
                        why += '→ 이 아이디로 조회기간에 수집된 조회건수·로그인·국외IP 데이터가 모두 없습니다. USER_ID 철자를 확인하세요.';
                    }
                } else if (dgChkAll > 0 && dgChkIpd === 0) {
                    why += '→ 조회건수 알림은 있으나 <b>IPD 필드가 없는 구버전 메시지</b>입니다. '
                        + '<span style="color:#c0392b;">menu_chk_monitor_webhook.sh 를 재기동</span>해야 IP별 상세가 쌓입니다.';
                } else if (dgChkAll > 0) {
                    why += '→ IPD 가 포함된 알림이 있는데 파싱 결과가 0건입니다. ALARM_MSG 의 IPD 형식을 확인하세요.';
                } else {
                    why += '→ 국외IP 이력만 있고 조회건수 알림이 없습니다. 이 사용자는 조회기간에 메뉴권한체크를 한 번도 호출하지 않았습니다.';
                }
                why += '</span>';
                $scope.chkCntIpSummary = $sce.trustAsHtml(why);
            }, function () {
                /* 진단 실패는 무시 — 0건 안내만 유지 */
            });
    }

    /* ── ② USER_ID + IP별 상세 조회 (③ 메뉴별도 함께) ── */
    $scope.searchChkCntIp = function () {
        if (!$scope.ipUserId || $scope.ipUserId.replace(/\s/g, '') === '') {
            $scope._popMsg('USER_ID 를 입력하세요.');
            return;
        }
        validDates();

        var params = {
            chkDay1  : day1Ymd(),
            chkDay2  : day2Ymd(),
            ipUserId : $scope.ipUserId.trim()
        };

        $scope.$broadcast('loadingPopupActive');
        $http.post('/sys/stats/userWebHist/userWebHist/getUserWebChkCntIpList.sb', params)
            .then(function (res) {
                var list = extractList(res);
                $scope.chkCntIpData.sourceCollection = list;
                $scope.chkCntIpData.refresh();

                if (list.length === 0) {
                    $scope.chkCntIpSummary = $sce.trustAsHtml('대상 USER_ID: <b>' + esc(params.ipUserId) + '</b> &nbsp;|&nbsp; 조회기간: <b>' + periodLabel() + '</b> &nbsp;|&nbsp; 데이터 없음 — 원천 데이터 진단 중…');
                    diagChkCntIp(params);
                } else {
                    var sumTot = 0, sumReal = 0, sumBlk = 0, approxCnt = 0, sumLgn = 0, sumMeTot = 0, sumMeExc = 0;
                    angular.forEach(list, function (r) {
                        sumTot  += toInt(r.todayTot   , 0);
                        sumReal += toInt(r.todayReal  , 0);
                        sumBlk  += toInt(r.blkTodayCnt, 0);
                        if (toInt(r.isApprox, 0) === 1) approxCnt++;
                        if (r.gubun === 'L') {
                            sumLgn   += toInt(r.lgnTodayTot, 0);
                            sumMeTot += toInt(r.meTot, 0);
                            sumMeExc += toInt(r.meExc, 0);
                        }
                    });
                    var html = '대상 USER_ID: <b>' + esc(params.ipUserId) + '</b>'
                        + ' &nbsp;|&nbsp; 조회기간: <b>' + periodLabel() + '</b>'
                        + (approxCnt > 0 ? ' &nbsp;|&nbsp; <span style="color:#c0392b;">(근사) ' + approxCnt + '행 — 스크립트 재기동 전 알림은 IP별 건수(IPD)가 없어 마지막 IP 에 합산</span>' : '')
                        + ' &nbsp;|&nbsp; 행 수: <b>' + list.length + '개</b>'
                        + ' &nbsp;|&nbsp; 조회 총/실: <b>' + sumTot + ' / ' + sumReal + '건</b>'
                        + ' &nbsp;|&nbsp; 로그인 합계: <b>' + sumLgn + '건</b>'
                        + ' &nbsp;|&nbsp; 불일치 총/예외/실: <b>' + sumMeTot + ' / ' + sumMeExc + ' / ' + (sumMeTot - sumMeExc) + '건</b>'
                        + ' &nbsp;|&nbsp; 차단접속(국외IP+SecViol): <b>' + sumBlk + '건</b>'
                        + ' &nbsp;|&nbsp; <span style="color:#555;font-size:11px;">* NOIP = IP 없는 FN_GET_MENU_CHK_YN 호출 · (IP 없음) = 로그인·현재메뉴불일치 집계 · 차단 = 국외IP + ORA-20001 Login Blocked</span>';
                    $scope.chkCntIpSummary = $sce.trustAsHtml(html);
                }

                $scope.$broadcast('loadingPopupInactive');
                $scope.searchPauseHist(true);
                $scope.searchUserInfoLog(true);
                $scope.searchChkCntMenu();
            }, function () {
                $scope.$broadcast('loadingPopupInactive');
                $scope._popMsg('조회정보(IP별 상세) 조회 중 오류가 발생했습니다.');
            });
    };

    /* ── ③ 메뉴(URL)별 건수 조회 ── */
    $scope.searchChkCntMenu = function () {
        if (!$scope.ipUserId || $scope.ipUserId.replace(/\s/g, '') === '') return;
        validDates();

        var params = {
            chkDay1  : day1Ymd(),
            chkDay2  : day2Ymd(),
            ipUserId : $scope.ipUserId.trim()
        };

        $scope.$broadcast('loadingPopupActive');
        $http.post('/sys/stats/userWebHist/userWebHist/getUserWebChkCntMenuList.sb', params)
            .then(function (res) {
                var list = extractList(res);
                $scope.chkCntMenuData.sourceCollection = list;
                $scope.chkCntMenuData.refresh();

                var uExc = 0, whTot = 0, whReal = 0, whRows = 0, chkTot = 0, chkExc = 0, meTot = 0, meExc = 0;
                angular.forEach(list, function (r) {
                    var isExc = (r.isExc === 'Y');
                    var wt = toInt(r.whTot, 0), ct = toInt(r.chkCnt, 0), mc = toInt(r.meCnt, 0);
                    whTot += wt; whReal += toInt(r.whReal, 0); chkTot += ct; meTot += mc;
                    if (wt > 0) whRows++;
                    if (isExc) { uExc++; chkExc += ct; meExc += mc; }
                });
                var html = 'URL 종류: <b>' + list.length + '개</b> (예외URL <b>' + uExc + '개</b>)'
                    + ' &nbsp;|&nbsp; 웹훅 호출 총/실: <b>' + whTot + ' / ' + whReal + '건</b>'
                    + ' &nbsp;|&nbsp; HIST 호출 총/예외/실: <b>' + chkTot + ' / ' + chkExc + ' / ' + (chkTot - chkExc) + '건</b>'
                    + ' &nbsp;|&nbsp; 불일치 총/예외/실: <b>' + meTot + ' / ' + meExc + ' / ' + (meTot - meExc) + '건</b>'
                    + (whRows === 0 && list.length > 0
                        ? ' &nbsp;|&nbsp; <span style="color:#c0392b;font-size:11px;">웹훅 URLD 없음 — menu_chk_monitor_webhook.sh v3 재기동 이후 알림부터 집계됩니다</span>'
                        : '')
                    + (list.length === 0
                        ? ' &nbsp;|&nbsp; <span style="color:#999;font-size:11px;">조회기간에 메뉴(URL) 호출 이력이 없습니다.</span>'
                        : '')
                    + ' &nbsp;|&nbsp; <span style="color:#555;font-size:11px;">* 웹훅 = 스크립트 URLD(전체 사용자) · HIST = TB_WB_LOGIN_HIST_CHK(PROC_FG=\'C\', 기록 대상만) · 회색 = 예외URL · 붉은 행 = 현재메뉴 불일치 발생 · NOURL = YN 호출 · ETC = URL 표기초과</span>';
                $scope.chkCntMenuSummary = $sce.trustAsHtml(html);

                $scope.$broadcast('loadingPopupInactive');
            }, function () {
                $scope.$broadcast('loadingPopupInactive');
                $scope._popMsg('조회정보(메뉴별 건수) 조회 중 오류가 발생했습니다.');
            });
    };

    /* ── 초기화 ── */
    $scope.resetChkCnt = function () {
        chkDay1.value    = todayDate();
        chkDay2.value    = todayDate();
        $scope.chkUserId = '';
        angular.forEach(TH_DEFAULT, function (v, k) { $scope[k] = v; });
        $scope.apHitYn  = 'Y';
        $scope.apProcFg = '';
        $scope.searchChkCnt();
    };

    /* ══════════ 일시정지 ══════════ */

    /* 선택 USER_ID 의 ① 그리드 행 찾기 */
    function findSumRow(userId) {
        var key = String(userId || '').toUpperCase();
        var rows = $scope.chkCntData.sourceCollection || [];
        for (var i = 0; i < rows.length; i++) {
            if (String(rows[i].userId || '').toUpperCase() === key) return rows[i];
        }
        return null;
    }

    /* 화면 요약 텍스트 (이력 SUMMARY_TXT) */
    function buildPauseSummary(r) {
        if (!r) return '조회기간 ' + periodLabel() + ' | ① 종합 목록에 없는 USER_ID — 수치 없음';
        var risk = RISK_MAP[r.risk] ? r.risk + '(' + RISK_MAP[r.risk].txt + ')' : '-';
        return [
            '조회기간 ' + periodLabel(),
            '위험도 ' + risk,
            '기간 조회 실/총 ' + toInt(r.todayReal, 0) + '/' + toInt(r.todayTot, 0),
            '5분 실건수 ' + toInt(r.min5Real, 0),
            '불일치 실건수 ' + toInt(r.meReal, 0),
            '로그인 합계 ' + toInt(r.lgnTodayTot, 0) + ' (실패 ' + toInt(r.lgnTodayErr, 0) + ', 5분실패 ' + toInt(r.lgnMin5Err, 0) + ')',
            '로그인 IP수 ' + toInt(r.lgnIpCnt, 0),
            '국외IP 기간/10분 ' + toInt(r.blkTodayCnt, 0) + '/' + toInt(r.blkMin10Cnt, 0),
            '조회 최종IP ' + (r.chkLastIp || '-') + ' (IP수 ' + toInt(r.chkIpCnt, 0) + ', 국외IP일치 ' + (r.ipHit || 'N') + ')',
            '국가 ' + (r.countryCode || '-'),
            '사유 ' + (r.blockReason || '-'),
            '자동정지 판정 ' + (apSplit(r.apHitCodes).length ? r.apHitCodes + ' (' + (r.apResult || '-') + ')' : '-')
        ].join(' | ');
    }

    /* 일시정지 / 해제 팝업 열기 — ② USER_ID 입력값 대상 (mode : 'P' 일시정지, 'R' 해제) */
    function openPauseLayer(mode) {
        var uid = ($scope.ipUserId || '').trim();
        if (uid === '') { $scope._popMsg('USER_ID 를 입력하거나 ① 목록에서 선택하세요.'); return; }
        var row = findSumRow(uid);

        // wj-popup 은 닫힐 때 DOM 에서 제거되므로 반드시 show() 후에 scope 를 가져온다
        $scope.userWebChkCntPauseLayer.show(true);
        var scope = agrid.getScope('userWebChkCntPauseCtrl');
        scope.setPauseTarget({
            mode     : mode,
            userId   : uid,
            row      : row,
            summary  : buildPauseSummary(row),
            chkDay1  : day1Ymd(),
            chkDay2  : day2Ymd(),
            onDone   : function () { $scope.searchPauseHist(); }
        });
    }
    $scope.openPausePopup  = function () { openPauseLayer('P'); };
    $scope.openResumePopup = function () { openPauseLayer('R'); };

    /* ④ 일시정지 이력 조회 — 맨 위 조회기간 + ② USER_ID (비우면 전체)
       silent = true 이면 로딩 팝업 생략 (② 조회 시 연쇄 호출용) */
    $scope.searchPauseHist = function (silent) {
        validDates();
        var uid = ($scope.ipUserId || '').trim();
        var params = { chkDay1: day1Ymd(), chkDay2: day2Ymd(), userId: uid };

        if (!silent) $scope.$broadcast('loadingPopupActive');
        $http.post('/sys/stats/userWebHist/userWebHist/getUserPauseHistList.sb', params)
            .then(function (res) {
                var list = extractList(res);
                $scope.pauseHistData.sourceCollection = list;
                $scope.pauseHistData.refresh();

                var pCnt = 0, rCnt = 0, tCnt = 0;
                angular.forEach(list, function (r) {
                    if (r.procFg === 'P') pCnt++;
                    if (r.procFg === 'R') rCnt++;
                    if (r.procFg === 'T') tCnt++;
                });
                $scope.pauseHistSummary = $sce.trustAsHtml(
                    '조회기간: <b>' + periodLabel() + '</b>'
                    + ' &nbsp;|&nbsp; USER_ID: <b>' + (uid === '' ? '전체' : esc(uid)) + '</b>'
                    + ' &nbsp;|&nbsp; 이력 <b>' + list.length + '건</b> (일시정지 ' + pCnt + ' · 해제 ' + rCnt + ' · 시험 ' + tCnt + ')');

                if (!silent) $scope.$broadcast('loadingPopupInactive');
            }, function () {
                if (!silent) $scope.$broadcast('loadingPopupInactive');
                $scope._popMsg('일시정지 이력 조회 중 오류가 발생했습니다.');
            });
    };

    $scope.initPauseHistGrid = function (s, e) {
        s.formatItem.addHandler(function (s, e) {
            if (e.panel !== s.cells) return;
            var col = s.columns[e.col], item = s.rows[e.row].dataItem;
            if (!item) return;
            if (col.binding === 'resultFg') {
                e.cell.innerHTML = item.resultFg === 'Y' ? tag('반영', '#1e8449', true) : tag('미반영', C_SUB, false);
            }
            if (col.binding === 'statFg') {
                e.cell.innerHTML = esc(item.befStatFg || '-') + ' → ' + esc(item.aftStatFg || '-');
            }
            if (col.binding === 'risk') {
                var m = RISK_MAP[item.risk];
                e.cell.innerHTML = m ? tag(m.txt, m.color, m.bold) : tag('-', C_SUB, false);
            }
            if (col.binding === 'summaryTxt' || col.binding === 'remark') e.cell.title = item[col.binding] || '';
            if (item.resultFg !== 'Y') e.cell.style.color = '#999';
        });
    };

    /* ── 엑셀 다운로드 ── */
    function excelDown(flex, fileName) {
        if (!flex || flex.rows.length <= 0) {
            $scope._popMsg('다운로드 할 데이터가 없습니다.');
            return;
        }
        $scope.$broadcast('loadingPopupActive');
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(flex, {
                includeColumnHeaders: true,
                includeCellStyles   : true,
                includeColumns      : function (column) { return column.visible; }
            }, fileName + '_' + getToday() + '.xlsx', function () {
                $timeout(function () { $scope.$broadcast('loadingPopupInactive'); }, 10);
            });
        }, 10);
    }
    $scope.excelChkCnt      = function () { excelDown($scope.chkCntFlex    , '조회건수_USER_ID종합'); };
    $scope.excelAutoPause   = function () { excelDown($scope.autoPauseFlex , '자동일시정지내역'); };
    $scope.excelChkCntIp    = function () { excelDown($scope.chkCntIpFlex  , '조회건수_IP별상세'); };
    $scope.excelChkCntMenu  = function () { excelDown($scope.chkCntMenuFlex, '조회건수_메뉴별'); };

    /* ══════════ ⑤ 웹사용자정보변경이력 ══════════ */
    $scope.initUserInfoLogGrid = function (s, e) {
        s.formatItem.addHandler(function (s, e) {
            if (e.panel !== s.cells) return;
            var col = s.columns[e.col], item = s.rows[e.row].dataItem;
            if (!item) return;
            var b = col.binding;

            if (b === 'pauseChg') {
                if (item.pauseChg === '해제')      e.cell.innerHTML = tag('일시정지 해제', C_ERR , true);
                else if (item.pauseChg === '정지') e.cell.innerHTML = tag('일시정지'     , C_WARN, true);
                else                               e.cell.innerHTML = '';
            }
            if (b === 'statChg'  && item.pauseChg)          markErr(e.cell, true);
            if (b === 'pwdChgYn' && item.pwdChgYn === 'Y')  markErr(e.cell, false);
            if (b === 'chgItems') e.cell.title = item.chgItems || '';

            if (item.pauseChg === '해제')      e.cell.style.background = '#fdf2f1';
            else if (item.pauseChg === '정지') e.cell.style.background = '#fff8e1';
        });
    };

    /* ⑤ 조회 — 맨 위 조회기간 + ② USER_ID (비우면 전체) */
    $scope.searchUserInfoLog = function (silent) {
        validDates();
        var uid = ($scope.ipUserId || '').trim();
        var params = { chkDay1: day1Ymd(), chkDay2: day2Ymd(), userId: uid };

        if (!silent) $scope.$broadcast('loadingPopupActive');
        $http.post('/sys/stats/userWebHist/userWebHist/getUserInfoLogList.sb', params)
            .then(function (res) {
                var list = extractList(res);
                $scope.userInfoLogData.sourceCollection = list;
                $scope.userInfoLogData.refresh();

                var relCnt = 0, pauseCnt = 0;
                angular.forEach(list, function (r) {
                    if (r.pauseChg === '해제') relCnt++;
                    if (r.pauseChg === '정지') pauseCnt++;
                });
                $scope.userInfoLogSummary = $sce.trustAsHtml(
                    '조회기간: <b>' + periodLabel() + '</b>'
                    + ' &nbsp;|&nbsp; USER_ID: <b>' + (uid === '' ? '전체' : esc(uid)) + '</b>'
                    + ' &nbsp;|&nbsp; 변경 <b>' + list.length + '건</b>'
                    + ' &nbsp;|&nbsp; <span style="color:#c0392b;font-weight:bold;">일시정지 해제 ' + relCnt + '건</span>'
                    + ' &nbsp;|&nbsp; 일시정지 ' + pauseCnt + '건'
                    + ' &nbsp;|&nbsp; <span style="color:#555;font-size:11px;">* TB_LG_WB_USER_INFO_LOG 기준 · 붉은 행 = 일시정지(50)가 풀린 변경 · 비밀번호/자동로그인은 변경여부만 표시</span>');

                if (!silent) $scope.$broadcast('loadingPopupInactive');
            }, function () {
                if (!silent) $scope.$broadcast('loadingPopupInactive');
                $scope._popMsg('웹사용자정보변경이력 조회 중 오류가 발생했습니다.');
            });
    };

    $scope.excelUserInfoLog = function () { excelDown($scope.userInfoLogFlex, '웹사용자정보변경이력'); };

    /* ── 팝업 닫기 ── */
    $scope.close = function () {
        $scope.userWebChkCntLayer.hide();
    };
    $scope.closeChkCntPopup = $scope.close;

}]);

/****************************************************************
 * 일시정지 / 해제 확인 팝업 (비고 + 비밀번호) — 처리 후 이력 등록
 ****************************************************************/
app.controller('userWebChkCntPauseCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('userWebChkCntPauseCtrl', $scope, $http, $timeout, false));

    var _target = null;
    $scope.mode = 'P';

    $scope.setPauseTarget = function (t) {
        _target = t;
        $scope.mode          = (t.mode === 'R') ? 'R' : 'P';
        $scope.pauseUserId   = t.userId;
        $scope.pauseSummary  = t.summary;
        $scope.pauseRemark   = '';
        $scope.pausePassword = '';
        if (!$scope.$$phase) $scope.$apply();
    };

    function n(v) { var x = parseInt(String(v === undefined || v === null ? '' : v).replace(/[^0-9]/g, ''), 10); return isNaN(x) ? 0 : x; }

    $scope.doExec = function () {
        if (!_target) return;
        var isResume = ($scope.mode === 'R');
        var procNm   = isResume ? '일시정지 해제' : '일시정지';

        if (!$scope.pausePassword) { $scope._popMsg('비밀번호를 입력하세요.'); return; }
        if (!$scope.pauseRemark || $scope.pauseRemark.trim() === '') { $scope._popMsg('비고를 입력하세요.'); return; }

        var r = _target.row || {};
        var params = {
            userId      : _target.userId,
            password    : $scope.pausePassword,
            remark      : $scope.pauseRemark.trim(),
            summaryTxt  : _target.summary,
            chkDay1     : _target.chkDay1,
            chkDay2     : _target.chkDay2,
            risk        : r.risk || '-',
            todayReal   : n(r.todayReal),   todayTot   : n(r.todayTot),
            min5Real    : n(r.min5Real),    meReal     : n(r.meReal),
            lgnTodayTot : n(r.lgnTodayTot), lgnTodayErr: n(r.lgnTodayErr), lgnMin5Err : n(r.lgnMin5Err),
            lgnIpCnt    : n(r.lgnIpCnt),
            blkTodayCnt : n(r.blkTodayCnt), blkMin10Cnt: n(r.blkMin10Cnt),
            chkLastIp   : r.chkLastIp || '-', chkIpCnt : n(r.chkIpCnt), ipHit : r.ipHit || 'N',
            countryCode : r.countryCode || '-',
            blockReason : (r.blockReason || '-').substring(0, 500)
        };

        var url = isResume
            ? '/sys/stats/userWebHist/userWebHist/getResumeUserIdWithHist.sb'
            : '/sys/stats/userWebHist/userWebHist/getPauseUserIdWithHist.sb';

        $scope.$broadcast('loadingPopupActive');
        $http.post(url, params)
            .then(function (res) {
                $scope.$broadcast('loadingPopupInactive');
                var d = res.data || {};
                var result = parseInt((d.data !== undefined ? d.data : d), 10);
                if (result === 1) {
                    $scope._popMsg(_target.userId + ' ' + procNm + ' 처리 및 이력 등록 완료');
                    $scope.close();
                    if (_target.onDone) _target.onDone();
                } else if (result === 0) {
                    $scope._popMsg(isResume
                        ? '일시정지(50) 상태가 아니어서 해제할 수 없습니다. 이력에는 미반영으로 기록되었습니다.'
                        : '이미 일시정지 상태이거나 정상(00) 상태가 아닙니다. 이력에는 미반영으로 기록되었습니다.');
                    $scope.close();
                    if (_target.onDone) _target.onDone();
                } else if (result === -1) {
                    $scope._popMsg('비밀번호가 일치하지 않습니다.');
                    $scope.pausePassword = '';
                } else if (result === -2) {
                    $scope._popMsg('존재하지 않는 USER_ID 입니다.');
                } else {
                    $scope._popMsg(procNm + ' 처리 중 오류가 발생했습니다.');
                }
            }, function () {
                $scope.$broadcast('loadingPopupInactive');
                $scope._popMsg(procNm + ' 처리 중 오류가 발생했습니다.');
            });
    };
    $scope.doPause = $scope.doExec;   // 하위 호환

    $scope.close = function () {
        $scope.userWebChkCntPauseLayer.hide();
    };
}]);