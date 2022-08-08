import React from "react";
import Child1 from "./child1";
import Child2 from "./child2";

class Counter extends React.Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {
        console.log("Parent mounted");
    }

    componentDidUpdate() {}

    render() {
        console.log("Parent rendering");
        return (
            <div>
                <Child1 />
                <br />
                <br />
                <br />
                <br />
                <Child2 />
            </div>
        );
    }
}

export default Counter;
