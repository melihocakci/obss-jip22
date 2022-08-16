import React from "react";
import { useNavigate, useParams } from "react-router-dom";
import BookService from "../../service/BookService";
import { Typography, Divider, Button } from "antd";
const { Title } = Typography;

export default () => {
  const navigate = useNavigate();
  const { id: bookId } = useParams();

  const removeBook = async () => {
    const response = await BookService.removeBook(bookId);

    if (response) {
      navigate("/books");
      alert("Book deleted");
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

      <Button onClick={removeBook}>Remove Book</Button>
      <Button onClick={updateBook}>Update Book</Button>
    </div>
  );
};
