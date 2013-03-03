package com.github.arturh.justnotes;

import com.activeandroid.ActiveAndroid;

import android.app.Application;

public class JustNotesApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		
		ActiveAndroid.initialize(this, true);
	}
}
