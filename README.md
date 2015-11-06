SwitchButton
============

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SwitchButton-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1119)

**To get a quick preview, you can find the Demo in [Google Play](https://play.google.com/store/apps/details?id=com.kyleduo.switchbutton.demo) or [My Blog](http://kyleduo.com/project/switchbutton/switchbutton_demo_130.apk).**

This project provides you a convenient way to use and customise a SwitchButton widget in Android. With just resources changed and attrs set, you can create a lifelike SwitchButton of Android 5.0+, iOS, MIUI, or Flyme and so on.

Now we get the biggest movement since SwitchButton published. v1.3.0 comes with totally reconsitution and more convenient API. A wholly new demo can give you a tour in it.

***
Change Log
---
[Change Log 1.3.0](https://github.com/kyleduo/SwitchButton/blob/master/CHANGELOG.md)

Using SwitchButton in your application
---
~~ADT~~

	~~Clone the project and use the -adt part as library~~
	No more support
	
__Gradle__
	
Add dependencies in build.gradle of your module

	dependencies {
    	compile 'com.kyleduo.switchbutton:library:1.3.0'
	}
***
Demo
---
I create a new demo apk to show you how to style the cute widget and use it. There's some screenshots of the new demo.

![demo_preview](https://raw.githubusercontent.com/kyleduo/SwitchButton/master/preview/demo_130.jpg)

***
Usage
---

In 1.3.0, I updated the usage of __SwitchButton__ library. To make it more Android way to use, I've combined the thumb and back style each to StateListColor/StateListDrawable. So you are free to create styles in different states.

In __xml__ layout file, you can configure the face of switch button using these attrs.

*   __kswThumbDrawable__: drawable for thumb
*   __kswThumbColor__: color for thumb
*   __kswThumbMargin__: margin from thumb to back, can be negative. maybe cover by single direction margins
*   __kswThumbMarginTop__: same to __kswThumbMargin__, just top
*   __kswThumbMarginBottom__: same to __kswThumbMargin__, just bottom
*   __kswThumbMarginLeft__: same to __kswThumbMargin__, just left
*   __kswThumbMarginRight__: same to __kswThumbMargin__, just right
*   __kswThumbWidth__: width of thumb
*   __kswThumbHeight__: height of thumb
*   __kswThumbRadius__: radius of thumb rect, only work with __kswThumbColor__
*   __kswBackRadius__: radius of background rect, only work with __kswBackColor__
*   __kswBackDrawable__: drawable for background
*   __kswBackColor__: color for background
*   __kswFadeBack__: fade background color/drawable when drag or animate between on/off status or not
*   __kswBackMeasureRatio__: decide width of background from thumb's, this is the ratio
*   __kswAnimationDuration__: duration of animation between 2 status
*   __kswTintColor__: change SwitchButton's style just by __one__ property, all relevant color will be generate automatically.

You can alse change the configuration of SwitchButton ___in code___. You can find the api from Demo apk. There's a glance.

```
private String[] opts = new String[]{
			"setThumbColorRes/setThumbColor",
			"setThumbDrawableRes/setThumbDrawable",
			"setBackColorRes/setBackColor",
			"setBackDrawableRes/setBackDrawable",
			"setTintColor",
			"setThumbMargin",
			"setThumbSize",
			"setThumbRadius (color-mode only)",
			"setBackRadius (color-mode only)",
			"setFadeBack",
			"setBackMeasureRatio",
			"setAnimationDuration",
			"setDrawDebugRect",
	};
```

License
---

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.