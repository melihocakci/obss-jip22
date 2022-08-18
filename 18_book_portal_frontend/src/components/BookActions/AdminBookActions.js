import React from "react";
import { useNavigate, useParams } from "react-router-dom";
import BookService from "../../service/BookService";
import { Typography, Divider, Button, Popconfirm, message } from "antd";
const { Title } = Typography;

export default () => {
  const navigate = useNavigate();
  const { id: bookId } = useParams();

  const removeBook = async () => {
    const response = await BookService.removeBook(bookId);

    if (response.success) {
      navigate("/books");
      message.success("Book deleted");
    }
  };

  const updateBook = () => {
    navigate("/books/" + bookId + "/update");
  };

  return (
    <div style={{ float: "right", display: "inline-block" }}>
      <Divider orientation="left">
        <Title level={5}>Actions</Title>
      </Divider>

      <Popconfirm
        title="Are you sure?"
        okText="Yes"
        cancelText="No"
        onConfirm={removeBook}>
        <Button>Delete Book</Button>
      </Popconfirm>

      <Button onClick={updateBook}>Update Book</Button>
    </div>
  );
};
