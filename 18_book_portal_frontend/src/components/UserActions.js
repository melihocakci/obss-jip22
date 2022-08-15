import React from "react";
import "antd/dist/antd.css";
import LocalStorageService from "../util/LocalStorageUtil";
import jwt from "jwt-decode";
import { Link } from "react-router-dom";
import AuthService from "../service/AuthService";
import { useNavigate } from "react-router-dom";
import UserService from "../service/UserService";
import { Button, Divider, Typography } from "antd";
const { Title } = Typography;

const UserActions = ({ userId }) => {
  const navigate = useNavigate();

  const signOut = () => {
    AuthService.signout();
    navigate("/login");
    window.location.reload();
  };

  const removeUser = async () => {
    const response = await UserService.removeUser(userId);
    if (response) {
      navigate("/users");
      alert("User deleted");
    }
  };

  const removeThisUser = async () => {
    const response = await UserService.removeUser("");
    if (response) {
      AuthService.signout();
      navigate("/");
      alert("Account deleted");
      window.location.reload();
    }
  };

  const token = LocalStorageService.getToken();

  if (!token) {
    return;
  }

  const user = jwt(token);

  if (user.id == userId) {
    return (
      <div>
        <Divider orientation="left">
          <Title level={5}>Actions</Title>
        </Divider>

        <Button onClick={signOut}>Sign out</Button>
        <Button onClick={removeThisUser}>Delete Account</Button>
        <Button
          onClick={() => {
            navigate("/users/" + userId + "/update");
          }}>
          Update Account
        </Button>
      </div>
    );
  } else if (user.role == "ROLE_ADMIN") {
    return (
      <div>
        <Divider orientation="left">
          <Title level={5}>Actions</Title>
        </Divider>

        <Button onClick={removeUser}>Delete User</Button>
        <Button
          onClick={() => {
            navigate("/users/" + userId + "/update");
          }}>
          Update User
        </Button>
      </div>
    );
  }
};

export default UserActions;
