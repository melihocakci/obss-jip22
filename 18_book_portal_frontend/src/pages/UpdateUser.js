import { Form, Input, Button, Spin } from "antd";
import { Link, useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import UserService from "../service/UserService";
import AuthService from "../service/AuthService";
import ThisUser from "../util/ThisUser";

const UpdateUser = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const [loading, setLoading] = useState(true);
    const [currentCredentials, setCurrentCredentials] = useState({});
    const [credentials, setCredentials] = useState({});

    useEffect(() => {
        fetch();
    }, []);

    const fetch = async () => {
        setLoading(true);
        const user = await UserService.fetchUser(id);
        setCurrentCredentials(user);
        setLoading(false);
    };

    const onFinish = async (values) => {
        console.log("Success:", values);
        if (ThisUser.getId() == id) {
            const response = await UserService.updateUser("", credentials);
            if (response) {
                AuthService.signout();
                navigate("/login");
                alert("Account updated");
            }
        } else {
            const response = await UserService.updateUser(id, credentials);
            if (response) {
                navigate("/users/" + id);
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

    if (loading) {
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

export default UpdateUser;
