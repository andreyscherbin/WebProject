$(document).ready(function(){
 $.ajaxSetup({ cache: false });
 $('#search').keyup(function(){
  $('#result').html('');
  $('#state').val('');
  var searchField = $('#search').val();
  var expression = new RegExp(searchField, "i");
  $.getJSON('search', function(data) {
   $.each(data, function(key, value){
    if (value.header.search(expression) != -1)
    {
     $('#result').append('<li class="list-group-item link-class"> '+value.header+'</li>');
    }
   });   
  });
 });
 
 $('#result').on('click', 'li', function() {
  var click_text = $(this).text().split('|');
  $('#search').val($.trim(click_text[0]));
  $("#result").html('');
 });
});