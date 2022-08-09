import { StrictMode } from "react";
import ReactDOM from "react-dom";
import Owo from "./owo";

function CheckedForm() {
    const root = document.getElementById("root");

    ReactDOM.render(
        <StrictMode>
            <Owo />
        </StrictMode>,
        root
    );
}

export default CheckedForm;
