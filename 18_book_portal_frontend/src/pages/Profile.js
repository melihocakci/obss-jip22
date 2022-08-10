import React, { useEffect, useState } from "react";
import "antd/dist/antd.css";
import UserService from "../service/UserService";
import AuthService from "../service/AuthService";
import { Link, useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import UserActions from "../components/UserActions";
import { Spin } from "antd";

const Profile = (props) => {
    const [user, setUser] = useState();

    const { id } = useParams();

    useEffect(() => {
        fetch();
    }, []);

    const fetch = async () => {
        const user = await UserService.fetchUser(id);
        setUser(user);
    };

    if (!user) {
        return <Spin />;
    }

    return (
        <div>
            <h1>Username: {user.username}</h1>
            <h3>Read Books:</h3>
            <ul>
                {user.readBooks.map((book) => {
                    return (
                        <li key={book.id}>
                            <Link to={"/books/" + book.id}>
                                {book.name}, {book.author}
                            </Link>
                        </li>
                    );
                })}
            </ul>
            <h3>Favorite Books:</h3>
            <ul>
                {user.favoriteBooks.map((book) => {
                    return (
                        <li key={book.id}>
                            <Link to={"/books/" + book.id}>
                                {book.name}, {book.author}
                            </Link>
                        </li>
                    );
                })}
            </ul>
            <UserActions userId={id} />
        </div>
    );
};

export default Profile;
