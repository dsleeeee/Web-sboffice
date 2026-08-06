<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<style>
  #smsUserRegistView{
    --sur-blue:#1B5EE3; --sur-blue-soft:#EAF1FE; --sur-blue-dark:#144CBB;
    --sur-ink:#171A21; --sur-ink-2:#3C4049; --sur-ink-3:#6B7078; --sur-ink-4:#9297A0; --sur-ink-5:#C4C8CE;
    --sur-line:#E4E6EA; --sur-line-strong:#D3D6DB;
    --sur-surface:#ffffff; --sur-surface-1:#F6F7F9; --sur-surface-2:#F0F1F4;
    --sur-green:#1C9A5B; --sur-green-soft:#E8F7EE;
    --sur-amber:#B7791F; --sur-amber-soft:#FDF3E1;
    --sur-red:#D3413C; --sur-red-soft:#FCEBEA;
    --sur-radius:10px;
  }
  #smsUserRegistView{background:var(--sur-surface-1); color:var(--sur-ink-2); font-size:14px; line-height:1.6; word-break:keep-all; min-height:100vh; padding:56px 28px 28px;}
  #smsUserRegistView *{box-sizing:border-box;}
  #smsUserRegistView .sur-content{display:flex; justify-content:center;}
  #smsUserRegistView .sur-panel{width:100%; max-width:640px;}

  #smsUserRegistView .sur-page-head{margin-bottom:24px;}
  #smsUserRegistView .sur-page-title{font-size:20px; font-weight:800; color:var(--sur-ink); letter-spacing:-0.01em;}
  #smsUserRegistView .sur-page-desc{margin-top:6px; font-size:13px; color:var(--sur-ink-4);}

  /* Stepper */
  #smsUserRegistView .sur-stepper{display:flex; align-items:center; margin-bottom:28px; background:var(--sur-surface); border:1px solid var(--sur-line); border-radius:var(--sur-radius); padding:18px 20px;}
  #smsUserRegistView .sur-step{display:flex; align-items:center; gap:10px; flex:1;}
  #smsUserRegistView .sur-step-circle{
    width:26px; height:26px; border-radius:50%; display:flex; align-items:center; justify-content:center;
    font-size:12px; font-weight:700; flex-shrink:0; border:1.5px solid var(--sur-line-strong); color:var(--sur-ink-4); background:var(--sur-surface);
  }
  #smsUserRegistView .sur-step-label{font-size:12.5px; font-weight:600; color:var(--sur-ink-4); white-space:nowrap;}
  #smsUserRegistView .sur-step.sur-done .sur-step-circle{background:var(--sur-green); border-color:var(--sur-green); color:#fff;}
  #smsUserRegistView .sur-step.sur-done .sur-step-label{color:var(--sur-ink-2);}
  #smsUserRegistView .sur-step.sur-current .sur-step-circle{background:var(--sur-blue); border-color:var(--sur-blue); color:#fff;}
  #smsUserRegistView .sur-step.sur-current .sur-step-label{color:var(--sur-ink);}
  #smsUserRegistView .sur-step-line{height:1.5px; background:var(--sur-line); flex:1; margin:0 4px; max-width:40px;}
  #smsUserRegistView .sur-step-line.sur-done{background:var(--sur-green);}

  /* Card */
  #smsUserRegistView .sur-card{background:var(--sur-surface); border:1px solid var(--sur-line); border-radius:var(--sur-radius); overflow:hidden;}
  #smsUserRegistView .sur-card + .sur-card{margin-top:16px;}
  #smsUserRegistView .sur-card-head{padding:18px 22px; border-bottom:1px solid var(--sur-line); display:flex; align-items:center; justify-content:space-between;}
  #smsUserRegistView .sur-card-head h2{font-size:14.5px; font-weight:700; color:var(--sur-ink);}
  #smsUserRegistView .sur-card-body{padding:22px;}
  #smsUserRegistView .sur-badge{font-size:11px; font-weight:700; padding:3px 8px; border-radius:100px;}
  #smsUserRegistView .sur-badge-required{background:var(--sur-red-soft); color:var(--sur-red);}

  /* Screen 1: consent */
  #smsUserRegistView .sur-consent-item{border-bottom:1px solid var(--sur-line);}
  #smsUserRegistView .sur-consent-item:last-child{border-bottom:none;}
  #smsUserRegistView .sur-consent-row{display:flex; align-items:center; justify-content:space-between; padding:16px 22px; cursor:pointer;}
  #smsUserRegistView .sur-consent-row-left{display:flex; align-items:center; gap:10px;}
  #smsUserRegistView .sur-checkbox{
    width:20px; height:20px; border-radius:5px; border:1.5px solid var(--sur-line-strong); flex-shrink:0;
    display:flex; align-items:center; justify-content:center; background:var(--sur-surface);
  }
  #smsUserRegistView .sur-checkbox.sur-checked{background:var(--sur-blue); border-color:var(--sur-blue);}
  #smsUserRegistView .sur-checkbox svg{opacity:0;}
  #smsUserRegistView .sur-checkbox.sur-checked svg{opacity:1;}
  #smsUserRegistView .sur-consent-title{font-size:13.5px; font-weight:600; color:var(--sur-ink);}
  #smsUserRegistView .sur-consent-toggle{font-size:12px; color:var(--sur-ink-4); display:flex; align-items:center; gap:4px;}
  #smsUserRegistView .sur-consent-detail{padding:0 22px 18px 52px; font-size:12.5px; color:var(--sur-ink-3); line-height:1.7;}
  #smsUserRegistView .sur-consent-body{background:var(--sur-surface-1); border:1px solid var(--sur-line); border-radius:10px; padding:4px 16px;}

  #smsUserRegistView .sur-ci-row{display:flex; gap:16px; padding:11px 0; border-bottom:1px solid var(--sur-line);}
  #smsUserRegistView .sur-ci-row:last-child{border-bottom:none;}
  #smsUserRegistView .sur-ci-k{width:104px; flex-shrink:0; font-size:12px; font-weight:700; color:var(--sur-ink-3);}
  #smsUserRegistView .sur-ci-v{font-size:12.5px; color:var(--sur-ink-2); line-height:1.6;}

  #smsUserRegistView .sur-consent-list{padding:12px 0; display:flex; flex-direction:column; gap:9px;}
  #smsUserRegistView .sur-consent-list > li{list-style:none; position:relative; padding-left:14px; font-size:12.5px; color:var(--sur-ink-2); line-height:1.6;}
  #smsUserRegistView .sur-consent-list > li::before{content:""; position:absolute; left:0; top:8px; width:4px; height:4px; border-radius:50%; background:var(--sur-ink-4);}
  #smsUserRegistView .sur-consent-sublist{margin-top:6px; padding-left:2px; display:flex; flex-direction:column; gap:5px;}
  #smsUserRegistView .sur-consent-sublist li{list-style:none; position:relative; padding-left:13px; font-size:12px; color:var(--sur-ink-3); line-height:1.55;}
  #smsUserRegistView .sur-consent-sublist li::before{content:"–"; position:absolute; left:0; top:0; color:var(--sur-ink-5);}

  #smsUserRegistView .sur-consent-note{margin:2px 0 12px; padding:13px 14px; background:var(--sur-amber-soft); border-radius:8px; display:flex; flex-direction:column; gap:8px;}
  #smsUserRegistView .sur-consent-note p{font-size:12px; color:#8a5a10; line-height:1.6;}

  #smsUserRegistView .sur-all-agree{display:flex; align-items:center; gap:10px; padding:16px 22px; background:var(--sur-surface-1); border-bottom:1px solid var(--sur-line); cursor:pointer;}
  #smsUserRegistView .sur-all-agree-label{font-size:13.5px; font-weight:700; color:var(--sur-ink);}

  /* Screen 2: register */
  #smsUserRegistView .sur-field-row{display:flex; align-items:center; padding:16px 0; border-bottom:1px solid var(--sur-line);}
  #smsUserRegistView .sur-field-row:last-child{border-bottom:none;}
  #smsUserRegistView .sur-field-label{width:140px; flex-shrink:0; font-size:13px; font-weight:600; color:var(--sur-ink-3);}
  #smsUserRegistView .sur-field-value{font-size:14px; color:var(--sur-ink); font-weight:600;}

  #smsUserRegistView .sur-verify-box{border:1.5px dashed var(--sur-line-strong); border-radius:8px; padding:24px; text-align:center; display:flex; flex-direction:column; align-items:center; gap:12px;}
  #smsUserRegistView .sur-verify-icon{width:44px; height:44px; border-radius:50%; background:var(--sur-blue-soft); display:flex; align-items:center; justify-content:center;}
  #smsUserRegistView .sur-verify-text{font-size:13px; color:var(--sur-ink-4);}

  #smsUserRegistView .sur-btn{
    display:inline-flex; align-items:center; justify-content:center; gap:6px;
    padding:11px 20px; border-radius:8px; font-size:13.5px; font-weight:700; cursor:pointer;
    border:none;
  }
  #smsUserRegistView .sur-btn-primary{background:var(--sur-blue); color:#fff;}
  #smsUserRegistView .sur-btn-primary:disabled{background:var(--sur-ink-5); cursor:not-allowed;}
  #smsUserRegistView .sur-btn-outline{background:var(--sur-surface); color:var(--sur-ink-2); border:1px solid var(--sur-line-strong);}
  #smsUserRegistView .sur-btn-sm{padding:8px 14px; font-size:12.5px;}

  #smsUserRegistView .sur-verified-result{border:1.5px solid var(--sur-green); background:var(--sur-green-soft); border-radius:8px; padding:18px 20px; display:flex; align-items:flex-start; gap:12px;}
  #smsUserRegistView .sur-verified-result .sur-vi-icon{width:22px; height:22px; border-radius:50%; background:var(--sur-green); flex-shrink:0; display:flex; align-items:center; justify-content:center;}
  #smsUserRegistView .sur-verified-result-title{font-size:13.5px; font-weight:700; color:var(--sur-ink);}
  #smsUserRegistView .sur-verified-result-desc{font-size:12.5px; color:var(--sur-ink-3); margin-top:4px;}
  #smsUserRegistView .sur-verified-info{margin-top:12px; display:flex; flex-direction:column; gap:6px;}
  #smsUserRegistView .sur-verified-info-row{display:flex; gap:8px; font-size:12.5px;}
  #smsUserRegistView .sur-verified-info-row .sur-k{color:var(--sur-ink-4); width:60px; flex-shrink:0;}
  #smsUserRegistView .sur-verified-info-row .sur-v{color:var(--sur-ink-2); font-weight:600; font-variant-numeric:tabular-nums;}

  #smsUserRegistView .sur-dup-error{border:1.5px solid var(--sur-red); background:var(--sur-red-soft); border-radius:8px; padding:16px 18px; display:flex; align-items:flex-start; gap:10px; font-size:12.5px; color:var(--sur-ink-2);}

  #smsUserRegistView .sur-verify-fail-box{border:1.5px dashed var(--sur-red); border-radius:8px; padding:24px; text-align:center; display:flex; flex-direction:column; align-items:center; gap:10px; background:var(--sur-red-soft);}
  #smsUserRegistView .sur-verify-fail-icon{width:44px; height:44px; border-radius:50%; background:var(--sur-surface); border:1.5px solid var(--sur-red); display:flex; align-items:center; justify-content:center;}
  #smsUserRegistView .sur-verify-fail-title{font-size:13.5px; font-weight:700; color:var(--sur-ink);}
  #smsUserRegistView .sur-verify-fail-desc{font-size:12.5px; color:var(--sur-ink-3); line-height:1.6;}
  #smsUserRegistView .sur-verify-fail-reason{font-size:12px; color:var(--sur-red); background:var(--sur-surface); border:1px solid var(--sur-red); border-radius:6px; padding:6px 12px; font-weight:600;}

  #smsUserRegistView .sur-actions{display:flex; gap:10px; margin-top:20px;}
  #smsUserRegistView .sur-actions .sur-btn{flex:1;}

  /* Screen 3: done */
  #smsUserRegistView .sur-done-wrap{text-align:center; padding:56px 22px;}
  #smsUserRegistView .sur-done-icon{width:64px; height:64px; border-radius:50%; background:var(--sur-green-soft); border:2px solid var(--sur-green); display:flex; align-items:center; justify-content:center; margin:0 auto 20px;}
  #smsUserRegistView .sur-done-title{font-size:18px; font-weight:800; color:var(--sur-ink);}
  #smsUserRegistView .sur-done-desc{margin-top:8px; font-size:13px; color:var(--sur-ink-4);}
  #smsUserRegistView .sur-done-summary{margin-top:28px; text-align:left; background:var(--sur-surface-1); border-radius:8px; padding:18px 20px;}

  #smsUserRegistView .sur-delete-row{display:flex; justify-content:center; padding:16px 22px 22px;}
  #smsUserRegistView .sur-btn-danger-outline{
    display:inline-flex; align-items:center; justify-content:center; width:100%; padding:13px 20px; border-radius:8px;
    background:var(--sur-surface); border:1px solid var(--sur-red); color:var(--sur-red); font-size:14px; font-weight:700; cursor:pointer;
  }

  @media (max-width:760px){
    #smsUserRegistView .sur-field-row{flex-direction:column; align-items:flex-start; gap:6px;}
    #smsUserRegistView .sur-field-label{width:auto;}
  }
