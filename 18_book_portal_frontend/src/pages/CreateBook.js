import { Form, Input, Button, message, Typography, Alert } from "antd";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import BookService from "../service/BookService";
const { Title } = Typography;

const CreateBook = () => {
  const navigate = useNavigate();
  const [credentials, setCredentials] = useState({});
  const [alertMessage, setAlertMessage] = useState();

  const onFinish = async () => {
    const response = await BookService.createBook(credentials);

    if (response.success) {
      navigate("/admin");
      message.success("Book created");
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
        <Title level={3}>Create Book</Title>

        {getAlert()}

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
          <Input
            onChange={handleChange}
            name="author"
            value={credentials.author}
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

export default CreateBook;
