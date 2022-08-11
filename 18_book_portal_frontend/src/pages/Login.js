import { Form, Input, Button } from "antd";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import AuthService from "../service/AuthService";
import { Typography } from "antd";
const { Title } = Typography;

const Login = () => {
    const navigate = useNavigate();
    const [credentials, setCredentials] = useState({});

    const onFinish = async (values) => {
        console.log("Success:", values);

        const response = await AuthService.signin(credentials);
        if (response) {
            navigate("/");
            window.location.reload();
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
