import Title from "./Title";
import VehicleList from "./VehicleList";
import CheckBox from "./CheckBox";
import DropDown from "./Dropdown";
import RenderOptions from "./RenderOptions";

export default (props) => {
    const shop = {
        categories: [
            {
                name: "Cars",
                vehicles: [
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
                vehicles: [
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

    return (
        <div>
            <Title title="Welcome to React Transportation" subTitle="The best place to buy vehicles online" />

            <RenderOptions />

            {shop.categories.map((category) => {
                return <VehicleList key={category} header={category.name} vehicles={category.vehicles} />;
            })}
        </div>
    );
};
