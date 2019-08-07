package com.cw.web.common.sms.util.http;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;

public class HttpClientUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	private final static String RESP_CONTENT = "通信失败";
	private final static String ENCODE_CHARSET = "utf-8";// 响应报文解码字符集

	/**
	 * 发送HTTP_GET请求
	 *
	 * @see 1)该方法会自动关闭连接,释放资源
	 * @see 2)方法内设置了连接和读取超时时间,单位为毫秒,超时或发生其它异常时方法会自动返回"通信失败"字符串
	 * @see 3)请求参数含中文时,经测试可直接传入中文,HttpClient会自动编码发给Server,应用时应根据实际效果决 定传入前是否转码
	 * @see 4)该方法会自动获取到响应消息头中[Content-Type:text/html;
	 *      charset=GBK]的charset值作为响应报文的 解码字符集
	 * @see 若响应消息头中无Content-Type属性,则会使用HttpClient内部默认的ISO-8859-1作为响应报文的解码字符 集
	 * @param requestURL
	 *            请求地址(含参数)
	 * @return 远程主机响应正文
	 */
	public static String sendGetRequest(String reqURL, String param) {
		if (null != param) {
			reqURL += "?" + param;
		}
		CloseableHttpClient httpClient = HttpClientManager.getHttpClient();
		String respContent = RESP_CONTENT; // 响应内容
		HttpGet httpget = new HttpGet(reqURL);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpget, HttpClientContext.create()); // 执行GET请求
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if (null != entity) {
				Charset respCharset = ContentType.getOrDefault(entity).getCharset();
				respContent = EntityUtils.toString(entity, respCharset);
				EntityUtils.consume(entity);
			}
		} catch (ConnectTimeoutException cte) {
			logger.error("请求通信[" + reqURL + "]时连接超时,堆栈轨迹如下", cte);
		} catch (SocketTimeoutException ste) {
			logger.error("请求通信[" + reqURL + "]时读取超时,堆栈轨迹如下", ste);
		} catch (ClientProtocolException cpe) {
			// 该异常通常是协议错误导致:比如构造HttpGet对象时传入协议不对(将'http'写成'htp')or响应内容不符合HTTP协议要求等
			logger.error("请求通信[" + reqURL + "]时协议异常,堆栈轨迹如下", cpe);
		} catch (ParseException pe) {
			logger.error("请求通信[" + reqURL + "]时解析异常,堆栈轨迹如下", pe);
		} catch (IOException ioe) {
			// 该异常通常是网络原因引起的,如HTTP服务器未启动等
			logger.error("请求通信[" + reqURL + "]时网络异常,堆栈轨迹如下", ioe);
		} catch (Exception e) {
			logger.error("请求通信[" + reqURL + "]时偶遇异常,堆栈轨迹如下", e);
		} finally {
			try {
				if (response != null)
					response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (httpget != null) {
				httpget.releaseConnection();
			}
		}
		return respContent;
	}

	/**
	 * 发送HTTP_POST请求 type: 默认是表单请求，
	 * 
	 * @see 1)该方法允许自定义任何格式和内容的HTTP请求报文体
	 * @see 2)该方法会自动关闭连接,释放资源
	 * @see 3)方法内设置了连接和读取超时时间,单位为毫秒,超时或发生其它异常时方法会自动返回"通信失败"字符串
	 * @see 4)请求参数含中文等特殊字符时,可直接传入本方法,并指明其编码字符集encodeCharset参数,方法内部会自 动对其转码
	 * @see 5)该方法在解码响应报文时所采用的编码,取自响应消息头中的[Content-Type:text/html; charset=GBK]的
	 *      charset值
	 * @see 若响应消息头中未指定Content-Type属性,则会使用HttpClient内部默认的ISO-8859-1
	 * @param reqURL
	 *            请求地址
	 * @param reqData
	 *            请求参数,若有多个参数则应拼接为param11=value11&22=value22&33=value33的形式
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,此参数为必填项(不能为""或null)
	 * @return 远程主机响应正文
	 */
	public static String sendPostRequest(String reqURL, String param, String type) {
		String result = RESP_CONTENT;
		// 获取连接
		CloseableHttpClient httpClient = HttpClientManager.getHttpClient();
		// 设置请求和传输超时时间
		HttpPost httpPost = new HttpPost(reqURL);
		// 这就有可能会导致服务端接收不到POST过去的参数,比如运行在Tomcat6.0.36中的Servlet,所以我们手工指定CONTENT_TYPE头消息
		if (type != null && type.length() > 0) {
			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json; charset=" + ENCODE_CHARSET);
		} else {
			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=" + ENCODE_CHARSET);
		}
		CloseableHttpResponse response = null;
		try {
			if (param != null) {
				StringEntity entity = new StringEntity(param, ENCODE_CHARSET);
				httpPost.setEntity(entity);
			}
			logger.info("开始执行请求：" + reqURL);
			response = httpClient.execute(httpPost, HttpClientContext.create());
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				result = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
				logger.info("执行请求完毕：" + result);
				EntityUtils.consume(entity);
			}
		} catch (ConnectTimeoutException cte) {
			logger.error("请求通信[" + reqURL + "]时连接超时,堆栈轨迹如下", cte);
		} catch (SocketTimeoutException ste) {
			logger.error("请求通信[" + reqURL + "]时读取超时,堆栈轨迹如下", ste);
		} catch (ClientProtocolException cpe) {
			logger.error("请求通信[" + reqURL + "]时协议异常,堆栈轨迹如下", cpe);
		} catch (ParseException pe) {
			logger.error("请求通信[" + reqURL + "]时解析异常,堆栈轨迹如下", pe);
		} catch (IOException ioe) {
			logger.error("请求通信[" + reqURL + "]时网络异常,堆栈轨迹如下", ioe);
		} catch (Exception e) {
			logger.error("请求通信[" + reqURL + "]时偶遇异常,堆栈轨迹如下", e);
		} finally {
			try {
				if (response != null)
					response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (httpPost != null) {
				httpPost.releaseConnection();
			}
		}
		return result;
	}
}
