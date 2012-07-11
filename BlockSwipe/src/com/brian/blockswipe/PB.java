package com.brian.blockswipe;

import android.content.Context;
import android.content.SharedPreferences;

public class PB {

	public static String pB = "PB";
	SharedPreferences personalBests;

	Context myContext;
	private Context cont;

	public PB(Context context) {
		cont = context;
		personalBests = context.getSharedPreferences(pB, 0);
		
	}

	public void save(int level, int score) {

		personalBests = cont.getSharedPreferences(pB, 0);
		int best = personalBests.getInt("Level" + level, 1000);
		int levelcomp = personalBests.getInt("levelComp", -1);

		if (score < best) {
			SharedPreferences.Editor editor = personalBests.edit();
			editor.putInt("Level" + level, score);
			editor.commit();
		}
		if (level > levelcomp){
			SharedPreferences.Editor editor = personalBests.edit();
			editor.putInt("levelComp", level);
			editor.commit();
		}
	}

	public int loadPb(int level) {
		personalBests = cont.getSharedPreferences(pB, 0);
		return personalBests.getInt("Level" + level, 1000);
	}
	
	public int loadLevelComp(){
		personalBests = cont.getSharedPreferences(pB, 0);
		return personalBests.getInt("levelComp", 0);
	}
}
