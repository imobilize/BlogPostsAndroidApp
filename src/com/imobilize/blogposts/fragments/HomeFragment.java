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

package com.imobilize.blogposts.fragments;

import info.androidhive.slidingmenu.R;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.imobilize.blogposts.adapters.CategoriesNavigationAdapter;
import com.imobilize.blogposts.articles.ArticlesManager;
import com.imobilize.blogposts.model.SpinnerNavItem;

public class HomeFragment extends Fragment implements ActionBar.OnNavigationListener{
	
	public HomeFragment(){}
	
	private View rootView;
	private ListView articlesListView;
	
    private ActionBar actionBar;
 
    private ArrayList<SpinnerNavItem> navSpinner;    
    private CategoriesNavigationAdapter categoriesAdapter;
    
    private String selectedCategory;
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
        rootView = inflater.inflate(R.layout.main_layout, container, false);  
        articlesListView = (ListView)rootView.findViewById(R.id.listview);
        
		createCategoriesDropDownMenu();               
		loadArticles();
		
        return rootView;
    }
	
	
	public void loadArticles(){
		
		 if(articlesListView.getAdapter() == null) {
				ArticlesManager.getInstance().loadArticlesTo(getActivity(), articlesListView, selectedCategory); //container.getContext()
			}
			articlesListView.setOnItemClickListener(ArticlesManager.getInstance().onArticleClickListener(getActivity()));
	}
	
	
	public void createCategoriesDropDownMenu(){
		actionBar = getActivity().getActionBar();
        
        actionBar.setDisplayShowTitleEnabled(true);
 
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
             
        addCategoriesTitles();
        
        categoriesAdapter = new CategoriesNavigationAdapter(getActivity().getApplicationContext(), navSpinner);
  
        actionBar.setListNavigationCallbacks(categoriesAdapter, this);		
	}
	
	
	public void addCategoriesTitles(){
		
		navSpinner = new ArrayList<SpinnerNavItem>();
        navSpinner.add(new SpinnerNavItem("Categories", R.drawable.all_categories));
        navSpinner.add(new SpinnerNavItem("Music", R.drawable.music));
        navSpinner.add(new SpinnerNavItem("Technology", R.drawable.technology));
        navSpinner.add(new SpinnerNavItem("Animals", R.drawable.animals)); 
        navSpinner.add(new SpinnerNavItem("Fashion", R.drawable.fashion));
        navSpinner.add(new SpinnerNavItem("Nature", R.drawable.nature));
        navSpinner.add(new SpinnerNavItem("Travel", R.drawable.travel));
        navSpinner.add(new SpinnerNavItem("Beauty", R.drawable.beauty));
        navSpinner.add(new SpinnerNavItem("Other", R.drawable.others));
	}
	
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		switch (itemPosition) {
        case 0:
        	selectedCategory = "All";
        	loadArticlesByCategory();
            return true;
        case 1:
        	selectedCategory = "Music";
        	loadArticlesByCategory();
            return true;
        case 2:
        	selectedCategory = "Technology";
        	loadArticlesByCategory();
            return true;
        case 3:
        	selectedCategory = "Animals";
        	loadArticlesByCategory();
            return true;
        case 4:
        	selectedCategory = "Fashion";
        	loadArticlesByCategory();
            return true;
        case 5:
        	selectedCategory = "Nature";
        	loadArticlesByCategory();
            return true;
        case 6:
        	selectedCategory = "Travel";
        	loadArticlesByCategory();
            return true;
        case 7:
        	selectedCategory = "Beauty";
        	loadArticlesByCategory();
            return true;
        case 8:
        	selectedCategory = "Other";
        	loadArticlesByCategory();
            return true;
            
        default:
            return false;
		}
	}
	
	public void loadArticlesByCategory(){
		ArticlesManager.getInstance().loadArticlesTo(getActivity(), articlesListView, selectedCategory);
		articlesListView.setOnItemClickListener(ArticlesManager.getInstance().onArticleClickListener(getActivity()));
	}

}
