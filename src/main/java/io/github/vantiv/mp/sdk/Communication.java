package io.github.vantiv.mp.sdk;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Communication {

    private static final String[] SUPPORTED_PROTOCOLS = new String[] {"TLSv1.2", "TLSv1.1"};
    private static final String NEUTER_STR = "NEUTERED";
    private CloseableHttpClient httpClient;
    private final int KEEP_ALIVE_DURATION = 8000;
    private final String CONTENT_TYPE_HEADER = "Content-Type";
    private final String HEADER_VALUE = "application/com.vantivcnp.payfac-v17+xml";
    private final String ACCEPT_HEADER = "Accept";
    private final String CONNECTION_EXCEPTION_MESSAGE = "Error connecting to Vantiv";
    private final String XML_ENCODING = "UTF-8";
    private Properties config;
    private String baseUrl;


    public Communication(Properties config) {
        setupCommunication();
        this.config = config;
        baseUrl = config.getProperty("url");
    }

    public Communication() {
        setupCommunication();
        this.config = (new Configuration()).getProperties();
        baseUrl = config.getProperty("url");
    }


    private void setupCommunication(){
        try {

            String protocol = getBestProtocol(SSLContext.getDefault().getSupportedSSLParameters().getProtocols());
            if (protocol == null) {
                throw new IllegalStateException("No supported TLS protocols available");
            }
            SSLContext ctx = SSLContexts.custom().useProtocol(protocol).build();
            ConnectionSocketFactory plainSocketFactory = new PlainConnectionSocketFactory();
            LayeredConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(ctx);

            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", plainSocketFactory)
                    .register("https", sslSocketFactory)
                    .build();

            BasicHttpClientConnectionManager manager = new BasicHttpClientConnectionManager(registry);

            HttpRequestRetryHandler requestRetryHandler = new DefaultHttpRequestRetryHandler(0, true);

            // Vantiv will a close an idle connection, so we define our Keep-alive strategy to be below that threshold

            ConnectionKeepAliveStrategy keepAliveStrategy = new ConnectionKeepAliveStrategy() {
                public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                    return KEEP_ALIVE_DURATION;
                }
            };
            httpClient = HttpClients.custom().setConnectionManager(manager)
                    .setRetryHandler(requestRetryHandler)
                    .setKeepAliveStrategy(keepAliveStrategy)
                    .build();

        } catch (GeneralSecurityException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static String getBestProtocol(final String[] availableProtocols) {
        String bestProtocol = null;
        if (availableProtocols == null || availableProtocols.length == 0) {
            return bestProtocol;
        }
        List<String> availableProtocolsList = Arrays.asList(availableProtocols);
        for (String supportedProtocol: SUPPORTED_PROTOCOLS) {
            if (availableProtocolsList.contains(supportedProtocol)) {
                bestProtocol = supportedProtocol;
                break;
            }
        }
        return bestProtocol;
    }


    //Payfac endpoints//


    public String httpGetRequest(String requestUrl) {
        HttpGet get = new HttpGet(requestUrl);
        get.setHeader(CONTENT_TYPE_HEADER, HEADER_VALUE);
        get.setHeader(ACCEPT_HEADER, HEADER_VALUE);
        printToConsole("\nGET request to url: \n", requestUrl);
        String response = sendHttpRequestToCnp(get);
        return response;
    }

    public String httpPutRequest(String xmlRequest, String requestUrl) {
        HttpPut put = new HttpPut(requestUrl);
        put.setHeader(CONTENT_TYPE_HEADER, HEADER_VALUE);
        put.setHeader(ACCEPT_HEADER, HEADER_VALUE);
        put.setEntity(new StringEntity(xmlRequest, XML_ENCODING));
        printToConsole("\nPUT request to url: \n", requestUrl);
        printToConsole("\nRequest XML: \n", xmlRequest);
        String response = sendHttpRequestToCnp(put);
        return response;
    }

    public String httpPostRequest(String xmlRequest, String requestUrl) {
        HttpPost post = new HttpPost(requestUrl);
        post.setHeader(CONTENT_TYPE_HEADER, HEADER_VALUE);
        post.setHeader(ACCEPT_HEADER, HEADER_VALUE);
        post.setEntity(new StringEntity(xmlRequest, XML_ENCODING));
        printToConsole("\nPOST request to url: \n", requestUrl);
        printToConsole("\nRequest XML: \n", xmlRequest);
        String response = sendHttpRequestToCnp(post);
        return response;
    }

    public String httpDeleteRequest(String requestUrl) {
        HttpDelete delete = new HttpDelete(requestUrl);
        delete.setHeader(CONTENT_TYPE_HEADER, HEADER_VALUE);
        delete.setHeader(ACCEPT_HEADER, HEADER_VALUE);
        printToConsole("\nDELETE request to url: \n", requestUrl);
        String response = sendHttpRequestToCnp(delete);
        return response;
    }


    //////////////////        HELPER METHODS         /////////////

    private String sendHttpRequestToCnp(HttpRequestBase request) {

        String xmlResponse = execHttpRequest(request);
        printToConsole("\nResponse XML: \n", xmlResponse);
        return xmlResponse;
    }

    private String execHttpRequest(HttpRequestBase request) {

        prepareHttpRequest(request);
        try {
            HttpResponse response = httpClient.execute(request);
            return validateResponse(response);
        }
        catch (IOException ex) {
            throw new PayFacException(CONNECTION_EXCEPTION_MESSAGE, ex);
        }
        finally {
            System.out.println("Headers");
            for(Header header : request.getAllHeaders()) {
                System.out.println(header.getName() + " : " + header.getValue());
            }
            request.abort();
        }
    }

    private String validateResponse(HttpResponse response) {
        HttpEntity entity = null;
        String xml;

        try {
            entity = response.getEntity();
            String contentType = entity.getContentType().getValue();
            int statusCode = response.getStatusLine().getStatusCode();
            xml = EntityUtils.toString(entity, XML_ENCODING);

            if(statusCode != 200 && statusCode != 201) {
                printToConsole("\nErrorResponse: ", xml);
                if(contentType.contains(HEADER_VALUE)) {
                    ErrorResponse errorResponse = XMLConverters.generateErrorResponse(xml);
                    throw new PayFacWebException(getErrorMessage(errorResponse), String.valueOf(statusCode), errorResponse.getErrors().getErrors());
                }
                throw new PayFacWebException(xml, String.valueOf(statusCode));
            }
        }
        catch (IOException ex) {
            throw new PayFacWebException("There was an exception while fetching the response xml.", ex);
        }
        finally {
            if(entity != null) {
                EntityUtils.consumeQuietly(entity);
            }
        }

        return xml;
    }

    private String getErrorMessage(ErrorResponse errorResponse) {
        StringBuilder errorMessage = new StringBuilder();
        String prefix = "";

        for(String error : errorResponse.getErrors().getErrors()) {
            errorMessage.append(prefix).append(error);
            prefix="\n";
        }

        return errorMessage.toString();
    }

    private void prepareHttpRequest(HttpRequestBase request) {

        String proxyHost = config.getProperty("proxyHost");
        String proxyPort = config.getProperty("proxyPort");
        int httpTimeout = Integer.valueOf(config.getProperty("timeout", "6000"));
        String username = config.getProperty("username");
        String password = config.getProperty("password");
        boolean httpKeepAlive = Boolean.valueOf(config.getProperty("httpKeepAlive", "false"));

        RequestConfig requestConfig = generateHttpRequestConfig(proxyHost, proxyPort, httpTimeout);
        String authCode = generateAuthCode(username, password);
        request.setHeader("Authorization", authCode);
        if(!httpKeepAlive) {
            request.setHeader("Connection", "close");
        }

        request.setConfig(requestConfig);
    }

    private String generateAuthCode(String username, String password) {

        return "Basic " + new String(Base64.encodeBase64((username + ":" + password).getBytes()));
    }

    private void printToConsole(String prefixMessage, String xmlResponse) {
        boolean printxml = "true".equalsIgnoreCase(config.getProperty("printXml"));
        boolean neuterXml = "true".equalsIgnoreCase(config.getProperty("neuterXml"));

        if(printxml) {
            if(neuterXml) {
                xmlResponse = neuterXml(xmlResponse);
            }
            System.out.println(prefixMessage + xmlResponse);
        }
    }

    public String neuterXml(String xml) {
        if (xml == null) {
            return xml;
        }
        xml = xml.replaceAll("<accNum>.*</accNum>", "<accNum>" + NEUTER_STR + "</accNum>");
        xml = xml.replaceAll("<user>.*</user>", "<user>" + NEUTER_STR + "</user>");
        xml = xml.replaceAll("<password>.*</password>", "<password>" + NEUTER_STR + "</password>");
        xml = xml.replaceAll("<track>.*</track>", "<track>" + NEUTER_STR + "</track>");
        xml = xml.replaceAll("<number>.*</number>", "<number>" + NEUTER_STR + "</number>");
        return xml;
    }

    private RequestConfig generateHttpRequestConfig(String proxyHost, String proxyPort, int httpTimeout) {

        HttpHost proxy;
        RequestConfig requestConfig;

        if(proxyHost != null && proxyHost.length() > 0 && proxyPort != null) {
            proxy = new HttpHost(proxyHost, Integer.valueOf(proxyPort));
            requestConfig = RequestConfig.copy(RequestConfig.DEFAULT)
                    .setProxy(proxy)
                    .setSocketTimeout(httpTimeout)
                    .setConnectTimeout(httpTimeout)
                    .build();
        }
        else {
            requestConfig = RequestConfig.copy(RequestConfig.DEFAULT)
                    .setSocketTimeout(httpTimeout)
                    .setConnectTimeout(httpTimeout)
                    .build();
        }

        return requestConfig;
    }
}
