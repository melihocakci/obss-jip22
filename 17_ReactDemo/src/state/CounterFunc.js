import React, { useEffect, useState } from "react";

export default () => {
    const [counter, setCounter] = useState(0);

    useEffect(() => {
        setCounter(counter + 1);
        setCounter(counter + 1);
        setCounter(counter + 1);
        setCounter(counter + 1);
    }, []);

    return <p>{counter}</p>;
};
