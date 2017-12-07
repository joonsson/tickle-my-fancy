document.addEventListener("DOMContentLoaded", (event) => {
    const card = document.getElementsByClassName("card")[0];
    card.addEventListener("click", () => {
        card.classList.toggle("flipped");
    });
});
