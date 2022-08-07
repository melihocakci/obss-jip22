import Title from "./Title";
import VehicleList from "./VehicleList";
import CheckBox from "./CheckBox";
import DropDown from "./Dropdown";

export default (props) => {
    const shop = {
        categories: [
            {
                name: "Cars",
                items: [
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
            },
            {
                name: "Trucks",
                items: [
                    {
                        year: 2014,
                        model: "D",
                        price: "$18000",
                    },
                    {
                        year: 2013,
                        model: "E",
                        price: "$5200",
                    },
                ],
            },
        ],
    };

    let itemNumber = 0;
    let categoryList = [];

    shop.categories.forEach((category) => {
        categoryList.push(<VehicleList key={category} header={category.name} items={category.items} />);
    });

    return (
        <div>
            <Title title="Welcome to React Transportation" subTitle="The best place to buy vehicles online" />
            <p className="title">Choose Options</p>
            <CheckBox title="Choose Options" boxlabel="New Only" />
            <DropDown dropdownLabel="Select Type" />
            {categoryList}
        </div>
    );
};
