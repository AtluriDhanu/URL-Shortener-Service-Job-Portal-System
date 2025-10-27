<html>
<head>
<title> Short URL Result Page </title>
<style>
body {
    background-color: Grey;
    color: black;
    text-align: center;
    font-size: 20px;
}
a {
    color: lightblue;
}
</style>
</head>
<body bgcolor="Grey">

<center>
<h2>Your Short URL:</h2>
<p><a href="${shortUrl}" target="_blank">${shortUrl}</a></p>

<h3> QR Code: </h3>
<img src="${qrPath}" alt="QR Code" width="200" height="200"/>
</center>

</body>
</html>
