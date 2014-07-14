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

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.imobilize.blogposts.activities.MainActivity;
import com.imobilize.blogposts.constants.Constants;
import com.imobilize.blogposts.json.ApplicationController;
import com.imobilize.blogposts.json.MyJsonAppErrorResponse;

public class SubscribeFragment extends Fragment implements OnClickListener{

	private View rootView;
	
	private CheckBox music, technology, animals, fashion, nature, travel, beauty, other;
	private Button btnRegister;
	
	private GoogleCloudMessaging gcm;
    private String regid;
    private Context context;
    
    private boolean isSomethingChecked = false;
    private Map<String, CheckBox> checkList;
    private String allCheckedCategories;
    
    private Set<String> checkedCategories;
	
	public SubscribeFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		ActionBar actionBar = getActivity().getActionBar();        
        actionBar.setDisplayShowTitleEnabled(true); 
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		
		rootView = inflater.inflate(R.layout.login, container, false); 		 		
		context = getActivity().getApplicationContext();	
		
		declareCheckBoxes();
		declareRegisterButton();
		
		enableDisableButton();		
		enableDisableCheckBoxes();
		
		return rootView;
	}

	public void declareCheckBoxes(){
		
		music = (CheckBox) rootView.findViewById(R.id.checkbox_music);
		technology = (CheckBox) rootView.findViewById(R.id.checkbox_technology);
		animals = (CheckBox) rootView.findViewById(R.id.checkbox_animals);
		fashion = (CheckBox) rootView.findViewById(R.id.checkbox_fashion);
		nature = (CheckBox) rootView.findViewById(R.id.checkbox_nature);
		travel = (CheckBox) rootView.findViewById(R.id.checkbox_travel);
		beauty = (CheckBox) rootView.findViewById(R.id.checkbox_beauty);
		other = (CheckBox) rootView.findViewById(R.id.checkbox_others);
		
		checkList = new HashMap<String, CheckBox>();
		checkList.put("Music", music);
		checkList.put("Technology", technology);
		checkList.put("Animals", animals);
		checkList.put("Fashion", fashion);
		checkList.put("Nature", nature);
		checkList.put("Travel", travel);
		checkList.put("Beauty", beauty);
		checkList.put("Other", other);
	}
	
	public void enableDisableCheckBoxes(){
		
		final SharedPreferences prefs = getGcmPreferences(context);
        Set<String> checkedCategories = prefs.getStringSet(Constants.CHECKED_CATEGORIES, null);
        CheckBox checkedCheckBox;
        
        if(checkedCategories != null){
        	
        	Iterator<String> iterator = checkedCategories.iterator();
        	while(iterator.hasNext()){
        		
        		checkedCheckBox = findCheckBoxByName(iterator.next());
        		checkedCheckBox.setChecked(true);
        	}
        	
        	for (Entry<String, CheckBox> e : checkList.entrySet()) {
        		CheckBox checkBoxVal = e.getValue();
        		checkBoxVal.setEnabled(false);
        	}
        }
	}
	
	public CheckBox findCheckBoxByName(String name){
		CheckBox checkBox;
		
		switch (name) {
		case "Music":
			checkBox = music;
			break;
		case "Technology":
			checkBox = technology;
			break;
		case "Animals":
			checkBox = animals;		
			break;
		case "Fashion":
			checkBox = fashion;
			break;
		case "Nature":
			checkBox = nature;
			break;
		case "Travel":
			checkBox = travel;		
			break;
		case "Beauty":
			checkBox = beauty;
			break;
		default:
			checkBox = other;
			break;
		}
		
		return checkBox;
	}
	
	public void declareRegisterButton(){
		
		btnRegister = (Button) rootView.findViewById(R.id.btnRegister);	
		btnRegister.setOnClickListener(this);
	}
	
	public void enableDisableButton(){
		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(context);
			regid = getRegistrationId(context);
			
			if (!regid.isEmpty()) {
				btnRegister.setEnabled(false);

			} else {
				btnRegister.setEnabled(true);
			}
		}
	}
	
	public void onClick(View v) { 
		
		getCheckedCategories();
				
		prepareForRegistration();
	}
	
	
	public void prepareForRegistration(){
		
		if(isSomethingChecked){ 
			if (checkPlayServices()) {
				gcm = GoogleCloudMessaging.getInstance(context);
				regid = getRegistrationId(context);
	
				if (regid.isEmpty()) {
					btnRegister.setEnabled(false);
					
					 registerInBackground();
					 
				} else {
					Toast.makeText(context, "Device already Registered", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(context, "You don't have valid Google Play Services", Toast.LENGTH_LONG).show();
				Log.i(Constants.TAG, "No valid Google Play Services APK found.");
			}
		}
		else{
			Toast.makeText(context, "You should select one or more categories", Toast.LENGTH_LONG).show();
		}
	}
	
	 private boolean checkPlayServices() {
	        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
	        
	        if (resultCode != ConnectionResult.SUCCESS) {
	            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), Constants.PLAY_SERVICES_RESOLUTION_REQUEST).show();
	                
	            } else {
	                Log.i(Constants.TAG, "This device is not supported.");
	                getActivity().finish();
	            }
	            return false;
	        }
	        return true;
	    }
	

	 private void registerInBackground() {
	        new AsyncTask<Void, Void, String>() {
	            @Override
	            protected String doInBackground(Void... params) {
	                String msg = "";
	                try {
	                    if (gcm == null) {
	                        gcm = GoogleCloudMessaging.getInstance(context);
	                    }
	                    regid = gcm.register(Constants.SENDER_ID);
	                    msg = "Device registered, registration ID=" + regid;

	                    sendRegistrationIdToBackend();

	                   storeRegistrationId(context, regid);	                   	                   
		        		
	                } catch (IOException ex) {
	                    msg = "Error :" + ex.getMessage();
	                }
	                return msg;
	            }

	            @Override
	            protected void onPostExecute(String result) {	            	
	            	super.onPostExecute(result);
	            	Toast.makeText(context, "Registration Completed. Now you can see the notifications", Toast.LENGTH_SHORT).show();
	        		Log.v(Constants.TAG, result);
	            }
	        }.execute(null, null, null);
	    }
	 

	 
	 private String getRegistrationId(Context context) {
	        final SharedPreferences prefs = getGcmPreferences(context);
	        String registrationId = prefs.getString(Constants.PROPERTY_REG_ID, "");
	        
	        if (registrationId.equals("")) {
	            Log.i(Constants.TAG, "Registration not found.");
	            return "";
	        }
	        
	        int registeredVersion = prefs.getInt(Constants.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	        int currentVersion = getAppVersion(context);
	        if (registeredVersion != currentVersion) {
	            Log.i(Constants.TAG, "App version changed.");
	            return "";
	        }
	        return registrationId;
	    }
	
	 private static int getAppVersion(Context context) {
	        try {
	            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
	            return packageInfo.versionCode;
	            
	        } catch (NameNotFoundException e) {
	            // should never happen
	            throw new RuntimeException("Could not get package name: " + e);
	        }
	    }

	 
	    private SharedPreferences getGcmPreferences(Context context) {
	        return getActivity().getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	    }

		
		 private void sendRegistrationIdToBackend() {
 	    	
		    	String url = Constants.blogHostAndPort + "GCMRegistration?regId=" + regid + "&categories=" + allCheckedCategories;
		    	
		    	JsonObjectRequest registrationIdRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {		    	
		    		 
					@Override
					public void onResponse(JSONObject response) {
						Log.d("Response", response.toString());					
					}
	    	    }, new MyJsonAppErrorResponse());
		    		
		    	ApplicationController.getInstance().addToRequestQueue(registrationIdRequest);
		    }    		
		 
		 
		 private void storeRegistrationId(Context context, String regId) {
		    	
		        final SharedPreferences prefs = getGcmPreferences(context);
		        int appVersion = getAppVersion(context);
		        
		        Log.i(Constants.TAG, "Saving regId on app version " + appVersion);
		        SharedPreferences.Editor editor = prefs.edit();
		        
		        editor.putString(Constants.PROPERTY_REG_ID, regId);
		        editor.putInt(Constants.PROPERTY_APP_VERSION, appVersion);
		        editor.putStringSet(Constants.CHECKED_CATEGORIES, checkedCategories); //
		        editor.commit();
		    }
		 
		 
		 protected void onFragmentResume() {
		        super.onResume();

		        checkPlayServices();
		 }		
		 
		 public void getCheckedCategories(){
			 
			 	 checkedCategories = new LinkedHashSet<String>();
			 
				 StringBuilder allCheckedCategoriesTmp = new StringBuilder();				 
				
				 String key;
				 CheckBox value;
				 
				 for (Entry<String, CheckBox> e : checkList.entrySet()) {
					    key = e.getKey();
					    value = e.getValue();
					    
					    if(value.isChecked()){
					    	
					    	checkedCategories.add(key);
					    	allCheckedCategoriesTmp.append(key).append(',');
					    	isSomethingChecked = true;
					    	
					    	value.setEnabled(false);
					    }
					}
				 
				 this.allCheckedCategories = allCheckedCategoriesTmp.toString();
			 }		 
}
