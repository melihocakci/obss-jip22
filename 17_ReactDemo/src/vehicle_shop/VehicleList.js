import ListItem from "./ListItem";

export default (props) => {
    const { header, vehicles } = props;

    return (
        <div>
            <p className="title">{header}</p>
            <ol>
                {vehicles.map((vehicle) => {
                    return (
                        <ListItem
                            key={vehicle}
                            year={vehicle.year}
                            model={vehicle.model}
                            price={vehicle.price}></ListItem>
                    );
                })}
            </ol>
        </div>
    );
};
