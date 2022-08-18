import React, { useEffect } from "react";
import { useParams } from "react-router-dom";
import UserService from "../../service/UserService";
import { useState } from "react";
import { Typography, Spin, Divider, Button, message } from "antd";
const { Title } = Typography;

export default () => {
  const [read, setRead] = useState();
  const [favorite, setFavorite] = useState();
  const [loading, setLoading] = useState(true);
  const { id: bookId } = useParams();

  useEffect(() => {
    fetch();
  }, []);

  const fetch = async () => {
    setLoading(true);
    const response = await UserService.fetchThisUser();

    const { body: user } = response;

    let isRead = false;
    for (let i = 0; i < user.readBooks.length; i++) {
      if (user.readBooks[i].id == bookId) {
        isRead = true;
        break;
      }
    }
    setRead(isRead);

    let isFavorite = false;
    for (let i = 0; i < user.favoriteBooks.length; i++) {
      if (user.favoriteBooks[i].id == bookId) {
        isFavorite = true;
        break;
      }
    }
    setFavorite(isFavorite);

    setLoading(false);
  };

  const toggleRead = async () => {
    if (!read) {
      await UserService.addRead(bookId);
      message.success("Added to read books");
    } else {
      await UserService.removeRead(bookId);
      message.success("Removed from read books");
    }

    fetch();
  };

  const toggleFavorite = async () => {
    if (!favorite) {
      await UserService.addFavorite(bookId);
      message.success("Added to favorite books");
    } else {
      await UserService.removeFavorite(bookId);
      message.success("Removed from favorite books");
    }

    fetch();
  };

  if (loading) {
    return <Spin />;
  }

  return (
    <div style={{ float: "right", display: "inline-block" }}>
      <Divider orientation="left">
        <Title level={5}>Actions</Title>
      </Divider>
      <Button onClick={toggleRead}>
        {read ? "Remove from read list" : "Add to read list"}
      </Button>
      <Button onClick={toggleFavorite}>
        {favorite ? "Remove from favorite list" : "Add to favorite list"}
      </Button>
    </div>
  );
};
