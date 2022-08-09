import ReactDOM from "react-dom";
import UserList from "./UserList";

export default () => {
    const root = document.getElementById("root");

    ReactDOM.render(<UserList />, root);
};
