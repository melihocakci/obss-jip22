import { StrictMode } from "react";
import ReactDOM from "react-dom";
import CheckedForms from "./form";

function CheckedForm() {
    const root = document.getElementById("root");

    ReactDOM.render(
        <StrictMode>
            <CheckedForms />
        </StrictMode>,
        root
    );
}

export default CheckedForm;
