import ReactDOM from 'react-dom';

export default function products() {

    let product = { name: "book", stock: 0 }
    const answers = {
        noStoc: <h1>No books in stock</h1>,
        yesStoc: <h1>There are {product.stock} {product.name}s in stock</h1>
    }

    let answer;

    if (product.stock < 1) {
        answer = answers.noStoc;
    } else {
        answer = answers.yesStoc;
    }

    ReactDOM.render(answer, document.getElementById("root"));


}

products();


