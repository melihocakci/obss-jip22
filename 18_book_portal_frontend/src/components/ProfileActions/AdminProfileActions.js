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

    if (response.success) {
      navigate("/users");
      message.success("User deleted");
    }
  };

  const updateUser = () => {
    navigate("/users/" + userId + "/update");
  };

  return (
    <div style={{ float: "right", display: "inline-block" }}>
      <Divider orientation="left">
        <Title level={5}>Actions</Title>
      </Divider>

      <Popconfirm
        title="Are you sure?"
        okText="Yes"
        cancelText="No"
        onConfirm={removeUser}>
        <Button>Delete User</Button>
      </Popconfirm>

      <Button onClick={updateUser}>Update User</Button>
    </div>
  );
};
