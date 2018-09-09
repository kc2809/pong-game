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
import com.google.android.gms.ads.*;
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
		adView.setVisibility(View.INVISIBLE);
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

	@Override
	public void setAdViewVisibility(final boolean visible) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (!visible) {
					adView.setVisibility(View.GONE);
					return;
				}
				adView.setVisibility(View.VISIBLE);
			}
		});


	}

	@Override
	public void showToastMessage(final String s) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
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

			@Override
			public void onAdFailedToLoad(int i) {
				adView.loadAd(new AdRequest.Builder().addTestDevice("99001229731084").build());
			}
		});
		adView.loadAd(new AdRequest.Builder().addTestDevice("99001229731084").build());
	}

	@Override
	public void onRewardedVideoAdLoaded() {
		Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRewardedVideoAdOpened() {
	}

	@Override
	public void onRewardedVideoStarted() {
	}

	@Override
	public void onRewardedVideoAdClosed() {
		loadVideoAdmob();
	}

	@Override
	public void onRewarded(RewardItem rewardItem) {
		game.rewardUser();
	}

	@Override
	public void onRewardedVideoAdLeftApplication() {
	}

	@Override
	public void onRewardedVideoAdFailedToLoad(int i) {
		loadVideoAdmob();
	}

	@Override
	public void onRewardedVideoCompleted() {
	}
}
