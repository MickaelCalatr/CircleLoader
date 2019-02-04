# CircleLoader
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/3000e9f53be34ab694ac1bb36d90d695)](https://app.codacy.com/app/MickaelCalatr/CircleLoader?utm_source=github.com&utm_medium=referral&utm_content=MickaelCalatr/CircleLoader&utm_campaign=Badge_Grade_Settings)
[![](https://jitpack.io/v/MickaelCalatr/CircleLoading.svg)](https://jitpack.io/#MickaelCalatr/CircleLoading)

This is a simple circle loader library for android API 21+.

I needed to create a circle progress bar.
This is how it looks in standard mode but you can configure it as you want.

![](assets/demo_loader.gif)

## Dependency
You can copy the CircleLoader.java (in the library module) and the attrs.xml
content into your project. Or you can get the binaries from Maven central by
adding in your build.gradle dependencies:

### Step 1

```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

### Step 2
```gradle
dependencies {
        implementation 'com.github.MickaelCalatr:CircleLoading:1.0'
}
```
## Usage
You can create your own CircleLoader in xml like this;

```xml
    <com.mickael.circleloading.CircleLoaderView
        android:id="@+id/loading_circle"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:background="@color/color_loader"
        app:background_alpha="0.7"
        app:loader_height="200dp"
        app:loader_width="200dp"
        app:loader_color="#fff"/>
```

To start the loading you can use:
```java
CircleLoaderView loadingView = (CircleLoaderView) findViewById(R.id.loading_circle);
loadingView.startLoading();
```
 and to stop the loading use:
```java
loadingView.stopLoading();
```

You can also change all variables programmatically using getter and setter.
```java
loadingView.setBackgroundAlpha(float backgroundAlpha)

loadingView.setBackground(int color)

loadingView.setRotateDuration(int rotateDuration)

loadingView.setStrokeWidth(int strokeSize)
```

## Other options
In the xml definition, besides the property, you can set:

-   loader_width (dimension) set the loader width
-   loader_height (dimension) set the loader height
-   background_color (color) set a color to the background
-   background_alpha (float) set the alpha maximum to the alpha transition (0 to don't use it)
-   rotate_duration (integer) set the rotation duration
-   loading_stroke_width (integer) set the width of the strokes
-   loading_color (color) set the color of th loader (by default it's white)

## Version

-   1.0 Initial release

## Special thanks
The project of [Yankai-victor](https://github.com/yankai-victor/Loading) help me
so much to create my library, thanks to his and his work !

## License

```license
The MIT License (MIT)

Copyright (c) 2019 Calatraba Mickael

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
