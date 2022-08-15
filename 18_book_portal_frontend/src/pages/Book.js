import React, { useEffect, useState } from "react";
import "antd/dist/antd.css";
import BookService from "../service/BookService";
import BookActions from "../components/BookActions";
import { useParams } from "react-router-dom";
import { Typography, Spin, Divider } from "antd";
const { Title } = Typography;

const Book = (props) => {
  const [book, setBook] = useState();

  const { id } = useParams();

  useEffect(() => {
    BookService.fetchBook(id).then((book) => {
      setBook(book);
    });
  }, []);

  if (!book) {
    return <Spin />;
  }

  return (
    <div>
      <div style={{ display: "inline-block" }}>
        <Title>{book.name}</Title>
        <Title level={3}>by {book.author}</Title>
      </div>
      <div style={{ float: "right", display: "inline-block" }}>
        <BookActions bookId={id} />
      </div>

      <Divider orientation="left" style={{ marginTop: 30 }}>
        <Title level={5}>Sypnosis</Title>
      </Divider>

      <p>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore
        magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
        consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
        pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est
        laborum.
      </p>
    </div>
  );
};

export default Book;
