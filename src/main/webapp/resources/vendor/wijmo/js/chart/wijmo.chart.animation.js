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
        for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
      };
    return function(e, n) {
      function i() {
        this.constructor = e
      }
      t(e, n), e.prototype = null === n ? Object.create(n) : (i.prototype = n.prototype, new i)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(n) {
      var i;
      ! function(t) {
        t[t.Linear = 0] = "Linear", t[t.Swing = 1] = "Swing", t[t.EaseInQuad = 2] = "EaseInQuad", t[t.EaseOutQuad = 3] = "EaseOutQuad", t[t.EaseInOutQuad = 4] = "EaseInOutQuad", t[t.EaseInCubic = 5] = "EaseInCubic", t[t.EaseOutCubic = 6] = "EaseOutCubic", t[t.EaseInOutCubic = 7] = "EaseInOutCubic", t[t.EaseInQuart = 8] = "EaseInQuart", t[t.EaseOutQuart = 9] = "EaseOutQuart", t[t.EaseInOutQuart = 10] = "EaseInOutQuart", t[t.EaseInQuint = 11] = "EaseInQuint", t[t.EaseOutQuint = 12] = "EaseOutQuint", t[t.EaseInOutQuint = 13] = "EaseInOutQuint", t[t.EaseInSine = 14] = "EaseInSine", t[t.EaseOutSine = 15] = "EaseOutSine", t[t.EaseInOutSine = 16] = "EaseInOutSine", t[t.EaseInExpo = 17] = "EaseInExpo", t[t.EaseOutExpo = 18] = "EaseOutExpo", t[t.EaseInOutExpo = 19] = "EaseInOutExpo", t[t.EaseInCirc = 20] = "EaseInCirc", t[t.EaseOutCirc = 21] = "EaseOutCirc", t[t.EaseInOutCirc = 22] = "EaseInOutCirc", t[t.EaseInBack = 23] = "EaseInBack", t[t.EaseOutBack = 24] = "EaseOutBack", t[t.EaseInOutBack = 25] = "EaseInOutBack", t[t.EaseInBounce = 26] = "EaseInBounce", t[t.EaseOutBounce = 27] = "EaseOutBounce", t[t.EaseInOutBounce = 28] = "EaseInOutBounce", t[t.EaseInElastic = 29] = "EaseInElastic", t[t.EaseOutElastic = 30] = "EaseOutElastic", t[t.EaseInOutElastic = 31] = "EaseInOutElastic"
      }(i = n.Easing || (n.Easing = {}));
      var a;
      ! function(t) {
        t[t.All = 0] = "All", t[t.Point = 1] = "Point", t[t.Series = 2] = "Series"
      }(a = n.AnimationMode || (n.AnimationMode = {}));
      var o = function() {
        function n(n, i) {
          this._play = !0;
          var a = this,
            o = n.hostElement,
            r = new t.Size(o.offsetWidth, o.offsetHeight);
          a._chart = n, a._updateEventArgs = [], n instanceof e.FlexPie ? a._animation = new s(n, a._updateEventArgs) : (t.chart.radar && t.chart.radar.FlexRadar && n instanceof t.chart.radar.FlexRadar ? a._animation = new l(n, a._updateEventArgs) : a._animation = new u(n, a._updateEventArgs), a._chartType = n.chartType), i && a._initOptions(i), n.beginUpdate(), window.setTimeout(function() {
            n.rendered.addHandler(a._playAnimation, a), n.endUpdate()
          }, 0), a._setCV(n.collectionView), window.addEventListener("resize", function(e) {
            var n = new t.Size(o.offsetWidth, o.offsetHeight);
            r.equals(n) || (a._play = !1, r = n)
          })
        }
        return n.prototype._initOptions = function(t) {
          t.duration && (this.duration = t.duration), t.easing && (this.easing = t.easing), t.animationMode && (this.animationMode = t.animationMode)
        }, n.prototype._setCV = function(t) {
          this._cv = t, this._animation._clearState()
        }, Object.defineProperty(n.prototype, "animationMode", {
          get: function() {
            return this._animation.animationMode
          },
          set: function(e) {
            (e = t.asEnum(e, a)) != this.animationMode && (this._animation.animationMode = e)
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "easing", {
          get: function() {
            return this._animation.easing
          },
          set: function(e) {
            (e = t.asEnum(e, i)) != this.easing && (this._animation.easing = e)
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "duration", {
          get: function() {
            return this._animation.duration
          },
          set: function(e) {
            (e = t.asNumber(e)) != this.duration && (this._animation.duration = e)
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "axisAnimation", {
          get: function() {
            return this._animation.axisAnimation
          },
          set: function(e) {
            (e = t.asBoolean(e)) != this.axisAnimation && (this._animation.axisAnimation = e)
          },
          enumerable: !0,
          configurable: !0
        }), n.prototype._playAnimation = function() {
          var t = this,
            e = t._chart,
            n = e.chartType;
          t._cv !== e.collectionView && t._setCV(e.collectionView), null != t._chartType && t._chartType !== n && (t._chartType = n, t._animation._clearState()), t._play ? t._animation.playAnimation() : t._play = !0
        }, n.prototype.animate = function() {
          var t = this._chart;
          if (t) {
            var e = t.itemsSource;
            t.beginUpdate(), t.itemsSource = null, t.itemsSource = e, t.endUpdate()
          }
        }, n
      }();
      n.ChartAnimation = o;
      var r = function() {
          function e(t, e) {
            this._axisAnimation = !0, this._chart = t, this._timers = []
          }
          return Object.defineProperty(e.prototype, "animationMode", {
            get: function() {
              return this._animationMode || a.All
            },
            set: function(e) {
              (e = t.asEnum(e, a, !1)) !== this._animationMode && (this._clearState(), this._animationMode = e)
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(e.prototype, "easing", {
            get: function() {
              return null == this._easing ? i.Swing : this._easing
            },
            set: function(e) {
              e !== this._easing && (this._easing = t.asEnum(e, i, !1))
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(e.prototype, "duration", {
            get: function() {
              return this._duration || 400
            },
            set: function(e) {
              e !== this._duration && (this._duration = t.asNumber(e, !1, !0))
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(e.prototype, "axisAnimation", {
            get: function() {
              return !!this._axisAnimation
            },
            set: function(e) {
              e !== this._axisAnimation && (this._axisAnimation = t.asBoolean(e, !1))
            },
            enumerable: !0,
            configurable: !0
          }), e.prototype.playAnimation = function() {}, e.prototype._clearState = function() {
            this._previousState && (this._previousState = null), this._currentState && (this._currentState = null)
          }, e.prototype._setInitState = function(t, e, n) {
            var i = c.parseAttrs(e, n);
            c.setElementAttr(t, i, 0)
          }, e.prototype._getAnimation = function(t, e) {
            return t[e] || (t[e] = []), t[e]
          }, e.prototype._toggleVisibility = function(t, e) {
            e ? c.playAnimation(t, {
              opacity: 0
            }, {
              opacity: 1
            }, null, i.Swing, 100) : t.setAttribute("opacity", "0")
          }, e.prototype._toggleDataLabelVisibility = function(t) {
            var e = this._chart.hostElement,
              n = e && e.querySelector(".wj-data-labels");
            n && this._toggleVisibility(n, t)
          }, e.prototype._playAnimation = function(e) {
            var n, i = this,
              a = this,
              o = a.duration,
              r = a.easing,
              s = e.length;
            a._toggleDataLabelVisibility(!1), n = a._getDurationAndDelay(e.length, o), this._timers && this._timers.length && (this._timers.forEach(function(t) {
              return window.clearInterval(t)
            }), this._timers.length = 0), e.forEach(function(e, o) {
              var u;
              e && (u = window.setTimeout(function() {
                var u;
                e.forEach(function(e, l) {
                  if (e && e.ele) {
                    if (o === s - 1 && 0 === l) {
                      var p = e.done;
                      e.done = function() {
                        a._toggleDataLabelVisibility(!0), p && p()
                      }
                    }
                    t.isArray(e.ele) ? (u = c.playAnimations(e.ele, e.from, e.to, e.done, r, n.duration), i._timers = i._timers.concat.apply(u)) : (u = c.playAnimation(e.ele, e.from, e.to, e.done, r, n.duration), i._timers.push(u))
                  }
                })
              }, n.delay * o), i._timers.push(u))
            })
          }, e.prototype._getDurationAndDelay = function(t, e) {
            var n = {
              duration: e,
              delay: 0
            };
            return t > 1 && (this._previousState ? (n.duration = e / t, n.delay = e / t) : (n.duration = .5 * e, n.delay = .5 * e / (t - 1))), n
          }, e
        }(),
        s = function(t) {
          function e(e, n) {
            var i = t.call(this, e, n) || this;
            return e.selectionChanged.addHandler(i._selectionChanged, i), i
          }
          return __extends(e, t), e.prototype._selectionChanged = function() {
            this._isSelectionChanged = !0
          }, e.prototype._clearState = function() {
            t.prototype._clearState.call(this), this._isSelectionChanged = !1
          }, e.prototype._getElementRotate = function(t) {
            var e, n = t.getAttribute("transform");
            return n = n && n.indexOf("rotate") > -1 ? (n = n.replace("rotate(", "").replace(")", "")).indexOf(",") > -1 ? n.split(",").map(function(t) {
              return +t
            }) : n.split(" ").map(function(t) {
              return +t
            }) : [0, (e = this._chart._areas[0].center).x, e.y]
          }, e.prototype._getDurationAndDelay = function(t, e) {
            var n = {
              duration: e,
              delay: 0
            };
            return this.animationMode === a.Point && t > 1 && (n.duration = e / t, n.delay = e / t), n
          }, e.prototype.playAnimation = function() {
            t.prototype.playAnimation.call(this);
            var e = this,
              n = [];
            e._playPieAnimation(n), n.length && e._playAnimation(n)
          }, e.prototype._playPieAnimation = function(t) {
            var e = this,
              n = e._chart,
              i = !0;
            if (e._previousState = e._currentState, e._currentState = {
              areas: n._areas,
              pels: n._pels,
              rotate: n._pels.length && e._getElementRotate(n._pels[0].parentNode)
            }, e._previousState && (i = !1), e._isSelectionChanged) return n.isAnimated || e._playSelectPieAnimation(t), void(e._isSelectionChanged = !1);
            i ? e._playLoadPieAnimation(t) : e._playUpdatePieAnimation(t)
          }, e.prototype._playSelectPieAnimation = function(t) {
            if (null != this._previousState) {
              var e, n, i, a = this,
                o = a._chart._pels[0].parentNode,
                r = a._previousState.rotate,
                s = a._getElementRotate(o),
                u = r[0],
                l = s[0];
              u !== l && (u - l > 180 ? s[0] += 360 : l - u > 180 && (r[0] += 360), e = a._getAnimation(t, 0), n = {
                rotate: r
              }, i = {
                rotate: s
              }, a._setInitState(o, n, i), e.push({
                ele: o,
                from: n,
                to: i
              }))
            }
          }, e.prototype._playUpdatePieAnimation = function(t) {
            var e, n, i, a, o = this,
              r = o._chart,
              s = o._previousState,
              u = r._areas,
              l = r._pels,
              p = s.areas.length,
              h = u.length,
              m = Math.max(h, p),
              d = o._getAnimation(t, 0),
              f = 0;
            if (0 !== h && 0 !== p)
              for (o._playSelectPieAnimation(t), e = 0; e < m; e++) n = {}, l[e] && l[e].childNodes && l[e].childNodes.length > 0 && (e < h && e < p && (i = u[0], 0 === e && (f = i.angle), 1 === p ? l[e].childNodes[0].setAttribute("d", c.getPathDescOfPie(i.center.x, i.center.y, i.radius, f, 2 * Math.PI, i.innerRadius || 0)) : l[e].childNodes[0].setAttribute("d", s.pels[e].childNodes[0].getAttribute("d"))), e < h ? (i = u[e], n.to = {
                pie: [i.center.x, i.center.y, i.radius, i.angle, i.sweep, i.innerRadius || 0]
              }, n.ele = l[e].childNodes[0]) : (i = u[0], a = s.pels[e], n.to = {
                pie: [i.center.x, i.center.y, i.radius, f + 2 * Math.PI, 0, i.innerRadius || 0]
              }, l[0].parentNode.appendChild(a), n.done = function(t) {
                return function() {
                  t.parentNode.removeChild(t)
                }
              }(a), n.ele = a.childNodes[0]), e < p ? (i = s.areas[e], n.from = {
                pie: [i.center.x, i.center.y, i.radius, i.angle, i.sweep, i.innerRadius || 0]
              }) : (l[e].childNodes[0].setAttribute("d", c.getPathDescOfPie(i.center.x, i.center.y, i.radius, 2 * Math.PI + f, 0, i.innerRadius || 0)), i = s.areas[0], n.from = {
                pie: [i.center.x, i.center.y, i.radius, 2 * Math.PI + f, 0, i.innerRadius || 0]
              }), d.push(n))
          }, e.prototype._playLoadPieAnimation = function(t) {
            var e = this,
              n = e._chart,
              i = e.animationMode,
              o = n._areas;
            n._pels.forEach(function(n, r) {
              var s, u = n.childNodes[0],
                l = {},
                c = {};
              u && (i === a.Point ? (e._parsePathByAngle(o[r], l, c), s = e._getAnimation(t, r)) : (e._parsePathByRadius(o[r], l, c), s = e._getAnimation(t, 0)), e._setInitState(u, l, c), s.push({
                ele: u,
                from: l,
                to: c
              }))
            })
          }, e.prototype._parsePathByRadius = function(t, e, n) {
            var i, a, o = t.center.x,
              r = t.center.y,
              s = t.radius,
              u = t.angle,
              l = t.sweep;
            i = [o, r, 0, u, l, 0], a = [o, r, s, u, l, t.innerRadius || 0], e.pie = i, n.pie = a
          }, e.prototype._parsePathByAngle = function(t, e, n) {
            var i, a, o = t.center.x,
              r = t.center.y,
              s = t.radius,
              u = t.angle,
              l = t.sweep,
              c = t.innerRadius;
            i = [o, r, s, u, 0, c || 0], a = [o, r, s, u, l, c || 0], e.pie = i, e["stroke-width"] = 0, n.pie = a, n["stroke-width"] = 1
          }, e
        }(r),
        u = function(n) {
          function o(t, e) {
            return n.call(this, t, e) || this
          }
          return __extends(o, n), o.prototype._clearState = function() {
            n.prototype._clearState.call(this);
            var t = this;
            t._prevAxesStates && (t._prevAxesStates = null), t._currAxesStates && (t._currAxesStates = null)
          }, o.prototype.playAnimation = function() {
            n.prototype.playAnimation.call(this);
            var e, i, a, o, r, s, u, l, c, p = this,
              h = !0,
              m = p._chart,
              d = t.chart.finance && t.chart.finance.FinancialChart && m instanceof t.chart.finance.FinancialChart,
              f = m.series,
              _ = f.length,
              y = [];
            for (p._previousState = p._currentState, p._previousXVal = p._currentXVal, p._currentState = [], p._addStart = 0, p._removeStart = 0, p._currentXVal = m._xlabels.slice(), p._previousState && p._previousState.length && (h = !1, r = (s = p._previousState).length, u = p._previousXVal, l = p._currentXVal, u.length > 2 && l.length > 2 && ((e = l.indexOf(u[0])) > 0 && e < l.length - 2 ? l[e + 1] === u[1] && l[e + 2] === u[2] && (p._addStart = e) : (e = u.indexOf(l[0])) > 0 && e < u.length - 2 && u[e + 1] === l[1] && u[e + 2] === l[2] && (p._removeStart = e))), e = 0; e < _; e++)
              if (i = f[e], o = null != i._getChartType() ? i._getChartType() : m._getChartType(), a = p._getChartType(o), p._currentState.push({
                seriesType: o,
                ele: i.hostElement
              }), d) p._playDefaultAnimation(y, e);
              else {
                if (c = s && s[e], "Default" === a) {
                  p._playDefaultAnimation(y, e);
                  continue
                }
                if (h || c && c.seriesType !== o || c && c.ele && ("" == c.ele.innerHTML || 0 === c.ele.childNodes.length)) p._playLoadAnimation(y, e, a);
                else if (p._playUpdateAnimation(y, e, a, i, c && c.ele || null), e === _ - 1 && e < r - 1)
                  for (e++; e <= r - 1; e++) p._playUpdateAnimation(y, e, a, null, c.ele)
              }
            p._adjustAnimations(a, y), y.length && p._playAnimation(y), p.axisAnimation && !d && p._playAxesAnimation()
          }, o.prototype._playAxesAnimation = function() {
            var e, n, i, a = this,
              o = a._chart.axes,
              r = o.length;
            for (a._prevAxesStates = a._currAxesStates, a._currAxesStates = [], n = 0; n < r; n++)(e = o[n]).hostElement && a._currAxesStates.push({
              ele: e.hostElement,
              vals: e._vals,
              axis: e,
              maxValue: t.isDate(e.actualMax) ? e.actualMax.getTime() : e.actualMax,
              minValue: t.isDate(e.actualMin) ? e.actualMin.getTime() : e.actualMin
            });
            if (a._prevAxesStates)
              for (i = Math.max(a._prevAxesStates.length, a._currAxesStates.length), n = 0; n < i; n++) a._playAxisAnimation(a._prevAxesStates[n], a._currAxesStates[n])
          }, o.prototype._playAxisAnimation = function(t, e) {
            var n, i = this,
              a = [],
              o = [];
            e && e.maxValue - e.minValue && (n = i._parseAxisState(e), i._convertAxisAnimation(a, n.major, e.axis, t.maxValue, t.minValue), i._convertAxisAnimation(a, n.minor, e.axis, t.maxValue, t.minValue)), t && t.maxValue - t.minValue && (n = i._parseAxisState(t), i._convertAxisAnimation(o, n.major, t.axis), i._convertAxisAnimation(o, n.minor, t.axis)), a && o && i._combineAxisAnimations(a, o), i._playCurrAxisAnimation(a), i._playPrevAxisAnimation(o)
          }, o.prototype._combineAxisAnimations = function(t, e) {
            var n, i, a = this,
              o = e.length;
            for (n = o - 1; n >= 0; n--)(i = e[n]).text && t.some(function(t) {
              if (t.text && t.text === i.text) return a._combineAxisAnimation(t, i), e.splice(n, 1), !0
            })
          }, o.prototype._combineAxisAnimation = function(t, e) {
            var n = this;
            ["label", "majorGrid", "tick"].forEach(function(i) {
              t[i] && e[i] && n._resetExistAxisAttrs(t[i], e[i])
            })
          }, o.prototype._resetExistAxisAttrs = function(t, e) {
            var n = t.ele,
              i = e.ele,
              a = {},
              o = {};
            ["x", "y", "x1", "x2", "y1", "y2"].forEach(function(t) {
              var e = n.getAttribute(t),
                r = i.getAttribute(t);
              e !== r && (a[t] = r, o[t] = e)
            }), t.calcPos = a, t.elePos = o
          }, o.prototype._convertAxisAnimation = function(t, n, i, a, o) {
            var r, s = this,
              u = i.hostElement,
              l = i.axisType == e.AxisType.Y;
            n.forEach(function(e, n) {
              var c = i.convert(e.val, a, o);
              isNaN(c) || (r = {}, e.majorGrid && (r.majorGrid = s._getAxisAnimationAttrs(e.majorGrid, u, c, l)), e.label && (r.label = s._getAxisAnimationAttrs(e.label, u, c, l), r.text = e.label.innerHTML || e.label.textContent), e.tick && (r.tick = s._getAxisAnimationAttrs(e.tick, u, c, l)), t.push(r))
            })
          }, o.prototype._getAxisAnimationAttrs = function(t, e, n, i) {
            var a, o, r;
            return a = {
              ele: t,
              parent: e,
              elePos: {},
              calcPos: {}
            }, "text" === t.nodeName ? (o = i ? "y" : "x", r = Number(t.getAttribute(o)), a.elePos[o] = r, a.calcPos[o] = n) : (o = i ? "y1" : "x1", r = Number(t.getAttribute(o)), i ? (a.elePos = {
              y1: r,
              y2: r
            }, a.calcPos = {
              y1: n,
              y2: n
            }) : (a.elePos = {
              x1: r,
              x2: r
            }, a.calcPos = {
              x1: n,
              x2: n
            })), a.elePos.opacity = 1, a.calcPos.opacity = 0, a
          }, o.prototype._playCurrAxisAnimation = function(t) {
            var e = this.duration;
            t && 0 !== t.length && t.forEach(function(t) {
              ["majorGrid", "label", "tick"].forEach(function(n) {
                var a = t[n];
                if (a) {
                  a.parent;
                  var o = a.ele,
                    r = a.elePos,
                    s = a.calcPos;
                  c.playAnimation(o, s, r, null, i.Swing, e)
                }
              })
            })
          }, o.prototype._playPrevAxisAnimation = function(t) {
            var e = this.duration;
            t && 0 !== t.length && t.forEach(function(t) {
              ["majorGrid", "label", "tick"].forEach(function(n) {
                var a = t[n];
                if (a) {
                  var o = a.parent,
                    r = a.ele,
                    s = a.elePos,
                    u = a.calcPos;
                  o.appendChild(r), c.playAnimation(r, s, u, function() {
                    r.parentNode === o && o.removeChild(r)
                  }, i.Swing, e)
                }
              })
            })
          }, o.prototype._parseAxisState = function(n) {
            if (null == n) return null;
            var i = n.vals,
              a = n.axis,
              o = a.axisType == e.AxisType.Y,
              r = n.ele.childNodes,
              s = 0,
              u = i.major,
              l = i.minor,
              c = i.hasLbls,
              p = [],
              h = [];
            return u && u.forEach(function(n, i) {
              var u, l = {},
                h = !!c[i];
              p.push(l), l.val = n, u = r[s], a.majorGrid && t.hasClass(u, e.FlexChart._CSS_GRIDLINE) && (l.majorGrid = u, u = r[++s]), o ? (h && u && a.majorTickMarks !== e.TickMark.None && t.hasClass(u, e.FlexChart._CSS_TICK) && (l.tick = u, u = r[++s]), h && u && (t.hasClass(u, e.FlexChart._CSS_LABEL) || u.querySelector("." + e.FlexChart._CSS_LABEL)) && (l.label = u, s++)) : (h && u && (t.hasClass(u, e.FlexChart._CSS_LABEL) || u.querySelector("." + e.FlexChart._CSS_LABEL)) && (l.label = u, u = r[++s]), h && u && a.majorTickMarks !== e.TickMark.None && t.hasClass(u, e.FlexChart._CSS_TICK) && (l.tick = u, s++))
            }), l && l.forEach(function(n, i) {
              var o, u = {};
              h.push(u), u.val = n, o = r[s], a.minorTickMarks !== e.TickMark.None && t.hasClass(o, e.FlexChart._CSS_TICK_MINOR) && (u.tick = o, o = r[++s]), a.minorGrid && t.hasClass(o, e.FlexChart._CSS_GRIDLINE_MINOR) && (u.majorGrid = o, s++)
            }), {
              major: p,
              minor: h
            }
          }, o.prototype._playLoadAnimation = function(t, e, n) {
            this["_playLoad" + n + "Animation"](t, e)
          }, o.prototype._playUpdateAnimation = function(t, e, n, i, a) {
            null == i || null == a ? null == i ? this["_play" + n + "RemoveAnimation"](t, a) : this["_play" + n + "AddAnimation"](t, i) : this["_play" + n + "MoveAnimation"](t, i, a)
          }, o.prototype._adjustAnimations = function(t, e) {
            var n, i = e.length;
            if ("Column" === t || "Bar" === t)
              for (n = i - 1; n >= 0; n--) null == e[n] && e.splice(n, 1)
          }, o.prototype._getChartType = function(t) {
            var n = "Default",
              i = this._chart._isRotated();
            switch (t) {
              case e.ChartType.Scatter:
              case e.ChartType.Bubble:
              case e.ChartType.Candlestick:
              case e.ChartType.HighLowOpenClose:
                n = "Scatter";
                break;
              case e.ChartType.Column:
              case e.ChartType.Bar:
                n = i ? "Bar" : "Column";
                break;
              case e.ChartType.Line:
              case e.ChartType.LineSymbols:
              case e.ChartType.Area:
              case e.ChartType.Spline:
              case e.ChartType.SplineSymbols:
              case e.ChartType.SplineArea:
                n = "Line";
                break;
              default:
                n = "Default"
            }
            return n
          }, o.prototype._playLoadLineAnimation = function(t, e) {
            var n, i = this,
              o = i._chart.series[e],
              r = i.animationMode,
              s = o.hostElement;
            r === a.Point ? i._playDefaultAnimation(t, e) : (n = r === a.All ? i._getAnimation(t, 0) : i._getAnimation(t, e), [].slice.call(s.childNodes).forEach(function(t) {
              i._setLineRiseDiveAnimation(n, t, !0)
            }))
          }, o.prototype._setLineRiseDiveAnimation = function(t, e, n) {
            var i, a, o, r, s, u, l, c = this,
              p = this,
              h = p._chart,
              m = e.nodeName,
              d = [],
              f = [],
              _ = p._chart._plotRect,
              y = _.top + _.height,
              A = _.left,
              g = {},
              v = {};
            if ("g" === m && e.childNodes)[].slice.call(e.childNodes).forEach(function(e) {
              c._setLineRiseDiveAnimation(t, e, n)
            });
            else {
              if ("polyline" === m || "polygon" === m) {
                for (o = (u = e.points).length || u.numberOfItems, r = 0; r < o; r++) s = u[r] || u.getItem(r), h.rotated ? d.push({
                  x: A,
                  y: s.y
                }) : d.push({
                  x: s.x,
                  y: y
                }), f.push({
                  x: s.x,
                  y: s.y
                });
                g[m] = d, v[m] = f
              } else "ellipse" === m && (p._toggleVisibility(e, !1), n && (l = function() {
                p._toggleVisibility(e, !0)
              }));
              i = n ? g : v, a = n ? v : g, p._setInitState(e, i, a), t.push({
                ele: e,
                from: i,
                to: a,
                done: l
              })
            }
          }, o.prototype._setLineMoveAnimation = function(t, e, n, i, a) {
            if (null != e && null != n) {
              var o, r, s, u, l, c, p, h, m, d = this,
                f = e.nodeName,
                _ = [],
                y = [],
                A = {},
                g = {};
              for (m = "polygon" === f, s = e.points, c = n.points, o = s.length || s.numberOfItems, u = c.length || c.numberOfItems, h = Math.max(o, u), p = 0; p < h; p++) p < o && (r = s[p] || s.getItem(p), _.push({
                x: r.x,
                y: r.y
              })), p < u && (l = c[p] || c.getItem(p), y.push({
                x: l.x,
                y: l.y
              }));
              d._addStart ? (d._adjustStartLinePoints(d._addStart, _, s), o += d._addStart) : d._removeStart && (d._adjustStartLinePoints(d._removeStart, y, c), u += d._removeStart), u > o ? d._adjustEndLinePoints(u, o, _, s, m) : u < o && d._adjustEndLinePoints(o, u, y, c, m), A[f] = _, g[f] = y, d._setInitState(i, A, g), t.push({
                ele: i,
                from: A,
                to: g,
                done: a
              })
            }
          }, o.prototype._adjustStartLinePoints = function(t, e, n) {
            for (var i = n[0] || n.getItem(0); t;) e.splice(0, 0, {
              x: i.x,
              y: i.y
            }), t--
          }, o.prototype._adjustEndLinePoints = function(t, e, n, i, a) {
            var o, r, s;
            for (a && (i.length >= 3 || i.numberOfItems >= 3) ? (r = n.pop(), o = n.pop(), s = i[i.length - 3] || i.getItem(i.numberOfItems - 3)) : (i.length > 0 || i.numberOfItems > 0) && (s = i[i.length - 1] || i.getItem(i.numberOfItems - 1)); t > e && s;) n.push({
              x: s.x,
              y: s.y
            }), e++;
            a && r && o && (n.push(o), n.push(r))
          }, o.prototype._playLineRemoveAnimation = function(t, e) {
            var n, i = this,
              a = i._chart.series[0].hostElement.parentNode,
              o = i._getAnimation(t, 0);
            a.appendChild(e), [].slice.call(e.childNodes).forEach(function(t) {
              i._setLineRiseDiveAnimation(o, t, !1)
            }), o.length && (n = o[0].done, o[0].done = function() {
              e && e.parentNode === a && a.removeChild(e), n && n()
            })
          }, o.prototype._playLineAddAnimation = function(t, e) {
            var n = this,
              i = e.hostElement,
              a = this._getAnimation(t, 0);
            [].slice.call(i.childNodes).forEach(function(t) {
              n._setLineRiseDiveAnimation(a, t, !0)
            })
          }, o.prototype._playLineMoveAnimation = function(t, e, n) {
            var i, a, o, r, s = this,
              u = (s._chart, s._getAnimation(t, 0)),
              l = [];
            i = e.hostElement, a = [].slice.call(n.childNodes), [].slice.call(i.childNodes).forEach(function(t, e) {
              r = t.nodeName, o = a[e], "g" === r && t.nodeChilds ? [].slice.call(t.nodeChilds).forEach(function(t, e) {
                o && (l.push(t), s._toggleVisibility(t, !1))
              }) : "polygon" === r || "polyline" === r ? s._setLineMoveAnimation(u, o, t, t, 0 === e ? function() {
                l.forEach(function(t) {
                  s._toggleVisibility(t, !0)
                }), l = null
              } : null) : o && (l.push(t), s._toggleVisibility(t, !1))
            })
          }, o.prototype._playLoadColumnAnimation = function(t, e) {
            this._playLoadBarAnimation(t, e, !0)
          }, o.prototype._playLoadBarAnimation = function(t, e, n) {
            void 0 === n && (n = !1);
            var i = this,
              o = i._chart.series[e],
              r = i.animationMode,
              s = o.hostElement;
            [].slice.call(s.childNodes).forEach(function(o, s) {
              var u, l = o.nodeName;
              u = r === a.Point ? i._getAnimation(t, s) : r === a.Series ? i._getAnimation(t, e) : i._getAnimation(t, 0), "g" === l ? o.childNodes && [].slice.call(o.childNodes).forEach(function(t, e) {
                i._setLoadBarAnimation(u, t, n)
              }) : i._setLoadBarAnimation(u, o, n)
            })
          }, o.prototype._setBarAnimation = function(t, e, n, i, a) {
            this._setInitState(e, n, i), t.push({
              ele: e,
              from: n,
              to: i,
              done: a
            })
          }, o.prototype._setLoadBarAnimation = function(t, e, n, i, a) {
            void 0 === i && (i = !1);
            var o, r, s = this,
              u = n ? "height" : "width",
              l = n ? "y" : "x",
              c = e.getAttribute(u),
              p = e.getAttribute(l),
              h = n ? "top" : "left",
              m = s._chart._plotRect,
              d = {},
              f = {};
            d[u] = 0, f[u] = Number(c), n && (d[l] = m[u] + m[h], f[l] = Number(p)), o = i ? f : d, r = i ? d : f, "g" === e.nodeName ? e.childNodes && [].slice.call(e.childNodes).forEach(function(e) {
              s._setBarAnimation(t, e, o, r, a)
            }) : s._setBarAnimation(t, e, o, r, a)
          }, o.prototype._setMoveBarAnimation = function(t, e, n) {
            var i = {},
              a = {};
            null != e && null != n && (["width", "height", "x", "y", "top", "left"].forEach(function(t) {
              var o = e.getAttribute(t),
                r = n.getAttribute(t);
              o !== r && (i[t] = Number(o), a[t] = Number(r))
            }), this._setInitState(n, i, a), t.push({
              ele: n,
              from: i,
              to: a
            }))
          }, o.prototype._playColumnRemoveAnimation = function(t, e) {
            this._playBarRemoveAnimation(t, e, !0)
          }, o.prototype._playColumnAddAnimation = function(t, e) {
            this._playBarAddAnimation(t, e, !0)
          }, o.prototype._playColumnMoveAnimation = function(t, e, n) {
            this._playBarMoveAnimation(t, e, n, !0)
          }, o.prototype._playBarRemoveAnimation = function(t, e, n) {
            void 0 === n && (n = !1);
            var i = this,
              a = i._chart.series[0].hostElement.parentNode,
              o = i._getAnimation(t, 0);
            a.appendChild(e), [].slice.call(e.childNodes).forEach(function(t) {
              i._setLoadBarAnimation(o, t, n, !0)
            }), o.length && (o[0].done = function() {
              e && e.parentNode === a && a.removeChild(e)
            })
          }, o.prototype._playBarAddAnimation = function(t, e, n) {
            var i = this;
            void 0 === n && (n = !1);
            var a = e.hostElement,
              o = this._getAnimation(t, 2);
            [].slice.call(a.childNodes).forEach(function(t) {
              i._setLoadBarAnimation(o, t, n, !1)
            })
          }, o.prototype._playBarMoveAnimation = function(t, e, n, i) {
            void 0 === i && (i = !1);
            var a, o, r, s, u, l, c, p = this;
            p._chart;
            if (a = e.hostElement, r = [].slice.call(n.childNodes), p._addStart)
              for (c = 0, s = r[0]; c < p._addStart;) r.splice(0, 0, s), c++;
            if (p._removeStart)
              for (c = 0, s = r[r.length - 1]; c < p._removeStart;) {
                var h = r.shift();
                r.push(h), c++
              }
            u = r.length, o = [].slice.call(a.childNodes), l = o.length, o.forEach(function(e, n) {
              var a;
              if (n < u) {
                if (s = r[n], n < p._addStart ? (a = p._getAnimation(t, 2), p._setLoadBarAnimation(a, e, i, !1)) : n >= u - p._removeStart ? (a = p._getAnimation(t, 2), p._setLoadBarAnimation(a, e, i, !1), a = p._getAnimation(t, 0), p._removeBarAnimation(a, e, s, i)) : (a = p._getAnimation(t, 1), p._setMoveBarAnimation(a, s, e)), n === l - 1 && n < u - 1)
                  for (a = p._getAnimation(t, 0), n++; n < u; n++) s = r[n], p._removeBarAnimation(a, e, s, i)
              } else a = p._getAnimation(t, 2), p._setLoadBarAnimation(a, e, i, !1)
            })
          }, o.prototype._removeBarAnimation = function(t, e, n, i) {
            var a = e.parentNode;
            a.appendChild(n), this._setLoadBarAnimation(t, n, i, !0, function(t) {
              return function() {
                t.parentNode && t.parentNode === a && a.removeChild(t)
              }
            }(n))
          }, o.prototype._playLoadScatterAnimation = function(t, e) {
            var n = this,
              i = n._chart,
              o = i.series[e],
              r = n.animationMode,
              s = o.hostElement,
              u = o._xValues || i._xvals;
            0 === u.length && (u = o._pointIndexes), [].slice.call(s.childNodes).forEach(function(i, o) {
              var s;
              s = r === a.Point ? n._getScatterAnimation(t, u[o]) : r === a.Series ? n._getAnimation(t, e) : n._getAnimation(t, 0), n._setLoadScatterAnimation(s, i, !1)
            })
          }, o.prototype._setLoadScatterAnimation = function(t, e, n, i) {
            var a = this;
            void 0 === n && (n = !1);
            var o, r, s = {},
              u = {};
            "g" === e.nodeName && e.childNodes ? [].slice.call(e.childNodes).forEach(function(e) {
              a._setLoadScatterAnimation(t, e, n, i)
            }) : (["rx", "ry", "stroke-width"].forEach(function(t) {
              var n = e.getAttribute(t);
              s[t] = 0, u[t] = Number(n)
            }), o = n ? u : s, r = n ? s : u, this._setInitState(e, o, r), t.push({
              ele: e,
              from: o,
              to: r,
              done: i
            }))
          }, o.prototype._setUpdateScatterAnimation = function(t, e, n, i) {
            var a = {},
              o = {};
            ["cx", "cy"].forEach(function(t) {
              var i = e.getAttribute(t),
                r = n.getAttribute(t);
              i !== r && (a[t] = Number(i), o[t] = Number(r))
            }), this._setInitState(n, a, o), t.push({
              ele: n,
              from: a,
              to: o,
              done: i
            })
          }, o.prototype._getScatterAnimation = function(t, e) {
            var n = this._getScatterAnimationIndex(t, e);
            return t[n] || (t[n] = []), t[n]
          }, o.prototype._getScatterAnimationIndex = function(t, e) {
            var n = this._chart.axisX,
              i = null == n.min ? n.actualMin : n.min,
              a = null == n.max ? n.actualMax : n.max;
            return Math.ceil((e - i) / ((a - i) / 20))
          }, o.prototype._playScatterRemoveAnimation = function(t, e) {
            var n = this,
              i = n._chart.series[0].hostElement.parentNode,
              a = n._getAnimation(t, 0);
            i.appendChild(e), [].slice.call(e.childNodes).forEach(function(t) {
              n._setLoadScatterAnimation(a, t, !0)
            }), a.length && (a[0].done = function() {
              e && e.parentNode === i && i.removeChild(e)
            })
          }, o.prototype._playScatterAddAnimation = function(t, e) {
            var n = this,
              i = e.hostElement,
              a = this._getAnimation(t, 0);
            [].slice.call(i.childNodes).forEach(function(t) {
              n._setLoadScatterAnimation(a, t, !1)
            })
          }, o.prototype._playScatterMoveAnimation = function(t, e, n) {
            var i, a, o, r, s, u, l, c = this,
              p = (c._chart, c._getAnimation(t, 0));
            if (i = e.hostElement, o = [].slice.call(n.childNodes), c._addStart)
              for (l = 0, r = o[0]; l < c._addStart;) o.splice(0, 0, r), l++;
            if (c._removeStart)
              for (l = 0, r = o[o.length - 1]; l < c._removeStart;) {
                var h = o.shift();
                o.push(h), l++
              }
            s = o.length, a = [].slice.call(i.childNodes), u = a.length, a.forEach(function(t, e) {
              if (e < s) {
                if (e < c._addStart ? c._setLoadScatterAnimation(p, t, !1) : e >= s - c._removeStart ? (c._setLoadScatterAnimation(p, t, !1), r = o[e], c._removeScatterAnimation(p, t, r)) : (r = o[e], c._setUpdateScatterAnimation(p, r, t)), e === u - 1 && e < s - 1)
                  for (e++; e < s; e++) r = o[e], c._removeScatterAnimation(p, t, r)
              } else c._setLoadScatterAnimation(p, t, !1)
            })
          }, o.prototype._removeScatterAnimation = function(t, e, n) {
            var i = e.parentNode;
            i.appendChild(n), this._setLoadScatterAnimation(t, n, !0, function(t) {
              return function() {
                t.parentNode && t.parentNode === i && i.removeChild(t)
              }
            }(n))
          }, o.prototype._playDefaultAnimation = function(e, n) {
            var i, a = this._chart,
              o = a.series[n].hostElement,
              r = a._plotRect,
              s = a._currentRenderEngine,
              u = o.getAttribute("clip-path"),
              l = "clipPath" + (1e6 * Math.random()).toFixed();
            s.addClipRect(new t.Rect(r.left, r.top, 0, r.height), l), o.setAttribute("clip-path", "url(#" + l + ")"), i = a.hostElement.querySelector("#" + l), this._getAnimation(e, 0).push({
              ele: i.querySelector("rect"),
              from: {
                width: 0
              },
              to: {
                width: r.width
              },
              done: function() {
                o && (u ? o.setAttribute("clip-path", u) : o.removeAttribute("clip-path"), i && i.parentNode && i.parentNode.removeChild(i))
              }
            })
          }, o
        }(r),
        l = function(t) {
          function e(e, n) {
            return t.call(this, e, n) || this
          }
          return __extends(e, t), e.prototype._getDurationAndDelay = function(e, n) {
            var i = t.prototype._getDurationAndDelay.call(this, e, n);
            return this.animationMode === a.Point && (i.duration = n / e, i.delay = n / e), i
          }, e.prototype._playAxesAnimation = function() {}, e.prototype._getChartType = function(e) {
            var n = t.prototype._getChartType.call(this, e);
            return "Bar" === n && (n = "Column"), n
          }, e.prototype._playLoadLineAnimation = function(t, e) {
            var n, i, o, r = this,
              s = r._chart,
              u = r._chart.series[e],
              l = u._xValues || s._xvals,
              c = r.animationMode,
              p = u.hostElement;
            c === a.Point ? (0 === l.length && (l = u._pointIndexes), o = [].slice.call(p.childNodes), i = o.length - p.querySelectorAll("ellipse").length, o.forEach(function(e, n) {
              r._setRadarLinePointAnimation(t, e, n, l, i)
            })) : (n = c === a.All ? r._getAnimation(t, 0) : r._getAnimation(t, e), [].slice.call(p.childNodes).forEach(function(t) {
              r._setLineRiseDiveAnimation(n, t, !0)
            }))
          }, e.prototype._setRadarLinePointAnimation = function(t, e, n, i, a) {
            var o, r, s, u, l, c, p, h = this,
              m = h._chart,
              d = e.nodeName,
              f = [],
              _ = [],
              y = [],
              A = [],
              g = m._center,
              v = [],
              x = !1,
              E = {},
              S = {},
              b = 0;
            if ("polyline" === d || "polygon" === d) {
              for (o = (u = e.points).length || u.numberOfItems, r = 0; r < o; r++) v[p = h._getScatterAnimationIndex(t, i[r])] || (v[p] = []), v[p].push(r), s = u[r] || u.getItem(r), f.push({
                x: g.x,
                y: g.y
              }), _.push({
                x: s.x,
                y: s.y
              });
              for (r = 0, o = v.length; r < o; r++) v[r] && (c = h._getAnimation(t, b), y = A.length ? A.slice() : f.slice(), A = y.slice(), v[r].forEach(function(t) {
                var e = _[t];
                A[t] = {
                  x: e.x,
                  y: e.y
                }
              }), S = {}, (E = {})[d] = y, S[d] = A, x || (h._setInitState(e, E, S), x = !0), c.push({
                ele: e,
                from: E,
                to: S,
                done: l
              }), b++)
            } else if ("ellipse" === d) {
              if ((r = n - (a || 0)) < 0) return;
              c = m._isPolar ? h._getScatterAnimation(t, i[r]) : h._getScatterAnimation(t, r), h._toggleVisibility(e, !1), l = function() {
                h._toggleVisibility(e, !0)
              }, c.push({
                ele: e,
                from: E,
                to: S,
                done: l
              })
            }
          }, e.prototype._setLineRiseDiveAnimation = function(t, e, n) {
            var i, a, o, r, s, u, l, c = this,
              p = c._chart,
              h = e.nodeName,
              m = [],
              d = [],
              f = p._center,
              _ = {},
              y = {};
            if ("polyline" === h || "polygon" === h) {
              for (o = (u = e.points).length || u.numberOfItems, r = 0; r < o; r++) s = u[r] || u.getItem(r), m.push({
                x: f.x,
                y: f.y
              }), d.push({
                x: s.x,
                y: s.y
              });
              _[h] = m, y[h] = d
            } else "ellipse" === h && (c._toggleVisibility(e, !1), n && (l = function() {
              c._toggleVisibility(e, !0)
            }));
            i = n ? _ : y, a = n ? y : _, c._setInitState(e, i, a), t.push({
              ele: e,
              from: i,
              to: a,
              done: l
            })
          }, e.prototype._parsePathByRadius = function(t, e, n) {
            var i, a, o = t.center.x,
              r = t.center.y,
              s = t.radius,
              u = t.angle,
              l = t.sweep;
            i = [o, r, 0, u, l, 0], a = [o, r, s, u, l, t.innerRadius || 0], e.pie = i, n.pie = a
          }, e.prototype._playUpdateAnimation = function(e, n, i, a, o) {
            if ("Bar" === i || "Column" === i) {
              if (null == a) return;
              this._playLoadBarAnimation(e, n, !1)
            } else t.prototype._playUpdateAnimation.call(this, e, n, i, a, o)
          }, e.prototype._playLoadBarAnimation = function(t, e, n) {
            void 0 === n && (n = !1);
            var i = this,
              o = i._chart,
              r = o.series[e],
              s = o._areas[e],
              u = i.animationMode,
              l = r.hostElement;
            [].slice.call(l.childNodes).forEach(function(n, o) {
              var r, l, c = {},
                p = {};
              r = u === a.Point ? i._getAnimation(t, o) : u === a.Series ? i._getAnimation(t, e) : i._getAnimation(t, 0), l = s[o], i._parsePathByRadius(l, c, p), i._setInitState(n, c, p), r.push({
                ele: n,
                from: c,
                to: p
              })
            })
          }, e
        }(u),
        c = function() {
          function e() {}
          return e.playAnimations = function(t, n, a, o, r, s, u) {
            void 0 === r && (r = i.Swing);
            var l = t.length,
              c = 0,
              p = [];
            return t.forEach(function(t, i) {
              var h = e.playAnimation(t, n[i], a[i], function() {
                c === l - 1 && o && o(), c++
              }, r, s, u);
              p.push(h)
            }), p
          }, e.playAnimation = function(t, n, a, o, r, s, u) {
            void 0 === r && (r = i.Swing);
            var l = e.parseAttrs(n, a);
            return e.animate(function(n) {
              e.setElementAttr(t, l, n)
            }, o, r, s, u)
          }, e.setElementAttr = function(t, n, i) {
            var a, o;
            for (o in n) a = n[o], e.calcValue(a, i), t.setAttribute(o, a.getValue(a.value, i))
          }, e.getPathDescOfPie = function(e, n, i, a, o, r) {
            void 0 === r && (r = 0);
            var s = !1;
            o >= 2 * Math.PI && (s = !0, o = 2 * Math.PI - .001);
            var u = new t.Point(e, n);
            u.x += i * Math.cos(a), u.y += i * Math.sin(a);
            var l = a + o,
              c = new t.Point(e, n);
            if (c.x += i * Math.cos(l), c.y += i * Math.sin(l), r) {
              var p = new t.Point(e, n);
              p.x += r * Math.cos(l), p.y += r * Math.sin(l);
              var h = new t.Point(e, n);
              h.x += r * Math.cos(a), h.y += r * Math.sin(a)
            }
            var m = " 0 0,1 ",
              d = " 0 0,0 ";
            Math.abs(o) > Math.PI && (m = " 0 1,1 ", d = " 0 1,0 ");
            var f = "M " + u.x.toFixed(3) + "," + u.y.toFixed(3);
            return f += " A " + i.toFixed(3) + "," + i.toFixed(3) + m, f += c.x.toFixed(3) + "," + c.y.toFixed(3), r ? (f += s ? " M " + p.x.toFixed(3) + "," + p.y.toFixed(3) : " L " + p.x.toFixed(3) + "," + p.y.toFixed(3), f += " A " + r.toFixed(3) + "," + r.toFixed(3) + d, f += h.x.toFixed(3) + "," + h.y.toFixed(3)) : f += " L " + e.toFixed(3) + "," + n.toFixed(3), s || (f += " z"), f
          }, e.parseAttrs = function(t, n) {
            var i = {};
            for (var a in t)
              if (null != n[a]) switch (a) {
                case "polyline":
                  i.points = e.parseAttr(t[a], n[a], function(t, e) {
                    if (1 === e) {
                      for (var n, i, a; t.length > 1;) {
                        if (i = t[0], a = t[1], i.x !== a.x || i.y !== a.y) {
                          i = null, a = null;
                          break
                        }
                        t.splice(1, 1)
                      }
                      for (n = t.length - 1; n > 0; n--)
                        if (i = a, a = t[n], i) {
                          if (i.x !== a.x || i.y !== a.y) break;
                          t.pop()
                        }
                    }
                    return t.map(function(t) {
                      return t.x + "," + t.y
                    }).join(" ")
                  });
                  break;
                case "polygon":
                  i.points = e.parseAttr(t[a], n[a], function(t, e) {
                    if (1 === e) {
                      var n, i, a, o, r;
                      for (o = t.pop(), r = t.pop(); t.length > 1;) {
                        if (i = t[0], a = t[1], i.x !== a.x || i.y !== a.y) {
                          i = null, a = null;
                          break
                        }
                        t.splice(1, 1)
                      }
                      for (n = t.length - 1; n >= 0; n--)
                        if (i = a, a = t[n], i) {
                          if (i.x !== a.x || i.y !== a.y) break;
                          t.splice(n, 1)
                        }
                      t.push(r), t.push(o)
                    }
                    return t.map(function(t) {
                      return t.x + "," + t.y
                    }).join(" ")
                  });
                  break;
                case "d":
                  i[a] = e.parseAttr(t[a], n[a], function(t) {
                    return t.map(function(t) {
                      return "string" == typeof t ? t : t[0] + "," + t[1]
                    }).join(" ")
                  });
                  break;
                case "pie":
                  i.d = e.parseAttr(t[a], n[a], function(t) {
                    return e.getPathDescOfPie.apply(e, t)
                  });
                  break;
                case "rotate":
                  i.transform = e.parseAttr(t[a], n[a], function(t) {
                    return "rotate(" + t.join(" ") + ")"
                  });
                  break;
                case "width":
                case "height":
                case "rx":
                case "ry":
                case "stroke-width":
                  i[a] = e.parseAttr(t[a], n[a], function(t) {
                    return Math.abs(t)
                  });
                  break;
                default:
                  i[a] = e.parseAttr(t[a], n[a])
              }
            return i
          }, e.animate = function(e, n, a, o, r) {
            void 0 === a && (a = i.Swing), void 0 === o && (o = 400), void 0 === r && (r = 16), t.asFunction(e), t.asNumber(o, !1, !0), t.asNumber(r, !1, !0);
            var s = 0,
              u = setInterval(function() {
                Date.now();
                var t = s / o;
                t = p[i[a]](t), e(t), (s += r) >= o && (clearInterval(u), (t < 1 || t > 1) && e(1), n && n())
              }, r);
            return u
          }, e.calcValue = function(n, i) {
            var a = n.from,
              o = n.diff,
              r = n.value;
            t.isNumber(a) ? n.value = 0 === o ? a : a + o * i : t.isArray(a) && e.parseArrayAttr(r, a, o, function(t, e) {
              return "number" == typeof t ? t + e * i : t
            })
          }, e.parseAttr = function(n, i, a) {
            var o, r, s, u;
            return t.isArray(n) && t.isArray(i) ? (r = i, s = [], u = (o = n).slice(), e.parseArrayAttr(s, o, r, function(t, e) {
              return t === e ? 0 : e - t
            })) : (u = o = Number(n), s = (r = Number(i)) - o), {
              from: o,
              to: r,
              value: u,
              diff: s,
              getValue: a || function(t, e) {
                return t
              }
            }
          }, e.parseArrayAttr = function(e, n, i, a) {
            n.forEach(function(n, o) {
              var r = {},
                s = [],
                u = i[o];
              t.isNumber(n) || "string" == typeof n ? e[o] = a(n, u) : t.isArray(n) ? (n.forEach(function(t, e) {
                s[e] = a(n[e], u[e])
              }), e[o] = s) : (Object.getOwnPropertyNames(n).forEach(function(t) {
                r[t] = a(n[t], u[t])
              }), e[o] = r)
            })
          }, e
        }(),
        p = function() {
          function t() {}
          return t.Linear = function(t) {
            return t
          }, t.Swing = function(t) {
            var e = 1.70158;
            return (t /= .5) < 1 ? t * t * ((1 + (e *= 1.525)) * t - e) * .5 : .5 * ((t -= 2) * t * ((1 + (e *= 1.525)) * t + e) + 2)
          }, t.EaseInQuad = function(t) {
            return t * t
          }, t.EaseOutQuad = function(t) {
            return t * (2 - t)
          }, t.EaseInOutQuad = function(t) {
            return t < .5 ? 2 * t * t : (4 - 2 * t) * t - 1
          }, t.EaseInCubic = function(t) {
            return t * t * t
          }, t.EaseOutCubic = function(t) {
            return --t * t * t + 1
          }, t.EaseInOutCubic = function(t) {
            return t < .5 ? 4 * t * t * t : (t - 1) * (2 * t - 2) * (2 * t - 2) + 1
          }, t.EaseInQuart = function(t) {
            return t * t * t * t
          }, t.EaseOutQuart = function(t) {
            return 1 - --t * t * t * t
          }, t.EaseInOutQuart = function(t) {
            return t < .5 ? 8 * t * t * t * t : 1 - 8 * --t * t * t * t
          }, t.EaseInQuint = function(t) {
            return t * t * t * t * t
          }, t.EaseOutQuint = function(t) {
            return 1 + --t * t * t * t * t
          }, t.EaseInOutQuint = function(t) {
            return t < .5 ? 16 * t * t * t * t * t : 1 + 16 * --t * t * t * t * t
          }, t.EaseInSine = function(t) {
            return 1 - Math.cos(t * (Math.PI / 2))
          }, t.EaseOutSine = function(t) {
            return Math.sin(t * (Math.PI / 2))
          }, t.EaseInOutSine = function(t) {
            return -.5 * (Math.cos(Math.PI * t) - 1)
          }, t.EaseInExpo = function(t) {
            return 0 == t ? 0 : Math.pow(2, 10 * (t - 1))
          }, t.EaseOutExpo = function(t) {
            return 1 == t ? 1 : 1 - Math.pow(2, -10 * t)
          }, t.EaseInOutExpo = function(t) {
            return t == !!t ? t : (t /= .5) < 1 ? .5 * Math.pow(2, 10 * (t - 1)) : .5 * (2 - Math.pow(2, -10 * --t))
          }, t.EaseInCirc = function(t) {
            return -(Math.sqrt(1 - t * t) - 1)
          }, t.EaseOutCirc = function(t) {
            return Math.sqrt(1 - Math.pow(t - 1, 2))
          }, t.EaseInOutCirc = function(t) {
            return (t /= .5) < 1 ? -.5 * (Math.sqrt(1 - t * t) - 1) : .5 * (Math.sqrt(1 - (t -= 2) * t) + 1)
          }, t.EaseInBack = function(t) {
            var e = 1.70158;
            return t * t * ((e + 1) * t - e)
          }, t.EaseOutBack = function(t) {
            var e = 1.70158;
            return (t -= 1) * t * ((e + 1) * t + e) + 1
          }, t.EaseInOutBack = function(t) {
            var e = 1.70158;
            return (t /= .5) < 1 ? t * t * ((1 + (e *= 1.525)) * t - e) * .5 : .5 * ((t -= 2) * t * ((1 + (e *= 1.525)) * t + e) + 2)
          }, t.EaseInBounce = function(e) {
            return 1 - t.EaseOutBounce(1 - e)
          }, t.EaseOutBounce = function(t) {
            var e = 7.5625;
            return t < 1 / 2.75 ? e * t * t : t < 2 / 2.75 ? e * (t -= 1.5 / 2.75) * t + .75 : t < 2.5 / 2.75 ? e * (t -= 2.25 / 2.75) * t + .9375 : e * (t -= 2.625 / 2.75) * t + .984375
          }, t.EaseInOutBounce = function(e) {
            return e < .5 ? .5 * t.EaseInBounce(2 * e) : .5 * t.EaseOutBounce(2 * e - 1) + .5
          }, t.EaseInElastic = function(t) {
            return t == !!t ? t : -Math.pow(2, 10 * (t -= 1)) * Math.sin((t - .075) * (2 * Math.PI) / .3)
          }, t.EaseOutElastic = function(t) {
            return t == !!t ? t : Math.pow(2, -10 * t) * Math.sin((t - .075) * (2 * Math.PI) / .3) + 1
          }, t.EaseInOutElastic = function(t) {
            return t == !!t ? t : (t *= 2) < 1 ? Math.pow(2, 10 * (t -= 1)) * Math.sin((t - .1125) * (2 * Math.PI) / .45) * -.5 : Math.pow(2, -10 * (t -= 1)) * Math.sin((t - .1125) * (2 * Math.PI) / .45) * .5 + 1
          }, t
        }()
    }(e.animation || (e.animation = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
