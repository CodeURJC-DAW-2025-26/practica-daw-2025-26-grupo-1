/*let page = 0;

document.getElementById("loadItems").addEventListener("click", loadMoreItems);

function loadMoreItems() {
    const spinner = document.getElementById("spinner");
    const button = document.getElementById("loadItems");

    spinner.style.display = "block";

    //const sectionName = window.location.pathname.split("/")[2];

    window.addEventListener("DOMContentLoaded", () => {
    loadMoreItems(); // carga página 0 al inicio
});

    fetch(`/section/${sectionName}/more/${page}`)
        .then(response => response.text())
        .then(html => {
            if (!html || html.trim() === "") {

                button.style.display = "none";
                spinner.style.display = "none";
                return;
            }

            const container = document.getElementById("itemsContainer");
            container.insertAdjacentHTML("beforeend", html);

            page++;
            spinner.style.display = "none";
        })
        .catch(() => {
            spinner.style.display = "none";
        });
}

//window.addEventListener("DOMContentLoaded", loadMoreItems); */


let page = 1;
const sectionName = window.location.pathname.split("/")[2];
const container = document.getElementById("itemsContainer");
const spinner = document.getElementById("spinner");
const button = document.getElementById("loadItems");

async function loadMoreItems() {
    spinner.style.display = "block";
    button.disabled = true;

    const response = await fetch(`/section/${sectionName}/more/${page}`);
    const html = await response.text();

    if (!html.trim()) {
        // No hay más objetos
        button.style.display = "none";
    } else {
        container.insertAdjacentHTML("beforeend", html);
        page++; // incrementa solo si hay contenido
        button.disabled = false;
    }

    spinner.style.display = "none";
}

// Carga inicial al abrir la página
window.addEventListener("DOMContentLoaded", loadMoreItems);

// Carga siguiente página al pulsar
button.addEventListener("click", loadMoreItems);