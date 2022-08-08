import React from "react";

export default (props) => {
    const { id, clickHandler, active } = props;

    return (
        <button
            id={id}
            onClick={() => {
                clickHandler(id);
            }}
            style={{ color: active == id ? "red" : "blue" }}>
            Button {id}
        </button>
    );
};
