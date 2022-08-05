import React from "react";

class HelloClass extends React.Component {

    render() {
        const { name, callback } = this.props;

        return <div>
            <h1>
                Whats up {name}
            </h1>

            {callback()}
        </div>
    }
}

export default HelloClass;