import React from "react";
import Button from "./button";

class Switch extends React.Component {
    constructor(props) {
        super(props);
        this.state = { activeButton: null };
        this.clickHandler = this.clickHandler.bind(this);
    }

    clickHandler(id) {
        this.setState({ activeButton: id });
    }

    render() {
        return (
            <div>
                <Button id={0} clickHandler={this.clickHandler} activeButton={this.state.activeButton} />
                <br />
                <Button id={1} clickHandler={this.clickHandler} activeButton={this.state.activeButton} />
            </div>
        );
    }
}

export default Switch;
