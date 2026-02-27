<?php
// Local check script - list more
$conn = new mysqli("localhost", "root", "Ravindu0627@", "hypermart_local");
if ($conn->connect_error) die("Connection failed: " . $conn->connect_error);

$res = $conn->query("SELECT id, customer_name, customer_code, is_synced FROM customers ORDER BY id DESC LIMIT 10");
while ($row = $res->fetch_assoc()) {
    echo "ID: {$row['id']} | Name: {$row['customer_name']} | Code: {$row['customer_code']} | Synced: {$row['is_synced']}\n";
}
