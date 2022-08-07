import ListItem from "./ListItem";

export default (props) => {
    let itemList = [];
    if (props.items) {
        props.items.forEach((item) => {
            itemList.push(<ListItem key={item} item={item}></ListItem>);
        });
    }

    return (
        <div>
            <h4>
                {props.header}: {props.itemNum}
            </h4>
            <ol>{itemList}</ol>
        </div>
    );
};
