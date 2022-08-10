import React, { useEffect } from "react";
import "antd/dist/antd.css";
import LocalStorageService from "../util/LocalStorageUtil";
import jwt from "jwt-decode";
import { Link, useNavigate } from "react-router-dom";
import UserService from "../service/UserService";
import BookService from "../service/BookService";
import { useState } from "react";
import { Spin } from "antd";

const BookActions = (props) => {
    const navigate = useNavigate();
    const [read, setRead] = useState();
    const [favorite, setFavorite] = useState();
    const [loading, setLoading] = useState(true);
    const token = LocalStorageService.getToken();

    useEffect(() => {
        fetch();
    }, []);

    const fetch = async () => {
        setLoading(true);
        const user = await UserService.fetchAuthUser();

        setRead(false);
        for (let i = 0; i < user.readBooks.length; i++) {
            if (user.readBooks[i].id == props.bookId) {
                setRead(true);
                break;
            }
        }

        setFavorite(false);
        for (let i = 0; i < user.favoriteBooks.length; i++) {
            if (user.favoriteBooks[i].id == props.bookId) {
                setFavorite(true);
                break;
            }
        }
        setLoading(false);
    };

    const toggleRead = async () => {
        if (!read) {
            await UserService.addRead(props.bookId);
        } else {
            await UserService.removeRead(props.bookId);
        }

        fetch();
    };

    const toggleFavorite = async () => {
        if (!favorite) {
            await UserService.addFavorite(props.bookId);
        } else {
            await UserService.removeFavorite(props.bookId);
        }

        fetch();
    };

    const removeBook = async () => {
        const response = BookService.removeBook(props.bookId);

        if (response) {
            navigate("/books");
        }
    };

    if (!token) {
        return;
    }

    const { role } = jwt(token);
    if (role == "ROLE_ADMIN") {
        return (
            <div>
                <h3>Actions:</h3>
                <button onClick={removeBook}>Remove Book</button>
                <button
                    onClick={() => {
                        navigate("/books/" + props.bookId + "/update");
                    }}>
                    Update Book
                </button>
            </div>
        );
    } else if (role == "ROLE_USER") {
        if (loading) {
            return <Spin />;
        }

        return (
            <div>
                <h3>Actions:</h3>
                <button onClick={toggleRead}>{read ? "Remove from read list" : "Add to read list"}</button>
                <button onClick={toggleFavorite}>
                    {favorite ? "Remove from favorite list" : "Add to favorite list"}
                </button>
            </div>
        );
    }
};

export default BookActions;
