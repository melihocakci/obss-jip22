import { Form, Input, Button } from "antd";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import UserService from "../service/UserService";
import { Typography, Alert } from "antd";
const { Title } = Typography;

const Register = () => {
  const navigate = useNavigate();
  const [credentials, setCredentials] = useState({});
  const [errorMessage, setErrorMessage] = useState();

  const onFinish = async () => {
    const response = await UserService.createUser(credentials);

    if (response.success) {
      navigate("/login");
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
        <Title level={3}>Register</Title>

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
      </Form>
    </div>
  );
};

export default Register;
