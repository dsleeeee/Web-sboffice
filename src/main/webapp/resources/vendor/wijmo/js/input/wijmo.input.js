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
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    t._addCultureInfo("DropDown", {
      ariaLabels: {
        tgl: "Toggle Dropdown"
      }
    });
    var i = function(e) {
      function i(i, n) {
        var o = e.call(this, i, null, !0) || this;
        o._showBtn = !0, o._autoExpand = !0, o._animate = !1, o.textChanged = new t.Event, o.isDroppedDownChanging = new t.Event, o.isDroppedDownChanged = new t.Event;
        var s = o.getTemplate();
        o.applyTemplate("wj-control wj-content wj-dropdown", s, {
          _tbx: "input",
          _btn: "btn",
          _dropDown: "dropdown"
        }, "input");
        var r = t.culture.DropDown.ariaLabels;
        t.setAriaLabel(o._btn.querySelector("button"), r.tgl);
        var a = o._tbx;
        o._elRef = a, t.disableAutoComplete(a), o._createDropDown(), o._updateBtn(), t.removeChild(o._dropDown), t.addClass(o.hostElement, "wj-state-collapsed");
        var l = o.addEventListener.bind(o),
          h = o._updateFocusState.bind(o);
        l(o.dropDown, "blur", h, !0), l(o.dropDown, "focus", h);
        var c = o._keydown.bind(o);
        return l(o.hostElement, "keydown", c), l(o.dropDown, "keydown", c), l(a, "keypress", function(t) {
          9787 == t.keyCode && o._altDown && t.preventDefault()
        }), l(a, "keyup", function(t) {
          o._altDown = o._altDown || t.altKey
        }), l(a, "input", function() {
          o._setText(o.text, !1)
        }), l(a, "click", function() {
          o._autoExpand && o._expandSelection()
        }), l(o._btn, "mousedown", function(t) {
          t.defaultPrevented || 0 != t.button || (o._btn.focus(), o._btnclick(t), t.preventDefault())
        }), t.isIE() && o._elRef == o._tbx && l(o.hostElement, "mouseup", function(e) {
          if (!e.defaultPrevented && 0 == e.button && t.hasClass(e.target, "wj-btn")) {
            var i = t.getActiveElement();
            i && i != e.target && setTimeout(function() {
              i.focus()
            })
          }
        }), t.isIE9() && l(a, "keyup", function() {
          o._setText(o.text, !1)
        }), l(o._dropDown, "click", o._dropDownClick.bind(o)), o
      }
      return __extends(i, e), Object.defineProperty(i.prototype, "text", {
        get: function() {
          return this._tbx.value
        },
        set: function(t) {
          t != this.text && this._setText(t, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "inputElement", {
        get: function() {
          return this._tbx
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "isReadOnly", {
        get: function() {
          return this._tbx.readOnly
        },
        set: function(e) {
          this._tbx.readOnly = t.asBoolean(e), t.toggleClass(this.hostElement, "wj-state-readonly", this.isReadOnly)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "isRequired", {
        get: function() {
          return this._tbx.required
        },
        set: function(e) {
          this._tbx.required = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "placeholder", {
        get: function() {
          return this._tbx.placeholder
        },
        set: function(t) {
          this._tbx.placeholder = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "isDroppedDown", {
        get: function() {
          var t = this._dropDown;
          return t && "none" != t.style.display
        },
        set: function(e) {
          var i = this;
          if ((e = t.asBoolean(e) && !this.isDisabled && !this.isReadOnly) != this.isDroppedDown && this.onIsDroppedDownChanging(new t.CancelEventArgs)) {
            var n = this.hostElement,
              o = this._dropDown;
            if (e) this._minWidthDropdown = o.style.minWidth, o.style.display = "block", this._updateDropDown(), this.addEventListener(window, "touchstart", function(e) {
              for (var o = e.target; o;) {
                if (o == n) return;
                o = o[t.Control._OWNR_KEY] || o.parentNode
              }
              t.Control._touching = !0, i.isDroppedDown = !1, t.Control._touching = !1
            });
            else {
              this.removeEventListener(window, "touchstart");
              var s = this.containsFocus();
              t.hidePopup(o), s && (this.isTouching && this.showDropDownButton ? n.focus() : this.selectAll()), o.style.minWidth = this._minWidthDropdown
            }
            this._updateFocusState(), t.toggleClass(n, "wj-state-collapsed", !this.isDroppedDown), this.onIsDroppedDownChanged()
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "dropDown", {
        get: function() {
          return this._dropDown
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "dropDownCssClass", {
        get: function() {
          return this._cssClass
        },
        set: function(e) {
          e != this._cssClass && (t.removeClass(this._dropDown, this._cssClass), this._cssClass = t.asString(e), t.addClass(this._dropDown, this._cssClass))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "showDropDownButton", {
        get: function() {
          return this._showBtn
        },
        set: function(e) {
          this._showBtn = t.asBoolean(e), this._updateBtn()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "autoExpandSelection", {
        get: function() {
          return this._autoExpand
        },
        set: function(e) {
          this._autoExpand = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "isAnimated", {
        get: function() {
          return this._animate
        },
        set: function(e) {
          this._animate = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.selectAll = function() {
        this._elRef == this._tbx && t.setSelectionRange(this._tbx, 0, this.text.length), this.containsFocus() || this.focus()
      }, i.prototype.onTextChanged = function(t) {
        this.textChanged.raise(this, t), this._updateState()
      }, i.prototype.onIsDroppedDownChanging = function(t) {
        return this.isDroppedDownChanging.raise(this, t), !t.cancel
      }, i.prototype.onIsDroppedDownChanged = function(t) {
        this.isDroppedDownChanged.raise(this, t)
      }, i.prototype.onGotFocus = function(i) {
        this.isTouching || t.contains(this._dropDown, t.getActiveElement()) || this.selectAll(), e.prototype.onGotFocus.call(this, i)
      }, i.prototype.onLostFocus = function(t) {
        this._commitText(), this.containsFocus() || (this.isDroppedDown = !1), e.prototype.onLostFocus.call(this, t)
      }, i.prototype.containsFocus = function() {
        return e.prototype.containsFocus.call(this) || this.isDroppedDown && t.contains(this._dropDown, t.getActiveElement())
      }, i.prototype.dispose = function() {
        this.isDroppedDown = !1;
        var i = this.dropDown;
        if (i) {
          var n = t.Control.getControl(i);
          n && n.dispose(), t.removeChild(i)
        }
        e.prototype.dispose.call(this)
      }, i.prototype.refresh = function(i) {
        if (void 0 === i && (i = !0), e.prototype.refresh.call(this, i), this.hostElement.offsetHeight && this.isDroppedDown) {
          var n = t.getActiveElement();
          this.isAnimated && "" != this._dropDown.style.opacity || t.showPopup(this._dropDown, this.hostElement, !1, !1, null == this.dropDownCssClass), n instanceof HTMLElement && n != t.getActiveElement() && n.focus()
        }
      }, i.prototype._handleResize = function() {
        this.isDroppedDown && this.refresh()
      }, i.prototype._dropDownClick = function(t) {
        t.stopPropagation()
      }, i.prototype._expandSelection = function() {
        var e = this._tbx,
          i = e.value,
          n = e.selectionStart,
          o = e.selectionEnd;
        if (i && n == o) {
          var s = this._getCharType(i, n);
          if (s > -1) {
            for (; o < i.length && this._getCharType(i, o) == s; o++);
            for (; n > 0 && this._getCharType(i, n - 1) == s; n--);
            n != o && t.setSelectionRange(e, n, o)
          }
        }
      }, i.prototype._getCharType = function(t, e) {
        var i = t[e];
        return i >= "0" && i <= "9" ? 0 : i >= "a" && i <= "z" || i >= "A" && i <= "Z" ? 1 : -1
      }, i.prototype._keydown = function(e) {
        if (this._altDown = e.altKey, !e.defaultPrevented) switch (e.keyCode) {
          case t.Key.Tab:
          case t.Key.Escape:
          case t.Key.Enter:
            this.isDroppedDown && (this.isDroppedDown = !1, e.keyCode == t.Key.Tab || this.containsFocus() || this.focus(), e.preventDefault());
            break;
          case t.Key.F4:
          case t.Key.Up:
          case t.Key.Down:
            if ((e.keyCode == t.Key.F4 || e.altKey) && t.contains(document.body, this.hostElement)) {
              var i = this.isDroppedDown;
              this.isDroppedDown = !i, this.isDroppedDown == !i && e.preventDefault()
            }
        }
      }, i.prototype._btnclick = function(t) {
        t.defaultPrevented || 0 != t.button || (this.isDroppedDown = !this.isDroppedDown)
      }, i.prototype._setText = function(t, e) {
        null == t && (t = ""), (t = t.toString()) != this._tbx.value && (this._tbx.value = t), t != this._oldText && (this._oldText = t, this.onTextChanged())
      }, i.prototype._updateBtn = function() {
        this._btn.style.display = this._showBtn ? "" : "none"
      }, i.prototype._createDropDown = function() {}, i.prototype._commitText = function() {}, i.prototype._updateDropDown = function() {
        if (this.isDroppedDown) {
          this._commitText();
          var e = this.dropDown;
          t.setAttribute(e, "dir", this.rightToLeft ? "rtl" : null), t.showPopup(e, this.hostElement, !1, this._animate, null == this.dropDownCssClass)
        }
      }, i.controlTemplate = '<div class="wj-template"><div class="wj-input"><div class="wj-input-group wj-input-btn-visible"><input wj-part="input" type="text" class="wj-form-control"/><span wj-part="btn" class="wj-input-group-btn"><button class="wj-btn wj-btn-default" tabindex="-1"><span class="wj-glyph-down"></span></button></span></div></div><div wj-part="dropdown" class="wj-content wj-dropdown-panel" style="display:none"></div></div>', i
    }(t.Control);
    e.DropDown = i
  }(t.input || (t.input = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    t._addCultureInfo("Calendar", {
      ariaLabels: {
        calendar: "Calendar",
        monthView: "Month View",
        yearView: "Year View",
        prvMo: "Previous Month",
        today: "Today",
        nxtMo: "Next Month",
        prvYr: "Previous Year",
        currMo: "Current Month",
        nxtYr: "Next Year"
      }
    });
    var i;
    ! function(t) {
      t[t.None = 0] = "None", t[t.Day = 1] = "Day", t[t.Month = 2] = "Month"
    }(i = e.DateSelectionMode || (e.DateSelectionMode = {}));
    var n = function(n) {
      function o(e, o) {
        var s = n.call(this, e, null, !0) || this;
        s._readOnly = !1, s._selMode = i.Day, s._yrPicker = !0, s._fmtYrMo = "y", s._fmtYr = "yyyy", s._fmtDayHdr = "ddd", s._fmtDay = "d ", s._fmtMonths = "MMM", s.valueChanged = new t.Event, s.displayMonthChanged = new t.Event, s.formatItem = new t.Event;
        var r = s.hostElement;
        return r.tabIndex = -1, s._value = t.DateTime.newDate(), s._currMonth = s._getMonth(s._value), s._createChildren(), s._createYearPicker(), s.addEventListener(r, "keydown", s._keydown.bind(s)), s.addEventListener(r, "click", s._click.bind(s)), s.addEventListener(r, "mousedown", function() {
          s._yrPickerWasVisible = s._lbYears.hostElement.offsetHeight > 0
        }), s._rptUp = new t._ClickRepeater(s._btnPrv), s._rptDn = new t._ClickRepeater(s._btnNxt), s.addEventListener(r, "wheel", function(e) {
          if (!e.defaultPrevented && !s.isReadOnly && s.containsFocus()) {
            t.clamp(-e.deltaY, -1, 1);
            e.deltaY < 0 ? s._btnPrv.click() : s._btnNxt.click(), e.preventDefault()
          }
        }), s.initialize(o), s.refresh(!0), s
      }
      return __extends(o, n), Object.defineProperty(o.prototype, "value", {
        get: function() {
          return this._value
        },
        set: function(e) {
          e = t.asDate(e, !0), e = this._clamp(e), (this._valid(e) || null == e) && (this.displayMonth = this._getMonth(e), t.DateTime.equals(this._value, e) || (this._value = e, this.invalidate(!1), this.onValueChanged()))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "min", {
        get: function() {
          return this._min
        },
        set: function(e) {
          e != this.min && (this._min = t.asDate(e, !0), this.refresh())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "max", {
        get: function() {
          return this._max
        },
        set: function(e) {
          e != this.max && (this._max = t.asDate(e, !0), this.refresh())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "selectionMode", {
        get: function() {
          return this._selMode
        },
        set: function(e) {
          if ((e = t.asEnum(e, i)) != this._selMode) {
            this._selMode = e;
            var n = this._monthMode();
            n && (this.monthView = !1);
            var o = this._btnMth.querySelector(".wj-glyph-down");
            o && (o.style.display = n ? "none" : ""), this.refresh()
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "isReadOnly", {
        get: function() {
          return this._readOnly
        },
        set: function(e) {
          this._readOnly = t.asBoolean(e), t.toggleClass(this.hostElement, "wj-state-readonly", this.isReadOnly)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "repeatButtons", {
        get: function() {
          return !this._rptUp.disabled
        },
        set: function(e) {
          this._rptUp.disabled = this._rptDn.disabled = !t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "showYearPicker", {
        get: function() {
          return this._yrPicker
        },
        set: function(e) {
          this._yrPicker = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "firstDayOfWeek", {
        get: function() {
          return this._fdw
        },
        set: function(e) {
          if (e != this._fdw) {
            if ((e = t.asNumber(e, !0)) && (e > 6 || e < 0)) throw "firstDayOfWeek must be between 0 and 6 (Sunday to Saturday).";
            this._fdw = e, this.refresh()
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "displayMonth", {
        get: function() {
          return this._currMonth
        },
        set: function(e) {
          t.DateTime.equals(this.displayMonth, e) || (e = t.asDate(e), (this.monthView ? this._monthInValidRange(e) : this._yearInValidRange(e)) && (this._currMonth = this._getMonth(this._clamp(e)), this.invalidate(!0), this.onDisplayMonthChanged()))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "formatYearMonth", {
        get: function() {
          return this._fmtYrMo
        },
        set: function(e) {
          e != this._fmtYrMo && (this._fmtYrMo = t.asString(e), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "formatDayHeaders", {
        get: function() {
          return this._fmtDayHdr
        },
        set: function(e) {
          e != this._fmtDayHdr && (this._fmtDayHdr = t.asString(e), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "formatDays", {
        get: function() {
          return this._fmtDay
        },
        set: function(e) {
          e != this._fmtDay && (this._fmtDay = t.asString(e), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "formatYear", {
        get: function() {
          return this._fmtYr
        },
        set: function(e) {
          e != this._fmtYr && (this._fmtYr = t.asString(e), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "formatMonths", {
        get: function() {
          return this._fmtMonths
        },
        set: function(e) {
          e != this._fmtMonths && (this._fmtMonths = t.asString(e), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "showHeader", {
        get: function() {
          return "none" != this._tbHdr.style.display
        },
        set: function(e) {
          this._tbHdr.style.display = t.asBoolean(e) ? "" : "none"
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "monthView", {
        get: function() {
          return "none" != this._tbMth.style.display
        },
        set: function(e) {
          if (e != this.monthView) {
            this._tbMth.style.display = e ? "" : "none", this._tbYr.style.display = e ? "none" : "", this.refresh();
            var i = t.culture.Calendar.ariaLabels;
            t.setAriaLabel(this._btnPrv, e ? i.prvMo : i.prvYr), t.setAriaLabel(this._btnTdy, e ? i.today : i.currMo), t.setAriaLabel(this._btnNxt, e ? i.nxtMo : i.nxtYr), t.setAriaLabel(this._btnMth, e ? i.monthView : i.yearView)
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "itemFormatter", {
        get: function() {
          return this._itemFormatter
        },
        set: function(e) {
          e != this._itemFormatter && (this._itemFormatter = t.asFunction(e), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "itemValidator", {
        get: function() {
          return this._itemValidator
        },
        set: function(e) {
          e != this._itemValidator && (this._itemValidator = t.asFunction(e), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype.onValueChanged = function(t) {
        this.valueChanged.raise(this, t)
      }, o.prototype.onDisplayMonthChanged = function(t) {
        this.displayMonthChanged.raise(this, t)
      }, o.prototype.onFormatItem = function(t) {
        this.formatItem.raise(this, t)
      }, o.prototype.containsFocus = function() {
        return n.prototype.containsFocus.call(this) || this._lbYears.containsFocus()
      }, o.prototype.dispose = function() {
        this._rptUp.element = null, this._rptDn.element = null, this._lbYears.dispose(), n.prototype.dispose.call(this)
      }, o.prototype.refresh = function(i) {
        void 0 === i && (i = !0);
        var o, s, r, a = this.displayMonth,
          l = null != this.firstDayOfWeek ? this.firstDayOfWeek : t.Globalize.getFirstDayOfWeek(),
          h = this.hostElement,
          c = t.getActiveElement(),
          u = this.containsFocus(),
          p = t.contains(h, c) ? c : null;
        n.prototype.refresh.call(this, i), this._firstDay = t.DateTime.addDays(a, -(a.getDay() - l + 7) % 7), t.setText(this._spMth, t.Globalize.format(a, this._fmtYrMo)), o = this._tbMth.querySelectorAll("td");
        for (y = 0; y < 7 && y < o.length; y++) r = t.DateTime.addDays(this._firstDay, y), t.setText(o[y], t.Globalize.format(r, this._fmtDayHdr));
        for (y = 7; y < o.length; y++) {
          s = o[y], r = t.DateTime.addDays(this._firstDay, y - 7), t.setText(s, t.Globalize.format(r, this._fmtDay));
          var d = !this._valid(r),
            f = this._selMode && t.DateTime.sameDate(r, this.value),
            _ = r.getDay();
          if (t.toggleClass(s, "wj-state-invalid", d), t.toggleClass(s, "wj-state-selected", f), t.toggleClass(s, "wj-day-weekend", 0 == _ || 6 == _), t.toggleClass(s, "wj-day-today", t.DateTime.sameDate(r, t.DateTime.newDate())), t.toggleClass(s, "wj-day-othermonth", d || r.getMonth() != a.getMonth() || !this._inValidRange(r)), t.setAttribute(s, "aria-selected", f), s.tabIndex = -1, this.itemFormatter && this.itemFormatter(r, s), this.formatItem.hasHandlers) {
            var m = new e.FormatItemEventArgs(y, r, s);
            this.onFormatItem(m)
          }
        }
        var b = this._tbMth.querySelectorAll("tr");
        b.length && (r = t.DateTime.addDays(this._firstDay, 28), b[b.length - 2].style.display = r.getMonth() == a.getMonth() ? "" : "none", r = t.DateTime.addDays(this._firstDay, 35), b[b.length - 1].style.display = r.getMonth() == a.getMonth() ? "" : "none"), (o = this._tbYr.querySelectorAll("td")).length && t.setText(o[0], t.Globalize.format(a, this._fmtYr));
        for (var y = 1; y < o.length; y++) {
          r = t.DateTime.newDate(a.getFullYear(), y - 1, 1);
          var d = !this._monthInValidRange(r),
            f = this._sameMonth(r, this.value);
          s = o[y], t.setText(s, t.Globalize.format(r, this._fmtMonths)), t.toggleClass(s, "wj-state-disabled", d), t.toggleClass(s, "wj-state-selected", f), t.setAttribute(s, "aria-selected", f), s.tabIndex = -1
        }
        var g = (this.monthView ? this._tbMth : this._tbYr).querySelector("td.wj-state-selected") || h;
        g.tabIndex = this._orgTabIndex, u && (p && p != g && (p.tabIndex = -1), g.focus())
      }, o.prototype._canChangeValue = function() {
        return !this._readOnly && this._selMode != i.None
      }, o.prototype._valid = function(e) {
        return !!t.isDate(e) && (!!t.DateTime.sameDate(e, this._clamp(e)) && (!t.isFunction(this.itemValidator) || this.itemValidator(e)))
      }, o.prototype._inValidRange = function(e) {
        return !(this.min && e < t.DateTime.fromDateTime(this.min, e)) && !(this.max && e > t.DateTime.fromDateTime(this.max, e))
      }, o.prototype._monthInValidRange = function(e) {
        if (this.min || this.max) {
          var i = e.getFullYear(),
            n = e.getMonth(),
            o = t.DateTime.newDate(i, n, 1),
            s = t.DateTime.newDate(i, n + 1, 0);
          if (this.min && this.min > s) return !1;
          if (this.max && this.max < o) return !1
        }
        return !0
      }, o.prototype._yearInValidRange = function(e) {
        if (this.min || this.max) {
          var i = e.getFullYear(),
            n = t.DateTime.newDate(i, 0, 1),
            o = t.DateTime.newDate(i, 11, 31);
          if (this.min && this.min > o) return !1;
          if (this.max && this.max < n) return !1
        }
        return !0
      }, o.prototype._sameMonth = function(e, i) {
        return t.isDate(e) && t.isDate(i) && e.getMonth() == i.getMonth() && e.getFullYear() == i.getFullYear()
      }, o.prototype._getValidDate = function(e, i) {
        if (t.isDate(e))
          for (var n = e.getFullYear(), o = e.getMonth() + (i ? 0 : 1), s = i ? 1 : 0, r = t.DateTime.newDate(n, o, s), a = i ? 1 : -1, l = 0; l < 31; l++) {
            if (this._valid(r)) return r;
            r = t.DateTime.addDays(r, a)
          }
        return null
      }, o.prototype._clamp = function(e) {
        if (e) {
          if (this.min) {
            var i = t.DateTime.fromDateTime(this.min, e);
            e < i && (e = i)
          }
          if (this.max) {
            var n = t.DateTime.fromDateTime(this.max, e);
            e > n && (e = n)
          }
        }
        return e
      }, o.prototype._createChildren = function() {
        var e = this.getTemplate();
        this.applyTemplate("wj-control wj-calendar", e, {
          _tbHdr: "tbl-header",
          _btnMth: "btn-month",
          _spMth: "span-month",
          _btnPrv: "btn-prev",
          _btnTdy: "btn-today",
          _btnNxt: "btn-next",
          _tbMth: "tbl-month",
          _tbYr: "tbl-year"
        });
        var i = t.culture.Calendar.ariaLabels;
        t.setAriaLabel(this._tbMth, i.calendar), t.setAriaLabel(this._tbYr, i.calendar), t.setAriaLabel(this._btnPrv, i.calendar), t.setAriaLabel(this._btnMth, i.monthView), t.setAriaLabel(this._btnPrv, i.prvMo), t.setAriaLabel(this._btnTdy, i.today), t.setAriaLabel(this._btnNxt, i.nxtMo);
        for (var n = this._createElement("tr", this._tbMth, "wj-header"), o = 0; o < 7; o++) this._createElement("td", n);
        for (var s = 0; s < 6; s++) {
          n = this._createElement("tr", this._tbMth);
          for (o = 0; o < 7; o++) this._createElement("td", n)
        }
        n = this._createElement("tr", this._tbYr, "wj-header"), this._createElement("td", n).setAttribute("colspan", "4");
        for (var r = 0; r < 3; r++) {
          n = this._createElement("tr", this._tbYr);
          for (var a = 0; a < 4; a++) this._createElement("td", n)
        }
      }, o.prototype._createYearPicker = function() {
        var e = this,
          i = this.hostElement,
          n = this._createElement("div", null, "wj-dropdown-panel wj-yearpicker");
        n.tabIndex = this._orgTabIndex, this._lbYears = new t.input.ListBox(n, {
          lostFocus: function(i, o) {
            if (t.hidePopup(n), e.removeEventListener(window, "touchstart"), i.selectedIndex > -1) {
              var s = t.DateTime.clone(e.displayMonth);
              s.setFullYear(i.selectedIndex + i.itemsSource[0]), e.displayMonth = s
            }
          }
        }), this.addEventListener(n, "keydown", function(n) {
          switch (n.keyCode) {
            case t.Key.Enter:
              i.focus();
              break;
            case t.Key.Escape:
              e._lbYears.selectedIndex = -1, i.focus()
          }
          n.defaultPrevented && n.stopPropagation()
        }), this.addEventListener(n, "click", function(t) {
          i.focus()
        })
      }, o.prototype._createElement = function(e, i, n) {
        var o = document.createElement(e);
        if (o.tabIndex = -1, n && t.addClass(o, n), "TD" == o.tagName) {
          var s = t.closest(o, ".wj-header") ? "columnheader" : "gridcell";
          t.setAttribute(o, "role", s), t.setAttribute(o, "aria-selected", !1)
        }
        return i && i.appendChild(o), o
      }, o.prototype._click = function(e) {
        var i = this;
        if (!e.defaultPrevented && 0 == e.button) {
          var n = !1,
            o = e.target;
          if (t.contains(this._btnMth, o) && !this._monthMode() ? (this.monthView = !this.monthView, n = !0) : t.contains(this._btnPrv, o) ? (this._navigate(-1), n = !0) : t.contains(this._btnNxt, o) ? (this._navigate(1), n = !0) : t.contains(this._btnTdy, o) && (this._navigate(0), n = !0), o && !n && this._yrPicker && !this._yrPickerWasVisible && t.closest(o, ".wj-header") && t.closest(o, ".wj-calendar-year")) {
            for (var s = t.closest(o, ".wj-header"), r = this.displayMonth.getFullYear(), a = this.min ? this.min.getFullYear() : r - 100, l = this.max ? this.max.getFullYear() : r + 100, h = [], c = a; c <= l; c++) h.push(c);
            var u = this._lbYears,
              p = u.hostElement;
            return u.itemsSource = h, u.selectedIndex = r - h[0], t.setAttribute(p, "dir", this.rightToLeft ? "rtl" : null), p.style.minWidth = "", t.showPopup(p, s, !1, !1, this.hostElement), u.focus(), this.addEventListener(window, "touchstart", function(e) {
              t.contains(p, e.target) || (t.hidePopup(p), i.removeEventListener(window, "touchstart"))
            }), void e.preventDefault()
          }
          if (o && !n) {
            var d = t.closest(o, "TD");
            if (d)
              if (this.monthView) {
                if ((f = this._getCellIndex(this._tbMth, d)) > 6 && this._canChangeValue()) {
                  _ = t.DateTime.fromDateTime(t.DateTime.addDays(this._firstDay, f - 7), this.value);
                  this._inValidRange(_) && this._valid(_) && (this.value = _), n = !0
                }
              } else {
                var f = this._getCellIndex(this._tbYr, d);
                if (f > 0) {
                  if (this.displayMonth = t.DateTime.newDate(this.displayMonth.getFullYear(), f - 1, 1), this._monthMode()) {
                    if (this._canChangeValue()) {
                      var _ = t.DateTime.fromDateTime(this.displayMonth, this.value);
                      this._inValidRange(_) && (this.value = _)
                    }
                  } else this.monthView = !0;
                  n = !0
                }
              }
          }
          n && (e.preventDefault(), this.focus())
        }
      }, o.prototype._getCellIndex = function(t, e) {
        for (var i = t.querySelectorAll("TD"), n = 0; n < i.length; n++)
          if (i[n] == e) return n;
        return -1
      }, o.prototype._keydown = function(e) {
        if (!e.defaultPrevented) {
          if (e.altKey) switch (e.keyCode) {
            case t.Key.Up:
            case t.Key.Down:
              return;
            case t.Key.End:
              return this._navigate(0), void e.preventDefault()
          }
          if (!(e.ctrlKey || e.metaKey || e.shiftKey)) {
            var i = this._getKeyCode(e),
              n = 0,
              o = 0,
              s = !0;
            if (this.monthView) switch (i) {
              case t.Key.Left:
                n = -1;
                break;
              case t.Key.Right:
                n = 1;
                break;
              case t.Key.Up:
                n = -7;
                break;
              case t.Key.Down:
                n = 7;
                break;
              case t.Key.PageDown:
                o = e.altKey ? 12 : 1;
                break;
              case t.Key.PageUp:
                o = e.altKey ? -12 : -1;
                break;
              case t.Key.Home:
              case t.Key.End:
                if (this._canChangeValue()) {
                  a = this.value || this.displayMonth;
                  (r = this._getValidDate(a, i == t.Key.Home)) && (this.value = t.DateTime.fromDateTime(r, this.value))
                }
                break;
              default:
                s = !1
            } else switch (i) {
              case t.Key.Left:
                o = -1;
                break;
              case t.Key.Right:
                o = 1;
                break;
              case t.Key.Up:
                o = -4;
                break;
              case t.Key.Down:
                o = 4;
                break;
              case t.Key.PageDown:
                o = e.altKey ? 120 : 12;
                break;
              case t.Key.PageUp:
                o = e.altKey ? -120 : -12;
                break;
              case t.Key.Home:
                o = this.value ? -this.value.getMonth() : 0;
                break;
              case t.Key.End:
                o = this.value ? 11 - this.value.getMonth() : 0;
                break;
              case t.Key.Enter:
                this._monthMode() ? s = !1 : this.monthView = !0;
                break;
              default:
                s = !1
            }
            if (this._canChangeValue() && (n || o)) {
              var r = this.value;
              if (r ? (r = t.DateTime.addDays(r, n), r = t.DateTime.addMonths(r, o)) : r = this._getValidDate(new Date, !0), o && !this._valid(r))
                for (var a = r.getMonth(), l = 1; l < 31 && !this._valid(r); l++) {
                  var h = t.DateTime.addDays(r, +l),
                    c = t.DateTime.addDays(r, -l);
                  this._valid(h) && h.getMonth() == a ? r = h : this._valid(c) && c.getMonth() == a && (r = c)
                }
              for (l = 0; l < 31 && !this._valid(r); l++) r = t.DateTime.addDays(r, n > 0 || o > 0 ? 1 : -1);
              this.value = r
            }
            s && e.preventDefault()
          }
        }
      }, o.prototype._getMonth = function(e) {
        return e || (e = t.DateTime.newDate()), t.DateTime.newDate(e.getFullYear(), e.getMonth(), 1)
      }, o.prototype._monthMode = function() {
        return this.selectionMode == i.Month
      }, o.prototype._navigate = function(e) {
        var i = this.monthView,
          n = this.displayMonth;
        switch (e) {
          case 0:
            var o = t.DateTime.newDate();
            this._canChangeValue() && (this.value = i ? t.DateTime.fromDateTime(o, this.value) : this._getMonth(o)), n = this._getMonth(o);
            break;
          case 1:
            n = t.DateTime.addMonths(n, i ? 1 : 12);
            break;
          case -1:
            n = t.DateTime.addMonths(n, i ? -1 : -12)
        }
        this.displayMonth = n
      }, o.controlTemplate = '<div class="wj-content wj-calendar-outer"><div wj-part="tbl-header" class="wj-calendar-header"><div wj-part="btn-month" class="wj-month-select" role="button"><span wj-part="span-month"></span> <span class="wj-glyph-down"></span></div><div class="wj-btn-group"><button wj-part="btn-prev" class="wj-btn wj-btn-default" tabindex="-1"><span class="wj-glyph-left"></span></button><button wj-part="btn-today" class="wj-btn wj-btn-default" tabindex="-1"><span class="wj-glyph-circle"></span></button><button wj-part="btn-next" class="wj-btn wj-btn-default" tabindex="-1"><span class="wj-glyph-right"></span></button></div></div><table wj-part="tbl-month" class="wj-calendar-month" role="grid"></table><table wj-part="tbl-year" class="wj-calendar-year" role="grid" style="display:none"></table></div>', o
    }(t.Control);
    e.Calendar = n
  }(t.input || (t.input = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function(e) {
      function i(n, o) {
        var s = e.call(this, n) || this;
        s._hsb = [.5, 1, 1], s._alpha = 1, s.valueChanged = new t.Event;
        var r = s.getTemplate();
        s.applyTemplate("wj-control wj-content wj-colorpicker", r, {
          _eSB: "div-sb",
          _eHue: "div-hue",
          _eAlpha: "div-alpha",
          _ePreview: "div-pv",
          _ePal: "div-pal",
          _eText: "div-text"
        }), s._palette = "#FFF,#000, #F00,#FFC000,#FFFF00,#92D050,#00B050,#00B0F0,#0070C0,#7030A0".split(","), s._updatePalette(), s._eHue.style.backgroundImage = "url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAD4CAIAAACi6hsPAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAYdEVYdFNvZnR3YXJlAHBhaW50Lm5ldCA0LjAuM4zml1AAAAGvSURBVDhPXdBPaM9xHMfxz4pWaxcmtoOhpdXSVpiyHWxqmVpDjaU5rK34XfypjTJ/p+ZPay6jhsOsRrKwaJElf9IQq03WIkv4FeMwMq221tfje1ByeFzfvd7PEKWGEKWTQRZLySWfVRRTQjmVbKWGOhLsZT+HaeY0bbTTQSfdXOcWffTzmAFeMcwoYyT5ygS/mA5hNgphip98J8kHRnnNSwZ4yH1uc4OrdHGR87RximYO0cgedlLLdqqoYAPrWMtKVrCcJSxiPmnMJUQp/Bsyk2xyyKOAQooopYwKtlDNDur5G7SBJo7RQiv/B+2hl3s84CkvGGKEOOYnxolj/mYmhBmDJ5ngCx95xxsGecYj4pB3iENeoZMO2mmlhaMcpIE4ZII6aqhmM3HMMkooopB88sghm0wySCeVlCjMCVFIYx4LWUwOeRSwhmLWU84mqqihll3sppEmjnOSs5zjEl1c4yZ99POE5wwxwns+840fTDFLFKaZZIJxkozxlmEGGSC+GF++Sy89dHOZC8Rr4lVnOMERDrCPBPXEX22jko2UEn+/mnxyWUYWC0gnNUQh/AEc0HJs6cex0gAAAABJRU5ErkJggg==)", s._eHue.style.backgroundSize = "contain", navigator.appVersion.indexOf("MSIE 9") > -1 && (s._eSB.children[0].style.filter = "progid:DXImageTransform.Microsoft.gradient(startColorstr=#ffffffff,endColorstr=#00ffffff,GradientType=1)", s._eSB.children[1].style.filter = "progid:DXImageTransform.Microsoft.gradient(startColorstr=#00000000,endColorstr=#ff000000,GradientType=0)"), r = i._tplCursor, s._cSB = t.createElement(r), s._cHue = t.createElement(r), s._cHue.style.width = "100%", s._cAlpha = t.createElement(r), s._cAlpha.style.height = "100%", s._eSB.appendChild(s._cSB), s._eHue.appendChild(s._cHue), s._eAlpha.appendChild(s._cAlpha), s.addEventListener(s.hostElement, "mousedown", function(t) {
          document.addEventListener("mousemove", a), document.addEventListener("mouseup", l), s._mouseDown(t)
        });
        var a = function(t) {
            s._mouseMove(t)
          },
          l = function(t) {
            document.removeEventListener("mousemove", a), document.removeEventListener("mouseup", l), s._mouseUp(t)
          };
        return s.addEventListener(s.hostElement, "click", function(e) {
          var i = e.target;
          if (i && "DIV" == i.tagName && t.contains(s._ePal, i)) {
            var n = i.style.backgroundColor;
            n && (s.value = new t.Color(n).toString())
          }
        }), s.value = "#ffffff", s.initialize(o), s._updatePanels(), s
      }
      return __extends(i, e), Object.defineProperty(i.prototype, "showAlphaChannel", {
        get: function() {
          return "none" != this._eAlpha.parentElement.style.display
        },
        set: function(e) {
          this._eAlpha.parentElement.style.display = t.asBoolean(e) ? "" : "none"
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "showColorString", {
        get: function() {
          return "none" != this._eText.style.display
        },
        set: function(e) {
          this._eText.style.display = t.asBoolean(e) ? "" : "none"
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "value", {
        get: function() {
          return this._value
        },
        set: function(e) {
          if (e != this.value) {
            this._value = t.asString(e), this._eText.innerText = this._value;
            var i = new t.Color(this._value),
              n = i.getHsb();
            this._hsb[0] == n[0] && this._hsb[1] == n[1] && this._hsb[2] == n[2] && this._alpha == i.a || (0 == n[2] ? (n[0] = this._hsb[0], n[1] = this._hsb[1]) : 0 == n[1] && (n[0] = this._hsb[0]), this._hsb = n, this._alpha = i.a, this.onValueChanged())
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "palette", {
        get: function() {
          return this._palette
        },
        set: function(e) {
          e = t.asArray(e);
          for (var i = 0; i < e.length && i < this._palette.length; i++) {
            var n = t.asString(e[i]);
            this._palette[i] = n
          }
          this._updatePalette()
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.onValueChanged = function(t) {
        this._updatePanels(), this.valueChanged.raise(this, t)
      }, i.prototype._mouseDown = function(t) {
        this._htDown = this._getTargetPanel(t), this._htDown && (t.preventDefault(), this.focus(), this._mouseMove(t))
      }, i.prototype._mouseMove = function(e) {
        if (this._htDown) {
          var i = this._htDown.getBoundingClientRect();
          this._htDown == this._eHue ? (this._hsb[0] = t.clamp((e.clientY - i.top) / i.height, 0, .99), this._updateColor()) : this._htDown == this._eSB ? (this._hsb[1] = t.clamp((e.clientX - i.left) / i.width, 0, 1), this._hsb[2] = t.clamp(1 - (e.clientY - i.top) / i.height, 0, 1), this._updateColor()) : this._htDown == this._eAlpha && (this._alpha = t.clamp((e.clientX - i.left) / i.width, 0, 1), this._updateColor())
        }
      }, i.prototype._mouseUp = function(t) {
        this._htDown = null
      }, i.prototype._updateColor = function() {
        var e = t.Color.fromHsb(this._hsb[0], this._hsb[1], this._hsb[2], this._alpha);
        this.value = e.toString(), this._updatePanels()
      }, i.prototype._updatePalette = function() {
        var e = new t.Color("#fff"),
          i = new t.Color("#000");
        this._ePal.innerHTML = "";
        for (var n = 0; n < this._palette.length; n++) {
          var o = t.createElement('<div style="float:left;width:10%;box-sizing:border-box;padding:1px">'),
            s = new t.Color(this._palette[n]),
            r = s.getHsb();
          o.appendChild(this._makePalEntry(s, 4));
          for (var a = 0; a < 5; a++) {
            if (0 == r[1]) {
              var l = .1 * a + (r[2] > .5 ? .05 : .55);
              s = t.Color.interpolate(e, i, l)
            } else s = t.Color.fromHsb(r[0], .1 + .2 * a, 1 - .1 * a);
            o.appendChild(this._makePalEntry(s, 0))
          }
          this._ePal.appendChild(o)
        }
      }, i.prototype._makePalEntry = function(e, i) {
        var n = document.createElement("div");
        return t.setCss(n, {
          cursor: "pointer",
          backgroundColor: e.toString(),
          marginBottom: i || ""
        }), n.innerHTML = "&nbsp", n
      }, i.prototype._updatePanels = function() {
        var e = t.Color.fromHsb(this._hsb[0], 1, 1, 1),
          i = t.Color.fromHsb(this._hsb[0], this._hsb[1], this._hsb[2], 1);
        this._eSB.style.backgroundColor = e.toString(), this._eAlpha.style.background = "linear-gradient(to right, transparent 0%, " + i.toString() + " 100%)", navigator.appVersion.indexOf("MSIE 9") > -1 && (this._eAlpha.style.filter = "progid:DXImageTransform.Microsoft.gradient(startColorstr=#00000000,endColorstr=" + i.toString() + ", GradientType = 1)"), this._ePreview.style.backgroundColor = this.value, this._cHue.style.top = (100 * this._hsb[0]).toFixed(0) + "%", this._cSB.style.left = (100 * this._hsb[1]).toFixed(0) + "%", this._cSB.style.top = (100 - 100 * this._hsb[2]).toFixed(0) + "%", this._cAlpha.style.left = (100 * this._alpha).toFixed(0) + "%"
      }, i.prototype._getTargetPanel = function(e) {
        var i = e.target;
        return t.contains(this._eSB, i) ? this._eSB : t.contains(this._eHue, i) ? this._eHue : t.contains(this._eAlpha, i) ? this._eAlpha : null
      }, i.controlTemplate = '<div style="position:relative;width:100%;height:100%"><div style="float:left;width:50%;height:100%;box-sizing:border-box;padding:2px"><div wj-part="div-pal"><div style="float:left;width:10%;box-sizing:border-box;padding:2px"><div style="background-color:black;width:100%">&nbsp;</div><div style="height:6px"></div></div></div><div wj-part="div-text" style="position:absolute;bottom:0px;display:none"></div></div><div style="float:left;width:50%;height:100%;box-sizing:border-box;padding:2px"><div wj-part="div-sb" class="wj-colorbox" style="float:left;width:89%;height:89%"><div style="position:absolute;width:100%;height:100%;background:linear-gradient(to right, white 0%,transparent 100%)"></div><div style="position:absolute;width:100%;height:100%;background:linear-gradient(to top, black 0%,transparent 100%)"></div></div><div style="float:left;width:1%;height:89%"></div><div style="float:left;width:10%;height:89%"><div wj-part="div-hue" class="wj-colorbox"></div></div><div style="float:left;width:89%;height:1%"></div><div style="float:left;width:89%;height:10%"><div style="width:100%;height:100%;background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAICAIAAABLbSncAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAYdEVYdFNvZnR3YXJlAHBhaW50Lm5ldCA0LjAuM4zml1AAAAAcSURBVBhXY/iPBBYgAWpKQGkwgMqDAdUk/v8HAM7Mm6GatDUYAAAAAElFTkSuQmCC)"><div wj-part="div-alpha" class="wj-colorbox"></div></div></div><div style="float:left;width:1%;height:10%"></div><div style="float:left;width:10%;height:10%"><div wj-part="div-pv" class="wj-colorbox" style="position:static"></div></div></div></div>', i._tplCursor = '<div style="position:absolute;left:50%;top:50%;width:7px;height:7px;transform:translate(-50%,-50%);border:2px solid #f0f0f0;border-radius:50px;box-shadow:0px 0px 4px 2px #0f0f0f"></div>', i
    }(t.Control);
    e.ColorPicker = i
  }(t.input || (t.input = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function(e) {
      function i(i, n) {
        var o = e.call(this, i) || this;
        o._pathDisplay = new t.Binding(null), o._pathValue = new t.Binding(null), o._pathChecked = new t.Binding(null), o._html = !1, o._shGroups = !1, o._checkedItems = [], o._itemRole = "option", o._search = "", o._fmtItemHandlers = 0, o._itemCount = 0, o.selectedIndexChanged = new t.Event, o.itemsChanged = new t.Event, o.loadingItems = new t.Event, o.loadedItems = new t.Event, o.itemChecked = new t.Event, o.checkedItemsChanged = new t.Event, o.formatItem = new t.Event, o.applyTemplate("wj-control wj-content wj-listbox", null, null);
        var s = o.hostElement;
        return t.setAttribute(s, "role", "listbox", !0), "SELECT" == o._orgTag && o._initFromSelect(o.hostElement), o.addEventListener(s, "click", o._click.bind(o)), o.addEventListener(s, "keydown", o._keydown.bind(o)), o.addEventListener(s, "keypress", o._keypress.bind(o)), o.addEventListener(s, "wheel", function(t) {
          s.scrollHeight > s.offsetHeight && (t.deltaY < 0 && 0 == s.scrollTop || t.deltaY > 0 && s.scrollTop + s.offsetHeight >= s.scrollHeight) && t.preventDefault()
        }), o.initialize(n), o
      }
      return __extends(i, e), Object.defineProperty(i.prototype, "itemsSource", {
        get: function() {
          return this._items
        },
        set: function(e) {
          this._items != e && (this._cv && (this._cv.currentChanged.removeHandler(this._cvCurrentChanged, this), this._cv.collectionChanged.removeHandler(this._cvCollectionChanged, this), this._cv = null), this._items = e, this._cv = t.asCollectionView(e), null != this._cv && (this._cv.currentChanged.addHandler(this._cvCurrentChanged, this), this._cv.collectionChanged.addHandler(this._cvCollectionChanged, this)), this._populateList(), this.onItemsChanged(), this.onSelectedIndexChanged())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "collectionView", {
        get: function() {
          return this._cv
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "showGroups", {
        get: function() {
          return this._shGroups
        },
        set: function(e) {
          e != this._shGroups && (this._shGroups = t.asBoolean(e), this._populateList())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "isContentHtml", {
        get: function() {
          return this._html
        },
        set: function(e) {
          e != this._html && (this._html = t.asBoolean(e), this._populateList())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "itemFormatter", {
        get: function() {
          return this._itemFormatter
        },
        set: function(e) {
          e != this._itemFormatter && (this._itemFormatter = t.asFunction(e), this._populateList())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "displayMemberPath", {
        get: function() {
          return this._pathDisplay.path
        },
        set: function(e) {
          e != this.displayMemberPath && (this._pathDisplay.path = t.asString(e), this._populateList())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "selectedValuePath", {
        get: function() {
          return this._pathValue.path
        },
        set: function(e) {
          this._pathValue.path = t.asString(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "checkedMemberPath", {
        get: function() {
          return this._pathChecked.path
        },
        set: function(e) {
          e != this.checkedMemberPath && (this._pathChecked.path = t.asString(e), this._populateList())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "itemRole", {
        get: function() {
          return this._itemRole
        },
        set: function(e) {
          e != this.itemRole && (this._itemRole = t.asString(e), this._populateList())
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.getDisplayValue = function(e) {
        var i = null;
        e > -1 && t.hasItems(this._cv) && (i = this._cv.items[e], this._pathDisplay && (i = this._pathDisplay.getValue(i)));
        var n = null != i ? i.toString() : "";
        return this._itemFormatter && (n = this._itemFormatter(e, n)), n
      }, i.prototype.getDisplayText = function(t) {
        var e = this._getChild(t);
        return null != e ? e.textContent : ""
      }, i.prototype.isItemEnabled = function(e) {
        var i = this._getChild(e);
        return null != i && !i.hasAttribute("disabled") && !t.hasClass(i, "wj-state-disabled")
      }, Object.defineProperty(i.prototype, "selectedIndex", {
        get: function() {
          return this._cv ? this._cv.currentPosition : -1
        },
        set: function(e) {
          this._cv && this._cv.moveCurrentToPosition(t.asNumber(e))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "selectedItem", {
        get: function() {
          return this._cv ? this._cv.currentItem : null
        },
        set: function(t) {
          this._cv && this._cv.moveCurrentTo(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "selectedValue", {
        get: function() {
          var t = this.selectedItem;
          return t && this.selectedValuePath && (t = this._pathValue.getValue(t)), t
        },
        set: function(e) {
          var i = this.selectedValuePath,
            n = -1;
          if (this._cv) {
            for (var o = 0; o < this._cv.items.length; o++) {
              var s = this._cv.items[o],
                r = i ? this._pathValue.getValue(s) : s;
              if (r === e || t.DateTime.equals(r, e)) {
                n = o;
                break
              }
            }
            this.selectedIndex = n
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "maxHeight", {
        get: function() {
          var t = this.hostElement;
          return t ? parseFloat(t.style.maxHeight) : null
        },
        set: function(e) {
          var i = this.hostElement;
          i && (i.style.maxHeight = t.asNumber(e) + "px")
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.showSelection = function(e) {
        var i = this.selectedIndex,
          n = this.hostElement,
          o = n.children;
        i = this._getElementIndex(i);
        for (var s = 0; s < o.length; s++) {
          var r = o[s],
            a = s == i;
          t.toggleClass(r, "wj-state-selected", a), t.setAttribute(r, "aria-selected", a), r.tabIndex = a ? this._orgTabIndex : -1
        }
        if (i > -1 && i < o.length) {
          var l = (r = o[i]).getBoundingClientRect(),
            h = n.getBoundingClientRect(),
            c = 0;
          if (this._shGroups) {
            var u = o[0];
            t.hasClass(u, "wj-header") && (c = u.offsetHeight)
          }
          l.bottom > h.bottom ? n.scrollTop += l.bottom - h.bottom : l.top < h.top + c && (n.scrollTop -= h.top + c - l.top)
        }
        i > -1 && (null == e && (e = this.containsFocus()), e && (r = o[i]) instanceof HTMLElement && !t.contains(r, t.getActiveElement()) && r.focus()), n.tabIndex = i < 0 ? this._orgTabIndex : -1
      }, i.prototype.loadList = function() {
        this._populateList()
      }, i.prototype.getItemChecked = function(e) {
        var i = this._cv.items[e],
          n = this._pathChecked;
        if (t.isObject(i) && n.path) return n.getValue(i);
        var o = this._getCheckbox(e);
        return !!o && o.checked
      }, i.prototype.setItemChecked = function(t, e) {
        this._setItemChecked(t, e, !0)
      }, i.prototype.toggleItemChecked = function(t) {
        this.setItemChecked(t, !this.getItemChecked(t))
      }, Object.defineProperty(i.prototype, "checkedItems", {
        get: function() {
          if (this._checkedItems.splice(0, this._checkedItems.length), this._cv)
            for (var t = 0; t < this._cv.items.length; t++) this.getItemChecked(t) && this._checkedItems.push(this._cv.items[t]);
          return this._checkedItems
        },
        set: function(e) {
          var i = this._cv,
            n = this.hostElement,
            o = t.asArray(e, !1),
            s = !1;
          if (i && o) {
            for (var r = i.currentPosition, a = n.scrollTop, l = 0; l < i.items.length; l++) {
              var h = i.items[l];
              this._setItemChecked(l, o.indexOf(h) > -1, !1) && (s = !0)
            }
            i.moveCurrentToPosition(r), n.scrollTop = a, s && this.onCheckedItemsChanged()
          }
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.indexOf = function(e) {
        if (this._shGroups && t.hasClass(e, "wj-header")) return -1;
        for (var i = this.hostElement.children, n = 0, o = 0; n < i.length; n++) {
          var s = i[n];
          if (t.contains(i[n], e)) return o;
          this._shGroups && t.hasClass(s, "wj-header") || o++
        }
        return -1
      }, i.prototype.onSelectedIndexChanged = function(t) {
        this.selectedIndexChanged.raise(this, t)
      }, i.prototype.onItemsChanged = function(t) {
        this.itemsChanged.raise(this, t)
      }, i.prototype.onLoadingItems = function(t) {
        this.loadingItems.raise(this, t)
      }, i.prototype.onLoadedItems = function(t) {
        this.loadedItems.raise(this, t)
      }, i.prototype.onItemChecked = function(t) {
        this.itemChecked.raise(this, t)
      }, i.prototype.onCheckedItemsChanged = function(t) {
        this.checkedItemsChanged.raise(this, t)
      }, i.prototype.onFormatItem = function(t) {
        this.formatItem.raise(this, t)
      }, i.prototype.refresh = function(t) {
        void 0 === t && (t = !0), e.prototype.refresh.call(this, t), (this._cv ? this._cv.items.length : 0) == this._itemCount && this.formatItem.handlerCount == this._fmtItemHandlers || (this._e.offsetHeight || this._e.offsetWidth) && (this._fmtItemHandlers = this.formatItem.handlerCount, this._populateList())
      }, i.prototype._getChild = function(t) {
        return t = this._getElementIndex(t), this.hostElement.children[t]
      }, i.prototype._getElementIndex = function(e) {
        if (this._shGroups)
          for (var i = this.hostElement.children, n = 0; n <= e && n < i.length; n++) {
            var o = i[n];
            t.hasClass(o, "wj-header") && e++
          }
        return e
      }, i.prototype._setItemChecked = function(e, i, n) {
        void 0 === n && (n = !0);
        var o = this._cv.items[e],
          s = this._pathChecked,
          r = !1;
        if (t.isObject(o) && s.path) {
          var a = t.tryCast(this._cv, "IEditableCollectionView");
          s.getValue(o) != i && (r = !0, this._checking = !0, a ? (a.editItem(o), s.setValue(o, i), a.commitEdit()) : (s.setValue(o, i), this._cv.refresh()), this._checking = !1)
        } else r = !0;
        var l = this._getCheckbox(e);
        if (l) {
          l.checked = i;
          var h = t.closest(l, ".wj-listbox-item");
          h && t.toggleClass(h, "wj-state-checked", i)
        }
        return n && r && (this.onItemChecked(), this.onCheckedItemsChanged()), r
      }, i.prototype._cvCollectionChanged = function(t, e) {
        this._checking || (this._populateList(), this.onItemsChanged())
      }, i.prototype._cvCurrentChanged = function(t, e) {
        this._checking || (this.showSelection(), this.onSelectedIndexChanged())
      }, i.prototype._populateList = function() {
        var e = this.hostElement;
        if (this._itemCount = this._cv ? this._cv.items.length : 0, e) {
          var i = this.containsFocus(),
            o = -1,
            s = void 0;
          this.onLoadingItems(), e.textContent = "", e.scrollTop = 0;
          var r = this._cv;
          if (r) {
            var a = [],
              l = 0,
              h = void 0;
            if (o = r.currentPosition, this._shGroups && r.groups && r.groups.length) {
              s = {};
              for (var c = 0; c < r.groups.length; c++) {
                var u = r.groups[c];
                s[l] = u, h = this._createHeaderItem(u), a.push(h);
                for (_ = 0; _ < u.items.length; _++, l++) h = this._createItem(l), a.push(h)
              }
            } else
              for (_ = 0; _ < r.items.length; _++, l++) h = this._createItem(l), a.push(h);
            e.innerHTML = a.join("")
          }
          if (this.formatItem.hasHandlers && r)
            for (var p = e.children, d = r.items, l = 0, f = new n(0, null, null), _ = 0; _ < p.length; _++) f._item = p[_], this._shGroups && t.hasClass(f._item, "wj-header") ? (f._index = -1, f._data = s[l]) : (f._index = l, f._data = d[l], l++), this.onFormatItem(f);
          0 == e.children.length && e.appendChild(document.createElement("div")), e.tabIndex = o < 0 ? this._orgTabIndex : -1, i ? (o >= 0 ? e.children[this._getElementIndex(o)] : e).focus() : o > 0 && this.showSelection(), this.onLoadedItems()
        }
      }, i.prototype._createItem = function(e) {
        var i = this._cv.items[e],
          n = e == this._cv.currentPosition,
          o = this.getDisplayValue(e);
        1 != this._html && (o = t.escapeHtml(o));
        var s = !1;
        this.checkedMemberPath && (o = '<label><input tabindex="-1" type="checkbox"' + ((s = this._pathChecked.getValue(i)) ? " checked" : "") + "> " + o + "</label>");
        var r = "wj-listbox-item";
        if (n && (r += " wj-state-selected"), s && (r += " wj-state-checked"), this._html) {
          var a = ["wj-separator", "wj-state-disabled"];
          if (o.indexOf(a[0]) > -1 || o.indexOf(a[1]) > -1) {
            var l = t.createElement(o);
            a.forEach(function(e) {
              t.hasClass(l, e) && (r += " " + e)
            })
          }
        }
        return '<div class="' + r + '" role="' + this.itemRole + '" aria-selected="' + n + '" tabindex="' + (n ? this._orgTabIndex : -1) + '">' + o + "</div>"
      }, i.prototype._createHeaderItem = function(e) {
        return '<div class="wj-listbox-item wj-header wj-state-disabled" role="presentation" tabindex="-1">' + t.escapeHtml(e.name) + "</div>"
      }, i.prototype._click = function(t) {
        if (0 == t.button && !t.defaultPrevented) {
          var e = this.indexOf(t.target);
          if (e > -1 && (this.selectedIndex = e, this.checkedMemberPath)) {
            var i = this._getCheckbox(e),
              n = this.hostElement.children[e];
            i != t.target && t.target != n || (n.focus(), this.setItemChecked(e, i.checked))
          }
        }
      }, i.prototype._keydown = function(e) {
        var i = this.selectedIndex;
        this.hostElement.children;
        if (!e.defaultPrevented) {
          if (65 == e.keyCode && (e.ctrlKey || e.metaKey) && this.checkedMemberPath && t.hasItems(this.collectionView)) return this.checkedItems = this.getItemChecked(0) ? [] : this.collectionView.items, void e.preventDefault();
          if (!(e.ctrlKey || e.shiftKey || e.altKey || e.metaKey)) switch (e.keyCode) {
            case t.Key.Down:
              e.preventDefault(), this._selectNext();
              break;
            case t.Key.Up:
              e.preventDefault(), this._selectPrev();
              break;
            case t.Key.Home:
              e.preventDefault(), this._selectFirst();
              break;
            case t.Key.End:
              e.preventDefault(), this._selectLast();
              break;
            case t.Key.PageDown:
              e.preventDefault(), this._selectNextPage();
              break;
            case t.Key.PageUp:
              e.preventDefault(), this._selectPrevPage();
              break;
            case t.Key.Space:
              if (this.checkedMemberPath && i > -1) {
                var n = this._getCheckbox(i);
                n && this.isItemEnabled(i) && (this.setItemChecked(i, !n.checked), e.preventDefault())
              }
          }
        }
      }, i.prototype._keypress = function(e) {
        var i = this;
        if (!e.defaultPrevented && !(e.target instanceof HTMLInputElement) && (e.charCode > 32 || 32 == e.charCode && this._search)) {
          e.preventDefault(), this._search += String.fromCharCode(e.charCode).toLowerCase(), this._toSearch && clearTimeout(this._toSearch), this._toSearch = setTimeout(function() {
            i._toSearch = null, i._search = ""
          }, t.Control._SEARCH_DELAY);
          var n = this._findNext();
          n < 0 && this._search.length > 1 && (this._search = this._search[this._search.length - 1], n = this._findNext()), n > -1 && (this.selectedIndex = n)
        }
      }, i.prototype._selectNext = function() {
        for (var t = this.hostElement.children.length, e = this.selectedIndex + 1; e < t; e++)
          if (this.isItemEnabled(e)) return this.selectedIndex = e, !0;
        return !1
      }, i.prototype._selectPrev = function() {
        for (var t = this.selectedIndex - 1; t >= 0; t--)
          if (this.isItemEnabled(t)) return this.selectedIndex = t, !0;
        return !1
      }, i.prototype._selectFirst = function() {
        for (var t = this.hostElement.children.length, e = 0; e < t; e++)
          if (this.isItemEnabled(e)) return this.selectedIndex = e, !0;
        return !1
      }, i.prototype._selectLast = function() {
        for (var t = this.hostElement.children.length - 1; t >= 0; t--)
          if (this.isItemEnabled(t)) return this.selectedIndex = t, !0;
        return !1
      }, i.prototype._selectNextPage = function() {
        for (var t = this.hostElement, e = t.offsetHeight, i = t.children, n = 0, o = this.selectedIndex + 1; o < this._cv.items.length; o++) {
          var s = i[o].scrollHeight;
          if (n + s > e && this.isItemEnabled(o)) return this.selectedIndex = o, !0;
          n += s
        }
        return this._selectLast()
      }, i.prototype._selectPrevPage = function() {
        for (var t = this.hostElement, e = t.offsetHeight, i = t.children, n = 0, o = this.selectedIndex - 1; o > 0; o--) {
          var s = i[o].scrollHeight;
          if (n + s > e && this.isItemEnabled(o)) return this.selectedIndex = o, !0;
          n += s
        }
        return this._selectFirst()
      }, i.prototype._findNext = function() {
        if (this.hostElement) {
          var t = this.hostElement.childElementCount,
            e = this.selectedIndex;
          (e < 0 || 1 == this._search.length) && e++;
          for (var i = 0; i < t; i++) {
            var n = (e + i) % t;
            if (0 == this.getDisplayText(n).trim().toLowerCase().indexOf(this._search) && this.isItemEnabled(n)) return n
          }
        }
        return -1
      }, i.prototype._getCheckbox = function(t) {
        var e = this.hostElement,
          i = e ? e.children : null;
        return t = this._getElementIndex(t), e && t > -1 && t < i.length ? i[t].querySelector("input[type=checkbox]") : null
      }, i.prototype._initFromSelect = function(t) {
        for (var e = t.children, i = [], n = -1, o = 0; o < e.length; o++) {
          var s = e[o];
          "OPTION" == s.tagName && (s.hasAttribute("selected") && (n = i.length), s.innerHTML ? i.push({
            hdr: s.innerHTML,
            val: s.getAttribute("value"),
            cmdParam: s.getAttribute("cmd-param")
          }) : i.push({
            hdr: '<div class="wj-separator"></div>'
          }), t.removeChild(s), o--)
        }
        i && (this.displayMemberPath = "hdr", this.selectedValuePath = "val", this.itemsSource = i, this.selectedIndex = n)
      }, i
    }(t.Control);
    e.ListBox = i;
    var n = function(e) {
      function i(i, n, o) {
        var s = e.call(this) || this;
        return s._index = t.asNumber(i), s._data = n, s._item = t.asType(o, HTMLElement, !0), s
      }
      return __extends(i, e), Object.defineProperty(i.prototype, "index", {
        get: function() {
          return this._index
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "data", {
        get: function() {
          return this._data
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "item", {
        get: function() {
          return this._item
        },
        enumerable: !0,
        configurable: !0
      }), i
    }(t.EventArgs);
    e.FormatItemEventArgs = n
  }(t.input || (t.input = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function(i) {
      function n(e, n) {
        var o = i.call(this, e) || this;
        o._editable = !1, o._delKey = 0, o._composing = !1, o._pathHdr = new t.Binding(null), o._bsCollapse = !0, o.itemsSourceChanged = new t.Event, o.selectedIndexChanged = new t.Event;
        var s = o.hostElement;
        return t.addClass(s, "wj-combobox"), o.dropDown.id = t.getUniqueId(s.id + "_dropdown"), o.autoExpandSelection = !1, o.addEventListener(o._tbx, "compositionstart", function() {
          o._composing = !0
        }), o.addEventListener(o._tbx, "compositionend", function() {
          o._composing = !1, setTimeout(function() {
            o._setText(o.text, !0)
          })
        }), o.addEventListener(s, "wheel", o._wheel.bind(o)), "SELECT" == o._orgTag && o._lbx._initFromSelect(s), o._lbx.loadedItems.addHandler(function(t) {
          o.selectedIndex > -1 && (o.selectedIndex = o._lbx.selectedIndex)
        }), o.isRequired = !0, o.initialize(n), o
      }
      return __extends(n, i), Object.defineProperty(n.prototype, "itemsSource", {
        get: function() {
          return this._lbx.itemsSource
        },
        set: function(t) {
          this._lbx.itemsSource != t && (this._lbx.itemsSource = t, this.onItemsSourceChanged()), this._updateBtn()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "collectionView", {
        get: function() {
          return this._lbx.collectionView
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "showGroups", {
        get: function() {
          return this._lbx.showGroups
        },
        set: function(t) {
          this._lbx.showGroups = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "displayMemberPath", {
        get: function() {
          return this._lbx.displayMemberPath
        },
        set: function(t) {
          this._lbx.displayMemberPath = t;
          var e = this.getDisplayText();
          this.text != e && this._setText(e, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "headerPath", {
        get: function() {
          return this._pathHdr.path
        },
        set: function(e) {
          this._pathHdr.path = t.asString(e);
          var i = this.getDisplayText();
          this.text != i && this._setText(i, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "selectedValuePath", {
        get: function() {
          return this._lbx.selectedValuePath
        },
        set: function(t) {
          this._lbx.selectedValuePath = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "isContentHtml", {
        get: function() {
          return this._lbx.isContentHtml
        },
        set: function(e) {
          if (e != this.isContentHtml) {
            this._lbx.isContentHtml = t.asBoolean(e);
            var i = this.getDisplayText();
            this.text != i && this._setText(i, !0)
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "itemFormatter", {
        get: function() {
          return this._lbx.itemFormatter
        },
        set: function(e) {
          this._lbx.itemFormatter = t.asFunction(e), this.selectedIndex = this._lbx.selectedIndex
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "formatItem", {
        get: function() {
          return this.listBox.formatItem
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "selectedIndex", {
        get: function() {
          return this._lbx.selectedIndex
        },
        set: function(t) {
          t != this.selectedIndex && (this._lbx.selectedIndex = t), t = this.selectedIndex;
          var e = this.getDisplayText(t);
          this.text != e && this._setText(e, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "selectedItem", {
        get: function() {
          return this._lbx.selectedItem
        },
        set: function(t) {
          this._lbx.selectedItem = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "selectedValue", {
        get: function() {
          return this._lbx.selectedValue
        },
        set: function(t) {
          this._lbx.selectedValue = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "isEditable", {
        get: function() {
          return this._editable
        },
        set: function(e) {
          this._editable = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "maxDropDownHeight", {
        get: function() {
          return this._lbx.maxHeight
        },
        set: function(e) {
          this._lbx.maxHeight = t.asNumber(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "maxDropDownWidth", {
        get: function() {
          var t = this._dropDown;
          return parseInt(t.style.maxWidth)
        },
        set: function(e) {
          var i = this._dropDown;
          i.style.maxWidth = t.asNumber(e) + "px", i.style.minWidth = ""
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.getDisplayText = function(e) {
        if (void 0 === e && (e = this.selectedIndex), this.headerPath && e > -1 && t.hasItems(this.collectionView)) {
          var i = this.collectionView.items[e],
            n = i ? this._pathHdr.getValue(i) : null;
          return n = null != n ? n.toString() : "", this.isContentHtml && (this._cvt || (this._cvt = document.createElement("div")), this._cvt.innerHTML = n, n = this._cvt.textContent), n.trim()
        }
        return this._lbx.getDisplayText(e).trim()
      }, n.prototype.indexOf = function(e, i) {
        var n = this.collectionView;
        if (t.hasItems(n) && null != e) {
          var o = this.selectedIndex;
          if (i && e == this.getDisplayText(o)) return o;
          e = e.toString().toLowerCase();
          for (var s = 0; s < n.items.length; s++)
            if (this.listBox.isItemEnabled(s)) {
              var r = this.getDisplayText(s).toLowerCase();
              if (i) {
                if (r == e) return s
              } else if (e && 0 == r.indexOf(e)) return s
            }
        }
        return -1
      }, Object.defineProperty(n.prototype, "listBox", {
        get: function() {
          return this._lbx
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.onItemsSourceChanged = function(t) {
        this.itemsSourceChanged.raise(this, t)
      }, n.prototype.onSelectedIndexChanged = function(t) {
        this._updateBtn(), this.selectedIndexChanged.raise(this, t)
      }, n.prototype.refresh = function(e) {
        void 0 === e && (e = !0), i.prototype.refresh.call(this, e), t.hasItems(this.collectionView) && (this._lbx.refresh(), this.selectedIndex > -1 && (this.selectedIndex = this._lbx.selectedIndex))
      }, n.prototype.onLostFocus = function(e) {
        this._composing && (this._composing = !1, this._setText(this.text, !0)), this.isEditable && this.isRequired && !this.text && t.hasItems(this.collectionView) && (this.selectedIndex = 0), i.prototype.onLostFocus.call(this, e)
      }, n.prototype.onIsDroppedDownChanging = function(e) {
        return this.isDroppedDown || t.hasItems(this.collectionView) ? i.prototype.onIsDroppedDownChanging.call(this, e) : (e.cancel = !0, !1)
      }, n.prototype.onIsDroppedDownChanged = function(e) {
        i.prototype.onIsDroppedDownChanged.call(this, e), this.isDroppedDown && this._lbx.showSelection(), this.containsFocus() && !this.isTouching && this.selectAll(), t.setAttribute(this.dropDown, "aria-expanded", this.isDroppedDown)
      }, n.prototype._updateBtn = function() {
        var e = this.collectionView,
          n = this._tbx,
          o = this.dropDown.id,
          s = t.hasItems(e);
        i.prototype._updateBtn.call(this), this._btn.style.display = this._showBtn && null != e ? "" : "none", t.enable(this._btn, s), t.setAttribute(n, "role", s ? "combobox" : null), t.setAttribute(n, "aria-autocomplete", s ? "both" : null), t.setAttribute(n, "aria-owns", s && this.dropDown.parentElement ? o : null), t.setAttribute(this.dropDown, "aria-expanded", !s && null)
      }, n.prototype._btnclick = function(t) {
        i.prototype._btnclick.call(this, t), this.isTouching || this._elRef != this._tbx || this.selectAll()
      }, n.prototype._createDropDown = function() {
        var i = this;
        this._lbx || (this._lbx = new e.ListBox(this._dropDown)), this._lbx.maxHeight = 200, this._lbx.selectedIndexChanged.addHandler(function() {
          i._updateBtn();
          var e = i._lbx.selectedIndex,
            n = i._lbx.hostElement.children,
            o = e > -1 && e < n.length ? n[e].id : null;
          t.setAttribute(i._tbx, "aria-activedescendant", o && o.length ? o : null), i.selectedIndex = e, i.onSelectedIndexChanged()
        }), this._lbx.itemsChanged.addHandler(function() {
          i._updateBtn()
        })
      }, n.prototype._wheel = function(e) {
        if (!e.defaultPrevented && !this.isDroppedDown && !this.isReadOnly && this.containsFocus() && this.selectedIndex > -1) {
          var i = t.clamp(-e.deltaY, -1, 1);
          this.selectedIndex = t.clamp(this.selectedIndex - i, 0, this.collectionView.items.length - 1), e.preventDefault()
        }
      }, n.prototype._dropDownClick = function(t) {
        t.defaultPrevented || t.target != this._dropDown && (this.isDroppedDown = !1), i.prototype._dropDownClick.call(this, t)
      }, n.prototype._setText = function(e, n) {
        if (!this._composing && !this._settingText) {
          this._settingText = !0, null == e && (e = ""), e = e.toString();
          var o = this.selectedIndex,
            s = this.collectionView,
            r = this._getSelStart(),
            a = -1,
            l = !0;
          if (this.isEditable && (this._delKey || this._getSelEnd() < e.length || !this.containsFocus()) && (n = !0, l = !1), o = this.indexOf(e, n), l && (o < 0 && n && (o = this.indexOf(e, !1)), o < 0 && r > 0 && (o = this.indexOf(e.substr(0, r), !1))), o < 0 && !this.isEditable && t.hasItems(s) && (this.isRequired || e)) {
            var h = this._oldText || "";
            o = Math.max(0, this.indexOf(h, !1));
            for (var c = 0; c < e.length && c < h.length; c++)
              if (e[c] != h[c]) {
                r = c;
                break
              }
          }
          o > -1 && (a = r, e = this.getDisplayText(o)), e != this._tbx.value && (this._tbx.value = e), a > -1 && this.containsFocus() && !this.isTouching && this._updateInputSelection(a), s && s.moveCurrentToPosition(o), this._delKey = 0, this._settingText = !1, i.prototype._setText.call(this, e, n)
        }
      }, n.prototype._findNext = function(t, e) {
        var i = this.selectedIndex,
          n = this.collectionView,
          o = n ? n.items.length : 0,
          s = this.listBox;
        if (n && o && e) {
          t = t.toLowerCase();
          for (var r = i + e; r > -1 && r < o; r += e)
            if (0 == this.getDisplayText(r).toLowerCase().indexOf(t) && (!this.dropDown.children[r] || s.isItemEnabled(r))) return r
        }
        return i
      }, n.prototype._keydown = function(e) {
        if (i.prototype._keydown.call(this, e), !e.defaultPrevented && !this.isReadOnly && !e.altKey && t.hasItems(this.collectionView) && this._elRef == this._tbx) {
          this._delKey = 0;
          var n = this._getSelStart();
          switch (e.keyCode) {
            case t.Key.Back:
              if (this._bsCollapse && !this.isEditable) {
                var o = this._getSelEnd();
                n > 0 && o == this._tbx.value.length && t.hasItems(this.collectionView) && this._setSelRange(n - 1, o)
              }
              this._delKey = e.keyCode;
              break;
            case t.Key.Delete:
              this._delKey = e.keyCode;
              break;
            case t.Key.Up:
            case t.Key.Down:
              n == this.text.length && (n = 0), this.selectedIndex = this._findNext(this.text.substr(0, n), e.keyCode == t.Key.Up ? -1 : 1), this._setSelRange(n, this.text.length), e.preventDefault();
              break;
            case t.Key.PageUp:
              this._lbx._selectPrevPage(), this.selectAll(), e.preventDefault();
              break;
            case t.Key.PageDown:
              this._lbx._selectNextPage(), this.selectAll(), e.preventDefault()
          }
        }
      }, n.prototype._updateInputSelection = function(t) {
        this._elRef == this._tbx && this._setSelRange(t, this._tbx.value.length)
      }, n.prototype._getSelStart = function() {
        return this._tbx && this._tbx.value ? this._tbx.selectionStart : 0
      }, n.prototype._getSelEnd = function() {
        return this._tbx && this._tbx.value ? this._tbx.selectionEnd : 0
      }, n.prototype._setSelRange = function(e, i) {
        var n = this._tbx;
        this._elRef == n && t.setSelectionRange(n, e, i)
      }, n
    }(e.DropDown);
    e.ComboBox = i
  }(t.input || (t.input = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function(e) {
      function i(i, n) {
        var o = e.call(this, i) || this;
        return o._cssMatch = "wj-autocomplete-match", o._minLength = 2, o._maxItems = 6, o._itemCount = 0, o._delay = 500, o._query = "", o._inCallback = !1, o._srchProps = [], t.addClass(o.hostElement, "wj-autocomplete"), o._bsCollapse = !1, o.isEditable = !0, o.isRequired = !1, o.listBox.formatItem.addHandler(o._formatListItem, o), o._itemsSourceFnCallBackBnd = o._itemSourceFunctionCallback.bind(o), o.initialize(n), o
      }
      return __extends(i, e), Object.defineProperty(i.prototype, "minLength", {
        get: function() {
          return this._minLength
        },
        set: function(e) {
          this._minLength = t.asNumber(e, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "maxItems", {
        get: function() {
          return this._maxItems
        },
        set: function(e) {
          this._maxItems = t.asNumber(e, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "delay", {
        get: function() {
          return this._delay
        },
        set: function(e) {
          this._delay = t.asNumber(e, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "searchMemberPath", {
        get: function() {
          return this._srchProp
        },
        set: function(e) {
          this._srchProp = t.asString(e), this._srchProps = e ? e.trim().split(/\s*,\s*/) : []
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "itemsSourceFunction", {
        get: function() {
          return this._itemsSourceFn
        },
        set: function(e) {
          this._itemsSourceFn = t.asFunction(e), t.isFunction(this._itemsSourceFn) && this.itemsSourceFunction(this.text, this.maxItems, this._itemsSourceFnCallBackBnd)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "cssMatch", {
        get: function() {
          return this._cssMatch
        },
        set: function(e) {
          this._cssMatch = t.asString(e)
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype._keydown = function(i) {
        if (!i.defaultPrevented && this.isDroppedDown) switch (i.keyCode) {
          case t.Key.Up:
          case t.Key.Down:
            this.selectAll()
        }
        e.prototype._keydown.call(this, i)
      }, i.prototype._setText = function(e) {
        var i = this;
        if (!this._inCallback) {
          if (!e && this.selectedIndex > -1 && this.getDisplayText() && (this.selectedIndex = -1), e != this._oldText && (this._tbx.value != e && (this._tbx.value = e), this._oldText = e, this.onTextChanged(), !e && this.collectionView)) return (this._query || this.selectedIndex < 0) && (this.isDroppedDown = !1), this._query = this._rxHighlight = null, void(this.collectionView.filter = null);
          this._toSearch && clearTimeout(this._toSearch), e != this.getDisplayText() && (this._toSearch = setTimeout(function() {
            i._toSearch = null;
            var e = i.text.trim().toLowerCase();
            if (e.length >= i._minLength && e != i._query) {
              i._query = e, e = e.replace(/([.?*+^$[\]\\(){}|-])/g, "\\$1");
              var n = t.escapeHtml(e);
              i._rxMatch = new RegExp("(?=.*" + e.replace(/ /g, ")(?=.*") + ")", "ig"), i._rxHighlight = i.isContentHtml ? new RegExp("(" + n.replace(/ /g, "|") + ")(?![^<]*>|[^<>]* </)", "ig") : new RegExp("(" + n.replace(/ /g, "|") + ")", "ig"), i.itemsSourceFunction ? i.itemsSourceFunction(e, i.maxItems, i._itemsSourceFnCallBackBnd) : i._updateItems()
            }
          }, this._delay))
        }
      }, i.prototype._itemSourceFunctionCallback = function(e) {
        this._inCallback = !0;
        var i = t.asCollectionView(e);
        i && i.moveCurrentToPosition(-1), this.itemsSource = i, this._inCallback = !1, this.containsFocus() && (this.isDroppedDown = !0, this.refresh())
      }, i.prototype.onIsDroppedDownChanged = function(i) {
        if (this.containsFocus() ? (this.isDroppedDownChanged.raise(this, i), this.isDroppedDown && this._lbx.showSelection(), t.setAttribute(this.dropDown, "aria-expanded", this.isDroppedDown)) : e.prototype.onIsDroppedDownChanged.call(this, i), this.containsFocus() && !this.isDroppedDown && !this.isTouching && null == this.selectedItem) {
          var n = this.text.length;
          t.setSelectionRange(this._tbx, n)
        }
        this._query = ""
      }, i.prototype._updateItems = function() {
        var t = this.collectionView;
        if (t) {
          this._inCallback = !0, t.beginUpdate(), this._itemCount = 0, t.filter = this._filter.bind(this), t.moveCurrentToPosition(-1), t.endUpdate(), this._inCallback = !1;
          var e = t.items.length;
          this.isDroppedDown = e > 0 && this.containsFocus(), 0 != e || this.isEditable || (this.selectedIndex = -1), this.refresh()
        }
      }, i.prototype._filter = function(t) {
        if (this._itemCount >= this._maxItems) return !1;
        var e = this._getItemText(t, !1);
        if (this._srchProps)
          for (var i = 0; i < this._srchProps.length; i++) e += "\0" + t[this._srchProps[i]];
        return this.isContentHtml && (e = e.replace(/<[^>]*>/g, "")), !!e.match(this._rxMatch) && (this._itemCount++, !0)
      }, i.prototype._getItemText = function(t, e) {
        var i = t ? t.toString() : "",
          n = e && this.headerPath ? this._pathHdr : this._lbx._pathDisplay;
        return n && (i = null != (i = n.getValue(t)) ? i.toString() : ""), i
      }, i.prototype._formatListItem = function(t, e) {
        if (this._cssMatch && this._rxHighlight) {
          var i = '<span class="' + this._cssMatch + '">$1</span>';
          e.item.innerHTML = e.item.innerHTML.replace(this._rxHighlight, i)
        }
      }, i
    }(e.ComboBox);
    e.AutoComplete = i
  }(t.input || (t.input = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function(e) {
      function i(i, n) {
        var o = e.call(this, i) || this;
        o._isButton = !1, o._openOnHover = !1, o._hoverEnterBnd = o._hoverEnter.bind(o), o._hoverLeaveBnd = o._hoverLeave.bind(o), o._hoverOverBnd = o._hoverOver.bind(o), o.itemClicked = new t.Event;
        var s = o.hostElement,
          r = o._tbx,
          a = o._lbx,
          l = o.dropDown;
        t.addClass(s, "wj-menu"), r.style.display = "none";
        o._hdr = o._elRef = t.createElement('<div wj-part="header" class="wj-form-control"/>'), r.parentElement.insertBefore(o._hdr, o._tbx);
        var h = o._orgOuter.match(/tabindex="?(-?\d+)"?/i);
        return s.tabIndex = h ? parseInt(h[1]) : 0, o.isRequired = !1, t.setAttribute(s, "role", "menubar", !0), t.setAttribute(r, "role", null), t.setAttribute(r, "aria-autocomplete", null), t.setAttribute(r, "aria-owns", null), t.setAttribute(l, "role", "menu"), a.itemRole = "menuitem", "SELECT" == o._orgTag && (o.header = s.getAttribute("header"), o._lbx.itemsSource && (o.commandParameterPath = "cmdParam")), o.isContentHtml = !0, o.maxDropDownHeight = 500, o.addEventListener(o._hdr, "click", function(t) {
          t.defaultPrevented || (o._clearHover(), o._isButton ? (o.isDroppedDown = !1, o._raiseCommand()) : o.isDroppedDown = !o.isDroppedDown)
        }), a.lostFocus.addHandler(function() {
          o.containsFocus() || (o.isDroppedDown = !1)
        }), t.addClass(l, "wj-menu-items"), a.formatItem.addHandler(o._formatMenuItem.bind(o)), o.initialize(n), o
      }
      return __extends(i, e), Object.defineProperty(i.prototype, "header", {
        get: function() {
          return this._hdr.innerHTML
        },
        set: function(e) {
          this._hdr.innerHTML = t.asString(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "command", {
        get: function() {
          return this._cmd
        },
        set: function(t) {
          this._cmd = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "commandPath", {
        get: function() {
          return this._cmdPath
        },
        set: function(e) {
          this._cmdPath = t.asString(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "commandParameterPath", {
        get: function() {
          return this._cmdParamPath
        },
        set: function(e) {
          this._cmdParamPath = t.asString(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "subItemsPath", {
        get: function() {
          return this._subPath
        },
        set: function(t) {
          t != this._subPath && (this._subPath = t, this.refresh(!0))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "openOnHover", {
        get: function() {
          return this._openOnHover
        },
        set: function(e) {
          this._openOnHover = t.asBoolean(e);
          var i = this.hostElement,
            n = this.dropDown,
            o = this.addEventListener.bind(this),
            s = this.removeEventListener.bind(this);
          s(i, "mouseenter", this._hoverEnterBnd), s(i, "mouseleave", this._hoverLeaveBnd), s(n, "mouseover", this._hoverOverBnd), s(n, "mouseleave", this._hoverLeaveBnd), this._openOnHover && (o(i, "mouseenter", this._hoverEnterBnd), o(i, "mouseleave", this._hoverLeaveBnd), o(n, "mouseover", this._hoverOverBnd), o(n, "mouseleave", this._hoverLeaveBnd))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "isButton", {
        get: function() {
          return this._isButton
        },
        set: function(e) {
          this._isButton = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "owner", {
        get: function() {
          return this._owner
        },
        set: function(e) {
          this._owner = t.asType(e, HTMLElement, !0), this._enableDisableItems()
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.show = function(e) {
        if (!this.isDroppedDown) {
          var i = this.dropDown;
          this.selectedIndex = -1, this.onIsDroppedDownChanging(new t.CancelEventArgs) && (this.owner && (i[t.Control._OWNR_KEY] = this.owner), t.showPopup(i, e, !1, this.isAnimated), this.onIsDroppedDownChanged(), i.focus())
        }
      }, i.prototype.hide = function() {
        this.isDroppedDown && this.onIsDroppedDownChanging(new t.CancelEventArgs) && (t.hidePopup(this.dropDown), this.onIsDroppedDownChanged())
      }, i.prototype.onItemClicked = function(t) {
        this.itemClicked.raise(this, t)
      }, i.prototype.refresh = function(t) {
        void 0 === t && (t = !0), e.prototype.refresh.call(this, t), this._enableDisableItems()
      }, i.prototype.onIsDroppedDownChanged = function(t) {
        e.prototype.onIsDroppedDownChanged.call(this, t), this.isDroppedDown ? (this._closing = !0, this._defaultItem = this.selectedItem, this.isRequired = !1, this.selectedIndex = -1, this._enableDisableItems(), this._closing = !1, this.dropDown.focus()) : this.selectedItem || (this.selectedItem = this._defaultItem)
      }, i.prototype._getSubItems = function(e) {
        var i = this.subItemsPath,
          n = e && i ? e[i] : null;
        return t.isArray(n) && n.length ? n : null
      }, i.prototype._formatMenuItem = function(e, i) {
        var n = i.item;
        this._getSubItems(i.data) ? t.addClass(n, "wj-subitems") : "-" == n.innerHTML && (n.innerHTML = "", t.addClass(n, "wj-separator"))
      }, i.prototype._keydown = function(n) {
        if (!n.defaultPrevented) {
          var o = this._getKeyCode(n);
          if (this.isDroppedDown) switch (o) {
            case t.Key.Enter:
            case t.Key.Right:
              if (this._showSubMenu()) return void n.preventDefault();
              break;
            case t.Key.Left:
              var s = t.Control.getControl(this.owner);
              if (s instanceof i) {
                var r = s.dropDown,
                  a = s.selectedIndex;
                return (a > -1 ? r.children[a] : r).focus(), void n.preventDefault()
              }
          }
          o == t.Key.Enter && (this.isDroppedDown ? this.getDisplayText(this.selectedIndex) && this._raiseCommand() : (this.isDroppedDown = !0, n.preventDefault()))
        }
        e.prototype._keydown.call(this, n)
      }, i.prototype._dropDownClick = function(t) {
        if (!t.defaultPrevented && t.target != this.dropDown) {
          if (this._clearHover(), this._showSubMenu()) return void t.preventDefault();
          this.getDisplayText(this.selectedIndex) && this._raiseCommand()
        }
        e.prototype._dropDownClick.call(this, t)
      }, i.prototype._showSubMenu = function() {
        var e = this,
          n = this._getSubItems(this.selectedItem);
        if (!n) return null;
        var o = this.dropDown.children[this.selectedIndex].getBoundingClientRect(),
          s = new t.Point(o.right, o.top);
        if (0 == o.height) return null;
        var r = this.rightToLeft || "rtl" == this.dropDown.getAttribute("dir");
        r && (s.x = o.left);
        var a = new i(document.createElement("div"), {
          owner: this.hostElement,
          itemsSource: n,
          itemClicked: function(t, i) {
            e.itemClicked.raise(t, i)
          }
        });
        "displayMemberPath,selectedValuePath,isContentHtml,command,commandPath,commandParameterPath,maxDropDownWidth,maxDropDownHeight,dropDownCssClass,isAnimated,subItemsPath,openOnHover".split(",").forEach(function(t) {
          a[t] = e[t]
        });
        var l = a.dropDown;
        return t.setAttribute(l, "dir", r ? "rtl" : null), a.show(s), l[t.Control._OWNR_KEY] = this.dropDown, l.focus(), a
      }, i.prototype._raiseCommand = function(e) {
        var n = this.selectedItem,
          o = this._getSubItems(n),
          s = this._getCommand(n);
        if (s && !o) {
          var r = this._getCommandParm(n);
          if (!this._canExecuteCommand(s, r)) return;
          this._executeCommand(s, r)
        }
        if (this.onItemClicked(e), this.containsFocus()) {
          for (var a = this; a instanceof i && a.owner;) a = t.Control.getControl(a.owner);
          a instanceof i && (a.isDroppedDown = !1, a.focus())
        }
      }, i.prototype._getCommand = function(t) {
        var e = t && this._cmdPath ? t[this._cmdPath] : null;
        return e || this._cmd
      }, i.prototype._getCommandParm = function(t) {
        var e = this._cmdParamPath;
        return t && e ? t[e] : t
      }, i.prototype._executeCommand = function(e, i) {
        e && !t.isFunction(e) && (e = e.executeCommand), t.isFunction(e) && e(i)
      }, i.prototype._canExecuteCommand = function(e, i) {
        if (e) {
          var n = e.canExecuteCommand;
          if (t.isFunction(n)) return n(i)
        }
        return !0
      }, i.prototype._enableDisableItems = function() {
        if (this.collectionView && (this._cmd || this._cmdPath))
          for (var e = this.collectionView.items, i = 0; i < e.length; i++) {
            var n = e[i],
              o = this._getCommand(n);
            if (o) {
              var s = this._lbx.hostElement.children[i],
                r = this._getCommandParm(n);
              t.toggleClass(s, "wj-state-disabled", !this._canExecuteCommand(o, r))
            }
          }
      }, i.prototype._clearHover = function() {
        this._toHover && clearTimeout(this._toHover), this._toHover = null
      }, i.prototype._hoverEnter = function(e) {
        var i = this;
        this._clearHover(), this._toHover = setTimeout(function() {
          i._toHover = null, i.isDroppedDown = !0
        }, t.Control._HOVER_DELAY)
      }, i.prototype._hoverLeave = function(e) {
        if (this._clearHover(), this.isDroppedDown) {
          var i = document.elementFromPoint(e.clientX, e.clientY),
            n = t.closest(i, ".wj-listbox.wj-menu-items"),
            o = this._subMenu;
          n || t.contains(this.hostElement, i) || (this.isDroppedDown = !1, o && (o.isDroppedDown = !1))
        }
      }, i.prototype._hoverOver = function(e) {
        var i = this;
        this._clearHover(), this._toHover = setTimeout(function() {
          i._toHover = null;
          var t = i.listBox.indexOf(e.target);
          t > -1 && (i.selectedIndex = t, i._subMenu = i._showSubMenu())
        }, t.Control._HOVER_DELAY)
      }, i
    }(e.ComboBox);
    e.Menu = i
  }(t.input || (t.input = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    t._addCultureInfo("MultiSelect", {
      itemsSelected: "{count:n0} items selected",
      selectAll: "Select All"
    });
    var i = function(e) {
      function i(i, n) {
        var o = e.call(this, i) || this;
        return o._maxHdrItems = 2, o._readOnly = !1, o._hdrFmt = t.culture.MultiSelect.itemsSelected, o.checkedItemsChanged = new t.Event, t.addClass(o.hostElement, "wj-multiselect"), o._tbx.readOnly = !0, o.checkedMemberPath = null, o.addEventListener(o.inputElement, "click", function(t) {
          document.elementFromPoint(t.clientX, t.clientY) == o.inputElement && (o.isDroppedDown = !o.isDroppedDown)
        }), o.addEventListener(o._selectAll, "click", function(e) {
          t.hasItems(o.collectionView) && e.target == o._selectAllCheckbox && (o.checkedItems = e.target.checked ? o.collectionView.items : [])
        }), o.removeEventListener(o.dropDown, "click"), o._updateHeader(), o.listBox.itemsChanged.addHandler(function() {
          o._updateHeader()
        }), o.listBox.checkedItemsChanged.addHandler(function() {
          o._updateHeader(), o.onCheckedItemsChanged()
        }), o.initialize(n), o
      }
      return __extends(i, e), Object.defineProperty(i.prototype, "showSelectAllCheckbox", {
        get: function() {
          return "" == this._selectAll.style.display
        },
        set: function(e) {
          this._selectAll.style.display = t.asBoolean(e) ? "" : "none"
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "selectAllLabel", {
        get: function() {
          return this._selectAllLabel
        },
        set: function(e) {
          e != this._selectAllLabel && (this._selectAllLabel = t.asString(e), this.refresh())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "checkedMemberPath", {
        get: function() {
          var t = this.listBox.checkedMemberPath;
          return t != i._DEF_CHECKED_PATH ? t : null
        },
        set: function(e) {
          e = t.asString(e), this.listBox.checkedMemberPath = e || i._DEF_CHECKED_PATH
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "maxHeaderItems", {
        get: function() {
          return this._maxHdrItems
        },
        set: function(e) {
          this._maxHdrItems != e && (this._maxHdrItems = t.asNumber(e), this._updateHeader())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "headerFormat", {
        get: function() {
          return this._hdrFmt
        },
        set: function(e) {
          e != this._hdrFmt && (this._hdrFmt = t.asString(e), this._updateHeader())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "headerFormatter", {
        get: function() {
          return this._hdrFormatter
        },
        set: function(e) {
          e != this._hdrFormatter && (this._hdrFormatter = t.asFunction(e), this._updateHeader())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "checkedItems", {
        get: function() {
          return this.listBox.checkedItems
        },
        set: function(e) {
          this.listBox.checkedItems = t.asArray(e)
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.onCheckedItemsChanged = function(t) {
        this.checkedItemsChanged.raise(this, t)
      }, i.prototype._createDropDown = function() {
        this._selectAll = t.createElement('<div class="wj-listbox-item wj-header wj-select-all" tabindex="0" style="display:none"><label><input type="checkbox"> <span></span></label></div>', this._dropDown), this._selectAllCheckbox = this._selectAll.querySelector("input[type=checkbox]"), this._selectAllSpan = this._selectAll.querySelector("label>span"), t.setText(this._selectAllSpan, t.culture.MultiSelect.selectAll);
        var i = t.createElement('<div style="width:100%;border:none"></div>', this._dropDown);
        this._lbx = new t.input.ListBox(i), e.prototype._createDropDown.call(this), t.addClass(this.dropDown, "wj-control wj-listbox"), this.dropDown.style.overflow = "hidden"
      }, Object.defineProperty(i.prototype, "isReadOnly", {
        get: function() {
          return this._readOnly
        },
        set: function(e) {
          this._readOnly = t.asBoolean(e), t.toggleClass(this.hostElement, "wj-state-readonly", this.isReadOnly)
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.refresh = function(i) {
        void 0 === i && (i = !0), e.prototype.refresh.call(this, i), this._updateHeader(), this._selectAllSpan && t.setText(this._selectAllSpan, this._selectAllLabel || t.culture.MultiSelect.selectAll)
      }, i.prototype.onIsDroppedDownChanged = function(t) {
        var i = this;
        e.prototype.onIsDroppedDownChanged.call(this, t), this.isDroppedDown && this.containsFocus() && setTimeout(function() {
          i.listBox.focus()
        }, 200)
      }, i.prototype._setText = function(t, e) {}, i.prototype._keydown = function(i) {
        e.prototype._keydown.call(this, i), !i.defaultPrevented && t.hasItems(this.collectionView) && i.keyCode > 32 && (this.isDroppedDown = !0)
      }, i.prototype._updateHeader = function() {
        var e = this.checkedItems;
        if (t.isFunction(this._hdrFormatter)) this.inputElement.value = this._hdrFormatter();
        else {
          var i = "";
          if (e.length > 0)
            if (e.length <= this._maxHdrItems) {
              if (this.displayMemberPath) {
                var n = new t.Binding(this.displayMemberPath);
                e = e.map(function(t) {
                  return n.getValue(t)
                })
              }
              i = e.join(", ")
            } else i = t.format(this.headerFormat, {
              count: e.length
            });
          this.inputElement.value = i
        }
        var o = null,
          s = this.collectionView;
        t.hasItems(s) && (0 == e.length ? o = !1 : e.length == s.items.length && (o = !0)), this._selectAllCheckbox.indeterminate = null == o, null != o && (this._selectAllCheckbox.checked = o), this._updateState()
      }, i._DEF_CHECKED_PATH = "$checked", i
    }(e.ComboBox);
    e.MultiSelect = i
  }(t.input || (t.input = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function(e) {
      function i(i, n) {
        var o = e.call(this, i) || this;
        return o._selItems = [], o._lastInputValue = "", o._selPath = new t.Binding(null), o._notAddItm = !1, o.selectedItemsChanged = new t.Event, t.addClass(o.hostElement, "wj-multi-autocomplete"), o.showDropDownButton = !1, o.initialize(n), o._wjTpl = o.hostElement.querySelector(".wj-template"), o._wjInput = o.hostElement.querySelector(".wj-input"), o.addEventListener(o.hostElement, "keyup", o._keyup.bind(o), !0), o.addEventListener(window, "resize", o._adjustInputWidth.bind(o)), o.addEventListener(o._tbx, "focus", function() {
          o._itemOff()
        }), o._addHelperInput(), o._initSeltems(), o.listBox.itemsChanged.addHandler(function() {
          return o.selectedIndex = -1
        }), o._refreshHeader(), o
      }
      return __extends(i, e), Object.defineProperty(i.prototype, "showDropDownButton", {
        set: function(t) {
          this._showBtn = !1
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "maxSelectedItems", {
        get: function() {
          return this._maxSelItems
        },
        set: function(e) {
          this._maxSelItems != e && (this._maxSelItems = t.asNumber(e, !0), this._updateMaxItems(), this._refreshHeader(), this._clearSelIndex())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "selectedMemberPath", {
        get: function() {
          return this._selPath.path
        },
        set: function(e) {
          (e = t.asString(e)) !== this.selectedMemberPath && (this._selPath.path = e, this._initSeltems(), this._refreshHeader(), this.onSelectedItemsChanged())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "selectedItems", {
        get: function() {
          return this._selItems
        },
        set: function(e) {
          if (this._selItems = t.asArray(e), this.selectedMemberPath && "" !== this.selectedMemberPath && this._selItems)
            for (var i = 0; i < this._selItems.length; i++) {
              var n = this._selItems[i];
              this._setSelItem(n, !1)
            }
          this._updateMaxItems(), this.onSelectedItemsChanged(), this._refreshHeader(), this._clearSelIndex()
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.onSelectedItemsChanged = function(t) {
        this.selectedItemsChanged.raise(this, t)
      }, i.prototype.onIsDroppedDownChanged = function(t) {
        !this.isDroppedDown && this.selectedIndex > -1 && !this._notAddItm && this._addItem(!0), this._notAddItm = !1, e.prototype.onIsDroppedDownChanged.call(this, t)
      }, i.prototype.refresh = function(t) {
        void 0 === t && (t = !0), e.prototype.refresh.call(this, t), this._initSeltems(), this.isDroppedDown || this._refreshHeader()
      }, i.prototype._keydown = function(i) {
        if (!this.isReadOnly) {
          if (!i.defaultPrevented) switch (i.keyCode) {
            case t.Key.Back:
              this._lastInputValue = this._tbx.value;
              break;
            case t.Key.Enter:
              this._itemOff(), this._addItem(!0), t.isIE && t.setSelectionRange(this._tbx, 0, 0);
              break;
            case t.Key.Tab:
              this.isDroppedDown ? (this._addItem(!1), this._tbx.value = "", this._lbx.selectedIndex = -1, i.preventDefault()) : this._updateFocus();
              break;
            case t.Key.Space:
              if ("" !== this._tbx.value) return;
              this.isDroppedDown || this._tbx.disabled || (this.isDroppedDown = !0, this._clearSelIndex());
              break;
            case t.Key.Escape:
              this.isDroppedDown && (this._notAddItm = !0);
              break;
            case t.Key.Left:
              this._itemOn(!this.rightToLeft);
              break;
            case t.Key.Right:
              this._itemOn(!!this.rightToLeft);
              break;
            case t.Key.Up:
            case t.Key.Down:
              var n = t.getActiveElement();
              if (i.altKey) {
                if (this._tbx == n) return this.isDroppedDown = !this.isDroppedDown, this.isDroppedDown || this._tbx.focus(), void i.preventDefault()
              } else if (this._tbx !== n) return;
            default:
              if (i.keyCode === t.Key.Back || i.keyCode === t.Key.Delete) return;
              this._itemOff(), null != this._maxSelItems && this._selItems.length >= this._maxSelItems && i.preventDefault()
          }
          this._tbx.disabled || e.prototype._keydown.call(this, i)
        }
      }, i.prototype._updateState = function() {
        e.prototype._updateState.call(this), this._wjTpl && (t.hasClass(this.hostElement, "wj-state-focused") || this._itemOff())
      }, i.prototype._keyup = function(e) {
        if (!this.isReadOnly && !e.defaultPrevented) switch (e.keyCode) {
          case t.Key.Back:
            0 === this._tbx.value.length && 0 === this._lastInputValue.length && this._delItem(!1);
            break;
          case t.Key.Delete:
            this._delItem(!0)
        }
      }, i.prototype._addHelperInput = function() {
        var t = document.createElement("input");
        t.type = "text", t.tabIndex = -1, t.className = "wj-token-helper", t.readOnly = !0, this._wjTpl.insertBefore(t, this._wjInput), this._helperInput = t
      }, i.prototype._refreshHeader = function() {
        for (var t = this.hostElement.querySelectorAll(".wj-token"), e = 0; e < t.length; e++) this._wjTpl.removeChild(t[e]);
        var i = this.selectedItems;
        if (!i || 0 === i.length) return this._wjInput.style.cssFloat = this.rightToLeft ? "right" : "left", void this._adjustInputWidth();
        for (e = 0; e < i.length; e++) this._insertToken(i[e]);
        this._wjInput.style.cssFloat = this.rightToLeft ? "right" : "left", this._adjustInputWidth()
      }, i.prototype._insertToken = function(e) {
        var i = this._getItemText(e, !0);
        this.isContentHtml || (i = t.escapeHtml(i)), this._wjTpl.insertBefore(this._createItem(i), this._wjInput)
      }, i.prototype._updateMaxItems = function() {
        null != this._maxSelItems && this._selItems && this._selItems.length > this._maxSelItems && (this._selItems = this._selItems.slice(0, this._maxSelItems))
      }, i.prototype._updateFocus = function() {
        var e = this,
          n = this._wjTpl.querySelector("." + i._clsActive);
        n ? (t.removeClass(n, i._clsActive), setTimeout(function() {
          e._tbx.focus()
        })) : (this._clearSelIndex(), t.removeClass(this.hostElement, "wj-state-focused"))
      }, i.prototype._addItem = function(t) {
        this.selectedItems.indexOf(this.selectedItem) > -1 ? this._clearSelIndex() : this.selectedIndex > -1 && (this._updateSelItems(this.selectedItem, !0), this._refreshHeader(), t && this._clearSelIndex(), this._disableInput(!0))
      }, i.prototype._delItem = function(t) {
        var e, n, o = this._wjTpl.querySelector("." + i._clsActive),
          s = !1;
        t && !o || (o ? (n = this._getItemIndex(o)) > -1 && (e = this._selItems[n], s = !0) : this._selItems.length > 0 && (e = this._selItems[this._selItems.length - 1], s = !0), s && (this._updateSelItems(e, !1), this._refreshHeader(), this._clearSelIndex(), this._disableInput(!1)), this._tbx.focus())
      }, i.prototype._updateSelItems = function(t, e) {
        if (e) {
          if (this._selItems && 0 !== this._selItems.length || (this._selItems = []), null != this._maxSelItems && this._selItems.length >= this._maxSelItems) return;
          this._selItems.push(t)
        } else {
          var i = this._selItems.indexOf(t);
          this._selItems.splice(i, 1)
        }
        this._hasSelectedMemeberPath() && this._setSelItem(t, e), this.onSelectedItemsChanged()
      }, i.prototype._createItem = function(e) {
        var n = this,
          o = document.createElement("div"),
          s = document.createElement("span"),
          r = document.createElement("a");
        return o.appendChild(s), o.appendChild(r), o.className = "wj-token", s.className = "wj-token-label", s.innerHTML = e, r.className = "wj-token-close", r.href = "#", r.tabIndex = -1, r.text = "×", o.style.cssFloat = this.rightToLeft ? "right" : "left", this.addEventListener(o, "click", function(e) {
          n._helperInput.focus();
          var s = n._wjTpl.querySelector("." + i._clsActive);
          s && t.removeClass(s, i._clsActive), t.addClass(o, i._clsActive), e.stopPropagation(), e.preventDefault()
        }), this.addEventListener(r, "click", function(t) {
          if (!n.isReadOnly) {
            var e = n._getItemIndex(o);
            if (e > -1) {
              var i = n._selItems[e];
              n._updateSelItems(i, !1)
            }
            n._wjTpl.removeChild(o), n._adjustInputWidth(), n._disableInput(!1), n._tbx.focus(), t.stopPropagation(), t.preventDefault()
          }
        }), o
      }, i.prototype._itemOn = function(e) {
        var n, o, s, r = t.getActiveElement();
        if ((this._tbx != r || 0 === this._tbx.value.length) && 0 !== (n = this._wjTpl.querySelectorAll(".wj-token")).length)
          if (o = this._wjTpl.querySelector("." + i._clsActive), s = this._getItemIndex(o), e) {
            if (0 === s) return; - 1 === s ? (t.addClass(n[n.length - 1], i._clsActive), this._helperInput.focus()) : (t.removeClass(o, i._clsActive), t.addClass(n[s - 1], i._clsActive), this._helperInput.focus())
          } else if (!e) {
            if (-1 === s) return;
            s !== n.length - 1 ? (t.removeClass(o, i._clsActive), t.addClass(n[s + 1], i._clsActive), this._helperInput.focus()) : (t.removeClass(o, i._clsActive), this._tbx.focus())
          }
      }, i.prototype._itemOff = function() {
        var e = this._wjTpl.querySelector("." + i._clsActive);
        e && t.removeClass(e, i._clsActive)
      }, i.prototype._initSeltems = function() {
        if (this.selectedMemberPath && "" !== this.selectedMemberPath) {
          var t = this.itemsSource;
          if (this._selItems.splice(0, this._selItems.length), t)
            for (var e = 0; e < t.sourceCollection.length; e++) this._getSelItem(e) && this._selItems.push(t.sourceCollection[e])
        }
      }, i.prototype._getSelItem = function(e) {
        var i = this.itemsSource.sourceCollection[e];
        return !(!t.isObject(i) || !this.selectedMemberPath) && this._selPath.getValue(i)
      }, i.prototype._setSelItem = function(e, i) {
        this.itemsSource;
        t.isObject(e) && this._selPath.getValue(e) != i && this._selPath.setValue(e, i)
      }, i.prototype._clearSelIndex = function() {
        this.selectedIndex = -1
      }, i.prototype._hasSelectedMemeberPath = function() {
        return this.selectedMemberPath && "" !== this.selectedMemberPath
      }, i.prototype._disableInput = function(t) {
        null != this._maxSelItems && (this._selItems.length < this._maxSelItems ? (this._tbx.disabled = !1, this._tbx.focus()) : (this._tbx.disabled = !0, this.hostElement.focus()))
      }, i.prototype._adjustInputWidth = function() {
        this._tbx.style.width = "60px";
        var e, i = t.getElementRect(this.hostElement),
          n = t.getElementRect(this._tbx),
          o = getComputedStyle(this._tbx),
          s = parseInt(o.paddingLeft, 10),
          r = parseInt(o.paddingRight, 10);
        e = this.rightToLeft ? n.left + n.width - i.left - s - r - 8 : i.left + i.width - n.left - s - r - 8, this._tbx.style.width = e + "px"
      }, i.prototype._getItemIndex = function(t) {
        for (var e = this.hostElement.querySelectorAll(".wj-token"), i = 0; i < e.length; i++)
          if (t === e[i]) return i;
        return -1
      }, i._clsActive = "wj-token-active", i
    }(e.AutoComplete);
    e.MultiAutoComplete = i
  }(t.input || (t.input = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i;
    ! function(t) {
      t[t.None = 0] = "None", t[t.Click = 1] = "Click", t[t.Blur = 2] = "Blur", t[t.ClickOrBlur = 3] = "ClickOrBlur"
    }(i = e.PopupTrigger || (e.PopupTrigger = {}));
    var n = function(n) {
      function o(e, o) {
        var s = n.call(this, e, null, !0) || this;
        s._showTrigger = i.Click, s._hideTrigger = i.Blur, s._fadeIn = !0, s._fadeOut = !0, s._removeOnHide = !0, s._visible = !1, s._ownerClickBnd = s._ownerClick.bind(s), s._ownerMousedownBnd = s._ownerMouseDown.bind(s), s.showing = new t.Event, s.shown = new t.Event, s.hiding = new t.Event, s.hidden = new t.Event;
        var r = s.hostElement;
        return t.addClass(r, "wj-control wj-content wj-popup"), r.getAttribute("tabindex") || (r.tabIndex = 0), t.hidePopup(r, !1), s.addEventListener(r, "compositionstart", function(t) {
          s._composing = !0
        }), s.addEventListener(r, "compositionend", function(t) {
          s._composing = !1
        }), s.addEventListener(r, "keydown", function(e) {
          if (!e.defaultPrevented) {
            if (e.keyCode != t.Key.Escape || s._composing || (e.preventDefault(), s.hide()), e.keyCode == t.Key.Enter) {
              var i = s.dialogResultEnter;
              i && (e.preventDefault(), s._validateAndHide(i))
            }
            e.keyCode == t.Key.Tab && s.modal && (e.preventDefault(), t.moveFocus(r, e.shiftKey ? -1 : 1))
          }
        }), s.addEventListener(r, "click", function(t) {
          if (t.target instanceof HTMLElement) {
            var e = t.target.className.match(/\bwj-hide[\S]*\b/);
            e && e.length > 0 && (t.preventDefault(), t.stopPropagation(), s.hide(e[0]))
          }
        }), s.addEventListener(document, "wheel", function(t) {
          if (s.isVisible && s._modal) {
            for (var e = t.target; e && e != document.body; e = e.parentElement)
              if (e.scrollHeight > e.clientHeight) return;
            t.preventDefault()
          }
        }), s.initialize(o), s
      }
      return __extends(o, n), Object.defineProperty(o.prototype, "owner", {
        get: function() {
          return this._owner
        },
        set: function(e) {
          var i = this._owner;
          i && (this.removeEventListener(i, "mousedown"), this.removeEventListener(i, "click")), (i = this._owner = null != e ? t.getElement(e) : null) && (this.addEventListener(i, "mousedown", this._ownerMousedownBnd, !0), this.addEventListener(i, "click", this._ownerClickBnd, !0))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "content", {
        get: function() {
          return this.hostElement.firstElementChild
        },
        set: function(t) {
          t != this.content && (this.hostElement.innerHTML = "", t instanceof HTMLElement && this.hostElement.appendChild(t))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "showTrigger", {
        get: function() {
          return this._showTrigger
        },
        set: function(e) {
          this._showTrigger = t.asEnum(e, i)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "hideTrigger", {
        get: function() {
          return this._hideTrigger
        },
        set: function(e) {
          this._hideTrigger = t.asEnum(e, i)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "fadeIn", {
        get: function() {
          return this._fadeIn
        },
        set: function(e) {
          this._fadeIn = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "fadeOut", {
        get: function() {
          return this._fadeOut
        },
        set: function(e) {
          this._fadeOut = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "removeOnHide", {
        get: function() {
          return this._removeOnHide
        },
        set: function(e) {
          this._removeOnHide = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "modal", {
        get: function() {
          return this._modal
        },
        set: function(e) {
          this._modal = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "isDraggable", {
        get: function() {
          return this._draggable
        },
        set: function(e) {
          this._draggable = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "dialogResult", {
        get: function() {
          return this._result
        },
        set: function(t) {
          this._result = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "dialogResultEnter", {
        get: function() {
          return this._resultEnter
        },
        set: function(t) {
          this._resultEnter = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "isVisible", {
        get: function() {
          var t = this.hostElement;
          return this._visible && null != t && t.offsetHeight > 0
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype.show = function(e, n) {
        var o = this;
        if (!this.isVisible) {
          var s = this.hostElement;
          this.dialogResult = null, this._callback = null, this._hideAnim && (clearInterval(this._hideAnim), this._hideAnim = null);
          var r = new t.CancelEventArgs;
          this.onShowing(r) && (null != e && (this.modal = t.asBoolean(e)), null != n && (this._callback = t.asFunction(n)), t.showPopup(s, this._owner, !1, this._fadeIn, !1), this._modal && this._showBackdrop(), this._composing = !1, this._visible = !0, this.onShown(r), this.modal && this.addEventListener(window, "focus", function() {
            o.containsFocus() || t.moveFocus(s, 0)
          }), this._dragged = !1, this._makeDraggable(this._draggable), this.addEventListener(window, "touchstart", function(e) {
            t.contains(s, e.target) || o.isVisible && o._hideTrigger & i.Blur && o.hide()
          }), setTimeout(function() {
            if (!o.isTouching) {
              var e = s.querySelector("input[autofocus]");
              e && e.clientHeight > 0 && !e.disabled && e.tabIndex > -1 && !t.closest(e, "[disabled],.wj-state-disabled") ? (e.focus(), e.select()) : t.moveFocus(s, 0)
            }
            o.containsFocus() || (s.tabIndex = 0, s.focus())
          }, 200))
        }
      }, o.prototype.hide = function(i) {
        var n = this;
        if (this._makeDraggable(!1), this.removeEventListener(window, "touchstart"), this.isVisible) {
          t.isUndefined(i) || (this.dialogResult = i);
          var o = new t.CancelEventArgs;
          if (this.onHiding(o)) {
            for (var s = this.hostElement.querySelectorAll(".wj-control.wj-dropdown"), r = 0; r < s.length; r++) {
              var a = t.Control.getControl(s[r]);
              a instanceof e.DropDown && (a.isDroppedDown = !1)
            }
            this._bkdrop && t.hidePopup(this._bkdrop, this.removeOnHide, this.fadeOut), this._hideAnim = t.hidePopup(this.hostElement, this.removeOnHide, this.fadeOut), this._visible = !1, this.removeEventListener(window, "focus"), this.containsFocus() && t.getActiveElement().blur(), setTimeout(function() {
              n._updateState(), n.onHidden(o), n._callback && n._callback(n)
            })
          }
        }
      }, o.prototype.onShowing = function(t) {
        return this.showing.raise(this, t), !t.cancel
      }, o.prototype.onShown = function(t) {
        this.shown.raise(this, t)
      }, o.prototype.onHiding = function(t) {
        return this.hiding.raise(this, t), !t.cancel
      }, o.prototype.onHidden = function(t) {
        this.hidden.raise(this, t)
      }, o.prototype.dispose = function() {
        this.owner = null, n.prototype.dispose.call(this)
      }, o.prototype.onLostFocus = function(t) {
        this.isVisible && this._hideTrigger & i.Blur && (this.containsFocus() || this.hide()), n.prototype.onLostFocus.call(this, t)
      }, o.prototype.refresh = function(e) {
        if (void 0 === e && (e = !0), n.prototype.refresh.call(this, e), this.isVisible && !this._refreshing) {
          this._refreshing = !0;
          var i = t.getActiveElement(),
            o = this._owner ? this._owner.getBoundingClientRect() : null;
          t.showPopup(this.hostElement, o), this._modal && i instanceof HTMLElement && i != t.getActiveElement() && i.focus(), this._refreshing = !1
        }
      }, o.prototype._makeDraggable = function(e) {
        var i, n, s = this,
          r = this.hostElement,
          a = r ? r.querySelector(".wj-dialog-header") : null,
          l = document,
          h = "mouseup",
          c = "touchend",
          u = !1,
          p = function(e) {
            r && t.contains(a, e.target) && !e.button && (i = new t.Point(r.offsetLeft, r.offsetTop), n = t.mouseToPage(e), u = !1, ["mousemove", h, "touchmove", c].forEach(function(t) {
              s.removeEventListener(l, t)
            }), ["mousemove", "touchmove"].forEach(function(t) {
              s.addEventListener(l, t, d, !1, !0)
            }), [h, c].forEach(function(t) {
              s.addEventListener(l, t, f)
            }))
          },
          d = function(e) {
            var a = t.mouseToPage(e),
              l = a.x - n.x,
              h = a.y - n.y;
            (u || Math.abs(l) + Math.abs(h) > o._DRAG_THRESHOLD) && (t.setCss(r, {
              left: Math.max(i.x + l, 50 - r.offsetWidth),
              top: Math.max(i.y + (a.y - n.y), 0)
            }), s._dragged = !0, u = !0)
          },
          f = function(t) {
            ["mousemove", h, "touchmove", c].forEach(function(t) {
              s.removeEventListener(l, t)
            })
          };
        ["mousedown", "touchstart"].forEach(function(t) {
          s.removeEventListener(l, t)
        }), a && (t.setCss(a, {
          touchAction: e ? "none" : ""
        }), e && ["mousedown", "touchstart"].forEach(function(t) {
          s.addEventListener(l, t, p)
        }))
      }, o.prototype._handleResize = function() {
        this.isVisible && !this._dragged && this.refresh()
      }, o.prototype._ownerClick = function(t) {
        this.isVisible ? this._hideTrigger & i.Click && this.hide() : this._showTrigger & i.Click && (this._wasVisible || this.show())
      }, o.prototype._ownerMouseDown = function(t) {
        this._wasVisible = this.isVisible
      }, o.prototype._showBackdrop = function() {
        var e = this;
        this._bkdrop || (this._bkdrop = document.createElement("div"), this._bkdrop.tabIndex = -1, t.addClass(this._bkdrop, "wj-popup-backdrop"), this.addEventListener(this._bkdrop, "mousedown", function(t) {
          t.preventDefault(), t.stopPropagation(), e.hostElement.focus(), e.hideTrigger & i.Blur && e.hide()
        })), t.setCss(this._bkdrop, {
          zIndex: t.Control._POPUP_ZINDEX,
          display: ""
        });
        var n = this.hostElement;
        n.parentElement.insertBefore(this._bkdrop, n)
      }, o.prototype._validateAndHide = function(t) {
        var e = this.hostElement.querySelector(":invalid");
        e ? e.focus() : this.hide(t)
      }, o._DRAG_THRESHOLD = 6, o
    }(t.Control);
    e.Popup = n
  }(t.input || (t.input = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function(i) {
      function n(n, o) {
        var s = i.call(this, n) || this;
        if (s._format = "d", s.valueChanged = new t.Event, t.addClass(s.hostElement, "wj-inputdate"), s._msk = new t._MaskProvider(s._tbx), t.isIE9() || (s.inputType = "tel"), s._tbx.type.match(/date/i) && (s.inputType = ""), s.addEventListener(s.hostElement, "wheel", function(i) {
          if (!i.defaultPrevented && !s.isDroppedDown && s.containsFocus() && null != s.value && s._canChangeValue()) {
            var n = t.clamp(-i.deltaY, -1, 1);
            s.value = s.selectionMode == e.DateSelectionMode.Month ? t.DateTime.addMonths(s.value, n) : t.DateTime.addDays(s.value, n), s.selectAll(), i.preventDefault()
          }
        }), s.value = t.DateTime.newDate(), "INPUT" == s._orgTag) {
          var r = s._tbx.getAttribute("value");
          r && (s.value = t.Globalize.parseDate(r, "yyyy-MM-dd"))
        }
        return s.isRequired = !0, s.initialize(o), s
      }
      return __extends(n, i), Object.defineProperty(n.prototype, "value", {
        get: function() {
          return this._value
        },
        set: function(e) {
          if (t.DateTime.equals(this._value, e)) this._tbx.value = t.Globalize.format(e, this.format);
          else {
            var i = !this.isRequired || null == e && null == this.value;
            e = t.asDate(e, i), e = this._clamp(e), this._isValidDate(e) ? (this._tbx.value = e ? t.Globalize.format(e, this.format) : "", e == this._value || t.DateTime.equals(this._value, e) || (this._value = e, this.onValueChanged())) : this._tbx.value = e ? t.Globalize.format(this.value, this.format) : "", this._calChanging = !0, this._calendar.value = this.value, this._calChanging = !1, this.text != this._oldText && (this._oldText = this.text, this.onTextChanged())
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "text", {
        get: function() {
          return this._tbx.value
        },
        set: function(t) {
          t != this.text && (this._setText(t, !0), this._commitText())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "selectionMode", {
        get: function() {
          return this.calendar.selectionMode
        },
        set: function(t) {
          this.calendar.selectionMode = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "min", {
        get: function() {
          return this._calendar.min
        },
        set: function(e) {
          this._calendar.min = t.asDate(e, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "max", {
        get: function() {
          return this._calendar.max
        },
        set: function(e) {
          this._calendar.max = t.asDate(e, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "repeatButtons", {
        get: function() {
          return this.calendar.repeatButtons
        },
        set: function(e) {
          this.calendar.repeatButtons = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "showYearPicker", {
        get: function() {
          return this.calendar.showYearPicker
        },
        set: function(e) {
          this.calendar.showYearPicker = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "format", {
        get: function() {
          return this._format
        },
        set: function(e) {
          e != this.format && (this._format = t.asString(e), this._tbx.value = t.Globalize.format(this.value, this.format))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "mask", {
        get: function() {
          return this._msk.mask
        },
        set: function(e) {
          this._msk.mask = t.asString(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "calendar", {
        get: function() {
          return this._calendar
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "inputElement", {
        get: function() {
          return this._tbx
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "inputType", {
        get: function() {
          return this._tbx.type
        },
        set: function(e) {
          this._tbx.type = t.asString(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "itemValidator", {
        get: function() {
          return this._calendar.itemValidator
        },
        set: function(e) {
          e != this.itemValidator && (this._calendar.itemValidator = t.asFunction(e), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "itemFormatter", {
        get: function() {
          return this.calendar.itemFormatter
        },
        set: function(e) {
          e != this.itemFormatter && (this.calendar.itemFormatter = t.asFunction(e))
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.onValueChanged = function(t) {
        this.valueChanged.raise(this, t)
      }, n.prototype.refresh = function(e) {
        void 0 === e && (e = !0), i.prototype.refresh.call(this, e), this.isDroppedDown = !1, this._msk && this._msk.refresh(), this._calendar && this._calendar.refresh(), this._tbx.value = t.Globalize.format(this.value, this.format)
      }, n.prototype.onIsDroppedDownChanged = function(t) {
        i.prototype.onIsDroppedDownChanged.call(this, t), this.isDroppedDown && (this._calChanged = !1, this.dropDown.focus(), this._calendar.refresh())
      }, n.prototype._createDropDown = function() {
        var i = this;
        this._calendar = new e.Calendar(this._dropDown), this._calendar.valueChanged.addHandler(function() {
          i._calChanging || (i.value = t.DateTime.fromDateTime(i._calendar.value, i.value), i._calChanged = !0)
        }), this.addEventListener(this._dropDown, "click", function(e) {
          var n = e.target;
          t.closest(e.target, ".wj-header") && t.closest(e.target, ".wj-calendar-year") || (i._calChanged && !t.closest(n, ".wj-calendar-header") ? i.isDroppedDown = !1 : (n = t.closest(n, ".wj-btn")) && "btn-today" == n.getAttribute("wj-part") && (i.isDroppedDown = !1))
        })
      }, n.prototype._updateDropDown = function() {
        this._commitText();
        var t = this._calendar;
        t.value = this.value, t.min = this.min, t.max = this.max, this.selectionMode != e.DateSelectionMode.Month && (t.monthView = !0);
        var n = getComputedStyle(this.hostElement);
        this._dropDown.style.minWidth = 18 * parseFloat(n.fontSize) + "px", this._calendar.refresh(), i.prototype._updateDropDown.call(this)
      }, n.prototype._keydown = function(n) {
        if (!(n.defaultPrevented || n.altKey || n.ctrlKey || n.metaKey)) switch (n.keyCode) {
          case t.Key.Enter:
            this._commitText(), this.selectAll();
            break;
          case t.Key.Escape:
            this.text = t.Globalize.format(this.value, this.format), this.selectAll();
            break;
          case t.Key.Up:
          case t.Key.Down:
            if (!this.isDroppedDown && this.value && this._canChangeValue()) {
              var o = n.keyCode == t.Key.Up ? 1 : -1,
                s = this.selectionMode == e.DateSelectionMode.Month ? t.DateTime.addMonths(this.value, o) : t.DateTime.addDays(this.value, o);
              this.value = t.DateTime.fromDateTime(s, this.value), this.selectAll(), n.preventDefault()
            }
        }
        i.prototype._keydown.call(this, n)
      }, n.prototype._canChangeValue = function() {
        return !this.isReadOnly && this.selectionMode != e.DateSelectionMode.None
      }, n.prototype._clamp = function(t) {
        return this.calendar._clamp(t)
      }, n.prototype._commitText = function() {
        var e = this._tbx.value;
        if (e || this.isRequired) {
          var i = t.Globalize.parseDate(e, this.format, this.value);
          i ? this.value = t.DateTime.fromDateTime(i, this.value) : this._tbx.value = t.Globalize.format(this.value, this.format)
        } else this.value = null
      }, n.prototype._isValidDate = function(t) {
        if (t) {
          if (this._clamp(t) != t) return !1;
          if (this.itemValidator && !this.itemValidator(t)) return !1
        }
        return !0
      }, n
    }(e.DropDown);
    e.InputDate = i
  }(t.input || (t.input = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function(e) {
      function i(i, n) {
        var o = e.call(this, i) || this;
        if (o._format = "t", o.valueChanged = new t.Event, t.addClass(o.hostElement, "wj-inputtime"), o._value = t.DateTime.newDate(), o._msk = new t._MaskProvider(o._tbx), t.isIE9() || (o._tbx.type = "tel"), "INPUT" == o._orgTag) {
          var s = o._tbx.getAttribute("value");
          s && (o.value = t.Globalize.parseDate(s, "HH:mm:ss"))
        }
        return o.step = 15, o.autoExpandSelection = !0, o.initialize(n), o
      }
      return __extends(i, e), Object.defineProperty(i.prototype, "inputElement", {
        get: function() {
          return this._tbx
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "inputType", {
        get: function() {
          return this._tbx.type
        },
        set: function(e) {
          this._tbx.type = t.asString(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "value", {
        get: function() {
          return this._value
        },
        set: function(e) {
          (e = t.asDate(e, !this.isRequired)) && (e = this._clamp(e)), this._setText(e ? t.Globalize.format(e, this.format) : "", !0), this.selectedItem && this.selectedItem.value && (e = t.DateTime.fromDateTime(e, this.selectedItem.value)), e == this._value || t.DateTime.equals(e, this._value) || (this._value = e, this.onValueChanged())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "text", {
        get: function() {
          return this._tbx.value
        },
        set: function(t) {
          t != this.text && (this._setText(t, !0), this._commitText())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "min", {
        get: function() {
          return this._min
        },
        set: function(e) {
          this._min = t.asDate(e, !0), this.isDroppedDown = !1, this._updateItems()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "max", {
        get: function() {
          return this._max
        },
        set: function(e) {
          this._max = t.asDate(e, !0), this.isDroppedDown = !1, this._updateItems()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "step", {
        get: function() {
          return this._step
        },
        set: function(e) {
          this._step = t.asNumber(e, !0), this.isDroppedDown = !1, this._updateItems()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "format", {
        get: function() {
          return this._format
        },
        set: function(e) {
          e != this.format && (this._format = t.asString(e), this._tbx.value = t.Globalize.format(this.value, this.format), t.hasItems(this.collectionView) && this._updateItems())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "mask", {
        get: function() {
          return this._msk.mask
        },
        set: function(e) {
          this._msk.mask = t.asString(e)
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.onValueChanged = function(t) {
        this.valueChanged.raise(this, t)
      }, i.prototype.onItemsSourceChanged = function(t) {
        e.prototype.onItemsSourceChanged.call(this, t), this._hasCustomItems = null != this.itemsSource
      }, i.prototype.refresh = function(i) {
        void 0 === i && (i = !0), e.prototype.refresh.call(this, i), this.isDroppedDown = !1, this._msk.refresh(), this._tbx.value = t.Globalize.format(this.value, this.format), this._updateItems()
      }, i.prototype.onSelectedIndexChanged = function(i) {
        if (this.selectedIndex > -1 && !this._settingText) {
          var n = this.value ? this.value : t.DateTime.newDate(),
            o = null != this.selectedItem.value ? this.selectedItem.value : t.Globalize.parseDate(this.text, this.format, this.value);
          this.value = t.DateTime.fromDateTime(n, o)
        }
        e.prototype.onSelectedIndexChanged.call(this, i)
      }, i.prototype._clamp = function(e) {
        return null != this._min && this._getTime(e) < this._getTime(this._min) && (e = t.DateTime.fromDateTime(e, this._min)), null != this._max && this._getTime(e) > this._getTime(this._max) && (e = t.DateTime.fromDateTime(e, this._max)), e
      }, i.prototype._wheel = function(i) {
        if (!i.defaultPrevented && !this.isDroppedDown && !this.isReadOnly && this.containsFocus() && this.selectedIndex < 0 && this.value && t.isNumber(this.step) && this.step > 0) {
          var n = t.DateTime.addMinutes(this.value, this.step * t.clamp(i.deltaY, -1, 1));
          this.value = this._clamp(n), this.selectAll(), i.preventDefault()
        }
        e.prototype._wheel.call(this, i)
      }, i.prototype._updateInputSelection = function(i) {
        if (this._delKey) e.prototype._updateInputSelection.call(this, i);
        else {
          for (var n = this._tbx.value; i < n.length && !n[i].match(/[a-z0-9]/i);) i++;
          t.setSelectionRange(this._tbx, i, this._tbx.value.length)
        }
      }, i.prototype._updateItems = function() {
        if (!this._hasCustomItems) {
          var e = [],
            i = t.DateTime.newDate(0, 0, 0, 0, 0),
            n = t.DateTime.newDate(0, 0, 0, 23, 59, 59);
          if (this.min && i.setHours(this.min.getHours(), this.min.getMinutes(), this.min.getSeconds()), this.max && n.setHours(this.max.getHours(), this.max.getMinutes(), this.max.getSeconds()), t.isNumber(this.step) && this.step > 0)
            for (var o = i; o <= n; o = t.DateTime.addMinutes(o, this.step)) e.push({
              value: o,
              text: t.Globalize.format(o, this.format)
            });
          var s = this.value;
          this._settingText = !0, this.displayMemberPath = "text", this.selectedValuePath = "text", this.itemsSource = e, this._hasCustomItems = !1, this._settingText = !1, this.value = s
        }
      }, i.prototype._getTime = function(t) {
        return 3600 * t.getHours() + 60 * t.getMinutes() + t.getSeconds()
      }, i.prototype._keydown = function(i) {
        if (e.prototype._keydown.call(this, i), !i.defaultPrevented) switch (i.keyCode) {
          case t.Key.Enter:
            this.isDroppedDown || (this._commitText(), this.selectAll());
            break;
          case t.Key.Escape:
            this.text = t.Globalize.format(this.value, this.format), this.selectAll()
        }
      }, i.prototype._commitText = function() {
        if (this.text || this.isRequired) {
          var e = this.value ? t.Globalize.format(this.value, this.format) : null;
          if (this.text != e) {
            var i = this.selectedItem && this.selectedItem.value ? this.selectedItem.value : t.Globalize.parseDate(this.text, this.format, this.value);
            i ? this.value = t.DateTime.fromDateTime(this.value, i) : this.text = e
          }
        } else this.value = null
      }, i
    }(e.ComboBox);
    e.InputTime = i
  }(t.input || (t.input = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    t._addCultureInfo("InputDateTime", {
      ariaLabels: {
        tglDate: "Toggle Calendar",
        tglTime: "Toggle Time List"
      }
    });
    var i = function(i) {
      function n(n, o) {
        var s = i.call(this, n) || this;
        t.addClass(s.hostElement, "wj-inputdatetime"), s._btnTm = s.hostElement.querySelector('[wj-part="btn-tm"]');
        var r = t.culture.InputDateTime.ariaLabels;
        t.setAriaLabel(s._btn, r.tglDate), t.setAriaLabel(s._btnTm, r.tglTime), s.format = "g", s._inputTime = new e.InputTime(document.createElement("div"), {
          isDroppedDownChanging: function(t, e) {
            s._hadFocus = s.containsFocus()
          },
          isDroppedDownChanged: function(t, e) {
            s._hadFocus && !s.isDroppedDown && (s.isTouching ? s.hostElement.focus() : s.selectAll())
          },
          valueChanged: function(e, i) {
            s.value = t.DateTime.fromDateTime(s.value, e.value)
          }
        });
        var a = s._inputTime.dropDown,
          l = s.calendar.hostElement,
          h = s.addEventListener.bind(s),
          c = s._keydown.bind(s);
        return h(a, "keydown", c, !0), h(a, "blur", function() {
          s._updateFocusState()
        }, !0), h(s._btn, "mousedown", function(t) {
          s._setDropdown(l) && s._btnclick(t)
        }), h(s._btnTm, "mousedown", function(t) {
          s._setDropdown(a), s._btnclick(t)
        }), s.initialize(o), s
      }
      return __extends(n, i), Object.defineProperty(n.prototype, "timeMin", {
        get: function() {
          return this._inputTime.min
        },
        set: function(t) {
          this._inputTime.min = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "timeMax", {
        get: function() {
          return this._inputTime.max
        },
        set: function(t) {
          this._inputTime.max = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "timeFormat", {
        get: function() {
          return this._inputTime.format
        },
        set: function(t) {
          this._inputTime.format = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "timeStep", {
        get: function() {
          return this._inputTime.step
        },
        set: function(t) {
          this._inputTime.step = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "inputTime", {
        get: function() {
          return this._inputTime
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.dispose = function() {
        this._setDropdown(this.calendar.hostElement), i.prototype.dispose.call(this), this._inputTime.dispose()
      }, n.prototype.refresh = function(t) {
        void 0 === t && (t = !0), this._setDropdown(this.calendar.hostElement), this._inputTime.refresh(), i.prototype.refresh.call(this, t)
      }, n.prototype._updateBtn = function() {
        i.prototype._updateBtn.call(this), this._btnTm && (this._btnTm.tabIndex = this._btn.tabIndex, this._btnTm.parentElement.style.display = this._btn.style.display)
      }, n.prototype._clamp = function(t) {
        return t && (this.min && t < this.min && (t = this.min), this.max && t > this.max && (t = this.max)), t
      }, n.prototype._commitText = function() {
        var e = this._tbx.value;
        if (e || this.isRequired) {
          var i = t.Globalize.parseDate(e, this.format, this.value);
          i ? this.value = i : this._tbx.value = t.Globalize.format(this.value, this.format)
        } else this.value = null
      }, n.prototype._setDropdown = function(t) {
        if (this._dropDown == t) return !1;
        this.isDroppedDown && (this.isDroppedDown = !1);
        var e = this.dropDownCssClass;
        return this.dropDownCssClass = "", this._dropDown = t, this.dropDownCssClass = e, !0
      }, n.prototype._updateDropDown = function() {
        var t = this._inputTime;
        this._dropDown == t.dropDown ? (this._commitText(), i.prototype._updateDropDown.call(this), t.isRequired = this.isRequired, t.value = this.value, this.isDroppedDown && t.listBox.showSelection()) : i.prototype._updateDropDown.call(this)
      }, n.controlTemplate = '<div class="wj-template"><div class="wj-input"><div class="wj-input-group wj-input-btn-visible"><input wj-part="input" type="text" class="wj-form-control"/><span class="wj-input-group-btn"><button wj-part="btn" class="wj-btn wj-btn-default" tabindex="-1"><span class="wj-glyph-calendar"></span></button><button wj-part="btn-tm" class="wj-btn wj-btn-default" tabindex="-1"><span class="wj-glyph-clock"></span></button></span></div></div><div wj-part="dropdown" class="wj-content wj-dropdown-panel" style="display:none"></div></div>', n
    }(e.InputDate);
    e.InputDateTime = i
  }(t.input || (t.input = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    t._addCultureInfo("InputNumber", {
      ariaLabels: {
        incVal: "Increase Value",
        decVal: "Decrease Value"
      }
    });
    var i = function(e) {
      function i(i, n) {
        var o = e.call(this, i) || this;
        o._showBtn = !0, o._readOnly = !1, o.textChanged = new t.Event, o.valueChanged = new t.Event;
        var s = o.hostElement;
        t.setAttribute(s, "role", "spinbutton", !0);
        var r = o.getTemplate();
        o.applyTemplate("wj-control wj-content wj-inputnumber", r, {
          _tbx: "input",
          _btnUp: "btn-inc",
          _btnDn: "btn-dec"
        }, "input");
        var a = t.culture.InputNumber.ariaLabels;
        t.setAriaLabel(o._btnUp.querySelector("button"), a.incVal), t.setAriaLabel(o._btnDn.querySelector("button"), a.decVal), o._tbx.type.match(/number/i) && (o.inputType = "");
        var l = o._tbx;
        l.autocomplete = "off", l.spellcheck = !1, o._updateSymbols(), o.addEventListener(o._tbx, "compositionstart", function() {
          o._composing = !0
        }), o.addEventListener(o._tbx, "compositionend", function() {
          o._composing = !1, setTimeout(function() {
            var t = o.text;
            o._oldText.indexOf(o._chrPct) > -1 && t.indexOf(o._chrPct) < 0 && (t += o._chrPct), o._setText(t)
          })
        }), o.addEventListener(l, "keypress", o._keypress.bind(o)), o.addEventListener(l, "keydown", o._keydown.bind(o)), o.addEventListener(l, "input", o._input.bind(o));
        var h = o._clickSpinner.bind(o);
        return o.addEventListener(o._btnUp, "click", h), o.addEventListener(o._btnDn, "click", h), o._rptUp = new t._ClickRepeater(o._btnUp.querySelector("button")), o._rptDn = new t._ClickRepeater(o._btnDn.querySelector("button")), o.addEventListener(s, "wheel", function(e) {
          if (!e.defaultPrevented && !o.isReadOnly && o.containsFocus()) {
            var i = t.clamp(-e.deltaY, -1, 1);
            o._increment((o.step || 1) * i), setTimeout(function() {
              return o.selectAll()
            }), e.preventDefault()
          }
        }), o.value = 0, o.isRequired = !0, o.initialize(n), o
      }
      return __extends(i, e), Object.defineProperty(i.prototype, "inputElement", {
        get: function() {
          return this._tbx
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "inputType", {
        get: function() {
          return this._tbx.type
        },
        set: function(e) {
          this._tbx.type = t.asString(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "value", {
        get: function() {
          return this._value
        },
        set: function(e) {
          if (e != this._value)
            if (null == (e = t.asNumber(e, !this.isRequired || null == e && null == this._value))) this._setText("");
            else if (!isNaN(e)) {
              var i = t.Globalize.format(e, this.format);
              this._setText(i)
            }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "isRequired", {
        get: function() {
          return this._tbx.required
        },
        set: function(e) {
          this._tbx.required = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "isReadOnly", {
        get: function() {
          return this._readOnly
        },
        set: function(e) {
          this._readOnly = t.asBoolean(e), this.inputElement.readOnly = this._readOnly, t.toggleClass(this.hostElement, "wj-state-readonly", this.isReadOnly)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "min", {
        get: function() {
          return this._min
        },
        set: function(e) {
          e != this._min && (this._min = t.asNumber(e, !0), this._updateAria())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "max", {
        get: function() {
          return this._max
        },
        set: function(e) {
          e != this._max && (this._max = t.asNumber(e, !0), this._updateAria())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "step", {
        get: function() {
          return this._step
        },
        set: function(e) {
          this._step = t.asNumber(e, !0), this._updateBtn()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "format", {
        get: function() {
          return this._format
        },
        set: function(e) {
          e != this.format && (this._format = t.asString(e), this.refresh())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "text", {
        get: function() {
          return this._tbx.value
        },
        set: function(t) {
          t != this.text && (this._oldText = null, this._setText(t))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "placeholder", {
        get: function() {
          return this._tbx.placeholder
        },
        set: function(t) {
          this._tbx.placeholder = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "showSpinner", {
        get: function() {
          return this._showBtn
        },
        set: function(e) {
          this._showBtn = t.asBoolean(e), this._updateBtn()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "repeatButtons", {
        get: function() {
          return !this._rptUp.disabled
        },
        set: function(e) {
          this._rptUp.disabled = this._rptDn.disabled = !t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.selectAll = function() {
        var e = this._tbx;
        t.setSelectionRange(e, 0, e.value.length)
      }, i.prototype.clamp = function(e) {
        return t.clamp(e, this.min, this.max)
      }, i.prototype.onTextChanged = function(t) {
        this.textChanged.raise(this, t), this._updateState()
      }, i.prototype.onValueChanged = function(t) {
        this._updateAria(), this.valueChanged.raise(this, t)
      }, i.prototype.dispose = function() {
        this._rptUp.element = null, this._rptDn.element = null, e.prototype.dispose.call(this)
      }, i.prototype.onGotFocus = function(t) {
        this.isTouching || (this._tbx.focus(), this.selectAll()), e.prototype.onGotFocus.call(this, t)
      }, i.prototype.onLostFocus = function(i) {
        this._composing && (this._composing = !1, this._setText(this.text));
        var n = this.clamp(this.value),
          o = t.Globalize.format(n, this.format);
        this._setText(o), e.prototype.onLostFocus.call(this, i)
      }, i.prototype.refresh = function(i) {
        void 0 === i && (i = !0), e.prototype.refresh.call(this, i), this._updateSymbols();
        var n = t.Globalize.format(this.value, this.format);
        this._setText(n)
      }, i.prototype._updateSymbols = function() {
        var e = t.culture.Globalize.numberFormat,
          i = this.format ? this.format.match(/([a-z])(\d*)(,*)(.*)/i) : null;
        this._chrDec = e["."] || ".", this._chrNeg = e["-"] || "-", this._chrPls = e["+"] || "+", this._chrPct = e["%"] || "%", this._chrCur = i && i[4] ? i[4] : e.currency.symbol || "$", this._fmtSpc = i && i[1] ? i[1].toLowerCase() : "n", this._fmtPrc = i && i[2] ? parseInt(i[2]) : null, this._rxSym = new RegExp("^[%+\\-() \\" + this._chrDec + "\\" + this._chrCur + "\\" + this._chrNeg + "\\" + this._chrPls + "\\" + this._chrPct + "]*$"), this._rxNeg = new RegExp("(\\-|\\(|\\" + this._chrNeg + ")")
      }, i.prototype._isNumeric = function(t, e) {
        var i = t == this._chrDec || t >= "0" && t <= "9",
          n = "x" == this._fmtSpc;
        return !i && n && (i = t >= "a" && t <= "f" || t >= "A" && t <= "F"), i || e || n || (i = t == this._chrPls || t == this._chrNeg || "(" == t || ")" == t), i
      }, i.prototype._getInputRange = function(t) {
        for (var e = [0, 0], i = this.text, n = !1, o = 0; o < i.length; o++) this._isNumeric(i[o], t) && (n || (e[0] = o, n = !0), e[1] = o + 1);
        return e
      }, i.prototype._flipSign = function() {
        var t = this._getSelStartDigits();
        this.value *= -1, this._setSelStartDigits(t)
      }, i.prototype._getSelStartDigits = function() {
        for (var t = 0, e = this._tbx.selectionStart, i = this._tbx.value, n = 0; n < i.length && n < e; n++) this._isNumeric(i[n], !0) && t++;
        return t
      }, i.prototype._setSelStartDigits = function(e) {
        for (var i = this._tbx.value, n = 0; n < i.length && e >= 0; n++)
          if (this._isNumeric(i[n], !0)) {
            if (!e) {
              t.setSelectionRange(this._tbx, n);
              break
            }
            e--
          } else if (!e) {
            t.setSelectionRange(this._tbx, n);
            break
          }
      }, i.prototype._increment = function(e) {
        if (e) {
          var i = this.clamp(t.isNumber(this.value) ? this.value + e : 0),
            n = t.Globalize.format(i, this.format, !1, !1);
          this._setText(n)
        }
      }, i.prototype._updateBtn = function() {
        var e = this.showSpinner && !!this.step,
          i = e;
        t.setCss([this._btnUp, this._btnDn], {
          display: e ? "" : "none"
        }), t.toggleClass(this.hostElement, "wj-input-show-spinner", e), t.enable(this._btnUp, i), t.enable(this._btnDn, i), this._updateAria()
      }, i.prototype._setText = function(e) {
        if (!this._composing) {
          var i = this._tbx,
            n = this._rxNeg.test(e),
            o = this._delKey;
          if (e && this._rxSym.test(e) && (e = this.isRequired || !o ? "0" : ""), this._delKey = !1, o && 0 == this.value && !this.isRequired && (e = ""), !e) {
            if (!this.isRequired) return i.value = "", null != this._value && (this._value = null, this.onValueChanged()), this._oldText && (this._oldText = e, this.onTextChanged()), void this._updateBtn();
            e = "0"
          }
          var s = this._format || (e.indexOf(this._chrDec) > -1 ? "n2" : "n0"),
            r = t.Globalize.parseFloat(e, s);
          if (isNaN(r)) i.value = this._oldText;
          else {
            var a = t.Globalize.format(r, s, !1);
            n && r >= 0 && !o && (a = this._chrNeg + a), null != this._fmtPrc && "g" != this._fmtSpc || !o && i.value.match(/\.0?$/) && (a = e + ("0" == e ? this._chrDec : "")), i.value != a && ("g" == this._fmtSpc && i.value.match(a + "0*") && this.containsFocus() && i.selectionStart == i.value.length && (a = i.value), i.value = a, r = t.Globalize.parseFloat(a, this.format)), r != this._value && (this._value = r, this.onValueChanged()), this.text != this._oldText && (this._oldText = this.text, this.onTextChanged()), this._updateBtn()
          }
        }
      }, i.prototype._keypress = function(e) {
        if (!(e.defaultPrevented || this._composing || this.isReadOnly) && e.charCode && !e.ctrlKey && !e.metaKey) {
          var i = this._tbx,
            n = String.fromCharCode(e.charCode);
          if (this._isNumeric(n, !1)) {
            var o = this._getInputRange(!0),
              s = i.selectionStart,
              r = i.selectionEnd;
            if (s < o[0] && r < i.value.length && (r = Math.max(r, o[0]), t.setSelectionRange(i, o[0], r)), s >= o[1]) {
              var a = null != this._fmtPrc ? this._fmtPrc : 2,
                l = i.value.indexOf(this._chrDec);
              l > -1 && s - l > a && e.preventDefault()
            }
          } else e.preventDefault();
          switch (n) {
            case "-":
            case this._chrNeg:
              this.min >= 0 ? this.value < 0 && this._flipSign() : this.value && i.selectionStart == i.selectionEnd ? this._flipSign() : this.clamp(-1) < 0 && (i.value = this._chrNeg, t.setSelectionRange(i, 1)), e.preventDefault();
              break;
            case "+":
            case this._chrPls:
              this.value < 0 && this._flipSign(), e.preventDefault();
              break;
            case this._chrDec:
              if (0 == this._fmtPrc) e.preventDefault();
              else {
                var h = i.value.indexOf(n);
                h > -1 && (i.selectionStart <= h && h++, t.setSelectionRange(i, h), e.preventDefault())
              }
          }
        }
      }, i.prototype._keydown = function(e) {
        var i = this;
        if (this._delKey = !1, !e.defaultPrevented && !this._composing) {
          var n = this._tbx,
            o = n.value,
            s = n.selectionStart,
            r = n.selectionEnd;
          switch (e.keyCode) {
            case 65:
              e.ctrlKey && (setTimeout(function() {
                i.selectAll()
              }), e.preventDefault());
              break;
            case t.Key.Up:
            case t.Key.Down:
              this.step && !this.isReadOnly && (this._increment(this.step * (e.keyCode == t.Key.Up ? 1 : -1)), setTimeout(function() {
                i.selectAll()
              }), e.preventDefault());
              break;
            case t.Key.Back:
              if (this._delKey = !0, r - s < 2 && !this.isReadOnly) {
                var a = o[r - 1];
                a != this._chrDec && a != this._chrPct && ")" != a || (setTimeout(function() {
                  r = a == i._chrPct ? i._getInputRange(!0)[1] : r - 1, t.setSelectionRange(n, r)
                }), e.preventDefault())
              }
              break;
            case t.Key.Delete:
              if (this._delKey = !0, r - s < 2 && !this.isReadOnly)
                if ("0" == o && 1 == s) t.setSelectionRange(n, 0);
                else {
                  var l = o[s];
                  l != this._chrDec && l != this._chrPct || (setTimeout(function() {
                    t.setSelectionRange(n, s + 1)
                  }), e.preventDefault())
                }
          }
        }
      }, i.prototype._input = function(e) {
        var i = this;
        this._composing || setTimeout(function() {
          var e = i._tbx,
            n = e.value,
            o = n.indexOf(i._chrDec),
            s = e.selectionStart,
            r = i._getSelStartDigits();
          if ("p" == i._fmtSpc && n.length && n.indexOf(i._chrPct) < 0 && (n += i._chrPct), i._setText(n), i.containsFocus()) {
            var a = e.value,
              l = a.indexOf(i._chrDec),
              h = i._getInputRange(!0);
            if (n == i._chrNeg + i._chrDec && l > -1) return void t.setSelectionRange(e, l + 1);
            if (n[0] == i._chrNeg && a[0] != i._chrNeg) return void(1 == a.length ? t.setSelectionRange(e, 1) : i._setSelStartDigits(r));
            n ? n == i._chrDec && l > -1 ? s = l + 1 : s <= o && l > -1 || o < 0 && l < 0 ? s += a.length - n.length : o < 0 && l > -1 && (s = l) : s = l > -1 ? l : h[1], s = t.clamp(s, h[0], h[1]), t.setSelectionRange(e, s)
          }
        })
      }, i.prototype._clickSpinner = function(e) {
        var i = this;
        e.defaultPrevented || this.isReadOnly || !this.step || (this._increment(this.step * (t.contains(this._btnUp, e.target) ? 1 : -1)), this.isTouching || setTimeout(function() {
          return i.selectAll()
        }))
      }, i.prototype._updateAria = function() {
        var e = this.hostElement;
        e && (t.setAttribute(e, "aria-valuemin", this.min), t.setAttribute(e, "aria-valuemax", this.max), t.setAttribute(e, "aria-valuenow", this.value))
      }, i.controlTemplate = '<div class="wj-input"><div class="wj-input-group"><span wj-part="btn-dec" class="wj-input-group-btn" tabindex="-1"><button class="wj-btn wj-btn-default" tabindex="-1">-</button></span><input type="tel" wj-part="input" class="wj-form-control wj-numeric"/><span wj-part="btn-inc" class="wj-input-group-btn" tabindex="-1"><button class="wj-btn wj-btn-default" tabindex="-1">+</button></span></div></div>', i
    }(t.Control);
    e.InputNumber = i
  }(t.input || (t.input = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function(e) {
      function i(i, n) {
        var o = e.call(this, i) || this;
        o.valueChanged = new t.Event;
        var s = o.getTemplate();
        if (o.applyTemplate("wj-control wj-content wj-inputmask", s, {
          _tbx: "input"
        }, "input"), "INPUT" == o._orgTag) {
          var r = o._tbx.getAttribute("value");
          r && (o.value = r)
        }
        return o._msk = new t._MaskProvider(o._tbx), o.isRequired = !0, o.initialize(n), o.addEventListener(o._tbx, "input", function() {
          setTimeout(function() {
            o.onValueChanged()
          })
        }), o
      }
      return __extends(i, e), Object.defineProperty(i.prototype, "inputElement", {
        get: function() {
          return this._tbx
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "value", {
        get: function() {
          return this._tbx.value
        },
        set: function(e) {
          e != this.value && (this._tbx.value = t.asString(e), e = this._msk._applyMask(), this._tbx.value = e, this.onValueChanged())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "rawValue", {
        get: function() {
          return this._msk.getRawValue()
        },
        set: function(e) {
          e != this.rawValue && (this.value = t.asString(e))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "mask", {
        get: function() {
          return this._msk.mask
        },
        set: function(e) {
          var i = this.value;
          this._msk.mask = t.asString(e), this.value != i && this.onValueChanged()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "promptChar", {
        get: function() {
          return this._msk.promptChar
        },
        set: function(t) {
          var e = this.value;
          this._msk.promptChar = t, this.value != e && this.onValueChanged()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "placeholder", {
        get: function() {
          return this._tbx.placeholder
        },
        set: function(t) {
          this._tbx.placeholder = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "maskFull", {
        get: function() {
          return this._msk.maskFull
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "isRequired", {
        get: function() {
          return this._tbx.required
        },
        set: function(e) {
          this._tbx.required = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "isReadOnly", {
        get: function() {
          return this._tbx.readOnly
        },
        set: function(e) {
          this._tbx.readOnly = t.asBoolean(e), t.toggleClass(this.hostElement, "wj-state-readonly", this.isReadOnly)
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.selectAll = function() {
        var e = this._msk.getMaskRange();
        t.setSelectionRange(this._tbx, e[0], e[1] + 1)
      }, i.prototype.onValueChanged = function(t) {
        this.value != this._oldValue && (this._oldValue = this.value, this.valueChanged.raise(this, t)), this._updateState()
      }, i.prototype._updateState = function() {
        e.prototype._updateState.call(this);
        var i = !this._pristine && this._tbx.value && !this.maskFull;
        t.toggleClass(this.hostElement, "wj-state-invalid", i)
      }, i.prototype.dispose = function() {
        this._msk.input = null, e.prototype.dispose.call(this)
      }, i.prototype.refresh = function(t) {
        void 0 === t && (t = !0), e.prototype.refresh.call(this, t), this._msk.refresh()
      }, i.prototype.onGotFocus = function(t) {
        e.prototype.onGotFocus.call(this, t), this.selectAll()
      }, i.controlTemplate = '<div class="wj-input"><div class="wj-input-group"><input wj-part="input" class="wj-form-control"/></div></div>', i
    }(t.Control);
    e.InputMask = i
  }(t.input || (t.input = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function(i) {
      function n(e, n) {
        var o = i.call(this, e) || this;
        o.valueChanged = new t.Event, t.addClass(o.hostElement, "wj-inputcolor");
        return o._ePreview = t.createElement('<div class="wj-inputcolorbox"></div>', o.hostElement.firstElementChild), "INPUT" == o._orgTag && (o._tbx.type = "", o._commitText()), o.value = "#ffffff", o.isRequired = !0, o.initialize(n), o.addEventListener(o._colorPicker.hostElement, "click", function(e) {
          var i = e.target;
          i && "DIV" == i.tagName && (t.closest(i, '[wj-part="div-pal"]') || t.closest(i, '[wj-part="div-pv"]')) && i.style.backgroundColor && (o.isDroppedDown = !1)
        }), o
      }
      return __extends(n, i), Object.defineProperty(n.prototype, "value", {
        get: function() {
          return this._value
        },
        set: function(e) {
          e != this.value && (!e && this.isRequired || (this.text = t.asString(e)))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "text", {
        get: function() {
          return this._tbx.value
        },
        set: function(e) {
          e != this.text && (this._setText(t.asString(e), !0), this._commitText())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "showAlphaChannel", {
        get: function() {
          return this._colorPicker.showAlphaChannel
        },
        set: function(t) {
          this._colorPicker.showAlphaChannel = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "palette", {
        get: function() {
          return this._colorPicker.palette
        },
        set: function(t) {
          this._colorPicker.palette = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "colorPicker", {
        get: function() {
          return this._colorPicker
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.onValueChanged = function(t) {
        this.valueChanged.raise(this, t)
      }, n.prototype.onIsDroppedDownChanged = function(t) {
        i.prototype.onIsDroppedDownChanged.call(this, t), this.isDroppedDown && !this.isTouching && this.selectAll()
      }, n.prototype._createDropDown = function() {
        var i = this;
        this._colorPicker = new e.ColorPicker(this._dropDown), t.setCss(this._dropDown, {
          minWidth: 420,
          minHeight: 200
        }), this._colorPicker.valueChanged.addHandler(function() {
          i.value = i._colorPicker.value
        })
      }, n.prototype._keydown = function(e) {
        if (!e.defaultPrevented) switch (e.keyCode) {
          case t.Key.Enter:
            this._commitText(), this.selectAll();
            break;
          case t.Key.Escape:
            this.text = this.value, this.selectAll()
        }
        i.prototype._keydown.call(this, e)
      }, n.prototype._commitText = function() {
        if (this.value != this.text) {
          if (!this.isRequired && !this.text) return this._value = this.text, void(this._ePreview.style.backgroundColor = "");
          t.Color.fromString(this.text) ? (this._colorPicker.value = this.text, this._value = this._colorPicker.value, this._ePreview.style.backgroundColor = this.value, this.onValueChanged()) : this.text = this._value ? this._value : ""
        }
      }, n
    }(e.DropDown);
    e.InputColor = i
  }(t.input || (t.input = {}))
}(wijmo || (wijmo = {}));
