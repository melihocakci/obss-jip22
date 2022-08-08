import ReactDOM from "react-dom";
import CounterClass from "./CounterClass";
import CounterFunc from "./CounterFunc";

export default () => {
    const root = document.getElementById("root");

    ReactDOM.render(<CounterFunc />, root);
};
