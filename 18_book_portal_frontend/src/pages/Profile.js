import React, { useEffect, useState } from "react";
import "antd/dist/antd.css";
import UserService from "../service/UserService";
import AuthService from "../service/AuthService";
import { Link, useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import UserActions from "../components/UserActions";
import ThisUser from "../util/ThisUser";
import { Typography, List, Spin, Divider, Card } from "antd";
const { Title } = Typography;

const Profile = (props) => {
  const navigate = useNavigate();
  const [user, setUser] = useState();
  const [loading, setLoading] = useState(true);

  const { id } = useParams();

  useEffect(() => {
    fetch();
  }, []);

  const fetch = async () => {
    setLoading(true);
    const user = await UserService.fetchUser(id);
    setUser(user);
    setLoading(false);
  };

  const readBooks = () => {
    if (user.readBooks.length == 0) {
      return "None";
    }

    return user.readBooks.map((book) => {
      return (
        <li key={book.id}>
          <Link to={"/books/" + book.id}>
            {book.name}, {book.author}
          </Link>
        </li>
      );
    });
  };

  const favoriteBooks = () => {
    if (user.favoriteBooks.length == 0) {
      return "None";
    }

    return user.favoriteBooks.map((book) => {
      return (
        <li key={book.id}>
          <Link to={"/books/" + book.id}>
            {book.name}, {book.author}
          </Link>
        </li>
      );
    });
  };

  if (loading) {
    return <Spin />;
  }

  return (
    <div>
      <div style={{ display: "inline-block" }}>
        <Title>{user.username}</Title>
      </div>
      <div style={{ float: "right", display: "inline-block" }}>
        <UserActions userId={id} />
      </div>

      <Divider orientation="left">
        <Title level={5}>Read Books</Title>
      </Divider>

      <List
        grid={{
          gutter: 16,
          xs: 1,
          sm: 2,
          md: 4,
          lg: 4,
          xl: 6,
          xxl: 3,
        }}
        dataSource={user.readBooks}
        renderItem={(book) => (
          <List.Item>
            <Card
              title={book.name}
              onClick={() => {
                navigate("/books/" + book.id);
              }}>
              {book.author}
            </Card>
          </List.Item>
        )}
      />

      <Divider orientation="left">
        <Title level={5}>Favorite Books</Title>
      </Divider>

      <List
        grid={{
          gutter: 16,
          xs: 1,
          sm: 2,
          md: 4,
          lg: 4,
          xl: 6,
          xxl: 3,
        }}
        dataSource={user.favoriteBooks}
        renderItem={(book) => (
          <List.Item>
            <Card
              title={book.name}
              onClick={() => {
                navigate("/books/" + book.id);
              }}>
              {book.author}
            </Card>
          </List.Item>
        )}
      />
    </div>
  );
};

export default Profile;
