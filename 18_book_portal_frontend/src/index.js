import ReactDOM from "react-dom";
import { StrictMode } from "react";
import App from "./App";
import AxiosConfigurer from "./config/AxiosConfigurer";

AxiosConfigurer.configure();

const rootElement = document.getElementById("root");
ReactDOM.render(
    <StrictMode>
        <App />
    </StrictMode>,
    rootElement
);
