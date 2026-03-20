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

//window.addEventListener("DOMContentLoaded", loadMoreItems); 


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


async function loadMoreItems() {
    spinner.style.display = "block";
    button.disabled = true;

    // 1. Detectamos si en la barra del navegador hay una categoría (ej: ?category=Mar)
    const urlParams = new URLSearchParams(window.location.search);
    const category = urlParams.get('category');

    // 2. Construimos la ruta base que ya tienes
    let url = `/section/${sectionName}/more/${page}`;

    // 3. Si hay categoría, le añadimos el "?" a la ruta
    if (category) {
        url = url + "?category=" + category;
    }

    // 4. Hacemos la petición
    const response = await fetch(url);
    const html = await response.text();

    if (!html.trim()) {
        button.style.display = "none";
    } else {
        container.insertAdjacentHTML("beforeend", html);
        page++; 
        button.disabled = false;
    }

    spinner.style.display = "none";
}


// Carga inicial al abrir la página
//window.addEventListener("DOMContentLoaded", loadMoreItems);

// Carga siguiente página al pulsar
button.addEventListener("click", loadMoreItems); */









// 1. Configuración inicial
let page = 1; // Empezamos en 1 porque Java ya pintó la 0
const sectionName = window.location.pathname.split("/")[2];
const container = document.getElementById("itemsContainer");
const spinner = document.getElementById("spinner");
const button = document.getElementById("loadItems");

async function loadMoreItems() {
    // Protección para no mandar mil peticiones si el usuario clickea mucho
    if (button.disabled) return;
    
    spinner.style.display = "block";
    button.disabled = true;

    // 2. Gestión de la Categoría
    const urlParams = new URLSearchParams(window.location.search);
    const category = urlParams.get('category');

    // Construimos la URL
    let url = `/section/${sectionName}/more/${page}`;
    if (category) {
        url += "?category=" + category;
    }

    try {
        const response = await fetch(url);
        const html = await response.text();

        // 3. Lógica de inserción y parada
        // Si el HTML es muy corto (ej: menos de 10 caracteres), es que no hay objetos
        if (!html || html.trim().length < 10) {
            button.style.display = "none";
        } else {
            container.insertAdjacentHTML("beforeend", html);
            page++; 
            button.disabled = false;
        }
    } catch (error) {
        console.error("Error en la carga:", error);
        button.disabled = false;
    } finally {
        spinner.style.display = "none";
    }
}

// 4. Único Listener (IMPORTANTE: Solo el click)
if (button) {
    button.addEventListener("click", loadMoreItems);
}