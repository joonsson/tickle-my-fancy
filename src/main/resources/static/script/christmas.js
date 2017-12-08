const mariah = new Audio("./sound/MariahCarey.mp3");
const fariydust = new Audio("./sound/Fairydust.mp3");
document.addEventListener("DOMContentLoaded", (event) => {
    //mariah.play();
    const card = document.getElementsByClassName("card")[0];
    card.addEventListener("click", () => {
        fariydust.play();
        card.classList.toggle("flipped");
    });
});
document.getElementById("about").addEventListener("click", () => {
    mariah.pause();
    document.getElementById("julis").className = "hidden";
});
