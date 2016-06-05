package org.whh.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientHelper {
	public static final int GET = 1;
	public static final int POST = 2;

	private static String getOrPost(int type, String url, List<NameValuePair> pairs, String requestParam) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpRequestBase request = null;
		try {
			switch (type) {
			case HttpClientHelper.GET:
				request = new HttpGet(url + "?" + requestParam);
				break;
			case HttpClientHelper.POST:
				request = new HttpPost(url);
				HttpEntity entity = new UrlEncodedFormEntity(pairs, "utf-8");
				((HttpPost) request).setEntity(entity);
				break;
			}
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpclient.execute(request, responseHandler);
			return responseBody;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String get(String url, String data) {
		return getOrPost(HttpClientHelper.GET, url, null, data);
	}

	public static String post(String url, List<NameValuePair> pairs) {
		return getOrPost(HttpClientHelper.POST, url, pairs, null);
	}

	private static byte[] getOrPostGetByte(int type, String url, List<NameValuePair> pairs, String requestParam) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpRequestBase request = null;
		try {
			switch (type) {
			case HttpClientHelper.GET:
				request = new HttpGet(url + "?" + requestParam);
				break;
			case HttpClientHelper.POST:
				request = new HttpPost(url);
				HttpEntity entity = new UrlEncodedFormEntity(pairs, "utf-8");
				((HttpPost) request).setEntity(entity);
				break;
			}
			CloseableHttpResponse response = httpclient.execute(request);
			HttpEntity httpEntity = response.getEntity();
			return IOUtils.toByteArray(httpEntity.getContent());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static byte[] getAndGetByte(String url, List<NameValuePair> pairs) {
		return getOrPostGetByte(HttpClientHelper.GET, url, pairs, null);
	}

	public static byte[] postAndGetByte(String url, List<NameValuePair> pairs) {
		return getOrPostGetByte(HttpClientHelper.POST, url, pairs, null);
	}

	public static String getRequestParam(HashMap<String, String> params) {
		if (params.size() <= 0) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		for (String key : params.keySet()) {
			buffer.append(key + "=");
			buffer.append(params.get(key));
			buffer.append("&");
		}
		return buffer.substring(0, buffer.length() - 1).toString();
	}

	public static String uploadWdFile(String url, List<NameValuePair> pairs, File file) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		try {
			MultipartEntityBuilder buider = MultipartEntityBuilder.create();
			for (NameValuePair nameValuePair : pairs) {
				buider.addTextBody(nameValuePair.getName(), nameValuePair.getValue());
			}
			String name = file.getName();
			if (name.endsWith(".png")) {
				buider.addBinaryBody("media", file, ContentType.create("image/png"), file.getName());
			} else if (name.endsWith(".jpg")) {
				buider.addBinaryBody("media", file, ContentType.create("image/jpeg"), file.getName());
			} else if (name.endsWith(".gif")) {
				buider.addBinaryBody("media", file, ContentType.create("image/gif"), file.getName());
			} else if (name.endsWith("x-bmp")) {
				buider.addBinaryBody("media", file, ContentType.create("image/x-bmp"), file.getName());
			}
			/**
			 * 没有类型则设置为png
			 */
			else {
				buider.addBinaryBody("media", file, ContentType.create("image/png"), file.getName());
			}
			post.setEntity(buider.build());
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpclient.execute(post, responseHandler);
			return responseBody;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