</style>

<div id="smsUserRegistView" name="smsUserRegistView" class="subCon" ng-controller="smsUserRegistCtrl">
  <div class="sur-content">
    <div class="sur-panel">

      <!-- ===================== STEP 1: 동의 화면 ===================== -->
      <div ng-show="step==1">
        <div class="sur-page-head">
          <div class="sur-page-title">SMS 사용 등록</div>
          <div class="sur-page-desc">본인인증 및 개인정보 수집·이용에 동의해야 다음 단계로 진행할 수 있습니다.</div>
        </div>

        <div class="sur-stepper">
          <div class="sur-step sur-current"><div class="sur-step-circle">1</div><div class="sur-step-label">약관 동의</div></div>
          <div class="sur-step-line"></div>
          <div class="sur-step"><div class="sur-step-circle">2</div><div class="sur-step-label">본인인증 · 등록</div></div>
          <div class="sur-step-line"></div>
          <div class="sur-step"><div class="sur-step-circle">3</div><div class="sur-step-label">완료</div></div>
        </div>

        <div class="sur-card">
          <div class="sur-all-agree" ng-click="toggleAllConsent()">
            <div class="sur-checkbox" ng-class="{'sur-checked': isAllAgreed()}"><svg width="12" height="10" viewBox="0 0 12 10" fill="none"><path d="M1 5L4.2 8.2L11 1" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/></svg></div>
            <div class="sur-all-agree-label">아래 내용에 모두 동의합니다</div>
          </div>

          <!-- 필수1: 본인확인 -->
          <div class="sur-consent-item">
            <div class="sur-consent-row" ng-click="toggleConsent('c1')">
              <div class="sur-consent-row-left">
                <div class="sur-checkbox" ng-class="{'sur-checked': consent.c1}"><svg width="12" height="10" viewBox="0 0 12 10" fill="none"><path d="M1 5L4.2 8.2L11 1" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/></svg></div>
                <div class="sur-consent-title">[필수] 본인확인을 위한 개인정보 수집·이용 동의</div>
              </div>
              <div class="sur-consent-toggle" ng-click="$event.stopPropagation(); toggleDetail('c1')">자세히 <span>{{detailOpen.c1 ? '▴' : '▾'}}</span></div>
            </div>
            <div class="sur-consent-detail" ng-show="detailOpen.c1">
              <div class="sur-consent-body">
                <div class="sur-ci-row">
                  <div class="sur-ci-k">수집 항목</div>
                  <div class="sur-ci-v">DI(중복가입확인정보), 휴대폰번호</div>
                </div>
                <div class="sur-ci-row">
                  <div class="sur-ci-k">수집 목적</div>
                  <div class="sur-ci-v">DI를 활용한 계정 동일인 확인 및 SMS 사용 등록 중복가입 방지, 발신번호 소유자 확인, 로그인 인증(MFA)</div>
                </div>
                <div class="sur-ci-row">
                  <div class="sur-ci-k">보유 및 이용 기간</div>
                  <div class="sur-ci-v">
                    · DI, 휴대폰번호: 회원 탈퇴 또는 발신번호 등록 해지 시 지체 없이 파기<br>
                    · 인증 이력: 1년 이상 보관
                  </div>
                </div>
                <div class="sur-ci-row">
                  <div class="sur-ci-k">동의 거부 시</div>
                  <div class="sur-ci-v">SMS 사용 등록이 제한됩니다.</div>
                </div>
              </div>
            </div>
          </div>

          <!-- 필수2: 이용약관 -->
          <div class="sur-consent-item">
            <div class="sur-consent-row" ng-click="toggleConsent('c2')">
              <div class="sur-consent-row-left">
                <div class="sur-checkbox" ng-class="{'sur-checked': consent.c2}"><svg width="12" height="10" viewBox="0 0 12 10" fill="none"><path d="M1 5L4.2 8.2L11 1" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/></svg></div>
                <div class="sur-consent-title">[필수] SMS 발신 서비스 이용약관</div>
              </div>
              <div class="sur-consent-toggle" ng-click="$event.stopPropagation(); toggleDetail('c2')">자세히 <span>{{detailOpen.c2 ? '▴' : '▾'}}</span></div>
            </div>
            <div class="sur-consent-detail" ng-show="detailOpen.c2">
              <div class="sur-consent-body">
                <ul class="sur-consent-list">
                  <li>발신번호 등록 요청은 <b>SMS 전송화면</b>에서 진행할 수 있습니다.</li>
                  <li>발신번호를 등록하려면 신청자 본인인증을 진행하며, <b>인증 DI와 계정 DI가 일치</b>하는 경우에만 등록할 수 있습니다.</li>
                  <li>
                    <b>무선(휴대폰) 번호</b> — 최대 2회선
                    <ul class="sur-consent-sublist">
                      <li>본인 명의: 등록하려는 번호로 직접 본인인증하며, 인증 DI와 계정 DI가 일치하면 별도 제출 서류 없이 등록됩니다.</li>
                      <li>기업 명의: 신청자 본인인증(DI 대조) 후 제출 — 통신서비스 이용증명원 + 사업자등록증 + 재직증명서</li>
                    </ul>
                  </li>
                  <li>
                    <b>유선 번호</b> — 최대 5회선
                    <ul class="sur-consent-sublist">
                      <li>본인 명의: 신청자 본인인증(DI 대조) 후 제출 — 통신서비스 이용증명원</li>
                      <li>기업 명의: 신청자 본인인증(DI 대조) 후 제출 — 통신서비스 이용증명원 + 사업자등록증 + 재직증명서</li>
                    </ul>
                  </li>
                  <li>본인인증 정보가 변경되는 경우 등록된 발신번호는 자동으로 사용이 제한됩니다.</li>
                  <li>매장 폐점 시 로그인 및 SMS 관련 메뉴 접근이 제한됩니다.</li>
                </ul>
              </div>
              <div class="sur-consent-note">
                <p>문자 발송을 위해서는 발신번호를 별도로 등록해야 하며, 등록 과정에서 개인정보취급방침 및 광고·스팸 문자 정책에 대한 동의가 필요합니다.</p>
                <p>서비스의 안전한 운영 및 문자발송 시스템의 부정 이용 방지를 위하여 국외 IP 또는 VPN·프록시 등 우회접속을 통한 문자 발송을 차단합니다.</p>
              </div>
            </div>
          </div>
        </div>

        <div class="sur-actions">
          <button class="sur-btn sur-btn-primary" ng-disabled="!isAllAgreed()" ng-click="goStep(2)">다음</button>
        </div>
      </div>

      <!-- ===================== STEP 2: 등록 화면 ===================== -->
      <div ng-show="step==2">
        <div class="sur-page-head">
          <div class="sur-page-title">SMS 사용 등록</div>
          <div class="sur-page-desc">본인인증을 완료하면 인증된 휴대폰 번호로 SMS 사용 등록이 진행됩니다.</div>
        </div>

        <div class="sur-stepper">
          <div class="sur-step sur-done"><div class="sur-step-circle">✓</div><div class="sur-step-label">약관 동의</div></div>
          <div class="sur-step-line sur-done"></div>
          <div class="sur-step sur-current"><div class="sur-step-circle">2</div><div class="sur-step-label">본인인증 · 등록</div></div>
          <div class="sur-step-line"></div>
          <div class="sur-step"><div class="sur-step-circle">3</div><div class="sur-step-label">완료</div></div>
        </div>

        <div class="sur-card">
          <div class="sur-card-head"><h2>계정 정보</h2></div>
          <div class="sur-card-body" style="padding-top:6px; padding-bottom:6px;">
            <div class="sur-field-row">
              <div class="sur-field-label">사용자 아이디</div>
              <div class="sur-field-value">{{userId}}</div>
            </div>
            <div class="sur-field-row">
              <div class="sur-field-label">사용자명</div>
              <div class="sur-field-value">{{userNm}}</div>
            </div>
          </div>
        </div>

        <div class="sur-card">
          <div class="sur-card-head">
            <h2>본인인증</h2>
            <span class="sur-badge sur-badge-required">필수</span>
          </div>
          <div class="sur-card-body">

            <!-- 인증 전 / 진행중 -->
            <div ng-show="verifyState=='before' || verifyState=='loading'">
              <div class="sur-verify-box">
                <div class="sur-verify-icon">
                  <svg width="20" height="20" viewBox="0 0 20 20" fill="none"><path d="M6 2h8a1 1 0 011 1v14a1 1 0 01-1 1H6a1 1 0 01-1-1V3a1 1 0 011-1z" stroke="#1B5EE3" stroke-width="1.5"/><path d="M9 15h2" stroke="#1B5EE3" stroke-width="1.5" stroke-linecap="round"/></svg>
                </div>
                <div class="sur-verify-text" ng-if="verifyState=='before'">SMS 수신·로그인에 사용할 휴대폰 번호 확인을 위해<br>본인인증이 필요합니다.</div>
                <div class="sur-verify-text" ng-if="verifyState=='loading'">본인인증 창을 확인해주세요…</div>
                <button class="sur-btn sur-btn-primary" ng-if="verifyState=='before'" ng-click="runVerify()">본인인증 시작</button>
              </div>
            </div>

            <!-- 중복 오류 -->
            <div class="sur-dup-error" ng-show="verifyState=='duplicate'">
              <svg width="16" height="16" viewBox="0 0 16 16" fill="none" style="flex-shrink:0; margin-top:1px;"><circle cx="8" cy="8" r="7" stroke="#D3413C" stroke-width="1.5"/><path d="M8 4.5V9" stroke="#D3413C" stroke-width="1.5" stroke-linecap="round"/><circle cx="8" cy="11.2" r="0.9" fill="#D3413C"/></svg>
              <div style="flex:1;">
                <div>이미 다른 계정(<b>{{dupUserId}}</b>)에 등록된 본인인증 정보입니다. 한 사람은 하나의 SMS 사용 계정만 등록할 수 있습니다.</div>
                <button class="sur-btn sur-btn-outline sur-btn-sm" style="margin-top:10px;" ng-click="resetVerify()">다시 시도</button>
              </div>
            </div>

            <!-- 본인인증 실패 -->
            <div ng-show="verifyState=='fail'">
              <div class="sur-verify-fail-box">
                <div class="sur-verify-fail-icon">
                  <svg width="18" height="18" viewBox="0 0 18 18" fill="none"><path d="M5 5L13 13M13 5L5 13" stroke="#D3413C" stroke-width="1.8" stroke-linecap="round"/></svg>
                </div>
                <div class="sur-verify-fail-title">본인인증에 실패했습니다</div>
                <div class="sur-verify-fail-desc">인증 시간이 초과되었거나 인증 정보가 확인되지 않았습니다.<br>다시 시도해주세요.</div>
                <div class="sur-verify-fail-reason">사유: {{verifyFailReason}}</div>
                <button class="sur-btn sur-btn-primary" ng-click="resetVerify()">다시 인증하기</button>
              </div>
            </div>

            <!-- 인증 완료 -->
            <div ng-show="verifyState=='success'">
              <div class="sur-verified-result">
                <div class="sur-vi-icon"><svg width="11" height="9" viewBox="0 0 11 9" fill="none"><path d="M1 4.5L4 7.5L10 1" stroke="white" stroke-width="1.6" stroke-linecap="round" stroke-linejoin="round"/></svg></div>
                <div style="flex:1;">
                  <div class="sur-verified-result-title">본인인증이 완료되었습니다</div>
                  <div class="sur-verified-result-desc">중복가입 확인 결과, 등록 가능한 정보입니다.</div>
                  <div class="sur-verified-info">
                    <div class="sur-verified-info-row"><span class="sur-k">성명</span><span class="sur-v">{{verifiedInfo.userNm}}</span></div>
                    <div class="sur-verified-info-row"><span class="sur-k">휴대폰</span><span class="sur-v">{{verifiedInfo.telNo}}</span></div>
                    <div class="sur-verified-info-row"><span class="sur-k">인증시각</span><span class="sur-v">{{verifiedInfo.verifyDt}}</span></div>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>

        <div class="sur-card">
          <div class="sur-card-body" style="display:flex; gap:10px; align-items:flex-start;">
            <svg width="16" height="16" viewBox="0 0 16 16" fill="none" style="flex-shrink:0; margin-top:1px;"><circle cx="8" cy="8" r="7" stroke="#1B5EE3" stroke-width="1.5"/><path d="M8 5v3.5" stroke="#1B5EE3" stroke-width="1.5" stroke-linecap="round"/><circle cx="8" cy="11" r="0.9" fill="#1B5EE3"/></svg>
            <div style="font-size:12.5px; color:var(--sur-ink-3); line-height:1.6;">발신번호는 위 인증된 휴대폰 번호로만 <b style="color:var(--sur-ink-2);">[휴대폰본인인증]</b> 하여 추가 등록할 수 있습니다.</div>
          </div>
        </div>

        <div class="sur-actions">
          <button class="sur-btn sur-btn-outline" ng-click="goStep(1)">이전</button>
        </div>
      </div>

      <!-- ===================== STEP 3: 완료 ===================== -->
      <div ng-show="step==3">
        <div class="sur-card">
          <div class="sur-done-wrap">
            <div class="sur-done-icon">
              <svg width="26" height="22" viewBox="0 0 26 22" fill="none"><path d="M2 11L9.5 18.5L24 2" stroke="#1C9A5B" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"/></svg>
            </div>
            <div class="sur-done-title">SMS 사용 등록이 완료되었습니다</div>
            <div class="sur-done-desc">다음 로그인부터 인증된 휴대폰 번호로 로그인 인증번호를 받게 됩니다.</div>

            <div class="sur-done-summary">
              <div class="sur-field-row">
                <div class="sur-field-label">사용자 아이디</div>
                <div class="sur-field-value">{{userId}}</div>
              </div>
              <div class="sur-field-row">
                <div class="sur-field-label">인증 휴대폰</div>
                <div class="sur-field-value">{{verifiedInfo.telNo}}</div>
              </div>
              <div class="sur-field-row">
                <div class="sur-field-label">등록일시</div>
                <div class="sur-field-value">{{verifiedInfo.verifyDt}}</div>
              </div>
            </div>

          </div>
        </div>

        <div class="sur-card">
          <div class="sur-card-body" style="display:flex; gap:10px; align-items:flex-start;">
            <svg width="16" height="16" viewBox="0 0 16 16" fill="none" style="flex-shrink:0; margin-top:1px;"><circle cx="8" cy="8" r="7" stroke="#1B5EE3" stroke-width="1.5"/><path d="M8 5v3.5" stroke="#1B5EE3" stroke-width="1.5" stroke-linecap="round"/><circle cx="8" cy="11" r="0.9" fill="#1B5EE3"/></svg>
            <div style="font-size:12.5px; color:var(--sur-ink-3); line-height:1.6;">발신번호는 위 인증된 휴대폰 번호로만 <b style="color:var(--sur-ink-2);">[휴대폰본인인증]</b> 하여 추가 등록할 수 있습니다.</div>
          </div>
          <div class="sur-delete-row">
            <button class="sur-btn-danger-outline" ng-click="deleteRegist()">SMS 사용 등록 삭제</button>
          </div>
        </div>
      </div>

    </div>
  </div>
</div>

<script type="text/javascript">
    var suUserId = "${sessionScope.sessionInfo.userId}";
    var suUserNm = "${sessionScope.sessionInfo.userNm}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsUserRegist/smsUserRegist.js?ver=20260731.01" charset="utf-8"></script>
