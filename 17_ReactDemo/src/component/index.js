import { StrictMode } from 'react';
import ReactDOM from 'react-dom';
import Hello from './Hello';
import HelloClass from "./HelloClass"


export default function () {
    const root = document.getElementById("root");

    /*
    ReactDOM.render(
        <StrictMode>
            <Hello name="John" />
        </StrictMode>,
        root
    );
    */

    ReactDOM.render(
        <StrictMode>
            <HelloClass name="John" callback={() => { console.log("Amogus") }} />
        </StrictMode>,
        root
    );

}
