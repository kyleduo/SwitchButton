
Change Log
============

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SwitchButton-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1119)

**To get a quick preview, you can find Demo apk in [Google Play](https://play.google.com/store/apps/details?id=com.kyleduo.switchbutton.demo) or [Directly download](./demo/switchbutton_demo_141.apk).**

This project provides you a convenient way to use and customise a SwitchButton widget in Android. With just resources changed and attrs set, you can create a lifelike SwitchButton of Android 5.0+, iOS, MIUI, or Flyme and so on.

Now we get the biggest movement since SwitchButton published. v1.3.0 comes with totally reconsitution and more convenient API. A wholly new demo can give you a tour in it.

***

## 2.0.2 <font color="#FF684A" size="4">(Latest)</font>

**ENG**

1.  Fix [#122](https://github.com/kyleduo/SwitchButton/issues/122). Support sub-class extending from SwitchButton.
2.  Fix bug which move faster than expected while touch and move vertically.
3.  Remove dependency of AppCompat library.


**CHN**

1.  修复 [#122](https://github.com/kyleduo/SwitchButton/issues/122)。支持从SwitchButton集成子类。
2.  修复纵向滑动导致移动过快的Bug。
3.  移除对AppCompat库的依赖。


## 2.0.0

**ENG**

1.  Re-clarify the meaning of some params.
2.  Update the measurement of SwitchButton and the logic becomes more clear, especially the text part.
3.  Support config SwitchButton's size by setting a exact width and height. There are now TWO mainly method to control it's size.


**CHN**

1.  重新明确了参数的含义。
2.  更新了SwitchButton的测量机制，逻辑更加清晰；尤其是文字部分。
3.  支持设置确定的宽高，来确定SwitchButton的View大小。现在有两种方式可以控制SwitchButton的大小了。



## 1.4.6

- Fixed [#89](https://github.com/kyleduo/SwitchButton/issues/89) .

## 1.4.5

- Fixed [#75](https://github.com/kyleduo/SwitchButton/issues/75) [#78](https://github.com/kyleduo/SwitchButton/issues/78) [#85](https://github.com/kyleduo/SwitchButton/issues/85). 



1.4.4 
---

* Fixed [#65](https://github.com/kyleduo/SwitchButton/issues/65). 
* Update text layout, tests looks like center.


1.4.3
---

* Fixed [#64](https://github.com/kyleduo/SwitchButton/issues/64). Respect to clickable and focusable attributes.


1.4.2
---

* Support [#49](https://github.com/kyleduo/SwitchButton/issues/60). By default SwitchButton found accentColor of your theme for tintColor. (**accentColor** is used for controls according to [Material Design guideline](https://material.google.com/style/color.html#color-color-schemes). )


1.4.1
---

* fixed [#49](https://github.com/kyleduo/SwitchButton/issues/49).
* Support operation without onCheckedChanged callback.


1.4.0
---

* Add text feature. You can set text for either checked or unchecked status with __kswTextOn__ and __kswTextOff__ attrs. And you can set the margin of text in horizontal direction using __kswTextMarginH__ attr.
* For additional, you can set small icons now by using SpannableString with __setText()__ method in code.
* Thanks [@lpmfilho](https://github.com/lpmfilho)

***

1.3.4 
---

* Fix [#40](https://github.com/kyleduo/SwitchButton/issues/40)

***

1.3.3
---

*	Fix SwitchButtonMD style bug in RecyclerView and add page for test. 
   *Fix bug in setCheckedImmediately() in onCheckedChanged() method. **(setChecked in onChecked)**

***

1.3.2
---

*	**setClickable(boolean)** support.
   * Bug fix.

***

1.3.1
---
*	Remove shadow of MD style to support under 5.0.

***

1.3.0
---
*	Reconstructe the whole library.
   * More convenient customization way by __tintColor__.
   * New design demo. All APIs in ONE.
     *Fix issue [#23](https://github.com/kyleduo/SwitchButton/issues/23) [#25](https://github.com/kyleduo/SwitchButton/issues/25) [#26](https://github.com/kyleduo/SwitchButton/issues/26)
     *Just exciting!!!

***


1.2.10
---
*	Fix issue [#22](https://github.com/kyleduo/SwitchButton/issues/22) by change the attributes' name to prevent conflict;

***


1.2.9
---
*	Fix issue [#19](https://github.com/kyleduo/SwitchButton/issues/19).

***


1.2.8
---
*	Fix stretch bug while using higher API.
   *Add Gradle support.
   *Built in Android Studio.

***


1.2.7
---
*	Fix rendering bug on some devices.
   *Fix states bug.

***


1.2.6
---
*   With calling the method ___setChecked(boolean, false);___, you can change the status without invoking the listener.

***


1.2.5
---
*	Fix shrink bug in Android 5.0 (the problem is same like it is in Android 4.4, which has been fixed in 1.2.4).
   *More available to setup Material Design style SwitchButton using ___@style/MD___ in xml layout.
   *Fix Demo Project bug

***


1.2.4
---
*   fix shrink bug(that will cause the content out of bounds not disapper, on Android 4.4)
*   upload .pad resource, whitch I forgot to upload before.(My fault.)

***


1.2.3
---
*   bug fix
*   upgrade demo apk
*   more clear way to use

Since the animation ran on sub thread, "toggle" and "setChecked" methods may mot execute as you wish what cause the checked status change immediately.

This problem may be solved, but I declared 2 methods to deal with the situation whether you want it happen immediately.

If you want to set the checked status immediately, just call ___setChecked(boolean checked);___ method like CheckBox. And ___slideToChecked(boolean checked);___ method for slow one with slide animation.

When toggle, call ___toggle();___ with change the status immediately and ___toggle(false);___ for slow one.


![easy_to_use](https://raw.githubusercontent.com/kyleduo/SwitchButton/master/preview/easy_to_use_128.png)

***

1.2
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


1.1
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

![demo_preview](https://raw.githubusercontent.com/kyleduo/SwitchButton/master/preview/easy_to_style_128.png)

***


1.0
---
Add an attr of radius, now you can change the radius when configure the button's face!