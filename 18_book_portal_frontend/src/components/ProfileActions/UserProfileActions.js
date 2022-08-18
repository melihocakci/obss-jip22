import React, { useContext } from "react";
import AuthService from "../../service/AuthService";
import { useNavigate } from "react-router-dom";
import UserService from "../../service/UserService";
import { Button, Divider, Typography, Popconfirm, message } from "antd";
import UserContext from "../../context/UserContext";
const { Title } = Typography;

export default () => {
  const { user, setUser } = useContext(UserContext);
  const navigate = useNavigate();

  const signOut = () => {
    AuthService.signout();
    setUser();
    navigate("/signin");
    message.success("Signed out");
  };

  const updateAccount = () => {
    navigate("/users/" + user.id + "/update");
  };

  const removeThisUser = async () => {
    const response = await UserService.removeUser("");

    if (!response.success) {
      message.error(response.message);
      return;
    }

    AuthService.signout();
    setUser();
    navigate("/");
    message.success("Account deleted");
  };

  return (
    <div class="actions">
      <Divider orientation="left">
        <Title level={5}>Actions</Title>
      </Divider>

      <Button onClick={signOut}>Sign out</Button>

      <Popconfirm
        title="Are you sure?"
        okText="Yes"
        cancelText="No"
        onConfirm={removeThisUser}>
        <Button>Delete Account</Button>
      </Popconfirm>

      <Button onClick={updateAccount}>Update Account</Button>
    </div>
  );
};
