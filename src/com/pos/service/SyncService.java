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

    public static void setAccessToken(String token) {
        accessToken = token;
    }

    public static void startSyncThread() {
        new Thread(() -> {
            while (true) {
                try {
                    if (NetworkService.isInternetAvailable() && !accessToken.isEmpty()) {
                        syncItems();
                        syncCustomers();
                        pushSales();
                    }
                    Thread.sleep(300000); // Sync every 5 minutes
                } catch (Exception e) {
                    e.printStackTrace();
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
                if (obj.get("success").getAsBoolean()) {
                    JsonArray data = obj.getAsJsonArray("data");
                    for (JsonElement el : data) {
                        JsonObject item = el.getAsJsonObject();
                        String sql = "INSERT INTO `items` (`id`, `item_code`, `item_name`, `purchase_price`, `retail_price`, `wholesale_price`, `barcode`, `quantity`) "
                                +
                                "VALUES (" + item.get("id").getAsInt() + ", '" + item.get("item_code").getAsString()
                                + "', '" + item.get("item_name").getAsString().replace("'", "''") + "', " +
                                item.get("purchase_price").getAsDouble() + ", " +
                                item.get("retail_price").getAsDouble() + ", "
                                + item.get("wholesale_price").getAsDouble() + ", '"
                                + (item.get("barcode").isJsonNull() ? "" : item.get("barcode").getAsString()) + "', " +
                                item.get("quantity").getAsInt() + ") " +
                                "ON DUPLICATE KEY UPDATE `item_name`=VALUES(`item_name`), `purchase_price`=VALUES(`purchase_price`), `retail_price`=VALUES(`retail_price`), "
                                +
                                "`wholesale_price`=VALUES(`wholesale_price`), `barcode`=VALUES(`barcode`), `quantity`=VALUES(`quantity`)";
                        MySQL.execute(sql);
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
                if (obj.get("success").getAsBoolean()) {
                    JsonArray data = obj.getAsJsonArray("data");
                    for (JsonElement el : data) {
                        JsonObject customer = el.getAsJsonObject();
                        String sql = "INSERT INTO `customers` (`id`, `customer_name`, `contact_number`) " +
                                "VALUES (" + customer.get("id").getAsInt() + ", '"
                                + customer.get("customer_name").getAsString().replace("'", "''") + "', '"
                                + customer.get("contact_number").getAsString() + "') " +
                                "ON DUPLICATE KEY UPDATE `customer_name`=VALUES(`customer_name`), `contact_number`=VALUES(`contact_number`)";
                        MySQL.execute(sql);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void pushSales() {
        try {
            String sql = "SELECT s.*, p.grand_total, p.paid_amount, p.due_amount, p.received_amount, p.payment_type, p.payment_status "
                    +
                    "FROM `sales` s " +
                    "JOIN `payments` p ON s.id = p.sales_id " +
                    "WHERE s.is_synced = 0";
            java.sql.ResultSet rs = MySQL.execute(sql);
            while (rs.next()) {
                int saleId = rs.getInt("id");
                JsonObject saleObj = new JsonObject();
                saleObj.addProperty("sales_code", rs.getString("sales_code"));
                saleObj.addProperty("selectedCustomerId", rs.getInt("customers_id"));
                saleObj.addProperty("grand_total", rs.getDouble("grand_total"));
                saleObj.addProperty("p_amount", rs.getDouble("paid_amount"));
                saleObj.addProperty("r_amount", rs.getDouble("received_amount"));
                saleObj.addProperty("p_type", rs.getString("payment_type"));
                saleObj.addProperty("p_status", rs.getString("payment_status"));
                saleObj.addProperty("selected_price_type", "retail"); // Column removed, default to retail
                saleObj.addProperty("warranty_period", rs.getString("warranty_period"));
                saleObj.addProperty("warranty_card_no", rs.getString("warranty_card_no"));
                saleObj.addProperty("pos_system_id", rs.getString("pos_system_id"));

                JsonArray itemsArr = new JsonArray();
                String itemSql = "SELECT * FROM `sales_items` WHERE `sales_id` = " + saleId;
                java.sql.ResultSet rsItems = MySQL.execute(itemSql);
                while (rsItems.next()) {
                    JsonObject itemObj = new JsonObject();
                    itemObj.addProperty("items_id", rsItems.getInt("items_id"));
                    itemObj.addProperty("quantity", rsItems.getInt("quantity"));
                    itemObj.addProperty("price", rsItems.getDouble("price"));
                    itemsArr.add(itemObj);
                }
                saleObj.add("items", itemsArr);

                String response = postToApi("/sales", new Gson().toJson(saleObj));
                if (response != null
                        && (response.contains("\"success\":true") || response.contains("\"success\": true"))) {
                    MySQL.execute("UPDATE `sales` SET `is_synced` = 1 WHERE `id` = " + saleId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
