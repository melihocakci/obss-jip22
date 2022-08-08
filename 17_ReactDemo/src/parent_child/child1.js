import React from "react";

class Counter extends React.Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {
        console.log("child 1 mounted");
    }

    componentDidUpdate() {
        
    }

    render() {
        console.log("child 1 rendering");
        return <p>Child 1</p>;
    }
}

export default Counter;
