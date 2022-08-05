import { StrictMode } from 'react';
import ReactDOM from 'react-dom';
import ShoppingApp from './ShoppingApp'

function shopping() {
    const root = document.getElementById("root")

    ReactDOM.render(
        <StrictMode>
            <ShoppingApp />
        </StrictMode>,
        root
    );

}

export default shopping;