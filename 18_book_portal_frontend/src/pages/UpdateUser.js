import { Form, Input, Button, Spin, message, Typography } from "antd";
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
  const { setUser } = useContext(UserContext);

  useEffect(() => {
    fetch();
  }, []);

  const fetch = async () => {
    const response = await UserService.fetchUser(userId);

    if (response.success) {
      setCurrentCredentials(response.body);
    }
  };

  const onFinish = async () => {
    if (ThisUser.getId() == userId) {
      const response = await UserService.updateUser("", clean(credentials));

      if (response.success) {
        AuthService.signout();
        setUser();
        navigate("/signin");
        message.success("Account updated");
      }
    } else {
      const response = await UserService.updateUser(userId, clean(credentials));

      if (response.success) {
        navigate("/users/" + userId);
        message.success("User updated");
      }
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

  if (!currentCredentials) {
    return <Spin />;
  }

  return (
    <div>
      <Form
        name="basic"
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
        onFinishFailed={onFinishFailed}
        style={{ margin: "0 auto", width: 400 }}>
        <Title level={3}>Update user</Title>

        <Form.Item
          label="Username"
          name="username"
          rules={[
            {
              required: false,
              message: "Please input username!",
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
          label="password"
          name="password"
          rules={[
            {
              required: false,
              message: "Please input password!",
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
            Submit
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};
