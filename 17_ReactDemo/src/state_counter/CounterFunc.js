import React, { useEffect, useState } from "react";

export default () => {
    const [counter, setCounter] = useState(0);

    useEffect(() => {
        setInterval(() => {
            setCounter((prevState) => {
                return prevState + 1;
            });
        }, 500);
    }, []);

    return <p>{counter}</p>;
};
