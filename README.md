SwitchButton
============

Update
----
Add an attr of radius, now you can change the radius when configure the button's face!
***
Usage
---
In xml layout file, you can configure the face of switch button using these attrs.

*   __onDrawable__: drawable of background for status ON
*   __offDrawable__: drawable of background for status OFF
*   __thumbDrawable__: drawable of thumb
*   __thumb_margin__: set inner margin between thumb and edges
*   __thumb_marginLeft/Top/Bottom/Right__: set margin for specific edge
*   __thumb_width__: set the width of thumb, probably used for gradient drawable
*   __thumb_height__: set the height of thumb
*   __onColor__: set the color of status ON, usd for flat version, the priority is below of onDrawable
*   __offColor__: like the onColor
*   __thumbColor__: like the onColor
*   __thumbPressedColor__: like the thumbColor, but for pressed status
*   __animationVelocity__: distance of animation per frame
*   __radius__: used for color version, radius of corner of background and thumb.

***
Original
----

After a few days before, I have updated this project completely. To finish this work, I refer to the project of [@Issacw0ng](https://github.com/Issacw0ng/SwitchButton), to learn how to control the animation and response to UI and did some update under my understand.

Now, SwitchButton can used more easily to present a switch of flat style using color. The default looking is like this:

![default_on](https://raw.githubusercontent.com/kyleduo/SwitchButton/master/preview/default_on.png) ![default_on](https://raw.githubusercontent.com/kyleduo/SwitchButton/master/preview/default_off.png)

To offer a convient way to configuration the looking, I designed a series of interface. You can use them to change the style. In demo, you can find how to use them. The demo looks like this:

![default_on](https://raw.githubusercontent.com/kyleduo/SwitchButton/master/preview/all_on.png)
