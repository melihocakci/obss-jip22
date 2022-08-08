import React from "react";

class Counter extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const { id, clickHandler, activeButton } = this.props;
        return (
            <button
                id={id}
                onClick={() => {
                    clickHandler(id);
                }}
                style={{ color: activeButton == id ? "red" : "blue" }}>
                Button {id}
            </button>
        );
    }
}

export default Counter;
