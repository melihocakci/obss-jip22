import React from "react";

class ControlledForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = { type: "", isNew: false };
        this.handleChange = this.handleChange.bind(this);
    }

    async handleChange(event) {
        await this.setState({ [event.target.name]: event.target.checked || event.target.value });

        console.log(this.state.isNew);
        console.log(this.state.type);
    }

    render() {
        return (
            <div>
                <input name="type" type="text" value={this.state.value} onChange={this.handleChange} />
                <input name="isNew" type="checkbox" value={this.state.checked} onChange={this.handleChange} />
            </div>
        );
    }
}

export default ControlledForm;
