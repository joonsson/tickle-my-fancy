let logo = document.getElementById("logo");
let searchBtn = document.getElementById("searchButton");
let searchTerm = document.getElementById("searchTerm");

logo.addEventListener("click", () => {
    window.location.href = "/";
});

searchBtn.addEventListener("click", search);

searchTerm.addEventListener("keypress", (e) => {
    const key = e.which || e.keyCode;
    if (key === 13) { // 13 is Enter
      search();
    }
});

function search() {
    window.location.href = "/search?srch=" +
    encodeURIComponent((document.getElementById("searchTerm") as HTMLInputElement).value);
}
