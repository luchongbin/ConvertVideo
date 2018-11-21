# ConvertVideo
视频压缩工具
## 使用说明  
1、Add the JitPack repository to your build file
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
2、Add the dependency
```
dependencies {
	        implementation 'com.github.luchongbin:ConvertVideo:v1.0.0'
	}
```
3、别忘了添加权限（6.0以上需要动态申请喽）  
```
 <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 ```
4、在你需要的地方使用下流代码
```
/**
     *
     * @param srcPath  输入视频路径
     * @param destPath  输出视频路径
     * @param outputWidth 输出视频宽度
     * @param outputHeight 输出视频高度
     * @param bitrate   视频码率(值也大 文件越大 视频质量越好)
     * @param listener
     * @return
     */
 VideoLCB.convertVideo(srcPath, destPath, 720, 1280, 720 * 1280 * 7, new VideoLCB.ProgressListener() {
                        @Override
                        public void onStart() {
                           

                        }

                        @Override
                        public void onFinish(boolean result) {
                           
                        }


                        @Override
                        public void onProgress(float percent) {
                          
                        }
                    });
``` 

### 最后特别说明 本文参考https://github.com/zolad/VideoSlimmer.git 所改写

## License
```
MIT License
Copyright (c) 2018 卢崇斌
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
