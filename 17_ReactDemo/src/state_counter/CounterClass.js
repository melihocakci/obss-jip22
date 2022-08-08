import React from "react";

class Counter extends React.Component {
    constructor(props) {
        super(props);
        this.state = { counter: 0 };
    }

    componentDidMount() {
        setInterval(() => {
            this.increment();
        }, 500);
    }

    increment() {
        this.setState((state, props) => ({
            counter: state.counter + 1,
        }));
    }

    render() {
        return <p>{this.state.counter}</p>;
    }
}

export default Counter;
