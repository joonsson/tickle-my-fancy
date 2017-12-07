import $ from "jquery";
import Back from "./EasePack.js";
import Elastic from "./EasePack.js";
import Power1 from "./EasePack.js";
import TweenLite from "./TweenMax.js";

$(document).ready(() => {
    TweenLite.set(".card-wrapper");
    TweenLite.set(".card", { transformStyle: "preserve-3d", transformOrigin: "left 50%", transformPerspective: 1800 });
    TweenLite.set(".back", { rotationY: 180 });
    TweenLite.set([".back", ".front"], { backfaceVisibility: "hidden" });
    TweenLite.set(".card__contents", { scale: 0, autoAlpha: 0 });

    $(".card-wrapper").bind({
        click: () => {
            TweenLite.to($(this).find(".card"), 0.8, { rotationY: -120, ease: Back.easeOut });
            TweenLite.to(".card__contents", 1.2, { scale: 1, autoAlpha: 1, delay: 0.5, ease: Elastic.easeOut });
        },
    });

    $(".close").bind({
        click: () => {
            TweenLite.to(".card__contents", 0.4, { scale: 0, autoAlpha: 0, ease: Power1.easeOut });
            TweenLite.to(".card", 0.6, { rotationY: 0, delay: 0.5, ease: Power1.easeOut });
        },
    });

    TweenLite.set(".text-box", { autoAlpha: 0, y: "20px" });

    /*$('.arrow--right').bind({
    click: function() {
     TweenLite.to(".text-box", 0.8, {autoAlpha: 1, y: '0px', ease:Power1.easeOut});
            TweenLite.to(".text-box", 0.8, {autoAlpha: 0, y: '40px', delay: 2, ease:Power1.easeIn});
    }
});*/

});
