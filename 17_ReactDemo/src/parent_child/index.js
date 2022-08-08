import ReactDOM from "react-dom";
import Parent from "./parent"

export default () => {
    const root = document.getElementById("root");

    ReactDOM.render(<Parent />, root);
};
