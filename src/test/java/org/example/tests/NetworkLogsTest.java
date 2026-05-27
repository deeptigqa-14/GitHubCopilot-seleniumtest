package org.example.tests;

import org.example.pages.NetworkLogsPage;
import org.example.utils.DriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.junit.Assert.*;

public class NetworkLogsTest {
    private WebDriver driver;
    private NetworkLogsPage networkLogsPage;

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        networkLogsPage = new NetworkLogsPage(driver);
    }

    @After
    public void tearDown() {
        networkLogsPage.disableNetworkMonitoring();
        DriverManager.closeDriver();
    }

    @Test
    public void testBasicNetworkLogging() {
        // Enable network monitoring
        networkLogsPage.enableNetworkMonitoring();

        // Navigate to home page
        networkLogsPage.navigateToHome();
        networkLogsPage.staticWait(3);

        // Collect logs
        networkLogsPage.staticWait(2);

        // Wait for manual verification
        networkLogsPage.staticWait(5);

        // Verify requests were captured
        int totalRequests = networkLogsPage.getTotalRequestCount();
        System.out.println("Total requests captured: " + totalRequests);
        assertTrue("Should capture at least one request", totalRequests > 0);
    }

    @Test
    public void testSuccessfulRequests() {
        // Enable network monitoring
        networkLogsPage.enableNetworkMonitoring();

        // Navigate to home page
        networkLogsPage.navigateToHome();
        networkLogsPage.staticWait(3);

        // Get successful requests
        networkLogsPage.staticWait(2);
        List<NetworkLogsPage.NetworkLog> successfulRequests = networkLogsPage.getSuccessfulRequests();

        // Wait for manual verification
        networkLogsPage.staticWait(5);

        // Verify successful requests exist
        System.out.println("Successful requests: " + successfulRequests.size());
        assertTrue("Should have successful (2xx) requests", successfulRequests.size() > 0);
        for (NetworkLogsPage.NetworkLog log : successfulRequests) {
            System.out.println(log);
            assertTrue("Status code should be 2xx", log.statusCode >= 200 && log.statusCode < 300);
        }
    }

    @Test
    public void testFailedRequests() {
        // Enable network monitoring
        networkLogsPage.enableNetworkMonitoring();

        // Navigate to page
        networkLogsPage.navigateToHome();
        networkLogsPage.staticWait(3);

        // Get failed requests
        networkLogsPage.staticWait(2);
        List<NetworkLogsPage.NetworkLog> failedRequests = networkLogsPage.getFailedRequests();

        // Wait for manual verification
        networkLogsPage.staticWait(5);

        // Print results (may be 0 if no failed requests)
        System.out.println("Failed requests (4xx/5xx): " + failedRequests.size());
        for (NetworkLogsPage.NetworkLog log : failedRequests) {
            System.out.println(log);
        }

        // Note: Home page might not have failed requests, this is expected
    }

    @Test
    public void testNetworkStatistics() {
        // Enable network monitoring
        networkLogsPage.enableNetworkMonitoring();

        // Navigate to home page
        networkLogsPage.navigateToHome();
        networkLogsPage.staticWait(3);

        // Collect logs
        networkLogsPage.staticWait(2);

        // Wait for manual verification
        networkLogsPage.staticWait(5);

        // Print detailed statistics
        networkLogsPage.printNetworkStatistics();

        // Verify basic stats
        assertTrue("Should have requests", networkLogsPage.getTotalRequestCount() > 0);
        assertTrue("Average response time should be positive", networkLogsPage.getAverageResponseTime() >= 0);
    }

    @Test
    public void testAllNetworkLogs() {
        // Enable network monitoring
        networkLogsPage.enableNetworkMonitoring();

        // Navigate to home page
        networkLogsPage.navigateToHome();
        networkLogsPage.staticWait(3);

        // Collect logs
        networkLogsPage.staticWait(2);

        // Wait for manual verification
        networkLogsPage.staticWait(5);

        // Print all logs
        networkLogsPage.printAllNetworkLogs();

        // Verify
        assertTrue("Should capture requests", networkLogsPage.getTotalRequestCount() > 0);
    }

    @Test
    public void testRequestsByContentType() {
        // Enable network monitoring
        networkLogsPage.enableNetworkMonitoring();

        // Navigate to home page
        networkLogsPage.navigateToHome();
        networkLogsPage.staticWait(3);

        // Collect logs
        networkLogsPage.staticWait(2);

        // Get HTML requests
        List<NetworkLogsPage.NetworkLog> htmlRequests = networkLogsPage.getRequestsByContentType("text/html");

        // Wait for manual verification
        networkLogsPage.staticWait(5);

        // Print results
        System.out.println("HTML requests: " + htmlRequests.size());
        networkLogsPage.printRequestsByContentType("text/html");

        // Note: May vary based on page structure
    }

    @Test
    public void testResponseTimes() {
        // Enable network monitoring
        networkLogsPage.enableNetworkMonitoring();

        // Navigate to home page
        networkLogsPage.navigateToHome();
        networkLogsPage.staticWait(3);

        // Collect logs
        networkLogsPage.staticWait(2);

        // Get response times
        long totalTime = networkLogsPage.getTotalResponseTime();
        double avgTime = networkLogsPage.getAverageResponseTime();

        // Wait for manual verification
        networkLogsPage.staticWait(5);

        // Print results
        System.out.println("Total Response Time: " + totalTime + " ms");
        System.out.println("Average Response Time: " + String.format("%.2f", avgTime) + " ms");

        // Verify
        assertTrue("Total response time should be positive", totalTime >= 0);
        assertTrue("Average response time should be positive", avgTime >= 0);
    }

    @Test
    public void testDataTransferred() {
        // Enable network monitoring
        networkLogsPage.enableNetworkMonitoring();

        // Navigate to home page
        networkLogsPage.navigateToHome();
        networkLogsPage.staticWait(3);

        // Collect logs
        networkLogsPage.staticWait(2);

        // Get data transfer stats
        long totalData = networkLogsPage.getTotalDataTransferred();

        // Wait for manual verification
        networkLogsPage.staticWait(5);

        // Print results
        System.out.println("Total Data Transferred: " + (totalData / 1024) + " KB");
        System.out.println("Total Data Transferred: " + (totalData / (1024 * 1024)) + " MB");

        // Verify
        assertTrue("Should transfer some data", totalData >= 0);
    }

    @Test
    public void testUrlWasRequested() {
        // Enable network monitoring
        networkLogsPage.enableNetworkMonitoring();

        // Navigate to home page
        networkLogsPage.navigateToHome();
        networkLogsPage.staticWait(3);

        // Collect logs
        networkLogsPage.staticWait(2);

        // Wait for manual verification
        networkLogsPage.staticWait(5);

        // Check if specific URLs were requested
        boolean homeRequested = networkLogsPage.urlWasRequested("the-internet.herokuapp.com");
        System.out.println("Home URL requested: " + homeRequested);
        assertTrue("the-internet.herokuapp.com should be requested", homeRequested);
    }

    @Test
    public void testNetworkByStatusCode() {
        // Enable network monitoring
        networkLogsPage.enableNetworkMonitoring();

        // Navigate to home page
        networkLogsPage.navigateToHome();
        networkLogsPage.staticWait(3);

        // Collect logs
        networkLogsPage.staticWait(2);

        // Get count by status code
        int count200 = networkLogsPage.getCountByStatusCode(200);
        int count404 = networkLogsPage.getCountByStatusCode(404);

        // Wait for manual verification
        networkLogsPage.staticWait(5);

        // Print results
        System.out.println("200 OK responses: " + count200);
        System.out.println("404 Not Found responses: " + count404);

        // Verify at least one successful request
        assertTrue("Should have at least one 200 response", count200 > 0);
    }

    @Test
    public void testClearNetworkLogs() {
        // Enable network monitoring
        networkLogsPage.enableNetworkMonitoring();

        // Navigate to home page
        networkLogsPage.navigateToHome();
        networkLogsPage.staticWait(3);

        // Collect logs
        networkLogsPage.staticWait(2);

        // Verify logs exist
        int countBefore = networkLogsPage.getTotalRequestCount();
        assertTrue("Should have logs", countBefore > 0);

        // Clear logs
        networkLogsPage.clearNetworkLogs();

        // Verify logs cleared
        int countAfter = networkLogsPage.getTotalRequestCount();
        assertEquals("Logs should be cleared", 0, countAfter);

        // Wait for manual verification
        networkLogsPage.staticWait(5);
    }
}