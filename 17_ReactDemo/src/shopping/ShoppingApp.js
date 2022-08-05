import ShoppingTitle from "./ShoppingTitle"
import ShoppingList from "./ShoppingList"

export default (props) => {
    const shop = {
        categories: [
            {
                name: "food",
                items: [
                    "Banana",
                    "Orange",
                    "Melon"
                ]
            },
            {
                name: "clothes",
                items: [
                    "Shirt",
                    "Jeans",
                    "Shoes"
                ]
            },
            {
                name: "supplies"
            }
        ]

    }

    let itemNumber = 0;
    let categoryList = [];
    shop.categories.forEach(category => {
        itemNumber += category.items ? category.items.length : 0;
        categoryList.push(
            <ShoppingList
                key={category}
                header={category.name}
                itemNum={category.items ? category.items.length : 0}
                items={category.items} />)
    })

    return <div>
        <ShoppingTitle title="Shopping app" itemNum={itemNumber}></ShoppingTitle>
        {categoryList}
    </div>
}