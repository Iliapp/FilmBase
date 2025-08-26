document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.show-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const extra = btn.nextElementSibling;
            extra.style.display = extra.style.display === 'none' ? 'block' : 'none';
        });
    });
});
