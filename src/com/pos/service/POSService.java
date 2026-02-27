package com.pos.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.MySQL;

public class POSService {

    public static class Item {
        public int id;
        public String code;
        public String name;
        public double retailPrice;
        public double wholesalePrice;
        public String barcode;

        public Item(int id, String code, String name, double retailPrice, double wholesalePrice, String barcode) {
            this.id = id;
            this.code = code;
            this.name = name;
            this.retailPrice = retailPrice;
            this.wholesalePrice = wholesalePrice;
            this.barcode = barcode;
        }
    }

    public static class Customer {
        public int id;
        public String name;
        public String mobile;

        public Customer(int id, String name, String mobile) {
            this.id = id;
            this.name = name;
            this.mobile = mobile;
        }
    }

    public static List<Item> searchItems(String query) {
        List<Item> items = new ArrayList<>();
        try {
            String sql = "SELECT * FROM `items` WHERE LOWER(`item_name`) LIKE LOWER('%" + query
                    + "%') OR LOWER(`item_code`) LIKE LOWER('%" + query
                    + "%') OR LOWER(`barcode`) LIKE LOWER('%" + query + "%')";
            ResultSet rs = MySQL.execute(sql);
            while (rs.next()) {
                items.add(new Item(
                        rs.getInt("id"),
                        rs.getString("item_code"),
                        rs.getString("item_name"),
                        rs.getDouble("retail_price"),
                        rs.getDouble("wholesale_price"),
                        rs.getString("barcode")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public static Item findItemByBarcode(String barcode) {
        try {
            ResultSet rs = MySQL.execute(
                    "SELECT * FROM `items` WHERE `barcode` = '" + barcode + "' OR `item_code` = '" + barcode + "'");
            if (rs.next()) {
                return new Item(
                        rs.getInt("id"),
                        rs.getString("item_code"),
                        rs.getString("item_name"),
                        rs.getDouble("retail_price"),
                        rs.getDouble("wholesale_price"),
                        rs.getString("barcode"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Customer> searchCustomers(String query) {
        List<Customer> customers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM `customers` WHERE LOWER(`customer_name`) LIKE LOWER('%" + query
                    + "%') OR `contact_number` LIKE '%" + query + "%'";
            ResultSet rs = MySQL.execute(sql);
            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getString("contact_number")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }
}
