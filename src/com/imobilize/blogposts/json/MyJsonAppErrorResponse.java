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

package com.imobilize.blogposts.json;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class MyJsonAppErrorResponse implements Response.ErrorListener{

	@Override
	public void onErrorResponse(VolleyError error) {
		System.out.println("Response error" + error);	
	}
}
