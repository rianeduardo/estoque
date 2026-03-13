const toggle = document.querySelector(".menu-toggle");
const nav = document.querySelector(".senai-nav");

toggle.addEventListener("click", () => {
    nav.classList.toggle("active");
});