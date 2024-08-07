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
  ! function(i) {
    ! function(i) {
      "use strict";
      var e = function() {
        function i(i, e, s, n) {
          this._isVisible = !0, this._buttonsVisible = !0, this._minScale = 0, this._maxScale = 1, this._seamless = !1, this._rsContainer = null, this._rsEle = null, this._decBtn = null, this._incBtn = null, this._rsContent = null, this._minHandler = null, this._rangeHandler = null, this._maxHandler = null, this._wrapperSliderMousedown = null, this._wrapperDocMouseMove = null, this._wrapperDocMouseup = null, this._wrapperBtnMousedown = null, this._wrapperRangeSpaceMousedown = null, this._wrapperRangeMouseleave = null, this._isTouch = !1, this._slidingInterval = null, this._rangeSliderRect = null, this._isHorizontal = !0, this._isBtnMousedown = !1, this._needSpaceClick = !1, this._hasButtons = !0, this._movingEle = null, this._movingOffset = null, this._range = null, this._startPt = null, this._minPos = 0, this._maxPos = 1, this.rangeChanged = new t.Event, this.rangeChanging = new t.Event, i || t.assert(!1, "The container cannot be null."), this._isTouch = "ontouchstart" in window, this._needSpaceClick = e, this._hasButtons = s, t.copy(this, n), this._createSlider(i)
        }
        return Object.defineProperty(i.prototype, "buttonsVisible", {
          get: function() {
            return this._buttonsVisible
          },
          set: function(i) {
            if (i != this._buttonsVisible) {
              if (this._buttonsVisible = t.asBoolean(i), !this._rsContainer || !this._hasButtons) return;
              this._refresh()
            }
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "isHorizontal", {
          get: function() {
            return this._isHorizontal
          },
          set: function(i) {
            if (i != this._isHorizontal) {
              if (this._isHorizontal = t.asBoolean(i), !this._rsContainer) return;
              this._invalidate()
            }
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "isVisible", {
          get: function() {
            return this._isVisible
          },
          set: function(i) {
            if (i != this._isVisible) {
              if (this._isVisible = t.asBoolean(i), !this._rsContainer) return;
              this._rsContainer.style.visibility = this._isVisible ? "visible" : "hidden"
            }
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "minScale", {
          get: function() {
            return this._minScale
          },
          set: function(i) {
            i >= 0 && i != this._minScale && (this._minScale = t.asNumber(i))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "maxScale", {
          get: function() {
            return this._maxScale
          },
          set: function(i) {
            i >= 0 && i != this._maxScale && (this._maxScale = t.asNumber(i))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "seamless", {
          get: function() {
            return this._seamless
          },
          set: function(i) {
            i != this._seamless && (this._seamless = t.asBoolean(i))
          },
          enumerable: !0,
          configurable: !0
        }), i.prototype.onRangeChanged = function(t) {
          this.rangeChanged.raise(this, t)
        }, i.prototype.onRangeChanging = function(t) {
          this.rangeChanging.raise(this, t)
        }, Object.defineProperty(i.prototype, "_isSliding", {
          get: function() {
            return null !== this._startPt
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "_handleWidth", {
          get: function() {
            return this._minHandler.offsetWidth
          },
          enumerable: !0,
          configurable: !0
        }), i.prototype._createSlider = function(e) {
          var s = this._isHorizontal ? i._HRANGESLIDER : i._VRANGESLIDER,
            n = this._isHorizontal ? "wj-glyph-left" : "wj-glyph-down",
            a = this._isHorizontal ? "wj-glyph-right" : "wj-glyph-up";
          this._rsContainer = e, this._rsContainer.style.visibility = this._isVisible ? "visible" : "hidden", this._rsEle = t.createElement('<div class="wj-chart-rangeslider ' + s + '"></div>'), this._rsContainer.appendChild(this._rsEle), this._hasButtons && (this._decBtn = t.createElement('<button class="wj-rangeslider-decbtn wj-btn wj-btn-default" type="button" tabindex="-1"><span class="' + n + " " + i._RANGESLIDER_DECBTN + '"></span></button>'), this._rsEle.appendChild(this._decBtn), this._incBtn = t.createElement('<button class="wj-rangeslider-incbtn wj-btn wj-btn-default" type="button" tabindex="-1"><span class="' + a + " " + i._RANGESLIDER_INCBTN + '"></span></button>'), this._rsEle.appendChild(this._incBtn)), this._rsContent = t.createElement('<div class="wj-rangeslider-content"><div class="wj-rangeslider-rangehandle"></div><div class="wj-rangeslider-minhandle"></div><div class="wj-rangeslider-maxhandle"></div>'), this._rsEle.appendChild(this._rsContent), this._minHandler = this._rsContent.querySelector("." + i._RANGESLIDER_MINHANDLE), this._rangeHandler = this._rsContent.querySelector("." + i._RANGESLIDER_RANGEHANDLE), this._maxHandler = this._rsContent.querySelector("." + i._RANGESLIDER_MAXHANDLE), this._wrapperSliderMousedown = this._onSliderMousedown.bind(this), this._wrapperDocMouseMove = this._onDocMouseMove.bind(this), this._wrapperDocMouseup = this._onDocMouseup.bind(this), this._wrapperRangeSpaceMousedown = this._onRangeSpaceMousedown.bind(this), this._wrapperRangeMouseleave = this._onRangeMouseleave.bind(this), this._wrapperBtnMousedown = this._onBtnMousedown.bind(this), this._switchEvent(!0)
        }, i.prototype._switchEvent = function(t) {
          var i = t ? "addEventListener" : "removeEventListener";
          this._rsContainer && (this._needSpaceClick && this._rsEle[i]("mousedown", this._wrapperRangeSpaceMousedown), this._rsEle[i]("mouseleave", this._wrapperRangeMouseleave), this._rsContent[i]("mousedown", this._wrapperSliderMousedown), this._hasButtons && (this._decBtn[i]("mousedown", this._wrapperBtnMousedown), this._incBtn[i]("mousedown", this._wrapperBtnMousedown)), document[i]("mousemove", this._wrapperDocMouseMove), document[i]("mouseup", this._wrapperDocMouseup), "ontouchstart" in window && (this._needSpaceClick && this._rsEle[i]("touchstart", this._wrapperRangeSpaceMousedown), this._rsContent[i]("touchstart", this._wrapperSliderMousedown), this._hasButtons && (this._decBtn[i]("touchstart", this._wrapperBtnMousedown), this._incBtn[i]("touchstart", this._wrapperBtnMousedown)), document[i]("touchmove", this._wrapperDocMouseMove), document[i]("touchend", this._wrapperDocMouseup)))
        }, i.prototype._onSliderMousedown = function(e) {
          this._isVisible && (this._movingEle = e.srcElement || e.target, this._startPt = e instanceof MouseEvent ? new t.Point(e.pageX, e.pageY) : new t.Point(e.changedTouches[0].pageX, e.changedTouches[0].pageY), t.removeClass(this._minHandler, i._RANGESLIDER_HANDLE_ACTIVE), t.removeClass(this._maxHandler, i._RANGESLIDER_HANDLE_ACTIVE), this._movingOffset = t.getElementRect(this._movingEle), this._movingEle != this._rangeHandler ? (this._isHorizontal ? this._movingOffset.left += .5 * this._movingEle.offsetWidth : this._movingOffset.top += .5 * this._movingEle.offsetHeight, t.addClass(this._movingEle, i._RANGESLIDER_HANDLE_ACTIVE)) : this._range = this._maxPos - this._minPos, e.preventDefault())
        }, i.prototype._onDocMouseMove = function(i) {
          if (this._isVisible && this._startPt) {
            var e = i instanceof MouseEvent ? new t.Point(i.pageX, i.pageY) : new t.Point(i.changedTouches[0].pageX, i.changedTouches[0].pageY);
            this._onMove(e)
          }
        }, i.prototype._onMove = function(e) {
          var s, n = this,
            a = this._startPt,
            h = this._movingOffset,
            o = this._plotBox,
            r = this._range,
            _ = this._movingEle,
            l = this._minHandler,
            c = this._rangeHandler,
            p = this._maxHandler;
          a && h && ((s = this._isHorizontal ? (h.left + e.x - a.x - o.x) / o.width : 1 - (h.top + e.y - a.y - o.y) / o.height) < 0 ? s = 0 : s > 1 && (s = 1), _ === l ? this._seamless && 0 === this._minScale && s >= this._maxPos ? (n._minPos = n._maxPos, n._movingEle = p, t.removeClass(this._minHandler, i._RANGESLIDER_HANDLE_ACTIVE), t.addClass(this._maxHandler, i._RANGESLIDER_HANDLE_ACTIVE)) : (s > this._maxPos - this._minScale && (s = this._maxPos - this._minScale), s < this._maxPos - this._maxScale && (s = this._maxPos - this._maxScale), this._minPos = s) : _ === p ? this._seamless && 0 === this._minScale && s <= this._minPos ? (n._maxPos = n._minPos, n._movingEle = l, t.removeClass(this._maxHandler, i._RANGESLIDER_HANDLE_ACTIVE), t.addClass(this._minHandler, i._RANGESLIDER_HANDLE_ACTIVE)) : (s < this._minPos + this._minScale && (s = this._minPos + this._minScale), s > this._minPos + this._maxScale && (s = this._minPos + this._maxScale), this._maxPos = s) : _ === c && (this._isHorizontal ? (this._minPos = s, this._maxPos = this._minPos + r, this._maxPos >= 1 && (this._maxPos = 1, this._minPos = this._maxPos - r)) : (this._maxPos = s, this._minPos = this._maxPos - r, this._minPos <= 0 && (this._minPos = 0, this._maxPos = this._minPos + r))), this._updateElesPosition(), this.onRangeChanging())
        }, i.prototype._onDocMouseup = function(e) {
          this._isVisible && (this._clearInterval(), this._isBtnMousedown = !1, this._startPt && (this.onRangeChanged(), this._startPt = null, this._movingOffset = null), t.removeClass(this._minHandler, i._RANGESLIDER_HANDLE_ACTIVE), t.removeClass(this._maxHandler, i._RANGESLIDER_HANDLE_ACTIVE))
        }, i.prototype._onRangeSpaceMousedown = function(i) {
          var e = i instanceof MouseEvent ? new t.Point(i.pageX, i.pageY) : new t.Point(i.changedTouches[0].pageX, i.changedTouches[0].pageY),
            s = t.getElementRect(this._rsContent),
            n = t.getElementRect(this._rangeHandler),
            a = i.srcElement || i.target,
            h = 0;
          i.stopPropagation(), i.preventDefault(), a !== this._rsContent && a !== this._rsEle || (this._isHorizontal ? (h = n.width / s.width, e.x < n.left ? h *= -1 : e.x > n.left + n.width && (h *= 1)) : (h = n.height / s.height, e.y < n.top ? h *= 1 : e.y > n.top + n.height && (h *= -1)), 0 !== h && this._doSliding(h, e))
        }, i.prototype._onRangeMouseleave = function(t) {
          t.stopPropagation(), t.preventDefault(), this._isBtnMousedown && (this._clearInterval(), this.onRangeChanged())
        }, i.prototype._onBtnMousedown = function(e) {
          var s = e.srcElement || e.target,
            n = 0;
          if (e.stopPropagation(), e.preventDefault(), t.hasClass(s, i._RANGESLIDER_DECBTN)) {
            if (0 === this._minPos) return;
            n = -.05
          } else if (t.hasClass(s, i._RANGESLIDER_INCBTN)) {
            if (1 === this._maxPos) return;
            n = .05
          }
          this._isBtnMousedown = !0, 0 !== n && this._doSliding(n)
        }, i.prototype._refresh = function(i) {
          var e, s, n = 0,
            a = t.getElementRect(this._rsContainer);
          i && (this._rangeSliderRect = i), this._rangeSliderRect && (this._hasButtons && this._buttonsVisible ? (this._decBtn.style.display = "block", this._incBtn.style.display = "block", n = this._isHorizontal ? this._decBtn.offsetWidth + this._minHandler.offsetWidth / 2 : this._decBtn.offsetHeight + this._minHandler.offsetHeight / 2) : (this._hasButtons && (this._decBtn.style.display = "none", this._incBtn.style.display = "none"), n = this._isHorizontal ? this._minHandler.offsetWidth / 2 : this._minHandler.offsetHeight / 2), e = this._getRsRect(), this._isHorizontal ? (e.left -= this._minHandler.offsetWidth / 2, e.width += this._minHandler.offsetWidth, s = {
            left: n,
            width: e.width - 2 * n
          }) : (e.top -= this._minHandler.offsetHeight / 2, e.height += this._minHandler.offsetHeight, s = {
            top: n,
            height: e.height - 2 * n
          }), t.setCss(this._rsEle, e), t.setCss(this._rsContent, s), a = t.getElementRect(this._rsContent), this._plotBox = {
            x: a.left,
            y: a.top,
            width: a.width,
            height: a.height
          }, this._updateElesPosition())
        }, i.prototype._updateElesPosition = function() {
          var t, i, e, s = this._minHandler,
            n = (this._rangeHandler, this._maxHandler),
            a = this._plotBox,
            h = this._isHorizontal;
          a && (t = h ? {
            left: this._minPos * a.width - .5 * s.offsetWidth
          } : {
            top: (1 - this._minPos) * a.height - .5 * n.offsetHeight
          }, i = h ? {
            left: this._minPos * a.width,
            width: (this._maxPos - this._minPos) * a.width
          } : {
            top: (1 - this._maxPos) * a.height,
            height: (this._maxPos - this._minPos) * a.height
          }, e = h ? {
            left: this._maxPos * a.width - .5 * n.offsetWidth
          } : {
            top: (1 - this._maxPos) * a.height - .5 * s.offsetHeight
          }, this._refreshSlider(t, i, e))
        }, i.prototype._refreshSlider = function(i, e, s) {
          t.setCss(this._minHandler, i), t.setCss(this._rangeHandler, e), t.setCss(this._maxHandler, s)
        }, i.prototype._invalidate = function() {
          var e, s;
          this._rsContainer && (e = this._isHorizontal ? i._HRANGESLIDER : i._VRANGESLIDER, s = this._isHorizontal ? i._VRANGESLIDER : i._HRANGESLIDER, t.removeClass(this._rsEle, s), t.addClass(this._rsEle, e), [this._rsEle, this._rsContent, this._minHandler, this._maxHandler, this._rangeHandler].forEach(function(t) {
            t.removeAttribute("style")
          }), this._refresh())
        }, i.prototype._changeRange = function(t) {
          var i = this._maxPos - this._minPos;
          t < 0 && 0 === this._minPos || t > 0 && 1 === this._maxPos || (t < 0 ? (this._minPos += t, this._minPos = this._minPos < 0 ? 0 : this._minPos, this._maxPos = this._minPos + i) : (this._maxPos += t, this._maxPos = this._maxPos > 1 ? 1 : this._maxPos, this._minPos = this._maxPos - i), this._updateElesPosition())
        }, i.prototype._doSliding = function(i, e) {
          t.getElementRect(this._rsContent), t.getElementRect(this._rangeHandler);
          this._clearInterval(), this._startPt = new t.Point, this._changeRange(i), this.onRangeChanged(), this._setSlidingInterval(i, e)
        }, i.prototype._setSlidingInterval = function(i, e) {
          var s, n, a = this;
          this._slidingInterval = window.setInterval(function() {
            if (e)
              if (s = t.getElementRect(a._rsContent), n = t.getElementRect(a._rangeHandler), a._isHorizontal) {
                if (e.x >= n.left && e.x <= n.left + n.width) return void a._clearInterval()
              } else if (e.y >= n.top && e.y <= n.top + n.height) return void a._clearInterval();
            a._changeRange(i), a.onRangeChanged()
          }, 200)
        }, i.prototype._clearInterval = function() {
          this._slidingInterval && window.clearInterval(this._slidingInterval)
        }, i.prototype._getRsRect = function() {
          var t = this._rangeSliderRect,
            i = {};
          if (t) return ["left", "top", "width", "height"].forEach(function(e) {
            t[e] && (i[e] = t[e])
          }), i
        }, i._HRANGESLIDER = "wj-chart-hrangeslider", i._VRANGESLIDER = "wj-chart-vrangeslider", i._RANGESLIDER_DECBTN = "wj-rangeslider-decbtn", i._RANGESLIDER_INCBTN = "wj-rangeslider-incbtn", i._RANGESLIDER_RANGEHANDLE = "wj-rangeslider-rangehandle", i._RANGESLIDER_MINHANDLE = "wj-rangeslider-minhandle", i._RANGESLIDER_MAXHANDLE = "wj-rangeslider-maxhandle", i._RANGESLIDER_HANDLE_ACTIVE = "wj-rangeslider-handle-active", i
      }();
      i._RangeSlider = e
    }(i.interaction || (i.interaction = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(i) {
    ! function(e) {
      "use strict";
      var s;
      ! function(t) {
        t[t.X = 0] = "X", t[t.Y = 1] = "Y"
      }(s = e.Orientation || (e.Orientation = {}));
      var n = function() {
        function n(e, n) {
          this._isVisible = !0, this._orientation = s.X, this._seamless = !1, this._minScale = 0, this._maxScale = 1, this.rangeChanged = new t.Event, this._chart = t.asType(e, i.FlexChartCore, !1), this._createRangeSelector(), t.copy(this, n)
        }
        return Object.defineProperty(n.prototype, "isVisible", {
          get: function() {
            return this._isVisible
          },
          set: function(i) {
            i != this._isVisible && (this._isVisible = t.asBoolean(i), this._rangeSlider && (this._rangeSlider.isVisible = i))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "min", {
          get: function() {
            return this._min
          },
          set: function(i) {
            if ((i = t.asNumber(i, !0, !1)) != this._min) {
              var e = !1;
              null == i || void 0 === i || isNaN(i) || null == this._max ? (this._min = i, e = !0) : i <= this._max && (this._min = i, e = !0), this._rangeSlider && e && this._changeRange()
            }
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "max", {
          get: function() {
            return this._max
          },
          set: function(i) {
            if ((i = t.asNumber(i, !0, !1)) != this._max) {
              var e = !1;
              null == i || isNaN(i) ? (this._max = i, e = !0) : i >= this._min && (this._max = i, e = !0), this._rangeSlider && e && this._changeRange()
            }
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "orientation", {
          get: function() {
            return this._orientation
          },
          set: function(i) {
            (i = t.asEnum(i, s)) !== this._orientation && (this._orientation = i, this._rangeSlider && (this._rangeSlider.isHorizontal = i == s.X))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "seamless", {
          get: function() {
            return this._seamless
          },
          set: function(i) {
            (i = t.asBoolean(i, !0)) != this._seamless && (this._seamless = i, this._rangeSlider && (this._rangeSlider.seamless = i))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "minScale", {
          get: function() {
            return this._minScale
          },
          set: function(i) {
            (i = t.asNumber(i)) <= 1 && i >= 0 && i != this._minScale && i < this._maxScale && (this._minScale = i, this._rangeSlider && (this._rangeSlider.minScale = t.asNumber(i), this._updateMinAndMaxWithScale(!0)))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "maxScale", {
          get: function() {
            return this._maxScale
          },
          set: function(i) {
            (i = t.asNumber(i)) <= 1 && i >= 0 && i != this._maxScale && i > this._minScale && (this._maxScale = i, this._rangeSlider && (this._rangeSlider.maxScale = t.asNumber(i), this._updateMinAndMaxWithScale(!0)))
          },
          enumerable: !0,
          configurable: !0
        }), n.prototype.remove = function() {
          this._rangeSelectorEle && (this._chart.hostElement.removeChild(this._rangeSelectorEle), this._switchEvent(!1), this._rangeSelectorEle = null, this._rangeSlider = null)
        }, n.prototype.onRangeChanged = function(t) {
          this.rangeChanged.raise(this, t)
        }, n.prototype._createRangeSelector = function() {
          var i = this._chart.hostElement,
            n = this._orientation === s.X;
          this._rangeSelectorEle = t.createElement('<div class="wj-chart-rangeselector-container"></div>'), this._rangeSlider = new e._RangeSlider(this._rangeSelectorEle, !1, !1, {
            isHorizontal: n,
            isVisible: this._isVisible,
            seamless: this._seamless
          }), i.appendChild(this._rangeSelectorEle), this._switchEvent(!0)
        }, n.prototype._switchEvent = function(t) {
          var i = t ? "addHandler" : "removeHandler";
          this._chart.hostElement && (this._rangeSlider.rangeChanged[i](this._updateRange, this), this._chart.rendered[i](this._refresh, this))
        }, n.prototype._refresh = function() {
          var e, s, n, a = this._chart.hostElement,
            h = t.getElementRect(this._rangeSelectorEle);
          e = a.querySelector("." + i.FlexChart._CSS_PLOT_AREA), s = t.getElementRect(e), (n = e.getBBox()) && n.width && n.height && (this._adjustMinAndMax(), this._rangeSlider._refresh({
            left: n.x,
            top: s.top - h.top,
            width: n.width,
            height: n.height
          }))
        }, n.prototype._adjustMinAndMax = function() {
          var i = this,
            e = i._chart,
            n = i._rangeSlider,
            a = i._min,
            h = i._max,
            o = i._orientation === s.X ? e.axisX : e.axisY,
            r = t.isDate(o.actualMin) ? o.actualMin.valueOf() : o.actualMin,
            _ = t.isDate(o.actualMax) ? o.actualMax.valueOf() : o.actualMax;
          i._min = null === a || isNaN(a) || void 0 === a || a < r || a > _ ? r : a, i._max = null === h || isNaN(h) || void 0 === h || h < r || h > _ ? _ : h;
          var l = this._chart._plotRect;
          if (l) {
            var c = void 0,
              p = void 0;
            this._orientation === s.X ? (c = (o.convert(i._min) - l.left) / l.width, p = (o.convert(i._max) - l.left) / l.width) : (c = (l.top - o.convert(i._min)) / l.height + 1, p = (l.top - o.convert(i._max)) / l.height + 1), n._minPos = isNaN(c) ? 0 : c, n._maxPos = isNaN(p) ? 1 : p, this._updateMinAndMaxWithScale(!1)
          }
        }, n.prototype._updateMinAndMaxWithScale = function(t) {
          var i, e = this._rangeSlider,
            s = !1;
          if (0 !== this._minScale && e._minPos + this._minScale > e._maxPos && ((i = e._minPos + this._minScale) > 1 ? (e._maxPos = 1, e._minPos = 1 - this._minScale) : e._maxPos = i, s = !0), 1 !== this._maxScale && e._minPos + this._maxScale < e._maxPos && ((i = e._minPos + this._maxScale) > 1 ? (e._maxPos = 1, e._minPos = 1 - this._maxScale) : e._maxPos = i, s = !0), s) {
            var n = this._getMinAndMax();
            this._min = n.min, this._max = n.max, t && this._rangeSelectorEle && (this._rangeSlider._refresh(), this.onRangeChanged())
          }
        }, n.prototype._changeRange = function() {
          this._adjustMinAndMax(), this._rangeSelectorEle && (this._rangeSlider._refresh(), this.onRangeChanged())
        }, n.prototype._updateRange = function() {
          var t;
          this._rangeSlider;
          t = this._chart, this._orientation === s.X ? t.axisX : t.axisY;
          var i = this._getMinAndMax();
          this._min = i.min, this._max = i.max, this.onRangeChanged()
        }, n.prototype._getMinAndMax = function() {
          var t = this._rangeSlider,
            i = this._chart,
            e = i._plotRect,
            n = null,
            a = null;
          return e && (this._orientation === s.X ? (n = i.axisX.convertBack(e.left + t._minPos * e.width), a = i.axisX.convertBack(e.left + t._maxPos * e.width)) : (n = i.axisY.convertBack(e.top + (1 - t._minPos) * e.height), a = i.axisY.convertBack(e.top + (1 - t._maxPos) * e.height))), {
            min: n,
            max: a
          }
        }, n
      }();
      e.RangeSelector = n
    }(i.interaction || (i.interaction = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(i) {
    ! function(e) {
      var s;
      ! function(t) {
        t[t.Zoom = 0] = "Zoom", t[t.Pan = 1] = "Pan"
      }(s = e.MouseAction || (e.MouseAction = {}));
      var n;
      ! function(t) {
        t[t.X = 0] = "X", t[t.Y = 1] = "Y", t[t.XY = 2] = "XY"
      }(n = e.InteractiveAxes || (e.InteractiveAxes = {}));
      var a = function() {
        function e(i, e) {
          this._chart = null, this._zoomEle = null, this._overlayEle = null, this._wrapperMousedown = null, this._wrapperMouseMove = null, this._wrapperMouseup = null, this._wrapperPointerdown = null, this._wrapperPointerMove = null, this._wrapperPointerup = null, this._wrapperTouchStart = null, this._wrapperTouchMove = null, this._wrapperTouchEnd = null, this._wrapperMouseWheel = null, this._startFirstPt = null, this._minX = null, this._maxX = null, this._minY = null, this._maxY = null, this._threadHold = 20, this._clip = {}, this._selection = {}, this._startPointers = [], this._mvPointers = [], this._pinchStartEvents = [], this._minXRange = null, this._minYRange = null, this._innerUpdating = !1, this._lastMinX = null, this._lastMaxX = null, this._lastMinY = null, this._lastMaxY = null, this._mouseAction = s.Zoom, this._interactiveAxes = n.X, this._enable = !0, this._scaleX = 1, this._scaleY = 1, this._posX = 0, this._posY = 0, i || t.assert(!1, "The FlexChart cannot be null."), this._chart = i, t.copy(this, e), this._initialize()
        }
        return Object.defineProperty(e.prototype, "mouseAction", {
          get: function() {
            return this._mouseAction
          },
          set: function(i) {
            this._mouseAction = t.asEnum(i, s)
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "interactiveAxes", {
          get: function() {
            return this._interactiveAxes
          },
          set: function(i) {
            i !== this._interactiveAxes && (this._interactiveAxes = t.asEnum(i, n))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "enable", {
          get: function() {
            return this._enable
          },
          set: function(i) {
            i !== this._enable && (this._enable = t.asBoolean(i, !0))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "scaleX", {
          get: function() {
            return this._scaleX
          },
          set: function(i) {
            i !== this._scaleX && (this._scaleX = i < 0 ? 0 : i > 1 ? 1 : t.asNumber(i), this._seriesGroup && this._initAxisRangeWithPosAndScale(!0))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "scaleY", {
          get: function() {
            return this._scaleY
          },
          set: function(i) {
            i !== this._scaleY && (this._scaleY = i < 0 ? 0 : i > 1 ? 1 : t.asNumber(i), this._seriesGroup && this._initAxisRangeWithPosAndScale(!1))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "posX", {
          get: function() {
            return this._posX
          },
          set: function(i) {
            i !== this._posX && (this._posX = i < 0 ? 0 : i > 1 ? 1 : t.asNumber(i), this._seriesGroup && this._initAxisRangeWithPosAndScale(!0))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "posY", {
          get: function() {
            return this._posY
          },
          set: function(i) {
            i !== this._posY && (this._posY = i < 0 ? 0 : i > 1 ? 1 : t.asNumber(i), this._seriesGroup && this._initAxisRangeWithPosAndScale(!1))
          },
          enumerable: !0,
          configurable: !0
        }), e.prototype.remove = function() {
          this._zoomEle && (this._chart.hostElement.removeChild(this._zoomEle), this._zoomEle = null), t.removeClass(this._chart.hostElement, e._CSS_TOUCH_DISABLED), this._switchEvent(!1), this._wrapperMousedown = null, this._wrapperMouseMove = null, this._wrapperMouseup = null, this._wrapperPointerdown = null, this._wrapperPointerMove = null, this._wrapperPointerup = null, this._wrapperTouchStart = null, this._wrapperTouchMove = null, this._wrapperTouchEnd = null, this._wrapperMouseWheel = null
        }, e.prototype.reset = function() {
          var t = this._chart,
            i = t.axisX,
            e = t.axisY;
          this._maxX && (i.max = this._maxX), this._minX && (i.min = this._minX), this._maxY && (e.max = this._maxY), this._minY && (e.min = this._minY), this._initAxisRangeWithPosAndScale(!0), this._initAxisRangeWithPosAndScale(!1)
        }, e.prototype._refreshChart = function() {
          var t = this._chart,
            i = t.axisX,
            e = t.axisY;
          this._minX = this._getAxisMin(i), this._maxX = this._getAxisMax(i), this._minY = this._getAxisMin(e), this._maxY = this._getAxisMax(e), this._minXRange = .005 * (this._maxX - this._minX), this._minYRange = .005 * (this._maxY - this._minY), this._initAxisRangeWithPosAndScale(!0), this._initAxisRangeWithPosAndScale(!1)
        }, e.prototype._initialize = function() {
          var i = this._chart.hostElement;
          this._zoomEle = t.createElement('<div class="' + e._CSS_ZOOM + '"><div class="' + e._CSS_ZOOM_OVERLAY + '"></div>'), this._zoomEle.style.visibility = "visible", i.appendChild(this._zoomEle), t.addClass(i, e._CSS_TOUCH_DISABLED), this._overlayEle = this._zoomEle.querySelector("." + e._CSS_ZOOM_OVERLAY), this._wrapperMousedown = this._onMousedown.bind(this), this._wrapperMouseMove = this._onMouseMove.bind(this), this._wrapperMouseup = this._onMouseup.bind(this), this._wrapperPointerdown = this._onPointerdown.bind(this), this._wrapperPointerMove = this._onPointerMove.bind(this), this._wrapperPointerup = this._onPointerup.bind(this), this._wrapperMouseWheel = this._onMouseWheel.bind(this), this._wrapperTouchStart = this._onTouchStart.bind(this), this._wrapperTouchMove = this._onTouchMove.bind(this), this._wrapperTouchEnd = this._onTouchEnd.bind(this), this._switchEvent(!0)
        }, e.prototype._switchEvent = function(t) {
          var i = this._chart.hostElement,
            e = t ? "addEventListener" : "removeEventListener",
            s = t ? "addHandler" : "removeHandler";
          i && (i[e]("mousedown", this._wrapperMousedown), i[e]("mousemove", this._wrapperMouseMove), document[e]("mouseup", this._wrapperMouseup), "onpointerdown" in window && (i[e]("pointerdown", this._wrapperPointerdown), i[e]("pointermove", this._wrapperPointerMove), document[e]("pointerup", this._wrapperPointerup)), i[e]("wheel", this._wrapperMouseWheel), "ontouchstart" in window && (i[e]("touchstart", this._wrapperTouchStart), i[e]("touchmove", this._wrapperTouchMove), document[e]("touchend", this._wrapperTouchEnd)), this._chart.rendered[s](this._refresh, this))
        }, e.prototype._refresh = function() {
          var e, s, n, a = this._chart,
            h = a.axisX,
            o = a.axisY,
            r = a.hostElement;
          this._seriesGroup = r.querySelector(".wj-series-group"), e = r.querySelector("." + i.FlexChart._CSS_PLOT_AREA), this._plotOffset = t.getElementRect(e), this._plotBox = e.getBBox(), this._zoomEleOffset = t.getElementRect(this._zoomEle), this._overlayEle && this._overlayEle.removeAttribute("style"), this._innerUpdating ? this._innerUpdating = !1 : (s = !1, n = !1, (null === this._minX || isNaN(this._minX) || 0 === this._minX || -1 === this._minX || this._lastMinX !== this._getAxisMin(h)) && (this._minX = this._getAxisMin(h), null === this._minX || isNaN(this._minX) || 0 === this._minX || -1 === this._minX || (s = !0)), (null === this._maxX || isNaN(this._maxX) || 0 === this._maxX || -1 === this._maxX || this._lastMaxX !== this._getAxisMax(h)) && (this._maxX = this._getAxisMax(h), null === this._maxX || isNaN(this._maxX) || 0 === this._maxX || -1 === this._maxX || (s = !0)), (null === this._minY || isNaN(this._minY) || this._lastMinY !== this._getAxisMin(o)) && (this._minY = this._getAxisMin(o), isNaN(this._minY) || (n = !0)), (null === this._maxY || isNaN(this._maxY) || this._lastMaxY !== this._getAxisMax(o)) && (this._maxY = this._getAxisMax(o), isNaN(this._maxY) || (n = !0)), this._minXRange = .005 * (this._maxX - this._minX), this._minYRange = .005 * (this._maxY - this._minY), s && null !== this._scaleX && void 0 !== this._scaleX && 1 !== this._scaleX && null !== this._posX && void 0 !== this._posX && 0 !== this._posX && this._initAxisRangeWithPosAndScale(!0), n && null !== this._scaleY && void 0 !== this._scaleY && 1 !== this._scaleY && null !== this._posY && void 0 !== this._posY && 0 !== this._posY && this._initAxisRangeWithPosAndScale(!1))
        }, e.prototype._onMousedown = function(t) {
          this._enable && (this._disabledOthersInteraction(!0), this._mouseDown(t), t.preventDefault())
        }, e.prototype._onMouseMove = function(t) {
          this._enable && (this._mouseMove(t), t.preventDefault())
        }, e.prototype._onMouseup = function(t) {
          this._enable && (this._mouseup(t), this._disabledOthersInteraction(!1))
        }, e.prototype._onMouseWheel = function(t) {
          var i = -t.deltaY > 0 ? .05 : -.05;
          this._enable && (this._scaling = !0, this._interactiveAxes !== n.X && this._interactiveAxes !== n.XY || this._updateAxisByChg(!0, i, -i), this._interactiveAxes !== n.Y && this._interactiveAxes !== n.XY || this._updateAxisByChg(!1, i, -i), this._scaling = !1, t.preventDefault())
        }, e.prototype._mouseDown = function(i) {
          this._startFirstPt = this._getPoint(i), this._updatePoint(this._startFirstPt), this._mouseAction === s.Zoom ? this._initOverlay() : (this._seriesGroup.setAttribute("clip-path", "url(#" + this._chart._plotrectId + ")"), t.toggleClass(this._chart.hostElement, e._CSS_PANABLE, this._mouseAction === s.Pan))
        }, e.prototype._mouseMove = function(i) {
          var e;
          this._startFirstPt && (e = this._getPoint(i), this._updatePoint(e), this._endPoint = new t.Point(e.x, e.y), this._mouseAction === s.Zoom ? this._updateOverLay(e) : (this._panning = !0, this._panningChart(e.x - this._startFirstPt.x, e.y - this._startFirstPt.y)))
        }, e.prototype._mouseup = function(i) {
          var n = this._endPoint;
          this._chart.axisX;
          if (!this._startFirstPt || !n) return t.removeClass(this._chart.hostElement, e._CSS_PANABLE), void this._reset();
          this._mouseAction === s.Zoom ? (this._zoomedChart(n), this._reset()) : (this._pannedChart(n.x - this._startFirstPt.x, n.y - this._startFirstPt.y), this._reset()), t.removeClass(this._chart.hostElement, e._CSS_PANABLE)
        }, e.prototype._onPointerdown = function(t) {
          if (this._enable) {
            switch (this._disabledOthersInteraction(!0), t.pointerType) {
              case "touch":
                this._pointerDown(t);
                break;
              case "mouse":
                this._mouseDown(t)
            }
            t.preventDefault()
          }
        }, e.prototype._onPointerMove = function(t) {
          if (this._enable) {
            switch (t.pointerType) {
              case "touch":
                this._pointerMove(t);
                break;
              case "mouse":
                this._mouseMove(t)
            }
            t.preventDefault()
          }
        }, e.prototype._onPointerup = function(t) {
          if (this._enable) {
            switch (t.pointerType) {
              case "touch":
                this._pointerUp(t);
                break;
              case "mouse":
                this._mouseup(t)
            }
            this._disabledOthersInteraction(!1), t.preventDefault()
          }
        }, e.prototype._pointerDown = function(t) {
          t.preventManipulation && t.preventManipulation(), this._seriesGroup.setAttribute("clip-path", "url(#" + this._chart._plotrectId + ")"), this._startPointers.push({
            id: t.pointerId,
            x: t.pageX,
            y: t.pageY
          }), 1 === this._startPointers.length ? (this._scaling = !1, this._panning = !0) : 2 === this._startPointers.length && (this._panning = !1, this._scaling = !0, this._startDistance = {
            x: this._startPointers[0].x - this._startPointers[1].x,
            y: this._startPointers[0].y - this._startPointers[1].y
          })
        }, e.prototype._pointerMove = function(i) {
          var e, s, a, h, o = new t.Point(i.pageX, i.pageY),
            r = {},
            _ = {};
          if (i.preventManipulation && i.preventManipulation(), this._panning) {
            if (!this._pointInPlotArea(o)) return;
            this._endPoint = new t.Point(i.pageX, i.pageY), this._panningChart(this._endPoint.x - this._startPointers[0].x, this._endPoint.y - this._startPointers[0].y)
          } else this._scaling && (e = this._startPointers[0].id + "", s = this._startPointers[1].id + "", this._mvPointers[i.pointerId + ""] = {
            x: i.pageX,
            y: i.pageY
          }, this._mvPointers[e] && this._mvPointers[s] && (Math.abs(this._startDistance.x) > this._threadHold && this._interactiveAxes !== n.Y && (a = this._mvPointers[e].x - this._plotOffset.left, h = this._startPointers[0].x - this._plotOffset.left, _.x = Math.abs((this._mvPointers[e].x - this._mvPointers[s].x) / this._startDistance.x), r.x = a - _.x * h, this._clip.x = (this._plotBox.x - a) / _.x + h, this._selection.w = this._plotBox.width / _.x), Math.abs(this._startDistance.y) > this._threadHold && this._interactiveAxes !== n.X && (a = this._mvPointers[e].y - this._plotOffset.top, h = this._startPointers[0].y - this._plotOffset.top, _.y = Math.abs((this._mvPointers[e].y - this._mvPointers[s].y) / this._startDistance.y), r.y = a - _.y * h, this._clip.y = (this._plotBox.y - a) / _.y + h, this._selection.h = this._plotBox.height / _.y), this._scalingChart(_, r)))
        }, e.prototype._pointerUp = function(t) {
          t.preventManipulation && t.preventManipulation(), this._panning ? (this._endPoint && this._pannedChart(this._endPoint.x - this._startPointers[0].x, this._endPoint.y - this._startPointers[0].y), this._reset()) : this._scaling && (this._scaledChart(t), this._reset())
        }, e.prototype._onTouchStart = function(t) {
          if (this._enable) return this._disabledOthersInteraction(!0), 1 == t.touches.length ? (this._scaling = !1, this._panning = !0, this._startFirstPt = this._getPoint(t)) : 2 == t.touches.length && (this._pinchStartEvents = this._getTouchPair(t), this._startDistance = this._touchDistance(t), this._panning = !1, this._scaling = !0), this._seriesGroup && this._seriesGroup.setAttribute("clip-path", "url(#" + this._chart._plotrectId + ")"), this._chart._hideToolTip(), !0
        }, e.prototype._onTouchMove = function(i) {
          if (this._enable) {
            var e, s, a, h, o, r = {},
              _ = {},
              l = i.touches[0],
              c = new t.Point(l.pageX, l.pageY);
            if (i.preventDefault(), this._panning) {
              if (this._startFirstPt) {
                if (!this._pointInPlotArea(c)) return;
                this._endPoint = new t.Point(l.pageX, l.pageY), this._panningChart(this._endPoint.x - this._startFirstPt.x, this._endPoint.y - this._startFirstPt.y)
              }
            } else this._scaling && (a = this._touchDistance(i), h = this._getTouchPair(i)[0], o = this._pinchStartEvents[0], Math.abs(this._startDistance.x) > this._threadHold && this._interactiveAxes !== n.Y && (e = h.pageX - this._plotOffset.left, s = o.pageX - this._plotOffset.left, r.x = Math.abs(a.x / this._startDistance.x), _.x = e - r.x * s, this._clip.x = (this._plotBox.x - e) / r.x + s, this._selection.w = this._plotBox.width / r.x), Math.abs(this._startDistance.y) > this._threadHold && this._interactiveAxes !== n.X && (e = h.pageY - this._plotOffset.top, s = o.pageY - this._plotOffset.top, r.y = Math.abs(a.y / this._startDistance.y), _.y = e - r.y * s, this._clip.y = (this._plotBox.y - e) / r.y + s, this._selection.h = this._plotBox.height / r.y), this._scalingChart(r, _));
            return !0
          }
        }, e.prototype._onTouchEnd = function(t) {
          if (this._enable) {
            var i = this._endPoint;
            if (this._panning) {
              if (!this._startFirstPt || !i) return void this._reset();
              this._pannedChart(i.x - this._startFirstPt.x, i.y - this._startFirstPt.y)
            } else this._scaling && this._scaledChart(t);
            return this._reset(), this._disabledOthersInteraction(!1), !0
          }
        }, e.prototype._initOverlay = function() {
          switch (this._zoomEle.style.visibility = "visible", this._interactiveAxes) {
            case n.X:
              this._overlayEle.style.left = this._startFirstPt.x - this._zoomEleOffset.left + "px", this._overlayEle.style.top = this._plotOffset.top - this._zoomEleOffset.top + "px";
              break;
            case n.Y:
              this._overlayEle.style.left = this._plotBox.x + "px", this._overlayEle.style.top = this._startFirstPt.y - this._zoomEleOffset.top + "px";
              break;
            case n.XY:
              this._overlayEle.style.left = this._startFirstPt.x - this._zoomEleOffset.left + "px", this._overlayEle.style.top = this._startFirstPt.y - this._zoomEleOffset.top + "px"
          }
        }, e.prototype._updateOverLay = function(i) {
          var e = this._startFirstPt.x - i.x,
            s = this._startFirstPt.y - i.y,
            a = {};
          switch (this._interactiveAxes) {
            case n.X:
              if (Math.abs(e) < this._threadHold) return;
              a = e <= 0 ? {
                width: Math.abs(e) + "px",
                height: this._plotBox.height + "px"
              } : {
                left: i.x - this._zoomEleOffset.left + "px",
                width: e + "px",
                height: this._plotBox.height + "px"
              };
              break;
            case n.Y:
              if (Math.abs(s) < this._threadHold) return;
              a = s <= 0 ? {
                height: Math.abs(s) + "px",
                width: this._plotBox.width + "px"
              } : {
                top: i.y - this._zoomEleOffset.top + "px",
                height: s + "px",
                width: this._plotBox.width + "px"
              };
              break;
            case n.XY:
              Math.abs(e) >= this._threadHold && (a.width = Math.abs(e) + "px", e > 0 && (a.left = i.x - this._zoomEleOffset.left + "px")), Math.abs(s) >= this._threadHold && (a.height = Math.abs(s) + "px", s > 0 && (a.top = i.y - this._zoomEleOffset.top + "px"))
          }
          t.setCss(this._overlayEle, a)
        }, e.prototype._updatePoint = function(t) {
          var i = this._plotOffset;
          t.x < i.left && (t.x = i.left), t.x > i.left + i.width && (t.x = i.left + i.width), t.y < i.top && (t.y = i.top), t.y > i.top + i.height && (t.y = i.top + i.height)
        }, e.prototype._pointInPlotArea = function(t) {
          var i = this._plotOffset;
          return t.x >= i.left && t.x <= i.left + i.width && t.y >= i.top && t.y <= i.top + i.height
        }, e.prototype._zoomedChart = function(t) {
          t && (this._interactiveAxes !== n.X && this._interactiveAxes !== n.XY || this._zoomedAxis(t, !0), this._interactiveAxes !== n.Y && this._interactiveAxes !== n.XY || this._zoomedAxis(t, !1), this._startFirstPt = null)
        }, e.prototype._zoomedAxis = function(t, i) {
          var e, s, n = i ? this._chart.axisX : this._chart.axisY,
            a = i ? "x" : "y",
            h = i ? "left" : "top";
          t && Math.abs(this._startFirstPt[a] - t[a]) > this._threadHold && (e = n.convertBack(this._startFirstPt[a] - this._plotOffset[h] + this._plotBox[a]), (s = n.convertBack(t[a] - this._plotOffset[h] + this._plotBox[a])) - e != 0 && this._updateAxisRange(n, Math.min(e, s), Math.max(e, s)))
        }, e.prototype._panningChart = function(t, i) {
          var e = this._chart.axisX,
            s = this._chart.axisY,
            a = this._getTransFormGroups();
          t = Math.abs(t) < this._threadHold ? 0 : t, i = Math.abs(i) < this._threadHold ? 0 : i, this._interactiveAxes === n.X && (i = 0), this._interactiveAxes === n.Y && (t = 0), t > 0 && e.actualMin.valueOf() === this._minX && (t = 0), t < 0 && e.actualMax.valueOf() === this._maxX && (t = 0), i > 0 && s.actualMax.valueOf() === this._maxY && (i = 0), i < 0 && s.actualMin.valueOf() === this._minY && (i = 0);
          for (var h = 0; h < a.length; h++) a[h].setAttribute("transform", "translate(" + t + "," + i + ")")
        }, e.prototype._pannedChart = function(t, i) {
          this._interactiveAxes !== n.X && this._interactiveAxes !== n.XY || this._updateAxisByDistance(!0, t), this._interactiveAxes !== n.Y && this._interactiveAxes !== n.XY || this._updateAxisByDistance(!1, -i)
        }, e.prototype._scalingChart = function(t, i) {
          var e, s, n, a = this._chart.axisX,
            h = this._chart.axisY,
            o = void 0 !== i.x ? i.x : 0,
            r = void 0 !== i.y ? i.y : 0;
          if (t) {
            e = this._getTransFormGroups(), void 0 !== t.x && t.x < 1 && a.actualMin.valueOf() === this._minX && a.actualMax.valueOf() === this._maxX && (t.x = 1, o = 0), void 0 !== t.y && t.y < 1 && h.actualMin.valueOf() === this._minY && h.actualMax.valueOf() === this._maxY && (t.y = 1, r = 0), s = void 0 !== t.x ? t.x : 1, n = void 0 !== t.y ? t.y : 1;
            for (var _ = 0; _ < e.length; _++) e[_].setAttribute("transform", "translate(" + o + ", " + r + ") scale(" + s + ", " + n + ")")
          }
        }, e.prototype._scaledChart = function(t) {
          var i, e, s = this._chart,
            a = s.axisX,
            h = s.axisY;
          this._clip && (this._interactiveAxes !== n.Y && void 0 !== this._clip.x && (i = Math.max(this._minX, a.convertBack(this._clip.x))) - (e = Math.min(this._maxX, a.convertBack(this._clip.x + this._selection.w))) != 0 && this._updateAxisRange(a, i, e), this._interactiveAxes !== n.X && void 0 !== this._clip.y && (e = Math.min(this._maxY, h.convertBack(this._clip.y)), (i = Math.max(this._minY, h.convertBack(this._clip.y + this._selection.h))) - e != 0 && this._updateAxisRange(h, i, e)))
        }, e.prototype._updateAxisByDistance = function(t, i) {
          var e, s = t ? this._chart.axisX : this._chart.axisY,
            n = t ? this._minX : this._minY,
            a = t ? this._maxX : this._maxY,
            h = s.actualMin.valueOf(),
            o = s.actualMax.valueOf();
          if (0 !== i) {
            if (i > 0 && n === h || i < 0 && a === o) return this._innerUpdating = !0, void this._chart.invalidate();
            e = i / (t ? this._plotBox.width : this._plotBox.height), this._updateAxisByChg(t, -e, -e)
          }
        }, e.prototype._updateAxisByChg = function(t, i, e) {
          var s, n, a = t ? this._chart.axisX : this._chart.axisY,
            h = t ? this._minX : this._minY,
            o = t ? this._maxX : this._maxY,
            r = a.actualMin.valueOf(),
            _ = (a.actualMax.valueOf(), this._chart._plotRect),
            l = t ? _.left : _.top,
            c = t ? _.width : _.height,
            p = t ? this._minXRange : this._minYRange;
          isNaN(i) || isNaN(e) || (this._panning ? i < 0 ? (s = t ? a.convertBack(l + i * c) : a.convertBack(l + c - i * c)) < h ? (s = h, n = t ? a.convertBack(a.convert(s) + c) : a.convertBack(a.convert(s) - c)) : n = t ? a.convertBack(l + c + e * c) : a.convertBack(l - e * c) : (n = t ? a.convertBack(l + c + e * c) : a.convertBack(l - e * c)) > o ? (n = o, s = t ? a.convertBack(a.convert(n) - c) : a.convertBack(a.convert(n) + c)) : s = t ? a.convertBack(l + i * c) : a.convertBack(l + c - i * c) : this._scaling && (s = t ? a.convertBack(l + i * c) : a.convertBack(l + c - i * c), n = t ? a.convertBack(l + c + e * c) : a.convertBack(l - e * c), s < h && (s = h), n > o && (n = o), n - s < p && (s = n - p)), this._updateAxisRange(a, s, n))
        }, e.prototype._initAxisRangeWithPosAndScale = function(t) {
          var i, e, s, n;
          t ? (e = (i = this._maxX - this._minX) * this._scaleX, n = (s = this._minX + this._posX * (i - e)) + e, this._innerUpdating = !0, this._chart.axisX.min = s, this._chart.axisX.max = n, this._lastMinX = s, this._lastMaxX = n) : (e = (i = this._maxY - this._minY) * this._scaleY, n = (s = this._minY + this._posY * (i - e)) + e, this._innerUpdating = !0, this._chart.axisY.min = s, this._chart.axisY.max = n, this._lastMinY = s, this._lastMaxY = n)
        }, e.prototype._updateAxisRange = function(t, i, e) {
          this._chart.beginUpdate(), t.min = i, t.max = e, t === this._chart.axisX ? (this._lastMinX = i, this._lastMaxX = e) : (this._lastMinY = i, this._lastMaxY = e), this._innerUpdating = !0, this._chart.endUpdate()
        }, e.prototype._reset = function() {
          this._scaling = !1, this._panning = !1, this._startDistance = 0, this._startFirstPt = null, this._pinchStartEvents = [], this._startPointers = [], this._mvPointers = [], this._endPoint = null, this._clip = {}, this._selection = {}
        }, e.prototype._getAxisMin = function(i) {
          return t.isDate(i.actualMin) ? i.actualMin.valueOf() : i.actualMin
        }, e.prototype._getAxisMax = function(i) {
          return t.isDate(i.actualMax) ? i.actualMax.valueOf() : i.actualMax
        }, e.prototype._getTransFormGroups = function() {
          var t = this._seriesGroup.querySelectorAll("g[clip-path]");
          return 0 === t.length && (t = this._seriesGroup.querySelectorAll("g")), t
        }, e.prototype._disabledOthersInteraction = function(i) {
          var s = this._chart.hostElement;
          if (null !== s && void 0 !== s)
            for (var n = s.querySelectorAll(".wj-chart-linemarker-container"), a = 0; a < n.length; a++) i ? t.addClass(n[a], e._CSS_BLOCK_INTERACTION) : t.removeClass(n[a], e._CSS_BLOCK_INTERACTION)
        }, e.prototype._getPoint = function(i) {
          return i instanceof MouseEvent ? new t.Point(i.pageX, i.pageY) : new t.Point(i.changedTouches[0].pageX, i.changedTouches[0].pageY)
        }, e.prototype._getTouchPair = function(i) {
          var e = [];
          return t.isArray(i) ? (e[0] = i[0], e[1] = i[1]) : "touchend" === i.type ? 1 === i.touches.length ? (e[0] = i.touches[0], e[1] = i.changedTouches[0]) : 0 === i.touches.length && (e[0] = i.changedTouches[0], e[1] = i.changedTouches[1]) : (e[0] = i.touches[0], e[1] = i.touches[1]), e
        }, e.prototype._touchDistance = function(t) {
          var i = this._getTouchPair(t),
            e = 0,
            s = 0;
          return i[0] && void 0 !== i[0].pageX && i[1] && void 0 !== i[1].pageX && (e = i[0].pageX - i[1].pageX), i[0] && void 0 !== i[0].pageY && i[1] && void 0 !== i[1].pageY && (s = i[0].pageY - i[1].pageY), {
            x: e,
            y: s
          }
        }, e._CSS_ZOOM = "wj-zoom", e._CSS_ZOOM_OVERLAY = "wj-zoom-overlay", e._CSS_PANABLE = "wj-panable", e._CSS_TOUCH_DISABLED = "wj-flexchart-touch-disabled", e._CSS_BLOCK_INTERACTION = "wj-block-other-interaction", e
      }();
      e.ChartGestures = a
    }(i.interaction || (i.interaction = {}))
  }(t.chart || (t.chart = {}))
}(wijmo || (wijmo = {}));
