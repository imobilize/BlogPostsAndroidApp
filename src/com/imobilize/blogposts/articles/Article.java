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

import org.json.JSONException;
import org.json.JSONObject;

public class Article {
	
	private String author;
	private String title;
	private String description;
	private String content;
	private String creationDate;
	private String key;

	public Article(JSONObject articleJson){
		
		try {
			this.author = articleJson.getString("author") ;
			this.title = articleJson.getString("title");
			this.description = articleJson.getString("description");
			this.content = articleJson.getString("content");
			this.creationDate = articleJson.getString("creationDate");
			this.key = articleJson.getString("key");
		} catch (JSONException error) {
			error.printStackTrace();
		}
		
	}

	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}

	public String getContent() {
		return content;
	}

	public String getCreationDate() {
		return creationDate;
	}
	
	public String getKey() {
		return key;
	}

	public String getAuthor() {
		return author;
	}

}
