import React from "react";
import "antd/dist/antd.css";
import { Link } from "react-router-dom";

const AdminPanel = (props) => {
    return (
        <div>
            <h1>Admin Panel</h1>
            <ul>
                <li>
                    <Link to="/admin/create">Create Book</Link>
                </li>
            </ul>
        </div>
    );
};

export default AdminPanel;
