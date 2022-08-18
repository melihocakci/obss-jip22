import React, { useContext, useEffect, useState } from "react";
import UserService from "../service/UserService";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import UserProfileActions from "../components/ProfileActions/UserProfileActions";
import AdminProfileActions from "../components/ProfileActions/AdminProfileActions";
import { Typography, List, Spin, Divider, Card, message } from "antd";
import UserContext from "../context/UserContext";
const { Title } = Typography;

export default () => {
  const navigate = useNavigate();
  const [userDetails, setUserDetails] = useState();
  const { user } = useContext(UserContext);

  const { id } = useParams();

  useEffect(() => {
    fetch();
  }, []);

  const fetch = async () => {
    const response = await UserService.fetchUser(id);

    if (response.success) {
      setUserDetails(response.body);
    } else {
      message.error(response.message);
    }
  };

  const profileActions = () => {
    if (!user) {
      return;
    }

    if (user.id === userDetails.id) {
      return <UserProfileActions />;
    }

    if (user.role === "admin") {
      return <AdminProfileActions />;
    }
  };

  if (!userDetails) {
    return <Spin />;
  }

  return (
    <div>
      <div style={{ display: "inline-block" }}>
        <Title>{userDetails.username}</Title>
      </div>

      {profileActions()}

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
        dataSource={userDetails.readBooks}
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
        dataSource={userDetails.favoriteBooks}
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
