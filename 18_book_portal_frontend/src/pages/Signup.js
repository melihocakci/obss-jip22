import { Form, Input, Button } from "antd";
import { useNavigate, Link } from "react-router-dom";
import { useContext, useState } from "react";
import UserService from "../service/UserService";
import { Typography, Alert, message, Select } from "antd";
import UserContext from "../context/UserContext";
import clean from "../util/clean";
const { Title } = Typography;
const { Option } = Select;

const Signup = () => {
  const navigate = useNavigate();
  const [credentials, setCredentials] = useState({});
  const [errorMessage, setErrorMessage] = useState();
  const { user } = useContext(UserContext);

  const onFinish = async () => {
    const response = await UserService.createUser(clean(credentials));

    if (!response.success) {
      setErrorMessage(response.message);
      return;
    }

    if (user && user.role === "admin") {
      navigate("/admin");
    } else {
      navigate("/signin");
    }

    message.success("User created");
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
        <Title level={3}>Create account</Title>

        {getAlert()}

        <Form.Item
          label="Username"
          name="username"
          rules={[
            {
              required: true,
              message: "Please input your username!",
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
          <Input onChange={handleChange} name="username" />
        </Form.Item>

        <Form.Item
          name="email"
          label="E-mail"
          rules={[
            {
              type: "email",
              message: "Invalid E-mail!",
            },
            {
              required: true,
              message: "Please input your E-mail!",
            },
          ]}>
          <Input onChange={handleChange} name="email" />
        </Form.Item>

        <Form.Item
          name="gender"
          label="Gender"
          rules={[
            {
              required: true,
              message: "Please select gender!",
            },
          ]}>
          <Select
            placeholder="select your gender"
            onChange={(value) => {
              setCredentials({
                ...credentials,
                gender: value,
              });
            }}
            name="gender">
            <Option value="male">Male</Option>
            <Option value="female">Female</Option>
            <Option value="other">Other</Option>
          </Select>
        </Form.Item>

        <Form.Item
          label="Password"
          name="password"
          rules={[
            {
              required: true,
              message: "Please input your password!",
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
          <Input.Password onChange={handleChange} name="password" />
        </Form.Item>

        <Form.Item
          name="confirm"
          label="Confirm Password"
          dependencies={["password"]}
          hasFeedback
          rules={[
            {
              required: true,
              message: "Please confirm your password!",
            },
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
            Sign up
          </Button>
        </Form.Item>
        <Link to="/signin">Sign in</Link>
      </Form>
    </div>
  );
};

export default Signup;
