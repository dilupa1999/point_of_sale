package com.pos.service;

import java.io.*;
import java.util.Properties;

public class ConfigService {
    private static final String CONFIG_FILE = "config.properties";
    private static Properties props = new Properties();

    static {
        loadConfig();
    }

    private static void loadConfig() {
        File file = new File(CONFIG_FILE);
        if (!file.exists()) {
            createDefaultConfig();
        }
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            props.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void createDefaultConfig() {
        String computerName = "UNKNOWN";
        try {
            computerName = java.net.InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
        }

        props.setProperty("pos_system_id", "POS-" + computerName.toUpperCase());
        try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
            props.store(output, "POS System Configuration");
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static String getPosSystemId() {
        return props.getProperty("pos_system_id", "POS-UNKNOWN");
    }
}
