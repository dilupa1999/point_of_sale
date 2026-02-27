package com.pos.service;

import model.MySQL;

public class SeedData {
    public static void main(String[] args) {
        seed();
    }

    public static void seed() {
        try {
            // Seed Items
            MySQL.execute(
                    "INSERT INTO `items` (`id`, `item_code`, `item_name`, `purchase_price`, `retail_price`, `wholesale_price`, `barcode`, `quantity`) VALUES "
                            +
                            "(1, 'I001', 'Fresh Milk 1L', 200, 250, 240, '1001', 50), " +
                            "(2, 'I002', 'White Bread', 150, 180, 175, '1002', 30), " +
                            "(3, 'I003', 'Sugar 1kg', 300, 350, 340, '1003', 100), " +
                            "(4, 'I004', 'Tea Bags 50s', 400, 480, 460, '1004', 40), " +
                            "(5, 'I005', 'Butter 200g', 500, 600, 580, '1005', 25) " +
                            "ON DUPLICATE KEY UPDATE `item_name`=VALUES(`item_name`), `retail_price`=VALUES(`retail_price`)");

            // Ensure is_synced exists in sales
            try {
                MySQL.execute("ALTER TABLE `sales` ADD COLUMN `is_synced` TINYINT(1) DEFAULT 0");
            } catch (Exception e) {
                // Ignore if column already exists
            }

            System.out.println("Seeding completed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
