import React, { useEffect, useState } from "react";
import "antd/dist/antd.css";
import BookService from "../service/BookService";
import BookActions from "../components/BookActions";
import { useParams } from "react-router-dom";
import { Spin } from "antd";

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
            <h1>{book.name}</h1>
            <h1>{book.author}</h1>
            <h3>Sypnosis</h3>
            <p>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
                dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip
                ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu
                fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia
                deserunt mollit anim id est laborum.
            </p>
            <BookActions bookId={id} />
        </div>
    );
};

export default Book;
