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

package com.imobilize.blogposts.articles;

import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
//import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.imobilize.blogposts.activities.ArticleActivity;
import com.imobilize.blogposts.adapters.ArticlesArrayAdapter;
import com.imobilize.blogposts.adapters.ArticlesArrayAdapter.ViewHolder;
import com.imobilize.blogposts.constants.Constants;
import com.imobilize.blogposts.json.ApplicationController;

public class ArticlesManager {
	
	private static ArticlesManager articleManagerInstance;
	

	public static ArticlesManager getInstance(){
		if(articleManagerInstance == null) {
			articleManagerInstance = new ArticlesManager();
		}
		return articleManagerInstance;
	}
	

	public <T> void loadArticlesTo(Context articlesContext, ListView articlesListView, String selectedCategory){
		String urlJsonData = Constants.blogHostAndPort + "articlesToJson?category=" + selectedCategory;
		JsonArrayRequest articlesJsonRequest = new JsonArrayRequest(urlJsonData, listener(articlesContext, articlesListView), errorListener());
		ApplicationController.getInstance().addToRequestQueue(articlesJsonRequest);
	}
	
	
	private Listener<JSONArray> listener(final Context articlesContext, final ListView articlesListView) {
		return new Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				articlesListView.setAdapter(new ArticlesArrayAdapter(articlesContext, response));
			}
		};
	}
	
	
	private Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
			}
		};
	}
	
	
	public OnItemClickListener onArticleClickListener(final Context articlesContext){
		return new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ViewHolder holder = (ViewHolder)view.getTag();
				if(holder.getArticleTitle() != null){
					loadSingleArticleTo(articlesContext, holder);
				}
			}
		};
	}
	
	
	private <T> void loadSingleArticleTo(Context context, ViewHolder holder){
		Intent intentForArticleActivity = new Intent(context, ArticleActivity.class);	
		intentForArticleActivity.putExtra(Constants.ARTICLE_TITLE, holder.getArticleTitle().getText());
		intentForArticleActivity.putExtra(Constants.ARTICLE_METADATA, holder.getArticleMetadata().getText());
		intentForArticleActivity.putExtra(Constants.ARTICLE_CONTENT, holder.getArticleContent());
		intentForArticleActivity.putExtra(Constants.ARTICLE_KEY, holder.getArticleKey());
		context.startActivity(intentForArticleActivity);
	}

}
