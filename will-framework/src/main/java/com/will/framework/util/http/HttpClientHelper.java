package com.will.framework.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

/**
 * <p>
 * Description: Http客户端辅助类
 * </p>
 * 
 * @author will
 */
public class HttpClientHelper {
	private static Log log = LogFactory.getLog(HttpClientHelper.class);
	private List<NameValuePair> paramList = new ArrayList<NameValuePair>();

	/**
	 * @Description： TODO
	 */
	public HttpClientHelper() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 添加请求参数
	 * 
	 * @param name
	 *            参数名
	 * @param value
	 *            参数值
	 * @return {@link HttpClientHelper}
	 */
	public HttpClientHelper addParameter(String name, String value) {
		paramList.add(new BasicNameValuePair(name, value));
		return this;
	}

	/**
	 * 批量添加参数
	 * 
	 * @param mapParam
	 *            {@link Map<String, String>},key=参数名，value=参数值
	 * @return {@link HttpClientHelper}
	 */
	public HttpClientHelper addParameter(Map<String, String> mapParam) {
		if (mapParam != null) {
			for (Iterator<String> itr = mapParam.keySet().iterator(); itr
					.hasNext();) {
				String key = itr.next();
				String value = mapParam.get(key);
				paramList.add(new BasicNameValuePair(key, value));
			}
		}
		return this;
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            请求地址
	 * @return 请求结果内容
	 * @throws Exception
	 */
	public String doPost(String url) throws Exception {
		return doPost(url, 0);
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param timeout
	 *            超时时间毫秒
	 * @return 请求结果内容
	 * @throws Exception
	 * @author 罗家友
	 */
	public String doPost(String url, int timeout) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String result = "";
		HttpEntity entity = null;
		CloseableHttpResponse response = null;
		try {
			// 创建httppost
			HttpPost httppost = new HttpPost(url);
			if (timeout > 0) {
				httppost.setConfig(RequestConfig.custom()
						.setSocketTimeout(timeout).setConnectTimeout(timeout)
						.setConnectionRequestTimeout(timeout).build());
			}
			// 编码实体
			UrlEncodedFormEntity urlEncodeEntyty;
			// 编码
			urlEncodeEntyty = new UrlEncodedFormEntity(paramList, "utf-8");
			// 绑定到post对象
			httppost.setEntity(urlEncodeEntyty);
			// 执行httppost
			response = httpclient.execute(httppost);
			// 获取相应实体
			entity = response.getEntity();
			int statuscode = response.getStatusLine().getStatusCode();
			log.debug("访问：" + url + ",statuscode：" + statuscode);
			if (null != entity && statuscode == HttpStatus.SC_OK) {
				result = EntityUtils.toString(entity);
			} else {
				throw new Exception("access statuscode=" + statuscode);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != entity) {
				EntityUtils.consume(entity);
			}
			if (null != response) {
				response.close();
			}
			if (null != httpclient) {
				httpclient.close();
			}
		}
		log.debug("访问：" + url + ",返回：" + result);
		return result;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 *            请求地址
	 * @return 请求结果内容
	 * @throws Exception
	 */
	public static String doGet(String url) throws Exception {
		return doGet(url, 0);
	}

	/**
	 * get请求
	 * 
	 * @param url
	 *            请求地址
	 * @param timeout
	 *            超时时间
	 * @return String
	 * @throws Exception
	 * @author 罗家友
	 */
	public static String doGet(String strUrl, int timeout) throws Exception {
		String result = "";
		HttpEntity entity = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			// 创建HttpGet
			// mod yzh 2014-08-22
			// HttpGet httpget = new HttpGet(url);
			URL url = new URL(strUrl);

			URI uri = null;
			if (80 == url.getPort()) {
				uri = new URI(url.getProtocol(), url.getHost(), url.getPath(),
						url.getQuery(), null);
			} else {
				uri = new URI(url.getProtocol(), null, url.getHost(),
						url.getPort(), url.getPath(), url.getQuery(), null);
			}

			HttpGet httpget = new HttpGet(uri);

			if (timeout > 0) {
				httpget.setConfig(RequestConfig.custom()
						.setSocketTimeout(timeout).setConnectTimeout(timeout)
						.setConnectionRequestTimeout(timeout).build());
			}

			// 执行get请求
			response = httpclient.execute(httpget);
			// 获取相应实体
			entity = response.getEntity();
			int statuscode = response.getStatusLine().getStatusCode();
			log.debug("访问：" + url + ",statuscode：" + statuscode);
			if (null != entity && statuscode == HttpStatus.SC_OK) {
				result = EntityUtils.toString(entity,"UTF-8");
			} else {
				throw new Exception("access statuscode=" + statuscode);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != entity) {
				EntityUtils.consume(entity);
			}
			if (null != response) {
				response.close();
			}
			if (null != httpclient) {
				httpclient.close();
			}
		}
		log.debug("访问：" + strUrl + ",返回：" + result);
		return result;
	}

	/**
	 * 获取URL网络状态
	 * 
	 * @param url
	 *            服务器地址
	 * @return 正常访问返回true、异常返回false
	 * @throws IOException
	 */
	public static boolean getUrlState(String url) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			// 创建HttpGet
			HttpGet httpget = new HttpGet(url);
			// 执行get请求
			response = httpclient.execute(httpget);
			// 获取相应实体
			int state = response.getStatusLine().getStatusCode();
			log.debug(state);
			return state == HttpStatus.SC_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (null != response) {
				response.close();
			}
			if (null != httpclient) {
				httpclient.close();
			}
		}
	}

	/**
	 * @Title: sendXmlReturnRespCode
	 * @Description: 向服务器发送报文并得到响应结果
	 * @param reqPackets
	 * @return
	 */
	public String sendXmlReturnRespCode(String url, String reqPackets,
			String charsetName) {
		if (StringUtils.isEmpty(url) || StringUtils.isEmpty(reqPackets))
			return "";
		charsetName = StringUtils.isEmpty(charsetName) ? "UTF-8" : charsetName;
		String returnXml = "";
		try {
			// 发送报文
			URL sendUrl = new URL(url);
			URLConnection connection = sendUrl.openConnection();
			
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			connection.setDoOutput(true);

			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream(), charsetName);
			out.write(reqPackets);
			out.flush();
			out.close();
			
			// 一旦发送成功，用以下方法就可以得到服务器的回应：
			String currentLine = "";
			InputStream l_urlStream = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(l_urlStream,
					charsetName);
			BufferedReader l_reader = new BufferedReader(isr);
			while ((currentLine = l_reader.readLine()) != null) {
				returnXml += currentLine + "\r\n";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnXml;
	}
	
	/**
	 * 带SSL认证的发送xml报文并得到相应结果
	 * @param url  请求地址
	 * @param reqPackets  请求内容
	 * @param sslsf  SLL信息
	 * @return
	 */
	public String sendSSLXmlReturnRespCode(String url, String reqPackets,
			SSLConnectionSocketFactory sslsf) {
		if (StringUtils.isEmpty(url) || StringUtils.isEmpty(reqPackets)||null==sslsf)
			return "";
		String returnXml = "";
		try {
			  HttpEntity entity =  new StringEntity(reqPackets);
			  CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			  HttpPost post = new HttpPost(url);
			  post.setHeaders(new Header[]{new BasicHeader("Content-Type", "application/xml;charset=utf-8"),new BasicHeader("Accept", "application/xml;charset=utf-8")});
			  post.setEntity(entity);
			  CloseableHttpResponse response =  httpclient.execute(post);
			  entity = response.getEntity();
			  returnXml = EntityUtils.toString(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnXml;
	}
	
	/**
	 * post 请求
	 * @param url
	 * @param param
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public String doPost(String url,String param) throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String result = "";
		HttpEntity entity = null;
		CloseableHttpResponse response = null;
		try {
			// 创建httppost
			HttpPost httppost = new HttpPost(url);
			StringEntity strEntity = new StringEntity(param,"UTF-8");
			// 绑定到post对象
			httppost.setEntity(strEntity);
			// 执行httppost
			response = httpclient.execute(httppost);
			// 获取相应实体
			entity = response.getEntity();
			int statuscode = response.getStatusLine().getStatusCode();
			log.debug("访问：" + url + ",statuscode：" + statuscode);
			if (null != entity && statuscode == HttpStatus.SC_OK) {
				result = EntityUtils.toString(entity,"UTF-8");
			} else {
				throw new Exception("access statuscode=" + statuscode);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != entity) {
				EntityUtils.consume(entity);
			}
			if (null != response) {
				response.close();
			}
			if (null != httpclient) {
				httpclient.close();
			}
		}
		log.debug("访问：" + url + ",返回：" + result);
		return result;
	}
}
