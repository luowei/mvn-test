/*
 * jQuery UI Effects Scale 1.8.17
 *
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI/Effects/Scale
 *
 * Depends:
 *	jquery.effects.core.js
 */
(function (a, b) {
    a.effects.puff = function (b) {
        return this.queue(function () {
            var c = a(this), d = a.effects.setMode(c, b.options.mode || "hide"), e = parseInt(b.options.percent, 10) || 150, f = e / 100, g = {height:c.height(), width:c.width()};
            a.extend(b.options, {fade:!0, mode:d, percent:d == "hide" ? e : 100, from:d == "hide" ? g : {height:g.height * f, width:g.width * f}}), c.effect("scale", b.options, b.duration, b.callback), c.dequeue()
        })
    }, a.effects.scale = function (b) {
        return this.queue(function () {
            var c = a(this), d = a.extend(!0, {}, b.options), e = a.effects.setMode(c, b.options.mode || "effect"), f = parseInt(b.options.percent, 10) || (parseInt(b.options.percent, 10) == 0 ? 0 : e == "hide" ? 0 : 100), g = b.options.direction || "both", h = b.options.origin;
            e != "effect" && (d.origin = h || ["middle", "center"], d.restore = !0);
            var i = {height:c.height(), width:c.width()};
            c.from = b.options.from || (e == "show" ? {height:0, width:0} : i);
            var j = {y:g != "horizontal" ? f / 100 : 1, x:g != "vertical" ? f / 100 : 1};
            c.to = {height:i.height * j.y, width:i.width * j.x}, b.options.fade && (e == "show" && (c.from.opacity = 0, c.to.opacity = 1), e == "hide" && (c.from.opacity = 1, c.to.opacity = 0)), d.from = c.from, d.to = c.to, d.mode = e, c.effect("size", d, b.duration, b.callback), c.dequeue()
        })
    }, a.effects.size = function (b) {
        return this.queue(function () {
            var c = a(this), d = ["position", "top", "bottom", "left", "right", "width", "height", "overflow", "opacity"], e = ["position", "top", "bottom", "left", "right", "overflow", "opacity"], f = ["width", "height", "overflow"], g = ["fontSize"], h = ["borderTopWidth", "borderBottomWidth", "paddingTop", "paddingBottom"], i = ["borderLeftWidth", "borderRightWidth", "paddingLeft", "paddingRight"], j = a.effects.setMode(c, b.options.mode || "effect"), k = b.options.restore || !1, l = b.options.scale || "both", m = b.options.origin, n = {height:c.height(), width:c.width()};
            c.from = b.options.from || n, c.to = b.options.to || n;
            if (m) {
                var p = a.effects.getBaseline(m, n);
                c.from.top = (n.height - c.from.height) * p.y, c.from.left = (n.width - c.from.width) * p.x, c.to.top = (n.height - c.to.height) * p.y, c.to.left = (n.width - c.to.width) * p.x
            }
            var q = {from:{y:c.from.height / n.height, x:c.from.width / n.width}, to:{y:c.to.height / n.height, x:c.to.width / n.width}};
            if (l == "box" || l == "both")q.from.y != q.to.y && (d = d.concat(h), c.from = a.effects.setTransition(c, h, q.from.y, c.from), c.to = a.effects.setTransition(c, h, q.to.y, c.to)), q.from.x != q.to.x && (d = d.concat(i), c.from = a.effects.setTransition(c, i, q.from.x, c.from), c.to = a.effects.setTransition(c, i, q.to.x, c.to));
            (l == "content" || l == "both") && q.from.y != q.to.y && (d = d.concat(g), c.from = a.effects.setTransition(c, g, q.from.y, c.from), c.to = a.effects.setTransition(c, g, q.to.y, c.to)), a.effects.save(c, k ? d : e), c.show(), a.effects.createWrapper(c), c.css("overflow", "hidden").css(c.from);
            if (l == "content" || l == "both")h = h.concat(["marginTop", "marginBottom"]).concat(g), i = i.concat(["marginLeft", "marginRight"]), f = d.concat(h).concat(i), c.find("*[width]").each(function () {
                child = a(this), k && a.effects.save(child, f);
                var c = {height:child.height(), width:child.width()};
                child.from = {height:c.height * q.from.y, width:c.width * q.from.x}, child.to = {height:c.height * q.to.y, width:c.width * q.to.x}, q.from.y != q.to.y && (child.from = a.effects.setTransition(child, h, q.from.y, child.from), child.to = a.effects.setTransition(child, h, q.to.y, child.to)), q.from.x != q.to.x && (child.from = a.effects.setTransition(child, i, q.from.x, child.from), child.to = a.effects.setTransition(child, i, q.to.x, child.to)), child.css(child.from), child.animate(child.to, b.duration, b.options.easing, function () {
                    k && a.effects.restore(child, f)
                })
            });
            c.animate(c.to, {queue:!1, duration:b.duration, easing:b.options.easing, complete:function () {
                c.to.opacity === 0 && c.css("opacity", c.from.opacity), j == "hide" && c.hide(), a.effects.restore(c, k ? d : e), a.effects.removeWrapper(c), b.callback && b.callback.apply(this, arguments), c.dequeue()
            }})
        })
    }
})(jQuery)