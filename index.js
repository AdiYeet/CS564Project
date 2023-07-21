function search() {
    var searchTerm = document.getElementById("search").value;
    var table = document.getElementById("table");
    var rows = table.getElementsByTagName("tr");

    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];
        var cells = row.getElementsByTagName("td");
        var genre = cells[0].textContent;
        var rating = cells[1].textContent;
        var publisher = cells[2].textContent;
        var developer = cells[3].textContent;

        if (genre.toLowerCase().includes(searchTerm.toLowerCase()) ||
            rating.toLowerCase().includes(searchTerm.toLowerCase()) ||
            publisher.toLowerCase().includes(searchTerm.toLowerCase()) ||
            developer.toLowerCase().includes(searchTerm.toLowerCase())) {
            row.style.display = "";
        } else {
            row.style.display = "none";
        }
    }
}

document.getElementById("search").addEventListener("keyup", search);
function changeFilter() {
    var filterOption = document.getElementById("filter-options").value;
    var searchTerm = document.getElementById("search").value;
    var table = document.getElementById("table");
    var rows = table.getElementsByTagName("tr");

    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];
        var cells = row.getElementsByTagName("td");
        var genre = cells[0].textContent;
        var rating = cells[1].textContent;
        var publisher = cells[2].textContent;
        var developer = cells[3].textContent;

        if (filterOption == "genre" && genre.toLowerCase().includes(searchTerm.toLowerCase())) {
            row.style.display = "";
        } else if (filterOption == "rating" && rating.toLowerCase().includes(searchTerm.toLowerCase())) {
            row.style.display = "";
        } else if (filterOption == "publisher" && publisher.toLowerCase().includes(searchTerm.toLowerCase())) {
            row.style.display = "";
        } else if (filterOption == "developer" && developer.toLowerCase().includes(searchTerm.toLowerCase())) {
            row.style.display = "";
        } else {
            row.style.display = "none";
        }
    }
}

document.getElementById("filter").addEventListener("click", changeFilter);