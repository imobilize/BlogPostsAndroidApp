/**
* Copyright 2014 iMobilize Ltd.
*
* Licensed under the Apache License, Version 2.0 (the "License"); you may
* not use this file except in compliance with the License. You may obtain
* a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
* WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
* License for the specific language governing permissions and limitations
* under the License.
*/

package com.imobilize.blogposts.cachemanagers;

import com.android.volley.toolbox.ImageLoader.ImageCache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

public class BitmapLruImageCache extends LruCache<String, Bitmap> implements ImageCache{
	
	private final String TAG = this.getClass().getSimpleName();
	
	public BitmapLruImageCache(int maxSize) {
		super(maxSize);
	}
	
	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight();
	}
	
	@Override
	public Bitmap getBitmap(String url) {
		Log.v(TAG, "Retrieved item from Mem Cache");
		return get(url);
	}
 
	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		Log.v(TAG, "Added item to Mem Cache");
		put(url, bitmap);
	}
}
