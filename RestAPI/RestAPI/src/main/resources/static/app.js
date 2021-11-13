function solve() {
    let baseURI = 'http://localhost:8080/books';
    let loadBtn = document.querySelector('#loadBooks');
    let submitForm = document.querySelector('.createForm');

    loadBtn.addEventListener('click', loadEventHandler);
    submitForm.addEventListener('submit', submitEventHandler);

    async function submitEventHandler(e) {
        e.preventDefault();
        let formData = new FormData(submitForm);
        let title = formData.get('title');
        let isbn = formData.get('isbn');
        let author = formData.get('author');

        let obj = {
            title,
            isbn,
            author: {
                name: author
            }
        }

        try {
            let response = await fetch(baseURI, {
                method: 'post',
                headers: { 'Content-type': 'application/json' },
                body: JSON.stringify(obj)
            });

            if (response.status == 201) {
                loadEventHandler();
            }

        } catch (error) {
            alert('Unable to create data');
        }

        submitForm.reset();
    }

    async function loadEventHandler(e) {
        let tbodyElement = document.querySelector('#books');
        let loadURI = `${baseURI}/all`;
        Array.from(tbodyElement.children).forEach(e => e.remove());

        try {
            let responseData = await fetch(`${loadURI}`);
            let data = await responseData.json();

            Object.keys(data).forEach(key => {
                tbodyElement.appendChild(createTbodyHTML(key, data[key]));
            })

        } catch (error) {
            alert('Unable to load data');
        }
    }


    function createTbodyHTML(key, value) {

        let tr = document.createElement('tr');

        let thTitle = document.createElement('th');
        thTitle.textContent = value.title;
        thTitle.classList.add('title');

        let thAuthor = document.createElement('th');
        thAuthor.textContent = value.author.name;
        thAuthor.classList.add('author');

        let thIsbn = document.createElement('th');
        thIsbn.textContent = value.isbn;
        thIsbn.classList.add('isbn');

        let thAction = document.createElement('th');

        let editBtn = document.createElement('button');
        editBtn.textContent = 'Edit';
        editBtn.id = value.id === undefined ? key : value.id;
        editBtn.addEventListener('click', editHandler);

        let deleteBtn = document.createElement('button');
        deleteBtn.textContent = 'Delete';
        deleteBtn.id = value.id === undefined ? key : value.id;
        deleteBtn.addEventListener('click', deleteHandler);

        thAction.appendChild(editBtn);
        thAction.appendChild(deleteBtn);

        tr.appendChild(thTitle);
        tr.appendChild(thAuthor);
        tr.appendChild(thIsbn);
        tr.appendChild(thAction);

        return tr;

    }

    function editHandler(e) {
        let submitForm = document.querySelector('.createForm');
        let editForm = document.querySelector('.editForm');

        submitForm.style.display = 'none';
        editForm.style.display = 'block';

        let curTitle = e.target.parentElement.parentElement.querySelector('.title').textContent;
        let curAuthor = e.target.parentElement.parentElement.querySelector('.author').textContent;
        let curIsbn = e.target.parentElement.parentElement.querySelector('.isbn').textContent;

        let title = document.querySelector('.editForm input[name="title"]');
        let author = document.querySelector('.editForm input[name="author"]');
        let isbn = document.querySelector('.editForm input[name="isbn"]')

        editForm.dataset.id = (e.target.id);
        title.value = curTitle
        author.value = curAuthor;
        isbn.value = curIsbn;
        document.querySelector('#cancelBtn').addEventListener('click', cancelEvent);

        editForm.addEventListener('submit', updateHandler);
    }

    async function updateHandler(e) {
        e.preventDefault();

        let bookId = e.target.dataset.id;

        let formData = new FormData(e.target);

        let title = formData.get('title');
        let authorName = formData.get('author');
        let isbn = formData.get('isbn');
        let obj = { author: { name: authorName }, title, isbn };

        try {
            let response = await fetch(`${baseURI}/${bookId}`, {
                method: 'put',
                headers: { 'Content-type': 'application/json' },
                body: JSON.stringify(obj)
            });
            if (response.status == 204) {
                loadEventHandler();
            }

        } catch (error) {
            alert('Unable to update data');
        }

        let submitForm = document.querySelector('.createForm');
        let editForm = document.querySelector('.editForm');

        editForm.style.display = 'none';
        submitForm.style.display = 'block';
    }

    function cancelEvent(e) {
        let submitForm = document.querySelector('.createForm');
        let editForm = document.querySelector('.editForm');
        editForm.style.display = 'none';
        submitForm.style.display = 'block';
    }

    async function deleteHandler(e) {
        let id = e.target.id;
        try {
            let response = await fetch(`${baseURI}/${id}`, {
                method: 'delete'
            });
            alert('Entry is deleted');

        } catch (error) {
            alert('Unable to delete data');
        }
        e.target.parentElement.parentElement.remove();
    }

}

solve();