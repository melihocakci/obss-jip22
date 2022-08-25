import {
  Form,
  Input,
  Button,
  Spin,
  message,
  Typography,
  Alert,
  Popconfirm,
} from "antd";
import { useNavigate, useParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import UserService from "../service/UserService";
import AuthService from "../service/AuthService";
import ThisUser from "../util/ThisUser";
import UserContext from "../context/UserContext";
import clean from "../util/clean";
const { Title } = Typography;

export default () => {
  const navigate = useNavigate();
  const { id: userId } = useParams();
  const [currentCredentials, setCurrentCredentials] = useState();
  const [credentials, setCredentials] = useState({});
  const { user, setUser } = useContext(UserContext);
  const [errorMessage, setErrorMessage] = useState();

  useEffect(() => {
    fetch();
  }, []);

  const fetch = async () => {
    const response = await UserService.fetchUser(userId);

    if (!response.success) {
      message.error(response.message);
      return;
    }

    setCurrentCredentials(response.body);
  };

  const onFinish = async () => {
    if (ThisUser.getId() == userId) {
      const response = await UserService.updateUser("", clean(credentials));

      if (!response.success) {
        setErrorMessage(response.message);
        return;
      }

      AuthService.signout();
      setUser();
      navigate("/signin");
      message.success("Account updated");
    } else {
      const response = await UserService.updateUser(userId, clean(credentials));

      if (!response.success) {
        setErrorMessage(response.message);
        return;
      }

      navigate("/users/" + userId);
      message.success("User updated");
    }
  };

  const onFinishFailed = (errorInfo) => {
    console.log("Failed:", errorInfo);
  };

  const handleChange = (event) => {
    setCredentials({
      ...credentials,
      [event.target.name]: event.target.value,
    });
  };

  const getAlert = () => {
    if (errorMessage) {
      return (
        <Alert
          message={errorMessage}
          type="error"
          showIcon
          style={{ marginBottom: "10px" }}
        />
      );
    }
  };

  const removeThisUser = async () => {
    if (userId == user.id) {
      var id = "";
    } else if (user.role === "admin") {
      var id = userId;
    } else {
      message.error("Unauthorized");
      return;
    }

    var response = await UserService.removeUser(id);

    if (!response.success) {
      message.error(response.message);
      return;
    }

    AuthService.signout();
    setUser();
    navigate("/");
    message.success("Account deleted");
  };

  if (!currentCredentials) {
    return <Spin />;
  }

  return (
    <div style={{ margin: "0 auto", marginBottom: 20, width: 400 }}>
      <Title level={3}>Account Settings</Title>

      <Form
        name="username"
        labelCol={{
          span: 8,
        }}
        wrapperCol={{
          span: 16,
        }}
        initialValues={{
          remember: true,
        }}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}>
        {getAlert()}

        <Form.Item
          label="Username"
          name="username"
          rules={[
            {
              required: false,
              message: "Please input username!",
            },
            {
              min: 3,
              message: "too short",
            },
            {
              max: 20,
              message: "too long",
            },
          ]}>
          <Input
            onChange={handleChange}
            name="username"
            value={credentials.username}
            placeholder={currentCredentials.username}
          />
        </Form.Item>

        <Form.Item
          wrapperCol={{
            offset: 8,
            span: 16,
          }}>
          <Button type="primary" htmlType="submit">
            Change username
          </Button>
        </Form.Item>
      </Form>

      <Form
        name="password"
        labelCol={{
          span: 8,
        }}
        wrapperCol={{
          span: 16,
        }}
        initialValues={{
          remember: true,
        }}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}>
        {getAlert()}

        <Form.Item
          label="Password"
          name="password"
          rules={[
            {
              required: false,
              message: "Please input password!",
            },
            {
              min: 8,
              message: "too short",
            },
            {
              max: 20,
              message: "too long",
            },
          ]}>
          <Input.Password
            onChange={handleChange}
            name="password"
            value={credentials.password}
          />
        </Form.Item>

        <Form.Item
          wrapperCol={{
            offset: 8,
            span: 16,
          }}>
          <Button type="primary" htmlType="submit">
            Change password
          </Button>
        </Form.Item>
      </Form>

      <Popconfirm
        title="Are you sure?"
        okText="Yes"
        cancelText="No"
        onConfirm={removeThisUser}>
        <Button>Delete Account</Button>
      </Popconfirm>
    </div>
  );
};
