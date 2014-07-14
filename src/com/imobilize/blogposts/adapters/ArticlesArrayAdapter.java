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

package com.imobilize.blogposts.adapters;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.imobilize.blogposts.articles.Article;
import com.imobilize.blogposts.cachemanagers.ImageCacheManager;
import com.imobilize.blogposts.constants.Constants;

import info.androidhive.slidingmenu.R;

public class ArticlesArrayAdapter extends ArrayAdapter<String> {

	
	public List<Article> articles = new ArrayList<Article>();
		

	public ArticlesArrayAdapter(Context context, JSONArray response) {
		super(context, R.layout.listview_layout);
		
		for(int i = 0; i < response.length(); i++){
			try {
				articles.add(new Article(response.getJSONObject(i)));
			}
			catch (JSONException e) {
	             e.printStackTrace();
	        }
		}
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		ViewHolder viewHolder;


		if(v == null){
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.listview_layout, null);

			viewHolder = new ViewHolder();
			viewHolder.articleImage = (NetworkImageView)v.findViewById(R.id.articleImage);
			viewHolder.articleTitle = (TextView)v.findViewById(R.id.articleTitle);
			viewHolder.articleDescription = (TextView)v.findViewById(R.id.articleDescription);
			viewHolder.articleMetadata = (TextView)v.findViewById(R.id.articleMetadata);

			v.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) v.getTag();
		}

		Article article = articles.get(position);
		if(article != null){
			viewHolder.articleImage.setImageUrl(Constants.blogHostAndPort + Constants.blogURLFirstPart + article.getKey(), ImageCacheManager.getInstance().getImageLoader());
			viewHolder.articleTitle.setText(article.getTitle());
			viewHolder.articleDescription.setText(article.getDescription());
			viewHolder.articleMetadata.setText("Posted by " + article.getAuthor() + " on "  + article.getCreationDate());
			viewHolder.articleContent = article.getContent();
			viewHolder.articleKey = article.getKey();
		}

		return v;
	}
	

	@Override
	public int getCount() {
		return articles.size();
	}


	public static class ViewHolder{
		private NetworkImageView articleImage;
		private TextView articleTitle;
		private TextView articleDescription;
		private TextView articleMetadata;
		private String articleContent;
		private String articleKey;
		
		public NetworkImageView getArticleImage() {
			return articleImage;
		}

		public TextView getArticleTitle() {
			return articleTitle;
		}

		public TextView getArticleDescription() {
			return articleDescription;
		}

		public TextView getArticleMetadata() {
			return articleMetadata;
		}

		public String getArticleContent() {
			return articleContent;
		}

		public String getArticleKey() {
			return articleKey;
		}		
	}
}
