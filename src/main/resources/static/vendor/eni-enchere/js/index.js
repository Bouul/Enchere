let currentPage = 0;
const pageSize = 6;

// Affiche les enchères et la pagination
function updateBidsDisplay(data) {
    const container = document.getElementById('bids-container');
    const noResultsMessage = document.getElementById('no-bids-message');
    container.innerHTML = '';

    if (!data.bids || data.bids.length === 0) {
        if (noResultsMessage) noResultsMessage.style.display = 'block';
        else {
            const msg = document.createElement('div');
            msg.id = 'no-bids-message';
            msg.className = 'uk-alert uk-alert-warning';
            msg.innerHTML = '<p>Aucune enchère disponible pour le moment.</p>';
            container.parentNode.insertBefore(msg, container);
        }
        document.getElementById('pagination-container')?.remove();
        return;
    }
    if (noResultsMessage) noResultsMessage.style.display = 'none';

    data.bids.forEach(bid => {
        // Lecture directe des propriétés du DTO
        const itemName      = bid.itemName;
        const endDate       = new Date(bid.itemEndDate);
        const startingPrice = bid.itemStartingPrice;
        const image         = bid.itemImage;
        const seller        = bid.sellerUsername || 'Inconnu';

        const isOngoing = endDate > new Date();

        // Vous n’avez plus pickupLocation dans le DTO
        const pickupAddress = bid.pickupAddress || 'Non spécifié';

        const bidCard = document.createElement('div');
        bidCard.className = 'uk-card uk-card-default uk-margin-bottom';
        bidCard.innerHTML = `
                <div class="uk-card-media-top">
                    ${
            image
                ? `<div class="uk-height-medium uk-flex uk-flex-center uk-flex-middle">
                                   <img src="/images/${image}"
                                        alt="Image de l'article"
                                        class="uk-border-rounded"
                                        style="max-height:180px; max-width:100%; object-fit:cover;">
                               </div>`
                : `<div class="uk-height-medium uk-background-muted uk-flex uk-flex-center uk-flex-middle">
                                   <span>Pas d'image disponible</span>
                               </div>`
        }
                </div>
                <div class="uk-card-body">
                    <div class="uk-flex uk-flex-between">
                        <h3 class="uk-card-title">${itemName || 'Nom non disponible'}</h3>
                        <span class="status-label ${isOngoing ? 'en-cours' : 'terminée'}">
                            ${isOngoing ? 'En cours' : 'Terminée'}
                        </span>
                    </div>
                    <p><strong>Prix de départ:</strong> <span>${startingPrice}</span></p>
                    <p><strong>Fin de l'enchère:</strong> <span>${bid.itemEndDate || 'Non spécifié'}</span></p>
                    <p><strong>Retrait:</strong> <span>${pickupAddress}</span></p>
                    <p><strong>Vendeur:</strong> <span>${seller}</span></p>
                    <a href="/bidding-page?id=${bid.itemId}" class="uk-button uk-button-primary uk-width-1-1">
                        Voir détails
                    </a>
                </div>
            `;
        container.appendChild(bidCard);
    });

    updatePaginationControls(data.currentPage, data.totalPages);
}

// Affiche les contrôles de pagination
function updatePaginationControls(current, total) {
    let pagination = document.getElementById('pagination-container');
    if (!pagination) {
        pagination = document.createElement('div');
        pagination.id = 'pagination-container';
        pagination.className = 'uk-margin-top uk-flex uk-flex-center';
        const container = document.getElementById('bids-container');
        container.parentNode.appendChild(pagination);
    }
    let html = `<ul class="uk-pagination">`;
    html += `<li class="${current === 0 ? 'uk-disabled' : ''}"><a href="#" id="prev-page">&laquo;</a></li>`;
    let start = Math.max(0, current - 2);
    let end = Math.min(total - 1, start + 4);
    if (end - start < 4) start = Math.max(0, end - 4);
    for (let i = start; i <= end; i++) {
        html += `<li class="${i === current ? 'uk-active' : ''}">
                         <a href="#" class="page-link" data-page="${i}">${i + 1}</a>
                     </li>`;
    }
    html += `<li class="${current === total - 1 ? 'uk-disabled' : ''}">
                     <a href="#" id="next-page">&raquo;</a>
                 </li>`;
    html += `</ul>`;
    pagination.innerHTML = html;

    document.getElementById('prev-page').onclick = e => {
        e.preventDefault();
        if (current > 0) goToPage(current - 1);
    };
    document.getElementById('next-page').onclick = e => {
        e.preventDefault();
        if (current < total - 1) goToPage(current + 1);
    };
    document.querySelectorAll('.page-link').forEach(link => {
        link.onclick = e => {
            e.preventDefault();
            goToPage(parseInt(link.dataset.page));
        };
    });
}

function getFilters(isAuth) {
    if (isAuth) {
        return {
            categoryId: document.getElementById('categories-auth').value,
            search: document.getElementById('search-auth').value.trim(),
            filterType: document.getElementById('filter-type').value
        };
    } else {
        return {
            categoryId: document.getElementById('categories').value,
            search: document.getElementById('search').value.trim()
        };
    }
}

function filterBids(page = 0) {
    currentPage = page;
    const { categoryId, search } = getFilters(false);
    let url = `/api/bids/filter?page=${currentPage}&size=${pageSize}`;
    if (categoryId && categoryId !== '0') url += `&categoryId=${categoryId}`;
    if (search) url += `&search=${encodeURIComponent(search)}`;
    fetch(url)
        .then(r => r.json())
        .then(data => updateBidsDisplay(data))
        .catch(e => console.error(e));
}

function filterBidsAuth(page = 0) {
    currentPage = page;
    const { categoryId, search, filterType } = getFilters(true);
    let url = `/api/bids/filter?page=${currentPage}&size=${pageSize}`;
    if (categoryId && categoryId !== '0') url += `&categoryId=${categoryId}`;
    if (search) url += `&search=${encodeURIComponent(search)}`;
    if (filterType && filterType !== 'all') url += `&filterType=${filterType}`;
    fetch(url)
        .then(r => r.json())
        .then(data => updateBidsDisplay(data))
        .catch(e => console.error(e));
}

function goToPage(page) {
    if (document.getElementById('categories-auth')) filterBidsAuth(page);
    else filterBids(page);
}

document.addEventListener('DOMContentLoaded', function() {
    if (document.getElementById('categories')) {
        document.getElementById('categories').addEventListener('change', () => filterBids(0));
        document.getElementById('search-button').addEventListener('click', () => filterBids(0));
    }
    if (document.getElementById('categories-auth')) {
        document.getElementById('categories-auth').addEventListener('change', () => filterBidsAuth(0));
        document.getElementById('search-button-auth').addEventListener('click', () => filterBidsAuth(0));
        document.getElementById('filter-type').addEventListener('change', () => filterBidsAuth(0));
    }
    if (document.getElementById('categories-auth')) filterBidsAuth(0);
    else filterBids(0);
});