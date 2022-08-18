import React from "react";
import { Link } from "react-router-dom";
import { Typography } from "antd";
const { Title } = Typography;

const AdminPanel = () => {
  return (
    <div>
      <Title>Admin Panel</Title>
      <ul>
        <li>
          <h2>
            <Link to="/admin/create">Create Book</Link>
          </h2>
        </li>
        <li>
          <h2>
            <Link to="/signup">Create User</Link>
          </h2>
        </li>
      </ul>
    </div>
  );
};

export default AdminPanel;
