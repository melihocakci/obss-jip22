export default (props) => {
    return (
        <div>
            <label>
                {props.boxlabel}
                <input type="checkbox" defaultChecked="true" />
            </label>
        </div>
    );
};
