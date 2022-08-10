import ThisUser from "../other/ThisUser";
import { Menu } from "antd";
import { Link } from "react-router-dom";

const AdminPanel = () => {
    if (!ThisUser.isAdmin()) {
        return;
    }

    return (
        <Menu.Item key="6">
            <Link to="/admin">Admin Panel</Link>
        </Menu.Item>
    );
};

export default AdminPanel;
