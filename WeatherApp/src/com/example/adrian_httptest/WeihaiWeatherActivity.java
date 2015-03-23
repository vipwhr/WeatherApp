package com.example.adrian_httptest;

import java.io.IOException;
import java.sql.Date;

import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WeihaiWeatherActivity extends Activity {

	Button button;
	TextView textView;
	RequestQueue requestQueue;
	JsonObjectRequest jsonObjectRequest;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		textView = (TextView)findViewById(R.id.textView1);
		button = (Button)findViewById(R.id.buttonWeather);
		requestQueue = Volley.newRequestQueue(this);
		
		jsonObjectRequest = new JsonObjectRequest("http://www.weather.com.cn/data/cityinfo/101121301.html", null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						ObjectMapper mapper = new ObjectMapper();
						Weather weather = new Weather();
						String resString = response.toString();
						
						
						try {
							weather = (Weather)mapper.readValue(resString, Weather.class);
							textView.setText("城市： "+weather.weatherinfo.getCity()+
									"\r\n城市编号： "+ weather.weatherinfo.getCityid() +
									"\r\n最高温： "+ weather.weatherinfo.getTemp1() +
									"\r\n最低温： " + weather.weatherinfo.getTemp2());
						} catch (JsonParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JsonMappingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
					}
				}, 
				new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						textView.setText(error.toString());
					}
				});
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				requestQueue.add(jsonObjectRequest);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weihai_weather, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public Location getLocation() {// 获取Location通过LocationManger获取！
		  LocationManager locManger = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		  Location loc = locManger.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		  if (loc == null) {
		   loc = locManger.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		  }
		  return loc;
		 }
}
