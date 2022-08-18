import { Form, Input, Button, Spin, message, Typography, Alert } from "antd";
import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import BookService from "../service/BookService";
import clean from "../util/clean";
const { Title } = Typography;

const UpdateBook = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [loading, setLoading] = useState(true);
  const [currentCredentials, setCurrentCredentials] = useState({});
  const [credentials, setCredentials] = useState({});
  const [alertMessage, setAlertMessage] = useState();

  useEffect(() => {
    fetch();
  }, []);

  const fetch = async () => {
    setLoading(true);
    const { body: user } = await BookService.fetchBook(id);
    setCurrentCredentials(user);
    setLoading(false);
  };

  const onFinish = async () => {
    const response = await BookService.updateBook(id, clean(credentials));

    if (response.success) {
      navigate("/books/" + id);
      message.success("Book updated");
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

  if (loading) {
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
        <Title level={3}>Update book</Title>

        {getAlert()}

        <Form.Item
          label="Name"
          name="name"
          rules={[
            {
              required: false,
              message: "Please input name!",
            },
          ]}>
          <Input
            onChange={handleChange}
            name="name"
            value={credentials.name}
            placeholder={currentCredentials.name}
          />
        </Form.Item>

        <Form.Item
          label="Author"
          name="author"
          rules={[
            {
              required: false,
              message: "Please input author!",
            },
          ]}>
          <Input
            onChange={handleChange}
            name="author"
            value={credentials.author}
            placeholder={currentCredentials.author}
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

export default UpdateBook;
