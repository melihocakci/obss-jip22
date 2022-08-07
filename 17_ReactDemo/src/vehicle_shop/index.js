import { StrictMode } from "react";
import ReactDOM from "react-dom";
import VehicleShop from "./VehicleShop";

function shopping() {
    const root = document.getElementById("root");

    ReactDOM.render(
        <StrictMode>
            <VehicleShop />
        </StrictMode>,
        root
    );
}

export default shopping;
