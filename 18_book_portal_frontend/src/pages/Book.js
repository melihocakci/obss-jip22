import React, { useContext, useEffect, useState } from "react";
import BookService from "../service/BookService";
import AdminBookActions from "../components/BookActions/AdminBookActions";
import UserBookActions from "../components/BookActions/UserBookActions";
import { useParams } from "react-router-dom";
import { Typography, Spin, Divider, message } from "antd";
import UserContext from "../context/UserContext";
const { Title } = Typography;

export default () => {
  const [book, setBook] = useState();
  const { user } = useContext(UserContext);

  const { id: bookId } = useParams();

  useEffect(() => {
    fetch();
  }, []);

  const fetch = async () => {
    const response = await BookService.fetchBook(bookId);

    if (response.success) {
      setBook(response.body);
    } else {
      message.error(response.message);
    }
  };

  const bookActions = () => {
    if (!user) {
      return;
    }

    if (user.role === "user") {
      return <UserBookActions />;
    }

    if (user.role === "admin") {
      return <AdminBookActions />;
    }
  };

  if (!book) {
    return <Spin />;
  }

  return (
    <div>
      <div style={{ display: "inline-block" }}>
        <Title>{book.name}</Title>
        <Title level={3}>by {book.author}</Title>
      </div>

      {bookActions()}

      <Divider orientation="left" style={{ marginTop: 30 }}>
        <Title level={5}>Sypnosis</Title>
      </Divider>

      <p>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
        tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim
        veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
        commodo consequat. Duis aute irure dolor in reprehenderit in voluptate
        velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint
        occaecat cupidatat non proident, sunt in culpa qui officia deserunt
        mollit anim id est laborum.
      </p>
    </div>
  );
};
