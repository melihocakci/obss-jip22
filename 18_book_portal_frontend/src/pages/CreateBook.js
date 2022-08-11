import { Form, Input, Button } from "antd";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import AuthService from "../service/AuthService";
import UserService from "../service/UserService";
import BookService from "../service/BookService";

const CreateBook = () => {
    const navigate = useNavigate();
    const [credentials, setCredentials] = useState({});

    const onFinish = async (values) => {
        console.log("Success:", values);

        const response = await BookService.createBook(credentials);
        if (response) {
            navigate("/admin");
            alert("Book added");
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
                <h1>Create Book</h1>
                <Form.Item
                    label="Name"
                    name="name"
                    rules={[
                        {
                            required: true,
                            message: "Please input name!",
                        },
                    ]}>
                    <Input onChange={handleChange} name="name" value={credentials.name} />
                </Form.Item>

                <Form.Item
                    label="Author"
                    name="author"
                    rules={[
                        {
                            required: true,
                            message: "Please input author!",
                        },
                    ]}>
                    <Input onChange={handleChange} name="author" value={credentials.author} />
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

export default CreateBook;
