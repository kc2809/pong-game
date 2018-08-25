package com.mygdx.game;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.mygdx.game.MyGdxGame.AdmodCallBack;

public class AndroidLauncher extends AndroidApplication implements AdmodCallBack, RewardedVideoAdListener {

	protected AdView adView;
	protected InterstitialAd interstitialAd;

	protected RewardedVideoAd rewardedVideoAd;
	protected MyGdxGame game;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//		initialize(new MyGdxGame(), config);

		// Do the stuff that initialize do
		//full-screen, removing the title..
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);


//		MobileAds.initialize(this,
//				"ca-app-pub-3940256099942544~3347511713");

		//setup adview
		setUpBanner();
		setupInterestAd();

		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.CENTER_IN_PARENT);

		RelativeLayout layout = new RelativeLayout(this);
		game = new MyGdxGame();
		game.setAdmobCallBack(this);
		View gameView = initializeForView(game, config);

		layout.addView(gameView);
		layout.addView(adView, adParams);

		//setup video admob
		rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
		rewardedVideoAd.setRewardedVideoAdListener(this);

		loadVideoAdmob();

		//hook it all up
		setContentView(layout);
	}

	private void loadVideoAdmob(){
		rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
				new AdRequest.Builder().build());
	}

	@Override
	public void callAdmobBanner() {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (interstitialAd.isLoaded()) {
					interstitialAd.show();
				}
			}
		});
	}

	@Override
	public void callVideo() {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (rewardedVideoAd.isLoaded()) {
					rewardedVideoAd.show();
				}
			}
		});
	}

	private void setupInterestAd() {
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
		interstitialAd.loadAd(new AdRequest.Builder()
				.addTestDevice("99001229731084").build());
		interstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				interstitialAd.loadAd(new AdRequest.Builder()
						.addTestDevice("99001229731084")
						.build());
			}
		});
	}

	private void setUpBanner() {
		adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111"); // secret key here
		adView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				Log.i("WTF", "is this shit");
			}
		});
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice("99001229731084")
				.build();
		adView.loadAd(adRequest);
	}

	@Override
	public void onRewardedVideoAdLoaded() {
		Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onRewardedVideoAdOpened() {
		Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRewardedVideoStarted() {
		Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRewardedVideoAdClosed() {
		Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
		loadVideoAdmob();
	}

	@Override
	public void onRewarded(RewardItem rewardItem) {
		Toast.makeText(this, "onRewarded! currency: " , Toast.LENGTH_LONG).show();
		System.out.println("THUONG TIEN NE MAY");
		game.rewardUser();
		// Reward the user.
	}

	@Override
	public void onRewardedVideoAdLeftApplication() {
		Toast.makeText(this, "onRewardedVideoAdLeftApplication",
				Toast.LENGTH_SHORT).show();
		System.out.println("LEFT");
	}

	@Override
	public void onRewardedVideoAdFailedToLoad(int i) {
		Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRewardedVideoCompleted() {
		Toast.makeText(this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
	}
}
