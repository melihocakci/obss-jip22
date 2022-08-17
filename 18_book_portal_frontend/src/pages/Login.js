import { Form, Input, Button } from "antd";
import { Link, useNavigate } from "react-router-dom";
import { useState, useContext } from "react";
import AuthService from "../service/AuthService";
import { Typography, Alert } from "antd";
import UserContext from "../context/UserContext";
import ThisUser from "../util/ThisUser";
const { Title } = Typography;

const Login = () => {
  const navigate = useNavigate();
  const [credentials, setCredentials] = useState({});
  const { setUser } = useContext(UserContext);
  const [errorMessage, setErrorMessage] = useState();

  const onFinish = async () => {
    const response = await AuthService.signin(credentials);

    if (response.success) {
      setUser(ThisUser.get());
      navigate("/");
    } else {
      setErrorMessage(response.message);
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
      return <Alert message={errorMessage} type="error" style={{ marginBottom: "10px" }} />;
    }
  };

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
        <Title level={3}>Log in</Title>

        {getAlert()}

        <Form.Item
          label="Username"
          name="username"
          rules={[
            {
              required: true,
              message: "Please input your username!",
            },
          ]}>
          <Input onChange={handleChange} name="username" value={credentials.username} />
        </Form.Item>

        <Form.Item
          label="Password"
          name="password"
          rules={[
            {
              required: true,
              message: "Please input your password!",
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

        <Link to="/register">Register</Link>
      </Form>
    </div>
  );
};

export default Login;
