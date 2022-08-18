import { Form, Input, Button } from "antd";
import { useNavigate, Link } from "react-router-dom";
import { useContext, useState } from "react";
import UserService from "../service/UserService";
import { Typography, Alert, message } from "antd";
import UserContext from "../context/UserContext";
import clean from "../util/clean";
const { Title } = Typography;

const Register = () => {
  const navigate = useNavigate();
  const [credentials, setCredentials] = useState({});
  const [alertMessage, setAlertMessage] = useState();
  const { user } = useContext(UserContext);

  const onFinish = async () => {
    const response = await UserService.createUser(clean(credentials));

    if (response.success) {
      if (user && user.role === "admin") {
        navigate("/admin");
      } else {
        navigate("/signin");
      }

      message.success("User created");
    } else {
      setAlertMessage(response.message);
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
    if (alertMessage) {
      return (
        <Alert
          message={alertMessage}
          type="error"
          showIcon
          style={{ marginBottom: "10px" }}
        />
      );
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
          <Input
            onChange={handleChange}
            name="username"
            value={credentials.username}
          />
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
        <Link to="/signin">Sign in</Link>
      </Form>
    </div>
  );
};

export default Register;
