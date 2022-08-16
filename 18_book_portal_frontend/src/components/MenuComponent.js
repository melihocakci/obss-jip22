import { Link } from "react-router-dom";
import { Menu } from "antd";
import { useContext } from "react";
import UserContext from "../context/UserContext";

export default () => {
  const { user } = useContext(UserContext);

  const loginOrProfile = () => {
    if (user) {
      return <Link to={"/users/" + user.id}>Profile</Link>;
    } else {
      return <Link to={"/login"}>Login</Link>;
    }
  };

  const adminMenu = () => {
    if (user && user.role === "admin") {
      return <Link to="/admin">Admin Panel</Link>;
    } else {
      return;
    }
  };

  return (
    <Menu theme="dark" mode="horizontal">
      <Menu.Item key="1">
        <Link to="/">Home</Link>
      </Menu.Item>

      <Menu.Item key="2">
        <Link to="/books">Books</Link>
      </Menu.Item>

      <Menu.Item key="3">
        <Link to="/users">Users</Link>
      </Menu.Item>

      <Menu.Item key="4">
        <Link to="/about">About</Link>
      </Menu.Item>

      <Menu.Item key="5">{loginOrProfile()}</Menu.Item>

      <Menu.Item key="6">{adminMenu()}</Menu.Item>
    </Menu>
  );
};
