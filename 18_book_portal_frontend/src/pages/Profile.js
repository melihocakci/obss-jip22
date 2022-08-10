import React, { useEffect, useState } from "react";
import "antd/dist/antd.css";
import UserService from "../service/UserService";
import { useParams } from "react-router-dom";

import { Table } from "antd";

const columns = [
    {
        title: "Name",
        dataIndex: "name",
        sorter: true,
        width: "20%",
    },
    {
        title: "Author",
        dataIndex: "author",
        sorter: true,
        width: "20%",
    },
];

const Profile = (props) => {
    const [user, setUser] = useState();

    const { id } = useParams();

    useEffect(() => {
        UserService.fetchUser(id).then((user) => {
            setUser(user);
        });
    }, []);

    if (user) {
        return (
            <div>
                <h1>Username: {user.username}</h1>
                <h3>Read Books:</h3>
                <ul>
                    {user.read_list.map((book) => {
                        return (
                            <li key={book.id}>
                                {book.name}, {book.author}
                            </li>
                        );
                    })}
                </ul>
                <h3>Favorite Books:</h3>
                <ul>
                    {user.favorite_list.map((book) => {
                        return (
                            <li key={book.id}>
                                {book.name}, {book.author}
                            </li>
                        );
                    })}
                </ul>
            </div>
        );
    } else {
        return <h1>Loading</h1>;
    }
};

export default Profile;
