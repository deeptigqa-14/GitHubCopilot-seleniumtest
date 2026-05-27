package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Page object for the tables page: https://the-internet.herokuapp.com/tables
 *
 * Indexing convention: rowIndex and colIndex are zero-based.
 * tableNumber: 1 -> table#table1, 2 -> table#table2
 */
public class TableDataPage extends BasePage {

    @FindBy(id = "table1")
    private WebElement table1;

    @FindBy(id = "table2")
    private WebElement table2;

    public TableDataPage(WebDriver driver) {
        super(driver);
    }

    // --- Low-level helpers ---

    private WebElement tableElement(int tableNumber) {
        return tableNumber == 2 ? table2 : table1;
    }

    private List<WebElement> headerCells(WebElement table) {
        return table.findElements(By.cssSelector("thead th"));
    }

    private List<WebElement> bodyRows(WebElement table) {
        return table.findElements(By.cssSelector("tbody tr"));
    }

    private List<WebElement> rowCells(WebElement row) {
        return row.findElements(By.cssSelector("td"));
    }

    // --- Public API ---

    /**
     * Count rows in the given table (zero-based indexing for row access).
     */
    public int getRowCount(int tableNumber) {
        WebElement table = tableElement(tableNumber);
        return bodyRows(table).size();
    }

    /**
     * Count visible columns (header cells) in the given table.
     */
    public int getColumnCount(int tableNumber) {
        WebElement table = tableElement(tableNumber);
        return headerCells(table).size();
    }

    /**
     * Get the text of a cell by zero-based rowIndex and colIndex.
     */
    public String getCellText(int tableNumber, int rowIndex, int colIndex) {
        WebElement table = tableElement(tableNumber);
        List<WebElement> rows = bodyRows(table);
        if (rowIndex < 0 || rowIndex >= rows.size()) {
            throw new IndexOutOfBoundsException("Invalid rowIndex: " + rowIndex);
        }
        List<WebElement> cells = rowCells(rows.get(rowIndex));
        if (colIndex < 0 || colIndex >= cells.size()) {
            throw new IndexOutOfBoundsException("Invalid colIndex: " + colIndex);
        }
        return cells.get(colIndex).getText().trim();
    }

    /**
     * Return the index of a header column by its visible text (case-insensitive).
     * Returns -1 if not found.
     */
    public int getColumnIndexByName(int tableNumber, String headerName) {
        WebElement table = tableElement(tableNumber);
        List<WebElement> headers = headerCells(table);
        for (int i = 0; i < headers.size(); i++) {
            String text = headers.get(i).getText().trim();
            if (text.equalsIgnoreCase(headerName.trim())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Get cell text given a row index and header name.
     */
    public String getCellTextByHeader(int tableNumber, int rowIndex, String headerName) {
        int colIndex = getColumnIndexByName(tableNumber, headerName);
        if (colIndex == -1) {
            throw new IllegalArgumentException("Header not found: " + headerName);
        }
        return getCellText(tableNumber, rowIndex, colIndex);
    }

    /**
     * Return a map of headerName -> cellText for the specified row.
     */
    public Map<String, String> getRowDataAsMap(int tableNumber, int rowIndex) {
        WebElement table = tableElement(tableNumber);
        List<WebElement> headers = headerCells(table);
        List<WebElement> rows = bodyRows(table);
        if (rowIndex < 0 || rowIndex >= rows.size()) {
            throw new IndexOutOfBoundsException("Invalid rowIndex: " + rowIndex);
        }
        List<WebElement> cells = rowCells(rows.get(rowIndex));
        Map<String, String> map = new HashMap<>();
        int columns = Math.min(headers.size(), cells.size());
        for (int i = 0; i < columns; i++) {
            String header = headers.get(i).getText().trim();
            String value = cells.get(i).getText().trim();
            map.put(header, value);
        }
        return map;
    }

    /**
     * Get all rows as a list of maps (header -> value).
     */
    public boolean isTextPresentInTable(int tableNumber, String text) {
        WebElement table = tableElement(tableNumber);
        List<WebElement> rows = bodyRows(table);
        for (WebElement row : rows) {
            if (row.getText().contains(text)) {
                return true;
            }
        }
        return false;
    }

    public List<Map<String, String>> getAllRowsData(int tableNumber) {
        WebElement table = tableElement(tableNumber);
        List<Map<String, String>> all = new ArrayList<>();
        List<WebElement> rows = bodyRows(table);
        for (int i = 0; i < rows.size(); i++) {
            all.add(getRowDataAsMap(tableNumber, i));
        }
        return all;
    }

    /**
     * Click the action link within a row. action should be the visible link text,
     * e.g. "edit" or "delete" (case-insensitive). Returns true if clicked.
     */
    public boolean clickActionOnRow(int tableNumber, int rowIndex, String action) {
        WebElement table = tableElement(tableNumber);
        List<WebElement> rows = bodyRows(table);
        if (rowIndex < 0 || rowIndex >= rows.size()) {
            throw new IndexOutOfBoundsException("Invalid rowIndex: " + rowIndex);
        }
        WebElement row = rows.get(rowIndex);
        String actionText = action.trim();
        // find link by link text (case-insensitive match)
        List<WebElement> links = row.findElements(By.cssSelector("a"));
        for (WebElement link : links) {
            if (link.getText().trim().equalsIgnoreCase(actionText)) {
                link.click();
                return true;
            }
        }
        return false;
    }

    /**
     * Find the first row index where the given column (by headerName) equals value (exact match).
     * Returns -1 if not found.
     */
    public int findRowIndexByColumnValue(int tableNumber, String headerName, String value) {
        int colIndex = getColumnIndexByName(tableNumber, headerName);
        if (colIndex == -1) return -1;
        WebElement table = tableElement(tableNumber);
        List<WebElement> rows = bodyRows(table);
        for (int i = 0; i < rows.size(); i++) {
            List<WebElement> cells = rowCells(rows.get(i));
            if (colIndex < cells.size()) {
                if (cells.get(colIndex).getText().trim().equals(value)) {
                    return i;
                }
            }
        }
        return -1;
    }

    //return the first and last name from the table as a list of strings
    public List<String> getFirstAndLastNames(int tableNumber) {
        List<String> names = new ArrayList<>();
        WebElement table = tableElement(tableNumber);
        List<WebElement> rows = bodyRows(table);
        for (WebElement row : rows) {
            List<WebElement> cells = rowCells(row);
            if (cells.size() >= 2) {
                String firstName = cells.get(0).getText().trim();
                String lastName = cells.get(1).getText().trim();
                names.add(firstName + " " + lastName);
            }
        }
        return names;
    }
}