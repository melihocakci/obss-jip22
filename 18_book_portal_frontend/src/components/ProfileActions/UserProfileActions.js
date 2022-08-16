import React, { useContext } from "react";
import AuthService from "../../service/AuthService";
import { useNavigate } from "react-router-dom";
import UserService from "../../service/UserService";
import { Button, Divider, Typography } from "antd";
import UserContext from "../../context/UserContext";
const { Title } = Typography;

export default () => {
  const { user, setUser } = useContext(UserContext);
  const navigate = useNavigate();

  const signOut = () => {
    AuthService.signout();
    setUser(undefined);
    navigate("/login");
  };

  const updateAccount = () => {
    navigate("/users/" + user.id + "/update");
  };

  const removeThisUser = async () => {
    const response = await UserService.removeUser("");
    if (response) {
      AuthService.signout();
      setUser(undefined);
      navigate("/");
      alert("Account deleted");
    }
  };

  return (
    <div style={{ float: "right", display: "inline-block" }}>
      <Divider orientation="left">
        <Title level={5}>Actions</Title>
      </Divider>

      <Button onClick={signOut}>Sign out</Button>
      <Button onClick={removeThisUser}>Delete Account</Button>
      <Button onClick={updateAccount}>Update Account</Button>
    </div>
  );
};
