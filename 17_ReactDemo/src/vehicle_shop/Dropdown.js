export default (props) => {
    return (
        <div>
            <label>
                {props.dropdownLabel}
                <select>
                    <option value="volvo">Volvo</option>
                    <option value="saab">Saab</option>
                    <option value="mercedes">Mercedes</option>
                    <option value="audi">Audi</option>
                </select>
            </label>
        </div>
    );
};
