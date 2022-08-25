import React from "react";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import UserService from "../../service/UserService";
import { Button, Divider, Typography, Popconfirm, message } from "antd";
const { Title } = Typography;

export default () => {
  const { id: userId } = useParams();
  const navigate = useNavigate();

  const removeUser = async () => {
    const response = await UserService.removeUser(userId);

    if (!response.success) {
      message.error(response.message);
      return;
    }

    navigate("/users");
    message.success("User deleted");
  };

  const updateUser = () => {
    navigate("/users/" + userId + "/settings");
  };

  return (
    <div class="actions">
      <Divider orientation="left">
        <Title level={5}>Admin Actions</Title>
      </Divider>

      <Button onClick={updateUser}>Account Settings</Button>
    </div>
  );
};
