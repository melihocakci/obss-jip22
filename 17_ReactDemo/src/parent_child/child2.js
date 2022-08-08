import React from "react";

class Counter extends React.Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {
        console.log("child 2 mounted");
    }

    componentDidUpdate() {
        
    }

    render() {
        console.log("child 2 rendering");
        return <p>Child 2</p>;
    }
}

export default Counter;
