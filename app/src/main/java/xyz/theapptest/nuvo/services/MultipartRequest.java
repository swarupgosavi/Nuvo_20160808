package xyz.theapptest.nuvo.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;

public class MultipartRequest extends Request<String> {

	private MultipartEntity entity = new MultipartEntity();

	private HashMap<String, File> mFileUploads = null;
	private HashMap<String, String> mDtata = null;
	private final Response.Listener<String> mListener;

	public MultipartRequest(String url, Response.ErrorListener errorListener, Response.Listener<String> listener, HashMap<String, String> mDtata, HashMap<String, File> mFileUploads)
	{
		super(Method.POST, url, errorListener);
		this.mFileUploads = mFileUploads;
		this.mDtata = mDtata;
		mListener = listener;
		buildMultipartEntity();
	}

	private void buildMultipartEntity()
	{
		for ( String key : mFileUploads.keySet() ) {
			entity.addPart(key, new FileBody(mFileUploads.get(key), "image/jpeg"));
		}
		try
		{
			for ( String key : mDtata.keySet() ) {
				entity.addPart(key, new StringBody(mDtata.get(key)));
			}
		}
		catch (UnsupportedEncodingException e)
		{
			VolleyLog.e("UnsupportedEncodingException");
		}
	}

	@Override
	public String getBodyContentType()
	{
		return entity.getContentType().getValue();
	}

	@Override
	public byte[] getBody() throws AuthFailureError
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try
		{
			entity.writeTo(bos);
		}
		catch (IOException e)
		{
			VolleyLog.e("IOException writing to ByteArrayOutputStream");
		}
		return bos.toByteArray();
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response)
	{
		String jsonString = new String(response.data);
		return Response.success(jsonString.toString(), getCacheEntry());
	}

	@Override
	protected void deliverResponse(String response)
	{
		mListener.onResponse(response);
	}
}