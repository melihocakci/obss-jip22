import CheckBox from "./CheckBox";
import Dropdown from "./Dropdown";

export default (props) => {
    return (
        <div>
            <p className="title">Choose Options</p>
            <CheckBox title="Choose Options" boxlabel="New Only" />
            <Dropdown dropdownLabel="Select Type" />
        </div>
    );
};
