package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v129.network.Network;
import org.openqa.selenium.devtools.v129.network.model.Headers;
import org.openqa.selenium.devtools.v129.network.model.Response;

import java.util.*;

/**
 * Page object for capturing and validating network logs.
 * Uses Selenium DevTools to capture HTTP requests/responses.
 */
public class NetworkLogsPage extends BasePage {
    private DevTools devTools;
    private List<NetworkLog> networkLogs;
    private static final String HOME_URL = "https://the-internet.herokuapp.com";

    public class NetworkLog {
        public String requestId;
        public String url;
        public String method;
        public int statusCode;
        public String statusText;
        public long responseTime=0;
        public String contentType;
        public long contentLength;
        public Map<String, String> requestHeaders;
        public Headers responseHeaders;

        @Override
        public String toString() {
            return String.format("[%d %s] %s %s (%.2f ms, %d bytes)",
                    statusCode, statusText, method, url, responseTime, contentLength);
        }
    }

    public NetworkLogsPage(WebDriver driver) {
        super(driver);
        this.networkLogs = Collections.synchronizedList(new ArrayList<>());
        initializeDevTools();
    }

    private void initializeDevTools() {
        try {
            if (driver instanceof ChromeDriver) {
                ChromeDriver chromeDriver = (ChromeDriver) driver;
                devTools = chromeDriver.getDevTools();
               // if (!devTools.isConnected()) {
                    devTools.createSession();
              //  }
                System.out.println("DevTools initialized successfully");
            }
        } catch (Exception e) {
            System.err.println("Error initializing DevTools: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void enableNetworkMonitoring() {
        if (devTools == null) {
            System.err.println("DevTools not initialized");
            return;
        }

        try {
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

            // Listen for request will be sent
            devTools.addListener(Network.requestWillBeSent(),
                    request -> {
                        System.out.println("Request: " + request.getRequest().getUrl());
                    });

            // Listen for response received
            devTools.addListener(Network.responseReceived(),
                    response -> {
                        Response resp = response.getResponse();
                        System.out.println("Response: " + resp.getStatus() + " - " + resp.getUrl());

                        NetworkLog log = new NetworkLog();
                        log.requestId = response.getRequestId().toString();
                        log.url = resp.getUrl();
                        log.method = response.getResponse().getRequestHeaders() != null ? "GET/POST" : "GET";
                        log.statusCode = resp.getStatus();
                        log.statusText = resp.getStatusText();
                       // log.responseTime = resp.getResponseTime() != null ? resp.getResponseTime().longValue() : 0;
                        log.contentType = resp.getMimeType();
                        log.responseHeaders = resp.getHeaders();

                        networkLogs.add(log);
                    });

            System.out.println("Network monitoring enabled");
        } catch (Exception e) {
            System.err.println("Error enabling network monitoring: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void disableNetworkMonitoring() {
       // if (devTools != null && devTools.isConnected()) {
            try {
                devTools.send(Network.disable());
                System.out.println("Network monitoring disabled");
            } catch (Exception e) {
                System.err.println("Error disabling network monitoring: " + e.getMessage());
            }
        //}
    }

    public void navigateToHome() {
        navigateTo(HOME_URL);
    }

    public void navigateTo(String url) {
        super.navigateTo(url);
    }

    // --- Query Methods ---

    public List<NetworkLog> getAllNetworkLogs() {
        return new ArrayList<>(networkLogs);
    }

    public int getTotalRequestCount() {
        return networkLogs.size();
    }

    public List<NetworkLog> getRequestsByMethod(String method) {
        List<NetworkLog> filtered = new ArrayList<>();
        for (NetworkLog log : networkLogs) {
            if (log.method.equalsIgnoreCase(method)) {
                filtered.add(log);
            }
        }
        return filtered;
    }

    public List<NetworkLog> getRequestsByStatusCode(int statusCode) {
        List<NetworkLog> filtered = new ArrayList<>();
        for (NetworkLog log : networkLogs) {
            if (log.statusCode == statusCode) {
                filtered.add(log);
            }
        }
        return filtered;
    }

    public List<NetworkLog> getSuccessfulRequests() {
        List<NetworkLog> filtered = new ArrayList<>();
        for (NetworkLog log : networkLogs) {
            if (log.statusCode >= 200 && log.statusCode < 300) {
                filtered.add(log);
            }
        }
        return filtered;
    }

    public List<NetworkLog> getFailedRequests() {
        List<NetworkLog> filtered = new ArrayList<>();
        for (NetworkLog log : networkLogs) {
            if (log.statusCode >= 400) {
                filtered.add(log);
            }
        }
        return filtered;
    }

    public List<NetworkLog> getRedirectRequests() {
        List<NetworkLog> filtered = new ArrayList<>();
        for (NetworkLog log : networkLogs) {
            if (log.statusCode >= 300 && log.statusCode < 400) {
                filtered.add(log);
            }
        }
        return filtered;
    }

    public List<NetworkLog> getRequestsByContentType(String contentType) {
        List<NetworkLog> filtered = new ArrayList<>();
        for (NetworkLog log : networkLogs) {
            if (log.contentType != null && log.contentType.contains(contentType)) {
                filtered.add(log);
            }
        }
        return filtered;
    }

    public List<NetworkLog> getRequestsByUrl(String urlPattern) {
        List<NetworkLog> filtered = new ArrayList<>();
        for (NetworkLog log : networkLogs) {
            if (log.url.contains(urlPattern)) {
                filtered.add(log);
            }
        }
        return filtered;
    }

    public boolean urlWasRequested(String urlPattern) {
        return !getRequestsByUrl(urlPattern).isEmpty();
    }

    public int getCountByStatusCode(int statusCode) {
        return getRequestsByStatusCode(statusCode).size();
    }

    public long getTotalResponseTime() {
        long total = 0;
        for (NetworkLog log : networkLogs) {
            total += log.responseTime;
        }
        return total;
    }

    public double getAverageResponseTime() {
        if (networkLogs.isEmpty()) return 0;
        return (double) getTotalResponseTime() / networkLogs.size();
    }

    public long getTotalDataTransferred() {
        long total = 0;
        for (NetworkLog log : networkLogs) {
            total += log.contentLength;
        }
        return total;
    }

    public NetworkLog getRequestByUrl(String url) {
        for (NetworkLog log : networkLogs) {
            if (log.url.equals(url)) {
                return log;
            }
        }
        return null;
    }

    // --- Reporting Methods ---

    public void printAllNetworkLogs() {
        System.out.println("=== All Network Logs (" + networkLogs.size() + " requests) ===");
        for (int i = 0; i < networkLogs.size(); i++) {
            System.out.println((i + 1) + ". " + networkLogs.get(i));
        }
    }

    public void printFailedRequests() {
        List<NetworkLog> failed = getFailedRequests();
        System.out.println("=== Failed Requests (" + failed.size() + ") ===");
        for (NetworkLog log : failed) {
            System.out.println(log);
        }
    }

    public void printRequestsByContentType(String contentType) {
        List<NetworkLog> filtered = getRequestsByContentType(contentType);
        System.out.println("=== Requests with content type: " + contentType + " (" + filtered.size() + ") ===");
        for (NetworkLog log : filtered) {
            System.out.println(log);
        }
    }

    public void printNetworkStatistics() {
        System.out.println("=== Network Statistics ===");
        System.out.println("Total Requests: " + getTotalRequestCount());
        System.out.println("Successful (2xx): " + getSuccessfulRequests().size());
        System.out.println("Redirects (3xx): " + getRedirectRequests().size());
        System.out.println("Client Errors (4xx): " + getRequestsByStatusCode(400).size() +
                getRequestsByStatusCode(401).size() +
                getRequestsByStatusCode(403).size() +
                getRequestsByStatusCode(404).size());
        System.out.println("Server Errors (5xx): " + getFailedRequests().stream()
                .filter(r -> r.statusCode >= 500).count());
        System.out.println("Total Response Time: " + getTotalResponseTime() + " ms");
        System.out.println("Average Response Time: " + String.format("%.2f", getAverageResponseTime()) + " ms");
        System.out.println("Total Data Transferred: " + (getTotalDataTransferred() / 1024) + " KB");
    }

    public void clearNetworkLogs() {
        networkLogs.clear();
        System.out.println("Network logs cleared");
    }
}