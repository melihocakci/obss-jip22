import React from "react";

class Button extends React.Component {
    constructor(props) {
        super(props);
        this.clickHandler = this.clickHandler.bind(this);
    }

    clickHandler() {
        console.log("Button clicked");
    }

    render() {
        return <button onClick={this.clickHandler}>Click Me!</button>;
    }
}

export default Button;
