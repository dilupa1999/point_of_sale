<?php
// Local check script - JSON dump
$conn = new mysqli("localhost", "root", "Ravindu0627@", "hypermart_local");
if ($conn->connect_error) die("Connection failed: " . $conn->connect_error);

$res = $conn->query("SELECT * FROM customers WHERE id = 8");
if ($row = $res->fetch_assoc()) {
    file_put_contents("row_8_dump.json", json_encode($row, JSON_PRETTY_PRINT));
    echo "Dumped to row_8_dump.json\n";
} else {
    echo "Customer 8 not found.\n";
}
