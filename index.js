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

const searchBar = document.getElementById('search-bar');
const filterOptions = document.getElementById('filter-options');
const table = document.getElementById('table');

// Function to filter the table rows
function filterRows(searchTerm, filterType) {
    const rows = table.querySelectorAll('tr');
    const filteredRows = [];

    for (let i = 0; i < rows.length; i++) {
        const row = rows[i];
        const gameName = row.querySelector('td:first-child').textContent;

        if (searchTerm.length === 0 || gameName.toLowerCase().includes(searchTerm.toLowerCase())) {
            if (filterType === 'genre') {
                const genre = row.querySelector('td:nth-child(2)').textContent;
                if (genre.toLowerCase().includes(filterType.toLowerCase())) {
                    filteredRows.push(row);
                }
            } else if (filterType === 'rating') {
                const rating = row.querySelector('td:nth-child(3)').textContent;
                if (rating.toLowerCase().includes(filterType.toLowerCase())) {
                    filteredRows.push(row);
                }
            } else if (filterType === 'publisher') {
                const publisher = row.querySelector('td:nth-child(4)').textContent;
                if (publisher.toLowerCase().includes(filterType.toLowerCase())) {
                    filteredRows.push(row);
                }
            } else if (filterType === 'developer') {
                const developer = row.querySelector('td:nth-child(5)').textContent;
                if (developer.toLowerCase().includes(filterType.toLowerCase())) {
                    filteredRows.push(row);
                }
            }
        }
    }

    table.innerHTML = '';
    filteredRows.forEach(row => table.appendChild(row));
}

// Event listener for the search bar
searchBar.addEventListener('input', (event) => {
    const searchTerm = event.target.value;
    filterRows(searchTerm, filterOptions.value);
});

// Event listener for the filter options select element
filterOptions.addEventListener('change', (event) => {
    const filterType = event.target.value;
    const searchTerm = searchBar.value;
    filterRows(searchTerm, filterType);
});


document.getElementById("filter").addEventListener("click", changeFilter);