package com.cw.web.common.sms.util.http;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpClientManager {
	private static Logger logger = LoggerFactory.getLogger(HttpClientManager.class);
	private final static int CONNECT_TIMEOUT = 3000;// 连接超时毫秒
	private final static int SOCKET_TIMEOUT = 10000;// 传输超时毫秒
	private final static int REQUESTCONNECT_TIMEOUT = 3000;// 获取请求超时毫秒
	private final static int CONNECT_TOTAL = 300;// 最大连接数
	private final static int CONNECT_ROUTE = 20;// 每个路由基础的连接数
	private static PoolingHttpClientConnectionManager connManager = null;
	private static RequestConfig requestConfig;
	private static HttpRequestRetryHandler httpRequestRetryHandler;
	
	static {
		ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
		LayeredConnectionSocketFactory sslsf = createSSLConnSocketFactory();
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", plainsf).register("https", sslsf).build();
		connManager = new PoolingHttpClientConnectionManager(registry);
		// 将最大连接数增加到100
		connManager.setMaxTotal(CONNECT_TOTAL);
		// 将每个路由基础的连接增加到20
		connManager.setDefaultMaxPerRoute(CONNECT_ROUTE);
		// 可用空闲连接过期时间,重用空闲连接时会先检查是否空闲时间超过这个时间，如果超过，释放socket重新建立
		connManager.setValidateAfterInactivity(30000);
		// 设置socket超时时间
		SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(SOCKET_TIMEOUT).build();
		connManager.setDefaultSocketConfig(socketConfig);
		requestConfig = RequestConfig.custom().setConnectionRequestTimeout(REQUESTCONNECT_TIMEOUT)
				.setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		httpRequestRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount >= 3) {// 如果已经重试了3次，就放弃
					return false;
				}
				if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
					return true;
				}
				if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
					return false;
				}
				if (exception instanceof InterruptedIOException) {// 超时
					return true;
				}
				if (exception instanceof UnknownHostException) {// 目标服务器不可达
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
					return false;
				}
				if (exception instanceof SSLException) {// ssl握手异常
					return false;
				}
				HttpClientContext clientContext = HttpClientContext.adapt(context);
				HttpRequest request = clientContext.getRequest();
				// 如果请求是幂等的，就再次尝试
				if (!(request instanceof HttpEntityEnclosingRequest)) {
					return true;
				}
				return false;
			}
		};
		
		if (connManager != null && connManager.getTotalStats() != null) {
			logger.info("now client pool " + connManager.getTotalStats().toString());
		}
	}
	
	// SSL的socket工厂创建
		private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
			SSLConnectionSocketFactory sslsf = null;
			// 创建TrustManager() 用于解决javax.net.ssl.SSLPeerUnverifiedException: peer
			// not authenticated
			X509TrustManager trustManager = new X509TrustManager() {
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(X509Certificate[] arg0, String authType) throws CertificateException {
					// TODO Auto-generated method stub
				}

				@Override
				public void checkServerTrusted(X509Certificate[] arg0, String authType) throws CertificateException {
					// TODO Auto-generated method stub
				}
			};
			SSLContext sslContext;
			try {
				sslContext = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
				sslContext.init(null, new TrustManager[] { (TrustManager) trustManager }, null);
				// 创建SSLSocketFactory , // 不校验域名 ,取代以前验证规则
				sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return sslsf;
		}

	public static CloseableHttpClient getHttpClient(){
		CloseableHttpClient httpClient  = HttpClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(requestConfig)
				.setRetryHandler(httpRequestRetryHandler).build();
		return httpClient;
	}
}
