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
    var i;
    ! function(t) {
      t[t.None = 0] = "None", t[t.Value = 1] = "Value", t[t.MinMax = 2] = "MinMax", t[t.All = 3] = "All"
    }(i = e.ShowText || (e.ShowText = {}));
    var n = function(n) {
      function s(r, a) {
        var o = n.call(this, r, null, !0) || this;
        o._ranges = new t.collections.ObservableArray, o._rngElements = [], o._format = "n0", o._showRanges = !0, o._stackRanges = !1, o._shadow = !0, o._animated = !0, o._readOnly = !0, o._step = 1, o._showText = i.None, o._showTicks = !1, o._thickness = .8, o._initialized = !1, o.valueChanged = new t.Event, o._getPercent = function(t) {
          var e = this.max > this.min ? (t - this.min) / (this.max - this.min) : 0;
          return Math.max(0, Math.min(1, e))
        }, s._ctr++;
        var h = o.hostElement;
        t.setAttribute(h, "role", "slider", !0);
        var u = o.getTemplate();
        return o.applyTemplate("wj-control wj-gauge", u, {
          _dSvg: "dsvg",
          _svg: "svg",
          _filter: "filter",
          _gFace: "gface",
          _gRanges: "granges",
          _gPointer: "gpointer",
          _gCover: "gcover",
          _pFace: "pface",
          _pPointer: "ppointer",
          _cValue: "cvalue",
          _tValue: "value",
          _tMin: "min",
          _tMax: "max",
          _pTicks: "pticks"
        }), o._filterID = "wj-gauge-filter-" + s._ctr.toString(36), o._filter.setAttribute("id", o._filterID), o.face = new e.Range, o.pointer = new e.Range, o._ranges.collectionChanged.addHandler(function() {
          for (var i = o._ranges, n = 0; n < i.length; n++)
            if (!t.tryCast(i[n], e.Range)) throw "ranges array must contain Range objects.";
          o._rangesDirty = !0, o.invalidate()
        }), o.addEventListener(h, "keydown", o._keydown.bind(o)), o.addEventListener(h, "click", function(t) {
          0 == t.button && (o.focus(), o._applyMouseValue(t))
        }), o.addEventListener(h, "mousedown", function(t) {
          0 == t.button && (o.focus(), o._dragging = !0, o._applyMouseValue(t))
        }), o.addEventListener(h, "mousemove", function(t) {
          o._dragging && o.containsFocus() && o._applyMouseValue(t, !0)
        }), o.addEventListener(h, "mouseup", function(t) {
          o._dragging = !1
        }), o.addEventListener(h, "mouseleave", function(t) {
          t.target == h && (o._dragging = !1)
        }), "ontouchstart" in window && (o.addEventListener(h, "touchstart", function(t) {
          o.focus(), !t.defaultPrevented && o._applyMouseValue(t, !1) && t.preventDefault()
        }), o.addEventListener(h, "touchmove", function(t) {
          !t.defaultPrevented && o._applyMouseValue(t, !0) && t.preventDefault()
        })), o.addEventListener(h, "wheel", function(e) {
          if (!e.defaultPrevented && !o.isReadOnly && o.containsFocus() && null != o.value && o.hitTest(e)) {
            var i = t.clamp(-e.deltaY, -1, 1);
            o.value = t.clamp(o.value + (o.step || 1) * i, o.min, o.max), e.preventDefault()
          }
        }), o.isReadOnly = !0, o.initialize(a), o.invalidate(), o
      }
      return __extends(s, n), Object.defineProperty(s.prototype, "value", {
        get: function() {
          return this._pointer.max
        },
        set: function(e) {
          e != this.value && (this._pointer.max = t.asNumber(e, !0), this._updateAria())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "min", {
        get: function() {
          return this._face.min
        },
        set: function(e) {
          e != this.min && (this._face.min = t.asNumber(e), this._updateAria())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "max", {
        get: function() {
          return this._face.max
        },
        set: function(e) {
          e != this.max && (this._face.max = t.asNumber(e), this._updateAria())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "origin", {
        get: function() {
          return this._origin
        },
        set: function(e) {
          e != this._origin && (this._origin = t.asNumber(e, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "isReadOnly", {
        get: function() {
          return this._readOnly
        },
        set: function(e) {
          this._readOnly = t.asBoolean(e), this._setAttribute(this._svg, "cursor", this._readOnly ? null : "pointer"), t.toggleClass(this.hostElement, "wj-state-readonly", this.isReadOnly)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "step", {
        get: function() {
          return this._step
        },
        set: function(e) {
          e != this._step && (this._step = t.asNumber(e, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "format", {
        get: function() {
          return this._format
        },
        set: function(e) {
          e != this._format && (this._format = t.asString(e), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "getText", {
        get: function() {
          return this._getText
        },
        set: function(e) {
          e != this._getText && (this._getText = t.asFunction(e), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "thickness", {
        get: function() {
          return this._thickness
        },
        set: function(e) {
          e != this._thickness && (this._thickness = t.clamp(t.asNumber(e, !1), 0, 1), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "face", {
        get: function() {
          return this._face
        },
        set: function(i) {
          i != this._face && (this._face && this._face.propertyChanged.removeHandler(this._rangeChanged), this._face = t.asType(i, e.Range), this._face && this._face.propertyChanged.addHandler(this._rangeChanged, this), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "pointer", {
        get: function() {
          return this._pointer
        },
        set: function(i) {
          if (i != this._pointer) {
            var n = null;
            this._pointer && (n = this.value, this._pointer.propertyChanged.removeHandler(this._rangeChanged)), this._pointer = t.asType(i, e.Range), this._pointer && (n && (this.value = n), this._pointer.propertyChanged.addHandler(this._rangeChanged, this)), this.invalidate()
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "showText", {
        get: function() {
          return this._showText
        },
        set: function(e) {
          (e = t.asEnum(e, i)) != this._showText && (this._showText = e, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "showTicks", {
        get: function() {
          return this._showTicks
        },
        set: function(e) {
          e != this._showTicks && (this._showTicks = t.asBoolean(e), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "tickSpacing", {
        get: function() {
          return this._tickSpacing
        },
        set: function(e) {
          e != this._tickSpacing && (this._tickSpacing = t.asNumber(e, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "thumbSize", {
        get: function() {
          return this._thumbSize
        },
        set: function(e) {
          e != this._thumbSize && (this._thumbSize = t.asNumber(e, !0, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "showRanges", {
        get: function() {
          return this._showRanges
        },
        set: function(e) {
          e != this._showRanges && (this._showRanges = t.asBoolean(e), this._animColor = null, this._rangesDirty = !0, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "stackRanges", {
        get: function() {
          return this._stackRanges
        },
        set: function(e) {
          e != this._stackRanges && (this._stackRanges = t.asBoolean(e), this._animColor = null, this._rangesDirty = !0, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "hasShadow", {
        get: function() {
          return this._shadow
        },
        set: function(e) {
          e != this._shadow && (this._shadow = t.asBoolean(e), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "isAnimated", {
        get: function() {
          return this._animated
        },
        set: function(e) {
          this._animated = t.asBoolean(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(s.prototype, "ranges", {
        get: function() {
          return this._ranges
        },
        enumerable: !0,
        configurable: !0
      }), s.prototype.onValueChanged = function(t) {
        this.valueChanged.raise(this, t)
      }, s.prototype.refresh = function(t) {
        if (void 0 === t && (t = !0), n.prototype.refresh.call(this, t), this._rangesDirty) {
          this._rangesDirty = !1;
          for (var e = this._gRanges, s = 0; s < this._rngElements.length; s++) this._rngElements[s].rng.propertyChanged.removeHandler(this._rangeChanged);
          for (; e.lastChild;) e.removeChild(e.lastChild);
          if (this._rngElements = [], this._showRanges)
            for (s = 0; s < this.ranges.length; s++) {
              var r = this.ranges[s];
              r.propertyChanged.addHandler(this._rangeChanged, this), this._rngElements.push({
                rng: r,
                el: this._createElement("path", e)
              })
            }
        }
        this._showElement(this._tValue, 0 != (this.showText & i.Value)), this._showElement(this._tMin, 0 != (this.showText & i.MinMax)), this._showElement(this._tMax, 0 != (this.showText & i.MinMax)), this._showElement(this._cValue, 0 != (this.showText & i.Value) || this._thumbSize > 0), this._updateText();
        var a = this._getFilterUrl();
        this._setAttribute(this._pFace, "filter", a), this._setAttribute(this._pPointer, "filter", a), this._updateRange(this._face), this._updateRange(this._pointer), this._updateTicks();
        for (s = 0; s < this.ranges.length; s++) this._updateRange(this.ranges[s]);
        this._initialized = !0
      }, s.prototype.hitTest = function(e, i) {
        t.isNumber(e) && t.isNumber(i) ? e = new t.Point(e, i) : e instanceof t.Point || (e = t.mouseToPage(e)), e = t.asType(e, t.Point);
        var n = t.Rect.fromBoundingRect(this._dSvg.getBoundingClientRect());
        return e.x -= n.left + pageXOffset, e.y -= n.top + pageYOffset, this._getValueFromPoint(e)
      }, s._getBBox = function(t) {
        try {
          return t.getBBox()
        } catch (t) {
          return {
            x: 0,
            y: 0,
            width: 0,
            height: 0
          }
        }
      }, s.prototype._getFilterUrl = function() {
        return this.hasShadow ? "url(#" + this._filterID + ")" : null
      }, s.prototype._getRangeElement = function(t) {
        if (t == this._face) return this._pFace;
        if (t == this._pointer) return this._pPointer;
        for (var e = 0; e < this._rngElements.length; e++) {
          var i = this._rngElements[e];
          if (i.rng == t) return i.el
        }
        return null
      }, s.prototype._rangeChanged = function(e, i) {
        var n = this;
        if (e == this._pointer && "max" == i.propertyName && (this.onValueChanged(), this._updateText()), e != this._face)
          if (e == this._pointer && "max" == i.propertyName && (this._animInterval && clearInterval(this._animInterval), this.isAnimated && !this.isUpdating && this._initialized)) {
            var s = this._getPointerColor(i.oldValue),
              r = this._getPointerColor(i.newValue),
              a = s ? new t.Color(s) : null,
              o = r ? new t.Color(r) : null,
              h = t.clamp(Math.abs(i.newValue - i.oldValue) / (this.max - this.min), 0, 1);
            this._animInterval = t.animate(function(s) {
              n._animColor = a && o ? t.Color.interpolate(a, o, s).toString() : null, n._updateRange(e, i.oldValue + s * (i.newValue - i.oldValue)), s >= 1 && (n._animColor = null, n._animInterval = null, n._updateRange(e), n._updateText())
            }, h * t.Control._ANIM_DEF_DURATION)
          } else this._updateRange(e);
        else this.invalidate()
      }, s.prototype._createElement = function(t, e, i) {
        var n = document.createElementNS(s._SVGNS, t);
        return i && n.setAttribute("class", i), e.appendChild(n), n
      }, s.prototype._centerText = function(e, i, n) {
        if ("none" != e.getAttribute("display")) {
          var r = t.Globalize.format(i, this.format);
          if (t.isFunction(this.getText)) {
            var a = e == this._tValue ? "value" : e == this._tMin ? "min" : e == this._tMax ? "max" : null;
            t.assert(null != a, "unknown element"), r = this.getText(this, a, i, r)
          }
          e.textContent = r;
          var o = t.Rect.fromBoundingRect(s._getBBox(e)),
            h = n.x - o.width / 2,
            u = n.y + o.height / 4;
          e.setAttribute("x", this._fix(h)), e.setAttribute("y", this._fix(u))
        }
      }, s.prototype._copy = function(i, n) {
        if ("ranges" == i) {
          for (var s = t.asArray(n), r = 0; r < s.length; r++) {
            var a = new e.Range;
            t.copy(a, s[r]), this.ranges.push(a)
          }
          return !0
        }
        return "pointer" == i && (t.copy(this.pointer, n), !0)
      }, s.prototype._showElement = function(t, e) {
        this._setAttribute(t, "display", e ? "" : "none")
      }, s.prototype._setAttribute = function(t, e, i) {
        i ? t.setAttribute(e, i) : t.removeAttribute(e)
      }, s.prototype._updateRange = function(t, e) {
        void 0 === e && (e = t.max), t == this._pointer && (t.min = null != this.origin ? this.origin : this.min < 0 && this.max > 0 ? 0 : this.min);
        var i = this._getRangeElement(t);
        if (i) {
          this._updateRangeElement(i, t, e);
          var n = t.color;
          t == this._pointer && (n = this._animColor ? this._animColor : this._getPointerColor(t.max)), this._setAttribute(i, "style", n ? "fill:" + n : null)
        }
      }, s.prototype._getPointerColor = function(t) {
        if (!this._showRanges) {
          for (var e, i = 0; i < this._ranges.length; i++) {
            var n = this._ranges[i];
            if (t >= n.min && t <= n.max) {
              e = n;
              break
            }
          }
          if (e) return e.color
        }
        return this._pointer.color
      }, s.prototype._keydown = function(e) {
        if (!this._readOnly && this._step) {
          var i = !0;
          switch (this._getKey(e.keyCode)) {
            case t.Key.Left:
            case t.Key.Down:
              this.value = t.clamp(this.value - this._step, this.min, this.max);
              break;
            case t.Key.Right:
            case t.Key.Up:
              this.value = t.clamp(this.value + this._step, this.min, this.max);
              break;
            case t.Key.Home:
              this.value = this.min;
              break;
            case t.Key.End:
              this.value = this.max;
              break;
            default:
              i = !1
          }
          i && e.preventDefault()
        }
      }, s.prototype._getKey = function(t) {
        return t
      }, s.prototype._applyMouseValue = function(e, i) {
        if (!this.isReadOnly && this.containsFocus()) {
          var n = this.hitTest(e),
            s = this._animated,
            r = this._step;
          if (null != n) return i && (this._animated = !1), r && (n = Math.round(n / r) * r), this.value = t.clamp(n, this.min, this.max), this._animated = s, !0
        }
        return !1
      }, s.prototype._updateRangeElement = function(e, i, n) {
        t.assert(!1, "Gauge is an abstract class.")
      }, s.prototype._updateText = function() {
        t.assert(!1, "Gauge is an abstract class.")
      }, s.prototype._updateTicks = function() {
        t.assert(!1, "Gauge is an abstract class.")
      }, s.prototype._getValueFromPoint = function(t) {
        return null
      }, s.prototype._fix = function(e) {
        return t.isNumber(e) ? parseFloat(e.toFixed(4)).toString() : this._fix(e.x) + " " + this._fix(e.y)
      }, s.prototype._updateAria = function() {
        var e = this.hostElement;
        e && (t.setAttribute(e, "aria-valuemin", this.min), t.setAttribute(e, "aria-valuemax", this.max), t.setAttribute(e, "aria-valuenow", this.value))
      }, s._SVGNS = "http://www.w3.org/2000/svg", s._ctr = 0, s.controlTemplate = '<div wj-part="dsvg" style="width:100%;height:100%"><svg wj-part="svg" width="100%" height="100%" style="overflow:visible"><defs><filter wj-part="filter"><feOffset dx="3" dy="3"></feOffset><feGaussianBlur result="offset-blur" stdDeviation="5"></feGaussianBlur><feComposite operator="out" in="SourceGraphic" in2="offset-blur" result="inverse"></feComposite><feFlood flood-color="black" flood-opacity="0.2" result="color"></feFlood><feComposite operator="in" in="color" in2="inverse" result="shadow"></feComposite><feComposite operator="over" in="shadow" in2="SourceGraphic"></feComposite></filter></defs><g wj-part="gface" class="wj-face" style="cursor:inherit"><path wj-part="pface"/></g><g wj-part="granges" class="wj-ranges" style="cursor:inherit"/><g wj-part="gpointer" class="wj-pointer" style="cursor:inherit"><path wj-part="ppointer"/></g><g wj-part="gcover" class="wj-cover" style="cursor:inherit"><path wj-part="pticks" class="wj-ticks"/><circle wj-part="cvalue" class="wj-pointer wj-thumb"/><text wj-part="value" class="wj-value"/><text wj-part="min" class="wj-min" aria-hidden="true"/><text wj-part="max" class="wj-max" aria-hidden="true"/></g></svg></div>', s
    }(t.Control);
    e.Gauge = n
  }(t.gauge || (t.gauge = {}))
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
      t[t.Right = 0] = "Right", t[t.Left = 1] = "Left", t[t.Up = 2] = "Up", t[t.Down = 3] = "Down"
    }(i = e.GaugeDirection || (e.GaugeDirection = {}));
    var n = function(n) {
      function s(e, s) {
        var r = n.call(this, e, null) || this;
        return r._direction = i.Right, t.addClass(r.hostElement, "wj-lineargauge"), r.initialize(s), r
      }
      return __extends(s, n), Object.defineProperty(s.prototype, "direction", {
        get: function() {
          return this._direction
        },
        set: function(e) {
          (e = t.asEnum(e, i)) != this._direction && (this._direction = e, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), s.prototype._updateRangeElement = function(n, s, r) {
        var a = this._getRangeRect(s, r);
        this._updateSegment(n, a);
        var o = s == this._pointer && 0 != (this.showText & e.ShowText.Value),
          h = o || s == this._pointer && this.thumbSize > 0,
          u = a.left + a.width / 2,
          c = a.top + a.height / 2;
        switch (this._getDirection()) {
          case i.Right:
            u = a.right;
            break;
          case i.Left:
            u = a.left;
            break;
          case i.Up:
            c = a.top;
            break;
          case i.Down:
            c = a.bottom
        }
        if (o && this._centerText(this._tValue, r, new t.Point(u, c)), o || h) {
          a = t.Rect.fromBoundingRect(e.Gauge._getBBox(this._tValue));
          var l = this._animColor ? this._animColor : this._getPointerColor(s.max),
            g = null != this.thumbSize ? this.thumbSize / 2 : .8 * Math.max(a.width, a.height),
            _ = this._cValue;
          this._setAttribute(_, "cx", this._fix(u)), this._setAttribute(_, "cy", this._fix(c)), this._setAttribute(_, "style", l ? "fill:" + l : null), this._setAttribute(_, "r", this._fix(g))
        }
      }, s.prototype._updateText = function() {
        var t = this._getRangeRect(this._face);
        switch (this._getDirection()) {
          case i.Right:
            this._setText(this._tMin, this.min, t, "left"), this._setText(this._tMax, this.max, t, "right");
            break;
          case i.Left:
            this._setText(this._tMin, this.min, t, "right"), this._setText(this._tMax, this.max, t, "left");
            break;
          case i.Up:
            this._setText(this._tMin, this.min, t, "bottom"), this._setText(this._tMax, this.max, t, "top");
            break;
          case i.Down:
            this._setText(this._tMin, this.min, t, "top"), this._setText(this._tMax, this.max, t, "bottom")
        }
      }, s.prototype._updateTicks = function() {
        var t = this.tickSpacing && this.tickSpacing > 0 ? this.tickSpacing : this.step,
          e = "";
        if (this.showTicks && t > 0)
          for (var n = this._getRangeRect(this._face), s = void 0, r = this.min + t; r < this.max; r += t) switch (this._getDirection()) {
            case i.Right:
              e += "M " + (s = this._fix(n.left + n.width * this._getPercent(r))) + " " + this._fix(n.top) + " L " + s + " " + this._fix(n.bottom) + " ";
              break;
            case i.Left:
              e += "M " + (s = this._fix(n.right - n.width * this._getPercent(r))) + " " + n.top.toFixed(2) + " L " + s + " " + n.bottom.toFixed(2) + " ";
              break;
            case i.Up:
              s = (n.bottom - n.height * this._getPercent(r)).toFixed(2), e += "M " + this._fix(n.left) + " " + s + " L " + this._fix(n.right) + " " + s + " ";
              break;
            case i.Down:
              s = (n.top + n.height * this._getPercent(r)).toFixed(2), e += "M " + n.left.toFixed(2) + " " + s + " L " + n.right.toFixed(2) + " " + s + " "
          }
        this._pTicks.setAttribute("d", e)
      }, s.prototype._updateSegment = function(e, i) {
        var n = {
            p1: this._fix(new t.Point(i.left, i.top)),
            p2: this._fix(new t.Point(i.right, i.top)),
            p3: this._fix(new t.Point(i.right, i.bottom)),
            p4: this._fix(new t.Point(i.left, i.bottom))
          },
          s = t.format("M {p1} L {p2} L {p3} L {p4} Z", n);
        e.setAttribute("d", s)
      }, s.prototype._setText = function(i, n, s, r) {
        if ("none" != i.getAttribute("display")) {
          var a = t.Globalize.format(n, this.format);
          if (t.isFunction(this.getText)) {
            var o = i == this._tValue ? "value" : i == this._tMin ? "min" : i == this._tMax ? "max" : null;
            t.assert(null != o, "unknown element"), a = this.getText(this, o, n, a)
          }
          i.textContent = a;
          var h = t.Rect.fromBoundingRect(e.Gauge._getBBox(i)),
            u = new t.Point(s.left + s.width / 2 - h.width / 2, s.top + s.height / 2 + h.height / 2);
          switch (r) {
            case "top":
              u.y = s.top - 4;
              break;
            case "left":
              u.x = s.left - 4 - h.width;
              break;
            case "right":
              u.x = s.right + 4;
              break;
            case "bottom":
              u.y = s.bottom + 4 + h.height
          }
          i.setAttribute("x", this._fix(u.x)), i.setAttribute("y", this._fix(u.y))
        }
      }, s.prototype._getRangeRect = function(n, s) {
        void 0 === s && (s = n.max);
        var r = this.hostElement,
          a = new t.Rect(0, 0, r.clientWidth, r.clientHeight),
          o = this._getDirection(),
          h = this.thumbSize ? Math.ceil(this.thumbSize / 2) : 0;
        if (this.showText != e.ShowText.None) {
          var u = parseInt(getComputedStyle(r).fontSize);
          isNaN(u) || (h = Math.max(h, 3 * u))
        }
        switch (o) {
          case i.Right:
          case i.Left:
            a = a.inflate(-h, -a.height * (1 - this.thickness * n.thickness) / 2);
            break;
          case i.Up:
          case i.Down:
            a = a.inflate(-a.width * (1 - this.thickness * n.thickness) / 2, -h)
        }
        if (this.stackRanges && n != this.face && n != this.pointer) {
          var c = this.ranges.indexOf(n);
          if (c > -1) {
            var l = this.ranges.length;
            switch (o) {
              case i.Right:
              case i.Left:
                a.height /= l, a.top += c * a.height;
                break;
              case i.Up:
              case i.Down:
                a.width /= l, a.left += c * a.width
            }
          }
        }
        var g = n == this._face,
          _ = g ? 0 : this._getPercent(n.min),
          p = g ? 1 : this._getPercent(s);
        switch (o) {
          case i.Right:
            a.left += a.width * _, a.width *= p - _;
            break;
          case i.Left:
            a.left = a.right - a.width * p, a.width = a.width * (p - _);
            break;
          case i.Down:
            a.top += a.height * _, a.height *= p - _;
            break;
          case i.Up:
            a.top = a.bottom - a.height * p, a.height = a.height * (p - _)
        }
        return a
      }, s.prototype._getValueFromPoint = function(t) {
        var e = this._getRangeRect(this._face),
          n = 0;
        switch (this._getDirection()) {
          case i.Right:
            n = e.width > 0 ? (t.x - e.left) / e.width : 0;
            break;
          case i.Left:
            n = e.width > 0 ? (e.right - t.x) / e.width : 0;
            break;
          case i.Up:
            n = e.height > 0 ? (e.bottom - t.y) / e.height : 0;
            break;
          case i.Down:
            n = e.height > 0 ? (t.y - e.top) / e.height : 0
        }
        return this.min + n * (this.max - this.min)
      }, s.prototype._getDirection = function() {
        var t = this._direction;
        if (this.rightToLeft) switch (t) {
          case i.Left:
            t = i.Right;
            break;
          case i.Right:
            t = i.Left
        }
        return t
      }, s.prototype._getKey = function(e) {
        switch (this._getDirection()) {
          case i.Left:
            switch (e) {
              case t.Key.Left:
                e = t.Key.Right;
                break;
              case t.Key.Right:
                e = t.Key.Left
            }
            break;
          case i.Down:
            switch (e) {
              case t.Key.Up:
                e = t.Key.Down;
                break;
              case t.Key.Down:
                e = t.Key.Up
            }
        }
        return e
      }, s
    }(e.Gauge);
    e.LinearGauge = n
  }(t.gauge || (t.gauge = {}))
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
      function n(n, s) {
        var r = i.call(this, n, null) || this;
        return r._startAngle = 0, r._sweepAngle = 180, r._autoScale = !0, t.addClass(r.hostElement, "wj-radialgauge"), r._thickness = .4, r.showText = e.ShowText.All, r.initialize(s), r
      }
      return __extends(n, i), Object.defineProperty(n.prototype, "startAngle", {
        get: function() {
          return this._startAngle
        },
        set: function(e) {
          e != this._startAngle && (this._startAngle = t.clamp(t.asNumber(e, !1), -360, 360), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "sweepAngle", {
        get: function() {
          return this._sweepAngle
        },
        set: function(e) {
          e != this._sweepAngle && (this._sweepAngle = t.clamp(t.asNumber(e, !1), -360, 360), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "autoScale", {
        get: function() {
          return this._autoScale
        },
        set: function(e) {
          e != this._autoScale && (this._autoScale = t.asBoolean(e), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "clientSize", {
        get: function() {
          var e = this._rcSvg;
          return e ? new t.Size(e.width, e.height) : null
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.refresh = function(n) {
        if (void 0 === n && (n = !0), this._setAttribute(this._svg, "viewBox", null), this._rcSvg = t.Rect.fromBoundingRect(this._dSvg.getBoundingClientRect()), i.prototype.refresh.call(this, n), this._ctmInv = null, this._ptSvg = null, this._autoScale) {
          this._setAttribute(this._svg, "viewBox", "");
          var s = t.Rect.fromBoundingRect(e.Gauge._getBBox(this._pFace));
          0 != (this.showText & e.ShowText.Value) && (s = t.Rect.union(s, t.Rect.fromBoundingRect(e.Gauge._getBBox(this._tValue)))), 0 != (this.showText & e.ShowText.MinMax) && (s = t.Rect.union(s, t.Rect.fromBoundingRect(e.Gauge._getBBox(this._tMin))), s = t.Rect.union(s, t.Rect.fromBoundingRect(e.Gauge._getBBox(this._tMax))));
          var r = [this._fix(s.left), this._fix(s.top), this._fix(s.width), this._fix(s.height)].join(" ");
          this._setAttribute(this._svg, "viewBox", r);
          var a = this._pFace.getCTM();
          this._ctmInv = a ? a.inverse() : null, this._ptSvg = this._svg.createSVGPoint()
        }
      }, n.prototype._updateRangeElement = function(e, i, n) {
        if (this._rcSvg) {
          var s = this._rcSvg,
            r = new t.Point(s.width / 2, s.height / 2),
            a = Math.min(s.width, s.height) / 2,
            o = a * this.thickness,
            h = o * i.thickness,
            u = a - (o - h) / 2,
            c = u - h,
            l = this.startAngle + 180,
            g = this.sweepAngle,
            _ = i == this._face,
            p = _ ? 0 : this._getPercent(i.min),
            f = l + g * p,
            d = g * ((_ ? 1 : this._getPercent(n)) - p);
          if (this.stackRanges && i != this.face && i != this.pointer) {
            var m = this.ranges.indexOf(i);
            if (m > -1) {
              var b = this.ranges.length,
                y = (u - c) / b;
              u = (c += (b - 1 - m) * y) + y
            }
          }
          if (this._updateSegment(e, r, u, c, f, d), i == this._pointer && this.thumbSize > 0) {
            var w = this._animColor ? this._animColor : this._getPointerColor(i.max),
              v = this._getPoint(r, l + g * this._getPercent(n), (u + c) / 2),
              x = this._cValue;
            this._setAttribute(x, "cx", this._fix(v.x)), this._setAttribute(x, "cy", this._fix(v.y)), this._setAttribute(x, "style", w ? "fill:" + w : null), this._setAttribute(x, "r", this._fix(this.thumbSize / 2))
          }
        }
      }, n.prototype._updateText = function() {
        if (this._rcSvg) {
          var i = this._rcSvg,
            n = new t.Point(i.width / 2, i.height / 2),
            s = Math.min(i.width, i.height) / 2,
            r = Math.max(0, s * (1 - this.thickness)),
            a = this.startAngle + 180,
            o = this.sweepAngle;
          this._showElement(this._cValue, this.thumbSize > 0);
          var h = 0 != (this.showText & e.ShowText.MinMax) && Math.abs(o) <= 300;
          this._showElement(this._tMin, h), this._showElement(this._tMax, h), this._centerText(this._tValue, this.value, n);
          var u = 10 * (this.sweepAngle < 0 ? -1 : 1);
          this._centerText(this._tMin, this.min, this._getPoint(n, a - u, (s + r) / 2)), this._centerText(this._tMax, this.max, this._getPoint(n, a + o + u, (s + r) / 2))
        }
      }, n.prototype._updateTicks = function() {
        var e = this.tickSpacing && this.tickSpacing > 0 ? this.tickSpacing : this.step,
          i = "";
        if (this.showTicks && e > 0)
          for (var n = this._rcSvg, s = new t.Point(n.width / 2, n.height / 2), r = Math.min(n.width, n.height) / 2, a = r * this.thickness, o = a * this._face.thickness, h = r - (a - o) / 2, u = h - o, c = this.min + e; c < this.max; c += e) {
            var l = this.startAngle + 180 + this.sweepAngle * this._getPercent(c);
            i += "M " + this._fix(this._getPoint(s, l, u)) + " L " + this._fix(this._getPoint(s, l, h)) + " "
          }
        this._pTicks.setAttribute("d", i)
      }, n.prototype._updateSegment = function(e, i, n, s, r, a) {
        a = Math.min(Math.max(a, -359.99), 359.99);
        var o = this._getPoint(i, r, s),
          h = this._getPoint(i, r, n),
          u = this._getPoint(i, r + a, n),
          c = this._getPoint(i, r + a, s),
          l = {
            large: Math.abs(a) > 180 ? 1 : 0,
            cw: a > 0 ? 1 : 0,
            ccw: a > 0 ? 0 : 1,
            or: this._fix(n),
            ir: this._fix(s),
            p1: this._fix(o),
            p2: this._fix(h),
            p3: this._fix(u),
            p4: this._fix(c)
          },
          g = t.format("M {p1} L {p2} A {or} {or} 0 {large} {cw} {p3} L {p4} A {ir} {ir} 0 {large} {ccw} {p1} Z", l);
        e.setAttribute("d", g)
      }, n.prototype._getPoint = function(e, i, n) {
        return i = i * Math.PI / 180, new t.Point(e.x + n * Math.cos(i), e.y + n * Math.sin(i))
      }, n.prototype._getValueFromPoint = function(e) {
        if (this.autoScale && this._ctmInv && (this._ptSvg.x = e.x, this._ptSvg.y = e.y, this._ptSvg = this._ptSvg.matrixTransform(this._ctmInv), e.x = this._ptSvg.x, e.y = this._ptSvg.y), !this._rcSvg) return null;
        var i = this._rcSvg,
          n = new t.Point(i.width / 2, i.height / 2),
          s = Math.min(i.width, i.height) / 2,
          r = s * (1 - this.thickness),
          a = e.x - n.x,
          o = e.y - n.y,
          h = o * o + a * a;
        if (h > s * s + 16 || h < r * r - 16) return null;
        var u = 180 * (Math.PI - Math.atan2(-o, a)) / Math.PI,
          c = this.startAngle,
          l = this.sweepAngle;
        if (l > 0) {
          for (; u < c;) u += 360;
          for (; u > c + l;) u -= 360
        } else {
          for (; u < c + l;) u += 360;
          for (; u > c;) u -= 360
        }
        var g = Math.abs(u - c) / Math.abs(l);
        return this.min + g * (this.max - this.min)
      }, n
    }(e.Gauge);
    e.RadialGauge = i
  }(t.gauge || (t.gauge = {}))
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
      function n(n, s) {
        var r = i.call(this, n, null) || this;
        return t.addClass(r.hostElement, "wj-bulletgraph"), r._pointer.thickness = .35, r._rngTarget = new e.Range("target"), r._rngTarget.thickness = .8, r._rngTarget.color = "black", r._rngGood = new e.Range("good"), r._rngGood.color = "rgba(0,0,0,.15)", r._rngBad = new e.Range("bad"), r._rngBad.color = "rgba(0,0,0,.3)", r.ranges.push(r._rngBad), r.ranges.push(r._rngGood), r.ranges.push(r._rngTarget), r.initialize(s), r
      }
      return __extends(n, i), Object.defineProperty(n.prototype, "target", {
        get: function() {
          return this._rngTarget.max
        },
        set: function(t) {
          this._rngTarget.max = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "good", {
        get: function() {
          return this._rngGood.max
        },
        set: function(t) {
          this._rngGood.max = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "bad", {
        get: function() {
          return this._rngBad.max
        },
        set: function(t) {
          this._rngBad.max = t
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype._getRangeRect = function(t, n) {
        void 0 === n && (n = t.max);
        var s = i.prototype._getRangeRect.call(this, t, n);
        if (t == this._rngTarget) switch (this.direction) {
          case e.GaugeDirection.Right:
            s.left = s.right - 1, s.width = 3;
            break;
          case e.GaugeDirection.Left:
            s.width = 3;
            break;
          case e.GaugeDirection.Up:
            s.height = 3;
            break;
          case e.GaugeDirection.Down:
            s.top = s.bottom - 1, s.height = 3
        }
        return s
      }, n
    }(e.LinearGauge);
    e.BulletGraph = i
  }(t.gauge || (t.gauge = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var i = function() {
      function e(e) {
        this._min = 0, this._max = 100, this._thickness = 1, this.propertyChanged = new t.Event, t.isString(e) ? this._name = e : e && t.copy(this, e)
      }
      return Object.defineProperty(e.prototype, "min", {
        get: function() {
          return this._min
        },
        set: function(e) {
          this._setProp("_min", t.asNumber(e, !0))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "max", {
        get: function() {
          return this._max
        },
        set: function(e) {
          this._setProp("_max", t.asNumber(e, !0))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "color", {
        get: function() {
          return this._color
        },
        set: function(e) {
          this._setProp("_color", t.asString(e))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "thickness", {
        get: function() {
          return this._thickness
        },
        set: function(e) {
          this._setProp("_thickness", t.clamp(t.asNumber(e), 0, 1))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "name", {
        get: function() {
          return this._name
        },
        set: function(e) {
          this._setProp("_name", t.asString(e))
        },
        enumerable: !0,
        configurable: !0
      }), e.prototype.onPropertyChanged = function(t) {
        this.propertyChanged.raise(this, t)
      }, e.prototype._setProp = function(e, i) {
        var n = this[e];
        if (i != n) {
          this[e] = i;
          var s = new t.PropertyChangedEventArgs(e.substr(1), n, i);
          this.onPropertyChanged(s)
        }
      }, e._ctr = 0, e
    }();
    e.Range = i
  }(t.gauge || (t.gauge = {}))
}(wijmo || (wijmo = {}));
