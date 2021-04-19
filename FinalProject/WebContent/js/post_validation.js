function validate()
{
  var content = document.getElementById('content').value;
  var contentRGEX = /^[0-9A-z]$/;
  var contentResult = contentRGEX.test(content);
  alert("phone:"+contentResult );
}