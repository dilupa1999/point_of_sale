<?php
// Local check script
$conn = new mysqli("localhost", "root", "Ravindu0627@", "hypermart_local");
if ($conn->connect_error) die("Connection failed: " . $conn->connect_error);

$res = $conn->query("SELECT id, customer_name, is_synced, customer_code, created_by_user_id FROM customers ORDER BY id DESC LIMIT 5");
while ($row = $res->fetch_assoc()) {
    echo "ID: {$row['id']} | Name: {$row['customer_name']} | Synced: {$row['is_synced']} | Code: {$row['customer_code']} | User: {$row['created_by_user_id']}\n";
}
