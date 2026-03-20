document.addEventListener('DOMContentLoaded', function () {
    const typeSelect = document.getElementById('typeSelect');
    const categorySelect = document.getElementById('categorySelect');
    
    if (!typeSelect || !categorySelect) return;

    const allGroups = Array.from(categorySelect.querySelectorAll('optgroup'));

    function filterCategories() {
        const selectedType = typeSelect.value;
        
        categorySelect.innerHTML = '';

        allGroups.forEach(group => {
            if (group.getAttribute('data-type') === selectedType) {
                categorySelect.appendChild(group.cloneNode(true));
            }
        });
    }

    typeSelect.addEventListener('change', filterCategories);
    
    filterCategories(); 
});


