import React from "react";
import uniqueId from "lodash";

class Table extends React.Component {
    constructor(props) {
        super(props);
        this.props = props;
    }

    shouldComponentUpdate(nextProps, nextState) {
        return nextProps.cars !== this.props.cars;
    }

    render() {
        console.log("component loaded");
        const { cars } = this.props;
        return (
            <div>
                <table>
                    <tr>
                        <th>year</th>
                        <th>model</th>
                        <th>price</th>
                    </tr>
                    {cars.map((car) => {
                        return (
                            <tr key={uniqueId()}>
                                <td>{car.year}</td>
                                <td>{car.model}</td>
                                <td>{car.price}</td>
                            </tr>
                        );
                    })}
                </table>
            </div>
        );
    }
}

export default Table;
