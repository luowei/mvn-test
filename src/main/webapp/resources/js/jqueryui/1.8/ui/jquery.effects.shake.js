/*
 * jQuery UI Effects Shake 1.8.17
 *
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI/Effects/Shake
 *
 * Depends:
 *	jquery.effects.core.js
 */
(function (a, b) {
    a.effects.shake = function (b) {
        return this.queue(function () {
            var c = a(this), d = ["position", "top", "bottom", "left", "right"], e = a.effects.setMode(c, b.options.mode || "effect"), f = b.options.direction || "left", g = b.options.distance || 20, h = b.options.times || 3, i = b.duration || b.options.duration || 140;
            a.effects.save(c, d), c.show(), a.effects.createWrapper(c);
            var j = f == "up" || f == "down" ? "top" : "left", k = f == "up" || f == "left" ? "pos" : "neg", l = {}, m = {}, n = {};
            l[j] = (k == "pos" ? "-=" : "+=") + g, m[j] = (k == "pos" ? "+=" : "-=") + g * 2, n[j] = (k == "pos" ? "-=" : "+=") + g * 2, c.animate(l, i, b.options.easing);
            for (var p = 1; p < h; p++)c.animate(m, i, b.options.easing).animate(n, i, b.options.easing);
            c.animate(m, i, b.options.easing).animate(l, i / 2, b.options.easing, function () {
                a.effects.restore(c, d), a.effects.removeWrapper(c), b.callback && b.callback.apply(this, arguments)
            }), c.queue("fx", function () {
                c.dequeue()
            }), c.dequeue()
        })
    }
})(jQuery)