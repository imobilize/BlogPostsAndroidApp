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

package com.imobilize.blogposts.activities;

import info.androidhive.slidingmenu.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.imobilize.blogposts.cachemanagers.ImageCacheManager;
import com.imobilize.blogposts.constants.Constants;

public class ArticleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		
		Intent articleIntent = getIntent();
		
		getActionBar().setTitle(articleIntent.getStringExtra(Constants.ARTICLE_TITLE));
		getActionBar().setHomeButtonEnabled(true);
		
		TextView articleTitle = (TextView) findViewById(R.id.articleTitle);
		TextView articleMetadata = (TextView) findViewById(R.id.articleMetadata);
		TextView articleContent = (TextView) findViewById(R.id.articleContent);
		NetworkImageView articleImage = (NetworkImageView) findViewById(R.id.articleImage);
		
        articleTitle.setText(articleIntent.getStringExtra(Constants.ARTICLE_TITLE));
        articleMetadata.setText(articleIntent.getStringExtra(Constants.ARTICLE_METADATA));
        articleContent.setText(Html.fromHtml(articleIntent.getStringExtra(Constants.ARTICLE_CONTENT)));
        articleImage.setImageUrl(Constants.blogHostAndPort + Constants.blogURLFirstPart + articleIntent.getStringExtra(Constants.ARTICLE_KEY), ImageCacheManager.getInstance().getImageLoader());
	}
	
}
