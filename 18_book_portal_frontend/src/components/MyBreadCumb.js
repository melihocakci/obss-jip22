import { Breadcrumb } from "antd";

const MyBreadcumb = () => {
    return (
        <Breadcrumb style={{ margin: "16px 0" }}>
            <Breadcrumb.Item>Home</Breadcrumb.Item>
            <Breadcrumb.Item>Books</Breadcrumb.Item>
            <Breadcrumb.Item>Users</Breadcrumb.Item>
            <Breadcrumb.Item>About</Breadcrumb.Item>
        </Breadcrumb>
    );
};

export default MyBreadcumb;
