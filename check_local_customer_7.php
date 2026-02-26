<?php
// Local check script for ID 7
$conn = new mysqli("localhost", "root", "Ravindu0627@", "hypermart_local");
if ($conn->connect_error) die("Connection failed: " . $conn->connect_error);

$res = $conn->query("SELECT * FROM customers WHERE id = 7");
if ($row = $res->fetch_assoc()) {
    foreach ($row as $k => $v) {
        echo "$k: $v\n";
    }
} else {
    echo "Customer 7 not found.\n";
}
