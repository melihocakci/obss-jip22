import React from "react";
import Table from "./RenderTable";

class MainPage extends React.Component {
    constructor(props) {
        super(props);
        this.clickHandler = this.clickHandler.bind(this);

        this.state = {
            cars: [
                {
                    year: 2013,
                    model: "A",
                    price: "$32000",
                },
                {
                    year: 2011,
                    model: "B",
                    price: "$4400",
                },
                {
                    year: 2016,
                    model: "B",
                    price: "$15500",
                },
            ],

            isNew: false,
        };
    }

    clickHandler() {
        this.state.isNew ? this.setState({ isNew: false }) : this.setState({ isNew: true });
    }

    render() {
        return (
            <div>
                <p className="title">Choose Options</p>
                <input type="checkbox" onClick={this.clickHandler} />
                <Table cars={this.state.cars} />
            </div>
        );
    }
}

export default MainPage;
