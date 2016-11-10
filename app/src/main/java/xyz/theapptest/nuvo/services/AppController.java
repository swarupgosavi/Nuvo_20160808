package xyz.theapptest.nuvo.services;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
/*
 * @author Sunny
 * Appcontoller to create a singleton volley queue and other required methods 
 * */
public class AppController extends Application {

	public static final String TAG = AppController.class.getSimpleName();
	private RequestQueue mRequestQueue;  // volley request Queue
	private ImageLoader mImageLoader;	//Volley ImageLoader

	private static AppController mInstance; 

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}

	public static synchronized AppController getInstance() {
		return mInstance;
	}

	
	/*
	 * Get volley request queue
	 * */
	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}
		return mRequestQueue;
	}

	
	/*
	 * GEt ImageLoader Instance
	 * 
	 * */
	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new LruBitmapCache());
		}
		return this.mImageLoader;
	}

	/*
	 * Add request to volley queue with tag
	 * @param request
	 * @param tag to request
	 * */
	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}
	
	/*
	 * Add request to volley queue without tag
	 * @param request
	 * */
	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}
	
	
	/*
	 * Cancel particular request from volley queue
	 * @param request tag to cancnel
	 * */
	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
	
}
