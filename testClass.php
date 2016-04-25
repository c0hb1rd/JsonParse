{{   }}<?php
    header("Content-type: text/html; charset=utf-8");
    header("Content-type: text/json; charset=utf-8");

    $myDb = mysql_connect("localhost", "root", "");

    $STUDENT_ID = $_GET['id'];

    if (!$myDb) {
        die("Could not connect:" . mysql_error());
    }

    mysql_set_charset("utf8");

    mysql_select_db("kaoshi01", $myDb);
    $result = mysql_query("SELECT * FROM tableName where condition='$STUDENT_ID'");

    $myData = array();
    $i = 0;
    while($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
        $myData[$i] = $row;
        $i++;
    }

    for($j = 0; $j < $i; $j++) {
        foreach($myData[$j] as $key => $value) {
            $myData[$j][$key] = urlencode($value);
        }
    }

    echo urldecode(json_encode($myData));

    mysql_close($myDb);


?>
