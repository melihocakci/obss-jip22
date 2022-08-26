import {
  Form,
  Input,
  Button,
  Spin,
  message,
  Typography,
  Alert,
  Popconfirm,
  Divider,
  Select,
} from "antd";
import { useNavigate, useParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import UserService from "../service/UserService";
import AuthService from "../service/AuthService";
import ThisUser from "../util/ThisUser";
import UserContext from "../context/UserContext";
import clean from "../util/clean";
import { render } from "@testing-library/react";
const { Title } = Typography;
const { Option } = Select;

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
    if (user.id == userId) {
      var response = await UserService.updateUser("", clean(credentials));
    } else if (user.role === "admin") {
      var response = await UserService.updateUser(userId, clean(credentials));
    } else {
      message.error("Unauhorized");
      return;
    }

    if (!response.success) {
      setErrorMessage(response.message);
      return;
    }

    message.success("Update successful");

    if (user.id == userId && (credentials.username || credentials.password)) {
      AuthService.signout();
      setUser();
      navigate("/signin");
      message.info("Please sign in with your new credentials");
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

  const GetAlert = () => {
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

  const removeUser = async () => {
    if (userId == user.id) {
      const response = await UserService.removeUser("");

      if (!response.success) {
        message.error(response.message);
        return;
      }

      AuthService.signout();
      setUser();
      navigate("/");
      message.success("Account deleted");
    } else if (user.role === "admin") {
      const response = await UserService.removeUser(userId);

      if (!response.success) {
        message.error(response.message);
        return;
      }

      navigate("/users");
      message.success("User deleted");
    } else {
      message.error("Unauthorized");
    }
  };

  if (!currentCredentials) {
    return <Spin />;
  }

  return (
    <div style={{ margin: "0 auto", marginBottom: 20, width: 400 }}>
      <Title level={3}>Account Settings</Title>

      <GetAlert />

      <Divider />

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
        <Form.Item
          label="Username"
          name="username"
          rules={[
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

        <Divider />

        <Form.Item
          label="E-mail"
          name="email"
          rules={[
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
            name="email"
            value={credentials.email}
            placeholder={currentCredentials.email}
          />
        </Form.Item>

        <Divider />

        <Form.Item name="gender" label="Gender">
          <Select
            placeholder={currentCredentials.gender}
            onChange={(value) => {
              setCredentials({
                ...credentials,
                gender: value,
              });
            }}
            name="gender">
            <Option value="male">male</Option>
            <Option value="female">female</Option>
            <Option value="other">other</Option>
          </Select>
        </Form.Item>

        <Divider />

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
          name="confirm"
          label="Confirm Password"
          dependencies={["password"]}
          hasFeedback
          rules={[
            ({ getFieldValue }) => ({
              validator(_, value) {
                if (!value || getFieldValue("password") === value) {
                  return Promise.resolve();
                }

                return Promise.reject(new Error("The passwords do not match!"));
              },
            }),
          ]}>
          <Input.Password />
        </Form.Item>

        <Form.Item
          wrapperCol={{
            offset: 8,
            span: 16,
          }}>
          <Button type="primary" htmlType="submit">
            Update Account
          </Button>
        </Form.Item>
      </Form>

      <Divider />

      <Popconfirm
        title="Are you sure?"
        okText="Yes"
        cancelText="No"
        onConfirm={removeUser}>
        <Button type="primary" danger>
          Delete Account
        </Button>
      </Popconfirm>
    </div>
  );
};
