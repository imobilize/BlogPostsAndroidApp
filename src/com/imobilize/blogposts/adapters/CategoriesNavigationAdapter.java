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

import com.imobilize.blogposts.model.SpinnerNavItem;

import info.androidhive.slidingmenu.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
	 
	public class CategoriesNavigationAdapter extends BaseAdapter {
	 
	    private ImageView imgIcon;
	    private TextView txtTitle;
	    private ArrayList<SpinnerNavItem> spinnerNavItem;
	    private Context context;
	 
	    public CategoriesNavigationAdapter(Context context, ArrayList<SpinnerNavItem> spinnerNavItem) {
	        this.spinnerNavItem = spinnerNavItem;
	        this.context = context;
	    }
	 
	    @Override
	    public int getCount() {
	        return spinnerNavItem.size();
	    }
	 
	    @Override
	    public Object getItem(int index) {
	        return spinnerNavItem.get(index);
	    }
	 
	    @Override
	    public long getItemId(int position) {
	        return position;
	    }
	 
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	
	        if (convertView == null) {
	            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	            convertView = mInflater.inflate(R.layout.list_categories_navigation, null);
	        }
	         
	        imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
	        txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
	         
	        imgIcon.setImageResource(spinnerNavItem.get(position).getIcon());
	        imgIcon.setVisibility(View.GONE);
	        txtTitle.setText(spinnerNavItem.get(position).getTitle());
	        
	        return convertView;
	    }
	     
	 
	    @Override
	    public View getDropDownView(int position, View convertView, ViewGroup parent) {
	    	
	        if (convertView == null) {
	            LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	            convertView = mInflater.inflate(R.layout.list_categories_navigation, null);
	        }
	         
	        imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
	        txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
	         
	        imgIcon.setImageResource(spinnerNavItem.get(position).getIcon());       
	        txtTitle.setText(spinnerNavItem.get(position).getTitle());
	        return convertView;
	    }
	 
	}