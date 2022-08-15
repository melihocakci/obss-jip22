import { Form, Input, Button, Spin } from "antd";
import { Link, useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import BookService from "../service/BookService";

const UpdateBook = () => {
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
    const user = await BookService.fetchBook(id);
    setCurrentCredentials(user);
    setLoading(false);
  };

  const onFinish = async (values) => {
    console.log("Success:", values);

    const response = await BookService.updateBook(id, credentials);
    if (response) {
      navigate("/books/" + id);
      alert("Book updated");
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
      <h1>Update Book</h1>
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
          label="Name"
          name="name"
          rules={[
            {
              required: false,
              message: "Please input name!",
            },
          ]}>
          <Input onChange={handleChange} name="name" value={credentials.name} placeholder={currentCredentials.name} />
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
