SwitchButton
============

update 1.2.3
---
*   bug fix
*   upgrade demo apk
*   more clear way to use

Since the animation ran on sub thread, "toggle" and "setChecked" methods may mot execute as you wish what cause the checked status change immediately.

This problem may be solved, but I declared 2 methods to deal with the situation whether you want it happen immediately.

If you want to set the checked status immediately, just call ___setChecked(boolean checked);___ method like CheckBox. And ___slideToChecked(boolean checked);___ method for slow one with slide animation.

When toggle, call ___toggle();___ with change the status immediately and ___toggle(false);___ for slow one.


![easy_to_use](https://raw.githubusercontent.com/kyleduo/SwitchButton/master/preview/easy_to_use.png)
***
update 1.2
---
(11/08/2014)

* Add StateList support for all resources and enable/disable, pressed has been tested in Demo.
* New Style with __Material Design__, preview below.
* Add "shrink" feature. This makes you can draw your image resources out size the view bounds, in the other word, shrink the size of view and make the experience stay the same.

New Style with Material Design:

![materialdesign_style](https://raw.githubusercontent.com/kyleduo/SwitchButton/master/preview/switchbutton_md.jpg)

To use shrink feature, you can easily add these attributes in your xml file. It is recommended that set these values positive.

*   __insetLeft__: left size for shrinking
*   __insetRight__: right size for shrinking
*   __insetTop__: top size for shrinking
*   __insetBottom__: bottom size for shrinking


***
update 1.1
---
(10/08/2014)

* Fix lots of bugs.
* Change the __default style__
* Add iOS7 style, you can just use like how the demo did
* Update demo, it becomes more convenient, effective and beautiful
* Add new attribute: measureFactor, you can custom the factor between width and height now. It is convenient to config the rest space of background. Learn more in the demo.
* Update the logic of thumbMargin, it can work well with negative margins now (iOS style just using this trick).

new default style and demo apk looks like this:

![default_style](https://raw.githubusercontent.com/kyleduo/SwitchButton/master/preview/default_style.png)

![demo_preview](https://raw.githubusercontent.com/kyleduo/SwitchButton/master/preview/easy_to_style.png)

***

Update
---
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
*   __measureFactor__: factor limit the minimum width equals almost (the height of thumb * measureFactor)

In Preference

```xml
<com.kyleduo.switchbutton.SwitchButtonPreference
    android:key="example_checkbox"
    android:title="@string/pref_title_example"
    android:summary="@string/pref_description_example"
    android:defaultValue="true" />
```

***
Original
----

After a few days before, I have updated this project completely. To finish this work, I refer to the project of [@Issacw0ng](https://github.com/Issacw0ng/SwitchButton), to learn how to control the animation and response to UI and did some update under my understand.

Now, SwitchButton can used more easily to present a switch of flat style using color. The default looking is like this:

(find the latest version upside)

To offer a convenient way to configuration the looking, I designed a series of interface. You can use them to change the style. In demo, you can find how to use them. The demo looks like this:

(find the latest version upside)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SwitchButton-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1119)
