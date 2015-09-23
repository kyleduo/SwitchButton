SwitchButton
============

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SwitchButton-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1119)

**To get a quick preview, you can find the Demo in [Google Play](https://play.google.com/store/apps/details?id=com.kyleduo.switchbutton.demo) or [My Blog](http://kyleduo.com/apk/switchbutton_128.apk).**

This project provides you a convient way to customise a SwitchButton widget in Android. With just resources changed and attrs set, you can get a lifelike SwitchButton in Android 5.0, iOS 7/8, MIUI, or Flyme and so on.

What are you waiting for, come to enjoy this widget.

***
Using SwitchButton in your application
---
__ADT__

	Clone the project and use the -adt part as library
	
__Gradle__
	
Add dependencies in build.gradle of your module

	dependencies {
    	compile 'com.kyleduo.switchbutton:library:1.2.10'
	}
***
Demo
---
Default Style

![default_style](https://raw.githubusercontent.com/kyleduo/SwitchButton/master/preview/default_style.png)

Material Style

![materialdesign_style](https://raw.githubusercontent.com/kyleduo/SwitchButton/master/preview/switchbutton_md.jpg)

Demo apk:

![demo_preview](https://raw.githubusercontent.com/kyleduo/SwitchButton/master/preview/easy_to_style_128.png)

![easy_to_use](https://raw.githubusercontent.com/kyleduo/SwitchButton/master/preview/easy_to_use_128.png)
***
Usage
---

In ___xml___ layout file, you can configure the face of switch button using these attrs.

(Update from 1.2.10)

*   __kswOnDrawable__: drawable of background for status ON
*   __kswOffDrawable__: drawable of background for status OFF
*   __kswThumbDrawable__: drawable of thumb
*   __kswThumb_margin__: set inner margin between thumb and edges
*   __kswThumb_marginLeft/Top/Bottom/Right__: set margin for specific edge
*   __kswThumb_width__: set the width of thumb, probably used for gradient drawable
*   __kswThumb_height__: set the height of thumb
*   __kswOnColor__: set the color of status ON, usd for flat version, the priority is below of onDrawable
*   __kswOffColor__: like the onColor
*   __kswThumbColor__: like the onColor
*   __kswThumbPressedColor__: like the thumbColor, but for pressed status
*   __kswAnimationVelocity__: distance of animation per frame
*   __kswRadius__: used for color version, radius of corner of background and thumb.
*   __kswMeasureFactor__: factor limit the minimum width equals almost (the height of thumb * measureFactor)
*   __kswInsetLeft__: left size for shrinking (1.2)
*   __kswInsetRight__: right size for shrinking (1.2)
*   __kswInsetTop__: top size for shrinking (1.2)
*   __kswInsetBottom__: bottom size for shrinking (1.2)

You can alse change the configuration of SwitchButton ___in code___ using class __Configuration__. The attributes in xml each has a setter and you can use them. Call method __setConfiguration(Configuration conf);__ of SwitchButton after that.

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