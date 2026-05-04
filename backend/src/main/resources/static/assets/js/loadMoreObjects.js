// Initial configuration
let page = 1; // Start from 1 because backend already rendered page 0
const sectionName = window.location.pathname.split("/")[2];
const container = document.getElementById("itemsContainer");
const spinner = document.getElementById("spinner");
const button = document.getElementById("loadItems");

async function loadMoreItems() {
    // Prevent multiple rapid requests
    if (button.disabled) return;
    
    spinner.style.display = "block";
    button.disabled = true;

    // Get category filter from URL
    const urlParams = new URLSearchParams(window.location.search);
    const category = urlParams.get('category');

    // Build request URL
    let url = `/section/${sectionName}/more/${page}`;
    if (category) {
        url += "?category=" + category;
    }

    try {
        const response = await fetch(url);
        const html = await response.text();

        // If response is empty, no more items to load
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

// Attach click listener
if (button) {
    button.addEventListener("click", loadMoreItems);
}