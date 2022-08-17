import { Form, Input, Button, Spin } from "antd";
import { useNavigate, useParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import UserService from "../service/UserService";
import AuthService from "../service/AuthService";
import ThisUser from "../util/ThisUser";
import UserContext from "../context/UserContext";

export default () => {
  const navigate = useNavigate();
  const { id: userId } = useParams();
  const [currentCredentials, setCurrentCredentials] = useState();
  const [credentials, setCredentials] = useState({});
  const { user, setUser } = useContext(UserContext);

  useEffect(() => {
    fetch();
  }, []);

  const fetch = async () => {
    const { body: user } = await UserService.fetchUser(userId);
    setCurrentCredentials(user);
  };

  const onFinish = async (values) => {
    console.log("Success:", values);
    if (ThisUser.getId() == userId) {
      const response = await UserService.updateUser("", credentials);
      if (response) {
        AuthService.signout();
        setUser(undefined);
        navigate("/login");
        alert("Account updated");
      }
    } else {
      const response = await UserService.updateUser(userId, credentials);
      if (response) {
        navigate("/users/" + userId);
        alert("User updated");
      }
    }

    //UserService.delete();
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
      <h1>Update User</h1>
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
          <Input.Password onChange={handleChange} name="password" value={credentials.password} />
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
