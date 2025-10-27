<!DOCTYPE html>
<html>
<head>
<title> URL Shortener Page </title>
</head>
<body bgcolor="Grey">

<center>
<h2> URL Shortener Service </h2>
<hr color="Black">

<form action="shorten" method="post">
<pre>
Original URL: <input type="text" name="originalUrl">

Max Clicks: <input type="number" name="maxClicks" value="5">

Duration (Days): <input type="number" name="durationDays" value="10"> 


<input type="submit" value="Generate Short URL">
</pre>
</form>

</center>
</body>
</html>
