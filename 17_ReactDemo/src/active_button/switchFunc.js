import React, { useState } from "react";
import Button from "./buttonFunc";

export default () => {
    const [active, setActive] = useState(null);

    function clickHandler(id) {
        setActive(id);
    }

    clickHandler = clickHandler.bind(this);

    return (
        <div>
            <Button id={0} clickHandler={clickHandler} active={active} />
            <br />
            <Button id={1} clickHandler={clickHandler} active={active} />
        </div>
    );
};
