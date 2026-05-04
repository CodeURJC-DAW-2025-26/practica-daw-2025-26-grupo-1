document.addEventListener('DOMContentLoaded', function () {
    const typeSelect = document.getElementById('typeSelect');
    const categorySelect = document.getElementById('categorySelect');
    
    // Exit if elements are not present
    if (!typeSelect || !categorySelect) return;

    const allGroups = Array.from(categorySelect.querySelectorAll('optgroup'));

    function filterCategories() {
        const selectedType = typeSelect.value;
        
        // Clear current options
        categorySelect.innerHTML = '';

        // Show only categories matching selected type
        allGroups.forEach(group => {
            if (group.getAttribute('data-type') === selectedType) {
                categorySelect.appendChild(group.cloneNode(true));
            }
        });
    }

    typeSelect.addEventListener('change', filterCategories);
    
    // Initial filtering on page load
    filterCategories(); 
});
