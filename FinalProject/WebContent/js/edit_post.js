 function showEdit(x){
    document.getElementById("editForm" + x).style.display = "block";
    document.getElementById("editLink" + x).style.display = "none";
    document.getElementById("content" + x).focus;
    var edit = document.getElementById("edit" + x).innerHTML.trim();
    document.getElementById("content" + x).innerHTML = edit;
    }

 function closeEdit(x){
    document.getElementById("editForm" + x).style.display = "none";
    document.getElementById("editLink" + x).style.display = "block";
    }