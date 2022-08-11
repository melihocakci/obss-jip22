import React from "react";
import "antd/dist/antd.css";
import { Link } from "react-router-dom";
import { Typography } from "antd";
const { Title } = Typography;

const AdminPanel = () => {
    return (
        <div>
            <Title>Admin Panel</Title>
            <ul>
                <li>
                    <Link to="/admin/create">Create Book</Link>
                </li>
                <li>
                    <Link to="/register">Create User</Link>
                </li>
            </ul>
        </div>
    );
};

export default AdminPanel;
