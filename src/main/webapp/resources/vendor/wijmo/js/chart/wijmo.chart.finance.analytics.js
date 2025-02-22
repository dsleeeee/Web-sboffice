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
      function s() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (s.prototype = i.prototype, new s)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(i) {
      ! function(s) {
        "use strict";

        function n(e) {
          return isFinite(e) && !isNaN(e) && t.isNumber(e)
        }
        var a = function(i) {
          function s(t) {
            var s = i.call(this) || this;
            return s._levels = [0, 23.6, 38.2, 50, 61.8, 100], s._uptrend = !0, s._labelPosition = e.LabelPosition.Left, s.rendering.addHandler(s._render), s.initialize(t), s
          }
          return __extends(s, i), Object.defineProperty(s.prototype, "low", {
            get: function() {
              return this._low
            },
            set: function(e) {
              e != this._low && (this._low = t.asNumber(e, !0), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "high", {
            get: function() {
              return this._high
            },
            set: function(e) {
              e != this._high && (this._high = t.asNumber(e, !0), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "labelPosition", {
            get: function() {
              return this._labelPosition
            },
            set: function(i) {
              (i = t.asEnum(i, e.LabelPosition, !0)) != this._labelPosition && (this._labelPosition = i, this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "uptrend", {
            get: function() {
              return this._uptrend
            },
            set: function(e) {
              e != this._uptrend && (this._uptrend = t.asBoolean(e, !0), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "levels", {
            get: function() {
              return this._levels
            },
            set: function(e) {
              e != this._levels && (this._levels = t.asArray(e, !0), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "minX", {
            get: function() {
              return this._minX
            },
            set: function(t) {
              t != this._minX && (this._minX = t, this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "maxX", {
            get: function() {
              return this._maxX
            },
            set: function(t) {
              t != this._maxX && (this._maxX = t, this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), s.prototype._getMinX = function() {
            return t.isNumber(this._minX) ? this._minX : t.isDate(this._minX) ? t.asDate(this._minX).valueOf() : this._getAxisX().actualMin
          }, s.prototype._getMaxX = function() {
            return t.isNumber(this._maxX) ? this._maxX : t.isDate(this._maxX) ? t.asDate(this._maxX).valueOf() : this._getAxisX().actualMax
          }, s.prototype._updateLevels = function() {
            var t = void 0,
              e = void 0;
            if (void 0 === this._low || void 0 === this._high) {
              var s = i.prototype.getValues.call(this, 0),
                n = i.prototype.getValues.call(this, 1);
              if (s)
                for (var a = s.length, r = this._getMinX(), o = this._getMaxX(), l = 0; l < a; l++) {
                  var h = s[l],
                    c = n ? n[l] : l;
                  c < r || c > o || (isNaN(h) || ((void 0 === t || t > h) && (t = h), (void 0 === e || e < h) && (e = h)))
                }
            }
            void 0 === this._low && void 0 !== t ? this._actualLow = t : this._actualLow = this._low, void 0 === this._high && void 0 !== e ? this._actualHigh = e : this._actualHigh = this._high
          }, s.prototype._render = function(i, s) {
            s.cancel = !0;
            var n = i;
            n._updateLevels();
            var a = n._getAxisX(),
              r = n._getAxisY(),
              o = s.engine,
              l = n._getSymbolStroke(n._chart.series.indexOf(n)),
              h = e._BasePlotter.cloneStyle(n.style, ["fill"]),
              c = e._BasePlotter.cloneStyle(n.style, ["stroke"]),
              _ = n.chart._plotrectId;
            o.stroke = l, o.strokeWidth = 2, o.textFill = l;
            var u = n._getMinX(),
              p = n._getMaxX();
            u < a.actualMin && (u = a.actualMin), p > a.actualMax && (p = a.actualMax), o.startGroup(null, _);
            for (var d = n._levels ? n._levels.length : 0, f = 0; f < d; f++) {
              var g = n._levels[f],
                y = a.convert(u),
                m = a.convert(p),
                v = n.uptrend ? r.convert(n._actualLow + .01 * g * (n._actualHigh - n._actualLow)) : r.convert(n._actualHigh - .01 * g * (n._actualHigh - n._actualLow));
              if (e._DataInfo.isValid(y) && e._DataInfo.isValid(m) && e._DataInfo.isValid(v) && (o.drawLine(y, v, m, v, null, h), n.labelPosition != e.LabelPosition.None)) {
                var V = g.toFixed(1) + "%",
                  b = 0;
                switch ((n.uptrend && 0 == f || !n.uptrend && f == d - 1) && (b = 2), n.labelPosition) {
                  case e.LabelPosition.Left:
                    e.FlexChartCore._renderText(o, V, new t.Point(y, v), 0, b, null, null, c);
                    break;
                  case e.LabelPosition.Center:
                    e.FlexChartCore._renderText(o, V, new t.Point(.5 * (y + m), v), 1, b, null, null, c);
                    break;
                  case e.LabelPosition.Right:
                    e.FlexChartCore._renderText(o, V, new t.Point(m, v), 2, b, null, null, c)
                }
              }
            }
            o.stroke = null, o.strokeWidth = null, o.textFill = null, o.endGroup()
          }, s.prototype._getChartType = function() {
            return e.ChartType.Line
          }, s
        }(e.SeriesBase);
        s.Fibonacci = a;
        var r = function(i) {
          function s(t) {
            var s = i.call(this) || this;
            return s._levels = [38.2, 50, 61.8], s._labelPosition = e.LabelPosition.Top, s.rendering.addHandler(s._render, s), s.initialize(t), s
          }
          return __extends(s, i), Object.defineProperty(s.prototype, "start", {
            get: function() {
              return this._start
            },
            set: function(i) {
              i !== this.start && (this._start = t.asType(i, e.DataPoint), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "end", {
            get: function() {
              return this._end
            },
            set: function(i) {
              i !== this.end && (this._end = t.asType(i, e.DataPoint), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "levels", {
            get: function() {
              return this._levels
            },
            set: function(e) {
              e !== this._levels && (this._levels = t.asArray(e, !1), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "labelPosition", {
            get: function() {
              return this._labelPosition
            },
            set: function(i) {
              (i = t.asEnum(i, e.LabelPosition)) !== this.labelPosition && (this._labelPosition = i, this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), s.prototype._render = function(s, a) {
            a.cancel = !0;
            var r = this._getX(0),
              o = this._getY(0),
              l = this._getX(1),
              h = this._getY(1);
            if (!(i.prototype._getLength.call(this) <= 1) && n(r) && n(o) && n(l) && n(h)) {
              var c, _ = this._getAxisX(),
                u = this._getAxisY(),
                p = a.engine,
                d = this.chart.series.indexOf(this),
                f = this._getSymbolStroke(d),
                g = e._BasePlotter.cloneStyle(this.style, ["fill"]),
                y = e._BasePlotter.cloneStyle(this.style, ["stroke"]);
              p.stroke = f, p.strokeWidth = 2, p.textFill = f;
              var m, v, V, b, x, P, w, k, L, X = this.chart._plotrectId,
                O = h - o;
              c = p.startGroup(null, X), t.addClass(c, "fibonacci-arcs"), n(r) && n(o) && n(l) && n(h) && p.drawLines([_.convert(r), _.convert(l)], [u.convert(o), u.convert(h)], null, g), b = Math.sqrt(Math.pow(_.convert(l) - _.convert(r), 2) + Math.pow(u.convert(h) - u.convert(o), 2)), P = new t.Point(l, h);
              for (var j = 0; j < this.levels.length; j++)
                if (w = .01 * this.levels[j], x = Math.abs(b * w), n(P.x) && n(P.y) && n(x) && (m = _.convert(P.x), v = u.convert(P.y), p.drawDonutSegment(m, v, x, x, O > 0 ? 0 : Math.PI, Math.PI, null, g), this.labelPosition !== e.LabelPosition.None && 0 !== w)) {
                  switch (L = t.Globalize.format(w, "p1"), k = p.measureString(L, null, null, y), V = O <= 0 ? v - x : v + x, this.labelPosition) {
                    case e.LabelPosition.Center:
                      V += .5 * k.height;
                      break;
                    case e.LabelPosition.Bottom:
                      V += O <= 0 ? k.height : 0;
                      break;
                    default:
                      V += O <= 0 ? 0 : k.height
                  }
                  p.drawString(L, new t.Point(m - .5 * k.width, V), null, y)
                }
              p.stroke = null, p.strokeWidth = null, p.textFill = null, p.endGroup()
            }
          }, s.prototype._getX = function(e) {
            var i = null;
            return 0 === e && this.start ? i = this.start.x : 1 === e && this.end && (i = this.end.x), t.isDate(i) && (i = t.asDate(i).valueOf()), i
          }, s.prototype._getY = function(t) {
            var e = null;
            return 0 === t && this.start ? e = this.start.y : 1 === t && this.end && (e = this.end.y), e
          }, s.prototype._getChartType = function() {
            return e.ChartType.Line
          }, s
        }(e.SeriesBase);
        s.FibonacciArcs = r;
        var o = function(s) {
          function a(t) {
            var i = s.call(this) || this;
            return i._levels = [0, 23.6, 38.2, 50, 61.8, 100], i._labelPosition = e.LabelPosition.Top, i.rendering.addHandler(i._render, i), i.initialize(t), i
          }
          return __extends(a, s), Object.defineProperty(a.prototype, "start", {
            get: function() {
              return this._start
            },
            set: function(i) {
              i !== this.start && (this._start = t.asType(i, e.DataPoint), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(a.prototype, "end", {
            get: function() {
              return this._end
            },
            set: function(i) {
              i !== this.end && (this._end = t.asType(i, e.DataPoint), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(a.prototype, "levels", {
            get: function() {
              return this._levels
            },
            set: function(e) {
              e !== this._levels && (this._levels = t.asArray(e, !1), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(a.prototype, "labelPosition", {
            get: function() {
              return this._labelPosition
            },
            set: function(i) {
              (i = t.asEnum(i, e.LabelPosition)) !== this.labelPosition && (this._labelPosition = i, this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), a.prototype._updateLevels = function() {
            if (!this.start || !this.end) {
              var t, a, r, o, l = this.chart._getPlotter(this),
                h = this._getAxisX(),
                c = s.prototype.getValues.call(this, 0),
                _ = s.prototype.getValues.call(this, 1) || l.dataInfo.getXVals();
              c && c.length > 0 && (r = i._minimum(c), o = i._maximum(c)), _ && _.length > 0 ? (t = i._minimum(_), a = i._maximum(_)) : (t = h.actualMin, a = h.actualMax), n(t) && n(r) && n(a) && n(o) && (this.start = new e.DataPoint(t, r), this.end = new e.DataPoint(a, o))
            }
          }, a.prototype._render = function(i, a) {
            a.cancel = !0, this._updateLevels();
            var r = this._getX(0),
              o = this._getY(0),
              l = this._getX(1),
              h = this._getY(1);
            if (!(s.prototype._getLength.call(this) <= 1) && n(r) && n(o) && n(l) && n(h)) {
              var c = this._getAxisX(),
                _ = this._getAxisY(),
                u = this.chart.series.indexOf(this),
                p = a.engine,
                d = this._getSymbolStroke(u),
                f = e._BasePlotter.cloneStyle(this.style, ["fill"]),
                g = e._BasePlotter.cloneStyle(this.style, ["stroke"]);
              p.stroke = d, p.strokeWidth = 2, p.textFill = d;
              var y, m, v, V, b, x, P, w, k, L, X, O, j, M = h - o,
                I = l - r,
                B = this.chart._plotrectId;
              y = r, v = o, V = h;
              var C = m = l;
              p.startGroup(null, B);
              for (var A = 0; A < this.levels.length; A++)
                if (m = I < 0 ? c.actualMin : c.actualMax, L = .01 * this.levels[A], V = v + L * M, w = (V - v) / (C - y), k = V - w * C, V = w * m + k, M > 0 && V > _.actualMax ? m = ((V = _.actualMax) - k) / w : M < 0 && V < _.actualMin && (m = ((V = _.actualMin) - k) / w), n(y) && n(v) && n(m) && n(V) && (b = new t.Point(c.convert(y), _.convert(v)), x = new t.Point(c.convert(m), _.convert(V)), p.drawLines([b.x, x.x], [b.y, x.y], null, f), this.labelPosition != e.LabelPosition.None)) {
                  X = t.Globalize.format(L, "p1"), O = p.measureString(X, null, null, g), j = 180 * Math.atan((x.y - b.y) / (x.x - b.x)) / Math.PI, P = x.clone(), x.x = I > 0 ? x.x - O.width : x.x;
                  var D, S = j * Math.PI / 180,
                    Y = new t.Point,
                    T = new t.Point,
                    W = new t.Point,
                    N = new t.Point,
                    z = _.convert(_.actualMin),
                    R = _.convert(_.actualMax),
                    F = c.convert(c.actualMin),
                    H = c.convert(c.actualMax),
                    E = P.clone();
                  switch (this.labelPosition) {
                    case e.LabelPosition.Center:
                      x.y += .5 * O.height, E.y += .5 * O.height;
                      break;
                    case e.LabelPosition.Bottom:
                      x.y += O.height
                  }
                  I > 0 ? (this.labelPosition === e.LabelPosition.Top || this.labelPosition === e.LabelPosition.Center ? (N = E.clone(), W.x = N.x + O.height * Math.sin(S), W.y = N.y - O.height * Math.cos(S), Y.x = N.x - O.width * Math.cos(S) + O.height * Math.sin(S), Y.y = N.y - O.width * Math.sin(S) - O.height * Math.cos(S), T.x = N.x - O.width * Math.cos(S), T.y = N.y - O.width * Math.sin(S)) : this.labelPosition === e.LabelPosition.Bottom && (W = E.clone(), Y.x = W.x - O.width * Math.cos(S), Y.y = W.y - O.width * Math.sin(S), T.x = Y.x - O.height * Math.sin(S), T.y = Y.y + O.height * Math.cos(S), N.x = Y.x + O.width * Math.cos(S) - O.height * Math.sin(S), N.y = Y.y + O.width * Math.sin(S) + O.height * Math.cos(S)), M > 0 ? (W.y < R && (w = (_.convertBack(W.y) - _.convertBack(Y.y)) / (c.convertBack(W.x) - c.convertBack(Y.x)), k = _.convertBack(W.y) - w * c.convertBack(W.x), D = c.convert((_.actualMax - k) / w), x.x -= Math.abs(W.x - D)), N.x > H && (x.x -= Math.abs(H - N.x))) : M < 0 && (N.y > z && (w = (_.convertBack(T.y) - _.convertBack(N.y)) / (c.convertBack(T.x) - c.convertBack(N.x)), k = _.convertBack(N.y) - w * c.convertBack(N.x), D = c.convert((_.actualMin - k) / w), x.x -= Math.max(Math.abs(D - N.x), Math.abs(z - N.y))), W.x > H && (x.x -= Math.abs(H - W.x)))) : I < 0 && (this.labelPosition === e.LabelPosition.Top || this.labelPosition === e.LabelPosition.Center ? (T = E.clone(), Y.x = T.x + O.height * Math.sin(S), Y.y = T.y - O.height * Math.cos(S), N.x = T.x + O.width * Math.cos(S), N.y = T.y + O.width * Math.sin(S), W.x = Y.x + O.width * Math.cos(S), W.y = Y.y + O.width * Math.sin(S)) : this.labelPosition === e.LabelPosition.Bottom && (Y = E.clone(), W.x = Y.x + O.width * Math.cos(S), W.y = Y.y + O.width * Math.sin(S), T.x = Y.x - O.height * Math.sin(S), T.y = Y.y + O.height * Math.cos(S), N.x = Y.x + O.width * Math.cos(S) - O.height * Math.sin(S), N.y = Y.y + O.width * Math.sin(S) + O.height * Math.cos(S)), M > 0 ? (Y.y < R && (w = (_.convertBack(Y.y) - _.convertBack(W.y)) / (c.convertBack(Y.x) - c.convertBack(W.x)), k = _.convertBack(Y.y) - w * c.convertBack(Y.x), D = c.convert((_.actualMax - k) / w), x.x += Math.abs(Y.x - D)), T.x < F && (x.x += Math.abs(F - T.x))) : M < 0 && (T.y > z && (w = (_.convertBack(N.y) - _.convertBack(T.y)) / (c.convertBack(N.x) - c.convertBack(T.x)), k = _.convertBack(T.y) - w * c.convertBack(T.x), D = c.convert((_.actualMin - k) / w), x.x += Math.max(Math.abs(D - T.x), Math.abs(z - T.y))), Y.x < F && (x.x += Math.abs(F - Y.x)))), 0 === j ? p.drawString(X, x, null, g) : p.drawStringRotated(X, x, P, j, null, g)
                }
              p.stroke = null, p.strokeWidth = null, p.textFill = null, p.endGroup()
            }
          }, a.prototype._getX = function(e) {
            var i = null;
            return 0 === e && this.start ? i = this.start.x : 1 === e && this.end && (i = this.end.x), t.isDate(i) && (i = t.asDate(i).valueOf()), i
          }, a.prototype._getY = function(t) {
            var e = null;
            return 0 === t && this.start ? e = this.start.y : 1 === t && this.end && (e = this.end.y), e
          }, a.prototype._getChartType = function() {
            return e.ChartType.Line
          }, a
        }(e.SeriesBase);
        s.FibonacciFans = o;
        var l = function(i) {
          function s(t) {
            var s = i.call(this) || this;
            return s._levels = [0, 1, 2, 3, 5, 8, 13, 21, 34], s._labelPosition = e.LabelPosition.Right, s.rendering.addHandler(s._render, s), s.initialize(t), s
          }
          return __extends(s, i), Object.defineProperty(s.prototype, "startX", {
            get: function() {
              return this._startX
            },
            set: function(e) {
              e !== this.startX && (t.isDate(e) ? this._startX = t.asDate(e) : this._startX = t.asNumber(e), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "endX", {
            get: function() {
              return this._endX
            },
            set: function(e) {
              e !== this.endX && (t.isDate(e) ? this._endX = t.asDate(e) : this._endX = t.asNumber(e), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "levels", {
            get: function() {
              return this._levels
            },
            set: function(e) {
              e !== this._levels && (this._levels = t.asArray(e, !1), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "labelPosition", {
            get: function() {
              return this._labelPosition
            },
            set: function(i) {
              (i = t.asEnum(i, e.LabelPosition)) !== this.labelPosition && (this._labelPosition = i, this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), s.prototype._render = function(s, a) {
            a.cancel = !0, this._updateLevels();
            var r = this._getX(0),
              o = this._getX(1);
            if (!(i.prototype._getLength.call(this) <= 1) && n(r) && n(o)) {
              var l, h, c, _, u = o - r,
                p = this._getAxisX(),
                d = this._getAxisY(),
                f = this._chart.series.indexOf(this),
                g = a.engine,
                y = this._getSymbolStroke(f),
                m = e._BasePlotter.cloneStyle(this.style, ["fill"]),
                v = e._BasePlotter.cloneStyle(this.style, ["stroke"]),
                V = d.convert(d.actualMin),
                b = d.convert(d.actualMax),
                x = this.chart._plotrectId;
              if (g.stroke = y, g.strokeWidth = 2, g.textFill = y, 0 !== u) {
                g.startGroup(null, x);
                for (var P = 0; P < this.levels.length; P++)
                  if (l = this.levels[P], !((h = u * l + r) < p.actualMin || p.actualMax < h) && n(h) && (h = p.convert(h), g.drawLine(h, V, h, b, null, m), this.labelPosition !== e.LabelPosition.None)) {
                    switch (_ = t.Globalize.format(l, "n0"), c = g.measureString(_, null, null, v), this.labelPosition) {
                      case e.LabelPosition.Left:
                        h -= c.width + 2;
                        break;
                      case e.LabelPosition.Center:
                        h -= c.width / 2;
                        break;
                      case e.LabelPosition.Right:
                        h += 2;
                        break;
                      default:
                        h = u < 0 ? h - c.width - 2 : h + 2
                    }
                    g.drawString(_, new t.Point(h, V), null, v)
                  }
                g.stroke = null, g.strokeWidth = null, g.textFill = null, g.endGroup()
              }
            }
          }, s.prototype._updateLevels = function() {
            var e = this.chart._getPlotter(this),
              s = i.prototype.getValues.call(this, 1) || e.dataInfo.getXVals();
            if (!(i.prototype._getLength.call(this) <= 1)) {
              var n = this._getX(0),
                a = this._getX(1),
                r = t.isNumber(n) && t.isNumber(a);
              r || s ? !r && s && (this._startX = s[0], this._endX = s[1]) : (this._startX = 0, this._endX = 1)
            }
          }, s.prototype._getX = function(e) {
            var i = null;
            return 0 === e ? i = this.startX : 1 === e && (i = this.endX), t.isDate(i) && (i = t.asDate(i).valueOf()), i
          }, s.prototype._getChartType = function() {
            return e.ChartType.Line
          }, s
        }(e.SeriesBase);
        s.FibonacciTimeZones = l
      }(i.analytics || (i.analytics = {}))
    }(e.finance || (e.finance = {}))
  }(t.chart || (t.chart = {}))
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
      function s() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (s.prototype = i.prototype, new s)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(i) {
      ! function(s) {
        "use strict";
        var n = function(i) {
          function s(t) {
            var e = i.call(this, t) || this;
            return e._seriesCount = 1, e
          }
          return __extends(s, i), Object.defineProperty(s.prototype, "_hitTester", {
            get: function() {
              return this._plotter && !this.__hitTester && (this.__hitTester = this._plotter.hitTester), this.__hitTester
            },
            enumerable: !0,
            configurable: !0
          }), s.prototype._getChartType = function() {
            return e.ChartType.Line
          }, s.prototype._getXValues = function() {
            return i.prototype.getValues.call(this, 1) || this._plotter.dataInfo.getXVals()
          }, s.prototype._getDataPoint = function(t, i, s, n, a, r) {
            var o = new e._DataPoint(s, n, t, i);
            return o.y = i, o.yfmt = r._formatValue(i), o.x = t, o.xfmt = a._formatValue(t), o
          }, s.prototype._shouldCalculate = function() {
            return !0
          }, s.prototype._init = function() {}, s.prototype._calculate = function() {}, s.prototype._clearValues = function() {
            i.prototype._clearValues.call(this), this.__hitTester = null
          }, s.prototype._getName = function(t) {
            var e = void 0;
            if (this.name)
              if (this.name.indexOf(",")) {
                var i = this.name.split(",");
                i && i.length - 1 >= t && (e = i[t].trim())
              } else e = this.name;
            return e
          }, s.prototype._getStyles = function(t) {
            var e = null;
            if (t < 0 || null === this._styles) return e;
            var i = 0;
            for (var s in this._styles) {
              if (i === t && this._styles.hasOwnProperty(s)) {
                e = this._styles[s];
                break
              }
              i++
            }
            return e
          }, s.prototype.legendItemLength = function() {
            return this._seriesCount
          }, s.prototype.measureLegendItem = function(e, i) {
            var s = this._getName(i),
              n = new t.Size(0, 0);
            return s && (n = this._measureLegendItem(e, this._getName(i))), n
          }, s.prototype.drawLegendItem = function(t, e, i) {
            var s = this._getStyles(i) || this.style;
            this._getName(i) && this._drawLegendItem(t, e, this._getChartType(), this._getName(i), s, this.symbolStyle)
          }, s
        }(e.SeriesBase);
        s.OverlayIndicatorBase = n;
        var a = function(s) {
          function n(t) {
            var e = s.call(this, t) || this;
            return e._period = 14, e
          }
          return __extends(n, s), Object.defineProperty(n.prototype, "period", {
            get: function() {
              return this._period
            },
            set: function(e) {
              e !== this._period && (this._period = t.asInt(e, !1, !0), this._clearValues(), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), n.prototype.getValues = function(t) {
            var e = null;
            return s.prototype._getLength.call(this) <= 0 ? e : (this._shouldCalculate() && (this._init(), this._calculate()), 0 === t ? e = this._yvals : 1 === t && (e = this._xvals), e)
          }, n.prototype.getDataRect = function(n, a) {
            if (a) return a;
            var r = null;
            if (s.prototype._getLength.call(this) <= 0) return r;
            this._shouldCalculate() && (this._init(), this._calculate());
            var o = i._minimum(this._xvals),
              l = i._maximum(this._xvals),
              h = i._minimum(this._yvals),
              c = i._maximum(this._yvals);
            return e._DataInfo.isValid(o) && e._DataInfo.isValid(l) && e._DataInfo.isValid(h) && e._DataInfo.isValid(c) && (r = new t.Rect(o, h, l - o, c - h)), r
          }, n.prototype._clearValues = function() {
            s.prototype._clearValues.call(this), this._xvals = null, this._yvals = null
          }, n.prototype._shouldCalculate = function() {
            return !this._yvals || !this._xvals
          }, n.prototype._init = function() {
            s.prototype._init.call(this), this._yvals = [], this._xvals = []
          }, n.prototype._getItem = function(t) {
            return s.prototype._getLength.call(this) <= 0 ? s.prototype._getItem.call(this, t) : (this._shouldCalculate() && (this._init(), this._calculate()), t = s.prototype._getLength.call(this) - i._minimum(this._yvals.length, this._xvals.length) + t, s.prototype._getItem.call(this, t))
          }, n
        }(n);
        s.SingleOverlayIndicatorBase = a
      }(i.analytics || (i.analytics = {}))
    }(e.finance || (e.finance = {}))
  }(t.chart || (t.chart = {}))
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
      function s() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (s.prototype = i.prototype, new s)
    }
  }(),
  wijmo;
! function(t) {
  ! function(t) {
    ! function(t) {
      ! function(e) {
        "use strict";
        var i = function(e) {
          function i(t) {
            var i = e.call(this) || this;
            return i.period = 14, i.initialize(t), i
          }
          return __extends(i, e), i.prototype._calculate = function() {
            if (!(e.prototype._getLength.call(this) <= 0)) {
              var i = e.prototype._getBindingValues.call(this, 0),
                s = e.prototype._getBindingValues.call(this, 1),
                n = e.prototype._getBindingValues.call(this, 3),
                a = this._getXValues();
              this._yvals = t._avgTrueRng(i, s, n, this.period), this._xvals = a ? a.slice(this.period - 1) : t._range(this.period - 1, i.length)
            }
          }, i
        }(e.SingleOverlayIndicatorBase);
        e.ATR = i
      }(t.analytics || (t.analytics = {}))
    }(t.finance || (t.finance = {}))
  }(t.chart || (t.chart = {}))
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
      function s() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (s.prototype = i.prototype, new s)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(e) {
      ! function(i) {
        "use strict";

        function s(i, s, n, a, r) {
          t.asArray(i, !1), t.asArray(s, !1), t.asArray(n, !1), t.asInt(a, !1, !0), t.asNumber(r, !1, !0);
          var o, l, h = e._minimum(i.length, s.length, n.length),
            c = [],
            _ = [],
            u = [];
          for (t.assert(h > a && a > 1, "CCI period must be an integer less than the length of the data and greater than one."), l = 0; l < h; l++) c.push(e._average(i[l], s[l], n[l]));
          o = e._sma(c, a);
          var p;
          for (l = 0; l < o.length; l++) p = c.slice(l, a + l).reduce(function(t, e) {
            return t + Math.abs(o[l] - e)
          }, 0), _.push(p / a);
          for (c.splice(0, a - 1), l = 0; l < o.length; l++) u.push((c[l] - o[l]) / (r * _[l]));
          return u
        }
        var n = function(i) {
          function n(t) {
            var e = i.call(this) || this;
            return e._constant = .015, e.period = 20, e.initialize(t), e
          }
          return __extends(n, i), Object.defineProperty(n.prototype, "constant", {
            get: function() {
              return this._constant
            },
            set: function(e) {
              e !== this._constant && (this._constant = t.asNumber(e, !1), this._clearValues(), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), n.prototype._calculate = function() {
            var t = i.prototype._getLength.call(this);
            if (!(t <= 0)) {
              var n = i.prototype._getBindingValues.call(this, 0),
                a = i.prototype._getBindingValues.call(this, 1),
                r = i.prototype._getBindingValues.call(this, 3),
                o = this._getXValues();
              this._yvals = s(n, a, r, this.period, this.constant), this._xvals = o ? o.slice(this.period - 1) : e._range(this.period - 1, t - 1)
            }
          }, n
        }(i.SingleOverlayIndicatorBase);
        i.CCI = n, i._cci = s
      }(e.analytics || (e.analytics = {}))
    }(e.finance || (e.finance = {}))
  }(t.chart || (t.chart = {}))
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
      function s() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (s.prototype = i.prototype, new s)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(e) {
      ! function(i) {
        "use strict";

        function s(i, s, n, a) {
          t.asArray(i, !1), t.asArray(s, !1), t.asArray(n, !1), t.asInt(a, !1, !0);
          var r, o = e._minimum(i.length, s.length, n.length),
            l = [],
            h = [],
            c = [];
          for (t.assert(o > a && a > 1, "Williams %R period must be an integer less than the length of the data and greater than one."), r = a; r <= i.length; r++) l.push(e._maximum(i.slice(r - a, r))), h.push(e._minimum(s.slice(r - a, r)));
          for (n.splice(0, a - 1), r = 0; r < l.length; r++) c.push((l[r] - n[r]) / (l[r] - h[r]) * -100);
          return c
        }
        var n = function(t) {
          function i(e) {
            var i = t.call(this) || this;
            return i.period = 14, i.initialize(e), i
          }
          return __extends(i, t), i.prototype._calculate = function() {
            var i = t.prototype._getLength.call(this);
            if (!(i <= 0)) {
              var n = t.prototype._getBindingValues.call(this, 0),
                a = t.prototype._getBindingValues.call(this, 1),
                r = t.prototype._getBindingValues.call(this, 3),
                o = this._getXValues();
              this._yvals = s(n, a, r, this.period), this._xvals = o ? o.slice(this.period - 1) : e._range(this.period - 1, i - 1)
            }
          }, i
        }(i.SingleOverlayIndicatorBase);
        i.WilliamsR = n, i._williamsR = s
      }(e.analytics || (e.analytics = {}))
    }(e.finance || (e.finance = {}))
  }(t.chart || (t.chart = {}))
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
      function s() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (s.prototype = i.prototype, new s)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(i) {
      ! function(s) {
        "use strict";
        var n;
        ! function(t) {
          t[t.Simple = 0] = "Simple", t[t.Exponential = 1] = "Exponential"
        }(n = s.MovingAverageType || (s.MovingAverageType = {}));
        var a = function(s) {
          function a(t) {
            var e = s.call(this) || this;
            return e._period = 20, e._type = n.Simple, e._size = .025, e.rendering.addHandler(e._rendering, e), e.initialize(t), e
          }
          return __extends(a, s), Object.defineProperty(a.prototype, "period", {
            get: function() {
              return this._period
            },
            set: function(e) {
              e !== this._period && (this._period = t.asInt(e, !1, !0), this._clearValues(), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(a.prototype, "type", {
            get: function() {
              return this._type
            },
            set: function(e) {
              (e = t.asEnum(e, n, !1)) !== this._type && (this._type = e, this._clearValues(), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(a.prototype, "size", {
            get: function() {
              return this._size
            },
            set: function(e) {
              e !== this._size && (this._size = t.asNumber(e, !1, !0), this._clearValues(), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), a.prototype.getDataRect = function(n, a) {
            if (a) return a;
            var r = null;
            if (s.prototype._getLength.call(this) <= 0) return r;
            this._shouldCalculate() && (this._init(), this._calculate());
            var o = this._upperYVals.concat(this._lowerYVals),
              l = i._minimum(this._xVals),
              h = i._maximum(this._xVals),
              c = i._minimum(o),
              _ = i._maximum(o);
            return e._DataInfo.isValid(l) && e._DataInfo.isValid(h) && e._DataInfo.isValid(c) && e._DataInfo.isValid(_) && (r = new t.Rect(l, c, h - l, _ - c)), r
          }, a.prototype._clearValues = function() {
            s.prototype._clearValues.call(this), this._upperYVals = null, this._lowerYVals = null, this._xVals = null
          }, a.prototype._init = function() {
            s.prototype._init.call(this), this._upperYVals = [], this._lowerYVals = [], this._xVals = []
          }, a.prototype._shouldCalculate = function() {
            return !this._upperYVals || !this._lowerYVals || !this._xVals
          }, a.prototype._calculate = function() {
            var t = this;
            if (!(s.prototype._getLength.call(this) <= 0)) {
              var e, a = s.prototype.getValues.call(this, 0),
                r = this._getXValues();
              switch (this.type) {
                case n.Exponential:
                  e = i._ema(a, this.period);
                  break;
                case n.Simple:
                default:
                  e = i._sma(a, this.period)
              }
              this._xVals = r ? r.slice(this.period - 1) : i._range(this.period - 1, s.prototype._getLength.call(this) - 1), this._upperYVals = e.map(function(e) {
                return e + e * t.size
              }), this._lowerYVals = e.map(function(e) {
                return e - e * t.size
              })
            }
          }, a.prototype._rendering = function(n, a) {
            if (a.cancel = !0, !(s.prototype._getLength.call(this) <= 0)) {
              this._shouldCalculate() && (this._init(), this._calculate());
              var r = this.chart.series.indexOf(this),
                o = a.engine,
                l = this._getAxisX(),
                h = this._getAxisY(),
                c = i._minimum(this._upperYVals.length, this._lowerYVals.length, this._xVals.length),
                _ = e._BasePlotter.cloneStyle(this.style, ["fill"]),
                u = this._getSymbolStroke(r),
                p = this.chart._plotrectId;
              if (c && !(c <= 0)) {
                o.stroke = u, o.strokeWidth = 2;
                for (var d, f, g, y = [], m = [], v = [], V = this._getLength(), b = 0; b < c; b++) g = V - c + b, y.push(l.convert(this._xVals[b])), m.push(h.convert(this._upperYVals[b])), d = this._getDataPoint(this._xVals[b], this._upperYVals[b], r, g, l, h), (f = new e._CircleArea(new t.Point(y[b], m[b]), .5 * o.strokeWidth)).tag = d, this._hitTester.add(f, r), v.push(h.convert(this._lowerYVals[b])), d = this._getDataPoint(this._xVals[b], this._lowerYVals[b], r, g, l, h), (f = new e._CircleArea(new t.Point(y[b], v[b]), .5 * o.strokeWidth)).tag = d, this._hitTester.add(f, r);
                this._hitTester.add(new e._LinesArea(y, m), r), this._hitTester.add(new e._LinesArea(y, v), r), o.drawLines(y, m, null, _, p), o.drawLines(y, v, null, _, p)
              }
            }
          }, a.prototype.getCalculatedValues = function(e) {
            e = t.asString(e, !1);
            var i = [],
              n = 0;
            if (s.prototype._getLength.call(this) <= 0) return i;
            switch (this._shouldCalculate() && (this._init(), this._calculate()), e) {
              case "upperEnvelope":
                for (; n < this._upperYVals.length; n++) i.push({
                  x: this._xVals[n],
                  y: this._upperYVals[n]
                });
                break;
              case "lowerEnvelope":
                for (; n < this._lowerYVals.length; n++) i.push({
                  x: this._xVals[n],
                  y: this._lowerYVals[n]
                })
            }
            return i
          }, a
        }(s.OverlayIndicatorBase);
        s.Envelopes = a
      }(i.analytics || (i.analytics = {}))
    }(e.finance || (e.finance = {}))
  }(t.chart || (t.chart = {}))
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
      function s() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (s.prototype = i.prototype, new s)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(i) {
      ! function(s) {
        "use strict";

        function n(e, s, n) {
          t.asArray(e, !1), t.asInt(s, !1, !0), t.asNumber(n, !1, !0), t.assert(e.length > s && s > 1, "Bollinger Bands period must be an integer less than the length of the data and greater than one.");
          var a, r = i._sma(e, s),
            o = [];
          for (a = s; a <= e.length; a++) o.push(i._stdDeviation(e.slice(a - s, a)));
          var l = r,
            h = r.map(function(t, e) {
              return t + o[e] * n
            });
          return {
            lowers: r.map(function(t, e) {
              return t - o[e] * n
            }),
            middles: l,
            uppers: h
          }
        }
        var a = function(s) {
          function a(t) {
            var e = s.call(this) || this;
            return e._period = 20, e._multiplier = 2, e.rendering.addHandler(e._rendering, e), e.initialize(t), e
          }
          return __extends(a, s), Object.defineProperty(a.prototype, "period", {
            get: function() {
              return this._period
            },
            set: function(e) {
              e !== this._period && (this._period = t.asInt(e, !1, !0), this._clearValues(), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(a.prototype, "multiplier", {
            get: function() {
              return this._multiplier
            },
            set: function(e) {
              e !== this._multiplier && (this._multiplier = t.asNumber(e, !1, !0), this._clearValues(), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), a.prototype.getDataRect = function(n, a) {
            if (a) return a;
            if (s.prototype._getLength.call(this) <= 0) return null;
            this._shouldCalculate() && (this._init(), this._calculate());
            var r = this._upperYVals.concat(this._lowerYVals),
              o = i._minimum(this._xVals),
              l = i._maximum(this._xVals),
              h = i._minimum(r),
              c = i._maximum(r);
            return e._DataInfo.isValid(o) && e._DataInfo.isValid(l) && e._DataInfo.isValid(h) && e._DataInfo.isValid(c) ? new t.Rect(o, h, l - o, c - h) : null
          }, a.prototype._clearValues = function() {
            s.prototype._clearValues.call(this), this._upperYVals = null, this._middleYVals = null, this._lowerYVals = null, this._xVals = null
          }, a.prototype._shouldCalculate = function() {
            return !(this._upperYVals && this._middleYVals && this._lowerYVals && this._xVals)
          }, a.prototype._init = function() {
            s.prototype._init.call(this), this._upperYVals = [], this._middleYVals = [], this._lowerYVals = [], this._xVals = []
          }, a.prototype._calculate = function() {
            var t = s.prototype._getLength.call(this);
            if (!(t <= 0)) {
              var e = s.prototype.getValues.call(this, 0),
                a = this._getXValues(),
                r = n(e, this.period, this.multiplier);
              this._upperYVals = r.uppers, this._middleYVals = r.middles, this._lowerYVals = r.lowers, this._xVals = a ? a.slice(this.period - 1) : i._range(this.period - 1, t - 1)
            }
          }, a.prototype._rendering = function(n, a) {
            if (a.cancel = !0, !(s.prototype._getLength.call(this) <= 0)) {
              this._shouldCalculate() && (this._init(), this._calculate());
              var r = this.chart.series.indexOf(this),
                o = a.engine,
                l = this._getAxisX(),
                h = this._getAxisY(),
                c = i._minimum(this._upperYVals.length, this._middleYVals.length, this._lowerYVals.length, this._xVals.length),
                _ = e._BasePlotter.cloneStyle(this.style, ["fill"]),
                u = this._getSymbolStroke(r),
                p = this.chart._plotrectId;
              if (c && !(c <= 0)) {
                o.stroke = u, o.strokeWidth = 2;
                for (var d, f, g, y = [], m = [], v = [], V = [], b = this._getLength(), x = 0; x < c; x++) g = b - c + x, y.push(l.convert(this._xVals[x])), m.push(h.convert(this._upperYVals[x])), d = this._getDataPoint(this._xVals[x], this._upperYVals[x], r, g, l, h), (f = new e._CircleArea(new t.Point(y[x], m[x]), .5 * o.strokeWidth)).tag = d, this._hitTester.add(f, r), v.push(h.convert(this._middleYVals[x])), d = this._getDataPoint(this._xVals[x], this._middleYVals[x], r, g, l, h), (f = new e._CircleArea(new t.Point(y[x], v[x]), .5 * o.strokeWidth)).tag = d, this._hitTester.add(f, r), V.push(h.convert(this._lowerYVals[x])), d = this._getDataPoint(this._xVals[x], this._lowerYVals[x], r, g, l, h), (f = new e._CircleArea(new t.Point(y[x], V[x]), .5 * o.strokeWidth)).tag = d, this._hitTester.add(f, r);
                this._hitTester.add(new e._LinesArea(y, m), r), this._hitTester.add(new e._LinesArea(y, v), r), this._hitTester.add(new e._LinesArea(y, V), r), o.drawLines(y, m, null, _, p), o.drawLines(y, v, null, _, p), o.drawLines(y, V, null, _, p)
              }
            }
          }, a.prototype.getCalculatedValues = function(e) {
            e = t.asString(e, !1);
            var i = [],
              n = 0;
            if (s.prototype._getLength.call(this) <= 0) return i;
            switch (this._shouldCalculate() && (this._init(), this._calculate()), e) {
              case "upperBand":
                for (; n < this._upperYVals.length; n++) i.push({
                  x: this._xVals[n],
                  y: this._upperYVals[n]
                });
                break;
              case "middleBand":
                for (; n < this._middleYVals.length; n++) i.push({
                  x: this._xVals[n],
                  y: this._middleYVals[n]
                });
                break;
              case "lowerBand":
                for (; n < this._lowerYVals.length; n++) i.push({
                  x: this._xVals[n],
                  y: this._lowerYVals[n]
                })
            }
            return i
          }, a
        }(s.OverlayIndicatorBase);
        s.BollingerBands = a, s._bollingerBands = n
      }(i.analytics || (i.analytics = {}))
    }(e.finance || (e.finance = {}))
  }(t.chart || (t.chart = {}))
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
      function s() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (s.prototype = i.prototype, new s)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(e) {
      ! function(i) {
        "use strict";

        function s(i, s) {
          t.asArray(i, !1), t.asInt(s, !0, !1), t.assert(i.length > s && s > 1, "RSI period must be an integer less than the length of the data and greater than one.");
          var n, a, r, o, l = [],
            h = [],
            c = [],
            _ = [];
          for (o = 1; o < i.length; o++) l.push(i[o] - i[o - 1]);
          for (n = l.map(function(t) {
            return t > 0 ? t : 0
          }), a = l.map(function(t) {
            return t < 0 ? Math.abs(t) : 0
          }), o = s; o <= l.length; o++) o === s ? (h.push(e._sum(n.slice(o - s, o)) / s), c.push(e._sum(a.slice(o - s, o)) / s)) : (h.push((n[o - 1] + h[o - s - 1] * (s - 1)) / s), c.push((a[o - 1] + c[o - s - 1] * (s - 1)) / s)), r = h[o - s] / c[o - s], r = isFinite(r) ? r : 0, _.push(100 - 100 / (1 + r));
          return _
        }
        var n = function(t) {
          function i(e) {
            var i = t.call(this) || this;
            return i.period = 14, i.initialize(e), i
          }
          return __extends(i, t), i.prototype._calculate = function() {
            var i = t.prototype._getLength.call(this);
            if (!(i <= 0)) {
              var n = t.prototype._getBindingValues.call(this, 0),
                a = this._getXValues();
              this._yvals = s(n, this.period), this._xvals = a ? a.slice(this.period) : e._range(this.period, i)
            }
          }, i
        }(i.SingleOverlayIndicatorBase);
        i.RSI = n, i._rsi = s
      }(e.analytics || (e.analytics = {}))
    }(e.finance || (e.finance = {}))
  }(t.chart || (t.chart = {}))
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
      function s() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (s.prototype = i.prototype, new s)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(i) {
      ! function(s) {
        "use strict";

        function n(e, s, n, a) {
          t.asArray(e, !1), t.asInt(s, !1, !0), t.asInt(n, !1, !0), t.asInt(a, !1, !0);
          var r, o = s > n;
          o && (r = n, n = s, s = r);
          var l, h, c = i._ema(e, s),
            _ = i._ema(e, n),
            u = [],
            p = [];
          for (c.splice(0, n - s), h = 0; h < c.length; h++) r = c[h] - _[h], o && (r *= -1), u.push(r);
          l = i._ema(u, a);
          var d = u.slice(u.length - l.length, u.length);
          for (h = 0; h < d.length; h++) p.push(d[h] - l[h]);
          return {
            macds: u,
            signals: l,
            histograms: p
          }
        }
        var a = function(e) {
          function s(t) {
            var i = e.call(this, t) || this;
            return i._fastPeriod = 12, i._slowPeriod = 26, i._smoothingPeriod = 9, i
          }
          return __extends(s, e), Object.defineProperty(s.prototype, "fastPeriod", {
            get: function() {
              return this._fastPeriod
            },
            set: function(e) {
              e !== this._fastPeriod && (this._fastPeriod = t.asInt(e, !1, !0), this._clearValues(), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "slowPeriod", {
            get: function() {
              return this._slowPeriod
            },
            set: function(e) {
              e !== this._slowPeriod && (this._slowPeriod = t.asInt(e, !1, !0), this._clearValues(), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(s.prototype, "smoothingPeriod", {
            get: function() {
              return this._smoothingPeriod
            },
            set: function(e) {
              e !== this._smoothingPeriod && (this._smoothingPeriod = t.asInt(e, !1, !0), this._clearValues(), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), s.prototype._clearValues = function() {
            e.prototype._clearValues.call(this), this._macdVals = null, this._macdXVals = null, this._signalVals = null, this._signalXVals = null, this._histogramVals = null, this._histogramXVals = null
          }, s.prototype._shouldCalculate = function() {
            return !(this._macdVals && this._macdXVals && this._signalVals && this._signalXVals && this._histogramVals && this._histogramXVals)
          }, s.prototype._init = function() {
            e.prototype._init.call(this), this._macdVals = [], this._macdXVals = [], this._signalVals = [], this._signalXVals = [], this._histogramVals = [], this._histogramXVals = []
          }, s.prototype._calculate = function() {
            var t = e.prototype._getLength.call(this);
            if (!(t <= 0)) {
              var s = e.prototype.getValues.call(this, 0),
                a = this._getXValues(),
                r = n(s, this.fastPeriod, this.slowPeriod, this.smoothingPeriod);
              this._macdVals = r.macds, this._signalVals = r.signals, this._histogramVals = r.histograms, this._macdXVals = a ? a.slice(t - this._macdVals.length, t) : i._range(t - this._macdVals.length, t - 1), this._signalXVals = a ? a.slice(t - this._signalVals.length, t) : i._range(t - this._signalVals.length, t - 1), this._histogramXVals = a ? a.slice(t - this._histogramVals.length, t) : i._range(t - this._histogramVals.length, t - 1)
            }
          }, s
        }(s.OverlayIndicatorBase);
        s.MacdBase = a;
        var r = function(s) {
          function n(t) {
            var e = s.call(this) || this;
            return e._seriesCount = 2, e.rendering.addHandler(e._rendering, e), e.initialize(t), e
          }
          return __extends(n, s), Object.defineProperty(n.prototype, "styles", {
            get: function() {
              return this._styles
            },
            set: function(t) {
              t !== this._styles && (this._styles = t, this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), n.prototype.legendItemLength = function() {
            return this.name.indexOf(",") > -1 ? 2 : 1
          }, n.prototype.getDataRect = function(n, a) {
            if (a) return a;
            var r = null;
            if (s.prototype._getLength.call(this) <= 0) return r;
            this._shouldCalculate() && (this._init(), this._calculate());
            var o = [],
              l = [];
            l.push.apply(l, this._macdXVals), l.push.apply(l, this._signalXVals), o.push.apply(o, this._macdVals), o.push.apply(o, this._signalVals);
            var h = i._minimum(l),
              c = i._maximum(l),
              _ = i._minimum(o),
              u = i._maximum(o);
            return e._DataInfo.isValid(h) && e._DataInfo.isValid(c) && e._DataInfo.isValid(_) && e._DataInfo.isValid(u) && (r = new t.Rect(h, _, c - h, u - _)), r
          }, n.prototype._rendering = function(i, n) {
            if (n.cancel = !0, !(s.prototype._getLength.call(this) <= 0)) {
              this._shouldCalculate() && (this._init(), this._calculate());
              var a = this.chart.series.indexOf(this),
                r = n.engine,
                o = this._getAxisX(),
                l = this._getAxisY(),
                h = e._BasePlotter.cloneStyle(this.style, ["fill"]),
                c = this._getSymbolStroke(a),
                _ = this.chart._plotrectId,
                u = null,
                p = c,
                d = 2,
                f = null,
                g = c,
                y = 2;
              this.styles && t.isObject(this.styles) && (this.styles.macdLine && t.isObject(this.styles.macdLine) && (p = (u = e._BasePlotter.cloneStyle(this.styles.macdLine, ["fill"])).stroke ? u.stroke : c, d = u.strokeWidth ? u.strokeWidth : 2), this.styles.signalLine && t.isObject(this.styles.signalLine) && (g = (f = e._BasePlotter.cloneStyle(this.styles.signalLine, ["fill"])).stroke ? f.stroke : c, y = f.strokeWidth ? f.strokeWidth : 2));
              var m, v, V, b, x = [],
                P = [],
                w = [],
                k = [],
                L = this._getLength();
              for (V = 0; V < this._macdVals.length; V++) b = L - this._macdVals.length + V, P.push(o.convert(this._macdXVals[V])), x.push(l.convert(this._macdVals[V])), (m = this._getDataPoint(this._macdXVals[V], this._macdVals[V], a, b, o, l)).name = this._getName(0), (v = new e._CircleArea(new t.Point(P[V], x[V]), .5 * r.strokeWidth)).tag = m, this._hitTester.add(v, a);
              for (this._hitTester.add(new e._LinesArea(P, x), a), r.stroke = p, r.strokeWidth = d, r.drawLines(P, x, null, h, _), V = 0; V < this._signalVals.length; V++) b = L - this._signalVals.length + V, k.push(o.convert(this._signalXVals[V])), w.push(l.convert(this._signalVals[V])), (m = this._getDataPoint(this._signalXVals[V], this._signalVals[V], a, b, o, l)).name = this._getName(1), (v = new e._CircleArea(new t.Point(k[V], w[V]), .5 * r.strokeWidth)).tag = m, this._hitTester.add(v, a);
              this._hitTester.add(new e._LinesArea(k, w), a), r.stroke = g, r.strokeWidth = y, r.drawLines(k, w, null, h, _)
            }
          }, n.prototype.getCalculatedValues = function(e) {
            e = t.asString(e, !1);
            var i = [],
              n = 0;
            if (s.prototype._getLength.call(this) <= 0) return i;
            switch (this._shouldCalculate() && (this._init(), this._calculate()), e) {
              case "macdLine":
                for (; n < this._macdVals.length; n++) i.push({
                  x: this._macdXVals[n],
                  y: this._macdVals[n]
                });
                break;
              case "signalLine":
                for (; n < this._signalVals.length; n++) i.push({
                  x: this._signalXVals[n],
                  y: this._signalVals[n]
                })
            }
            return i
          }, n
        }(a);
        s.Macd = r;
        var o = function(s) {
          function n(t) {
            return s.call(this, t) || this
          }
          return __extends(n, s), n.prototype.getValues = function(t) {
            var e = null;
            return s.prototype._getLength.call(this) <= 0 ? e : (this._shouldCalculate() && (this._init(), this._calculate()), 0 === t ? e = this._histogramVals : 1 === t && (e = this._histogramXVals), e)
          }, n.prototype.getDataRect = function(n, a) {
            if (a) return a;
            var r = null;
            if (s.prototype._getLength.call(this) <= 0) return r;
            this._shouldCalculate() && (this._init(), this._calculate());
            var o = i._minimum(this._histogramXVals),
              l = i._maximum(this._histogramXVals),
              h = i._minimum(this._histogramVals),
              c = i._maximum(this._histogramVals);
            return e._DataInfo.isValid(o) && e._DataInfo.isValid(l) && e._DataInfo.isValid(h) && e._DataInfo.isValid(c) && (r = new t.Rect(o, h, l - o, c - h)), r
          }, n.prototype._getChartType = function() {
            return e.ChartType.Column
          }, n.prototype._getItem = function(t) {
            return t = s.prototype._getLength.call(this) - i._minimum(this._histogramVals.length, this._histogramXVals.length) + t, s.prototype._getItem.call(this, t)
          }, n
        }(a);
        s.MacdHistogram = o, s._macd = n
      }(i.analytics || (i.analytics = {}))
    }(e.finance || (e.finance = {}))
  }(t.chart || (t.chart = {}))
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
      function s() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (s.prototype = i.prototype, new s)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(i) {
      ! function(s) {
        "use strict";

        function n(e, s, n, a, r, o) {
          t.asArray(e, !1), t.asArray(s, !1), t.asArray(n, !1), t.asInt(a, !1, !0), t.asInt(r, !1, !0), t.asInt(o, !0, !0);
          var l, h, c = [],
            _ = [],
            u = [];
          for (h = a; h <= e.length; h++) c.push(i._maximum(e.slice(h - a, h))), _.push(i._minimum(s.slice(h - a, h)));
          for (n = n.slice(a - 1), h = 0; h < n.length; h++) u.push((n[h] - _[h]) / (c[h] - _[h]) * 100);
          return o && o > 1 && (u = i._sma(u, o)), l = i._sma(u, r), {
            ks: u,
            ds: l
          }
        }
        var a = function(s) {
          function a(t) {
            var e = s.call(this) || this;
            return e._kPeriod = 14, e._dPeriod = 3, e._smoothingPeriod = 1, e._seriesCount = 2, e.rendering.addHandler(e._rendering, e), e.initialize(t), e
          }
          return __extends(a, s), Object.defineProperty(a.prototype, "kPeriod", {
            get: function() {
              return this._kPeriod
            },
            set: function(e) {
              e !== this._kPeriod && (this._kPeriod = t.asInt(e, !1, !0), this._clearValues(), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(a.prototype, "dPeriod", {
            get: function() {
              return this._dPeriod
            },
            set: function(e) {
              e !== this._dPeriod && (this._dPeriod = t.asInt(e, !1, !0), this._clearValues(), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(a.prototype, "smoothingPeriod", {
            get: function() {
              return this._smoothingPeriod
            },
            set: function(e) {
              e !== this._smoothingPeriod && (this._smoothingPeriod = t.asInt(e, !1, !0), this._clearValues(), this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(a.prototype, "styles", {
            get: function() {
              return this._styles
            },
            set: function(t) {
              t !== this._styles && (this._styles = t, this._invalidate())
            },
            enumerable: !0,
            configurable: !0
          }), a.prototype.getDataRect = function(n, a) {
            if (a) return a;
            var r = null;
            if (s.prototype._getLength.call(this) <= 0) return r;
            this._shouldCalculate() && (this._init(), this._calculate());
            var o = this._kVals.concat(this._dVals),
              l = this._kXVals.concat(this._dXVals),
              h = i._minimum(l),
              c = i._maximum(l),
              _ = i._minimum(o),
              u = i._maximum(o);
            return e._DataInfo.isValid(h) && e._DataInfo.isValid(c) && e._DataInfo.isValid(_) && e._DataInfo.isValid(u) && (r = new t.Rect(h, _, c - h, u - _)), r
          }, a.prototype._clearValues = function() {
            s.prototype._clearValues.call(this), this._kVals = null, this._kXVals = null, this._dVals = null, this._dXVals = null
          }, a.prototype._shouldCalculate = function() {
            return !(this._kVals && this._kXVals && this._dVals && this._dXVals)
          }, a.prototype._init = function() {
            s.prototype._init.call(this), this._kVals = [], this._kXVals = [], this._dVals = [], this._dXVals = []
          }, a.prototype._calculate = function() {
            var t = s.prototype._getLength.call(this);
            if (!(s.prototype._getLength.call(this) <= 0)) {
              var e = s.prototype._getBindingValues.call(this, 0),
                a = s.prototype._getBindingValues.call(this, 1),
                r = s.prototype._getBindingValues.call(this, 3),
                o = this._getXValues(),
                l = n(e, a, r, this.kPeriod, this.dPeriod, this.smoothingPeriod);
              this._kVals = l.ks, this._dVals = l.ds, this._kXVals = o ? o.slice(this.kPeriod - 1) : i._range(this.kPeriod - 1, t - 1), this.smoothingPeriod && this.smoothingPeriod > 1 && (this._kXVals = this._kXVals.slice(this._kXVals.length - this._kVals.length, this._kXVals.length)), this._dXVals = this._kXVals.slice(this._kXVals.length - this._dVals.length, this._kXVals.length)
            }
          }, a.prototype._rendering = function(i, n) {
            if (n.cancel = !0, !(s.prototype._getLength.call(this) <= 0)) {
              this._shouldCalculate() && (this._init(), this._calculate());
              var a = this.chart.series.indexOf(this),
                r = n.engine,
                o = this._getAxisX(),
                l = this._getAxisY(),
                h = e._BasePlotter.cloneStyle(this.style, ["fill"]),
                c = this._getSymbolStroke(a),
                _ = this.chart._plotrectId,
                u = null,
                p = c,
                d = 2,
                f = null,
                g = c,
                y = 2;
              this.styles && t.isObject(this.styles) && (this.styles.kLine && t.isObject(this.styles.kLine) && (p = (u = e._BasePlotter.cloneStyle(this.styles.kLine, ["fill"])).stroke ? u.stroke : c, d = u.strokeWidth ? u.strokeWidth : 2), this.styles.dLine && t.isObject(this.styles.dLine) && (g = (f = e._BasePlotter.cloneStyle(this.styles.dLine, ["fill"])).stroke ? f.stroke : c, y = f.strokeWidth ? f.strokeWidth : 2));
              var m, v, V, b, x = [],
                P = [],
                w = [],
                k = [],
                L = this._getLength();
              for (V = 0; V < this._kVals.length; V++) b = L - this._kVals.length + V, P.push(o.convert(this._kXVals[V])), x.push(l.convert(this._kVals[V])), (m = this._getDataPoint(this._kXVals[V], this._kVals[V], a, b, o, l)).name = this._getName(0), (v = new e._CircleArea(new t.Point(P[V], x[V]), .5 * r.strokeWidth)).tag = m, this._hitTester.add(v, a);
              for (this._hitTester.add(new e._LinesArea(P, x), a), r.stroke = p, r.strokeWidth = d, r.drawLines(P, x, null, h, _), V = 0; V < this._dVals.length; V++) b = L - this._dVals.length + V, k.push(o.convert(this._dXVals[V])), w.push(l.convert(this._dVals[V])), (m = this._getDataPoint(this._dXVals[V], this._dVals[V], a, b, o, l)).name = this._getName(1), (v = new e._CircleArea(new t.Point(k[V], w[V]), .5 * r.strokeWidth)).tag = m, this._hitTester.add(v, a);
              this._hitTester.add(new e._LinesArea(k, w), a), r.stroke = g, r.strokeWidth = y, r.drawLines(k, w, null, h, _)
            }
          }, a.prototype.getCalculatedValues = function(e) {
            e = t.asString(e, !1);
            var i = [],
              n = 0;
            if (s.prototype._getLength.call(this) <= 0) return i;
            switch (this._shouldCalculate() && (this._init(), this._calculate()), e) {
              case "kLine":
                for (; n < this._kVals.length; n++) i.push({
                  x: this._kXVals[n],
                  y: this._kVals[n]
                });
                break;
              case "dLine":
                for (; n < this._dVals.length; n++) i.push({
                  x: this._dXVals[n],
                  y: this._dVals[n]
                })
            }
            return i
          }, a
        }(s.OverlayIndicatorBase);
        s.Stochastic = a, s._stochastic = n
      }(i.analytics || (i.analytics = {}))
    }(e.finance || (e.finance = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
