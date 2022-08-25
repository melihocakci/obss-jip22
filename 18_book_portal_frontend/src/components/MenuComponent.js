import { Link, useNavigate } from "react-router-dom";
import { Menu, Dropdown, Space, message } from "antd";
import { useContext } from "react";
import UserContext from "../context/UserContext";
import AuthService from "../service/AuthService";

export default () => {
  const { user, setUser } = useContext(UserContext);
  const navigate = useNavigate();

  const loginOrProfile = () => {
    const signOut = () => {
      AuthService.signout();
      setUser();
      navigate("/signin");
      message.success("Signed out");
    };

    if (user) {
      const menu = (
        <Menu
          items={[
            {
              label: <Link to={"/users/" + user.id}>Profile</Link>,
              key: "0",
            },
            {
              label: (
                <Link to={"/users/" + user.id + "/settings"}>
                  Account Settings
                </Link>
              ),
              key: "1",
            },
            {
              label: <a onClick={signOut}>Sign out</a>,
              key: "3",
            },
          ]}
        />
      );

      return (
        <Menu.Item key="5" style={{ marginLeft: "auto" }}>
          <Dropdown overlay={menu} trigger={["click"]}>
            <a onClick={(e) => e.preventDefault()}>
              <Space>Account</Space>
            </a>
          </Dropdown>
        </Menu.Item>
      );
    } else {
      return (
        <Menu.Item key="5" style={{ marginLeft: "auto" }}>
          <Link to={"/signin"}>Sign in</Link>
        </Menu.Item>
      );
    }
  };

  const adminMenu = () => {
    if (user && user.role === "admin") {
      return (
        <Menu.Item key="6">
          <Link to="/admin">Admin Panel</Link>
        </Menu.Item>
      );
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

      {adminMenu()}

      {loginOrProfile()}
    </Menu>
  );
};
