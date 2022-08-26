import React, { useContext, useEffect, useState } from "react";
import UserService from "../service/UserService";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { Typography, List, Spin, Divider, Card, message, Button } from "antd";
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

    if (!response.success) {
      message.error(response.message);
      return;
    }

    setUserDetails(response.body);
  };

  const SettingsButton = () => {
    if (user && user.role === "admin")
      return (
        <div className="actions">
          <Button
            onClick={() => {
              navigate("/users/" + id + "/settings");
            }}>
            Account Settings
          </Button>
        </div>
      );
  };

  if (!userDetails) {
    return <Spin />;
  }

  return (
    <div>
      <div style={{ display: "inline-block" }}>
        <Title>{userDetails.username}</Title>
      </div>

      <SettingsButton />

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
            <a>
              <Card
                title={book.name}
                onClick={() => {
                  navigate("/books/" + book.id);
                }}>
                {book.author}
              </Card>
            </a>
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
            <a>
              <Card
                title={book.name}
                onClick={() => {
                  navigate("/books/" + book.id);
                }}>
                {book.author}
              </Card>
            </a>
          </List.Item>
        )}
      />
    </div>
  );
};
