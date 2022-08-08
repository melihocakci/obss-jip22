import ReactDOM from "react-dom";
import MainPage from "./MainPage";

export default () => {
    const root = document.getElementById("root");

    ReactDOM.render(<MainPage />, root);
};
