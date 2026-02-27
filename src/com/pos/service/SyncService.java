package com.pos.service;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.MySQL;

public class SyncService {

    private static final String API_BASE_URL = "http://127.0.0.1:8000/api";
    private static String accessToken = "";
    private static int currentUserId = 1; // Default to admin or 1
    private static Runnable onSyncCompleteListener;

    public static void setCurrentUserId(int userId) {
        currentUserId = userId;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static void setOnSyncCompleteListener(Runnable listener) {
        onSyncCompleteListener = listener;
    }

    public static void setAccessToken(String token) {
        accessToken = token;
    }

    public static void startSyncThread() {
        new Thread(() -> {
            while (true) {
                try {
                    if (NetworkService.isInternetAvailable() && !accessToken.isEmpty()) {
                        try {
                            syncItems();
                        } catch (Exception e) {
                            System.err.println("Sync items failed: " + e.getMessage());
                        }
                        try {
                            syncCustomers();
                        } catch (Exception e) {
                            System.err.println("Sync pull customers failed: " + e.getMessage());
                        }
                        try {
                            pushCustomers();
                        } catch (Exception e) {
                            System.err.println("Sync push customers failed: " + e.getMessage());
                        }
                        try {
                            int synced = pushSales();
                            if (synced >= 0 && onSyncCompleteListener != null) {
                                onSyncCompleteListener.run();
                            }
                        } catch (Exception e) {
                            System.err.println("Sync push sales failed: " + e.getMessage());
                        }
                    }
                    Thread.sleep(300000); // Sync every 5 minutes
                } catch (Exception e) {
                    // Overall thread protection
                }
            }
        }).start();
    }

    private static void syncItems() {
        try {
            String jsonResponse = fetchFromApi("/items");
            if (jsonResponse != null) {
                Gson gson = new Gson();
                JsonObject obj = gson.fromJson(jsonResponse, JsonObject.class);
                if (obj != null && obj.has("success") && !obj.get("success").isJsonNull()
                        && obj.get("success").getAsBoolean()) {
                    JsonArray data = obj.has("data") ? obj.getAsJsonArray("data") : null;
                    if (data != null) {
                        for (JsonElement el : data) {
                            JsonObject item = el.getAsJsonObject();
                            String sql = "INSERT INTO `items` (`id`, `item_code`, `item_name`, `purchase_price`, `retail_price`, `wholesale_price`, `barcode`, `quantity`) "
                                    +
                                    "VALUES (" + item.get("id").getAsInt() + ", '" + item.get("item_code").getAsString()
                                    + "', '" + item.get("item_name").getAsString().replace("'", "''") + "', " +
                                    item.get("purchase_price").getAsDouble() + ", " +
                                    item.get("retail_price").getAsDouble() + ", "
                                    + item.get("wholesale_price").getAsDouble() + ", '"
                                    + (item.get("barcode").isJsonNull() ? "" : item.get("barcode").getAsString())
                                    + "', " +
                                    item.get("quantity").getAsInt() + ") " +
                                    "ON DUPLICATE KEY UPDATE `item_name`=VALUES(`item_name`), `purchase_price`=VALUES(`purchase_price`), `retail_price`=VALUES(`retail_price`), "
                                    +
                                    "`wholesale_price`=VALUES(`wholesale_price`), `barcode`=VALUES(`barcode`), `quantity`=VALUES(`quantity`)";
                            MySQL.execute(sql);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void syncCustomers() {
        try {
            String jsonResponse = fetchFromApi("/customers");
            if (jsonResponse != null) {
                Gson gson = new Gson();
                JsonObject obj = gson.fromJson(jsonResponse, JsonObject.class);
                if (obj != null && obj.has("success") && !obj.get("success").isJsonNull()
                        && obj.get("success").getAsBoolean()) {
                    JsonArray data = obj.has("data") ? obj.getAsJsonArray("data") : null;
                    if (data != null) {
                        for (JsonElement el : data) {
                            JsonObject customer = el.getAsJsonObject();
                            String customerCode = customer.get("customer_code").isJsonNull() ? null
                                    : customer.get("customer_code").getAsString();

                            // Use ON DUPLICATE KEY UPDATE with customer_code as a fallback or anchor
                            String sql = "INSERT INTO `customers` (`id`, `customer_name`, `contact_number`, `email`, `city_name`, `address_line_1`, `due_amount`, `customer_code`, `is_synced`) "
                                    +
                                    "VALUES (" + customer.get("id").getAsInt() + ", "
                                    + "'" + customer.get("customer_name").getAsString().replace("'", "''") + "', "
                                    + "'" + customer.get("contact_number").getAsString().replace("'", "''") + "', "
                                    + (customer.get("email").isJsonNull() ? "NULL"
                                            : "'" + customer.get("email").getAsString().replace("'", "''") + "'")
                                    + ", "
                                    + (customer.get("city").isJsonNull() ? "NULL"
                                            : "'" + customer.get("city").getAsString().replace("'", "''") + "'")
                                    + ", "
                                    + (customer.get("address").isJsonNull() ? "NULL"
                                            : "'" + customer.get("address").getAsString().replace("'", "''") + "'")
                                    + ", "
                                    + customer.get("due_amount").getAsDouble() + ", "
                                    + (customerCode == null ? "NULL" : "'" + customerCode.replace("'", "''") + "'")
                                    + ", 1) " +
                                    "ON DUPLICATE KEY UPDATE `customer_name`=VALUES(`customer_name`), `contact_number`=VALUES(`contact_number`), "
                                    +
                                    "`email`=VALUES(`email`), `city_name`=VALUES(`city_name`), `address_line_1`=VALUES(`address_line_1`), "
                                    +
                                    "`due_amount`=VALUES(`due_amount`), `is_synced`=1";
                            MySQL.execute(sql);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void pushCustomers() {
        try {
            java.sql.ResultSet rs = MySQL.execute("SELECT * FROM `customers` WHERE `is_synced` = 0");
            while (rs.next()) {
                int localId = rs.getInt("id");
                System.out.println("Processing customer sync for local ID: " + localId);
                try {
                    JsonObject custObj = new JsonObject();
                    custObj.addProperty("customer_name", rs.getString("customer_name"));
                    custObj.addProperty("contact_number", rs.getString("contact_number"));
                    String email = rs.getString("email");
                    custObj.addProperty("email", (email == null || email.trim().isEmpty()) ? null : email);

                    String city = rs.getString("city_name");
                    custObj.addProperty("city_name", (city == null || city.trim().isEmpty()) ? null : city);

                    String address = rs.getString("address_line_1");
                    custObj.addProperty("address_line_1",
                            (address == null || address.trim().isEmpty()) ? null : address);

                    custObj.addProperty("due_amount", rs.getDouble("due_amount"));

                    // Metadata - safely extract
                    try {
                        custObj.addProperty("customer_code", rs.getString("customer_code"));
                        custObj.addProperty("pos_system_id", rs.getString("pos_system_id"));
                        int createdByUserId = rs.getInt("created_by_user_id");
                        if (rs.wasNull() || createdByUserId == 0) {
                            custObj.addProperty("created_by_user_id", currentUserId);
                        } else {
                            custObj.addProperty("created_by_user_id", createdByUserId);
                        }
                    } catch (Exception excol) {
                        // Metadata columns might be missing
                    }

                    String payload = new Gson().toJson(custObj);
                    System.out.println("Syncing Customer Payload: " + payload);
                    String response = postToApi("/customers", payload);
                    System.out.println("Syncing Customer Response: " + response);

                    if (response != null
                            && (response.contains("\"success\":true") || response.contains("\"success\": true"))) {

                        // Extract server-generated ID for reconciliation
                        try {
                            JsonObject respObj = new Gson().fromJson(response, JsonObject.class);
                            if (respObj.has("data") && !respObj.get("data").isJsonNull()) {
                                JsonObject data = respObj.getAsJsonObject("data");
                                int serverId = data.get("id").getAsInt();

                                if (serverId != localId) {
                                    System.out.println("Reconciling ID: Local " + localId + " -> Server " + serverId);
                                    // Update customers table ID (needs to be done carefully due to PK)
                                    // First, update foreign keys to avoid orphan records during the swap if we were
                                    // to delete/re-insert,
                                    // but MySQL lets us update PK if no conflicts.
                                    MySQL.execute("UPDATE `sales` SET `customers_id` = " + serverId
                                            + " WHERE `customers_id` = " + localId);
                                    MySQL.execute("UPDATE `customers` SET `id` = " + serverId
                                            + ", `is_synced` = 1 WHERE `id` = " + localId);
                                } else {
                                    MySQL.execute("UPDATE `customers` SET `is_synced` = 1 WHERE `id` = " + localId);
                                }
                            } else {
                                MySQL.execute("UPDATE `customers` SET `is_synced` = 1 WHERE `id` = " + localId);
                            }
                        } catch (Exception exresp) {
                            System.err.println(
                                    "Error parsing sync response for ID reconciliation: " + exresp.getMessage());
                            MySQL.execute("UPDATE `customers` SET `is_synced` = 1 WHERE `id` = " + localId);
                        }

                        System.out.println("Customer " + localId + " synced successfully.");
                    } else {
                        System.err.println("Failed to sync customer " + localId + ". Response: " + response);
                    }
                } catch (Exception exrs) {
                    System.err.println("Error processing customer row " + localId + ": " + exrs.getMessage());
                }
            }
        } catch (Exception e) {
            // Table might not have is_synced yet, ignore
        }
    }

    public static synchronized int pushSales() {
        int syncedCount = 0;
        try {
            String sql = "SELECT s.*, p.grand_total, p.paid_amount, p.due_amount, p.received_amount, p.payment_type, p.payment_status, c.customer_code "
                    +
                    "FROM `sales` s " +
                    "JOIN `payments` p ON s.id = p.sales_id " +
                    "LEFT JOIN `customers` c ON s.customers_id = c.id " +
                    "WHERE s.is_synced = 0";
            java.sql.ResultSet rs = MySQL.execute(sql);
            while (rs.next()) {
                int saleId = rs.getInt("id");
                JsonObject saleObj = new JsonObject();
                saleObj.addProperty("sales_code", rs.getString("sales_code"));

                String customerCode = rs.getString("customer_code");
                saleObj.addProperty("customer_code", customerCode);

                saleObj.addProperty("selectedCustomerId", rs.getInt("customers_id"));
                saleObj.addProperty("grand_total", rs.getDouble("grand_total"));
                saleObj.addProperty("p_amount", rs.getDouble("paid_amount"));
                saleObj.addProperty("r_amount", rs.getDouble("received_amount"));
                saleObj.addProperty("p_type", rs.getString("payment_type"));
                saleObj.addProperty("p_status", rs.getString("payment_status"));
                saleObj.addProperty("selected_price_type", "retail");
                saleObj.addProperty("warranty_period", rs.getString("warranty_period"));
                saleObj.addProperty("warranty_card_no", rs.getString("warranty_card_no"));
                saleObj.addProperty("pos_system_id", rs.getString("pos_system_id"));

                try {
                    String createdAt = rs.getString("created_at");
                    if (createdAt != null && !createdAt.isEmpty()) {
                        saleObj.addProperty("created_at", createdAt);
                    }
                } catch (Exception ex) {
                }

                JsonArray itemsArr = new JsonArray();
                String itemSql = "SELECT * FROM `sales_items` WHERE `sales_id` = " + saleId;
                java.sql.ResultSet rsItems = MySQL.execute(itemSql);
                while (rsItems.next()) {
                    JsonObject itemObj = new JsonObject();
                    itemObj.addProperty("id", rsItems.getInt("items_id")); // Changed from items_id to id
                    itemObj.addProperty("quantity", rsItems.getInt("quantity"));
                    itemObj.addProperty("price", rsItems.getDouble("price"));
                    itemsArr.add(itemObj);
                }
                if (itemsArr.size() == 0) {
                    System.out.println("Skipping Sale " + rs.getString("sales_code") + " because it has no items.");
                    continue;
                }
                saleObj.add("items", itemsArr);

                // Map customer 0 to 1 (Guest)
                int customerId = rs.getInt("customers_id");
                if (customerId == 0) {
                    customerId = 1;
                }
                saleObj.addProperty("selectedCustomerId", customerId);

                String payload = new Gson().toJson(saleObj);
                System.out.println("Syncing Sale: " + payload);
                String response = postToApi("/sales", payload);
                if (response != null
                        && (response.contains("\"success\":true") || response.contains("\"success\": true"))) {
                    MySQL.execute("UPDATE `sales` SET `is_synced` = 1 WHERE `id` = " + saleId);
                    syncedCount++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return syncedCount;
    }

    private static String fetchFromApi(String endpoint) {
        try {
            URL url = URI.create(API_BASE_URL + endpoint).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() == 200) {
                Scanner scanner = new Scanner(conn.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext())
                    response.append(scanner.next()).append(" ");
                scanner.close();
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String postToApi(String endpoint, String jsonBody) {
        try {
            URL url = URI.create(API_BASE_URL + endpoint).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            try (java.io.OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            if (conn.getResponseCode() == 201 || conn.getResponseCode() == 200) {
                Scanner scanner = new Scanner(conn.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext())
                    response.append(scanner.next()).append(" ");
                scanner.close();
                return response.toString();
            } else {
                // Log error details
                System.err.println("API Error: " + conn.getResponseCode() + " for endpoint: " + endpoint);
                try (Scanner scanner = new Scanner(conn.getErrorStream())) {
                    StringBuilder errorResponse = new StringBuilder();
                    while (scanner.hasNext())
                        errorResponse.append(scanner.next()).append(" ");
                    System.err.println("Error Body: " + errorResponse.toString());
                } catch (Exception e) {
                    System.err.println("Could not read error body: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
