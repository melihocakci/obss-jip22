class StateDisplay extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            message: "initial message",
            complexMessage: {
                innerSimpleMessage: "inner simple message",
                innerComplexMessage: "inner complex message",
            },
        };
    }

    componentDidMount() {
        this.setState((prevState) => {
            return {
                newTopLevelMessage: "new top level message",
                complexMessage: {
                    ...prevState.complexMessage,
                    innerComplexMessage: { message: "new inner complex message" },
                },
            };
        });
    }

    render() {
        return <div>Message:{this.state.complexMessage.innerSimpleMessage}</div>;
    }
}
