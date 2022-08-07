import ListItem from "./ListItem";

export default (props) => {
    let itemList = [];
    if (props.items) {
        props.items.forEach((vehicle) => {
            itemList.push(
                <ListItem key={vehicle} year={vehicle.year} model={vehicle.model} price={vehicle.price}></ListItem>
            );
        });
    }

    return (
        <div>
            <p className="title">{props.header}</p>
            <ol>{itemList}</ol>
        </div>
    );
};
