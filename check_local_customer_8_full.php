<?php
// Local check script - full print_r
$conn = new mysqli("localhost", "root", "Ravindu0627@", "hypermart_local");
if ($conn->connect_error) die("Connection failed: " . $conn->connect_error);

$res = $conn->query("SELECT * FROM customers WHERE id = 8");
if ($row = $res->fetch_assoc()) {
    print_r($row);
} else {
    echo "Customer 8 not found.\n";
}
