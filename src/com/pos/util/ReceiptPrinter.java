package com.pos.util;

import java.awt.*;
import java.awt.print.*;
import java.util.List;
import java.util.Map;

public class ReceiptPrinter implements Printable {

    private Map<String, Object> saleData;

    public ReceiptPrinter(Map<String, Object> saleData) {
        this.saleData = saleData;
    }

    public static void printReceipt(Map<String, Object> saleData) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new ReceiptPrinter(saleData));
        try {
            // job.print(); // Uncomment this for real printing
            // For testing/demonstration, we might want to show a dialog or similar
            // But the user asked to "Do that" so we should aim for direct printing if
            // possible
            // or at least have the logic ready.
            if (job.printDialog()) {
                job.print();
            }
        } catch (PrinterException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        int y = 20;
        int width = 200; // Approx width for 80mm at 72dpi (226 pts max)

        Font fontHeader = new Font("Segoe UI", Font.BOLD, 12);
        Font fontSubHeader = new Font("Segoe UI", Font.PLAIN, 8);
        Font fontTable = new Font("Segoe UI", Font.PLAIN, 7);
        Font fontBold = new Font("Segoe UI", Font.BOLD, 7);

        // Header
        g2d.setFont(fontHeader);
        drawCenteredString(g2d, (String) saleData.get("shop_name"), width, y);
        y += 15;
        g2d.setFont(fontSubHeader);
        drawCenteredString(g2d, (String) saleData.get("address"), width, y);
        y += 10;
        drawCenteredString(g2d, (String) saleData.get("phone"), width, y);
        y += 10;

        String type = (String) saleData.getOrDefault("type", "SALE");
        if ("PAYMENT".equals(type)) {
            drawCenteredString(g2d, "========== PAYMENT RECEIPT ==========", width, y);
        } else {
            drawCenteredString(g2d, "SALE CODE: " + saleData.get("sales_code"), width, y);
        }
        y += 15;

        // Metadata
        g2d.setFont(fontTable);
        g2d.drawString("DATE: " + saleData.get("date"), 5, y);
        g2d.drawString("TIME: " + saleData.get("time"), 110, y);
        y += 10;
        g2d.drawString("CUS: " + saleData.get("customer"), 5, y);
        g2d.drawString("PTYPE: " + saleData.get("ptype"), 110, y);
        y += 10;
        g2d.drawString("CUSID: " + saleData.getOrDefault("customerId", "-"), 5, y);
        g2d.drawString("USER: " + saleData.get("user"), 110, y);
        y += 12;

        if ("PAYMENT".equals(type)) {
            // Payment Receipt Details
            g2d.drawLine(5, y, width - 5, y);
            y += 15;
            drawSummaryRow(g2d, "DUE AMOUNT", (String) saleData.get("due_amount"), width, y);
            y += 12;
            drawSummaryRow(g2d, "AMOUNT PAID", (String) saleData.get("amount_paid"), width, y);
            y += 12;
            drawSummaryRow(g2d, "REMAINING BALANCE", (String) saleData.get("remaining_balance"), width, y);
            y += 15;
            g2d.drawLine(5, y, width - 5, y);
            y += 15;

            // Footer for Payment
            g2d.setFont(fontBold);
            drawCenteredString(g2d, "--------- THANK YOU! VISIT AGAIN ---------", width, y);
            y += 12;
            g2d.setFont(fontTable);
            drawCenteredString(g2d, "# No warranty.", width, y);
            y += 10;
            drawCenteredString(g2d, "# We are not responsible for colour changes after purchased.", width, y);
            y += 10;
            drawCenteredString(g2d, "# Bill must be produced for claims....", width, y);
            y += 15;
            g2d.drawLine(5, y, width - 5, y);
            y += 15;
            drawCenteredString(g2d, "Powered by Silicon Radon Networks (Pvt) Ltd.", width, y);

        } else {
            // Original Sale Table Header
            g2d.drawLine(5, y, width - 5, y);
            y += 10;
            g2d.setFont(fontBold);
            g2d.drawString("LN", 5, y);
            g2d.drawString("ITEM", 25, y);
            g2d.drawString("QTY", 75, y);
            g2d.drawString("M.PRICE", 100, y);
            g2d.drawString("O.PRICE", 140, y);
            g2d.drawString("AMOUNT", 175, y);
            y += 10;
            g2d.drawLine(5, y, width - 5, y);
            y += 10;

            // Items
            g2d.setFont(fontTable);
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> items = (List<Map<String, Object>>) saleData.get("items");
            for (Map<String, Object> item : items) {
                g2d.drawString(String.valueOf(item.get("ln")), 5, y);
                String itemName = (String) item.get("item");
                if (itemName.length() > 20)
                    itemName = itemName.substring(0, 18) + "..";
                g2d.drawString(itemName, 25, y);
                y += 8;
                g2d.drawString(item.get("qty") + " " + item.get("unit"), 35, y);
                g2d.drawString(String.valueOf(item.get("market_price")), 100, y);
                g2d.drawString(String.valueOf(item.get("our_price")), 140, y);
                g2d.drawString(String.valueOf(item.get("amount")), 175, y);
                y += 10;
            }
            g2d.drawLine(5, y, width - 5, y);
            y += 12;

            // Calculation Details
            drawSummaryRow(g2d, "TOTAL", (String) saleData.get("total"), width, y);
            y += 10;
            drawSummaryRow(g2d, "NET TOTAL", (String) saleData.get("net_total"), width, y);
            y += 10;
            drawSummaryRow(g2d, "RECEIVED", (String) saleData.get("received"), width, y);
            y += 10;
            drawSummaryRow(g2d, "PAID", (String) saleData.get("paid"), width, y);
            y += 10;
            drawSummaryRow(g2d, "CHANGE", (String) saleData.get("change"), width, y);
            y += 10;
            drawSummaryRow(g2d, "TOTAL DUE", (String) saleData.get("due"), width, y);
            y += 10;
            drawSummaryRow(g2d, "NO OF ITEMS", (String) saleData.get("count"), width, y);
            y += 15;

            // Footer
            g2d.setFont(fontBold);
            drawCenteredString(g2d, "PLEASE CHECK THE RECEIVED GOODS", width, y);
            y += 10;
            drawCenteredString(g2d, "THANKS FOR SHOPPING WITH US!", width, y);
            y += 10;
            g2d.drawLine(5, y, width - 5, y);
            y += 10;
            g2d.setFont(fontTable);
            drawCenteredString(g2d, "Powered by Silicon Radon Networks (Pvt) Ltd.", width, y);
        }

        return PAGE_EXISTS;
    }

    private void drawCenteredString(Graphics2D g, String text, int width, int y) {
        FontMetrics fm = g.getFontMetrics();
        int x = (width - fm.stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }

    private void drawSummaryRow(Graphics2D g, String label, String value, int width, int y) {
        g.drawString(label, 5, y);
        g.drawString(":", 110, y);
        FontMetrics fm = g.getFontMetrics();
        g.drawString(value, width - 10 - fm.stringWidth(value), y);
    }
}
