﻿/*
 *
 * Wijmo Library 5.20183.550
 * http://wijmo.com/
 *
 * Copyright(c) GrapeCity, Inc.  All rights reserved.
 *
 * Licensed under the GrapeCity Commercial License.
 * sales@wijmo.com
 * wijmo.com/products/wijmo-5/license/
 *
 */
var wijmo;
! function(t) {
  "use strict";

  function e(t, e) {
    return null == t ? null : n(e) ? s(t.implementsInterface) && t.implementsInterface(e) ? t : null : t instanceof e ? t : null
  }

  function n(t) {
    return "string" == typeof t
  }

  function r(t) {
    return "number" == typeof t
  }

  function i(t) {
    return r(t) && t == Math.round(t)
  }

  function o(t) {
    return "boolean" == typeof t
  }

  function s(t) {
    return "function" == typeof t
  }

  function a(t) {
    return (t instanceof Date || "[object Date]" === Object.prototype.toString.call(t)) && !isNaN(t.getTime())
  }

  function u(t) {
    return t instanceof Array || Array.isArray(t) || "[object Array]" === Object.prototype.toString.call(t)
  }

  function c(t) {
    return null != t && "object" == typeof t && !a(t) && !u(t)
  }

  function l(e, r, i) {
    if (null != e) {
      if (n(e)) switch (r) {
        case I.Number:
          var o = t.Globalize.parseFloat(e, i);
          return isNaN(o) ? e : o;
        case I.Date:
          var s = t.Globalize.parseDate(e, i);
          return s || i || !e || (s = new Date(e)), s && isFinite(s.getTime()) ? s : e;
        case I.Boolean:
          switch (e.toLowerCase()) {
            case "true":
              return !0;
            case "false":
              return !1
          }
          return e
      }
      if (r == I.String) return t.Globalize.format(e, i)
    }
    return e
  }

  function h(e, n) {
    if (n)
      for (var r in n)
        if ("_" != r[0]) {
          p(r in e, 'Unknown property "' + r + '".');
          var i = n[r];
          e._copy && e._copy(r, i) || (e[r] instanceof t.Event ? s(i) && e[r].addHandler(i) : !c(i) || "undefined" != typeof Element && i instanceof Element || !e[r] || "itemsSource" == r ? e[r] = i : h(e[r], i))
        }
  }

  function p(t, e) {
    if (!t) throw e = "** Assertion failed in Wijmo: " + e, console.error(e), e
  }

  function f(t, e) {
    return void 0 === e && (e = !0), p(e && null == t || n(t), "String expected."), t
  }

  function d(t, e, n) {
    if (void 0 === e && (e = !1), void 0 === n && (n = !1), p(e && null == t || r(t), "Number expected."), n && t && t < 0) throw "Positive number expected.";
    return t
  }

  function g(t, e) {
    return void 0 === e && (e = !1), p(e && null == t || o(t), "Boolean expected."), t
  }

  function m(t, e) {
    return void 0 === e && (e = !0), p(e && null == t || s(t), "Function expected."), t
  }

  function _(e, n) {
    if (e && n) {
      if (e instanceof HTMLElement && e.classList && !t.isIE9()) return e.classList.contains(n);
      if (e.getAttribute) {
        var r = new RegExp("(\\s|^)" + n + "(\\s|$)");
        return e && r.test(e.getAttribute("class"))
      }
    }
    return !1
  }

  function v(e, n) {
    if (e && n) {
      if (e instanceof HTMLElement && e.classList && !t.isIE9()) {
        if (n.indexOf(" ") < 0) e.classList.add(n);
        else
          for (var r = n.split(" "), i = 0; i < r.length; i++) e.classList.add(r[i]);
        return
      }
      if (e.setAttribute)
        for (var r = n.split(" "), i = 0; i < r.length; i++) {
          var o = r[i];
          if (!_(e, o)) {
            var s = e.getAttribute("class");
            e.setAttribute("class", s ? s + " " + o : o)
          }
        }
    }
  }

  function y(e, n) {
    if (e && n) {
      if (e instanceof HTMLElement && e.classList && !t.isIE9()) {
        if (n.indexOf(" ") < 0) e.classList.remove(n);
        else
          for (var r = n.split(" "), i = 0; i < r.length; i++) e.classList.remove(r[i]);
        return
      }
      if (e.setAttribute)
        for (var r = n.split(" "), i = 0; i < r.length; i++) {
          var o = r[i];
          if (_(e, o)) {
            var s = new RegExp("((\\s|^)" + o + "(\\s|$))", "g"),
              a = e.getAttribute("class");
            (a = a.replace(s, " ").replace(/ +/g, " ").trim()) ? e.setAttribute("class", a): e.removeAttribute("class")
          }
        }
    }
  }

  function b(t, e, n) {
    null == n && (n = !_(t, e)), n ? v(t, e) : y(t, e)
  }

  function C(t, e, n, r) {
    t && (null != n ? r && t.getAttribute(e) || t.setAttribute(e, n.toString()) : t.removeAttribute(e))
  }

  function w() {
    var t = document.activeElement;
    if (t) {
      var e = t.shadowRoot;
      e && e.activeElement && (t = e.activeElement)
    }
    return t
  }

  function E(e) {
    for (var n = [], r = e.querySelectorAll("input,select,textarea,button,a,div"), i = 0; i < r.length; i++) {
      var o = r[i];
      if (o.offsetHeight > 0 && o.tabIndex > -1 && !o.disabled && !S(o, "[disabled],.wj-state-disabled")) {
        if (o instanceof HTMLAnchorElement && !o.hasAttribute("href")) continue;
        if (t.isIE() && !o.hasAttribute("tabindex")) {
          if (o instanceof HTMLDivElement) continue;
          var s = t.Control.getControl(S(o, ".wj-flexgrid"));
          if (s && 0 == s.keyActionTab) continue
        }!t.Control.getControl(o) && E(o).length || n.push(o)
      }
    }
    return n
  }

  function A(t) {
    return t instanceof Element ? t : n(t) ? document.querySelector(t) : t && t.jquery ? t[0] : null
  }

  function x(t, e) {
    for (var n = e; n && t;) {
      if (n === t) return !0;
      n = n.parentNode || n.host
    }
    return !1
  }

  function S(t, e) {
    var n = t ? t.matches || t.webkitMatchesSelector || t.mozMatchesSelector || t.msMatchesSelector : null;
    if (n)
      for (; t; t = t.parentNode)
        if (t instanceof Element && n.call(t, e)) return t;
    return null
  }

  function O(t, e) {
    if (p(c(e), "css parameter should be an object"), t instanceof Array)
      for (var n = 0; n < t.length; n++) O(t[n], e);
    else if (t && t.style) {
      var r = t.style;
      for (var i in e) {
        var o = e[i];
        "number" == typeof o && i.match(/width|height|left|top|right|bottom|size|padding|margin'/i) && (o += "px"), r[i] !== o && (r[i] = o.toString())
      }
    }
  }
  var T = "5.20183.550";
  t.getVersion = function() {
    return T
  }, t.setLicenseKey = function(e) {
    t.Control._licKey = e
  };
  ! function(t) {
    t[t.Back = 8] = "Back", t[t.Tab = 9] = "Tab", t[t.Enter = 13] = "Enter", t[t.Escape = 27] = "Escape", t[t.Space = 32] = "Space", t[t.PageUp = 33] = "PageUp", t[t.PageDown = 34] = "PageDown", t[t.End = 35] = "End", t[t.Home = 36] = "Home", t[t.Left = 37] = "Left", t[t.Up = 38] = "Up", t[t.Right = 39] = "Right", t[t.Down = 40] = "Down", t[t.Delete = 46] = "Delete", t[t.F1 = 112] = "F1", t[t.F2 = 113] = "F2", t[t.F3 = 114] = "F3", t[t.F4 = 115] = "F4", t[t.F5 = 116] = "F5", t[t.F6 = 117] = "F6", t[t.F7 = 118] = "F7", t[t.F8 = 119] = "F8", t[t.F9 = 120] = "F9", t[t.F10 = 121] = "F10", t[t.F11 = 122] = "F11", t[t.F12 = 123] = "F12"
  }(t.Key || (t.Key = {}));
  var I;
  ! function(t) {
    t[t.Object = 0] = "Object", t[t.String = 1] = "String", t[t.Number = 2] = "Number", t[t.Boolean = 3] = "Boolean", t[t.Date = 4] = "Date", t[t.Array = 5] = "Array"
  }(I = t.DataType || (t.DataType = {})), t.tryCast = e, t.isPrimitive = function(t) {
    return n(t) || r(t) || o(t) || a(t)
  }, t.isString = n, t.isNullOrWhiteSpace = function(t) {
    return null == t || t.replace(/\s/g, "").length < 1
  }, t.isNumber = r, t.isInt = i, t.isBoolean = o, t.isFunction = s, t.isUndefined = function(t) {
    return void 0 === t
  }, t.isDate = a, t.isArray = u, t.isObject = c, t.isEmpty = function(t) {
    for (var e in t) return !1;
    return !0
  }, t.getUniqueId = function(t) {
    for (var e = t, n = 0; null != document.getElementById(e); n++) e = t + n;
    return e
  }, t.mouseToPage = function(t) {
    if (t instanceof F) return t;
    if (t.touches && t.touches.length > 0 && (t = t.touches[0]), r(t.clientX) && r(t.clientY)) return new F(t.clientX + pageXOffset, t.clientY + pageYOffset);
    throw "Mouse or touch event expected."
  }, t.getType = function(t) {
    return r(t) ? I.Number : o(t) ? I.Boolean : a(t) ? I.Date : n(t) ? I.String : u(t) ? I.Array : I.Object
  }, t.changeType = l, t.toFixed = function(t, e, n) {
    if (n) {
      var r = (i = t.toString()).indexOf(".");
      i.indexOf("e") < 0 && r > -1 && (i = i.substr(0, r + 1 + e), t = parseFloat(i))
    } else {
      var i = t.toFixed(e);
      t = parseFloat(i)
    }
    return t
  }, t.format = function(e, i, o) {
    if ((e = f(e)).match(/\{.*"count".*:.*"when".*:.*\}/)) try {
      var s = JSON.parse(e);
      if (n(s.count)) {
        var a = i[s.count],
          u = s.when;
        if (r(a) && c(u)) {
          var l = u[a] || u.other;
          n(l) && (e = l)
        }
      }
    } catch (t) {}
    return e.replace(/\{(.*?)(:(.*?))?\}/g, function(e, n, r, s) {
      var a = e;
      return n && "{" != n[0] && i && (a = i[n], s && (a = t.Globalize.format(a, s)), o && (a = o(i, n, s, a))), null == a ? "" : a
    })
  }, t.clamp = function(t, e, n) {
    return null != t && (null != n && t > n && (t = n), null != e && t < e && (t = e)), t
  }, t.copy = h, t.assert = p, t._deprecated = function(t, e) {
    console.error('** WARNING: "' + t + '" has been deprecated; please use "' + e + '" instead.')
  }, t.asString = f, t.asNumber = d, t.asInt = function(t, e, n) {
    if (void 0 === e && (e = !1), void 0 === n && (n = !1), p(e && null == t || i(t), "Integer expected."), n && t && t < 0) throw "Positive integer expected.";
    return t
  }, t.asBoolean = g, t.asDate = function(t, e) {
    if (void 0 === e && (e = !1), n(t)) {
      var r = l(t, I.Date, "r");
      a(r) && (t = r)
    }
    return p(e && null == t || a(t), "Date expected."), t
  }, t.asFunction = m, t.asArray = function(t, e) {
    return void 0 === e && (e = !0), p(e && null == t || u(t), "Array expected."), t
  }, t.asType = function(t, n, r) {
    return void 0 === r && (r = !1), t = e(t, n), p(r || null != t, n + " expected."), t
  }, t.asEnum = function(t, e, n) {
    if (void 0 === n && (n = !1), null == t && n) return null;
    var i = e[t];
    return p(null != i, 'Invalid enum value: "' + t + '"'), r(i) ? i : t
  }, t.asCollectionView = function(n, r) {
    if (void 0 === r && (r = !0), null == n && r) return null;
    var i = e(n, "ICollectionView");
    return null != i ? i : (u(n) || p(!1, "Array or ICollectionView expected."), new t.collections.CollectionView(n))
  }, t.hasItems = function(t) {
    return null != t && null != t.items && t.items.length > 0
  }, t.toHeaderCase = function(t) {
    return t && t.length ? t[0].toUpperCase() + t.substr(1).replace(/([a-z])([A-Z])/g, "$1 $2") : ""
  }, t.escapeHtml = function(t) {
    return n(t) && (t = t.replace(/[&<>"'\/]/g, function(t) {
      return P[t]
    })), t
  };
  var P = {
    "&": "&amp;",
    "<": "&lt;",
    ">": "&gt;",
    '"': "&quot;",
    "'": "&#39;",
    "/": "&#x2F;"
  };
  t.hasClass = _, t.addClass = v, t.removeClass = y, t.toggleClass = b, t.setAttribute = C, t.setAriaLabel = function(t, e) {
    C(t, "aria-label", e)
  }, t.setSelectionRange = function(e, n, r) {
    if (void 0 === r && (r = n), p(e instanceof HTMLInputElement || e instanceof HTMLTextAreaElement, "INPUT or TEXTAREA element expected"), x(document.body, e) && !e.disabled && e.offsetHeight) try {
      return e.setSelectionRange(d(n), d(r), t.isIE() ? null : "backward"), e.focus(), !0
    } catch (t) {}
    return !1
  }, t.disableAutoComplete = function(t) {
    "autocomplete,autocorrect,autocapitalize,spellcheck".split(",").forEach(function(e) {
      t.setAttribute(e, "spellcheck" == e ? "false" : "off")
    })
  }, t.removeChild = function(t) {
    return t && t.parentNode ? t.parentNode.removeChild(t) : null
  }, t.getActiveElement = w, t.moveFocus = function(t, e) {
    var n = E(t),
      r = 0;
    if (e) {
      var i = n.indexOf(w());
      i > -1 && (r = (i + e + n.length) % n.length)
    }
    if (r < n.length) {
      var o = n[r];
      return o.focus(), o instanceof HTMLInputElement && o.select(), !0
    }
    return !1
  }, t.getElement = A, t.createElement = function(t, e) {
    var n = document.createElement("div");
    return n.innerHTML = t, 1 == n.children.length && (n = n.children[0]), e && (e = A(e)).appendChild(n), n
  }, t.setText = function(t, e) {
    t.textContent = e || ""
  }, t.contains = x, t.closest = S, t.closestClass = function(t, e) {
    return S(t, "." + e)
  }, t.enable = function(t, e) {
    var n = !e;
    b(t, "wj-state-disabled", n), C(t, "disabled", n ? "true" : null);
    for (var r = t.querySelectorAll("input"), i = 0; i < r.length; i++) {
      var o = r[i];
      e ? o.removeAttribute("disabled") : o.setAttribute("disabled", "true")
    }
  }, t.getElementRect = function(t) {
    var e = t.getBoundingClientRect();
    return new D(e.left + pageXOffset, e.top + pageYOffset, e.width, e.height)
  }, t.setCss = O, t.animate = function(e, n, r) {
    null == n && (n = t.Control._ANIM_DEF_DURATION), null == r && (r = t.Control._ANIM_DEF_STEP), e = m(e), n = d(n, !1, !0), r = d(r, !1, !0);
    var i, o = Date.now(),
      s = setInterval(function() {
        var t = Math.min(1, (Date.now() - o) / n);
        t = Math.sin(t * Math.PI / 2), t *= t, i && cancelAnimationFrame(i), i = requestAnimationFrame(function() {
          i = null, e(t)
        }), t >= 1 && clearInterval(s)
      }, r);
    return s
  };
  var F = function() {
    function t(t, e) {
      void 0 === t && (t = 0), void 0 === e && (e = 0), this.x = d(t), this.y = d(e)
    }
    return t.prototype.equals = function(e) {
      return e instanceof t && this.x == e.x && this.y == e.y
    }, t.prototype.clone = function() {
      return new t(this.x, this.y)
    }, t
  }();
  t.Point = F;
  var M = function() {
    function t(t, e) {
      void 0 === t && (t = 0), void 0 === e && (e = 0), this.width = d(t), this.height = d(e)
    }
    return t.prototype.equals = function(e) {
      return e instanceof t && this.width == e.width && this.height == e.height
    }, t.prototype.clone = function() {
      return new t(this.width, this.height)
    }, t
  }();
  t.Size = M;
  var D = function() {
    function t(t, e, n, r) {
      this.left = d(t), this.top = d(e), this.width = d(n), this.height = d(r)
    }
    return Object.defineProperty(t.prototype, "right", {
      get: function() {
        return this.left + this.width
      },
      enumerable: !0,
      configurable: !0
    }), Object.defineProperty(t.prototype, "bottom", {
      get: function() {
        return this.top + this.height
      },
      enumerable: !0,
      configurable: !0
    }), t.prototype.equals = function(e) {
      return e instanceof t && this.left == e.left && this.top == e.top && this.width == e.width && this.height == e.height
    }, t.prototype.clone = function() {
      return new t(this.left, this.top, this.width, this.height)
    }, t.fromBoundingRect = function(e) {
      return null != e.left ? new t(e.left, e.top, e.width, e.height) : null != e.x ? new t(e.x, e.y, e.width, e.height) : void p(!1, "Invalid source rectangle.")
    }, t.union = function(e, n) {
      var r = Math.min(e.left, n.left),
        i = Math.min(e.top, n.top);
      return new t(r, i, Math.max(e.right, n.right) - r, Math.max(e.bottom, n.bottom) - i)
    }, t.intersection = function(e, n) {
      var r = Math.max(e.left, n.left),
        i = Math.max(e.top, n.top);
      return new t(r, i, Math.min(e.right, n.right) - r, Math.min(e.bottom, n.bottom) - i)
    }, t.prototype.contains = function(e) {
      if (e instanceof F) return e.x >= this.left && e.x <= this.right && e.y >= this.top && e.y <= this.bottom;
      if (e instanceof t) {
        var n = e;
        return n.left >= this.left && n.right <= this.right && n.top >= this.top && n.bottom <= this.bottom
      }
      p(!1, "Point or Rect expected.")
    }, t.prototype.inflate = function(e, n) {
      return new t(this.left - e, this.top - n, this.width + 2 * e, this.height + 2 * n)
    }, t
  }();
  t.Rect = D;
  var j = function() {
    function e() {}
    return e.addDays = function(t, n) {
      return e.newDate(t.getFullYear(), t.getMonth(), t.getDate() + n)
    }, e.addMonths = function(t, n) {
      return e.newDate(t.getFullYear(), t.getMonth() + n, t.getDate())
    }, e.addYears = function(t, n) {
      return e.newDate(t.getFullYear() + n, t.getMonth(), t.getDate())
    }, e.addHours = function(t, n) {
      return e.newDate(t.getFullYear(), t.getMonth(), t.getDate(), t.getHours() + n)
    }, e.addMinutes = function(t, n) {
      return e.newDate(t.getFullYear(), t.getMonth(), t.getDate(), t.getHours(), t.getMinutes() + n)
    }, e.addSeconds = function(t, n) {
      return e.newDate(t.getFullYear(), t.getMonth(), t.getDate(), t.getHours(), t.getMinutes(), t.getSeconds() + n)
    }, e.sameDate = function(t, e) {
      return a(t) && a(e) && t.getFullYear() == e.getFullYear() && t.getMonth() == e.getMonth() && t.getDate() == e.getDate()
    }, e.sameTime = function(t, e) {
      return a(t) && a(e) && t.getHours() == e.getHours() && t.getMinutes() == e.getMinutes() && t.getSeconds() == e.getSeconds()
    }, e.equals = function(t, e) {
      return a(t) && a(e) && t.getTime() == e.getTime()
    }, e.fromDateTime = function(t, n) {
      return t || n ? (t || (t = n), n || (n = t), e.newDate(t.getFullYear(), t.getMonth(), t.getDate(), n.getHours(), n.getMinutes(), n.getSeconds(), n.getMilliseconds())) : null
    }, e.toFiscal = function(n, r) {
      var i = t.culture.Globalize.calendar;
      return u(i.fiscalYearOffsets) ? e.addMonths(n, -i.fiscalYearOffsets[r ? 0 : 1]) : n
    }, e.fromFiscal = function(n, r) {
      var i = t.culture.Globalize.calendar;
      return u(i.fiscalYearOffsets) ? e.addMonths(n, +i.fiscalYearOffsets[r ? 0 : 1]) : n
    }, e.newDate = function(t, e, n, r, i, o, s) {
      if (null == t || null == e || null == n) {
        var a = new Date;
        null == t && (t = a.getFullYear()), null == e && (e = a.getMonth()), null == n && (n = a.getDate())
      }
      null == r && (r = 0), null == i && (i = 0), null == o && (o = 0), null == s && (s = 0);
      var u = new Date(t, e, n, r, i, o, s),
        c = u.getFullYear();
      return t < 100 && c >= 1900 && u.setFullYear(u.getFullYear() - 1900), u
    }, e.clone = function(t) {
      return e.fromDateTime(t, t)
    }, e
  }();
  t.DateTime = j, t.httpRequest = function(t, e) {
    e || (e = {});
    var i = e.method ? f(e.method).toUpperCase() : "GET",
      o = null == e.async || g(e.async),
      u = e.data;
    if (null != u && "GET" == i) {
      var l = [];
      for (var h in u) {
        var p = u[h];
        a(p) && (p = p.toJSON()), l.push(h + "=" + p)
      }
      l.length && (t += (t.indexOf("?") < 0 ? "?" : "&") + l.join("&")), u = null
    }
    var d = new XMLHttpRequest;
    d.URL_DEBUG = t;
    var _ = !1;
    if (null == u || n(u) || (_ = c(u), u = JSON.stringify(u)), d.onload = function() {
      4 == d.readyState && (d.status < 300 ? e.success && m(e.success)(d) : e.error && m(e.error)(d), e.complete && m(e.complete)(d))
    }, d.onerror = function() {
      if (!s(e.error)) throw "HttpRequest Error: " + d.status + " " + d.statusText;
      e.error(d)
    }, d.open(i, t, o, e.user, e.password), e.user && e.password && d.setRequestHeader("Authorization", "Basic " + btoa(e.user + ":" + e.password)), _ && d.setRequestHeader("Content-Type", "application/json"), e.requestHeaders)
      for (var v in e.requestHeaders) d.setRequestHeader(v, e.requestHeaders[v]);
    return r(e.timeout) && (d.timeout = e.timeout), s(e.beforeSend) && e.beforeSend(d), d.send(u), d
  }
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  "use strict";
  t.culture = "undefined" != typeof window && window.wijmo.culture || {
    Globalize: {
      numberFormat: {
        ".": ".",
        ",": ",",
        "-": "-",
        "+": "+",
        "%": "%",
        percent: {
          pattern: ["-n %", "n %"]
        },
        currency: {
          decimals: 2,
          symbol: "$",
          pattern: ["($n)", "$n"]
        }
      },
      calendar: {
        "/": "/",
        ":": ":",
        firstDay: 0,
        days: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
        daysAbbr: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
        months: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
        monthsAbbr: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
        am: ["AM", "A"],
        pm: ["PM", "P"],
        eras: ["A.D.", "B.C."],
        patterns: {
          d: "M/d/yyyy",
          D: "dddd, MMMM dd, yyyy",
          f: "dddd, MMMM dd, yyyy h:mm tt",
          F: "dddd, MMMM dd, yyyy h:mm:ss tt",
          t: "h:mm tt",
          T: "h:mm:ss tt",
          M: "MMMM d",
          m: "MMMM d",
          Y: "MMMM, yyyy",
          y: "MMMM, yyyy",
          g: "M/d/yyyy h:mm tt",
          G: "M/d/yyyy h:mm:ss tt",
          s: 'yyyy"-"MM"-"dd"T"HH":"mm":"ss',
          o: 'yyyy"-"MM"-"dd"T"HH":"mm":"ss"."fffffffK',
          O: 'yyyy"-"MM"-"dd"T"HH":"mm":"ss"."fffffffK',
          U: "dddd, MMMM dd, yyyy h:mm:ss tt"
        },
        fiscalYearOffsets: [-3, -3]
      }
    }
  };
  var e = function() {
    function e() {}
    return e.format = function(n, r, i, o) {
      return t.isString(n) ? n : t.isNumber(n) ? (r = r || (n == Math.round(n) ? "n0" : "n2"), e.formatNumber(n, r, i, o)) : t.isDate(n) ? (r = r || "d", e.formatDate(n, r)) : null != n ? n.toString() : ""
    }, e.formatNumber = function(n, r, i, o) {
      n = t.asNumber(n), r = t.asString(r);
      var s, a = t.culture.Globalize.numberFormat,
        u = r ? r.match(/([a-z])(\d*)(,*)(.*)/i) : null,
        c = u ? u[1].toLowerCase() : "n",
        l = u && u[2] ? parseInt(u[2]) : "c" == c ? a.currency.decimals : n == Math.round(n) ? 0 : 2,
        h = u && u[3] ? 3 * u[3].length : 0,
        p = a["."] || ".",
        f = a[","] || ",",
        d = a["-"] || "-";
      if (h && (n /= Math.pow(10, h)), "d" == c || "x" == c) {
        for (s = Math.round(Math.abs(n)).toString("d" == c ? 10 : 16); s.length < l;) s = "0" + s;
        return n < 0 && (s = d + s), r && "X" == r[0] && (s = s.toUpperCase()), s
      }
      if ("p" == c && (n = e._mul100(n), n = t.toFixed(n, l, o)), o && "p" != c && (n = t.toFixed(n, l, !0)), s = "c" == c || "p" == c ? e._toFixedStr(Math.abs(n), l) : e._toFixedStr(n, l), (i || "g" == c) && s.indexOf(".") > -1 && (s = (s = s.replace(/(\.[0-9]*?)0+$/g, "$1")).replace(/\.$/, "")), "." != p && (s = s.replace(".", p)), "-" != d && (s = s.replace("-", d)), f && ("n" == c || "c" == c || "p" == c)) {
        var g = s.indexOf(p),
          m = /\B(?=(\d\d\d)+(?!\d))/g;
        s = g > -1 ? s.substr(0, g).replace(m, f) + s.substr(g) : s.replace(m, f)
      }
      if ("c" == c) {
        var _ = a.currency.pattern[n < 0 ? 0 : 1],
          v = u && u[4] ? u[4] : a.currency.symbol;
        "​" == v && (v = ""), s = _.replace("n", s).replace("$", v)
      }
      if ("p" == c) {
        var _ = a.percent.pattern[n < 0 ? 0 : 1],
          y = a["%"] || "%";
        s = _.replace("n", s), "%" != y && (s = s.replace("%", y)), "-" != d && n < 0 && (s = s.replace("-", d))
      }
      return s
    }, e.formatDate = function(n, r) {
      switch (n = t.asDate(n), r) {
        case "r":
        case "R":
          return n.toUTCString();
        case "u":
          return n.toISOString().replace(/\.\d{3}/, "")
      }
      r = e._expandFormat(r);
      for (var i = e._parseDateFormat(r), o = "", s = 0; s < i.length; s++) o += e._formatDatePart(n, r, i[s]);
      return o
    }, e.parseInt = function(t, n) {
      return Math.round(e.parseFloat(t, n))
    }, e.parseFloat = function(e, n) {
      var r = t.culture.Globalize.numberFormat,
        i = r["-"] || "-",
        o = r["%"] || "%",
        s = e.indexOf(i) > -1 || e.indexOf("(") > -1 && e.indexOf(")") > -1 ? -1 : 1,
        a = e.indexOf(o) > -1 ? .01 : 1,
        u = n ? n.match(/,+/) : null,
        c = u ? 3 * u[0].length : 0,
        l = 0;
      if (!n || "x" != n[0] && "X" != n[0]) {
        var h = r["."] || ".",
          p = new RegExp("[^\\d\\" + h + "]", "g");
        e = e.replace(p, "").replace(h, "."), l = parseFloat(e)
      } else e = e.replace(/[^0-9a-f]+.*$/gi, ""), l = parseInt(e, 16);
      return l * s * a * Math.pow(10, c)
    }, e.parseDate = function(n, r, i) {
      if (!(n = t.asString(n))) return null;
      if ("u" == r) return new Date(n);
      var o;
      if ("R" == r || "r" == r) {
        var s = /(([0-9]+)\-([0-9]+)\-([0-9]+))?\s?(([0-9]+):([0-9]+)(:([0-9]+))?)?/,
          a = n.match(s);
        return a[1] || a[5] ? (o = a[1] ? new Date(parseInt(a[2]), parseInt(a[3]) - 1, parseInt(a[4])) : new Date, a[5] && (o.setHours(parseInt(a[6])), o.setMinutes(parseInt(a[7])), o.setSeconds(a[8] ? parseInt(a[9]) : 0))) : o = new Date(n), isNaN(o.getTime()) ? null : o
      }
      r = e._expandFormat(r || "d");
      var u, c, l, h, p, f, d = t.culture.Globalize.calendar,
        g = e._CJK,
        m = new RegExp("(\\" + d["/"] + ")|(\\" + d[":"] + ")|(\\d+)|([" + g + "\\.]{2,})|([" + g + "]+)", "gi"),
        _ = n.match(m),
        v = e._parseDateFormat(r),
        y = 0,
        b = -1,
        C = 0,
        w = 1,
        E = 0,
        A = 0,
        x = 0,
        S = 0,
        O = -1;
      if (!(_ && _.length && v && v.length)) return null;
      for (var T = 0; T < v.length && _; T++) {
        var I = T - y,
          P = I > -1 && I < _.length ? _[I] : "",
          F = v[T].length;
        switch (v[T]) {
          case "EEEE":
          case "EEE":
          case "EE":
          case "E":
          case "eeee":
          case "eee":
          case "ee":
          case "e":
            f = v[T];
          case "yyyy":
          case "yyy":
          case "yy":
          case "y":
            F > 1 && P.length > F && (_[I] = P.substr(F), P = P.substr(0, F), y++), b = parseInt(P), p = 4 == P.length;
            break;
          case "MMMM":
          case "MMM":
            h = !0;
            var M = P.toLowerCase();
            C = -1;
            for (var D = 0; D < 12; D++)
              if (0 == d.months[D].toLowerCase().indexOf(M)) {
                C = D;
                break
              }
            if (C > -1) break;
          case "MM":
          case "M":
            h = !0, F > 1 && P.length > F && (_[I] = P.substr(F), P = P.substr(0, F), y++), C = parseInt(P) - 1;
            break;
          case "dddd":
          case "ddd":
            u = !0;
            break;
          case "dd":
          case "d":
            F > 1 && P.length > F && (_[I] = P.substr(F), P = P.substr(0, F), y++), w = parseInt(P), c = !0;
            break;
          case "hh":
          case "h":
            F > 1 && P.length > F && (_[I] = P.substr(F), P = P.substr(0, F), y++), E = 12 == (E = parseInt(P)) ? 0 : E;
            break;
          case "HH":
            F > 1 && P.length > F && (_[I] = P.substr(F), P = P.substr(0, F), y++), E = parseInt(P);
            break;
          case "H":
            E = parseInt(P);
            break;
          case "mm":
          case "m":
            F > 1 && P.length > F && (_[I] = P.substr(F), P = P.substr(0, F), y++), A = parseInt(P);
            break;
          case "ss":
          case "s":
            F > 1 && P.length > F && (_[I] = P.substr(F), P = P.substr(0, F), y++), x = parseInt(P);
            break;
          case "fffffff":
          case "FFFFFFF":
          case "ffffff":
          case "FFFFFF":
          case "fffff":
          case "FFFFF":
          case "ffff":
          case "FFFF":
          case "fff":
          case "FFF":
          case "ff":
          case "FF":
          case "f":
          case "F":
            S = parseInt(P) / Math.pow(10, F - 3);
            break;
          case "tt":
          case "t":
            P = P.toUpperCase(), E < 12 && d.pm.indexOf(P) > -1 && (E += 12);
            break;
          case "q":
          case "Q":
          case "u":
          case "U":
            l = !0;
            break;
          case "ggg":
          case "gg":
          case "g":
            O = d.eras.length > 1 ? e._getEra(P, d) : -1;
            break;
          case "/":
          case ":":
            if (P && P != d[v[T]]) return null;
            break;
          case "K":
            break;
          default:
            e._unquote(v[T]).trim() != P.trim() && y++
        }
      }
      if (h && c && (isNaN(E) && (E = 0), isNaN(A) && (A = 0), isNaN(x) && (x = 0)), C < 0 || C > 11 || isNaN(C) || w < 1 || w > 31 || isNaN(w) || E < 0 || E > 24 || isNaN(E) || A < 0 || A > 60 || isNaN(A) || x < 0 || x > 60 || isNaN(x)) return null;
      if (f) {
        if (!h) return null;
        var j = t.culture.Globalize.calendar;
        if (t.isArray(j.fiscalYearOffsets)) {
          var N = "E" == f[0],
            L = C - j.fiscalYearOffsets[N ? 0 : 1];
          b += L > 11 ? -1 : L < 0 ? 1 : 0
        }
      }
      if (u && !c) return null;
      if (l && !h) return null;
      if (b < 0) b = t.isDate(i) ? i.getFullYear() : (new Date).getFullYear();
      else if (O > -1) b = b + d.eras[O].start.getFullYear() - 1;
      else if (b < 100 && !p) {
        var R = t.isNumber(d.twoDigitYearMax) ? d.twoDigitYearMax : 2029;
        R > 99 && (b += b + 2e3 <= R ? 2e3 : 1900)
      }
      return w > new Date(b, C + 1, 0).getDate() ? null : (o = t.DateTime.newDate(b, C, w, E, A + 0, x, S), isNaN(o.getTime()) ? null : o)
    }, e.getFirstDayOfWeek = function() {
      var e = t.culture.Globalize.calendar.firstDay;
      return e || 0
    }, e.getNumberDecimalSeparator = function() {
      var e = t.culture.Globalize.numberFormat["."];
      return e || "."
    }, e._toFixedStr = function(t, e) {
      var n = t.toString(),
        r = n.indexOf("."),
        i = e - (n.length - r) + 1;
      return t && e < 14 && (t += 1e-14 * (t > 0 ? 1 : -1)), n.indexOf("e") < 0 && r > -1 && i >= 0 ? n + Array(i + 1).join("0") : t.toFixed(e)
    }, e._unquote = function(t) {
      return t.length > 1 && t[0] == t[t.length - 1] && ("'" == t[0] || '"' == t[0]) ? t.substr(1, t.length - 2) : t
    }, e._parseDateFormat = function(t) {
      if (t in e._dateFormatParts) return e._dateFormatParts[t];
      var n, r, i = [];
      if (t)
        for (n = 0; n > -1 && n < t.length; n++) {
          var o = t[n];
          if (("'" == o || '"' == o) && (r = t.indexOf(o, n + 1)) > -1) i.push(t.substring(n, r + 1)), n = r;
          else if ("\\" == o && n < t.length - 1) n++, i.push('"' + t[n] + '"');
          else {
            for (r = n + 1; r < t.length && t[r] == o; r++);
            i.push(t.substring(n, r)), n = r - 1
          }
        }
      return e._dateFormatParts[t] = i, i
    }, e._formatDatePart = function(n, r, i) {
      var o, s = t.culture.Globalize.calendar,
        a = 0,
        u = 0,
        c = 0,
        l = i.length;
      switch (i) {
        case "yyyy":
        case "yyy":
        case "yy":
        case "y":
        case "EEEE":
        case "EEE":
        case "EE":
        case "E":
        case "eeee":
        case "eee":
        case "ee":
        case "e":
          u = (o = "E" == i[0] ? t.DateTime.toFiscal(n, !0) : "e" == i[0] ? t.DateTime.toFiscal(n, !1) : n).getFullYear(), s.eras.length > 1 && r.indexOf("g") > -1 && (a = e._getEra(n, s)) > -1 && (u = u - s.eras[a].start.getFullYear() + 1);
          var h = i.length < 3 ? u % 100 : 3 == i.length ? u % 1e3 : u;
          return e._zeroPad(h, i.length);
        case "MMMMM":
          return s.monthsAbbr[n.getMonth()][0];
        case "MMMM":
          return s.months[n.getMonth()];
        case "MMM":
          return s.monthsAbbr[n.getMonth()];
        case "MM":
        case "M":
          return e._zeroPad(n.getMonth() + 1, l);
        case "dddd":
          return s.days[n.getDay()];
        case "ddd":
          return s.daysAbbr[n.getDay()];
        case "dd":
          return e._zeroPad(n.getDate(), 2);
        case "d":
          return n.getDate().toString();
        case "hh":
        case "h":
          return e._zeroPad(e._h12(n), l);
        case "HH":
        case "H":
          return e._zeroPad(n.getHours(), l);
        case "mm":
        case "m":
          return e._zeroPad(n.getMinutes(), l);
        case "ss":
        case "s":
          return e._zeroPad(n.getSeconds(), l);
        case "fffffff":
        case "FFFFFFF":
        case "ffffff":
        case "FFFFFF":
        case "fffff":
        case "FFFFF":
        case "ffff":
        case "FFFF":
        case "fff":
        case "FFF":
        case "ff":
        case "FF":
        case "f":
        case "F":
          return c = n.getMilliseconds() * Math.pow(10, l - 3), "f" == i[0] ? e._zeroPad(c, l) : c.toFixed(0);
        case "tt":
          return n.getHours() < 12 ? s.am[0] : s.pm[0];
        case "t":
          return n.getHours() < 12 ? s.am[1] : s.pm[1];
        case "q":
        case "Q":
          return (Math.floor(n.getMonth() / 3) + 1).toString();
        case "u":
        case "U":
          return o = t.DateTime.toFiscal(n, "U" == i), (Math.floor(o.getMonth() / 3) + 1).toString();
        case "ggg":
        case "gg":
        case "g":
          if (s.eras.length > 1 && (a = e._getEra(n, s)) > -1) {
            var p = s.eras[a];
            return "ggg" == i ? p.name : "gg" == i ? p.name[0] : p.symbol
          }
          return s.eras[0];
        case ":":
        case "/":
          return s[i];
        case "K":
          var f = n.toString().match(/(\+|\-)(\d{2})(\d{2})/);
          return f ? f[1] + f[2] + f[3] : "";
        case "zzz":
        case "zz":
        case "z":
          var d = -n.getTimezoneOffset(),
            g = void 0;
          switch (i) {
            case "zzz":
              g = e.format(d / 60, "d2", !1, !0) + s[":"] + e.format(d % 60, "d2", !1, !0);
              break;
            case "zz":
              g = e.format(d / 60, "d2", !1, !0);
              break;
            case "z":
              g = e.format(d / 60, "d", !1, !0)
          }
          return d >= 0 ? "+" + g : g
      }
      return l > 1 && i[0] == i[l - 1] && ('"' == i[0] || "'" == i[0]) ? i.substr(1, l - 2) : i
    }, e._getEra = function(e, n) {
      if (t.isDate(e)) {
        for (r = 0; r < n.eras.length; r++)
          if (e >= n.eras[r].start) return r
      } else if (t.isString(e))
        for (var r = 0; r < n.eras.length; r++)
          if (n.eras[r].name && (0 == n.eras[r].name.indexOf(e) || 0 == n.eras[r].symbol.indexOf(e))) return r;
      return -1
    }, e._expandFormat = function(e) {
      var n = t.culture.Globalize.calendar.patterns[e];
      return n || e
    }, e._zeroPad = function(t, e) {
      var n = t.toFixed(0),
        r = e - n.length + 1;
      return r > 0 ? Array(r).join("0") + n : n
    }, e._h12 = function(e) {
      var n = t.culture.Globalize.calendar,
        r = e.getHours();
      return n.am && n.am[0] && 0 == (r %= 12) && (r = 12), r
    }, e._mul100 = function(t) {
      var e = t.toString(),
        n = e.indexOf(".");
      return e.indexOf("e") > -1 ? 100 * t : (n < 0 ? e += "00" : (n += 2, e = (e = e.replace(".", "") + "00").substr(0, n) + "." + e.substr(n)), parseFloat(e))
    }, e._CJK = "a-z" + "u00c0-u017fu3000-u30ffu4e00-u9faf".replace(/u/g, "\\u") + "u1100-u11ffu3130-u318fua960-ua97fuac00-ud7afud7b0-ud7ff".replace(/u/g, "\\u"), e._dateFormatParts = {}, e
  }();
  t.Globalize = e, t._updateCulture = function() {
    t.culture = window.wijmo.culture
  }, t._addCultureInfo = function(e, n) {
    t.culture[e] = window.wijmo.culture[e] || n
  }
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  "use strict";
  var e = function() {
    function t(t) {
      this.path = t
    }
    return Object.defineProperty(t.prototype, "path", {
      get: function() {
        return this._path
      },
      set: function(t) {
        this._path = t, this._parts = t ? t.split(".") : [];
        for (var e = 0; e < this._parts.length; e++) {
          var n = this._parts[e],
            r = n.indexOf("[");
          r > -1 && (this._parts[e] = n.substr(0, r), this._parts.splice(++e, 0, parseInt(n.substr(r + 1))))
        }
        this._key = 1 == this._parts.length ? this._parts[0] : null
      },
      enumerable: !0,
      configurable: !0
    }), t.prototype.getValue = function(t) {
      if (t) {
        if (this._key) return t[this._key];
        if (this._path && this._path in t) return t[this._path];
        for (var e = 0; e < this._parts.length && t; e++) t = t[this._parts[e]]
      }
      return t
    }, t.prototype.setValue = function(t, e) {
      if (t) {
        if (this._path in t) return void(t[this._path] = e);
        for (var n = 0; n < this._parts.length - 1; n++)
          if (null == (t = t[this._parts[n]])) return;
        t[this._parts[this._parts.length - 1]] = e
      }
    }, t
  }();
  t.Binding = e
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
      };
    return function(e, n) {
      function r() {
        this.constructor = e
      }
      t(e, n), e.prototype = null === n ? Object.create(n) : (r.prototype = n.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  "use strict";
  var e = function() {
      return function(t, e) {
        this.handler = t, this.self = e
      }
    }(),
    n = function() {
      function n() {
        this._handlers = []
      }
      return n.prototype.addHandler = function(n, r) {
        n = t.asFunction(n), this._handlers.push(new e(n, r))
      }, n.prototype.removeHandler = function(e, n) {
        e = t.asFunction(e);
        for (var r = 0; r < this._handlers.length; r++) {
          var i = this._handlers[r];
          if ((i.handler == e || null == e) && (i.self == n || null == n) && (this._handlers.splice(r, 1), e && n)) break
        }
      }, n.prototype.removeAllHandlers = function() {
        this._handlers.length = 0
      }, n.prototype.raise = function(t, e) {
        void 0 === e && (e = r.empty);
        for (var n = 0; n < this._handlers.length; n++) {
          var i = this._handlers[n];
          i.handler.call(i.self, t, e)
        }
      }, Object.defineProperty(n.prototype, "hasHandlers", {
        get: function() {
          return this._handlers.length > 0
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "handlerCount", {
        get: function() {
          return this._handlers.length
        },
        enumerable: !0,
        configurable: !0
      }), n
    }();
  t.Event = n;
  var r = function() {
    function t() {}
    return t.empty = new t, t
  }();
  t.EventArgs = r;
  var i = function(t) {
    function e() {
      var e = null !== t && t.apply(this, arguments) || this;
      return e.cancel = !1, e
    }
    return __extends(e, t), e
  }(r);
  t.CancelEventArgs = i;
  var o = function(t) {
    function e(e, n, r) {
      var i = t.call(this) || this;
      return i._name = e, i._oldVal = n, i._newVal = r, i
    }
    return __extends(e, t), Object.defineProperty(e.prototype, "propertyName", {
      get: function() {
        return this._name
      },
      enumerable: !0,
      configurable: !0
    }), Object.defineProperty(e.prototype, "oldValue", {
      get: function() {
        return this._oldVal
      },
      enumerable: !0,
      configurable: !0
    }), Object.defineProperty(e.prototype, "newValue", {
      get: function() {
        return this._newVal
      },
      enumerable: !0,
      configurable: !0
    }), e
  }(r);
  t.PropertyChangedEventArgs = o;
  var s = function(t) {
    function e(e, n) {
      var r = t.call(this) || this;
      return r._xhr = e, r._msg = n, r
    }
    return __extends(e, t), Object.defineProperty(e.prototype, "request", {
      get: function() {
        return this._xhr
      },
      enumerable: !0,
      configurable: !0
    }), Object.defineProperty(e.prototype, "message", {
      get: function() {
        return this._msg
      },
      set: function(t) {
        this._msg = t
      },
      enumerable: !0,
      configurable: !0
    }), e
  }(i);
  t.RequestErrorEventArgs = s
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
      };
    return function(e, n) {
      function r() {
        this.constructor = e
      }
      t(e, n), e.prototype = null === n ? Object.create(n) : (r.prototype = n.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  "use strict";
  t.controlBaseClass = "undefined" != typeof window && window["wj-control-is-element"] ? HTMLElement : Object;
  var e = !1;
  try {
    var n = function() {
      return function() {}
    }();
    n.prototype = Array.prototype, e = n.prototype !== Array.prototype
  } catch (t) {
    e = !0
  }
  var r = e || t.controlBaseClass !== Object,
    i = function(t) {
      function e() {
        var e = this;
        return r && (e = t.call(this) || this), e
      }
      return __extends(e, t), e
    }(t.controlBaseClass);
  t.ControlBase = i;
  var o = function(e) {
    function n(r, i, o) {
      void 0 === i && (i = null), void 0 === o && (o = !1);
      var s = e.call(this) || this;
      s._listeners = [], s._pristine = !0, s._focus = !1, s._updating = 0, s._fullUpdate = !1, s.gotFocus = new t.Event, s.lostFocus = new t.Event, s.refreshing = new t.Event, s.refreshed = new t.Event, s._updateWme(), t.assert(null == n.getControl(r), "Element is already hosting a control.");
      var a = t.getElement(r);
      t.assert(null != a, "Cannot find the host element."), s._orgTabIndex = a.hasAttribute("tabindex") ? a.tabIndex : 0, s._orgOuter = a.outerHTML, s._orgTag = a.tagName, s._orgAtts = a.attributes, "INPUT" != a.tagName && "SELECT" != a.tagName || (a = s._replaceWithDiv(a)), s._e = a, a[n._CTRL_KEY] = s;
      var u = s.addEventListener.bind(s);
      if (1 == o) {
        s._szCtl = new t.Size(a.offsetWidth, a.offsetHeight);
        var c = s._handleResize.bind(s);
        u(window, "resize", c)
      }
      var l = s._handleFocusBlur.bind(s);
      u(a, "focus", l, !0), u(a, "blur", l, !0);
      var h = s._handleDisabled.bind(s);
      if (u(a, "mousedown", h, !0), u(a, "mouseup", h, !0), u(a, "click", h, !0), u(a, "dblclick", h, !0), u(a, "wheel", h, t.getEventOptions(!0, !0)), u(a, "keydown", function(e) {
        s.isDisabled && e.keyCode != t.Key.Tab && e.preventDefault()
      }), null == n._touching && (n._touching = !1, "ontouchstart" in window || "onpointerdown" in window)) {
        var p = document.body,
          f = p.addEventListener.bind(p),
          d = s._handleTouchStart,
          g = s._handleTouchEnd,
          m = t.getEventOptions(!0, !0);
        "ontouchstart" in window ? (f("touchstart", d, m), f("touchend", g, m), f("touchcancel", g, m), f("touchleave", g, m)) : "onpointerdown" in window && (f("pointerdown", d, m), f("pointerup", g, m), f("pointerout", g, m), f("pointercancel", g, m), f("pointerleave", g, m))
      }
      return s
    }
    return __extends(n, e), n.prototype.getTemplate = function() {
      for (var t = Object.getPrototypeOf(this); t; t = Object.getPrototypeOf(t)) {
        var e = t.constructor.controlTemplate;
        if (e) return e
      }
      return null
    }, n.prototype.applyTemplate = function(e, r, i, o) {
      var s = this,
        a = this._e;
      e && t.addClass(a, e);
      var u = null;
      r && (u = t.createElement(r, a));
      for (var c = a.querySelectorAll("a.wj-btn"), l = 0; l < c.length; l++) {
        var h = c[l];
        t.setAttribute(h, "role", "button", !0), t.setAttribute(h, "href", "", !0), t.setAttribute(h, "draggable", !1, !0)
      }
      for (var p = a.querySelectorAll("button"), l = 0; l < p.length; l++) t.setAttribute(p[l], "type", "button", !0);
      var f = a.querySelectorAll("input"),
        d = 1 == f.length ? f[0] : null;
      if (d) {
        var g = n._rxInputAtts;
        this._copyAttributes(d, a.attributes, g), this._copyAttributes(d, this._orgAtts, g)
      }
      if (d && a.id) {
        for (var m = a; m.parentElement;) m = m.parentElement;
        var _ = m.querySelector('label[for="' + a.id + '"]');
        if (_ instanceof HTMLLabelElement) {
          var v = t.getUniqueId(a.id + "_input");
          d.id = v, _.htmlFor = v
        }
      }
      if (d) {
        var y = document.createEvent("HTMLEvents"),
          b = d.value;
        y.initEvent("change", !0, !1), this.addEventListener(d, "input", function() {
          s._pristine = !1, b = d.value
        }, !0), this.gotFocus.addHandler(function() {
          b = d.value
        }), this.lostFocus.addHandler(function() {
          s._pristine && (s._pristine = !1, s._updateState()), b != d.value && d.dispatchEvent(y)
        })
      }
      if (d ? a.tabIndex = -1 : a.getAttribute("tabindex") || (a.tabIndex = 0), this._updateState(), i)
        for (var C in i) {
          var w = i[C];
          if (this[C] = u.querySelector('[wj-part="' + w + '"]'), null == this[C] && u.getAttribute("wj-part") == w && (this[C] = u), null == this[C]) throw 'Missing template part: "' + w + '"';
          if (w == o) {
            var E = "name",
              A = a.attributes[E];
            A && A.value && this[C].setAttribute(E, A.value), E = "accesskey", (A = a.attributes[E]) && A.value && (this[C].setAttribute(E, A.value), a.removeAttribute(E))
          }
        }
      return u
    }, n.prototype.dispose = function() {
      if (this._e) {
        for (var e = this._e.querySelectorAll(".wj-control"), r = 0; r < e.length; r++) {
          var i = n.getControl(e[r]);
          i && i.dispose()
        }
        this._toInv && (clearTimeout(this._toInv), this._toInv = null), this.removeEventListener();
        for (var o in this) o.length > 2 && 0 == o.indexOf("on") && (a = this[o[2].toLowerCase() + o.substr(3)]) instanceof t.Event && a.removeAllHandlers();
        var s = this.collectionView;
        if (s instanceof t.collections.CollectionView)
          for (var o in s) {
            var a = s[o];
            a instanceof t.Event && a.removeHandler(null, this)
          }
        this._e.parentNode && (this._e.outerHTML = this._orgOuter), this._e[n._CTRL_KEY] = null, this._e = this._orgOuter = this._orgTag = null
      }
    }, n.getControl = function(e) {
      var r = t.getElement(e);
      return r ? t.asType(r[n._CTRL_KEY], n, !0) : null
    }, Object.defineProperty(n.prototype, "hostElement", {
      get: function() {
        return this._e
      },
      enumerable: !0,
      configurable: !0
    }), Object.defineProperty(n.prototype, "rightToLeft", {
      get: function() {
        return null == this._rtlDir && (this._rtlDir = !!this._e && "rtl" == getComputedStyle(this._e).direction), this._rtlDir
      },
      enumerable: !0,
      configurable: !0
    }), n.prototype.focus = function() {
      var e = this._e;
      e && (e.tabIndex >= 0 || !t.moveFocus(e, 0)) && e.focus()
    }, n.prototype.containsFocus = function() {
      var e = this._e,
        r = t.getActiveElement();
      if (!e) return !1;
      for (var i = r; i;) {
        if (i == e) return !0;
        i = i[n._OWNR_KEY] || i.parentNode
      }
      return !1
    }, n.prototype.invalidate = function(t) {
      var e = this;
      void 0 === t && (t = !0), this._rtlDir = null, this._fullUpdate = this._fullUpdate || t, this._toInv && (clearTimeout(this._toInv), this._toInv = null), this.isUpdating || (this._toInv = setTimeout(function() {
        e.refresh(e._fullUpdate), e._toInv = null
      }, n._REFRESH_INTERVAL))
    }, n.prototype.refresh = function(t) {
      var e = this;
      void 0 === t && (t = !0), this.isUpdating || (this.onRefreshing(), setTimeout(function() {
        e.onRefreshed()
      })), !this.isUpdating && this._toInv && (clearTimeout(this._toInv), this._toInv = null, this._fullUpdate = !1), this._updateState(), this._updateWme()
    }, n.invalidateAll = function(t) {
      if (t || (t = document.body), t.children)
        for (var e = 0; e < t.children.length; e++) n.invalidateAll(t.children[e]);
      var r = n.getControl(t);
      r && r.invalidate()
    }, n.refreshAll = function(t) {
      if (t || (t = document.body), t.children)
        for (var e = 0; e < t.children.length; e++) n.refreshAll(t.children[e]);
      var r = n.getControl(t);
      r && r.refresh()
    }, n.disposeAll = function(t) {
      var e = n.getControl(t);
      if (e) e.dispose();
      else if (t.children)
        for (var r = 0; r < t.children.length; r++) n.disposeAll(t.children[r])
    }, n.prototype.beginUpdate = function() {
      this._updating++
    }, n.prototype.endUpdate = function() {
      this._updating--, this._updating <= 0 && this.invalidate()
    }, Object.defineProperty(n.prototype, "isUpdating", {
      get: function() {
        return this._updating > 0
      },
      enumerable: !0,
      configurable: !0
    }), n.prototype.deferUpdate = function(t) {
      try {
        this.beginUpdate(), t()
      } finally {
        this.endUpdate()
      }
    }, Object.defineProperty(n.prototype, "isTouching", {
      get: function() {
        return n._touching
      },
      enumerable: !0,
      configurable: !0
    }), Object.defineProperty(n.prototype, "isDisabled", {
      get: function() {
        return this._e && null != this._e.getAttribute("disabled")
      },
      set: function(e) {
        if ((e = t.asBoolean(e, !0)) != this.isDisabled) {
          var n = this._e;
          n && (t.enable(n, !e), n.tabIndex = this.isDisabled ? -1 : this._orgTabIndex)
        }
      },
      enumerable: !0,
      configurable: !0
    }), n.prototype.initialize = function(e) {
      e && t.copy(this, e)
    }, n.prototype.addEventListener = function(e, n, r, i, o) {
      if (e) {
        null == i && (i = !1), t.isBoolean(i) && null == o && n.match(/touch|wheel/) && (o = !1);
        var s = t.isBoolean(i) && t.isBoolean(o) ? t.getEventOptions(i, o) : i;
        e.addEventListener(n, r, s), this._listeners.push({
          target: e,
          type: n,
          fn: r,
          capture: i
        })
      }
    }, n.prototype.removeEventListener = function(t, e, n, r) {
      for (var i = 0, o = 0; o < this._listeners.length; o++) {
        var s = this._listeners[o];
        null != t && t != s.target || null != e && e != s.type || (null == n || n == s.fn || n && s.fn && n.toString() == s.fn.toString()) && (null != r && r != s.capture || (s.target.removeEventListener(s.type, s.fn, s.capture), this._listeners.splice(o, 1), o--, i++))
      }
      return i
    }, n.prototype.onGotFocus = function(t) {
      this.gotFocus.raise(this, t)
    }, n.prototype.onLostFocus = function(t) {
      this.lostFocus.raise(this, t)
    }, n.prototype.onRefreshing = function(t) {
      this.refreshing.raise(this, t)
    }, n.prototype.onRefreshed = function(t) {
      this.refreshed.raise(this, t)
    }, n.prototype._getProductInfo = function() {
      return "B0C3,Control"
    }, n.prototype._updateWme = function() {
      var e = window.wijmo._updateWme;
      t.isFunction(e) && e(this, n._licKey)
    }, n.prototype._hasPendingUpdates = function() {
      return null != this._toInv
    }, n.prototype._handleResize = function() {
      if (this._e.parentElement) {
        var e = new t.Size(this._e.offsetWidth, this._e.offsetHeight);
        e.equals(this._szCtl) || (this._szCtl = e, this.invalidate())
      }
    }, n.prototype._handleFocusBlur = function() {
      var t = this;
      this._toFocus || (this._toFocus = setTimeout(function() {
        t._toFocus = null, t._updateFocusState()
      }, n._FOCUS_INTERVAL))
    }, n.prototype._updateFocusState = function() {
      setTimeout(function() {
        for (var e = t.EventArgs.empty, r = document.body.querySelectorAll(".wj-state-focused"), i = 0; i < r.length; i++)(u = n.getControl(r[i])) && u._focus && !u.containsFocus() && (t.removeClass(u._e, "wj-state-focus"), u._focus = !1, u._updateState(), u.onLostFocus(e));
        var o = t.getActiveElement();
        if (o)
          for (var s = !0, a = o; a;) {
            var u = n.getControl(a);
            u && !u._focus && u.containsFocus() && (s && (t.addClass(a, "wj-state-focus"), s = !1), u._focus = !0, u._updateState(), u.onGotFocus(e)), a = a[n._OWNR_KEY] || a.parentNode
          }
      })
    }, n.prototype._updateState = function() {
      var e = this.hostElement;
      if (e) {
        t.toggleClass(e, "wj-state-focused", this._focus), this._rtlDir = "rtl" == getComputedStyle(e).direction, t.toggleClass(e, "wj-rtl", this._rtlDir);
        var n = e.querySelector("input");
        if (n instanceof HTMLInputElement) {
          t.toggleClass(e, "wj-state-empty", 0 == n.value.length), t.toggleClass(e, "wj-state-readonly", n.readOnly);
          var r = n.validationMessage;
          t.toggleClass(e, "wj-state-invalid", !this._pristine && null != r && r.length > 0)
        }
      }
    }, n.prototype._handleTouchStart = function(t) {
      null != t.pointerType && "touch" != t.pointerType || (n._toTouch && (clearTimeout(n._toTouch), n._toTouch = null), n._touching = !0)
    }, n.prototype._handleTouchEnd = function(t) {
      null != t.pointerType && "touch" != t.pointerType || (n._toTouch = setTimeout(function() {
        n._toTouch = null, n._touching = !1
      }, 900))
    }, n.prototype._handleDisabled = function(e) {
      (this.isDisabled || t.closest(e.target, ".wj-state-disabled")) && (e.preventDefault(), e.stopPropagation(), e.stopImmediatePropagation())
    }, n.prototype._replaceWithDiv = function(t) {
      var e = document.createElement("div");
      return t.parentElement.replaceChild(e, t), e.innerHTML = t.innerHTML, this._copyAttributes(e, t.attributes, /id|style|class/i), e
    }, n.prototype._copyAttributes = function(t, e, n) {
      if (t)
        for (var r = 0; r < e.length; r++) {
          var i = e[r].name;
          i.match(n) && t.setAttribute(i, e[r].value)
        }
    }, n.prototype._getKeyCode = function(e) {
      var n = e.keyCode;
      if (this.rightToLeft) switch (n) {
        case t.Key.Right:
          n = t.Key.Left;
          break;
        case t.Key.Left:
          n = t.Key.Right
      }
      return n
    }, n._REFRESH_INTERVAL = 10, n._FOCUS_INTERVAL = 0, n._ANIM_DEF_DURATION = 400, n._ANIM_DEF_STEP = 35, n._CLICK_DELAY = 800, n._CLICK_REPEAT = 75, n._CLIPBOARD_DELAY = 100, n._POPUP_ZINDEX = 1500, n._SEARCH_DELAY = 600, n._HOVER_DELAY = 300, n._DRAG_SCROLL_EDGE = 15, n._DRAG_SCROLL_STEP = 10, n._CTRL_KEY = "$WJ-CTRL", n._OWNR_KEY = "$WJ-OWNR", n._SCRL_KEY = "$WJ-SCRL", n._rxInputAtts = /name|tabindex|placeholder|autofocus|autocomplete|autocorrect|autocapitalize|spellcheck|readonly|minlength|maxlength|pattern|type|aria-.+/i, n
  }(i);
  t.Control = o
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  "use strict";
  var e;
  ! function(t) {
    t[t.None = 0] = "None", t[t.Sum = 1] = "Sum", t[t.Cnt = 2] = "Cnt", t[t.Avg = 3] = "Avg", t[t.Max = 4] = "Max", t[t.Min = 5] = "Min", t[t.Rng = 6] = "Rng", t[t.Std = 7] = "Std", t[t.Var = 8] = "Var", t[t.StdPop = 9] = "StdPop", t[t.VarPop = 10] = "VarPop", t[t.CntAll = 11] = "CntAll", t[t.First = 12] = "First", t[t.Last = 13] = "Last"
  }(e = t.Aggregate || (t.Aggregate = {})), t.getAggregate = function(n, r, i) {
    var o = 0,
      s = 0,
      a = 0,
      u = 0,
      c = null,
      l = null,
      h = null,
      p = i ? new t.Binding(i) : null;
    if ((n = t.asEnum(n, e)) == e.CntAll) return r.length;
    for (var f = 0; f < r.length; f++) {
      var d = r[f];
      if (p && (d = p.getValue(d)), n == e.First) return d;
      null != d && (o++, (null == c || d < c) && (c = d), (null == l || d > l) && (l = d), h = d, t.isNumber(d) && !isNaN(d) ? (s++, a += d, u += d * d) : t.isBoolean(d) && (s++, 1 == d && (a++, u++)))
    }
    var g = 0 == s ? 0 : a / s;
    switch (n) {
      case e.Avg:
        return g;
      case e.Cnt:
        return o;
      case e.Max:
        return l;
      case e.Min:
        return c;
      case e.Rng:
        return l - c;
      case e.Sum:
        return a;
      case e.VarPop:
        return s <= 1 ? 0 : u / s - g * g;
      case e.StdPop:
        return s <= 1 ? 0 : Math.sqrt(u / s - g * g);
      case e.Var:
        return s <= 1 ? 0 : (u / s - g * g) * s / (s - 1);
      case e.Std:
        return s <= 1 ? 0 : Math.sqrt((u / s - g * g) * s / (s - 1));
      case e.Last:
        return h
    }
    throw "Invalid aggregate type."
  }
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
      };
    return function(e, n) {
      function r() {
        this.constructor = e
      }
      t(e, n), e.prototype = null === n ? Object.create(n) : (r.prototype = n.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n;
    ! function(t) {
      t[t.Add = 0] = "Add", t[t.Remove = 1] = "Remove", t[t.Change = 2] = "Change", t[t.Reset = 3] = "Reset"
    }(n = e.NotifyCollectionChangedAction || (e.NotifyCollectionChangedAction = {}));
    var r = function(t) {
      function e(e, r, i) {
        void 0 === e && (e = n.Reset), void 0 === r && (r = null), void 0 === i && (i = -1);
        var o = t.call(this) || this;
        return o.action = e, o.item = r, o.index = i, o
      }
      return __extends(e, t), e.reset = new e(n.Reset), e
    }(t.EventArgs);
    e.NotifyCollectionChangedEventArgs = r;
    var i = function() {
      function e(e, n) {
        this._bnd = new t.Binding(e), this._asc = n
      }
      return Object.defineProperty(e.prototype, "property", {
        get: function() {
          return this._bnd.path
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "ascending", {
        get: function() {
          return this._asc
        },
        enumerable: !0,
        configurable: !0
      }), e
    }();
    e.SortDescription = i;
    var o = function(t) {
      function e(e) {
        var n = t.call(this) || this;
        return n.newPageIndex = e, n
      }
      return __extends(e, t), e
    }(t.CancelEventArgs);
    e.PageChangingEventArgs = o;
    var s = function() {
      function t() {}
      return t.prototype.groupNameFromItem = function(t, e) {
        return ""
      }, t.prototype.namesMatch = function(t, e) {
        return t === e
      }, t
    }();
    e.GroupDescription = s;
    var a = function(e) {
      function n(n, r) {
        var i = e.call(this) || this;
        return i._bnd = new t.Binding(n), i._converter = r, i
      }
      return __extends(n, e), Object.defineProperty(n.prototype, "propertyName", {
        get: function() {
          return this._bnd.path
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.groupNameFromItem = function(t, e) {
        return this._converter ? this._converter(t, this.propertyName) : this._bnd.getValue(t)
      }, n.prototype.namesMatch = function(t, e) {
        return t === e
      }, n
    }(s);
    e.PropertyGroupDescription = a
  }(t.collections || (t.collections = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
      };
    return function(e, n) {
      function r() {
        this.constructor = e
      }
      t(e, n), e.prototype = null === n ? Object.create(n) : (r.prototype = n.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = function(t) {
      function e() {
        var e = this;
        return r ? e.length = 0 : e = t.call(this) || this, e
      }
      return __extends(e, t), e
    }(Array);
    e.ArrayBase = n;
    var r = !0;
    try {
      n.prototype = Array.prototype, r = n.prototype === Array.prototype
    } catch (t) {
      r = !1
    }
    var i = "undefined" != typeof window ? window.Symbol : null;
    !r && i && i.species && Object.defineProperty(n, i.species, {
      get: function() {
        return Array
      },
      enumerable: !1,
      configurable: !1
    });
    var o = function(n) {
      function r(e) {
        var r = n.call(this) || this;
        if (r._updating = 0, r.collectionChanged = new t.Event, e) {
          e = t.asArray(e), r._updating++;
          for (var i = 0; i < e.length; i++) r.push(e[i]);
          r._updating--
        }
        return r
      }
      return __extends(r, n), r.prototype.push = function() {
        for (var t = [], r = 0; r < arguments.length; r++) t[r] = arguments[r];
        for (var i = this.length, o = 0; t && o < t.length; o++) i = n.prototype.push.call(this, t[o]), this._updating || this._raiseCollectionChanged(e.NotifyCollectionChangedAction.Add, t[o], i - 1);
        return i
      }, r.prototype.pop = function() {
        var t = n.prototype.pop.call(this);
        return this._raiseCollectionChanged(e.NotifyCollectionChangedAction.Remove, t, this.length), t
      }, r.prototype.splice = function(t, r, i) {
        var o, s = e.NotifyCollectionChangedAction;
        return r && i ? (o = n.prototype.splice.call(this, t, r, i), 1 == r ? this._raiseCollectionChanged(s.Change, i, t) : this._raiseCollectionChanged(), o) : i ? (o = n.prototype.splice.call(this, t, 0, i), this._raiseCollectionChanged(s.Add, i, t), o) : (o = n.prototype.splice.call(this, t, r), 1 == r ? this._raiseCollectionChanged(s.Remove, o[0], t) : this._raiseCollectionChanged(), o)
      }, r.prototype.slice = function(t, e) {
        return n.prototype.slice.call(this, t, e)
      }, r.prototype.indexOf = function(t, e) {
        return n.prototype.indexOf.call(this, t, e)
      }, r.prototype.sort = function(t) {
        var e = n.prototype.sort.call(this, t);
        return this._raiseCollectionChanged(), e
      }, r.prototype.insert = function(t, e) {
        this.splice(t, 0, e)
      }, r.prototype.remove = function(t) {
        var e = this.indexOf(t);
        return e > -1 && (this.removeAt(e), !0)
      }, r.prototype.removeAt = function(t) {
        this.splice(t, 1)
      }, r.prototype.setAt = function(t, e) {
        t > this.length && (this.length = t), this.splice(t, 1, e)
      }, r.prototype.clear = function() {
        this.length && this.splice(0, this.length)
      }, r.prototype.beginUpdate = function() {
        this._updating++
      }, r.prototype.endUpdate = function() {
        this._updating > 0 && (this._updating--, 0 == this._updating && this._raiseCollectionChanged())
      }, Object.defineProperty(r.prototype, "isUpdating", {
        get: function() {
          return this._updating > 0
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype.deferUpdate = function(t) {
        try {
          this.beginUpdate(), t()
        } finally {
          this.endUpdate()
        }
      }, r.prototype.implementsInterface = function(t) {
        return "INotifyCollectionChanged" == t
      }, r.prototype.onCollectionChanged = function(t) {
        void 0 === t && (t = e.NotifyCollectionChangedEventArgs.reset), this.isUpdating || this.collectionChanged.raise(this, t)
      }, r.prototype._raiseCollectionChanged = function(t, n, r) {
        if (void 0 === t && (t = e.NotifyCollectionChangedAction.Reset), !this.isUpdating) {
          var i = new e.NotifyCollectionChangedEventArgs(t, n, r);
          this.onCollectionChanged(i)
        }
      }, r
    }(n);
    e.ObservableArray = o
  }(t.collections || (t.collections = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = function() {
      function n(n, r) {
        var i = this;
        this._idx = -1, this._srtDsc = new e.ObservableArray, this._grpDesc = new e.ObservableArray, this._newItem = null, this._edtItem = null, this._pgSz = 0, this._pgIdx = 0, this._updating = 0, this._stableSort = !1, this._nullsFirst = !1, this._canFilter = !0, this._canGroup = !0, this._canSort = !0, this._canAddNew = !0, this._canCancelEdit = !0, this._canRemove = !0, this._canChangePage = !0, this._trackChanges = !1, this._chgAdded = new e.ObservableArray, this._chgRemoved = new e.ObservableArray, this._chgEdited = new e.ObservableArray, this.collectionChanged = new t.Event, this.sourceCollectionChanging = new t.Event, this.sourceCollectionChanged = new t.Event, this.currentChanged = new t.Event, this.currentChanging = new t.Event, this.pageChanged = new t.Event, this.pageChanging = new t.Event, this._srtDsc.collectionChanged.addHandler(function() {
          i._srtDsc.forEach(function(n) {
            t.assert(n instanceof e.SortDescription, "sortDescriptions array must contain SortDescription objects.")
          }), i.canSort && i.refresh()
        }), this._grpDesc.collectionChanged.addHandler(function() {
          i._grpDesc.forEach(function(n) {
            t.assert(n instanceof e.GroupDescription, "groupDescriptions array must contain GroupDescription objects.")
          }), i.canGroup && (i.currentEditItem || i.currentAddItem ? i.commitEdit() : i.refresh())
        }), this.sourceCollection = n || new e.ObservableArray, r && (this.beginUpdate(), t.copy(this, r), this.endUpdate())
      }
      return n.prototype._copy = function(n, r) {
        switch (n) {
          case "sortDescriptions":
            this.sortDescriptions.clear();
            for (var i = t.asArray(r), o = 0; o < i.length; o++) {
              a = i[o];
              t.isString(a) && (a = new e.SortDescription(a, !0)), this.sortDescriptions.push(a)
            }
            return !0;
          case "groupDescriptions":
            this.groupDescriptions.clear();
            for (var s = t.asArray(r), o = 0; o < s.length; o++) {
              var a = s[o];
              t.isString(a) && (a = new e.PropertyGroupDescription(a)), this.groupDescriptions.push(a)
            }
            return !0;
          case "currentItem":
            return this.currentItem = r, !0
        }
        return !1
      }, Object.defineProperty(n.prototype, "newItemCreator", {
        get: function() {
          return this._itemCreator
        },
        set: function(e) {
          this._itemCreator = t.asFunction(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "sortConverter", {
        get: function() {
          return this._srtCvt
        },
        set: function(e) {
          e != this._srtCvt && (this._srtCvt = t.asFunction(e, !0))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "sortComparer", {
        get: function() {
          return this._srtCmp
        },
        set: function(e) {
          e != this._srtCmp && (this._srtCmp = t.asFunction(e, !0))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "useStableSort", {
        get: function() {
          return this._stableSort
        },
        set: function(e) {
          this._stableSort = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "sortNullsFirst", {
        get: function() {
          return this._nullsFirst
        },
        set: function(e) {
          this._nullsFirst = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.getAggregate = function(e, n, r) {
        var i = r ? this._pgView : this._view;
        return t.getAggregate(e, i, n)
      }, Object.defineProperty(n.prototype, "trackChanges", {
        get: function() {
          return this._trackChanges
        },
        set: function(e) {
          this._trackChanges = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "itemsAdded", {
        get: function() {
          return this._chgAdded
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "itemsRemoved", {
        get: function() {
          return this._chgRemoved
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "itemsEdited", {
        get: function() {
          return this._chgEdited
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.clearChanges = function() {
        this._chgAdded.clear(), this._chgRemoved.clear(), this._chgEdited.clear()
      }, n.prototype.implementsInterface = function(t) {
        switch (t) {
          case "ICollectionView":
          case "IEditableCollectionView":
          case "IPagedCollectionView":
          case "INotifyCollectionChanged":
            return !0
        }
        return !1
      }, Object.defineProperty(n.prototype, "getError", {
        get: function() {
          return this._getError
        },
        set: function(e) {
          this._getError != e && (this._getError = t.asFunction(e), this._raiseCollectionChanged())
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.onCollectionChanged = function(t) {
        void 0 === t && (t = e.NotifyCollectionChangedEventArgs.reset), t.action != e.NotifyCollectionChangedAction.Change || this._committing || this._canceling || t.item == this.currentEditItem || t.item == this.currentAddItem || this._trackItemChanged(t.item), this.collectionChanged.raise(this, t)
      }, n.prototype._raiseCollectionChanged = function(t, n, r) {
        void 0 === t && (t = e.NotifyCollectionChangedAction.Reset);
        var i = new e.NotifyCollectionChangedEventArgs(t, n, r);
        this.onCollectionChanged(i)
      }, n.prototype._notifyItemChanged = function(t) {
        var n = new e.NotifyCollectionChangedEventArgs(e.NotifyCollectionChangedAction.Change, t, this.items.indexOf(t));
        this.onCollectionChanged(n)
      }, n.prototype.onSourceCollectionChanging = function(t) {
        return this.sourceCollectionChanging.raise(this, t), !t.cancel
      }, n.prototype.onSourceCollectionChanged = function(t) {
        this.sourceCollectionChanged.raise(this, t)
      }, Object.defineProperty(n.prototype, "canFilter", {
        get: function() {
          return this._canFilter
        },
        set: function(e) {
          this._canFilter = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "canGroup", {
        get: function() {
          return this._canGroup
        },
        set: function(e) {
          this._canGroup = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "canSort", {
        get: function() {
          return this._canSort
        },
        set: function(e) {
          this._canSort = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "currentItem", {
        get: function() {
          return this._pgView && this._idx > -1 && this._idx < this._pgView.length ? this._pgView[this._idx] : null
        },
        set: function(t) {
          this.moveCurrentTo(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "currentPosition", {
        get: function() {
          return this._idx
        },
        set: function(e) {
          this.moveCurrentToPosition(t.asNumber(e))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "filter", {
        get: function() {
          return this._filter
        },
        set: function(e) {
          this._filter != e && (this._filter = t.asFunction(e), this.canFilter && this.refresh())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "groupDescriptions", {
        get: function() {
          return this._grpDesc
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "groups", {
        get: function() {
          return this._groups
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "isEmpty", {
        get: function() {
          return !this._pgView || !this._pgView.length
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "sortDescriptions", {
        get: function() {
          return this._srtDsc
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "sourceCollection", {
        get: function() {
          return this._src
        },
        set: function(e) {
          if (e != this._src) {
            if (!this.onSourceCollectionChanging(new t.CancelEventArgs)) return;
            var n = this.currentPosition;
            this.commitEdit(), null != this._ncc && this._ncc.collectionChanged.removeHandler(this._sourceChanged), this._src = t.asArray(e, !1), this._ncc = t.tryCast(this._src, "INotifyCollectionChanged"), this._ncc && this._ncc.collectionChanged.addHandler(this._sourceChanged, this), this.clearChanges(), this.refresh(), this.moveCurrentToFirst(), this.onSourceCollectionChanged(), this.currentPosition < 0 && n > -1 && this.onCurrentChanged()
          }
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype._sourceChanged = function(t, e) {
        this._updating <= 0 && this.refresh()
      }, n.prototype.contains = function(t) {
        return this._pgView.indexOf(t) > -1
      }, n.prototype.moveCurrentTo = function(t) {
        return this.moveCurrentToPosition(this._pgView.indexOf(t))
      }, n.prototype.moveCurrentToFirst = function() {
        return this.moveCurrentToPosition(0)
      }, n.prototype.moveCurrentToLast = function() {
        return this.moveCurrentToPosition(this._pgView.length - 1)
      }, n.prototype.moveCurrentToPrevious = function() {
        return this._idx > 0 && this.moveCurrentToPosition(this._idx - 1)
      }, n.prototype.moveCurrentToNext = function() {
        return this.moveCurrentToPosition(this._idx + 1)
      }, n.prototype.moveCurrentToPosition = function(e) {
        if (e >= -1 && e < this._pgView.length && e != this._idx) {
          var n = new t.CancelEventArgs;
          if (this.onCurrentChanging(n)) {
            var r = this._pgView[e];
            this._edtItem && r != this._edtItem ? this.commitEdit() : this._newItem && r != this._newItem && this.commitNew(), this._idx = e, this.onCurrentChanged()
          }
        }
        return this._idx == e
      }, n.prototype.refresh = function() {
        this._newItem || this._edtItem ? this._pendingRefresh = !0 : this._updating <= 0 && (this._performRefresh(), this.onCollectionChanged())
      }, n.prototype._performRefresh = function() {
        if (!(this._updating > 0)) {
          this._pendingRefresh = !1;
          var e = this.currentItem;
          this._view = this._src ? this._performFilter(this._src) : [], this.canSort && this._srtDsc.length > 0 && (this._view == this._src && (this._view = this._src.slice()), this._performSort(this._view)), this._groups = this.canGroup ? this._createGroups(this._view) : null, this._fullGroups = this._groups, this._groups && (this._view = this._mergeGroupItems(this._groups)), this._pgIdx = t.clamp(this._pgIdx, 0, this.pageCount - 1), this._pgView = this._getPageView(), this._groups && this.pageCount > 1 && (this._groups = this._createGroups(this._pgView), this._mergeGroupItems(this._groups));
          var n = this._pgView.indexOf(e);
          n < 0 && (n = Math.min(this._idx, this._pgView.length - 1)), this._idx = n, this._digest = this._getGroupsDigest(this.groups), this.currentItem !== e && this.onCurrentChanged()
        }
      }, n.prototype._performSort = function(t) {
        if (this._stableSort) {
          var e = t.map(function(t, e) {
              return {
                item: t,
                index: e
              }
            }),
            n = this._compareItems();
          e.sort(function(t, e) {
            var r = n(t.item, e.item);
            return 0 == r ? t.index - e.index : r
          });
          for (var r = 0; r < t.length; r++) t[r] = e[r].item
        } else t.sort(this._compareItems())
      }, n.prototype._compareItems = function() {
        var t = this._srtDsc,
          e = this._srtCvt,
          r = this._srtCmp,
          i = this._nullsFirst ? -1 : 1,
          o = !0,
          s = 0;
        return function(a, u) {
          for (var c = 0; c < t.length; c++) {
            var l = t[c],
              h = l._bnd.getValue(a),
              p = l._bnd.getValue(u);
            if (e && (h = e(l, a, h, o), p = e(l, u, p, !1), o = !1), r && null != (s = r(h, p))) return l.ascending ? +s : -s;
            if (h !== h && (h = null), p !== p && (p = null), null != h && null == p) return -1 * i;
            if (null == h && null != p) return 1 * i;
            if ("string" == typeof h && "string" == typeof p) {
              var f = n._collator;
              if (f) {
                if (0 != (s = f.compare(h, p))) return l.ascending ? +s : -s;
                continue
              }
              var d = h.toLowerCase(),
                g = p.toLowerCase();
              d != g && (h = d, p = g)
            }
            if (0 != (s = h < p ? -1 : h > p ? 1 : 0)) return l.ascending ? +s : -s
          }
          return 0
        }
      }, n.prototype._performFilter = function(t) {
        return this.canFilter && this._filter ? t.filter(this._filter, this) : t
      }, n.prototype.onCurrentChanged = function(e) {
        void 0 === e && (e = t.EventArgs.empty), this.currentChanged.raise(this, e)
      }, n.prototype.onCurrentChanging = function(t) {
        return this.currentChanging.raise(this, t), !t.cancel
      }, Object.defineProperty(n.prototype, "items", {
        get: function() {
          return this._pgView
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.beginUpdate = function() {
        this._updating++
      }, n.prototype.endUpdate = function() {
        this._updating--, this._updating <= 0 && this.refresh()
      }, Object.defineProperty(n.prototype, "isUpdating", {
        get: function() {
          return this._updating > 0
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.deferUpdate = function(t) {
        try {
          this.beginUpdate(), t()
        } finally {
          this.endUpdate()
        }
      }, Object.defineProperty(n.prototype, "canAddNew", {
        get: function() {
          return this._canAddNew
        },
        set: function(e) {
          this._canAddNew = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "canCancelEdit", {
        get: function() {
          return this._canCancelEdit
        },
        set: function(e) {
          this._canCancelEdit = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "canRemove", {
        get: function() {
          return this._canRemove
        },
        set: function(e) {
          this._canRemove = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "currentAddItem", {
        get: function() {
          return this._newItem
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "currentEditItem", {
        get: function() {
          return this._edtItem
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "isAddingNew", {
        get: function() {
          return null != this._newItem
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "isEditingItem", {
        get: function() {
          return null != this._edtItem
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.editItem = function(t) {
        t != this._edtItem && this.moveCurrentTo(t) && (this.commitEdit(), this._edtItem = t, this._edtClone = {}, this._extend(this._edtClone, this._edtItem))
      }, n.prototype.commitEdit = function() {
        var t = this._edtItem,
          n = this._edtClone;
        if (null != t) {
          this._committing = !0;
          var r = this._sameContent(t, n),
            i = this._pendingRefresh;
          this._edtItem = null, this._edtClone = null;
          var o = this._pgView.indexOf(t),
            s = this._digest;
          this._performRefresh(), r || this._trackItemChanged(t), this._pgView.indexOf(t) != o || s != this._digest || i ? this._raiseCollectionChanged() : this._raiseCollectionChanged(e.NotifyCollectionChangedAction.Change, t, o), this.commitNew(), this._committing = !1
        }
      }, n.prototype.cancelEdit = function() {
        var n = this._edtItem;
        if (null != n) {
          if (this._edtItem = null, !this.canCancelEdit) return void t.assert(!1, "cannot cancel edits (canCancelEdit == false).");
          var r = this._src.indexOf(n);
          r >= 0 && this._edtClone && (this._extend(this._src[r], this._edtClone), this._edtClone = null, this._canceling = !0, this._raiseCollectionChanged(e.NotifyCollectionChangedAction.Change, n, r), this._canceling = !1, this._pendingRefresh && (this._performRefresh(), this._raiseCollectionChanged()))
        }
      }, n.prototype.addNew = function() {
        if (arguments.length > 0 && t.assert(!1, "addNew does not take any parameters, it creates the new items."), this.commitEdit(), !this.canAddNew) return t.assert(!1, "cannot add items (canAddNew == false)."), null;
        var n = null,
          r = this.sourceCollection;
        if (null != (n = this.newItemCreator ? this.newItemCreator() : r && r.length ? new r[0].constructor : {})) {
          if (this._newItem = n, this._updating++, this._src.push(n), this._updating--, this._pgView != this._src && this._pgView.push(n), this.groups && this.groups.length) {
            var i = this.groups[this.groups.length - 1];
            for (i.items.push(n); i.groups && i.groups.length;)(i = i.groups[i.groups.length - 1]).items.push(n)
          }
          this._raiseCollectionChanged(e.NotifyCollectionChangedAction.Add, n, this._pgView.length - 1), this.moveCurrentTo(n)
        }
        return this._newItem
      }, n.prototype.commitNew = function() {
        var t = this._newItem;
        if (null != t) {
          this._newItem = null;
          var n = this._pgView.indexOf(t),
            r = this._digest;
          if (this._performRefresh(), this._trackChanges) {
            var i = this._chgEdited.indexOf(t);
            i > -1 && this._chgEdited.removeAt(i), this._chgAdded.indexOf(t) < 0 && this._chgAdded.push(t)
          }
          this._pgView.indexOf(t) == n && r == this._digest ? this._raiseCollectionChanged(e.NotifyCollectionChangedAction.Change, t, n) : this._raiseCollectionChanged()
        }
      }, n.prototype.cancelNew = function() {
        var t = this._newItem;
        null != t && this.remove(t)
      }, n.prototype.remove = function(n) {
        var r = n == this._newItem;
        if (r && (this._newItem = null), n == this._edtItem && this.cancelEdit(), this.canRemove) {
          var i = this._src.indexOf(n);
          if (i > -1) {
            var o = this.currentItem;
            this._updating++, this._src.splice(i, 1), this._updating--;
            var s = this._digest;
            if (this._performRefresh(), this._trackChanges) {
              var a = this._chgAdded.indexOf(n);
              a > -1 && this._chgAdded.removeAt(a);
              var u = this._chgEdited.indexOf(n);
              u > -1 && this._chgEdited.removeAt(u), this._chgRemoved.indexOf(n) < 0 && !r && a < 0 && this._chgRemoved.push(n)
            }
            var c = this.sortDescriptions.length > 0,
              l = this.pageSize > 0 && this._pgIdx > -1;
            c || l || s != this._getGroupsDigest(this.groups) ? this._raiseCollectionChanged() : this._raiseCollectionChanged(e.NotifyCollectionChangedAction.Remove, n, i), this.currentItem !== o && this.onCurrentChanged()
          }
        } else t.assert(!1, "cannot remove items (canRemove == false).")
      }, n.prototype.removeAt = function(e) {
        e = t.asInt(e), this.remove(this._pgView[e])
      }, n.prototype._trackItemChanged = function(t) {
        if (this._trackChanges) {
          var n = this.sourceCollection;
          if (n && n.indexOf(t) > -1) {
            var r = this._chgEdited.indexOf(t),
              i = e.NotifyCollectionChangedAction.Change;
            if (r < 0 && this._chgAdded.indexOf(t) < 0) this._chgEdited.push(t);
            else if (r > -1) {
              o = new e.NotifyCollectionChangedEventArgs(i, t, r);
              this._chgEdited.onCollectionChanged(o)
            } else if ((r = this._chgAdded.indexOf(t)) > -1) {
              var o = new e.NotifyCollectionChangedEventArgs(i, t, r);
              this._chgAdded.onCollectionChanged(o)
            }
          }
        }
      }, n.prototype._extend = function(t, e) {
        for (var n in e) t[n] = e[n]
      }, n.prototype._sameContent = function(e, n) {
        t.assert(null != n && null != e, "Two objects expected");
        for (var r in n)
          if (!this._sameValue(e[r], n[r])) return !1;
        for (var r in e)
          if (!this._sameValue(e[r], n[r])) return !1;
        return !0
      }, n.prototype._sameValue = function(e, n) {
        return e === n || t.DateTime.equals(e, n)
      }, Object.defineProperty(n.prototype, "canChangePage", {
        get: function() {
          return this._canChangePage
        },
        set: function(e) {
          this._canChangePage = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "isPageChanging", {
        get: function() {
          return !1
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "itemCount", {
        get: function() {
          return this._pgView.length
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "pageIndex", {
        get: function() {
          return this._pgIdx
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "pageSize", {
        get: function() {
          return this._pgSz
        },
        set: function(e) {
          e != this._pgSz && (this._pgSz = t.asInt(e), this.refresh())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "totalItemCount", {
        get: function() {
          return this._view.length
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "pageCount", {
        get: function() {
          return this.pageSize ? Math.ceil(this.totalItemCount / this.pageSize) : 1
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.moveToFirstPage = function() {
        return this.moveToPage(0)
      }, n.prototype.moveToLastPage = function() {
        return this.moveToPage(this.pageCount - 1)
      }, n.prototype.moveToPreviousPage = function() {
        return this.moveToPage(this.pageIndex - 1)
      }, n.prototype.moveToNextPage = function() {
        return this.moveToPage(this.pageIndex + 1)
      }, n.prototype.moveToPage = function(n) {
        var r = t.clamp(n, 0, this.pageCount - 1);
        if (r != this._pgIdx) {
          this.canChangePage || t.assert(!1, "cannot change pages (canChangePage == false).");
          var i = new e.PageChangingEventArgs(r);
          this.onPageChanging(i) && (this._pgIdx = r, this._pgView = this._getPageView(), this._idx = 0, this.groupDescriptions && 0 != this.groupDescriptions.length ? this.refresh() : this.onCollectionChanged(), this.onPageChanged())
        }
        return this._pgIdx == n
      }, n.prototype.onPageChanged = function(e) {
        void 0 === e && (e = t.EventArgs.empty), this.pageChanged.raise(this, e)
      }, n.prototype.onPageChanging = function(t) {
        return this.pageChanging.raise(this, t), !t.cancel
      }, n.prototype._getFullGroup = function(t) {
        var e = this._getGroupByPath(this._fullGroups, t.level, t._path);
        return null != e && (t = e), t
      }, n.prototype._getGroupByPath = function(t, e, n) {
        for (var r = 0; r < t.length; r++) {
          var i = t[r];
          if (i.level == e && i._path == n) return i;
          if (i.level < e && 0 == n.indexOf(i._path) && null != (i = this._getGroupByPath(i.groups, e, n))) return i
        }
        return null
      }, n.prototype._getPageView = function() {
        if (this.pageSize <= 0 || this._pgIdx < 0) return this._view;
        var t = this._pgSz * this._pgIdx,
          e = Math.min(t + this._pgSz, this._view.length);
        return this._view.slice(t, e)
      }, n.prototype._createGroups = function(e) {
        if (!this._grpDesc || !this._grpDesc.length) return null;
        for (var n = [], r = {}, i = null, o = 0; o < e.length; o++)
          for (var s = e[o], a = n, u = this._grpDesc.length, c = "", l = 0; l < u; l++) {
            var h = this._grpDesc[l],
              p = h.groupNameFromItem(s, l),
              f = l == u - 1;
            !(i = r[c]) && t.isPrimitive(p) && (i = {}, r[c] = i);
            var d = this._getGroup(h, a, i, p, l, f);
            c += "/" + p, d._path = c, f && d.items.push(s), a = d.groups
          }
        return n
      }, n.prototype._getGroupsDigest = function(t) {
        for (var e = "", n = 0; null != t && n < t.length; n++) {
          var r = t[n];
          e += "{" + r.name + ":" + (r.items ? r.items.length : "*"), r.groups.length > 0 && (e += ",", e += this._getGroupsDigest(r.groups)), e += "}"
        }
        return e
      }, n.prototype._mergeGroupItems = function(t) {
        for (var e = [], n = 0; n < t.length; n++) {
          var r = t[n];
          if (!r._isBottomLevel)
            for (var i = this._mergeGroupItems(r.groups), o = 0, s = i.length; o < s; o++) r._items.push(i[o]);
          for (var o = 0, s = r._items.length; o < s; o++) e.push(r._items[o])
        }
        return e
      }, n.prototype._getGroup = function(e, n, i, o, s, a) {
        var u;
        if (i && t.isPrimitive(o)) {
          if (u = i[o]) return u
        } else
          for (var c = 0; c < n.length; c++)
            if (e.namesMatch(n[c].name, o)) return n[c]; var l = new r(e, o, s, a);
        return n.push(l), i && (i[o] = l), l
      }, n._collator = "undefined" != typeof window && window.Intl ? new Intl.Collator : null, n
    }();
    e.CollectionView = n;
    var r = function() {
      function e(t, e, n, r) {
        this._gd = t, this._name = e, this._level = n, this._isBottomLevel = r, this._groups = [], this._items = []
      }
      return Object.defineProperty(e.prototype, "name", {
        get: function() {
          return this._name
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "level", {
        get: function() {
          return this._level
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "isBottomLevel", {
        get: function() {
          return this._isBottomLevel
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "items", {
        get: function() {
          return this._items
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "groups", {
        get: function() {
          return this._groups
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "groupDescription", {
        get: function() {
          return this._gd
        },
        enumerable: !0,
        configurable: !0
      }), e.prototype.getAggregate = function(e, r, i) {
        var o = t.tryCast(i, n),
          s = o ? o._getFullGroup(this) : this;
        return t.getAggregate(e, s.items, r)
      }, e
    }();
    e.CollectionViewGroup = r
  }(t.collections || (t.collections = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
      };
    return function(e, n) {
      function r() {
        this.constructor = e
      }
      t(e, n), e.prototype = null === n ? Object.create(n) : (r.prototype = n.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  "use strict";
  var e = function() {
    function e(e) {
      this._showAutoTipBnd = this._showAutoTip.bind(this), this._hideAutoTipBnd = this._hideAutoTip.bind(this), this._html = !0, this._gap = 6, this._showAtMouse = !1, this._showDelay = 500, this._hideDelay = 0, this._tips = [], this.popup = new t.Event, t.copy(this, e)
    }
    return e.prototype.setTooltip = function(e, n) {
      e = t.getElement(e), n = this._getContent(n);
      var r = this._indexOf(e);
      r > -1 && (this._detach(e), this._tips.splice(r, 1)), n && (this._attach(e), this._tips.push({
        element: e,
        content: n
      }))
    }, e.prototype.getTooltip = function(e) {
      e = t.getElement(e);
      for (var n = this._tips, r = 0; r < n.length; r++)
        if (n[r].element == e) return n[r].content;
      return null
    }, e.prototype.show = function(r, i, o) {
      r = t.getElement(r), i = this._getContent(i), o || (o = t.Rect.fromBoundingRect(r.getBoundingClientRect()));
      var s = e._eTip;
      s || (s = e._eTip = document.createElement("div"), t.addClass(s, "wj-tooltip"), s.style.visibility = "none"), this._setContent(i);
      var a = new n(i);
      this.onPopup(a), a.content && !a.cancel && (document.body.appendChild(s), this._setContent(a.content), s.style.minWidth = "", o = new t.Rect(o.left - (s.offsetWidth - o.width) / 2, o.top - this.gap, s.offsetWidth, o.height + 2 * this.gap), t.showPopup(s, o, !0), document.addEventListener("mousedown", this._hideAutoTipBnd))
    }, e.prototype.hide = function() {
      var n = e._eTip;
      n && (t.removeChild(n), n.innerHTML = ""), document.removeEventListener("mousedown", this._hideAutoTipBnd)
    }, e.prototype.dispose = function() {
      var t = this;
      this._tips.forEach(function(e) {
        t._detach(e.element)
      }), this._tips.splice(0, this._tips.length)
    }, Object.defineProperty(e.prototype, "isVisible", {
      get: function() {
        var t = e._eTip;
        return null != t && null != t.parentElement && t.offsetWidth > 0
      },
      enumerable: !0,
      configurable: !0
    }), Object.defineProperty(e.prototype, "isContentHtml", {
      get: function() {
        return this._html
      },
      set: function(e) {
        this._html = t.asBoolean(e)
      },
      enumerable: !0,
      configurable: !0
    }), Object.defineProperty(e.prototype, "gap", {
      get: function() {
        return this._gap
      },
      set: function(e) {
        this._gap = t.asNumber(e)
      },
      enumerable: !0,
      configurable: !0
    }), Object.defineProperty(e.prototype, "showAtMouse", {
      get: function() {
        return this._showAtMouse
      },
      set: function(e) {
        this._showAtMouse = t.asBoolean(e)
      },
      enumerable: !0,
      configurable: !0
    }), Object.defineProperty(e.prototype, "showDelay", {
      get: function() {
        return this._showDelay
      },
      set: function(e) {
        this._showDelay = t.asInt(e)
      },
      enumerable: !0,
      configurable: !0
    }), Object.defineProperty(e.prototype, "hideDelay", {
      get: function() {
        return this._hideDelay
      },
      set: function(e) {
        this._hideDelay = t.asInt(e)
      },
      enumerable: !0,
      configurable: !0
    }), e.prototype.onPopup = function(t) {
      return this.popup && this.popup.raise(this, t), !t.cancel
    }, e.prototype._indexOf = function(t) {
      for (var e = 0; e < this._tips.length; e++)
        if (this._tips[e].element == t) return e;
      return -1
    }, e.prototype._attach = function(t) {
      t.addEventListener("mouseenter", this._showAutoTipBnd), t.addEventListener("mouseleave", this._hideAutoTipBnd), t.addEventListener("click", this._showAutoTipBnd)
    }, e.prototype._detach = function(t) {
      t.removeEventListener("mouseenter", this._showAutoTipBnd), t.removeEventListener("mouseleave", this._hideAutoTipBnd), t.removeEventListener("click", this._showAutoTipBnd)
    }, e.prototype._showAutoTip = function(e) {
      var n = this;
      if (!e.defaultPrevented)
        if ("click" != e.type || t.Control._touching) {
          var r = "mouseenter" == e.type ? this._showDelay : 0,
            i = this._hideDelay;
          this._clearTimeouts(), this._toShow = setTimeout(function() {
            var r = n._indexOf(e.target);
            if (r > -1) {
              var o = n._tips[r],
                s = n._showAtMouse ? new t.Rect(e.clientX, e.clientY, 0, 0) : null;
              n.show(o.element, o.content, s), i > 0 && (n._toHide = setTimeout(function() {
                n.hide()
              }, i))
            }
          }, r)
        } else this._hideAutoTip(e)
    }, e.prototype._hideAutoTip = function(t) {
      this._clearTimeouts(), this.hide()
    }, e.prototype._clearTimeouts = function() {
      this._toShow && (clearTimeout(this._toShow), this._toShow = null), this._toHide && (clearTimeout(this._toHide), this._toHide = null)
    }, e.prototype._getContent = function(e) {
      if ((e = t.asString(e)) && "#" == e[0]) {
        var n = t.getElement(e);
        n && (e = n.innerHTML)
      }
      return e
    }, e.prototype._setContent = function(t) {
      var n = e._eTip;
      n && (this.isContentHtml ? n.innerHTML = t : n.textContent = t)
    }, e
  }();
  t.Tooltip = e;
  var n = function(e) {
    function n(n) {
      var r = e.call(this) || this;
      return r._content = t.asString(n), r
    }
    return __extends(n, e), Object.defineProperty(n.prototype, "content", {
      get: function() {
        return this._content
      },
      set: function(e) {
        this._content = t.asString(e)
      },
      enumerable: !0,
      configurable: !0
    }), n
  }(t.CancelEventArgs);
  t.TooltipEventArgs = n
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  "use strict";
  var e = function() {
    function e(t) {
      this._r = 0, this._g = 0, this._b = 0, this._a = 1, t && this._parse(t)
    }
    return Object.defineProperty(e.prototype, "r", {
      get: function() {
        return this._r
      },
      set: function(e) {
        this._r = t.clamp(t.asNumber(e), 0, 255)
      },
      enumerable: !0,
      configurable: !0
    }), Object.defineProperty(e.prototype, "g", {
      get: function() {
        return this._g
      },
      set: function(e) {
        this._g = t.clamp(t.asNumber(e), 0, 255)
      },
      enumerable: !0,
      configurable: !0
    }), Object.defineProperty(e.prototype, "b", {
      get: function() {
        return this._b
      },
      set: function(e) {
        this._b = t.clamp(t.asNumber(e), 0, 255)
      },
      enumerable: !0,
      configurable: !0
    }), Object.defineProperty(e.prototype, "a", {
      get: function() {
        return this._a
      },
      set: function(e) {
        this._a = t.clamp(t.asNumber(e), 0, 1)
      },
      enumerable: !0,
      configurable: !0
    }), e.prototype.equals = function(t) {
      return t instanceof e && this.r == t.r && this.g == t.g && this.b == t.b && this.a == t.a
    }, e.prototype.toString = function() {
      var t = Math.round(100 * this.a);
      return t > 99 ? "#" + ((1 << 24) + (this.r << 16) + (this.g << 8) + this.b).toString(16).slice(1) : "rgba(" + this.r + "," + this.g + "," + this.b + "," + t / 100 + ")"
    }, e.fromRgba = function(n, r, i, o) {
      void 0 === o && (o = 1);
      var s = new e(null);
      return s.r = Math.round(t.clamp(t.asNumber(n), 0, 255)), s.g = Math.round(t.clamp(t.asNumber(r), 0, 255)), s.b = Math.round(t.clamp(t.asNumber(i), 0, 255)), s.a = t.clamp(t.asNumber(o), 0, 1), s
    }, e.fromHsb = function(n, r, i, o) {
      void 0 === o && (o = 1);
      var s = e._hsbToRgb(t.clamp(t.asNumber(n), 0, 1), t.clamp(t.asNumber(r), 0, 1), t.clamp(t.asNumber(i), 0, 1));
      return e.fromRgba(s[0], s[1], s[2], o)
    }, e.fromHsl = function(n, r, i, o) {
      void 0 === o && (o = 1);
      var s = e._hslToRgb(t.clamp(t.asNumber(n), 0, 1), t.clamp(t.asNumber(r), 0, 1), t.clamp(t.asNumber(i), 0, 1));
      return e.fromRgba(s[0], s[1], s[2], o)
    }, e.fromString = function(n) {
      var r = new e(null);
      return r._parse(t.asString(n)) ? r : null
    }, e.prototype.getHsb = function() {
      return e._rgbToHsb(this.r, this.g, this.b)
    }, e.prototype.getHsl = function() {
      return e._rgbToHsl(this.r, this.g, this.b)
    }, e.interpolate = function(n, r, i) {
      i = t.clamp(t.asNumber(i), 0, 1);
      var o = e._rgbToHsl(n.r, n.g, n.b),
        s = e._rgbToHsl(r.r, r.g, r.b),
        a = 1 - i,
        u = n.a * a + r.a * i,
        c = [o[0] * a + s[0] * i, o[1] * a + s[1] * i, o[2] * a + s[2] * i],
        l = e._hslToRgb(c[0], c[1], c[2]);
      return e.fromRgba(l[0], l[1], l[2], u)
    }, e.toOpaque = function(n, r) {
      if (1 == (n = t.isString(n) ? e.fromString(n) : t.asType(n, e)).a) return n;
      r = null == r ? e.fromRgba(255, 255, 255, 1) : t.isString(r) ? e.fromString(r) : t.asType(r, e);
      var i = n.a,
        o = 1 - i;
      return e.fromRgba(n.r * i + r.r * o, n.g * i + r.g * o, n.b * i + r.b * o)
    }, e.prototype._parse = function(n) {
      if ("transparent" == (n = n.toLowerCase())) return this._r = this._g = this._b = this._a = 0, !0;
      if (n && 0 != n.indexOf("#") && 0 != n.indexOf("rgb") && 0 != n.indexOf("hsl")) {
        var r = document.createElement("div");
        r.style.color = n;
        var i = r.style.color;
        i == n && ((i = window.getComputedStyle(r).color) || (document.body.appendChild(r), i = window.getComputedStyle(r).color, t.removeChild(r))), n = i.toLowerCase()
      }
      if (0 == n.indexOf("#")) return 4 == n.length ? (this.r = parseInt(n[1] + n[1], 16), this.g = parseInt(n[2] + n[2], 16), this.b = parseInt(n[3] + n[3], 16), this.a = 1, !0) : 7 == n.length && (this.r = parseInt(n.substr(1, 2), 16), this.g = parseInt(n.substr(3, 2), 16), this.b = parseInt(n.substr(5, 2), 16), this.a = 1, !0);
      if (0 == n.indexOf("rgb")) {
        var o = n.indexOf("("),
          s = n.indexOf(")");
        if (o > -1 && s > -1 && (a = n.substr(o + 1, s - (o + 1)).split(",")).length > 2) return this.r = parseInt(a[0]), this.g = parseInt(a[1]), this.b = parseInt(a[2]), this.a = a.length > 3 ? parseFloat(a[3]) : 1, !0
      }
      if (0 == n.indexOf("hsl")) {
        var o = n.indexOf("("),
          s = n.indexOf(")");
        if (o > -1 && s > -1) {
          var a = n.substr(o + 1, s - (o + 1)).split(",");
          if (a.length > 2) {
            var u = parseInt(a[0]) / 360,
              c = parseInt(a[1]),
              l = parseInt(a[2]);
            a[1].indexOf("%") > -1 && (c /= 100), a[2].indexOf("%") > -1 && (l /= 100);
            var h = e._hslToRgb(u, c, l);
            return this.r = h[0], this.g = h[1], this.b = h[2], this.a = a.length > 3 ? parseFloat(a[3]) : 1, !0
          }
        }
      }
      return !1
    }, e._hslToRgb = function(n, r, i) {
      t.assert(n >= 0 && n <= 1 && r >= 0 && r <= 1 && i >= 0 && i <= 1, "bad HSL values");
      var o, s, a;
      if (0 == r) o = s = a = i;
      else {
        var u = i < .5 ? i * (1 + r) : i + r - i * r,
          c = 2 * i - u;
        o = e._hue2rgb(c, u, n + 1 / 3), s = e._hue2rgb(c, u, n), a = e._hue2rgb(c, u, n - 1 / 3)
      }
      return [Math.round(255 * o), Math.round(255 * s), Math.round(255 * a)]
    }, e._hue2rgb = function(t, e, n) {
      return n < 0 && (n += 1), n > 1 && (n -= 1), n < 1 / 6 ? t + 6 * (e - t) * n : n < .5 ? e : n < 2 / 3 ? t + (e - t) * (2 / 3 - n) * 6 : t
    }, e._rgbToHsl = function(e, n, r) {
      t.assert(e >= 0 && e <= 255 && n >= 0 && n <= 255 && r >= 0 && r <= 255, "bad RGB values"), e /= 255, n /= 255, r /= 255;
      var i, o, s = Math.max(e, n, r),
        a = Math.min(e, n, r),
        u = (s + a) / 2;
      if (s == a) i = o = 0;
      else {
        var c = s - a;
        switch (o = u > .5 ? c / (2 - s - a) : c / (s + a), s) {
          case e:
            i = (n - r) / c + (n < r ? 6 : 0);
            break;
          case n:
            i = (r - e) / c + 2;
            break;
          case r:
            i = (e - n) / c + 4
        }
        i /= 6
      }
      return [i, o, u]
    }, e._rgbToHsb = function(n, r, i) {
      t.assert(n >= 0 && n <= 255 && r >= 0 && r <= 255 && i >= 0 && i <= 255, "bad RGB values");
      var o = e._rgbToHsl(n, r, i);
      return e._hslToHsb(o[0], o[1], o[2])
    }, e._hsbToRgb = function(t, n, r) {
      var i = e._hsbToHsl(t, n, r);
      return e._hslToRgb(i[0], i[1], i[2])
    }, e._hsbToHsl = function(e, n, r) {
      t.assert(e >= 0 && e <= 1 && n >= 0 && n <= 1 && r >= 0 && r <= 1, "bad HSB values");
      var i = t.clamp(r * (2 - n) / 2, 0, 1),
        o = 1 - Math.abs(2 * i - 1),
        s = t.clamp(o > 0 ? r * n / o : n, 0, 1);
      return t.assert(!isNaN(i) && !isNaN(s), "bad conversion to HSL"), [e, s, i]
    }, e._hslToHsb = function(e, n, r) {
      t.assert(e >= 0 && e <= 1 && n >= 0 && n <= 1 && r >= 0 && r <= 1, "bad HSL values");
      var i = t.clamp(1 == r ? 1 : (2 * r + n * (1 - Math.abs(2 * r - 1))) / 2, 0, 1),
        o = t.clamp(i > 0 ? 2 * (i - r) / i : n, 0, 1);
      return t.assert(!isNaN(i) && !isNaN(o), "bad conversion to HSB"), [e, o, i]
    }, e
  }();
  t.Color = e
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  "use strict";
  var e = function() {
    function e() {}
    return e.copy = function(n) {
      e._copyPaste(t.asString(n), null)
    }, e.paste = function(n) {
      e._copyPaste(null, t.asFunction(n))
    }, e._copyPaste = function(e, n) {
      for (var r = t.getActiveElement(), i = t.closest(r, ".wj-control"); i && t.Control.getControl(i);) i = i.parentElement;
      if (i = i || document.body) {
        var o = t.createElement('<textarea class="wj-clipboard" style="position:fixed;opacity:0"/>', i),
          s = !1;
        o.onkeydown = function(t) {
          t.preventDefault()
        }, o.onchange = function(t) {
          t.stopImmediatePropagation()
        }, t.isString(e) ? (o.value = e, o.select(), document.execCommand("copy") && (s = !0)) : (o.select(), t.isFunction(n) && document.execCommand("paste") && (n(o.value), s = !0)), setTimeout(function() {
          r.focus(), t.removeChild(o), !s && t.isFunction(n) && n(o.value)
        }, t.Control._CLIPBOARD_DELAY)
      }
    }, e
  }();
  t.Clipboard = e
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  "use strict";

  function e(e, n) {
    e.style.display = "none", n && e.parentElement && setTimeout(function() {
      "none" == e.style.display && (t.removeChild(e), t.isFunction(n) && n())
    }, t.Control._FOCUS_INTERVAL + 1);
    var r = e[t.Control._SCRL_KEY];
    r instanceof t.Control && r.dispose(), delete e[t.Control._SCRL_KEY], delete e[t.Control._OWNR_KEY]
  }

  function n(e) {
    var n = window.jQuery,
      r = t.isFunction(n) ? n(e) : null;
    return r && t.isFunction(r.dialog) && t.hasClass(e, "ui-dialog")
  }
  t.showPopup = function(r, i, o, s, a) {
    void 0 === a && (a = !0);
    var u = document.body;
    if (i instanceof HTMLElement) {
      if (!t.contains(document.body, i)) return;
      for (var c = i; c; c = c.parentElement)
        if ("DIALOG" == c.tagName || n(c) || "fixed" == getComputedStyle(c).position) {
          u = c;
          break
        }
    }
    t.setCss(r, {
      display: "",
      opacity: "0",
      position: "fixed",
      left: 0,
      top: 0,
      transform: ""
    }), r.offsetHeight && r.offsetWidth && r.parentElement == u || u.lastChild != r && u.appendChild(r);
    var l = document.body,
      h = new t.Point(l.scrollLeft || pageXOffset, l.scrollTop || pageYOffset),
      p = document.documentElement,
      f = p.clientWidth / window.innerWidth;
    if (u != l || f > 1.005) {
      var d = u == l ? p : r.offsetParent || u;
      if (d == l && (d = u), d) {
        var g = d.getBoundingClientRect();
        h = new t.Point(d.scrollLeft - g.left, d.scrollTop - g.top)
      }
    }
    var m = getComputedStyle(u);
    if (h.x -= parseFloat(m.borderLeftWidth), h.y -= parseFloat(m.borderTopWidth), i instanceof HTMLElement && a) {
      var _ = a instanceof HTMLElement ? a : i,
        v = getComputedStyle(_);
      1 == new t.Color(v.backgroundColor).a && t.setCss(r, {
        color: v.color,
        backgroundColor: v.backgroundColor,
        fontFamily: v.fontFamily,
        fontSize: v.fontSize,
        fontWeight: v.fontWeight,
        fontStyle: v.fontStyle
      })
    }
    t.Control.refreshAll(r);
    var y = getComputedStyle(r),
      b = parseFloat(y.marginTop) + parseFloat(y.marginBottom),
      C = parseFloat(y.marginLeft) + parseFloat(y.marginRight),
      w = new t.Size(r.offsetWidth + C, r.offsetHeight + b),
      E = t.hasClass(r, "wj-rtl") || "rtl" == r.getAttribute("dir"),
      A = new t.Point,
      x = null,
      S = p.clientWidth,
      O = p.clientHeight;
    if (i && null != i.clientX && null != i.clientY && null != i.pageX && null != i.pageY) i.clientX <= 0 && i.clientY <= 0 && i.target ? x = i.target.getBoundingClientRect() : (A.x = i.pageX - pageXOffset, A.y = i.pageY - pageYOffset, E && (A.x -= w.width));
    else if (i instanceof t.Point) A = i, E && (A.x -= w.width);
    else if (i instanceof HTMLElement) x = i.getBoundingClientRect();
    else if (i && null != i.top && null != i.left) x = i;
    else {
      if (null != i) throw "Invalid ref parameter.";
      A.x = (S - w.width) / 2, A.y = Math.round((O - w.height) / 2 * .7)
    }
    var T = parseFloat(y.minWidth);
    if (x) {
      T = Math.max(T, x.width), !t.isIE() || i && "TR" == i.tagName || (T -= r.offsetWidth - (r.clientWidth + parseInt(y.borderLeftWidth) + parseInt(y.borderRightWidth))), A.x = x.left;
      var I = x.top,
        P = O - x.bottom;
      A.y = o ? I > w.height || I > P ? x.top - w.height : x.bottom : P > w.height || P > I ? x.bottom : x.top - w.height
    }
    A.y + w.height > O - 20 && (A.y = Math.max(0, O - 20 - w.height)), A.x + w.width > S - 20 && (A.x = Math.max(0, S - 20 - w.width)), A.x = Math.max(0, A.x) + h.x, A.y = Math.max(0, A.y) + h.y;
    var F = {
      position: "absolute",
      opacity: s ? "0" : "",
      minWidth: T,
      zIndex: t.Control._POPUP_ZINDEX
    };
    0 == h.x ? (F.left = A.x, F.top = A.y) : F.transform = t.format("translate({x}px, {y}px)", A), i instanceof HTMLElement && (r[t.Control._OWNR_KEY] = i);
    var M = null;
    s && (M = t.animate(function(t) {
      r.style.opacity = t < 1 ? t.toString() : ""
    })), t.setCss(r, F);
    var D = i instanceof MouseEvent ? i.target : i;
    if (D instanceof HTMLElement && D.parentElement != document.body) {
      var j = Date.now(),
        N = D.getBoundingClientRect(),
        L = new t.Control(document.createElement("div"));
      r[t.Control._SCRL_KEY] = L, L.addEventListener(document, "scroll", function(n) {
        if (n.target != r.parentElement && Date.now() - j > 100 && t.contains(document, D) && !t.contains(r, n.target) && (n.target != document || null != i && "fixed" == r.style.position)) {
          var o = D.getBoundingClientRect(),
            s = Math.abs(o.left - N.left),
            a = Math.abs(o.top - N.top);
          (s > 1 || a > 1) && (e(r, !0), L.dispose())
        }
      }, !0)
    }
    return M
  }, t.hidePopup = function(n, r, i) {
    void 0 === r && (r = !0), void 0 === i && (i = !1);
    var o = null;
    return i ? o = t.animate(function(t) {
      n.style.opacity = (1 - t).toString(), 1 == t && (e(n, r), n.style.opacity = "")
    }) : e(n, r), o
  }
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  "use strict";
  var e = function() {
    function e(e) {
      this._copyCss = !0, null != e && t.copy(this, e)
    }
    return Object.defineProperty(e.prototype, "title", {
      get: function() {
        return this._title
      },
      set: function(e) {
        this._title = t.asString(e)
      },
      enumerable: !0,
      configurable: !0
    }), Object.defineProperty(e.prototype, "copyCss", {
      get: function() {
        return this._copyCss
      },
      set: function(e) {
        this._copyCss = t.asBoolean(e)
      },
      enumerable: !0,
      configurable: !0
    }), e.prototype.addCSS = function(t) {
      this._css || (this._css = []), this._css.push(t)
    }, e.prototype.append = function(e) {
      var n = this._getDocument(),
        r = n.body,
        i = !1;
      if (r)
        if (t.isString(e)) r.appendChild(t.createElement(e));
        else if (e instanceof Node) {
          o = this._cloneNode(e);
          r.appendChild(o)
        } else i = !0;
      else if (t.isString(e)) n.write(e);
      else if (e instanceof HTMLElement) {
        var o = this._cloneNode(e);
        n.write(o.outerHTML)
      } else i = !0;
      i && t.assert(!1, "child parameter should be an HTML node or an HTML string.")
    }, e.prototype.print = function() {
      var e = this;
      this._iframe && (this._close(), setTimeout(function() {
        var n = e._iframe.contentWindow,
          r = "onafterprint" in n && !t.isFirefox();
        r && (n.onafterprint = function() {
          t.removeChild(e._iframe), e._iframe = null
        }), document.queryCommandSupported("print") ? n.document.execCommand("print", !1, null) : (n.focus(), n.print()), r || (t.removeChild(e._iframe), e._iframe = null)
      }, 100))
    }, e.prototype._cloneNode = function(t) {
      var e = t.cloneNode(!0);
      return t instanceof HTMLElement && e instanceof HTMLElement && ["select", "textarea"].forEach(function(n) {
        for (var r = t.querySelectorAll(n), i = e.querySelectorAll(n), o = 0; o < r.length; o++) i[o].value = r[o].value
      }), e
    }, e.prototype._getDocument = function() {
      return this._iframe || (this._iframe = document.createElement("iframe"), t.addClass(this._iframe, "wj-printdocument"), t.setCss(this._iframe, {
        position: "fixed",
        left: 1e4,
        top: 1e4
      }), document.body.appendChild(this._iframe), this._iframe.contentDocument.write('<body style="position:static"/>')), this._iframe.contentDocument
    }, e.prototype._close = function() {
      var e = this._getDocument();
      if (e.close(), e.title = null != this.title ? this.title : document.title, e.title && e.title.trim() || (e.title = " "), this._copyCss) {
        for (var n = document.head.querySelectorAll("link"), r = 0; r < n.length; r++) {
          var i = n[r];
          if (i.href.match(/\.css$/i) && i.rel.match(/stylesheet/i)) {
            a = t.httpRequest(i.href, {
              async: !1
            });
            this._addStyle(a.responseText)
          }
        }
        for (var o = document.head.querySelectorAll("STYLE"), r = 0; r < o.length; r++) this._addStyle(o[r].textContent)
      }
      if (this._css)
        for (r = 0; r < this._css.length; r++) {
          var s = e.createElement("style"),
            a = t.httpRequest(this._css[r], {
              async: !1
            });
          s.textContent = a.responseText, e.head.appendChild(s)
        }
    }, e.prototype._addStyle = function(t) {
      var e = this._getDocument(),
        n = e.createElement("style");
      n.textContent = t, e.head.appendChild(n)
    }, e
  }();
  t.PrintDocument = e
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  "use strict";

  function e(t) {
    return t._getProductInfo().split(",")[0]
  }

  function n(t) {
    return t._getProductInfo().split(",")[1]
  }

  function r(e, n, r) {
    var c = t.culture.Licensing || P,
      h = null;
    r ? o(e, r) ? s(e, r) ? a(e, r) ? i(e, r) ? u(e, r) && (h = c.evl) : h = c.prd : h = c.dmn : h = c.ver : h = c.exp : h = n ? c.lic : c.mss, h && l(e, h += "<br/><br/>" + c.ctc, r)
  }

  function i(t, n) {
    var r = e(t);
    if (r && r.length >= 4)
      for (var i = 0; i < n.Prd.length; i++)
        if (n.Prd[i].C.indexOf(r) > -1) return !0;
    return !1
  }

  function o(t, e) {
    if (e.Exp) {
      var n = e.Exp,
        r = new Date(parseInt(n.substr(0, 4)) || 0, parseInt(n.substr(4, 2)) - 1 || 0, parseInt(n.substr(6, 2)) || 0);
      if (Math.ceil((r.valueOf() - Date.now()) / 864e5) <= 0) return !1
    }
    return !0
  }

  function s(e, n) {
    var r = n.Anl ? n.Anl.ver : null,
      i = t.getVersion();
    return !r || !i || parseInt(r.replace("v", "")) + 10 >= parseInt(i.split(".")[1])
  }

  function a(t, e) {
    if (c() || !e.Dms) return !0;
    for (var n = location.hostname, r = e.Dms.split(","), i = !1, o = 0; o < r.length; o++) {
      var s = r[o];
      if (i = s && 0 == s.lastIndexOf("*.", 0), (s = i ? s.slice(2) : s) == n) return !0;
      if (i) {
        var a = n.indexOf("." + s);
        if (a > 0 && a == n.length - s.length - 1) return !0
      }
    }
    return !1
  }

  function u(t, e) {
    return !!e && e.Evl
  }

  function c() {
    var t = atob("Xih8bG9jYWxob3N0fGZpZGRsZS5qc2hlbGwubmV0fHJ1bi5wbG5rci5jb3xzLmNvZGVwZW4uaW98bnVsbC5qc2Jpbi5jb218MTI3LjAuMC4xKSQ="),
      e = new RegExp(t, "i");
    return location.hostname.match(e)
  }

  function l(e, n, r) {
    if (n = p(e, n, r), !F[" "] && !c() && !u(e, r)) {
      F[" "] = !0;
      var i = t.culture.Licensing || P,
        o = '<div style="position:fixed;background:rgba(0,0,0,0.3);left:0;top:0;width:100%;height:100%;font-family: sans-serif;z-index:10000;"><div style="position:relative;background:white;width:450px;padding:36pt;left:50%;top:50%;transform:translate(-50%,-50%)"><div><p style="font-size:150%;font-weight:bold">' + i.hdr + "</p><p>" + n + '</p></div><div style="text-align:right;margin-top:24pt"><button style="border:none;background:rgba(158,158,158,.2);padding:8px 16px">' + i.cls + "</button></div></div></div>",
        s = t.createElement(o, document.body);
      s.addEventListener("click", function(e) {
        e.target instanceof HTMLButtonElement && t.removeChild(s)
      })
    }
    if (!t.Control._wme) {
      var o = '<div><a href="' + h() + '" target="_blank" rel="noopener">' + p(e) + "</a></div>",
        a = t.createElement(o),
        l = {
          position: "fixed",
          display: "block",
          visibility: "visible",
          background: "rgba(240,240,240,.9)",
          padding: "8px 12px",
          margin: "6px",
          fontFamily: "sans-serif",
          fontSize: "14pt",
          fontWeight: "bold",
          zIndex: 1e4,
          opacity: .8,
          height: "auto",
          width: "auto",
          transform: "none"
        };
      switch (Math.round(100 * Math.random()) % 3) {
        case 0:
          l.right = l.bottom = 0;
          break;
        case 1:
          l.left = l.bottom = 0;
          break;
        case 2:
          l.right = l.top = 0
      }
      t.setCss(a, l), document.body.appendChild(a), t.Control._wme = a, (new t.Tooltip).setTooltip(a, n)
    }
  }

  function h() {
    return "ja" == t.culture.Globalize.name ? "https://www.grapecity.co.jp/developer/support/wijmo-license" : "https://www.grapecity.com/en/licensing/wijmo"
  }

  function p(e, r, i) {
    (!r || r.trim().length < 15) && (r = (t.culture.Licensing || P).evl), (!r || r.trim().length < 15) && (r = atob("V2lqbW8gRXZhbHVhdGlvbiBWZXJzaW9uICh7dmVyc2lvbn0p"));
    var o = null;
    if (i && i.Exp) {
      var s = i.Exp;
      o = new Date(parseInt(s.substr(0, 4)) || 0, parseInt(s.substr(4, 2)) - 1 || 0, parseInt(s.substr(6, 2)) || 0)
    }
    return r = t.format(r, {
      version: t.getVersion(),
      domain: location.hostname,
      control: n(e),
      licVer: i && i.Anl ? "5." + i.Anl.ver.replace("v", "") + ".*" : null,
      licDomain: i ? i.Dms : null,
      expDate: o
    })
  }

  function f(t, e, n, r) {
    var i = d(t, e.algorithmCode);
    if (!i) return r();
    var o = i.licData,
      s = i.prefix + "#" + e.algorithmCode;
    if (O(s + JSON.stringify(o.D)) != o.H) return r();
    var a = window.msCrypto || window.crypto,
      u = a ? a.subtle : null;
    if (!u || !location.protocol.match(/^https/i)) return n(o.D);
    var c = {
        name: "RSASSA-PKCS1-v1_5",
        hash: {
          name: "SHA-256"
        }
      },
      l = {
        alg: "RS256",
        e: "AQAB",
        kty: "RSA",
        n: x(e.publicKey)
      };
    l = window.msCrypto ? S(JSON.stringify(l)) : l;
    try {
      T(u.importKey("jwk", l, c, !1, ["verify"])).then(function(t) {
        var e = s + w(E(A(JSON.stringify(o.D))));
        T(u.verify(c, t, E(o.S), S(e))).then(function(t) {
          return t ? n(o.D) : r()
        })
      }, function() {
        return n(o.D)
      })
    } catch (t) {
      return n(o.D)
    }
  }

  function d(t, e) {
    try {
      e = "#" + e;
      var n = t.indexOf(e);
      if (n > -1) return {
        prefix: t.substring(0, n),
        licData: JSON.parse(g(t.substr(n + e.length)))
      }
    } catch (t) {}
    return null
  }

  function g(t) {
    if (!t) return "";
    var e = m(t);
    e = b(e);
    var n = Math.ceil(e.length / 2);
    return e = e.substr(n) + e.substr(0, n), e = e.replace("#", "="), e = e.replace("&", "=="), C(e)
  }

  function m(t) {
    for (var e = t.split(""), n = e.length - 5; n >= 0; n--) _(e, n + 1, n + 3, y), _(e, n, n + 2, y);
    return e.join("")
  }

  function _(t, e, n, r) {
    t.length > 1 && t.splice(n, 1, r(t.splice(e, 1, r(t[n]))[0]))
  }

  function v(t, e) {
    var n = t.charCodeAt(0);
    return n >= 65 && n <= 90 ? t.toLowerCase() : n >= 97 && n <= 122 ? t.toUpperCase() : n >= 48 && n <= 57 ? String.fromCharCode(48 + (n - 48 + 10 + e) % 10) : t
  }

  function y(t) {
    return v(t, -1)
  }

  function b(t) {
    return t.split("").reverse().join("")
  }

  function C(t) {
    return decodeURIComponent(Array.prototype.map.call(atob(t), function(t) {
      return "%" + ("00" + t.charCodeAt(0).toString(16)).slice(-2)
    }).join(""))
  }

  function w(t) {
    return String.fromCharCode.apply(null, new M(t))
  }

  function E(t) {
    for (var e = atob(t), n = e.length, r = new M(n), i = 0; i < n; i++) r[i] = e.charCodeAt(i);
    return r.buffer
  }

  function A(t) {
    return btoa(encodeURIComponent(t).replace(/%([0-9A-F]{2})/g, function(t, e) {
      return String.fromCharCode(parseInt(e, 16))
    }))
  }

  function x(t) {
    return t.replace(/\+/g, "-").replace(/\//g, "_").replace(/\=+$/, "")
  }

  function S(t) {
    for (var e = new ArrayBuffer(t.length), n = new M(e), r = 0, i = t.length; r < i; r++) n[r] = t.charCodeAt(r);
    return e
  }

  function O(t) {
    for (var e = 0, n = 5381, r = 0, i = t.length - 1; i >= 0; i--) {
      var o = t.charCodeAt(i);
      e = o + (e << 6) + (e << 16) - e, n = o + ((n << 5) + n), r = o + ((r << 5) - r), r &= r
    }
    var s = e ^ n ^ r;
    return s < 0 && (s = ~s), s.toString(16).toUpperCase()
  }

  function T(t) {
    return "oncomplete" in t && (t.then = function(e, n) {
      t.resolve = e, t.reject = n
    }, t.oncomplete = function(e) {
      t.resolve(t.result)
    }, t.onerror = function(e) {
      t.reject()
    }), t
  }
  var I = "The Wijmo license in this application ",
    P = {
      evl: "Wijmo Evaluation Version ({version})",
      prd: I + "is not valid for the <b>{control}</b> control.",
      ver: I + "is not valid for the version in use. The license version is <b>{licVer}</b>; the product version is <b>{version}</b>.",
      dmn: I + "is not valid for the current domain. The license domain is <b>{licDomain}</b>; the current domain is <b>{domain}</b>.",
      exp: I + "has expired. The license expiration date is <b>{expDate:d}</b>.",
      lic: I + "is invalid.",
      mss: I + "is not set.",
      ctc: 'Please <a target="_blank" rel="noopener" href="https://www.grapecity.com/en/licensing/wijmo">create a license</a> or contact GrapeCity: <a href="mailto:us.sales@grapecity.com">us.sales@grapecity.com</a>.',
      hdr: "Wijmo License",
      cls: "CLOSE"
    },
    F = {};
  t._updateWme = function(n, i) {
    var o = t.Control._wme;
    if (!(o && t.contains(document.body, o) && o.offsetWidth && o.offsetHeight))
      if (i) {
        var s = e(n);
        F[s] || (F[s] = !0, f(i, {
          algorithmCode: "B0",
          publicKey: "l6/zrbWoSbcLFwEetFh38rH3ErBZE9H+Cqix3R+wTlfA1wD5B+lUcCQn+EJ60I4RGrm0x1sFjkiLWwB0jAn6BWZv0W4WbqAKriOdeoivxDp1Wmjs3qkEDhvbsjPtfvwx2BHil6o+/tDrdMJQSGs18WZm2PoQLQuL+9VhZ4FNRHUQU3Jtioke/OZEGHJOdYVwvCGalzBad6QFOiVbDBQPePpS3++GJzOxN8SN/7lyS5/IdKiy3WJRaVGkB370+HbN6hKraDfUgReLX26yxRaKC/5aWnGAJ2NnWLoGyAGRcwT9dVjo4bcAZNrrA0U9JVKQxaSskhdv2p49XzJkltXx5w=="
        }, function(t) {
          r(n, i, t)
        }, function() {
          r(n, i, null)
        }), setTimeout(function() {
          delete F[s]
        }, 50))
      } else r(n, null, null)
  };
  var M = "undefined" == typeof window ? null : window.Uint8Array || window.TypedArray
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  "use strict";
  var e = function() {
    function e(e, n, r) {
      void 0 === n && (n = null), void 0 === r && (r = "_"), this._promptChar = "_", this._mskArr = [], this._full = !0, this._hbInput = this._input.bind(this), this._hbKeyDown = this._keydown.bind(this), this._hbKeyPress = this._keypress.bind(this), this._hbCompositionStart = this._compositionstart.bind(this), this._hbCompositionEnd = this._compositionend.bind(this), this.mask = t.asString(n), this.input = e, this.promptChar = t.asString(r, !1), this._connect(!0), this._evtInput = document.createEvent("HTMLEvents"), this._evtInput.initEvent("input", !0, !1)
    }
    return Object.defineProperty(e.prototype, "input", {
      get: function() {
        return this._tbx
      },
      set: function(t) {
        this._connect(!1), this._tbx = t, this._connect(!0)
      },
      enumerable: !0,
      configurable: !0
    }), Object.defineProperty(e.prototype, "mask", {
      get: function() {
        return this._msk
      },
      set: function(e) {
        e != this._msk && (this._msk = t.asString(e, !0), this._parseMask(), this._valueChanged())
      },
      enumerable: !0,
      configurable: !0
    }), Object.defineProperty(e.prototype, "promptChar", {
      get: function() {
        return this._promptChar
      },
      set: function(e) {
        e != this._promptChar && (this._promptChar = t.asString(e, !1), t.assert(1 == this._promptChar.length, "promptChar must be a string with length 1."), this._valueChanged())
      },
      enumerable: !0,
      configurable: !0
    }), Object.defineProperty(e.prototype, "maskFull", {
      get: function() {
        return this._full
      },
      enumerable: !0,
      configurable: !0
    }), e.prototype.getMaskRange = function() {
      return this._mskArr.length ? [this._firstPos, this._lastPos] : [0, this._tbx.value.length - 1]
    }, e.prototype.getRawValue = function() {
      var t = this._tbx ? this._tbx.value : "";
      if (!this.mask) return t;
      for (var e = "", n = 0; n < this._mskArr.length && n < t.length; n++) this._mskArr[n].literal || t[n] == this._promptChar || (e += t[n]);
      return e
    }, e.prototype.refresh = function() {
      this._parseMask(), this._valueChanged()
    }, e.prototype._input = function(t) {
      var e = this;
      this._composing ? t.stopImmediatePropagation() : setTimeout(function() {
        e._valueChanged()
      })
    }, e.prototype._keydown = function(e) {
      if (e.keyCode == t.Key.Back) {
        var n = this._tbx.selectionStart,
          r = this._tbx.selectionEnd;
        if (n <= this._firstPos && r == n) return e.preventDefault(), void(this._backSpace = !1)
      }
      this._backSpace = e.keyCode == t.Key.Back
    }, e.prototype._keypress = function(t) {
      t.ctrlKey || t.metaKey || t.altKey || this._composing || !this._preventKey(t.charCode) || t.preventDefault()
    }, e.prototype._compositionstart = function(t) {
      this._composing = !0
    }, e.prototype._compositionend = function(e) {
      var n = this;
      this._composing && (this._composing = !1, setTimeout(function() {
        var e = n._tbx;
        e && (n._valueChanged() || t.setSelectionRange(e, e.value.length), e.dispatchEvent(n._evtInput))
      }))
    }, e.prototype._preventKey = function(e) {
      if (e && this._mskArr.length) {
        var n = this._tbx,
          r = n.selectionStart,
          i = String.fromCharCode(e);
        if (r < this._firstPos && (r = this._firstPos, t.setSelectionRange(n, r)), r >= this._mskArr.length) return !0;
        var o = this._mskArr[r];
        if (o.literal) this._validatePosition(r);
        else if (o.wildCard != i && !this._isCharValid(o.wildCard, i)) return !0
      }
      return !1
    }, e.prototype._connect = function(e) {
      var n = this._tbx;
      n && (t.assert(n instanceof HTMLInputElement || n instanceof HTMLTextAreaElement, "INPUT or TEXTAREA element expected."), e ? (this._autoComplete = n.autocomplete, this._spellCheck = n.spellcheck, n.autocomplete = "off", n.spellcheck = !1, n.addEventListener("input", this._hbInput, !0), n.addEventListener("keydown", this._hbKeyDown, !0), n.addEventListener("keypress", this._hbKeyPress, !0), n.addEventListener("compositionstart", this._hbCompositionStart, !0), n.addEventListener("compositionend", this._hbCompositionEnd, !0), n.addEventListener("blur", this._hbCompositionEnd, !0), this._valueChanged()) : (n.autocomplete = this._autoComplete, n.spellcheck = this._spellCheck, n.removeEventListener("input", this._hbInput), n.removeEventListener("keydown", this._hbKeyDown, !0), n.removeEventListener("keypress", this._hbKeyPress, !0), n.removeEventListener("compositionstart", this._hbCompositionStart, !0), n.removeEventListener("compositionend", this._hbCompositionEnd, !0), n.removeEventListener("blur", this._hbCompositionEnd, !0)))
    }, e.prototype._valueChanged = function() {
      if (!this._tbx || !this._msk) return !1;
      var t = this._tbx,
        e = t.value.length < 2,
        n = t.selectionStart,
        r = n > 0 ? t.value[n - 1] : "",
        i = t.value;
      t.value = this._applyMask(), e && (n = this._firstPos + 1);
      var o = n > 0 ? t.value[n - 1] : "";
      return n > 0 && o == this._promptChar && r != this.promptChar && n--, n == i.length && (n = this._matchEnd), this._validatePosition(n), i != t.value
    }, e.prototype._applyMask = function() {
      this._full = !0, this._matchEnd = 0;
      var t = this._tbx.value;
      if (!this._msk) return t;
      if (!t && !this._tbx.required) return t;
      var e = "",
        n = 0,
        r = this._promptChar;
      t = this._handleVagueLiterals(t);
      for (var i = 0; i < this._mskArr.length; i++) {
        var o = this._mskArr[i],
          s = o.literal;
        if (s && s == t[n] && n++, o.wildCard) {
          if (s = r, t) {
            for (var a = n; a < t.length; a++)
              if (this._isCharValid(o.wildCard, t[a])) {
                switch (s = t[a], o.charCase) {
                  case ">":
                    s = s.toUpperCase();
                    break;
                  case "<":
                    s = s.toLowerCase()
                }
                s != r && (this._matchEnd = e.length + 1);
                break
              }
            n = a + 1
          }
          s == r && (this._full = !1)
        }
        e += s
      }
      return e
    }, e.prototype._handleVagueLiterals = function(e) {
      if (e.length > this._mskArr.length + 1) return e;
      var n = e.length - this._mskArr.length;
      if (0 != n && e.length > 1) {
        for (var r = -1, i = this._tbx, o = i == t.getActiveElement() ? i.selectionStart : i.value.length, s = Math.max(0, o - n); s < this._mskArr.length; s++)
          if (this._mskArr[s].vague) {
            r = s;
            break
          }
        if (r > -1)
          if (n < 0) {
            var a = Array(1 - n).join(this._promptChar),
              u = r + n;
            u > -1 && (e = e.substr(0, u) + a + e.substr(u))
          } else {
            for (; r > 0 && this._mskArr[r - 1].literal;) r--;
            e = e.substr(0, r) + e.substr(r + n)
          }
      }
      return e
    }, e.prototype._isCharValid = function(t, n) {
      var r = this._promptChar;
      switch (t) {
        case "0":
          return n >= "0" && n <= "9" || n == r;
        case "9":
          return n >= "0" && n <= "9" || " " == n || n == r;
        case "#":
          return n >= "0" && n <= "9" || " " == n || "+" == n || "-" == n || n == r;
        case "L":
          return n >= "a" && n <= "z" || n >= "A" && n <= "Z" || n == r;
        case "l":
          return n >= "a" && n <= "z" || n >= "A" && n <= "Z" || " " == n || n == r;
        case "A":
          return n >= "0" && n <= "9" || n >= "a" && n <= "z" || n >= "A" && n <= "Z" || n == r;
        case "a":
          return n >= "0" && n <= "9" || n >= "a" && n <= "z" || n >= "A" && n <= "Z" || " " == n || n == r;
        case "９":
          return n >= "０" && n <= "９" || n == r;
        case "Ｊ":
        case "Ｇ":
          return !("Ｇ" == t && e._X_DBCS_BIG_HIRA.indexOf(n) > -1) && (n >= "ぁ" && n <= "ゖ" || n == r);
        case "Ｋ":
        case "Ｎ":
          return !("Ｎ" == t && e._X_DBCS_BIG_KATA.indexOf(n) > -1) && (n >= "ァ" && n <= "ヺ" || n == r);
        case "Ｚ":
          return n <= "!" || n >= "ÿ" || n == r;
        case "H":
          return n >= "!" && n <= "ÿ" || n == r;
        case "K":
        case "N":
          return !("N" == t && e._X_SBCS_BIG_KATA.indexOf(n) > -1) && (n >= "ｦ" && n <= "ﾟ" || n == r)
      }
      return !1
    }, e.prototype._validatePosition = function(e) {
      var n = this._mskArr;
      if (this._backSpace)
        for (; e > 0 && e < n.length && n[e - 1].literal;) e--;
      if (0 == e || !this._backSpace)
        for (; e < n.length && n[e].literal;) e++;
      t.getActiveElement() == this._tbx && t.setSelectionRange(this._tbx, e), this._backSpace = !1
    }, e.prototype._parseMask = function() {
      this._mskArr = [], this._firstPos = -1, this._lastPos = -1;
      for (var e, r = this._msk, i = "|", o = 0; r && o < r.length; o++) switch (r[o]) {
        case "0":
        case "9":
        case "#":
        case "A":
        case "a":
        case "L":
        case "l":
        case "９":
        case "Ｊ":
        case "Ｇ":
        case "Ｋ":
        case "Ｎ":
        case "Ｚ":
        case "K":
        case "N":
        case "H":
          this._firstPos < 0 && (this._firstPos = this._mskArr.length), this._lastPos = this._mskArr.length, this._mskArr.push(new n(r[o], i));
          break;
        case ".":
        case ",":
        case ":":
        case "/":
        case "$":
          switch (r[o]) {
            case ".":
            case ",":
              e = t.culture.Globalize.numberFormat[r[o]];
              break;
            case ":":
            case "/":
              e = t.culture.Globalize.calendar[r[o]];
              break;
            case "$":
              e = t.culture.Globalize.numberFormat.currency.symbol
          }
          for (a = 0; a < e.length; a++) this._mskArr.push(new n(e[a]));
          break;
        case "<":
        case ">":
        case "|":
          i = r[o];
          break;
        case "\\":
          o < r.length - 1 && o++, this._mskArr.push(new n(r[o]));
          break;
        default:
          this._mskArr.push(new n(r[o]))
      }
      for (o = 0; o < this._mskArr.length; o++) {
        var s = this._mskArr[o];
        if (s.literal)
          for (var a = 0; a < o; a++) {
            var u = this._mskArr[a];
            if (u.wildCard && this._isCharValid(u.wildCard, s.literal)) {
              s.vague = !0;
              break
            }
          }
      }
    }, e._X_DBCS_BIG_HIRA = "ぁぃぅぇぉっゃゅょゎゕゖ", e._X_DBCS_BIG_KATA = "ァィゥェォッャュョヮヵヶ", e._X_SBCS_BIG_KATA = "ｧｨｩｪｫｬｭｮｯ", e
  }();
  t._MaskProvider = e;
  var n = function() {
    return function(t, e) {
      e ? (this.wildCard = t, this.charCase = e) : this.literal = t
    }
  }();
  t._MaskElement = n
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  "use strict";
  var e = function() {
    function e(t) {
      this._isDown = !1, this._mousedownBnd = this._mousedown.bind(this), this._mouseupBnd = this._mouseup.bind(this), this._clickBnd = this._click.bind(this), this.element = t, this._connect(!0)
    }
    return Object.defineProperty(e.prototype, "element", {
      get: function() {
        return this._e
      },
      set: function(e) {
        this._connect(!1), this._e = t.asType(e, HTMLElement, !0), this._connect(!0)
      },
      enumerable: !0,
      configurable: !0
    }), Object.defineProperty(e.prototype, "disabled", {
      get: function() {
        return this._disabled
      },
      set: function(e) {
        this._disabled = t.asBoolean(e)
      },
      enumerable: !0,
      configurable: !0
    }), e.prototype._connect = function(t) {
      if (this._e) {
        t ? this._e.addEventListener("mousedown", this._mousedownBnd) : this._e.removeEventListener("mousedown", this._mousedownBnd)
      }
    }, e.prototype._clearTimeouts = function() {
      this._toRepeat && (clearInterval(this._toRepeat), this._toRepeat = null), this._toDelay && (clearInterval(this._toDelay), this._toDelay = null)
    }, e.prototype._mousedown = function(n) {
      var r = this;
      this._isDown && this._mouseup(null), this._disabled || (this._isDown = !0, e._stopEvents.forEach(function(t) {
        document.addEventListener(t, r._mouseupBnd)
      }), this._clearTimeouts(), this._toDelay = setTimeout(function() {
        r._isDown && (r._click(), r._toRepeat = setTimeout(r._clickBnd, t.Control._CLICK_REPEAT))
      }, t.Control._CLICK_DELAY))
    }, e.prototype._mouseup = function(t) {
      var n = this;
      this._isDown && t && "mouseup" == t.type && t.preventDefault(), e._stopEvents.forEach(function(t) {
        document.removeEventListener(t, n._mouseupBnd)
      }), this._clearTimeouts(), this._isDown = !1
    }, e.prototype._click = function() {
      this._clearTimeouts(), this._e && (this._e.click(), this._isDown && (this._toRepeat = setTimeout(this._clickBnd, t.Control._CLICK_REPEAT)))
    }, e._stopEvents = ["mouseup", "mouseout", "keydown"], e
  }();
  t._ClickRepeater = e
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  "use strict";

  function e() {
    return r
  }
  var n = null != navigator.userAgent.match(/Android|webOS|iPhone|iPad|iPod|BlackBerry|Windows Phone/i);
  t.isMobile = function() {
    return n
  };
  var r = null != navigator.userAgent.match(/Firefox\//);
  t.isFirefox = e;
  var i = null != navigator.userAgent.match(/^((?!chrome|android).)*safari/i);
  t.isSafari = function() {
    return i
  };
  var o = null != navigator.userAgent.match(/Edge\//);
  t.isEdge = function() {
    return o
  };
  var s = null != navigator.userAgent.match(/MSIE |Trident\/|Edge\//);
  t.isIE = function() {
    return s
  };
  var a = !1;
  t.isIE9 = function() {
    return a
  };
  var u = !1;
  t.isIE10 = function() {
    return u
  };
  var c = !1;
  "undefined" != typeof document && document.addEventListener("test", function(t) {}, {get passive() {
      return c = !0, !0
    }
  }), t.getEventOptions = function(t, e) {
    return c ? {
      capture: t,
      passive: e
    } : t
  };
  var l = !1;
  if ("undefined" != typeof document && document.createElement("div").focus({get preventScroll() {
      return l = !0, !0
    }
  }), t.supportsFocusOptions = function() {
    return l
  }, t._startDrag = function(t, n) {
    t.effectAllowed = n, e() && t.setData("text", "")
  }, "undefined" != typeof document && document.doctype && (navigator.appVersion.indexOf("MSIE 10") > -1 && (u = !0), navigator.appVersion.indexOf("MSIE 9") > -1 && (a = !0, document.addEventListener("mousemove", function(e) {
    if (1 == e.which) {
      var n = t.closest(e.target, ".wj-control");
      if (n && !n.style.cursor)
        for (var r = e.target; r; r = r.parentElement)
          if (r.attributes && r.attributes.draggable) return r.dragDrop(), !1
    }
  }))), "undefined" != typeof window) {
    var h = "requestAnimationFrame";
    if (!window[h]) {
      var p = 0;
      window[h] = function(t) {
        var e = Date.now(),
          n = 16 - (e - p),
          r = n > 0 ? n : 0;
        return p = e + r, setTimeout(function() {
          t(p)
        }, r)
      }, window.cancelAnimationFrame = clearTimeout
    }
    if (!window.atob) {
      var f = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
        d = new RegExp("[^" + f + "]");
      window.atob = function(t) {
        var e, n, r, i, o = [],
          s = 0,
          a = t.length;
        if (d.test(t) || /=/.test(t) && (/=[^=]/.test(t) || /={3}/.test(t))) throw new Error("Invalid base64 data");
        for (a % 4 > 0 && (a = (t += Array(4 - a % 4 + 1).join("=")).length); s < a;) {
          for (n = [], i = s; s < i + 4;) n.push(f.indexOf(t.charAt(s++)));
          for (r = [((e = (n[0] << 18) + (n[1] << 12) + ((63 & n[2]) << 6) + (63 & n[3])) & 255 << 16) >> 16, 64 === n[2] ? -1 : (65280 & e) >> 8, 64 === n[3] ? -1 : 255 & e], i = 0; i < 3; ++i)(r[i] >= 0 || 0 === i) && o.push(String.fromCharCode(r[i]))
        }
        return o.join("")
      }, window.btoa = function(t) {
        for (var e, n, r = [], i = 0, o = t.length; i < o;) e = ((n = [t.charCodeAt(i++), t.charCodeAt(i++), t.charCodeAt(i++)])[0] << 16) + ((n[1] || 0) << 8) + (n[2] || 0), r.push(f.charAt((e & 63 << 18) >> 18), f.charAt((258048 & e) >> 12), f.charAt(isNaN(n[1]) ? 64 : (4032 & e) >> 6), f.charAt(isNaN(n[2]) ? 64 : 63 & e));
        return r.join("")
      }
    }
  }
}(wijmo || (wijmo = {}));
